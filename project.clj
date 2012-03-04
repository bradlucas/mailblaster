(defproject org.clojars.blucas/mailblaster "1.0.0"
  :description "Clojure Mass Mailing Application"
  :dependencies
  [[org.clojure/clojure "1.3.0"]
   [org.clojure/tools.cli "0.2.1"]
   ;;   [com.draines/postal "1.7-SNAPSHOT"]          ;; need the 1.8 version to work properly, not yet on clojars
   [org.clojars.blucas/postal "1.8-SNAPSHOT"]
   ]
  :main mailblaster.core)
