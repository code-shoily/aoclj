(ns ^{:title "Internet Protocol Version 7",
      :doc "Module for solving Advent of Code 2016 Day 7 problem.",
      :url "http://www.adventofcode.com/2016/day/7",
      :difficulty :s,
      :year 2016,
      :day 7,
      :stars 2,
      :tags [:set :partition]}
    aoclj.year-2016.day-07
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [clojure.set :as set]))

(defrecord IP [super hyper])
(defn parse-ip
  [ip]
  (->> (str/split ip #"\[|\]")
       ((fn [[_ & xs :as ipseq]]
          (->IP (vec (take-nth 2 ipseq)) (vec (take-nth 2 xs)))))))

(defn parse
  [input]
  (->> input
       str/split-lines
       (map parse-ip)))

(defn abba?
  [sequence]
  (->> sequence
       (partitionv 4 1)
       (some (fn [[a b c d]] (and (= a d) (= b c) (not= a b))))))

(defn tls?
  [{:keys [super hyper]}]
  (and (->> super
            (some abba?))
       (->> hyper
            (not-any? abba?))))

(defn bab? [[a b c]] (and (= a c) (not= a b)))

(defn get-babs
  [coll]
  (->> coll
       (mapcat (comp #(filter bab? %) #(partition 3 1 %)))))

(defn invert [[a b _]] [b a b])

(defn ssl?
  [{:keys [super hyper]}]
  (let [babs (set (map invert (get-babs super)))
        abas (set (get-babs hyper))]
    ((complement empty?) (set/intersection babs abas))))

(defn part-1
  [input]
  (->> input
       (filter tls?)
       count))

(defn part-2
  [input]
  (->> input
       (filter ssl?)
       count))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2016 7))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")