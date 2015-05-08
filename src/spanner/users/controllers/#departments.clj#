(ns spanner.users.controllers.departments
  (:require [clojure.string :as str]
            [ring.util.response :as resp]
            [clj-http.client :as client]
            )
  (:require [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            [spanner.users.models.department :as department]
            [spanner.users.models.user :as user])
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]]))

(defn admin?
  [token]
  (user/find-is-admin token))

(defn authorized?
  [headers]
  (let [auth-token (get headers "auth-token")]
    (log/info "authorized user is admin " (admin? auth-token))
    (and (nil? (user/get-uid-by-token (str auth-token))) (admin? auth-token))))

(defn create
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        father-id (get params :father_id 0)
        description (get params :description)
        name (get params :name)
        phone (get params :phone)
        email (get params :email)
        address (get params :address)]
    (log/info "create params have father-id: " father-id " description: " description " name : " name " phone : " phone " email " email " address " address)
    (if (nil? name)
      (web/error-response -200001 "name is null.")
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (do
          (department/create! (uuid/gen-uuid) father-id description name phone email address)
          (resp/response {:success true}))))
))

(defn update
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        id (get params :id)
        father-id (get params :father_id 0)
        description (get params :description)
        name (get params :name)
        phone (get params :phone)
        email (get params :email)
        address (get params :address)]
    (log/info "update params have father-id: " father-id " description: " description " name : " name " phone : " phone " email " email " address " address)
    (if (nil? name)
      (web/error-response -200001 "name is null.")
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (do
          (department/update! id father-id description name phone email address)
          (resp/response {:success true}))))
))

(defn delete
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        did (get params :id)]
    (if (nil? did)
      (web/error-response -200002 "did is null.")
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (do
          (department/delete! did)
          (resp/response {:success true}))))
))

(defn all
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (let [currentpage (get params :currentpage 1)
              linesize (get params :linesize 20)
              offset (* (- (read-string (str currentpage)) 1) (read-string (str linesize)))
              departments (department/all offset (read-string (str linesize)))
              allcount (get (first (department/allcount)) :allcount)
              total-pages (if (= (rem (read-string (str allcount)) (read-string (str linesize))) 0) (quot (read-string (str allcount)) (read-string (str linesize))) (+ (quot (read-string (str allcount)) (read-string (str linesize))) 1))]
          (log/info "get all params have currentpage : " currentpage " linesize : " linesize)
          (resp/response {:departments departments
                          :allcount allcount
                          :totalpages total-pages
                          :currentpage currentpage})))
    ))

(defn get-department-by-did
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        id (get params :id)]
    (log/info "get department params have id : " id)
    (if (nil? id)
      (web/error-response -200003 "id is null.")
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (resp/response (first (department/find-department-by-id id)))))
    ))

(defn get-all-name
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        ]
    (resp/response (department/all-map))))

(defn verify-name
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        name (get params :name)
        type (get params :type)]
    (if (= 1 (read-string (str type)))
      ;; add
      (resp/response {:success (nil? (first (department/find-by-name name)))})
      ;; update
      (resp/response {:success (nil? (first (into [] (rest (department/find-by-name name)))))})
      )
    ))
