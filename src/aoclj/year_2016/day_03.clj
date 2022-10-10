;;; --- Year 2016 Day 3: Squares With Three Sides ---
;;; Link: https://adventofcode.com/2016/day/3
;;; Solution: [993 1849]
(ns aoclj.year-2016.day-03
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2016 3))

(defn parse [input]
  (->> input
       (mapv (comp u/to-ints
                   #(remove str/blank? %)
                   #(str/split % #"\s")))))

(defn triangle? [[a b c]]
  (and (> (+ a b) c)
       (> (+ b c) a)
       (> (+ c a) b)))

(defn count-triangles [triplets]
  (->> triplets
       (filter triangle?)
       count))

(defn transposed [triplets]
  (->> triplets
       u/transpose
       (mapcat identity)
       (partition 3)))

;; Solutions
(defn solve-1 [input] (count-triangles input))
(defn solve-2 [input] (count-triangles (transposed input)))
(def solve (comp (juxt solve-1 solve-2) parse))

;; Run the solution
; (time (solve input))

;; REPL Driven
; (comment
; )
