(ns ^{:title      "Chronal Calibration",
      :doc        "Module for solving Advent of Code 2018 Day 1 problem.",
      :url        "http://www.adventofcode.com/2018/day/1",
      :year       2018,
      :day        1,
      :difficulty :xs,
      :stars      2,
      :tags       [:infinite-sequence :set]}
    aoclj.year-2018.day-01
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  [raw-input]
  (->> raw-input
       str/split-lines
       (map parse-long)))

(defn part-1 [input] (reduce + input))

(defn part-2
  [input]
  (->> (cycle input)
       (reduce (fn [[freq visited] x]
                 (if freq
                   (let [new-freq (+ freq x)]
                     (if (visited new-freq)
                       (reduced new-freq)
                       [new-freq (conj visited new-freq)]))
                   [x (conj visited x)]))
               [nil #{}])))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "REPL Explorations"
  (def raw-input (utils/read-input-data 2018 1))
  (def input (parse raw-input))
  (time (solve raw-input))
  "REPL Exploration end")

(tests
 (solve (utils/read-input-data 2018 1))
 :=
 [590 83445])
