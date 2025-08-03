(ns ^{:title "No Time for a Taxicab",
      :doc "Module for solving Advent of Code 2016 Day 1 problem.",
      :url "http://www.adventofcode.com/2016/day/1",
      :year 2016,
      :day 1,
      :difficulty :s,
      :stars 2,
      :tags [:grid :set]}
    aoclj.year-2016.day-01
  (:require [aoclj.utils :as utils]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-directions
  [[x & xs]]
  (let [direction (case x
                    \L :left
                    \R :right)
        steps (-> xs
                  str/join
                  Integer/parseInt)]
    [direction steps]))

(defn parse
  [input-data]
  (->> (str/split input-data #", ")
       (map parse-directions)))

(defrecord Traveller [position facing])

(defn next-facing
  [facing direction]
  (case [facing direction]
    [:north :left] :west
    [:north :right] :east
    [:south :left] :east
    [:south :right] :west
    [:east :left] :north
    [:east :right] :south
    [:west :left] :south
    [:west :right] :north))

(defn next-position
  [[x y] direction steps]
  (case direction
    :north [x (+ y steps)]
    :south [x (- y steps)]
    :east [(+ x steps) y]
    :west [(- x steps) y]))

(defn follow
  [{:keys [position facing]} [direction steps]]
  (let [[x y] position
        next-facing (next-facing facing direction)
        next-position (next-position [x y] next-facing steps)]
    (->Traveller next-position next-facing)))

(defonce traveller (->Traveller [0 0] :north))
(defn distance-from-origin [[i f]] (+ (abs i) (abs f)))

(defn a-to-z
  [a z]
  (let [comparison (compare z a)
        incr (if (pos? comparison) inc dec)]
    (range a (incr z) comparison)))

(defn get-interim-locations
  [[[x1 y1] [x2 y2]]]
  (into #{}
        (next (if (= x1 x2)
                (for [i (a-to-z y1 y2)] [x1 i])
                (for [i (a-to-z x1 x2)] [i y1])))))

(defn part-1
  [instructions]
  (->> instructions
       (reduce follow traveller)
       :position
       distance-from-origin))

(defn part-2
  [instructions]
  (->> instructions
       (reductions follow traveller)
       (map :position)
       (partitionv 2 1)
       (map get-interim-locations)
       (reduce (fn [acc x]
                 (let [repeat? (set/intersection acc x)]
                   (if (empty? repeat?) (set/union acc x) (reduced repeat?)))))
       first
       distance-from-origin))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "Repl Exploration"
  (def raw-input (utils/read-input-data 2016 1))
  (time (solve raw-input))
  "End Repl Exploration")