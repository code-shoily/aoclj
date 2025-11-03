(ns aoclj.helpers.seq
  (:require [hyperfiddle.rcf :refer [tests]]))

(defn transpose
  "Transposes vector `mat`"
  [mat]
  (apply mapv vector mat))

;!zprint {:format :off}
(tests
 (transpose [[]]) := []
 (transpose [[1 2 3]]) := [[1] [2] [3]]
 (transpose [[1 2 3] [4 5 6] [7 8 9] [10 11 12]]) := [[1 4 7 10] [2 5 8 11] [3 6 9 12]])