(ns ^{:title      "Security Through Obscurity",
      :doc        "Module for solving Advent of Code 2016 Day 4 problem.",
      :url        "http://www.adventofcode.com/2016/day/4",
      :difficulty :s,
      :year       2016,
      :day        4,
      :stars      2,
      :tags       [:checksum :ascii]}
    aoclj.year-2016.day-04
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]
            [medley.core :as m]))

(defrecord Room [name sector-id checksum])

(defn- make-room
  [[name [sector-id checksum]]]
  (->Room name (parse-long sector-id) checksum))

(defn- parse-room
  [line]
  (->> (str/split line #"-")
       ((juxt (comp #(apply str %) butlast)
              (comp #(str/split % #"\[|\]") last)))
       make-room))

(defn compute-checksum
  [name]
  (->> (frequencies name)
       (m/map-kv (fn [k v] [k (* -1 v)]))
       (sort-by (juxt second first))
       (map first)
       (take 5)
       (apply str)))

(defn real-room? [{:keys [name checksum]}] (= (compute-checksum name) checksum))

(defn decrypt-name
  [{:keys [name sector-id]}]
  (letfn [(rotate [by ch] (char (+ 97 (mod (+ (mod (int ch) 97) by) 26))))]
    (apply str (map (partial rotate sector-id) name))))

(defn parse
  [input]
  (->> (str/split-lines input)
       (map parse-room)))

(defn part-1
  [input]
  (->> input
       (filter real-room?)
       (map :sector-id)
       (reduce +)))

(defn part-2
  [input]
  (->> input
       (filter real-room?)
       (m/find-first (comp (partial str/starts-with? "northpoleobjectstorage")
                           decrypt-name))
       :sector-id))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2016 4))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")

(tests
 (def input (utils/read-input-data 2016 4))
 (solve input)
 :=
 [158835 993])
