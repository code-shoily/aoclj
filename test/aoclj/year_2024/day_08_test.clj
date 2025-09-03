(ns aoclj.year-2024.day-08-test
  (:require
    [aoclj.year-2024.day-08 :as day-08]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [291 1015])
(def input (utils/read-input-data 2024 8))

(deftest year-2024-day-08-is-solved
  (is (= (day-08/solve input) *result*)))