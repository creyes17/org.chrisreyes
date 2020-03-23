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

(ns org.chrisreyes.component.menu
  (:require
    [org.chrisreyes.style.theme :as theme]
    [reagent.core]
    ["react-router-dom" :as router]))

(def site-title
  (theme/styled
    router/NavLink
    (fn [clj-props current-theme]
      #js{:color (:opaque (:primary (:color current-theme)))
          :textDecoration "none"})))

(defn make-active-classname
  ; This is going to get a little complicated...
  ; Idea is to create a partial in app.cljs which populates
  ; the styletron-client.
  ; Then this function should be passed as an argument to
  ; the Menu component below.
  ; That Menu component should pass this function back
  ; through to create the actual styled NavLinks.
  [styletron-client current-theme]
  (.renderStyle
    styletron-client
    #js{:color (:opaque
                 (:secondary
                   (:color current-theme)))}))

(defn nav-link-template
  [make-classname]
  (theme/with-theme
    (fn [props & children]
      (into
        [:> router/NavLink
         (conj props
               {:activeClassName
                (make-classname (js->clj (:$theme props)
                                         :keywordize-keys true))})
         children]))))

(defn Menu
  "The Site navigation menu. Takes a function 'make-classname'
  that takes two arguments [clj-props current-theme] which will
  be passed through to theme/styled."
  [make-classname]
  (let [nav-link (nav-link-template make-classname)]
    [:nav
     [:ul
      [:li
       [site-title {:to "/"} "Chris Reyes"]]
      [:li
       [nav-link {:to "/"} "About"]]
      [:li
       [nav-link {:to "/support/"} "Support"]]]]))
