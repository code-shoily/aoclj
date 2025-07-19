(ns aoclj.utils
  (:require
   [clojure.java.io :as io]))

(defn valid-year-day?
  "Check if the year and day are valid.
  Year should be between 2015 and 2024, and day should be between 1 and 25."
  [year day]
  (and (<= 2015 year 2024)
       (<= 1 day 25)))

(defn get-input-file-name
  "Construct the input file name based on year and day.
  If a prefix is provided, it will be used to construct the file path.
  The default prefix is 'inputs'."
  ([year day prefix]
   (str prefix "/" year "_" (format "%02d" day) ".txt"))
  ([year day]
   (get-input-file-name year day "inputs")))

(defn read-input-data
  "Read the input data for a given year and day.
  If a prefix is provided, it will be used to construct the file path.
  The default prefix is 'inputs'."
  ([year day prefix]
   (slurp
    (io/resource
     (get-input-file-name year day prefix))))
  ([year day]
   (read-input-data year day "inputs")))
