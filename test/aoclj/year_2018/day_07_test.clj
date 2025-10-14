(ns aoclj.year-2018.day-07-test
  (:require
    [aoclj.year-2018.day-07 :as day-07]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result ["BCADPVTJFZNRWXHEKSQLUYGMIO" 101])
(def input (utils/read-input-data 2018 7))

(deftest year-2018-day-07-is-solved
  (is (= (day-07/solve input) result)))