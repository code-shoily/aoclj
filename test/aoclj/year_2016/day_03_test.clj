(ns aoclj.year-2016.day-03-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-03 :refer [input solve]]))

(deftest solution-test-16-3
  (testing "Solution of 2016/3"
    (is (= (solve input) [993 1849]))))