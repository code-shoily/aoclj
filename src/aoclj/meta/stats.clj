(ns aoclj.meta.stats
  (:require [aoclj.helpers.meta :refer [get-ns-string]]
            [aoclj.year-2015.solver]
            [aoclj.year-2016.solver]
            [aoclj.year-2017.solver]
            [aoclj.year-2018.solver]
            [aoclj.year-2019.solver]
            [aoclj.year-2020.solver]
            [aoclj.year-2021.solver]
            [aoclj.year-2022.solver]
            [aoclj.year-2023.solver]
            [aoclj.year-2024.solver]))

(defn get-stat-for
  "Get stat for a particular year/day challenge"
  [year day]
  (-> (get-ns-string year day)
      read-string
      find-ns
      meta))

(defn summarize
  "Get summary of all years"
  [year]
  (let [days     (range 1 26)
        metadata (->> days
                      (mapv (partial get-stat-for year))
                      (remove nil?))]
    {:completed (count metadata),
     :stars     (reduce + (map :stars metadata)),
     :solutions metadata}))
