(ns spanner.users.controllers.tests
  (:require [clojure.string :as str])
  (:use [compojure.core]
        [ring.util.response])
  (:use [spanner.utils.common]
        [spanner.utils.web]))

(defhandler hello [res]
  (println res)
  (if (nil? res)
    (error-response 100003 "arg is null")
    (response {:hello "hello world"
               :success true})))

(defhandler resp [res]
  (println res)
  (response {:resp "ok"
             :success true}))
