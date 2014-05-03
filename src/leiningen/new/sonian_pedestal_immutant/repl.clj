(ns {{name}}.module.repl
  (:require [carousel.module :as carousel]
            [immutant.repl :as repl]
            [io.pedestal.service.log :as log]
            [{{name}}.config :refer [config offset-port]]))

(defonce nrepl-servers (atom {}))

(carousel/defadmin repl
  "Start a REPL inside your running server"
  []
  (clojure.main/repl))

(carousel/defadmin nrepl-start
  "Start the nrepl server"
  [& [port]]
  (locking nrepl-servers
    (if-let [port (str (or port (offset-port (config :nrepl-port))))]
      (if-not (get @nrepl-servers port)
        (do
          (swap! nrepl-servers assoc port (repl/start-nrepl port))
          (log/info :msg "started nrepl server" :port port)
          (println "The nrepl server is now running on port" port))
        (println "The nrepl server is already running on port" port))
      (println "No nrepl port specified in the config or command line."))))

(defn stop-nrepl-server [port]
  (when-let [server (get @nrepl-servers port)]
    (repl/stop-nrepl server)
    (swap! nrepl-servers dissoc port)
    (log/info :msg "stopped nrepl server" :port port)))

(carousel/defadmin nrepl-stop
  "Start the nrepl server"
  [& [port]]
  (locking nrepl-servers
    (let [port (str (or port (offset-port (config :nrepl-port))))]
      (if (get @nrepl-servers port)
        (do (stop-nrepl-server port)
            (println "The nrepl server at port" port "has been stopped."))
        (println "The nrepl server at port" port "is not running")))))

(carousel/defadmin nrepl-list
  "List the running nrepl servers"
  []
  (locking nrepl-servers
    (if (pos? (count @nrepl-servers))
      (println "There are nrepl servers running on ports:"
               (keys @nrepl-servers))
      (println "There are no running nrepl servers."))))
