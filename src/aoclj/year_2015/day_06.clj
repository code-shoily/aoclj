(ns
  ^{:title      "Probably a Fire Hazard",
    :doc        "Module for solving Advent of Code 2015 Day 6 problem.",
    :url        "http://www.adventofcode.com/2015/day/6",
    :difficulty :l,
    :year       2015,
    :day        6,
    :stars      2,
    :tags       [:grid]}
  aoclj.year-2015.day-06
  (:require [aoclj.helpers.io :as io]
            [clojure.core.async :as async]
            [clojure.spec.alpha :as s]
            [hyperfiddle.rcf :as rcf]))

(s/def ::setting #{:toggle :on :off})
(s/def ::point (s/tuple int? int?))
(s/def ::cmd (s/tuple ::setting ::point ::point))
(s/def ::input (s/coll-of ::cmd))

(defn parse-line
  [[cmd fx fy tx ty]]
  {:post [(s/valid? ::cmd %)]}
  [(case cmd
     "toggle"   :toggle
     "turn on"  :on
     "turn off" :off)
   [(parse-long fx) (parse-long fy)]
   [(parse-long tx) (parse-long ty)]])

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  {:post [(s/valid? ::input %)]}
  (let [line-pattern
        #"(toggle|turn on|turn off) (\d+),(\d+) through (\d+),(\d+)"]
    (->> raw-input
         (io/lines (partial re-find line-pattern))
         (map rest)
         (map parse-line))))

(s/fdef parse :args string? :ret ::input)

(defn set-lights
  [^longs grid [cmd [x1 y1] [x2 y2]]]
  (let [xmin (min x1 x2)
        xmax (max x1 x2)
        ymin (min y1 y2)
        ymax (max y1 y2)]
    (doseq [x    (range xmin (inc xmax))
            y    (range ymin (inc ymax))
            :let [idx (+ (* x 1000) y)]]
      (case cmd
        :toggle    (aset grid idx (bit-xor (aget grid idx) 1))
        (:on :off) (aset grid idx (if (= cmd :on) 1 0))))
    grid))


(defn set-brightness
  [^longs grid [cmd [x1 y1] [x2 y2]]]
  (let [xmin (min x1 x2)
        xmax (max x1 x2)
        ymin (min y1 y2)
        ymax (max y1 y2)]
    (doseq [x    (range xmin (inc xmax))
            y    (range ymin (inc ymax))
            :let [idx (+ (* x 1000) y)
                  v   (aget grid idx)]]
      (case cmd
        :toggle    (aset grid idx (+ 2 v))
        (:on :off) (aset grid idx (if (= cmd :on) (inc v) (max 0 (dec v))))))
    grid))


(defn part-1
  [moves]
  (let [grid (long-array (* 1000 1000))]
    (reduce set-lights grid moves)
    (reduce + grid)))

(defn part-2
  [moves]
  (let [grid (long-array (* 1000 1000))]
    (reduce set-brightness grid moves)
    (reduce + grid)))

(defn solve
  [raw-input]
  (let [input       (parse raw-input)
        part-1-chan (async/thread (part-1 input))
        part-2-chan (async/thread (part-2 input))]
    [(async/<!! part-1-chan) (async/<!! part-2-chan)]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2015 6))

  (def input (parse raw-input))

  (time (solve raw-input))

  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
   (def input (io/read-input-data 2015 6))
   (solve input) := [377891 14110788])
