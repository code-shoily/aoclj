(ns ^{:title      "Historian Hysteria",
      :doc        "Module for solving Advent of Code 2024 Day 1 problem.",
      :url        "http://www.adventofcode.com/2024/day/1",
      :year       2024,
      :day        1,
      :difficulty :xs,
      :stars      2,
      :tags       [:sequence]}
    aoclj.year-2024.day-01
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  [input]
  (->> input
       str/split-lines
       (mapv #(mapv parse-long (str/split % #"\s+")))
       utils/transpose))

(defn part-1
  [[left right]]
  (->> (zipmap (sort left) (sort right))
       (map (fn [[a b]] (abs (- b a))))
       (reduce +)))

(defn part-2
  [[left right]]
  (let [freq (frequencies right)]
    (->> left
         (map (fn [i] (* i (get freq i 0))))
         (reduce +))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2024 1))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")