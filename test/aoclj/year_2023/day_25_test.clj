(ns aoclj.year-2023.day-25-test
  (:require
    [aoclj.year-2023.day-25 :as day-25]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [558376 :🎉])
(def input (utils/read-input-data 2023 25))

(deftest ^:flaky year-2023-day-25-is-solved
  (is (= (day-25/solve input) *result*)))