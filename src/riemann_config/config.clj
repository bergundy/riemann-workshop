(require '[riemann-config.mock-pager :refer [post]])

(tcp-server {:host "127.0.0.1" :port 5555})
(instrumentation {:enabled? false})
(periodically-expire 2)

(defn test-supervisor-state [e]
  (assoc e :state (condp = (:state e)
                    "RUNNING" "passed"
                    "FATAL" "failed"
                    "undefined")))

(streams (smap test-supervisor-state post))
