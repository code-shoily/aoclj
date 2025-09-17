(ns
  ^{:title      "Plutonian Pebbles",
    :doc        "Module for solving Advent of Code 2024 Day 11 problem.",
    :url        "http://www.adventofcode.com/2024/day/11",
    :difficulty :m,
    :year       2024,
    :day        11,
    :stars      2,
    :tags       [:frequency :bignum :memoization]}
  aoclj.year-2024.day-11
  (:require [aoclj.utils :as utils]
            [clojure.math :refer [floor log10 pow]]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse
  "Returns a frequency map of initial pebbles"
  [raw-input]
  (->> (str/split (str/trim raw-input) #"\s")
       (mapv parse-long)
       frequencies))

(defn digit-count
  "Returns the number of digits of a positive number"
  [^long num]
  (if (zero? num)
    1
    (-> num
        log10
        floor
        inc
        long)))

(defn blink-action
  "Effect of a blink on a pebble."
  [^long num]
  (let [digits      (digit-count num)
        bisectable? (even? digits)
        by          (long (pow 10 (quot digits 2)))]
    (if bisectable?
      [(quot num by) (rem num by)]
      (case num
        0 1N
        (* num 2024N)))))

(defn add-pebbles [freq] (fnil #(+ freq %) 0))

(defn blink
  [pebbles]
  (->> pebbles
       (reduce (fn [acc [pebble freq]]
                 (-> (match (blink-action pebble)
                            [a b]
                            (-> acc
                                (update a (add-pebbles freq))
                                (update b (add-pebbles freq)))
                            value
                            (update acc value (add-pebbles freq)))
                     (update pebble #(- % freq))))
               pebbles)
       (remove (fn [[_ b]] (>= 0 b)))
       (into {})))

(defn blink-times
  [n pebbles]
  (->> pebbles
       (iterate blink)
       (take (inc n))
       last
       vals
       (reduce +)))

(def part-1 (partial blink-times 25))

(def part-2 (partial blink-times 75))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2024 11))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)