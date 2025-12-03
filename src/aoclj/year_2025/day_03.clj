(ns
  ^{:title      "Lobby",
    :doc        "Module for solving Advent of Code 2025 Day 3 problem.",
    :url        "http://www.adventofcode.com/2025/day/3",
    :difficulty :m,
    :year       2025,
    :day        3,
    :stars      2,
    :tags       [:sort :index]}
  aoclj.year-2025.day-03
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [raw-input]
  (map (comp (partial map-indexed vector)
             (partial map #(Character/digit % 10)))
       (io/lines raw-input)))

(defn find-best-candidate
  [indexed-coll after-idx needed]
  (let [total-len (count indexed-coll)]
    (->> indexed-coll
         (filter (fn [[idx _]] (> idx after-idx)))
         (sort-by second >)
         (filter (fn [[idx _]]
                   (let [remaining-after (- total-len 1 idx)]
                     (>= remaining-after (dec needed)))))
         first)))

(defn joltage-for
  [n indexed-coll]
  (loop [last-idx -1
         needed   n
         acc      []]
    (if (zero? needed)
      (apply str acc)
      (let [[new-idx val]
            (find-best-candidate indexed-coll last-idx needed)]
        (recur new-idx (dec needed) (conj acc val))))))

(defn total-output-joltage
  [n indexed-coll]
  (->> indexed-coll
       (map (partial joltage-for n))
       (map parse-long)
       (reduce +)))

(def part-1 (partial total-output-joltage 2))

(def part-2 (partial total-output-joltage 12))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 3))

  (def input (parse raw-input))

  (count input)

  (part-2 input)

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 3)) := [17343 172664333119298])