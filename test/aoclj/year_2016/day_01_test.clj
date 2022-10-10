(ns aoclj.year-2016.day-01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-01 :refer [input solve]]))

(deftest solution-test-16-1
  (testing "Solution of 2016/1"
    (is (= (solve input) [253 126]))))