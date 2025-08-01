(ns aoclj.year-2015.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2015.day-01 :as day-01]
            [aoclj.year-2015.day-02 :as day-02]
            [aoclj.year-2015.day-04 :as day-04]
            [aoclj.year-2015.day-05 :as day-05]))

(def ^:dynamic *year* 2015)

(defn solve [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      2 (day-02/solve (read-input 2))
      4 (day-04/solve (read-input 4))
      5 (day-05/solve (read-input 5))
      ;; Add cases for days as needed
      (str "[ERROR] Not Solved: " *year* "/" day))))

(defn stats
  "Returns the stats for the problem of `day` for 2015"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))