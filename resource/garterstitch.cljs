
(require '[instaparse.core :as insta])
(require 'clojure.edn)

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


(insta/parses knit-and-yarnover "Row 1 k3 yo k k2tog k k2tog k yo k3
                                 R2: k
                                 R3: k3 k2tog k yo k yo twice k k2tog k3
                                 R4: k
                                 R3: k3 k2tog twice k yo 3 times k yo twice k k2tog k")


(def parsvec [:instructions [:line [:linenumber "Row" [:number "1"]] [:k [:repeat [:number "3"]]] [:yo] [:k] [:k2tog] [:k] [:k2tog] [:k] [:yo] [:k [:repeat [:number "3"]]]] [:line [:linenumber "R" [:number "2"]] ":" [:k]] [:line [:linenumber "R" [:number "3"]] ":" [:k [:repeat [:number "3"]]] [:k2tog] [:k] [:yo] [:k] [:yo [:repeat [:twice "twice"]]] [:k] [:k2tog] [:k [:repeat [:number "3"]]]] [:line [:linenumber "R" [:number "4"]] ":" [:k]] [:line [:linenumber "R" [:number "3"]] ":" [:k [:repeat [:number "3"]]] [:k2tog [:repeat [:twice "twice"]]] [:k] [:yo [:repeat [:number "3"]]] [:k] [:yo [:repeat [:twice "twice"]]] [:k] [:k2tog] [:k]]])


(def hcptrnsfm
  {:word str
   :number (comp clojure.edn/read-string str)
   :once (fn [_] 1)
   :twice (fn [_] 2)
   })

(->> (insta/parses knit-and-yarnover "Row 1 k3 yo k k2tog k k2tog k yo k3
                                 R2: k
                                 R3: k3 k2tog k yo k yo twice k k2tog k3
                                 R4: k
                                 R3: k3 k2tog once k yo 3 times k yo twice k k2tog k")
     (insta/transform hcptrnsfm)
     )




;;; wtf

	(def arithmetic
	  (insta/parser
	    "expr = add-sub
	     <add-sub> = mul-div | add | sub
	     add = add-sub <'+'> mul-div
	     sub = add-sub <'-'> mul-div
	     <mul-div> = term | mul | div
	     mul = mul-div <'*'> term
	     div = mul-div <'/'> term
	     <term> = number | <'('> add-sub <')'>
	     number = #'[0-9]+'"))

(->> (arithmetic "1-2/(3-4)+5*6")
	     (insta/transform
              {
               :add +
               :sub -
               :mul *
               :div /
               :number clojure.edn/read-string
               :expr identity
               }))


(->> (arithmetic "1-2/(3-4)+5*6")
	     (insta/transform
              {
               ;;:add +
               ;;:sub -
               ;;:mul *
               :div clojure.edn/read-string
               ;;:number clojure.edn/read-string
               :expr vec
               }))
