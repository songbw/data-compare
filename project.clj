(defproject compare "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [ring/ring-json "0.2.0"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [lobos "1.0.0-20120828.002929-9"]
                 [org.clojars.kbarber/postgresql "9.2-1002.jdbc4"]
                 [cheshire "5.0.0"]
                 [clj-json "0.5.3"]
                 [korma "0.3.0-RC4"]
                 [clj-http "0.7.3"]
                 [incanter/incanter-core "1.5.4"]
                 [incanter/incanter-excel "1.5.4"]
                 [incanter/incanter-io "1.5.4"]
                 [org.clojure/tools.trace "0.7.8"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 []
                 [log4j/log4j "1.2.16"
                  :exclusions [javax.mail/mail
                               javax.jms/jms
                               com.sun.jdmk/jmxtools
                               com.sun.jmx/jmxri]]
]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler spanner.server/app}
  :main spanner.server
  ;;:source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options     ["-target" "1.7" "-source" "1.7"]
)
