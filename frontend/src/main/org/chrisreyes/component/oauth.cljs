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

(ns org.chrisreyes.component.oauth)

(goog-define google-client-id "set_in_shadow-cljs.edn")

(def login-container-id
  "login-container")

(defn google-signin-button
  []
  ; See https://developers.google.com/identity/sign-in/web/reference#gapiauth2initparams
  ; Let's try making our own button so that we can get access to the callbacks.
  [:div {:id login-container-id}])

(defn on-sign-in
  [google-user]
  ((.-log js/console) "Logged in")
  ((.-log js/console) (str "Found google user? "
                           (some? google-user))))
