(ns
  ^{:title      "Gear Ratios",
    :doc        "Module for solving Advent of Code 2023 Day 3 problem.",
    :url        "http://www.adventofcode.com/2023/day/3",
    :difficulty :s,
    :year       2023,
    :day        3,
    :stars      2,
    :tags       [:reduction :regex :grid]}
  aoclj.year-2023.day-03
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn mark-number-positions
  "Expands a range into [row n] for n in x...y. The values are a tuple of
   [r from to] triplet (to be used as common identifier of similar numbers)
   and the number itself"
  [m row start end number]
  (->> (range start end)
       (reduce
        (fn [acc x]
          (assoc acc [row x] [[row start end] (parse-long number)]))
        m)))

(defn mark-symbol-positions
  "Expands a range into [row n] for n in x...y"
  [m row start end number]
  (->> (range start end)
       (reduce (fn [acc x] (assoc acc [row x] number)) m)))

(defn collect-objects
  "Given a schematic row as string, get the span of objects (number/parts/gear etc)
   based on the regex and collect them into a map with their span and row/span coords
   via a marker function"
  [regex marker row line]
  (let [m (re-matcher regex line)]
    (loop [number (re-find m)
           result {}]
      (if (nil? number)
        result
        (let [start (.start m)
              end   (.end m)]
          (recur (re-find m)
                 (marker result row start end number)))))))

(defn collect-and-merge
  "Given collection rule for one line, map over all lines in a schematic
   and merge the result as a single map of {coords, object-rep}"
  [f input]
  (->> input
       (map-indexed #(f %1 %2))
       (reduce merge)))

(def collect-numbers (partial collect-objects #"\d+" mark-number-positions))
(def collect-symbols (partial collect-objects #"[^0-9.]" mark-symbol-positions))
(def collect-asterisks (partial collect-objects #"\*" mark-symbol-positions))
(def collect-all-numbers (partial collect-and-merge collect-numbers))
(def collect-all-symbols (partial collect-and-merge collect-symbols))
(def collect-all-asterisks (partial collect-and-merge collect-asterisks))

(defn get-adjacent-coords
  "Get all adjacent objects from (x, y)"
  [[x y]]
  [[(dec x) (dec y)] ;tl
   [(dec x) y] ;t
   [(dec x) (inc y)] ;tr
   [(inc x) (dec y)] ;bl
   [(inc x) y] ;b
   [(inc x) (inc y)] ;br
   [x (dec y)] ;l
   [x (inc y)]])

(defn get-adjacent-numbers
  "Find numbers if exists. Does not process or deduplicate numbers because 
   these numbers could be duplicated in the outer layer, so all sub-data is 
   to be collected."
  [number-store [coords _]]
  (remove nil? (mapv number-store (get-adjacent-coords coords))))

(defn get-gear-ratio
  "If the symbol is attached to only two numbers (aka gear) - get the ratio.
   If the symbol is not a gear, then return `nil`"
  [number-store [coords _]]
  (let [numbers (->> coords
                     get-adjacent-coords
                     (mapv number-store)
                     (remove nil?)
                     (group-by first)
                     (map (comp second first second)))]
    (when (= 2 (count numbers))
      numbers)))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [lines (str/split-lines raw-input)]
    [lines (collect-all-numbers lines)]))

(defn part-1
  "Solve part 1 - sum of parts"
  [[lines all-numbers]]
  (->> (collect-all-symbols lines)
       (mapcat (partial get-adjacent-numbers all-numbers))
       (group-by first)
       (map (comp second first second))
       (reduce +)))

(defn part-2
  "Solve part 2 - sum of gear ratios"
  [[lines all-numbers]]
  (->> (collect-all-asterisks lines)
       (keep (partial get-gear-ratio all-numbers))
       (map (partial apply *))
       (reduce +)))

(def solve (io/generic-solver part-1 part-2 parse))

(rcf/tests
 (def input-data (io/read-input-data 2023 3))
 (solve input-data)
 :=
 [539713 84159075])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2023 3))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")
