(ns aoclj.year-2017.day-05-test
  (:require [aoclj.year-2017.day-05 :as day-05]
            [clojure.test :refer [is deftest]]
            [aoclj.utils :as utils]))

(def ^:dynamic *result* [372671 25608480])
(def input (utils/read-input-data 2017 5))

(deftest year-2017-day-05-is-solved (is (= (day-05/solve input) *result*)))