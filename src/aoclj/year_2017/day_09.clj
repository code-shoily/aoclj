(ns ^{:title      "Stream Processing",
      :doc        "Module for solving Advent of Code 2017 Day 9 problem.",
      :url        "http://www.adventofcode.com/2017/day/9",
      :difficulty :s,
      :year       2017,
      :day        9,
      :stars      2,
      :tags       [:state-machine]}
    aoclj.year-2017.day-09
  (:require [aoclj.utils :as utils]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse [input] (str/split input #""))

(defn count-groups
  [coll]
  (loop [stream   coll
         level    1
         group    0
         garbage? false
         cancel?  false
         chars    0]
    (match [stream level group garbage? cancel? chars]
      [[] _ _ _ _ _] [group chars]
      [[_ & r] _ _ _ true _] (recur r level group garbage? false chars)
      [["{" & r] _ _ false _ _] (recur r (inc level) group false cancel? chars)
      [["}" & r] _ _ false _ _]
      (recur r (dec level) (+ (dec level) group) false cancel? chars)
      [["<" & r] _ _ false _ _] (recur r level group true cancel? chars)
      [[">" & r] _ _ true _ _] (recur r level group false cancel? chars)
      [["!" & r] _ _ _ _ _] (recur r level group garbage? true chars)
      [[_ & r] _ _ true _ _] (recur r level group garbage? cancel? (inc chars))
      [[_ & r] _ _ _ _ _] (recur r level group garbage? cancel? chars))))

(defn solve
  [input]
  (-> input
      parse
      count-groups))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2017 9))
  (time (solve input-data))
  "</Explore>")