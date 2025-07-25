(ns aoclj.meta.code-org
  (:require
   [aoclj.meta.fetcher :as fetcher]
   [aoclj.utils :as utils]
   [clojure.java.io :as io]
   [clostache.parser :as parser]))

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
  [year day template & {:keys [title] :or {title ""}}]
  (let [padded-day (utils/get-padded-day day)]
    (parser/render-resource
     template
     {:year year :day day :padded-day padded-day :title title})))

(defn render-source-content
  [year day]
  (let [title (fetcher/fetch-problem-title year day)]
    (render-content-for year day "templates/source.txt" :title title)))

(render-source-content 2016 5)
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
      (do (prn (format "File %s already exists" file-name))
          false)
      (do
        (spit file-obj content)
        (prn (format "File %s created" file-name))
        true))))

(defn create-source-stub
  [year day]
  (stub-creator year day get-source-path render-source-content))

(defn create-test-stub
  [year day]
  (stub-creator year day get-test-path render-test-content))

(defn create-input-stub
  [year day]
  (stub-creator year day get-input-path fetch-input-content))

(defn create-solution-stub
  "Creates the stubs for input, source, test if they aren't create already"
  [year day]
  (let [input-created? (create-input-stub year day)
        source-created? (create-source-stub year day)
        test-created? (create-test-stub year day)]
    {:input input-created?
     :source source-created?
     :test test-created?}))
