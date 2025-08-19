(ns aoclj.year-2017.day-08-test
  (:require
    [aoclj.year-2017.day-08 :as day-08]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [2971 4254])
(def input (utils/read-input-data 2017 8))

(deftest year-2017-day-08-is-solved
  (is (= (day-08/solve input) *result*)))