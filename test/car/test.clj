(ns spanner.config.routes
  (:refer-clojure :exclude [resultset-seq])
  (:require [compojure.route :as route]
            [clojure.string :as str]
            [spanner.controllers.tests_controller :as tests])
  (:use [compojure.core]
        [cheshire.core]
        [ring.util.response]))

(defroutes test-routes
  (GET "/gettest" [] /hello)
  (POST "/getresp" [] /resp))
