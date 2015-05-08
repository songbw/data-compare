(ns spanner.users.models.user
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [uid username password is-admin tel company name pid]
  (transaction
   (insert users
           (values {:uid uid
                    :username username
                    :password password
                    :is_admin is-admin}))
   (insert profile
           (values {:pid pid
                    :user_id uid
                    :username username
                    :tel tel
                    :company company
                    :name name}))))

(defn update!
  [username name tel company is_admin password]
  (transaction
   (update users
           (set-fields {:is_admin is_admin
                        :password password})
           (where {:username username}))
   (update profile
           (set-fields {:name name
                        :tel tel
                        :company company})
           (where {:username username}))
   )
  )

(defn update-token
  [username access_token refresh_token expires_in]
  (update users
          (set-fields {:access_token access_token
                       :refresh_token refresh_token
                       :expires_in expires_in})
          (where {:username username})))

(defn delete!
  [username]
  (delete users
            (where {:username username}))
)

(defn- find-user-id
  [token]
  (select users
          (fields :uid)
          (where {:access_token token})))

(defn find-username-by-token
  [token]
  (select users
          (fields :username)
          (where {:access_token token})))

(defn get-uid-by-token
  [token]
  (let [user-id (find-user-id token)]
    (:uid (first user-id))))

(defn find-username
  [username]
  (select users
          (fields :username)
          (where {:username username})))

(defn get-username-by-token
  [token]
  (let [username (find-username token)]
    (:username (first username))))

(defn get-is-admin-by-token
  [token]
  (select users
          (fields :is_admin)
          (where {:access_token token})))

(defn find-is-admin
  [token]
  (let [is_admin (get-is-admin-by-token token)]
     (:is_admin (first is_admin))))

(defn- find-pwd
  [username]
  (select users
          (fields :password)
          (where {:username username})))

(defn get-pwd-by-username
  [username]
  (let [pwd (find-pwd username)]
    (:password (first pwd))))

(defn- find-admin
  [username]
  (select users
          (fields :is_admin)
          (where {:username username})))

(defn admin?
  [username]
  (let [is-admin (find-admin username)]
    (:is_admin (first is-admin)))
  )

(defn change-password
  [username password]
  (update users
          (set-fields {:password password})
          (where {:username username})))

(defn activate!
  [username status]
  (update users
          (set-fields {:status status})
          (where {:username username}))
  )

(defn find-user-by-username
  [username]
  (select users
          (with profile)
          (fields :uid :username :is_admin :profile.name :profile.tel :profile.company)
          (where {:username username
                  :uid :profile.user_id})))

(defn all
  [o l]
  (println "o------>" o "    l------------>" l)
  (select users
          (with profile)
          (fields :uid :username :status :profile.name :profile.tel)
          (where {:uid :profile.user_id
                  :is_admin false})
          (order :id :DESC)
          (offset o)
          (limit l)))

(defn allcount
  []
  (select users
          (with profile)
          (aggregate (count :*) :allcount)
          (where {:uid :profile.user_id
                  :is_admin false})))
