(ns ^{:title      "Perfectly Spherical Houses in a Vacuum",
      :doc        "Module for solving Advent of Code 2015 Day 3 problem.",
      :url        "http://www.adventofcode.com/2015/day/3",
      :difficulty :xs,
      :year       2015,
      :day        3,
      :stars      2,
      :tags       [:set]}
    aoclj.year-2015.day-03
  (:require [aoclj.helpers.io :as io]
            [clojure.set :as set]
            [hyperfiddle.rcf :as rcf :refer [tests]]))

(def parse seq)

(defrecord House [pos visits])

(defn next-pos
  [[x y] dir]
  (case dir
    \^ [x (inc y)]
    \v [x (dec y)]
    \< [(dec x) y]
    \> [(inc x) y]))

(defn deliver-presents
  "Returns the state of the present delivery after all houses were
   visited following dirs"
  [dirs]
  (->> dirs
       (reduce (fn [{:keys [pos visits]} dir]
                 (let [new-pos (next-pos pos dir)]
                   (->House new-pos (conj visits new-pos))))
               (->House [0 0] #{[0 0]}))))

(defn part-1
  [input]
  (->> input
       deliver-presents
       :visits
       count))

(defn divide-directions
  "Given [santa robot santa robot santa robot ...] this groups
   instructions for santa and robot as separate sequences"
  [dirs]
  [(take-nth 2 dirs) (take-nth 2 (rest dirs))])

(defn part-2
  [input]
  (let [[santa robot] (divide-directions input)
        [{santa-visits :visits} {robo-visits :visits}]
        [(deliver-presents santa) (deliver-presents robot)]]
    (count (set/union santa-visits robo-visits))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input (io/read-input-data 2015 3))
  (def input (parse raw-input))
  (time (solve raw-input))
  "</Explore>")

(tests
 (def input (io/read-input-data 2015 3))
 (solve input)
 :=
 [2081 2341])
