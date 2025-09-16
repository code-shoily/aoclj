(ns
  ^{:title      "Sea Cucumber",
    :doc        "Module for solving Advent of Code 2021 Day 25 problem.",
    :url        "http://www.adventofcode.com/2021/day/25",
    :difficulty :m,
    :year       2021,
    :day        25,
    :stars      2,
    :tags       [:simulation :slow]}
  aoclj.year-2021.day-25
  (:require [aoclj.utils :as utils]
            [clojure.core.reducers :as r]
            [clojure.math.combinatorics :refer [cartesian-product]]
            [clojure.string :as str]))

(defn to-grid-map
  [cucumbers]
  (let [height    (count cucumbers)
        width     (count (first cucumbers))
        cucumbers (->> (cartesian-product (range height) (range width))
                       (keep (fn [coords]
                               (let [cucumber (get-in cucumbers coords)]
                                 (when-not (= \. cucumber)
                                   [(vec coords) cucumber]))))
                       (into {}))]
    [cucumbers height width]))

(defn move-right
  [cucumbers width]
  (->> cucumbers
       (r/filter (fn [[_ cucumber]] (= \> cucumber)))
       (r/reduce (fn [acc [[v h] _]]
                   (let [next-h (rem (inc h) width)]
                     (if-not (cucumbers [v next-h])
                       (-> acc
                           (dissoc! [v h])
                           (assoc! [v next-h] \>))
                       acc)))
                 (transient cucumbers))
       persistent!))

(defn move-down
  [cucumbers height]
  (->> cucumbers
       (r/filter (fn [[_ cucumber]] (= \v cucumber)))
       (r/reduce (fn [acc [[v h] _]]
                   (let [next-v (rem (inc v) height)]
                     (if-not (cucumbers [next-v h])
                       (-> acc
                           (dissoc! [v h])
                           (assoc! [next-v h] \v))
                       acc)))
                 (transient cucumbers))
       persistent!))

(defn simulate
  [[grid height width]]
  (loop [grid* grid
         iter  0]
    (let [after-right (move-right grid* width)
          after-down  (move-down after-right height)
          stopped?    (= after-down grid*)]
      (if stopped? (inc iter) (recur after-down (inc iter))))))

(defn parse
  [raw-input]
  (->> (str/split-lines raw-input)
       (mapv #(mapv identity %))
       to-grid-map))

(defn solve
  [raw-input]
  (let [input (parse raw-input)]
    [(simulate input) :🎉]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 25))

  (time (solve raw-input))

  "</Explore>"
)