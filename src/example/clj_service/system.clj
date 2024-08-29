(ns example.clj-service.system
  (:require
   [clojure.tools.logging :as log]
   [integrant.core :as integrant]
   [example.clj-service.server :as server]))

(defn system-map []
  {::server {}})

(defn start []
  (log/info "Starting system")
  (-> (system-map)
      (integrant/init)))

(defn stop [system]
  (log/info "Stopping system")
  (-> system
      (integrant/halt!)))

(defmethod integrant/init-key ::server [_ opts]
  (server/start opts))

(defmethod integrant/halt-key! ::server [_ server]
  (server/stop server))

(comment
  (def system (start))
  (stop system))
