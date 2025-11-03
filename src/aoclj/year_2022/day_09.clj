(ns
  ^{:title      "Rope Bridge",
    :doc        "Module for solving Advent of Code 2022 Day 9 problem.",
    :url        "http://www.adventofcode.com/2022/day/9",
    :difficulty :m,
    :year       2022,
    :day        9,
    :stars      2,
    :tags       [:grid :revisit]}
  aoclj.year-2022.day-09
  (:require [aoclj.helpers.io :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn to-dir
  [s]
  (case s
    "R" :right
    "L" :left
    "U" :up
    "D" :down))

(defn parse
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv (comp (fn [[dir val]] [(to-dir dir) (parse-long val)])
                   #(str/split % #"\s+")))))

(defn move-head
  "Move head based on direction"
  [[hx hy] direction]
  (case direction
    :up    [hx (inc hy)]
    :down  [hx (dec hy)]
    :left  [(dec hx) hy]
    :right [(inc hx) hy]))

(defn move-tail
  "Move tail to follow head based on relative position"
  [[hx hy] [tx ty]]
  (let [dx (- hx tx)
        dy (- hy ty)]
    (cond
      ;; Vertical movement - same column
      (and (zero? dx) (= dy 2))  [tx (inc ty)]
      (and (zero? dx) (= dy -2)) [tx (dec ty)]

      ;; Horizontal movement - same row
      (and (zero? dy) (= dx 2))  [(inc tx) ty]
      (and (zero? dy) (= dx -2)) [(dec tx) ty]

      ;; Adjacent positions (including diagonally) - don't move
      (= (abs (* dx dy)) 1)      [tx ty]

      ;; Diagonal movements when too far
      (and (pos? dx) (pos? dy))  [(inc tx) (inc ty)]
      (and (pos? dx) (neg? dy))  [(inc tx) (dec ty)]
      (and (neg? dx) (pos? dy))  [(dec tx) (inc ty)]
      (and (neg? dx) (neg? dy))  [(dec tx) (dec ty)]

      ;; Default - don't move
      :else                      [tx ty])))

(defn move-tails
  "Move all tail segments following the head"
  [head-pos tails]
  (reduce (fn [new-tails tail-pos]
            (let [leader (if (empty? new-tails) head-pos (last new-tails))]
              (conj new-tails (move-tail leader tail-pos))))
          []
          tails))

(defn move
  "Move head and all tails in given direction, return new state and updated visits"
  [[hx hy] tails direction visits]
  (let [head-pos  (move-head [hx hy] direction)
        new-tails (move-tails head-pos tails)
        last-tail (last new-tails)]
    [head-pos new-tails (conj visits last-tail)]))

(defn simulate
  "Simulate rope movement for given instructions with specified number of knots"
  ([instructions] (simulate 2 instructions))
  ([num-knots instructions]
   (let [initial-pos   [0 0]
         initial-tails (vec (repeat (dec num-knots) initial-pos))]
     (loop [instructions instructions
            head-pos     initial-pos
            tails        initial-tails
            visits       #{initial-pos}]
       (if (empty? instructions)
         visits
         (let [[direction steps] (first instructions)]
           (if (zero? steps)
             (recur (rest instructions) head-pos tails visits)
             (let [[new-head new-tails new-visits]
                   (move head-pos tails direction visits)]
               (recur (cons [direction (dec steps)] (rest instructions))
                      new-head
                      new-tails
                      (conj new-visits (last new-tails)))))))))))

(defn part-1
  "Solve part 1 - rope with 2 knots (head + 1 tail)"
  [input]
  (->> input
       simulate
       count))

(defn part-2
  "Solve part 2 - rope with 10 knots (head + 9 tails)"
  [input]
  (->> input
       (simulate 10)
       count))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 9))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2022 9))
 :=
 [5907 2303])
