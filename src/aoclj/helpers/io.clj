(ns aoclj.helpers.io
  (:require [clojure.java.io :as io]
            [aoclj.helpers.meta :refer [get-input-file-name]]
            [hyperfiddle.rcf :as rcf]
            [clojure.string :as str]))

(defn read-input-data
  "Read the input data for a given year and day.
  If a prefix is provided, it will be used to construct the file path.
  The default prefix is 'inputs'."
  ([year day prefix]
   (slurp (io/resource (get-input-file-name year day prefix))))
  ([year day] (read-input-data year day "inputs")))

(defn lines
  "Splits s into vector lines. If `by` is given, then it
   transforms each lines accordingly"
  ([by s]
   (->> (str/split-lines s)
        (mapv by)))
  ([s] (lines identity s)))

(defn line
  "Extracts the single line of s. If `by` is given, then it
   transforms each lines accordingly"
  ([by s]
   (by (str/trim s)))
  ([s] (line identity s)))

(defn generic-solver
  "Generic template for solvers. 
   Formats result as string of [part-1 part-2] in parallel"
  [part-1 part-2 parse]
  (fn [input]
    (vec (pmap #(% (parse input)) [part-1 part-2]))))

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (read-input-data 2015 1 "test_inputs") := "data for 2015_01"
 (read-input-data 2024 12 "test_inputs") := "data for 2024_12")

(rcf/tests
 (lines "a\nb\nc\n") := ["a" "b" "c"]
 (lines parse-long "1\n10\n100\n") := [1 10 100]
 (lines parse-long "a\n1\nb\n2") := [nil 1 nil 2]
 )
