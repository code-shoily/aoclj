(ns aoclj.year-2018.day-04-test
  (:require
    [aoclj.year-2018.day-04 :as day-04]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [74743 132484])
(def input (utils/read-input-data 2018 4))

(deftest year-2018-day-04-is-solved
  (is (= (day-04/solve input) *result*)))