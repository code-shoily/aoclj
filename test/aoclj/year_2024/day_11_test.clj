(ns aoclj.year-2024.day-11-test
  (:require
    [aoclj.year-2024.day-11 :as day-11]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [172484 205913561055242])
(def input (utils/read-input-data 2024 11))

(deftest year-2024-day-11-is-solved
  (is (= (day-11/solve input) result)))