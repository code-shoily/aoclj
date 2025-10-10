(ns aoclj.year-2022.day-10-test
  (:require
    [aoclj.year-2022.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [11820 nil])
(def input (utils/read-input-data 2022 10))

(deftest year-2022-day-10-is-solved
  (is (= (day-10/solve input) result)))