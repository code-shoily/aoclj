(ns aoclj.year-2022.day-05-test
  (:require
    [aoclj.year-2022.day-05 :as day-05]
    [clojure.test :refer [is deftest]]
    [aoclj.utils :as utils]))

(def ^:dynamic *result* ["VPCDMSLWJ" "TPWCGNCCG"])
(def input (utils/read-input-data 2022 5))

(deftest year-2022-day-05-is-solved
  (is (= (day-05/solve input) *result*)))