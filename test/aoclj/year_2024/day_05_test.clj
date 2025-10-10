(ns aoclj.year-2024.day-05-test
  (:require
    [aoclj.year-2024.day-05 :as day-05]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [5391 6142])
(def input (utils/read-input-data 2024 5))

(deftest year-2024-day-05-is-solved
  (is (= (day-05/solve input) result)))