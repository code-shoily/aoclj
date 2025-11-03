(ns aoclj.meta.doc-renderer
  (:require [aoclj.meta.stats :as stats]
            [aoclj.helpers.meta :refer
             [aoc-years get-padded-day total-stars total-trophies]]
            [aoclj.helpers.matrix :refer [transpose]]
            [selmer.parser :as parser]
            [clojure.string :as str]))

;; ----------------------------------------------------- Protocols
(defprotocol IRender
  (render [this]))

(defprotocol IGenerator
  (generate [this]))

;; ----------------------------------------------------- Common Markdown
;; Renderers
(defn render-md-link [name url] (format "[%s](%s)" name url))
(defn render-star
  [num]
  (case num
    1 ":star:"
    2 ":star2: :star2:"
    ":skull:"))

(defn render-trophy
  [num]
  (case num
    1 ":2nd_place_medal:"
    2 ":1st_place_medal:"
    " "))

(defn render-difficulty
  [level]
  (let [repetition (case level
                     :xs 1
                     :s  2
                     :m  3
                     :l  4
                     xl  5
                     0)]
    (str/join " " (repeat repetition ":snowflake:"))))

(defn code-metadata
  [year day]
  (let [padded-day      (get-padded-day day)
        source-filename (format "day_%s.clj" padded-day)
        source-path     (format "/src/aoclj/year_%s/%s" year source-filename)]
    {:name    source-filename,
     :path    source-path,
     :md-link (render-md-link source-filename source-path)}))

(defn source-md-link [year day] ((code-metadata year day) :md-link))

(defn render-navlinks
  [year]
  (letfn [(render-single-year [year']
            (if (= year' year)
              year
              (render-md-link (format "Year %s" year')
                              (format "/src/aoclj/year_%s/" year'))))]
    (->> aoc-years
         (mapv render-single-year)
         (str/join " | "))))

;; ----------------------------------------------------- RECORDS
(defrecord YearwiseRow [title year day url tags stars difficulty]
  IRender
    (render [{:keys [year day title url tags stars difficulty]}]
      (letfn [(formatted-link [year day]
                (format (source-md-link year day)))
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
      (let [header (str/join "\n"
                             ["| Day | Difficulty | Stars | Links | Tags |"
                              "|:---: | :---: | :---: | :---: | :----: |"])]
        (->> solutions
             (map (comp render map->YearwiseRow))
             (cons header)
             (str/join "\n")))))

(defrecord YearwisePage [template-file year]
  IRender
    (render [{:keys [template-file year]}]
      (let [summary   (stats/summarize year)
            nav-links (render-navlinks year)
            table     (render (map->YearwiseTable summary))]
        (parser/render-file (format "templates/%s.md" template-file)
                            {:year      year,
                             :nav-links nav-links,
                             :stars     (:stars summary),
                             :table     table})))
  IGenerator
    (generate [{:keys [year], :as this}]
      (->> this
           render
           (spit (format "src/aoclj/year_%s/README.md" year)))))

(defn generate-solution-matrix
  "Get a score mapping of all data in a matrix format"
  []
  (let [completed (->> aoc-years
                       (map stats/summarize)
                       (mapcat (fn [{:keys [solutions]}]
                                 (map (juxt :year :day :stars) solutions)))
                       (group-by (juxt first second))
                       (map (fn [[k [[_ _ v]]]] [k v]))
                       (into {}))]
    (->> (range 1 26)
         (map #(map (fn [year] (get completed [year %] 0)) aoc-years)))))

(defn render-table-md
  "Render the data only part of the table as markdown"
  []
  (let [solution-matrix (generate-solution-matrix)
        yearwise-total  (->> solution-matrix
                             transpose
                             (map #(reduce + %)))
        to-table-row    #(->> %
                              (str/join " | ")
                              (format "| %s |"))]
    (->> solution-matrix
         (map-indexed (fn [idx row] (cons (inc idx) (map render-trophy row))))
         (cons (cons ":star:" yearwise-total))
         (map to-table-row)
         (str/join "\n"))))

(defn get-completion-metrics
  []
  (let [total-stars    total-stars
        total-trophies total-trophies]
    (as-> aoc-years x
      (mapv (comp #(select-keys % [:stars :completed]) stats/summarize) x)
      (reduce (partial merge-with +) x)
      (assoc x :target-stars (- total-stars (:stars x)))
      (assoc x :target-trophies (- total-trophies (:completed x)))
      (merge x {:total-stars total-stars, :total-trophies total-trophies}))))

(defrecord Readme [template-file]
  IRender
    (render [{:keys [template-file]}]
      (let [table (render-table-md)
            completion-metrics (get-completion-metrics)]
        (-> (format "templates/%s.md" template-file)
            (parser/render-file (merge completion-metrics {:table table})))))
  IGenerator
    (generate [this]
      (->> this
           render
           (spit "README.md"))))

;; ----------------------------------------------------- Public API for
;; generating READMEs

(defn generate-yearwise-readmes
  "Generates year_<year>/README.md with updated status"
  []
  (->> aoc-years
       (mapv #(->YearwisePage "yearwise_readme" %))
       (mapv generate)))

(defn generate-readme
  "Generates the main README"
  []
  (generate (->Readme "readme")))

(comment
  "<Explore>"
  (generate-solution-matrix)
  (generate-yearwise-readmes)
  (generate-readme)
  "</Explore>")
