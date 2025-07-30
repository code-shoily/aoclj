(ns aoclj.year-2017.day-02-test
  (:require
   [aoclj.year-2017.day-02 :as day-02]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* [32020 236])
(def input (utils/read-input-data 2017 2))

(deftest year-2017-day-02-is-solved
  (is (= (day-02/solve input) *result*)))