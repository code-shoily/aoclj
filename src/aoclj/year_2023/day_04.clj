(ns
  ^{:title      "Scratchcards",
    :doc        "Module for solving Advent of Code 2023 Day 4 problem.",
    :url        "http://www.adventofcode.com/2023/day/4",
    :difficulty :m,
    :year       2023,
    :day        4,
    :stars      2,
    :tags       [:map :recursion]}
  aoclj.year-2023.day-04
  (:require [aoclj.utils :as utils]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[_ id left right] (re-find #"Card\s+(\d+):\s+(.+)\s+\|\s+(.+)" line)
        to-ints #(mapv parse-long (str/split % #"\s+"))]
    [(parse-long id)
     [(into #{} (to-ints left)) (into #{} (to-ints right))]]))


(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (letfn [(count-matches [[id [win pick]]]
            (vector id (count (set/intersection win pick))))]
    (->> raw-input
         str/split-lines
         (mapv (comp count-matches parse-line))
         (into {}))))

(defn- score-for
  [len]
  (if (< len 1) len (bit-shift-left 1 (dec len))))

(defn part-1
  "Solve part 1 - calculate total points when each matching numbers
   get you points (in 1 + 2^n form)"
  [winning-picks]
  (->> winning-picks
       (map (comp score-for second))
       (reduce +)))

(defn- tally-keys
  [m]
  (->> m
       (map (fn [[k _]] [k 1]))
       (into {})))

(defn count-cards [m] (reduce + (vals m)))

(defn part-2
  "Solve part 2 - calculate total cards when each matching numbers
   get you subsequent cards"
  [winning-picks]
  (loop [cur   1
         tally (tally-keys winning-picks)]
    (if (nil? (winning-picks cur))
      (count-cards tally)
      (recur (inc cur)
             (->> (range cur (+ cur (winning-picks cur)))
                  (map #(vector (inc %) (tally cur)))
                  (into {})
                  (merge-with + tally))))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2023 4))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")