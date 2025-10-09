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
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [cartesian-product]]))

(defn parse
  "Get a 2D array representing all heights"
  [raw-input]
  (let [grid (->> raw-input
                  str/split-lines
                  (mapv (comp #(mapv Character/getNumericValue %) seq)))
        xdim (count grid)
        ydim (count (first grid))]
    [grid xdim ydim]))

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

(defn part-1
  [[grid xdim ydim]]
  (->> (cartesian-product (range xdim) (range ydim))
       (keep #(risk-level grid %))
       (map second)
       (reduce +)))

(defn same-basin?
  [grid ref-height pos]
  (let [height (get-in grid pos)]
    (and
     (not (nil? height))
     (not (= 9 height))
     (> height ref-height))))

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

(defn part-2
  [[grid xdim ydim]]
  (let [low-points (->> (cartesian-product (range xdim) (range ydim))
                        (keep #(risk-level grid %))
                        (mapv first))]
    (->> low-points
         (map (partial flood-fill grid))
         (sort-by count >)
         (take 3)
         (map count)
         (reduce *))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 9))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")