(ns
  ^{:title      "Spiral Memory",
    :doc        "Module for solving Advent of Code 2017 Day 3 problem.",
    :url        "http://www.adventofcode.com/2017/day/3",
    :difficulty :m,
    :year       2017,
    :day        3,
    :stars      2,
    :tags       [:spiral-coords]}
  aoclj.year-2017.day-03
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn next-pos
  "Returns the next position based on the direction and unit step."
  [[x y] dir]
  (case dir
    :top-left     [(dec x) (dec y)]
    :left         [x (dec y)]
    :bottom-left  [(inc x) (dec y)]
    :top-right    [(dec x) (inc y)]
    :right        [x (inc y)]
    :bottom-right [(inc x) (inc y)]
    :up           [(dec x) y]
    :down         [(inc x) y]))

(def directions
  [:right :up :left :down :top-left :top-right :bottom-left :bottom-right])

(defn get-adjacent-coords
  [coord]
  (->> directions
       (map (partial next-pos coord))))

(defn get-dir-at [step] (directions (mod step 4)))

(defn distance-from-origin [[a b]] (+ (abs a) (abs b)))

(defn encoded-movements
  "If we follow the direction, we see a pattern:
   
   ..> right up left left down down right right right ...
   
   It repeats the same move (n/2 + 1) times. This function
   returns encoded movements based on value (or lazily)"
  ([]
   (->> (iterate inc 0)
        (mapcat #(repeat (inc (quot % 2)) %))
        (map get-dir-at)))
  ([n] (take n (encoded-movements))))

(def parse (comp parse-long str/trim))

(defn part-1
  [input]
  (->> (encoded-movements (dec input))
       (reduce next-pos [0 0])
       distance-from-origin))

(defn get-adjacent-sum
  "Returns sum of all neighbouring values that exist."
  [m coord]
  (->> coord
       get-adjacent-coords
       (keep m)
       (reduce +)))

(defn part-2
  [input]
  (->> (encoded-movements)
       (reduce
        (fn [[m coord] dir]
          (let [pos (next-pos coord dir)
                val (get-adjacent-sum m pos)]
            (if (> val input) (reduced val) [(assoc m pos val) pos])))
        [{[0 0] 1} [0 0]])))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2017 3))


  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>")

(tests
 (def input (utils/read-input-data 2017 3))
 (solve input)
 :=
 [430 312453])
