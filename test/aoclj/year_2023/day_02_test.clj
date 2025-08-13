(ns aoclj.year-2023.day-02-test
  (:require
    [aoclj.year-2023.day-02 :as day-02]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [2085 79315])
(def input (utils/read-input-data 2023 2))

(deftest year-2023-day-02-is-solved
  (is (= (day-02/solve input) *result*)))