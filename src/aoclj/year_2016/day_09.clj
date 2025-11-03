(ns
  ^{:title      "Explosives in Cyberspace",
    :doc        "Module for solving Advent of Code 2016 Day 9 problem.",
    :url        "http://www.adventofcode.com/2016/day/9",
    :difficulty :m,
    :year       2016,
    :day        9,
    :stars      2,
    :tags       [:regex :pattern-matching]}
  aoclj.year-2016.day-09
  (:require [aoclj.helpers.io :as utils]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]
            [hyperfiddle.rcf :refer [tests]]))

(defn find-pattern
  "Scans the string for a (NxM) and returns the values:
   
   [length-before-pattern N M size-of-marker)]"
  [input]
  (if-let [[patt pre len mul] (re-find #"([A-Z]*)\((\d+)x(\d+)\)" input)]
    [(count pre) (parse-long len) (parse-long mul) (count patt)]
    (count input)))

(defn decompress
  ([input v2?]
   (decompress input 0 v2?))

  ([input curr-len v2?]
   (match (find-pattern input)
     [len< len mul marker]
     (let [[took remaining] (map #(apply str %)
                                 (split-at len (drop marker input)))
           decompressed (if v2?
                          (decompress took true)
                          len)
           seen         (+ curr-len len< (* mul decompressed))]
       (recur remaining seen v2?))
     len (+ curr-len len)))) ;;<------ base case :D

(defn solve
  [raw-input]
  (mapv (partial decompress (str/trim raw-input)) [false true]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2016 9))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (def input (utils/read-input-data 2016 9))
 (solve input)
 :=
 [102239 10780403063])
