;;;; --- Year 2021 Day 3: Binary Diagnostic ---
;;;; Link: https://adventofcode.com/2021/day/3
;;;; Solutions: [1540244 4203981]
(ns aoclj.year-2021.day-03
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2021 3))

(defn parse [input] (map vec input))

(defn freq [direction xs]
  (if (direction (get xs \1 0)
                 (get xs \0 0)) \1 \0))

(defn to-num [num]
  (as-> num $
    (str/join $)
    (Long/parseLong $ 2)))

(defn compute [direction input]
  (->> input
       (mapv #(direction (frequencies %)))
       to-num))

(def gamma (partial compute (partial freq >)))
(def epsilon (partial compute (partial freq <)))
(defn power-consumption [input] (* (gamma input) (epsilon input)))

(defn cmp [when-0 when-1 default freq]
  (cond
    (when-0 (get freq \1 0) (get freq \0 0)) \0
    (when-1 (get freq \1 0) (get freq \0 0)) \1
    :else default))

(defn histogram [cmp-fn input]
  (->> input
       u/transpose
       (mapv #(cmp-fn (frequencies %)))
       (map-indexed (fn [a b] [a b]))
       (into {})))

(defn o2-hist [data]  (histogram (partial cmp < > \1) data))
(defn co2-hist [data] (histogram (partial cmp > < \0) data))

(defn keep-only [idx hist input]
  (filterv #(= (get % idx)
               (hist idx))
           input))

(defn get-gas [hist-fn input]
  (reduce
   (fn [[data hist] idx]
     (if (= 1 (count data))
       (reduced (to-num (first data)))
       (let [new-data (keep-only idx hist data)
             new-hist (hist-fn new-data)]
         [new-data new-hist])))
   [input (hist-fn input)]
   (cycle (range 12))))

(def get-o2 (partial get-gas o2-hist))
(def get-co2 (partial get-gas co2-hist))

;; Solutions
(defn solve-1 [input]
  (->> input
       u/transpose
       power-consumption))

(defn solve-2 [input]
  (apply * ((juxt get-o2 get-co2) input)))

(def solve (partial (comp (juxt solve-1 solve-2) parse)))

;; Run the solutions
; (time (solve input))