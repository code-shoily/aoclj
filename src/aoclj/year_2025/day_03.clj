(ns
  ^{:title      "Lobby",
    :doc        "Module for solving Advent of Code 2025 Day 3 problem.",
    :url        "http://www.adventofcode.com/2025/day/3",
    :difficulty :m,
    :year       2025,
    :day        3,
    :stars      2,
    :tags       [:index :sort]}
  aoclj.year-2025.day-03
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [raw-input]
  (map (comp (partial map-indexed vector)
             (partial map #(Character/digit % 10)))
       (io/lines raw-input)))

(defn find-best-candidate
  [icoll after-idx need]
  (let [size (count icoll)]
    (->> icoll
         (filter (fn [[idx _]] (> idx after-idx)))
         (sort-by second >)
         (filter (fn [[idx _]]
                   (let [remaining-after (- size 1 idx)]
                     (>= remaining-after (dec need)))))
         first)))

(defn joltage-for
  [n icoll]
  (loop [last-idx -1
         need     n
         acc      []]
    (if (zero? need)
      (apply str acc)
      (let [[new-idx val]
            (find-best-candidate icoll last-idx need)]
        (recur new-idx (dec need) (conj acc val))))))

(defn total-output-joltage
  [n icoll]
  (->> icoll
       (map (comp parse-long (partial joltage-for n)))
       (reduce +)))

(def part-1 (partial total-output-joltage 2))
(def part-2 (partial total-output-joltage 12))
(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 3))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 3)) := [17343 172664333119298])