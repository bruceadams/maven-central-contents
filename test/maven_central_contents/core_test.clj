(ns maven-central-contents.core-test
  (:use clojure.test
        maven-central-contents.core)
  (:require (clj-json [core :as json])))

(deftest simple-search
  (let [docs (get-one-level 47)]
    (is (= {"id" "-1144916964"
            "path" "HTTPClient/"
            "name" "HTTPClient"
            "type" 0
            "lastModified" 1130821416000
            "repositoryId" "central"
            "fileSize" 735018
            "parentId" "47"}
           (first docs)))
    (is (= {"path" "test.txt"
            "sha1" "915058761c1fa4b96fb7203f7476b636412c38ea"
            "repositoryId" "central"
            "parentId" "47"
            "name" "test.txt"
            "type" 1
            "id" "-1147906284"
            "fileSize" 28
            "lastModified" 1349318490000}
           (last docs)))
    (is (= 745 (count docs)))
    (is (= 735 (count (filter #(= 0 (% "type")) docs))))))
