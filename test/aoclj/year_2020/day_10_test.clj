(ns aoclj.year-2020.day-10-test
  (:require
    [aoclj.year-2020.day-10 :as day-10]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [2030 42313823813632])
(def input (utils/read-input-data 2020 10))

(deftest year-2020-day-10-is-solved
  (is (= (day-10/solve input) result)))