(ns spanner.users.controllers.red-lists
  (:require [clojure.string :as str]
            [ring.util.response :as resp]
            [clj-http.client :as client])
  (:require [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            [spanner.users.models.red_list :as red_list]
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
        car_no (get params :car_no)
        mark_type (get params :mark_type)
        tag_color (get params :tag_color)
        frame_no (get params :frame_no)]
    (log/info "create params have car_no : " car_no " mark_type : " mark_type " tag_color : " tag_color " frame_no : " frame_no)
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (do
        (red_list/create! (uuid/gen-uuid) car_no mark_type frame_no tag_color)
        (resp/response {:success true})))
    ))

(defn update
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        id (get params :id)
        car_no (get params :car_no)
        mark_type (get params :mark_type)
        tag_color (get params :tag_color)
        frame_no (get params :frame_no)]
    (log/info "update params have car_no : " car_no " mark_type : " mark_type " tag_color : " tag_color " frame_no : " frame_no)
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (do
        (red_list/update! id car_no mark_type frame_no tag_color)
        (resp/response {:success true})))
    ))

(defn delete
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        id (get params :id)]
    (if (nil? id)
      (web/error-response -200003 "id is null.")
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (do
          (red_list/delete! id)
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
            red_lists (red_list/all offset (read-string (str linesize)))
            allcount (get (first (red_list/allcount)) :allcount)
            total-pages (if (= (rem allcount (read-string (str linesize))) 0) (quot allcount (read-string (str linesize))) (+ (quot allcount (read-string (str linesize))) 1))]
        (log/info "get all params have currentpage : " currentpage " linesize : " linesize)
        (resp/response {:red_lists red_lists
                        :allcount allcount
                        :totalpages total-pages
                        :currentpage currentpage})))
    ))

(defn get-red_list-by-rid
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        id (get params :id)]
    (log/info "get red_list params have id : " id)
    (if (nil? id)
      (web/error-response -200003 "id is null.")
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (do
          (resp/response (first (red_list/find-red_list-by-id id))))))
    ))
