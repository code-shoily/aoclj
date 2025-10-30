(ns
  ^{:title      "Guard Gallivant",
    :doc        "Module for solving Advent of Code 2024 Day 6 problem.",
    :url        "http://www.adventofcode.com/2024/day/6",
    :difficulty :m,
    :year       2024,
    :day        6,
    :stars      2,
    :tags       [:grid :traversal]}
  aoclj.year-2024.day-06
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn to-grid-2d
  "Converts a string grid to vector grid.
   
   TODO: Look into making this a map grid to save some spaces"
  [input]
  (->> input
       (mapv (comp (partial mapv str) seq))))

(defn get-guard-pos
  "Find the position of the guard in the grid."
  [grid]
  (let [v-size (count grid)
        h-size (first (map count grid))]
    (first (remove nil?
                   (for [v (range v-size)
                         h (range h-size)]
                     (when (= "^" ((grid v) h))
                       [v h]))))))

(defn parse
  [raw-input]
  (let [grid      (to-grid-2d (str/split-lines raw-input))
        guard-pos (get-guard-pos grid)]
    [grid [guard-pos :north]]))

(defn turn-right
  "Which direction will the guard be facing if she turns right?"
  [facing]
  (case facing
    :north :east
    :south :west
    :east  :south
    :west  :north))

(defn get-next-pos
  "What's the next position of the guard is based on her position?"
  [[v h] facing]
  (case facing
    :north [(dec v) h]
    :south [(inc v) h]
    :east  [v (inc h)]
    :west  [v (dec h)]))

(defn at [grid [v h]] (get (get grid v) h))

(defn obstacle? [grid pos] (= "#" (at grid pos)))

(defn count-distinct-positions
  [pos-set]
  (->> pos-set
       (map first)
       distinct
       count))

(defn traverse
  "Guard moves around the map following her rule until she is
   out of the map. Returns the positions visited (point, facing)
   or `nil` if this traversal ends with a loop"
  [[grid guard]]
  (loop [[pos facing] guard
         visited      #{}]
    (if (nil? (at grid pos))
      visited
      (let [next-pos    (get-next-pos pos facing)
            next-facing (turn-right facing)
            loop?       (contains? visited [pos facing])
            visited     (conj visited [pos facing])]
        (cond
          (obstacle? grid next-pos) (recur [pos next-facing] visited)
          loop? nil
          :else (recur [next-pos facing] visited))))))

(defn place-marker-at [grid pos] (assoc-in grid pos "#"))


(defn count-loops
  [[grid guard :as input]]
  (let [path-taken (traverse input)]
    (->> path-taken
         (map first)
         (into #{})
         (map (partial place-marker-at grid))
         (pmap (fn [new-grid] (traverse [new-grid guard])))
         (filter nil?)
         count)))

(def part-1 (comp count-distinct-positions traverse))

(def part-2 count-loops)

(def solve (utils/generic-solver part-1 part-2 parse))

(tests
 (def input-data (utils/read-input-data 2024 6))
 (solve input-data)
 :=
 [4982 1663])

(comment
  "<Explore>"

  (def raw-input
    (utils/read-input-data 2024 6))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")
