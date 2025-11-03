(ns aoclj.helpers.seq)

(defn transpose
  "Transposes vector `mat`"
  [mat]
  (apply mapv vector mat))