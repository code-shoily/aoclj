(ns aoclj.common.reader
  (:require [aoclj.common.utils :as u]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- get-file-name
  "Get the input file name for `year` and `day` (i.e. 2020/1.txt)"
  [year day]
  (str year "/" day ".txt"))

(defn get-input-str
  "Reads the input for `day` of `year` and return the content"
  [year day]
  (->> (get-file-name year day)
       io/resource
       slurp))

(defn get-input-lines
  "Reads the input for `day` of `year` and return the lines"
  [year day]
  (str/split-lines (get-input-str year day)))

(defn get-input-ints
  "Reads the input for `day` of `year` and return the lines as numbers"
  [year day]
  (u/to-ints (get-input-lines year day)))
