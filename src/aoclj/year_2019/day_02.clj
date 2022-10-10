;;;; ---  Year 2019 Day 2: 1202 Program Alarm ---
;;;; Link: https://adventofcode.com/2019/day/2
;;;; Solution: [3562624 8298]
(ns aoclj.year-2019.day-02
  (:require [aoclj.common.reader :as reader]
            [aoclj.year-2019.intcode :as ic]))

(def input (reader/get-input-str 2019 2))
(defn parse [input] (ic/parse-intcode input))

(defn fix-1202 [input]
  (->> input
       (ic/provide-inputs 12 2)
       ic/run-cmd
       ic/output))

(defn find-inputs-for [desired-output input]
  (first (for [noun (range 1 100)
               verb (range 1 100)
               :let [program (ic/provide-inputs noun verb input)
                     output (ic/output (ic/run-cmd program))]
               :when (= desired-output output)]
           [noun verb])))

;; Solutions
(def solve-1 fix-1202)
(def solve-2 (comp (fn [[a b]] (+ (* 100 a) b))
                   #(find-inputs-for 19690720 %)))

(def solve (partial (comp (juxt solve-1 solve-2) parse)))

;; Run the solution
(time (solve input))
