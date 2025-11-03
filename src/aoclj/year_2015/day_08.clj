(ns
  ^{:title      "Matchsticks",
    :doc        "Module for solving Advent of Code 2015 Day 8 problem.",
    :url        "http://www.adventofcode.com/2015/day/8",
    :difficulty :l,
    :year       2015,
    :day        8,
    :stars      2,
    :tags       [:chars]}
  aoclj.year-2015.day-08
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->>
    raw-input
    str/split-lines))

(defn truncate
  "We inspect the first two characters, if they are escaped, then drop
   the escaped amount of chars, otherwise drop just one. One truncate 
   excludes `1` escaped character from the string (4 for hex, 2 for \\ 
   or \", and 1 otherwise)"
  [line]
  (case (take 2 line)
    [\\ \\] (drop 2 line)
    [\\ \"] (drop 2 line)
    [\\ \x] (drop 4 line)
    (drop 1 line)))

(defn truncated-len
  "We return the truncated length, each truncate will deduct 1 char, and 
   remove the leading and trailing double-quotes (-2) that were immune to
   this rule"
  [line]
  (loop [cnt   -2
         chars (seq line)]
    (if (empty? chars)
      cnt
      (recur (inc cnt)
             (truncate chars)))))

(defn expanded-len
  "Opposite of truncate, we double up the \\ or \" and then wrap with 2 
   double quotes (+2) that are immune to this rule"
  [line]
  (->> line
       (map (fn [char]
              (case char
                \\ 2
                \" 2
                1)))
       (reduce + 2)))

(defn len-diff
  "Computes sum of length differences as computed by given functions"
  [large-f small-f]
  (fn [code]
    (->> code
         (map (fn [line] (- (large-f line) (small-f line))))
         (reduce +))))

(def part-1 (len-diff count truncated-len))
(def part-2 (len-diff expanded-len count))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2015 8))

  (def input (parse raw-input))

  input

  (time (solve raw-input))
  "</Explore>"
)

(rcf/tests
 (def input (io/read-input-data 2015 8))
 (solve input)
 :=
 [1333 2046])
