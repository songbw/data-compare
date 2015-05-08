(ns spanner.users.controllers.images
  (:require [clojure.string :as str]
            [ring.util.response :as resp]
            [clojure.contrib.duck-streams :as ds])
  (:require [spanner.users.models.image :as image]
            [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            )
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]]))

(defn- upload-file
  [file path]
  (println file)
  (nil? (ds/copy (file :tempfile) (ds/file-str path))))

(defn create
  [req]
  (println req)
  (let [params (get req :params)
        headers (get req :headers)
        file (get params "file")
       ;; md5 (get params :md5)ls
        md5 (str "songbwmd522")
        name (get file :filename)
        ;;type (get file :content-type)
        type ((str/split name #"\.") 1)
        size (get file :size)
        category (get params :category)
        description (get params :description)
        path (str "fileupload/")
        temppath (str "fileupload/" md5 "." type)
        creator (str "songbw")]
    (upload-file file temppath)
    (let [iid (uuid/gen-uuid)]
      (image/create! iid md5 name type size path creator category description)
      (resp/response {:success true
                      :image {:iid iid}}))))

(defn all
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (let []
      (resp/response {:images (image/all)
                      :success true}))))

(defn render [t]
  (apply str t))

(defn find-image
  [req]
   (println req)
  (let [params (get req :params)
        headers (get req :headers)]
    (let [iid (get params :iid)]
    )))
