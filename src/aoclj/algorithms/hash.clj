(ns aoclj.algorithms.hash
  (:import [java.security MessageDigest]
           [java.math BigInteger])
  (:require
    [hyperfiddle.rcf :as rcf]))

(defn md5
  "Returns md5 hash. Currently using digest but keeping it here for easy
   refactoring later."
  [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw       (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

;!zprint {:format :off}
(rcf/enable! false)
(rcf/tests
 (md5 "") := "d41d8cd98f00b204e9800998ecf8427e"
 (md5 "Hello World!") := "ed076287532e86365e841e92bfc50d8c"
 (md5 "test") := "098f6bcd4621d373cade4e832627b4f6"
 (md5 "1") := "c4ca4238a0b923820dcc509a6f75849b"
 (md5 "2") := "c81e728d9d4c2f636f067f89cc14862c"
 (md5 "5") := "e4da3b7fbbce2345d7772b0674a318d5"
 (md5 "123") := "202cb962ac59075b964b07152d234b70")
