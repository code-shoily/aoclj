(ns aoclj.year-2019.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2019.day-01 :refer [input solve]]))

(deftest solution-test-19-1
  (testing "Solution of 2019/1"
    (is (= (solve input) [3421505 5129386]))))