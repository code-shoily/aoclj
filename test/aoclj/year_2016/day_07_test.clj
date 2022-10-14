(ns aoclj.year-2016.day-07-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-07 :refer [input solve]]))

(deftest solution-test-16-7
  (testing "Solution of 2016/7"
    (is (= (solve input) [105 258]))))