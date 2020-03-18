(ns org.chrisreyes.app
  (:require
    [reagent.dom]
    [reagent.core]
    ["styletron-engine-atomic" :rename {Client StyletronClient}]
    ["styletron-react" :refer (styled) :rename {Provider StyletronProvider}]))

(def engine
  (new StyletronClient))

(defn App []
  [:div
   [:> StyletronProvider
    {:value engine}
    [:div "Hello World"]]])
 
(defn ^:dev/after-load start []
  (reagent.dom/render App
                      (.getElementById js/document "root")))

(defn init []
  (start))
