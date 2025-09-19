(ns aoclj.year-2016.day-18-test
  (:require
    [aoclj.year-2016.day-18 :as day-18]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [1951 20002936])
(def input (utils/read-input-data 2016 18))

(deftest ^:slow year-2016-day-18-is-solved
  (is (= (day-18/solve input) *result*)))