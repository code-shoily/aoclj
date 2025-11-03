(ns
  ^{:title      "Lanternfish",
    :doc        "Module for solving Advent of Code 2021 Day 6 problem.",
    :url        "http://www.adventofcode.com/2021/day/6",
    :difficulty :s,
    :year       2021,
    :day        6,
    :stars      2,
    :tags       [:simulation :frequency]}
  aoclj.year-2021.day-06
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (str/split (str/trim raw-input) #",")
       (map parse-long)
       frequencies))

(defn reproduce
  "Lanternfish reproduction for given number of days"
  [day fishes]
  (if (zero? day)
    (apply + (vals fishes))
    (let [spawning   (get fishes 0 0)
          remaining  (dissoc fishes 0)
          aged       (into {} (map (fn [[k v]] [(dec k) v]) remaining))
          new-fishes (merge-with + aged {6 spawning, 8 spawning})]
      (recur (dec day) new-fishes))))


(def part-1 (partial reproduce 80))
(def part-2 (partial reproduce 256))
(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 6))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2021 6))
 :=
 [350149 1590327954513])
