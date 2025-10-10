(ns aoclj.year-2023.day-09-test
  (:require
    [aoclj.year-2023.day-09 :as day-09]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [1939607039 1041])
(def input (utils/read-input-data 2023 9))

(deftest year-2023-day-09-is-solved
  (is (= (day-09/solve input) result)))