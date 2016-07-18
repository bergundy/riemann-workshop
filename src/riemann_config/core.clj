(ns riemann-config.core
  (:gen-class)
  (:require riemann.bin))

(defn -main
  "Runs riemann.bin/-main"
  [& args]
  (apply riemann.bin/-main args))
