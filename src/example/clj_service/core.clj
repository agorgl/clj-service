(ns example.clj-service.core
  (:require
   [clojure.tools.logging :as log]
   [example.clj-service.system :as system])
  (:gen-class))

(defn add-shutdown-hook [f]
  (let [shutdown-fn
        (fn []
          (f)
          (shutdown-agents))]
    (-> (Runtime/getRuntime)
        (.addShutdownHook
         (Thread. shutdown-fn)))))

(defn -main [& args]
  (log/info "Hello there!")
  (let [system (system/start)]
    (add-shutdown-hook #(system/stop system))))
