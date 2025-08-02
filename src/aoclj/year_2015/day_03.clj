(ns
 ^{:title "Perfectly Spherical Houses in a Vacuum"
   :doc "Module for solving Advent of Code 2015 Day 3 problem."
   :url "http://www.adventofcode.com/2015/day/3"
   :difficulty :xs
   :year 2015
   :day 3
   :stars 2
   :tags [:set]}
 aoclj.year-2015.day-03
  (:require [aoclj.utils :as utils]
            [clojure.set :as set]))

(defn parse [input] (seq input))

(defrecord House [pos visits])

(defn next-pos [[x y] dir]
  (case dir
    \^ [x (inc y)]
    \v [x (dec y)]
    \< [(dec x) y]
    \> [(inc x) y]))

(defn deliver-presents
  [dirs]
  (->> dirs
       (reduce (fn [{:keys [pos visits]} dir]
                 (let [new-pos (next-pos pos dir)]
                   (->House new-pos (conj visits new-pos))))
               (->House [0 0] #{[0 0]}))))

(defn count-visited [{:keys [visits]}] (count visits))

(defn part-1
  [input]
  (->> input deliver-presents count-visited))

(defn divide-directions
  [dirs]
  [(take-nth 2 dirs) (take-nth 2 (rest dirs))])

(defn part-2
  [input]
  (let [[santa robot]
        (divide-directions input)
        [{santa-visits :visits} {robo-visits :visits}]
        [(deliver-presents santa) (deliver-presents robot)]]
    (count (set/union santa-visits robo-visits))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment "<Explore>"
         (def input-data
           (utils/read-input-data 2015 3))

         (def input (parse input-data))

         (time (solve input-data))
         "</Explore>")