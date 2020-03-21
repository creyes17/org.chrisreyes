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
  {:color {:background "#3A4353"
           :backgroundContrast "#BDC3C7"
           :primary "#7DF37D"
           :primaryContrast "#1D0029"
           :secondary "#DDA0DD"
           :secondaryContrast "#1D1905"
           :tertiary "#FFA07A"
           :tertiaryContrast "#1C2833"}})

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
