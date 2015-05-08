(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time long])
  (:use (lobos [migration :only [defmigration]] core schema))
  (:use lobos.config)
  (:use lobos.helpers))

(defmigration create-users-table
  (up [] (create
          (tbl :users
               (decimal :uid 64 0 :primary-key)
               (varchar :username 255 :unique)
               (check :username (> (length :username) 2))
               (varchar :password 128 :not-null)
               (boolean :is_admin (default false))
               (varchar :access_token 128)
               (varchar :refresh_token 128)
               (bigint :expires_in (default 30000))
               (timestamp :login_at)
               (timestamp :logout_at)
               (boolean :status (default true))
               )))
  (down [] (drop (table :users)))
  )
;;; Note
;;; {x}char 类型字段一定要指定长度，否则编译时报错。

(defmigration create-profile-table
  (up [] (create
          (tbl :profile
               ;;decimal :uid 64 0 , 指定长度
               (decimal :pid 64 0 :primary-key)
               (decimal :user_id 64 0 [:refer :users :uid :on-delete :cascade] :not-null)
               (varchar :username 255)
               (varchar :name 255)
               (varchar :nickname 255)
               (varchar :cardid 255)
               (char :gender)
               (timestamp :birthday)
               (varchar :tel 32)
               (varchar :mobile 32)
               (varchar :company 255) (varchar :email 255))))
  (down [] (drop (table :profile))))

(defmigration create-departments-table
  (up [] (create
          (tbl :departments
               (decimal :did 64 0 :primary-key)
               (decimal :father_id 64 0)
               (varchar :description 255)
               (varchar :name 255)
               (varchar :phone 255)
               (varchar :email 255)
               (varchar :address 255)
               (varchar :keyword 255))))
  (down [] (drop (table :departments))))

(defmigration create-user_department-table
  (up [] (create
          (tbl :user_department
               (decimal :ud_id 64 0 :primary-key)
               (integer :department_id)
               (varchar :username 255)
               )))
  (down [] (drop (table :user_department))))

(defmigration create-red_list-table
  (up [] (create
          (tbl :red_list
               (decimal :rid 64 0 :primary-key)
               (varchar :car_no 255)    ;; car number
               (varchar :mark_type 255)
               (timestamp :regist_date)
               (varchar :frame_no 255)
               (varchar :tag_color 255)
               )))
  (down [] (drop (table :red_list))))

(defmigration create-compares-table
  (up [] (create
          (tbl :compares
;;               (decimal :cid 64 0 :primary-key)
               (varchar :serial_no 255)
               (varchar :record_no 255)
               (varchar :car_no 255)
               (varchar :mark_type 255)
               (varchar :name 255)
               (varchar :address 255)
               (varchar :illegal_time 255)
               (varchar :illegal_address 255)
               (varchar :illegal_code 255)
               (varchar :fines 255)
               (varchar :capture_no 255)
               (varchar :record_time 255)
               (varchar :travel_speed 255)
               (varchar :speed_limit 255)
               (varchar :ultra_rate 255)
               (varchar :phone 255)
               (varchar :car_type 255)
               (varchar :car_brand 255)
               (varchar :violation 255)
               (timestamp :created_at (default (now)))
               (integer :department)
               )))
  (down [] (drop (table :compares))))

(defmigration create-files-table
  (up [] (create
          (tbl :files
               (decimal :fid 64 0 :primary-key)
               (varchar :name 255)
               (integer :size (default 0))
               (varchar :path 255)
               (timestamp :created_at (default (now)))
               (varchar :creator 255)
               (varchar :category 255)
               )))
  (down [] (drop (table :files))))
