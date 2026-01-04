(ns
  ^{:title      "Christmas Tree Farm",
    :doc        "Module for solving Advent of Code 2025 Day 12 problem.",
    :url        "http://www.adventofcode.com/2025/day/12",
    :difficulty :l,
    :year       2025,
    :day        12,
    :stars      2,
    :tags       [:geometry :backtrack :slow]}
  aoclj.year-2025.day-12
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse-shape
  [lines]
  (let [coords (for [[r line] (map-indexed vector lines)
                     [c char] (map-indexed vector line)
                     :when    (= char \#)]
                 [r c])]
    (if (empty? coords) #{} (set coords))))

(defn parse
  [raw-input]
  (let [chunks       (str/split raw-input #"\n\n")
        shape-chunks (filter #(re-find #"^\s*\d+:" %) chunks)
        task-chunks  (filter #(re-find #"^\s*\d+x\d+:" %) chunks)]
    {:shapes (mapv (fn [s]
                     (parse-shape (drop 1 (str/split-lines s))))
                   shape-chunks),
     :tasks  (mapcat (fn [chunk]
                       (->> (str/split-lines chunk)
                            (filter #(re-find #"^\s*\d+x\d+:" %))
                            (map (fn [line]
                                   (let [[dims counts] (str/split line #": ")
                                         [w h]         (map parse-long
                                                            (str/split dims
                                                                       #"x"))
                                         reqs          (map parse-long
                                                            (str/split counts
                                                                       #" "))]
                                     {:w w, :h h, :reqs reqs})))))
              task-chunks)}))

(defn normalize
  [coords]
  (if (empty? coords)
    #{}
    (let [min-r (apply min (map first coords))
          min-c (apply min (map second coords))]
      (set (map (fn [[r c]] [(- r min-r) (- c min-c)]) coords)))))

(defn transformations
  [coords]
  (let [rot  (fn [s] (normalize (map (fn [[r c]] [c (- r)]) s)))
        flip (fn [s] (normalize (map (fn [[r c]] [r (- c)]) s)))
        r0   coords
        r1   (rot r0)
        r2   (rot r1)
        r3   (rot r2)]
    (vec (distinct (concat [r0 r1 r2 r3]
                           (map flip [r0 r1 r2 r3]))))))

(defn precompute-shapes
  [shapes]
  (mapv transformations shapes))

(defn fits?
  [grid w h shape-coords off-r off-c]
  (every? (fn [[dr dc]]
            (let [nr (+ off-r dr)
                  nc (+ off-c dc)]
              (and (>= nr 0)
                   (< nr h)
                   (>= nc 0)
                   (< nc w)
                   (not (contains? grid [nr nc])))))
          shape-coords))

(defn place
  [grid shape-coords off-r off-c]
  (reduce (fn [g [dr dc]] (conj g [(+ off-r dr) (+ off-c dc)]))
          grid
          shape-coords))

(defn solve-packing
  [grid w h pieces variations-map]
  (if (empty? pieces)
    true
    (let [current-id (first pieces)
          remaining  (rest pieces)
          vars       (nth variations-map current-id)]
      (some (fn [shape-coords]
              (let [max-r   (apply max (map first shape-coords))
                    max-c   (apply max (map second shape-coords))
                    valid-h (- h max-r)
                    valid-w (- w max-c)]
                (some (fn [r]
                        (some (fn [c]
                                (when (fits? grid w h shape-coords r c)
                                  (if (solve-packing
                                       (place grid shape-coords r c)
                                       w
                                       h
                                       remaining
                                       variations-map)
                                    true
                                    false)))
                              (range valid-w)))
                      (range valid-h))))
            vars))))

(defn solve-region
  [shapes-vars {:keys [w h reqs]}]
  (let [piece-ids   (vec (mapcat (fn [id count] (repeat count id))
                          (range)
                          reqs))
        sorted-ids  (sort-by #(count (first (nth shapes-vars %))) > piece-ids)
        total-area  (* w h)
        pieces-area (reduce +
                            (map #(count (first (nth shapes-vars %)))
                                 piece-ids))]

    (if (> pieces-area total-area)
      false
      (solve-packing #{} w h sorted-ids shapes-vars))))

(defn solve
  [input]
  (let [{:keys [shapes tasks]} (parse input)
        shapes-vars (precompute-shapes shapes)]
    [(count (filter #(solve-region shapes-vars %) tasks)) :🎉]))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 12))

  (def input (parse raw-input))

  input

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 12)) := [510 :🎉])