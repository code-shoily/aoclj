(ns aoclj.year-2020.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2020.day-02 :refer [input solve]]))

(deftest solution-test-20-2
  (testing "Solution of 2020/2"
    (is (= (solve input) [607 321]))))