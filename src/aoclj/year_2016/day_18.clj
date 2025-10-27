(ns
  ^{:title      "Like a Rogue",
    :doc        "Module for solving Advent of Code 2016 Day 18 problem.",
    :url        "http://www.adventofcode.com/2016/day/18",
    :difficulty :xs,
    :year       2016,
    :day        18,
    :stars      2,
    :tags       [:slow :partition :revisit]}
  aoclj.year-2016.day-18
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            #_[hyperfiddle.rcf :refer [tests]]))

;; This one is slow, should use the bit-math version
;; See:
;; https://github.com/code-shoily/advent-of-scala/blob/main/src/main/scala/advent_of_scala/year_2016/Day18.scala

(def parse str/trim)

(defn count-safe
  [tiles]
  (count (filter #(= \. %) tiles)))

(defn with-safe-edges [line] (str "." line "."))

(def trap-guide
  #{[\^ \^ \.] [\. \^ \^] [\^ \. \.] [\. \. \^]})

(defn get-next-tile
  [current-tile]
  (->> current-tile
       with-safe-edges
       (partitionv 3 1)
       (map #(if (trap-guide %) "^" "."))
       (apply str)))

(defn count-safe-tiles
  [times init]
  (loop [current    init
         safe-tiles 0
         iter       0]
    (if (= times iter)
      safe-tiles
      (recur (get-next-tile current)
             (+ safe-tiles (count-safe current))
             (inc iter)))))

(def part-1 (partial count-safe-tiles 40))

(def part-2 (partial count-safe-tiles 400000))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2016 18))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

#_(tests
   (def input (utils/read-input-data 2016 18))
   (solve input)
   :=
   [1951 20002936])