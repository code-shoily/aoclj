(ns aoclj.year-2016.day-01
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-str 2016 1))

(defn- parse-instruction [instruction]
  ((juxt first
         #(->> %
               rest
               (str/join "")
               Integer/parseInt))
   instruction))

(defn- change-face [face towards]
  (condp = #{face towards}
    #{:north \L} :west
    #{:north \R} :east
    #{:south \L} :east
    #{:south \R} :west
    #{:east \L} :north
    #{:east \R} :south
    #{:west \L} :south
    #{:west \R} :north))

(defn- flatten-dirs [face value]
  (->> [face 1]
       (repeat value)
       (into [])))

(defn- direction [[face history] [towards value]]
  (let [new-face (change-face face towards)]
    [new-face (conj history (flatten-dirs new-face value))]))

(defn- directions [dirs]
  (->> dirs
       (reduce direction [:north []])
       second))

(defn parse [content]
  (as-> content $
    (str/split $ #", ")
    (map parse-instruction $)
    (directions $)
    (mapcat identity $)))

(defn step [[x y] [dir steps]]
  (case dir
    :north [x (+ y steps)]
    :south [x (- y steps)]
    :east [(+ x steps) y]
    :west [(- x steps) y]))

(defn walk [dirs]
  (reduce step [0 0] dirs))

(defn distance [[x y]] (+ (abs x) (abs y)))

(defn solve-1 [input]
  (->> input walk distance))

(defn visit [[origin visits] dir]
  (let [new-origin (step origin dir)]
    (if (contains? visits new-origin)
      (reduced new-origin)
      [new-origin (conj visits new-origin)])))

(defn revisits [dirs]
  (reduce visit [[0 0] #{}] dirs))

(defn solve-2 [input]
  (->> input revisits distance))

(defn solve
  [input]
  (let [input (parse input)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))
