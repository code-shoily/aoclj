(ns aoclj.year-2015.day-01-test
  (:require [aoclj.year-2015.day-01 :as d]
            [clojure.test :refer [is deftest]]
            [aoclj.utils :as utils]))

(def input (utils/read-input-data 2015 1))

(deftest year-2015-day-1-is-solved (is (= (d/solve input) [232 1783])))