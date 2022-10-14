;;;; --- Year 2016 Day 7: Internet Protocol Version 7 ---
;;;; Link: https://adventofcode.com/2016/day/7
;;;; Solutions: [105 258]
(ns aoclj.year-2016.day-07
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn separate-by [pred segments] 
  (keep-indexed #(if (pred %1) %2 nil) segments))

(defn parse-ipv7 [parsed]
  ((juxt (partial separate-by even?) 
         (partial separate-by odd?)) parsed))

(defn parse [input] 
  (mapv (comp parse-ipv7 #(str/split % #"[\[\]]")) input))

(defn has-abba? [ip-addr]
  (->> ip-addr
       (partition 4 1)
       (some (fn [[a b b' a']] 
               (and (= a a') 
                    (= b b') 
                    (not= a b))))))

(defn supports-tls?
  [[ip-addrs hypernet-seqs]]
  (and (some has-abba? ip-addrs)
       (not-any? has-abba? hypernet-seqs)))

(defn get-babs [ip-addr]
  (->> ip-addr
       (partition 3 1)
       (filter (fn [[a b a']] (and (= a a') (not= a b))))
       (map (fn [[a b _]] [b a b]))
       set))

(defn collect-babs [ip-addrs] (reduce set/union (map get-babs ip-addrs)))

(defn collect-triples [hypernet-seqs]
  (->> hypernet-seqs
       (map (comp set #(partition 3 1 %)))
       (reduce set/union))) 

(defn supports-ssl?
  [[ip-addrs hypernet-seqs]]
  (seq (->> hypernet-seqs
            collect-triples
            (set/intersection (collect-babs ip-addrs)))))

;; Solutions
(def input (reader/get-input-lines 2016 7))
(defn solution-template [pred input] (count (filter pred input)))
(def solve-1 (partial solution-template supports-tls?))
(def solve-2 (partial solution-template supports-ssl?))
(def solve (comp (juxt solve-1 solve-2) parse))

;; Run the solution
;; (time (solve input))
