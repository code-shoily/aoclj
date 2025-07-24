(ns aoclj.meta.code-org
  (:require
   [clojure.java.io :as io]
   [clostache.parser :as parser]
   [aoclj.utils :as utils]))

(defn- build-file-path [prefix year day]
  (io/file
   prefix
   "aoclj"
   (str "year_" year)
   (str
    "day_"
    (utils/get-padded-day day)
    (if (= prefix "test") "_test" "")
    ".clj")))

(def get-source-path (partial build-file-path "src"))
(def get-test-path (partial build-file-path "test"))

(defn get-input-path
  [year day]
  (io/file "resources"
           "inputs"
           (str year "_" (utils/get-padded-day day) ".txt")))

(defn- render-content-for
  [year day template]
  (let [padded-day (utils/get-padded-day day)]
    (parser/render-resource
     template
     {:year year :day day :padded-day padded-day})))

(defn render-source-content
  [year day]
  (render-content-for year day "templates/source.txt"))

(defn render-test-content
  [year day]
  (render-content-for year day "templates/test.txt"))

(defn fetch-input-content [_ _] "")

(defn- stub-creator
  [year day path-getter renderer]
  (let [file-obj (path-getter year day)
        file-name (.getPath file-obj)
        content (renderer year day)]
    (if (.exists file-obj)
      (prn (format "File %s already exists" file-name))
      (do
        (spit file-obj content)
        (prn (format "File %s created" file-name))))))

(defn create-source-stub
  [year day]
  (stub-creator year day get-source-path render-source-content))

(defn create-test-stub
  [year day]
  (stub-creator year day get-test-path render-test-content))

(defn create-input-stub
  [year day]
  (stub-creator year day get-input-path fetch-input-content))

