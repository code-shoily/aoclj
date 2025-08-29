(ns aoclj.utils
  (:require [clojure.java.io :as io]))

(def current-year 2024)
(def total-stars 500)
(def total-trophies 250)

(def aoc-years (range 2015 (inc current-year)))

(defn transpose "Transposes vector `mat`" [mat] (apply mapv vector mat))

(defn get-padded-day
  "Returns padded day, as represented in various codes/docs
   1 becomes 01, 10 remains 10"
  [day]
  (format "%02d" day))

(defn generic-solver
  "Generic template for solvers. Formats result as string of [part-1 part-2] in parallel"
  [part-1 part-2 parse]
  (fn [input]
    (vec (pmap #(% (parse input)) [part-1 part-2]))))

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

(defn read-input-data
  "Read the input data for a given year and day.
  If a prefix is provided, it will be used to construct the file path.
  The default prefix is 'inputs'."
  ([year day prefix]
   (slurp (io/resource (get-input-file-name year day prefix))))
  ([year day] (read-input-data year day "inputs")))
