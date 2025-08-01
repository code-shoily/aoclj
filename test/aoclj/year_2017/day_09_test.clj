(ns aoclj.year-2017.day-09-test
  (:require
   [aoclj.year-2017.day-09 :as day-09]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* [7616 3838])
(def input (utils/read-input-data 2017 9))

(deftest year-2017-day-09-is-solved
  (is (= (day-09/solve input) *result*)))