(ns
  ^{:title      "Seven Segment Search",
    :doc        "Module for solving Advent of Code 2021 Day 8 problem.",
    :url        "http://www.adventofcode.com/2021/day/8",
    :difficulty :m,
    :year       2021,
    :day        8,
    :stars      2,
    :tags       [:set]}
  aoclj.year-2021.day-08
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [clojure.set :refer [difference subset?]]
            [hyperfiddle.rcf :refer [tests]]))


(defn as-sets
  [segments]
  (mapv (partial into #{})
        (str/split segments #"\s")))


(defn parse
  [raw-input]
  (mapv (comp #(mapv as-sets %)
              #(str/split % #"\s\|\s"))
        (str/split-lines raw-input)))

(defn count-1478
  [display]
  (count (keep (comp #{2 3 4 7} count) display)))

(defn infer-1478
  [segments]
  (->> segments
       (map (fn [seg]
              [(case (count seg)
                 2 1
                 3 7
                 4 4
                 7 8
                 nil) seg]))
       (remove (fn [[digit _]] (nil? digit)))
       (into {})))

(defn infer-digit
  [{one 1, four 4} segment]
  (case (count segment)
    2 1
    3 7
    4 4
    5 (condp subset? segment
        one 3
        (difference four one) 5
        2)
    6 (condp subset? segment
        four 9
        one  0
        6)
    7 8))


(defn get-display-digit
  [[segment display]]
  (let [mapping (infer-1478 segment)]
    (->> display
         (map (partial infer-digit mapping))
         (apply str)
         parse-long)))

(defn part-1 [input] (reduce + (map (comp count-1478 second) input)))

(defn part-2 [input] (reduce + (map get-display-digit input)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2021 8))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (io/read-input-data 2021 8))
 :=
 [534 1070188])
