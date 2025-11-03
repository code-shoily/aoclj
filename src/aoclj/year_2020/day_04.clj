(ns
  ^{:title      "Passport Processing",
    :doc        "Module for solving Advent of Code 2020 Day 4 problem.",
    :url        "http://www.adventofcode.com/2020/day/4",
    :difficulty :xs,
    :year       2020,
    :day        4,
    :stars      2,
    :tags       [:schema :validation]}
  aoclj.year-2020.day-04
  (:require [aoclj.utils :as utils]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests]]))

(s/def ::passport-v1
  (s/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
          :opt-un [::cid]))

(s/def :v2/byr (s/and int? #(<= 1920 % 2002)))
(s/def :v2/iyr (s/and int? #(<= 2010 % 2020)))
(s/def :v2/eyr (s/and int? #(<= 2020 % 2030)))
(s/def :v2/hgt
  (s/and (s/tuple int? string?)
         (fn [[v u]]
           (case u
             "cm" (<= 150 v 193)
             "in" (<= 59 v 76)
             false))))
(s/def :v2/hcl
  (s/and #(= \# (first %))
         #(every? (fn [ch]
                    (or (Character/isDigit ch) (#{\a \b \c \d \e \f} ch)))
                  (rest %))))

(s/def :v2/ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def :v2/pid (s/and #(= (count %) 9) #(every? Character/isDigit %)))

(s/def ::passport-v2
  (s/keys :req-un [:v2/byr
                   :v2/iyr
                   :v2/eyr
                   :v2/hgt
                   :v2/hcl
                   :v2/ecl
                   :v2/pid]
          :opt-un [:v2/cid]))

(defn str->hgt
  [line]
  (->> (re-find #"(\d+)(in|cm)" line)
       (#(vector (parse-long (second %)) (last %)))))

(defn map->passport
  [{:keys [byr iyr eyr hgt], :as line}]
  (try (merge line
              {:byr (parse-long byr),
               :iyr (parse-long iyr),
               :eyr (parse-long eyr),
               :hgt (str->hgt hgt)})
       (catch Exception _ line)))

(defn as-map
  [line]
  (->> (str/split line #" ")
       (map #(str/split % #":"))
       (into {})
       (#(update-keys % (partial keyword)))
       map->passport))

(defn parse
  [input]
  (->> (str/split input #"\n\n")
       (map (comp as-map #(str/replace % #"\n" " ")))
       (filter (partial s/valid? ::passport-v1))
       (map map->passport)))

(defn validate-all-for
  [validator]
  #(count (filter (partial s/valid? validator) %)))

(def part-1 (validate-all-for ::passport-v1))

(def part-2 (validate-all-for ::passport-v2))

(def solve (utils/generic-solver part-1 part-2 parse))

(comment
  "<Explore>"
  (def input-data (utils/read-input-data 2020 4))
  (def input (parse input-data))
  input
  (time (solve input-data))
  "</Explore>")

(tests
 (solve (utils/read-input-data 2020 4))
 :=
 [233 111])
