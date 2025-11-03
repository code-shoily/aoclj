(ns
  ^{:title      "Two-Factor Authentication",
    :doc        "Module for solving Advent of Code 2016 Day 8 problem.",
    :url        "http://www.adventofcode.com/2016/day/8",
    :difficulty :s,
    :year       2016,
    :day        8,
    :stars      2,
    :tags       [:grid :parse-heavy :visual-output]}
  aoclj.year-2016.day-08
  (:require [aoclj.helpers.io :as utils]
            [aoclj.helpers.seq :refer [transpose]]
            [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [clojure.core.match :refer [match]]
            [hyperfiddle.rcf :refer [tests]]
            [instaparse.core :as insta]))

(s/def ::cmd #{:draw :rot-row :rot-col})
(s/def ::instruction (s/tuple ::cmd number? number?))
(s/def ::instruction-set (s/coll-of ::instruction))

(def parser
  (insta/parser
   "S ::= Rect|Rot
      Rect ::= RectL T N T N
      Rot ::= RotL T By
      By ::= R | C
      R ::= 'row y=' N T N
      C ::= 'column x=' N T N
      T ::= ' ' | ' by ' | 'x'
      RectL ::= 'rect'
      RotL ::= 'rotate'
      N ::= #'\\d+'
      "))

(defn transform-numbers
  [parsed-int]
  (insta/transform {:N parse-long} parsed-int))

(defn extract-info
  [parsed-data]
  (match parsed-data
    [:S [:Rect _ _ x _ y]] [:draw x y]
    [:S [:Rot _ _ [_ [:R _ x _ y]]]] [:rot-row x y]
    [:S [:Rot _ _ [_ [:C _ x _ y]]]] [:rot-col x y]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  {:post [(s/valid? ::instruction-set %)]}
  (->> raw-input
       str/split-lines
       (map (comp extract-info
                  transform-numbers
                  parser))))

(defn make-screen
  "Create a screen that's width x height"
  [width height]
  (->> (repeat width false)
       vec
       (repeat height)
       vec))

(defn draw
  [screen width height]
  (let [coords (for [v (range height) h (range width)] [v h])]
    (reduce #(assoc-in %1 %2 true) screen coords)))

(defn rot-row
  [screen row amount]
  (let [width   (count (first screen))
        old-row (nth screen row)
        new-row (vec (map #(nth old-row (mod (- % amount) width))
                          (range width)))]
    (assoc screen row new-row)))

(defn rot-col
  [screen col amount]
  (-> screen
      transpose
      (rot-row col amount)
      transpose))

(defn transform
  [screen input]
  (match input
    [:draw x y]    (draw screen x y)
    [:rot-row x y] (rot-row screen x y)
    [:rot-col x y] (rot-col screen x y)))

(defn print-screen
  [grid]
  (->> grid
       (mapv (comp (partial str/join "")
                   (fn [row] (mapv #(if (= % true) "▊" " ") row))))
       (str/join "\n")))


(defn solve
  [raw-input]
  (let [display       (make-screen 50 6)
        input         (parse raw-input)
        final-display (reduce transform display input)
        part-1        (->> final-display
                           flatten
                           (filter identity)
                           count)
        display-data  (print-screen final-display)]
    (println display-data)
    [part-1 :👀]))

(comment
  "<Explore>"
  (def raw-input (utils/read-input-data 2016 8))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))

  "</Explore>")

(tests
 (def input (utils/read-input-data 2016 8))
 (solve input)
 :=
 [115 :👀])
