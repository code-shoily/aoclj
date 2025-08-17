(ns aoclj.year-2019.day-02-test
  (:require
    [aoclj.year-2019.day-02 :as day-02]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* [3562624 8298])
(def input (utils/read-input-data 2019 2))

(deftest year-2019-day-02-is-solved
  (is (= (day-02/solve input) *result*)))