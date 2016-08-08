(require '[riemann-config.mock-pager :refer [post]])

(tcp-server {:host "127.0.0.1" :port 5555})
(instrumentation {:enabled? false})
(periodically-expire 2)

(defn test-state [evs]
  (let [last-event (last evs)
        last-event-passed (= (:state last-event) "passed")
        all-failed (and
                     (= (count evs) 3)
                     (every? #(= (:state %) "failed") evs))]
    (assoc last-event :state
           (if last-event-passed
             "passed"
             (if all-failed
               "failed"
               "undefined"
             )))))

(streams prn)
