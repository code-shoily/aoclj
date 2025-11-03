(ns
  ^{:title      "Smoke Basin",
    :doc        "Module for solving Advent of Code 2021 Day 9 problem.",
    :url        "http://www.adventofcode.com/2021/day/9",
    :difficulty :m,
    :year       2021,
    :day        9,
    :stars      2,
    :tags       [:flood-fill :grid]}
  aoclj.year-2021.day-09
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [cartesian-product]]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  "Get a 2D array representing all heights"
  [raw-input]
  (->> raw-input
       str/split-lines
       (mapv (comp #(mapv Character/getNumericValue %) seq))))

;; Movement Functions

(defn up [[x y]] [(dec x) y])
(defn down [[x y]] [(inc x) y])
(defn left [[x y]] [x (dec y)])
(defn right [[x y]] [x (inc y)])
(def neighbours (juxt up down left right))

;; Solution Functions

(defn risk-level
  [grid pos]
  (let [height     (get-in grid pos)
        low-point? (->> (neighbours pos)
                        (map #(get-in grid %))
                        (remove nil?)
                        (every? #(< height %)))]
    (if low-point? [pos (inc height)] nil)))

(defn get-low-points
  [grid]
  (let [height (count grid)
        width  (count (first grid))]
    (->> (cartesian-product (range height) (range width))
         (keep #(risk-level grid %)))))

(defn same-basin?
  [grid src pos]
  (let [height (get-in grid pos)]
    (and height (> 9 height src))))

(defn flood-fill
  [grid pos]
  (let [flooded (atom #{})]
    (letfn [(fill [pos]
              (let [height (get-in grid pos)
                    flood? (partial same-basin? grid height)
                    up     (up pos)
                    down   (down pos)
                    left   (left pos)
                    right  (right pos)]
                (when-not (@flooded pos)
                  (swap! flooded conj pos)
                  (when (flood? up) (fill up))
                  (when (flood? down) (fill down))
                  (when (flood? left) (fill left))
                  (when (flood? right) (fill right)))))]
      (fill pos)
      @flooded)))

(defn part-1 [grid] (reduce + (map second (get-low-points grid))))

(defn part-2
  [grid]
  (->> (get-low-points grid)
       (map (comp (partial flood-fill grid) first))
       (sort-by count >)
       (take 3)
       (map count)
       (reduce *)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2021 9))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(rcf/tests
 (solve (io/read-input-data 2021 9))
 :=
 [528 920448])
