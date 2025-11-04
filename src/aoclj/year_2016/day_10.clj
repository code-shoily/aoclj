(ns
  ^{:title      "Balance Bots",
    :doc        "Module for solving Advent of Code 2016 Day 10 problem.",
    :url        "http://www.adventofcode.com/2016/day/10",
    :difficulty :s,
    :year       2016,
    :day        10,
    :stars      2,
    :tags       [:graph :needs-improvement :topological-sort]}
  aoclj.year-2016.day-10
  (:require [aoclj.helpers.io :as io]
            [clojure.core.match :refer [match]]
            [clojure.set :as set]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]
            [medley.core :as m]))

(defn build-graph
  "Create a graph of bot nodes based on values sent from K -> V-set"
  [input]
  (->> input
       (filter #(= :send (:type %)))
       (reduce
        (fn [graph {:keys [src lo hi]}]
          (merge graph
                 {src (->> [(when (= :bot (first lo)) (second lo))
                            (when (= :bot (first hi)) (second hi))]
                           (remove nil?))}))
        {})))

(defn get-in-degrees
  "Get the number of bots that send values to receiving bots."
  [graph]
  (->> graph
       (reduce
        (fn [acc [_ nodes]]
          (->> nodes
               (map #(do [% 1]))
               (into {})
               (merge-with + acc)))
        {})))

(defn get-first-bot
  "Given a bot graph and bots that receive at least one value from another bot, 
   compute the roots (i.e. bots that receive values directly)"
  [graph in-degrees]
  (let [nodes (into #{} (keys graph))
        with-in-degrees (into #{} (keys in-degrees))]
    (first (set/difference nodes with-in-degrees))))

(defn parse-line
  "Parse send/receive sentences into structured data. 
   FIXME: This step could have been avoided but it felt helpful to see 
   the intermediate steps while processing and querying data in the repl."
  [line]
  (match (str/split line #"\s")
    [_ src _ _ _ lo-type lo _ _ _ hi-type hi]
    {:src  (parse-long src),
     :type :send,
     :lo   [(keyword lo-type) (parse-long lo)],
     :hi   [(keyword hi-type) (parse-long hi)]}
    ["value" val _ _ "bot" bot]
    {:src   (parse-long bot),
     :type  :receive,
     :value (parse-long val)}))


(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [events     (io/lines parse-line raw-input)
        graph      (build-graph events)
        in-degrees (get-in-degrees graph)
        first-bot  (get-first-bot graph in-degrees)]
    {:events     (group-by :src events),
     :graph      graph,
     :in-degrees in-degrees,
     :first      first-bot}))

(defn topological-sort
  "Returns the topological order of bots in which they can sending values"
  [{:keys [graph in-degrees first]}]
  (loop [in-degrees in-degrees
         queue      (m/queue [first])
         res        (vec queue)]
    (if (empty? queue)
      res
      (let [new-queue (pop queue)
            current   (peek queue)
            [new-in-degrees new-roots]
            (->> (graph current)
                 (reduce
                  (fn [[degs ls] node]
                    (let [degs* (update degs node dec)]
                      [degs*
                       (if (zero? (degs* node))
                         (conj ls node)
                         ls)]))
                  [in-degrees []]))
            queue     (if-not (empty? new-roots)
                        (apply conj new-queue new-roots)
                        new-queue)]
        (recur new-in-degrees
               queue
               (apply conj res new-roots))))))

(defn- send-values
  "Sends the values to other bots or output. It is expected that the sending bot
   will already have its lo/hi values assigned."
  [events [outputs bots] bot]
  (->> (events bot)
       (reduce (fn [[outputs' bots'] event]
                 (let [id     (:src event)
                       lo-val (apply min (get bots' id []))
                       hi-val (apply max (get bots' id []))]
                   (match event
                     {:value value}
                     [outputs' (update bots' id conj value)]
                     {:lo [:bot lo], :hi [:bot hi]}
                     [outputs'
                      (merge-with concat bots' {lo [lo-val], hi [hi-val]})]
                     {:lo [:output lo], :hi [:output hi]}
                     [(merge-with concat outputs' {lo [lo-val], hi [hi-val]})
                      bots']
                     {:lo [:bot lo], :hi [:output hi]}
                     [(update outputs' hi conj hi-val)
                      (update bots' lo conj lo-val)]
                     {:lo [:output lo], :hi [:bot hi]}
                     [(update outputs' lo conj lo-val)
                      (update bots' hi conj hi-val)])))
               [outputs bots])))


(defn hydrate-bots
  "Update the directly assigned values to the bots"
  [{:keys [events]}]
  (->> (vals events)
       (mapcat identity)
       (filter #(= :receive (:type %)))
       (reduce (fn [acc {src :src, val :value}] (update acc src conj val)) {})))

(defn solve
  [raw-input]
  (let [input          (parse raw-input)
        events         (:events input)
        ordering       (topological-sort input)
        [outputs bots] (->> ordering
                            (reduce (partial send-values events)
                                    [{} (hydrate-bots input)]))
        part-1         (->> bots
                            (keep #(when (= (sort (second %)) '(17 61))
                                     (first %)))
                            first)
        part-2         (apply *
                              (-> outputs
                                  (select-keys [0 1 2])
                                  vals
                                  flatten))]
    [part-1 part-2]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2016 10))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))

  "</Explore>"
)

(rcf/tests
 (def input (io/read-input-data 2016 10))
 (solve input)
 :=
 [56 7847])
