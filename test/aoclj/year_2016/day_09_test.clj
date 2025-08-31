(ns aoclj.year-2016.day-09-test
  (:require
    [aoclj.year-2016.day-09 :as day-09]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [102239 10780403063])
(def input (utils/read-input-data 2016 9))

(deftest year-2016-day-09-is-solved
  (is (= (day-09/solve input) *result*)))