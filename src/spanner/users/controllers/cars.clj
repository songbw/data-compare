(ns spanner.users.controllers.cars
  (:require [clojure.string :as str]
            [ring.util.response :as resp]
            [clj-http.client :as client]
            [clojure.xml :as pxml]
            [clojure.zip :as pzip])
  (:require [spanner.users.models.car :as car]
            [spanner.users.models.user :as user]
            [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            [clojure.contrib.lazy-xml :as conxml]
)
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]])
  (:import (utils Common Common)))

(defn authorized-client?
  [auth-token]
  (nil? (user/get-uid-by-token (str auth-token)))
  )

(def wlmquri "http://172.16.3.102:8080/LableService/WlMQLBserver.asmx")

(def wlmqparams "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetLabelData xmlns=\"http://tempuri.org/\"><vehicleTagNo>%s</vehicleTagNo><vehicleNoColor>%s</vehicleNoColor></GetLabelData></soap:Body></soap:Envelope>")

(def headers {"Content-Type" "text/xml; charset=utf-8"
             })

(defn- post-wlmq
  [tag color]
;;  (log/info "post-wlmq _------------> " tag "       " color )
  (let [xmlbody (format wlmqparams tag color)
        wlmqresp (client/post wlmquri
                         {:body xmlbody
                          :headers headers})]
;;    (log/info "resp : \n" wlmqresp)
    wlmqresp))

(defn escape-xml
  [text]
 ;; (log/info "escape - xml -------------> " text)
  (clojure.string/replace text #"&lt;|&gt;" {"&lt;" "<" "&gt;" ">"}))

(defn- parse-aotuid
  [post-wlmq node]
  (let [body (get post-wlmq :body)
        escapebody (str (escape-xml body))
        xml-file (pxml/parse (java.io.ByteArrayInputStream. (.getBytes escapebody)))]
    (first (for [x (xml-seq xml-file)
                 :when (= node (:tag x))]
             [(keyword node) (first (:content x))]))))

(defn- parse2json
  [file]
  (into {}
        (map parse-aotuid
            (into [] (repeat 9 file)) [:label_no :mark_type :regist_date :fuel_type :emission_norm :tag_color :period_date :issued_date :car_id])))

(defn string2date
  [str-date]
  (.. (java.text.SimpleDateFormat. "yyyy-MM-dd") (parse str-date)))

(defn string2sqldate
  [str-date]
  (java.sql.Date. (.getTime (string2date str-date))))

(defn selectremote
  [count color]
  (let [post-wlmq (post-wlmq count color)
        wlmq2json (parse2json post-wlmq)
        ]
    (if (nil? (get wlmq2json :label_no))
      true
      false
)))

(defn create
  [req]
  (log/info "cars shuchu..." req)
  (let [body (get req :body)
        headers (get req :headers)
        params (get req :params)
        auth_token (get params :auth_token)
        auto (get params :auto)
        number (get auto :number)
        color (get auto :color)
        mode (get auto :mode)
        serialno (get auto :serialno)]
    (if (authorized-client? auth_token)
      (web/error-status "Token Unauthorized." 401)
      (let [post-wlmq (post-wlmq number color)
            wlmq2json (parse2json post-wlmq)]
        (resp/response {:respone "ok"
                          :auto wlmq2json})
        (if (nil? (get wlmq2json :label_no))
          (resp/response {:respone "ok"
                          :auto {:label_id (:label_no wlmq2json)
                                 :label_type (:mark_type wlmq2json)
                                 :regist_at (get wlmq2json :regist_date "")
                                 :fuel_type (:fuel_type wlmq2json)
                                 :emission (:emission_norm wlmq2json)
                                 :color (:tag_color wlmq2json)
                                 :period_at (get wlmq2json :period_date "")
                                 :auto_type (get wlmq2json :car_id)
                                 :issued_at (get wlmq2json :issued_date "")
                                 :number number}})
          (let [label_id (:label_no wlmq2json)
                label_type (:mark_type wlmq2json)
                regist_at (get wlmq2json :regist_date "")
                fuel_type (:fuel_type wlmq2json)
                emission (:emission_norm wlmq2json)
                color (:tag_color wlmq2json)
                period_at (get wlmq2json :period_date "")
                auto_type (get wlmq2json :car_id)
                issued_at (get wlmq2json :issued_date "")
                ]
            (car/create! (uuid/gen-uuid)
                         label_id
                         label_type
                         (string2sqldate regist_at)
                         fuel_type
                         emission
                         color
                         (string2sqldate period_at)
                         (string2sqldate issued_at)
                         auto_type)
            (resp/response {:respone "ok"
                            :auto {:label_id label_id
                                   :label_type label_type
                                   :regist_at regist_at
                                   :fuel_type fuel_type
                                   :emission emission
                                   :color color
                                   :period_at period_at
                                   :auto_type auto_type
                                   :issued_at issued_at
                                   :number number}})
            ))
        )
      )
    ))
