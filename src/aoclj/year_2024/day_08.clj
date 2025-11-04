(ns
  ^{:title      "Resonant Collinearity",
    :doc        "Module for solving Advent of Code 2024 Day 8 problem.",
    :url        "http://www.adventofcode.com/2024/day/8",
    :difficulty :m,
    :year       2024,
    :day        8,
    :stars      2,
    :tags       [:needs-improvement :geometry]}
  aoclj.year-2024.day-08
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as comb]
            [hyperfiddle.rcf :as rcf]))

;; Disclaimer: I used copilot to convert my own F# code to Clojure.
;; The F# code is here:
;; https://github.com/code-shoily/AdventOfFSharp/blob/master/Year2024/Day08.fs
;; I never thought of using `comb/cartesian-product` in place of F# `allPairs`
;; -
;; nice to know!

(defn solves?
  [{:keys [m c]} x y]
  (< (abs (- y (+ (* m x) c))) 0.001))

(defn slope-intercept-from-points
  "Create slope-intercept from two points"
  [[av ah] [bv bh]]
  (let [x  (double av)
        y  (double ah)
        x' (double bv)
        y' (double bh)
        m  (/ (- y' y) (- x' x))]
    {:m m, :c (- y (* m x))}))

(defn valid-coord?
  "Check if coordinate is within bounds"
  [v-size h-size [v h]]
  (and (<= 0 v)
       (< v v-size)
       (<= 0 h)
       (< h h-size)))

(defn double-distance
  "Calculate double distance point"
  [a b]
  (+ a (* (- b a) 2)))

(defn get-antinodes
  "Get all antinodes for a list of antennas"
  [antennas]
  (->> (comb/cartesian-product antennas antennas)
       (remove (fn [[a b]] (= a b)))
       (map (fn [[[av ah] [bv bh]]]
              [(double-distance av bv)
               (double-distance ah bh)]))))

(defn bigger-than?
  "Check if point a comes before point b in ordering"
  [[av ah] [bv bh]]
  (if (= av bv)
    (< ah bh)
    (< av bv)))

(defn get-same-frequency-pairs
  "Get pairs of antennas for same frequency"
  [antennas]
  (->> (comb/cartesian-product antennas antennas)
       (filter (fn [[a b]] (bigger-than? a b)))))

(defn get-equations
  "Get slope-intercept equations for antenna pairs"
  [antennas]
  (->> antennas
       (mapcat (fn [antenna-list]
                 (->> antenna-list
                      get-same-frequency-pairs
                      (map (fn [[a b]] (slope-intercept-from-points a b))))))
       distinct))


(defn parse
  "Parse raw string input into antennas grouped by frequency"
  [raw-input]
  (let [lines    (str/split-lines raw-input)
        v-size   (count lines)
        h-size   (count (first lines))
        antennas (->> lines
                      (map-indexed (fn [v line]
                                     (map-indexed (fn [h cell]
                                                    [[v h] cell])
                                                  line)))
                      (apply concat)
                      (remove (fn [[_ cell]] (= cell \.)))
                      (group-by second)
                      (map (fn [[_ positions]]
                             (map first positions))))]
    [antennas v-size h-size]))

(defn part-1
  "Solve part 1"
  [[antennas v-size h-size]]
  (->> antennas
       (mapcat get-antinodes)
       (filter (partial valid-coord? v-size h-size))
       distinct
       count))

(defn part-2
  "Solve part 2"
  [[antennas v-size h-size]]
  (let [equations  (get-equations antennas)
        all-points (for [x (range v-size)
                         y (range h-size)]
                     [x y])]
    (->> all-points
         (map (fn [[x y]]
                (some #(solves? % x y) equations)))
         (filter some?)
         count)))

(def solve (io/generic-solver part-1 part-2 parse))

(rcf/tests
 (def input-data (io/read-input-data 2024 8))
 (solve input-data)
 :=
 [291 1015])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2024 8))

  (time (solve raw-input))
  "</Explore>")
