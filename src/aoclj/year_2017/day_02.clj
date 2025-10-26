(ns ^{:title      "Corruption Checksum",
      :doc        "Module for solving Advent of Code 2017 Day 2 problem.",
      :url        "http://www.adventofcode.com/2017/day/2",
      :difficulty :xs,
      :year       2017,
      :day        2,
      :stars      2,
      :tags       [:checksum]}
    aoclj.year-2017.day-02
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  [input]
  (->> input
       str/split-lines
       (mapv (comp #(mapv parse-long %) #(str/split % #"\t")))))

(defn checksum-1
  [coll]
  (->> coll
       ((juxt #(apply max %) #(apply min %)))
       (apply -)))

(defn checksum-2
  [coll]
  (->> (for [x coll y coll :when (and (not= x y) (< x y) (not= x 0))] [x y])
       (reduce (fn [_ [a b]] (if (zero? (mod b a)) (reduced (quot b a)) 0)))))

(defn compute-checksum [f] (fn [input] (reduce + (map f input))))

(def part-1 (compute-checksum checksum-1))
(def part-2 (compute-checksum checksum-2))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2017 2))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")