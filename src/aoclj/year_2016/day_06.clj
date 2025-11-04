(ns ^{:title      "Signals and Noise",
      :doc        "Module for solving Advent of Code 2016 Day 6 problem.",
      :url        "http://www.adventofcode.com/2016/day/6",
      :difficulty :xs,
      :year       2016,
      :day        6,
      :stars      2,
      :tags       [:frequency :matrix]}
    aoclj.year-2016.day-06
  (:require [aoclj.helpers.io :as io]
            [aoclj.helpers.matrix :as mat]
            [hyperfiddle.rcf :as rcf]))

(defn get-min-max
  [freqs]
  (->> freqs
       (reduce (fn [[[_ minv :as min] [_ maxv :as max]] [_ v :as kv]]
                 [(if (< v minv) kv min) (if (> v maxv) kv max)])
               [[nil Integer/MAX_VALUE] [nil Integer/MIN_VALUE]])
       ((fn [[[kmin _] [kmax _]]] [kmin kmax]))))

(defn solve
  [input]
  (let [[min-freq max-freq] (->> (io/lines input)
                                 mat/transpose
                                 (map (comp get-min-max frequencies))
                                 mat/transpose
                                 (map (partial apply str)))]
    [max-freq min-freq]))

(comment
  "<Explore>"
  (def input-data (io/read-input-data 2016 6))
  (time (solve input-data))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (def input (io/read-input-data 2016 6))
 (solve input) := ["qzedlxso" "ucmifjae"])
