(ns aoclj.meta.doc-renderer
  (:require [aoclj.meta.stats :as stats]
            [aoclj.utils :as utils]
            [clostache.parser :as parser]
            [clojure.string :as str]))

;; ----------------------------------------------------- Protocols
(defprotocol IRender
  (render [this]))

(defprotocol IGenerator
  (generate [this]))

;; ----------------------------------------------------- Common Markdown Renderers
(defn render-md-link [name url] (format "[%s](%s)" name url))
(defn render-star [num]
  (case num
    1 ":star:"
    2 ":star2: :star2:"
    ":skull:"))

(defn render-trophy [num]
  (case num
    1 ":2nd_place_medal:"
    2 ":1st_place_medal:"
    " "))

(defn render-difficulty [level]
  (let [repetition (case level :xs 1, :s 2, :m 3, :l 4, xl 5, 0)]
    (str/join " " (repeat repetition ":snowflake:"))))

(defn code-metadata [year day type]
  (let [padded-day (utils/get-padded-day day)
        source-filename (format "day_%s.clj" padded-day)
        source-path (format "/src/aoclj/year_%s/%s" year source-filename)
        test-filename (format "day_%s_test.clj" padded-day)
        test-path (format "/test/aoclj/year_%s/%s" year test-filename)]
    (case type
      :source {:name source-filename
               :path source-path
               :md-link (render-md-link source-filename source-path)}
      :test {:name test-filename
             :path test-path
             :md-link (render-md-link test-filename test-path)})))

(defn source-md-link [year day] ((code-metadata year day :source) :md-link))

(defn test-md-link [year day] ((code-metadata year day :test) :md-link))

(defn render-navlinks
  [year]
  (letfn [(render-single-year [year'] (if (= year' year)
                                        year
                                        (render-md-link (format "Year %s" year')
                                                        (format "/src/aoclj/year_%s/" year'))))]
    (->> utils/aoc-years
         (mapv render-single-year)
         (str/join " | "))))

;; ----------------------------------------------------- RECORDS
(defrecord YearwiseRow [title year day url tags stars difficulty]
  IRender
  (render [{:keys [year day title url tags stars difficulty]}]
    (letfn [(formatted-link
              [year day]
              (format ":small_orange_diamond: %s <br /> :small_orange_diamond: %s"
                      (source-md-link year day)
                      (test-md-link year day)))
            (render-tags [tags] (str/join "," (mapv name tags)))]
      (format "[%s](%s)|%s|%s|%s|%s"
              title
              url
              (render-difficulty difficulty)
              (render-star stars)
              (formatted-link year day)
              (render-tags tags)))))

(defrecord YearwiseTable [completed stars solutions]
  IRender
  (render [{:keys [solutions]}]
    (let [header (str/join
                  "\n"
                  ["| Day | Difficulty | Stars | Links | Tags |"
                   "|:---: | :---: | :---: | :--- | :----: |"])]
      (->> solutions
           (map (comp render map->YearwiseRow))
           (cons header)
           (str/join "\n")))))

(defrecord YearwisePage [template-file year]
  IRender
  (render [{:keys [template-file year]}]
    (let [summary (stats/summarize year)
          nav-links (render-navlinks year)
          table (render (map->YearwiseTable summary))]
      (parser/render-resource
       (format "templates/%s.md" template-file)
       {:year year :nav-links nav-links :stars (:stars summary) :table table})))
  IGenerator
  (generate [{:keys [year] :as this}]
    (->> this
         render
         (spit (format "src/aoclj/year_%s/README.md"
                       year)))))

(defn generate-solution-matrix
  "Get a score mapping of all data in a matrix format"
  []
  (let [completed (->> utils/aoc-years
                       (map stats/summarize)
                       (mapcat (fn [{:keys [solutions]}] (map (juxt :year :day :stars)
                                                              solutions)))
                       (group-by (juxt first second))
                       (map (fn [[k [[_ _ v]]]] [k v]))
                       (into {}))]
    (->> (range 1 26)
         (map #(map (fn [year] (get completed [year %] 0))
                    utils/aoc-years)))))

(defn render-table-md
  "Render the data only part of the table as markdown"
  []
  (let [solution-matrix (generate-solution-matrix)
        yearwise-total (->> solution-matrix utils/transpose (map #(reduce + %)))
        to-table-row #(->> % (str/join " | ") (format "| %s |"))]
    (->> solution-matrix
         (map-indexed (fn [idx row] (cons (inc idx) (map render-trophy row))))
         (cons (cons ":star:" yearwise-total))
         (map to-table-row)
         (str/join "\n"))))

(defn get-completion-metrics
  []
  (let [total-stars utils/total-stars
        total-trophies utils/total-trophies]
    (as-> utils/aoc-years x
      (mapv (comp #(select-keys % [:stars :completed]) stats/summarize) x)
      (reduce (partial merge-with +) x)
      (assoc x :target-stars (- total-stars (:stars x)))
      (assoc x :target-trophies (- total-trophies (:completed x)))
      (merge x {:total-stars total-stars :total-trophies total-trophies}))))

(defrecord
 Readme
 [template-file]
  IRender
  (render [{:keys [template-file]}]
    (let [table (render-table-md)
          completion-metrics (get-completion-metrics)]
      (-> (format "templates/%s.md" template-file)
          (parser/render-resource (merge completion-metrics {:table table})))))
  IGenerator
  (generate [this]
    (->> this
         render
         (spit "README.md"))))

;; ----------------------------------------------------- Public API for generating READMEs

(defn generate-yearwise-readmes
  "Generates year_<year>/README.md with updated status"
  []
  (->> utils/aoc-years
       (mapv #(->YearwisePage "yearwise_readme" %))
       (mapv generate)))

(defn generate-readme
  "Generates the main README"
  []
  (generate (->Readme "readme")))

(comment "<Explore>"
         (generate-solution-matrix)
         (generate-yearwise-readmes)
         (generate-readme)
         "</Explore>")