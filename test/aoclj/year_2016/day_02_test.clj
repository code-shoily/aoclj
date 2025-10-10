(ns aoclj.year-2016.day-02-test
  (:require [aoclj.year-2016.day-02 :as day-02]
            [clojure.test :refer [is deftest]]
            [aoclj.utils :as utils]))

(def ^:const result ["76792" "A7AC3"])
(def input (utils/read-input-data 2016 2))

(deftest year-2016-day-02-is-solved (is (= (day-02/solve input) result)))