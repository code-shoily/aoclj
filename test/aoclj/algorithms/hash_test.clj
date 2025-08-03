(ns aoclj.algorithms.hash-test
  (:require [aoclj.algorithms.hash :as hash]
            [clojure.test :refer [deftest is testing]]))

(deftest md5-basic-test
  (testing "MD5 hash of basic strings"
    (is (= "d41d8cd98f00b204e9800998ecf8427e" (hash/md5 "")))
    (is (= "ed076287532e86365e841e92bfc50d8c" (hash/md5 "Hello World!")))
    (is (= "098f6bcd4621d373cade4e832627b4f6" (hash/md5 "test")))
    (is (= "c4ca4238a0b923820dcc509a6f75849b" (hash/md5 "1")))
    (is (= "c81e728d9d4c2f636f067f89cc14862c" (hash/md5 "2")))
    (is (= "e4da3b7fbbce2345d7772b0674a318d5" (hash/md5 "5")))
    (is (= "202cb962ac59075b964b07152d234b70" (hash/md5 "123")))))