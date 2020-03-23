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

; TODO: Add flexbox styling
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

; TODO: Add flexbox styling
(def styled-nav-link
  (theme/styled
    router/NavLink
    (fn [clj-props current-theme]
      (let [inactive-color (:contrast (:background (:color current-theme)))]
      (clj->js {
                :color inactive-color
                ":link" {:color inactive-color}
                ":visited" {:color inactive-color}
                })))))

(defn nav-link-template
  [make-classname]
  (theme/with-theme
    (fn [props & children]
      (into
        [styled-nav-link
         (conj props
               {:activeClassName
                (make-classname (js->clj (:$theme props)
                                         :keywordize-keys true))})
         children]))))

(def flexbox-container
  (theme/styled
    "div"
    (fn [clj-props current-theme]
      (clj->js
        {
         (:tiny theme/screen-size) #js{:alignItems "stretch"}
         (:small theme/screen-size) #js{:alignItems "stretch"}
         (:medium theme/screen-size) #js{:alignItems "stretch"}
         (:large theme/screen-size) #js{:alignItems "stretch"}
         :alignItems "stretch"
         :display "flex"
         :flexWrap "wrap"
         :fontSize (:subtitle (:size (:font current-theme)))
         :justifyContent "space-between"
         :width "100%"
         }))))

(defn Menu
  "The Site navigation menu. Takes a function 'make-classname'
  that takes two arguments [clj-props current-theme] which will
  be passed through to theme/styled."
  [make-classname]
  (let [nav-link (nav-link-template make-classname)]
    [:nav
     ; TODO: Switch to flexbox-container
     [:ul
      [:li
       [site-title {:to "/"} "Chris Reyes"]]
      [:li
       [nav-link {:to "/"} "About"]]
      [:li
       [nav-link {:to "/support/"} "Support"]]]]))
