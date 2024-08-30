(ns user
  (:require
   [integrant.repl :refer [clear go halt prep init reset reset-all]]
   [example.clj-service.config :as config]
   [example.clj-service.system :as system]))

(integrant.repl/set-prep!
 (fn []
   (-> (config/config-map)
       (system/system-map)
       (update-vals #(assoc % :dev? true)))))
