(ns
  ^{:title      "Alchemical Reduction",
    :doc        "Module for solving Advent of Code 2018 Day 5 problem.",
    :url        "http://www.adventofcode.com/2018/day/5",
    :difficulty :xs,
    :year       2018,
    :day        5,
    :stars      2,
    :tags       [:stack :brute-force]}
  aoclj.year-2018.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse [raw-input] (map int (str/trim raw-input)))

(defn reacts?
  "`a` reacts with `b` if they are same letter with differing cases."
  [a b]
  (= 32 (abs (- a b))))

(defn equivalent?
  "Is `n` either upper or lower cased value of `X`? `n` itself can be 
   upper or lower case."
  [n x]
  (or (= n x)
      (= (- n 32) x)
      (= (+ n 32) x)))

(defn react
  "React polymer, with an exception of `without` unit."
  [polymer without]
  (loop [[s & ss :as stack] '()
         [x & xs :as coll]  polymer]
    (cond
      (empty? coll)           (count stack)
      (empty? stack)          (recur (cons x stack) xs)
      (reacts? x s)           (recur ss xs)
      (equivalent? x without) (recur stack xs)
      :else                   (recur (cons x stack) xs))))

(def units (range (int \A) (inc (int \Z))))

(defn part-1 [input] (react input 0))

(defn part-2 [input] (apply min (pmap #(react input %) units)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2018 5))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>"
)

(rcf/tests
 (solve (io/read-input-data 2018 5))
 :=
 [10496 5774])
