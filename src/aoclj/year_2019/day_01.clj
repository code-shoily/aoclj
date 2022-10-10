;;;; ---  Year 2019 Day 1: The Tyranny of the Rocket Equation ---
;;;; Link: https://adventofcode.com/2019/day/1
;;;; Solution: [3421505 5129386]
(ns aoclj.year-2019.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2019 1))

(defn get-fuel [mass] (-> mass (/ 3) int (- 2)))

(defn get-fuel-corrected [mass]
  (loop [mass mass fuels []]
    (let [new-mass (get-fuel mass)]
      (if (<= new-mass 0)
        (apply + fuels)
        (recur new-mass (conj fuels new-mass))))))

(defn solver [fun input] (->> input (map fun) (apply +)))

;; Solutions
(def solve-1 (partial solver get-fuel))
(def solve-2 (partial solver get-fuel-corrected))
(def solve (partial (juxt solve-1 solve-2)))

;; Run the solution
; (time (solve input))
