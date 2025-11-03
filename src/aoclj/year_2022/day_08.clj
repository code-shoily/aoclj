(ns
  ^{:title      "Treetop Tree House",
    :doc        "Module for solving Advent of Code 2022 Day 8 problem.",
    :url        "http://www.adventofcode.com/2022/day/8",
    :difficulty :m,
    :year       2022,
    :day        8,
    :stars      2,
    :tags       [:matrix]}
  aoclj.year-2022.day-08
  (:require [aoclj.helpers.io :as utils]
            [aoclj.helpers.seq :refer [transpose]]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  "Create a NxN grid of heights"
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv (partial mapv #(- (int %) (int \0))))))

(defn visible-from-outside?
  [tree all-dirs]
  (reduce (fn [acc dir]
            (if (every? #(< % tree) dir) (reduced true) acc))
          false
          all-dirs))

(defn viewing-distance
  "Viewing distance of the tree at a particular direction"
  [tree trees-at-dir]
  (reduce (fn [dist tree']
            (if (< tree' tree)
              (inc dist)
              (reduced (min (count trees-at-dir) (inc dist)))))
          0
          trees-at-dir))

(defn survey-trees
  "Quadcopter's survey of the grid map and creating a result grid
   with visibility from outside and scenic score.
   
   This is a pretty interesting pattern to traverse 2D vectors.
   Thanks to narimiran/AdventOfCode2022 for inspiration."
  [grid]
  (let [h-grid grid
        v-grid (transpose grid)]
    (for [[y row] (map-indexed vector h-grid)      ;; <--- y --->
          [x col] (map-indexed vector v-grid)      ;; ^--- x ---v
          :let    [dirs   [(rseq (subvec row 0 x)) ;; <---(x ...
                           (subvec row (inc x))    ;; ...x)--->
                           (rseq (subvec col 0 y)) ;; ^--- (x ...
                           (subvec col (inc y))]   ;; ...x) ---v
                   height (row x)]]
      {:visible? (visible-from-outside? height dirs),
       :score    (reduce * (map (partial viewing-distance height) dirs))})))

(defn solve
  [raw-input]
  (let [tree-summary (survey-trees (parse raw-input))
        part-1       (count (filter :visible? tree-summary))
        part-2       (reduce max (map :score tree-summary))]
    [part-1 part-2]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 8))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2022 8))
 :=
 [1763 671160])
