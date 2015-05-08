(ns spanner.utils.web
  (:require [spanner.utils.log :as logger])
  (:use [ring.util.response]))

(defmacro defhandler
  [name args & body]
  `(defn ~name [req#]
     (let [{:keys ~args :or {~'req req#}} (:params req#)]
       ~@body)))

;;(defmacro )

(defn fail
  [msg]
  (println msg))

(defn error-response
  [errno msg]
  (response {:success false
             :errno errno
             :message msg}))

(defn error-status
  [error s]
  (status (response {:error error})
          s))

(defn get-error-response
  [msg]
  (response msg))

(defn ok-response
  []
  (response {:success true}))

(defn log-request-response [handler]
  "middleware for log request and response directly"
  (fn [req]
    (let [resp (handler req)]
      (logger/info "\nrequest -> \n" req "\nresponse -> \n" resp)
      resp
      )))

(defn wrap-request-logging [handler]
  (fn [{:keys [request-method uri] :as req}]
    (let [resp (handler req)]
      (logger/log-message "Processing %s %s" request-method uri)
      resp)))

(def clientSecret "ccef581b9f60a96d6c6214a20492b0ac")

(defn validate-singature [clientid version date signature]
  (let [check (.format "%s\n%s\n%s\n%s" (clientid version date clientSecret))]
    (if (= signature check) true false)
    ))
