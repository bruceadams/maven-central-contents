(ns maven-central-contents.core
  (:require (clj-json [core :as json]))
  (:require (org.httpkit [client :as http])))

;; http://search.maven.org/solrsearch/select?q=parentId%3A%2247%22&rows=100000&core=filelisting&wt=json

(def search-maven-url "http://search.maven.org/solrsearch/select")

(defn get-one-level
  "Return the immediate children of the given id"
  [id]
  (let [options {:query-params {:q (str "parentId:\"" id "\"")
                                :rows "100000"
                                :core "filelisting"
                                :wt "json"}}
        {:keys [status headers body error] :as resp}
          @(http/get search-maven-url options)]
    (if error
      (println "HTTP error" error)
      (if (= status 200)
        (((json/parse-string body) "response") "docs")
        (println "Bad HTTP status of" status)))))

(defn walk-levels
  ""
  ([] (walk-levels 47))
  ([start]
     (let [next-level (get-one-level start)]
       (map #(get-one-level (% "id")) next-level))))
