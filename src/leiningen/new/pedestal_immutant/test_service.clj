(ns {{name}}.test.service
    (:require [clojure.test :refer :all]
            [io.pedestal.service.test :refer :all]
            [io.pedestal.service.http :as bootstrap]
            [{{name}}.service :as service]))

(def service
  (::bootstrap/service-fn (bootstrap/create-servlet service/service)))
