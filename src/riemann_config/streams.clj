(ns riemann-config.streams
  (:require [riemann.streams :refer [smap]]))

(defmacro test-by [t & children]
  `(smap #(assoc % :state (if (-> % ~@t) "passed" "failed")) ~@children))
