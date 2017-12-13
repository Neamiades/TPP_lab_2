grammar Expression;
command:
        MtxVar '=' substractExpr
	|   NumVar '=' var
	|   var
    |   substractExpr;

substractExpr:
        mtxExpr ('-' mtxExpr)*;

mtxExpr:
	atomExpr ('^' MtxOperator)*;

atomExpr:
	|   mtx
	|   var '*' mtxExpr
    |   '(' substractExpr ')';

mtx:
	    MtxVar
	|   '[' vctr (',' vctr)* ']';

vctr:   '[' var (',' var)*']';

var: NumVar | Number;

MtxOperator : 'T' | '-1';

Number: ('-')? ('0' ..'9')+ ('.' ('0' ..'9')+)?;

MtxVar: [A-SU-Z];

NumVar: [a-z];

WS: [ \t\r\n]+ -> skip;