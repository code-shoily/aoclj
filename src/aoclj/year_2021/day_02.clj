(ns aoclj.year-2021.day-02
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2021 2))

(defn parse [input]
  (map (comp (fn [[a b]] [a (Integer/parseInt b)])
             #(str/split % #" "))
       input))

(defn move-1 [course [cmd x]]
  (case cmd
    "forward" (merge-with + course {:horiz x})
    "up" (merge-with - course {:depth x})
    "down" (merge-with + course {:depth x})))

(defn move-2 [{:keys [aim] :as course} [cmd x]]
  (case cmd
    "forward" (merge-with + course {:horiz x :depth (* aim x)})
    "up" (merge-with - course {:aim x})
    "down" (merge-with + course {:aim x})))

(defn pilot [move-fn init input]
  (as-> input $
    (reduce move-fn init $)
    (select-keys $ [:horiz :depth])
    (vals $)
    (apply * $)))

(defn solve [input]
  (let [input (parse input)
        init {:horiz 0 :depth 0 :aim 0}
        solve-1 (pilot move-1 init input)
        solve-2 (pilot move-2 init input)]
    {1 solve-1 2 solve-2}))

(time (solve input))