(ns aoclj.year-2018.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2018.day-01 :refer [input solve]]))

(deftest solution-test-18-1
  (testing "Solution of 2018/1"
    (is (= (solve input) [590 83445]))))