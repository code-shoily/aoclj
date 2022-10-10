(ns aoclj.year-2018.day-02
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-lines 2018 2))

(defn get-repetitions [map]
  (as-> (vals map) $
    (apply hash-set $)
    (mapv #(quot (get $ % 0) %) [2 3])))

(defn solve-1 [input]
  (->> input
       (mapv (comp get-repetitions frequencies vec))
       (reduce (fn [[a b] [a' b']]
                 [(+ a a') (+ b b')])
               [0 0])
       (apply *)))

(defn delete-at [^String s idx]
  [idx (str (doto (StringBuilder. s)
              (.deleteCharAt idx)))])

(defn without-one-char [^String s]
  (mapv #(delete-at s %) (range (.length s))))

(defn solve-2 [input]
  (letfn [(extract [[[[_ result] _] _]] result)
          (is-twice [[_ b]] (= b 2))]
    (->> input
         (mapcat without-one-char)
         frequencies
         (filter is-twice)
         extract)))

(defn solve [input]
  {1 (solve-1 input) 2 (solve-2 input)})

(time (solve input))
