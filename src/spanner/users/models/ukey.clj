 (ns spanner.users.models.ukey
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [kid user_id username serialno]
  (transaction
   (insert ukeys
           (values {:kid kid
                    :users_id user_id
                    :username username
                    :serialno serialno}))))

(defn delete!
  [id]
  (println "id  -------> " id)
  (delete ukeys
          (where {:id (Integer/parseInt id)}))
)

(defn find-by-uid
  [username]
  (select ukeys
          (fields :id :kid :serialno :username)
          (where {:username username})))

(defn find-serialno-by-username
  [username]
  (select ukeys
          (fields :serialno)
          (where {:username username})))

(defn all
  [o l]
  (select ukeys
          (with users)
          (fields :id :kid :users.username :serialno)
          (where {:users.uid :users_id})
          (offset o)
          (limit l)))

(defn allcount
  []
  (select ukeys
          (with users)
          (aggregate (count :*) :allcount)
          (where {:ukeys.users_id :users.uid})))
