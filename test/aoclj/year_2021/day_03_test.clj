(ns aoclj.year-2021.day-03-test
  (:require
    [aoclj.year-2021.day-03 :as day-03]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [1540244 4203981])
(def input (utils/read-input-data 2021 3))

(deftest year-2021-day-03-is-solved
  (is (= (day-03/solve input) result)))