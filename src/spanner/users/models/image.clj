(ns spanner.users.models.image
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [iid md5 name type size path creator category description]
  (transaction
   (insert images
           (values {:iid iid
                    :md5 md5
                    :name name
                    :type type
                    :size size
                    :path path
                    :creator creator
                    :category category
                    :description description}))))

(defn all
  []
  (select images
          (fields :path :md5 :type :size)))
