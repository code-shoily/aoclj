(ns ^{:title      "Password Philosophy",
      :doc        "Module for solving Advent of Code 2020 Day 2 problem.",
      :url        "http://www.adventofcode.com/2020/day/2",
      :difficulty :xs,
      :year       2020,
      :day        2,
      :stars      2,
      :tags       [:frequency :nil]}
    aoclj.year-2020.day-02
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defrecord PasswordPolicy [lo hi ch pwd])

(defn make-password-policy
  [line]
  (let [[_ lo hi ch pwd] (re-matches #"(\d+)-(\d+) (.): (.+)" line)]
    (->PasswordPolicy (parse-long lo)
                      (parse-long hi)
                      (first ch)
                      pwd)))

(defn valid-by-frequency?
  [{:keys [lo hi ch pwd]}]
  (let [freq (get (frequencies pwd) ch 0)] (<= lo freq hi)))

(defn valid-by-position?
  [{:keys [lo hi ch pwd]}]
  (letfn [(is-at? [idx] (= ch (nth pwd (dec idx))))]
    (not= (is-at? lo) (is-at? hi))))

(defn check-with
  [f]
  #(->> %
        (filter f)
        count))
(defn parse [input] (map make-password-policy (str/split-lines input)))

(def part-1 (check-with valid-by-frequency?))
(def part-2 (check-with valid-by-position?))
(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2020 2))
  (def input (parse input-data))
  input
  (time (solve input-data))
  "</Explore>")
