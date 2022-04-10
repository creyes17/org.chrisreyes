; org.chrisreyes - The source code for https://chrisreyes.org
; Copyright (C) 2020  Christopher R. Reyes
; 
; This program is free software: you can redistribute it and/or modify
; it under the terms of the GNU General Public License as published by
; the Free Software Foundation, either version 3 of the License, or
; (at your option) any later version.
; 
; This program is distributed in the hope that it will be useful,
; but WITHOUT ANY WARRANTY; without even the implied warranty of
; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
; GNU General Public License for more details.
; 
; You should have received a copy of the GNU General Public License
; along with this program.  If not, see <http://www.gnu.org/licenses/>.

(ns org.chrisreyes.app
  (:require
    [org.chrisreyes.component.menu :as menu]
    [org.chrisreyes.component.oauth :as oauth]
    [org.chrisreyes.content.about :refer (about-section)]
    [org.chrisreyes.content.contact :refer (contact-section)]
    [org.chrisreyes.content.support :refer (support-section)]
    [org.chrisreyes.style.global :refer (global-style)]
    [org.chrisreyes.style.theme :as theme]
    [reagent.dom]
    [reagent.core]
    ["react-router-dom" :as router]
    ["styletron-engine-atomic" :rename {Client StyletronClient}]
    ["styletron-react" :rename {Provider StyletronProvider}]))

(def engine
  (new StyletronClient))

;TODO: Load routes from data (AWS or local file or something)

(def main
  (theme/styled
    "main"
    (fn [clj-props current-theme]
      #js{:display "block"
          :marginLeft "auto"
          :marginRight "auto"
          :width "75%"})))

(def skip-link
  (theme/styled
    "a"
    (fn [clj-props current-theme]
      (let [visible-props #js{:height "auto"
                              :left 0
                              :overflow "visible"
                              :top 0
                              :width "auto"}]
      #js{":active" visible-props
          ":focus" visible-props
          ":hover" visible-props
          :height "1px"
          :left "-1000px"
          :overflow "hidden"
          :position "absolute"
          :top "-1000px"
          :width "1px"}))))

(def App
  [:<>
   [theme/ThemeProvider
    [:> StyletronProvider
     {:value engine}
     [global-style]
     [skip-link {:href "#content"} "Skip to Content"]
     [:> router/BrowserRouter
      [menu/Menu (partial menu/make-active-classname engine)]
      [main {:id "content"}
       [:> router/Route {:path "/"
                         :exact true
                         :component (reagent.core/reactify-component
                                      about-section)}]
       [:> router/Route {:path "/support"
                         :component (reagent.core/reactify-component
                                      support-section)}]
       [:> router/Route {:path "/contact"
                         :component (reagent.core/reactify-component
                                      contact-section)}]]]]]])

(defn ^:dev/after-load start []
  (reagent.dom/render App
                      (.getElementById js/document "root")))

(defn init []
  (start))
