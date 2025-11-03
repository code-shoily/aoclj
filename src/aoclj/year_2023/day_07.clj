(ns
  ^{:title      "Camel Cards",
    :doc        "Module for solving Advent of Code 2023 Day 7 problem.",
    :url        "http://www.adventofcode.com/2023/day/7",
    :difficulty :l,
    :year       2023,
    :day        7,
    :stars      2,
    :tags       [:pattern-matching :ranking]}
  aoclj.year-2023.day-07
  (:require [aoclj.helpers.io :as io]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(def card-rank-1
  {"2" 1,
   "3" 2,
   "4" 3,
   "5" 4,
   "6" 5,
   "7" 6,
   "8" 7,
   "9" 8,
   "T" 9,
   "J" 10,
   "Q" 11,
   "K" 12,
   "A" 13})

(def card-rank-2 (assoc card-rank-1 "J" 0))

(defn hand-frequency
  [hand]
  (->> hand
       (group-by identity)
       (map (fn [[a b]] [a (count b)]))
       (into {})))

(defn compute-rank
  [hand]
  (match hand
    [1 1 1 1 1] 1
    [1 1 1 2]   2
    [1 2 2]     3
    [1 1 3]     4
    [2 3]       5
    [1 4]       6
    [5]         7))

(defn get-rank
  [hand]
  (->> hand
       hand-frequency
       vals
       sort
       vec
       compute-rank))

(defn parse
  [raw-input]
  (->> raw-input
       str/split-lines
       (map #(str/split % #"\s"))
       (map (fn [[hand bid]] [(get-rank (map str hand))
                              (mapv str hand)
                              (parse-long bid)]))))

(defn smaller?
  [[x & xs] [y & ys] mapping]
  (cond
    (> (mapping x) (mapping y)) false
    (< (mapping x) (mapping y)) true
    :else                       (recur xs ys mapping)))

(defn jokered-rank-freq
  [rank freq]
  (match [rank freq]
    [_ nil] rank
    [1 _]   2
    [2 _]   4
    [3 1]   5
    [3 2]   6
    [4 _]   6
    [5 _]   7
    [6 _]   7
    [_ _]   rank))

(defn jokered-rank-hand
  [rank hand]
  (jokered-rank-freq rank ((hand-frequency hand) "J")))

(defn get-winnings
  "Solve part 1 -"
  [mapping input]
  (->> input
       (group-by first)
       (sort-by first)
       (mapcat (fn [[_ hands]]
                 (->> hands
                      (sort (fn [[_ a _] [_ b _]]
                              (smaller? a b mapping))))))
       (map-indexed (fn [a b] [(inc a) b]))
       (reduce (fn [acc [rank [_ _ bid]]] (+ acc (* rank bid))) 0)))

(def part-1 (partial get-winnings card-rank-1))

(defn part-2
  "Solve part 2 -"
  [input]
  (->> input
       (map (fn [[rank hand bid]] [(jokered-rank-hand rank hand) hand bid]))
       (get-winnings card-rank-2)))

(def solve (io/generic-solver part-1 part-2 parse))

(tests
 (def input-data (io/read-input-data 2023 7))
 (solve input-data)
 :=
 [255048101 253718286])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2023 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")
