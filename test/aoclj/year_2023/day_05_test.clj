(ns aoclj.year-2023.day-05-test
  (:require
    [aoclj.year-2023.day-05 :as day-05]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [3374647 6082852])
(def input (utils/read-input-data 2023 5))

(deftest year-2023-day-05-is-solved
  (is (= (day-05/solve input) result)))