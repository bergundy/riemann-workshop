(require '[riemann-config.mock-pager :refer [post]])

(tcp-server {:host "127.0.0.1" :port 5555})
(instrumentation {:enabled? false})
(streams prn)
