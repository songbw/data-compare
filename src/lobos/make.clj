(ns lobos.make
  "This ns is mostly used directly in shell.
   ex:
      $ lein run -m lobos.make/migrate create-users create-posts .
      $ lein run -m lobos.make/migrate-all
      $ lein run -m lobos.make/rollback create-user ..."
  (:require [lobos.core :as lobos])
  (:use lobos.migrations))

(defn migrate-all []
  (lobos/migrate))

(defn migrate [& migrations]
  (apply lobos/migrate (map symbol migrations)))

(defn rollback-all []
  (lobos/rollback :all))

(defn rollback [& migrations]
  (apply lobos/rollback (map symbol migrations)))
