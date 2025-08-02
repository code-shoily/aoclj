(ns aoclj.year-2016.day-06-test
  (:require
   [aoclj.year-2016.day-06 :as day-06]
   [clojure.test :refer [is deftest]]
   [aoclj.utils :as utils]))

(def ^:dynamic *result* ["qzedlxso" "ucmifjae"])
(def input (utils/read-input-data 2016 6))

(deftest year-2016-day-06-is-solved
  (is (= (day-06/solve input) *result*)))