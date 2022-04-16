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

; TODO: Turn this into an extensible "photo-tile" component or
; something and then load this content from AWS or a file or something.
(ns org.chrisreyes.content.about
  (:require
    [org.chrisreyes.component.header :as header]
    [org.chrisreyes.style.theme :as theme]
    [re-frame.core]))

(def description
  "Hi, my name is Chris and I use he/him/his pronouns. When I'm not coding at work,
  I'm training for bike rides for charity, playing
  games with friends, or investing in real estate. I'll
  occasionally post updates here!")


(def image-for-text-image
  (theme/styled
    "img"
    (fn [clj-props current-theme]
      #js{:width "100%"
          :zIndex 0})))

(def image-for-text-text
  (theme/styled
    "div"
    (fn [clj-props current-theme]
      ; TODO: the bottom alignment doesn't seem perfect...
      (let [bottom-left {:left 0
                         :bottom 0}
            bottom-right {:right 0
                          :bottom 0}
            top-left {:left 0
                      :top 0}
            top-right {:right 0
                       :top 0}
            ; Need to use string keys here instead of keywords
            ; because of how the values are translated between
            ; javascript and cljs.
            placement (get {"bottom-left" bottom-left
                            "bottom-right" bottom-right
                            "top-left" top-left
                            "top-right" top-right}
                           (:placement clj-props "top-right"))
            font-size (get (:size (:font current-theme))
                           (keyword
                             (:fontSize clj-props "normal")))]
        (clj->js
          (conj {:fontSize font-size
                 :height "auto"
                 :margin "0 auto"
                 :textAlign "center"
                 :verticalAlign "middle"
                 (:large theme/screen-size) (conj
                                              {:backgroundColor
                                               (:transparent
                                                 (:primary
                                                   (:color current-theme)))
                                               :color (:contrast
                                                        (:primary
                                                          (:color current-theme)))
                                               :maxHeight "50%"
                                               :position "absolute"
                                               :width "50%"
                                               :zIndex 1}
                                              placement)}))))))

(def image-with-text-container
  (theme/styled
    "div"
    (fn [clj-props current-theme]
      #js{:display "inline-block"
          :position "relative"
          :marginLeft "auto"
          :marginRight "auto"
          :width "100%"
          :height "fit-content"})))

(defn ImageWithText
  "A Reagent component for an image with text floating above it"
  [props]
  [image-with-text-container
   [image-for-text-image {:alt (:alt props)
                          :src (:src props)}]
   [image-for-text-text
    {:placement (:placement props)
     :fontSize (:fontSize props)}
    (:text props)]])

(defn about-section
  "Returns the 'About Me' section of the website"
  []
  [:section
   [header/section-header "About Me"]
   ; XXX: Just for testing
   [:div (str
           "Username is: "
           @(re-frame.core/subscribe [:username]))]
   [ImageWithText {:alt "Chris Reyes is wearing sunglasses and a Star Wars tee-shirt while smiling."
                   :src "/assets/ProfilePhoto-2020-03-23.jpg"
                   :text description
                   :placement "top-right"}]])
