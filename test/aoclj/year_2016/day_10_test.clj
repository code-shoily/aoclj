(ns aoclj.year-2016.day-10-test
  (:require
    [aoclj.year-2016.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [56 7847])
(def input (utils/read-input-data 2016 10))

(deftest year-2016-day-10-is-solved
  (is (= (day-10/solve input) result)))