(ns aoclj.year-2017.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2017.day-02 :refer [input solve]]))

(deftest solution-test-17-2
  (testing "Solution of 2017/2"
    (is (= (solve input) [32020 236]))))