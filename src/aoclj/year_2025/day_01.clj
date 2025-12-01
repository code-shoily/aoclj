(ns
  ^{:title      "Secret Entrance",
    :doc        "Module for solving Advent of Code 2025 Day 1 problem.",
    :url        "http://www.adventofcode.com/2025/day/1",
    :difficulty :s,
    :year       2025,
    :day        1,
    :stars      2,
    :tags       [:modular-algebra]}
  aoclj.year-2025.day-01
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]))

(defn parse-line
  [[dir & val]]
  [(if (= \L dir) - +) (parse-long (apply str val))])

(defn parse [raw-input] (map parse-line (io/lines raw-input)))

(defn rotate
  [[op val] cur]
  (let [diff        (op cur (rem val 100))
        zero-passed (quot val 100)]
    (cond (neg-int? diff) [(+ diff 100)
                           (if (zero? cur) zero-passed (inc zero-passed))]
          (>= diff 100)   [(- diff 100) (inc zero-passed)]
          (zero? diff)    [0 (inc zero-passed)]
          :else           [diff zero-passed])))

(defn part-1
  [input]
  (second
   (reduce (fn [[cur pwd] [dir val]]
             (let [[nxt _] (rotate [dir val] cur)
                   pwd*    (if (zero? nxt) (inc pwd) pwd)]
               [nxt pwd*]))
           [50 0]
           input)))

(defn part-2
  [input]
  (second
   (reduce
    (fn [[cur pwd] [dir val]]
      (let [[nxt zero-passed] (rotate [dir val] cur)
            pwd* (+ pwd zero-passed)]
        [nxt pwd*]))
    [50 0]
    input)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 1))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
  (solve (io/read-input-data 2025 1)) := [1059 6305])