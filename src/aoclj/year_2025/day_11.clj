(ns
  ^{:title      "Reactor",
    :doc        "Module for solving Advent of Code 2025 Day 11 problem.",
    :url        "http://www.adventofcode.com/2025/day/11",
    :difficulty :m,
    :year       2025,
    :day        11,
    :stars      2,
    :tags       [:graph :dynamic-programming]}
  aoclj.year-2025.day-11
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [hyperfiddle.rcf :as rcf]))

(defn parse
  [input]
  (->> (io/lines input)
       (map (comp (fn [[src dst]] [src (str/split dst #" ")])
                  #(str/split % #":\s+")))
       (into {})))

(def count-paths
  (memoize
   (fn [graph current target]
     (if (= current target)
       1
       (reduce +
               0
               (map #(count-paths graph % target)
                    (get graph current [])))))))

(defn part-1 [input] (count-paths input "you" "out"))

(defn part-2
  [graph]
  (let [svr-dac (* (count-paths graph "svr" "dac")
                   (count-paths graph "dac" "fft")
                   (count-paths graph "fft" "out"))
        svr-fft (* (count-paths graph "svr" "fft")
                   (count-paths graph "fft" "dac")
                   (count-paths graph "dac" "out"))]
    (+ svr-dac svr-fft)))

(def solve (io/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2025 11))

  (def input (parse raw-input))

  (time (solve raw-input))
  "</Explore>")

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (solve (io/read-input-data 2025 11)) := [428 331468292364745])