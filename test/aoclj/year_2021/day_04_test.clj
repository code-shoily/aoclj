(ns aoclj.year-2021.day-04-test
  (:require
    [aoclj.year-2021.day-04 :as day-04]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [11774 4495])
(def input (utils/read-input-data 2021 4))

(deftest year-2021-day-04-is-solved
  (is (= (day-04/solve input) *result*)))