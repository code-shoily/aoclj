(ns ^{:title      "Red-Nosed Reports",
      :doc        "Module for solving Advent of Code 2024 Day 2 problem.",
      :url        "http://www.adventofcode.com/2024/day/2",
      :difficulty :s,
      :year       2024,
      :day        2,
      :stars      2,
      :tags       [:brute-force :revisit]}
    aoclj.year-2024.day-02
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [input]
  (->> input
       str/split-lines
       (mapv (comp (partial mapv parse-long) #(str/split % #" ")))))

(defn remove-at-index
  [idx coll]
  (into [] (concat (subvec coll 0 idx) (subvec coll (inc idx)))))

(defn safe?
  [coll]
  (letfn [(growth-by [f]
            #(->> %
                  (map (fn [[a b]] (- b a)))
                  (every? f)))]
    (let [increasing? (growth-by pos?)
          decreasing? (growth-by neg?)
          pairs       (partitionv 2 1 coll)
          growing-or-shrinking? (or (increasing? pairs) (decreasing? pairs))]
      (when growing-or-shrinking?
        (->> pairs
             (map (fn [[a b]] (abs (- b a))))
             (every? (fn [i] (<= 1 i 3))))))))

(defn safe-after-removal?
  [coll]
  (->> (range (count coll))
       (map (comp safe? #(remove-at-index % coll)))
       (some true?)))

(defn count-safe
  [f]
  #(->> %
        (filter f)
        count))

(def part-1 (count-safe safe?))

(def part-2 (count-safe safe-after-removal?))

(def solve (io/generic-solver part-1 part-2 parse))

(rcf/tests
 (def input-data (io/read-input-data 2024 2))
 (solve input-data)
 :=
 [486 540])

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2024 2))
  (def input (parse input-data))
  input
  (part-2 input)
  (time (solve input-data))
  "</Explore>")
