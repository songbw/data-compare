(ns spanner.users.controllers.yellow
  (:require [clojure.string :as str]
            [ring.util.response :as resp]
            [clojure.contrib.duck-streams :as ds]
            [clj-http.client :as client]
            [clojure.java.io :as io]
            [incanter.excel :as excel]
            [incanter.core :as icore]
            [incanter.io :as iio]
            )
  (:require [spanner.utils.common :as common]
            [spanner.utils.web :as web]
            [spanner.utils.log :as log]
            [spanner.utils.uuid :as uuid]
            [clojure.contrib.lazy-xml :as conxml]
            [spanner.users.models.compare :as compare]
            [spanner.users.models.car :as car]
            [spanner.users.models.user :as user]
            [spanner.users.controllers.cars :as cars]
            [spanner.users.models.user-department :as ud]
            [clojure-csv.core :as csv]
            )
  (:use [compojure.core]
        [spanner.utils.web :only [defhandler]])
  )


(defn color?
  [type]
  (cond (= "大型汽车" type) "黄"
        (= "小型汽车" type) "蓝")
  )

(color? "小型汽车")

(defn count?
  [coll1 coll2]
  (let [type (str/trim (get coll1 0))
        num (str "新" (str/trim (get coll1 1)))
        color (color? (str/trim type))
        color2 (str/trim (get coll2 2))
        num2 (str/trim (get coll2 1))
        ]
    (if (and (= color color2) (= num num2))
      true
      false)
    ))



(def wulumuqi (common/parse-csv (io/reader "logout.csv" :encoding "gbk")))

(def yellow (common/parse-csv (io/reader "yellow.csv" :encoding "gbk")))

(def wulimuqi-header [["号牌种类" "号牌号码" "燃料种类" "出厂日期"]])

(def yellow-header [["序号" "车牌号码" "车牌颜色" "车辆厂牌" "车辆型号" "车辆类型" "车架号码" "初次登记日期" "出厂日期" "车辆用途" "燃料类型" "整备质量" "使用性质" "车主" "联系电话" ""]])

(defn selectyellow
  [coll]
  (let [num (get (first coll) 1)
        type (get (first coll) 0)]
    (for [s yellow
          :when (and
                 (= (str "新" num) (get (first s) 1))
                 (= (color? type) (get (first s) 2))
                 )]
      true
      ))
  )

(defn selectyellow?
  [coll]
  (first (selectyellow coll)))

;; shengcheng test-ye.xls
(defn test-wu
  []
  (let [data (filter #(selectyellow? %) wulumuqi)
        data1 (for [s data]
                (into [] (first s)))]
;;    (println data1)
    (excel/save-xls (icore/to-dataset data1) "logout-wu.xls" :encoding "gbk")
    )
)

(defn selectwu
  [coll]
  (let [num (get (first coll) 1)
        color (get (first coll) 2)]
    (for [s wulumuqi
          :when (and
                 (= num (str "新" (get (first s) 1)))
                 (= color (color? (get (first s) 0)))
                 )]
      true)))

(defn selectwu?
  [coll]
  (first (selectwu coll)))
;; shengcheng test-ye.xls
(defn test-ye
  []
  (let [data (remove #(selectwu? %) yellow)
        data1 (for [s data]
                (into [] (first s)))]

;;;(println data1)
   (excel/save-xls (icore/to-dataset data1) "logout-ye.xls" :encoding "gbk")
    )
  )
