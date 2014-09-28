(ns example.parse
  (:use [hiccup.element]
        [hiccup.util])
  (:require [instaparse.core :as insta]
            [clojure.edn]
            [hiccup
             [page :refer [html5]]
             [page :refer [include-js]]]
            ))
(def knit-and-yarnover
  (insta/parser
   "instructions = line (<whitespace> line)*
    line = linenumber ':'? <whitespace>? stitch (<whitespace> stitch)*
    linenumber = R <whitespace>? number
    <R> = 'r' | 'R' | 'row' | 'Row'
    <stitch> = k | yo | k2tog
    k : <'k'> (<whitespace>? repeat)?
    yo : <'yo'>  (<whitespace> repeat)?
    k2tog : <'k2tog'> (<whitespace> repeat)?
    repeat: (number | once | twice | number <whitespace 'times'>)+
    once = 'once'
    twice = 'twice'
    number : digit+
    <digit> = #'[0-9]+'
    whitespace = #'\\s+'
"
   ))

(def once_twice_trnsfm
  {:word str
   :number (comp clojure.edn/read-string str)
   :once (fn [_] 1)
   :twice (fn [_] 2)
   })

(def outputtxt
      (insta/transform once_twice_trnsfm
                    (insta/parses knit-and-yarnover "Row 1 k3 yo k k2tog k k2tog k yo k3
                                  R2: k
                                  R3: k3 k2tog k yo k yo twice k k2tog k3
                                  R4: k
                                  R3: k3 k2tog once k yo 3 times k yo twice k k2tog k")
                    )
   )

 (defn parse-page []
  (html5
    [:head
     [:title "Hello World"]
     ]
    [:body
     [:h1 "Hello World"]
     [:input {:id "query" :type "text"}]
     [:button {:id "search"} "Search"]
     [:p {:id "results"} (to-str (vec outputtxt))]
     (include-js "/js/main.js")
     ]))