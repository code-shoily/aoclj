(ns
  ^{:title      "Encoding Error",
    :doc        "Module for solving Advent of Code 2020 Day 9 problem.",
    :url        "http://www.adventofcode.com/2020/day/9",
    :difficulty :m,
    :year       2020,
    :day        9,
    :stars      2,
    :tags       [:sliding-window :two-sum]}
  aoclj.year-2020.day-09
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))
(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> raw-input
       str/split-lines
       (mapv parse-long)
       (split-at 25)))

(defn valid?
  "If this is a valid number, basically two-sum algorithm"
  [xs n]
  (let [coll (vec (sort xs))]
    (loop [left-idx  0
           right-idx (dec (count coll))]
      (let [sum (+ (coll left-idx) (coll right-idx))]
        (when (< left-idx right-idx)
          (cond
            (> sum n) (recur left-idx (dec right-idx))
            (< sum n) (recur (inc left-idx) right-idx)
            (= sum n) true))))))

(defn subvec-sum-to
  "Uses sliding window to find a subvector of `coll` that sums to `target`."
  [coll target]
  (let [nums (vec coll)]
    (loop [left  0
           right 0
           sum   0]
      (cond
        (= sum target) (subvec nums left right)

        (< sum target) (if (< right (count nums))
                         (recur left
                                (inc right)
                                (+ sum (nums right)))
                         nil)
        (> sum target) (if (< left right)
                         (recur (inc left)
                                right
                                (- sum (nums left)))
                         (recur (inc left) (inc left) 0))))))

(defn get-first-invalid
  [[init nums]]
  (loop [[_ & upcoming :as preamble] init
         [target & rst] nums]
    (if (valid? preamble target)
      (recur (concat upcoming [target]) rst)
      target)))

(defn find-weakness
  [coll]
  (->> (sort coll)
       ((juxt first last))
       (apply +)))


(defn solve
  [raw-input]
  (let [[init nums] (parse raw-input)
        all-nums    (concat init nums)
        part-1      (get-first-invalid [init nums])
        part-2      (find-weakness (subvec-sum-to all-nums part-1))]
    [part-1 part-2]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2020 9))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2020 9))
 :=
 [15353384 2466556])
