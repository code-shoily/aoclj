(ns aoclj.year-2019.day-02
  (:require [aoclj.common.reader :as reader]
            [aoclj.year-2019.intcode :refer [parse-intcode
                                             provide-inputs
                                             output
                                             run-cmd]]))

(def input (reader/get-input-str 2019 2))
(defn parse [input] (parse-intcode input))

(defn fix-1202 [input]
  (->> input
       (provide-inputs 12 2)
       run-cmd
       output))

(defn find-inputs-for [desired-output input]
  (first (for [noun (range 1 100)
               verb (range 1 100)
               :let [program (provide-inputs noun verb input)
                     output (output (run-cmd program))]
               :when (= desired-output output)]
           [noun verb])))

(defn solve [input]
  (let [input (parse input)
        solve-1 fix-1202
        solve-2 (comp
                 #(+ (second %) (* 100 (first %)))
                 (partial find-inputs-for 19690720))]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))

