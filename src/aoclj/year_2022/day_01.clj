;;;; --- Year 2022 Day 1: Calorie Counting ---
;;;; Link: https://adventofcode.com/2022/day/1
;;;; Solution: 
(ns aoclj.year-2022.day-01
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-str 2022 1))

(defn parse [calories]
  (->> (str/split calories #"\n\n")
       (map #(str/split %1 #"\n"))
       (map u/to-ints)
       (map #(apply + %))))

;; Solutions
(defn solve-1 [calories] (apply max calories))

(defn solve-2 [calories]
  (->> calories
       (sort >)
       (take 3)
       (apply +)))

(def solve 
  (comp 
   (partial (juxt solve-1 
                  solve-2))
   parse))

;; Run the solution.
;; (time (solve input))
