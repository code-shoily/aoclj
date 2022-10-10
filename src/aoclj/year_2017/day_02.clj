;;;; --- Year 2017 Day 2: Corruption Checksum ---
;;;; Link: https://adventofcode.com/2017/day/2
;;;; Solution: [32020 236]
(ns aoclj.year-2017.day-02
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2017 2))

(defn parse [input]
  (mapv (comp u/to-ints #(str/split % #"\t"))
        input))

(defn divides-each-other [xs]
  (first (let [xs (sort xs)
               lim (count xs)]
           (for [i (range lim)
                 j (range (inc i) lim)
                 :let [big (nth xs j) small (nth xs i)]
                 :when (and (not= big 0) (not= small 0)
                            (zero? (mod big small)))]
             (quot big small)))))

;; Solutions
(defn solve-1 [input]
  (->> input
       (map (comp #(apply - %)
                  (juxt last first)
                  sort))
       (apply +)))

(defn solve-2 [input]
  (->> input
       (map divides-each-other)
       (apply +)))

(def solve (partial (comp (juxt solve-1 solve-2) parse)))

;; Run the solution
; (time (solve input))

