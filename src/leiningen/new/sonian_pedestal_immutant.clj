;;   Copyright 2014 Sonian, Inc.
;;
;;   Licensed under the Apache License, Version 2.0 (the "License");
;;   you may not use this file except in compliance with the License.
;;   You may obtain a copy of the License at
;;
;;       http://www.apache.org/licenses/LICENSE-2.0
;;
;;   Unless required by applicable law or agreed to in writing, software
;;   distributed under the License is distributed on an "AS IS" BASIS,
;;   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;;   See the License for the specific language governing permissions and
;;   limitations under the License.

(ns leiningen.new.sonian-pedestal-immutant
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "sonian-pedestal-immutant"))

(defn sonian-pedestal-immutant
  "make all the things"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' sonian-pedestal-immutant project.")
    (->files data
             ;; project level things
             [".gitignore" (render "gitignore")]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ;; pedestal
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["src/{{sanitized}}/service.clj" (render "service.clj" data)]
             ;; application config loading (NOT settings)
             ["src/{{sanitized}}/config.clj" (render "config.clj" data)]
             ;; the settings are here instead
             ["resources/config.clj"
              (render "resources_config.clj" data)]
             ;; the hello-world app itself
             ["src/{{sanitized}}/http/main.clj" (render "main.clj" data)]
             ["src/{{sanitized}}/module/http.clj"
              (render "module_http.clj" data)]
             ;; these is for repl development
             ["dev/user.clj" (render "user.clj" data)]
             ["src/{{sanitized}}/module/repl.clj" (render "repl.clj" data)]
             ;; ctls
             ["src/{{sanitized}}/module/admin.clj" (render "admin.clj" data)]
             ["bin/{{sanitized}}ctl" (render "ctl" data)]
             ["bin/config.sh" (render "config.sh" data)]
             ;; unit tests
             ["test/{{sanitized}}/test/service.clj"
              (render "test_service.clj" data)]
             ["test/{{sanitized}}/test/support/immutant.clj"
              (render "test_support_immutant.clj" data)]
             ["test/{{sanitized}}/test/module/http.clj"
              (render "test_module_http.clj" data)])))
