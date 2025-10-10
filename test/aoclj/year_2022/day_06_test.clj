(ns aoclj.year-2022.day-06-test
  (:require
    [aoclj.year-2022.day-06 :as day-06]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [1651 3837])
(def input (utils/read-input-data 2022 6))

(deftest year-2022-day-06-is-solved
  (is (= (day-06/solve input) result)))