(ns aoclj.utils-test
  (:require [clojure.test :refer [deftest are]]
            [aoclj.utils :as utils]))

(deftest get-input-file-name-default-test
  (are [year day expected] (= expected (utils/get-input-file-name year day))
   2024 12 "inputs/2024_12.txt"
   2015 1  "inputs/2015_01.txt"))

(deftest get-input-file-name-prefixed-test
  (are [year day prefix expected]
   (= expected (utils/get-input-file-name year day prefix))
   2015 1  "test" "test/2015_01.txt"
   2024 12 "test" "test/2024_12.txt"))

(deftest read-input-data-test
  (are [year day prefix expected] (= expected
                                     (utils/read-input-data year day prefix))
   2015 1  "test_inputs" "data for 2015_01"
   2024 12 "test_inputs" "data for 2024_12"))

(deftest is-valid-year-day-test
  (are [year day expected] (= expected (utils/valid-year-day? year day))
   2015 1  true
   2024 12 true
   2024 13 true
   2011 1  false
   2024 0  false))