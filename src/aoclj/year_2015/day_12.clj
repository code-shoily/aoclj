(ns
  ^{:title      "JSAbacusFramework.io",
    :doc        "Module for solving Advent of Code 2015 Day 12 problem.",
    :url        "http://www.adventofcode.com/2015/day/12",
    :difficulty :s,
    :year       2015,
    :day        12,
    :stars      2,
    :tags       [:json :walk]}
  aoclj.year-2015.day-12
  (:require [aoclj.utils :as utils]
            [cheshire.core :as json]
            [clojure.string :as str]
            [clojure.walk :refer [postwalk]]))

(def parse str/trim)

(defn red? [m] (some #((set %) "red") m))

(defn part-1
  [input]
  (reduce + (map parse-long (re-seq #"-?[0-9]+" input))))

(defn part-2
  [input]
  (->> input
       json/decode
       (postwalk #(cond
                    (and (map? %) (red? %)) nil
                    (map? %) (seq %)
                    :else %))
       flatten
       (filter number?)
       (reduce +)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2015 12))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)