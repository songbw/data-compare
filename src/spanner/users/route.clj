(ns spanner.users.route
  (:refer-clojure :exclude [resultset-seq])
  (:require [compojure.route :as route]
            [clojure.string :as str]
            [spanner.users.controllers.users :as users]
            [spanner.users.controllers.ukeys :as ukeys]
            [spanner.users.controllers.images :as images]
            [spanner.users.controllers.departments :as departments]
            [spanner.users.controllers.red-lists :as red-lists]
            [spanner.users.controllers.cars :as cars]
            [spanner.users.controllers.compares :as compares]
            [ring.middleware.multipart-params :as mp])
  (:use [compojure.core]
        [cheshire.core]
        [ring.util.response])
 )
 (defroutes users-routes
   (POST "/api/user/login" [] users/login)
   (DELETE "/api/user/logout" [] users/logout)
   (POST "/api/user" [] users/create)
   (PUT  "/api/user" [] users/update)
   (GET "/api/user/:username" [] users/get-user-by-token)
   ;;(GET "/user/:uid" [] users/profile)
   (POST "/api/user/password" [] users/change-password)
   (GET "/api/users/:currentpage/:linesize" [] users/all)
   (GET "/api/user/verify/:username" [] users/verify-username)
   (POST "/api/users/activate" [] users/activate)
   (DELETE "/api/user/:username" [] users/delete)
   (POST "/api/ukeys" [] ukeys/create)
   (DELETE "/api/ukeys/:id" [] ukeys/delete)
   (GET "/api/ukeys/user/:username" [] ukeys/find-by-user)
   (GET "/api/ukeys/:currentpage/:linesize" [] ukeys/all)
   (mp/wrap-multipart-params
    ;;(POST "/api/images" [] images/create)
    (POST "/api/compare/import" [] compares/create))
   (GET "/api/images" [] images/all)
   (GET "/api/images/:iid" [] images/find-image)
   (POST "/api/cars" [] cars/create)
   (POST "/api/user/client/login" [] users/login-client)
   (POST "/api/user/client/password" [] users/change-client-password)
   (POST "/staff/sign_in.json" [] users/login-client-old)
   (POST "/settings/update_password" [] users/change-client-password-old)
   (POST "/autos/find_auto.json" [] cars/create)
   (GET "/api/init" [] users/init)
   (GET "/api/compare/expert" [] compares/get-excel)
   (GET "/api/compare/list" [] compares/all)
   (POST "/api/department" [] departments/create)
   (PUT "/api/department" [] departments/update)
   (DELETE "/api/department" [] departments/delete)
   (GET "/api/department/list" [] departments/all)
   (GET "/api/department" [] departments/get-department-by-did)
   (GET "/api/department/allname" [] departments/get-all-name)
   (GET "/api/department/verifyname" [] departments/verify-name)
   (POST "/api/red_list" [] red-lists/create)
   (PUT "/api/red_list" [] red-lists/update)
   (DELETE "/api/red_list" [] red-lists/delete)
   (GET "/api/red_list/list" [] red-lists/all)
   (GET "/api/red_list" [] red-lists/get-red_list-by-rid)
   )
