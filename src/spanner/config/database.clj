(ns spanner.config.database
  (:require [clojure.string :as string])
  (:use [korma.db]
        [spanner.utils.common]))

(def db-pg (postgres {:db "compare"
                      :user (getParam "db_user" "songbw") ;; :user (getParam "db_user" "spanner")
;;;                      :user "songbw"
                      :password (getParam "db_password" "songbw") ;;:password (getParam "db_password" "smallspanner")
;;;                      :password "songbw"
                      ;;OPTIONAL KEYS
                      :host (getParam "db_host" "127.0.0.1") ;;:host (getParam "db_host" "125.208.20.243")
;;;                    :host "127.0.0.1"
                      :port (getParam "db_port" "5432") ;;"5432"
                      :delimiters "" ;; remove delimiters
                      :naming {:keys string/lower-case
                               ;; set map keys to lower
                               :fields string/upper-case}}))
(defdb db db-pg)
