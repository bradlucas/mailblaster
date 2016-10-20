(ns mailblaster.core
  (:require [postal.core :as mail])
  (:require [clojure.java.io :as io])
  (:use [clojure.tools.cli :only (cli)])
  (:gen-class :main true))

;; Required:
;; create a directory in your $HOME directory called .smtp
;; create a file inside called smtp.properties with the following contents
;;
;; host="SERVER"
;; port=PORT
;; user="USERNAME"
;; pass="PASSWORD"
;;
(defn smtp-properties-filepath [] (str (System/getenv "HOME") "/.smtp/smtp.properties"))

;; Load smtp information out of a file from http://stackoverflow.com/a/7781443/406220
(defn load-props
  [file-name]
  (with-open [^java.io.Reader reader (clojure.java.io/reader file-name)]
    (let [props (java.util.Properties.)]
      (.load props reader)
      (into {} (for [[k v] props] [(keyword k) (read-string v)])))))

(defn send-mail [from to subject msg bcc]
  (mail/send-message (load-props (smtp-properties-filepath))
                     {:from from
                      :to to
                      :bcc bcc
                      :subject subject
                      :body msg}))

(defn read-email-list [file]
  ;; read the address or out :email-file
  (with-open [rdr (io/reader file)]
    (doall (line-seq rdr))))

(defn read-message-file [file]
  ;; read :message-file for message
  (slurp file))

(defn wait [sec] ; time in seconds
  (println "Waiting..." sec " seconds\n")
  (Thread/sleep (* sec 1000)))

(defn run [opts]
  (let [emails (read-email-list (:email-file opts))
        from (:from opts)
        subject (:subject opts)
        body (read-message-file (:message-file opts))
        bcc (:bcc opts)]
    (doseq [to emails]
      (if (:test opts)
        (println "TESTING: " from to subject bcc)
        (send-mail from to subject body bcc)
        )
      (wait (:delay opts)))))

(defn -main [& args]
  (let [[opts args banner]
        (cli args
             ["-h" "--help" "Show help" :flag true :default false]
             ["-d" "--delay" "Delay between messages (seconds)" :default 2]
             ["-f" "--from" "REQUIRED: From address)"]
             ["-e" "--email-file" "REQUIRED: Email addresses FILE)"]
             ["-s" "--subject" "REQUIRED: Message subject"]
             ["-m" "--message-file" "REQUIRED: Message FILE"]
             ["-b" "--bcc" "BCC address"] ;; optional
             ["-t" "--test" "Test mode does not send" :flag true :default false]
             )]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (if
        (and
         (:from opts)
         (:email-file opts)
         (:subject opts)
         (:message-file opts))
      (do
        (println "")
        (run opts))
      (println banner))))
