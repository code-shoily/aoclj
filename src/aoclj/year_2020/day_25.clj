(ns
  ^{:title      "Combo Breaker",
    :doc        "Module for solving Advent of Code 2020 Day 25 problem.",
    :url        "http://www.adventofcode.com/2020/day/25",
    :difficulty :s,
    :year       2020,
    :day        25,
    :stars      2,
    :tags       [:cryptography :modular-algebra :revisit]}
  aoclj.year-2020.day-25
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  [raw-input]
  (mapv parse-long (str/split-lines raw-input)))

(defn t1 [^long x ^long p] (rem (* x p) 20201227))

(defn t
  [x ^long p ^long s]
  (if (zero? s) x (recur (t1 x p) p (dec s))))

(defn lp
  [x ^long k ^long s]
  (let [r (t1 x 7)]
    (if (= k r) s (recur r k (inc s)))))

(defn solve
  [raw-input]
  (let [[a b] (parse raw-input)]
    [(t 1 a (lp 1 b 1)) :🎉]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2020 25))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>"
)