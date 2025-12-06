(ns
  ^{:title      "Cafeteria",
    :doc        "Module for solving Advent of Code 2025 Day 5 problem.",
    :url        "http://www.adventofcode.com/2025/day/5",
    :difficulty :xs,
    :year       2025,
    :day        5,
    :stars      2,
    :tags       [:range]}
  aoclj.year-2025.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [medley.core :as m]
            [hyperfiddle.rcf :as rcf]))

(defn parse-id-range
  [r]
  (let [[from to] (str/split r #"\-")]
    [(parse-long from) (parse-long to)]))

(defn merge-range-pair
  [[i f] [i' f']]
  (when (or (= i i') (<= i' (inc f)))
    [i (max f f')]))

(defn belongs? [id [i f]] (<= i id f))

(defn merge-ranges
  [ranges]
  (->> ranges
       (sort-by first)
       (reduce (fn [acc x]
                 (if (empty? acc)
                   (conj acc x)
                   (let [merged (merge-range-pair (peek acc) x)]
                     (if (nil? merged)
                       (conj acc x)
                       (conj (pop acc) merged)))))
               [])))

(defn parse
  [raw-input]
  (let [[ranges ids] (str/split raw-input #"\n\n")
        ids          (map parse-long (io/lines ids))
        ranges       (map parse-id-range (io/lines ranges))]
    [ids (merge-ranges ranges)]))

(defn part-1
  [[ids ranges]]
  (count
   (keep (fn [id] (m/find-first #(belongs? id %) ranges)) ids)))

(defn part-2
  [[_ ranges]]
  (->> ranges
       (reduce (fn [acc [start end]] (+ acc (inc (- end start)))) 0)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 5))

  (def input (parse raw-input))

  (-> input)

  (time (solve raw-input))
  "</Explore>"
)

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 5)) := [505 344423158480189])