(ns ^{:title      "Signals and Noise",
      :doc        "Module for solving Advent of Code 2016 Day 6 problem.",
      :url        "http://www.adventofcode.com/2016/day/6",
      :difficulty :xs,
      :year       2016,
      :day        6,
      :stars      2,
      :tags       [:transpose :frequency]}
    aoclj.year-2016.day-06
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn get-min-max
  [freqs]
  (->> freqs
       (reduce (fn [[[_ minv :as min] [_ maxv :as max]] [_ v :as kv]]
                 [(if (< v minv) kv min) (if (> v maxv) kv max)])
               [[nil Integer/MAX_VALUE] [nil Integer/MIN_VALUE]])
       ((fn [[[kmin _] [kmax _]]] [kmin kmax]))))

(defn solve
  [input]
  (let [[min-freq max-freq] (->> (str/split-lines input)
                                 utils/transpose
                                 (map (comp get-min-max frequencies))
                                 utils/transpose
                                 (map (partial apply str)))]
    [max-freq min-freq]))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2016 6))
  (time (solve input-data))
  "</Explore>")

(tests
 (def input (utils/read-input-data 2016 6))
 (solve input)
 :=
 ["qzedlxso" "ucmifjae"])