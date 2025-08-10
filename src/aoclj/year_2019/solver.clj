(ns aoclj.year-2019.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2019.day-01 :as day-01]
            [aoclj.year-2019.day-04 :as day-04]))

(def ^:dynamic *year* 2019)

(defn solve
  [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input day))
      4 (day-04/solve (read-input day))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" *year* day))))

(defn stats
  "Returns the stats for the problem of `day` for 2019"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))