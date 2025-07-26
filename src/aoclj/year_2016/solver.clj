(ns aoclj.year-2016.solver
  (:require
   [aoclj.year-2016.day-01 :as day-01]
   [aoclj.utils :as utils]))

(def ^:dynamic *year* 2016)

(defn solve [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      ;; Add cases for days as needed
      (str "[ERROR] Not Solved: " *year* "/" day))))


(defn stats
  "Returns the stats for the problem of `day` for 2016"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))

