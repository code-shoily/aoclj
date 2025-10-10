(ns aoclj.year-2017.day-25-test
  (:require
    [aoclj.year-2017.day-25 :as day-25]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [4287 :🎉])
(def input (utils/read-input-data 2017 25))

(deftest year-2017-day-25-is-solved
  (is (= (day-25/solve input) result)))