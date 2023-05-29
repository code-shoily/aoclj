(ns aoclj.year-2022.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2022.day-01 :refer [input solve]]))

(deftest solution-test-22-1
  (testing "Solution of 2022/1"
    (is (= (solve input) [70720 207148]))))