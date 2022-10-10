(ns aoclj.year-2015.day-02
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2015 2))

(defn parse-dims [dim]
  (as-> dim $
    (str/split $ #"x")
    (mapv #(Integer/parseInt %) $)
    (sort $)))

(defn parse [input] (map parse-dims input))

(defn present [[a b c]]
  (let [smallest-area (* a b)
        surface (* 2 (+ (* a b) (* b c) (* c a)))]
    (+ smallest-area surface)))

(defn ribbon [[a b c]]
  (let [smallest-perimeter (+ (* a 2) (* b 2))
        volume (* a b c)]
    (+ smallest-perimeter volume)))

(defn solution-template [fun input] (->> input (map fun) (apply +)))

(defn solve [input]
  (let [input (parse input)
        solve-1 (partial solution-template present)
        solve-2 (partial solution-template ribbon)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))
