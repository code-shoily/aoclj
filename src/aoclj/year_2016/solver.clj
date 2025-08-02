(ns aoclj.year-2016.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2016.day-01 :as day-01]
            [aoclj.year-2016.day-02 :as day-02]
            [aoclj.year-2016.day-03 :as day-03]
            [aoclj.year-2016.day-04 :as day-04]
            [aoclj.year-2016.day-05 :as day-05]))

(def ^:dynamic *year* 2016)

(defn solve [day]
  (let [read-input #(utils/read-input-data 2016 %)]
    (case day
      1 (day-01/solve (read-input 1))
      2 (day-02/solve (read-input 2))
      3 (day-03/solve (read-input 3))
      4 (day-04/solve (read-input 4))
      5 (day-05/solve (read-input 5))
      (throw (ex-info "Unsupported day" {:day day})))))

(defn stats
  "Returns the stats for the problem of `day` for 2016"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))

