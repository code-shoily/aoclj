(ns
  ^{:title      "Mull It Over",
    :doc        "Module for solving Advent of Code 2024 Day 3 problem.",
    :url        "http://www.adventofcode.com/2024/day/3",
    :difficulty :s,
    :year       2024,
    :day        3,
    :stars      2,
    :tags       [:state-machine :regex]}
  aoclj.year-2024.day-03
  (:require [aoclj.helpers.io :as io]
            [clojure.core.match :refer [match]]
            [hyperfiddle.rcf :as rcf]))

(defn part-1
  [input]
  (->> input
       (re-seq #"mul\((\d+),(\d+)\)")
       (map (comp (partial apply *)
                  #(map parse-long %)
                  rest))
       (reduce +)))

(defn part-2
  [input]
  (letfn [(multiply [s]
            (->> (re-seq #"mul\((\d+),(\d+)\)" s)
                 (mapcat rest)
                 (map parse-long)
                 (apply *)))]
    (->> input
         (re-seq #"do\(\)|don't\(\)|mul\(\d+,\d+\)")
         (reduce
          (fn [[collect? muls :as acc] tok]
            (match [collect? tok]
              [_ "don't()"] [false muls]
              [_ "do()"]    [true muls]
              [_ nil]       acc
              [true _]      [true (+ muls (multiply tok))]
              :else         acc))
          [true 0])
         second)))

(def solve (io/generic-solver part-1 part-2 identity))

(rcf/tests
 (def input-data (io/read-input-data 2024 3))
 (solve input-data)
 :=
 [190604937 82857512])

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2024 3))
  (time (solve input-data))
  "</Explore>")
