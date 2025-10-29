(ns
  ^{:title      "Giant Squid",
    :doc        "Module for solving Advent of Code 2021 Day 4 problem.",
    :url        "http://www.adventofcode.com/2021/day/4",
    :difficulty :l,
    :year       2021,
    :day        4,
    :stars      2,
    :tags       [:grid :reduction :parse-heavy]}
  aoclj.year-2021.day-04
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]
            [medley.core :as m]))

(defn parse-draws [s] (mapv parse-long (str/split s #",")))

(defn parse-row
  "Turns a space alined set of strings into vector of ints"
  [row]
  (->> (str/split row #"\s+")
       (remove str/blank?)
       (mapv parse-long)))

(defn parse-boards
  "Converts a line spaced set of strings into a matrix of ints"
  [coll]
  (->> coll
       (partition-by str/blank?)
       (drop 1)
       (take-nth 2)
       (map-indexed #(vector %1 (mapv parse-row %2)))))

(defn populate-board
  "Reads a 5x5 board and creates a map out of it."
  [[idx board]]
  (let [coords (for [i    (range 5)
                     j    (range 5)
                     :let [coords [i j]]]
                 [(get-in board coords) coords])
        cols   [0 0 0 0 0]
        rows   [0 0 0 0 0]]
    {:id idx, :cols cols, :rows rows, :coords (into {} coords), :won? false}))

(defn update-board
  [{:keys [coords rows cols], :as board} n]
  (if-let [[i j] (coords n)]
    (let [row-update (inc (rows i))
          col-update (inc (cols j))]
      (-> board
          (update :coords dissoc n)
          (update :rows #(assoc % i row-update))
          (update :cols #(assoc % j col-update))
          (assoc :won? (or (= row-update 5) (= col-update 5)))))
    board))

(defn update-boards [boards draw] (map #(update-board % draw) boards))
(defn get-score [idx {:keys [coords]}] (* idx (apply + (keys coords))))
(defn build-game [draws boards] [draws (mapv populate-board boards)])

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [[x & xs] (str/split-lines raw-input)]
    (build-game (parse-draws x) (parse-boards xs))))

(defn part-1
  "Solve part 1 - Score of the first winning board"
  [[draws boards]]
  (->> draws
       (reduce
        (fn [boards draw]
          (let [updated-boards (update-boards boards draw)]
            (if-let [winner (m/find-first :won? updated-boards)]
              (reduced (get-score draw winner))
              updated-boards)))
        boards)))

(defn update-board-state
  "Mark all the boards for a given draw value. If the board is the last
   one, then return the score"
  [winners boards draw]
  (->> boards
       (reduce
        (fn [acc x]
          (let [board  (update-board x draw)
                id     (:id board)
                won?   (board :won?)
                new?   (nil? (@winners id))
                final? (= 99 (count @winners))
                _upd   (when won?
                         (swap! winners conj id))]
            (if (and won? new? final?)
              (reduced (get-score draw board))
              (conj acc board))))
        [])))

(defn next-state-or-result
  [state]
  (if (integer? state) (reduced state) state))

(defn part-2
  "Solve part 2 - Get the last board that wins"
  [[draws boards]]
  (let [winners (atom #{})]
    (->> draws
         (reduce (comp next-state-or-result
                       (partial update-board-state winners))
                 boards))))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2021 4))

  (def input (parse raw-input))

  (time (solve raw-input))

  "</Explore>")

(tests
 (solve (utils/read-input-data 2021 4))
 :=
 [11774 4495])
