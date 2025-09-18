(ns aoclj.year-2015.day-12-test
  (:require
    [aoclj.year-2015.day-12 :as day-12]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [119433 68466])
(def input (utils/read-input-data 2015 12))

(deftest year-2015-day-12-is-solved
  (is (= (day-12/solve input) *result*)))