(ns aoclj.year-2015.day-05-test
  (:require [aoclj.year-2015.day-05 :as day-05]
            [clojure.test :refer [is deftest]]
            [aoclj.utils :as utils]))

(def ^:dynamic *result* [255 55])
(def input (utils/read-input-data 2015 5))

(deftest year-2015-day-05-is-solved (is (= (day-05/solve input) *result*)))