(ns aoclj.year-2023.solver
  (:require [aoclj.utils :as utils]
            [aoclj.year-2023.day-01 :as day-01]))

(def ^:dynamic *year* 2023)

(defn solve [day]
  (let [read-input (partial utils/read-input-data *year*)]
    (case day
      1 (day-01/solve (read-input 1))
      ;; Add cases for days as needed
      (str "[ERROR] Not Solved: " *year* "/" day))))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn stats
  "Returns the stats for the problem of `day` for 2023"
  [day]
  (-> (utils/get-ns-string *year* day)
      read-string
      find-ns
      meta))