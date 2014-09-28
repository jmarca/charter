(defproject charter "0.1.0-SNAPSHOT"
  :description "chart knit stitches"
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"
                  :exclusions [org.apache.ant/ant]]
                 [instaparse "1.3.4"]
                 [hiccup "1.0.4"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-ring "0.8.7"]]

  :cljsbuild {
    :builds [{:source-paths ["src-cljs"]
              :compiler {:output-to "resources/public/js/main.js"
                         :optimizations :whitespace
                         :pretty-print true
                         :source-tree true}}]}
  :ring {:handler example.routes/app})
