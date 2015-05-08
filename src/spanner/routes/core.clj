(ns spanner.routes.core
  (:refer-clojure :exclude [resultset-seq])
  (:require [compojure.route :as route])
  (:require [clojure.string :as str])
  (:use [compojure.core]
        [cheshire.core]
        [ring.util.response])
  (:use [spanner.users.route]))

(defroutes core-routes
  ;;(GET "/" [] (response {:hello "small spanner"}))
  (context "" [] users-routes)
  (route/not-found "Not Found"))
