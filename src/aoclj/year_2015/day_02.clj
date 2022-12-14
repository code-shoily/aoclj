;;;; --- Year 2015 Day 2: I Was Told There Would Be No Math ---
;;;; Link: https://adventofcode.com/2015/day/1
;;;; Solution: [1606483 3842356]
(ns aoclj.year-2015.day-02
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2015 2))

(defn parse-dims [dim]
  (as-> dim $
    (str/split $ #"x")
    (u/to-ints $)
    (sort $)))

(defn parse [input] (map parse-dims input))

(defn present [[a b c]]
  (let [smallest-area (* a b)
        surface (* 2 (+ (* a b) (* b c) (* c a)))]
    (+ smallest-area surface)))

(defn ribbon [[a b c]]
  (let [smallest-perimeter (+ (* a 2) (* b 2))
        volume (* a b c)]
    (+ smallest-perimeter volume)))

(defn solution-template [fun input] (->> input (map fun) (apply +)))

;; Solutions
(defn solve-1 [input] (solution-template present input))
(defn solve-2 [input] (solution-template ribbon input))
(def solve (partial (comp (juxt solve-1 solve-2) parse)))

;; Run solutions
; (time (solve input))
