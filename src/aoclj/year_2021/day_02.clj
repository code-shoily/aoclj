;;;; --- Year 2021 Day 2: Dive! ---
;;;; Link: https://adventofcode.com/2021/day/2
;;;; Solutions: [1660158 1604592846]
(ns aoclj.year-2021.day-02
  (:require [aoclj.common.reader :as reader]
            [aoclj.common.utils :as u]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2021 2))

(defn parse [input]
  (mapv (comp #(update % 1 u/to-int)
              #(str/split % #"\s+"))
        input))

(defn move-1 [course [cmd x]]
  (case cmd
    "forward" (merge-with + course {:horiz x})
    "up" (merge-with - course {:depth x})
    "down" (merge-with + course {:depth x})))

(defn move-2 [{:keys [aim] :as course} [cmd x]]
  (case cmd
    "forward" (merge-with + course {:horiz x :depth (* aim x)})
    "up" (merge-with - course {:aim x})
    "down" (merge-with + course {:aim x})))

(defn pilot [move-fn init input]
  (as-> input $
    (reduce move-fn init $)
    (select-keys $ [:horiz :depth])
    (vals $)
    (apply * $)))

;; Solutions
(def init {:horiz 0 :depth 0 :aim 0})
(def solve-1 (partial pilot move-1 init))
(def solve-2 (partial pilot move-2 init))
(def solve (partial (comp (juxt solve-1 solve-2) parse)))

;; Run the solution
; (time (solve input))
