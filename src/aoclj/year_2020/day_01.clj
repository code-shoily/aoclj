(ns
 ^{:title "Report Repair"
   :doc "Module for solving Advent of Code 2020 Day 1 problem."
   :url "http://www.adventofcode.com/2020/day/1"
   :difficulty :xs
   :stars 2
   :tags [:nsum]}
 aoclj.year-2020.day-01
  (:require
   [aoclj.utils :as utils]
   [clojure.string :as str]))

(defn parse
  [input]
  (->> (str/split-lines input)
       (map Integer/parseInt)
       sort
       vec))

(defn two-sum
  "Given sorted vector, returns the two values that sum to target"
  [target coll]
  (loop [idx-left 0 idx-right (dec (count coll))]
    (when (<= idx-left idx-right)
      (let [left (coll idx-left)
            right (coll idx-right)
            total (+ left right)]
        (case (compare total target)
          -1 (recur (inc idx-left) idx-right)
          1 (recur idx-left (dec idx-right))
          0 [left right])))))

(defn three-sum
  "Given sorted vector, returns the three values that sum to target"
  [target coll]
  (->> (range 1 (dec (count coll)))
       (reduce
        (fn [_ idx]
          (let [n (coll (dec idx))]
            (when-let [[left right]
                       (two-sum (- target n)
                                (subvec coll idx))]
              (reduced [n left right])))))))

(defn- solver [summing-fn] #(->> % (summing-fn 2020) (reduce *)))

(def part-1 (solver two-sum))
(def part-2 (solver three-sum))
(def solve (utils/generic-solver part-1 part-2 parse))


;; (comment "<Explore>"
(def input-data (utils/read-input-data 2020 1))
(def input (parse input-data))

(part-1 input)
(time (solve input-data))
;; "</Explore>")