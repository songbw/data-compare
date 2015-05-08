(ns spanner.users.models.entities
  (:use [korma.core]))

(declare users profile ukeys images files departments red_list compares)

(defentity users
  (table :users)
  ;;(database db-pg)
  (pk :uid)
  (has-one profile {:fk :user_id})
  (has-many ukeys {:fk :users_id})
  (entity-fields :uid :username))

(defentity profile
  (table :profile)
  ;;(database db-pg)
  (pk :pid)
  (belongs-to users)
  (entity-fields :user_id :username))

(defentity ukeys
  (table :ukeys)
  ;;(database db-pg)
  (pk :kid)
  (belongs-to users)
  (entity-fields :kid :users_id :username))

(defentity files
  (table :files)
  (pk :fid)
  )

(defentity images
  (table :images)
  (pk :iid)
  )

(defentity cars
  (table :cars)
  (pk :cid)
  )

(defentity departments
  (table :departments)
  (pk :did))

(defentity user_department
  (table :user_department)
  (pk :ud_id))

(defentity red_list
  (table :red_list)
  (pk :rid))

(defentity compares
  (table :compares)
)
