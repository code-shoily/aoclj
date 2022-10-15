(ns aoclj.common.utils
  (:import [java.security MessageDigest]
           [java.math BigInteger]))

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

(defn md5
  "Computes MD5 hash of string"
  [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))