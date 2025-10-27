(ns ^{:title      "Not Quite Lisp",
      :doc        "Module for solving Advent of Code 2015 Day 1 problem.",
      :url        "http://www.adventofcode.com/2015/day/1",
      :year       2015,
      :day        1,
      :difficulty :xs,
      :stars      2,
      :tags       [:sequence :reduction]}
    aoclj.year-2015.day-01
  (:require [aoclj.utils :as utils]
            [hyperfiddle.rcf :as rcf :refer [tests]]))

(defn parse
  "( means climbing up (+1) while ) means moving down (-1)"
  [raw-input]
  (->> raw-input
       (map #(case %
               \( 1
               \) -1))
       (map-indexed vector)))

(defn part-1
  [input]
  (->> input
       (map second)
       (reduce + 0)))

(defn part-2
  [input]
  (reduce (fn [acc [idx floor]]
            (let [new-floor (+ floor acc)]
              (if (= -1 new-floor) (reduced (inc idx)) new-floor)))
          0
          input))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "Repl Exploration"
  (def raw-input (utils/read-input-data 2015 1))
  (time (solve raw-input))
  "End Repl Exploration")

(tests
 (solve (utils/read-input-data 2015 1))
 :=
 [232 1783])