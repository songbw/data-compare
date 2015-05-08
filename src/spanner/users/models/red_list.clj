(ns spanner.users.models.red_list
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [rid car_no mark_type frame_no tag_color]
  (transaction
   (insert red_list
           (values {:rid rid
                    :car_no car_no
                    :mark_type mark_type
                    :frame_no frame_no
                    :tag_color tag_color}))))

(defn update!
  [id car_no mark_type frame_no tag_color]
  (transaction
   (update red_list
           (set-fields {:car_no car_no
                        :mark_type mark_type
                        :frame_no frame_no
                        :tag_color tag_color})
           (where {:id (read-string (str id))}))))

(defn delete!
  [id]
  (delete red_list
          (where {:id (read-string (str id))})))

(defn all
  [o l]
  (select red_list
          (fields :id :rid :car_no :mark_type :frame_no :tag_color)
          (order :id :DESC)
          (offset o)
          (limit l)))

(defn allcount
  []
  (select red_list
          (aggregate (count :*) :allcount)))

(defn find-red_list-by-id
  [id]
  (select red_list
          (fields :id :rid :car_no :mark_type :frame_no :tag_color)
          (where {:id (read-string (str id))})))
