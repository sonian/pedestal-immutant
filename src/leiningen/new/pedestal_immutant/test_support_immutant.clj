(ns {{name}}.test.support.immutant
  (:require [immutant.util :refer [in-immutant?]]))

(defn only-in-immutant-fixture [f]
  (if (in-immutant?)
    (f)
    (println "Skipping; Immutant is required.")))
