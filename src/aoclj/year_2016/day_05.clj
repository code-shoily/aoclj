(ns ^{:title      "How About a Nice Game of Chess?",
      :doc        "Module for solving Advent of Code 2016 Day 5 problem.",
      :url        "http://www.adventofcode.com/2016/day/5",
      :difficulty :m,
      :year       2016,
      :day        5,
      :stars      2,
      :tags       [:slow :md5]}
    aoclj.year-2016.day-05
  (:require [aoclj.algorithms.hash :as hash]
            [aoclj.utils :as utils]
            [clojure.string :as str]
            [medley.core :as m]))

(def parse str/trim)

(defn find-password-1
  [code]
  (transduce (comp (map #(hash/md5 (str code %)))
                   (filter #(= "00000" (subs % 0 5)))
                   (map #(nth % 5))
                   (take 8))
             conj
             []
             (range)))

(defn find-password-2
  [code]
  (transduce (comp (map #(hash/md5 (str code %)))
                   (filter #(and (= "00000" (subs % 0 5))
                                 (> 8 (Integer/parseInt (subs % 5 6) 16))))
                   (map (juxt #(parse-long (str (nth % 5))) #(nth % 6)))
                   (m/distinct-by first)
                   (take 8))
             conj
             []
             (range)))

(defn result-1 [x] (apply str x))

(defn result-2 [x] (apply str (map second (sort-by first x))))

(defn part-1
  [input]
  (->> input
       find-password-1
       result-1))

(defn part-2
  [input]
  (->> input
       find-password-2
       result-2))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2016 5))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")