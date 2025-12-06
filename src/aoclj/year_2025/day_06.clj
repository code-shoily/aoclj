(ns
  ^{:title      "Trash Compactor",
    :doc        "Module for solving Advent of Code 2025 Day 6 problem.",
    :url        "http://www.adventofcode.com/2025/day/6",
    :difficulty :l,
    :year       2025,
    :day        6,
    :stars      2,
    :tags       [:parse-heavy :transpose]}
  aoclj.year-2025.day-06
  (:require [aoclj.helpers.io :as io]
            [aoclj.helpers.matrix :refer [transpose]]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn split-tokens [line] (str/split (str/trim line) #"\s+"))
(def to-numbers (partial mapv (comp (partial mapv parse-long) split-tokens)))

(defn pad-lines
  [lines]
  (let [max-len (apply max (map count lines))]
    (mapv #(format (str "%-" max-len "s") %) lines)))

(defn col->num
  [col]
  (let [digits (->> (butlast col)
                    (filter #(Character/isDigit %))
                    (apply str))]
    (when-not (str/blank? digits) (parse-long digits))))

(defn solve-block
  [cols]
  (let [op-char (some #(let [c (last %)] (when (not= \space c) c)) cols)
        op-fun  (resolve (symbol (str op-char)))
        numbers (keep col->num (reverse cols))]
    (apply op-fun numbers)))

(defn parse-1
  [raw-input]
  (let [lines     (io/lines raw-input)
        numbers   (to-numbers (butlast lines))
        operators (->> lines
                       last
                       split-tokens
                       (map (comp resolve symbol)))]
    (conj numbers operators)))

(defn parse-2
  [raw-input]
  (let [lines  (io/lines raw-input)
        padded (pad-lines lines)
        cols   (transpose padded)]
    (partition-by (fn [col] (every? #{\space} col)) cols)))

(defn part-1
  [raw-input]
  (->> (parse-1 raw-input)
       transpose
       (map #(apply (last %) (butlast %)))
       (reduce +)))

(defn part-2
  [raw-input]
  (->> (parse-2 raw-input)
       (filter (fn [[b & _]] (some #(not= \space %) b)))
       (map solve-block)
       (reduce +)))

(def solve (io/generic-solver part-1 part-2 identity))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 6))

  (time (solve raw-input))
  "</Explore>"
)

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 6)) := [4805473544166 8907730960817])