(ns aoclj.common.utils)

(defn to-int
  "Convert a str to  number"
  [s]
  (Integer/parseInt s))

(defn to-ints 
  "Converts a sequence of strings to integers"
  [xs]
  (mapv to-int xs))

(defn transpose 
  "Transposes a matrix"
  [xs] 
  (apply mapv vector (mapv vec xs)))

(defn manhattan-distance
  "Computes manhattan distance from origin"
  [[x y]]
  (+ (abs x) (abs y)))
