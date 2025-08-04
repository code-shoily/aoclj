(ns
  ^{:title      "Toboggan Trajectory",
    :doc        "Module for solving Advent of Code 2020 Day 3 problem.",
    :url        "http://www.adventofcode.com/2020/day/3",
    :difficulty :xs,
    :year       2020,
    :day        3,
    :stars      2,
    :tags       [:grid]}
  aoclj.year-2020.day-03
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defprotocol IToboggan
  (update-slope [this [v h]])
  (move [this terrain]))

(defrecord Terrain [grid width height])

(defrecord Trajectory
  [slope current finished? trees]
  IToboggan
    (update-slope
      [this [down right]]
      (assoc this :slope [down right]))
    (move
      [_ {:keys [grid width height]}]
      (let [next-move (let [[down right] slope
                            [v h]        current]
                        [(+ v down) (mod (+ h right) width)])
            finished? (>= (first next-move) height)
            trees     (if-not finished?
                        (+ trees (if (= \# (get-in grid next-move)) 1 0))
                        trees)]
        (->Trajectory slope next-move finished? trees))))

(defn build
  [grid]
  (let [terrain    (->Terrain grid (count (first grid)) (count grid))
        trajectory (->Trajectory [1 3] [0 0] false 0)]
    [terrain trajectory]))

(defn count-trees
  [trajectory terrain]
  (if (:finished? trajectory)
    (:trees trajectory)
    (recur (move trajectory terrain) terrain)))

(defn parse
  [input]
  (build (mapv vec (str/split-lines input))))

(defn part-1
  [[terrain trajectory]]
  (-> trajectory
      (count-trees terrain)))

(defn part-2
  [[terrain trajectory]]
  (let [slopes [[1 1] [1 3] [1 5] [1 7] [2 1]]]
    (->> slopes
         (map (partial update-slope trajectory))
         (map #(count-trees % terrain))
         (reduce *))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data
    (utils/read-input-data 2020 3))

  (def input (parse input-data))

  (time (solve input-data))
  "</Explore>")