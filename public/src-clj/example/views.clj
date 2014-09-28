(ns example.views
  (:use hiccup.element)
  (:require
    [hiccup
      [page :refer [html5]]
      [page :refer [include-js]]]))


(defn index-page []
  (html5
    [:head
     [:title "Hello World"]
     ]
    [:body
     [:h1 "Hello World"]
     [:input {:id "query" :type "text"}]
     [:button {:id "search"} "Search"]
     [:p {:id "results"}]
     (include-js "/js/main.js")
     ]))
