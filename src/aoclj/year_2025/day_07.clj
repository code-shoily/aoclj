(ns
  ^{:title      "Laboratories",
    :doc        "Module for solving Advent of Code 2025 Day 7 problem.",
    :url        "http://www.adventofcode.com/2025/day/7",
    :difficulty :l,
    :year       2025,
    :day        7,
    :stars      2,
    :tags       [:grid :dynamic-programming]}
  aoclj.year-2025.day-07
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]))

(defn get-starting-pos
  [grid]
  (first
   (for [[y row] (map-indexed vector grid)
         [x val] (map-indexed vector row)
         :when   (= val \S)]
     [y x])))

(defn parse
  [raw-input]
  (let [grid (mapv vec (io/lines raw-input))]
    {:grid grid, :start (get-starting-pos grid)}))

(defn part-1
  [{:keys [grid start]}]
  (let [[start-y start-x] start
        height (count grid)]
    (loop [y           start-y
           beams       #{start-x}
           split-count 0]
      (let [next-y (inc y)]
        (if (>= next-y height)
          split-count
          (let [next-state
                (reduce (fn [acc x]
                          (let [cell (get-in grid [next-y x])]
                            (if (= cell \^)
                              (-> acc
                                  (update :splits inc)
                                  (update :beams conj (dec x) (inc x)))
                              (update acc :beams conj x))))
                        {:beams #{}, :splits 0}
                        beams)]
            (recur next-y
                   (:beams next-state)
                   (+ split-count (:splits next-state)))))))))

(defn part-2
  [{:keys [grid start]}]
  (let [[start-y start-x] start
        height (count grid)]
    (loop [y      start-y
           counts {start-x 1}]
      (if (>= (inc y) height)
        (reduce + (vals counts))
        (let [next-y (inc y)
              next-counts
              (reduce-kv (fn [acc x n]
                           (let [cell (get-in grid [next-y x])]
                             (if (= cell \^)
                               (-> acc
                                   (update (dec x) (fnil + 0) n)
                                   (update (inc x) (fnil + 0) n))
                               (update acc x (fnil + 0) n))))
                         {}
                         counts)]
          (recur next-y next-counts))))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 7))

  (def input (parse raw-input))

  input
  (time (solve raw-input))
  "</Explore>"
)

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 7)) := [1518 25489586715621])