(ns aoclj.year-2024.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2024.day-01 :as day-01]
            [aoclj.year-2024.day-02 :as day-02]
            [aoclj.year-2024.day-03 :as day-03]
            [aoclj.year-2024.day-04 :as day-04]
            [aoclj.year-2024.day-05 :as day-05]
            [aoclj.year-2024.day-06 :as day-06]
            [aoclj.year-2024.day-07 :as day-07]
            [aoclj.year-2024.day-08 :as day-08]
            [aoclj.year-2024.day-09 :as day-09]
            [aoclj.year-2024.day-10 :as day-10]
            [aoclj.year-2024.day-11 :as day-11]
            [aoclj.year-2024.day-25 :as day-25]))

(def ^:const year 2024)

(defn solve
  [day]
  (let [read-input (partial utils/read-input-data year)]
    (case day
      1  (day-01/solve (read-input day))
      2  (day-02/solve (read-input day))
      3  (day-03/solve (read-input day))
      4  (day-04/solve (read-input day))
      5  (day-05/solve (read-input day))
      6  (day-06/solve (read-input day))
      7  (day-07/solve (read-input day))
      8  (day-08/solve (read-input day))
      9  (day-09/solve (read-input day))
      10 (day-10/solve (read-input day))
      11 (day-11/solve (read-input day))
      25 (day-25/solve (read-input day))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" year day))))

(defn stats
  "Returns the stats for the problem of `day` for 2024"
  [day]
  (-> (utils/get-ns-string year day)
      read-string
      find-ns
      meta))
