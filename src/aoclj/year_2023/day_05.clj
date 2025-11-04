(ns
  ^{:title      "If You Give A Seed A Fertilizer",
    :doc        "Module for solving Advent of Code 2023 Day 5 problem.",
    :url        "http://www.adventofcode.com/2023/day/5",
    :difficulty :xl,
    :year       2023,
    :day        5,
    :stars      2,
    :tags       [:needs-improvement :range]}
  aoclj.year-2023.day-05
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse-seeds
  [seeds]
  (let [[_ vals] (str/split seeds #":\s")]
    (->> (str/split vals #"\s")
         (mapv parse-long))))

(defn parse-ranges
  [line]
  (->> (str/split line #"\s")
       (map parse-long)
       ((fn [[dst src len]]
          {:lo   src,
           :hi   (+ src (dec len)),
           :diff (- dst src)}))))

(defn parse-mappings
  [paragraphs]
  (->> paragraphs
       (partition-by #{""})
       (remove #{(list "")})
       (map (comp (partial sort-by :lo)
                  #(mapv parse-ranges (rest %))))))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (let [[seeds & rest] (str/split-lines raw-input)]
    [(parse-seeds seeds) (parse-mappings rest)]))

(defn convert-number
  [seed mapping]
  (reduce
   (fn [acc {:keys [lo hi diff]}]
     (if (<= lo acc hi)
       (reduced (+ acc diff))
       acc))
   seed
   mapping))

(defn convert-1
  [seeds mapping]
  (map #(convert-number % mapping) seeds))

(defn seed-ranges
  [seeds]
  (sort-by
   :start
   (for [[start len] (partition 2 seeds)]
     {:start start,
      :stop  (+ start (dec len))})))

(defn convert-2
  [seeds mapping]
  (loop [[{:keys [start stop]} & rem-seeds :as seeds] seeds
         [{:keys [lo hi diff]} & rem-mapping :as mapping] mapping
         result []]
    (if (or (empty? seeds) (empty? mapping))
      (sort-by :start (reduce conj result seeds))
      (cond
        (> start hi) (recur seeds rem-mapping result)
        (< stop lo) (recur rem-seeds
                           mapping
                           (conj result
                                 {:start start,
                                  :stop  stop}))
        (<= lo start stop hi) (recur rem-seeds
                                     mapping
                                     (conj result
                                           {:start (+ diff start),
                                            :stop  (+ diff stop)}))
        (<= lo start hi stop) (recur (conj rem-seeds
                                           {:start (inc hi),
                                            :stop  stop})
                                     rem-mapping
                                     (conj result
                                           {:start (+ diff start),
                                            :stop  (+ diff hi)}))
        (<= start lo stop hi) (recur rem-seeds
                                     mapping
                                     (-> result
                                         (conj {:start start,
                                                :stop  (dec lo)})
                                         (conj {:start (+ diff lo),
                                                :stop  (+ diff stop)})))
        :else (recur rem-seeds mapping (conj result {:start hi, :stop lo}))))))

(defn part-1
  [[seeds mappings]]
  (->> mappings
       (reduce convert-1 seeds)
       (reduce min)))

(defn part-2
  [[seeds mappings]]
  (->> mappings
       (reduce convert-2 (seed-ranges seeds))
       first
       :start))

(def solve (io/generic-solver part-1 part-2 parse))

(rcf/tests
 (def input-data (io/read-input-data 2023 5))
 (solve input-data)
 :=
 [3374647 6082852])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2023 5))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")
