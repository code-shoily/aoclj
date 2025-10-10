(ns aoclj.year-2024.day-07-test
  (:require
    [aoclj.year-2024.day-07 :as day-07]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [882304362421 145149066755184])
(def input (utils/read-input-data 2024 7))

(deftest year-2024-day-07-is-solved
  (is (= (day-07/solve input) result)))