(ns aoclj.year-2015.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2015.day-01 :refer [input solve]]))

(deftest solution-test
  (testing "Solution of 2015/1"
    (is (= (solve input) [232 1783]))))