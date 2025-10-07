(ns
  ^{:title      "Wait For It",
    :doc        "Module for solving Advent of Code 2023 Day 6 problem.",
    :url        "http://www.adventofcode.com/2023/day/6",
    :difficulty :m,
    :year       2023,
    :day        6,
    :stars      2,
    :tags       [:algebra]}
  aoclj.year-2023.day-06
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(defn parse
  [raw-input]
  (->> raw-input
       str/split-lines
       (map (comp
             #(mapv parse-long %)
             rest
             #(str/split % #"\s+")))
       ((fn [[time distance]] (zipmap time distance)))
       (into [])))

;; TODO: Check if quadratic equation formula can be applied here.

(defn win?
  [time distance x]
  (> (* (- time x) x) distance))

(defn ways-to-win
  [[time distance]]
  (->> (range 1 time)
       (r/filter #(win? time distance %))
       (r/reduce (fn [acc _] (inc acc)) 0)))

(defn part-1
  [input]
  (->> input
       (map ways-to-win)
       (reduce *)))

(defn part-2
  [input]
  (->> (utils/transpose input)
       (map (partial apply str))
       (mapv parse-long)
       ways-to-win))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"

  (def raw-input
    (utils/read-input-data 2023 6))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)