(ns aoclj.year-2023.day-06-test
  (:require
    [aoclj.year-2023.day-06 :as day-06]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [440000 26187338])
(def input (utils/read-input-data 2023 6))

(deftest year-2023-day-06-is-solved
  (is (= (day-06/solve input) result)))