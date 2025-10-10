(ns aoclj.year-2020.day-12-test
  (:require
    [aoclj.year-2020.day-12 :as day-12]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [439 12385])
(def input (utils/read-input-data 2020 12))

(deftest year-2020-day-12-is-solved
  (is (= (day-12/solve input) result)))