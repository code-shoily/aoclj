(ns aoclj.year-2016.day-14-test
  (:require
    [aoclj.year-2016.day-14 :as day-14]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [15168 20864])
(def input (utils/read-input-data 2016 14))

(deftest ^:slow year-2016-day-14-is-solved
  (is (= (day-14/solve input) result)))