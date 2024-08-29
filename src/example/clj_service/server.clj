(ns example.clj-service.server
  (:require
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as resp]))

(defn handler [request]
  (let [body "Hello there"]
    (-> body
        (resp/response)
        (resp/content-type "text/html"))))

(defn start []
  (let [opts {:port 8080
              :join? false}]
    (log/info "Starting server")
    (jetty/run-jetty #'handler opts)))

(defn stop [server]
  (log/info "Stopping server")
  (.stop server))

(comment
  (def server (start))
  (stop server))
