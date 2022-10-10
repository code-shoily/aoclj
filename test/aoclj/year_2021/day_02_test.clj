(ns aoclj.year-2021.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2021.day-02 :refer [input solve]]))

(deftest solution-test-21-2
  (testing "Solution of 2021/2"
    (is (= (solve input) [1660158 1604592846]))))