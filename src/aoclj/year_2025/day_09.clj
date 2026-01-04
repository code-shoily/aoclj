(ns
  ^{:title      "Movie Theater",
    :doc        "Module for solving Advent of Code 2025 Day 9 problem.",
    :url        "http://www.adventofcode.com/2025/day/9",
    :difficulty :l,
    :year       2025,
    :day        9,
    :stars      2,
    :tags       [:geometry :polygon]}
  aoclj.year-2025.day-09
  (:require [aoclj.helpers.io :as io]
            [aoclj.algorithms.geometry :as g]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [raw-input]
  (->> raw-input
       io/lines
       (mapv (comp #(mapv parse-long %)
                   #(str/split % #",")))))

(defn part-1
  [tiles]
  (let [n (count tiles)]
    (loop [i        0
           max-area 0]
      (if (= i n)
        max-area
        (let [current-tile
              (nth tiles i)
              local-max
              (reduce #(max %1 (g/rectangle-area current-tile (nth tiles %2)))
                      0
                      (range (inc i) n))]
          (recur (inc i) (max max-area local-max)))))))

(defn part-2
  [poly-points]
  (let [edges (g/polygon-edges poly-points)
        n     (count poly-points)]

    (loop [i        0
           max-area 0]
      (if (= i n)
        max-area
        (let [[x1 y1] (nth poly-points i)
              local-max
              (reduce
               (fn [best-a j]
                 (let [[x2 y2] (nth poly-points j)
                       rx-min  (min x1 x2)
                       rx-max  (max x1 x2)
                       ry-min  (min y1 y2)
                       ry-max  (max y1 y2)
                       rect    [rx-min ry-min rx-max ry-max]
                       area    (* (inc (- rx-max rx-min))
                                  (inc (- ry-max ry-min)))]
                   (if (<= area best-a)
                     best-a
                     (if (and (not-any? #(g/edge-intersects-rect? rect %)
                                        edges)
                              (g/point-in-polygon?
                               [(+ rx-min 0.5) (+ ry-min 0.5)]
                               edges))
                       area
                       best-a))))
               0
               (range (inc i) n))]
          (recur (inc i)
                 (max max-area local-max)))))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 9))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 9)) := [4746238001 1552139370])