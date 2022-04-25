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

<assigment-statement> ::= MAKE <identifier> <expression>

<movement-statement> ::=
      <forward-statement> |
      <backward-statement> |
      <right-statement> |
      <left-statement> |
      <setx-statement> |
      <sety-statement> |
      <setxy-statement> |
      HOME
<forward-statement> ::=  (FORWARD | FD) <simple-expression>
<backward-statement> ::= (BACKWARD | BK) <simple-expression>
<right-statement> ::= (RIGHT | RT) <simple-expression>
<left-statement> ::= (LEFT | LT) <simple-expression>
<setx-statement> ::= SETX <simple-expression>
<sety-statement> ::= SETY <simple-expression>
<setxy-statement> ::= SETXY <simple-expression> <simple-expression>

<drawing-statement> :=
  <clear-statement> |
  <circle-statement> |
  <arc-statement> |
  <penup-staement> |
  <pendown-statement> |
  <color-statement> |
  <penwidth-statement>
<clear-statement> ::= (CLEAR | CLS)
<circle-statement> ::= CIRCLE <simple-expression>
<arc-statement> ::= ARC <simple-expression>
<penup-statement> ::= (PENUP | PU)
<pendown-statement> ::= (PENDOWN | PD)
<color-statement> ::= COLOR <simple-expression> <simple-expression> <simple-expression> ']'
<penwidth-statement> ::= PENWIDTH <simple-expression>

<text-statement> ::= PRINT '[' <element> <more_elements> ']'
<more_elements> := ',' <element> <more_elements>
<more_elements> := ' '
element :=
  <expression> |
  <identifier> |
  <number> |
  <string>

<structured-statement> ::=
  <repetitive-statement> |
  <conditional-statement>
<repetitive-statement> ::= 
  REPEAT <simple-expression> '[' <statement-sequence> ']'
conditional-statement ::=
  <if-statement> |
  <if-else-statement>
<if-statement> ::= 
  IF <expression> '[' <if-true> ']'
<if-else-statement> ::= 
  IFELSE <expression> '[' <if-true> ']' '[' <if-false> ']'
<if-true> ::= <statement-sequence>
<if-false> ::= <statement-sequence>

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
  'n''o''t' <factor>

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
