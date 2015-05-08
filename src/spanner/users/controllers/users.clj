(ns spanner.users.controllers.users
  (:require [clojure.string :as str]
            [ring.util.response :as resp])
  (:require [spanner.users.models.user :as user]
            [spanner.users.models.ukey :as ukey]
            [spanner.users.models.user-department :as ud]
            [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            [lobos.make :as data]
            )
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]]))

(def error-msg {:username {:success false,:errno -100008,:message "username is null"}
                :name {:success false :errno -100010 :message "name is null"}
                :is_admin {:success false :errno -100011 :message "is-admin is null"}
})

(def msg-key [:username :name :is_admin])

(defn admin?
  [token]
  (user/find-is-admin token))

(defn- gen-token
  []
  (common/md5-sum (common/get-random-id 32)))

(defn authorized?
  [headers]
  (let [auth-token (get headers "auth-token")]
    (log/info "authorized user is admin " (admin? auth-token))
    (and (nil? (user/get-uid-by-token (str auth-token))) (admin? auth-token))))

(defn authorized-client?
  [auth-token]
  (nil? (user/get-uid-by-token (str auth-token)))
  )

(defn create
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        username (str (get params :username))
        password (str (get params :password))
        is_admin (get params :is_admin false)
        tel (str (get params :tel))
        company (str (get params :company))
        name (str (get params :name))
        department (get params :department)
        msg (common/get-nil-keys msg-key params)]
    (log/info "create params have username : " username " password : " password " is_admin : " is_admin " tel : " tel " company : " company " name : " name)
    (if-not (nil? (first msg))
      (web/get-error-response ((first msg) error-msg))
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (if (empty? username)
          (web/error-response -210101 "User name not null.")
          (let [uid (uuid/gen-uuid)
                pid (uuid/gen-uuid)
                udid (uuid/gen-uuid)]
            (user/create! uid username password is_admin tel company name pid)
            (ud/create! udid department username)
            (resp/response {:user {:uid uid
                                   :is_admin is_admin}
                            :success true})))))
    ))

(defn update
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        username (str (get params :username))
        password (str (get params :password))
        is_admin (get params :is_admin false)
        tel (str (get params :tel))
        company (str (get params :company))
        department (get params :department 0)
        name (str (get params :name))
        msg (common/get-nil-keys msg-key params)]
    (log/info "update params have username : " username " password : " password " is_admin : " is_admin " tel : " tel " company : " company " name : " name)
    (if-not (nil? (first msg))
      (web/get-error-response ((first msg) error-msg))
      (if (authorized? headers)
        (web/error-response -210104 "Token Unauthorized.")
        (if (nil? username)
          (web/error-response 100003 "username is null")
          (let [temppwd (user/get-pwd-by-username username)]
            (if (or (nil? password) (= "" password))
              (user/update! username name tel company is_admin temppwd)
              (user/update! username name tel company is_admin password))
            (if-let [udepartment (first (ud/find-by-username username))]
              (ud/update! (udepartment :id) department username)
              )
            (resp/response {:success true})))))
    ))

(defn delete
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [username (get params :username)]
        (log/info "delete params have username : " username)
        (if (nil? (user/delete! username))
          (web/error-response -210103 "account is not exist.")
          (do
            (ud/delete! ((first (ud/find-by-username username)) :id))
            (web/ok-response))
          )))))

(defhandler login
  [username password]
  (log/info "login params have username : " username " password : " password)
  (if (not= password (user/get-pwd-by-username username))
    (web/error-response -210102 "account is not exist or password error.")
    (if (user/admin? (str username))
      (let [access_token (gen-token)
            refresh_token (gen-token)
            expires_in (+ (common/currenttime) 8640000)
            is-admin (user/admin? (str username))]
        (user/update-token username access_token refresh_token expires_in)
        (resp/response {:user {:access_token access_token
                               :refresh_token refresh_token
                               :expires_in expires_in
                               :is_admin is-admin}
                        :success true}))
      (web/error-response -100007 "user is not login"))
    ))

(defn all
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [currentpage (get params :currentpage 1)
            linesize (get params :linesize 20)
            offsets (* (- (Integer/parseInt currentpage) 1) (Integer/parseInt linesize))
            users (user/all offsets (Integer/parseInt linesize))
            allcount (get (first (user/allcount)) :allcount)
            total-pages (if (= (rem allcount (Integer/parseInt linesize)) 0) (quot allcount (Integer/parseInt linesize)) (+ (quot allcount (Integer/parseInt linesize)) 1))]
        (log/info "get all params have currentpage : " currentpage " linesize : " linesize)
        (resp/response {:users users
                        :allcount allcount
                        :totalpages total-pages
                        :currentpage currentpage})))))

(defn activate
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [username (get params :username)
            status (get params :status)]
        (log/info "change status params have username : " username " status : " status)
        (if (nil? (user/activate! username status))
          (web/error-response -210103 "account is not exist.")
          (web/ok-response))))))

(defn change-password
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [username (get params :username)
            old_password (get params :old_password)
            new_password (get params :new_password)]
        (log/info "change password params have username : " username " old_password : " old_password " new_password : " new_password)
        (if (not= old_password (user/get-pwd-by-username username))
          (web/error-response -210102 "account is not exist or password error.")
          (do
            (user/change-password username new_password)
            (web/ok-response)))
        ))))

(defn logout
  [req]
  (let [params (get req :params)
        headers (get req :headers)]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [access_token nil
            refresh_token nil
            expires_in 0
            auth-token (get headers "auth-token")
            username (user/get-username-by-token (str auth-token))]
        (user/update-token username access_token refresh_token expires_in)
        (web/ok-response)))))

(defn get-user-by-token
  [req]
  (let [params (get req :params)
        headers (get req :headers)
        token (get headers "auth-token")
        username (get params :username)
        department (get (first (ud/find-by-username username)) :department_id 0)]
    (log/info "get me params have username : " username)
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (resp/response (conj (first (user/find-user-by-username username)) {:department department})))))

(defn signup
  [username password is-admin]
  (let [uid (uuid/gen-uuid)
        pid (uuid/gen-uuid)]
    (user/create! uid (str username) (str password) is-admin pid)))

(defn signin
  [req]
  (let [params (:params req)
        headers (:headers req)
        username (get params "username")
        auth-token (get headers "auth-token")
        content-type (get headers "content-type")]
    (println username)
    (println headers)
    (println content-type)
    (println auth-token)))

(defn verify-username
  [req]
  (let [params (:params req)
        headers (:headers req)]
    (if (authorized? headers)
      (web/error-response -210104 "Token Unauthorized.")
      (let [username (get params :username)]
        (log/info "------------------->" (user/find-username username) username (nil? (first (user/find-username username))))
        (if (nil? (first (user/find-username username)))
          (resp/response {:status true})
          (resp/response {:status false})
          )
        ))))

(defn authorized-serialno?
  [username serialno]
  (let [serialnos (ukey/find-serialno-by-username username)
        search (.indexOf serialnos {:serialno serialno})]
    (if (= -1 search)
      false
      true)))

(defhandler login-client
  [user]
  (log/info "client login params have user : " user)
  (let [username (get user :username)
        password (get user :password)
        serialno (get user :serialno)]
    (if (not= password (user/get-pwd-by-username username))
      (web/error-status "account is not exist or password error." 401)
      (if (authorized-serialno? username serialno)
        (let [access_token (gen-token)
              refresh_token (gen-token)
              expires_in (+ (common/currenttime) 8640000)
              is-admin (user/admin? (str username))
              user (user/find-user-by-username username)
              name (get (first user) :name)]
          (user/update-token username access_token refresh_token expires_in)
          (resp/response {:respone "ok"
                          :auth_token access_token
                          :user {:username username
                                 :admin is-admin
                                 :fullname name
                                 :email ""
                                 :created_at ""
                                 :id 0
                                 :updated_at ""}
                          }
                         ))
        (web/error-status "user is not login" 401)
        )
      ))
  )

(defhandler login-client-old
  [user]
  (log/info "client login params have user : " user)
  (let [username (get user :username)
        password (get user :password)
        password (common/md5-sum password)]
    (if (not= password (user/get-pwd-by-username username))
      (web/error-status "account is not exist or password error." 401)
      (let [access_token (gen-token)
            refresh_token (gen-token)
            expires_in (+ (common/currenttime) 8640000)
            is-admin (user/admin? (str username))
            user (user/find-user-by-username username)
            name (get (first user) :name)]
        (user/update-token username access_token refresh_token expires_in)
        (resp/response {:respone "ok"
                        :auth_token access_token
                        :user {:username username
                               :admin is-admin
                               :fullname name
                               :email ""
                               :created_at ""
                               :id 0
                               :updated_at ""}
                        }))
      ))
  )

(defn change-client-password
  [req]
  (log/info "req------------> " req)
  (let [params (get req :params)
        auth_token (get params :auth_token)]
    (if (authorized-client? auth_token)
      (web/error-status "Token Unauthorized." 401)
      (let [username (get params :username)
            old_password (get params :old_password)
            new_password (get params :new_password)
            auth_token (get params :auth_token)]
        (log/info "client change password params have username : " username " old_password : " old_password " new_password : " new_password)
        (if (= username (user/get-username-by-token auth_token))
          (if (not= old_password (user/get-pwd-by-username username))
            (web/error-status "account is not exist or password error." 401)
            (do
              (user/change-password username new_password)
              (resp/response {:respone "ok"
                              :status "ok"})))
          (web/error-status "username and token is not same." 401))
        ))))

(defn change-client-password-old
  [req]
  (log/info "req -------------> " req)
  (let [params (get req :params)
        auth_token (get params :auth_token)]
    (if (authorized-client? auth_token)
      (web/error-status "Token Unauthorized." 401)
      (let [username (:username (first (user/find-username-by-token auth_token)))
            password (get params :password)]
        (log/info "client change password params have username : " username " password : " password)
        (do
          (user/change-password username password)
          (resp/response {:respone "ok"
                          :status "ok"}))
        ))))

(defn init
  [req]
  (if (= "test" (common/getParam "sys_test" "test"))
    (let [params ()])
    ))
