(ns aoclj.year-2015.day-08-test
  (:require
   [aoclj.year-2015.day-08 :as day-08]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* [1333 2046])
(def input (utils/read-input-data 2015 8))

(deftest year-2015-day-08-is-solved
  (is (= (day-08/solve input) *result*)))