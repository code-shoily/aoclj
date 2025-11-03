(ns ^{:title      "Camp Cleanup",
      :doc        "Module for solving Advent of Code 2022 Day 4 problem.",
      :url        "http://www.adventofcode.com/2022/day/4",
      :difficulty :xs,
      :year       2022,
      :day        4,
      :stars      2,
      :tags       [:interval]}
    aoclj.year-2022.day-04
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  [input]
  (let [get-range #(->> (str/split % #"-")
                        (mapv parse-long))]
    (->> input
         str/split-lines
         (map #(str/split % #","))
         (map (fn [[a b]] [(get-range a) (get-range b)])))))

(defn fully-contains?
  [[[a b] [x y]]]
  (or (and (<= a x) (>= b y)) (and (>= a x) (<= b y))))

(defn overlaps?
  [[[a b] [x y] :as pair]]
  (or (fully-contains? pair) (and (<= a x) (>= b x)) (and (<= a y) (>= b y))))

(defn count-assignments-that [f] (comp count #(filter f %)))

(def part-1 (count-assignments-that fully-contains?))
(def part-2 (count-assignments-that overlaps?))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2022 4))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2022 4))
 :=
 [518 909])
