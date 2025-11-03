(ns
  ^{:title      "Binary Diagnostic",
    :doc        "Module for solving Advent of Code 2021 Day 3 problem.",
    :url        "http://www.adventofcode.com/2021/day/3",
    :difficulty :s,
    :year       2021,
    :day        3,
    :stars      2,
    :tags       [:transpose]}
  aoclj.year-2021.day-03
  (:require [aoclj.helpers.io :as io]
            [aoclj.helpers.matrix :as mat]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(def binvec->int
  "Return binary number given sequence of 0s and 1s
   TODO: Move it to util?"
  (comp #(Integer/parseInt % 2)
        #(apply str %)))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> raw-input
       str/split-lines
       (mapv (partial mapv #(- (int %) (int \0))))))

(defn get-commons
  "Returns [least-common best-common] - on tie 0 comes first."
  [coll]
  (->> coll
       frequencies
       (sort-by (juxt second first))
       (map first)))


(defn part-1
  "Solve part 1 - calculate power consumption (gamma x epsilon)"
  [input]
  (->> input
       mat/transpose
       (map get-commons)
       mat/transpose
       (map binvec->int)
       (apply *)))

(defn rating-component
  "Get the value for rating component, after removal of most or least
   common values per position (based on CO2 or O2 formula)"
  [co2? init]
  (loop [position 0
         bits     init]
    (let [[min max]     (-> (mat/transpose bits)
                            (nth position)
                            get-commons)
          del-val       (if co2? min max)
          width         (count (first init))
          next-position (mod (inc position) width)]
      (if (= 1 (count bits))
        (binvec->int (first bits))
        (recur next-position
               (remove #(= del-val (% position))
                       bits))))))

(defn part-2
  "Solve part 2 - Calculate the life support ratings O2 * CO2"
  [input]
  (let [o2-generator        (rating-component false input)
        co2-scrubber        (rating-component true input)
        life-support-rating (* o2-generator co2-scrubber)]
    life-support-rating))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2021 3))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (io/read-input-data 2021 3))
 :=
 [1540244 4203981])
