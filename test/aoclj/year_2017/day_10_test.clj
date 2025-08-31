(ns aoclj.year-2017.day-10-test
  (:require
    [aoclj.year-2017.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [9656 "20b7b54c92bf73cf3e5631458a715149"])
(def input (utils/read-input-data 2017 10))

(deftest year-2017-day-10-is-solved
  (is (= (day-10/solve input) *result*)))