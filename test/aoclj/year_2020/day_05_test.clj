(ns aoclj.year-2020.day-05-test
  (:require
    [aoclj.year-2020.day-05 :as day-05]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [930 515])
(def input (utils/read-input-data 2020 5))

(deftest year-2020-day-05-is-solved
  (is (= (day-05/solve input) *result*)))