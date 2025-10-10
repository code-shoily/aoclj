(ns aoclj.year-2023.day-01-test
  (:require [aoclj.year-2023.day-01 :as day-01]
            [clojure.test :refer [is deftest]]
            [aoclj.utils :as utils]))

(def ^:const result [53194 54249])
(def input (utils/read-input-data 2023 1))

(deftest year-2023-day-01-is-solved (is (= (day-01/solve input) result)))