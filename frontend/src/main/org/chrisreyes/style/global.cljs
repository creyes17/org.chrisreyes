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

(ns org.chrisreyes.style.global
  (:require
    [org.chrisreyes.style.theme :as theme]
    ["react-router-dom" :as router]))

(defn global-style-inner [props]
  (let [colors (:color
                 (:$theme
                   (js->clj
                     props
                     :keywordize-keys
                     true)))]
  [:style (str "body {background-color: "
               (:background colors)
               "; color: "
               (:background-contrast colors)
               ";}")]))

(def global-style
  (theme/with-theme global-style-inner))
