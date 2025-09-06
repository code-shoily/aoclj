(ns
  ^{:title      "The Treachery of Whales",
    :doc        "Module for solving Advent of Code 2021 Day 7 problem.",
    :url        "http://www.adventofcode.com/2021/day/7",
    :difficulty :xs,
    :year       2021,
    :day        7,
    :stars      2,
    :tags       [:statistics :formulaic]}
  aoclj.year-2021.day-07
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (sort (map parse-long (str/split (str/trim raw-input) #","))))

(defn median [coll] (nth coll (quot (count coll) 2)))

(defn part-1
  "Solve part 1 - find minimum fuel using linear cost"
  [positions]
  (let [target (median positions)]
    (reduce + (map #(abs (- % target)) positions))))

(defn triangular-number
  "Calculate nth triangular number: 1+2+...+n = n(n+1)/2"
  [n]
  (/ (* n (inc n)) 2))

(defn part-2
  "Solve part 2 - find minimum fuel using triangular cost"
  [positions]
  (let [mean       (/ (reduce + positions) (count positions))
        candidates (range (int (Math/floor mean))
                          (+ 2 (int (Math/ceil mean))))]
    (->> candidates
         (map
          (fn [target]
            (reduce + (map #(triangular-number (abs (- % target))) positions))))
         (apply min))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")