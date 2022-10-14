;; --- Year 2016 Day 6: Signals and Noise ---
;; Link: https://adventofcode.com/2016/day/6
;; Solution: ["qzedlxso" "ucmifjae"]
(ns aoclj.year-2016.day-06
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2016 6))
(defn parse [input] (map frequencies (u/transpose input)))

(defn max-min [freq-map]
  (->> freq-map
       (reduce-kv (fn [[[max max-char] [min min-char]] k v]
                    (let [[max-v max-c] (if (> v max) [v k] [max max-char])
                          [min-v min-c] (if (< v min) [v k] [min min-char])]
                      [[max-v max-c] [min-v min-c]]))
                  [[Integer/MIN_VALUE nil] [Integer/MAX_VALUE nil]])
       ((juxt #(get-in % [0 1]) #(get-in % [1 1])))))

(defn solution-template [cmd input]
  (str/join (map (comp cmd max-min) input)))

;; Solutions
(def solve-1 (partial solution-template first))
(def solve-2 (partial solution-template second))
(def solve (comp (juxt solve-1 solve-2) parse))

;; Run the solution
; (time (solve input))

