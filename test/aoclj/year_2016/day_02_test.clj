(ns aoclj.year-2016.day-02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-02 :refer [input solve]]))

(deftest solution-test-16-2
  (testing "Solution of 2016/2"
    (is (= (solve input) ["76792" "A7AC3"]))))