(ns
  ^{:title      "Rain Risk",
    :doc        "Module for solving Advent of Code 2020 Day 12 problem.",
    :url        "http://www.adventofcode.com/2020/day/12",
    :difficulty :s,
    :year       2020,
    :day        12,
    :stars      2,
    :tags       [:navigation]}
  aoclj.year-2020.day-12
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

;; ---------------------------------------- DIR RELATIONS
(def dir-mapping
  {"N" :north,
   "S" :south,
   "E" :east,
   "W" :west,
   "F" :forward,
   "L" :left,
   "R" :right})

(def rotations
  {:north {:right [:east :south :west], :left [:west :south :east]},
   :south {:right [:west :north :east], :left [:east :north :west]},
   :east  {:right [:south :west :north], :left [:north :west :south]},
   :west  {:right [:north :east :south], :left [:south :east :north]}})

;; --------------------------------------- SHIP MOVEMENTS
(defn ship-rot
  ([[facing _] [direction degrees]]
   (get-in rotations [facing direction ({90 0, 180 1, 270 2} degrees)])))

(defn ship-move
  [[_ [v h]] [direction amt]]
  (case direction
    :north [(+ v amt) h]
    :east  [v (+ h amt)]
    :south [(- v amt) h]
    :west  [v (- h amt)]))

(defn ship-forward
  [[facing _ :as cur] [_ amt]]
  (ship-move cur [facing amt]))

;; --------------------------------------- WAYPOINT MOVEMENTS
(defn waypoint-rotate
  [[ship [wv wh] :as pos] [dir deg]]
  (if (zero? deg)
    pos
    (let [next-pos (case dir
                     :right [(* -1 wh) wv]
                     :left  [wh (* -1 wv)])]
      (recur [ship next-pos] [dir (- deg 90)]))))

(defn waypoint-forward
  [[[sv sh] [wv wh :as waypoint]] [_ amt]]
  (let [[dv dh] [(* wv amt) (* wh amt)]]
    [[(+ sv dv) (+ sh dh)] waypoint]))

(defn waypoint-move
  [[ship [wv wh]] [dir amt]]
  (case dir
    :north [ship [(+ wv amt) wh]]
    :south [ship [(- wv amt) wh]]
    :east  [ship [wv (+ wh amt)]]
    :west  [ship [wv (- wh amt)]]))

;; --------------------------------------- PARSING
(defn parse-direction
  [s]
  (let [[ins amt] (str/split s #"" 2)]
    [(dir-mapping ins) (parse-long amt)]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv parse-direction)))

(defn manhattan-distance [[a b]] (+ (abs a) (abs b)))

(defn part-1
  "Solve without Waypoint"
  [input]
  (->> input
       (reduce (fn [[facing pos :as cur] [dir _ :as ins]]
                 (case dir
                   (:left :right) [(ship-rot cur ins) pos]
                   :forward       [facing (ship-forward cur ins)]
                   [facing (ship-move cur ins)]))
               [:east [0 0]])
       second
       manhattan-distance))

(defn part-2
  "Solve with Waypoint"
  [input]
  (->> input
       (reduce (fn [cur ins]
                 (case (first ins)
                   (:left :right) (waypoint-rotate cur ins)
                   :forward       (waypoint-forward cur ins)
                   (waypoint-move cur ins)))
               [[0 0] [1 10]])
       first
       manhattan-distance))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2020 12))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")