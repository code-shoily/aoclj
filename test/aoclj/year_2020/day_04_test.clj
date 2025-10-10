(ns aoclj.year-2020.day-04-test
  (:require
    [aoclj.year-2020.day-04 :as day-04]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [233 111])
(def input (utils/read-input-data 2020 4))

(deftest year-2020-day-04-is-solved
  (is (= (day-04/solve input) result)))