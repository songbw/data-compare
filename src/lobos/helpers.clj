(ns lobos.helpers
  (:refer-clojure :exclude [alter drop bigint boolean char double float time])
  (:use (lobos schema)))

(defn surrogate-key [table]
  ;;(integer table :id :auto-inc :primary-key)
  (integer table :id :auto-inc))

(defn timestamps [table]
  (-> table
      (timestamp :updated_at (default (now)))
      (timestamp :created_at (default (now)))))

(defn refer-to [table ptable]
  (let [cname (-> (->> ptable name butlast (apply str))
                  (str "_id")
                  keyword)]
    (integer table cname [:refer ptable :id :on-delete :set-null])))

(defmacro tbl [name & elements]
  `(-> (table ~name)
       (timestamps)
       ~@(reverse elements)
       (surrogate-key)))
