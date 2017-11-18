(defproject abitti-liitetiedostot "0.1.0-SNAPSHOT"
  :description "Utility for adding attachments to Abitti-exams"
  :url "http://MattiNieminen.github.io/abitti-liitetiedostot"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0-beta4"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.8.0-alpha2"]]
  :plugins [[lein-figwheel "0.5.14"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]
  :source-paths ["src"]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {:open-urls ["http://localhost:3449/index.html"]}
                :compiler {:main abitti-liitetiedostot.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/abitti_liitetiedostot.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/abitti_liitetiedostot.js"
                           :main abitti-liitetiedostot.core
                           :optimizations :advanced
                           :pretty-print false}}]}
  :figwheel {:css-dirs ["resources/public/css"]}
  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.4"]]
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
