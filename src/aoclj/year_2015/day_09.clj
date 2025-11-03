(ns
  ^{:title      "All in a Single Night",
    :doc        "Module for solving Advent of Code 2015 Day 9 problem.",
    :url        "http://www.adventofcode.com/2015/day/9",
    :difficulty :s,
    :year       2015,
    :day        9,
    :stars      2,
    :tags       [:graph :combinatorics :travelling-salesman :brute-force]}
  aoclj.year-2015.day-09
  (:require [aoclj.helpers.io :as io]
            [clojure.math.combinatorics :as combinatorics]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  "Parse raw string to a graph in a map format where (get-in [A B]) is
   the distance between A -> B or B -> A. Upon each encounter of A and
   B, both [A, B] and [B, A] keys are added."
  [raw-input]
  (->> (str/split-lines raw-input)
       (map (comp rest #(re-find #"(\w+) to (\w+) = (\d+)" %)))
       (reduce
        (fn [acc [from to dist]]
          (when (nil? (get-in acc [from to]))
            (-> acc
                (assoc-in [from to] dist)
                (assoc-in [to from] dist))))
        {})))

(defn get-distance
  "Total distance travelled when all cities are visited."
  [dist-map path]
  (->> path
       (partition 2 1)
       (map (comp parse-long #(get-in dist-map %)))
       (reduce +)))

(defn all-distances
  "Travel distances of all combinations of paths"
  [dist-map]
  (let [cities    (keys dist-map)
        all-paths (combinatorics/permutations cities)]
    (->> all-paths
         (map (partial get-distance dist-map)))))

(defn solve
  [raw-input]
  (let [dist-map  (parse raw-input)
        distances (all-distances dist-map)
        part-1    (apply min distances)
        part-2    (apply max distances)]
    [part-1 part-2]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2015 9))

  (def input (parse raw-input))



  (keys input)
  (time (solve raw-input))
  "</Explore>")



(rcf/tests
 (def input (io/read-input-data 2015 9))
 (solve input)
 :=
 [117 909])
