(ns aoclj.year-2020.day-08-test
  (:require
    [aoclj.year-2020.day-08 :as day-08]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [2080 2477])
(def input (utils/read-input-data 2020 8))

(deftest year-2020-day-08-is-solved
  (is (= (day-08/solve input) result)))