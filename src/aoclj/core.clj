(ns aoclj.core
  (:require [aoclj.utils :as utils]
            [aoclj.meta.code-org :as g]
            [aoclj.meta.doc-renderer :as renderer]
            [aoclj.year-2015.solver :as solver-2015]
            [aoclj.year-2016.solver :as solver-2016]
            [aoclj.year-2017.solver :as solver-2017]
            [aoclj.year-2018.solver :as solver-2018]
            [aoclj.year-2019.solver :as solver-2019]
            [aoclj.year-2020.solver :as solver-2020]
            [aoclj.year-2021.solver :as solver-2021]
            [aoclj.year-2022.solver :as solver-2022]
            [aoclj.year-2023.solver :as solver-2023]
            [aoclj.year-2024.solver :as solver-2024]))

(defn solve
  "Solve the Advent of Code problem for a given year and day and 
   return the solution as a string."
  [year day]
  {:pre [(utils/valid-year-day? year day)]}
  (case year
    2015 (solver-2015/solve day)
    2016 (solver-2016/solve day)
    2017 (solver-2017/solve day)
    2018 (solver-2018/solve day)
    2019 (solver-2019/solve day)
    2020 (solver-2020/solve day)
    2021 (solver-2021/solve day)
    2022 (solver-2022/solve day)
    2023 (solver-2023/solve day)
    2024 (solver-2024/solve day)
    (throw (ex-info "Unsupported year/day" {:year year, :day day}))))

(defn generate
  "This function generated source and test files for a given year/day"
  [year day]
  (g/create-solution-stub year day))

(defn update-stats
  "This functions updates the readmes to reflect the most recent solution status"
  []
  (renderer/generate-yearwise-readmes)
  (renderer/generate-readme)
  "Status updated for all readmes")

(defn -main
  [cmd & args]
  (letfn [(extract-year-day [[year day]] [(Integer/parseInt year)
                                          (Integer/parseInt day)])]
    (case cmd
      "update-stats" (println (update-stats))
      "solve"        (time (println (apply solve (extract-year-day args))))
      "gen"          (apply generate (extract-year-day args))
      (println (format "Unknown command %s . Use :update-stats :solve or :gen."
                       cmd)))))

