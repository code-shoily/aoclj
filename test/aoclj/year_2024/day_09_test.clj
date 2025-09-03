(ns aoclj.year-2024.day-09-test
  (:require
    [aoclj.year-2024.day-09 :as day-09]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [6446899523367 6478232739671])
(def input (utils/read-input-data 2024 9))

(deftest year-2024-day-09-is-solved
  (is (= (day-09/solve input) *result*)))