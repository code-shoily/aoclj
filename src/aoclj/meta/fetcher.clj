(ns aoclj.meta.fetcher
  (:require [babashka.http-client :as http]
            [reaver :refer [parse extract-from text]]))

(defonce session-key (System/getenv "AOC_SESSION_KEY"))

(defn fetch-problem-title
  "Fetches the title of the problem from advent of code page. `nil` if there
   was a problem fetching the title."
  [year day]
  (try (last (re-find #"--- Day \d+: (.+) ---"
                      (-> "https://www.adventofcode.com/%d/day/%d"
                          (format year day)
                          http/get
                          :body
                          parse
                          (extract-from ".day-desc > h2" [] "h2" text)
                          first)))
       (catch Exception _ "[Could not fetch title]")))

(defn get-input-data
  [year day]
  (try (-> "https://adventofcode.com/%d/day/%d/input"
           (format year day)
           (http/get {:headers {:cookie (format "session=%s" session-key)}})
           :body)
       (catch Exception _ "[Could not fetch input]")))

