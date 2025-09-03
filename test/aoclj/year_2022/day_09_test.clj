(ns aoclj.year-2022.day-09-test
  (:require
    [aoclj.year-2022.day-09 :as day-09]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [5907 2303])
(def input (utils/read-input-data 2022 9))

(deftest year-2022-day-09-is-solved
  (is (= (day-09/solve input) *result*)))