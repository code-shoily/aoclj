(ns ^{:title "The Ideal Stocking Stuffer",
      :doc "Module for solving Advent of Code 2015 Day 4 problem.",
      :url "http://www.adventofcode.com/2015/day/4",
      :difficulty :s,
      :year 2015,
      :day 4,
      :stars 2,
      :tags [:md5 :slow]}
    aoclj.year-2015.day-04
  (:require [aoclj.utils :as utils]
            [aoclj.algorithms.hash :as hash]
            [clojure.string :as str]
            [medley.core :as m]))

(def parse str/trim-newline)

(defn find-first-prefix
  [size secret]
  (let [zeroes (apply str (repeat size "0"))
        extract-prefix #(subs (first %) (count secret))
        hash-per-secret-seq (map (juxt identity hash/md5)
                              (map-indexed #(str %2 %1) (repeat secret)))]
    (->> (m/find-first #(= zeroes (subs (second %) 0 size)) hash-per-secret-seq)
         extract-prefix)))

(def part-1 (partial find-first-prefix 5))

(def part-2 (partial find-first-prefix 6))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2015 4))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")