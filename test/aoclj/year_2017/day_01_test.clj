(ns aoclj.year-2017.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2017.day-01 :refer [input solve]]))

(deftest solution-test-17-1
  (testing "Solution of 2017/1"
    (is (= (solve input) [1089 1156]))))