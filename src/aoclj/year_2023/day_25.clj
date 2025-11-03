(ns
  ^{:title      "Snowverload",
    :doc        "Module for solving Advent of Code 2023 Day 25 problem.",
    :url        "http://www.adventofcode.com/2023/day/25",
    :difficulty :l,
    :year       2023,
    :day        25,
    :stars      2,
    :tags       [:graph :partition :non-deterministic :revisit]}
  aoclj.year-2023.day-25
  (:require [aoclj.helpers.io :as utils]
            [medley.core :refer [queue]]
            [clojure.string :as str]
            #_[hyperfiddle.rcf :refer [tests]]))

(defn build-graph
  "Builds an adjacency list represented graph from components"
  [components]
  (let [graph (atom {})]
    (doseq [[u & vs] components
            v        vs]
      (swap! graph update u conj v)
      (swap! graph update v conj u))
    @graph))

(defn parse
  "Create a graph from component connectivity"
  [raw-input]
  (->> (str/split-lines raw-input)
       (map (comp
             (partial remove str/blank?)
             #(str/split % #":|\s")))
       build-graph))

(defn update-edges
  "Update edge counts given a set of connected vertices"
  [edge-counts vs]
  (reduce (fn [edge-counts [a b]]
            (update edge-counts (sort [a b]) (fnil inc 0)))
          edge-counts
          (partition 2 1 vs)))

(defn traverse
  "Use BFS to traverse from `start` to `end`, return size of the component
   if graph is not connected"
  [graph start end edge-counts]
  (loop [queue (conj (queue) [start []])
         seen  (transient #{})]
    (if-let [[curr prevs] (peek queue)]
      (cond
        (= curr end) (update-edges edge-counts (conj prevs curr))
        (seen curr)  (recur (pop queue) seen)
        :else        (recur (reduce (fn [q path]
                                      (conj q [path (conj prevs curr)]))
                                    (pop queue)
                                    (graph curr))
                            (conj! seen curr)))
      (count seen))))

(defn edge-frequency-sample
  "Get a sample of edges encountered by traversing different start/end
   pairs and get frequencies of different edges"
  [graph]
  (let [random-pair (->> #(shuffle (keys graph))
                         (repeatedly 200)
                         (map (partial take 2)))]
    (->> random-pair
         (reduce (fn [edge-counts [start end]]
                   (traverse graph start end edge-counts))
                 {}))))

(defn remove-most-frequent-edges
  "Remove three most frequent edges from component graph"
  [edge-counts graph]
  (let [top-3-edges (keys (take 3 (sort-by val > edge-counts)))]
    (reduce (fn [graph [a b]]
              (-> graph
                  (update a (fn [nbs] (remove #{b} nbs)))
                  (update b (fn [nbs] (remove #{a} nbs)))))
            graph
            top-3-edges)))

(defn part-1
  [graph]
  (let [edge-counts (edge-frequency-sample graph)
        graph*      (remove-most-frequent-edges edge-counts graph)
        key-set     (count (keys graph))
        disj-len    (traverse graph* (key (first graph)) nil nil)]
    (* (- key-set disj-len) disj-len)))

(defn solve
  [raw-input]
  [(part-1 (parse raw-input)) :🎉])

;; Flaky Test! See `non-deterministic` tag above
#_(tests
   (def input-data (utils/read-input-data 2023 25))
   (solve input-data)
   :=
   [558376 :🎉])

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2023 25))

  (def graph (parse raw-input))

  (keys graph)
  (edge-frequency-sample graph)

  (time (solve raw-input))
  "</Explore>")
