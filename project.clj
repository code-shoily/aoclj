(defproject aoclj "0.1.0-SNAPSHOT"
  :description    "Advent of Code solutions in Clojure"
  :url            "https://github.com/code-shoily/aoclj"
  :license        {:name "MIT", :url "https://opensource.org/licenses/MIT"}
  :dependencies   [[org.clojure/clojure "1.12.0"]
                   [org.clojure/core.async "1.6.681"]
                   [org.babashka/http-client "0.4.22"]
                   [dev.weavejester/medley "1.8.1"]
                   [org.clojure/core.match "1.1.0"]
                   [zprint "1.3.0"]
                   [selmer "1.12.62"]
                   [reaver "0.1.3"]]
  :source-paths   ["src"]
  :resource-paths ["resources"]
  :test-paths     ["test"]
  :main           aoclj.core
  :profiles       {:test {:dependencies [[lambdaisland/kaocha "1.91.1392"]]},
                   :bench {:dependencies [[criterium "0.4.4"]]},
                   :dev
                   {:plugins      [[lein-cljfmt "0.9.2"]
                                   [lein-marginalia "0.9.2"]
                                   [cider/cider-nrepl "0.50.2"]],
                    :zprint       {:old? false},
                    :dependencies [[nrepl "1.3.0"]
                                   [cider/cider-nrepl "0.50.2"]]}}
  :plugins        [[lein-zprint "1.3.0"]]
  :aliases        {"test" ["with-profile" "test" "run" "-m" "kaocha.runner"]}
  :repl-options   {:init-ns aoclj.core})
