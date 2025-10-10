(ns aoclj.year-2015.day-06-test
  (:require
    [aoclj.year-2015.day-06 :as day-06]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [377891 14110788])

(def input (utils/read-input-data 2015 6))

(deftest year-2015-day-06-is-solved
  (is (= (day-06/solve input) result)))