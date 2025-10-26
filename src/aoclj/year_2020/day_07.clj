(ns
  ^{:title      "Handy Haversacks",
    :doc        "Module for solving Advent of Code 2020 Day 7 problem.",
    :url        "http://www.adventofcode.com/2020/day/7",
    :difficulty :m,
    :year       2020,
    :day        7,
    :stars      2,
    :tags       [:graph-traversal]}
  aoclj.year-2020.day-07
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defn to-bag-label
  "Prettifies a bag label by removing space and making it keyword"
  [bag-str]
  (keyword (str/replace bag-str #"\s" "-")))

(defn bag-count
  "Returns a tuple of [bag-label bag-count]"
  [bag]
  (->> bag
       (re-find #"(\d+) (.+)")
       ((fn [[_ c b]] [(to-bag-label b) (parse-long c)]))))

(defn parse-contained-bags
  "Parses the contained bags to a list of (bag count) tuples"
  [bags]
  (->> (str/split bags #", ")
       (map (comp #(if (= % "no other") [nil 0] (bag-count %))
                  str/trim
                  #(str/replace % #"bags?.?" "")))
       (into {})))

(defn get-bag-info
  "A bag info is the name of the bag and the tally of the bags it contains"
  [line]
  (->> line
       (re-find #"([a-z\s]+) bags contain (.+)")
       ((fn [[_ src rst]] [(to-bag-label src)
                           (parse-contained-bags rst)]))))


(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> (str/split-lines raw-input)
       (map get-bag-info)
       (into {})))

(defn parent-graph
  "Invert the graph, A -> (B, 10) becomes B -> A. We are only concerned
   with the parent, not the bag count at this point."
  [bag-graph]
  (->> bag-graph
       (mapcat (fn [[parent children]]
                 (->> children
                      (map #(vector (first %) parent)))))
       (reduce (fn [graph [child parent]]
                 (update graph child (fnil conj []) parent))
               {})))

(defn find-containers-dfs
  "Run DFS to get a list of bags that can contain `node` bag."
  [node graph]
  (let [invert-graph (parent-graph graph)]
    (loop [stack      [node]
           containers #{}]
      (if (empty? stack)
        (disj containers node) ; Remember to remove the self node.
        (let [[current & rest] stack
              new-stack        (concat (invert-graph current) rest)]
          (recur new-stack (conj containers current)))))))

(defn part-1
  "Solve part 1 - how many bags can contain at least 1 shiny gold bag?"
  [input]
  (count (find-containers-dfs :shiny-gold input)))

(defn count-children-dfs
  "Run DFS on bag graph to get a count of total bags this bag can contain."
  [node graph]
  (letfn [(multiply-bags [node mul]
            (->> node
                 graph
                 (map #(vector (first %) (* (second %) mul)))))]
    (loop [stack    [[node 1]] ;; Initial multiplier is 1
           children -1] ;; Self node should be uncounted
      (if (empty? stack)
        children
        (let [[[current cnt] & rest] stack
              new-stack (concat (multiply-bags current cnt) rest)]
          (recur new-stack (+ children cnt)))))))

(defn part-2
  "Solve part 2 - the number of bag shiny gold bag can contain"
  [input]
  (count-children-dfs :shiny-gold input))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2020 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)