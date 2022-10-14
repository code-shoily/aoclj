;; --- year 2016 Day 4: Security Through Obscurity ---
;; Link: https://adventofcode.com/2016/day/4
;; Solution: [158835 993]
(ns aoclj.year-2016.day-04
  (:require [aoclj.common.reader :as reader]
            [clojure.string :as str]
            [aoclj.common.utils :as u]))

(def input (reader/get-input-lines 2016 4))

(defn parse-room [room]
  (let [room (str/split room #"[-\[\]]")
        [checksum sector-id & rest] (reverse room)]
    {:checksum checksum
     :sector-id (u/to-int sector-id)
     :name (apply str rest)}))

(defn parse [input] (mapv parse-room input))

(defn room-valid? [{:keys [checksum name]}]
  (->> (frequencies name)
       (mapv reverse)
       (group-by first)
       (sort-by first >)
       (mapcat #(sort (map second (second %))))
       (take 5)
       str/join
       (= checksum)))

(defn rotate [ch by]
  (-> (int ch)
      (- (int \a))
      (+ by)
      (mod 26)
      (+ (int \a))
      char))

(defn decipher [{:keys [name sector-id] :as m}]
  (merge m {:name (->> name
                       (map #(rotate % sector-id))
                       str/join)}))

;; Solutions
(defn solve-1 [valid-rooms]
  (->> valid-rooms
       (map :sector-id)
       (apply +)))

(defn solve-2 [valid-rooms]
  (->> valid-rooms
       (map decipher)
       (filter #(= (:name %) "storageobjectnorthpole"))
       first
       :sector-id))

(defn solve [input]
  (let [input (parse input)
        valid-rooms (filter room-valid? input)]
    [(solve-1 valid-rooms) (solve-2 valid-rooms)]))

;; Run the solution
; (time (solve input))

;; REPL Driven
; (comment
; )
