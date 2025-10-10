(ns aoclj.year-2016.day-19-test
  (:require
    [aoclj.year-2016.day-19 :as day-19]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [1842613 1424135])
(def input (utils/read-input-data 2016 19))

(deftest year-2016-day-19-is-solved
  (is (= (day-19/solve input) result)))