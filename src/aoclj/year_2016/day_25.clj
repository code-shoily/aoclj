(ns
  ^{:title      "Clock Signal",
    :doc        "Module for solving Advent of Code 2016 Day 25 problem.",
    :url        "http://www.adventofcode.com/2016/day/25",
    :difficulty :l,
    :year       2016,
    :day        25,
    :stars      2,
    :tags       [:assembly :pen-and-paper]}
  aoclj.year-2016.day-25
  (:require [aoclj.helpers.io :as io]
            [hyperfiddle.rcf :as rcf]))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(def explanation
  "This involved some manual tinckering as I remembered from this implementation
   https://github.com/code-shoily/aocgo/blob/main/year16/day25/main.go
   
   `jnz <int> <int> indicates infinite loop, only way out of this is incrementing
   a jnz above it (but within a block) that jumps out of it. This property of the 
   code helped me segregate the code in multiple blocks aand coming up with means 
   to assign (instantenous) values to the registers and identify a loop that covers
   the `out b` instruction. This needs to keep sending 1 0 1 0 1 0 ...
   
   TODO: Run a simulator in here and run the investigations in the `comment` form below.

   The loop enclosing the `out` command sets `d` as the value of (+ a 2572) and output
   is a value of binary value of that (aka d). So what we need is, to find the binary
   value of (- d 2572) for d = 1, 2, 3 ... that produces an alternating sequence that
   starts with 1 and ends with 0 (so that the next iteration repeats it).
   "
)

(defn is-alternating?
  [bin]
  (->> bin
       (partitionv 2)
       distinct
       (= (list [\1 \0]))))

(defn find-first-alternating
  [a-val]
  (letfn [(as-binary [val] (Integer/toString val 2))]
    (loop [iter 0]
      (if (-> a-val
              (+ iter)
              as-binary
              is-alternating?)
        iter
        (recur (inc iter))))))

(defn solve [_] [(find-first-alternating 2572) :🎉])

(comment
  "<Explore>"
  (def raw-input (io/read-input-data 2016 25))

  (time (solve raw-input))
  "</Explore>"
)

(rcf/tests
 (def input (io/read-input-data 2016 25))
 (solve input)
 :=
 [158 :🎉])
