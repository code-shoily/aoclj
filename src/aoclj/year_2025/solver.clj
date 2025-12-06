(ns aoclj.year-2025.solver
  (:require [aoclj.helpers.io :refer [read-input-data]]
            [aoclj.year-2025.day-01 :as day-01]
            [aoclj.year-2025.day-02 :as day-02]
            [aoclj.year-2025.day-03 :as day-03]
            [aoclj.year-2025.day-04 :as day-04]
            [aoclj.year-2025.day-05 :as day-05]
            [aoclj.year-2025.day-06 :as day-06]))

(def ^:const year 2025)

(defn solve
  [day]
  (let [read-input (partial read-input-data year)]
    (case day
      1 (day-01/solve (read-input day))
      2 (day-02/solve (read-input day))
      3 (day-03/solve (read-input day))
      4 (day-04/solve (read-input day))
      5 (day-05/solve (read-input day))
      6 (day-06/solve (read-input day))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" year day))))
