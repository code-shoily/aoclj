(ns aoclj.year-2019.day-01-test
  (:require
   [aoclj.year-2019.day-01 :as day-01]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* [3421505 5129386])
(def input (utils/read-input-data 2019 1))

(deftest year-2019-day-01-is-solved
  (is (= (day-01/solve input) *result*)))