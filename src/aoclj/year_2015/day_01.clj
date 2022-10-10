;;;; --- Year 2015 Day 1: Not Quite Lisp ---
;;;; Link: https://adventofcode.com/2015/day/1
;;;; Solution: [232 1783]
(ns aoclj.year-2015.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-str 2015 1))

(def fns {\( 1 \) -1})

;; Solutions
(defn solve-1 [instructions] (reduce #(+ %1 (fns %2)) 0 instructions))

(defn solve-2 [instructions]
  (->> instructions
       (reductions #(+ %1 (fns %2)) 0)
       (take-while #(not= % -1))
       count))

(def solve (partial (juxt solve-1 solve-2)))

;; Run the solution.
; (time (solve input))
