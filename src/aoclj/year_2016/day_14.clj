(ns
  ^{:title      "One-Time Pad",
    :doc        "Module for solving Advent of Code 2016 Day 14 problem.",
    :url        "http://www.adventofcode.com/2016/day/14",
    :difficulty :l,
    :year       2016,
    :day        14,
    :stars      2,
    :tags       [:slow :md5 :memoization]}
  aoclj.year-2016.day-14
  (:require [aoclj.helpers.io :as io]
            [aoclj.algorithms.hash :refer [md5]]
            [medley.core :as m]
            #_[hyperfiddle.rcf :as rcf]))

(defn get-hash-idx
  [salt idx]
  (md5 (str salt idx)))

(defn get-stretched-hash-idx
  [salt idx]
  (nth (iterate md5 (get-hash-idx salt idx)) 2016))

(defn triple-and-fives
  [hash]
  (-> {}
      (m/assoc-some :first-three (second (re-find #"(.)\1\1" hash)))
      (m/assoc-some :fives
                    (not-empty (set (map second (re-seq #"(.)\1\1\1\1" hash)))))
      not-empty))

(defn one-time-pad?
  [freq-fn i]
  (when-let [{:keys [first-three]} (freq-fn i)]
    (->> (range (inc i) (+ 1000 i))
         (keep (comp :fives freq-fn))
         (some #(contains? % first-three)))))

(defn get-result
  [index-has-key?]
  (->> (filter index-has-key? (range))
       (take 64)
       last))

(defn make-index-pred
  [hasher]
  (partial one-time-pad? (memoize (comp triple-and-fives hasher))))

(defn part-1
  "Solve part 1 - find the 64th one-time pad key"
  [input]
  (get-result (make-index-pred (partial get-hash-idx input))))

(defn part-2
  "Solve part 2 -"
  [input]
  (get-result (make-index-pred (partial get-stretched-hash-idx input))))

(def solve (io/generic-solver part-1 part-2 io/line))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2016 14))

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
#_(rcf/enable! false)
#_(rcf/tests
 (def input (io/read-input-data 2016 14))
 (solve input) := [15168 20864])
