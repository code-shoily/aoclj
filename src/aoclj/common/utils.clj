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

(defn make-board 
  "Creates an `row` x `col` board filled with value `val`. `#` for default"
  ([width height val] (vec (repeat height (vec (repeat width val)))))
  ([width height] (make-board width height \#)))

(make-board 23 2 0)
