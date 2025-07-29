(ns aoclj.year-2016.day-01-test
  (:require
   [aoclj.year-2016.day-01 :as d]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def input (utils/read-input-data 2016 1))

(deftest year-2016-day-1-is-solved
  (is (= (d/solve input) [253 126])))