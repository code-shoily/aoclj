(ns aoclj.year-2018.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2018.day-02 :refer [input solve]]))

(deftest solution-test-18-2
  (testing "Solution of 2018/2"
    (is (= (solve input) [7221 "mkcdflathzwsvjxrevymbdpoq"]))))