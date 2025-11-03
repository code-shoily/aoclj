(ns ^{:title      "I Heard You Like Registers",
      :doc        "Module for solving Advent of Code 2017 Day 8 problem.",
      :url        "http://www.adventofcode.com/2017/day/8",
      :difficulty :xs,
      :year       2017,
      :day        8,
      :stars      2,
      :tags       [:op-code]}
    aoclj.year-2017.day-08
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn as-fun
  [sym]
  (case sym
    "inc" +
    "dec" -
    "!="  not=
    (resolve (symbol sym))))

(defn parse-line
  "src inc/dec upd if dep pred? comp-val becomes a quadruplet with functions
   turning into closures."
  [[src op upd _ dep pred? val]]
  [src (as-fun op) (parse-long upd) dep (as-fun pred?)
   (parse-long val)])

(defn update-register
  "Resolves the statement and updates the src to either correct
   value (if pred? is met) or returns current value"
  [regs [src op upd dep pred? val]]
  (let [dep* (get regs dep 0)
        src* (get regs src 0)]
    (if (pred? dep* val) (op src* upd) src*)))

(defn parse
  [raw-input]
  (->> (str/split-lines raw-input)
       (map (comp parse-line #(str/split % #" ")))))

(defn part-1
  [input]
  (->> input
       (reduce (fn [regs [key & _ :as v]]
                 (assoc regs key (update-register regs v)))
               {})
       vals
       (apply max)))

(defn part-2
  [input]
  (->> input
       (reductions (fn [regs [key & _ :as v]]
                     (assoc regs key (update-register regs v)))
                   {})
       (mapcat vals)
       (apply max)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input (io/read-input-data 2017 8))
  (def input (parse raw-input))
  (time (solve raw-input))
  "</Explore>")

(rcf/tests
 (def input (io/read-input-data 2017 8))
 (solve input)
 :=
 [2971 4254])
