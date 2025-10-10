(ns aoclj.year-2023.day-03-test
  (:require
    [aoclj.year-2023.day-03 :as day-03]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [539713 84159075])
(def input (utils/read-input-data 2023 3))

(deftest year-2023-day-03-is-solved
  (is (= (day-03/solve input) result)))