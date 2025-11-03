(ns
  ^{:title      "Code Chronicle",
    :doc        "Module for solving Advent of Code 2024 Day 25 problem.",
    :url        "http://www.adventofcode.com/2024/day/25",
    :difficulty :xs,
    :year       2024,
    :day        25,
    :stars      2,
    :tags       [:transpose]}
  aoclj.year-2024.day-25
  (:require [aoclj.helpers.io :as io]
            [aoclj.helpers.seq :refer [transpose]]
            [clojure.math.combinatorics :as comb]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn extract-components
  [line]
  (let [parts (->> (str/split-lines line)
                   (mapv vec)
                   transpose)
        lock? (= \. (get-in parts [0 0]))]
    [lock?
     (mapv (comp dec
                 count
                 (partial filter #(= % \#)))
           parts)]))

(defn fits?
  [[lock key]]
  (loop [[l & ls] lock
         [k & ks] key]
    (cond
      (nil? l)      true
      (< 5 (+ l k)) false
      :else         (recur ls ks))))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (str/split raw-input #"\n\n")
       (mapv extract-components)
       (reduce (fn [[locks keys] [lock? heights]]
                 (if lock?
                   [(conj locks heights) keys]
                   [locks (conj keys heights)]))
               [[] []])))

(defn fitting-pairs
  "Solve part 1 -"
  [input]
  (let [[locks keys] input]
    (->> (comb/cartesian-product locks keys)
         vec
         (filterv fits?)
         count)))

(defn solve
  [raw-input]
  [(-> raw-input
       parse
       fitting-pairs) :🎉])

(tests
 (def input-data (io/read-input-data 2024 25))
 (solve input-data)
 :=
 [3146 :🎉])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2024 25))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")
