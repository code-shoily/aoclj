(ns
  ^{:title      "An Elephant Named Joseph",
    :doc        "Module for solving Advent of Code 2016 Day 19 problem.",
    :url        "http://www.adventofcode.com/2016/day/19",
    :difficulty :l,
    :year       2016,
    :day        19,
    :stars      2,
    :tags       [:arithmetic :bit-math :needs-improvement]}
  aoclj.year-2016.day-19
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(def parse (comp parse-long str/trim))

(defn part-1
  [input]
  (inc (bit-shift-left (- input (Integer/highestOneBit input)) 1)))

(defn part-2
  [input]
  (let [pow-3 (reduce (fn [acc _]
                        (if (>= (* acc 3) input)
                          (reduced acc)
                          (* acc 3)))
                      1
                      (cycle [:forever]))
        diff  (- input pow-3)]
    (if (<= diff pow-3)
      diff
      (- (* 2 diff) pow-3))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2016 19))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(rcf/tests
 (def input (io/read-input-data 2016 19))
 (solve input)
 :=
 [1842613 1424135])
