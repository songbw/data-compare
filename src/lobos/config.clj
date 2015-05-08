(ns lobos.config
  (:use lobos.connectivity)
  (:use [spanner.utils.common]))

(def db-pg
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname (str "//" (getParam "db_host" "127.0.0.1") "/compare") ;;"//125.208.20.243:5432/spanner"
   :user (getParam "db_user" "songbw") ;;"spanner"
   :password (getParam "db_password" "songbw") ;; "smallspanner"
   })

(defn open-global-when-necessary
  "Open a global connection only when necessary, that is, when no previous
  connection exist or when db-spec is different to the current global
  connection."
  [db-spec]
  ;; If the connection credentials has changed, close the connection.
  (when (and (@lobos.connectivity/global-connections :default-connection)
             (not= (:db-spec (@lobos.connectivity/global-connections :default-connection)) db-spec))
    (lobos.connectivity/close-global))
  ;; Open a new connection or return the existing one.
  (if (nil? (@lobos.connectivity/global-connections :default-connection))
    ((lobos.connectivity/open-global db-spec) :default-connection)
    (@lobos.connectivity/global-connections :default-connection)))

(open-global-when-necessary db-pg)
