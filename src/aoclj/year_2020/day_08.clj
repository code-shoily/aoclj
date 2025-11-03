(ns
  ^{:title      "Handheld Halting",
    :doc        "Module for solving Advent of Code 2020 Day 8 problem.",
    :url        "http://www.adventofcode.com/2020/day/8",
    :difficulty :s,
    :year       2020,
    :day        8,
    :stars      2,
    :tags       [:op-code]}
  aoclj.year-2020.day-08
  (:require [aoclj.helpers.io :as io]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn to-instruction [[i a]] [i (parse-long a)])

(defn instruction-map
  "Parses into an instruction map where in the form of `ptr -> [cmd, val]`"
  [input]
  (->> input
       (map (comp to-instruction #(str/split % #"\s")))
       (map-indexed vector)
       (into {})))

(defn run-one
  [ac np [p a] v]
  (let [v* (conj v np)]
    (case p
      "jmp" [ac (+ a np) v*]
      "acc" [(+ ac a) (inc np) v*]
      "nop" [ac (inc np) v*])))

(def parse (comp instruction-map str/split-lines))

(defn run-all
  "Given instruction map, runs the whole program - either finishing or
   reporting loop."
  [m]
  (reduce
   (fn [[ac p v] _]
     (cond
       (not (nil? (v p))) (reduced [:loop ac])
       (nil? (m p))       (reduced [:done ac])
       :else              (run-one ac p (m p) v)))
   [0 0 #{}]
   m))

(defn part-1 [input] (second (run-all input)))

(defn jmp-nop-positions
  "Returns the ptr-s where a jump or a not instruction exists."
  [input]
  (keep (fn [[ptr [cmd _]]] (when (#{"jmp" "nop"} cmd) ptr)) input))

(defn swap-jmp-nop-at
  "Swaps jump -> nop or nop -> jump at `ptr`. Throws exception 
   if the instruction at `ptr` wasn't nop/jmp"
  [m ptr]
  (match (m ptr)
    ["jmp" ac] (assoc m ptr ["nop" ac])
    ["nop" ac] (assoc m ptr ["jmp" ac])))

(defn part-2
  [m]
  (->> (jmp-nop-positions m)
       (map (comp run-all (partial swap-jmp-nop-at m)))
       (keep #(when (= (first %) :done) (second %)))
       first))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2020 8))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(rcf/tests
 (solve (io/read-input-data 2020 8))
 :=
 [2080 2477])
