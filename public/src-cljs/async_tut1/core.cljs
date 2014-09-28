(ns async_tut1.core
  (:require [goog.dom :as dom]
            [instaparse.core :as insta]))

(.log js/console (dom/getElement "query"))

(enable-console-print!)

(println "Hello world!")
