(ns spanner.users.controllers.compares
  (:require [clojure.string :as str]
            [ring.util.response :as resp]
            [clojure.contrib.duck-streams :as ds]
            [clj-http.client :as client]
            [clojure.java.io :as io]
            [incanter.excel :as excel]
            [incanter.core :as icore]
            [incanter.io :as iio]
            )
  (:require [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            [clojure.contrib.lazy-xml :as conxml]
            [spanner.users.models.compare :as compare]
            [spanner.users.models.car :as car]
            [spanner.users.models.user :as user]
            [spanner.users.controllers.cars :as cars]
            [spanner.users.models.user-department :as ud]
            [clojure-csv.core :as csv]
            )
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]])
  )

(defn authorized?
  [headers]
  (let [auth-token (get headers "auth-token")]
    (nil? (user/get-uid-by-token (str auth-token)))
    ))

(defn get-department-by-token
  [token]
  (let [username ( (first (user/find-username-by-token token)) :username)
        udp (ud/find-by-username username)]
    (println username udp)
   (first udp)))

(defn color?
  [type]
  (if (= "大型汽车" type)
    "黄"
    "蓝"))

;; select red_list and compare red_list and excel_file
(defn countt?
  [coll]
  true)

(defn count?
  [coll]
  (let [count (get (first coll) 2)
        type (get (first coll) 16)
        color (color? (str/trim type))]
    (if (cars/selectremote (str/trim count) color)
      false
      true)
    ))

(def csv-header [["序号" "记录编号" "号牌号码" "类型编号" "车主姓名" "联系地址" "违法时间" "违法地点" "违法代码" "罚款金额" "抓拍编号" "录入日期" "行驶速度" "路段限速" "超速比例" "联系电话" "车辆类型" "车辆品牌" "违法行为"]])

(defn test-write-csv
  [name]
  (let [data (common/parse-csv (io/reader name :encoding "gbk"))]
    (println (into [] (map #(str/trim %) (first data))))))

(defn test-csv
  [name]
  (let [data (common/parse-csv (io/reader name :encoding "gbk"))
        data-temp (remove #(count? %) data)]
    (doseq [s (rest data-temp)]
      (let [compare-line (first s)
            [serial_no record_no car_no mark_type name address illegal_time illegal_address illegal_code fines capture_no record_time travel_speed speed_limit ultra_rate phone car_type car_brand violation] (into [] (map #(str/trim %) compare-line))
            cid (uuid/gen-uuid)]
        (log/info serial_no "    " car_no)
        (compare/create! cid serial_no record_no car_no mark_type name address illegal_time illegal_address illegal_code fines capture_no record_time travel_speed speed_limit ultra_rate phone car_type car_brand violation)
        ))))

;; save excel file to local and compare save database
(defn upload-file
  [file path department]
  ;; save excel file
  (nil? (io/copy (file :tempfile) (io/file path) :encoding "gbk"))
  (let [;;data (excel/read-xls path)
        ;;data (common/parse-csv (io/reader path :encoding "gb2312"))
        data (common/parse-csv (io/reader (file :tempfile) :encoding "gbk"))
        ;; select red_list and delete excel_file in red_list
        data-temp (filter #(count? %) data)
        ]
    (doseq [s (rest data-temp)]
      (let [compare-line (first s)
            [serial_no record_no car_no mark_type name address illegal_time illegal_address illegal_code fines capture_no record_time travel_speed speed_limit ultra_rate phone car_type car_brand violation] (into [] (map #(str/trim %) compare-line))
            ]
        (log/info "serial_no  " serial_no " recored_no " record_no " car_no " car_no " mark_typ " mark_type " name " name " address " address " illegal_time " illegal_time " illegal_address " illegal_address " illegal_code " illegal_code " fines " fines " capture_no " capture_no " record_time " record_time " travel_speed " travel_speed " speed_limit " speed_limit " ultra_rate " ultra_rate " phone " phone " car_type " car_type " car_brand " car_brand " violation " violation)
        (compare/create! serial_no record_no car_no mark_type name address illegal_time illegal_address illegal_code fines capture_no record_time travel_speed speed_limit ultra_rate phone car_type car_brand violation department)
        ))
    )
)

(defn create
  [req]
  (println req)
  (let [params (get req :params)
        headers (get req :headers)
        file (get params "file")
        name (get file :filename)
       ;; type ((str/split name #"\.") 1)
        size (get file :size)
        department (get params "department")
        path (str "fileupload/")
        temppath (str "fileupload/" (common/currenttime) "_" department ".csv")
        creator (str "songbw")]
    (println department "******************************")
    (upload-file file temppath department)
    (let [iid (uuid/gen-uuid)]
      ;;(image/create! iid md5 name type size path creator category description)
      (resp/response {:success true
                      :image {:iid iid}}))))

(def compare-keys [:serial_no :record_no :car_no :mark_type :name :address :illegal_time :illegal_address :illegal_code :fines :capture_no :record_time :travel_speed :speed_limit :ultra_rate :phone :car_type :car_brand :violation])

(defn vec-vals
  [coll key]
  (let [value (get coll key)]
    (if (string? value)
      value
      (str value)))
  )

(defn get-excel
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        token (get params :auth)
        ;;        csv-data (csv/write-csv temp-data1 :end-of-line "\n")
        ]
    (if (nil? token)
      (web/error-response -210104 "Token Unauthorized.")
      (let [department (get (get-department-by-token token) :department_id 1)
            begin (get params :begin "2014-04-25")
            end (get params :end "2014-03-26")
            data (compare/all-excel department begin end)
            temp-data (for [s data]
                        (into [] (map #(vec-vals s %) compare-keys)))
            temp-data1 (into csv-header temp-data)
            filename (str (common/currenttime) "_" department ".xls")]
        (log/info temp-data1)
        (excel/save-xls (icore/to-dataset temp-data1) filename :encoding "gbk")
        (-> (resp/response (io/input-stream filename))
            (resp/content-type "application/vnd.ms-excel"))
        ))
    ;;    (spit "out.csv" csv-data :encoding "gbk")
    )
  )

(defn all
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        token (get headers "auth-token")]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [currentpage (get params :currentpage 1)
            linesize (get params :linesize 20)
            department (get (get-department-by-token token) :department_id 1)
            begin (get params :begin "2014-04-25")
            end (get params :end "2014-03-26")
            offset (* (- (read-string (str currentpage)) 1) (read-string (str linesize)))
            compares (compare/all offset (read-string (str linesize)) department begin end)
            allcount (get (first (compare/allcount department begin end)) :allcount)
            total-pages (if (= (rem (read-string (str allcount)) (read-string (str linesize))) 0) (quot (read-string (str allcount)) (read-string (str linesize))) (+ (quot (read-string (str allcount)) (read-string (str linesize))) 1))]
        (log/info "get all params have currentpage : " currentpage " linesize : " linesize)
        (resp/response {:compares compares
                        :allcount allcount
                        :totalpages total-pages
                        :currentpage currentpage})))
    ))
