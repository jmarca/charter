
(require '[instaparse.core :as insta])

(def knit-and-yarnover
  (insta/parser
   "instructions = line (<whitespace> line)*
    line = linenumber ':'? <whitespace>? token (<whitespace> token)*
    linenumber = R <whitespace>? number
    <R> = 'r' | 'R' | 'row' | 'Row'
    <token> = K | YO | K2tog
    K : 'k' number?
    YO : 'yo' | 'yo' <whitespace> 'twice'
    K2tog : 'k2tog'+
    number : #'[0-9]+'
    whitespace = #'\\s+'
"
   ))


(insta/parses knit-and-yarnover "Row 1 k3 yo k k2tog k k2tog k yo k3
                                 R2: k
                                 R3: k3 k2tog k yo k yo twice k k2tog k3
                                 R4: k")
