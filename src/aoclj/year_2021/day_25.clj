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

(defn movement
  [dir grid len]
  (->> grid
       (r/filter (fn [[_ x]] (= dir x)))
       (r/reduce (fn [acc [[v h] _]]
                   (let [h>       (rem (inc h) len)
                         v>       (rem (inc v) len)
                         next-pos (cond (= \> dir) [v h>]
                                        (= \v dir) [v> h])]
                     (if (nil? (grid next-pos))
                       (-> acc
                           (dissoc! [v h])
                           (assoc! next-pos dir))
                       acc)))
                 (transient grid))
       persistent!))

(defn simulate
  [[grid height width]]
  (loop [grid* grid
         iter  0]
    (let [after-right (movement \> grid* width)
          after-down  (movement \v after-right height)
          stopped?    (= after-down grid*)]
      (if stopped? (inc iter) (recur after-down (inc iter))))))

(defn parse
  [raw-input]
  (to-grid-map (mapv vec (str/split-lines raw-input))))

(defn solve
  [raw-input]
  (let [part-1 (-> raw-input parse simulate)]
    [part-1 :🎉]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 25))
  
  (time (solve raw-input))

  "</Explore>"
  )