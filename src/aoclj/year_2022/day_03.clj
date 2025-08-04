(ns ^{:title      "Rucksack Reorganization",
      :doc        "Module for solving Advent of Code 2022 Day 3 problem.",
      :url        "http://www.adventofcode.com/2022/day/3",
      :difficulty :xs,
      :year       2022,
      :day        3,
      :stars      2,
      :tags       [:set :partition]}
    aoclj.year-2022.day-03
  (:require [aoclj.utils :as utils]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn compartmentalize [rucksack] (split-at (quot (count rucksack) 2) rucksack))

(defn common-item [[a b]] (set/intersection (set a) (set b)))

(defn get-priority
  [item]
  (if (Character/isLowerCase item)
    (inc (mod (int item) (int \a)))
    (+ 27 (mod (int item) (int \A)))))

(def parse str/split-lines)

(defn part-1
  [input]
  (->> input
       (map (comp get-priority first common-item compartmentalize))
       (reduce +)))

(defn part-2
  [input]
  (->> input
       (partition 3)
       (map (comp get-priority first #(reduce set/intersection %) #(map set %)))
       (reduce +)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2022 3))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")