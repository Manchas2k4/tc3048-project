![Tec de Monterrey](images/logotecmty.png)
# Analysis and Design of Advanced Algorithms (TC2038)

```
program ::= <statement-sequence>

<statement-sequence> ::= <statement> <statement-sequence>
<statement-sequence> ::= ' '

<statement> ::=
  <simple-statement> |
  <structured-statement>

<simple-statement> ::=
  <assignment-statement> |
  <movement-statement> |
  <drawing-statement> |
  <text-statement>

<assigment-statement> ::= MAKE <identifier> <arithmetic-expression>

<movement-statement> ::=
      <forward-statement> |
      <backward-statement> |
      <right-statement> |
      <left-statement> |
      <setx-statement> |
      <sety-statement> |
      <setxy-statement> |
      HOME
<forward-statement> ::=  (FORWARD | FD) <arithmetic-expression>
<backward-statement> ::= (BACKWARD | BK) <arithmetic-expression>
<right-statement> ::= (RIGHT | RT) <arithmetic-expression>
<left-statement> ::= (LEFT | LT) <arithmetic-expression>
<setx-statement> ::= SETX <arithmetic-expression>
<sety-statement> ::= SETY <arithmetic-expression>
<setxy-statement> ::= SETXY <arithmetic-expression> <arithmetic-expression>

<drawing-statement> :=
  <clear-statement> |
  <circle-statement> |
  <arc-statement> |
  <penup-staement> |
  <pendown-statement> |
  <color-statement> |
  <penwidth-statement>
<clear-statement> ::= (CLEAR | CLS)
<circle-statement> ::= CIRCLE <arithmetic-expression>
<arc-statement> ::= ARC <arithmetic-expression>
<penup-statement> ::= (PENUP | PU)
<pendown-statement> ::= (PENDOWN | PD)
<color-statement> ::= COLOR <arithmetic-expression> <arithmetic-expression> <arithmetic-expression>
<penwidth-statement> ::= PENWIDTH <arithmetic-expression>

<text-statement> ::= PRINT '[' <element> <more_elements> ']'
element := 
  <string> |
  <boolean-expression> | 
  <arithmetic-expression>
<more_elements> := ',' <element> <more_elements>
<more_elements> := ' '

<structured-statement> ::=
  <repetitive-statement> |
  <conditional-statement>
<repetitive-statement> ::= 
  REPEAT <arithmetic-expression> '[' <statement-sequence> ']'
conditional-statement ::=
  <if-statement> |
  <if-else-statement>
<if-statement> ::= 
  IF <boolean-expression> '[' <if-true> ']'
<if-else-statement> ::= 
  IFELSE <boolean-expression> '[' <if-true> ']' '[' <if-false> ']'
<if-true> ::= <statement-sequence>
<if-false> ::= <statement-sequence>


<boolean-expresion> ::= <simple-boolean-expression> <extended-boolean-expression>
<extended-boolean-expression> ::= 
  <relational-operator> <simple-boolean-expression> <extended-boolean-expression>
<extended-boolean-expression> ::= ' '

<simple-boolean-expression> ::= 
  <boolean-term> <extended-simple-boolean-expression>
<extended-simple-boolean-expression> ::= 
  OR <boolean-term> <extended-simple-boolean-expression>
<extended-simple-expression> ::= ' '

<boolean-term> ::= <bolean-factor> <extended-boolean-term>
<extended-boolean-term> ::= AND <boolean-factor> <extended-boolean-term>
<extended-boolean-term> ::= ' '

<boolean-factor> ::=
  <true>   |
  <false>  |
  '(' <boolean-expresion> ')' |
  NOT <boolean-factor>

<expression> ::= <simple-expression> <extended-expression>
<extended-expression> ::= 
  <relational-operator> <simple-expression> <extended-expression>
<extended-expression> ::= ' '

<simple-expression> ::= 
  <sign> <term> <extended-simple-expression>
<extended-simple-expression> ::= 
  <addition-operator> <term> <extended-simple-expression>
<extended-simple-expression> ::= ' '

term ::= <factor> <extended-term>
<extended-term> ::= <multiplication-operator> <factor> <extended-term>
<extended-term> ::= ' '

<factor> ::=
  <identifier> |
  <number> |
  <string> |
  <true>   |
  <false>  |
  '(' <expression> ')' |
  NOT <factor>

relational-operator ::= '=' | '<''>' | '<' | '<''=' | '>' | '>''='

addition-operator ::= '+' | '-' | OR

multiplication-operator ::= '*' | '/' | MOD | AND

<identifier> ::= <letter> <characters>
<characters> ::= (<letter> | <digit>) <characters>
<characters> ::= ' '

<number> -> <integer-number> | <real-number>
<integer-number> ::= <digit-sequence>
<digit-sequence> ::= <sign> <unsigned-digit-sequence>
<unsigned-digit-sequence> ::= <digit> <digits>
<digits> ::= <digit> <digits>
<digits> ::= ' '
<sign> ::= '+' | '-' | ' '

<true> -> '#''t'
<false> -> '#''f'

<letter> ::= ['A'-'Z'] | ['a'-'z']
<digit> ::= ['0'-'9']
<string> ::= '"' <string-character> <more-string-chracters> '"'
<<more-string-chracters> ::= <string-character> <more-string-chracters>
<more-string-chracters> ::= ' '
<string-character> ::= <any-character-except-quote>
```
