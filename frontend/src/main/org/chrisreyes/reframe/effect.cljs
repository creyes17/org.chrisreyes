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

(ns org.chrisreyes.reframe.effect
  (:require
    [org.chrisreyes.component.oauth :as oauth]
    [re-frame.core]))

(defn load-google-auth!
  [_]
  (.log js/console "loading google auth library")
  (.load js/gapi
         "auth2"
         #(re-frame.core/dispatch [:google-loaded-auth2])))

(defn initialize-google-auth!
  [_]
  (.log js/console "initializing google auth")
  (-> js/gapi
      .-auth2
      ; See https://developers.google.com/identity/sign-in/web/reference#gapiauth2clientconfig
      ; for valid arguments to init
      (.init #js{:client_id oauth/google-client-id})
      ; XXX Seeing 400 response to https://accounts.google.com/_/IdpIFrameHttp/cspreport
      ; then it calls this first "onInit" rather than the second "onError"
      (.then #(re-frame.core/dispatch [:google-initialized-auth2 %])
             #(re-frame.core/dispatch [:google-failed-to-initialize-auth2 %]))))

(defn google-failed-to-initialize-auth2!
  [error]
  (.log js/console "google failed to initialize auth2")
  (.error js/console error))

(defn user-failed-to-sign-in!
  [_]
  (.error js/console "user failed to sign in"))

(defn render-signin-button!
  [_]
  (.log js/console "Rendering the signin button")
  (-> js/gapi
      .-signin2
      (.render oauth/login-container-id
               #js{:theme "dark"
                   :onsuccess #(re-frame.core/dispatch [:user-signed-in %])
                   :onfailure #(re-frame.core/dispatch [:user-failed-to-sign-in])})))
