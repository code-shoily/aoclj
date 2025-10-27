(ns
  ^{:title      "Crossed Wires",
    :doc        "Module for solving Advent of Code 2019 Day 3 problem.",
    :url        "http://www.adventofcode.com/2019/day/3",
    :difficulty :m,
    :year       2019,
    :day        3,
    :stars      2,
    :tags       [:geometry :grid]}
  aoclj.year-2019.day-03
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [fastmath.distance :as dist]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse-direction
  [s]
  (let [dir-map     {"L" :left, "R" :right, "U" :up, "D" :down}
        [_ dir val] (re-find #"(L|R|U|D)(\d+)" s)]
    [(dir-map dir) (parse-long val)]))

(defn get-next-state
  "Compute the next state given previous state where a state is
   a tuple of steps and line."
  [[k [_ [v h :as from]]] [dir steps]]
  [(+ k steps)
   [from
    (case dir
      :left  [v (- h steps)]
      :right [v (+ h steps)]
      :up    [(- v steps) h]
      :down  [(+ v steps) h])]])

(defn orientation
  [[_ [[v h] [v* h*]]]]
  (cond (= v v*) :horizontal
        (= h h*) :vertical))

(defn categorized-lines
  [wire]
  (->> wire
       (reductions get-next-state [0 [[0 0] [0 0]]])
       (drop 1)
       (reduce (fn [acc wire-seg]
                 (update acc (orientation wire-seg) conj wire-seg))
               {:horizontal [], :vertical []})))

(defn parse-wires
  [[wire-1 wire-2]]
  [(categorized-lines wire-1) (categorized-lines wire-2)])

(defn parse
  "Each wire is comprised of a collection of vertical line segments and
   a collection of horizontal line segments. Each line segment is a tuple 
   of [steps [p1, p2]] where steps is the steps wiring would have crossed
   WHEN IT REACHED p2 (it's end state, nor init)"
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv (comp (partial map parse-direction) #(str/split % #",")))
       parse-wires))

(defn within?
  "Checks if [a,b]....[x,y]..[a*, b*]"
  [[[a b] [a* b*]] [x y]]
  (cond (= a a*) (or (<= b y b*) (<= b* y b))
        (= b b*) (or (<= a x a*) (<= a* x a))))

(defn find-intersections
  "Finds the point that is the intersection between `line-1` and
   `line-2`. If they don't intersect, then `nil` is returned. 
   For the purpose of this problem, one line must be vertical
   and the other horizontal (see pre-check)"
  [[[a b] [a* b*] :as line-1] [[x y] [x* y*] :as line-2]]
  {:pre [(cond (= a a*) (= y y*)
               (= b b*) (= x x*))]}
  (let [common (cond
                 (= a a*) [a y]
                 (= b b*) [x b])]
    (when (and (within? line-1 common) (within? line-2 common)) common)))

(defn pairs
  "Returns all possible pairs between two sets of lines (of different orientations)"
  [lines-1 lines-2]
  (for [i lines-1 j lines-2] [i j]))

(defn all-pairs
  "Given two paths of two wires, finds all pairs of wire segments drawn by
   them - in polarized orientation (i.e h x v and v x h)"
  [wiring-1 wiring-2]
  (concat (pairs (:vertical wiring-1) (:horizontal wiring-2))
          (pairs (:horizontal wiring-1) (:vertical wiring-2))))

(defn part-1
  [[wire-1 wire-2]]
  (->> (all-pairs wire-1 wire-2)
       (keep (fn [[[_ line-1] [_ line-2]]] (find-intersections line-1 line-2)))
       (map (partial dist/manhattan [0 0]))
       (apply min)
       long))

(defn get-steps-to-point
  "If [a,b].......[x,y]...[a*,b*]<step-at-end> then what was the
   steps when [x,y] was reached?"
  [[[a b] [a* b*]] step-at-end [x y]]
  (- step-at-end
     (cond (= a a*) (abs (- b* y))
           (= b b*) (abs (- a* x)))))

(defn part-2
  [[wire-1 wire-2]]
  (->> (all-pairs wire-1 wire-2)
       (keep (fn [[[step-1 line-1] [step-2 line-2]]]
               (when-let [[x y] (find-intersections line-1 line-2)]
                 (+ (get-steps-to-point line-1 step-1 [x y])
                    (get-steps-to-point line-2 step-2 [x y])))))
       (apply min)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2019 3))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2019 3) := [1195 91518]))