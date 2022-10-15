;;;; --- Day 8: Two-Factor Authentication ---
;;;; Link: https://adventofcode.com/2016/day/8
;;;; Solution: [115 "EFEYKFRFIJ"]
(ns aoclj.year-2016.day-08
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse-rect [line]
  (->> line
       (re-matches #"rect (\d+)x(\d+)")
       rest
       u/to-ints))

(defn coords [[axis _ idx value]]
  [axis (u/to-int idx) (u/to-int value)])

(defn parse-rotation [line]
  (->> line
       (re-matches #"rotate (row|column) (x|y)=(\d+) by (\d+)")
       rest
       coords))

(defn parse-line [line]
  (let [[first & _] (str/split line #"\s")]
    (case first
      "rect" (parse-rect line)
      "rotate" (parse-rotation line))))

(defn parse [input] (map parse-line input))

(defn process-rect [board width height]
  (let [rect (for [col (range width)
                   row (range height)]
               [row col])]
    (reduce #(assoc-in %1 %2 \#) board rect)))

(defn rotate-by [by xs]
  (let [offset #(- % (mod by %))
        rotate (juxt #(drop % xs) #(take % xs))]
    (->> (count xs)
         offset
         rotate
         (mapcat identity)
         vec)))

(defn rotate-row [board idx by]
  (let [xs (board idx)
        rotated (rotate-by by xs)]
    (assoc board idx rotated)))

(defn rotate-column [board idx by]
  (let [transposed (u/transpose board)]
    (u/transpose (rotate-row transposed idx by))))

(defn process-instruction
  [board instruction]
  (match [instruction]
    [[x y]] (process-rect board x y)
    [["row" val by]] (rotate-row board val by)
    [["column" val by]] (rotate-column board val by)))

;; Solutions
(def input (reader/get-input-lines 2016 8))
(def board (u/make-board 50 6 " "))

(defn process-board [input]
  (->> input
       parse
       (reduce process-instruction board)))

(defn solve-1 [board]
  (->> board
       (mapcat identity)
       (filter #(= % \#))
       count))

(defn solve-2 [board] (map #(str/join "" %) board))

(def solve (comp (juxt solve-1 solve-2) process-board))

;; Run the solution
(solve input)