;;;; --- Year 2017 Day 1: Inverse Captcha ---
;;;; Link: https://adventofcode.com/2017/day/1
;;;; Solution: [1089 1156]
(ns aoclj.year-2017.day-01
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-str 2017 1))

(defn parse [captcha]
  (u/to-ints (str/split captcha #"")))

(defn pair-by [by coll]
  (->> coll
       cycle
       (drop by)
       (map vector coll)))

(defn solve-captcha
  ([value captcha]
   (->> captcha
        (pair-by value)
        (filter (fn [[a b]] (= a b)))
        (map first)
        (apply +)))
  ([captcha] (solve-captcha 1 captcha)))

;; Solutions
(defn solve-1 [input] (solve-captcha input))

(defn solve-2 [input]
  (-> input
      count
      (/ 2)
      (solve-captcha input)))

(def solve (partial (comp (juxt solve-1 solve-2) parse)))

;; Run the solution
; (time (solve input))
