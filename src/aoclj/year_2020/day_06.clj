(ns ^{:title      "Custom Customs",
      :doc        "Module for solving Advent of Code 2020 Day 6 problem.",
      :url        "http://www.adventofcode.com/2020/day/6",
      :difficulty :xs,
      :year       2020,
      :day        6,
      :stars      2,
      :tags       [:set]}
    aoclj.year-2020.day-06
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [input]
  (->> (str/split input #"\n\n")
       (mapv (comp #(mapv (partial mapv str) %) str/split-lines))))

(defn part-1
  [input]
  (->> input
       (map (comp count (partial into #{}) (partial mapcat identity)))
       (reduce +)))

(defn part-2
  [input]
  (->> input
       (map (comp count
                  #(reduce set/intersection %)
                  #(map (partial apply hash-set) %)))
       (reduce +)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2020 6))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

(rcf/tests
 (solve (io/read-input-data 2020 6))
 :=
 [6885 3550])
