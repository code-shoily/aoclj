(ns
  ^{:title      "Bridge Repair",
    :doc        "Module for solving Advent of Code 2024 Day 7 problem.",
    :url        "http://www.adventofcode.com/2024/day/7",
    :difficulty :l,
    :year       2024,
    :day        7,
    :stars      2,
    :tags       [:revisit :backtrack :equation]}
  aoclj.year-2024.day-07
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse-input
  [line]
  (let [[_ lhs rhs] (re-find #"(\d+): (.+)" line)]
    [(parse-long lhs) (mapv parse-long (str/split rhs #"\s"))]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv parse-input)))

(defn solves?
  [res [x1 x2 & xs] concat?]
  (letfn [(solves-for? [op] (solves? res (conj xs (op x1 x2)) concat?))]
    (cond
      (nil? x2)  (when (= res x1) res)
      (> x1 res) nil
      :else      (or (solves-for? +)
                     (solves-for? *)
                     (when concat? (solves-for? (comp parse-long str)))))))

(defn get-calibration-value
  [concat? input]
  (reduce +
          (keep identity
                (pmap (fn [[res vals]] (solves? res vals concat?))
                      input))))

(def part-1 (partial get-calibration-value false))

(def part-2 (partial get-calibration-value true))

(defn solve
  [raw-input]
  (let [input (parse raw-input)]
    (vec (pmap #(% input) [part-1 part-2]))))

(tests
 (def input-data (utils/read-input-data 2024 7))
 (solve input-data)
 :=
 [882304362421 145149066755184])

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2024 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")