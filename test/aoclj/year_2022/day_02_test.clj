(ns aoclj.year-2022.day-02-test
  (:require
    [aoclj.year-2022.day-02 :as day-02]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:const result [12645 11756])
(def input (utils/read-input-data 2022 2))

(deftest year-2022-day-02-is-solved
  (is (= (day-02/solve input) result)))