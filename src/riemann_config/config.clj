(require '[riemann-config.mock-pager :refer [post]])

(tcp-server {:host "127.0.0.1" :port 5555})
(instrumentation {:enabled? false})
(periodically-expire 2)

(defn make-test-fn [pred]
  "Returns a function that takes an event and associates
  it's :state attribute based on given predicate"
  (fn [e]
    (assoc e :state (if (pred e) "passed" "failed"))))

(def test-lte-0
  (make-test-fn #(<= (:metric %) 0)))

(streams prn)
