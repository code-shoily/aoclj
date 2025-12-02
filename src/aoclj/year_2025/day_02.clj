(ns
  ^{:title      "Gift Shop",
    :doc        "Module for solving Advent of Code 2025 Day 2 problem.",
    :url        "http://www.adventofcode.com/2025/day/2",
    :difficulty :s,
    :year       2025,
    :day        2,
    :stars      2,
    :tags       [:string :regex]}
  aoclj.year-2025.day-02
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]
            [clojure.string :as str]))

(defn parse
  [raw-input]
  (->> (str/split (io/line raw-input) #",")
       (map #(str/split % #"-"))))

(defn has-consecutive-repeat?
  [s]
  (let [mid (quot (count s) 2)]
    (= (subs s 0 mid) (subs s mid))))

(def comprised-of-repeats? (partial re-matches #"^(\d+)\1+$"))

(defn collect-invalid-ids
  [pred [i f]]
  (letfn [(leading-zero? [s] (str/starts-with? s "0"))]
    (when-not (or (leading-zero? i) (leading-zero? f))
      (filter #(pred (str %))
              (range (parse-long i)
                     (inc (parse-long f)))))))

(defn solve
  [raw-input]
  (let [input       (parse raw-input)
        part-1-pred has-consecutive-repeat?
        part-2-pred comprised-of-repeats?
        solver      #(->> input
                          (mapcat (partial collect-invalid-ids %))
                          (reduce +))]
    [(solver part-1-pred) (solver part-2-pred)]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 2))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 2)) := [44854383294 55647141923])