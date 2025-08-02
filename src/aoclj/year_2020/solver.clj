(ns aoclj.year-2020.solver
  (:require [aoclj.year-2020.day-01 :as day-01]
            [aoclj.year-2020.day-06 :as day-06]
            [aoclj.utils :as utils]))

(def ^:dynamic *year* 2020)

(defn solve [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      6 (day-06/solve (read-input 6))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" *year* day))))

(defn stats
  "Returns the stats for the problem of `day` for 2020"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))