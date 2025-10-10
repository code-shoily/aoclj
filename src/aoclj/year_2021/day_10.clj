(ns
  ^{:title      "Syntax Scoring",
    :doc        "Module for solving Advent of Code 2021 Day 10 problem.",
    :url        "http://www.adventofcode.com/2021/day/10",
    :difficulty :xs,
    :year       2021,
    :day        10,
    :stars      2,
    :tags       [:stack :parens]}
  aoclj.year-2021.day-10
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse [raw-input] (str/split-lines raw-input))

(defn validate-syntax
  [syntax]
  (let [matching-opener {\) \(, \] \[, \} \{, \> \<}
        closing-chars   #{\( \{ \[ \<}]
    (->> syntax
         (reduce (fn [[x & xs :as stck] cur]
                   (cond
                     (closing-chars cur)         (cons cur stck)
                     (= x (matching-opener cur)) xs
                     :else                       (reduced [:corrupt cur])))
                 nil))))

(defn corrupted? [[x & _]] (= x :corrupt))

(defn part-1
  [input]
  (let [score {\) 3, \} 1197, \] 57, \> 25137}]
    (->> input
         (map validate-syntax)
         (filter corrupted?)
         (map (comp score second))
         (reduce +))))

(defn complete-syntax
  [incomplete-part]
  (->> incomplete-part
       (map {\( \), \[ \], \{ \}, \< \>})
       (apply str)))

(defn completion-score
  [completions]
  (let [score {\) 1, \] 2, \} 3, \> 4}]
    (->> (seq completions)
         (reduce (fn [total x]
                   (+ (* total 5) (score x)))
                 0))))

(defn middle-score [colls] ((vec (sort colls)) (quot (count colls) 2)))

(defn part-2
  [input]
  (->> input
       (map validate-syntax)
       (remove corrupted?)
       (map (comp completion-score complete-syntax))
       middle-score))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 10))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>")