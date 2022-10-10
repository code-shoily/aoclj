(ns aoclj.year-2021.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2021 1))

(defn increases [size input]
  (->> input
       (partition size 1)
       (filter #(< (first %) (last %)))
       count))

(defn solve-1 [input] (increases input 2))

(defn solve-2 [input] (increases input 4))

(defn solve [input]
  (let [increase-by (partial increases input)
        solve-1 (increase-by 2)
        solve-2 (increase-by 4)]
    {1 (solve-1 )}))

(time (solve input))