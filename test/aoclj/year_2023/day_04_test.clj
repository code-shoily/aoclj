(ns aoclj.year-2023.day-04-test
  (:require
    [aoclj.year-2023.day-04 :as day-04]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [24542 8736438])
(def input (utils/read-input-data 2023 4))

(deftest year-2023-day-04-is-solved
  (is (= (day-04/solve input) result)))