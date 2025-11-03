(ns
  ^{:title      "The Sum of Its Parts",
    :doc        "Module for solving Advent of Code 2018 Day 7 problem.",
    :url        "http://www.adventofcode.com/2018/day/7",
    :difficulty :xl,
    :year       2018,
    :day        7,
    :stars      1,
    :tags       [:topological-sort]}
  aoclj.year-2018.day-07
  (:require [aoclj.helpers.io :as io]
            [clojure.data.priority-map :as pm]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

;; ### Priority Queue(ish)
;; We are for now using `priority-map` because somehow that was included
;; with this project as dep.
;; One option would be to do it with Java's PriorityQueue but wanted to
;; stay in immutable land.
;;
;; _TODO: Refactor this._
;;
;; Initially had [task task] as the [key value] for the map but upon
;; unlocking `part-2`, I realized that there is a task value associated
;; with it and that agrees with the task name sorting order.
(defn get-task-time [task] (+ 60 (- (int (first (seq task))) 64)))
(defn task->tuple [task] [task (get-task-time task)])

(defn init-queue
  "Initialized a priority queue with a given set of starting tasks"
  [tasks]
  (apply pm/priority-map (mapcat task->tuple tasks)))

(defn enqueue-tasks
  "Enqueue multiple tasks into the queue"
  [queue tasks]
  (->> tasks
       (reduce (fn [acc x] (apply assoc acc (task->tuple x)))
               queue)))

(defn dequeue-task
  "Get the highest priority task, and the new queue (without that task)
   as vector."
  [pq]
  ((juxt (comp first peek) pop) pq))

;; ### Graph Functions
(defn to-graph
  "Adjacency set graph u->v where u must finish before any elem of v 
   can start."
  [edges]
  (reduce (fn [acc [u v]] (update acc u (fnil conj #{}) v)) {} edges))

(defn get-in-degrees
  "Return a map with all the tasks with their parent count"
  [edges]
  (let [init-map (->> edges
                      flatten
                      distinct
                      (map #(do [% 0]))
                      (into {}))
        in-map   (->> edges
                      (map (juxt second first))
                      (group-by first)
                      (#(update-vals % (comp count distinct))))]
    (merge init-map in-map)))


(defn recompute-in-degrees
  "In-degree map after `task` has completed."
  [graph task prev]
  (->> (graph task)
       (reduce (fn [acc x] (merge acc {x (dec (acc x))})) prev)
       ((fn [m] (dissoc m task)))))

(defn get-roots
  "Return the roots - tasks that are either starting task, or all
   its dependencies have completed."
  [in-degrees]
  (map first (filter #(zero? (second %)) in-degrees)))

(defn topological-sort
  "Return a topological ordering of tasks. Task priority (based on time taken)
   will take priority (as in tie-break)"
  [edges]
  (let [graph (to-graph edges)]
    (loop [in-degrees (get-in-degrees edges)
           queue      (init-queue (get-roots in-degrees))
           ordering   []]
      (if (empty? queue)
        ordering
        (let [[task queue*] (dequeue-task queue)
              in-degrees*   (recompute-in-degrees graph task in-degrees)
              roots*        (get-roots in-degrees*)]
          (recur in-degrees*
                 (enqueue-tasks queue* roots*)
                 (conj ordering task)))))))

(defn parse
  "Parse an input line into a list of edges [A, B] where B depends on A."
  [raw-input]
  (let [line #"([A-Z]) must be finished before step ([A-Z])"]
    (->> raw-input
         str/split-lines
         (mapv (comp #(subvec % 1)
                     #(re-find line %))))))


(defn part-1 [input] (apply str (topological-sort input)))

(defn part-2
  [input]
  (->> input
       count))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2018 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

(tests
 (solve (io/read-input-data 2018 7))
 :=
 ["BCADPVTJFZNRWXHEKSQLUYGMIO" 101])
