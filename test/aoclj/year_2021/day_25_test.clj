(ns aoclj.year-2021.day-25-test
  (:require
    [aoclj.year-2021.day-25 :as day-25]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [504 :🎉])
(def input (utils/read-input-data 2021 25))

(deftest year-2021-day-25-is-solved
  (is (= (day-25/solve input) *result*)))