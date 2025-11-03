(ns ^{:title      "Doesn't He Have Intern-Elves For This?",
      :doc        "Module for solving Advent of Code 2015 Day 5 problem.",
      :url        "http://www.adventofcode.com/2015/day/5",
      :difficulty :s,
      :year       2015,
      :day        5,
      :stars      2,
      :tags       [:partition]}
    aoclj.year-2015.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn enough-vowels? [s] (>= (count (filter #{\a \e \i \o \u} s)) 3))

(defn twice-in-a-row?
  [s]
  ((complement empty?) (filter #(apply = %) (partition 2 1 s))))

(defn has-no-naughty-strings? [s] (nil? (re-find #"ab|cd|pq|xy" s)))

(defn check-niceness-for [fs] #(every? true? ((apply juxt fs) %)))

(defn has-sandwitched-letters?
  [s]
  (->> (partitionv 3 1 s)
       (filter (fn [[a _ c]] (= a c)))
       count
       (#(>= % 1))))

(defn has-non-overlapping-twins?
  [s]
  (->> s
       (partitionv 2 1)
       (reduce (fn [[pairs last-pair] current-pair]
                 (cond (= last-pair current-pair) [pairs nil]
                       (pairs current-pair)       (reduced true)
                       :else                      [(conj pairs current-pair)
                                                   current-pair]))
               [#{} nil])
       true?))

(def is-nice-v1?
  (check-niceness-for [enough-vowels? twice-in-a-row? has-no-naughty-strings?]))

(def is-nice-v2?
  (check-niceness-for [has-non-overlapping-twins? has-sandwitched-letters?]))

(def parse str/split-lines)

(defn part-1
  [input]
  (->> input
       (filter is-nice-v1?)
       count))

(defn part-2
  [input]
  (->> input
       (filter is-nice-v2?)
       count))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input (io/read-input-data 2015 5))
  (time (solve raw-input))
  "</Explore>")

(rcf/tests
 (def input (io/read-input-data 2015 5))
 (solve input)
 :=
 [255 55])
