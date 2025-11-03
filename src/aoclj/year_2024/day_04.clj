(ns
  ^{:title      "Ceres Search",
    :doc        "Module for solving Advent of Code 2024 Day 4 problem.",
    :url        "http://www.adventofcode.com/2024/day/4",
    :difficulty :m,
    :year       2024,
    :day        4,
    :stars      2,
    :tags       [:grid]}
  aoclj.year-2024.day-04
  (:require [aoclj.helpers.io :as utils]
            [aoclj.helpers.seq :refer [transpose]]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(def is-xmas #{"SAMX" "XMAS"})
(def is-mas #{"SAM" "MAS"})

(defn get-vh-count
  [grid]
  (->> grid
       (map (comp count
                  (partial filter is-xmas)
                  (partial map #(apply str %))
                  (partial partitionv 4 1)))
       (reduce +)))

(defn tl-br [r c] (mapv #(do [(+ r %) (+ c %)]) (range 4)))
(defn tr-bl [r c] (mapv #(do [(+ r %) (- c %)]) (range 4)))

(defn get-diag-count
  [dir-f grid]
  (let [rows (count grid)
        cols (count (first grid))]
    (count
     (for [r     (range rows)
           c     (range cols)
           :let  [pos  (dir-f r c)
                  word (->> pos
                            (map (comp str (partial get-in grid)))
                            (apply str))]
           :when (is-xmas word)]
       true))))

(defn x-mas?
  [grid [r c]]
  (let [lr [[(dec r) (dec c)]
            [r c]
            [(inc r) (inc c)]]
        rl [[(dec r) (inc c)]
            [r c]
            [(inc r) (dec c)]]]
    (not-any? nil?
              [(is-mas (apply str (map #(get-in grid %) rl)))
               (is-mas (apply str (map #(get-in grid %) lr)))])))

(defn parse
  [input]
  (->> input
       str/split-lines
       vec
       (mapv vec)))

(defn part-1
  [input]
  (reduce +
          ((juxt get-vh-count
                 #(get-vh-count (transpose %))
                 #(get-diag-count tl-br %)
                 #(get-diag-count tr-bl %))
           input))
)

(defn part-2
  [input]
  (count
   (for [r     (range (count input))
         c     (range (count (first input)))
         :when (x-mas? input [r c])]
     true)))

(def solve (utils/generic-solver part-1 part-2 parse))

(tests
 (def input-data (utils/read-input-data 2024 4))
 (solve input-data)
 :=
 [2575 2041])

(comment
  "<Explore>"
  (def input-data
    (utils/read-input-data 2024 4))

  (def input (parse input-data))

  (time (solve input-data))
  "</Explore>")
