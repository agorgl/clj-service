(ns example.clj-service.core
  (:require
   [clojure.tools.logging :as log])
  (:gen-class))

(defn -main [& args]
  (log/info "Hello there!"))
