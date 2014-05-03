(ns {{name}}.config
  (:require [carica.core :as carica]
            [clojure.java.io :refer [file]]
            [io.pedestal.service.log :as log]))

(def prod-configs ["/opt/{{name}}/rel/current/config/config.json"
                   "/opt/{{name}}/rel/current/config/config.clj"])

(defn lookup-configs [cfg-paths]
  (->> (map file cfg-paths)
       (filter #(.exists %))
       (map #(.toURL (.toURI %)))))

(defn make-config-fn [& extra-config-paths]
  (carica/configurer
   (concat (lookup-configs extra-config-paths)
           (carica/resources "config.json")
           (carica/resources "config.clj"))
   [carica/cache-config]))

(def config (apply make-config-fn prod-configs))
(def override-config (carica/overrider config))

(defn offset-port [port]
  (let [offset (try
                 (Integer/parseInt
                  (or (System/getProperty "jboss.socket.binding.port-offset")
                      "0"))
                 (catch Exception e
                   (log/error
                    :msg (str "The value of jboss.socket.binding.port-offset "
                              "is not a number."))
                   0))]
    (+ port offset)))
