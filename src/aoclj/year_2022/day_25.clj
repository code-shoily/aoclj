(ns
  ^{:title      "Full of Hot Air",
    :doc        "Module for solving Advent of Code 2022 Day 25 problem.",
    :url        "http://www.adventofcode.com/2022/day/25",
    :difficulty :m,
    :year       2022,
    :day        25,
    :stars      2,
    :tags       [:number-system :modular-algebra]}
  aoclj.year-2022.day-25
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn snafu->decimal
  "Converts a base-5 variant (snafu) to decimal"
  [snafu]
  (->> (reverse snafu)
       (map (fn [d]
              (case d
                \= -2
                \- -1
                (parse-long (str d)))))
       (map-indexed (fn [i d] (* d (Math/pow 5 i))))
       (reduce +)
       long))

(defn parse [raw-input] (map snafu->decimal (str/split-lines raw-input)))

(defn decimal->snafu
  "Convert a decimal number to snafu - mind the = and - padding when dividing"
  [decimal]
  (loop [remaining decimal
         snafu     []]
    (if (zero? remaining)
      (str/join "" snafu)
      (let [next-digit (rem remaining 5)]
        (case next-digit
          3 (recur (quot (+ 2 remaining) 5) (cons "=" snafu))
          4 (recur (quot (+ 1 remaining) 5) (cons "-" snafu))
          (recur (quot remaining 5) (cons (str next-digit) snafu)))))))

(defn part-1 [input] (decimal->snafu (reduce + input)))

(defn solve [raw-input] [(part-1 (parse raw-input)) :🎉])

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 25))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))

  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2022 25))
 :=
 ["2-==10===-12=2-1=-=0" :🎉])