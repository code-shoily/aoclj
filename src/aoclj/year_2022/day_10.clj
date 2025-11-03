(ns
  ^{:title      "Cathode-Ray Tube",
    :doc        "Module for solving Advent of Code 2022 Day 10 problem.",
    :url        "http://www.adventofcode.com/2022/day/10",
    :difficulty :s,
    :year       2022,
    :day        10,
    :stars      2,
    :tags       [:modular-algebra :grid :visual-output]}
  aoclj.year-2022.day-10
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse-cmds
  "Pad `0` in between to set the values apart to their cycle interval"
  [lines]
  (reduce
   (fn [acc line]
     (let [[instr amt] (str/split line #" ")]
       (if (= instr "addx")
         (conj acc 0 (parse-long amt))
         (conj acc 0))))
   []
   lines))

(defn parse
  "Parse the command into a stream of register values matching their
   values at n-th cycle. Since value starts from 1, we add reduction
   to have cumulative sum of values on their positions."
  [raw-input]
  (reductions + 1 (parse-cmds (str/split-lines raw-input))))

(defn part-1
  [x-positions]
  (->> [20 60 100 140 180 220]
       (map #(* % (nth x-positions (dec %))))
       (reduce +)))

(defn part-2
  [x-positions]
  (->> x-positions
       (map-indexed #(if (<= (dec %2) (mod %1 40) (inc %2)) "█" "▒"))
       (partition 40)
       (map str/join)))

(defn solve
  [raw-input]
  (let [input    (parse raw-input)
        strength (part-1 input)
        crt      (part-2 input)]
    (println (str/join "\n" crt))
    [strength nil]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2022 10))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2022 10))
 :=
 [11820 nil])
