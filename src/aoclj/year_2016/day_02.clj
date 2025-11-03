(ns ^{:title      "Bathroom Security",
      :doc        "Module for solving Advent of Code 2016 Day 2 problem.",
      :url        "http://www.adventofcode.com/2016/day/2",
      :difficulty :xs,
      :year       2016,
      :day        2,
      :stars      2,
      :tags       [:grid :decode]}
    aoclj.year-2016.day-02
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(def keypad1 [[1 2 3] [4 5 6] [7 8 9]])

(def keypad2
  [[nil nil \1 nil nil] [nil \2 \3 \4 nil] [\5 \6 \7 \8 \9] [nil \A \B \C nil]
   [nil nil \D nil nil]])

(defn move
  [direction [v h :as current] grid]
  (let [new-pos (case direction
                  \U [(dec v) h]
                  \D [(inc v) h]
                  \L [v (dec h)]
                  \R [v (inc h)])]
    (if-let [key (get-in grid new-pos)]
      [new-pos key]
      [current (get-in grid current)])))

(defn decode-keypad
  [init grid directions]
  (letfn [(decode-key [directions]
            (->> directions
                 (reduce (fn [[cur _] dir] (move dir cur grid))
                         [init (get-in grid init)])
                 second))]
    (->> directions
         (map decode-key)
         (apply str))))

(def parse str/split-lines)
(def part-1 (partial decode-keypad [2 2] keypad1))
(def part-2 (partial decode-keypad [2 0] keypad2))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2016 2))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

(rcf/tests
 (def input (io/read-input-data 2016 2))
 (solve input)
 :=
 ["76792" "A7AC3"])
