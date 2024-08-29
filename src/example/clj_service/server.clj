(ns example.clj-service.server
  (:require
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as resp]))

(defn index [request]
  (let [body "Hello there"]
    (-> body
        (resp/response)
        (resp/content-type "text/html"))))

(defn handler []
  index)

(defn reloading-ring-handler
  [f]
  (fn
    ([request] ((f) request))
    ([request respond raise] ((f) request respond raise))))

(defn start []
  (let [opts {:port 8080
              :join? false}
        handler (let [f (fn [] (handler))]
                  (reloading-ring-handler f))]
    (log/info "Starting server")
    (jetty/run-jetty handler opts)))

(defn stop [server]
  (log/info "Stopping server")
  (.stop server))

(comment
  (def server (start))
  (stop server))
