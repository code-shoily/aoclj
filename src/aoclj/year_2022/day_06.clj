(ns
  ^{:title      "Tuning Trouble",
    :doc        "Module for solving Advent of Code 2022 Day 6 problem.",
    :url        "http://www.adventofcode.com/2022/day/6",
    :difficulty :s,
    :year       2022,
    :day        6,
    :stars      2,
    :tags       [:subseq]}
  aoclj.year-2022.day-06
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :refer [tests]]))

(def parse vec)

(defn start-of-packet
  [len buffer]
  (letfn [(unique?
            [xs]
            (= (count xs) (count (into #{} xs))))]
    (loop [start  0
           marker len]
      (let [prefix (subvec buffer start marker)]
        (if (unique? prefix)
          marker
          (recur (inc start) (inc marker)))))))

(def part-1 (partial start-of-packet 4))
(def part-2 (partial start-of-packet 14))
(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2022 6))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (io/read-input-data 2022 6))
 :=
 [1651 3837])
