(ns aoclj.year-2020.day-01-test
  (:require
   [aoclj.year-2020.day-01 :as day-01]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* [1014624 80072256])
(def input (utils/read-input-data 2020 1))

(deftest year-2020-day-01-is-solved
  (is (= (day-01/solve input) *result*)))