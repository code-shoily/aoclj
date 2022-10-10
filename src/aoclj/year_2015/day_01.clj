(ns aoclj.year-2015.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-str 2015 1))

(def fns {\( 1 \) -1})

(defn solve-2 [instructions]
  (->> instructions
       (reductions #(+ %1 (fns %2)) 0)
       (take-while #(not= % -1))
       count))

(defn solve-1 [instructions] (reduce #(+ %1 (fns %2)) 0 instructions))

(defn solve [input]
  {1 (solve-1 input) 2 (solve-2 input)})

(time (solve input))
