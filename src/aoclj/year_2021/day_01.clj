(ns ^{:title "Sonar Sweep",
      :doc "Module for solving Advent of Code 2021 Day 1 problem.",
      :url "http://www.adventofcode.com/2021/day/1",
      :year 2021,
      :day 1,
      :difficulty :xs,
      :stars 2,
      :tags [:sequence]}
    aoclj.year-2021.day-01
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  [input]
  (->> input
       str/split-lines
       (map Integer/parseInt)))

(defn- count-increasing
  [freq-pairs]
  (->> freq-pairs
       (filter #(apply < %))
       count))

(defn part-1
  [input]
  (->> input
       (partition 2 1)
       count-increasing))

(defn part-2
  [input]
  (->> input
       (partition 4 1)
       (map (juxt first last))
       count-increasing))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2021 1))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")