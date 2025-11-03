(ns
  ^{:title      "Recursive Circus",
    :doc        "Module for solving Advent of Code 2017 Day 7 problem.",
    :url        "http://www.adventofcode.com/2017/day/7",
    :difficulty :m,
    :year       2017,
    :day        7,
    :stars      2,
    :tags       [:complected :tree :frequency]}
  aoclj.year-2017.day-07
  (:require [aoclj.helpers.io :as io]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(defn to-weight
  "(weight) becomes weight"
  [s]
  (->> (re-find #"\((\d+)\)" s)
       second
       parse-long))

(defn list-deps
  "Extracts the dependencies from '-> d1, d2, ..' part of the 
   string"
  [line-part]
  (-> line-part
      (str/replace #"\s*->\s*" "")
      (str/split #",\s*")))

(defn parse-discs
  "Gets a disk type by parsing a line"
  [line]
  (match (str/split line #"\s+" 3)
    [name weight deps] [name
                        {:name   name,
                         :weight (to-weight weight),
                         :deps   (list-deps deps)}]
    [name weight]      [name
                        {:name   name,
                         :weight (to-weight weight),
                         :deps   []}]))

(defn build-parent-map
  "Build a map of parents (i.e. inverse deps - if a -> b,c) then
   return {b a, c a}"
  [discs]
  (let [init-map (->> (keys discs)
                      (map #(vector % nil))
                      (into {}))]
    (reduce
     (fn [parents [name {:keys [deps]}]]
       (reduce (fn [parent dep] (assoc parent dep name)) parents deps))
     init-map
     discs)))

(defn parse
  "Return a triplet of tree, parent-map and leaves"
  [raw-input]
  (->> raw-input
       str/split-lines
       (mapv parse-discs)
       (into {})
       ((juxt identity build-parent-map))))

(defn part-1
  [[_ parent-map]]
  (->> parent-map
       (keep (fn [[name parent]] (when (nil? parent) name)))
       first))

(defn build-weight-map
  "Build a map with keys as discs and values as their weights.
   A light version of the tree to be used as init state"
  [input]
  (->> input
       (map (fn [[k v]] [k (:weight v)]))
       (into {})))


(defn get-path
  "Given a tree and parent mapping find the discs that balances
   leaf all the way down to bottom."
  [tree parents leaf]
  (->>
    (take-while (comp not nil?)
                (iterate (fn [leaf] (parents leaf)) leaf))
    rest
    (mapv (fn [disc] [disc (:weight (tree leaf))]))
    (into {})))

(defn update-all-weights
  "Returns a map that displays the weight a disc balances. 
   Sum of all higher disc weight in the tower."
  [tree parents]
  (let [weight-map (build-weight-map tree)]
    (->> (keys tree)
         (map (partial get-path tree parents))
         (reduce (partial merge-with +) weight-map))))

(defn get-unbalanced-disc
  "If any, returns the one disc that is not like the others.
   TODO: What if there are two top discs?"
  [coll]
  (let [freqs (->> (map second coll)
                   frequencies
                   (sort-by second <))]
    (when-let [off-value (when (< 1 (count freqs)) (first (first freqs)))]
      (->> coll
           (filter #(= (second %) off-value))
           first
           first))))

(defn get-difference
  "Find the difference between an unbalanced and correct weighted
   discs"
  [tree updated-weights disc]
  (->> (:deps (tree disc))
       (map updated-weights)
       frequencies
       (sort-by second >)
       keys
       (apply -)))

(defn get-weights
  "Returns [disc weight] collection for all immediately above
   discs"
  [tree updated-weights disc]
  (->> (:deps (tree disc))
       (map #(vector % (updated-weights %)))))

(defn get-corrected-weight
  "Get the fixed weight for an unbalanced disc for root"
  [tree updated-weights root]
  (let [diff (get-difference tree updated-weights root)]
    (loop [disc root]
      (let [weights    (get-weights tree updated-weights disc)
            unbalanced (get-unbalanced-disc weights)]
        (if (nil? unbalanced)
          (+ diff (:weight (tree disc)))
          (recur unbalanced))))))

(defn part-2
  [[tree parents] root]
  (let [updated-weights (update-all-weights tree parents)]
    (get-corrected-weight tree updated-weights root)))

(defn solve
  [raw-input]
  (let [input   (parse raw-input)
        root    (part-1 input)
        balance (part-2 input root)]
    [root balance]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2017 7))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

(tests
 (def input (io/read-input-data 2017 7))
 (solve input)
 :=
 ["hmvwl" 1853])
