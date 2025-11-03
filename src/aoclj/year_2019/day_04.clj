(ns
  ^{:title      "Secure Container",
    :doc        "Module for solving Advent of Code 2019 Day 4 problem.",
    :url        "http://www.adventofcode.com/2019/day/4",
    :difficulty :s,
    :year       2019,
    :day        4,
    :stars      2,
    :tags       [:arithmetic]}
  aoclj.year-2019.day-04
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn divmod
  "Returns division and remainder as a tuple. 
   TODO: Move to Lib"
  [num by]
  ((juxt #(quot % by) #(mod % by)) num))

(defn valid-1?
  [^long num]
  (->> (range 7)
       (reduce (fn [[^int n
                     ^boolean twice?
                     ^int last-val] _]
                 (let [[div mod] (divmod n 10)]
                   (if (> mod last-val)
                     (reduced [nil false nil nil])
                     (case (= mod last-val)
                       true [div true last-val]
                       [div twice? mod]))))
               [num false 100])
       second))

(defn valid-2?
  [^long num]
  (->> (range 7)
       (reduce (fn [[^int n
                     ^boolean twice?
                     ^int last-freq
                     ^int last-val] _]
                 (let [[^int div ^int mod] (divmod n 10)]
                   (if (> mod last-val)
                     (reduced [nil false nil nil])
                     (case (= mod last-val)
                       true  [div twice? (inc last-freq) last-val]
                       false (if (= 2 last-freq)
                               [div true 1 mod]
                               [div twice? 1 mod])))))
               [num false 0 100])
       second))

(defn parse
  [raw-input]
  (map parse-long
       (-> raw-input
           str/trim
           (str/split #"-"))))

(defn count-valid-by
  [pred]
  (fn [[from to]]
    (loop [cur from
           res 0]
      (if (>= cur to)
        res
        (recur (inc cur)
               (if (pred cur) (inc res) res))))))

(def part-1 (count-valid-by valid-1?))

(def part-2 (count-valid-by valid-2?))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2019 4))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (io/read-input-data 2019 4))
 :=
 [1099 710])
