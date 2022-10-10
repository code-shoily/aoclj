(ns aoclj.year-2017.day-02
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2017 2))

(defn parse [input]
  (mapv (comp (fn [xs] (mapv #(Integer/parseInt %) xs))
              #(str/split % #"\t"))
        input))

(defn divides-each-other [xs]
  (first (let [xs (sort xs)
               lim (count xs)]
           (for [i (range lim)
                 j (range (inc i) lim)
                 :let [big (nth xs j) small (nth xs i)]
                 :when (and (not= big 0) (not= small 0)
                            (zero? (mod big small)))]
             (quot big small)))))

(defn solve-1 [input]
  (->> input
       (map (comp #(apply - %)
                  (juxt last first)
                  sort))
       (apply +)))

(defn solve-2 [input]
  (->> input
       (map divides-each-other)
       (apply +)))

(defn solve [input]
  (let [input (parse input)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))

