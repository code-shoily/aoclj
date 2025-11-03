(ns
  ^{:title      "Disk Fragmenter",
    :doc        "Module for solving Advent of Code 2024 Day 9 problem.",
    :url        "http://www.adventofcode.com/2024/day/9",
    :difficulty :l,
    :year       2024,
    :day        9,
    :stars      2,
    :tags       [:revisit :two-pointer]}
  aoclj.year-2024.day-09
  (:require [aoclj.helpers.io :as io]
            [clojure.string :as str]
            [medley.core :as m]
            [hyperfiddle.rcf :as rcf]))

;; Thanks to https://adventofcode.com/2024/day/9#part2 <3 TIL: `reduce-kv`

(defn parse
  [input]
  (let [digits (map #(- (int %) (int \0)) (str/trim input))]
    (loop [[hd & tl] digits
           id        0
           file?     true
           disk      []]
      (cond
        (nil? hd) disk
        file?     (recur tl (inc id) false (into disk (repeat hd id)))
        :else     (recur tl id true (into disk (repeat hd nil)))))))

(defn defragment
  [disk]
  (loop [fi  0
         bi  (dec (count disk))
         res []]
    (cond
      (> fi bi) res
      (disk fi) (recur (inc fi) bi (conj res (disk fi)))
      (disk bi) (recur (inc fi) (dec bi) (conj res (disk bi)))
      :else     (recur fi (dec bi) res))))

(defn calc-checksum
  [disk]
  (reduce-kv (fn [acc idx id] (+ acc (* idx id))) 0 disk))

(defn part-1
  "Solve part 1 -"
  [input]
  (calc-checksum (defragment input)))

(defn block-sizes
  [disk]
  (let [blocks (partition-by identity disk)]
    (reduce
     (fn [{:keys [idx], :as acc} block]
       (let [id   (first block)
             size (count block)]
         (if id
           (-> acc
               (assoc-in [:files idx] [id size])
               (update :idx + size))
           (-> acc
               (assoc-in [:spaces idx] size)
               (update :idx + size)))))
     {:files  (sorted-map),
      :spaces (sorted-map),
      :idx    0}
     blocks)))

(defn find-space
  [spaces file-idx file-size]
  (->> spaces
       (take-while (fn [[space-idx _]] (< space-idx file-idx)))
       (m/find-first (fn [[_ space-size]] (>= space-size file-size)))))

(defn defragment-2
  [disk]
  (let [{:keys [files spaces]} (block-sizes disk)]
    (loop [[[idx [id size]] & tl] (rseq files)
           files  files
           spaces spaces]
      (if (nil? idx)
        files
        (if-let [[space-idx space-size] (find-space spaces idx size)]
          (let [spaces' (cond-> (dissoc spaces space-idx)
                          (> space-size size) (assoc (+ space-idx size)
                                                     (- space-size size)))
                files'  (-> files
                            (dissoc idx)
                            (assoc space-idx [id size]))]
            (recur tl files' spaces'))
          (recur tl files spaces))))))

(defn calc-checksum-2
  [disk]
  (reduce-kv
   (fn [acc idx [id size]]
     (reduce (fn [acc i]
               (+ acc (* id (+ idx i))))
             acc
             (range size)))
   0
   disk))

(defn part-2
  [input]
  (calc-checksum-2 (defragment-2 input)))

(def solve (io/generic-solver part-1 part-2 parse))

(rcf/tests
 (def input-data (io/read-input-data 2024 9))
 (solve input-data)
 :=
 [6446899523367 6478232739671])

(comment
  "<Explore>"
  (def raw-input
    (io/read-input-data 2024 9))

  (def input (parse raw-input))

  (def input (parse raw-input))

  (->> input)

  (time (solve raw-input))
  "</Explore>")
