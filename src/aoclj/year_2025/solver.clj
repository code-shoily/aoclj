(ns aoclj.year-2025.solver
  (:require [aoclj.helpers.io :refer [read-input-data]]
            [aoclj.year-2025.day-01 :as day-01]
            [aoclj.year-2025.day-02 :as day-02]
            [aoclj.year-2025.day-03 :as day-03]
            [aoclj.year-2025.day-04 :as day-04]
            [aoclj.year-2025.day-05 :as day-05]
            [aoclj.year-2025.day-06 :as day-06]
            [aoclj.year-2025.day-07 :as day-07]
            [aoclj.year-2025.day-08 :as day-08]
            [aoclj.year-2025.day-09 :as day-09]
            [aoclj.year-2025.day-10 :as day-10]
            [aoclj.year-2025.day-11 :as day-11]
            [aoclj.year-2025.day-12 :as day-12]))

(def ^:const year 2025)

(defn solve
  [day]
  (let [read-input (partial read-input-data year)]
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
      12 (day-12/solve (read-input day))
      (format "[ERROR] %s/%s was not solved" year day))))
