(ns spanner.users.models.compare
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities])
  (:import (utils Common Common)))

(defn create!
  [serial_no record_no car_no mark_type name address illegal_time illegal_address illegal_code fines capture_no record_time travel_speed speed_limit ultra_rate phone car_type car_brand violation department]
  (transaction
   (insert compares
           (values {:serial_no serial_no
                    :record_no record_no
                    :car_no car_no
                    :mark_type mark_type
                    :name "name"
                    :address "address"
                    :illegal_time illegal_time
                    :illegal_address illegal_address
                    :illegal_code illegal_code
                    :fines fines
                    :capture_no capture_no
                    :record_time "record_time"
                    :travel_speed travel_speed
                    :speed_limit speed_limit
                    :ultra_rate "0"
                    :phone "phone"
                    :car_type car_type
                    :car_brand "car_brand"
                    :violation violation
                    :department (read-string (str department))}))))

(defn update!
  [id serial_no record_no car_no mark_type name address illegal_time illegal_address illegal_code fines capture_no record_time travel_speed speed_limit ultra_rate phone car_type car_brand violation]
  (transaction
   (update compares
           (set-fields {:serial_no serial_no
                        :record_no record_no
                        :car_no car_no
                        :mark_type mark_type
                        :name name
                        :address address
                        :illegal_time illegal_time
                        :illegal_address illegal_address
                        :illegal_code illegal_code
                        :fines fines
                        :capture_no capture_no
                        :record_time record_time
                        :travel_speed travel_speed
                        :speed_limit speed_limit
                        :ultra_rate ultra_rate
                        :phone phone
                        :car_type car_type
                        :car_brand car_brand
                        :violation violation})
           (where {:id (read-string (str id))}))))

(defn delete!
  [cid]
  (delete compares
          (where {:cid (bigdec cid)})))

(defn string2date
  [str-date]
  (.. (java.text.SimpleDateFormat. "yyyy-MM-dd") (parse str-date)))

(defn string2sqldate
  [str-date]
  (java.sql.Date. (.getTime (string2date str-date))))

(defn all
  [o l department begin end]
  (select compares
          (fields :serial_no :record_no :car_no :mark_type :name :address :illegal_time :illegal_address :illegal_code :fines :capture_no :record_time :travel_speed :speed_limit :ultra_rate :phone :car_type :car_brand :violation)
          (where (and (= :department department)
                      (>= :created_at (string2sqldate end))
                      (<= :created_at (string2sqldate begin))
                      )
           )
          (order :id :DESC)
          (offset o)
          (limit l)))

(defn all-excel
  [department begin end]
  (select compares
          (fields :serial_no :record_no :car_no :mark_type :name :address :illegal_time :illegal_address :illegal_code :fines :capture_no :record_time :travel_speed :speed_limit :ultra_rate :phone :car_type :car_brand :violation)
          (where (and (= :department department)
                      (>= :created_at (string2sqldate end))
                      (<= :created_at (string2sqldate begin))
                      )
           )
          (order :id :DESC)
))

(defn allcount
  [department begin end]
  (select compares
          (aggregate (count :*) :allcount)
          (where (and (= :department department)
                      (>= :created_at (string2sqldate end))
                      (<= :created_at (string2sqldate begin))
                      )
           ))
  )

(defn select-all
  [o l]
  (exec-raw ["select serial_no,record_no,car_no,mark_type,name,address,illegal_time,illegal_address,illegal_code,fines,capture_no,record_time,travel_speed,speed_limit,ultra_rate,phone,car_type,car_brand,violation from compares where created_at > ? and created_at < ?" ])
)

(defn find-compare-by-id
  [id]
  (select compares
          (fields :cid :serial_no :record_no :car_no :mark_type :name :address :illegal_time :illegal_address :illegal_code :fines :capture_no :record_time :travel_speed :speed_limit :ultra_rate :phone :car_type :car_brand :violation)
          (where {:cid (bigdec id)})))
