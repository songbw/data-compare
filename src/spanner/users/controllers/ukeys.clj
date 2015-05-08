(ns spanner.users.controllers.ukeys
  (:require [clojure.string :as str]
            [ring.util.response :as resp])
  (:require [spanner.users.models.ukey :as ukey]
            [spanner.users.models.user :as user]
            [spanner.users.controllers.users :as users]
            [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            )
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]]))

(defn create
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        username (str (get params :username))
        serialno (str (get params :serialno))]
    (if (users/authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [kid (uuid/gen-uuid)
            user_id (:uid (first (user/find-user-by-username username)))]
        (ukey/create! kid (bigdec user_id) username serialno)
        (resp/response {:success true})))
    ))

(defn delete
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (users/authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [id (get params :id)]
        (if (nil? (ukey/delete! id))
          (web/error-response -210103 "ukey is not exist.")
          (web/ok-response))))))

(defn find-by-user
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (users/authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [username (get params :username)]
      (resp/response {:ukeys (ukey/find-by-uid username)})))
    ))

(defn all
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (users/authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [currentpage (get params :currentpage 0)
            linesize (get params :linesize 20)
            offsets (* (- (Integer/parseInt currentpage) 1) (Integer/parseInt linesize))
            ukeys (ukey/all offsets (Integer/parseInt linesize))
            allcount (get (first (ukey/allcount)) :allcount)
            total-pages (if (= (rem allcount (Integer/parseInt linesize)) 0) (quot allcount (Integer/parseInt linesize)) (+ (quot allcount (Integer/parseInt linesize)) 1))]
        (resp/response {:ukeys ukeys
                        :allcount allcount
                        :totalpages total-pages
                        :currentpage currentpage})))))
