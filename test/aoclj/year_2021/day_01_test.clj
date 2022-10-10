(ns aoclj.year-2021.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2021.day-01 :refer [input solve]]))

(deftest solution-test-21-1
  (testing "Solution of 2021/1"
    (is (= (solve input) [1139 1103]))))