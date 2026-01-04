(ns
  ^{:title      "Factory",
    :doc        "Module for solving Advent of Code 2025 Day 10 problem.",
    :url        "http://www.adventofcode.com/2025/day/10",
    :difficulty :xl,
    :year       2025,
    :day        10,
    :stars      2,
    :tags       [:parity-matching :binary-decomposition :tricky]}
  aoclj.year-2025.day-10
  (:require [aoclj.helpers.io :as io]
            [clojure.math.combinatorics :as comb]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse-line
  [line]
  (let [[lights & tl] (str/split line #"\s+")
        lights        (mapv #(= % \#)
                            (subs lights 1 (dec (count lights))))
        joltages      (io/collect-ints (last tl))
        buttons       (mapv io/collect-ints (butlast tl))]
    [lights buttons joltages]))


(defn parse [input] (map parse-line (str/split-lines input)))
(defn toggle
  [state button]
  (reduce (fn [state idx] (update state idx not)) state button))

(defn press-buttons
  [[goal buttons _]]
  (loop [[[lights buttons presses] & states'] (list [goal buttons 0])
         best-result 999999]
    (cond
      (nil? lights) best-result
      (every? false? lights) (recur states' presses)
      (and (seq buttons)
           (< (inc presses) best-result))
      (let [[button & buttons'] buttons
            lights' (toggle lights button)]
        (recur (conj states'
                     [lights' buttons' (inc presses)]
                     [lights buttons' presses])
               best-result))
      :else (recur states' best-result))))

(def press-results (juxt (comp frequencies flatten) count))
(defn light-parity
  [joltages]
  (set (keep (fn [[k v]] (when (odd? v) k)) joltages)))

(defn all-states
  [buttons]
  (update-vals (->> (comb/subsets buttons)
                    (map press-results)
                    (group-by (comp light-parity first)))
               distinct))

(defn new-state
  [joltages [deltas presses]]
  (let [joltages' (reduce-kv (fn [acc idx v]
                               (update acc idx - v))
                             joltages
                             deltas)]
    (when (not-any? neg? joltages')
      [(mapv #(quot % 2) joltages')
       presses])))

(defn press-buttons-2
  [states joltages]
  (if (every? zero? joltages)
    0
    (let [parity (light-parity (map-indexed vector joltages))]
      (->> (states parity)
           (keep #(new-state joltages %))
           (map (fn [[joltages' presses]]
                  (+ presses
                     (* 2 (press-buttons-2 states joltages')))))
           (reduce min 100000)))))

(defn part-1 [data] (reduce + (map press-buttons data)))

(defn part-2
  [data]
  (reduce +
          (pmap (fn [[_ buttons joltages]]
                  (press-buttons-2 (all-states buttons) joltages))
                data)))

(def solve (io/generic-solver part-1 part-2 parse))

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
 (solve (io/read-input-data 2025 10)) := [375 15377])

