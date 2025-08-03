(ns ^{:title "Trebuchet?!",
      :doc "Module for solving Advent of Code 2023 Day 1 problem.",
      :url "http://www.adventofcode.com/2023/day/1",
      :year 2023,
      :day 1,
      :difficulty :s,
      :stars 2,
      :tags [:regex :tricky]}
    aoclj.year-2023.day-01
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  [input]
  (->> input
       str/split-lines))

(defn calibration-value
  [pairs]
  (->> pairs
       (map (fn [[a b]] (+ (* 10 a) b)))
       (reduce +)))

(defn part-1
  [input]
  (calibration-value (->> input
                          (map #(->> (re-seq #"\d" %)
                                     (mapv Integer/parseInt)
                                     ((juxt first last)))))))

(defn parse-line
  [line]
  (let [numbers {"zero" 0,
                 "one" 1,
                 "two" 2,
                 "three" 3,
                 "four" 4,
                 "five" 5,
                 "six" 6,
                 "seven" 7,
                 "eight" 8,
                 "nine" 9}
        regex ; #"(?=(one|two..|\d))"
          (re-pattern (str "(?=(" (str/join "|" (keys numbers)) "|\\d))"))
        to-num #(if-let [num (get numbers %)]
                  num
                  (Integer/parseInt %))]
    (->> (re-seq regex line)
         (mapv (comp to-num second))
         ((juxt first last)))))

(defn part-2 [input] (calibration-value (mapv parse-line input)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2023 1))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")