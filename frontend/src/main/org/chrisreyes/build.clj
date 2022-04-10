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

(ns org.chrisreyes.build
  (:require
    [clostache.parser :as parser]
    [clojure.java.io :as io]))

(defn generate-index
  {:shadow.build/stage :flush}
  [build-state]
  "Generate the index.html file"
  (do
    (with-open [writer (io/writer (str (:output-dir (:shadow.build/config build-state))
                                       "/index.html"))]
      (.write writer (parser/render-resource
                       "template/index.mustache"
                       {:google-signin-client-id "260066049322-aqtv4d9dlv7leb93kkgdo48sseagr6pd"})))
    build-state))