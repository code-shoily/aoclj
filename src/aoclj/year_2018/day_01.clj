;;;; --- Year 2018 Day 1: Chronal Calibration ---
;;;; Link: https://adventofcode.com/2018/day/1
;;;; Solution: [590 83445]
(ns aoclj.year-2018.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2018 1))

(defn add-freqs [[freqs acc] x]
  (let [frequency (+ acc x)]
    (if (contains? freqs frequency)
      (reduced frequency)
      [(conj freqs frequency) frequency])))

;; Solutions
(defn solve-1 [input] (apply + input))

(defn solve-2 [input]
  (->> input
       cycle
       (reduce add-freqs [#{} 0])))

(def solve (partial (juxt solve-1 solve-2)))

;; Run the solution
; (time (solve input))

