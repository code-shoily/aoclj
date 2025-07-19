(ns aoclj.core
  (:require
   [aoclj.utils :as utils]))

(defn solve [& {:keys [year day]}]
  (if (utils/valid-year-day? year day)
    (println (str "Solving for " year " " day))
    (println "Invalid year/day combination")))

(defn generate [& {:keys [year day]}]
  (if (utils/valid-year-day? year day)
    (println (str "Generating input for " year " " day))
    (println "Invalid year/day combination")))

(defn -main [& {:keys [cmd year day]}]
  (case cmd
    :s (solve :year year :day day)
    :g (generate :year year :day day)
    (println "Unknown command. Use 'solve' or 'generate'.")))