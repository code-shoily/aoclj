(ns
  ^{:title      "No Matter How You Slice It",
    :doc        "Module for solving Advent of Code 2018 Day 3 problem.",
    :url        "http://www.adventofcode.com/2018/day/3",
    :difficulty :s,
    :year       2018,
    :day        3,
    :stars      2,
    :tags       [:brute-force :range-math :grid]}
  aoclj.year-2018.day-03
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse-fabric
  [line]
  (let [[_ id x y w h] (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" line)]
    (mapv parse-long [id x y w h])))

(defn parse
  [raw-input]
  (map parse-fabric (str/split-lines raw-input)))

(defn square-inches-of
  "Returns all squares owned by the fabric passed"
  [[_ x y w h]]
  (for [i (range x (+ x w))
        j (range y (+ y h))]
    [i j]))

(defn part-1
  [input]
  (->> input
       (reduce (fn [m fabric]
                 (->> (square-inches-of fabric)
                      (reduce #(update %1 %2 (fnil inc 0)) m)))
               {})
       (filter (fn [[_ v]] (> v 1)))
       count))

(defn overlaps?
  "Do two fabrics have overlap?"
  [[_ x1 y1 w1 h1] [_ x2 y2 w2 h2]]
  (and
   (if (<= x1 x2) (<= x2 (+ x1 w1)) (<= x1 (+ x2 w2)))
   (if (<= y1 y2) (<= y2 (+ y1 h1)) (<= y1 (+ y2 h2)))))

(defn part-2
  [input]
  (loop [[[id & _ :as x] & rst-x :as xs] input
         [y & rst-y :as ys] input]
    (cond
      (empty? ys) id
      (= x y)     (recur xs rst-y)
      :else       (if (overlaps? x y)
                    (recur rst-x input)
                    (recur xs rst-y)))))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2018 3))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (io/read-input-data 2018 3))
 :=
 [110389 552])
