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

(ns org.chrisreyes.style.theme
  (:require
    [reagent.core]
    ["react" :as react]
    ["react-router-dom" :as router]
    ["styletron-react" :rename {styled styletron-styled}]
    ["styletron-standard" :refer (driver getInitialStyle)]))

; Themes
(def default-theme
  {:color {:background {:opaque "rgb(58,67,83)"
                        :contrast "#BDC3C7"
                        :transparent "rgba(58,67,83,0.65)"}
           :primary {:opaque "rgba(125,243,125)"
                     :contrast "#1D0029"
                     :transparent "rgba(125,243,125,0.65)"}
           :secondary {:opaque "rgb(221,160,221)"
                       :contrast "#1D1905"
                       :transparent "rgba(221,160,221,0.65)"}
           :tertiary {:opaque "rgb(255,160,122)"
                      :contrast "#1C2833"
                      :transparent "rgba(255,160,122,0.65)"}}
   :font {:size {:header "xx-large"
                 :subtitle "x-large"
                 :normal "large"
                 :tiny "small"}}})

(def screen-size
  {:tiny "@media screen and (min-width: 1px)"
   :small "@media screen and (min-width: 982px)"
   :medium "@media screen and (min-width: 1200px)"
   :large "@media screen and (min-width: 1600px)"})

; React Context to pass the theme down
(defonce theme-context (react/createContext))
(set! (.-displayName theme-context) "StyletronTheme")

(def Provider (.-Provider theme-context))
(def Consumer (.-Consumer theme-context))

(defn ThemeProvider
  "Creates a React Provider to provide the color theme"
  [& children]
  (into
    [:> Provider
     {:value default-theme}]
    children))

(defn with-theme
  "Returns a Form-1 Reagent Component for adding the $theme to
  elements via props. The first arg (wrapped-component) should
  be a keyword or a Form-1, Form-2, or Form-3 Reagent Component"
  [wrapped-component]
  (fn [& children]
    (let [has-props? (and (not (empty? children))
                          (map? (first children)))
          props (if has-props?
                  (first children)
                  {})
          actual-children (if has-props?
                            (rest children)
                            children)]
      [:> Consumer
       {}
       (fn [theme]
         (reagent.core/as-element
           (into
             [wrapped-component
              (conj props {:$theme theme})]
             actual-children)))])))

(defn styled
  "Returns a styled Reagent component for use in bare hiccup forms."
  [element style-fn]
  (with-theme
    (reagent.core/adapt-react-class
      (styletron-styled
        element
        (fn [props]
          (let [clj-props (js->clj props :keywordize-keys true)
                current-theme (:$theme clj-props default-theme)]
            (style-fn clj-props current-theme)))))))
