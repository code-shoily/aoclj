(ns aoclj.year-2024.day-02-test
  (:require [aoclj.year-2024.day-02 :as day-02]
            [clojure.test :refer [is deftest]]
            [aoclj.utils :as utils]))

(def ^:const result [486 540])
(def input (utils/read-input-data 2024 2))

(deftest year-2024-day-02-is-solved (is (= (day-02/solve input) result)))