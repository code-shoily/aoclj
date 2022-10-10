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

(defn solve-1 [input]
  (->> input (two-sum 2020) (apply *)))

(defn solve-2 [input]
  (->> input (three-sum 2020) (apply *)))

(defn solve [input]
  (let [input (sort input)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))
