(ns {{name}}.test.module.http
  (:require [clj-http.client :as http]
            [clojure.test :refer :all]
            [immutant.util :refer [app-uri]]
            [robert.bruce :refer [try-try-again]]
            [{{name}}.module.http :refer :all]
            [{{name}}.test.support.immutant :refer [only-in-immutant-fixture]]))

(use-fixtures :once
  only-in-immutant-fixture)

(defn try-request [url]
  (try-try-again
   {:sleep 500 :tries 100}
   #(http/get url)))

(deftest test-server
  (stop-server)
  (is (not @running?))
  (start-server)
  (is @running?)
  (try
    (is (.contains (:body (try-request (app-uri))) "hello, world"))
    (catch Exception e
      (is false
          (str "The http request should not have failed: " (.getMessage e)))))
  (stop-server)
  (is (not @running?))
  (try
    (http/get (app-uri))
    (is false "We shouldn't have gotten here b/c the server should be down.")
    (catch Exception e
      (is (.contains (.getMessage e) "404")
          "requests should fail with a 404 after being stopped"))))

(deftest test-server-admins
  (stop-server)
  (is (not @running?))
  (let [out-start1 (with-out-str (http-server-start))
        out-start2 (with-out-str (http-server-start))
        out-stop1 (with-out-str (http-server-stop))
        out-stop2 (with-out-str (http-server-stop))]
    (is (.contains out-start1 "Starting"))
    (is (.contains out-start2 "already running"))
    (is (.contains out-stop1 "Stopping"))
    (is (.contains out-stop2 "not running"))))
