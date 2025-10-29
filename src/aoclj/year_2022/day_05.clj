(ns
  ^{:title      "Supply Stacks",
    :doc        "Module for solving Advent of Code 2022 Day 5 problem.",
    :url        "http://www.adventofcode.com/2022/day/5",
    :difficulty :m,
    :year       2022,
    :day        5,
    :stars      2,
    :tags       [:parse-heavy :stack :string-result]}
  aoclj.year-2022.day-05
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn pad-placeholder
  "Given padding of 4, [A B C] becomes [A B C \"\"]"
  [row padding]
  (let [row      (mapv str/trim row)
        row-size (count row)]
    (if (< padding row-size)
      row
      (into [] (concat row (repeat (- padding row-size) ""))))))

(defn parse-crate
  "To standardize the crate row, we are rejigging the spacing and
   surrounding brackets to give the crates equal spacing. Since last
   spaces are trimmed, we normalize the empty cells with padding."
  [row]
  (-> row
      (str/replace #"\[" "")
      (str/replace #"\]" "  ")
      (str/split #"\s\s\s")
      (pad-placeholder 9)))

(defn parse-crates
  "Separate the container schematics and crane procedures."
  [crates]
  (->> (butlast crates)
       (map parse-crate)
       utils/transpose
       (map #(drop-while str/blank? %))
       (map-indexed (fn [i v] [(inc i) v]))
       (into {})))

(defn parse-moves
  "A move instruction is [N A B] -> Move N crates from A to B "
  [moves]
  (letfn [(to-vec [line]
            (->> line
                 (re-find #"move (\d+) from (\d+) to (\d+)")
                 rest
                 (mapv parse-long)))]
    (map to-vec moves)))

(defn parse
  "Transform the raw-input into processable data structure"
  [raw-input]
  (->> (str/split-lines raw-input)
       (partition-by (partial = ""))
       ((fn [[crates _ procedure]]
          {:crates (parse-crates crates),
           :moves  (parse-moves procedure)}))))

(defn move
  "Performs one move on crate. Since the movement algorithm differs in
   whether to revert the moved crates or not (i.e. move by one or move
   in groups), the function is given as parameter."
  [f crates [n a b]]
  (let [a-crates       (crates a)
        b-crates       (crates b)
        transfer-crate (f (take n a-crates))]
    (merge crates
           {a (drop n a-crates),
            b (concat transfer-crate b-crates)})))

(defn top-crates
  "Returnes a string representing the names of all top crates"
  [crates]
  (->> crates
       (sort-by first)
       (map (comp first second))
       (apply str)))

(defn part-1
  [{:keys [crates moves]}]
  (->> moves
       (reduce (partial move reverse) crates)
       top-crates))

(defn part-2
  [{:keys [crates moves]}]
  (->> moves
       (reduce (partial move identity) crates)
       top-crates))


(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 5))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2022 5))
 :=
 ["VPCDMSLWJ" "TPWCGNCCG"])