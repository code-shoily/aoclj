;;;; --- Year 2016 Day 2: Bathroom Security ---
;;;; Link: https://adventofcode.com/2016/day/2
;;;; Solution: ["76792" "A7AC3"]
(ns aoclj.year-2016.day-02
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2016 2))

(def dialpad-1
  (to-array-2d
   [[nil nil nil nil nil]
    [nil \1 \2 \3 nil]
    [nil \4 \5 \6 nil]
    [nil \7 \8 \9 nil]
    [nil nil nil nil nil]]))

(def dialpad-2
  (to-array-2d
   [[nil nil nil nil nil nil nil]
    [nil nil nil \1 nil nil nil]
    [nil nil \2 \3 \4 nil nil]
    [nil \5 \6 \7 \8 \9 nil]
    [nil nil \A \B \C nil nil]
    [nil nil nil \D nil nil nil]
    [nil nil nil nil nil nil nil]]))

(defn walk [dialpad [x y] dir]
  (let [[new-x new-y] (case dir
                        \U [(dec x) y]
                        \D [(inc x) y]
                        \L [x (dec y)]
                        \R [x (inc y)])]
    (if (nil? (aget dialpad new-x new-y))
      [x y]
      [new-x new-y])))

(defn crack [dialpad pos line]
  (reduce (partial walk dialpad) pos line))

(defn get-code [dialpad pos input]
  (pmap (comp #(aget dialpad (first %) (second %))
              #(crack dialpad pos %))
        input))

;; Solutions
(defn solve-1 [input]
  (str/join (get-code dialpad-1 [2 2] input)))

(defn solve-2 [input]
  (str/join (get-code dialpad-2 [3 1] input)))

(def solve (partial (juxt solve-1 solve-2)))

;; Run the solution
(time (solve input))
