(ns aoclj.year-2022.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2022.day-01 :as day-01]
            [aoclj.year-2022.day-03 :as day-03]
            [aoclj.year-2022.day-04 :as day-04]))

(def ^:dynamic *year* 2022)

(defn solve
  [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      3 (day-03/solve (read-input 3))
      4 (day-04/solve (read-input 4))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" *year* day))))

(defn stats
  "Returns the stats for the problem of `day` for 20169"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))