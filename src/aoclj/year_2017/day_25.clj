(ns
  ^{:title      "The Halting Problem",
    :doc        "Module for solving Advent of Code 2017 Day 25 problem.",
    :url        "http://www.adventofcode.com/2017/day/25",
    :difficulty :todo,
    :year       2017,
    :day        25,
    :stars      2,
    :tags       [:parse-heavy :slow :state-machine]}
  aoclj.year-2017.day-25
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            #_[hyperfiddle.rcf :as rcf]))

(defn partition-by-sections
  [lines]
  (->> lines
       (partition-by #(= % ""))
       (remove #(= % (list "")))))

(defn extract-inputs
  [[state step]]
  (let [start-state (re-find #"\s[A-Z]" state)
        step        (re-find #"\d+" step)]
    {:init  (str/triml start-state),
     :steps (parse-long step)}))

(defn get-state-title
  [title]
  (second (re-find #"\s([A-Z]):" title)))

(defn get-write-value
  [line]
  (parse-long (re-find #"\d+" line)))

(defn get-move-value
  [line]
  (keyword (re-find #"left|right" line)))

(defn get-continue-state
  [title]
  (second (re-find #"\s([A-Z])\." title)))


(defn parse-state
  [state-lines]
  (let [[title _ write-0 move-0 cont-0 _ write-1 move-1 cont-1]
        (->> state-lines
             (map str/triml))]
    [(get-state-title title)
     {:write-0 (get-write-value write-0),
      :move-0  (get-move-value move-0),
      :cont-0  (get-continue-state cont-0),
      :write-1 (get-write-value write-1),
      :move-1  (get-move-value move-1),
      :cont-1  (get-continue-state cont-1)}]))

(defn parse
  [raw-input]
  (let [[header & state-data] (->> (str/split-lines raw-input)
                                   partition-by-sections)]
    [(extract-inputs header)
     (into {} (mapv parse-state state-data))]))

(defn do-next-state
  [write move cont pos]
  (let [next-pos
        (case move
          :left  (dec pos)
          :right (inc pos))]
    [cont write next-pos]))

(defn next-state
  [{:keys [write-0 move-0 cont-0 write-1 move-1 cont-1]} tape pos]
  (let [[cont
         write
         next-pos]
        (if (nil? (get tape pos))
          (do-next-state write-0 move-0 cont-0 pos)
          (do-next-state write-1 move-1 cont-1 pos))]
    [cont
     (if (zero? write)
       (disj! tape pos)
       (conj! tape pos)) next-pos]))

(defn part-1
  [[{:keys [init steps]} rules]]
  (loop [state     init
         tape      (transient #{})
         pos       0
         remaining steps]
    (if (zero? remaining)
      (count (persistent! tape))
      (let [[state tape pos] (next-state (rules state) tape pos)]
        (recur state tape pos (dec remaining))))))

(defn part-2 [_] :🎉)

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2017 25))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>"
)

#_(rcf/tests
   (def input (io/read-input-data 2017 25))
   (solve input)
   :=
   [4287 :🎉])
