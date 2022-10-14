(ns aoclj.year-2016.day-06-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoclj.year-2016.day-06 :refer [input solve]]))

(deftest solution-test-16-5
  (testing "Solution of 2016/6"
    (is (= (solve input) ["qzedlxso" "ucmifjae"]))))