(ns aoclj.year-2021.day-06-test
  (:require
    [aoclj.year-2021.day-06 :as day-06]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [350149 1590327954513])
(def input (utils/read-input-data 2021 6))

(deftest year-2021-day-06-is-solved
  (is (= (day-06/solve input) *result*)))