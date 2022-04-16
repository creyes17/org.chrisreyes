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

(ns org.chrisreyes.reframe.event)

(defn initialize-database
  [_]
  (.log js/console "initializing database")
  #:org.chrisreyes{:user nil})

(defn app-loaded-page
  [_ _]
  {:fx [[:load-google-auth! nil]]})

(defn google-loaded-auth2
  [_ _]
  {:fx [[:initialize-google-auth! nil]]})

(defn google-initialized-auth2
  [_ [_ google-auth]]
  {:fx [[:render-signin-button! google-auth]]})

(defn google-failed-to-initialize-auth2
  [_ [_ error]]
  {:fx [[:google-failed-to-initialize-auth2! error]]})

(defn user-failed-to-sign-in
  [_ _]
  {:fx [[:user-failed-to-sign-in! nil]]})

; TODO: Configure google signin button
; TODO: Add listener handlers for signing in and out
;   See https://developers.google.com/identity/sign-in/web/reference#googleauthissignedinlistenlistener
(defn configure-google-user
  [database [_ google-user]]
  (.log js/console "configuring google user")
  (assoc-in database
            [:org.chrisreyes/user :org.chrisreyes/username]
            (-> google-user
                .getBasicProfile
                .getEmail)))
