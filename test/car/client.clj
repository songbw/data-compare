(ns spanner.client
  (:require [clj-http.client :as client])
  (:use [slingshot.slingshot :only [throw+ try+]])
)

(def uri "http://debug.haotiben.cn/")

(def command "http://debug.haotiben.cn/command")

(defn get-cache
  [tag]
  (let [resp (client/get (str uri "cache/" tag))]
    (if (= 200 (get resp :status))
       (get resp :body))))

(defn post-verifySN
  [cmd]
  (let [resp (client/post command
                 {:body cmd
                  :content-type :json
                  :socket-timeout 1000
                  :conn-timeout 1000
                  :accept :json})]
   (println resp)))
