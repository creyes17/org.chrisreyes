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
    [org.chrisreyes.component.menu :refer (Menu)]
    [org.chrisreyes.style.global :refer (global-style)]
    [org.chrisreyes.style.theme :as theme]
    [reagent.dom]
    [reagent.core]
    ["react-router-dom" :as router]
    ["styletron-engine-atomic" :rename {Client StyletronClient}]
    ["styletron-react" :rename {Provider StyletronProvider}]))

(def engine
  (new StyletronClient))

(def TestLink
  (theme/styled
    "a"
    (fn [clj-props current-theme]
      #js{:backgroundColor (:$background
                             clj-props
                             (:primary
                               (:color current-theme {})
                               "black"))
          :color (:$color
                   clj-props
                   (:primaryContrast
                     (:color current-theme {})
                     "purple"))
          :display "block"
          :padding "1vh"
          :textAlign "center"})))

(def App
  [:<>
   global-style
   [theme/ThemeProvider
    [:> StyletronProvider
     {:value engine}
     [TestLink {"$background" "yellow"
                "$color" "black"}
      "Black and Yellow"]
     [TestLink "Default Styling"]
     [:> router/BrowserRouter
      [Menu]]]]])

(defn ^:dev/after-load start []
  (reagent.dom/render App
                      (.getElementById js/document "root")))

(defn init []
  (start))
