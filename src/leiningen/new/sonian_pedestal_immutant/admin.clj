(ns {{name}}.module.admin
  (:require [carousel.module :as carousel]
            [io.pedestal.service.log :as log]
            [{{name}}.config :refer [config offset-port]]))

(defonce admin-server (atom nil))

(carousel/defstart admin-server-start
  "Start the admin server"
  []
  (locking admin-server
    (let [port (offset-port (config :admin-port))]
      (when (and port (not @admin-server))
        (log/info :msg "starting admin server")
        (reset! admin-server (carousel/admin-server port))))))

(carousel/defadmin help
  "Information about usage."
  []
  (println "Usage:")
  (println (carousel/help)))
