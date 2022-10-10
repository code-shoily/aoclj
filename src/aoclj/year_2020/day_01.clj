;;;; --- Year 2020 Day 1: Report Repair ---
;;;; Link: https://adventofcode.com/2020/day/1
;;;; Solution: [1014624 80072256]
(ns aoclj.year-2020.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2020 1))

(defn two-sum [target nums]
  (loop [left 0 right (-> nums count dec)]
    (when (< left right)
      (let [val-a (nth nums left)
            val-b (nth nums right)
            current-total (+ val-a val-b)]
        (cond
          (> current-total target) (recur left (dec right))
          (< current-total target) (recur (inc left) right)
          (= current-total target) [val-a val-b])))))

(defn three-sum [target nums]
  (loop [idx 0]
    (let [val-a (nth nums idx)
          sub-array (->> nums (drop (inc idx)) (into []))
          target-sum (- target val-a)
          [val-b val-c] (two-sum target-sum sub-array)]
      (if (nil? val-b)
        (recur (inc idx))
        [val-a val-b val-c]))))

;; Solutions
(defn solution-template [summing-fn input] 
  (->> input 
       (summing-fn 2020) 
       (apply *)))

(def solve-1 (partial solution-template two-sum))
(def solve-2 (partial solution-template three-sum))
(def solve (partial (comp (juxt solve-1 solve-2) sort)))

;; Run the solution
(time (solve input))
