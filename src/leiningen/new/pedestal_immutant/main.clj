(ns {{name}}.http.main
    (:require [ring.util.response :as ring-resp]))

(defn home-page
  [request]
  (ring-resp/response
   "<html><head/><body>{{name}} says hello, world</body></html>"))
