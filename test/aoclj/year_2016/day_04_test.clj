(ns aoclj.year-2016.day-04-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-04 :refer [input solve]]))

(deftest solution-test-16-4
  (testing "Solution of 2016/4"
    (is (= (solve input) [158835 993]))))