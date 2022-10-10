(ns aoclj.year-2021.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2021 1))

(defn increase-by [input size]
  (->> input
       (partition size 1)
       (filter #(< (first %) (last %)))
       count))

(defn solve [input]
  (let [solve-1 #(increase-by % 2)
        solve-2 #(increase-by % 4)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))
