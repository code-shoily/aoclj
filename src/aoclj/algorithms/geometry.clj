(ns aoclj.algorithms.geometry
  (:require [hyperfiddle.rcf :as rcf]))

(defn rectangle-area
  "Calculates the area of a rectangle defined by two corners (inclusive).
   Uses discrete grid logic: distance of 0 means width of 1."
  [[x1 y1] [x2 y2]]
  (let [width  (inc (abs (- x1 x2)))
        height (inc (abs (- y1 y2)))]
    (* width height)))

(defn inside-interval?
  "Checks if val is strictly between a and b (exclusive).
   Returns false if val equals a or b."
  [val a b]
  (let [mn (min a b)
        mx (max a b)]
    (< mn val mx)))

(defn overlaps?
  "Checks if interval [a1, a2] strictly overlaps with [b1, b2].
   Touching endpoints (e.g. [0 5] and [5 10]) returns false."
  [a1 a2 b1 b2]
  (let [start-a (min a1 a2)
        end-a   (max a1 a2)
        start-b (min b1 b2)
        end-b   (max b1 b2)]
    (and (< start-a end-b) (> end-a start-b))))

(defn polygon-edges
  "Returns a sequence of edges [p1 p2] closing the loop from last to first point."
  [points]
  (map vector points (concat (rest points) [(first points)])))

(defn edge-intersects-rect?
  "Returns true if a polygon edge strictly slices through the rectangle's interior.
   Edges that only touch the boundary of the rectangle return false."
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
  "Determines if a point is inside a polygon using the Ray Casting algorithm (Even-Odd rule).
   Casts a ray to the right (+x)."
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

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 "Rectangle Area (Inclusive Grid)"
 (rectangle-area [0 0] [0 0]) := 1
 (rectangle-area [0 0] [2 3]) := 12
 (rectangle-area [2 3] [0 0]) := 12

 "Interval Checks"
 (inside-interval? 5 0 10)  := true
 (inside-interval? 0 0 10)  := false
 (inside-interval? 10 0 10) := false
 (inside-interval? 5 10 0)  := true

 "Interval Overlap (Strict)"
 (overlaps? 0 5 3 8)  := true
 (overlaps? 0 5 1 4)  := true
 (overlaps? 0 5 5 10) := false
 (overlaps? 0 5 6 10) := false
 (overlaps? 5 0 8 3)  := true

 "Polygon Edges"
 (polygon-edges [[0 0] [1 1] [2 2]]) := [[[0 0] [1 1]] [[1 1] [2 2]] [[2 2] [0 0]]]

 "Edge Intersection with Rectangle Interior"
 (let [rect [0 0 10 10]]
   (edge-intersects-rect? rect [[5 -5] [5 15]])  := true
   (edge-intersects-rect? rect [[5 2] [5 8]])    := true
   (edge-intersects-rect? rect [[0 -5] [0 15]])  := false
   (edge-intersects-rect? rect [[12 0] [12 10]]) := false
   (edge-intersects-rect? rect [[-5 5] [15 5]])  := true
   (edge-intersects-rect? rect [[0 10] [10 10]]) := false)

 "Point in Polygon (Ray Casting)"
 (let [square-edges  (polygon-edges [[0 0] [10 0] [10 10] [0 10]])
       concave-edges (polygon-edges [[0 0] [10 0] [10 10] [5 5] [0 10]])]

   (point-in-polygon? [5 5] square-edges)  := true
   (point-in-polygon? [15 5] square-edges) := false
   (point-in-polygon? [5 15] square-edges) := false
   (point-in-polygon? [-5 5] square-edges) := false
   (point-in-polygon? [5 2] concave-edges) := true
   (point-in-polygon? [5 8] concave-edges) := false
 ))