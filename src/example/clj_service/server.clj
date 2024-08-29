(ns example.clj-service.server
  (:require
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as resp]
   [ring.middleware.defaults :as ring-defaults]
   [reitit.core :as reitit]
   [reitit.ring :as reitit-ring]
   [hiccup2.core :as hiccup]
   [hiccup.page :as hiccup-page]))

(defn index-page []
  [:html {:lang "en"}
   [:head
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:title "Hello"]]
   [:body
    [:span "Hello there"]]])

(defn html [root]
  (str
   (hiccup-page/doctype :html5)
   (hiccup/html root)))

(defn index [request]
  (let [body (html (index-page))]
    (-> body
        (resp/response)
        (resp/content-type "text/html"))))

(defn routes []
  ["/" {:get index}])

(defn routes-handler []
  (reitit-ring/ring-handler
   (reitit-ring/router (routes))
   (reitit-ring/routes
    (reitit-ring/create-resource-handler {:path "/"})
    (reitit-ring/create-default-handler
     {:not-found (constantly (resp/not-found "Not found"))}))))

(defn handler []
  (-> (routes-handler)
      (ring-defaults/wrap-defaults ring-defaults/site-defaults)))

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
