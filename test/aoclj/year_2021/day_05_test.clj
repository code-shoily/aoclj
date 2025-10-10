(ns aoclj.year-2021.day-05-test
  (:require
    [aoclj.year-2021.day-05 :as day-05]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [4655 20500])
(def input (utils/read-input-data 2021 5))

(deftest year-2021-day-05-is-solved
  (is (= (day-05/solve input) result)))