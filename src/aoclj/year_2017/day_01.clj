(ns aoclj.year-2017.day-01
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-str 2017 1))

(defn parse [captcha]
  (mapv #(Integer/parseInt %)
        (str/split captcha #"")))

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

(defn solve-1 [input]
  (->> input parse solve-captcha))

(defn solve-2 [input]
  (let [parsed-input (parse input)
        pairing-size (/ (count parsed-input) 2)]
    (solve-captcha pairing-size parsed-input)))

(defn solve [input]
  {1 (solve-1 input) 2 (solve-2 input)})

(time (solve input))