(ns aoclj.year-2015.day-11-test
  (:require
    [aoclj.year-2015.day-11 :as day-11]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* ["cqjxxyzz" "cqkaabcc"])
(def input (utils/read-input-data 2015 11))

(deftest year-2015-day-11-is-solved
  (is (= (day-11/solve input) *result*)))