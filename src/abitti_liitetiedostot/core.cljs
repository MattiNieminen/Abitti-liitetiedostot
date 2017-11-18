(ns abitti-liitetiedostot.core
    (:require [reagent.core :as r]))

(defonce state (r/atom {:text "Hello world!"}))


(defn hello-world []
  [:div
   [:h1 (:text @state)]
   [:h3 "Edit this and watch it change!"]])

(r/render-component [hello-world]
                    (. js/document (getElementById "app")))
