(ns aoclj.year-2019.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2019.day-02 :refer [input solve]]))

(deftest solution-test-19-2
  (testing "Solution of 2019/2"
    (is (= (solve input) [3562624 8298]))))