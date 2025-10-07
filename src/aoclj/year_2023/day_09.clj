(ns
  ^{:title      "Mirage Maintenance",
    :doc        "Module for solving Advent of Code 2023 Day 9 problem.",
    :url        "http://www.adventofcode.com/2023/day/9",
    :difficulty :xs,
    :year       2023,
    :day        9,
    :stars      2,
    :tags       [:series]}
  aoclj.year-2023.day-09
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv (comp (partial mapv parse-long)
                   #(str/split % #"\s+")))))

(defn delta
  ([a b] (- b a))
  ([[a b]] (delta a b)))

(defn extrapolate-for
  [fun data]
  (loop [current data
         output  []]
    (if (every? zero? current)
      (fun output)
      (recur (mapv delta (partitionv 2 1 current))
             (conj output current)))))

(defn part-1
  [input]
  (letfn [(forward-fn [coll] (reduce + (map last coll)))]
    (reduce + (map (partial extrapolate-for forward-fn) input))))

(defn part-2
  [input]
  (letfn [(backwards-fn [coll]
            (->> (reverse coll)
                 (map first)
                 (reduce delta)))]
    (reduce + (map (partial extrapolate-for backwards-fn) input))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2023 9))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)