(ns aoclj.year-2015.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2015.day-02 :refer [input solve]]))

(deftest solution-test-15-2
  (testing "Solution of 2015/2"
    (is (= (solve input) [1606483 3842356]))))