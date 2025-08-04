(ns aoclj.algorithms.hash
  (:import [java.security MessageDigest]
           [java.math BigInteger]))

(defn md5
  "Returns md5 hash. Currently using digest but keeping it here for easy
   refactoring later."
  [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw       (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))