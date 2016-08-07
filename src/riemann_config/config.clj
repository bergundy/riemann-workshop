(require '[riemann-config.mock-pager :refer [post]])

(tcp-server {:host "127.0.0.1" :port 5555})
(instrumentation {:enabled? false})
(streams
  (where (state "failed")
    (tap :trigger-pagerduty
      (io post))))

(tests
  (deftest state-failed-triggers-pagerduty
    (let [event {:state "failed"}]
      (is (= [event]
             (:trigger-pagerduty (inject! [event])))))))
