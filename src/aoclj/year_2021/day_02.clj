(ns ^{:title      "Dive!",
      :doc        "Module for solving Advent of Code 2021 Day 2 problem.",
      :url        "http://www.adventofcode.com/2021/day/2",
      :difficulty :xs,
      :year       2021,
      :day        2,
      :stars      2,
      :tags       [:simulation :cmd]}
    aoclj.year-2021.day-02
  (:require [aoclj.utils :as utils]
            [clojure.string :as str]))

(defprotocol ICommand
  (forward [this x])
  (up [this x])
  (down [this x]))

(defrecord Submarine [horizon depth]
  ICommand
    (forward [{:keys [horizon depth]} x] (->Submarine (+ horizon x) depth))
    (down [{:keys [horizon depth]} x] (->Submarine horizon (+ depth x)))
    (up [{:keys [horizon depth]} x] (->Submarine horizon (- depth x))))

(defrecord SubmarineWithAim [horizon depth aim]
  ICommand
    (forward [{:keys [horizon depth aim]} x]
      (->SubmarineWithAim (+ horizon x) (+ depth (* aim x)) aim))
    (down [{:keys [horizon depth aim]} x]
      (->SubmarineWithAim horizon depth (+ aim x)))
    (up [{:keys [horizon depth aim]} x]
      (->SubmarineWithAim horizon depth (- aim x))))

(defn run-command
  [submarine [direction steps]]
  (case direction
    "forward" (forward submarine steps)
    "down"    (down submarine steps)
    "up"      (up submarine steps)))

(defn multiply [{:keys [horizon depth]}] (* horizon depth))

(defn interpret-for [type] (comp multiply #(reduce run-command type %)))

(defn parse-command
  [line]
  (->> (str/split line #" ")
       ((fn [[a b]] [a (Integer/parseInt b)]))))

(defn parse [input] (mapv parse-command (str/split-lines input)))

(def part-1 (interpret-for (->Submarine 0 0)))

(def part-2 (interpret-for (->SubmarineWithAim 0 0 0)))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2021 2))
  (def input (parse input-data))
  (time (solve input-data))
  "</Explore>")