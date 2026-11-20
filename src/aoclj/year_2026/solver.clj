(ns aoclj.year-2026.solver
  (:require [aoclj.helpers.io :refer [read-input-data]]))

(def ^:const year 2026)

(defn solve
  [day]
  (let [read-input (partial read-input-data year)]
    (case day
      (format "[ERROR] %s/%s was not solved" year day))))
