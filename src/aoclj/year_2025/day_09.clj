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
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [raw-input]
  (->> raw-input
       io/lines
       (mapv (comp #(mapv parse-long %)
                   #(str/split % #",")))))

(defn rectangle-area
  [[x1 y1] [x2 y2]]
  (let [width  (inc (abs (- x1 x2)))
        height (inc (abs (- y1 y2)))]
    (* width height)))

(defn inside-interval?
  "Checks if val is strictly between a and b."
  [val a b]
  (let [mn (min a b)
        mx (max a b)]
    (< mn val mx)))

(defn overlaps?
  "Checks if interval [a1, a2] overlaps with (b1, b2) strictly."
  [a1 a2 b1 b2]
  (let [start-a (min a1 a2)
        end-a   (max a1 a2)
        start-b (min b1 b2)
        end-b   (max b1 b2)]
    (and (< start-a end-b) (> end-a start-b))))

(defn polygon-edges
  [points]
  (map vector points (concat (rest points) [(first points)])))

(defn edge-intersects-rect?
  "Returns true if a polygon edge slices through the rectangle interior."
  [[x1 y1 x2 y2 :as _rect] [[ex1 ey1] [ex2 ey2] :as _edge]]
  (cond
    (= ex1 ex2)
    (and (inside-interval? ex1 x1 x2)
         (overlaps? ey1 ey2 y1 y2))
    (= ey1 ey2)
    (and (inside-interval? ey1 y1 y2)
         (overlaps? ex1 ex2 x1 x2))

    :else false))

(defn point-in-polygon?
  "Standard Ray Casting algorithm."
  [[x y] edges]
  (let [intersections
        (reduce (fn [cnt [[x1 y1] [_ y2]]]
                  (if (and
                       (not= y1 y2)
                       (<= (min y1 y2) y)
                       (< y (max y1 y2))
                       (> x1 x))
                    (inc cnt)
                    cnt))
                0
                edges)]
    (odd? intersections)))

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
              (reduce #(max %1 (rectangle-area current-tile (nth tiles %2)))
                      0
                      (range (inc i) n))]
          (recur (inc i) (max max-area local-max)))))))

(defn part-2
  [poly-points]
  (let [edges (polygon-edges poly-points)
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
                     (if (and (not-any? #(edge-intersects-rect? rect %)
                                        edges)
                              (point-in-polygon?
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