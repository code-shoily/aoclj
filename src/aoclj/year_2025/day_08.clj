(ns
  ^{:title      "Playground",
    :doc        "Module for solving Advent of Code 2025 Day 8 problem.",
    :url        "http://www.adventofcode.com/2025/day/8",
    :difficulty :m,
    :year       2025,
    :day        8,
    :stars      2,
    :tags       [:union-find :kruskal]}
  aoclj.year-2025.day-08
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [raw-input]
  (mapv (comp #(mapv Integer/parseInt %)
              #(str/split % #","))
        (io/lines raw-input)))

(defn squared-dist
  [[x y z] [x' y' z']]
  (let [dx (- x x')
        dy (- y y')
        dz (- z z')]
    (+ (* dx dx) (* dy dy) (* dz dz))))

(defn find-root
  [p i]
  (if (= (p i) i) i (recur p (p i))))

(defn union-sets
  [state [i j]]
  (let [root-i (find-root state i)
        root-j (find-root state j)]
    (if (not= root-i root-j)
      (assoc state root-j root-i)
      state)))

(defn build-sorted-graph
  [coords]
  (let [n (count coords)]
    (->> (for [i (range n)
               j (range (inc i) n)]
           [[i j]
            (squared-dist (coords i) (coords j))])
         (sort-by second))))

(defn part-1
  [coords]
  (let [n             (count coords)
        top-edges     (->> coords
                           (build-sorted-graph)
                           (take n))
        initial-state (vec (range n))
        parent-vec    (reduce (fn [state edge]
                                (union-sets state (first edge)))
                              initial-state
                              top-edges)
        circuits      (->> (range n)
                           (map #(find-root parent-vec %))
                           frequencies
                           vals
                           (sort >))]
    (apply * (take 3 circuits))))

(defn part-2
  [coords]
  (let [n (count coords)]
    (loop [edges        (build-sorted-graph coords)
           parent       (vec (range n))
           num-circuits n]
      (let [[[i j] _] (first edges)
            root-i    (find-root parent i)
            root-j    (find-root parent j)]
        (if (not= root-i root-j)
          (if (= num-circuits 2)
            (let [x1 (nth (coords i) 0)
                  x2 (nth (coords j) 0)]
              (* x1 x2))
            (recur (rest edges)
                   (assoc parent root-j root-i)
                   (dec num-circuits)))
          (recur (rest edges) parent num-circuits))))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 8))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

;!zprint {:format :off}
(rcf/enable! false)

(rcf/tests
 (solve (io/read-input-data 2025 8)) := [175500 6934702555])