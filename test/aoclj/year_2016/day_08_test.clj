(ns aoclj.year-2016.day-08-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-08 :refer [input solve]]))

(deftest solution-test-16-8
  (testing "Solution of 2016/8"
    (is (= (first (solve input)) 115))))