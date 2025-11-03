(ns ^{:title      "A Maze of Twisty Trampolines, All Alike",
      :doc        "Module for solving Advent of Code 2017 Day 5 problem.",
      :url        "http://www.adventofcode.com/2017/day/5",
      :difficulty :s,
      :year       2017,
      :day        5,
      :stars      2,
      :tags       [:array]}
    aoclj.year-2017.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn jump-1
  [coll]
  (let [arr (int-array coll)
        len (alength arr)]
    (loop [idx   0
           steps 0]
      (if (or (neg? idx) (>= idx len))
        steps
        (let [jump-val (aget arr idx)]
          (aset arr idx (inc jump-val))
          (recur (+ idx jump-val) (inc steps)))))))

(defn jump-2
  [coll]
  (let [arr (int-array coll)
        len (alength arr)]
    (loop [idx   0
           steps 0]
      (if (or (neg? idx) (>= idx len))
        steps
        (let [jump-val (aget arr idx)]
          (aset arr idx (if (>= jump-val 3) (dec jump-val) (inc jump-val)))
          (recur (+ idx jump-val) (inc steps)))))))

(defn parse [input] (mapv parse-long (str/split-lines input)))

(defn part-1 [input] (jump-1 input))
(defn part-2 [input] (jump-2 input))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2017 5))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

(rcf/tests
 (def input (io/read-input-data 2017 5))
 (solve input)
 :=
 [372671 25608480])
