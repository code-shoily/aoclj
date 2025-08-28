(ns aoclj.year-2024.day-06-test
  (:require
    [aoclj.year-2024.day-06 :as day-06]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [4982 1663])
(def input (utils/read-input-data 2024 6))

(deftest year-2024-day-06-is-solved
  (is (= (day-06/solve input) *result*)))