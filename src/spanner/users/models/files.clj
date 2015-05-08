(ns spanner.users.models.files
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [fid name type size path creator category]
  (transaction
   (insert files
           (values {:fid fid
                    :name name
                    :type type
                    :size size
                    :path path
                    :creator creator
                    :category category}))))
