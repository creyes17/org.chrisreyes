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
