(ns riemann-config.mock-pager
  (:require [clj-http.client :as client])
  (:require [cheshire.core :as json]))

(defn- action
  [event]
  (condp = (:state event)
    "passed" "resolve"
    "failed" "trigger"))

(defn- incident-key
  [event]
  (str (:host event) " " (:service event)))

(defn- format-event
  "Formats an event for PagerDuty"
  [event]
  {:header (str (:host event) " "
                (:service event) " is "
                (:state event) " ("
                (:metric event) ")")
   :details event
   :key (incident-key event)
   })

(defn post
  "POST to the PagerDuty events API."
  [event]
  (client/post (str "http://localhost:5000/incidents/" (action event))
               {:body (json/generate-string (format-event event))
                :socket-timeout 5000
                :conn-timeout 5000
                :content-type :json
                :accept :json
                :throw-entire-message? true}))
