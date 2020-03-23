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

(ns org.chrisreyes.content.contact
  (:require [org.chrisreyes.component.header :as header]
            [org.chrisreyes.style.theme :as theme]))

(def description-box
  (theme/styled
    "div"
    (fn [clj-props current-theme]
      #js{
          :margin "auto"
          :textAlign "center"
          :width "50%"
          })))

(defn description []
  [description-box
   [:p "Have a question? Want to collaborate on a project?"]
   [:p
    "You can get in touch with me "
    [:a {:href "mailto:ChrisReyes@alum.mit.edu"}"by email"]
    ", or "
    "schedule with me directly via "
    [:a {:href "https://calendly.com/chrisreyes"} "Calendly"]
    "."]])

(defn contact-section
  "Returns the 'About Me' section of the website"
  []
  [:section
   [header/section-header "Contact Me"]
   [description]])
