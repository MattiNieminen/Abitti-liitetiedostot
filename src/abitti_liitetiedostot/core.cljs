(ns abitti-liitetiedostot.core
    (:require [reagent.core :as r]))

(defonce state (r/atom {}))

(defn header-view []
  [:div.header
   [:h1.header__title "Matin ultimaattinen Abitti-apuohjelma"]])

(defn attachments-view [{:keys [attachments]}]
  (if-not (empty? attachments)
    [:div.attachments
     [:h2.attachments__header "Valitut liitteet"]
     [:ul.attachments-list
      (for [[idx itm] (map-indexed vector attachments)]
        [:li.attachments-list__item {:key idx} (:filename itm)])]]))

(defn content-view []
  (let [{:keys [exam attachments]} @state]
    [:div.content
     [:p.content__text
      "Lataa koe ensin Abitista omalle koneellesi, eli valitse \"Siirrä koe (.zip)\""]
     [:p.content__text
      "Klikkaa sitten alla olevaa nappia, ja valitse juuri lataamasi zip-tiedosto (Transfer_sinun_kokeesi_nimi.zip)."]
     [:input.content__file
      {:type "file"
       :on-change (fn [e]
                    (let [f (aget (-> e .-target .-files) 0)]
                      (swap! state assoc :exam {:file f
                                                :filename (.-name f)})))}]
     [:p.content__text
      "Klikkaa alla olevaa nappia, ja valitse kaikki liitetiedostot jotka haluat liittää kokeeseen. Voit valita useamman tiedoston pitämällä näppämistön CMD-nappia tai CTRL-nappia pohjassa"]
     [:input.content__file
      {:type "file"
       :multiple true
       :on-change (fn [e]
                    (let [files (-> e .-target .-files)]
                      (swap! state
                             assoc
                             :attachments
                             (for [idx (range (.-length files))
                                   :let [f (aget files idx)]]
                               {:file f
                                :filename (.-name f)}))))}]
     [attachments-view {:attachments attachments}]
     [:p.content__text
      "Klikkaa alla olevaa nappia ladataksesi uuden zip-tiedoston koneellesi, jonka voit tuoda takaisin Abittiin klikkaamalla \"Tuo koe (.zip)\" -nappia. Valitset vain tuolloin Abitista tiedoston jonka saat klikkaamalla alla olevaa nappia."]
     (if (empty? exam)
       [:p.content__text.content__text--warning
        "Et ole vielä valinnut Abitista tuotua zip-tiedostoa"])
     (if (empty? attachments)
       [:p.content__text.content__text--warning
        "Et ole vielä valinnut liitteitä"])
     [:button.content__button
      {:disabled (and (empty? exam) (empty? attachments))}
      "Lataa valmis koe zip-tiedostona Abittiin viemistä varten"]]))

(defn main-view []
  [:div.main
   [header-view]
   [content-view]])

(r/render-component [main-view]
                    (. js/document (getElementById "app")))
