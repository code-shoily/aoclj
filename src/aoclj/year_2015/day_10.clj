(ns
  ^{:title      "Elves Look, Elves Say",
    :doc        "Module for solving Advent of Code 2015 Day 10 problem.",
    :url        "http://www.adventofcode.com/2015/day/10",
    :difficulty :s,
    :year       2015,
    :day        10,
    :stars      2,
    :tags       [:brute-force :count]}
  aoclj.year-2015.day-10
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (map #(- (int %) (int \0)) (str/trim raw-input)))

(defn look-and-say
  [line]
  (into []
        (comp (partition-by identity)
              (mapcat #(vector (count %) (first %))))
        line))

(defn iter-look-and-say
  [times input]
  (nth (iterate look-and-say input) times))

(defn solve
  [raw-input]
  (let [input      (parse raw-input)
        part-1-str (iter-look-and-say 40 input)
        part-2-str (iter-look-and-say 10 part-1-str)]
    [(count part-1-str) (count part-2-str)]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2015 10))
  (time (solve raw-input))
  "</Explore>"
)
