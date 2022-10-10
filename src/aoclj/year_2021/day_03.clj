(ns aoclj.year-2021.day-03
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]))

(def input (reader/get-input-lines 2021 3))

(defn parse [input] (map vec input))

(defn freq [direction xs]
  (if (direction (get xs \1 0)
                 (get xs \0 0)) \1 \0))

(defn transposed [bits]
  (apply mapv vector (mapv vec bits)))

(defn to-num [num]
  (as-> num $
    (str/join $)
    (Long/parseLong $ 2)))

(defn compute [direction input]
  (->> input
       (mapv #(direction (frequencies %)))
       to-num))

(def gamma (partial compute (partial freq >)))
(def epsilon (partial compute (partial freq <)))
(defn power-consumption [input] (* (gamma input) (epsilon input)))

(defn cmp [when-0 when-1 default freq]
  (cond
    (when-0 (get freq \1 0) (get freq \0 0)) \0
    (when-1 (get freq \1 0) (get freq \0 0)) \1
    :else default))

(defn histogram [cmp-fn input]
  (->> input
       transposed
       (mapv #(cmp-fn (frequencies %)))
       (map-indexed (fn [a b] [a b]))
       (into {})))

(defn o2-hist [data]  (histogram (partial cmp < > \1) data))
(defn co2-hist [data] (histogram (partial cmp > < \0) data))

(defn keep-only [idx hist input]
  (filterv #(= (get % idx)
               (hist idx))
           input))

(defn get-gas [hist-fn input]
  (reduce
   (fn [[data hist] idx]
     (if (= 1 (count data))
       (reduced (to-num (first data)))
       (let [new-data (keep-only idx hist data)
             new-hist (hist-fn new-data)]
         [new-data new-hist])))
   [input (hist-fn input)]
   (cycle (range 12))))

(def get-o2 (partial get-gas o2-hist))
(def get-co2 (partial get-gas co2-hist))

(defn solve-1 [input]
  (->> input
       transposed
       power-consumption))

(defn solve-2 [input]
  (apply * ((juxt get-o2 get-co2) input)))

(defn solve [input]
  (let [input (parse input)]
    {1 (solve-1 input) 2 (solve-2 input)}))

(time (solve input))