(ns aoclj.year-2024.day-01-test
  (:require
   [aoclj.year-2024.day-01 :as day-01]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* [2742123 21328497])
(def input (utils/read-input-data 2024 1))

(deftest year-2024-day-01-is-solved
  (is (= (day-01/solve input) *result*)))