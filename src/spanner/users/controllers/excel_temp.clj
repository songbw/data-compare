(ns spanner.users.controllers.excel-temp
  (:use clojure.tools.trace
        [clojure.java.io :only [file]]
        [incanter.excel :only [read-xls save-xls]]
        [incanter.core :only [to-dataset]]))

(defn proc-row [people row]
  (let [name (get row "姓名")
        person (or (get people name) [])
        data (select-keys row ["姓名","税","本月实发",
                               "应税金额","工资","小计","当月增/减",
                               "病事假","税后扣款"])]
    (assoc people name (conj person data))))

(defn proc-file [people file]
  (let [data (read-xls file)
        rows (take-while #(string? (get % "姓名")) (drop 1 (:rows data)))]
    (reduce proc-row people rows)))

(defn -main []
  (let [xlsx-files (map (memfn getName)
                        (filter (fn [f] (.endsWith (.getName f) ".xlsx"))
                                (file-seq (file "."))))]
    (doseq [[name rows] (reduce proc-file {} xlsx-files)]
      (save-xls (to-dataset rows) (str name "_generated.xlsx")))))
