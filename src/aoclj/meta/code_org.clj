(ns aoclj.meta.code-org
  (:require [aoclj.meta.fetcher :as fetcher]
            [aoclj.utils :as utils]
            [clojure.java.io :as io]
            [selmer.parser :as parser]))

(defn- get-source-path
  [year day]
  (io/file "src"
           "aoclj"
           (str "year_" year)
           (str "day_"
                (utils/get-padded-day day)
                ".clj")))

(defn get-input-path
  [year day]
  (io/file "resources"
           "inputs"
           (str year "_" (utils/get-padded-day day) ".txt")))

(defn- render-content-for
  [year day template & {:keys [title], :or {title ""}}]
  (let [padded-day (utils/get-padded-day day)]
    (parser/render-file
     template
     {:year year, :day day, :padded-day padded-day, :title title})))

(defn render-source-content
  [year day]
  (let [title (fetcher/fetch-problem-title year day)]
    (render-content-for year day "templates/source.txt" :title title)))

(defn- stub-creator
  [year day path-getter renderer]
  (let [file-obj  (path-getter year day)
        file-name (.getPath file-obj)
        content   (renderer year day)]
    (if (.exists file-obj)
      (do (prn (format "File %s already exists" file-name)) false)
      (do (spit file-obj content)
          (prn (format "File %s created" file-name))
          true))))

(defn create-source-stub
  [year day]
  (stub-creator year day get-source-path render-source-content))

(defn create-input-stub
  [year day]
  (stub-creator year day get-input-path fetcher/get-input-data))

(defn create-solution-stub
  "Creates the stubs for input, source, test if they aren't create already"
  [year day]
  (let [input-created?  (create-input-stub year day)
        source-created? (create-source-stub year day)]
    {:input input-created?, :source source-created?}))
