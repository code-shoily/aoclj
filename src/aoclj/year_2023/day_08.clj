(ns
  ^{:title      "Haunted Wasteland",
    :doc        "Module for solving Advent of Code 2023 Day 8 problem.",
    :url        "http://www.adventofcode.com/2023/day/8",
    :difficulty :l,
    :year       2023,
    :day        8,
    :stars      2,
    :tags       [:map :lcm :tricky]}
  aoclj.year-2023.day-08
  (:require [aoclj.utils :as utils]
            [clojure.math.numeric-tower :as math]
            [clojure.string :as str]))

(defn parse-edges
  [edges]
  (->> (re-find #"(...) = \((...), (...)\)" edges)
       rest
       ((fn [[key left right]] [key {:L left, :R right}]))))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [[dirs & [_ & edges]] (str/split-lines raw-input)
        dirs  (map (comp keyword str) dirs)
        graph (->> edges
                   (map parse-edges)
                   (into {}))]
    [dirs graph]))

(defn steps-until
  [term? start [dirs graph]]
  (letfn [(step [dir src] ((graph src) dir))]
    (->> (cycle dirs)
         (reduce (fn [[n src] dir]
                   (let [dst (step dir src)]
                     (if (term? dst) (reduced n) [(inc n) dst])))
                 [1 start]))))

(def part-1 (partial steps-until #{"ZZZ"} "AAA"))

(defn part-2
  [input]
  (let [srcs  (filter #(str/ends-with? % "A") (keys (last input)))
        term? #(str/ends-with? % "Z")]
    (reduce (fn [lcm src] (math/lcm (steps-until term? src input) lcm))
            1
            srcs)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2023 8))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)
