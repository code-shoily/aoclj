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
            [fastmath.distance :as dist]
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

(def rot-mapping
  {:north {:right [:east :south :west], :left [:west :south :east]},
   :south {:right [:west :north :east], :left [:east :north :west]},
   :east  {:right [:south :west :north], :left [:north :west :south]},
   :west  {:right [:north :east :south], :left [:south :east :north]}})

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

;; --------------------------------------- SHIP MOVEMENTS

(defn ship-rotate
  ([[facing _] [dir deg]]
   (get-in rot-mapping [facing dir ({90 0, 180 1, 270 2} deg)])))

(defn ship-move
  [[_ [v h]] [dir amt]]
  (case dir
    :north [(+ v amt) h]
    :east  [v (+ h amt)]
    :south [(- v amt) h]
    :west  [v (- h amt)]))

(defn ship-forward
  [[facing _ :as ship] [_ amt]]
  (ship-move ship [facing amt]))

(defn part-1
  "Solve without Waypoint"
  [input]
  (->> input
       (reduce (fn [[facing pos :as ship] cmd]
                 (case (first cmd)
                   (:left :right) [(ship-rotate ship cmd) pos]
                   :forward       [facing (ship-forward ship cmd)]
                   [facing (ship-move ship cmd)]))
               [:east [0 0]])
       second
       (dist/manhattan [0 0])
       long))

;; --------------------------------------- WAYPOINT MOVEMENTS

(defn waypoint-rotate
  [[ship [wv wh] :as pos] [dir deg]]
  (if (zero? deg)
    pos
    (let [waypoint (case dir
                     :right [(* -1 wh) wv]
                     :left  [wh (* -1 wv)])]
      (recur [ship waypoint] [dir (- deg 90)]))))

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

(defn part-2
  "Solve with Waypoint"
  [input]
  (->> input
       (reduce (fn [pos cmd]
                 ((case (first cmd)
                    (:left :right) waypoint-rotate
                    :forward       waypoint-forward
                    waypoint-move)
                  pos
                  cmd))
               [[0 0] [1 10]])
       first
       (dist/manhattan [0 0])
       long))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2020 12))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")