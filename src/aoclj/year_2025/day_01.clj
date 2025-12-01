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

;; Quote of the day: Simulation != Arithmetic.

(defn parse-line [s] [(if (= \L (nth s 0)) - +) (parse-long (subs s 1))])

(defn parse [raw-input] (map parse-line (io/lines raw-input)))

(defn rotate
  [[op val] cur]
  (let [diff   (op cur (rem val 100))
        zeroes (quot val 100)]
    (cond (neg? diff)   [(+ diff 100) (if (zero? cur) zeroes (inc zeroes))]
          (>= diff 100) [(- diff 100) (inc zeroes)]
          (zero? diff)  [0 (inc zeroes)]
          :else         [diff zeroes])))

(defn part-1
  [input]
  (second
   (reduce (fn [[cur pwd] move]
             (let [[nxt _] (rotate move cur)
                   pwd*    (if (zero? nxt) (inc pwd) pwd)]
               [nxt pwd*]))
           [50 0]
           input)))

(defn part-2
  [input]
  (second
   (reduce
    (fn [[cur pwd] move]
      (let [[nxt zeroes] (rotate move cur)]
        [nxt (+ pwd zeroes)]))
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
 (rotate [- 10] 0) := [90 0]
 (rotate [- 10] 10) := [0 1]
 (solve (io/read-input-data 2025 1)) := [1059 6305])