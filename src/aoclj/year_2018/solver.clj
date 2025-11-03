(ns aoclj.year-2018.solver
  (:require [aoclj.helpers.io :refer [read-input-data]]
            [aoclj.helpers.meta :refer [get-ns-string]]
            [aoclj.year-2018.day-01 :as day-01]
            [aoclj.year-2018.day-02 :as day-02]
            [aoclj.year-2018.day-03 :as day-03]
            [aoclj.year-2018.day-04 :as day-04]
            [aoclj.year-2018.day-05 :as day-05]
            [aoclj.year-2018.day-07 :as day-07]))

(def ^:const year 2018)

(defn solve
  [day]
  (let [read-input (partial read-input-data year)]
    (case day
      1 (day-01/solve (read-input day))
      2 (day-02/solve (read-input day))
      3 (day-03/solve (read-input day))
      4 (day-04/solve (read-input day))
      5 (day-05/solve (read-input day))
      7 (day-07/solve (read-input day))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" year day))))

(defn stats
  "Returns the stats for the problem of `day` for 2018"
  [day]
  (-> (get-ns-string year day)
      read-string
      find-ns
      meta))
