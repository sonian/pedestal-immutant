(ns {{name}}.module.http
    (:require [carousel.module :as carousel]
              [{{name}}.service :as service]
              [immutant.web :as web]
              [io.pedestal.service.http :as bootstrap]
              [io.pedestal.service.log :as log]))

(defonce running? (atom false))

(defn start-server []
  (locking running?
    (when-not @running?
      (log/info :msg "starting the HTTP server")
      (web/start-servlet
       "/"
       (::bootstrap/servlet (bootstrap/create-servlet service/service)))
      (reset! running? true))))

(defn stop-server []
  (locking running?
    (when @running?
      (log/info :msg "stopping the HTTP server")
      (web/stop "/")
      (reset! running? false))))

(carousel/defstart http-server-defstart
  "Starts the REST service's HTTP-server"
  []
  (start-server))

(carousel/defadmin http-server-start
  "Start the HTTP server if it is not already running"
  []
  (if-not @running?
    (do
      (println "Starting the HTTP server")
      (start-server))
    (println "HTTP server is already running")))

(carousel/defadmin http-server-stop
  "Stop the HTTP server if it is running"
  []
  (if @running?
    (do
      (println "Stopping the HTTP server")
      (stop-server))
    (println "HTTP server is not running")))
