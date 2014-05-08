(ns user
  (:require [clojure.java.io :as io]
            [clojure.java.javadoc :refer [javadoc]]
            [clojure.pprint :refer [pprint]]
            [clojure.reflect :refer [reflect]]
            [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.set :as set]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.find :refer [find-namespaces-in-dir]]
            [clojure.tools.namespace.repl :refer [refresh refresh-all
                                                  set-refresh-dirs]]
            [immutant.util :as iutil]))

(defn classpath-dirs []
  (->> (map io/file (iutil/classpath))
       (filter #(.isDirectory ^java.io.File %))))

(defn dirs-to-refresh []
  (filter #(re-find #"(test|src)$" (.getAbsolutePath %))
          (classpath-dirs)))

(defn set-immutant-refresh-dirs []
  (when (iutil/in-immutant?)
    (apply set-refresh-dirs (dirs-to-refresh))))

(defn reset []
  (set-immutant-refresh-dirs)
  (refresh))

(defn reset-all []
  (set-immutant-refresh-dirs)
  (refresh-all))

(defn test-dirs []
  (filter #(re-find #"(test)$" (.getAbsolutePath %))
          (classpath-dirs)))

(defn do-run-all-tests []
  (println "\nRequiring test namespaces.")
  (doseq [dir (test-dirs)
          :let [nses (find-namespaces-in-dir dir)]]
    (prn :requiring nses)
    (apply require nses))
  (println "\nRunning all tests.")
  (test/run-all-tests #"^{{name}}\.test.*"))

(defn run-all-tests []
  (println "\nResetting all namespaces.")
  (set-immutant-refresh-dirs)
  (refresh-all :after 'user/do-run-all-tests)
  nil)
