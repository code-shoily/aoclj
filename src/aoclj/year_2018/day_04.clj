(ns
  ^{:title      "Repose Record",
    :doc        "Module for solving Advent of Code 2018 Day 4 problem.",
    :url        "http://www.adventofcode.com/2018/day/4",
    :difficulty :m,
    :year       2018,
    :day        4,
    :stars      2,
    :tags       [:time :nil-issue :revisit :groups]}
  aoclj.year-2018.day-04
  (:require [aoclj.utils :as utils]
            [clojure.core.match :refer [match]]
            [clojure.instant :as instant]
            [clojure.string :as str]))


(defn parse-timestamp
  [time-str]
  (-> time-str
      (str/replace #"^\d\d\d\d" "2018")
      (str/replace #"\s" "T")
      instant/read-instant-date
      inst-ms
      (quot 60000)))

(defn parse-guard-info
  [log]
  (let [guard-id (->> log
                      (re-find #"Guard #(\d+) begins shift")
                      last
                      parse-long)]
    {:guard guard-id, :status :awake}))

(defn to-state-1
  [log]
  (case log
    "falls asleep" {:guard nil, :status :sleeping}
    "wakes up"     {:guard nil, :status :awake}
    (parse-guard-info log)))

(defn parse-log
  [input]
  (let [[_ time info] (re-find #"\[(.+)\] (.+)" input)
        ts-minute     (parse-timestamp time)]
    (merge (to-state-1 info) {:timestamp ts-minute})))

(defn update-with-guard-info
  "Add guard context. Fill missing guard info from wake/asleep logs"
  [logs]
  (->> logs
       (sort-by :timestamp)
       (reduce
        (fn [[acc last-guard] log]
          (let [current-guard (if (nil? (log :guard)) last-guard (log :guard))
                current-log   (assoc log :guard current-guard)]
            [(conj acc current-log) current-guard]))
        [[] nil])
       first))

(defn get-minutes
  "What's the minute for a given timestamp in minutes?"
  [minute-ts]
  (->> minute-ts
       (* 60000)
       (java.util.Date.)
       (.getMinutes)))

(defn sleep-session-summary
  "Returns the duration and frequency by minute per sleep session"
  [[start stop]]
  (let [start-minute (get-minutes start)
        duration     (- stop start)
        tally        (for [i (range 0 duration)]
                       (mod (+ i start-minute) 60))]
    [duration (frequencies tally)]))

(defn get-sleep-range
  "Get sleep session summary of a guard."
  [logs]
  (loop [logs> logs
         res   []]
    (match (vec logs>)
      [] res
      [{:status :awake} & rest] (recur rest res)
      [{:status :sleeping, :timestamp sleep-ts}
       {:status :awake, :timestamp awake-ts} & rest]
      (recur rest (conj res (sleep-session-summary [sleep-ts awake-ts]))))))

(defn most-frequent-minute [freq] (first (sort-by second > freq)))

(defn combine-sleep-ranges
  [ranges]
  (if (empty? ranges)
    [0 {0 0}]
    (reduce (fn [[a-tot a-tally] [x-tot x-tally]]
              [(+ a-tot x-tot) (merge-with + a-tally x-tally)])
            ranges)))

(defn aggregate-sleep-sessions
  "Total statistics of a guard's sleep info."
  [logs]
  (->> logs
       (sort-by :timestamp)
       (partition-by :status)
       (mapcat identity)
       get-sleep-range
       combine-sleep-ranges
       ((fn ([[total tally]] [total (most-frequent-minute tally)])))))

(defn id-mult-min [[guard [_ [mins _]]]] (* guard mins))

(defn get-id-mult-min-by
  [by input]
  (->> input
       (sort-by by >)
       first
       id-mult-min))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (->> raw-input
       str/split-lines
       (map parse-log)
       update-with-guard-info
       (group-by :guard)
       (map (fn [[guard info]] [guard (aggregate-sleep-sessions info)]))))

(def part-1 (partial get-id-mult-min-by (comp first second)))
(def part-2 (partial get-id-mult-min-by (comp second second second)))
(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2018 4))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")