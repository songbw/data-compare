(ns spanner.users.models.car
  (:require [clojure.java.jdbc :as sql]
            spanner.config.database)
  (:use [korma.core]
        [korma.db]
        [spanner.users.models.entities]))

(defn create!
  [cid label_no mark_type regist_date fuel_type emission_norm tag_color period_date issued_date car_id]
  (transaction
   (insert cars
           (values {:cid cid
                    :label_no label_no
                    :mark_type mark_type
                    :regist_date regist_date
                    :fuel_type fuel_type
                    :emission_norm emission_norm
                    :tag_color tag_color
                    :period_date period_date
                    :issued_date issued_date
                    :car_id car_id
                    ;;:created_at created_at
                    }))))

(defn update!
   [cid label_no mark_type regist_date fuel_type emission_norm tag_color period_date issued_date car_id updated_at]
   (transaction
    (update cars
            (set-fields {:label_no label_no
                         :mark_type mark_type
                         :regist_date regist_date
                         :fuel_type fuel_type
                         :emission_norm emission_norm
                         :tag_color tag_color
                         :period_date period_date
                         :issued_date issued_date
                         :car_id car_id
                         :updated_at updated_at})
            (where {:cid (bigdec cid)}))))

(defn delete!
  [cid]
  (let [cid (bigdec cid)]
    (delete cars
            (where {:cid cid}))))

(defn find-by-cid
  [cid]
  (select cars
          (fields :label_no :mark_type :regist_date :fuel_type :emission_norm :tag_color :period_date :issued_date :car_id)
          (where {:cid cid})))

(defn find-by-car_id
  [car_id]
  (select cars
          (fields :label_no :mark_type :regist_date :fuel_type :emission_norm :tag_color :period_date :issued_date :car_id)
          (where {:car_id car_id})))
