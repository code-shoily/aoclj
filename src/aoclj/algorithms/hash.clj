(ns aoclj.algorithms.hash
  (:require [digest]))

(defn md5
  "Returns md5 hash. Currently using digest but keeping it here for easy
   refactoring later."
  [^String s]
  (digest/md5 s))