(ns aoclj.year-2018.day-03-test
  (:require
    [aoclj.year-2018.day-03 :as day-03]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [110389 552])
(def input (utils/read-input-data 2018 3))

(deftest year-2018-day-03-is-solved
  (is (= (day-03/solve input) *result*)))