(ns
  ^{:title      "Let It Snow",
    :doc        "Module for solving Advent of Code 2015 Day 25 problem.",
    :url        "http://www.adventofcode.com/2015/day/25",
    :difficulty :xs,
    :year       2015,
    :day        25,
    :stars      2,
    :tags       [:reduction :grid :one-off]}
  aoclj.year-2015.day-25
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> raw-input
       str/trim
       (re-find #"[^0-9]+(\d+)[^0-9]+(\d+)\.")
       rest
       (map parse-long)))

(defn index-of
  "Find the index at the right spot."
  [row col]
  (let [width  (/ (* col (inc col)) 2)
        height (reduce + (range col (+ col (dec row))))]
    (+ width height)))

(defn next-code
  [^long prev]
  (rem (* prev 252533) 33554393))

(defn get-code-at
  [row col]
  (let [index (index-of row col)]
    (->> (range 1 index)
         (reduce (fn [acc _] (next-code acc)) 20151125))))

(defn solve
  [raw-input]
  (let [[row col] (parse raw-input)
        next-code (get-code-at row col)]
    [next-code :🎉]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2015 25))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2015 25))
 :=
 [19980801 :🎉])
