(ns
  ^{:title      "Hydrothermal Venture",
    :doc        "Module for solving Advent of Code 2021 Day 5 problem.",
    :url        "http://www.adventofcode.com/2021/day/5",
    :difficulty :xs,
    :year       2021,
    :day        5,
    :stars      2,
    :tags       [:range-math]}
  aoclj.year-2021.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn diagonals
  "Returns diagonal points between two points (inclusive)
   
   Assumption: (= (- a x) (- b y)). Sorts the x-axis so that one
   side is always incremented, and the other inc/decremented based
   on whether (x, y) growth in same or different directions"
  [[[a b] [x y]]]
  (let [len   (abs (- x a))
        start (if (< a x) [a b] [x y])
        y-fun (if (= (= start [a b]) (< b y)) inc dec)]
    (loop [[i j]  start
           iter   0
           result []]
      (if (> iter len)
        result
        (recur [(inc i) (y-fun j)]
               (inc iter)
               (conj result [i j]))))))


(def get-range
  (partial mapv
           (comp #(mapv parse-long %)
                 #(str/split % #","))))

(defn parse
  [raw-input]
  (->> raw-input
       str/split-lines
       (map (comp get-range #(str/split % #" -> ")))))

(defn get-covered-points
  "Get all points covered between two points based on their relative
   positioning. Due to part 1, including diagonally oriented points 
   is optional"
  [with-diagonal? [[a b] [x y] :as points]]
  (cond
    (= a x) (for [i (range (inc (abs (- y b))))] [a (+ (min y b) i)])
    (= b y) (for [i (range (inc (abs (- x a))))] [(+ (min x a) i) b])
    :else   (if with-diagonal? (diagonals points) nil)))

(defn get-overlaps
  [with-diagonal? ranges]
  (->> ranges
       (pmap (partial get-covered-points with-diagonal?))
       (mapcat identity)
       (remove nil?)
       frequencies
       (filter (fn [[_ overlaps]] (> overlaps 1)))
       count))

(def part-1 (partial get-overlaps false))

(def part-2 (partial get-overlaps true))

(defn solve
  [raw-input]
  (let [input (parse raw-input)]
    (into [] (pmap #(% input) [part-1 part-2]))))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2021 5))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(rcf/tests
 (solve (io/read-input-data 2021 5))
 :=
 [4655 20500])
