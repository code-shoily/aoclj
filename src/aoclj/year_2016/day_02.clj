(ns aoclj.year-2016.day-02
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-lines 2016 2))

(def dialpad-1
  (to-array-2d
   [[nil nil nil nil nil]
    [nil \1 \2 \3 nil]
    [nil \4 \5 \6 nil]
    [nil \7 \8 \9 nil]
    [nil nil nil nil nil]]))

(def dialpad-2
  (to-array-2d
   [[nil nil nil nil nil nil nil]
    [nil nil nil \1 nil nil nil]
    [nil nil \2 \3 \4 nil nil]
    [nil \5 \6 \7 \8 \9 nil]
    [nil nil \A \B \C nil nil]
    [nil nil nil \D nil nil nil]
    [nil nil nil nil nil nil nil]]))

(defn walk [dialpad [x y] dir]
  (let [[new-x new-y] (case dir
                        \U [(dec x) y]
                        \D [(inc x) y]
                        \L [x (dec y)]
                        \R [x (inc y)])]
    (if (nil? (aget dialpad new-x new-y))
      [x y]
      [new-x new-y])))

(defn crack [dialpad pos line]
  (reduce (partial walk dialpad) pos line))

(defn get-code [dialpad pos input]
  (pmap (comp #(aget dialpad (first %) (second %))
              #(crack dialpad pos %))
        input))

(defn solve [input]
  (let [solve-1 (get-code dialpad-1 [2 2] input)
        solve-2 (get-code dialpad-2 [3 1] input)]
    [solve-1 solve-2]))

(time (solve input))