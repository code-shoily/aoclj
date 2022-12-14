(ns aoclj.year-2019.intcode
  (:require [aoclj.common.utils :as u]
            [clojure.string :as str]))

(defn parse-intcode
  "Parses a string code and returns vector of ints"
  [code]
  (u/to-ints (str/split code #",")))

(defn- do-op [op program pointer]
  (let [operand-1 (program (program (inc pointer)))
        operand-2 (program (program (+ 2 pointer)))
        target (program (+ 3 pointer))]
    (assoc program target (op operand-1 operand-2))))

(defn provide-inputs
  "Provides input to the program by inserting to 1st and 2nd cells"
  [noun verb program]
  (-> program
      (assoc 1 noun)
      (assoc 2 verb)))

(defn output
  "Returns the 0-th int which is the output"
  [intcode] 
  (first intcode))

(defn run-cmd
  "Runs the program, if no pointer is given, the start from 0"
  ([program pointer]
   (case (program pointer)
     1 (recur (do-op + program pointer) (+ pointer 4))
     2 (recur (do-op * program pointer) (+ pointer 4))
     99 program))
  ([program] (run-cmd program 0)))
