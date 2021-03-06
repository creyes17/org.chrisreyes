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
  (let [current-theme (:$theme
                        (js->clj
                          props
                          :keywordize-keys
                          true))
        colors (:color current-theme)
        background-colors (:background colors)
        new-link-color (:opaque (:tertiary colors))
        visited-link-color (:opaque (:secondary colors))]
  [:style (str "body {background-color: "
               (:opaque background-colors)
               "; color: "
               (:contrast background-colors)
               "; font-size: "
               (:normal (:size (:font current-theme)))
               ";}\n a:link {color: " new-link-color
               ";}\n a:visited {color: " visited-link-color)]))

(def global-style
  (theme/with-theme global-style-inner))
