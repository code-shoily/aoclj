(ns
  ^{:title      "Combo Breaker",
    :doc        "Module for solving Advent of Code 2020 Day 25 problem.",
    :url        "http://www.adventofcode.com/2020/day/25",
    :difficulty :s,
    :year       2020,
    :day        25,
    :stars      2,
    :tags       [:cryptography :modular-algebra]}
  aoclj.year-2020.day-25
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

;; Yes I converted this by hand:
;; github.com/code-shoily/advent_of_code/blob/master/lib/2020/day_25.ex

(set! *unchecked-math* true)

(defn parse [raw-input] (map parse-long (str/split-lines raw-input)))

(defn t1 [^long x ^long p] (rem (* x p) 20201227))

(defn t
  [^long x ^long p ^long s]
  (if (zero? s) x (recur (long (t1 x p)) p (dec s))))

(defn lp
  [^long x ^long k ^long s]
  (let [r (long (t1 x 7))]
    (if (= k r) s (recur r k (inc s)))))

(defn solve
  [raw-input]
  (let [[a b] (parse raw-input)]
    [(t 1 a (lp 1 b 1)) :🎉]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2020 25))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>"
)

(rcf/tests
 (solve (io/read-input-data 2020 25))
 :=
 [3286137 :🎉])
