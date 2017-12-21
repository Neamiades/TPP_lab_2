grammar Expression;
command:
      MtxVar '=' addExpr
    | NumVar '=' var
    | var
    | addExpr;

addExpr:
      mtxExpr ('+' mtxExpr)*;

mtxExpr:
      atomExpr ('*' atomExpr)*
    | atomExpr ('*' var)*
    | (var '*')+ atomExpr;

atomExpr:
      mtx
    | (mtx | '(' addExpr ')') (Inversion)*
    | '(' addExpr ')';

mtx:
      MtxVar
      | '[' vctr (',' vctr)* ']';

vctr:
      '[' var (',' var)*']';

var:
      NumVar | Number;

Inversion:
      '^' '-1';

Number:
      ('-')? ('0' ..'9')+ ('.' ('0' ..'9')+)?;

MtxVar:
      [A-Z];

NumVar:
      [a-z];

WS:
      [ \t\r\n]+ -> skip;