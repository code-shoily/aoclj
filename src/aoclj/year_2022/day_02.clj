(ns
  ^{:title      "Rock Paper Scissors",
    :doc        "Module for solving Advent of Code 2022 Day 2 problem.",
    :url        "http://www.adventofcode.com/2022/day/2",
    :difficulty :xs,
    :year       2022,
    :day        2,
    :stars      2,
    :tags       [:tabular]}
  aoclj.year-2022.day-02
  (:require [aoclj.helpers.io :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> raw-input
       str/split-lines
       (map #(str/split % #" "))))

(defn opponent->me
  "Translation where left (opponent) and right (me) are both selections 
   of #{rock paper scissor}"
  [char]
  (case char
    ("A" "X") :rock
    ("B" "Y") :paper
    ("C" "Z") :scissor))

(defn opponent->outcome
  "Translation where left is what opponents picked but right is what the 
   desired outcome should be"
  [char]
  (case char
    ("A" "B" "C") (opponent->me char)
    "X"           :lose
    "Y"           :draw
    "Z"           :win))

(defn score-for
  "There is score to receive both when a selection is made, and when
   an outcome is reached"
  [value]
  (case value
    :rock    1
    :paper   2
    :scissor 3
    :win     6
    :draw    3
    :lose    0))

(defn selection-strategy
  "Strategy where the guide involves both party selection based system"
  [opponent me]
  (case [opponent me]
    [:rock :rock]       :draw
    [:rock :paper]      :win
    [:rock :scissor]    :lose
    [:paper :rock]      :lose
    [:paper :paper]     :draw
    [:paper :scissor]   :win
    [:scissor :rock]    :win
    [:scissor :paper]   :lose
    [:scissor :scissor] :draw))

(defn outcome-strategy
  "Strategy when the guide involves opponent picks vs optimal outcome based
   system"
  [opponent me]
  (case [opponent me]
    [:rock :draw]    :rock
    [:rock :win]     :paper
    [:rock :lose]    :scissor
    [:paper :lose]   :rock
    [:paper :draw]   :paper
    [:paper :win]    :scissor
    [:scissor :win]  :rock
    [:scissor :lose] :paper
    [:scissor :draw] :scissor))

(defn get-score-by
  "Template for running the game (based on strategy)"
  [translation-fn outcome-fn scenario]
  (let [[opponent me] (map translation-fn scenario)
        outcome       (outcome-fn opponent me)]
    (+ (score-for me) (score-for outcome))))

(defn part-1
  [input]
  (->> input
       (map (partial get-score-by opponent->me selection-strategy))
       (reduce +)))

(defn part-2
  [input]
  (->> input
       (map (partial get-score-by opponent->outcome outcome-strategy))
       (reduce +)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 2))

  (def input (parse raw-input))


  (->> input)

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2022 2))
 :=
 [12645 11756])
