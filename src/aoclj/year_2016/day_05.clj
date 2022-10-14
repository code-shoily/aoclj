;; --- Year 2016 Day 5: How About a Nice Game of Chess? ---
;; Link: https://adventofcode.com/2016/day/5
;; Solution: ["f77a0e6e" "999828ec"]
;; FIXME: This solution is quite slow (~58 seconds) perhaps should
;;        look into improving speed in future.
(ns aoclj.year-2016.day-05
  (:require [clojure.string :refer [join starts-with?]])
  (:import [java.security MessageDigest]
           [java.math BigInteger]))

(def input "cxdnnyjw")

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn- to-char-num [ch] (- (int ch) (int \0)))

(defn- build-password [kv]
  (reduce #(assoc %1 (first %2) (second %2))
          (vec (repeat 8 nil))
          kv))

;; Solutions
(defn solve-1 [input]
  (->> (iterate inc 0)
       (map (comp md5 (partial str input)))
       (filter #(starts-with? % "00000"))
       (take 8)
       (map #(nth % 5))
       join))

(defn solve-2 [input]
  (->> (iterate inc 0)
       (map (comp md5 (partial str input)))
       (filter #(starts-with? % "00000"))
       (reduce (fn [[left updated :as acc] x]
                 (let [idx (to-char-num (nth x 5))
                       continue? (and (contains? left idx) (<= 0 idx 7))]
                   (cond
                     (empty? left) (reduced updated)
                     continue? [(disj left idx) (conj updated x)]
                     :else acc))) 
               [(set (range 8)) []])
       (mapv (juxt #(to-char-num (nth % 5)) #(nth % 6)))
       build-password
       join))

(def solve (juxt solve-1 solve-2))

;; Run the solution
; (time (solve input))