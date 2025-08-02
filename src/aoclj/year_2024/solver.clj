(ns aoclj.year-2024.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2024.day-01 :as day-01]
            [aoclj.year-2024.day-02 :as day-02]))

(def ^:dynamic *year* 2024)

(defn solve [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      2 (day-02/solve (read-input 2))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" *year* day))))

(defn stats
  "Returns the stats for the problem of `day` for 2024"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))