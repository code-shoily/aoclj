(ns ^{:title      "Calorie Counting",
      :doc        "Module for solving Advent of Code 2022 Day 1 problem.",
      :url        "http://www.adventofcode.com/2022/day/1",
      :year       2022,
      :day        1,
      :difficulty :xs,
      :stars      2,
      :tags       [:section-parse :take-n]}
    aoclj.year-2022.day-01
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn calories-per-elf
  "Parses the input and gets the total calories for each elf in descending 
   order or amount. O(n log n). Perhaps priority queue would be better?"
  [input-data]
  (->> input-data
       str/split-lines
       (partition-by (partial = ""))
       (filter (complement (comp str/blank? first)))
       (map (comp (partial reduce +) (partial map parse-long)))
       (sort >)))

(defn part-1 [input] (first input))
(defn part-2
  [input]
  (->> input
       (take 3)
       (reduce +)))

(def solve (io/generic-solver part-1 part-2 calories-per-elf))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2022 1))
  (time (solve input-data))
  "</Explore>")

(tests
 (solve (io/read-input-data 2022 1))
 :=
 [70720 207148])
