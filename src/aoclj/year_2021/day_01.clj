;;;; --- Year 2021 Day 1: Sonar Sweep ---
;;;; Link: https://adventofcode.com/2021/day/1
;;;; Solutions: [1139 1103]
(ns aoclj.year-2021.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2021 1))

(defn increase-by [size input]
  (->> input
       (partition size 1)
       (filter #(< (first %) (last %)))
       count))

;; Solutions
(def solve-1 (partial increase-by 2))
(def solve-2 (partial increase-by 4))
(def solve (partial (juxt solve-1 solve-2)))

;; Run the solution
; (time (solve input))
