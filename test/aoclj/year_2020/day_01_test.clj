(ns aoclj.year-2020.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2020.day-01 :refer [input solve]]))

(deftest solution-test-20-1
  (testing "Solution of 2020/1"
    (is (= (solve input) [1014624 80072256]))))