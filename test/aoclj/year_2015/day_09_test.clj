(ns aoclj.year-2015.day-09-test
  (:require
    [aoclj.year-2015.day-09 :as day-09]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [117 909])
(def input (utils/read-input-data 2015 9))

(deftest year-2015-day-09-is-solved
  (is (= (day-09/solve input) result)))