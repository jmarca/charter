
(require '[instaparse.core :as insta])

(def knit-and-yarnover
  (insta/parser
   "instructions = line (<whitespace> line)*
    line = linenumber ':'? <whitespace>? token (<whitespace> token)*
    linenumber = R <whitespace>? number
    <R> = 'r' | 'R' | 'row' | 'Row'
    <token> = K | YO | K2tog
    K : 'k' (<whitespace>? repeat)?
    YO : 'yo'  (<whitespace> repeat)?
    repeat: (number | 1 | 2 | number <whitespace 'times'>)+
    1 = <'once'>
    2 = <'twice'>
    K2tog : 'k2tog' (<whitespace> repeat)?
    <number> : #'[0-9]+'
    whitespace = #'\\s+'
"
   ))


(insta/parses knit-and-yarnover "Row 1 k3 yo k k2tog k k2tog k yo k3
                                 R2: k
                                 R3: k3 k2tog k yo k yo twice k k2tog k3
                                 R4: k
                                 R3: k3 k2tog twice k yo 3 times k yo twice k k2tog k")
