(ns
  ^{:title      "1202 Program Alarm",
    :doc        "Module for solving Advent of Code 2019 Day 2 problem.",
    :url        "http://www.adventofcode.com/2019/day/2",
    :difficulty :xs,
    :year       2019,
    :day        2,
    :stars      2,
    :tags       [:intcode :array]}
  aoclj.year-2019.day-02
  (:require [aoclj.utils :as utils]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (mapv parse-long (str/split (str/trim raw-input) #",\s*")))

(defn run-command
  [^longs arr ctr]
  (let [inp1 (aget arr (+ 1 ctr))
        inp2 (aget arr (+ 2 ctr))
        outp (aget arr (+ 3 ctr))
        goto (+ 4 ctr)]
    (match (aget arr ctr)
      1  [goto (aset arr outp (+ (aget arr inp1) (aget arr inp2)))]
      2  [goto (aset arr outp (* (aget arr inp1) (aget arr inp2)))]
      99 nil)))

(defn modify-with
  [noun verb coll]
  (-> coll
      (assoc 1 noun)
      (assoc 2 verb)))

(defn run-program
  [noun verb code]
  (let [fixed (modify-with noun verb code)
        arr   (long-array fixed)]
    (loop [ctr 0]
      (if-let [[goto _] (run-command arr ctr)]
        (recur goto)
        (aget arr 0)))))


(def part-1 (partial run-program 12 2))

(defn part-2
  [input]
  (let [combinations (for [i (range 100) j (range 100)] [i j])]
    (->> combinations
         (reduce (fn [_ [noun verb]]
                   (if (= 19690720 (run-program noun verb input))
                     (reduced (+ (* 100 noun) verb))
                     :move))
                 :start))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2019 2))

  (def input (parse raw-input))

  input

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (solve (utils/read-input-data 2019 2) := [3562624 8298]))