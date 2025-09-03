(ns aoclj.year-2024.day-10-test
  (:require
    [aoclj.year-2024.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [617 1477])
(def input (utils/read-input-data 2024 10))

(deftest year-2024-day-10-is-solved
  (is (= (day-10/solve input) *result*)))