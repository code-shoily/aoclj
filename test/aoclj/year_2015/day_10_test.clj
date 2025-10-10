(ns aoclj.year-2015.day-10-test
  (:require
    [aoclj.year-2015.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [360154 5103798])
(def input (utils/read-input-data 2015 10))

(deftest year-2015-day-10-is-solved
  (is (= (day-10/solve input) result)))