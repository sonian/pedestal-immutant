(ns {{name}}.server
  (:require [{{name}}.config :refer [config]]
            [carousel.module :as carousel]
            [io.pedestal.service.log :as log]))

(defonce stopped? (atom true))

(defn start-server []
  (locking stopped?
    (when @stopped?
      (let [modules (config :modules)]
        (log/info :msg "starting modules"
                  :modules modules)
        (carousel/start modules))
      (reset! stopped? false))))

(defn stop-server []
  (locking stopped?
    (when-not @stopped?
      (log/info :msg "stopping server")
      (carousel/stop (reverse (config :modules)))
      (reset! stopped? true))))

(defn run-server []
  (log/info :msg "starting server")
  (let [modules (config :modules)]
    ;; require nses
    (log/info :msg "initializing modules"
              :modules modules)
    (carousel/init modules))
  (start-server)
  (log/info :msg "done starting server"))

(defn initialize []
  (run-server)
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-server)))
