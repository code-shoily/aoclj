(ns
 ^{:title "A Maze of Twisty Trampolines, All Alike"
   :doc "Module for solving Advent of Code 2017 Day 5 problem."
   :url "http://www.adventofcode.com/2017/day/5"
   :difficulty :s
   :year 2017
   :day 5
   :stars 2
   :tags [:slow :array]}
 aoclj.year-2017.day-05
  (:require
   [aoclj.utils :as utils]
   [clojure.string :as str]))

(defn jump-1 [coll]
  (loop [idx 0 steps 0 coll' coll]
    (if (or (neg? idx) (>= idx (count coll)))
      steps
      (recur (+ idx (coll' idx))
             (inc steps)
             (update-in coll' [idx] inc)))))

(defn jump-2 [coll]
  (loop [idx 0 steps 0 coll' coll]
    (if (or (neg? idx) (>= idx (count coll)))
      steps
      (recur (+ idx (coll' idx))
             (inc steps)
             (update-in coll' [idx]
                        (fn [v] (if (>= v 3) (dec v) (inc v))))))))

(defn parse
  [input]
  (mapv Integer/parseInt (str/split-lines input)))

(defn part-1 [input] (jump-1 input))
(defn part-2 [input] (jump-2 input))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment "<Explore>"
         (def input-data
           (utils/read-input-data 2017 5))

         (def input (parse input-data))

         (time (solve input-data))
         "</Explore>")