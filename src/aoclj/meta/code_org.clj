(ns aoclj.meta.code-org
  (:require
   [aoclj.utils :as utils]
   [clojure.java.io :as io]))

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

(defn- stub-creator
  [year day path-getter]
  (let [file-obj (path-getter year day)
        file-name (.getPath file-obj)]
    (if (.exists file-obj)
      (prn (format "File %s already exists" file-name))
      (do
        (spit file-obj "")
        (prn (format "File %s created" file-name))))))

(defn create-source-stub
  [year day]
  (stub-creator year day get-source-path))

(defn create-test-stub
  [year day]
  (stub-creator year day get-test-path))

(defn create-input-stub
  [year day]
  (stub-creator year day get-input-path))
