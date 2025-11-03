(ns
  ^{:title      "Elves Look, Elves Say",
    :doc        "Module for solving Advent of Code 2015 Day 10 problem.",
    :url        "http://www.adventofcode.com/2015/day/10",
    :difficulty :s,
    :year       2015,
    :day        10,
    :stars      2,
    :tags       [:brute-force :count]}
  aoclj.year-2015.day-10
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (io/line raw-input)
       (map #(- (int %) (int \0)))))

(defn look-and-say
  [line]
  (into []
        (comp (partition-by identity)
              (mapcat #(vector (count %) (first %))))
        line))

(defn iter-look-and-say
  [times input]
  (nth (iterate look-and-say input) times))

(defn solve
  [raw-input]
  (let [input      (parse raw-input)
        part-1-str (iter-look-and-say 40 input)
        part-2-str (iter-look-and-say 10 part-1-str)]
    [(count part-1-str) (count part-2-str)]))

(comment
  "<Explore>"
  (def raw-input (io/read-input-data 2015 10))

  (io/line raw-input)
  (time (solve raw-input))
  "</Explore>"
  )

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (def input (io/read-input-data 2015 10))
 (solve input) := [360154 5103798])
