(ns
  ^{:title      "Cube Conundrum",
    :doc        "Module for solving Advent of Code 2023 Day 2 problem.",
    :url        "http://www.adventofcode.com/2023/day/2",
    :difficulty :s,
    :year       2023,
    :day        2,
    :stars      2,
    :tags       [:parse-heavy]}
  aoclj.year-2023.day-02
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]
            [instaparse.core :as insta]))

(def parser
  (insta/parser
   "S ::= 'Game ' ID ': ' Rest
    ID ::= Num
    Rest ::= (Combs '; '?)+
    Combs ::= (Comb ', '?)+
    Comb ::= Num ' ' Color
    Color ::= 'red' | 'green' | 'blue'
    Num ::= #'\\d+';"))

(defn transform
  [parse-tree]
  (->> parse-tree
       (insta/transform {:Num   parse-long,
                         :Color keyword,
                         :Combs (fn ([[_ cnt _ col] & _] [col cnt])),
                         :Rest  (fn [& tokens]
                                  (->> tokens
                                       (remove nil?)
                                       (partition-by #(= % "; "))
                                       (remove #(= "; " (first %)))
                                       (mapv #(reduce conj {} %)))),
                         :S     (fn [_ id _ rest] [(second id) rest])})))

(defn parse
  [raw-input]
  (->> (str/split-lines raw-input)
       (map (comp transform parser))))

(defn winning-pick?
  [{:keys [red green blue], :or {red 0, green 0, blue 0}}]
  (and (<= red 12)
       (<= green 13)
       (<= blue 14)))

(defn wins? [[_ picks]] (every? winning-pick? picks))

(defn part-1
  "Solve part 1 - Game IDs of the winning picks"
  [input]
  (->> input
       (keep #(when (wins? %1) (first %1)))
       (reduce +)))

(defn power
  "Power of the game = Product of minimum RGBs in back that makes it win"
  [[_ picks]]
  (->> picks
       (reduce (fn [{:keys [red green blue]}
                    {red-x   :red,
                     green-x :green,
                     blue-x  :blue,
                     :or     {red-x 0, green-x 0, blue-x 0}}]
                 {:red   (max red red-x),
                  :green (max green green-x),
                  :blue  (max blue blue-x)})
               {:red 0, :green 0, :blue 0})
       vals
       (apply *)))

(defn part-2
  "Solve part 2 - Power/Game"
  [input]
  (->> input
       (map power)
       (reduce +)))

(def solve (io/generic-solver part-1 part-2 parse))

(tests
 (def input-data (io/read-input-data 2023 2))
 (solve input-data)
 :=
 [2085 79315])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2023 2))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")
