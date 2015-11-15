// $ANTLR 3.4 SimpleCalc.g 2012-10-22 14:23:25

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class SimpleCalcParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIGIT", "DIV", "MINUS", "MULT", "NUMBER", "PLUS", "WHITESPACE"
    };

    public static final int EOF=-1;
    public static final int DIGIT=4;
    public static final int DIV=5;
    public static final int MINUS=6;
    public static final int MULT=7;
    public static final int NUMBER=8;
    public static final int PLUS=9;
    public static final int WHITESPACE=10;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public SimpleCalcParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public SimpleCalcParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return SimpleCalcParser.tokenNames; }
    public String getGrammarFileName() { return "SimpleCalc.g"; }


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



    // $ANTLR start "expr"
    // SimpleCalc.g:29:1: expr : term ( ( PLUS | MINUS ) term )* ;
    public final void expr() throws RecognitionException {
        try {
            // SimpleCalc.g:29:9: ( term ( ( PLUS | MINUS ) term )* )
            // SimpleCalc.g:29:11: term ( ( PLUS | MINUS ) term )*
            {
            pushFollow(FOLLOW_term_in_expr87);
            term();

            state._fsp--;


            // SimpleCalc.g:29:16: ( ( PLUS | MINUS ) term )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==MINUS||LA1_0==PLUS) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // SimpleCalc.g:29:18: ( PLUS | MINUS ) term
            	    {
            	    if ( input.LA(1)==MINUS||input.LA(1)==PLUS ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_term_in_expr102);
            	    term();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "expr"



    // $ANTLR start "term"
    // SimpleCalc.g:31:1: term : factor ( ( MULT | DIV ) factor )* ;
    public final void term() throws RecognitionException {
        try {
            // SimpleCalc.g:31:9: ( factor ( ( MULT | DIV ) factor )* )
            // SimpleCalc.g:31:11: factor ( ( MULT | DIV ) factor )*
            {
            pushFollow(FOLLOW_factor_in_term118);
            factor();

            state._fsp--;


            // SimpleCalc.g:31:18: ( ( MULT | DIV ) factor )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DIV||LA2_0==MULT) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // SimpleCalc.g:31:20: ( MULT | DIV ) factor
            	    {
            	    if ( input.LA(1)==DIV||input.LA(1)==MULT ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_factor_in_term132);
            	    factor();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "term"



    // $ANTLR start "factor"
    // SimpleCalc.g:33:1: factor : NUMBER ;
    public final void factor() throws RecognitionException {
        try {
            // SimpleCalc.g:33:9: ( NUMBER )
            // SimpleCalc.g:33:11: NUMBER
            {
            match(input,NUMBER,FOLLOW_NUMBER_in_factor146); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "factor"

    // Delegated rules


 

    public static final BitSet FOLLOW_term_in_expr87 = new BitSet(new long[]{0x0000000000000242L});
    public static final BitSet FOLLOW_set_in_expr91 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_term_in_expr102 = new BitSet(new long[]{0x0000000000000242L});
    public static final BitSet FOLLOW_factor_in_term118 = new BitSet(new long[]{0x00000000000000A2L});
    public static final BitSet FOLLOW_set_in_term122 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_factor_in_term132 = new BitSet(new long[]{0x00000000000000A2L});
    public static final BitSet FOLLOW_NUMBER_in_factor146 = new BitSet(new long[]{0x0000000000000002L});

}