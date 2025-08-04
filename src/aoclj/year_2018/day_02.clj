(ns ^{:title      "Inventory Management System",
      :doc        "Module for solving Advent of Code 2018 Day 2 problem.",
      :url        "http://www.adventofcode.com/2018/day/2",
      :difficulty :s,
      :year       2018,
      :day        2,
      :stars      2,
      :tags       [:set :match]}
    aoclj.year-2018.day-02
  (:require [aoclj.utils :as utils]
            [clojure.set :as set]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn two-or-three-times
  [s]
  (letfn [(tally [v] (if (empty? v) 0 1))]
    (->> (frequencies s)
         ((juxt #(filter (fn [[_ v]] (= v 2)) %)
                #(filter (fn [[_ v]] (= v 3)) %)))
         (#(map tally %)))))

(defn differs-by-one?
  [[a b]]
  (loop [mismatch 0
         a'       (vec a)
         b'       (vec b)]
    (match [mismatch a' b']
      [_ [] []] true
      [(_ :guard #(> % 1)) _ _] false
      [_ [x & xs] [y & ys]]
      (if (= x y) (recur mismatch xs ys) (recur (inc mismatch) xs ys)))))

(defn remove-difference
  [[[a b]]]
  (let [remove (set/difference (set a) (set b))]
    (str/replace a (str (first remove)) "")))

(defn all-pairs [input] (for [i input j input :when (not= i j)] [i j]))

(def parse str/split-lines)

(defn part-1
  [input]
  (->> input
       (map two-or-three-times)
       (filter (fn [[a b]] (or (= a 1) (= b 1))))
       (reduce (fn [[x y] [a b]] [(+ x a) (+ y b)]) [0 0])
       (apply *)))

(defn part-2
  [input]
  (->> (all-pairs input)
       (filter differs-by-one?)
       (take 1)
       remove-difference))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2018 2))
  (time (solve input-data))
  "</Explore>")