(defproject {{name}} "0.0.1-SNAPSHOT"
  :description "FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;; pedestal
                 [io.pedestal/pedestal.service "0.2.2"]
                 [io.pedestal/pedestal.service-tools "0.2.2"]
                 ;; immutant
                 [org.immutant/immutant "1.1.0"
                  :exclusions [javax.servlet/servlet-api]]
                 ;; servlet-api
                 ;; this prevents pedestal and immutant from arguing
                 ;; over which version to use
                 [javax.servlet/javax.servlet-api "3.0.1"]
                 ;; libraries we need
                 [clj-http "0.9.1"]
                 [robert/bruce "0.7.1"]
                 [sonian/carica "1.0.3"]
                 [sonian/carousel "1.0.4"]]
  :profiles {:dev {:source-paths ["dev"]
                   ;; quality-of-life for developers
                   :dependencies [[ns-tracker "0.2.1"]
                                  [org.clojure/tools.namespace "0.2.4"]]
                   :immutant {:nrepl-port 0
                              :nrepl-interface :management}}}
  :min-lein-version "2.3.4"
  :resource-paths ["config" "resources"]
  :plugins [[lein-immutant "1.2.0"]]
  :immutant {:init {{name}}.server/initialize
             :resolve-dependencies true
             :context-path "/"})
