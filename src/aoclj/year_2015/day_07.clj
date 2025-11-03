(ns
  ^{:title      "Some Assembly Required",
    :doc        "Module for solving Advent of Code 2015 Day 7 problem.",
    :url        "http://www.adventofcode.com/2015/day/7",
    :difficulty :m,
    :year       2015,
    :day        7,
    :stars      2,
    :tags       [:graph :topological-sort :revisit]}
  aoclj.year-2015.day-07
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [medley.core :as m]
            [clojure.core.match :refer [match]]
            [hyperfiddle.rcf :as rcf :refer [tests]]))

;;----------------------------------------- Evaluation io & Helpers

(defn numeric-string? [s] (boolean (re-matches #"\d+" s)))

(defn bit-not-16 [n] (bit-and (bit-not n) 0xFFFF))

(defn evaluate
  [k [params _] table]
  (letfn [(at [tab a] (if (numeric-string? a) (parse-long a) (tab a)))]
    (if-let [assigned-val (table k)]
      assigned-val
      (match params
        [:val a]    a
        [:assign a] (table a)
        [f a]       (f (at table a))
        [f a b]     (f (at table a)
                       (at table b))))))

;;----------------------------------------------------- Parse io

(defn parse-rule
  [tokens]
  (match tokens
    [a "AND" b]    [[bit-and a b] #{a b}]
    [a "OR" b]     [[bit-or a b] #{a b}]
    [a "LSHIFT" b] [[bit-shift-left a b] #{a b}]
    [a "RSHIFT" b] [[bit-shift-right a b] #{a b}]
    ["NOT" a]      [[bit-not-16 a] #{a}]
    [a]            (if (numeric-string? a)
                     [[:val (parse-long a)] #{}]
                     [[:assign a] #{a}])))

(defn parse-assignment [[lhs [rhs]]] [rhs (parse-rule lhs)])

(declare build-dependent-graph)

(defn parse
  [raw-input]
  (let [graph
        (->> raw-input
             str/split-lines
             (map #(str/split % #" -> "))
             (map (comp parse-assignment (partial map #(str/split % #"\s+"))))
             (into {}))]
    {:graph graph, :deps (build-dependent-graph graph)}))

(defn get-in-degrees
  "Get a mapping between wires and their incoming wire count"
  [graph]
  (into {}
        (map (fn [[k [_ v]]] [k (count (remove numeric-string? v))])
             graph)))

;;----------------------------------------------------- Graph io

(defn build-dependent-graph
  "Build a graph with outgoing wires"
  [input]
  (reduce
   (fn [graph [node-a [_ depends-on]]]
     (->> depends-on
          (reduce (fn [acc node-b]
                    (if (numeric-string? node-b)
                      acc
                      (update acc node-b conj node-a)))
                  graph)))
   {}
   input))

(defn get-roots
  "Get wires that have no incoming wires"
  [graph]
  (keep (fn [[k v]] (when (zero? v) k)) graph))


(defn topological-sort
  "Returns the nodes of the graph in topological order"
  [{:keys [graph deps]}]
  (loop [in-degrees (get-in-degrees graph)
         queue      (m/queue (get-roots in-degrees))
         res        (vec queue)]
    (if (empty? queue)
      res
      (let [new-queue (pop queue)
            current   (peek queue)
            [new-in-degrees new-roots]
            (->> (deps current)
                 (reduce (fn [[degs ls] node]
                           (let [degs* (update degs node dec)]
                             [degs*
                              (if (zero? (degs* node)) (conj ls node) ls)]))
                         [in-degrees []]))
            queue     (if-not (empty? new-roots)
                        (apply conj new-queue new-roots)
                        new-queue)]
        (recur new-in-degrees
               queue
               (apply conj res new-roots))))))

(defn build-signal-table
  [{:keys [graph], :as input} init]
  (->> (topological-sort input)
       (reduce (fn [table v] (assoc table v (evaluate v (graph v) table)))
               init)))

;;----------------------------------------------------- Solver

(defn solve
  [raw-input]
  (let [input  (parse raw-input)
        part-1 (-> input
                   (build-signal-table {})
                   (get "a"))
        part-2 (-> input
                   (build-signal-table {"b" part-1})
                   (get "a"))]
    [part-1 part-2]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2015 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (def input (io/read-input-data 2015 7))
 (solve input)
 :=
 [46065 14134])
