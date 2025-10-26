(ns ^{:title      "Inverse Captcha",
      :doc        "Module for solving Advent of Code 2017 Day 1 problem.",
      :url        "http://www.adventofcode.com/2017/day/1",
      :year       2017,
      :day        1,
      :difficulty :xs,
      :stars      2,
      :tags       [:arithmetic]}
    aoclj.year-2017.day-01
  (:require [aoclj.utils :as utils]))

(defn parse [input] (mapv (comp parse-long str) input))

(defn- captcha
  [data]
  (->> data
       (filter #(apply = %))
       (map first)
       (reduce +)))

(defn part-1
  [nums]
  (->> (conj nums (first nums))
       (partitionv 2 1)
       captcha))

(defn part-2
  [nums]
  (->> nums
       (split-at (quot (count nums) 2))
       (apply interleave)
       (partition 2)
       captcha
       (* 2)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "REPL Explorations"
  (def raw-input (utils/read-input-data 2017 1))
  (def input (parse raw-input))
  (time (solve input))
  "End REPL Explorations")