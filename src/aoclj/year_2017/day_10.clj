(ns
  ^{:title      "Knot Hash",
    :doc        "Module for solving Advent of Code 2017 Day 10 problem.",
    :url        "http://www.adventofcode.com/2017/day/10",
    :difficulty :m,
    :year       2017,
    :day        10,
    :stars      2,
    :tags       [:revisit :hash :array]}
  aoclj.year-2017.day-10
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn inplace-reverse
  [x pos length]
  (let [idx (mapv #(mod % (count x))
                  (range pos (+ pos length)))
        vs  (reduce #(cons (get x %2) %1) '() idx)]
    (loop [[id & tail-id] idx
           [v & tail-v] vs
           x x]
      (if id (recur tail-id tail-v (assoc x id v)) x))))

(defn transition
  [[x pos skip] length]
  [(inplace-reverse x pos length)
   (+ pos length skip)
   (inc skip)])

(def hash-round #(reduce transition %2 %1))

(defn sparse-hash
  [data]
  (first
   (nth (iterate (partial hash-round (concat data [17 31 73 47 23]))
                 [(vec (range 256)) 0 0])
        64)))

(defn dense-hash
  [data]
  (->> data
       (partition 16)
       (map #(reduce bit-xor %))
       (map #(format "%02x" %))
       (apply str)))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [nums  (->> (str/split (str/trim raw-input) #",")
                   (mapv parse-long))
        bytes (->> (str/trim raw-input)
                   (mapv int))]
    [nums bytes]))

(defn part-1
  [[nums _]]
  (->> (hash-round nums [(vec (range 256)) 0 0])
       first
       (take 2)
       (apply *)))

(def part-2 (comp dense-hash sparse-hash second))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2017 10))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>")

(tests
 (def input (io/read-input-data 2017 10))
 (solve input)
 :=
 [9656 "20b7b54c92bf73cf3e5631458a715149"])
