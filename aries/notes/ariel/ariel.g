grammar Java;
options {backtrack=true; memoize=true;}


@members {
    public static void main(String[] args) throws Exception {
        SimpleCalcLexer lex = new SimpleCalcLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);
 
        SimpleCalcParser parser = new SimpleCalcParser(tokens);
 
        try {
            parser.expr();
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}
 
/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/





PARTICIPANT: 'participant'; 

BEGIN: 'begin';
CHANNEL: 'channel';
END: 'end';
EXCEPTION: 'exception';
JOIN: 'join';
INVOKE: 'invoke';
MESSAGE: 'message';
PROTOCOL: 'protocol';
RECEIVE: 'receive';
REPLY: 'reply';
ROLE: 'role';
OPTION: 'option';
SEND: 'send';
SPLIT: 'split';
SUBSCRIBE: 'subscribe';
THROW: 'throw';
TIMEOUT: 'timeout';



LPAREN : '(' ;
RPAREN : ')' ;
AND : '&&';
OR : '||';
NOT : '!';
NUMBER: ('0'..'9')+;   

NAME: ('a'..'z' | 'A'..'Z')*;



WS : ( ' ' | '\t' | '\r' | '\n' | '\u000C')+ { $channel = HIDDEN; }



protocol: PROTOCOL^ NAME;


nameList: NAME (',' NAME)*;




//start rule
expression : andexpression;

//first, we try to match all first level && (e.g. && not included in some sub-expression)
andexpression : orexpression (AND^ orexpression)*;

//second, we try to match all first level || (e.g. || not included in some sub-expression)
orexpression : notexpression (OR^ notexpression)*;

//third, there may or may not be first level ! in front of an expression
notexpression : NOT^ atom | atom;

//finally, we found either name, or a sub-expression
atom : NAME | LPAREN! andexpression RPAREN!;

