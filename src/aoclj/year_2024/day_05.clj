(ns
  ^{:title      "Print Queue",
    :doc        "Module for solving Advent of Code 2024 Day 5 problem.",
    :url        "http://www.adventofcode.com/2024/day/5",
    :difficulty :m,
    :year       2024,
    :day        5,
    :stars      2,
    :tags       [:sort]}
  aoclj.year-2024.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse-each-by
  "Parse each line to get rules or page info."
  [sep lines]
  (->> (str/split-lines lines)
       (mapv (comp #(mapv parse-long %)
                   #(str/split % sep)))))

(defn parse-rules
  [rules]
  (apply hash-set rules))

(defn sort-by-pair
  [rules page]
  [page (vec (sort #(if (rules [%1 %2]) -1 1) page))])

(defn parse
  [raw-input]
  (let [[rule-part page-part] (str/split raw-input #"\n\n")
        rules (parse-rules (parse-each-by #"\|" rule-part))]
    (map (partial sort-by-pair rules)
         (parse-each-by #"," page-part))))

(defn mid-of [coll] (coll (quot (count coll) 2)))

(defn part-1
  [input]
  (->> input
       (keep (fn [[actual fixed]]
               (when (= actual fixed) (mid-of actual))))
       (reduce +)))

(defn part-2
  [input]
  (->> input
       (keep (fn [[actual fixed]]
               (when (not= actual fixed) (mid-of fixed))))
       (reduce +)))

(def solve (io/generic-solver part-1 part-2 parse))

(rcf/tests
 (def input-data (io/read-input-data 2024 5))
 (solve input-data)
 :=
 [5391 6142])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2024 5))

  (def input (parse raw-input))

  input

  (time (solve raw-input))
  "</Explore>")
