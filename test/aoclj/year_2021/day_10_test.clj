(ns aoclj.year-2021.day-10-test
  (:require
    [aoclj.year-2021.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [339411 2289754624])
(def input (utils/read-input-data 2021 10))

(deftest year-2021-day-10-is-solved
  (is (= (day-10/solve input) *result*)))