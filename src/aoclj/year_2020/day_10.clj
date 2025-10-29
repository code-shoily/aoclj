(ns
  ^{:title      "Adapter Array",
    :doc        "Module for solving Advent of Code 2020 Day 10 problem.",
    :url        "http://www.adventofcode.com/2020/day/10",
    :difficulty :l,
    :year       2020,
    :day        10,
    :stars      2,
    :tags       [:dynamic-programming]}
  aoclj.year-2020.day-10
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [nums    (->> raw-input
                     str/split-lines
                     (map parse-long))
        builtin (+ 3 (apply max nums))]
    (sort > (concat nums [0 builtin]))))

(defn part-1
  [input]
  (->> input
       (partition 2 1)
       (map (partial apply -))
       frequencies
       (#(select-keys % [1 3]))
       vals
       (apply *)))

(defn count-arrangements
  ([[max-joltage & joltages]] (count-arrangements joltages {max-joltage 1}))
  ([joltages m]
   (loop [[h & rst] joltages
          cache     m]
     (if (nil? h)
       (cache 0)
       (recur rst
              (assoc cache
                     h
                     (->> (range 1 4)
                          (map #(cache (+ h %) 0))
                          (reduce +))))))))


(def part-2 count-arrangements)

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2020 10))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2020 10))
 :=
 [2030 42313823813632])