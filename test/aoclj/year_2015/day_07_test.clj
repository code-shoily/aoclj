(ns aoclj.year-2015.day-07-test
  (:require
    [aoclj.year-2015.day-07 :as day-07]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [46065 14134])
(def input (utils/read-input-data 2015 7))

(deftest year-2015-day-07-is-solved
  (is (= (day-07/solve input) *result*)))