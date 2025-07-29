(ns aoclj.meta.stats
  (:require [aoclj.year-2015.solver :as solver-2015]
            [aoclj.year-2016.solver :as solver-2016]
            [aoclj.year-2017.solver :as solver-2017]
            [aoclj.year-2018.solver :as solver-2018]
            [aoclj.year-2019.solver :as solver-2019]
            [aoclj.year-2020.solver :as solver-2020]
            [aoclj.year-2021.solver :as solver-2021]
            [aoclj.year-2022.solver :as solver-2022]
            [aoclj.year-2023.solver :as solver-2023]
            [aoclj.year-2024.solver :as solver-2024]))

(defn get-stat-for
  [year day]
  (case year
    2015 (solver-2015/stats day)
    2016 (solver-2016/stats day)
    2017 (solver-2017/stats day)
    2018 (solver-2018/stats day)
    2019 (solver-2019/stats day)
    2020 (solver-2020/stats day)
    2021 (solver-2021/stats day)
    2022 (solver-2022/stats day)
    2023 (solver-2023/stats day)
    2024 (solver-2024/stats day)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn summarize
  [year]
  (let [days (range 1 26)
        metadata (->> days
                      (mapv (partial get-stat-for year))
                      (remove nil?))]
    {:completed (count metadata)
     :stars (reduce + (map :stars metadata))
     :solutions metadata}))
