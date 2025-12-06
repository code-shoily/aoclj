(ns
  ^{:title      "Printing Department",
    :doc        "Module for solving Advent of Code 2025 Day 4 problem.",
    :url        "http://www.adventofcode.com/2025/day/4",
    :difficulty :s,
    :year       2025,
    :day        4,
    :stars      2,
    :tags       [:grid :simulation]}
  aoclj.year-2025.day-04
  (:require [aoclj.helpers.io :as io]
            [clojure.set :as set]
            [medley.core :as m]
            [hyperfiddle.rcf :as rcf]))
(defn parse
  [raw-input]
  (let [lines (io/lines raw-input)]
    (into #{}
          (for [[row line] (m/indexed lines)
                [col char] (m/indexed line)
                :when      (= \@ char)]
            [row col]))))

(def deltas [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])

(defn neighbor-count
  [grid [r c]]
  (count
   (for [[dr dc] deltas
         :let    [nr (+ r dr)
                  nc (+ c dc)]
         :when   (grid [nr nc])]
     1)))

(defn part-1
  [input]
  (->> input
       (filter (fn [pos] (< (neighbor-count input pos) 4)))
       count))

(defn part-2
  [input]
  (loop [grid    input
         removed 0]
    (let [to-remove (set/select #(< (neighbor-count grid %) 4) grid)]
      (if (empty? to-remove)
        removed
        (recur (set/difference grid to-remove)
               (+ removed (count to-remove)))))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 4))

  ;; parallel version is 3x faster than set/select for part-2.
  ;; But at ~290ms it's perfectly fine (and readable too)! So I am
  ;; keeping the non-parallel version and leaving it here instead.
  (defn pselect
    [pred coll]
    (let [chunk-size
          (max 1
               (quot (count coll)
                     (.availableProcessors (Runtime/getRuntime))))]
      (->> coll
           (partition-all chunk-size)
           (pmap #(doall (filter pred %)))
           (mapcat identity)
           set)))


  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))

  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
  (solve (io/read-input-data 2025 4)) := [1395 8451])