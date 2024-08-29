(ns example.clj-service.config
  (:require
   [clojure.java.io :as io]
   [aero.core :as aero]))

(defn config-map []
  (-> "config.edn"
      (io/resource)
      (aero/read-config)))
