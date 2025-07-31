(ns aoclj.year-2017.solver
  (:require
   [aoclj.year-2017.day-01 :as day-01]
   [aoclj.year-2017.day-02 :as day-02]
   [aoclj.year-2017.day-04 :as day-04]
   [aoclj.utils :as utils]))

(def ^:dynamic *year* 2017)

(defn solve [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      2 (day-02/solve (read-input 2))
      4 (day-04/solve (read-input 4))
      ;; Add cases for days as needed
      (str "[ERROR] Not Solved: " *year* "/" day))))

(defn stats
  "Returns the stats for the problem of `day` for 2017"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))