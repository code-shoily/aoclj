(ns ^{:title      "Squares With Three Sides",
      :doc        "Module for solving Advent of Code 2016 Day 3 problem.",
      :url        "http://www.adventofcode.com/2016/day/3",
      :difficulty :xs,
      :year       2016,
      :day        3,
      :stars      2,
      :tags       [:transpose :geometry]}
    aoclj.year-2016.day-03
  (:require [aoclj.helpers.io :as io]
            [aoclj.helpers.matrix :as mat]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [input]
  (->> (str/split-lines input)
       (mapv (comp #(mapv parse-long %) #(str/split % #"\s+") str/trim))))

(defn triangle? [[a b c]] (and (> (+ a b) c) (> (+ b c) a) (> (+ c a) b)))

(defn transpose-triplets
  [triplets]
  (->> triplets
       mat/transpose
       (apply concat)
       (partition 3)))

(defn count-triangles
  [triplets]
  (->> triplets
       (filter triangle?)
       count))

(defn part-1 [input] (count-triangles input))
(defn part-2
  [input]
  (-> input
      transpose-triplets
      count-triangles))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2016 3))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

(rcf/tests
 (def input (io/read-input-data 2016 3))
 (solve input)
 :=
 [993 1849])
