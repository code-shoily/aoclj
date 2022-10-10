(ns aoclj.year-2020.day-02
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2020 2))

(defn parse-policy [policy]
  (let [[pos char chars] (str/split policy #"\s")
        [x y] (map #(Integer/parseInt %)
                   (str/split pos #"-"))
        char (first char)]
    [x y char chars]))

(defn parse [input]
  (map parse-policy input))

(defn solve-1 [input]
  (letfn [(password-valid? [[min max char chars]]
            (let [freqs (frequencies chars)
                  freq (freqs char 0)]
              (<= min freq max)))]
    (->> input
         (filterv password-valid?)
         count)))

(defn solve-2 [input]
  (letfn [(password-valid? [[x1 x2 char chars]]
            (let [x1-char (nth chars (dec x1))
                  x2-char (nth chars (dec x2))]
              (and (not= x1-char x2-char)
                   (or (= char x1-char)
                       (= char x2-char)))))]
    (->> input
         (filterv password-valid?)
         count)))

(defn solve [input]
  (let [input (parse input)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))
