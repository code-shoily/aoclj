(ns
 ^{:title "The Tyranny of the Rocket Equation"
   :doc "Module for solving Advent of Code 2019 Day 1 problem."
   :url "http://www.adventofcode.com/2019/day/1"
   :difficulty :xs
   :tags [:formula :recursion]}
 aoclj.year-2019.day-01
  (:require
   [aoclj.utils :as utils]
   [clojure.string :as str]))

(defn parse
  [input]
  (->>
   input
   str/split-lines
   (map Integer/parseInt)))

(defn get-fuel-1
  [mass]
  (-> mass
      (quot 3)
      (- 2)))

(defn part-1
  [input]
  (->> input
       (map get-fuel-1)
       (reduce +)))

(defn get-fuel-2
  [mass]
  (loop [fuel-part mass
         total 0]
    (let [new-fuel-part (get-fuel-1 fuel-part)]
      (if (< new-fuel-part 1)
        total
        (recur new-fuel-part (+ total new-fuel-part))))))

(defn part-2
  [input]
  (->> input
       (map get-fuel-2)
       (reduce +)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment "<Explore>"
         (def input-data (utils/read-input-data 2019 1))
         (time (solve input-data))
         "</Explore>")