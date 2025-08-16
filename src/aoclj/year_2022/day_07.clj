(ns
  ^{:title      "No Space Left On Device",
    :doc        "Module for solving Advent of Code 2022 Day 7 problem.",
    :url        "http://www.adventofcode.com/2022/day/7",
    :difficulty :todo,
    :year       2022,
    :day        7,
    :stars      0,
    :tags       []}
  aoclj.year-2022.day-07
  (:require [aoclj.utils :as utils]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> raw-input
       str/split-lines))

(defn part-1
  "Solve part 1 -"
  [input]
  (->> input
       count))

(defn part-2
  "Solve part 2 -"
  [input]
  (->> input
       count))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 7))

  (def input (parse raw-input))

  (update-in {:a {:b {:c {:d []}}}} [:a :b :c] #(assoc % :a {}))

  (defn parse-command
    [line]
    (let [token (str/split line #"\s+")]
      (match token
        ["$" "ls"]      [:ls]
        ["$" "cd" ".."] [:cd :up]
        ["$" "cd" dir]  [:cd dir]
        ["dir" dir]     [:dir dir]
        [size file]     [(Long/parseLong size) file])))

  (defn walk
    [cmd path structure]
    (match cmd
      [:ls]       [structure path]
      [:cd "/"]   [structure ["/"]]
      [:cd :up]   [structure (butlast path)]
      [:cd dir]   [structure (conj path dir)]
      [:dir dir] [(update-in structure path #(assoc % dir {})) path]
      [size file] [(update-in structure path #(assoc % file size)) path]))
  
  (walk [:dir "a"] ["/" "abc"] {"/" {}})

  (->> input)

  (time (solve raw-input))
  "</Explore>"
  )