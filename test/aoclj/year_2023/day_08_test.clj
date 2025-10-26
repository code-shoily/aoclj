(ns aoclj.year-2023.day-08-test
  (:require
    [aoclj.year-2023.day-08 :as day-08]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [20093 22103062509257])
(def input (utils/read-input-data 2023 8))

(deftest year-2023-day-08-is-solved
  (is (= (day-08/solve input) result)))