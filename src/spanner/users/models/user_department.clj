(ns spanner.users.models.user-department
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [ud_id department_id username]
  (transaction
   (insert user_department
           (values {:ud_id ud_id
                    :department_id (read-string (str department_id))
                    :username username}))))

(defn update!
  [id department_id username]
  (transaction
   (update user_department
           (set-fields {:department_id (read-string (str department_id))
                        :username username})
           (where {:id (read-string (str id))}))))

(defn delete!
  [id]
  (delete user_department
          (where {:id (read-string (str id))})))

(defn verifyone
  [department_id username]
  (select user_department
          (fields :department_id :uesrname)
          (where {:deparment_id (read-string (str department_id))
                  :username username})))

(defn find-by-id
  [id]
  (select user_department
          (fields :department_id :username)
          (where {:id (read-string (str id))})))

(defn find-by-username
  [username]
  (select user_department
          (fields :id :department_id :username)
          (where {:username username})))
