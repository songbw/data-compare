(ns spanner.server
  (:require [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :as middleware]
            [spanner.utils.web :as web]
            [spanner.utils.common :as common])
  (:use spanner.routes.core)
  (:gen-class))



(def app
  (-> (handler/api core-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response
       {:escape-non-ascii true})
      (middleware/wrap-json-params)
      ;;;(web/wrap-request-logging)
      (web/log-request-response)
      ))


(defn start [port]
  (jetty/run-jetty #'app {:port (or port 8080) :join? false}))

(defn -main []
  (let [port (common/getParam "POST" "6080")
        ;;(Integer/parseInt (get (System/getenv) "PORT" "6080"))
        ]
    (start (read-string (str port)))))
