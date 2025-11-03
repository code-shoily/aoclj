(ns ^{:title      "Hoof It",
      :doc        "Module for solving Advent of Code 2024 Day 10 problem.",
      :url        "http://www.adventofcode.com/2024/day/10",
      :difficulty :s,
      :year       2024,
      :day        10,
      :stars      2,
      :tags       [:graph-traversal :map-grid]}
    aoclj.year-2024.day-10
  (:require [aoclj.helpers.io :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn get-neighbours
  "Get valid neighboring positions that are exactly 1 height higher"
  [trail-map [x y]]
  (let [current-height (get-in trail-map [[x y]])]
    (->> [[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]]
         (filter (fn [point]
                   (when-let [neighbor-height (get trail-map point)]
                     (= current-height (dec neighbor-height))))))))

(defn get-trail-heads
  "Find all positions with height 0"
  [trail-map]
  (->> trail-map
       (filter (fn [[_ height]] (= height 0)))
       (map first)))

(defn find-trails-for
  "Find all trails starting from a given point and height"
  [trail-map height point]
  (cond
    (= height 9) [point]
    :else        (->> (get-neighbours trail-map point)
                      (mapcat #(find-trails-for trail-map (inc height) %)))))

(defn find-all-trails
  "Find all trails from all trailheads"
  [trail-map]
  (->> (get-trail-heads trail-map)
       (map #(find-trails-for trail-map 0 %))))

(defn create-trail-map
  "Create a trail map (x, y) -> height"
  [lines]
  (->> lines
       (map-indexed (fn [v row]
                      (map-indexed
                       (fn [h cell] [[v h] (Character/getNumericValue cell)])
                       row)))
       (apply concat)
       (into {})))

(defn parse
  "Parse raw string input into trail data"
  [raw-input]
  (->> (str/split-lines raw-input)
       create-trail-map
       find-all-trails))

(defn part-1
  "Solve part 1 - count distinct trail endpoints for each trailhead"
  [input]
  (->> input
       (map (comp count distinct))
       (reduce +)))

(defn part-2
  "Solve part 2 - count total number of distinct trails"
  [input]
  (->> input
       (map count)
       (reduce +)))

(def solve (utils/generic-solver part-1 part-2 parse))

(tests
 (def input-data (utils/read-input-data 2024 10))
 (solve input-data)
 :=
 [617 1477])

(comment
  "<Explore>"
  (def raw-input (utils/read-input-data 2024 10))

  (parse raw-input)
  (time (solve raw-input))
  "</Explore>")
