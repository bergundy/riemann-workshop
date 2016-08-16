(defproject riemann-config "0.1.0-SNAPSHOT"
  :description "riemann config files"
  :url "http://github.com/bergundy/riemann-workshop"
  :license {:name "BSD"
            :url "?"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [pjstadig/humane-test-output "0.7.1"]
                 [test2junit "1.2.1"]]
  :injections [(require 'pjstadig.humane-test-output)
               (pjstadig.humane-test-output/activate!)
               (require 'test2junit.core)]
  :profiles {:uberjar
             {:aot :all}
             :dev
             {:plugins [[jonase/eastwood "0.2.3"]
                        [lein-auto "0.1.2"]
                        [lein-iclojure "1.2"]
                        [test2junit "1.2.1"]]}
             :junit
             {:injections [(test2junit.core/apply-junit-output-hook "target/tests")]}}
  :resource-paths ["resources/riemann.jar"]
  :main ^:skip-aot riemann-config.core
  :auto {:default {:file-pattern #"\.(clj[sx]?)$"}}
  :target-path "target/%s"
  :aliases {"start" ["run" "start" "riemann.config"]
            "test" ["run" "test" "riemann.config"]})
