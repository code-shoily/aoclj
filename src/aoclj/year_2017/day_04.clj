(ns ^{:title      "High-Entropy Passphrases",
      :doc        "Module for solving Advent of Code 2017 Day 4 problem.",
      :url        "http://www.adventofcode.com/2017/day/4",
      :difficulty :xs,
      :year       2017,
      :day        4,
      :stars      2,
      :tags       [:frequency :string]}
    aoclj.year-2017.day-04
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [input]
  (->> input
       (io/lines #(str/split % #" "))))

(defn valid?
  [word-freq]
  (->> (vals word-freq)
       (into #{})
       (= #{1})))

(defn has-anagram?
  [words]
  (->> words
       (map (comp #(apply str %) sort))
       frequencies
       valid?))

(defn part-1
  [input]
  (->> input
       (map frequencies)
       (filter valid?)
       count))

(defn part-2
  [input]
  (->> input
       (filter has-anagram?)
       count))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2017 4))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

;!zpring {:format :off}
(rcf/enable! false)
(rcf/tests
 (def input (io/read-input-data 2017 4))
 (solve input) := [455 186])
