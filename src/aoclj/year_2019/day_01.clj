(ns aoclj.year-2019.day-01
  (:require [aoclj.common.reader :as reader]))

(def input (reader/get-input-ints 2019 1))

(defn get-fuel [mass] (-> mass (/ 3) int (- 2)))

(defn get-fuel-corrected [mass]
  (loop [mass mass fuels []]
    (let [new-mass (get-fuel mass)]
      (if (<= new-mass 0)
        (apply + fuels)
        (recur new-mass (conj fuels new-mass))))))

(defn solver [fun input] (->> input (map fun) (apply +)))

;; Solutions
(defn solve-1 [input] (solver get-fuel input))
(defn solve-2 [input] (solver get-fuel-corrected input))
(defn solve [input] ((juxt solve-1 solve-2) input))

;; Run the solution
(time (solve input))
