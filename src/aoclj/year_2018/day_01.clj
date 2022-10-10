(ns aoclj.year-2018.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2018 1))

(defn add-freqs [[freqs acc] x]
  (let [frequency (+ acc x)]
    (if (contains? freqs frequency)
      (reduced frequency)
      [(conj freqs frequency) frequency])))

(defn solve-1 [input] (apply + input))

(defn solve-2 [input]
  (->> input
       cycle
       (reduce add-freqs [#{} 0])))

(defn solve [input]
  {1 (solve-1 input) 2 (solve-2 input)})

(time (solve input))
