(ns
  ^{:title      "Factory",
    :doc        "Module for solving Advent of Code 2025 Day 10 problem.",
    :url        "http://www.adventofcode.com/2025/day/10",
    :difficulty :todo,
    :year       2025,
    :day        10,
    :stars      0,
    :tags       []}
  aoclj.year-2025.day-10
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse-line
  [line]
  (let [[diagram & rest-parts] (str/split line #"\s+")
        target  (mapv #(if (= % \#) 1 0)
                      (subs diagram 1 (dec (count diagram))))
        buttons (->> rest-parts
                     (take-while #(str/starts-with? % "("))
                     (mapv (fn [s]
                             (-> s
                                 (subs 1 (dec (count s)))
                                 (str/split #",")
                                 (->> (mapv parse-long))))))]
    {:target target, :buttons buttons}))


(defn parse
  [input]
  (map parse-line (str/split-lines input)))

(defn pivot-row
  "Finds the first row >= start-row that has a 1 in the given column."
  [matrix col start-row]
  (loop [r start-row]
    (cond
      (>= r (count matrix))         nil
      (= 1 (get-in matrix [r col])) r
      :else                         (recur (inc r)))))

(defn xor-rows
  [row-a row-b]
  (mapv bit-xor row-a row-b))

(defn gaussian-elimination
  "Performs Gaussian Elimination to reach Row Echelon Form."
  [matrix]
  (let [num-rows (count matrix)
        num-cols (dec (count (first matrix)))]
    (loop [matrix matrix
           r      0
           c      0
           pivots {}]
      (if (or (>= r num-rows) (>= c num-cols))
        {:matrix matrix, :pivots pivots}
        (if-let [pivot-r (pivot-row matrix c r)]
          (let [matrix        (assoc matrix
                                     r
                                     (nth matrix pivot-r)
                                     pivot-r
                                     (nth matrix r))
                pivot-row-val (nth matrix r)
                matrix        (vec (map-indexed
                                    (fn [idx row]
                                      (if (and (not= idx r) (= 1 (nth row c)))
                                        (xor-rows row pivot-row-val)
                                        row))
                                    matrix))]
            (recur matrix (inc r) (inc c) (assoc pivots c r)))
          (recur matrix r (inc c) pivots))))))

(defn solve-system
  [{:keys [target buttons]}]
  (let [num-lights (count target)
        num-buttons (count buttons)
        initial-matrix
        (vec (for [r (range num-lights)]
               (conj (vec (for [c (range num-buttons)]
                            (if (some #{r} (nth buttons c)) 1 0)))
                     (nth target r))))
        {:keys [matrix pivots]} (gaussian-elimination initial-matrix)
        pivot-cols (set (keys pivots))
        free-cols (remove pivot-cols (range num-buttons))
        solutions
        (for [i (range (bit-shift-left 1 (count free-cols)))]
          (let [free-vals (zipmap free-cols
                                  (for [b (range (count free-cols))]
                                    (if (bit-test i b) 1 0)))
                full-assignment
                (reduce (fn [assign col-idx]
                          (if (contains? free-vals col-idx)
                            assign
                            (let [row-idx       (get pivots col-idx)
                                  row           (nth matrix row-idx)
                                  augmented-val (peek row)
                                  sum-dependents
                                  (reduce (fn [acc k]
                                            (if (and (> k col-idx)
                                                     (= 1 (nth row k)))
                                              (bit-xor acc (get assign k 0))
                                              acc))
                                          0
                                          (range num-buttons))]
                              (assoc assign
                                     col-idx
                                     (bit-xor augmented-val sum-dependents)))))
                        free-vals
                        (reverse (range num-buttons)))]
            (reduce + (vals full-assignment))))]
    (apply min solutions)))

(defn part-1
  [parsed-input]
  (reduce + (map solve-system parsed-input)))

(def solve (io/generic-solver part-1 nil parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 10))

  (def input (parse raw-input))

  (time (solve raw-input))

  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
(solve (io/read-input-data 2025 10)) := [375 :not-implemented])

