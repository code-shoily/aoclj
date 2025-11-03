(ns
  ^{:title      "Corporate Policy",
    :doc        "Module for solving Advent of Code 2015 Day 11 problem.",
    :url        "http://www.adventofcode.com/2015/day/11",
    :difficulty :s,
    :year       2015,
    :day        11,
    :stars      2,
    :tags       [:sequence :ascii :slow]}
  aoclj.year-2015.day-11
  (:require [aoclj.utils :as utils]
            [medley.core :as m]
            [clojure.string :as str]
            #_[hyperfiddle.rcf :as rcf :refer [tests]]))

(defn parse
  "Parse raw string input into a processable data structure"
  [raw-input]
  (str/trim raw-input))

(defn increasing-triplet?
  [ch]
  (let [[a b c] (map int ch)]
    (and (< a b c) (= 1 (- b a)) (= 1 (- c b)))))

(defn increasing?
  [xs]
  (->> xs
       vec
       (partition 3 1)
       (map increasing-triplet?)
       (some true?)
       boolean))

(defn double-double?
  [xs]
  (->> xs
       (partition 2 1)
       (filter #(apply = %))
       distinct
       count
       (= 2)))

(defn valid-password?
  [xs]
  (and (increasing? xs) (double-double? xs)))

(defn get-next-sequence
  [prev]
  (->> prev
       m/indexed
       reverse
       (reduce
        (fn [acc [idx ch]]
          (let [next-char (cond
                            (= \h ch) \j
                            (= \k ch) \m
                            (= \n ch) \p
                            (= \z ch) \a
                            :else     (char (inc (int ch))))
                password  (assoc acc idx next-char)
                carry-on? (= \z ch)]
            (if-not carry-on? (reduced (apply str password)) password)))
        (vec prev))))

(defn get-next-valid-password
  [prev]
  (let [next-sequence (get-next-sequence prev)]
    (if (valid-password? next-sequence)
      next-sequence
      (recur next-sequence))))

(defn solve
  [raw-input]
  (let [init-passphrase (parse raw-input)
        part-1          (get-next-valid-password init-passphrase)
        part-2          (get-next-valid-password part-1)]
    [part-1 part-2]))

(comment
  "<Explore>"
  (def raw-input
    (utils/read-input-data 2015 11))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>")

#_(tests
   (solve (utils/read-input-data 2015 11))
   :=
   ["cqjxxyzz" "cqkaabcc"])
