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

(ns org.chrisreyes.reframe.init
  (:require
    [cljs.spec.alpha :as spec]
    [org.chrisreyes.reframe.effect :as effect]
    [org.chrisreyes.reframe.event :as event]
    [org.chrisreyes.reframe.query :as query]
    [re-frame.core]))

(spec/def :org.chrisreyes/db
  (spec/map-of :org.chrisreyes/user
    (spec/nilable (spec/map-of :org.chrisreyes/username string?))))

(defn register-events
  []
  (re-frame.core/reg-event-db
    :app-prepares-page
    event/initialize-database)
  (re-frame.core/reg-event-fx
    :app-loaded-page
    event/app-loaded-page)
  (re-frame.core/reg-event-fx
    :google-loaded-auth2
    event/google-loaded-auth2)
  (re-frame.core/reg-event-fx
    :google-initialized-auth2
    event/google-initialized-auth2)
  (re-frame.core/reg-event-fx
    :google-failed-to-initialize-auth2
    event/google-initialized-auth2)
  (re-frame.core/reg-event-db
    :user-signed-in
    event/configure-google-user)
  (re-frame.core/reg-event-fx
    :user-failed-to-sign-in
    event/user-failed-to-sign-in)
  )

(defn register-effects
  []
  (re-frame.core/reg-fx
    :load-google-auth! effect/load-google-auth!)
  (re-frame.core/reg-fx
    :initialize-google-auth! effect/initialize-google-auth!)
  (re-frame.core/reg-fx
    :render-signin-button! effect/render-signin-button!)
  (re-frame.core/reg-fx
    :google-failed-to-initialize-auth2! effect/google-failed-to-initialize-auth2!)
  (re-frame.core/reg-fx
    :user-failed-to-sign-in! effect/user-failed-to-sign-in!))

(defn register-queries
  "TODO: Register queries AKA subscriptions"
  []
  (re-frame.core/reg-sub
    :user
    query/user)
  (re-frame.core/reg-sub
    :username
    :<- [:user]
    query/username-from-user))

(defn register-everything
  []
  (register-events)
  (register-effects)
  (register-queries))

(defn start-reframe
  []
  (register-everything)
  (re-frame.core/dispatch-sync [:app-prepares-page]))

(defn setup-user-login
  []
  (re-frame.core/dispatch-sync [:app-loaded-page]))
