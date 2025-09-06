(ns aoclj.year-2021.day-07-test
  (:require
    [aoclj.year-2021.day-07 :as day-07]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [344138 94862124])
(def input (utils/read-input-data 2021 7))

(deftest year-2021-day-07-is-solved
  (is (= (day-07/solve input) *result*)))