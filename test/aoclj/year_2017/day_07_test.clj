(ns aoclj.year-2017.day-07-test
  (:require
    [aoclj.year-2017.day-07 :as day-07]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result ["hmvwl" 1853])
(def input (utils/read-input-data 2017 7))

(deftest year-2017-day-07-is-solved
  (is (= (day-07/solve input) result)))