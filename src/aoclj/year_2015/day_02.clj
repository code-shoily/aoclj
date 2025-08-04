(ns ^{:title      "I Was Told There Would Be No Math",
      :doc        "Module for solving Advent of Code 2015 Day 2 problem.",
      :url        "http://www.adventofcode.com/2015/day/2",
      :year       2015,
      :day        2,
      :difficulty :xs,
      :stars      2,
      :tags       [:geometry]}
    aoclj.year-2015.day-02
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn parse
  [input]
  (letfn [(extract-dims [s] (mapv Integer/parseInt (str/split s #"x")))]
    (->> (str/split-lines input)
         (mapv extract-dims))))

(defn area-calculator
  [f g input]
  (->> input
       (mapv (comp (partial apply +) (juxt f g)))
       (reduce +)))

(defn part-1
  [input]
  (letfn [(wrapping-paper [[x y z]] (* 2 (+ (* x y) (* y z) (* z x))))
          (slack [dims]
            (->> dims
                 sort
                 (take 2)
                 (apply *)))]
    (area-calculator wrapping-paper slack input)))

(defn part-2
  [input]
  (letfn [(bow-ribbon [dims] (apply * dims))
          (present-ribbon [dims]
            (->> dims
                 sort
                 (take 2)
                 (apply +)
                 (* 2)))]
    (area-calculator bow-ribbon present-ribbon input)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2015 2))
  (def input (parse input-data))
  input
  (time (solve input-data))
  "</Explore>")
