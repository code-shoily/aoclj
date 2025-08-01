(ns aoclj.year-2016.day-05-test
  (:require
   [aoclj.year-2016.day-05 :as day-05]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* ["f77a0e6e" "999828ec"])
(def input (utils/read-input-data 2016 5))

(deftest ^:slow year-2016-day-05-is-solved
  (is (= (day-05/solve input) *result*)))