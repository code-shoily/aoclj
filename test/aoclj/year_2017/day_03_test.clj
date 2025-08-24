(ns aoclj.year-2017.day-03-test
  (:require
    [aoclj.year-2017.day-03 :as day-03]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [430 312453])
(def input (utils/read-input-data 2017 3))

(deftest year-2017-day-03-is-solved
  (is (= (day-03/solve input) *result*)))