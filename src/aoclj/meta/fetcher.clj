(ns aoclj.meta.fetcher
  (:require [babashka.http-client :as http]
            [reaver :refer [parse extract-from text]]))


(defn fetch-problem-title
  "Fetches the title of the problem from advent of code page. `nil` if there
   was a problem fetching the title."
  [year day]
  (try
    (last (re-find
           #"--- Day \d+: (.+) ---"
           (-> "https://www.adventofcode.com/%d/day/%d"
               (format year day)
               http/get
               :body
               parse
               (extract-from ".day-desc > h2" [] "h2" text)
               first)))
    (catch Exception _ nil)))
