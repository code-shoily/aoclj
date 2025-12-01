(ns aoclj.year-2025.solver
  (:require [aoclj.helpers.io :refer [read-input-data]]
            [aoclj.year-2025.day-01 :as day-01]))

(def ^:const year 2023)

(defn solve
  [day]
  (let [read-input (partial read-input-data year)]
    (case day
      1 (day-01/solve (read-input day))
      ;; Add cases for days as needed
      (format "[ERROR] %s/%s was not solved" year day))))
