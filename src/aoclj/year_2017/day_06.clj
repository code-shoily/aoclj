(ns
  ^{:title      "Memory Reallocation",
    :doc        "Module for solving Advent of Code 2017 Day 6 problem.",
    :url        "http://www.adventofcode.com/2017/day/6",
    :difficulty :m,
    :year       2017,
    :day        6,
    :stars      2,
    :tags       [:iterative]}
  aoclj.year-2017.day-06
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]
            [medley.core :as m]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (as-> raw-input _
    (str/trim _)
    (str/split _ #"\t")
    (mapv parse-long _)))

(defn max-index
  "Returns the index of the largest value. In case of multiple largest
   values, it returns the smallest index (This is why max-key couldn't be
   applied)."
  [coll]
  (->> coll
       m/indexed
       (reduce (fn [[_ max :as acc] [i v]]
                 (if (> v max) [i v] acc))
               [0 (first coll)])
       first))

(defn redistribute-max
  [banks]
  (let [index (max-index banks)
        value (banks index)
        size  (count banks)
        banks (assoc banks index 0)]
    (loop [remaining value
           current   (mod (inc index) size)
           banks     banks]
      (if (zero? remaining)
        banks
        (recur (dec remaining)
               (mod (inc current) size)
               (update banks current inc))))))

(defn find-repeated-bank
  "Returns the [idx banks] where a memory bank is repeated."
  [input]
  (->> (iterate inc 0)
       (reduce (fn [[banks visited] idx]
                 (let [redistributed-banks (redistribute-max banks)]
                   (if (visited redistributed-banks)
                     (reduced [(inc idx) banks])
                     [redistributed-banks
                      (conj visited redistributed-banks)])))
               [input #{}])))

(defn solve
  [raw-input]
  (let [input        (parse raw-input)
        [idx-1 dist] (find-repeated-bank input)
        [idx-2 _]    (find-repeated-bank dist)]
    ;; We decrement the value because first occurence is not part of the
    ;; loop
    [idx-1 (dec idx-2)]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2017 6))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (def input (utils/read-input-data 2017 6))
 (solve input)
 :=
 [11137 1037])
