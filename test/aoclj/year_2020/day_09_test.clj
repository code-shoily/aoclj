(ns aoclj.year-2020.day-09-test
  (:require
    [aoclj.year-2020.day-09 :as day-09]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [15353384 2466556])
(def input (utils/read-input-data 2020 9))

(deftest year-2020-day-09-is-solved
  (is (= (day-09/solve input) result)))