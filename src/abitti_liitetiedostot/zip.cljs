(ns abitti-liitetiedostot.zip
  (:require [cljsjs.jszip]))

(defn create-zip []
  (js/JSZip.))

(defn import-zip [file]
  (.loadAsync (create-zip) file))

(defn add-file [z filename file]
  (.file z filename file))

(defn ->base64 [z]
  (.generateAsync z (clj->js {:type "base64"})))

;; TODO clean promises
(defn abitti-with-attachments [exam attachments]
  (let [zipped-attachments (create-zip)]
    (doseq [a attachments]
      (add-file zipped-attachments (.-name a) a))
    (.then (import-zip exam)
           (fn [z]
             (.then (->base64 zipped-attachments)
                    (fn [base64]
                      (add-file z "attachments.zip" base64)
                      (.then (->base64 z)
                             (fn [base64]
                               (aset js/window
                                     "location"
                                     (str "data:application/zip;base64,"
                                          base64)))))))))))
