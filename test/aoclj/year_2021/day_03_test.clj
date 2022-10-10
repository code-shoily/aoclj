(ns aoclj.year-2021.day-03-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2021.day-03 :refer [input solve]]))

(deftest solution-test-21-3
  (testing "Solution of 2021/3"
    (is (= (solve input) [1540244 4203981]))))