(ns aoclj.helpers.io
  (:require [clojure.java.io :as io]
            [aoclj.helpers.meta :refer [get-input-file-name]]
            [hyperfiddle.rcf :refer [tests]]))

(defn read-input-data
  "Read the input data for a given year and day.
  If a prefix is provided, it will be used to construct the file path.
  The default prefix is 'inputs'."
  ([year day prefix]
   (slurp (io/resource (get-input-file-name year day prefix))))
  ([year day] (read-input-data year day "inputs")))

(defn generic-solver
  "Generic template for solvers. Formats result as string of [part-1 part-2] in parallel"
  [part-1 part-2 parse]
  (fn [input]
    (vec (pmap #(% (parse input)) [part-1 part-2]))))

;!zprint {:format :off}
(tests
 (read-input-data 2015 1 "test_inputs")
 :=
 "data for 2015_01"
 (read-input-data 2024 12 "test_inputs")
 := "data for 2024_12")