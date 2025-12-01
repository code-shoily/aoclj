(ns aoclj.helpers.meta
  (:require [hyperfiddle.rcf :as rcf]))

(def current-year 2025)
(def total-stars 500)
(def total-trophies 250)

(def aoc-years (range 2015 (inc current-year)))

(defn get-padded-day
  "Returns padded day, as represented in various codes/docs
   1 becomes 01, 10 remains 10"
  [day]
  (format "%02d" day))

(defn get-ns-string
  "Returns the namespace string for a given solution
   (i.e for 2015 1 - aoclj.year-2015.day-01)"
  [year day]
  (let [padded-day (get-padded-day day)]
    (str "aoclj.year-" year ".day-" padded-day)))

(defn valid-year-day?
  "Check if the year and day are valid.
  Year should be between 2015 and 2024, and day should be between 1 and 25."
  [year day]
  (and (<= 2015 year current-year) (<= 1 day 25)))

(defn get-input-file-name
  "Construct the input file name based on year and day.
  If a prefix is provided, it will be used to construct the file path.
  The default prefix is 'inputs'."
  ([year day prefix] (str prefix "/" year "_" (get-padded-day day) ".txt"))
  ([year day] (get-input-file-name year day "inputs")))

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (get-input-file-name 2015 1) := "inputs/2015_01.txt"
 (get-input-file-name 2024 12) := "inputs/2024_12.txt"
 (get-input-file-name 2015 1 "test") := "test/2015_01.txt"
 (get-input-file-name 2024 12 "test") := "test/2024_12.txt")

(rcf/tests
 (valid-year-day? 2015 1) := true
 (valid-year-day? 2024 12) := true
 (valid-year-day? 2024 13) := true
 (valid-year-day? 2011 1) := false
 (valid-year-day? 2015 1) := true
 (valid-year-day? 2024 0) := false)
