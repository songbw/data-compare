(ns spanner.users.models.department
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [did father-id description name phone email address]
  (transaction
   (insert departments
           (values {:did did
                    :father_id (bigdec father-id)
                    :description description
                    :name name
                    :phone phone
                    :email email
                    :address address}))))

(defn update!
  [id father-id description name phone email address]
  (transaction
   (update departments
           (set-fields {:father_id father-id
                        :description description
                        :name name
                        :phone phone
                        :email email
                        :address address})
           (where {:id (read-string (str id))}))))

(defn delete!
  [id]
  (delete departments
          (where {:id (read-string (str id))})))

(defn all
  [o l]
  (select departments
          (fields :id :did :father_id :description :name :phone :email :address)
          (order :id :DESC)
          (offset o)
          (limit l)))

(defn all-map
  []
  (select departments
          (fields :id :name)))

(defn allcount
  []
  (select departments
          (aggregate (count :*) :allcount)
          ))

(defn find-department-by-id
  [id]
  (select departments
          (fields :id :did :father_id :description :name :phone :email :address)
          (where {:id (read-string (str id))})))

(defn find-by-name
  [name]
  (select departments
          (fields :name)
          (where {:name name})))
