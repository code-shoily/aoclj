(ns
  ^{:title      "Sea Cucumber",
    :doc        "Module for solving Advent of Code 2021 Day 25 problem.",
    :url        "http://www.adventofcode.com/2021/day/25",
    :difficulty :todo,
    :year       2021,
    :day        25,
    :stars      0,
    :tags       []}
  aoclj.year-2021.day-25
  (:require [aoclj.utils :as utils]
            [clojure.math.combinatorics :refer [cartesian-product]]
            [clojure.string :as str]))

(defn all-coords
  "Returns all coords in a given rectangle of width x height."
  [height width]
  (into [] (cartesian-product (range height) (range width))))

(defn- do-move!
  [type ^chars grid height width [v h]]
  (let [arr-inc  (fn [val lim] (mod (inc val) lim))
        cucumber (aget grid v h)
        ok?      (= type cucumber)]
    (when ok?
      (let [[v* h*] (case type
                      \> [v (arr-inc h width)]
                      \v [(arr-inc v height) h])]
        (when (= \. (aget grid v* h*))
          (aset grid v* h* cucumber)
          (aset grid v h \.))))))

(def maybe-move-> (partial do-move! \>))

(def maybe-move-v (partial do-move! \v))

(defn parse
  [raw-input]
  (let [grid   (->> (str/split-lines raw-input)
                    (mapv #(mapv identity %))
                    to-array-2d)
        height (alength grid)
        width  (alength (aget grid 0))]
    [grid height width]))


(defn part-1
  "Solve part 1 -"
  [input]
  (->> input
       count))

(defn part-2
  "Solve part 2 -"
  [input]
  (->> input
       count))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 25))

  (def input (parse raw-input))
  
  (defn simulate!
    [[^chars grid height width]]
    (let [mutations (atom Integer/MAX_VALUE)
          step (atom 0)
          coords (all-coords height width)]
      (while (not= 0 @mutations)
        (reset! mutations 0)
        (doseq [r coords]
          (when (= \. (maybe-move-> grid height width r))
            (swap! mutations inc)))
        (doseq [r coords]
          (when (= \. (maybe-move-v grid height width r))
            (swap! mutations inc)))
        (swap! step inc)
        (println [@mutations @step]))))
  
  (simulate! input)
  (time (solve raw-input))
  "</Explore>"
  )