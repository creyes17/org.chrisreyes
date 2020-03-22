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

; TODO: Turn this into an extensible collection of components or
; something and then load this content from AWS or a file or something.
(ns org.chrisreyes.content.support
  (:require [org.chrisreyes.component.header :as header]
            [org.chrisreyes.style.theme :as theme]))

(def description
  "If you'd like to support me, please consider donating to one
  of these organizations I support.")

(def disclaimer
  "These are organizations that I endorse, but my endorsing
   them does not imply that they endorse (or even know about)
   me or my site.")

(def intro-block
  (theme/styled
    "div"
    (fn [clj-props current-theme]
      #js{:backgroundColor (:opaque (:primary (:color current-theme)))
          :width "100%"
          :textAlign "center"
          :fontSize (:subtitle (:size (:font current-theme)))
          :color (:contrast (:primary (:color current-theme)))
          :marginBottom "2vh"})))

(def organization-outer-container
  (theme/styled
    "div"
    (fn [clj-props current-theme]
      (clj->js
        ; Make sure the media queries don't mess up vertical stretching
        {(:tiny theme/screen-size) #js{:alignItems "stretch"}
         (:small theme/screen-size) #js{:alignItems "stretch"}
         (:medium theme/screen-size) #js{:alignItems "stretch"}
         (:large theme/screen-size) #js{:alignItems "stretch"}
         :display "flex"
         :flexWrap "wrap"
         :justifyContent "space-between"
         :width "100%"}))))

(def organization-container
  (theme/styled
    "article"
    (fn [clj-props current-theme]
      (clj->js
        ; By default, on mobile there should be only one element per row
        {(:tiny theme/screen-size) #js{:flex "0 1 100%"}
         ; Who only two cards per row on small screens
         (:small theme/screen-size) #js{:flex "0 1 49%"}
         ; Three cards per row for mid-size screens
         (:medium theme/screen-size) #js{:flex "0 1 32%"}
         ; Four cards per row on larger screens
         (:large theme/screen-size) #js{:flex "0 1 24%"}
         :backgroundColor (:opaque
                            (:tertiary
                              (:color current-theme)))
         :color (:contrast
                  (:tertiary
                    (:color current-theme)))
         :fontSize (:subtitle (:size (:font current-theme)))
         :marginTop "1vh"
         :marginBottom "1vh"
         :textAlign "center"}))))

(def organization-image
  (theme/styled
    "img"
    (fn [clj-props current-theme]
      (let [set-background (:$background clj-props false)]
        (clj->js
          (conj {:height "auto"
                 :width "85%"}
                (if set-background
                  {:backgroundColor set-background}
                  {})))))))

(def organization-link-wrapper
  (theme/styled
    "a"
    (fn [clj-props current-theme]
      #js{:color (:contrast
                   (:tertiary
                     (:color current-theme)))
          :height "100%"
          :width "100%"})))

(defn organization-card
  "A styled 'card' representing an organization"
  [{background-color :$background
    image-url :image-url
    title :title
    url :url}]
  [organization-container
   [organization-link-wrapper {:href url :target "_blank"}
    [:div title]
    [organization-image {:$background background-color
                         :alt (str "Logo for " title)
                         :src image-url}]]])

(def disclaimer-ui
  (theme/styled
    "span"
    (fn [clj-props current-theme]
      #js{:fontSize (:tiny (:size (:font current-theme)))})))

(defn support-section
  "Returns the 'Support' section of the website"
  []
  [:section
   [header/section-header "Support Me"]
   [intro-block description]
   [organization-outer-container
    [organization-card
     {:image-url "https://secure.nationalmssociety.org/images/content/pagebuilder/2013-bike_ms_badge_final-a1188792.jpg"
      :title "National MS Society"
      :url "http://nationalmssociety.org/goto/chrisreyes"}]
    [organization-card
     {:image-url "https://www.acceleratedcure.org/sites/default/files/accelerated-cure-logo.png"
      :title "Accelerated Cure Project"
      :url "https://www.acceleratedcure.org/"}]
    [organization-card
     {:$background "white"
      :image-url "https://wp-cdn.milocloud.com/aids-life-cycle-wp/wp-content/uploads/20160907202526/AIDSLifeCycle-Logo.png"
      :title "AIDS/LifeCycle"
      :url "http://www.tofighthiv.org/goto/ChrisReyes"}]
    [organization-card
     {:$background "white"
      :image-url "https://www.savethechildren.org/etc/clientlibs/us/clientlib-site/images/icons/stc-logo.svg"
      :title "Save the Children"
      :url "https://www.savethechildren.org/"}]
    [organization-card
     {:image-url "https://www.childfund.org/images/CFLogo2016.jpg"
      :title "ChildFund International"
      :url "https://www.childfund.org/"}]
    [organization-card
     {:image-url "https://static.radio.net/images/broadcasts/5c/b0/9227/c300.png"
      :title "KQED Public Radio"
      :url "https://www.kqed.org/"}]
    [organization-card
     {:$background "white"
      :image-url "https://www.younglife.org/About/PublishingImages/YL-Prmry-Color.png"
      :title "Young Life"
      :url "https://www.younglife.org/Giving/Pages/default.aspx"}]]
   [:div "Disclaimer: "
    [disclaimer-ui disclaimer]]])
