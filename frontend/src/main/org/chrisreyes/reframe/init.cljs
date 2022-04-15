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
    [org.chrisreyes.reframe.event :as event]
    [cljs.spec.alpha :as spec]
    [re-frame.core]))

(spec/def :org.chrisreyes/db
  (spec/map-of :org.chrisreyes/user
    (spec/nilable (spec/map-of :org.chrisreyes/username string?))))

(defn register-events
  "TODO: Register events"
  []
  (re-frame.core/reg-event-db
    :initialize-db
    event/initialize-db))

(defn register-effects
  "TODO: Register effects (if any)"
  []
  nil)

(defn register-queries
  "TODO: Register queries AKA subscriptions"
  []
  nil)

(defn register-everything
  []
  (register-events)
  (register-effects)
  (register-queries))

(defn start-reframe
  []
  (register-everything)
  (re-frame.core/dispatch-sync [:initialize-db]))

(defn setup-user-login
  []
  (re-frame.core/dispatch-sync [:setup-user-login]))
