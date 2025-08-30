(ns
  ^{:title      "No Space Left On Device",
    :doc        "Module for solving Advent of Code 2022 Day 7 problem.",
    :url        "http://www.adventofcode.com/2022/day/7",
    :difficulty :s,
    :year       2022,
    :day        7,
    :stars      2,
    :tags       [:tree :file-system]}
  aoclj.year-2022.day-07
  (:require [aoclj.utils :as utils]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse-command
  "Create command vector from string.
   Note: Probably could have done away with keeping the strings
   but having a structure looks nice(?)"
  [line]
  (let [token (str/split line #"\s+")]
    (match token
      ["$" "ls"]      [:ls]
      ["$" "cd" ".."] [:cd :up]
      ["$" "cd" dir]  [:cd dir]
      ["dir" dir]     [:dir dir]
      [size file]     [:leaf (parse-long size) file])))

(defn update-parents
  "Recursively update all ancestors with the file's size"
  [dir-tree path size]
  (loop [dir-tree dir-tree
         path     path]
    (if (empty? path)
      dir-tree
      (recur (update dir-tree path (fnil + 0) size)
             (pop path)))))

(defn make-dir-tree
  [commands]
  (first (reduce (fn [[tree parents] cmd]
                   (match cmd
                     (:or [:ls] [:dir _]) [tree parents]
                     [:cd "/"]            [tree ["/"]]
                     [:cd :up]            [tree (pop parents)]
                     [:cd dir]            [tree (conj parents dir)]
                     [:leaf size _]       [(update-parents tree parents size)
                                           parents]))
                 [{} []]
                 commands)))

(defn parse
  "Traverse the commands and form a directory tree of the form {path-vector size}"
  [raw-input]
  (->> (str/split-lines raw-input)
       (map parse-command)
       make-dir-tree))

(defn part-1
  [dir-tree]
  (let [dir-sizes (vals dir-tree)]
    (reduce + (filter #(<= % 100000) dir-sizes))))

(defn part-2
  " Identify the smalles value larger than the target. "
  [dir-tree]
  (let [total-size (dir-tree ["/"])
        excess     (- total-size 40000000)]
    (->> (vals dir-tree)
         (filter #(>= % excess))
         (reduce min))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  " <Explore> "
  (def raw-input
    (utils/read-input-data 2022 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  " </Explore> "
)