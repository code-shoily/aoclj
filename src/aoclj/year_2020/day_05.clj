(ns
  ^{:title      "Binary Boarding",
    :doc        "Module for solving Advent of Code 2020 Day 5 problem.",
    :url        "http://www.adventofcode.com/2020/day/5",
    :difficulty :xs,
    :year       2020,
    :day        5,
    :stars      2,
    :tags       [:number-system]}
  aoclj.year-2020.day-05
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]
            [medley.core :as m]))

(defn encode
  [seat]
  (-> seat
      (str/replace #"B" "1")
      (str/replace #"F" "0")
      (str/replace #"R" "1")
      (str/replace #"L" "0")
      (Integer/parseInt 2)))

(defn get-seat-number [[row col]] (+ (* row 8) col))

(defn parse
  [input]
  (->> (str/split-lines input)
       (map (comp
             get-seat-number
             (partial map encode)
             (partial map #(apply str %))
             (partial partition-by #(or (= % \B) (= % \F)))))))

(defn part-1 [input] (apply max input))

(defn part-2
  [input]
  (->> (sort input)
       (partitionv 2 1)
       (m/find-first (fn [[a b]] (< 1 (- b a))))
       first
       inc))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data
    (utils/read-input-data 2020 5))

  (def input (parse input-data))

  input

  (time (solve input-data))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2020 5))
 :=
 [930 515])