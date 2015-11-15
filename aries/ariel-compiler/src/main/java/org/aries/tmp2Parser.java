// $ANTLR 3.4 org\\aries\\tmp2.g 2014-04-12 17:13:21

	package org.aries;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class tmp2Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BLOCK", "CHARLITERAL", "COMMENT", "Digit", "DoubleLiteral", "DoubleSuffix", "DoubleValueSuffix", "EXCEPTION", "EXPR", "EscapeSequence", "Exponent", "FALSE", "FloatLiteral", "FloatSuffix", "FloatValueSuffix", "HexDigit", "HexPrefix", "ITEM", "Identifier", "IntegerLiteral", "IntegerNumber", "IntegerValueSuffix", "LINE_COMMENT", "Letter", "LevelType", "LongSuffix", "MESSAGE", "NULL", "NonIntegerNumber", "PRIMARY", "STRINGLITERAL", "ScopeType", "THROWS", "TRUE", "TYPE", "TimeValueSuffix", "TimeoutLiteral", "TransactionType", "WS", "'!'", "'!='", "'%'", "'%='", "'&&'", "'&'", "'&='", "'('", "')'", "'*'", "'*='", "'+'", "'++'", "'+='", "','", "'-'", "'--'", "'-='", "'.'", "'/'", "'/='", "':'", "';'", "'<'", "'='", "'=='", "'>'", "'?'", "'['", "']'", "'^'", "'^='", "'adapter'", "'add'", "'backingStore'", "'boolean'", "'branch'", "'break'", "'byte'", "'cache'", "'channel'", "'char'", "'client'", "'condition'", "'continue'", "'do'", "'domain'", "'double'", "'else'", "'end'", "'execute'", "'float'", "'for'", "'group'", "'if'", "'import'", "'include'", "'index'", "'int'", "'invoke'", "'items'", "'join'", "'level'", "'listen'", "'long'", "'manager'", "'maxResponse'", "'minResponse'", "'namespace'", "'option'", "'participant'", "'persist'", "'post'", "'protocol'", "'receive'", "'reply'", "'restriction'", "'return'", "'role'", "'schedule'", "'scope'", "'send'", "'service'", "'set'", "'short'", "'source'", "'synchronous'", "'then'", "'throw'", "'throws'", "'timeout'", "'transaction'", "'while'", "'{'", "'|'", "'|='", "'||'", "'}'", "'~'"
    };

    public static final int EOF=-1;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int T__102=102;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__110=110;
    public static final int T__111=111;
    public static final int T__112=112;
    public static final int T__113=113;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__120=120;
    public static final int T__121=121;
    public static final int T__122=122;
    public static final int T__123=123;
    public static final int T__124=124;
    public static final int T__125=125;
    public static final int T__126=126;
    public static final int T__127=127;
    public static final int T__128=128;
    public static final int T__129=129;
    public static final int T__130=130;
    public static final int T__131=131;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int T__136=136;
    public static final int T__137=137;
    public static final int T__138=138;
    public static final int T__139=139;
    public static final int T__140=140;
    public static final int T__141=141;
    public static final int BLOCK=4;
    public static final int CHARLITERAL=5;
    public static final int COMMENT=6;
    public static final int Digit=7;
    public static final int DoubleLiteral=8;
    public static final int DoubleSuffix=9;
    public static final int DoubleValueSuffix=10;
    public static final int EXCEPTION=11;
    public static final int EXPR=12;
    public static final int EscapeSequence=13;
    public static final int Exponent=14;
    public static final int FALSE=15;
    public static final int FloatLiteral=16;
    public static final int FloatSuffix=17;
    public static final int FloatValueSuffix=18;
    public static final int HexDigit=19;
    public static final int HexPrefix=20;
    public static final int ITEM=21;
    public static final int Identifier=22;
    public static final int IntegerLiteral=23;
    public static final int IntegerNumber=24;
    public static final int IntegerValueSuffix=25;
    public static final int LINE_COMMENT=26;
    public static final int Letter=27;
    public static final int LevelType=28;
    public static final int LongSuffix=29;
    public static final int MESSAGE=30;
    public static final int NULL=31;
    public static final int NonIntegerNumber=32;
    public static final int PRIMARY=33;
    public static final int STRINGLITERAL=34;
    public static final int ScopeType=35;
    public static final int THROWS=36;
    public static final int TRUE=37;
    public static final int TYPE=38;
    public static final int TimeValueSuffix=39;
    public static final int TimeoutLiteral=40;
    public static final int TransactionType=41;
    public static final int WS=42;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public tmp2Parser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public tmp2Parser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
        this.state.ruleMemo = new HashMap[123+1];
         

    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return tmp2Parser.tokenNames; }
    public String getGrammarFileName() { return "org\\aries\\tmp2.g"; }


    public static class compilationUnit_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "compilationUnit"
    // org\\aries\\tmp2.g:43:1: compilationUnit : ( importDecl )* ( protocolDecl )* EOF ;
    public final tmp2Parser.compilationUnit_return compilationUnit() throws RecognitionException {
        tmp2Parser.compilationUnit_return retval = new tmp2Parser.compilationUnit_return();
        retval.start = input.LT(1);

        int compilationUnit_StartIndex = input.index();

        Object root_0 = null;

        Token EOF3=null;
        tmp2Parser.importDecl_return importDecl1 =null;

        tmp2Parser.protocolDecl_return protocolDecl2 =null;


        Object EOF3_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }

            // org\\aries\\tmp2.g:44:2: ( ( importDecl )* ( protocolDecl )* EOF )
            // org\\aries\\tmp2.g:44:5: ( importDecl )* ( protocolDecl )* EOF
            {
            root_0 = (Object)adaptor.nil();


            // org\\aries\\tmp2.g:44:5: ( importDecl )*
            loop1:
            do {
                int alt1=2;
                switch ( input.LA(1) ) {
                case 98:
                    {
                    alt1=1;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // org\\aries\\tmp2.g:44:5: importDecl
            	    {
            	    pushFollow(FOLLOW_importDecl_in_compilationUnit114);
            	    importDecl1=importDecl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, importDecl1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            // org\\aries\\tmp2.g:44:17: ( protocolDecl )*
            loop2:
            do {
                int alt2=2;
                switch ( input.LA(1) ) {
                case 116:
                    {
                    alt2=1;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // org\\aries\\tmp2.g:44:17: protocolDecl
            	    {
            	    pushFollow(FOLLOW_protocolDecl_in_compilationUnit117);
            	    protocolDecl2=protocolDecl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, protocolDecl2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_compilationUnit120); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF3_tree = 
            (Object)adaptor.create(EOF3)
            ;
            adaptor.addChild(root_0, EOF3_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 1, compilationUnit_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "compilationUnit"


    public static class assignmentDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assignmentDecl"
    // org\\aries\\tmp2.g:48:1: assignmentDecl : ( 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';' | 'set' ^ 'backingStore' '(' Identifier ')' ';' | 'add' ^ 'channel' '(' STRINGLITERAL ')' ';' | 'set' ^ 'condition' '(' Identifier ')' ';' | 'set' ^ 'domain' '(' STRINGLITERAL ')' ';' | 'set' ^ 'level' '(' LevelType ')' ';' | 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'set' ^ 'participant' '(' STRINGLITERAL ')' ';' | 'set' ^ 'restriction' '(' qualifiedName ')' ';' | 'set' ^ 'role' '(' STRINGLITERAL ')' ';' | 'add' ^ 'role' '(' STRINGLITERAL ')' ';' | 'set' ^ 'scope' '(' ScopeType ')' ';' | 'set' ^ 'source' '(' STRINGLITERAL ')' ';' | 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';' | 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';' | 'set' ^ 'transaction' '(' TransactionType ')' ';' | 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';' | 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';' );
    public final tmp2Parser.assignmentDecl_return assignmentDecl() throws RecognitionException {
        tmp2Parser.assignmentDecl_return retval = new tmp2Parser.assignmentDecl_return();
        retval.start = input.LT(1);

        int assignmentDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal4=null;
        Token string_literal5=null;
        Token char_literal6=null;
        Token STRINGLITERAL7=null;
        Token char_literal8=null;
        Token char_literal9=null;
        Token string_literal10=null;
        Token string_literal11=null;
        Token char_literal12=null;
        Token Identifier13=null;
        Token char_literal14=null;
        Token char_literal15=null;
        Token string_literal16=null;
        Token string_literal17=null;
        Token char_literal18=null;
        Token STRINGLITERAL19=null;
        Token char_literal20=null;
        Token char_literal21=null;
        Token string_literal22=null;
        Token string_literal23=null;
        Token char_literal24=null;
        Token Identifier25=null;
        Token char_literal26=null;
        Token char_literal27=null;
        Token string_literal28=null;
        Token string_literal29=null;
        Token char_literal30=null;
        Token STRINGLITERAL31=null;
        Token char_literal32=null;
        Token char_literal33=null;
        Token string_literal34=null;
        Token string_literal35=null;
        Token char_literal36=null;
        Token LevelType37=null;
        Token char_literal38=null;
        Token char_literal39=null;
        Token string_literal40=null;
        Token string_literal41=null;
        Token char_literal42=null;
        Token IntegerLiteral43=null;
        Token char_literal44=null;
        Token char_literal45=null;
        Token string_literal46=null;
        Token string_literal47=null;
        Token char_literal48=null;
        Token IntegerLiteral49=null;
        Token char_literal50=null;
        Token char_literal51=null;
        Token string_literal52=null;
        Token string_literal53=null;
        Token char_literal54=null;
        Token STRINGLITERAL55=null;
        Token char_literal56=null;
        Token char_literal57=null;
        Token string_literal58=null;
        Token string_literal59=null;
        Token char_literal60=null;
        Token STRINGLITERAL61=null;
        Token char_literal62=null;
        Token char_literal63=null;
        Token string_literal64=null;
        Token string_literal65=null;
        Token char_literal66=null;
        Token char_literal68=null;
        Token char_literal69=null;
        Token string_literal70=null;
        Token string_literal71=null;
        Token char_literal72=null;
        Token STRINGLITERAL73=null;
        Token char_literal74=null;
        Token char_literal75=null;
        Token string_literal76=null;
        Token string_literal77=null;
        Token char_literal78=null;
        Token STRINGLITERAL79=null;
        Token char_literal80=null;
        Token char_literal81=null;
        Token string_literal82=null;
        Token string_literal83=null;
        Token char_literal84=null;
        Token ScopeType85=null;
        Token char_literal86=null;
        Token char_literal87=null;
        Token string_literal88=null;
        Token string_literal89=null;
        Token char_literal90=null;
        Token STRINGLITERAL91=null;
        Token char_literal92=null;
        Token char_literal93=null;
        Token string_literal94=null;
        Token string_literal95=null;
        Token char_literal96=null;
        Token set97=null;
        Token char_literal98=null;
        Token char_literal99=null;
        Token string_literal100=null;
        Token string_literal101=null;
        Token char_literal102=null;
        Token TimeoutLiteral103=null;
        Token char_literal104=null;
        Token char_literal105=null;
        Token string_literal106=null;
        Token string_literal107=null;
        Token char_literal108=null;
        Token TransactionType109=null;
        Token char_literal110=null;
        Token char_literal111=null;
        Token string_literal112=null;
        Token string_literal113=null;
        Token char_literal114=null;
        Token STRINGLITERAL115=null;
        Token char_literal116=null;
        Token char_literal117=null;
        Token string_literal118=null;
        Token string_literal119=null;
        Token char_literal120=null;
        Token STRINGLITERAL121=null;
        Token char_literal122=null;
        Token char_literal123=null;
        Token string_literal124=null;
        Token string_literal125=null;
        Token char_literal126=null;
        Token STRINGLITERAL127=null;
        Token char_literal128=null;
        Token char_literal129=null;
        tmp2Parser.qualifiedName_return qualifiedName67 =null;


        Object string_literal4_tree=null;
        Object string_literal5_tree=null;
        Object char_literal6_tree=null;
        Object STRINGLITERAL7_tree=null;
        Object char_literal8_tree=null;
        Object char_literal9_tree=null;
        Object string_literal10_tree=null;
        Object string_literal11_tree=null;
        Object char_literal12_tree=null;
        Object Identifier13_tree=null;
        Object char_literal14_tree=null;
        Object char_literal15_tree=null;
        Object string_literal16_tree=null;
        Object string_literal17_tree=null;
        Object char_literal18_tree=null;
        Object STRINGLITERAL19_tree=null;
        Object char_literal20_tree=null;
        Object char_literal21_tree=null;
        Object string_literal22_tree=null;
        Object string_literal23_tree=null;
        Object char_literal24_tree=null;
        Object Identifier25_tree=null;
        Object char_literal26_tree=null;
        Object char_literal27_tree=null;
        Object string_literal28_tree=null;
        Object string_literal29_tree=null;
        Object char_literal30_tree=null;
        Object STRINGLITERAL31_tree=null;
        Object char_literal32_tree=null;
        Object char_literal33_tree=null;
        Object string_literal34_tree=null;
        Object string_literal35_tree=null;
        Object char_literal36_tree=null;
        Object LevelType37_tree=null;
        Object char_literal38_tree=null;
        Object char_literal39_tree=null;
        Object string_literal40_tree=null;
        Object string_literal41_tree=null;
        Object char_literal42_tree=null;
        Object IntegerLiteral43_tree=null;
        Object char_literal44_tree=null;
        Object char_literal45_tree=null;
        Object string_literal46_tree=null;
        Object string_literal47_tree=null;
        Object char_literal48_tree=null;
        Object IntegerLiteral49_tree=null;
        Object char_literal50_tree=null;
        Object char_literal51_tree=null;
        Object string_literal52_tree=null;
        Object string_literal53_tree=null;
        Object char_literal54_tree=null;
        Object STRINGLITERAL55_tree=null;
        Object char_literal56_tree=null;
        Object char_literal57_tree=null;
        Object string_literal58_tree=null;
        Object string_literal59_tree=null;
        Object char_literal60_tree=null;
        Object STRINGLITERAL61_tree=null;
        Object char_literal62_tree=null;
        Object char_literal63_tree=null;
        Object string_literal64_tree=null;
        Object string_literal65_tree=null;
        Object char_literal66_tree=null;
        Object char_literal68_tree=null;
        Object char_literal69_tree=null;
        Object string_literal70_tree=null;
        Object string_literal71_tree=null;
        Object char_literal72_tree=null;
        Object STRINGLITERAL73_tree=null;
        Object char_literal74_tree=null;
        Object char_literal75_tree=null;
        Object string_literal76_tree=null;
        Object string_literal77_tree=null;
        Object char_literal78_tree=null;
        Object STRINGLITERAL79_tree=null;
        Object char_literal80_tree=null;
        Object char_literal81_tree=null;
        Object string_literal82_tree=null;
        Object string_literal83_tree=null;
        Object char_literal84_tree=null;
        Object ScopeType85_tree=null;
        Object char_literal86_tree=null;
        Object char_literal87_tree=null;
        Object string_literal88_tree=null;
        Object string_literal89_tree=null;
        Object char_literal90_tree=null;
        Object STRINGLITERAL91_tree=null;
        Object char_literal92_tree=null;
        Object char_literal93_tree=null;
        Object string_literal94_tree=null;
        Object string_literal95_tree=null;
        Object char_literal96_tree=null;
        Object set97_tree=null;
        Object char_literal98_tree=null;
        Object char_literal99_tree=null;
        Object string_literal100_tree=null;
        Object string_literal101_tree=null;
        Object char_literal102_tree=null;
        Object TimeoutLiteral103_tree=null;
        Object char_literal104_tree=null;
        Object char_literal105_tree=null;
        Object string_literal106_tree=null;
        Object string_literal107_tree=null;
        Object char_literal108_tree=null;
        Object TransactionType109_tree=null;
        Object char_literal110_tree=null;
        Object char_literal111_tree=null;
        Object string_literal112_tree=null;
        Object string_literal113_tree=null;
        Object char_literal114_tree=null;
        Object STRINGLITERAL115_tree=null;
        Object char_literal116_tree=null;
        Object char_literal117_tree=null;
        Object string_literal118_tree=null;
        Object string_literal119_tree=null;
        Object char_literal120_tree=null;
        Object STRINGLITERAL121_tree=null;
        Object char_literal122_tree=null;
        Object char_literal123_tree=null;
        Object string_literal124_tree=null;
        Object string_literal125_tree=null;
        Object char_literal126_tree=null;
        Object STRINGLITERAL127_tree=null;
        Object char_literal128_tree=null;
        Object char_literal129_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }

            // org\\aries\\tmp2.g:49:2: ( 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';' | 'set' ^ 'backingStore' '(' Identifier ')' ';' | 'add' ^ 'channel' '(' STRINGLITERAL ')' ';' | 'set' ^ 'condition' '(' Identifier ')' ';' | 'set' ^ 'domain' '(' STRINGLITERAL ')' ';' | 'set' ^ 'level' '(' LevelType ')' ';' | 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'set' ^ 'participant' '(' STRINGLITERAL ')' ';' | 'set' ^ 'restriction' '(' qualifiedName ')' ';' | 'set' ^ 'role' '(' STRINGLITERAL ')' ';' | 'add' ^ 'role' '(' STRINGLITERAL ')' ';' | 'set' ^ 'scope' '(' ScopeType ')' ';' | 'set' ^ 'source' '(' STRINGLITERAL ')' ';' | 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';' | 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';' | 'set' ^ 'transaction' '(' TransactionType ')' ';' | 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';' | 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';' )
            int alt3=21;
            switch ( input.LA(1) ) {
            case 126:
                {
                switch ( input.LA(2) ) {
                case 75:
                    {
                    alt3=1;
                    }
                    break;
                case 77:
                    {
                    alt3=2;
                    }
                    break;
                case 86:
                    {
                    alt3=4;
                    }
                    break;
                case 89:
                    {
                    alt3=5;
                    }
                    break;
                case 105:
                    {
                    alt3=6;
                    }
                    break;
                case 109:
                    {
                    alt3=7;
                    }
                    break;
                case 110:
                    {
                    alt3=8;
                    }
                    break;
                case 111:
                    {
                    alt3=9;
                    }
                    break;
                case 113:
                    {
                    alt3=10;
                    }
                    break;
                case 119:
                    {
                    alt3=11;
                    }
                    break;
                case 121:
                    {
                    alt3=12;
                    }
                    break;
                case 123:
                    {
                    alt3=14;
                    }
                    break;
                case 128:
                    {
                    alt3=15;
                    }
                    break;
                case 129:
                    {
                    alt3=16;
                    }
                    break;
                case 133:
                    {
                    alt3=17;
                    }
                    break;
                case 134:
                    {
                    alt3=18;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;

                }

                }
                break;
            case 76:
                {
                switch ( input.LA(2) ) {
                case 83:
                    {
                    alt3=3;
                    }
                    break;
                case 121:
                    {
                    alt3=13;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;

                }

                }
                break;
            case 104:
                {
                alt3=19;
                }
                break;
            case 99:
                {
                alt3=20;
                }
                break;
            case 98:
                {
                alt3=21;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }

            switch (alt3) {
                case 1 :
                    // org\\aries\\tmp2.g:49:5: 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal4=(Token)match(input,126,FOLLOW_126_in_assignmentDecl133); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal4_tree = 
                    (Object)adaptor.create(string_literal4)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal4_tree, root_0);
                    }

                    string_literal5=(Token)match(input,75,FOLLOW_75_in_assignmentDecl136); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal5_tree = 
                    (Object)adaptor.create(string_literal5)
                    ;
                    adaptor.addChild(root_0, string_literal5_tree);
                    }

                    char_literal6=(Token)match(input,50,FOLLOW_50_in_assignmentDecl138); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal6_tree = 
                    (Object)adaptor.create(char_literal6)
                    ;
                    adaptor.addChild(root_0, char_literal6_tree);
                    }

                    STRINGLITERAL7=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl140); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL7_tree = 
                    (Object)adaptor.create(STRINGLITERAL7)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL7_tree);
                    }

                    char_literal8=(Token)match(input,51,FOLLOW_51_in_assignmentDecl142); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal8_tree = 
                    (Object)adaptor.create(char_literal8)
                    ;
                    adaptor.addChild(root_0, char_literal8_tree);
                    }

                    char_literal9=(Token)match(input,65,FOLLOW_65_in_assignmentDecl144); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal9_tree = 
                    (Object)adaptor.create(char_literal9)
                    ;
                    adaptor.addChild(root_0, char_literal9_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:50:5: 'set' ^ 'backingStore' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal10=(Token)match(input,126,FOLLOW_126_in_assignmentDecl150); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal10_tree = 
                    (Object)adaptor.create(string_literal10)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal10_tree, root_0);
                    }

                    string_literal11=(Token)match(input,77,FOLLOW_77_in_assignmentDecl153); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal11_tree = 
                    (Object)adaptor.create(string_literal11)
                    ;
                    adaptor.addChild(root_0, string_literal11_tree);
                    }

                    char_literal12=(Token)match(input,50,FOLLOW_50_in_assignmentDecl155); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal12_tree = 
                    (Object)adaptor.create(char_literal12)
                    ;
                    adaptor.addChild(root_0, char_literal12_tree);
                    }

                    Identifier13=(Token)match(input,Identifier,FOLLOW_Identifier_in_assignmentDecl157); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier13_tree = 
                    (Object)adaptor.create(Identifier13)
                    ;
                    adaptor.addChild(root_0, Identifier13_tree);
                    }

                    char_literal14=(Token)match(input,51,FOLLOW_51_in_assignmentDecl159); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal14_tree = 
                    (Object)adaptor.create(char_literal14)
                    ;
                    adaptor.addChild(root_0, char_literal14_tree);
                    }

                    char_literal15=(Token)match(input,65,FOLLOW_65_in_assignmentDecl161); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal15_tree = 
                    (Object)adaptor.create(char_literal15)
                    ;
                    adaptor.addChild(root_0, char_literal15_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:51:5: 'add' ^ 'channel' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal16=(Token)match(input,76,FOLLOW_76_in_assignmentDecl167); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal16_tree = 
                    (Object)adaptor.create(string_literal16)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal16_tree, root_0);
                    }

                    string_literal17=(Token)match(input,83,FOLLOW_83_in_assignmentDecl170); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal17_tree = 
                    (Object)adaptor.create(string_literal17)
                    ;
                    adaptor.addChild(root_0, string_literal17_tree);
                    }

                    char_literal18=(Token)match(input,50,FOLLOW_50_in_assignmentDecl172); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal18_tree = 
                    (Object)adaptor.create(char_literal18)
                    ;
                    adaptor.addChild(root_0, char_literal18_tree);
                    }

                    STRINGLITERAL19=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl174); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL19_tree = 
                    (Object)adaptor.create(STRINGLITERAL19)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL19_tree);
                    }

                    char_literal20=(Token)match(input,51,FOLLOW_51_in_assignmentDecl176); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal20_tree = 
                    (Object)adaptor.create(char_literal20)
                    ;
                    adaptor.addChild(root_0, char_literal20_tree);
                    }

                    char_literal21=(Token)match(input,65,FOLLOW_65_in_assignmentDecl178); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal21_tree = 
                    (Object)adaptor.create(char_literal21)
                    ;
                    adaptor.addChild(root_0, char_literal21_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:52:5: 'set' ^ 'condition' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal22=(Token)match(input,126,FOLLOW_126_in_assignmentDecl184); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal22_tree = 
                    (Object)adaptor.create(string_literal22)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal22_tree, root_0);
                    }

                    string_literal23=(Token)match(input,86,FOLLOW_86_in_assignmentDecl187); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal23_tree = 
                    (Object)adaptor.create(string_literal23)
                    ;
                    adaptor.addChild(root_0, string_literal23_tree);
                    }

                    char_literal24=(Token)match(input,50,FOLLOW_50_in_assignmentDecl189); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal24_tree = 
                    (Object)adaptor.create(char_literal24)
                    ;
                    adaptor.addChild(root_0, char_literal24_tree);
                    }

                    Identifier25=(Token)match(input,Identifier,FOLLOW_Identifier_in_assignmentDecl191); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier25_tree = 
                    (Object)adaptor.create(Identifier25)
                    ;
                    adaptor.addChild(root_0, Identifier25_tree);
                    }

                    char_literal26=(Token)match(input,51,FOLLOW_51_in_assignmentDecl193); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal26_tree = 
                    (Object)adaptor.create(char_literal26)
                    ;
                    adaptor.addChild(root_0, char_literal26_tree);
                    }

                    char_literal27=(Token)match(input,65,FOLLOW_65_in_assignmentDecl195); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal27_tree = 
                    (Object)adaptor.create(char_literal27)
                    ;
                    adaptor.addChild(root_0, char_literal27_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:53:5: 'set' ^ 'domain' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal28=(Token)match(input,126,FOLLOW_126_in_assignmentDecl201); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal28_tree = 
                    (Object)adaptor.create(string_literal28)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal28_tree, root_0);
                    }

                    string_literal29=(Token)match(input,89,FOLLOW_89_in_assignmentDecl204); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal29_tree = 
                    (Object)adaptor.create(string_literal29)
                    ;
                    adaptor.addChild(root_0, string_literal29_tree);
                    }

                    char_literal30=(Token)match(input,50,FOLLOW_50_in_assignmentDecl206); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal30_tree = 
                    (Object)adaptor.create(char_literal30)
                    ;
                    adaptor.addChild(root_0, char_literal30_tree);
                    }

                    STRINGLITERAL31=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl208); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL31_tree = 
                    (Object)adaptor.create(STRINGLITERAL31)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL31_tree);
                    }

                    char_literal32=(Token)match(input,51,FOLLOW_51_in_assignmentDecl210); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal32_tree = 
                    (Object)adaptor.create(char_literal32)
                    ;
                    adaptor.addChild(root_0, char_literal32_tree);
                    }

                    char_literal33=(Token)match(input,65,FOLLOW_65_in_assignmentDecl212); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal33_tree = 
                    (Object)adaptor.create(char_literal33)
                    ;
                    adaptor.addChild(root_0, char_literal33_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:54:5: 'set' ^ 'level' '(' LevelType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal34=(Token)match(input,126,FOLLOW_126_in_assignmentDecl218); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal34_tree = 
                    (Object)adaptor.create(string_literal34)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal34_tree, root_0);
                    }

                    string_literal35=(Token)match(input,105,FOLLOW_105_in_assignmentDecl221); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal35_tree = 
                    (Object)adaptor.create(string_literal35)
                    ;
                    adaptor.addChild(root_0, string_literal35_tree);
                    }

                    char_literal36=(Token)match(input,50,FOLLOW_50_in_assignmentDecl223); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal36_tree = 
                    (Object)adaptor.create(char_literal36)
                    ;
                    adaptor.addChild(root_0, char_literal36_tree);
                    }

                    LevelType37=(Token)match(input,LevelType,FOLLOW_LevelType_in_assignmentDecl225); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LevelType37_tree = 
                    (Object)adaptor.create(LevelType37)
                    ;
                    adaptor.addChild(root_0, LevelType37_tree);
                    }

                    char_literal38=(Token)match(input,51,FOLLOW_51_in_assignmentDecl227); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal38_tree = 
                    (Object)adaptor.create(char_literal38)
                    ;
                    adaptor.addChild(root_0, char_literal38_tree);
                    }

                    char_literal39=(Token)match(input,65,FOLLOW_65_in_assignmentDecl229); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal39_tree = 
                    (Object)adaptor.create(char_literal39)
                    ;
                    adaptor.addChild(root_0, char_literal39_tree);
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp2.g:55:5: 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal40=(Token)match(input,126,FOLLOW_126_in_assignmentDecl235); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal40_tree = 
                    (Object)adaptor.create(string_literal40)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal40_tree, root_0);
                    }

                    string_literal41=(Token)match(input,109,FOLLOW_109_in_assignmentDecl238); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal41_tree = 
                    (Object)adaptor.create(string_literal41)
                    ;
                    adaptor.addChild(root_0, string_literal41_tree);
                    }

                    char_literal42=(Token)match(input,50,FOLLOW_50_in_assignmentDecl240); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal42_tree = 
                    (Object)adaptor.create(char_literal42)
                    ;
                    adaptor.addChild(root_0, char_literal42_tree);
                    }

                    IntegerLiteral43=(Token)match(input,IntegerLiteral,FOLLOW_IntegerLiteral_in_assignmentDecl242); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IntegerLiteral43_tree = 
                    (Object)adaptor.create(IntegerLiteral43)
                    ;
                    adaptor.addChild(root_0, IntegerLiteral43_tree);
                    }

                    char_literal44=(Token)match(input,51,FOLLOW_51_in_assignmentDecl244); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal44_tree = 
                    (Object)adaptor.create(char_literal44)
                    ;
                    adaptor.addChild(root_0, char_literal44_tree);
                    }

                    char_literal45=(Token)match(input,65,FOLLOW_65_in_assignmentDecl246); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal45_tree = 
                    (Object)adaptor.create(char_literal45)
                    ;
                    adaptor.addChild(root_0, char_literal45_tree);
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp2.g:56:5: 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal46=(Token)match(input,126,FOLLOW_126_in_assignmentDecl252); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal46_tree = 
                    (Object)adaptor.create(string_literal46)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal46_tree, root_0);
                    }

                    string_literal47=(Token)match(input,110,FOLLOW_110_in_assignmentDecl255); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal47_tree = 
                    (Object)adaptor.create(string_literal47)
                    ;
                    adaptor.addChild(root_0, string_literal47_tree);
                    }

                    char_literal48=(Token)match(input,50,FOLLOW_50_in_assignmentDecl257); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal48_tree = 
                    (Object)adaptor.create(char_literal48)
                    ;
                    adaptor.addChild(root_0, char_literal48_tree);
                    }

                    IntegerLiteral49=(Token)match(input,IntegerLiteral,FOLLOW_IntegerLiteral_in_assignmentDecl259); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IntegerLiteral49_tree = 
                    (Object)adaptor.create(IntegerLiteral49)
                    ;
                    adaptor.addChild(root_0, IntegerLiteral49_tree);
                    }

                    char_literal50=(Token)match(input,51,FOLLOW_51_in_assignmentDecl261); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal50_tree = 
                    (Object)adaptor.create(char_literal50)
                    ;
                    adaptor.addChild(root_0, char_literal50_tree);
                    }

                    char_literal51=(Token)match(input,65,FOLLOW_65_in_assignmentDecl263); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal51_tree = 
                    (Object)adaptor.create(char_literal51)
                    ;
                    adaptor.addChild(root_0, char_literal51_tree);
                    }

                    }
                    break;
                case 9 :
                    // org\\aries\\tmp2.g:57:5: 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal52=(Token)match(input,126,FOLLOW_126_in_assignmentDecl269); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal52_tree = 
                    (Object)adaptor.create(string_literal52)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal52_tree, root_0);
                    }

                    string_literal53=(Token)match(input,111,FOLLOW_111_in_assignmentDecl272); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal53_tree = 
                    (Object)adaptor.create(string_literal53)
                    ;
                    adaptor.addChild(root_0, string_literal53_tree);
                    }

                    char_literal54=(Token)match(input,50,FOLLOW_50_in_assignmentDecl274); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal54_tree = 
                    (Object)adaptor.create(char_literal54)
                    ;
                    adaptor.addChild(root_0, char_literal54_tree);
                    }

                    STRINGLITERAL55=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl276); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL55_tree = 
                    (Object)adaptor.create(STRINGLITERAL55)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL55_tree);
                    }

                    char_literal56=(Token)match(input,51,FOLLOW_51_in_assignmentDecl278); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal56_tree = 
                    (Object)adaptor.create(char_literal56)
                    ;
                    adaptor.addChild(root_0, char_literal56_tree);
                    }

                    char_literal57=(Token)match(input,65,FOLLOW_65_in_assignmentDecl280); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal57_tree = 
                    (Object)adaptor.create(char_literal57)
                    ;
                    adaptor.addChild(root_0, char_literal57_tree);
                    }

                    }
                    break;
                case 10 :
                    // org\\aries\\tmp2.g:58:5: 'set' ^ 'participant' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal58=(Token)match(input,126,FOLLOW_126_in_assignmentDecl286); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal58_tree = 
                    (Object)adaptor.create(string_literal58)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal58_tree, root_0);
                    }

                    string_literal59=(Token)match(input,113,FOLLOW_113_in_assignmentDecl289); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal59_tree = 
                    (Object)adaptor.create(string_literal59)
                    ;
                    adaptor.addChild(root_0, string_literal59_tree);
                    }

                    char_literal60=(Token)match(input,50,FOLLOW_50_in_assignmentDecl291); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal60_tree = 
                    (Object)adaptor.create(char_literal60)
                    ;
                    adaptor.addChild(root_0, char_literal60_tree);
                    }

                    STRINGLITERAL61=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl293); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL61_tree = 
                    (Object)adaptor.create(STRINGLITERAL61)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL61_tree);
                    }

                    char_literal62=(Token)match(input,51,FOLLOW_51_in_assignmentDecl295); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal62_tree = 
                    (Object)adaptor.create(char_literal62)
                    ;
                    adaptor.addChild(root_0, char_literal62_tree);
                    }

                    char_literal63=(Token)match(input,65,FOLLOW_65_in_assignmentDecl297); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal63_tree = 
                    (Object)adaptor.create(char_literal63)
                    ;
                    adaptor.addChild(root_0, char_literal63_tree);
                    }

                    }
                    break;
                case 11 :
                    // org\\aries\\tmp2.g:59:5: 'set' ^ 'restriction' '(' qualifiedName ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal64=(Token)match(input,126,FOLLOW_126_in_assignmentDecl303); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal64_tree = 
                    (Object)adaptor.create(string_literal64)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal64_tree, root_0);
                    }

                    string_literal65=(Token)match(input,119,FOLLOW_119_in_assignmentDecl306); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal65_tree = 
                    (Object)adaptor.create(string_literal65)
                    ;
                    adaptor.addChild(root_0, string_literal65_tree);
                    }

                    char_literal66=(Token)match(input,50,FOLLOW_50_in_assignmentDecl308); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal66_tree = 
                    (Object)adaptor.create(char_literal66)
                    ;
                    adaptor.addChild(root_0, char_literal66_tree);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_assignmentDecl310);
                    qualifiedName67=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName67.getTree());

                    char_literal68=(Token)match(input,51,FOLLOW_51_in_assignmentDecl312); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal68_tree = 
                    (Object)adaptor.create(char_literal68)
                    ;
                    adaptor.addChild(root_0, char_literal68_tree);
                    }

                    char_literal69=(Token)match(input,65,FOLLOW_65_in_assignmentDecl314); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal69_tree = 
                    (Object)adaptor.create(char_literal69)
                    ;
                    adaptor.addChild(root_0, char_literal69_tree);
                    }

                    }
                    break;
                case 12 :
                    // org\\aries\\tmp2.g:60:5: 'set' ^ 'role' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal70=(Token)match(input,126,FOLLOW_126_in_assignmentDecl320); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal70_tree = 
                    (Object)adaptor.create(string_literal70)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal70_tree, root_0);
                    }

                    string_literal71=(Token)match(input,121,FOLLOW_121_in_assignmentDecl323); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal71_tree = 
                    (Object)adaptor.create(string_literal71)
                    ;
                    adaptor.addChild(root_0, string_literal71_tree);
                    }

                    char_literal72=(Token)match(input,50,FOLLOW_50_in_assignmentDecl325); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal72_tree = 
                    (Object)adaptor.create(char_literal72)
                    ;
                    adaptor.addChild(root_0, char_literal72_tree);
                    }

                    STRINGLITERAL73=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl327); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL73_tree = 
                    (Object)adaptor.create(STRINGLITERAL73)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL73_tree);
                    }

                    char_literal74=(Token)match(input,51,FOLLOW_51_in_assignmentDecl329); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal74_tree = 
                    (Object)adaptor.create(char_literal74)
                    ;
                    adaptor.addChild(root_0, char_literal74_tree);
                    }

                    char_literal75=(Token)match(input,65,FOLLOW_65_in_assignmentDecl331); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal75_tree = 
                    (Object)adaptor.create(char_literal75)
                    ;
                    adaptor.addChild(root_0, char_literal75_tree);
                    }

                    }
                    break;
                case 13 :
                    // org\\aries\\tmp2.g:61:5: 'add' ^ 'role' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal76=(Token)match(input,76,FOLLOW_76_in_assignmentDecl337); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal76_tree = 
                    (Object)adaptor.create(string_literal76)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal76_tree, root_0);
                    }

                    string_literal77=(Token)match(input,121,FOLLOW_121_in_assignmentDecl340); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal77_tree = 
                    (Object)adaptor.create(string_literal77)
                    ;
                    adaptor.addChild(root_0, string_literal77_tree);
                    }

                    char_literal78=(Token)match(input,50,FOLLOW_50_in_assignmentDecl342); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal78_tree = 
                    (Object)adaptor.create(char_literal78)
                    ;
                    adaptor.addChild(root_0, char_literal78_tree);
                    }

                    STRINGLITERAL79=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl344); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL79_tree = 
                    (Object)adaptor.create(STRINGLITERAL79)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL79_tree);
                    }

                    char_literal80=(Token)match(input,51,FOLLOW_51_in_assignmentDecl346); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal80_tree = 
                    (Object)adaptor.create(char_literal80)
                    ;
                    adaptor.addChild(root_0, char_literal80_tree);
                    }

                    char_literal81=(Token)match(input,65,FOLLOW_65_in_assignmentDecl348); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal81_tree = 
                    (Object)adaptor.create(char_literal81)
                    ;
                    adaptor.addChild(root_0, char_literal81_tree);
                    }

                    }
                    break;
                case 14 :
                    // org\\aries\\tmp2.g:62:5: 'set' ^ 'scope' '(' ScopeType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal82=(Token)match(input,126,FOLLOW_126_in_assignmentDecl354); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal82_tree = 
                    (Object)adaptor.create(string_literal82)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal82_tree, root_0);
                    }

                    string_literal83=(Token)match(input,123,FOLLOW_123_in_assignmentDecl357); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal83_tree = 
                    (Object)adaptor.create(string_literal83)
                    ;
                    adaptor.addChild(root_0, string_literal83_tree);
                    }

                    char_literal84=(Token)match(input,50,FOLLOW_50_in_assignmentDecl359); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal84_tree = 
                    (Object)adaptor.create(char_literal84)
                    ;
                    adaptor.addChild(root_0, char_literal84_tree);
                    }

                    ScopeType85=(Token)match(input,ScopeType,FOLLOW_ScopeType_in_assignmentDecl361); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ScopeType85_tree = 
                    (Object)adaptor.create(ScopeType85)
                    ;
                    adaptor.addChild(root_0, ScopeType85_tree);
                    }

                    char_literal86=(Token)match(input,51,FOLLOW_51_in_assignmentDecl363); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal86_tree = 
                    (Object)adaptor.create(char_literal86)
                    ;
                    adaptor.addChild(root_0, char_literal86_tree);
                    }

                    char_literal87=(Token)match(input,65,FOLLOW_65_in_assignmentDecl365); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal87_tree = 
                    (Object)adaptor.create(char_literal87)
                    ;
                    adaptor.addChild(root_0, char_literal87_tree);
                    }

                    }
                    break;
                case 15 :
                    // org\\aries\\tmp2.g:63:5: 'set' ^ 'source' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal88=(Token)match(input,126,FOLLOW_126_in_assignmentDecl371); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal88_tree = 
                    (Object)adaptor.create(string_literal88)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal88_tree, root_0);
                    }

                    string_literal89=(Token)match(input,128,FOLLOW_128_in_assignmentDecl374); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal89_tree = 
                    (Object)adaptor.create(string_literal89)
                    ;
                    adaptor.addChild(root_0, string_literal89_tree);
                    }

                    char_literal90=(Token)match(input,50,FOLLOW_50_in_assignmentDecl376); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal90_tree = 
                    (Object)adaptor.create(char_literal90)
                    ;
                    adaptor.addChild(root_0, char_literal90_tree);
                    }

                    STRINGLITERAL91=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl378); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL91_tree = 
                    (Object)adaptor.create(STRINGLITERAL91)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL91_tree);
                    }

                    char_literal92=(Token)match(input,51,FOLLOW_51_in_assignmentDecl380); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal92_tree = 
                    (Object)adaptor.create(char_literal92)
                    ;
                    adaptor.addChild(root_0, char_literal92_tree);
                    }

                    char_literal93=(Token)match(input,65,FOLLOW_65_in_assignmentDecl382); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal93_tree = 
                    (Object)adaptor.create(char_literal93)
                    ;
                    adaptor.addChild(root_0, char_literal93_tree);
                    }

                    }
                    break;
                case 16 :
                    // org\\aries\\tmp2.g:64:5: 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal94=(Token)match(input,126,FOLLOW_126_in_assignmentDecl388); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal94_tree = 
                    (Object)adaptor.create(string_literal94)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal94_tree, root_0);
                    }

                    string_literal95=(Token)match(input,129,FOLLOW_129_in_assignmentDecl391); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal95_tree = 
                    (Object)adaptor.create(string_literal95)
                    ;
                    adaptor.addChild(root_0, string_literal95_tree);
                    }

                    char_literal96=(Token)match(input,50,FOLLOW_50_in_assignmentDecl393); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal96_tree = 
                    (Object)adaptor.create(char_literal96)
                    ;
                    adaptor.addChild(root_0, char_literal96_tree);
                    }

                    set97=(Token)input.LT(1);

                    if ( input.LA(1)==FALSE||input.LA(1)==TRUE ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (Object)adaptor.create(set97)
                        );
                        state.errorRecovery=false;
                        state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    char_literal98=(Token)match(input,51,FOLLOW_51_in_assignmentDecl403); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal98_tree = 
                    (Object)adaptor.create(char_literal98)
                    ;
                    adaptor.addChild(root_0, char_literal98_tree);
                    }

                    char_literal99=(Token)match(input,65,FOLLOW_65_in_assignmentDecl405); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal99_tree = 
                    (Object)adaptor.create(char_literal99)
                    ;
                    adaptor.addChild(root_0, char_literal99_tree);
                    }

                    }
                    break;
                case 17 :
                    // org\\aries\\tmp2.g:65:5: 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal100=(Token)match(input,126,FOLLOW_126_in_assignmentDecl411); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal100_tree = 
                    (Object)adaptor.create(string_literal100)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal100_tree, root_0);
                    }

                    string_literal101=(Token)match(input,133,FOLLOW_133_in_assignmentDecl414); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal101_tree = 
                    (Object)adaptor.create(string_literal101)
                    ;
                    adaptor.addChild(root_0, string_literal101_tree);
                    }

                    char_literal102=(Token)match(input,50,FOLLOW_50_in_assignmentDecl416); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal102_tree = 
                    (Object)adaptor.create(char_literal102)
                    ;
                    adaptor.addChild(root_0, char_literal102_tree);
                    }

                    TimeoutLiteral103=(Token)match(input,TimeoutLiteral,FOLLOW_TimeoutLiteral_in_assignmentDecl418); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TimeoutLiteral103_tree = 
                    (Object)adaptor.create(TimeoutLiteral103)
                    ;
                    adaptor.addChild(root_0, TimeoutLiteral103_tree);
                    }

                    char_literal104=(Token)match(input,51,FOLLOW_51_in_assignmentDecl420); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal104_tree = 
                    (Object)adaptor.create(char_literal104)
                    ;
                    adaptor.addChild(root_0, char_literal104_tree);
                    }

                    char_literal105=(Token)match(input,65,FOLLOW_65_in_assignmentDecl422); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal105_tree = 
                    (Object)adaptor.create(char_literal105)
                    ;
                    adaptor.addChild(root_0, char_literal105_tree);
                    }

                    }
                    break;
                case 18 :
                    // org\\aries\\tmp2.g:66:5: 'set' ^ 'transaction' '(' TransactionType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal106=(Token)match(input,126,FOLLOW_126_in_assignmentDecl428); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal106_tree = 
                    (Object)adaptor.create(string_literal106)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal106_tree, root_0);
                    }

                    string_literal107=(Token)match(input,134,FOLLOW_134_in_assignmentDecl431); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal107_tree = 
                    (Object)adaptor.create(string_literal107)
                    ;
                    adaptor.addChild(root_0, string_literal107_tree);
                    }

                    char_literal108=(Token)match(input,50,FOLLOW_50_in_assignmentDecl433); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal108_tree = 
                    (Object)adaptor.create(char_literal108)
                    ;
                    adaptor.addChild(root_0, char_literal108_tree);
                    }

                    TransactionType109=(Token)match(input,TransactionType,FOLLOW_TransactionType_in_assignmentDecl435); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TransactionType109_tree = 
                    (Object)adaptor.create(TransactionType109)
                    ;
                    adaptor.addChild(root_0, TransactionType109_tree);
                    }

                    char_literal110=(Token)match(input,51,FOLLOW_51_in_assignmentDecl437); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal110_tree = 
                    (Object)adaptor.create(char_literal110)
                    ;
                    adaptor.addChild(root_0, char_literal110_tree);
                    }

                    char_literal111=(Token)match(input,65,FOLLOW_65_in_assignmentDecl439); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal111_tree = 
                    (Object)adaptor.create(char_literal111)
                    ;
                    adaptor.addChild(root_0, char_literal111_tree);
                    }

                    }
                    break;
                case 19 :
                    // org\\aries\\tmp2.g:67:5: 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal112=(Token)match(input,104,FOLLOW_104_in_assignmentDecl445); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal112_tree = 
                    (Object)adaptor.create(string_literal112)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal112_tree, root_0);
                    }

                    string_literal113=(Token)match(input,134,FOLLOW_134_in_assignmentDecl448); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal113_tree = 
                    (Object)adaptor.create(string_literal113)
                    ;
                    adaptor.addChild(root_0, string_literal113_tree);
                    }

                    char_literal114=(Token)match(input,50,FOLLOW_50_in_assignmentDecl450); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal114_tree = 
                    (Object)adaptor.create(char_literal114)
                    ;
                    adaptor.addChild(root_0, char_literal114_tree);
                    }

                    STRINGLITERAL115=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl452); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL115_tree = 
                    (Object)adaptor.create(STRINGLITERAL115)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL115_tree);
                    }

                    char_literal116=(Token)match(input,51,FOLLOW_51_in_assignmentDecl454); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal116_tree = 
                    (Object)adaptor.create(char_literal116)
                    ;
                    adaptor.addChild(root_0, char_literal116_tree);
                    }

                    char_literal117=(Token)match(input,65,FOLLOW_65_in_assignmentDecl456); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal117_tree = 
                    (Object)adaptor.create(char_literal117)
                    ;
                    adaptor.addChild(root_0, char_literal117_tree);
                    }

                    }
                    break;
                case 20 :
                    // org\\aries\\tmp2.g:68:5: 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal118=(Token)match(input,99,FOLLOW_99_in_assignmentDecl462); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal118_tree = 
                    (Object)adaptor.create(string_literal118)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal118_tree, root_0);
                    }

                    string_literal119=(Token)match(input,111,FOLLOW_111_in_assignmentDecl465); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal119_tree = 
                    (Object)adaptor.create(string_literal119)
                    ;
                    adaptor.addChild(root_0, string_literal119_tree);
                    }

                    char_literal120=(Token)match(input,50,FOLLOW_50_in_assignmentDecl467); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal120_tree = 
                    (Object)adaptor.create(char_literal120)
                    ;
                    adaptor.addChild(root_0, char_literal120_tree);
                    }

                    STRINGLITERAL121=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl469); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL121_tree = 
                    (Object)adaptor.create(STRINGLITERAL121)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL121_tree);
                    }

                    char_literal122=(Token)match(input,51,FOLLOW_51_in_assignmentDecl471); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal122_tree = 
                    (Object)adaptor.create(char_literal122)
                    ;
                    adaptor.addChild(root_0, char_literal122_tree);
                    }

                    char_literal123=(Token)match(input,65,FOLLOW_65_in_assignmentDecl473); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal123_tree = 
                    (Object)adaptor.create(char_literal123)
                    ;
                    adaptor.addChild(root_0, char_literal123_tree);
                    }

                    }
                    break;
                case 21 :
                    // org\\aries\\tmp2.g:69:5: 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal124=(Token)match(input,98,FOLLOW_98_in_assignmentDecl479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal124_tree = 
                    (Object)adaptor.create(string_literal124)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal124_tree, root_0);
                    }

                    string_literal125=(Token)match(input,111,FOLLOW_111_in_assignmentDecl482); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal125_tree = 
                    (Object)adaptor.create(string_literal125)
                    ;
                    adaptor.addChild(root_0, string_literal125_tree);
                    }

                    char_literal126=(Token)match(input,50,FOLLOW_50_in_assignmentDecl484); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal126_tree = 
                    (Object)adaptor.create(char_literal126)
                    ;
                    adaptor.addChild(root_0, char_literal126_tree);
                    }

                    STRINGLITERAL127=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl486); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL127_tree = 
                    (Object)adaptor.create(STRINGLITERAL127)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL127_tree);
                    }

                    char_literal128=(Token)match(input,51,FOLLOW_51_in_assignmentDecl488); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal128_tree = 
                    (Object)adaptor.create(char_literal128)
                    ;
                    adaptor.addChild(root_0, char_literal128_tree);
                    }

                    char_literal129=(Token)match(input,65,FOLLOW_65_in_assignmentDecl490); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal129_tree = 
                    (Object)adaptor.create(char_literal129)
                    ;
                    adaptor.addChild(root_0, char_literal129_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 2, assignmentDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "assignmentDecl"


    public static class branchDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "branchDecl"
    // org\\aries\\tmp2.g:72:1: branchDecl : 'branch' ^ ( Identifier )? ':' branchBody ;
    public final tmp2Parser.branchDecl_return branchDecl() throws RecognitionException {
        tmp2Parser.branchDecl_return retval = new tmp2Parser.branchDecl_return();
        retval.start = input.LT(1);

        int branchDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal130=null;
        Token Identifier131=null;
        Token char_literal132=null;
        tmp2Parser.branchBody_return branchBody133 =null;


        Object string_literal130_tree=null;
        Object Identifier131_tree=null;
        Object char_literal132_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }

            // org\\aries\\tmp2.g:73:2: ( 'branch' ^ ( Identifier )? ':' branchBody )
            // org\\aries\\tmp2.g:73:5: 'branch' ^ ( Identifier )? ':' branchBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal130=(Token)match(input,79,FOLLOW_79_in_branchDecl502); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal130_tree = 
            (Object)adaptor.create(string_literal130)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal130_tree, root_0);
            }

            // org\\aries\\tmp2.g:73:15: ( Identifier )?
            int alt4=2;
            switch ( input.LA(1) ) {
                case Identifier:
                    {
                    alt4=1;
                    }
                    break;
            }

            switch (alt4) {
                case 1 :
                    // org\\aries\\tmp2.g:73:15: Identifier
                    {
                    Identifier131=(Token)match(input,Identifier,FOLLOW_Identifier_in_branchDecl505); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier131_tree = 
                    (Object)adaptor.create(Identifier131)
                    ;
                    adaptor.addChild(root_0, Identifier131_tree);
                    }

                    }
                    break;

            }


            char_literal132=(Token)match(input,64,FOLLOW_64_in_branchDecl508); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal132_tree = 
            (Object)adaptor.create(char_literal132)
            ;
            adaptor.addChild(root_0, char_literal132_tree);
            }

            pushFollow(FOLLOW_branchBody_in_branchDecl510);
            branchBody133=branchBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, branchBody133.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 3, branchDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "branchDecl"


    public static class branchBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "branchBody"
    // org\\aries\\tmp2.g:76:1: branchBody : '{' ( branchMember )* '}' ;
    public final tmp2Parser.branchBody_return branchBody() throws RecognitionException {
        tmp2Parser.branchBody_return retval = new tmp2Parser.branchBody_return();
        retval.start = input.LT(1);

        int branchBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal134=null;
        Token char_literal136=null;
        tmp2Parser.branchMember_return branchMember135 =null;


        Object char_literal134_tree=null;
        Object char_literal136_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }

            // org\\aries\\tmp2.g:77:2: ( '{' ( branchMember )* '}' )
            // org\\aries\\tmp2.g:77:5: '{' ( branchMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal134=(Token)match(input,136,FOLLOW_136_in_branchBody523); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal134_tree = 
            (Object)adaptor.create(char_literal134)
            ;
            adaptor.addChild(root_0, char_literal134_tree);
            }

            // org\\aries\\tmp2.g:77:9: ( branchMember )*
            loop5:
            do {
                int alt5=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 94:
                case 95:
                case 97:
                case 101:
                case 106:
                case 107:
                case 112:
                case 115:
                case 118:
                case 120:
                case 124:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt5=1;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // org\\aries\\tmp2.g:77:10: branchMember
            	    {
            	    pushFollow(FOLLOW_branchMember_in_branchBody526);
            	    branchMember135=branchMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, branchMember135.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            char_literal136=(Token)match(input,140,FOLLOW_140_in_branchBody530); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal136_tree = 
            (Object)adaptor.create(char_literal136)
            ;
            adaptor.addChild(root_0, char_literal136_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 4, branchBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "branchBody"


    public static class branchMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "branchMember"
    // org\\aries\\tmp2.g:80:1: branchMember : ( optionDecl | listenDecl | statementDecl );
    public final tmp2Parser.branchMember_return branchMember() throws RecognitionException {
        tmp2Parser.branchMember_return retval = new tmp2Parser.branchMember_return();
        retval.start = input.LT(1);

        int branchMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.optionDecl_return optionDecl137 =null;

        tmp2Parser.listenDecl_return listenDecl138 =null;

        tmp2Parser.statementDecl_return statementDecl139 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }

            // org\\aries\\tmp2.g:81:2: ( optionDecl | listenDecl | statementDecl )
            int alt6=3;
            switch ( input.LA(1) ) {
            case 112:
                {
                alt6=1;
                }
                break;
            case 106:
                {
                alt6=2;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt6=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }

            switch (alt6) {
                case 1 :
                    // org\\aries\\tmp2.g:81:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_branchMember541);
                    optionDecl137=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl137.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:82:5: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_branchMember547);
                    listenDecl138=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl138.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:83:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_branchMember552);
                    statementDecl139=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl139.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 5, branchMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "branchMember"


    public static class cacheDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cacheDecl"
    // org\\aries\\tmp2.g:87:1: cacheDecl : 'cache' ^ Identifier cacheBody ;
    public final tmp2Parser.cacheDecl_return cacheDecl() throws RecognitionException {
        tmp2Parser.cacheDecl_return retval = new tmp2Parser.cacheDecl_return();
        retval.start = input.LT(1);

        int cacheDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal140=null;
        Token Identifier141=null;
        tmp2Parser.cacheBody_return cacheBody142 =null;


        Object string_literal140_tree=null;
        Object Identifier141_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }

            // org\\aries\\tmp2.g:88:2: ( 'cache' ^ Identifier cacheBody )
            // org\\aries\\tmp2.g:88:5: 'cache' ^ Identifier cacheBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal140=(Token)match(input,82,FOLLOW_82_in_cacheDecl565); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal140_tree = 
            (Object)adaptor.create(string_literal140)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal140_tree, root_0);
            }

            Identifier141=(Token)match(input,Identifier,FOLLOW_Identifier_in_cacheDecl568); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier141_tree = 
            (Object)adaptor.create(Identifier141)
            ;
            adaptor.addChild(root_0, Identifier141_tree);
            }

            pushFollow(FOLLOW_cacheBody_in_cacheDecl570);
            cacheBody142=cacheBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheBody142.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 6, cacheDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "cacheDecl"


    public static class cacheBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cacheBody"
    // org\\aries\\tmp2.g:91:1: cacheBody : '{' ( cacheMember )* '}' ;
    public final tmp2Parser.cacheBody_return cacheBody() throws RecognitionException {
        tmp2Parser.cacheBody_return retval = new tmp2Parser.cacheBody_return();
        retval.start = input.LT(1);

        int cacheBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal143=null;
        Token char_literal145=null;
        tmp2Parser.cacheMember_return cacheMember144 =null;


        Object char_literal143_tree=null;
        Object char_literal145_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }

            // org\\aries\\tmp2.g:92:2: ( '{' ( cacheMember )* '}' )
            // org\\aries\\tmp2.g:92:5: '{' ( cacheMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal143=(Token)match(input,136,FOLLOW_136_in_cacheBody582); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal143_tree = 
            (Object)adaptor.create(char_literal143)
            ;
            adaptor.addChild(root_0, char_literal143_tree);
            }

            // org\\aries\\tmp2.g:92:9: ( cacheMember )*
            loop7:
            do {
                int alt7=2;
                switch ( input.LA(1) ) {
                case 76:
                case 98:
                case 99:
                case 103:
                case 104:
                case 126:
                    {
                    alt7=1;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // org\\aries\\tmp2.g:92:10: cacheMember
            	    {
            	    pushFollow(FOLLOW_cacheMember_in_cacheBody585);
            	    cacheMember144=cacheMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheMember144.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            char_literal145=(Token)match(input,140,FOLLOW_140_in_cacheBody589); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal145_tree = 
            (Object)adaptor.create(char_literal145)
            ;
            adaptor.addChild(root_0, char_literal145_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 7, cacheBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "cacheBody"


    public static class cacheMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cacheMember"
    // org\\aries\\tmp2.g:95:1: cacheMember : ( assignmentDecl | itemsDecl );
    public final tmp2Parser.cacheMember_return cacheMember() throws RecognitionException {
        tmp2Parser.cacheMember_return retval = new tmp2Parser.cacheMember_return();
        retval.start = input.LT(1);

        int cacheMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl146 =null;

        tmp2Parser.itemsDecl_return itemsDecl147 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }

            // org\\aries\\tmp2.g:96:2: ( assignmentDecl | itemsDecl )
            int alt8=2;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt8=1;
                }
                break;
            case 103:
                {
                alt8=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // org\\aries\\tmp2.g:96:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_cacheMember600);
                    assignmentDecl146=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl146.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:97:4: itemsDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_itemsDecl_in_cacheMember605);
                    itemsDecl147=itemsDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsDecl147.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 8, cacheMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "cacheMember"


    public static class channelDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "channelDecl"
    // org\\aries\\tmp2.g:100:1: channelDecl : 'channel' ^ Identifier '{' ( channelBody )* '}' ;
    public final tmp2Parser.channelDecl_return channelDecl() throws RecognitionException {
        tmp2Parser.channelDecl_return retval = new tmp2Parser.channelDecl_return();
        retval.start = input.LT(1);

        int channelDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal148=null;
        Token Identifier149=null;
        Token char_literal150=null;
        Token char_literal152=null;
        tmp2Parser.channelBody_return channelBody151 =null;


        Object string_literal148_tree=null;
        Object Identifier149_tree=null;
        Object char_literal150_tree=null;
        Object char_literal152_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }

            // org\\aries\\tmp2.g:101:2: ( 'channel' ^ Identifier '{' ( channelBody )* '}' )
            // org\\aries\\tmp2.g:101:4: 'channel' ^ Identifier '{' ( channelBody )* '}'
            {
            root_0 = (Object)adaptor.nil();


            string_literal148=(Token)match(input,83,FOLLOW_83_in_channelDecl616); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal148_tree = 
            (Object)adaptor.create(string_literal148)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal148_tree, root_0);
            }

            Identifier149=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelDecl619); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier149_tree = 
            (Object)adaptor.create(Identifier149)
            ;
            adaptor.addChild(root_0, Identifier149_tree);
            }

            char_literal150=(Token)match(input,136,FOLLOW_136_in_channelDecl621); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal150_tree = 
            (Object)adaptor.create(char_literal150)
            ;
            adaptor.addChild(root_0, char_literal150_tree);
            }

            // org\\aries\\tmp2.g:101:30: ( channelBody )*
            loop9:
            do {
                int alt9=2;
                switch ( input.LA(1) ) {
                case 65:
                case 76:
                case 126:
                    {
                    alt9=1;
                    }
                    break;

                }

                switch (alt9) {
            	case 1 :
            	    // org\\aries\\tmp2.g:101:31: channelBody
            	    {
            	    pushFollow(FOLLOW_channelBody_in_channelDecl624);
            	    channelBody151=channelBody();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, channelBody151.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            char_literal152=(Token)match(input,140,FOLLOW_140_in_channelDecl628); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal152_tree = 
            (Object)adaptor.create(char_literal152)
            ;
            adaptor.addChild(root_0, char_literal152_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 9, channelDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "channelDecl"


    public static class channelBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "channelBody"
    // org\\aries\\tmp2.g:104:1: channelBody : ( 'add' 'client' '(' Identifier ')' ';' | 'add' 'service' '(' Identifier ')' ';' | 'add' 'manager' '(' Identifier ')' ';' | 'set' 'domain' '(' Identifier ')' ';' | ';' );
    public final tmp2Parser.channelBody_return channelBody() throws RecognitionException {
        tmp2Parser.channelBody_return retval = new tmp2Parser.channelBody_return();
        retval.start = input.LT(1);

        int channelBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal153=null;
        Token string_literal154=null;
        Token char_literal155=null;
        Token Identifier156=null;
        Token char_literal157=null;
        Token char_literal158=null;
        Token string_literal159=null;
        Token string_literal160=null;
        Token char_literal161=null;
        Token Identifier162=null;
        Token char_literal163=null;
        Token char_literal164=null;
        Token string_literal165=null;
        Token string_literal166=null;
        Token char_literal167=null;
        Token Identifier168=null;
        Token char_literal169=null;
        Token char_literal170=null;
        Token string_literal171=null;
        Token string_literal172=null;
        Token char_literal173=null;
        Token Identifier174=null;
        Token char_literal175=null;
        Token char_literal176=null;
        Token char_literal177=null;

        Object string_literal153_tree=null;
        Object string_literal154_tree=null;
        Object char_literal155_tree=null;
        Object Identifier156_tree=null;
        Object char_literal157_tree=null;
        Object char_literal158_tree=null;
        Object string_literal159_tree=null;
        Object string_literal160_tree=null;
        Object char_literal161_tree=null;
        Object Identifier162_tree=null;
        Object char_literal163_tree=null;
        Object char_literal164_tree=null;
        Object string_literal165_tree=null;
        Object string_literal166_tree=null;
        Object char_literal167_tree=null;
        Object Identifier168_tree=null;
        Object char_literal169_tree=null;
        Object char_literal170_tree=null;
        Object string_literal171_tree=null;
        Object string_literal172_tree=null;
        Object char_literal173_tree=null;
        Object Identifier174_tree=null;
        Object char_literal175_tree=null;
        Object char_literal176_tree=null;
        Object char_literal177_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }

            // org\\aries\\tmp2.g:105:2: ( 'add' 'client' '(' Identifier ')' ';' | 'add' 'service' '(' Identifier ')' ';' | 'add' 'manager' '(' Identifier ')' ';' | 'set' 'domain' '(' Identifier ')' ';' | ';' )
            int alt10=5;
            switch ( input.LA(1) ) {
            case 76:
                {
                switch ( input.LA(2) ) {
                case 85:
                    {
                    alt10=1;
                    }
                    break;
                case 125:
                    {
                    alt10=2;
                    }
                    break;
                case 108:
                    {
                    alt10=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;

                }

                }
                break;
            case 126:
                {
                alt10=4;
                }
                break;
            case 65:
                {
                alt10=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }

            switch (alt10) {
                case 1 :
                    // org\\aries\\tmp2.g:105:4: 'add' 'client' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal153=(Token)match(input,76,FOLLOW_76_in_channelBody639); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal153_tree = 
                    (Object)adaptor.create(string_literal153)
                    ;
                    adaptor.addChild(root_0, string_literal153_tree);
                    }

                    string_literal154=(Token)match(input,85,FOLLOW_85_in_channelBody641); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal154_tree = 
                    (Object)adaptor.create(string_literal154)
                    ;
                    adaptor.addChild(root_0, string_literal154_tree);
                    }

                    char_literal155=(Token)match(input,50,FOLLOW_50_in_channelBody643); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal155_tree = 
                    (Object)adaptor.create(char_literal155)
                    ;
                    adaptor.addChild(root_0, char_literal155_tree);
                    }

                    Identifier156=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody645); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier156_tree = 
                    (Object)adaptor.create(Identifier156)
                    ;
                    adaptor.addChild(root_0, Identifier156_tree);
                    }

                    char_literal157=(Token)match(input,51,FOLLOW_51_in_channelBody647); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal157_tree = 
                    (Object)adaptor.create(char_literal157)
                    ;
                    adaptor.addChild(root_0, char_literal157_tree);
                    }

                    char_literal158=(Token)match(input,65,FOLLOW_65_in_channelBody649); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal158_tree = 
                    (Object)adaptor.create(char_literal158)
                    ;
                    adaptor.addChild(root_0, char_literal158_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:106:4: 'add' 'service' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal159=(Token)match(input,76,FOLLOW_76_in_channelBody654); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal159_tree = 
                    (Object)adaptor.create(string_literal159)
                    ;
                    adaptor.addChild(root_0, string_literal159_tree);
                    }

                    string_literal160=(Token)match(input,125,FOLLOW_125_in_channelBody656); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal160_tree = 
                    (Object)adaptor.create(string_literal160)
                    ;
                    adaptor.addChild(root_0, string_literal160_tree);
                    }

                    char_literal161=(Token)match(input,50,FOLLOW_50_in_channelBody658); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal161_tree = 
                    (Object)adaptor.create(char_literal161)
                    ;
                    adaptor.addChild(root_0, char_literal161_tree);
                    }

                    Identifier162=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody660); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier162_tree = 
                    (Object)adaptor.create(Identifier162)
                    ;
                    adaptor.addChild(root_0, Identifier162_tree);
                    }

                    char_literal163=(Token)match(input,51,FOLLOW_51_in_channelBody662); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal163_tree = 
                    (Object)adaptor.create(char_literal163)
                    ;
                    adaptor.addChild(root_0, char_literal163_tree);
                    }

                    char_literal164=(Token)match(input,65,FOLLOW_65_in_channelBody664); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal164_tree = 
                    (Object)adaptor.create(char_literal164)
                    ;
                    adaptor.addChild(root_0, char_literal164_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:107:4: 'add' 'manager' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal165=(Token)match(input,76,FOLLOW_76_in_channelBody669); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal165_tree = 
                    (Object)adaptor.create(string_literal165)
                    ;
                    adaptor.addChild(root_0, string_literal165_tree);
                    }

                    string_literal166=(Token)match(input,108,FOLLOW_108_in_channelBody671); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal166_tree = 
                    (Object)adaptor.create(string_literal166)
                    ;
                    adaptor.addChild(root_0, string_literal166_tree);
                    }

                    char_literal167=(Token)match(input,50,FOLLOW_50_in_channelBody673); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal167_tree = 
                    (Object)adaptor.create(char_literal167)
                    ;
                    adaptor.addChild(root_0, char_literal167_tree);
                    }

                    Identifier168=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody675); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier168_tree = 
                    (Object)adaptor.create(Identifier168)
                    ;
                    adaptor.addChild(root_0, Identifier168_tree);
                    }

                    char_literal169=(Token)match(input,51,FOLLOW_51_in_channelBody677); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal169_tree = 
                    (Object)adaptor.create(char_literal169)
                    ;
                    adaptor.addChild(root_0, char_literal169_tree);
                    }

                    char_literal170=(Token)match(input,65,FOLLOW_65_in_channelBody679); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal170_tree = 
                    (Object)adaptor.create(char_literal170)
                    ;
                    adaptor.addChild(root_0, char_literal170_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:108:4: 'set' 'domain' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal171=(Token)match(input,126,FOLLOW_126_in_channelBody684); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal171_tree = 
                    (Object)adaptor.create(string_literal171)
                    ;
                    adaptor.addChild(root_0, string_literal171_tree);
                    }

                    string_literal172=(Token)match(input,89,FOLLOW_89_in_channelBody686); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal172_tree = 
                    (Object)adaptor.create(string_literal172)
                    ;
                    adaptor.addChild(root_0, string_literal172_tree);
                    }

                    char_literal173=(Token)match(input,50,FOLLOW_50_in_channelBody688); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal173_tree = 
                    (Object)adaptor.create(char_literal173)
                    ;
                    adaptor.addChild(root_0, char_literal173_tree);
                    }

                    Identifier174=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody690); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier174_tree = 
                    (Object)adaptor.create(Identifier174)
                    ;
                    adaptor.addChild(root_0, Identifier174_tree);
                    }

                    char_literal175=(Token)match(input,51,FOLLOW_51_in_channelBody692); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal175_tree = 
                    (Object)adaptor.create(char_literal175)
                    ;
                    adaptor.addChild(root_0, char_literal175_tree);
                    }

                    char_literal176=(Token)match(input,65,FOLLOW_65_in_channelBody694); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal176_tree = 
                    (Object)adaptor.create(char_literal176)
                    ;
                    adaptor.addChild(root_0, char_literal176_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:109:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal177=(Token)match(input,65,FOLLOW_65_in_channelBody699); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal177_tree = 
                    (Object)adaptor.create(char_literal177)
                    ;
                    adaptor.addChild(root_0, char_literal177_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 10, channelBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "channelBody"


    public static class commandDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "commandDecl"
    // org\\aries\\tmp2.g:112:1: commandDecl : ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' );
    public final tmp2Parser.commandDecl_return commandDecl() throws RecognitionException {
        tmp2Parser.commandDecl_return retval = new tmp2Parser.commandDecl_return();
        retval.start = input.LT(1);

        int commandDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal178=null;
        Token char_literal179=null;
        Token string_literal180=null;
        Token Identifier181=null;
        Token char_literal183=null;
        Token string_literal184=null;
        Token Identifier185=null;
        Token char_literal187=null;
        Token string_literal188=null;
        Token char_literal191=null;
        tmp2Parser.formalParameters_return formalParameters182 =null;

        tmp2Parser.formalParameters_return formalParameters186 =null;

        tmp2Parser.qualifiedName_return qualifiedName189 =null;

        tmp2Parser.formalParameters_return formalParameters190 =null;


        Object string_literal178_tree=null;
        Object char_literal179_tree=null;
        Object string_literal180_tree=null;
        Object Identifier181_tree=null;
        Object char_literal183_tree=null;
        Object string_literal184_tree=null;
        Object Identifier185_tree=null;
        Object char_literal187_tree=null;
        Object string_literal188_tree=null;
        Object char_literal191_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }

            // org\\aries\\tmp2.g:113:2: ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' )
            int alt14=4;
            switch ( input.LA(1) ) {
            case 92:
                {
                alt14=1;
                }
                break;
            case 115:
                {
                alt14=2;
                }
                break;
            case 118:
                {
                alt14=3;
                }
                break;
            case 124:
                {
                alt14=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;

            }

            switch (alt14) {
                case 1 :
                    // org\\aries\\tmp2.g:113:4: 'end' ^ ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal178=(Token)match(input,92,FOLLOW_92_in_commandDecl710); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal178_tree = 
                    (Object)adaptor.create(string_literal178)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal178_tree, root_0);
                    }

                    char_literal179=(Token)match(input,65,FOLLOW_65_in_commandDecl713); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal179_tree = 
                    (Object)adaptor.create(char_literal179)
                    ;
                    adaptor.addChild(root_0, char_literal179_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:114:4: 'post' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal180=(Token)match(input,115,FOLLOW_115_in_commandDecl718); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal180_tree = 
                    (Object)adaptor.create(string_literal180)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal180_tree, root_0);
                    }

                    Identifier181=(Token)match(input,Identifier,FOLLOW_Identifier_in_commandDecl721); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier181_tree = 
                    (Object)adaptor.create(Identifier181)
                    ;
                    adaptor.addChild(root_0, Identifier181_tree);
                    }

                    // org\\aries\\tmp2.g:114:23: ( formalParameters )?
                    int alt11=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt11=1;
                            }
                            break;
                    }

                    switch (alt11) {
                        case 1 :
                            // org\\aries\\tmp2.g:114:23: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_commandDecl723);
                            formalParameters182=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters182.getTree());

                            }
                            break;

                    }


                    char_literal183=(Token)match(input,65,FOLLOW_65_in_commandDecl726); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal183_tree = 
                    (Object)adaptor.create(char_literal183)
                    ;
                    adaptor.addChild(root_0, char_literal183_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:115:4: 'reply' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal184=(Token)match(input,118,FOLLOW_118_in_commandDecl731); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal184_tree = 
                    (Object)adaptor.create(string_literal184)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal184_tree, root_0);
                    }

                    Identifier185=(Token)match(input,Identifier,FOLLOW_Identifier_in_commandDecl734); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier185_tree = 
                    (Object)adaptor.create(Identifier185)
                    ;
                    adaptor.addChild(root_0, Identifier185_tree);
                    }

                    // org\\aries\\tmp2.g:115:24: ( formalParameters )?
                    int alt12=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt12=1;
                            }
                            break;
                    }

                    switch (alt12) {
                        case 1 :
                            // org\\aries\\tmp2.g:115:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_commandDecl736);
                            formalParameters186=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters186.getTree());

                            }
                            break;

                    }


                    char_literal187=(Token)match(input,65,FOLLOW_65_in_commandDecl739); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal187_tree = 
                    (Object)adaptor.create(char_literal187)
                    ;
                    adaptor.addChild(root_0, char_literal187_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:116:4: 'send' ^ qualifiedName ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal188=(Token)match(input,124,FOLLOW_124_in_commandDecl744); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal188_tree = 
                    (Object)adaptor.create(string_literal188)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal188_tree, root_0);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_commandDecl747);
                    qualifiedName189=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName189.getTree());

                    // org\\aries\\tmp2.g:116:26: ( formalParameters )?
                    int alt13=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt13=1;
                            }
                            break;
                    }

                    switch (alt13) {
                        case 1 :
                            // org\\aries\\tmp2.g:116:26: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_commandDecl749);
                            formalParameters190=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters190.getTree());

                            }
                            break;

                    }


                    char_literal191=(Token)match(input,65,FOLLOW_65_in_commandDecl752); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal191_tree = 
                    (Object)adaptor.create(char_literal191)
                    ;
                    adaptor.addChild(root_0, char_literal191_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 11, commandDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "commandDecl"


    public static class statementDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statementDecl"
    // org\\aries\\tmp2.g:120:1: statementDecl : ( statementBlock -> ^( BLOCK statementBlock ) | 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )? | 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl | 'while' ^ '(' expressionDecl ')' statementDecl | 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';' | 'return' ^ ( expressionDecl )? ';' | 'throw' ^ expressionDecl ';' | 'break' ^ ( Identifier )? ';' | 'continue' ^ ( Identifier )? ';' | expressionDecl ';' | commandDecl );
    public final tmp2Parser.statementDecl_return statementDecl() throws RecognitionException {
        tmp2Parser.statementDecl_return retval = new tmp2Parser.statementDecl_return();
        retval.start = input.LT(1);

        int statementDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal193=null;
        Token char_literal194=null;
        Token char_literal196=null;
        Token string_literal198=null;
        Token string_literal200=null;
        Token char_literal201=null;
        Token char_literal203=null;
        Token char_literal205=null;
        Token char_literal207=null;
        Token string_literal209=null;
        Token char_literal210=null;
        Token char_literal212=null;
        Token string_literal214=null;
        Token string_literal216=null;
        Token char_literal217=null;
        Token char_literal219=null;
        Token char_literal220=null;
        Token string_literal221=null;
        Token char_literal223=null;
        Token string_literal224=null;
        Token char_literal226=null;
        Token string_literal227=null;
        Token Identifier228=null;
        Token char_literal229=null;
        Token string_literal230=null;
        Token Identifier231=null;
        Token char_literal232=null;
        Token char_literal234=null;
        tmp2Parser.statementBlock_return statementBlock192 =null;

        tmp2Parser.expressionDecl_return expressionDecl195 =null;

        tmp2Parser.statementDecl_return statementDecl197 =null;

        tmp2Parser.statementDecl_return statementDecl199 =null;

        tmp2Parser.variableDeclaration_return variableDeclaration202 =null;

        tmp2Parser.expressionDecl_return expressionDecl204 =null;

        tmp2Parser.expressionList_return expressionList206 =null;

        tmp2Parser.statementDecl_return statementDecl208 =null;

        tmp2Parser.expressionDecl_return expressionDecl211 =null;

        tmp2Parser.statementDecl_return statementDecl213 =null;

        tmp2Parser.statementDecl_return statementDecl215 =null;

        tmp2Parser.expressionDecl_return expressionDecl218 =null;

        tmp2Parser.expressionDecl_return expressionDecl222 =null;

        tmp2Parser.expressionDecl_return expressionDecl225 =null;

        tmp2Parser.expressionDecl_return expressionDecl233 =null;

        tmp2Parser.commandDecl_return commandDecl235 =null;


        Object string_literal193_tree=null;
        Object char_literal194_tree=null;
        Object char_literal196_tree=null;
        Object string_literal198_tree=null;
        Object string_literal200_tree=null;
        Object char_literal201_tree=null;
        Object char_literal203_tree=null;
        Object char_literal205_tree=null;
        Object char_literal207_tree=null;
        Object string_literal209_tree=null;
        Object char_literal210_tree=null;
        Object char_literal212_tree=null;
        Object string_literal214_tree=null;
        Object string_literal216_tree=null;
        Object char_literal217_tree=null;
        Object char_literal219_tree=null;
        Object char_literal220_tree=null;
        Object string_literal221_tree=null;
        Object char_literal223_tree=null;
        Object string_literal224_tree=null;
        Object char_literal226_tree=null;
        Object string_literal227_tree=null;
        Object Identifier228_tree=null;
        Object char_literal229_tree=null;
        Object string_literal230_tree=null;
        Object Identifier231_tree=null;
        Object char_literal232_tree=null;
        Object char_literal234_tree=null;
        RewriteRuleSubtreeStream stream_statementBlock=new RewriteRuleSubtreeStream(adaptor,"rule statementBlock");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }

            // org\\aries\\tmp2.g:121:2: ( statementBlock -> ^( BLOCK statementBlock ) | 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )? | 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl | 'while' ^ '(' expressionDecl ')' statementDecl | 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';' | 'return' ^ ( expressionDecl )? ';' | 'throw' ^ expressionDecl ';' | 'break' ^ ( Identifier )? ';' | 'continue' ^ ( Identifier )? ';' | expressionDecl ';' | commandDecl )
            int alt22=11;
            switch ( input.LA(1) ) {
            case 136:
                {
                alt22=1;
                }
                break;
            case 97:
                {
                alt22=2;
                }
                break;
            case 95:
                {
                alt22=3;
                }
                break;
            case 135:
                {
                alt22=4;
                }
                break;
            case 88:
                {
                alt22=5;
                }
                break;
            case 120:
                {
                alt22=6;
                }
                break;
            case 131:
                {
                alt22=7;
                }
                break;
            case 80:
                {
                alt22=8;
                }
                break;
            case 87:
                {
                alt22=9;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 81:
            case 84:
            case 90:
            case 94:
            case 101:
            case 107:
            case 127:
            case 141:
                {
                alt22=10;
                }
                break;
            case 92:
            case 115:
            case 118:
            case 124:
                {
                alt22=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }

            switch (alt22) {
                case 1 :
                    // org\\aries\\tmp2.g:121:4: statementBlock
                    {
                    pushFollow(FOLLOW_statementBlock_in_statementDecl765);
                    statementBlock192=statementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statementBlock.add(statementBlock192.getTree());

                    // AST REWRITE
                    // elements: statementBlock
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 121:19: -> ^( BLOCK statementBlock )
                    {
                        // org\\aries\\tmp2.g:121:22: ^( BLOCK statementBlock )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(BLOCK, "BLOCK")
                        , root_1);

                        adaptor.addChild(root_1, stream_statementBlock.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:123:4: 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )?
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal193=(Token)match(input,97,FOLLOW_97_in_statementDecl780); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal193_tree = 
                    (Object)adaptor.create(string_literal193)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal193_tree, root_0);
                    }

                    char_literal194=(Token)match(input,50,FOLLOW_50_in_statementDecl783); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal194_tree = 
                    (Object)adaptor.create(char_literal194)
                    ;
                    adaptor.addChild(root_0, char_literal194_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl785);
                    expressionDecl195=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl195.getTree());

                    char_literal196=(Token)match(input,51,FOLLOW_51_in_statementDecl787); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal196_tree = 
                    (Object)adaptor.create(char_literal196)
                    ;
                    adaptor.addChild(root_0, char_literal196_tree);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl789);
                    statementDecl197=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl197.getTree());

                    // org\\aries\\tmp2.g:123:47: ( ( 'else' )=> 'else' statementDecl )?
                    int alt15=2;
                    switch ( input.LA(1) ) {
                        case 91:
                            {
                            int LA15_1 = input.LA(2);

                            if ( (synpred1_tmp2()) ) {
                                alt15=1;
                            }
                            }
                            break;
                    }

                    switch (alt15) {
                        case 1 :
                            // org\\aries\\tmp2.g:123:48: ( 'else' )=> 'else' statementDecl
                            {
                            string_literal198=(Token)match(input,91,FOLLOW_91_in_statementDecl798); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            string_literal198_tree = 
                            (Object)adaptor.create(string_literal198)
                            ;
                            adaptor.addChild(root_0, string_literal198_tree);
                            }

                            pushFollow(FOLLOW_statementDecl_in_statementDecl800);
                            statementDecl199=statementDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl199.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:124:4: 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal200=(Token)match(input,95,FOLLOW_95_in_statementDecl807); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal200_tree = 
                    (Object)adaptor.create(string_literal200)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal200_tree, root_0);
                    }

                    char_literal201=(Token)match(input,50,FOLLOW_50_in_statementDecl810); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal201_tree = 
                    (Object)adaptor.create(char_literal201)
                    ;
                    adaptor.addChild(root_0, char_literal201_tree);
                    }

                    // org\\aries\\tmp2.g:124:15: ( variableDeclaration )?
                    int alt16=2;
                    switch ( input.LA(1) ) {
                        case EXCEPTION:
                        case Identifier:
                        case MESSAGE:
                        case 78:
                        case 81:
                        case 84:
                        case 90:
                        case 94:
                        case 101:
                        case 107:
                        case 127:
                            {
                            alt16=1;
                            }
                            break;
                    }

                    switch (alt16) {
                        case 1 :
                            // org\\aries\\tmp2.g:124:16: variableDeclaration
                            {
                            pushFollow(FOLLOW_variableDeclaration_in_statementDecl813);
                            variableDeclaration202=variableDeclaration();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, variableDeclaration202.getTree());

                            }
                            break;

                    }


                    char_literal203=(Token)match(input,65,FOLLOW_65_in_statementDecl817); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal203_tree = 
                    (Object)adaptor.create(char_literal203)
                    ;
                    adaptor.addChild(root_0, char_literal203_tree);
                    }

                    // org\\aries\\tmp2.g:124:42: ( expressionDecl )?
                    int alt17=2;
                    switch ( input.LA(1) ) {
                        case CHARLITERAL:
                        case DoubleLiteral:
                        case EXCEPTION:
                        case FALSE:
                        case FloatLiteral:
                        case Identifier:
                        case IntegerLiteral:
                        case MESSAGE:
                        case NULL:
                        case STRINGLITERAL:
                        case TRUE:
                        case 43:
                        case 50:
                        case 54:
                        case 55:
                        case 58:
                        case 59:
                        case 78:
                        case 81:
                        case 84:
                        case 90:
                        case 94:
                        case 101:
                        case 107:
                        case 127:
                        case 141:
                            {
                            alt17=1;
                            }
                            break;
                    }

                    switch (alt17) {
                        case 1 :
                            // org\\aries\\tmp2.g:124:43: expressionDecl
                            {
                            pushFollow(FOLLOW_expressionDecl_in_statementDecl820);
                            expressionDecl204=expressionDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl204.getTree());

                            }
                            break;

                    }


                    char_literal205=(Token)match(input,65,FOLLOW_65_in_statementDecl824); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal205_tree = 
                    (Object)adaptor.create(char_literal205)
                    ;
                    adaptor.addChild(root_0, char_literal205_tree);
                    }

                    // org\\aries\\tmp2.g:124:64: ( expressionList )?
                    int alt18=2;
                    switch ( input.LA(1) ) {
                        case CHARLITERAL:
                        case DoubleLiteral:
                        case EXCEPTION:
                        case FALSE:
                        case FloatLiteral:
                        case Identifier:
                        case IntegerLiteral:
                        case MESSAGE:
                        case NULL:
                        case STRINGLITERAL:
                        case TRUE:
                        case 43:
                        case 50:
                        case 54:
                        case 55:
                        case 58:
                        case 59:
                        case 78:
                        case 81:
                        case 84:
                        case 90:
                        case 94:
                        case 101:
                        case 107:
                        case 127:
                        case 141:
                            {
                            alt18=1;
                            }
                            break;
                    }

                    switch (alt18) {
                        case 1 :
                            // org\\aries\\tmp2.g:124:65: expressionList
                            {
                            pushFollow(FOLLOW_expressionList_in_statementDecl827);
                            expressionList206=expressionList();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList206.getTree());

                            }
                            break;

                    }


                    char_literal207=(Token)match(input,51,FOLLOW_51_in_statementDecl831); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal207_tree = 
                    (Object)adaptor.create(char_literal207)
                    ;
                    adaptor.addChild(root_0, char_literal207_tree);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl833);
                    statementDecl208=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl208.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:125:4: 'while' ^ '(' expressionDecl ')' statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal209=(Token)match(input,135,FOLLOW_135_in_statementDecl838); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal209_tree = 
                    (Object)adaptor.create(string_literal209)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal209_tree, root_0);
                    }

                    char_literal210=(Token)match(input,50,FOLLOW_50_in_statementDecl841); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal210_tree = 
                    (Object)adaptor.create(char_literal210)
                    ;
                    adaptor.addChild(root_0, char_literal210_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl843);
                    expressionDecl211=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl211.getTree());

                    char_literal212=(Token)match(input,51,FOLLOW_51_in_statementDecl845); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal212_tree = 
                    (Object)adaptor.create(char_literal212)
                    ;
                    adaptor.addChild(root_0, char_literal212_tree);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl847);
                    statementDecl213=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl213.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:126:4: 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal214=(Token)match(input,88,FOLLOW_88_in_statementDecl852); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal214_tree = 
                    (Object)adaptor.create(string_literal214)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal214_tree, root_0);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl855);
                    statementDecl215=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl215.getTree());

                    string_literal216=(Token)match(input,135,FOLLOW_135_in_statementDecl857); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal216_tree = 
                    (Object)adaptor.create(string_literal216)
                    ;
                    adaptor.addChild(root_0, string_literal216_tree);
                    }

                    char_literal217=(Token)match(input,50,FOLLOW_50_in_statementDecl859); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal217_tree = 
                    (Object)adaptor.create(char_literal217)
                    ;
                    adaptor.addChild(root_0, char_literal217_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl861);
                    expressionDecl218=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl218.getTree());

                    char_literal219=(Token)match(input,51,FOLLOW_51_in_statementDecl863); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal219_tree = 
                    (Object)adaptor.create(char_literal219)
                    ;
                    adaptor.addChild(root_0, char_literal219_tree);
                    }

                    char_literal220=(Token)match(input,65,FOLLOW_65_in_statementDecl865); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal220_tree = 
                    (Object)adaptor.create(char_literal220)
                    ;
                    adaptor.addChild(root_0, char_literal220_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:127:4: 'return' ^ ( expressionDecl )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal221=(Token)match(input,120,FOLLOW_120_in_statementDecl870); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal221_tree = 
                    (Object)adaptor.create(string_literal221)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal221_tree, root_0);
                    }

                    // org\\aries\\tmp2.g:127:14: ( expressionDecl )?
                    int alt19=2;
                    switch ( input.LA(1) ) {
                        case CHARLITERAL:
                        case DoubleLiteral:
                        case EXCEPTION:
                        case FALSE:
                        case FloatLiteral:
                        case Identifier:
                        case IntegerLiteral:
                        case MESSAGE:
                        case NULL:
                        case STRINGLITERAL:
                        case TRUE:
                        case 43:
                        case 50:
                        case 54:
                        case 55:
                        case 58:
                        case 59:
                        case 78:
                        case 81:
                        case 84:
                        case 90:
                        case 94:
                        case 101:
                        case 107:
                        case 127:
                        case 141:
                            {
                            alt19=1;
                            }
                            break;
                    }

                    switch (alt19) {
                        case 1 :
                            // org\\aries\\tmp2.g:127:15: expressionDecl
                            {
                            pushFollow(FOLLOW_expressionDecl_in_statementDecl874);
                            expressionDecl222=expressionDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl222.getTree());

                            }
                            break;

                    }


                    char_literal223=(Token)match(input,65,FOLLOW_65_in_statementDecl878); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal223_tree = 
                    (Object)adaptor.create(char_literal223)
                    ;
                    adaptor.addChild(root_0, char_literal223_tree);
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp2.g:128:4: 'throw' ^ expressionDecl ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal224=(Token)match(input,131,FOLLOW_131_in_statementDecl883); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal224_tree = 
                    (Object)adaptor.create(string_literal224)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal224_tree, root_0);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl886);
                    expressionDecl225=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl225.getTree());

                    char_literal226=(Token)match(input,65,FOLLOW_65_in_statementDecl888); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal226_tree = 
                    (Object)adaptor.create(char_literal226)
                    ;
                    adaptor.addChild(root_0, char_literal226_tree);
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp2.g:129:4: 'break' ^ ( Identifier )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal227=(Token)match(input,80,FOLLOW_80_in_statementDecl893); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal227_tree = 
                    (Object)adaptor.create(string_literal227)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal227_tree, root_0);
                    }

                    // org\\aries\\tmp2.g:129:13: ( Identifier )?
                    int alt20=2;
                    switch ( input.LA(1) ) {
                        case Identifier:
                            {
                            alt20=1;
                            }
                            break;
                    }

                    switch (alt20) {
                        case 1 :
                            // org\\aries\\tmp2.g:129:14: Identifier
                            {
                            Identifier228=(Token)match(input,Identifier,FOLLOW_Identifier_in_statementDecl897); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            Identifier228_tree = 
                            (Object)adaptor.create(Identifier228)
                            ;
                            adaptor.addChild(root_0, Identifier228_tree);
                            }

                            }
                            break;

                    }


                    char_literal229=(Token)match(input,65,FOLLOW_65_in_statementDecl901); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal229_tree = 
                    (Object)adaptor.create(char_literal229)
                    ;
                    adaptor.addChild(root_0, char_literal229_tree);
                    }

                    }
                    break;
                case 9 :
                    // org\\aries\\tmp2.g:130:4: 'continue' ^ ( Identifier )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal230=(Token)match(input,87,FOLLOW_87_in_statementDecl906); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal230_tree = 
                    (Object)adaptor.create(string_literal230)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal230_tree, root_0);
                    }

                    // org\\aries\\tmp2.g:130:16: ( Identifier )?
                    int alt21=2;
                    switch ( input.LA(1) ) {
                        case Identifier:
                            {
                            alt21=1;
                            }
                            break;
                    }

                    switch (alt21) {
                        case 1 :
                            // org\\aries\\tmp2.g:130:17: Identifier
                            {
                            Identifier231=(Token)match(input,Identifier,FOLLOW_Identifier_in_statementDecl910); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            Identifier231_tree = 
                            (Object)adaptor.create(Identifier231)
                            ;
                            adaptor.addChild(root_0, Identifier231_tree);
                            }

                            }
                            break;

                    }


                    char_literal232=(Token)match(input,65,FOLLOW_65_in_statementDecl914); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal232_tree = 
                    (Object)adaptor.create(char_literal232)
                    ;
                    adaptor.addChild(root_0, char_literal232_tree);
                    }

                    }
                    break;
                case 10 :
                    // org\\aries\\tmp2.g:131:4: expressionDecl ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_expressionDecl_in_statementDecl919);
                    expressionDecl233=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl233.getTree());

                    char_literal234=(Token)match(input,65,FOLLOW_65_in_statementDecl921); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal234_tree = 
                    (Object)adaptor.create(char_literal234)
                    ;
                    adaptor.addChild(root_0, char_literal234_tree);
                    }

                    }
                    break;
                case 11 :
                    // org\\aries\\tmp2.g:132:4: commandDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_commandDecl_in_statementDecl926);
                    commandDecl235=commandDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, commandDecl235.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 12, statementDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "statementDecl"


    public static class statementBlock_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statementBlock"
    // org\\aries\\tmp2.g:135:1: statementBlock : '{' ( statementMember )* '}' ;
    public final tmp2Parser.statementBlock_return statementBlock() throws RecognitionException {
        tmp2Parser.statementBlock_return retval = new tmp2Parser.statementBlock_return();
        retval.start = input.LT(1);

        int statementBlock_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal236=null;
        Token char_literal238=null;
        tmp2Parser.statementMember_return statementMember237 =null;


        Object char_literal236_tree=null;
        Object char_literal238_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }

            // org\\aries\\tmp2.g:136:2: ( '{' ( statementMember )* '}' )
            // org\\aries\\tmp2.g:136:4: '{' ( statementMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal236=(Token)match(input,136,FOLLOW_136_in_statementBlock939); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal236_tree = 
            (Object)adaptor.create(char_literal236)
            ;
            adaptor.addChild(root_0, char_literal236_tree);
            }

            // org\\aries\\tmp2.g:136:8: ( statementMember )*
            loop23:
            do {
                int alt23=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 94:
                case 95:
                case 97:
                case 101:
                case 107:
                case 115:
                case 118:
                case 120:
                case 124:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt23=1;
                    }
                    break;

                }

                switch (alt23) {
            	case 1 :
            	    // org\\aries\\tmp2.g:136:9: statementMember
            	    {
            	    pushFollow(FOLLOW_statementMember_in_statementBlock942);
            	    statementMember237=statementMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementMember237.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            char_literal238=(Token)match(input,140,FOLLOW_140_in_statementBlock946); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal238_tree = 
            (Object)adaptor.create(char_literal238)
            ;
            adaptor.addChild(root_0, char_literal238_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 13, statementBlock_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "statementBlock"


    public static class statementMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statementMember"
    // org\\aries\\tmp2.g:139:1: statementMember : statementDecl ;
    public final tmp2Parser.statementMember_return statementMember() throws RecognitionException {
        tmp2Parser.statementMember_return retval = new tmp2Parser.statementMember_return();
        retval.start = input.LT(1);

        int statementMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.statementDecl_return statementDecl239 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }

            // org\\aries\\tmp2.g:140:2: ( statementDecl )
            // org\\aries\\tmp2.g:140:4: statementDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_statementDecl_in_statementMember958);
            statementDecl239=statementDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl239.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 14, statementMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "statementMember"


    public static class variableDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "variableDeclaration"
    // org\\aries\\tmp2.g:143:1: variableDeclaration : type Identifier ( '[' ']' )* '=' variableInitializer -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer ) ;
    public final tmp2Parser.variableDeclaration_return variableDeclaration() throws RecognitionException {
        tmp2Parser.variableDeclaration_return retval = new tmp2Parser.variableDeclaration_return();
        retval.start = input.LT(1);

        int variableDeclaration_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier241=null;
        Token char_literal242=null;
        Token char_literal243=null;
        Token char_literal244=null;
        tmp2Parser.type_return type240 =null;

        tmp2Parser.variableInitializer_return variableInitializer245 =null;


        Object Identifier241_tree=null;
        Object char_literal242_tree=null;
        Object char_literal243_tree=null;
        Object char_literal244_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_variableInitializer=new RewriteRuleSubtreeStream(adaptor,"rule variableInitializer");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }

            // org\\aries\\tmp2.g:144:2: ( type Identifier ( '[' ']' )* '=' variableInitializer -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer ) )
            // org\\aries\\tmp2.g:144:4: type Identifier ( '[' ']' )* '=' variableInitializer
            {
            pushFollow(FOLLOW_type_in_variableDeclaration970);
            type240=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type240.getTree());

            Identifier241=(Token)match(input,Identifier,FOLLOW_Identifier_in_variableDeclaration972); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Identifier.add(Identifier241);


            // org\\aries\\tmp2.g:144:20: ( '[' ']' )*
            loop24:
            do {
                int alt24=2;
                switch ( input.LA(1) ) {
                case 71:
                    {
                    alt24=1;
                    }
                    break;

                }

                switch (alt24) {
            	case 1 :
            	    // org\\aries\\tmp2.g:144:21: '[' ']'
            	    {
            	    char_literal242=(Token)match(input,71,FOLLOW_71_in_variableDeclaration975); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_71.add(char_literal242);


            	    char_literal243=(Token)match(input,72,FOLLOW_72_in_variableDeclaration977); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_72.add(char_literal243);


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            char_literal244=(Token)match(input,67,FOLLOW_67_in_variableDeclaration981); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(char_literal244);


            pushFollow(FOLLOW_variableInitializer_in_variableDeclaration983);
            variableInitializer245=variableInitializer();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_variableInitializer.add(variableInitializer245.getTree());

            // AST REWRITE
            // elements: 72, 71, Identifier, type, variableInitializer, 67
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 144:55: -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer )
            {
                // org\\aries\\tmp2.g:144:58: ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EXPR, "EXPR")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_Identifier.nextNode()
                );

                // org\\aries\\tmp2.g:144:81: ( '[' ']' )*
                while ( stream_72.hasNext()||stream_71.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_71.nextNode()
                    );

                    adaptor.addChild(root_1, 
                    stream_72.nextNode()
                    );

                }
                stream_72.reset();
                stream_71.reset();

                adaptor.addChild(root_1, 
                stream_67.nextNode()
                );

                adaptor.addChild(root_1, stream_variableInitializer.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 15, variableDeclaration_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "variableDeclaration"


    public static class variableInitializer_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "variableInitializer"
    // org\\aries\\tmp2.g:147:1: variableInitializer : ( arrayInitializer | expressionDecl );
    public final tmp2Parser.variableInitializer_return variableInitializer() throws RecognitionException {
        tmp2Parser.variableInitializer_return retval = new tmp2Parser.variableInitializer_return();
        retval.start = input.LT(1);

        int variableInitializer_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.arrayInitializer_return arrayInitializer246 =null;

        tmp2Parser.expressionDecl_return expressionDecl247 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }

            // org\\aries\\tmp2.g:148:2: ( arrayInitializer | expressionDecl )
            int alt25=2;
            switch ( input.LA(1) ) {
            case 136:
                {
                alt25=1;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 81:
            case 84:
            case 90:
            case 94:
            case 101:
            case 107:
            case 127:
            case 141:
                {
                alt25=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;

            }

            switch (alt25) {
                case 1 :
                    // org\\aries\\tmp2.g:148:4: arrayInitializer
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_arrayInitializer_in_variableInitializer1016);
                    arrayInitializer246=arrayInitializer();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayInitializer246.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:149:4: expressionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_expressionDecl_in_variableInitializer1021);
                    expressionDecl247=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl247.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 16, variableInitializer_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "variableInitializer"


    public static class arrayInitializer_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrayInitializer"
    // org\\aries\\tmp2.g:152:1: arrayInitializer : '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}' ;
    public final tmp2Parser.arrayInitializer_return arrayInitializer() throws RecognitionException {
        tmp2Parser.arrayInitializer_return retval = new tmp2Parser.arrayInitializer_return();
        retval.start = input.LT(1);

        int arrayInitializer_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal248=null;
        Token char_literal250=null;
        Token char_literal252=null;
        Token char_literal253=null;
        tmp2Parser.variableInitializer_return variableInitializer249 =null;

        tmp2Parser.variableInitializer_return variableInitializer251 =null;


        Object char_literal248_tree=null;
        Object char_literal250_tree=null;
        Object char_literal252_tree=null;
        Object char_literal253_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }

            // org\\aries\\tmp2.g:153:2: ( '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}' )
            // org\\aries\\tmp2.g:153:4: '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal248=(Token)match(input,136,FOLLOW_136_in_arrayInitializer1033); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal248_tree = 
            (Object)adaptor.create(char_literal248)
            ;
            adaptor.addChild(root_0, char_literal248_tree);
            }

            // org\\aries\\tmp2.g:153:8: ( variableInitializer ( ',' variableInitializer )* )?
            int alt27=2;
            switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 81:
                case 84:
                case 90:
                case 94:
                case 101:
                case 107:
                case 127:
                case 136:
                case 141:
                    {
                    alt27=1;
                    }
                    break;
            }

            switch (alt27) {
                case 1 :
                    // org\\aries\\tmp2.g:153:9: variableInitializer ( ',' variableInitializer )*
                    {
                    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1036);
                    variableInitializer249=variableInitializer();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableInitializer249.getTree());

                    // org\\aries\\tmp2.g:153:29: ( ',' variableInitializer )*
                    loop26:
                    do {
                        int alt26=2;
                        switch ( input.LA(1) ) {
                        case 57:
                            {
                            switch ( input.LA(2) ) {
                            case CHARLITERAL:
                            case DoubleLiteral:
                            case EXCEPTION:
                            case FALSE:
                            case FloatLiteral:
                            case Identifier:
                            case IntegerLiteral:
                            case MESSAGE:
                            case NULL:
                            case STRINGLITERAL:
                            case TRUE:
                            case 43:
                            case 50:
                            case 54:
                            case 55:
                            case 58:
                            case 59:
                            case 78:
                            case 81:
                            case 84:
                            case 90:
                            case 94:
                            case 101:
                            case 107:
                            case 127:
                            case 136:
                            case 141:
                                {
                                alt26=1;
                                }
                                break;

                            }

                            }
                            break;

                        }

                        switch (alt26) {
                    	case 1 :
                    	    // org\\aries\\tmp2.g:153:30: ',' variableInitializer
                    	    {
                    	    char_literal250=(Token)match(input,57,FOLLOW_57_in_arrayInitializer1039); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal250_tree = 
                    	    (Object)adaptor.create(char_literal250)
                    	    ;
                    	    adaptor.addChild(root_0, char_literal250_tree);
                    	    }

                    	    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1041);
                    	    variableInitializer251=variableInitializer();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableInitializer251.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }


            // org\\aries\\tmp2.g:153:59: ( ',' )?
            int alt28=2;
            switch ( input.LA(1) ) {
                case 57:
                    {
                    alt28=1;
                    }
                    break;
            }

            switch (alt28) {
                case 1 :
                    // org\\aries\\tmp2.g:153:60: ','
                    {
                    char_literal252=(Token)match(input,57,FOLLOW_57_in_arrayInitializer1049); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal252_tree = 
                    (Object)adaptor.create(char_literal252)
                    ;
                    adaptor.addChild(root_0, char_literal252_tree);
                    }

                    }
                    break;

            }


            char_literal253=(Token)match(input,140,FOLLOW_140_in_arrayInitializer1053); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal253_tree = 
            (Object)adaptor.create(char_literal253)
            ;
            adaptor.addChild(root_0, char_literal253_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 17, arrayInitializer_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "arrayInitializer"


    public static class expressionDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expressionDecl"
    // org\\aries\\tmp2.g:156:1: expressionDecl : conditionalExpression ( assignmentOperator expressionDecl )? -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? ) ;
    public final tmp2Parser.expressionDecl_return expressionDecl() throws RecognitionException {
        tmp2Parser.expressionDecl_return retval = new tmp2Parser.expressionDecl_return();
        retval.start = input.LT(1);

        int expressionDecl_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.conditionalExpression_return conditionalExpression254 =null;

        tmp2Parser.assignmentOperator_return assignmentOperator255 =null;

        tmp2Parser.expressionDecl_return expressionDecl256 =null;


        RewriteRuleSubtreeStream stream_assignmentOperator=new RewriteRuleSubtreeStream(adaptor,"rule assignmentOperator");
        RewriteRuleSubtreeStream stream_expressionDecl=new RewriteRuleSubtreeStream(adaptor,"rule expressionDecl");
        RewriteRuleSubtreeStream stream_conditionalExpression=new RewriteRuleSubtreeStream(adaptor,"rule conditionalExpression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }

            // org\\aries\\tmp2.g:157:2: ( conditionalExpression ( assignmentOperator expressionDecl )? -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? ) )
            // org\\aries\\tmp2.g:157:4: conditionalExpression ( assignmentOperator expressionDecl )?
            {
            pushFollow(FOLLOW_conditionalExpression_in_expressionDecl1065);
            conditionalExpression254=conditionalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_conditionalExpression.add(conditionalExpression254.getTree());

            // org\\aries\\tmp2.g:157:26: ( assignmentOperator expressionDecl )?
            int alt29=2;
            switch ( input.LA(1) ) {
                case 46:
                case 49:
                case 53:
                case 56:
                case 60:
                case 63:
                case 66:
                case 67:
                case 69:
                case 74:
                case 138:
                    {
                    alt29=1;
                    }
                    break;
            }

            switch (alt29) {
                case 1 :
                    // org\\aries\\tmp2.g:157:27: assignmentOperator expressionDecl
                    {
                    pushFollow(FOLLOW_assignmentOperator_in_expressionDecl1068);
                    assignmentOperator255=assignmentOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_assignmentOperator.add(assignmentOperator255.getTree());

                    pushFollow(FOLLOW_expressionDecl_in_expressionDecl1070);
                    expressionDecl256=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl256.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: conditionalExpression, expressionDecl, assignmentOperator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 157:63: -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? )
            {
                // org\\aries\\tmp2.g:157:66: ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EXPR, "EXPR")
                , root_1);

                adaptor.addChild(root_1, stream_conditionalExpression.nextTree());

                // org\\aries\\tmp2.g:157:95: ( assignmentOperator expressionDecl )?
                if ( stream_expressionDecl.hasNext()||stream_assignmentOperator.hasNext() ) {
                    adaptor.addChild(root_1, stream_assignmentOperator.nextTree());

                    adaptor.addChild(root_1, stream_expressionDecl.nextTree());

                }
                stream_expressionDecl.reset();
                stream_assignmentOperator.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 18, expressionDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "expressionDecl"


    public static class expressionList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expressionList"
    // org\\aries\\tmp2.g:160:1: expressionList : expressionDecl ( ',' expressionDecl )* ;
    public final tmp2Parser.expressionList_return expressionList() throws RecognitionException {
        tmp2Parser.expressionList_return retval = new tmp2Parser.expressionList_return();
        retval.start = input.LT(1);

        int expressionList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal258=null;
        tmp2Parser.expressionDecl_return expressionDecl257 =null;

        tmp2Parser.expressionDecl_return expressionDecl259 =null;


        Object char_literal258_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }

            // org\\aries\\tmp2.g:161:2: ( expressionDecl ( ',' expressionDecl )* )
            // org\\aries\\tmp2.g:161:4: expressionDecl ( ',' expressionDecl )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_expressionDecl_in_expressionList1099);
            expressionDecl257=expressionDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl257.getTree());

            // org\\aries\\tmp2.g:161:19: ( ',' expressionDecl )*
            loop30:
            do {
                int alt30=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt30=1;
                    }
                    break;

                }

                switch (alt30) {
            	case 1 :
            	    // org\\aries\\tmp2.g:161:20: ',' expressionDecl
            	    {
            	    char_literal258=(Token)match(input,57,FOLLOW_57_in_expressionList1102); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal258_tree = 
            	    (Object)adaptor.create(char_literal258)
            	    ;
            	    adaptor.addChild(root_0, char_literal258_tree);
            	    }

            	    pushFollow(FOLLOW_expressionDecl_in_expressionList1104);
            	    expressionDecl259=expressionDecl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl259.getTree());

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 19, expressionList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "expressionList"


    public static class assignmentOperator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assignmentOperator"
    // org\\aries\\tmp2.g:164:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '>' '=' | '>' '>' '=' );
    public final tmp2Parser.assignmentOperator_return assignmentOperator() throws RecognitionException {
        tmp2Parser.assignmentOperator_return retval = new tmp2Parser.assignmentOperator_return();
        retval.start = input.LT(1);

        int assignmentOperator_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal260=null;
        Token string_literal261=null;
        Token string_literal262=null;
        Token string_literal263=null;
        Token string_literal264=null;
        Token string_literal265=null;
        Token string_literal266=null;
        Token string_literal267=null;
        Token string_literal268=null;
        Token char_literal269=null;
        Token char_literal270=null;
        Token char_literal271=null;
        Token char_literal272=null;
        Token char_literal273=null;
        Token char_literal274=null;
        Token char_literal275=null;
        Token char_literal276=null;
        Token char_literal277=null;
        Token char_literal278=null;

        Object char_literal260_tree=null;
        Object string_literal261_tree=null;
        Object string_literal262_tree=null;
        Object string_literal263_tree=null;
        Object string_literal264_tree=null;
        Object string_literal265_tree=null;
        Object string_literal266_tree=null;
        Object string_literal267_tree=null;
        Object string_literal268_tree=null;
        Object char_literal269_tree=null;
        Object char_literal270_tree=null;
        Object char_literal271_tree=null;
        Object char_literal272_tree=null;
        Object char_literal273_tree=null;
        Object char_literal274_tree=null;
        Object char_literal275_tree=null;
        Object char_literal276_tree=null;
        Object char_literal277_tree=null;
        Object char_literal278_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }

            // org\\aries\\tmp2.g:165:2: ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '>' '=' | '>' '>' '=' )
            int alt31=12;
            switch ( input.LA(1) ) {
            case 67:
                {
                alt31=1;
                }
                break;
            case 56:
                {
                alt31=2;
                }
                break;
            case 60:
                {
                alt31=3;
                }
                break;
            case 53:
                {
                alt31=4;
                }
                break;
            case 63:
                {
                alt31=5;
                }
                break;
            case 49:
                {
                alt31=6;
                }
                break;
            case 138:
                {
                alt31=7;
                }
                break;
            case 74:
                {
                alt31=8;
                }
                break;
            case 46:
                {
                alt31=9;
                }
                break;
            case 66:
                {
                alt31=10;
                }
                break;
            case 69:
                {
                switch ( input.LA(2) ) {
                case 69:
                    {
                    switch ( input.LA(3) ) {
                    case 69:
                        {
                        alt31=11;
                        }
                        break;
                    case 67:
                        {
                        alt31=12;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 12, input);

                        throw nvae;

                    }

                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 11, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }

            switch (alt31) {
                case 1 :
                    // org\\aries\\tmp2.g:165:4: '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal260=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1118); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal260_tree = 
                    (Object)adaptor.create(char_literal260)
                    ;
                    adaptor.addChild(root_0, char_literal260_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:166:4: '+='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal261=(Token)match(input,56,FOLLOW_56_in_assignmentOperator1123); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal261_tree = 
                    (Object)adaptor.create(string_literal261)
                    ;
                    adaptor.addChild(root_0, string_literal261_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:167:4: '-='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal262=(Token)match(input,60,FOLLOW_60_in_assignmentOperator1128); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal262_tree = 
                    (Object)adaptor.create(string_literal262)
                    ;
                    adaptor.addChild(root_0, string_literal262_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:168:4: '*='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal263=(Token)match(input,53,FOLLOW_53_in_assignmentOperator1133); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal263_tree = 
                    (Object)adaptor.create(string_literal263)
                    ;
                    adaptor.addChild(root_0, string_literal263_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:169:4: '/='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal264=(Token)match(input,63,FOLLOW_63_in_assignmentOperator1138); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal264_tree = 
                    (Object)adaptor.create(string_literal264)
                    ;
                    adaptor.addChild(root_0, string_literal264_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:170:4: '&='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal265=(Token)match(input,49,FOLLOW_49_in_assignmentOperator1143); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal265_tree = 
                    (Object)adaptor.create(string_literal265)
                    ;
                    adaptor.addChild(root_0, string_literal265_tree);
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp2.g:171:4: '|='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal266=(Token)match(input,138,FOLLOW_138_in_assignmentOperator1148); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal266_tree = 
                    (Object)adaptor.create(string_literal266)
                    ;
                    adaptor.addChild(root_0, string_literal266_tree);
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp2.g:172:4: '^='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal267=(Token)match(input,74,FOLLOW_74_in_assignmentOperator1153); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal267_tree = 
                    (Object)adaptor.create(string_literal267)
                    ;
                    adaptor.addChild(root_0, string_literal267_tree);
                    }

                    }
                    break;
                case 9 :
                    // org\\aries\\tmp2.g:173:4: '%='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal268=(Token)match(input,46,FOLLOW_46_in_assignmentOperator1158); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal268_tree = 
                    (Object)adaptor.create(string_literal268)
                    ;
                    adaptor.addChild(root_0, string_literal268_tree);
                    }

                    }
                    break;
                case 10 :
                    // org\\aries\\tmp2.g:174:4: '<' '<' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal269=(Token)match(input,66,FOLLOW_66_in_assignmentOperator1163); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal269_tree = 
                    (Object)adaptor.create(char_literal269)
                    ;
                    adaptor.addChild(root_0, char_literal269_tree);
                    }

                    char_literal270=(Token)match(input,66,FOLLOW_66_in_assignmentOperator1165); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal270_tree = 
                    (Object)adaptor.create(char_literal270)
                    ;
                    adaptor.addChild(root_0, char_literal270_tree);
                    }

                    char_literal271=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1167); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal271_tree = 
                    (Object)adaptor.create(char_literal271)
                    ;
                    adaptor.addChild(root_0, char_literal271_tree);
                    }

                    }
                    break;
                case 11 :
                    // org\\aries\\tmp2.g:175:4: '>' '>' '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal272=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1172); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal272_tree = 
                    (Object)adaptor.create(char_literal272)
                    ;
                    adaptor.addChild(root_0, char_literal272_tree);
                    }

                    char_literal273=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1174); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal273_tree = 
                    (Object)adaptor.create(char_literal273)
                    ;
                    adaptor.addChild(root_0, char_literal273_tree);
                    }

                    char_literal274=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1176); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal274_tree = 
                    (Object)adaptor.create(char_literal274)
                    ;
                    adaptor.addChild(root_0, char_literal274_tree);
                    }

                    char_literal275=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1178); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal275_tree = 
                    (Object)adaptor.create(char_literal275)
                    ;
                    adaptor.addChild(root_0, char_literal275_tree);
                    }

                    }
                    break;
                case 12 :
                    // org\\aries\\tmp2.g:176:4: '>' '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal276=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1183); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal276_tree = 
                    (Object)adaptor.create(char_literal276)
                    ;
                    adaptor.addChild(root_0, char_literal276_tree);
                    }

                    char_literal277=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1185); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal277_tree = 
                    (Object)adaptor.create(char_literal277)
                    ;
                    adaptor.addChild(root_0, char_literal277_tree);
                    }

                    char_literal278=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1187); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal278_tree = 
                    (Object)adaptor.create(char_literal278)
                    ;
                    adaptor.addChild(root_0, char_literal278_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 20, assignmentOperator_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "assignmentOperator"


    public static class conditionalExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "conditionalExpression"
    // org\\aries\\tmp2.g:179:1: conditionalExpression : conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )? ;
    public final tmp2Parser.conditionalExpression_return conditionalExpression() throws RecognitionException {
        tmp2Parser.conditionalExpression_return retval = new tmp2Parser.conditionalExpression_return();
        retval.start = input.LT(1);

        int conditionalExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal280=null;
        Token char_literal282=null;
        tmp2Parser.conditionalOrExpression_return conditionalOrExpression279 =null;

        tmp2Parser.expressionDecl_return expressionDecl281 =null;

        tmp2Parser.conditionalExpression_return conditionalExpression283 =null;


        Object char_literal280_tree=null;
        Object char_literal282_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }

            // org\\aries\\tmp2.g:180:2: ( conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )? )
            // org\\aries\\tmp2.g:180:4: conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalExpression1198);
            conditionalOrExpression279=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalOrExpression279.getTree());

            // org\\aries\\tmp2.g:180:28: ( '?' expressionDecl ':' conditionalExpression )?
            int alt32=2;
            switch ( input.LA(1) ) {
                case 70:
                    {
                    alt32=1;
                    }
                    break;
            }

            switch (alt32) {
                case 1 :
                    // org\\aries\\tmp2.g:180:29: '?' expressionDecl ':' conditionalExpression
                    {
                    char_literal280=(Token)match(input,70,FOLLOW_70_in_conditionalExpression1201); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal280_tree = 
                    (Object)adaptor.create(char_literal280)
                    ;
                    adaptor.addChild(root_0, char_literal280_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_conditionalExpression1203);
                    expressionDecl281=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl281.getTree());

                    char_literal282=(Token)match(input,64,FOLLOW_64_in_conditionalExpression1205); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal282_tree = 
                    (Object)adaptor.create(char_literal282)
                    ;
                    adaptor.addChild(root_0, char_literal282_tree);
                    }

                    pushFollow(FOLLOW_conditionalExpression_in_conditionalExpression1207);
                    conditionalExpression283=conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalExpression283.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 21, conditionalExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "conditionalExpression"


    public static class conditionalOrExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "conditionalOrExpression"
    // org\\aries\\tmp2.g:183:1: conditionalOrExpression : conditionalAndExpression ( '||' conditionalAndExpression )* ;
    public final tmp2Parser.conditionalOrExpression_return conditionalOrExpression() throws RecognitionException {
        tmp2Parser.conditionalOrExpression_return retval = new tmp2Parser.conditionalOrExpression_return();
        retval.start = input.LT(1);

        int conditionalOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal285=null;
        tmp2Parser.conditionalAndExpression_return conditionalAndExpression284 =null;

        tmp2Parser.conditionalAndExpression_return conditionalAndExpression286 =null;


        Object string_literal285_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }

            // org\\aries\\tmp2.g:184:2: ( conditionalAndExpression ( '||' conditionalAndExpression )* )
            // org\\aries\\tmp2.g:184:4: conditionalAndExpression ( '||' conditionalAndExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1221);
            conditionalAndExpression284=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression284.getTree());

            // org\\aries\\tmp2.g:184:29: ( '||' conditionalAndExpression )*
            loop33:
            do {
                int alt33=2;
                switch ( input.LA(1) ) {
                case 139:
                    {
                    alt33=1;
                    }
                    break;

                }

                switch (alt33) {
            	case 1 :
            	    // org\\aries\\tmp2.g:184:30: '||' conditionalAndExpression
            	    {
            	    string_literal285=(Token)match(input,139,FOLLOW_139_in_conditionalOrExpression1224); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal285_tree = 
            	    (Object)adaptor.create(string_literal285)
            	    ;
            	    adaptor.addChild(root_0, string_literal285_tree);
            	    }

            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1226);
            	    conditionalAndExpression286=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression286.getTree());

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 22, conditionalOrExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "conditionalOrExpression"


    public static class conditionalAndExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "conditionalAndExpression"
    // org\\aries\\tmp2.g:187:1: conditionalAndExpression : inclusiveOrExpression ( '&&' inclusiveOrExpression )* ;
    public final tmp2Parser.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        tmp2Parser.conditionalAndExpression_return retval = new tmp2Parser.conditionalAndExpression_return();
        retval.start = input.LT(1);

        int conditionalAndExpression_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal288=null;
        tmp2Parser.inclusiveOrExpression_return inclusiveOrExpression287 =null;

        tmp2Parser.inclusiveOrExpression_return inclusiveOrExpression289 =null;


        Object string_literal288_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }

            // org\\aries\\tmp2.g:188:2: ( inclusiveOrExpression ( '&&' inclusiveOrExpression )* )
            // org\\aries\\tmp2.g:188:4: inclusiveOrExpression ( '&&' inclusiveOrExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1240);
            inclusiveOrExpression287=inclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, inclusiveOrExpression287.getTree());

            // org\\aries\\tmp2.g:188:26: ( '&&' inclusiveOrExpression )*
            loop34:
            do {
                int alt34=2;
                switch ( input.LA(1) ) {
                case 47:
                    {
                    alt34=1;
                    }
                    break;

                }

                switch (alt34) {
            	case 1 :
            	    // org\\aries\\tmp2.g:188:27: '&&' inclusiveOrExpression
            	    {
            	    string_literal288=(Token)match(input,47,FOLLOW_47_in_conditionalAndExpression1243); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal288_tree = 
            	    (Object)adaptor.create(string_literal288)
            	    ;
            	    adaptor.addChild(root_0, string_literal288_tree);
            	    }

            	    pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1245);
            	    inclusiveOrExpression289=inclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, inclusiveOrExpression289.getTree());

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 23, conditionalAndExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "conditionalAndExpression"


    public static class inclusiveOrExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "inclusiveOrExpression"
    // org\\aries\\tmp2.g:191:1: inclusiveOrExpression : exclusiveOrExpression ( '|' exclusiveOrExpression )* ;
    public final tmp2Parser.inclusiveOrExpression_return inclusiveOrExpression() throws RecognitionException {
        tmp2Parser.inclusiveOrExpression_return retval = new tmp2Parser.inclusiveOrExpression_return();
        retval.start = input.LT(1);

        int inclusiveOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal291=null;
        tmp2Parser.exclusiveOrExpression_return exclusiveOrExpression290 =null;

        tmp2Parser.exclusiveOrExpression_return exclusiveOrExpression292 =null;


        Object char_literal291_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }

            // org\\aries\\tmp2.g:192:2: ( exclusiveOrExpression ( '|' exclusiveOrExpression )* )
            // org\\aries\\tmp2.g:192:4: exclusiveOrExpression ( '|' exclusiveOrExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1259);
            exclusiveOrExpression290=exclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exclusiveOrExpression290.getTree());

            // org\\aries\\tmp2.g:192:26: ( '|' exclusiveOrExpression )*
            loop35:
            do {
                int alt35=2;
                switch ( input.LA(1) ) {
                case 137:
                    {
                    alt35=1;
                    }
                    break;

                }

                switch (alt35) {
            	case 1 :
            	    // org\\aries\\tmp2.g:192:27: '|' exclusiveOrExpression
            	    {
            	    char_literal291=(Token)match(input,137,FOLLOW_137_in_inclusiveOrExpression1262); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal291_tree = 
            	    (Object)adaptor.create(char_literal291)
            	    ;
            	    adaptor.addChild(root_0, char_literal291_tree);
            	    }

            	    pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1264);
            	    exclusiveOrExpression292=exclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exclusiveOrExpression292.getTree());

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 24, inclusiveOrExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "inclusiveOrExpression"


    public static class exclusiveOrExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exclusiveOrExpression"
    // org\\aries\\tmp2.g:195:1: exclusiveOrExpression : andExpression ( '^' andExpression )* ;
    public final tmp2Parser.exclusiveOrExpression_return exclusiveOrExpression() throws RecognitionException {
        tmp2Parser.exclusiveOrExpression_return retval = new tmp2Parser.exclusiveOrExpression_return();
        retval.start = input.LT(1);

        int exclusiveOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal294=null;
        tmp2Parser.andExpression_return andExpression293 =null;

        tmp2Parser.andExpression_return andExpression295 =null;


        Object char_literal294_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }

            // org\\aries\\tmp2.g:196:2: ( andExpression ( '^' andExpression )* )
            // org\\aries\\tmp2.g:196:4: andExpression ( '^' andExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression1278);
            andExpression293=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpression293.getTree());

            // org\\aries\\tmp2.g:196:18: ( '^' andExpression )*
            loop36:
            do {
                int alt36=2;
                switch ( input.LA(1) ) {
                case 73:
                    {
                    alt36=1;
                    }
                    break;

                }

                switch (alt36) {
            	case 1 :
            	    // org\\aries\\tmp2.g:196:19: '^' andExpression
            	    {
            	    char_literal294=(Token)match(input,73,FOLLOW_73_in_exclusiveOrExpression1281); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal294_tree = 
            	    (Object)adaptor.create(char_literal294)
            	    ;
            	    adaptor.addChild(root_0, char_literal294_tree);
            	    }

            	    pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression1283);
            	    andExpression295=andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpression295.getTree());

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 25, exclusiveOrExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exclusiveOrExpression"


    public static class andExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "andExpression"
    // org\\aries\\tmp2.g:199:1: andExpression : equalityExpression ( '&' equalityExpression )* ;
    public final tmp2Parser.andExpression_return andExpression() throws RecognitionException {
        tmp2Parser.andExpression_return retval = new tmp2Parser.andExpression_return();
        retval.start = input.LT(1);

        int andExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal297=null;
        tmp2Parser.equalityExpression_return equalityExpression296 =null;

        tmp2Parser.equalityExpression_return equalityExpression298 =null;


        Object char_literal297_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }

            // org\\aries\\tmp2.g:200:2: ( equalityExpression ( '&' equalityExpression )* )
            // org\\aries\\tmp2.g:200:4: equalityExpression ( '&' equalityExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_equalityExpression_in_andExpression1297);
            equalityExpression296=equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression296.getTree());

            // org\\aries\\tmp2.g:200:23: ( '&' equalityExpression )*
            loop37:
            do {
                int alt37=2;
                switch ( input.LA(1) ) {
                case 48:
                    {
                    alt37=1;
                    }
                    break;

                }

                switch (alt37) {
            	case 1 :
            	    // org\\aries\\tmp2.g:200:24: '&' equalityExpression
            	    {
            	    char_literal297=(Token)match(input,48,FOLLOW_48_in_andExpression1300); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal297_tree = 
            	    (Object)adaptor.create(char_literal297)
            	    ;
            	    adaptor.addChild(root_0, char_literal297_tree);
            	    }

            	    pushFollow(FOLLOW_equalityExpression_in_andExpression1302);
            	    equalityExpression298=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression298.getTree());

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 26, andExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "andExpression"


    public static class equalityExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "equalityExpression"
    // org\\aries\\tmp2.g:203:1: equalityExpression : relationalExpression ( ( '==' | '!=' ) relationalExpression )* ;
    public final tmp2Parser.equalityExpression_return equalityExpression() throws RecognitionException {
        tmp2Parser.equalityExpression_return retval = new tmp2Parser.equalityExpression_return();
        retval.start = input.LT(1);

        int equalityExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set300=null;
        tmp2Parser.relationalExpression_return relationalExpression299 =null;

        tmp2Parser.relationalExpression_return relationalExpression301 =null;


        Object set300_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }

            // org\\aries\\tmp2.g:204:2: ( relationalExpression ( ( '==' | '!=' ) relationalExpression )* )
            // org\\aries\\tmp2.g:204:4: relationalExpression ( ( '==' | '!=' ) relationalExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpression_in_equalityExpression1316);
            relationalExpression299=relationalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression299.getTree());

            // org\\aries\\tmp2.g:204:25: ( ( '==' | '!=' ) relationalExpression )*
            loop38:
            do {
                int alt38=2;
                switch ( input.LA(1) ) {
                case 44:
                case 68:
                    {
                    alt38=1;
                    }
                    break;

                }

                switch (alt38) {
            	case 1 :
            	    // org\\aries\\tmp2.g:204:27: ( '==' | '!=' ) relationalExpression
            	    {
            	    set300=(Token)input.LT(1);

            	    if ( input.LA(1)==44||input.LA(1)==68 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set300)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression1328);
            	    relationalExpression301=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression301.getTree());

            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 27, equalityExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "equalityExpression"


    public static class relationalExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalExpression"
    // org\\aries\\tmp2.g:207:1: relationalExpression : shiftExpression ( relationalOp shiftExpression )* ;
    public final tmp2Parser.relationalExpression_return relationalExpression() throws RecognitionException {
        tmp2Parser.relationalExpression_return retval = new tmp2Parser.relationalExpression_return();
        retval.start = input.LT(1);

        int relationalExpression_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.shiftExpression_return shiftExpression302 =null;

        tmp2Parser.relationalOp_return relationalOp303 =null;

        tmp2Parser.shiftExpression_return shiftExpression304 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }

            // org\\aries\\tmp2.g:208:2: ( shiftExpression ( relationalOp shiftExpression )* )
            // org\\aries\\tmp2.g:208:4: shiftExpression ( relationalOp shiftExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_shiftExpression_in_relationalExpression1342);
            shiftExpression302=shiftExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftExpression302.getTree());

            // org\\aries\\tmp2.g:208:20: ( relationalOp shiftExpression )*
            loop39:
            do {
                int alt39=2;
                switch ( input.LA(1) ) {
                case 66:
                    {
                    switch ( input.LA(2) ) {
                    case CHARLITERAL:
                    case DoubleLiteral:
                    case EXCEPTION:
                    case FALSE:
                    case FloatLiteral:
                    case Identifier:
                    case IntegerLiteral:
                    case MESSAGE:
                    case NULL:
                    case STRINGLITERAL:
                    case TRUE:
                    case 43:
                    case 50:
                    case 54:
                    case 55:
                    case 58:
                    case 59:
                    case 67:
                    case 78:
                    case 81:
                    case 84:
                    case 90:
                    case 94:
                    case 101:
                    case 107:
                    case 127:
                    case 141:
                        {
                        alt39=1;
                        }
                        break;

                    }

                    }
                    break;
                case 69:
                    {
                    switch ( input.LA(2) ) {
                    case CHARLITERAL:
                    case DoubleLiteral:
                    case EXCEPTION:
                    case FALSE:
                    case FloatLiteral:
                    case Identifier:
                    case IntegerLiteral:
                    case MESSAGE:
                    case NULL:
                    case STRINGLITERAL:
                    case TRUE:
                    case 43:
                    case 50:
                    case 54:
                    case 55:
                    case 58:
                    case 59:
                    case 67:
                    case 78:
                    case 81:
                    case 84:
                    case 90:
                    case 94:
                    case 101:
                    case 107:
                    case 127:
                    case 141:
                        {
                        alt39=1;
                        }
                        break;

                    }

                    }
                    break;

                }

                switch (alt39) {
            	case 1 :
            	    // org\\aries\\tmp2.g:208:21: relationalOp shiftExpression
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpression1345);
            	    relationalOp303=relationalOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalOp303.getTree());

            	    pushFollow(FOLLOW_shiftExpression_in_relationalExpression1347);
            	    shiftExpression304=shiftExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftExpression304.getTree());

            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 28, relationalExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "relationalExpression"


    public static class relationalOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relationalOp"
    // org\\aries\\tmp2.g:211:1: relationalOp : ( '<' '=' | '>' '=' | '<' | '>' );
    public final tmp2Parser.relationalOp_return relationalOp() throws RecognitionException {
        tmp2Parser.relationalOp_return retval = new tmp2Parser.relationalOp_return();
        retval.start = input.LT(1);

        int relationalOp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal305=null;
        Token char_literal306=null;
        Token char_literal307=null;
        Token char_literal308=null;
        Token char_literal309=null;
        Token char_literal310=null;

        Object char_literal305_tree=null;
        Object char_literal306_tree=null;
        Object char_literal307_tree=null;
        Object char_literal308_tree=null;
        Object char_literal309_tree=null;
        Object char_literal310_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }

            // org\\aries\\tmp2.g:212:2: ( '<' '=' | '>' '=' | '<' | '>' )
            int alt40=4;
            switch ( input.LA(1) ) {
            case 66:
                {
                switch ( input.LA(2) ) {
                case 67:
                    {
                    alt40=1;
                    }
                    break;
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 81:
                case 84:
                case 90:
                case 94:
                case 101:
                case 107:
                case 127:
                case 141:
                    {
                    alt40=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 1, input);

                    throw nvae;

                }

                }
                break;
            case 69:
                {
                switch ( input.LA(2) ) {
                case 67:
                    {
                    alt40=2;
                    }
                    break;
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 81:
                case 84:
                case 90:
                case 94:
                case 101:
                case 107:
                case 127:
                case 141:
                    {
                    alt40=4;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 2, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;

            }

            switch (alt40) {
                case 1 :
                    // org\\aries\\tmp2.g:212:4: '<' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal305=(Token)match(input,66,FOLLOW_66_in_relationalOp1361); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal305_tree = 
                    (Object)adaptor.create(char_literal305)
                    ;
                    adaptor.addChild(root_0, char_literal305_tree);
                    }

                    char_literal306=(Token)match(input,67,FOLLOW_67_in_relationalOp1363); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal306_tree = 
                    (Object)adaptor.create(char_literal306)
                    ;
                    adaptor.addChild(root_0, char_literal306_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:213:4: '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal307=(Token)match(input,69,FOLLOW_69_in_relationalOp1368); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal307_tree = 
                    (Object)adaptor.create(char_literal307)
                    ;
                    adaptor.addChild(root_0, char_literal307_tree);
                    }

                    char_literal308=(Token)match(input,67,FOLLOW_67_in_relationalOp1370); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal308_tree = 
                    (Object)adaptor.create(char_literal308)
                    ;
                    adaptor.addChild(root_0, char_literal308_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:214:4: '<'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal309=(Token)match(input,66,FOLLOW_66_in_relationalOp1375); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal309_tree = 
                    (Object)adaptor.create(char_literal309)
                    ;
                    adaptor.addChild(root_0, char_literal309_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:215:4: '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal310=(Token)match(input,69,FOLLOW_69_in_relationalOp1380); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal310_tree = 
                    (Object)adaptor.create(char_literal310)
                    ;
                    adaptor.addChild(root_0, char_literal310_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 29, relationalOp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "relationalOp"


    public static class shiftExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "shiftExpression"
    // org\\aries\\tmp2.g:218:1: shiftExpression : additiveExpression ( shiftOp additiveExpression )* ;
    public final tmp2Parser.shiftExpression_return shiftExpression() throws RecognitionException {
        tmp2Parser.shiftExpression_return retval = new tmp2Parser.shiftExpression_return();
        retval.start = input.LT(1);

        int shiftExpression_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.additiveExpression_return additiveExpression311 =null;

        tmp2Parser.shiftOp_return shiftOp312 =null;

        tmp2Parser.additiveExpression_return additiveExpression313 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }

            // org\\aries\\tmp2.g:219:2: ( additiveExpression ( shiftOp additiveExpression )* )
            // org\\aries\\tmp2.g:219:4: additiveExpression ( shiftOp additiveExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_additiveExpression_in_shiftExpression1392);
            additiveExpression311=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression311.getTree());

            // org\\aries\\tmp2.g:219:23: ( shiftOp additiveExpression )*
            loop41:
            do {
                int alt41=2;
                switch ( input.LA(1) ) {
                case 66:
                    {
                    switch ( input.LA(2) ) {
                    case 66:
                        {
                        switch ( input.LA(3) ) {
                        case CHARLITERAL:
                        case DoubleLiteral:
                        case EXCEPTION:
                        case FALSE:
                        case FloatLiteral:
                        case Identifier:
                        case IntegerLiteral:
                        case MESSAGE:
                        case NULL:
                        case STRINGLITERAL:
                        case TRUE:
                        case 43:
                        case 50:
                        case 54:
                        case 55:
                        case 58:
                        case 59:
                        case 78:
                        case 81:
                        case 84:
                        case 90:
                        case 94:
                        case 101:
                        case 107:
                        case 127:
                        case 141:
                            {
                            alt41=1;
                            }
                            break;

                        }

                        }
                        break;

                    }

                    }
                    break;
                case 69:
                    {
                    switch ( input.LA(2) ) {
                    case 69:
                        {
                        switch ( input.LA(3) ) {
                        case 69:
                            {
                            switch ( input.LA(4) ) {
                            case CHARLITERAL:
                            case DoubleLiteral:
                            case EXCEPTION:
                            case FALSE:
                            case FloatLiteral:
                            case Identifier:
                            case IntegerLiteral:
                            case MESSAGE:
                            case NULL:
                            case STRINGLITERAL:
                            case TRUE:
                            case 43:
                            case 50:
                            case 54:
                            case 55:
                            case 58:
                            case 59:
                            case 78:
                            case 81:
                            case 84:
                            case 90:
                            case 94:
                            case 101:
                            case 107:
                            case 127:
                            case 141:
                                {
                                alt41=1;
                                }
                                break;

                            }

                            }
                            break;
                        case CHARLITERAL:
                        case DoubleLiteral:
                        case EXCEPTION:
                        case FALSE:
                        case FloatLiteral:
                        case Identifier:
                        case IntegerLiteral:
                        case MESSAGE:
                        case NULL:
                        case STRINGLITERAL:
                        case TRUE:
                        case 43:
                        case 50:
                        case 54:
                        case 55:
                        case 58:
                        case 59:
                        case 78:
                        case 81:
                        case 84:
                        case 90:
                        case 94:
                        case 101:
                        case 107:
                        case 127:
                        case 141:
                            {
                            alt41=1;
                            }
                            break;

                        }

                        }
                        break;

                    }

                    }
                    break;

                }

                switch (alt41) {
            	case 1 :
            	    // org\\aries\\tmp2.g:219:24: shiftOp additiveExpression
            	    {
            	    pushFollow(FOLLOW_shiftOp_in_shiftExpression1395);
            	    shiftOp312=shiftOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftOp312.getTree());

            	    pushFollow(FOLLOW_additiveExpression_in_shiftExpression1397);
            	    additiveExpression313=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression313.getTree());

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 30, shiftExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "shiftExpression"


    public static class shiftOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "shiftOp"
    // org\\aries\\tmp2.g:222:1: shiftOp : ( '<' '<' | '>' '>' '>' | '>' '>' );
    public final tmp2Parser.shiftOp_return shiftOp() throws RecognitionException {
        tmp2Parser.shiftOp_return retval = new tmp2Parser.shiftOp_return();
        retval.start = input.LT(1);

        int shiftOp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal314=null;
        Token char_literal315=null;
        Token char_literal316=null;
        Token char_literal317=null;
        Token char_literal318=null;
        Token char_literal319=null;
        Token char_literal320=null;

        Object char_literal314_tree=null;
        Object char_literal315_tree=null;
        Object char_literal316_tree=null;
        Object char_literal317_tree=null;
        Object char_literal318_tree=null;
        Object char_literal319_tree=null;
        Object char_literal320_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }

            // org\\aries\\tmp2.g:223:2: ( '<' '<' | '>' '>' '>' | '>' '>' )
            int alt42=3;
            switch ( input.LA(1) ) {
            case 66:
                {
                alt42=1;
                }
                break;
            case 69:
                {
                switch ( input.LA(2) ) {
                case 69:
                    {
                    switch ( input.LA(3) ) {
                    case 69:
                        {
                        alt42=2;
                        }
                        break;
                    case CHARLITERAL:
                    case DoubleLiteral:
                    case EXCEPTION:
                    case FALSE:
                    case FloatLiteral:
                    case Identifier:
                    case IntegerLiteral:
                    case MESSAGE:
                    case NULL:
                    case STRINGLITERAL:
                    case TRUE:
                    case 43:
                    case 50:
                    case 54:
                    case 55:
                    case 58:
                    case 59:
                    case 78:
                    case 81:
                    case 84:
                    case 90:
                    case 94:
                    case 101:
                    case 107:
                    case 127:
                    case 141:
                        {
                        alt42=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 42, 3, input);

                        throw nvae;

                    }

                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 42, 2, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;

            }

            switch (alt42) {
                case 1 :
                    // org\\aries\\tmp2.g:223:4: '<' '<'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal314=(Token)match(input,66,FOLLOW_66_in_shiftOp1411); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal314_tree = 
                    (Object)adaptor.create(char_literal314)
                    ;
                    adaptor.addChild(root_0, char_literal314_tree);
                    }

                    char_literal315=(Token)match(input,66,FOLLOW_66_in_shiftOp1413); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal315_tree = 
                    (Object)adaptor.create(char_literal315)
                    ;
                    adaptor.addChild(root_0, char_literal315_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:224:4: '>' '>' '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal316=(Token)match(input,69,FOLLOW_69_in_shiftOp1418); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal316_tree = 
                    (Object)adaptor.create(char_literal316)
                    ;
                    adaptor.addChild(root_0, char_literal316_tree);
                    }

                    char_literal317=(Token)match(input,69,FOLLOW_69_in_shiftOp1420); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal317_tree = 
                    (Object)adaptor.create(char_literal317)
                    ;
                    adaptor.addChild(root_0, char_literal317_tree);
                    }

                    char_literal318=(Token)match(input,69,FOLLOW_69_in_shiftOp1422); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal318_tree = 
                    (Object)adaptor.create(char_literal318)
                    ;
                    adaptor.addChild(root_0, char_literal318_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:225:4: '>' '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal319=(Token)match(input,69,FOLLOW_69_in_shiftOp1427); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal319_tree = 
                    (Object)adaptor.create(char_literal319)
                    ;
                    adaptor.addChild(root_0, char_literal319_tree);
                    }

                    char_literal320=(Token)match(input,69,FOLLOW_69_in_shiftOp1429); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal320_tree = 
                    (Object)adaptor.create(char_literal320)
                    ;
                    adaptor.addChild(root_0, char_literal320_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 31, shiftOp_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "shiftOp"


    public static class additiveExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "additiveExpression"
    // org\\aries\\tmp2.g:228:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final tmp2Parser.additiveExpression_return additiveExpression() throws RecognitionException {
        tmp2Parser.additiveExpression_return retval = new tmp2Parser.additiveExpression_return();
        retval.start = input.LT(1);

        int additiveExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set322=null;
        tmp2Parser.multiplicativeExpression_return multiplicativeExpression321 =null;

        tmp2Parser.multiplicativeExpression_return multiplicativeExpression323 =null;


        Object set322_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }

            // org\\aries\\tmp2.g:229:2: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // org\\aries\\tmp2.g:229:4: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1441);
            multiplicativeExpression321=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression321.getTree());

            // org\\aries\\tmp2.g:229:29: ( ( '+' | '-' ) multiplicativeExpression )*
            loop43:
            do {
                int alt43=2;
                switch ( input.LA(1) ) {
                case 54:
                case 58:
                    {
                    alt43=1;
                    }
                    break;

                }

                switch (alt43) {
            	case 1 :
            	    // org\\aries\\tmp2.g:229:31: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    set322=(Token)input.LT(1);

            	    if ( input.LA(1)==54||input.LA(1)==58 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set322)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1453);
            	    multiplicativeExpression323=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression323.getTree());

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 32, additiveExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "additiveExpression"


    public static class multiplicativeExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "multiplicativeExpression"
    // org\\aries\\tmp2.g:232:1: multiplicativeExpression : unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* ;
    public final tmp2Parser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        tmp2Parser.multiplicativeExpression_return retval = new tmp2Parser.multiplicativeExpression_return();
        retval.start = input.LT(1);

        int multiplicativeExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set325=null;
        tmp2Parser.unaryExpression_return unaryExpression324 =null;

        tmp2Parser.unaryExpression_return unaryExpression326 =null;


        Object set325_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }

            // org\\aries\\tmp2.g:233:2: ( unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* )
            // org\\aries\\tmp2.g:233:4: unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1467);
            unaryExpression324=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression324.getTree());

            // org\\aries\\tmp2.g:233:20: ( ( '*' | '/' | '%' ) unaryExpression )*
            loop44:
            do {
                int alt44=2;
                switch ( input.LA(1) ) {
                case 45:
                case 52:
                case 62:
                    {
                    alt44=1;
                    }
                    break;

                }

                switch (alt44) {
            	case 1 :
            	    // org\\aries\\tmp2.g:233:22: ( '*' | '/' | '%' ) unaryExpression
            	    {
            	    set325=(Token)input.LT(1);

            	    if ( input.LA(1)==45||input.LA(1)==52||input.LA(1)==62 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set325)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1483);
            	    unaryExpression326=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression326.getTree());

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 33, multiplicativeExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpression"


    public static class unaryExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "unaryExpression"
    // org\\aries\\tmp2.g:240:1: unaryExpression : ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus );
    public final tmp2Parser.unaryExpression_return unaryExpression() throws RecognitionException {
        tmp2Parser.unaryExpression_return retval = new tmp2Parser.unaryExpression_return();
        retval.start = input.LT(1);

        int unaryExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal327=null;
        Token char_literal329=null;
        Token string_literal331=null;
        Token string_literal333=null;
        tmp2Parser.unaryExpression_return unaryExpression328 =null;

        tmp2Parser.unaryExpression_return unaryExpression330 =null;

        tmp2Parser.unaryExpression_return unaryExpression332 =null;

        tmp2Parser.unaryExpression_return unaryExpression334 =null;

        tmp2Parser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus335 =null;


        Object char_literal327_tree=null;
        Object char_literal329_tree=null;
        Object string_literal331_tree=null;
        Object string_literal333_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }

            // org\\aries\\tmp2.g:241:2: ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus )
            int alt45=5;
            switch ( input.LA(1) ) {
            case 54:
                {
                alt45=1;
                }
                break;
            case 58:
                {
                alt45=2;
                }
                break;
            case 55:
                {
                alt45=3;
                }
                break;
            case 59:
                {
                alt45=4;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 78:
            case 81:
            case 84:
            case 90:
            case 94:
            case 101:
            case 107:
            case 127:
            case 141:
                {
                alt45=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;

            }

            switch (alt45) {
                case 1 :
                    // org\\aries\\tmp2.g:241:4: '+' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal327=(Token)match(input,54,FOLLOW_54_in_unaryExpression1499); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal327_tree = 
                    (Object)adaptor.create(char_literal327)
                    ;
                    adaptor.addChild(root_0, char_literal327_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1501);
                    unaryExpression328=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression328.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:242:4: '-' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal329=(Token)match(input,58,FOLLOW_58_in_unaryExpression1506); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal329_tree = 
                    (Object)adaptor.create(char_literal329)
                    ;
                    adaptor.addChild(root_0, char_literal329_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1508);
                    unaryExpression330=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression330.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:243:4: '++' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal331=(Token)match(input,55,FOLLOW_55_in_unaryExpression1513); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal331_tree = 
                    (Object)adaptor.create(string_literal331)
                    ;
                    adaptor.addChild(root_0, string_literal331_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1515);
                    unaryExpression332=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression332.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:244:4: '--' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal333=(Token)match(input,59,FOLLOW_59_in_unaryExpression1520); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal333_tree = 
                    (Object)adaptor.create(string_literal333)
                    ;
                    adaptor.addChild(root_0, string_literal333_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1522);
                    unaryExpression334=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression334.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:245:4: unaryExpressionNotPlusMinus
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression1527);
                    unaryExpressionNotPlusMinus335=unaryExpressionNotPlusMinus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpressionNotPlusMinus335.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 34, unaryExpression_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "unaryExpression"


    public static class unaryExpressionNotPlusMinus_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "unaryExpressionNotPlusMinus"
    // org\\aries\\tmp2.g:248:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | primary ( selector )* ( '++' | '--' )? );
    public final tmp2Parser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus() throws RecognitionException {
        tmp2Parser.unaryExpressionNotPlusMinus_return retval = new tmp2Parser.unaryExpressionNotPlusMinus_return();
        retval.start = input.LT(1);

        int unaryExpressionNotPlusMinus_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal336=null;
        Token char_literal338=null;
        Token set342=null;
        tmp2Parser.unaryExpression_return unaryExpression337 =null;

        tmp2Parser.unaryExpression_return unaryExpression339 =null;

        tmp2Parser.primary_return primary340 =null;

        tmp2Parser.selector_return selector341 =null;


        Object char_literal336_tree=null;
        Object char_literal338_tree=null;
        Object set342_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }

            // org\\aries\\tmp2.g:249:2: ( '~' unaryExpression | '!' unaryExpression | primary ( selector )* ( '++' | '--' )? )
            int alt48=3;
            switch ( input.LA(1) ) {
            case 141:
                {
                alt48=1;
                }
                break;
            case 43:
                {
                alt48=2;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 50:
            case 78:
            case 81:
            case 84:
            case 90:
            case 94:
            case 101:
            case 107:
            case 127:
                {
                alt48=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;

            }

            switch (alt48) {
                case 1 :
                    // org\\aries\\tmp2.g:249:4: '~' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal336=(Token)match(input,141,FOLLOW_141_in_unaryExpressionNotPlusMinus1539); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal336_tree = 
                    (Object)adaptor.create(char_literal336)
                    ;
                    adaptor.addChild(root_0, char_literal336_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1541);
                    unaryExpression337=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression337.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:250:4: '!' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal338=(Token)match(input,43,FOLLOW_43_in_unaryExpressionNotPlusMinus1546); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal338_tree = 
                    (Object)adaptor.create(char_literal338)
                    ;
                    adaptor.addChild(root_0, char_literal338_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1548);
                    unaryExpression339=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression339.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:251:4: primary ( selector )* ( '++' | '--' )?
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_primary_in_unaryExpressionNotPlusMinus1553);
                    primary340=primary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary340.getTree());

                    // org\\aries\\tmp2.g:251:12: ( selector )*
                    loop46:
                    do {
                        int alt46=2;
                        switch ( input.LA(1) ) {
                        case 50:
                        case 61:
                        case 71:
                            {
                            alt46=1;
                            }
                            break;

                        }

                        switch (alt46) {
                    	case 1 :
                    	    // org\\aries\\tmp2.g:251:13: selector
                    	    {
                    	    pushFollow(FOLLOW_selector_in_unaryExpressionNotPlusMinus1556);
                    	    selector341=selector();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, selector341.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    // org\\aries\\tmp2.g:251:24: ( '++' | '--' )?
                    int alt47=2;
                    switch ( input.LA(1) ) {
                        case 55:
                        case 59:
                            {
                            alt47=1;
                            }
                            break;
                    }

                    switch (alt47) {
                        case 1 :
                            // org\\aries\\tmp2.g:
                            {
                            set342=(Token)input.LT(1);

                            if ( input.LA(1)==55||input.LA(1)==59 ) {
                                input.consume();
                                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                                (Object)adaptor.create(set342)
                                );
                                state.errorRecovery=false;
                                state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 35, unaryExpressionNotPlusMinus_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "unaryExpressionNotPlusMinus"


    public static class primary_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "primary"
    // org\\aries\\tmp2.g:254:1: primary : ( '(' expressionDecl ')' -> ^( PRIMARY '(' expressionDecl ')' ) | ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' ) -> ^( PRIMARY qualifiedName '(' ')' ) | ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' ) -> ^( PRIMARY qualifiedName '(' expressionList ')' ) | ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' ) -> ^( PRIMARY qualifiedName '[' expressionDecl ']' ) | ( type Identifier '=' )=> type Identifier -> ^( PRIMARY type Identifier ) | ( qualifiedName '.' )=> qualifiedName -> ^( PRIMARY qualifiedName ) | qualifiedName -> ^( PRIMARY qualifiedName ) | literal -> ^( PRIMARY literal ) );
    public final tmp2Parser.primary_return primary() throws RecognitionException {
        tmp2Parser.primary_return retval = new tmp2Parser.primary_return();
        retval.start = input.LT(1);

        int primary_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal343=null;
        Token char_literal345=null;
        Token char_literal347=null;
        Token char_literal348=null;
        Token char_literal350=null;
        Token char_literal352=null;
        Token char_literal354=null;
        Token char_literal356=null;
        Token Identifier358=null;
        tmp2Parser.expressionDecl_return expressionDecl344 =null;

        tmp2Parser.qualifiedName_return qualifiedName346 =null;

        tmp2Parser.qualifiedName_return qualifiedName349 =null;

        tmp2Parser.expressionList_return expressionList351 =null;

        tmp2Parser.qualifiedName_return qualifiedName353 =null;

        tmp2Parser.expressionDecl_return expressionDecl355 =null;

        tmp2Parser.type_return type357 =null;

        tmp2Parser.qualifiedName_return qualifiedName359 =null;

        tmp2Parser.qualifiedName_return qualifiedName360 =null;

        tmp2Parser.literal_return literal361 =null;


        Object char_literal343_tree=null;
        Object char_literal345_tree=null;
        Object char_literal347_tree=null;
        Object char_literal348_tree=null;
        Object char_literal350_tree=null;
        Object char_literal352_tree=null;
        Object char_literal354_tree=null;
        Object char_literal356_tree=null;
        Object Identifier358_tree=null;
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_expressionList=new RewriteRuleSubtreeStream(adaptor,"rule expressionList");
        RewriteRuleSubtreeStream stream_qualifiedName=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedName");
        RewriteRuleSubtreeStream stream_expressionDecl=new RewriteRuleSubtreeStream(adaptor,"rule expressionDecl");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_literal=new RewriteRuleSubtreeStream(adaptor,"rule literal");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }

            // org\\aries\\tmp2.g:255:2: ( '(' expressionDecl ')' -> ^( PRIMARY '(' expressionDecl ')' ) | ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' ) -> ^( PRIMARY qualifiedName '(' ')' ) | ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' ) -> ^( PRIMARY qualifiedName '(' expressionList ')' ) | ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' ) -> ^( PRIMARY qualifiedName '[' expressionDecl ']' ) | ( type Identifier '=' )=> type Identifier -> ^( PRIMARY type Identifier ) | ( qualifiedName '.' )=> qualifiedName -> ^( PRIMARY qualifiedName ) | qualifiedName -> ^( PRIMARY qualifiedName ) | literal -> ^( PRIMARY literal ) )
            int alt49=8;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==50) ) {
                alt49=1;
            }
            else if ( (LA49_0==Identifier) ) {
                int LA49_2 = input.LA(2);

                if ( (synpred2_tmp2()) ) {
                    alt49=2;
                }
                else if ( (synpred3_tmp2()) ) {
                    alt49=3;
                }
                else if ( (synpred4_tmp2()) ) {
                    alt49=4;
                }
                else if ( (synpred5_tmp2()) ) {
                    alt49=5;
                }
                else if ( (synpred6_tmp2()) ) {
                    alt49=6;
                }
                else if ( (true) ) {
                    alt49=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 49, 2, input);

                    throw nvae;

                }
            }
            else if ( (LA49_0==EXCEPTION||LA49_0==MESSAGE) ) {
                int LA49_3 = input.LA(2);

                if ( (synpred2_tmp2()) ) {
                    alt49=2;
                }
                else if ( (synpred3_tmp2()) ) {
                    alt49=3;
                }
                else if ( (synpred4_tmp2()) ) {
                    alt49=4;
                }
                else if ( (synpred5_tmp2()) ) {
                    alt49=5;
                }
                else if ( (synpred6_tmp2()) ) {
                    alt49=6;
                }
                else if ( (true) ) {
                    alt49=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 49, 3, input);

                    throw nvae;

                }
            }
            else if ( (LA49_0==78||LA49_0==81||LA49_0==84||LA49_0==90||LA49_0==94||LA49_0==101||LA49_0==107||LA49_0==127) && (synpred5_tmp2())) {
                alt49=5;
            }
            else if ( (LA49_0==CHARLITERAL||LA49_0==DoubleLiteral||(LA49_0 >= FALSE && LA49_0 <= FloatLiteral)||LA49_0==IntegerLiteral||LA49_0==NULL||LA49_0==STRINGLITERAL||LA49_0==TRUE) ) {
                alt49=8;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;

            }
            switch (alt49) {
                case 1 :
                    // org\\aries\\tmp2.g:255:4: '(' expressionDecl ')'
                    {
                    char_literal343=(Token)match(input,50,FOLLOW_50_in_primary1579); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal343);


                    pushFollow(FOLLOW_expressionDecl_in_primary1581);
                    expressionDecl344=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl344.getTree());

                    char_literal345=(Token)match(input,51,FOLLOW_51_in_primary1583); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal345);


                    // AST REWRITE
                    // elements: 51, expressionDecl, 50
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 255:27: -> ^( PRIMARY '(' expressionDecl ')' )
                    {
                        // org\\aries\\tmp2.g:255:30: ^( PRIMARY '(' expressionDecl ')' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_50.nextNode()
                        );

                        adaptor.addChild(root_1, stream_expressionDecl.nextTree());

                        adaptor.addChild(root_1, 
                        stream_51.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:256:4: ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' )
                    {
                    // org\\aries\\tmp2.g:256:31: ( qualifiedName '(' ')' )
                    // org\\aries\\tmp2.g:256:32: qualifiedName '(' ')'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1611);
                    qualifiedName346=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName346.getTree());

                    char_literal347=(Token)match(input,50,FOLLOW_50_in_primary1613); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal347);


                    char_literal348=(Token)match(input,51,FOLLOW_51_in_primary1615); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal348);


                    }


                    // AST REWRITE
                    // elements: qualifiedName, 50, 51
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 256:55: -> ^( PRIMARY qualifiedName '(' ')' )
                    {
                        // org\\aries\\tmp2.g:256:58: ^( PRIMARY qualifiedName '(' ')' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        adaptor.addChild(root_1, 
                        stream_50.nextNode()
                        );

                        adaptor.addChild(root_1, 
                        stream_51.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:257:4: ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' )
                    {
                    // org\\aries\\tmp2.g:257:27: ( qualifiedName '(' expressionList ')' )
                    // org\\aries\\tmp2.g:257:28: qualifiedName '(' expressionList ')'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1642);
                    qualifiedName349=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName349.getTree());

                    char_literal350=(Token)match(input,50,FOLLOW_50_in_primary1644); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal350);


                    pushFollow(FOLLOW_expressionList_in_primary1646);
                    expressionList351=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionList.add(expressionList351.getTree());

                    char_literal352=(Token)match(input,51,FOLLOW_51_in_primary1648); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal352);


                    }


                    // AST REWRITE
                    // elements: expressionList, 50, 51, qualifiedName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 257:66: -> ^( PRIMARY qualifiedName '(' expressionList ')' )
                    {
                        // org\\aries\\tmp2.g:257:69: ^( PRIMARY qualifiedName '(' expressionList ')' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        adaptor.addChild(root_1, 
                        stream_50.nextNode()
                        );

                        adaptor.addChild(root_1, stream_expressionList.nextTree());

                        adaptor.addChild(root_1, 
                        stream_51.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:258:4: ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' )
                    {
                    // org\\aries\\tmp2.g:258:27: ( qualifiedName '[' expressionDecl ']' )
                    // org\\aries\\tmp2.g:258:28: qualifiedName '[' expressionDecl ']'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1677);
                    qualifiedName353=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName353.getTree());

                    char_literal354=(Token)match(input,71,FOLLOW_71_in_primary1679); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_71.add(char_literal354);


                    pushFollow(FOLLOW_expressionDecl_in_primary1681);
                    expressionDecl355=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl355.getTree());

                    char_literal356=(Token)match(input,72,FOLLOW_72_in_primary1683); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_72.add(char_literal356);


                    }


                    // AST REWRITE
                    // elements: 71, qualifiedName, expressionDecl, 72
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 258:66: -> ^( PRIMARY qualifiedName '[' expressionDecl ']' )
                    {
                        // org\\aries\\tmp2.g:258:69: ^( PRIMARY qualifiedName '[' expressionDecl ']' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        adaptor.addChild(root_1, 
                        stream_71.nextNode()
                        );

                        adaptor.addChild(root_1, stream_expressionDecl.nextTree());

                        adaptor.addChild(root_1, 
                        stream_72.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:259:4: ( type Identifier '=' )=> type Identifier
                    {
                    pushFollow(FOLLOW_type_in_primary1713);
                    type357=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_type.add(type357.getTree());

                    Identifier358=(Token)match(input,Identifier,FOLLOW_Identifier_in_primary1715); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier358);


                    // AST REWRITE
                    // elements: type, Identifier
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 259:45: -> ^( PRIMARY type Identifier )
                    {
                        // org\\aries\\tmp2.g:259:48: ^( PRIMARY type Identifier )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_type.nextTree());

                        adaptor.addChild(root_1, 
                        stream_Identifier.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:260:4: ( qualifiedName '.' )=> qualifiedName
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1738);
                    qualifiedName359=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName359.getTree());

                    // AST REWRITE
                    // elements: qualifiedName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 260:41: -> ^( PRIMARY qualifiedName )
                    {
                        // org\\aries\\tmp2.g:260:44: ^( PRIMARY qualifiedName )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp2.g:261:4: qualifiedName
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1751);
                    qualifiedName360=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName360.getTree());

                    // AST REWRITE
                    // elements: qualifiedName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 261:18: -> ^( PRIMARY qualifiedName )
                    {
                        // org\\aries\\tmp2.g:261:21: ^( PRIMARY qualifiedName )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp2.g:262:4: literal
                    {
                    pushFollow(FOLLOW_literal_in_primary1764);
                    literal361=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_literal.add(literal361.getTree());

                    // AST REWRITE
                    // elements: literal
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 262:12: -> ^( PRIMARY literal )
                    {
                        // org\\aries\\tmp2.g:262:15: ^( PRIMARY literal )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(PRIMARY, "PRIMARY")
                        , root_1);

                        adaptor.addChild(root_1, stream_literal.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 36, primary_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "primary"


    public static class selector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "selector"
    // org\\aries\\tmp2.g:265:1: selector : ( ( '(' )=> arguments | ( '.' )=> '.' Identifier arguments | ( '[' )=> '[' expressionDecl ']' );
    public final tmp2Parser.selector_return selector() throws RecognitionException {
        tmp2Parser.selector_return retval = new tmp2Parser.selector_return();
        retval.start = input.LT(1);

        int selector_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal363=null;
        Token Identifier364=null;
        Token char_literal366=null;
        Token char_literal368=null;
        tmp2Parser.arguments_return arguments362 =null;

        tmp2Parser.arguments_return arguments365 =null;

        tmp2Parser.expressionDecl_return expressionDecl367 =null;


        Object char_literal363_tree=null;
        Object Identifier364_tree=null;
        Object char_literal366_tree=null;
        Object char_literal368_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }

            // org\\aries\\tmp2.g:266:2: ( ( '(' )=> arguments | ( '.' )=> '.' Identifier arguments | ( '[' )=> '[' expressionDecl ']' )
            int alt50=3;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==50) && (synpred7_tmp2())) {
                alt50=1;
            }
            else if ( (LA50_0==61) && (synpred8_tmp2())) {
                alt50=2;
            }
            else if ( (LA50_0==71) && (synpred9_tmp2())) {
                alt50=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;

            }
            switch (alt50) {
                case 1 :
                    // org\\aries\\tmp2.g:266:4: ( '(' )=> arguments
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_arguments_in_selector1791);
                    arguments362=arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arguments362.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:267:4: ( '.' )=> '.' Identifier arguments
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal363=(Token)match(input,61,FOLLOW_61_in_selector1802); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal363_tree = 
                    (Object)adaptor.create(char_literal363)
                    ;
                    adaptor.addChild(root_0, char_literal363_tree);
                    }

                    Identifier364=(Token)match(input,Identifier,FOLLOW_Identifier_in_selector1804); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier364_tree = 
                    (Object)adaptor.create(Identifier364)
                    ;
                    adaptor.addChild(root_0, Identifier364_tree);
                    }

                    pushFollow(FOLLOW_arguments_in_selector1806);
                    arguments365=arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arguments365.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:268:4: ( '[' )=> '[' expressionDecl ']'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal366=(Token)match(input,71,FOLLOW_71_in_selector1817); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal366_tree = 
                    (Object)adaptor.create(char_literal366)
                    ;
                    adaptor.addChild(root_0, char_literal366_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_selector1819);
                    expressionDecl367=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl367.getTree());

                    char_literal368=(Token)match(input,72,FOLLOW_72_in_selector1821); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal368_tree = 
                    (Object)adaptor.create(char_literal368)
                    ;
                    adaptor.addChild(root_0, char_literal368_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 37, selector_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "selector"


    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // org\\aries\\tmp2.g:271:1: type : ( primitiveType ( '[' ']' )* -> ^( TYPE primitiveType ( '[' ']' )* ) | qualifiedName ( typeArguments )? ( '[' ']' )* -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* ) );
    public final tmp2Parser.type_return type() throws RecognitionException {
        tmp2Parser.type_return retval = new tmp2Parser.type_return();
        retval.start = input.LT(1);

        int type_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal370=null;
        Token char_literal371=null;
        Token char_literal374=null;
        Token char_literal375=null;
        tmp2Parser.primitiveType_return primitiveType369 =null;

        tmp2Parser.qualifiedName_return qualifiedName372 =null;

        tmp2Parser.typeArguments_return typeArguments373 =null;


        Object char_literal370_tree=null;
        Object char_literal371_tree=null;
        Object char_literal374_tree=null;
        Object char_literal375_tree=null;
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleSubtreeStream stream_primitiveType=new RewriteRuleSubtreeStream(adaptor,"rule primitiveType");
        RewriteRuleSubtreeStream stream_qualifiedName=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedName");
        RewriteRuleSubtreeStream stream_typeArguments=new RewriteRuleSubtreeStream(adaptor,"rule typeArguments");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }

            // org\\aries\\tmp2.g:272:2: ( primitiveType ( '[' ']' )* -> ^( TYPE primitiveType ( '[' ']' )* ) | qualifiedName ( typeArguments )? ( '[' ']' )* -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* ) )
            int alt54=2;
            switch ( input.LA(1) ) {
            case 78:
            case 81:
            case 84:
            case 90:
            case 94:
            case 101:
            case 107:
            case 127:
                {
                alt54=1;
                }
                break;
            case EXCEPTION:
            case Identifier:
            case MESSAGE:
                {
                alt54=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;

            }

            switch (alt54) {
                case 1 :
                    // org\\aries\\tmp2.g:272:4: primitiveType ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_primitiveType_in_type1834);
                    primitiveType369=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primitiveType.add(primitiveType369.getTree());

                    // org\\aries\\tmp2.g:272:18: ( '[' ']' )*
                    loop51:
                    do {
                        int alt51=2;
                        switch ( input.LA(1) ) {
                        case 71:
                            {
                            alt51=1;
                            }
                            break;

                        }

                        switch (alt51) {
                    	case 1 :
                    	    // org\\aries\\tmp2.g:272:19: '[' ']'
                    	    {
                    	    char_literal370=(Token)match(input,71,FOLLOW_71_in_type1837); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_71.add(char_literal370);


                    	    char_literal371=(Token)match(input,72,FOLLOW_72_in_type1839); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_72.add(char_literal371);


                    	    }
                    	    break;

                    	default :
                    	    break loop51;
                        }
                    } while (true);


                    // AST REWRITE
                    // elements: 72, primitiveType, 71
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 272:29: -> ^( TYPE primitiveType ( '[' ']' )* )
                    {
                        // org\\aries\\tmp2.g:272:32: ^( TYPE primitiveType ( '[' ']' )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(TYPE, "TYPE")
                        , root_1);

                        adaptor.addChild(root_1, stream_primitiveType.nextTree());

                        // org\\aries\\tmp2.g:272:53: ( '[' ']' )*
                        while ( stream_72.hasNext()||stream_71.hasNext() ) {
                            adaptor.addChild(root_1, 
                            stream_71.nextNode()
                            );

                            adaptor.addChild(root_1, 
                            stream_72.nextNode()
                            );

                        }
                        stream_72.reset();
                        stream_71.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:273:4: qualifiedName ( typeArguments )? ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_qualifiedName_in_type1861);
                    qualifiedName372=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName372.getTree());

                    // org\\aries\\tmp2.g:273:18: ( typeArguments )?
                    int alt52=2;
                    switch ( input.LA(1) ) {
                        case 66:
                            {
                            alt52=1;
                            }
                            break;
                    }

                    switch (alt52) {
                        case 1 :
                            // org\\aries\\tmp2.g:273:18: typeArguments
                            {
                            pushFollow(FOLLOW_typeArguments_in_type1863);
                            typeArguments373=typeArguments();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_typeArguments.add(typeArguments373.getTree());

                            }
                            break;

                    }


                    // org\\aries\\tmp2.g:273:33: ( '[' ']' )*
                    loop53:
                    do {
                        int alt53=2;
                        switch ( input.LA(1) ) {
                        case 71:
                            {
                            alt53=1;
                            }
                            break;

                        }

                        switch (alt53) {
                    	case 1 :
                    	    // org\\aries\\tmp2.g:273:34: '[' ']'
                    	    {
                    	    char_literal374=(Token)match(input,71,FOLLOW_71_in_type1867); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_71.add(char_literal374);


                    	    char_literal375=(Token)match(input,72,FOLLOW_72_in_type1869); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_72.add(char_literal375);


                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);


                    // AST REWRITE
                    // elements: qualifiedName, typeArguments, 72, 71
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 273:44: -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* )
                    {
                        // org\\aries\\tmp2.g:273:47: ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(TYPE, "TYPE")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        // org\\aries\\tmp2.g:273:68: ( typeArguments )?
                        if ( stream_typeArguments.hasNext() ) {
                            adaptor.addChild(root_1, stream_typeArguments.nextTree());

                        }
                        stream_typeArguments.reset();

                        // org\\aries\\tmp2.g:273:83: ( '[' ']' )*
                        while ( stream_72.hasNext()||stream_71.hasNext() ) {
                            adaptor.addChild(root_1, 
                            stream_71.nextNode()
                            );

                            adaptor.addChild(root_1, 
                            stream_72.nextNode()
                            );

                        }
                        stream_72.reset();
                        stream_71.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 38, type_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "type"


    public static class typeList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typeList"
    // org\\aries\\tmp2.g:276:1: typeList : type ( ',' type )* ;
    public final tmp2Parser.typeList_return typeList() throws RecognitionException {
        tmp2Parser.typeList_return retval = new tmp2Parser.typeList_return();
        retval.start = input.LT(1);

        int typeList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal377=null;
        tmp2Parser.type_return type376 =null;

        tmp2Parser.type_return type378 =null;


        Object char_literal377_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }

            // org\\aries\\tmp2.g:277:2: ( type ( ',' type )* )
            // org\\aries\\tmp2.g:277:4: type ( ',' type )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_typeList1901);
            type376=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type376.getTree());

            // org\\aries\\tmp2.g:277:9: ( ',' type )*
            loop55:
            do {
                int alt55=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt55=1;
                    }
                    break;

                }

                switch (alt55) {
            	case 1 :
            	    // org\\aries\\tmp2.g:277:10: ',' type
            	    {
            	    char_literal377=(Token)match(input,57,FOLLOW_57_in_typeList1904); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal377_tree = 
            	    (Object)adaptor.create(char_literal377)
            	    ;
            	    adaptor.addChild(root_0, char_literal377_tree);
            	    }

            	    pushFollow(FOLLOW_type_in_typeList1906);
            	    type378=type();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, type378.getTree());

            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 39, typeList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typeList"


    public static class typeArguments_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typeArguments"
    // org\\aries\\tmp2.g:280:1: typeArguments : '<' typeArgument ( ',' typeArgument )* '>' ;
    public final tmp2Parser.typeArguments_return typeArguments() throws RecognitionException {
        tmp2Parser.typeArguments_return retval = new tmp2Parser.typeArguments_return();
        retval.start = input.LT(1);

        int typeArguments_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal379=null;
        Token char_literal381=null;
        Token char_literal383=null;
        tmp2Parser.typeArgument_return typeArgument380 =null;

        tmp2Parser.typeArgument_return typeArgument382 =null;


        Object char_literal379_tree=null;
        Object char_literal381_tree=null;
        Object char_literal383_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }

            // org\\aries\\tmp2.g:281:2: ( '<' typeArgument ( ',' typeArgument )* '>' )
            // org\\aries\\tmp2.g:281:4: '<' typeArgument ( ',' typeArgument )* '>'
            {
            root_0 = (Object)adaptor.nil();


            char_literal379=(Token)match(input,66,FOLLOW_66_in_typeArguments1920); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal379_tree = 
            (Object)adaptor.create(char_literal379)
            ;
            adaptor.addChild(root_0, char_literal379_tree);
            }

            pushFollow(FOLLOW_typeArgument_in_typeArguments1922);
            typeArgument380=typeArgument();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeArgument380.getTree());

            // org\\aries\\tmp2.g:281:21: ( ',' typeArgument )*
            loop56:
            do {
                int alt56=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt56=1;
                    }
                    break;

                }

                switch (alt56) {
            	case 1 :
            	    // org\\aries\\tmp2.g:281:22: ',' typeArgument
            	    {
            	    char_literal381=(Token)match(input,57,FOLLOW_57_in_typeArguments1925); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal381_tree = 
            	    (Object)adaptor.create(char_literal381)
            	    ;
            	    adaptor.addChild(root_0, char_literal381_tree);
            	    }

            	    pushFollow(FOLLOW_typeArgument_in_typeArguments1927);
            	    typeArgument382=typeArgument();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, typeArgument382.getTree());

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            char_literal383=(Token)match(input,69,FOLLOW_69_in_typeArguments1931); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal383_tree = 
            (Object)adaptor.create(char_literal383)
            ;
            adaptor.addChild(root_0, char_literal383_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 40, typeArguments_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typeArguments"


    public static class typeArgument_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typeArgument"
    // org\\aries\\tmp2.g:284:1: typeArgument : type ;
    public final tmp2Parser.typeArgument_return typeArgument() throws RecognitionException {
        tmp2Parser.typeArgument_return retval = new tmp2Parser.typeArgument_return();
        retval.start = input.LT(1);

        int typeArgument_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.type_return type384 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }

            // org\\aries\\tmp2.g:285:2: ( type )
            // org\\aries\\tmp2.g:285:4: type
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_typeArgument1943);
            type384=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type384.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 41, typeArgument_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typeArgument"


    public static class primitiveType_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "primitiveType"
    // org\\aries\\tmp2.g:288:1: primitiveType : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final tmp2Parser.primitiveType_return primitiveType() throws RecognitionException {
        tmp2Parser.primitiveType_return retval = new tmp2Parser.primitiveType_return();
        retval.start = input.LT(1);

        int primitiveType_StartIndex = input.index();

        Object root_0 = null;

        Token set385=null;

        Object set385_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }

            // org\\aries\\tmp2.g:289:2: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            // org\\aries\\tmp2.g:
            {
            root_0 = (Object)adaptor.nil();


            set385=(Token)input.LT(1);

            if ( input.LA(1)==78||input.LA(1)==81||input.LA(1)==84||input.LA(1)==90||input.LA(1)==94||input.LA(1)==101||input.LA(1)==107||input.LA(1)==127 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set385)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 42, primitiveType_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "primitiveType"


    public static class arguments_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arguments"
    // org\\aries\\tmp2.g:299:1: arguments : '(' ( expressionList )? ')' ;
    public final tmp2Parser.arguments_return arguments() throws RecognitionException {
        tmp2Parser.arguments_return retval = new tmp2Parser.arguments_return();
        retval.start = input.LT(1);

        int arguments_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal386=null;
        Token char_literal388=null;
        tmp2Parser.expressionList_return expressionList387 =null;


        Object char_literal386_tree=null;
        Object char_literal388_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }

            // org\\aries\\tmp2.g:300:2: ( '(' ( expressionList )? ')' )
            // org\\aries\\tmp2.g:300:4: '(' ( expressionList )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal386=(Token)match(input,50,FOLLOW_50_in_arguments2003); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal386_tree = 
            (Object)adaptor.create(char_literal386)
            ;
            adaptor.addChild(root_0, char_literal386_tree);
            }

            // org\\aries\\tmp2.g:300:8: ( expressionList )?
            int alt57=2;
            switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 81:
                case 84:
                case 90:
                case 94:
                case 101:
                case 107:
                case 127:
                case 141:
                    {
                    alt57=1;
                    }
                    break;
            }

            switch (alt57) {
                case 1 :
                    // org\\aries\\tmp2.g:300:9: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_arguments2006);
                    expressionList387=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList387.getTree());

                    }
                    break;

            }


            char_literal388=(Token)match(input,51,FOLLOW_51_in_arguments2010); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal388_tree = 
            (Object)adaptor.create(char_literal388)
            ;
            adaptor.addChild(root_0, char_literal388_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 43, arguments_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "arguments"


    public static class literal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "literal"
    // org\\aries\\tmp2.g:303:1: literal : ( IntegerLiteral | FloatLiteral | DoubleLiteral | CHARLITERAL | STRINGLITERAL | TRUE | FALSE | NULL );
    public final tmp2Parser.literal_return literal() throws RecognitionException {
        tmp2Parser.literal_return retval = new tmp2Parser.literal_return();
        retval.start = input.LT(1);

        int literal_StartIndex = input.index();

        Object root_0 = null;

        Token set389=null;

        Object set389_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }

            // org\\aries\\tmp2.g:304:2: ( IntegerLiteral | FloatLiteral | DoubleLiteral | CHARLITERAL | STRINGLITERAL | TRUE | FALSE | NULL )
            // org\\aries\\tmp2.g:
            {
            root_0 = (Object)adaptor.nil();


            set389=(Token)input.LT(1);

            if ( input.LA(1)==CHARLITERAL||input.LA(1)==DoubleLiteral||(input.LA(1) >= FALSE && input.LA(1) <= FloatLiteral)||input.LA(1)==IntegerLiteral||input.LA(1)==NULL||input.LA(1)==STRINGLITERAL||input.LA(1)==TRUE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set389)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 44, literal_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "literal"


    public static class exceptionDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exceptionDecl"
    // org\\aries\\tmp2.g:316:1: exceptionDecl : EXCEPTION ^ ':' exceptionBody ;
    public final tmp2Parser.exceptionDecl_return exceptionDecl() throws RecognitionException {
        tmp2Parser.exceptionDecl_return retval = new tmp2Parser.exceptionDecl_return();
        retval.start = input.LT(1);

        int exceptionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token EXCEPTION390=null;
        Token char_literal391=null;
        tmp2Parser.exceptionBody_return exceptionBody392 =null;


        Object EXCEPTION390_tree=null;
        Object char_literal391_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }

            // org\\aries\\tmp2.g:317:2: ( EXCEPTION ^ ':' exceptionBody )
            // org\\aries\\tmp2.g:317:4: EXCEPTION ^ ':' exceptionBody
            {
            root_0 = (Object)adaptor.nil();


            EXCEPTION390=(Token)match(input,EXCEPTION,FOLLOW_EXCEPTION_in_exceptionDecl2072); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EXCEPTION390_tree = 
            (Object)adaptor.create(EXCEPTION390)
            ;
            root_0 = (Object)adaptor.becomeRoot(EXCEPTION390_tree, root_0);
            }

            char_literal391=(Token)match(input,64,FOLLOW_64_in_exceptionDecl2075); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal391_tree = 
            (Object)adaptor.create(char_literal391)
            ;
            adaptor.addChild(root_0, char_literal391_tree);
            }

            pushFollow(FOLLOW_exceptionBody_in_exceptionDecl2077);
            exceptionBody392=exceptionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionBody392.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 45, exceptionDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exceptionDecl"


    public static class exceptionBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exceptionBody"
    // org\\aries\\tmp2.g:320:1: exceptionBody : '{' ( exceptionMember )* '}' ;
    public final tmp2Parser.exceptionBody_return exceptionBody() throws RecognitionException {
        tmp2Parser.exceptionBody_return retval = new tmp2Parser.exceptionBody_return();
        retval.start = input.LT(1);

        int exceptionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal393=null;
        Token char_literal395=null;
        tmp2Parser.exceptionMember_return exceptionMember394 =null;


        Object char_literal393_tree=null;
        Object char_literal395_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }

            // org\\aries\\tmp2.g:321:2: ( '{' ( exceptionMember )* '}' )
            // org\\aries\\tmp2.g:321:4: '{' ( exceptionMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal393=(Token)match(input,136,FOLLOW_136_in_exceptionBody2088); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal393_tree = 
            (Object)adaptor.create(char_literal393)
            ;
            adaptor.addChild(root_0, char_literal393_tree);
            }

            // org\\aries\\tmp2.g:321:8: ( exceptionMember )*
            loop58:
            do {
                int alt58=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 94:
                case 95:
                case 97:
                case 101:
                case 107:
                case 112:
                case 115:
                case 118:
                case 120:
                case 124:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt58=1;
                    }
                    break;

                }

                switch (alt58) {
            	case 1 :
            	    // org\\aries\\tmp2.g:321:9: exceptionMember
            	    {
            	    pushFollow(FOLLOW_exceptionMember_in_exceptionBody2091);
            	    exceptionMember394=exceptionMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionMember394.getTree());

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


            char_literal395=(Token)match(input,140,FOLLOW_140_in_exceptionBody2095); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal395_tree = 
            (Object)adaptor.create(char_literal395)
            ;
            adaptor.addChild(root_0, char_literal395_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 46, exceptionBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exceptionBody"


    public static class exceptionMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exceptionMember"
    // org\\aries\\tmp2.g:324:1: exceptionMember : ( optionDecl | statementDecl );
    public final tmp2Parser.exceptionMember_return exceptionMember() throws RecognitionException {
        tmp2Parser.exceptionMember_return retval = new tmp2Parser.exceptionMember_return();
        retval.start = input.LT(1);

        int exceptionMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.optionDecl_return optionDecl396 =null;

        tmp2Parser.statementDecl_return statementDecl397 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }

            // org\\aries\\tmp2.g:325:2: ( optionDecl | statementDecl )
            int alt59=2;
            switch ( input.LA(1) ) {
            case 112:
                {
                alt59=1;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt59=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;

            }

            switch (alt59) {
                case 1 :
                    // org\\aries\\tmp2.g:325:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_exceptionMember2106);
                    optionDecl396=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl396.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:326:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_exceptionMember2111);
                    statementDecl397=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl397.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 47, exceptionMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exceptionMember"


    public static class executeDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeDecl"
    // org\\aries\\tmp2.g:330:1: executeDecl : ( 'execute' ^ executeBody | 'execute' ^ 'then' Identifier formalParameters executeBody );
    public final tmp2Parser.executeDecl_return executeDecl() throws RecognitionException {
        tmp2Parser.executeDecl_return retval = new tmp2Parser.executeDecl_return();
        retval.start = input.LT(1);

        int executeDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal398=null;
        Token string_literal400=null;
        Token string_literal401=null;
        Token Identifier402=null;
        tmp2Parser.executeBody_return executeBody399 =null;

        tmp2Parser.formalParameters_return formalParameters403 =null;

        tmp2Parser.executeBody_return executeBody404 =null;


        Object string_literal398_tree=null;
        Object string_literal400_tree=null;
        Object string_literal401_tree=null;
        Object Identifier402_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }

            // org\\aries\\tmp2.g:331:2: ( 'execute' ^ executeBody | 'execute' ^ 'then' Identifier formalParameters executeBody )
            int alt60=2;
            switch ( input.LA(1) ) {
            case 93:
                {
                switch ( input.LA(2) ) {
                case 130:
                    {
                    alt60=2;
                    }
                    break;
                case 136:
                    {
                    alt60=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 60, 1, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;

            }

            switch (alt60) {
                case 1 :
                    // org\\aries\\tmp2.g:331:4: 'execute' ^ executeBody
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal398=(Token)match(input,93,FOLLOW_93_in_executeDecl2123); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal398_tree = 
                    (Object)adaptor.create(string_literal398)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal398_tree, root_0);
                    }

                    pushFollow(FOLLOW_executeBody_in_executeDecl2126);
                    executeBody399=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody399.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:332:4: 'execute' ^ 'then' Identifier formalParameters executeBody
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal400=(Token)match(input,93,FOLLOW_93_in_executeDecl2131); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal400_tree = 
                    (Object)adaptor.create(string_literal400)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal400_tree, root_0);
                    }

                    string_literal401=(Token)match(input,130,FOLLOW_130_in_executeDecl2134); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal401_tree = 
                    (Object)adaptor.create(string_literal401)
                    ;
                    adaptor.addChild(root_0, string_literal401_tree);
                    }

                    Identifier402=(Token)match(input,Identifier,FOLLOW_Identifier_in_executeDecl2136); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier402_tree = 
                    (Object)adaptor.create(Identifier402)
                    ;
                    adaptor.addChild(root_0, Identifier402_tree);
                    }

                    pushFollow(FOLLOW_formalParameters_in_executeDecl2138);
                    formalParameters403=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters403.getTree());

                    pushFollow(FOLLOW_executeBody_in_executeDecl2140);
                    executeBody404=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody404.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 48, executeDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeDecl"


    public static class executeDeclRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeDeclRest"
    // org\\aries\\tmp2.g:335:1: executeDeclRest : formalParameters ( executeBody | ';' ) ;
    public final tmp2Parser.executeDeclRest_return executeDeclRest() throws RecognitionException {
        tmp2Parser.executeDeclRest_return retval = new tmp2Parser.executeDeclRest_return();
        retval.start = input.LT(1);

        int executeDeclRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal407=null;
        tmp2Parser.formalParameters_return formalParameters405 =null;

        tmp2Parser.executeBody_return executeBody406 =null;


        Object char_literal407_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }

            // org\\aries\\tmp2.g:336:2: ( formalParameters ( executeBody | ';' ) )
            // org\\aries\\tmp2.g:336:4: formalParameters ( executeBody | ';' )
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameters_in_executeDeclRest2152);
            formalParameters405=formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters405.getTree());

            // org\\aries\\tmp2.g:336:21: ( executeBody | ';' )
            int alt61=2;
            switch ( input.LA(1) ) {
            case 136:
                {
                alt61=1;
                }
                break;
            case 65:
                {
                alt61=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;

            }

            switch (alt61) {
                case 1 :
                    // org\\aries\\tmp2.g:336:22: executeBody
                    {
                    pushFollow(FOLLOW_executeBody_in_executeDeclRest2155);
                    executeBody406=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody406.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:336:36: ';'
                    {
                    char_literal407=(Token)match(input,65,FOLLOW_65_in_executeDeclRest2159); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal407_tree = 
                    (Object)adaptor.create(char_literal407)
                    ;
                    adaptor.addChild(root_0, char_literal407_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 49, executeDeclRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeDeclRest"


    public static class executeBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeBody"
    // org\\aries\\tmp2.g:339:1: executeBody : '{' ( executeMember )* '}' ;
    public final tmp2Parser.executeBody_return executeBody() throws RecognitionException {
        tmp2Parser.executeBody_return retval = new tmp2Parser.executeBody_return();
        retval.start = input.LT(1);

        int executeBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal408=null;
        Token char_literal410=null;
        tmp2Parser.executeMember_return executeMember409 =null;


        Object char_literal408_tree=null;
        Object char_literal410_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }

            // org\\aries\\tmp2.g:340:2: ( '{' ( executeMember )* '}' )
            // org\\aries\\tmp2.g:340:4: '{' ( executeMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal408=(Token)match(input,136,FOLLOW_136_in_executeBody2171); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal408_tree = 
            (Object)adaptor.create(char_literal408)
            ;
            adaptor.addChild(root_0, char_literal408_tree);
            }

            // org\\aries\\tmp2.g:340:8: ( executeMember )*
            loop62:
            do {
                int alt62=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case 76:
                case 79:
                case 98:
                case 99:
                case 104:
                case 126:
                case 133:
                    {
                    alt62=1;
                    }
                    break;

                }

                switch (alt62) {
            	case 1 :
            	    // org\\aries\\tmp2.g:340:9: executeMember
            	    {
            	    pushFollow(FOLLOW_executeMember_in_executeBody2174);
            	    executeMember409=executeMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeMember409.getTree());

            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);


            char_literal410=(Token)match(input,140,FOLLOW_140_in_executeBody2178); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal410_tree = 
            (Object)adaptor.create(char_literal410)
            ;
            adaptor.addChild(root_0, char_literal410_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 50, executeBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeBody"


    public static class executeMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeMember"
    // org\\aries\\tmp2.g:343:1: executeMember : ( assignmentDecl | branchDecl | exceptionDecl | timeoutDecl );
    public final tmp2Parser.executeMember_return executeMember() throws RecognitionException {
        tmp2Parser.executeMember_return retval = new tmp2Parser.executeMember_return();
        retval.start = input.LT(1);

        int executeMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl411 =null;

        tmp2Parser.branchDecl_return branchDecl412 =null;

        tmp2Parser.exceptionDecl_return exceptionDecl413 =null;

        tmp2Parser.timeoutDecl_return timeoutDecl414 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }

            // org\\aries\\tmp2.g:344:2: ( assignmentDecl | branchDecl | exceptionDecl | timeoutDecl )
            int alt63=4;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt63=1;
                }
                break;
            case 79:
                {
                alt63=2;
                }
                break;
            case EXCEPTION:
                {
                alt63=3;
                }
                break;
            case 133:
                {
                alt63=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;

            }

            switch (alt63) {
                case 1 :
                    // org\\aries\\tmp2.g:344:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_executeMember2190);
                    assignmentDecl411=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl411.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:345:4: branchDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_branchDecl_in_executeMember2195);
                    branchDecl412=branchDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, branchDecl412.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:346:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_executeMember2200);
                    exceptionDecl413=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl413.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:347:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_executeMember2205);
                    timeoutDecl414=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl414.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 51, executeMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeMember"


    public static class groupDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "groupDecl"
    // org\\aries\\tmp2.g:350:1: groupDecl : 'group' ^ Identifier groupBody ;
    public final tmp2Parser.groupDecl_return groupDecl() throws RecognitionException {
        tmp2Parser.groupDecl_return retval = new tmp2Parser.groupDecl_return();
        retval.start = input.LT(1);

        int groupDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal415=null;
        Token Identifier416=null;
        tmp2Parser.groupBody_return groupBody417 =null;


        Object string_literal415_tree=null;
        Object Identifier416_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }

            // org\\aries\\tmp2.g:351:2: ( 'group' ^ Identifier groupBody )
            // org\\aries\\tmp2.g:351:4: 'group' ^ Identifier groupBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal415=(Token)match(input,96,FOLLOW_96_in_groupDecl2216); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal415_tree = 
            (Object)adaptor.create(string_literal415)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal415_tree, root_0);
            }

            Identifier416=(Token)match(input,Identifier,FOLLOW_Identifier_in_groupDecl2219); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier416_tree = 
            (Object)adaptor.create(Identifier416)
            ;
            adaptor.addChild(root_0, Identifier416_tree);
            }

            pushFollow(FOLLOW_groupBody_in_groupDecl2221);
            groupBody417=groupBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, groupBody417.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 52, groupDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "groupDecl"


    public static class groupBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "groupBody"
    // org\\aries\\tmp2.g:354:1: groupBody : '{' ( groupMember )* '}' ;
    public final tmp2Parser.groupBody_return groupBody() throws RecognitionException {
        tmp2Parser.groupBody_return retval = new tmp2Parser.groupBody_return();
        retval.start = input.LT(1);

        int groupBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal418=null;
        Token char_literal420=null;
        tmp2Parser.groupMember_return groupMember419 =null;


        Object char_literal418_tree=null;
        Object char_literal420_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }

            // org\\aries\\tmp2.g:355:2: ( '{' ( groupMember )* '}' )
            // org\\aries\\tmp2.g:355:4: '{' ( groupMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal418=(Token)match(input,136,FOLLOW_136_in_groupBody2232); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal418_tree = 
            (Object)adaptor.create(char_literal418)
            ;
            adaptor.addChild(root_0, char_literal418_tree);
            }

            // org\\aries\\tmp2.g:355:8: ( groupMember )*
            loop64:
            do {
                int alt64=2;
                switch ( input.LA(1) ) {
                case 76:
                case 98:
                case 99:
                case 104:
                case 126:
                    {
                    alt64=1;
                    }
                    break;

                }

                switch (alt64) {
            	case 1 :
            	    // org\\aries\\tmp2.g:355:9: groupMember
            	    {
            	    pushFollow(FOLLOW_groupMember_in_groupBody2235);
            	    groupMember419=groupMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, groupMember419.getTree());

            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            char_literal420=(Token)match(input,140,FOLLOW_140_in_groupBody2239); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal420_tree = 
            (Object)adaptor.create(char_literal420)
            ;
            adaptor.addChild(root_0, char_literal420_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 53, groupBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "groupBody"


    public static class groupMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "groupMember"
    // org\\aries\\tmp2.g:358:1: groupMember : assignmentDecl ;
    public final tmp2Parser.groupMember_return groupMember() throws RecognitionException {
        tmp2Parser.groupMember_return retval = new tmp2Parser.groupMember_return();
        retval.start = input.LT(1);

        int groupMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl421 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }

            // org\\aries\\tmp2.g:359:2: ( assignmentDecl )
            // org\\aries\\tmp2.g:359:4: assignmentDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_assignmentDecl_in_groupMember2250);
            assignmentDecl421=assignmentDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl421.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 54, groupMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "groupMember"


    public static class importDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "importDecl"
    // org\\aries\\tmp2.g:362:1: importDecl : 'import' ^ qualifiedName ( '.' '*' )? ';' ;
    public final tmp2Parser.importDecl_return importDecl() throws RecognitionException {
        tmp2Parser.importDecl_return retval = new tmp2Parser.importDecl_return();
        retval.start = input.LT(1);

        int importDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal422=null;
        Token char_literal424=null;
        Token char_literal425=null;
        Token char_literal426=null;
        tmp2Parser.qualifiedName_return qualifiedName423 =null;


        Object string_literal422_tree=null;
        Object char_literal424_tree=null;
        Object char_literal425_tree=null;
        Object char_literal426_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }

            // org\\aries\\tmp2.g:363:2: ( 'import' ^ qualifiedName ( '.' '*' )? ';' )
            // org\\aries\\tmp2.g:363:4: 'import' ^ qualifiedName ( '.' '*' )? ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal422=(Token)match(input,98,FOLLOW_98_in_importDecl2262); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal422_tree = 
            (Object)adaptor.create(string_literal422)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal422_tree, root_0);
            }

            pushFollow(FOLLOW_qualifiedName_in_importDecl2265);
            qualifiedName423=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName423.getTree());

            // org\\aries\\tmp2.g:363:28: ( '.' '*' )?
            int alt65=2;
            switch ( input.LA(1) ) {
                case 61:
                    {
                    alt65=1;
                    }
                    break;
            }

            switch (alt65) {
                case 1 :
                    // org\\aries\\tmp2.g:363:29: '.' '*'
                    {
                    char_literal424=(Token)match(input,61,FOLLOW_61_in_importDecl2268); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal424_tree = 
                    (Object)adaptor.create(char_literal424)
                    ;
                    adaptor.addChild(root_0, char_literal424_tree);
                    }

                    char_literal425=(Token)match(input,52,FOLLOW_52_in_importDecl2270); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal425_tree = 
                    (Object)adaptor.create(char_literal425)
                    ;
                    adaptor.addChild(root_0, char_literal425_tree);
                    }

                    }
                    break;

            }


            char_literal426=(Token)match(input,65,FOLLOW_65_in_importDecl2274); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal426_tree = 
            (Object)adaptor.create(char_literal426)
            ;
            adaptor.addChild(root_0, char_literal426_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 55, importDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "importDecl"


    public static class invokeDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "invokeDecl"
    // org\\aries\\tmp2.g:366:1: invokeDecl : 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody ;
    public final tmp2Parser.invokeDecl_return invokeDecl() throws RecognitionException {
        tmp2Parser.invokeDecl_return retval = new tmp2Parser.invokeDecl_return();
        retval.start = input.LT(1);

        int invokeDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal427=null;
        Token Identifier428=null;
        Token char_literal429=null;
        Token Identifier430=null;
        tmp2Parser.formalParameters_return formalParameters431 =null;

        tmp2Parser.invokeBody_return invokeBody432 =null;


        Object string_literal427_tree=null;
        Object Identifier428_tree=null;
        Object char_literal429_tree=null;
        Object Identifier430_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }

            // org\\aries\\tmp2.g:367:2: ( 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody )
            // org\\aries\\tmp2.g:367:4: 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal427=(Token)match(input,102,FOLLOW_102_in_invokeDecl2285); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal427_tree = 
            (Object)adaptor.create(string_literal427)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal427_tree, root_0);
            }

            Identifier428=(Token)match(input,Identifier,FOLLOW_Identifier_in_invokeDecl2288); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier428_tree = 
            (Object)adaptor.create(Identifier428)
            ;
            adaptor.addChild(root_0, Identifier428_tree);
            }

            char_literal429=(Token)match(input,61,FOLLOW_61_in_invokeDecl2290); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal429_tree = 
            (Object)adaptor.create(char_literal429)
            ;
            adaptor.addChild(root_0, char_literal429_tree);
            }

            Identifier430=(Token)match(input,Identifier,FOLLOW_Identifier_in_invokeDecl2292); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier430_tree = 
            (Object)adaptor.create(Identifier430)
            ;
            adaptor.addChild(root_0, Identifier430_tree);
            }

            pushFollow(FOLLOW_formalParameters_in_invokeDecl2294);
            formalParameters431=formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters431.getTree());

            pushFollow(FOLLOW_invokeBody_in_invokeDecl2296);
            invokeBody432=invokeBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeBody432.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 56, invokeDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "invokeDecl"


    public static class invokeBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "invokeBody"
    // org\\aries\\tmp2.g:370:1: invokeBody : '{' ( invokeMember )* '}' ;
    public final tmp2Parser.invokeBody_return invokeBody() throws RecognitionException {
        tmp2Parser.invokeBody_return retval = new tmp2Parser.invokeBody_return();
        retval.start = input.LT(1);

        int invokeBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal433=null;
        Token char_literal435=null;
        tmp2Parser.invokeMember_return invokeMember434 =null;


        Object char_literal433_tree=null;
        Object char_literal435_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }

            // org\\aries\\tmp2.g:371:2: ( '{' ( invokeMember )* '}' )
            // org\\aries\\tmp2.g:371:4: '{' ( invokeMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal433=(Token)match(input,136,FOLLOW_136_in_invokeBody2307); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal433_tree = 
            (Object)adaptor.create(char_literal433)
            ;
            adaptor.addChild(root_0, char_literal433_tree);
            }

            // org\\aries\\tmp2.g:371:8: ( invokeMember )*
            loop66:
            do {
                int alt66=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case MESSAGE:
                case 76:
                case 98:
                case 99:
                case 104:
                case 126:
                case 133:
                    {
                    alt66=1;
                    }
                    break;

                }

                switch (alt66) {
            	case 1 :
            	    // org\\aries\\tmp2.g:371:9: invokeMember
            	    {
            	    pushFollow(FOLLOW_invokeMember_in_invokeBody2310);
            	    invokeMember434=invokeMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeMember434.getTree());

            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);


            char_literal435=(Token)match(input,140,FOLLOW_140_in_invokeBody2314); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal435_tree = 
            (Object)adaptor.create(char_literal435)
            ;
            adaptor.addChild(root_0, char_literal435_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 57, invokeBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "invokeBody"


    public static class invokeMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "invokeMember"
    // org\\aries\\tmp2.g:374:1: invokeMember : ( assignmentDecl | messageDecl | exceptionDecl | timeoutDecl );
    public final tmp2Parser.invokeMember_return invokeMember() throws RecognitionException {
        tmp2Parser.invokeMember_return retval = new tmp2Parser.invokeMember_return();
        retval.start = input.LT(1);

        int invokeMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl436 =null;

        tmp2Parser.messageDecl_return messageDecl437 =null;

        tmp2Parser.exceptionDecl_return exceptionDecl438 =null;

        tmp2Parser.timeoutDecl_return timeoutDecl439 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }

            // org\\aries\\tmp2.g:375:2: ( assignmentDecl | messageDecl | exceptionDecl | timeoutDecl )
            int alt67=4;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt67=1;
                }
                break;
            case MESSAGE:
                {
                alt67=2;
                }
                break;
            case EXCEPTION:
                {
                alt67=3;
                }
                break;
            case 133:
                {
                alt67=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;

            }

            switch (alt67) {
                case 1 :
                    // org\\aries\\tmp2.g:375:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_invokeMember2325);
                    assignmentDecl436=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl436.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:376:4: messageDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_messageDecl_in_invokeMember2330);
                    messageDecl437=messageDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, messageDecl437.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:377:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_invokeMember2335);
                    exceptionDecl438=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl438.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:378:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_invokeMember2340);
                    timeoutDecl439=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl439.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 58, invokeMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "invokeMember"


    public static class itemsDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemsDecl"
    // org\\aries\\tmp2.g:381:1: itemsDecl : 'items' ^ itemsBody ;
    public final tmp2Parser.itemsDecl_return itemsDecl() throws RecognitionException {
        tmp2Parser.itemsDecl_return retval = new tmp2Parser.itemsDecl_return();
        retval.start = input.LT(1);

        int itemsDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal440=null;
        tmp2Parser.itemsBody_return itemsBody441 =null;


        Object string_literal440_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }

            // org\\aries\\tmp2.g:382:2: ( 'items' ^ itemsBody )
            // org\\aries\\tmp2.g:382:4: 'items' ^ itemsBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal440=(Token)match(input,103,FOLLOW_103_in_itemsDecl2351); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal440_tree = 
            (Object)adaptor.create(string_literal440)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal440_tree, root_0);
            }

            pushFollow(FOLLOW_itemsBody_in_itemsDecl2354);
            itemsBody441=itemsBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsBody441.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 59, itemsDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemsDecl"


    public static class itemsBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemsBody"
    // org\\aries\\tmp2.g:385:1: itemsBody : '{' ( itemsMember )* '}' ;
    public final tmp2Parser.itemsBody_return itemsBody() throws RecognitionException {
        tmp2Parser.itemsBody_return retval = new tmp2Parser.itemsBody_return();
        retval.start = input.LT(1);

        int itemsBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal442=null;
        Token char_literal444=null;
        tmp2Parser.itemsMember_return itemsMember443 =null;


        Object char_literal442_tree=null;
        Object char_literal444_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }

            // org\\aries\\tmp2.g:386:2: ( '{' ( itemsMember )* '}' )
            // org\\aries\\tmp2.g:386:4: '{' ( itemsMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal442=(Token)match(input,136,FOLLOW_136_in_itemsBody2365); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal442_tree = 
            (Object)adaptor.create(char_literal442)
            ;
            adaptor.addChild(root_0, char_literal442_tree);
            }

            // org\\aries\\tmp2.g:386:8: ( itemsMember )*
            loop68:
            do {
                int alt68=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                case 78:
                case 81:
                case 84:
                case 90:
                case 94:
                case 101:
                case 107:
                case 127:
                    {
                    alt68=1;
                    }
                    break;

                }

                switch (alt68) {
            	case 1 :
            	    // org\\aries\\tmp2.g:386:9: itemsMember
            	    {
            	    pushFollow(FOLLOW_itemsMember_in_itemsBody2368);
            	    itemsMember443=itemsMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsMember443.getTree());

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);


            char_literal444=(Token)match(input,140,FOLLOW_140_in_itemsBody2372); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal444_tree = 
            (Object)adaptor.create(char_literal444)
            ;
            adaptor.addChild(root_0, char_literal444_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 60, itemsBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemsBody"


    public static class itemsMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemsMember"
    // org\\aries\\tmp2.g:389:1: itemsMember : itemDecl ;
    public final tmp2Parser.itemsMember_return itemsMember() throws RecognitionException {
        tmp2Parser.itemsMember_return retval = new tmp2Parser.itemsMember_return();
        retval.start = input.LT(1);

        int itemsMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.itemDecl_return itemDecl445 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }

            // org\\aries\\tmp2.g:390:2: ( itemDecl )
            // org\\aries\\tmp2.g:390:4: itemDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_itemDecl_in_itemsMember2383);
            itemDecl445=itemDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemDecl445.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 61, itemsMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemsMember"


    public static class itemDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemDecl"
    // org\\aries\\tmp2.g:393:1: itemDecl : type Identifier itemDeclRest -> ^( ITEM type Identifier itemDeclRest ) ;
    public final tmp2Parser.itemDecl_return itemDecl() throws RecognitionException {
        tmp2Parser.itemDecl_return retval = new tmp2Parser.itemDecl_return();
        retval.start = input.LT(1);

        int itemDecl_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier447=null;
        tmp2Parser.type_return type446 =null;

        tmp2Parser.itemDeclRest_return itemDeclRest448 =null;


        Object Identifier447_tree=null;
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_itemDeclRest=new RewriteRuleSubtreeStream(adaptor,"rule itemDeclRest");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }

            // org\\aries\\tmp2.g:394:2: ( type Identifier itemDeclRest -> ^( ITEM type Identifier itemDeclRest ) )
            // org\\aries\\tmp2.g:394:4: type Identifier itemDeclRest
            {
            pushFollow(FOLLOW_type_in_itemDecl2394);
            type446=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type446.getTree());

            Identifier447=(Token)match(input,Identifier,FOLLOW_Identifier_in_itemDecl2396); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Identifier.add(Identifier447);


            pushFollow(FOLLOW_itemDeclRest_in_itemDecl2398);
            itemDeclRest448=itemDeclRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_itemDeclRest.add(itemDeclRest448.getTree());

            // AST REWRITE
            // elements: type, Identifier, itemDeclRest
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 394:33: -> ^( ITEM type Identifier itemDeclRest )
            {
                // org\\aries\\tmp2.g:394:36: ^( ITEM type Identifier itemDeclRest )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(ITEM, "ITEM")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_Identifier.nextNode()
                );

                adaptor.addChild(root_1, stream_itemDeclRest.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 62, itemDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemDecl"


    public static class itemDeclRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemDeclRest"
    // org\\aries\\tmp2.g:398:1: itemDeclRest : ( '{' ( itemMember )* '}' | ';' );
    public final tmp2Parser.itemDeclRest_return itemDeclRest() throws RecognitionException {
        tmp2Parser.itemDeclRest_return retval = new tmp2Parser.itemDeclRest_return();
        retval.start = input.LT(1);

        int itemDeclRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal449=null;
        Token char_literal451=null;
        Token char_literal452=null;
        tmp2Parser.itemMember_return itemMember450 =null;


        Object char_literal449_tree=null;
        Object char_literal451_tree=null;
        Object char_literal452_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }

            // org\\aries\\tmp2.g:399:2: ( '{' ( itemMember )* '}' | ';' )
            int alt70=2;
            switch ( input.LA(1) ) {
            case 136:
                {
                alt70=1;
                }
                break;
            case 65:
                {
                alt70=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;

            }

            switch (alt70) {
                case 1 :
                    // org\\aries\\tmp2.g:399:4: '{' ( itemMember )* '}'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal449=(Token)match(input,136,FOLLOW_136_in_itemDeclRest2424); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal449_tree = 
                    (Object)adaptor.create(char_literal449)
                    ;
                    adaptor.addChild(root_0, char_literal449_tree);
                    }

                    // org\\aries\\tmp2.g:399:8: ( itemMember )*
                    loop69:
                    do {
                        int alt69=2;
                        switch ( input.LA(1) ) {
                        case 100:
                            {
                            alt69=1;
                            }
                            break;

                        }

                        switch (alt69) {
                    	case 1 :
                    	    // org\\aries\\tmp2.g:399:9: itemMember
                    	    {
                    	    pushFollow(FOLLOW_itemMember_in_itemDeclRest2427);
                    	    itemMember450=itemMember();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemMember450.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop69;
                        }
                    } while (true);


                    char_literal451=(Token)match(input,140,FOLLOW_140_in_itemDeclRest2431); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal451_tree = 
                    (Object)adaptor.create(char_literal451)
                    ;
                    adaptor.addChild(root_0, char_literal451_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:400:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal452=(Token)match(input,65,FOLLOW_65_in_itemDeclRest2436); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal452_tree = 
                    (Object)adaptor.create(char_literal452)
                    ;
                    adaptor.addChild(root_0, char_literal452_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 63, itemDeclRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemDeclRest"


    public static class itemMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemMember"
    // org\\aries\\tmp2.g:403:1: itemMember : 'index' ^ '(' Identifier ')' ';' ;
    public final tmp2Parser.itemMember_return itemMember() throws RecognitionException {
        tmp2Parser.itemMember_return retval = new tmp2Parser.itemMember_return();
        retval.start = input.LT(1);

        int itemMember_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal453=null;
        Token char_literal454=null;
        Token Identifier455=null;
        Token char_literal456=null;
        Token char_literal457=null;

        Object string_literal453_tree=null;
        Object char_literal454_tree=null;
        Object Identifier455_tree=null;
        Object char_literal456_tree=null;
        Object char_literal457_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }

            // org\\aries\\tmp2.g:404:2: ( 'index' ^ '(' Identifier ')' ';' )
            // org\\aries\\tmp2.g:404:4: 'index' ^ '(' Identifier ')' ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal453=(Token)match(input,100,FOLLOW_100_in_itemMember2447); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal453_tree = 
            (Object)adaptor.create(string_literal453)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal453_tree, root_0);
            }

            char_literal454=(Token)match(input,50,FOLLOW_50_in_itemMember2450); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal454_tree = 
            (Object)adaptor.create(char_literal454)
            ;
            adaptor.addChild(root_0, char_literal454_tree);
            }

            Identifier455=(Token)match(input,Identifier,FOLLOW_Identifier_in_itemMember2452); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier455_tree = 
            (Object)adaptor.create(Identifier455)
            ;
            adaptor.addChild(root_0, Identifier455_tree);
            }

            char_literal456=(Token)match(input,51,FOLLOW_51_in_itemMember2454); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal456_tree = 
            (Object)adaptor.create(char_literal456)
            ;
            adaptor.addChild(root_0, char_literal456_tree);
            }

            char_literal457=(Token)match(input,65,FOLLOW_65_in_itemMember2456); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal457_tree = 
            (Object)adaptor.create(char_literal457)
            ;
            adaptor.addChild(root_0, char_literal457_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 64, itemMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemMember"


    public static class listenDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "listenDecl"
    // org\\aries\\tmp2.g:407:1: listenDecl : 'listen' ^ Identifier ( formalParameters )? listenBody ;
    public final tmp2Parser.listenDecl_return listenDecl() throws RecognitionException {
        tmp2Parser.listenDecl_return retval = new tmp2Parser.listenDecl_return();
        retval.start = input.LT(1);

        int listenDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal458=null;
        Token Identifier459=null;
        tmp2Parser.formalParameters_return formalParameters460 =null;

        tmp2Parser.listenBody_return listenBody461 =null;


        Object string_literal458_tree=null;
        Object Identifier459_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }

            // org\\aries\\tmp2.g:408:2: ( 'listen' ^ Identifier ( formalParameters )? listenBody )
            // org\\aries\\tmp2.g:408:4: 'listen' ^ Identifier ( formalParameters )? listenBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal458=(Token)match(input,106,FOLLOW_106_in_listenDecl2467); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal458_tree = 
            (Object)adaptor.create(string_literal458)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal458_tree, root_0);
            }

            Identifier459=(Token)match(input,Identifier,FOLLOW_Identifier_in_listenDecl2470); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier459_tree = 
            (Object)adaptor.create(Identifier459)
            ;
            adaptor.addChild(root_0, Identifier459_tree);
            }

            // org\\aries\\tmp2.g:408:25: ( formalParameters )?
            int alt71=2;
            switch ( input.LA(1) ) {
                case 50:
                    {
                    alt71=1;
                    }
                    break;
            }

            switch (alt71) {
                case 1 :
                    // org\\aries\\tmp2.g:408:25: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_listenDecl2472);
                    formalParameters460=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters460.getTree());

                    }
                    break;

            }


            pushFollow(FOLLOW_listenBody_in_listenDecl2475);
            listenBody461=listenBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, listenBody461.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 65, listenDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "listenDecl"


    public static class listenBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "listenBody"
    // org\\aries\\tmp2.g:411:1: listenBody : '{' ( listenMember )* '}' ;
    public final tmp2Parser.listenBody_return listenBody() throws RecognitionException {
        tmp2Parser.listenBody_return retval = new tmp2Parser.listenBody_return();
        retval.start = input.LT(1);

        int listenBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal462=null;
        Token char_literal464=null;
        tmp2Parser.listenMember_return listenMember463 =null;


        Object char_literal462_tree=null;
        Object char_literal464_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }

            // org\\aries\\tmp2.g:412:2: ( '{' ( listenMember )* '}' )
            // org\\aries\\tmp2.g:412:4: '{' ( listenMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal462=(Token)match(input,136,FOLLOW_136_in_listenBody2487); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal462_tree = 
            (Object)adaptor.create(char_literal462)
            ;
            adaptor.addChild(root_0, char_literal462_tree);
            }

            // org\\aries\\tmp2.g:412:8: ( listenMember )*
            loop72:
            do {
                int alt72=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 76:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 94:
                case 95:
                case 97:
                case 98:
                case 99:
                case 101:
                case 104:
                case 107:
                case 112:
                case 115:
                case 118:
                case 120:
                case 124:
                case 126:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt72=1;
                    }
                    break;

                }

                switch (alt72) {
            	case 1 :
            	    // org\\aries\\tmp2.g:412:9: listenMember
            	    {
            	    pushFollow(FOLLOW_listenMember_in_listenBody2490);
            	    listenMember463=listenMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenMember463.getTree());

            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);


            char_literal464=(Token)match(input,140,FOLLOW_140_in_listenBody2494); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal464_tree = 
            (Object)adaptor.create(char_literal464)
            ;
            adaptor.addChild(root_0, char_literal464_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 66, listenBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "listenBody"


    public static class listenMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "listenMember"
    // org\\aries\\tmp2.g:415:1: listenMember : ( assignmentDecl | optionDecl | statementDecl );
    public final tmp2Parser.listenMember_return listenMember() throws RecognitionException {
        tmp2Parser.listenMember_return retval = new tmp2Parser.listenMember_return();
        retval.start = input.LT(1);

        int listenMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl465 =null;

        tmp2Parser.optionDecl_return optionDecl466 =null;

        tmp2Parser.statementDecl_return statementDecl467 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }

            // org\\aries\\tmp2.g:416:2: ( assignmentDecl | optionDecl | statementDecl )
            int alt73=3;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt73=1;
                }
                break;
            case 112:
                {
                alt73=2;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt73=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;

            }

            switch (alt73) {
                case 1 :
                    // org\\aries\\tmp2.g:416:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_listenMember2505);
                    assignmentDecl465=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl465.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:417:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_listenMember2510);
                    optionDecl466=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl466.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:418:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_listenMember2515);
                    statementDecl467=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl467.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 67, listenMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "listenMember"


    public static class messageDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "messageDecl"
    // org\\aries\\tmp2.g:422:1: messageDecl : MESSAGE ^ Identifier ( formalParameters )? ':' messageBody ;
    public final tmp2Parser.messageDecl_return messageDecl() throws RecognitionException {
        tmp2Parser.messageDecl_return retval = new tmp2Parser.messageDecl_return();
        retval.start = input.LT(1);

        int messageDecl_StartIndex = input.index();

        Object root_0 = null;

        Token MESSAGE468=null;
        Token Identifier469=null;
        Token char_literal471=null;
        tmp2Parser.formalParameters_return formalParameters470 =null;

        tmp2Parser.messageBody_return messageBody472 =null;


        Object MESSAGE468_tree=null;
        Object Identifier469_tree=null;
        Object char_literal471_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }

            // org\\aries\\tmp2.g:423:2: ( MESSAGE ^ Identifier ( formalParameters )? ':' messageBody )
            // org\\aries\\tmp2.g:423:4: MESSAGE ^ Identifier ( formalParameters )? ':' messageBody
            {
            root_0 = (Object)adaptor.nil();


            MESSAGE468=(Token)match(input,MESSAGE,FOLLOW_MESSAGE_in_messageDecl2527); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MESSAGE468_tree = 
            (Object)adaptor.create(MESSAGE468)
            ;
            root_0 = (Object)adaptor.becomeRoot(MESSAGE468_tree, root_0);
            }

            Identifier469=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageDecl2530); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier469_tree = 
            (Object)adaptor.create(Identifier469)
            ;
            adaptor.addChild(root_0, Identifier469_tree);
            }

            // org\\aries\\tmp2.g:423:24: ( formalParameters )?
            int alt74=2;
            switch ( input.LA(1) ) {
                case 50:
                    {
                    alt74=1;
                    }
                    break;
            }

            switch (alt74) {
                case 1 :
                    // org\\aries\\tmp2.g:423:24: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_messageDecl2532);
                    formalParameters470=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters470.getTree());

                    }
                    break;

            }


            char_literal471=(Token)match(input,64,FOLLOW_64_in_messageDecl2535); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal471_tree = 
            (Object)adaptor.create(char_literal471)
            ;
            adaptor.addChild(root_0, char_literal471_tree);
            }

            pushFollow(FOLLOW_messageBody_in_messageDecl2537);
            messageBody472=messageBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, messageBody472.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 68, messageDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "messageDecl"


    public static class messageBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "messageBody"
    // org\\aries\\tmp2.g:426:1: messageBody : '{' ( messageMember )* '}' ;
    public final tmp2Parser.messageBody_return messageBody() throws RecognitionException {
        tmp2Parser.messageBody_return retval = new tmp2Parser.messageBody_return();
        retval.start = input.LT(1);

        int messageBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal473=null;
        Token char_literal475=null;
        tmp2Parser.messageMember_return messageMember474 =null;


        Object char_literal473_tree=null;
        Object char_literal475_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }

            // org\\aries\\tmp2.g:427:2: ( '{' ( messageMember )* '}' )
            // org\\aries\\tmp2.g:427:4: '{' ( messageMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal473=(Token)match(input,136,FOLLOW_136_in_messageBody2549); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal473_tree = 
            (Object)adaptor.create(char_literal473)
            ;
            adaptor.addChild(root_0, char_literal473_tree);
            }

            // org\\aries\\tmp2.g:427:8: ( messageMember )*
            loop75:
            do {
                int alt75=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 76:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 93:
                case 94:
                case 95:
                case 97:
                case 98:
                case 99:
                case 101:
                case 102:
                case 104:
                case 106:
                case 107:
                case 112:
                case 115:
                case 118:
                case 120:
                case 124:
                case 126:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt75=1;
                    }
                    break;

                }

                switch (alt75) {
            	case 1 :
            	    // org\\aries\\tmp2.g:427:9: messageMember
            	    {
            	    pushFollow(FOLLOW_messageMember_in_messageBody2552);
            	    messageMember474=messageMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, messageMember474.getTree());

            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);


            char_literal475=(Token)match(input,140,FOLLOW_140_in_messageBody2556); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal475_tree = 
            (Object)adaptor.create(char_literal475)
            ;
            adaptor.addChild(root_0, char_literal475_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 69, messageBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "messageBody"


    public static class messageMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "messageMember"
    // org\\aries\\tmp2.g:430:1: messageMember : ( assignmentDecl | optionDecl | executeDecl | invokeDecl | listenDecl | statementDecl );
    public final tmp2Parser.messageMember_return messageMember() throws RecognitionException {
        tmp2Parser.messageMember_return retval = new tmp2Parser.messageMember_return();
        retval.start = input.LT(1);

        int messageMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl476 =null;

        tmp2Parser.optionDecl_return optionDecl477 =null;

        tmp2Parser.executeDecl_return executeDecl478 =null;

        tmp2Parser.invokeDecl_return invokeDecl479 =null;

        tmp2Parser.listenDecl_return listenDecl480 =null;

        tmp2Parser.statementDecl_return statementDecl481 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }

            // org\\aries\\tmp2.g:431:2: ( assignmentDecl | optionDecl | executeDecl | invokeDecl | listenDecl | statementDecl )
            int alt76=6;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt76=1;
                }
                break;
            case 112:
                {
                alt76=2;
                }
                break;
            case 93:
                {
                alt76=3;
                }
                break;
            case 102:
                {
                alt76=4;
                }
                break;
            case 106:
                {
                alt76=5;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt76=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;

            }

            switch (alt76) {
                case 1 :
                    // org\\aries\\tmp2.g:431:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_messageMember2567);
                    assignmentDecl476=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl476.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:432:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_messageMember2572);
                    optionDecl477=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl477.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:433:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_messageMember2577);
                    executeDecl478=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl478.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:434:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_messageMember2582);
                    invokeDecl479=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl479.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:435:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_messageMember2587);
                    listenDecl480=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl480.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:436:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_messageMember2592);
                    statementDecl481=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl481.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 70, messageMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "messageMember"


    public static class methodDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodDecl"
    // org\\aries\\tmp2.g:440:1: methodDecl : qualifiedName ^ formalParametersSignature methodBody ;
    public final tmp2Parser.methodDecl_return methodDecl() throws RecognitionException {
        tmp2Parser.methodDecl_return retval = new tmp2Parser.methodDecl_return();
        retval.start = input.LT(1);

        int methodDecl_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.qualifiedName_return qualifiedName482 =null;

        tmp2Parser.formalParametersSignature_return formalParametersSignature483 =null;

        tmp2Parser.methodBody_return methodBody484 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }

            // org\\aries\\tmp2.g:441:2: ( qualifiedName ^ formalParametersSignature methodBody )
            // org\\aries\\tmp2.g:441:4: qualifiedName ^ formalParametersSignature methodBody
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_methodDecl2605);
            qualifiedName482=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(qualifiedName482.getTree(), root_0);

            pushFollow(FOLLOW_formalParametersSignature_in_methodDecl2608);
            formalParametersSignature483=formalParametersSignature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParametersSignature483.getTree());

            pushFollow(FOLLOW_methodBody_in_methodDecl2610);
            methodBody484=methodBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, methodBody484.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 71, methodDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "methodDecl"


    public static class methodBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodBody"
    // org\\aries\\tmp2.g:444:1: methodBody : '{' ( methodMember )* '}' ;
    public final tmp2Parser.methodBody_return methodBody() throws RecognitionException {
        tmp2Parser.methodBody_return retval = new tmp2Parser.methodBody_return();
        retval.start = input.LT(1);

        int methodBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal485=null;
        Token char_literal487=null;
        tmp2Parser.methodMember_return methodMember486 =null;


        Object char_literal485_tree=null;
        Object char_literal487_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }

            // org\\aries\\tmp2.g:445:2: ( '{' ( methodMember )* '}' )
            // org\\aries\\tmp2.g:445:4: '{' ( methodMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal485=(Token)match(input,136,FOLLOW_136_in_methodBody2621); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal485_tree = 
            (Object)adaptor.create(char_literal485)
            ;
            adaptor.addChild(root_0, char_literal485_tree);
            }

            // org\\aries\\tmp2.g:445:8: ( methodMember )*
            loop77:
            do {
                int alt77=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 93:
                case 94:
                case 95:
                case 97:
                case 101:
                case 102:
                case 106:
                case 107:
                case 115:
                case 118:
                case 120:
                case 124:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt77=1;
                    }
                    break;

                }

                switch (alt77) {
            	case 1 :
            	    // org\\aries\\tmp2.g:445:9: methodMember
            	    {
            	    pushFollow(FOLLOW_methodMember_in_methodBody2624);
            	    methodMember486=methodMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodMember486.getTree());

            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);


            char_literal487=(Token)match(input,140,FOLLOW_140_in_methodBody2628); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal487_tree = 
            (Object)adaptor.create(char_literal487)
            ;
            adaptor.addChild(root_0, char_literal487_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 72, methodBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "methodBody"


    public static class methodMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodMember"
    // org\\aries\\tmp2.g:448:1: methodMember : ( invokeDecl | executeDecl | listenDecl | statementDecl );
    public final tmp2Parser.methodMember_return methodMember() throws RecognitionException {
        tmp2Parser.methodMember_return retval = new tmp2Parser.methodMember_return();
        retval.start = input.LT(1);

        int methodMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.invokeDecl_return invokeDecl488 =null;

        tmp2Parser.executeDecl_return executeDecl489 =null;

        tmp2Parser.listenDecl_return listenDecl490 =null;

        tmp2Parser.statementDecl_return statementDecl491 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }

            // org\\aries\\tmp2.g:449:2: ( invokeDecl | executeDecl | listenDecl | statementDecl )
            int alt78=4;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt78=1;
                }
                break;
            case 93:
                {
                alt78=2;
                }
                break;
            case 106:
                {
                alt78=3;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt78=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;

            }

            switch (alt78) {
                case 1 :
                    // org\\aries\\tmp2.g:449:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_methodMember2639);
                    invokeDecl488=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl488.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:450:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_methodMember2644);
                    executeDecl489=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl489.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:451:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_methodMember2649);
                    listenDecl490=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl490.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:452:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_methodMember2654);
                    statementDecl491=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl491.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 73, methodMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "methodMember"


    public static class optionDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optionDecl"
    // org\\aries\\tmp2.g:456:1: optionDecl : 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody ;
    public final tmp2Parser.optionDecl_return optionDecl() throws RecognitionException {
        tmp2Parser.optionDecl_return retval = new tmp2Parser.optionDecl_return();
        retval.start = input.LT(1);

        int optionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal492=null;
        Token Identifier493=null;
        Token char_literal495=null;
        tmp2Parser.formalParameters_return formalParameters494 =null;

        tmp2Parser.optionBody_return optionBody496 =null;


        Object string_literal492_tree=null;
        Object Identifier493_tree=null;
        Object char_literal495_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }

            // org\\aries\\tmp2.g:457:2: ( 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody )
            // org\\aries\\tmp2.g:457:4: 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal492=(Token)match(input,112,FOLLOW_112_in_optionDecl2666); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal492_tree = 
            (Object)adaptor.create(string_literal492)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal492_tree, root_0);
            }

            // org\\aries\\tmp2.g:457:14: ( Identifier )?
            int alt79=2;
            switch ( input.LA(1) ) {
                case Identifier:
                    {
                    alt79=1;
                    }
                    break;
            }

            switch (alt79) {
                case 1 :
                    // org\\aries\\tmp2.g:457:14: Identifier
                    {
                    Identifier493=(Token)match(input,Identifier,FOLLOW_Identifier_in_optionDecl2669); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier493_tree = 
                    (Object)adaptor.create(Identifier493)
                    ;
                    adaptor.addChild(root_0, Identifier493_tree);
                    }

                    }
                    break;

            }


            // org\\aries\\tmp2.g:457:26: ( formalParameters )?
            int alt80=2;
            switch ( input.LA(1) ) {
                case 50:
                    {
                    alt80=1;
                    }
                    break;
            }

            switch (alt80) {
                case 1 :
                    // org\\aries\\tmp2.g:457:26: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_optionDecl2672);
                    formalParameters494=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters494.getTree());

                    }
                    break;

            }


            char_literal495=(Token)match(input,64,FOLLOW_64_in_optionDecl2675); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal495_tree = 
            (Object)adaptor.create(char_literal495)
            ;
            adaptor.addChild(root_0, char_literal495_tree);
            }

            pushFollow(FOLLOW_optionBody_in_optionDecl2677);
            optionBody496=optionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, optionBody496.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 74, optionDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optionDecl"


    public static class optionBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optionBody"
    // org\\aries\\tmp2.g:460:1: optionBody : '{' ( optionMember )* '}' ;
    public final tmp2Parser.optionBody_return optionBody() throws RecognitionException {
        tmp2Parser.optionBody_return retval = new tmp2Parser.optionBody_return();
        retval.start = input.LT(1);

        int optionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal497=null;
        Token char_literal499=null;
        tmp2Parser.optionMember_return optionMember498 =null;


        Object char_literal497_tree=null;
        Object char_literal499_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }

            // org\\aries\\tmp2.g:461:2: ( '{' ( optionMember )* '}' )
            // org\\aries\\tmp2.g:461:4: '{' ( optionMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal497=(Token)match(input,136,FOLLOW_136_in_optionBody2688); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal497_tree = 
            (Object)adaptor.create(char_literal497)
            ;
            adaptor.addChild(root_0, char_literal497_tree);
            }

            // org\\aries\\tmp2.g:461:8: ( optionMember )*
            loop81:
            do {
                int alt81=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 94:
                case 95:
                case 97:
                case 101:
                case 107:
                case 115:
                case 118:
                case 120:
                case 124:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt81=1;
                    }
                    break;

                }

                switch (alt81) {
            	case 1 :
            	    // org\\aries\\tmp2.g:461:9: optionMember
            	    {
            	    pushFollow(FOLLOW_optionMember_in_optionBody2691);
            	    optionMember498=optionMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionMember498.getTree());

            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);


            char_literal499=(Token)match(input,140,FOLLOW_140_in_optionBody2695); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal499_tree = 
            (Object)adaptor.create(char_literal499)
            ;
            adaptor.addChild(root_0, char_literal499_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 75, optionBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optionBody"


    public static class optionMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optionMember"
    // org\\aries\\tmp2.g:464:1: optionMember : statementDecl ;
    public final tmp2Parser.optionMember_return optionMember() throws RecognitionException {
        tmp2Parser.optionMember_return retval = new tmp2Parser.optionMember_return();
        retval.start = input.LT(1);

        int optionMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.statementDecl_return statementDecl500 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 76) ) { return retval; }

            // org\\aries\\tmp2.g:465:2: ( statementDecl )
            // org\\aries\\tmp2.g:465:4: statementDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_statementDecl_in_optionMember2706);
            statementDecl500=statementDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl500.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 76, optionMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optionMember"


    public static class participantDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "participantDecl"
    // org\\aries\\tmp2.g:470:1: participantDecl : 'participant' ^ Identifier participantBody ;
    public final tmp2Parser.participantDecl_return participantDecl() throws RecognitionException {
        tmp2Parser.participantDecl_return retval = new tmp2Parser.participantDecl_return();
        retval.start = input.LT(1);

        int participantDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal501=null;
        Token Identifier502=null;
        tmp2Parser.participantBody_return participantBody503 =null;


        Object string_literal501_tree=null;
        Object Identifier502_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 77) ) { return retval; }

            // org\\aries\\tmp2.g:471:2: ( 'participant' ^ Identifier participantBody )
            // org\\aries\\tmp2.g:471:4: 'participant' ^ Identifier participantBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal501=(Token)match(input,113,FOLLOW_113_in_participantDecl2721); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal501_tree = 
            (Object)adaptor.create(string_literal501)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal501_tree, root_0);
            }

            Identifier502=(Token)match(input,Identifier,FOLLOW_Identifier_in_participantDecl2724); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier502_tree = 
            (Object)adaptor.create(Identifier502)
            ;
            adaptor.addChild(root_0, Identifier502_tree);
            }

            pushFollow(FOLLOW_participantBody_in_participantDecl2726);
            participantBody503=participantBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, participantBody503.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 77, participantDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "participantDecl"


    public static class participantBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "participantBody"
    // org\\aries\\tmp2.g:474:1: participantBody : '{' ( participantMember )* '}' ;
    public final tmp2Parser.participantBody_return participantBody() throws RecognitionException {
        tmp2Parser.participantBody_return retval = new tmp2Parser.participantBody_return();
        retval.start = input.LT(1);

        int participantBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal504=null;
        Token char_literal506=null;
        tmp2Parser.participantMember_return participantMember505 =null;


        Object char_literal504_tree=null;
        Object char_literal506_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return retval; }

            // org\\aries\\tmp2.g:475:2: ( '{' ( participantMember )* '}' )
            // org\\aries\\tmp2.g:475:4: '{' ( participantMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal504=(Token)match(input,136,FOLLOW_136_in_participantBody2737); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal504_tree = 
            (Object)adaptor.create(char_literal504)
            ;
            adaptor.addChild(root_0, char_literal504_tree);
            }

            // org\\aries\\tmp2.g:475:8: ( participantMember )*
            loop82:
            do {
                int alt82=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                case 76:
                case 82:
                case 98:
                case 99:
                case 104:
                case 114:
                case 117:
                case 122:
                case 126:
                    {
                    alt82=1;
                    }
                    break;

                }

                switch (alt82) {
            	case 1 :
            	    // org\\aries\\tmp2.g:475:9: participantMember
            	    {
            	    pushFollow(FOLLOW_participantMember_in_participantBody2740);
            	    participantMember505=participantMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, participantMember505.getTree());

            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);


            char_literal506=(Token)match(input,140,FOLLOW_140_in_participantBody2744); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal506_tree = 
            (Object)adaptor.create(char_literal506)
            ;
            adaptor.addChild(root_0, char_literal506_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 78, participantBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "participantBody"


    public static class participantMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "participantMember"
    // org\\aries\\tmp2.g:478:1: participantMember : ( assignmentDecl | receiveDecl | cacheDecl | persistDecl | scheduleDecl | methodDecl );
    public final tmp2Parser.participantMember_return participantMember() throws RecognitionException {
        tmp2Parser.participantMember_return retval = new tmp2Parser.participantMember_return();
        retval.start = input.LT(1);

        int participantMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl507 =null;

        tmp2Parser.receiveDecl_return receiveDecl508 =null;

        tmp2Parser.cacheDecl_return cacheDecl509 =null;

        tmp2Parser.persistDecl_return persistDecl510 =null;

        tmp2Parser.scheduleDecl_return scheduleDecl511 =null;

        tmp2Parser.methodDecl_return methodDecl512 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 79) ) { return retval; }

            // org\\aries\\tmp2.g:479:2: ( assignmentDecl | receiveDecl | cacheDecl | persistDecl | scheduleDecl | methodDecl )
            int alt83=6;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt83=1;
                }
                break;
            case 117:
                {
                alt83=2;
                }
                break;
            case 82:
                {
                alt83=3;
                }
                break;
            case 114:
                {
                alt83=4;
                }
                break;
            case 122:
                {
                alt83=5;
                }
                break;
            case EXCEPTION:
            case Identifier:
            case MESSAGE:
                {
                alt83=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;

            }

            switch (alt83) {
                case 1 :
                    // org\\aries\\tmp2.g:479:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_participantMember2756);
                    assignmentDecl507=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl507.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:480:4: receiveDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_receiveDecl_in_participantMember2761);
                    receiveDecl508=receiveDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveDecl508.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:481:4: cacheDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cacheDecl_in_participantMember2766);
                    cacheDecl509=cacheDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheDecl509.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:482:4: persistDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_persistDecl_in_participantMember2771);
                    persistDecl510=persistDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistDecl510.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:483:4: scheduleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_scheduleDecl_in_participantMember2776);
                    scheduleDecl511=scheduleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleDecl511.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:484:4: methodDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_methodDecl_in_participantMember2781);
                    methodDecl512=methodDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodDecl512.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 79, participantMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "participantMember"


    public static class persistDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "persistDecl"
    // org\\aries\\tmp2.g:487:1: persistDecl : 'persist' ^ Identifier persistBody ;
    public final tmp2Parser.persistDecl_return persistDecl() throws RecognitionException {
        tmp2Parser.persistDecl_return retval = new tmp2Parser.persistDecl_return();
        retval.start = input.LT(1);

        int persistDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal513=null;
        Token Identifier514=null;
        tmp2Parser.persistBody_return persistBody515 =null;


        Object string_literal513_tree=null;
        Object Identifier514_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }

            // org\\aries\\tmp2.g:488:2: ( 'persist' ^ Identifier persistBody )
            // org\\aries\\tmp2.g:488:4: 'persist' ^ Identifier persistBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal513=(Token)match(input,114,FOLLOW_114_in_persistDecl2792); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal513_tree = 
            (Object)adaptor.create(string_literal513)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal513_tree, root_0);
            }

            Identifier514=(Token)match(input,Identifier,FOLLOW_Identifier_in_persistDecl2795); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier514_tree = 
            (Object)adaptor.create(Identifier514)
            ;
            adaptor.addChild(root_0, Identifier514_tree);
            }

            pushFollow(FOLLOW_persistBody_in_persistDecl2797);
            persistBody515=persistBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, persistBody515.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 80, persistDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "persistDecl"


    public static class persistBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "persistBody"
    // org\\aries\\tmp2.g:491:1: persistBody : '{' ( persistMember )* '}' ;
    public final tmp2Parser.persistBody_return persistBody() throws RecognitionException {
        tmp2Parser.persistBody_return retval = new tmp2Parser.persistBody_return();
        retval.start = input.LT(1);

        int persistBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal516=null;
        Token char_literal518=null;
        tmp2Parser.persistMember_return persistMember517 =null;


        Object char_literal516_tree=null;
        Object char_literal518_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 81) ) { return retval; }

            // org\\aries\\tmp2.g:492:2: ( '{' ( persistMember )* '}' )
            // org\\aries\\tmp2.g:492:4: '{' ( persistMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal516=(Token)match(input,136,FOLLOW_136_in_persistBody2808); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal516_tree = 
            (Object)adaptor.create(char_literal516)
            ;
            adaptor.addChild(root_0, char_literal516_tree);
            }

            // org\\aries\\tmp2.g:492:8: ( persistMember )*
            loop84:
            do {
                int alt84=2;
                switch ( input.LA(1) ) {
                case 76:
                case 98:
                case 99:
                case 103:
                case 104:
                case 126:
                    {
                    alt84=1;
                    }
                    break;

                }

                switch (alt84) {
            	case 1 :
            	    // org\\aries\\tmp2.g:492:9: persistMember
            	    {
            	    pushFollow(FOLLOW_persistMember_in_persistBody2811);
            	    persistMember517=persistMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistMember517.getTree());

            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);


            char_literal518=(Token)match(input,140,FOLLOW_140_in_persistBody2815); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal518_tree = 
            (Object)adaptor.create(char_literal518)
            ;
            adaptor.addChild(root_0, char_literal518_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 81, persistBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "persistBody"


    public static class persistMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "persistMember"
    // org\\aries\\tmp2.g:495:1: persistMember : ( assignmentDecl | itemsDecl );
    public final tmp2Parser.persistMember_return persistMember() throws RecognitionException {
        tmp2Parser.persistMember_return retval = new tmp2Parser.persistMember_return();
        retval.start = input.LT(1);

        int persistMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl519 =null;

        tmp2Parser.itemsDecl_return itemsDecl520 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 82) ) { return retval; }

            // org\\aries\\tmp2.g:496:2: ( assignmentDecl | itemsDecl )
            int alt85=2;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt85=1;
                }
                break;
            case 103:
                {
                alt85=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;

            }

            switch (alt85) {
                case 1 :
                    // org\\aries\\tmp2.g:496:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_persistMember2826);
                    assignmentDecl519=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl519.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:497:4: itemsDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_itemsDecl_in_persistMember2831);
                    itemsDecl520=itemsDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsDecl520.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 82, persistMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "persistMember"


    public static class protocolDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "protocolDecl"
    // org\\aries\\tmp2.g:500:1: protocolDecl : 'protocol' ^ Identifier protocolBody ;
    public final tmp2Parser.protocolDecl_return protocolDecl() throws RecognitionException {
        tmp2Parser.protocolDecl_return retval = new tmp2Parser.protocolDecl_return();
        retval.start = input.LT(1);

        int protocolDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal521=null;
        Token Identifier522=null;
        tmp2Parser.protocolBody_return protocolBody523 =null;


        Object string_literal521_tree=null;
        Object Identifier522_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 83) ) { return retval; }

            // org\\aries\\tmp2.g:501:2: ( 'protocol' ^ Identifier protocolBody )
            // org\\aries\\tmp2.g:501:4: 'protocol' ^ Identifier protocolBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal521=(Token)match(input,116,FOLLOW_116_in_protocolDecl2842); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal521_tree = 
            (Object)adaptor.create(string_literal521)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal521_tree, root_0);
            }

            Identifier522=(Token)match(input,Identifier,FOLLOW_Identifier_in_protocolDecl2845); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier522_tree = 
            (Object)adaptor.create(Identifier522)
            ;
            adaptor.addChild(root_0, Identifier522_tree);
            }

            pushFollow(FOLLOW_protocolBody_in_protocolDecl2847);
            protocolBody523=protocolBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, protocolBody523.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 83, protocolDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "protocolDecl"


    public static class protocolBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "protocolBody"
    // org\\aries\\tmp2.g:504:1: protocolBody : '{' ( protocolMember )* '}' ;
    public final tmp2Parser.protocolBody_return protocolBody() throws RecognitionException {
        tmp2Parser.protocolBody_return retval = new tmp2Parser.protocolBody_return();
        retval.start = input.LT(1);

        int protocolBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal524=null;
        Token char_literal526=null;
        tmp2Parser.protocolMember_return protocolMember525 =null;


        Object char_literal524_tree=null;
        Object char_literal526_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 84) ) { return retval; }

            // org\\aries\\tmp2.g:505:2: ( '{' ( protocolMember )* '}' )
            // org\\aries\\tmp2.g:505:4: '{' ( protocolMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal524=(Token)match(input,136,FOLLOW_136_in_protocolBody2858); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal524_tree = 
            (Object)adaptor.create(char_literal524)
            ;
            adaptor.addChild(root_0, char_literal524_tree);
            }

            // org\\aries\\tmp2.g:505:8: ( protocolMember )*
            loop86:
            do {
                int alt86=2;
                switch ( input.LA(1) ) {
                case 76:
                case 82:
                case 83:
                case 96:
                case 98:
                case 99:
                case 104:
                case 113:
                case 114:
                case 121:
                case 122:
                case 126:
                    {
                    alt86=1;
                    }
                    break;

                }

                switch (alt86) {
            	case 1 :
            	    // org\\aries\\tmp2.g:505:8: protocolMember
            	    {
            	    pushFollow(FOLLOW_protocolMember_in_protocolBody2860);
            	    protocolMember525=protocolMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, protocolMember525.getTree());

            	    }
            	    break;

            	default :
            	    break loop86;
                }
            } while (true);


            char_literal526=(Token)match(input,140,FOLLOW_140_in_protocolBody2863); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal526_tree = 
            (Object)adaptor.create(char_literal526)
            ;
            adaptor.addChild(root_0, char_literal526_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 84, protocolBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "protocolBody"


    public static class protocolMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "protocolMember"
    // org\\aries\\tmp2.g:508:1: protocolMember : ( assignmentDecl | roleDecl | groupDecl | channelDecl | participantDecl | cacheDecl | persistDecl | scheduleDecl );
    public final tmp2Parser.protocolMember_return protocolMember() throws RecognitionException {
        tmp2Parser.protocolMember_return retval = new tmp2Parser.protocolMember_return();
        retval.start = input.LT(1);

        int protocolMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl527 =null;

        tmp2Parser.roleDecl_return roleDecl528 =null;

        tmp2Parser.groupDecl_return groupDecl529 =null;

        tmp2Parser.channelDecl_return channelDecl530 =null;

        tmp2Parser.participantDecl_return participantDecl531 =null;

        tmp2Parser.cacheDecl_return cacheDecl532 =null;

        tmp2Parser.persistDecl_return persistDecl533 =null;

        tmp2Parser.scheduleDecl_return scheduleDecl534 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 85) ) { return retval; }

            // org\\aries\\tmp2.g:509:2: ( assignmentDecl | roleDecl | groupDecl | channelDecl | participantDecl | cacheDecl | persistDecl | scheduleDecl )
            int alt87=8;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt87=1;
                }
                break;
            case 121:
                {
                alt87=2;
                }
                break;
            case 96:
                {
                alt87=3;
                }
                break;
            case 83:
                {
                alt87=4;
                }
                break;
            case 113:
                {
                alt87=5;
                }
                break;
            case 82:
                {
                alt87=6;
                }
                break;
            case 114:
                {
                alt87=7;
                }
                break;
            case 122:
                {
                alt87=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;

            }

            switch (alt87) {
                case 1 :
                    // org\\aries\\tmp2.g:509:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_protocolMember2875);
                    assignmentDecl527=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl527.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:510:4: roleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_roleDecl_in_protocolMember2880);
                    roleDecl528=roleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, roleDecl528.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:511:4: groupDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_groupDecl_in_protocolMember2885);
                    groupDecl529=groupDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, groupDecl529.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:512:4: channelDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_channelDecl_in_protocolMember2890);
                    channelDecl530=channelDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, channelDecl530.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:513:4: participantDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_participantDecl_in_protocolMember2895);
                    participantDecl531=participantDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, participantDecl531.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:514:4: cacheDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cacheDecl_in_protocolMember2900);
                    cacheDecl532=cacheDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheDecl532.getTree());

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp2.g:515:4: persistDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_persistDecl_in_protocolMember2905);
                    persistDecl533=persistDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistDecl533.getTree());

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp2.g:516:4: scheduleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_scheduleDecl_in_protocolMember2910);
                    scheduleDecl534=scheduleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleDecl534.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 85, protocolMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "protocolMember"


    public static class receiveDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveDecl"
    // org\\aries\\tmp2.g:519:1: receiveDecl : 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' ) ;
    public final tmp2Parser.receiveDecl_return receiveDecl() throws RecognitionException {
        tmp2Parser.receiveDecl_return retval = new tmp2Parser.receiveDecl_return();
        retval.start = input.LT(1);

        int receiveDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal535=null;
        Token Identifier536=null;
        Token char_literal540=null;
        tmp2Parser.formalParametersSignature_return formalParametersSignature537 =null;

        tmp2Parser.throwsBody_return throwsBody538 =null;

        tmp2Parser.receiveBody_return receiveBody539 =null;


        Object string_literal535_tree=null;
        Object Identifier536_tree=null;
        Object char_literal540_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 86) ) { return retval; }

            // org\\aries\\tmp2.g:520:2: ( 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' ) )
            // org\\aries\\tmp2.g:520:4: 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' )
            {
            root_0 = (Object)adaptor.nil();


            string_literal535=(Token)match(input,117,FOLLOW_117_in_receiveDecl2921); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal535_tree = 
            (Object)adaptor.create(string_literal535)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal535_tree, root_0);
            }

            Identifier536=(Token)match(input,Identifier,FOLLOW_Identifier_in_receiveDecl2924); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier536_tree = 
            (Object)adaptor.create(Identifier536)
            ;
            adaptor.addChild(root_0, Identifier536_tree);
            }

            pushFollow(FOLLOW_formalParametersSignature_in_receiveDecl2926);
            formalParametersSignature537=formalParametersSignature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParametersSignature537.getTree());

            // org\\aries\\tmp2.g:520:52: ( throwsBody )?
            int alt88=2;
            switch ( input.LA(1) ) {
                case 132:
                    {
                    alt88=1;
                    }
                    break;
            }

            switch (alt88) {
                case 1 :
                    // org\\aries\\tmp2.g:520:52: throwsBody
                    {
                    pushFollow(FOLLOW_throwsBody_in_receiveDecl2928);
                    throwsBody538=throwsBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsBody538.getTree());

                    }
                    break;

            }


            // org\\aries\\tmp2.g:520:64: ( receiveBody | ';' )
            int alt89=2;
            switch ( input.LA(1) ) {
            case 136:
                {
                alt89=1;
                }
                break;
            case 65:
                {
                alt89=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;

            }

            switch (alt89) {
                case 1 :
                    // org\\aries\\tmp2.g:520:65: receiveBody
                    {
                    pushFollow(FOLLOW_receiveBody_in_receiveDecl2932);
                    receiveBody539=receiveBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveBody539.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:520:79: ';'
                    {
                    char_literal540=(Token)match(input,65,FOLLOW_65_in_receiveDecl2936); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal540_tree = 
                    (Object)adaptor.create(char_literal540)
                    ;
                    adaptor.addChild(root_0, char_literal540_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 86, receiveDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "receiveDecl"


    public static class receiveBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveBody"
    // org\\aries\\tmp2.g:523:1: receiveBody : '{' ( receiveMember )* '}' ;
    public final tmp2Parser.receiveBody_return receiveBody() throws RecognitionException {
        tmp2Parser.receiveBody_return retval = new tmp2Parser.receiveBody_return();
        retval.start = input.LT(1);

        int receiveBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal541=null;
        Token char_literal543=null;
        tmp2Parser.receiveMember_return receiveMember542 =null;


        Object char_literal541_tree=null;
        Object char_literal543_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 87) ) { return retval; }

            // org\\aries\\tmp2.g:524:2: ( '{' ( receiveMember )* '}' )
            // org\\aries\\tmp2.g:524:4: '{' ( receiveMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal541=(Token)match(input,136,FOLLOW_136_in_receiveBody2948); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal541_tree = 
            (Object)adaptor.create(char_literal541)
            ;
            adaptor.addChild(root_0, char_literal541_tree);
            }

            // org\\aries\\tmp2.g:524:8: ( receiveMember )*
            loop90:
            do {
                int alt90=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 76:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 93:
                case 94:
                case 95:
                case 97:
                case 98:
                case 99:
                case 101:
                case 102:
                case 104:
                case 106:
                case 107:
                case 112:
                case 115:
                case 118:
                case 120:
                case 124:
                case 126:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt90=1;
                    }
                    break;

                }

                switch (alt90) {
            	case 1 :
            	    // org\\aries\\tmp2.g:524:9: receiveMember
            	    {
            	    pushFollow(FOLLOW_receiveMember_in_receiveBody2951);
            	    receiveMember542=receiveMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveMember542.getTree());

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);


            char_literal543=(Token)match(input,140,FOLLOW_140_in_receiveBody2955); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal543_tree = 
            (Object)adaptor.create(char_literal543)
            ;
            adaptor.addChild(root_0, char_literal543_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 87, receiveBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "receiveBody"


    public static class throwsBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsBody"
    // org\\aries\\tmp2.g:527:1: throwsBody : 'throws' ^ throwsList ;
    public final tmp2Parser.throwsBody_return throwsBody() throws RecognitionException {
        tmp2Parser.throwsBody_return retval = new tmp2Parser.throwsBody_return();
        retval.start = input.LT(1);

        int throwsBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal544=null;
        tmp2Parser.throwsList_return throwsList545 =null;


        Object string_literal544_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 88) ) { return retval; }

            // org\\aries\\tmp2.g:528:2: ( 'throws' ^ throwsList )
            // org\\aries\\tmp2.g:528:4: 'throws' ^ throwsList
            {
            root_0 = (Object)adaptor.nil();


            string_literal544=(Token)match(input,132,FOLLOW_132_in_throwsBody2966); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal544_tree = 
            (Object)adaptor.create(string_literal544)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal544_tree, root_0);
            }

            pushFollow(FOLLOW_throwsList_in_throwsBody2969);
            throwsList545=throwsList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsList545.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 88, throwsBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsBody"


    public static class throwsList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsList"
    // org\\aries\\tmp2.g:531:1: throwsList : ( throwsListDecls )? ;
    public final tmp2Parser.throwsList_return throwsList() throws RecognitionException {
        tmp2Parser.throwsList_return retval = new tmp2Parser.throwsList_return();
        retval.start = input.LT(1);

        int throwsList_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.throwsListDecls_return throwsListDecls546 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 89) ) { return retval; }

            // org\\aries\\tmp2.g:532:2: ( ( throwsListDecls )? )
            // org\\aries\\tmp2.g:532:4: ( throwsListDecls )?
            {
            root_0 = (Object)adaptor.nil();


            // org\\aries\\tmp2.g:532:4: ( throwsListDecls )?
            int alt91=2;
            switch ( input.LA(1) ) {
                case Identifier:
                    {
                    alt91=1;
                    }
                    break;
            }

            switch (alt91) {
                case 1 :
                    // org\\aries\\tmp2.g:532:4: throwsListDecls
                    {
                    pushFollow(FOLLOW_throwsListDecls_in_throwsList2981);
                    throwsListDecls546=throwsListDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDecls546.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 89, throwsList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsList"


    public static class throwsListDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsListDecls"
    // org\\aries\\tmp2.g:535:1: throwsListDecls : throwsListDeclsRest ;
    public final tmp2Parser.throwsListDecls_return throwsListDecls() throws RecognitionException {
        tmp2Parser.throwsListDecls_return retval = new tmp2Parser.throwsListDecls_return();
        retval.start = input.LT(1);

        int throwsListDecls_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.throwsListDeclsRest_return throwsListDeclsRest547 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 90) ) { return retval; }

            // org\\aries\\tmp2.g:536:2: ( throwsListDeclsRest )
            // org\\aries\\tmp2.g:536:4: throwsListDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_throwsListDeclsRest_in_throwsListDecls2994);
            throwsListDeclsRest547=throwsListDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDeclsRest547.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 90, throwsListDecls_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsListDecls"


    public static class throwsListDeclsRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsListDeclsRest"
    // org\\aries\\tmp2.g:539:1: throwsListDeclsRest : Identifier ( ',' throwsListDeclsRest )? ;
    public final tmp2Parser.throwsListDeclsRest_return throwsListDeclsRest() throws RecognitionException {
        tmp2Parser.throwsListDeclsRest_return retval = new tmp2Parser.throwsListDeclsRest_return();
        retval.start = input.LT(1);

        int throwsListDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier548=null;
        Token char_literal549=null;
        tmp2Parser.throwsListDeclsRest_return throwsListDeclsRest550 =null;


        Object Identifier548_tree=null;
        Object char_literal549_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 91) ) { return retval; }

            // org\\aries\\tmp2.g:540:2: ( Identifier ( ',' throwsListDeclsRest )? )
            // org\\aries\\tmp2.g:540:4: Identifier ( ',' throwsListDeclsRest )?
            {
            root_0 = (Object)adaptor.nil();


            Identifier548=(Token)match(input,Identifier,FOLLOW_Identifier_in_throwsListDeclsRest3006); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier548_tree = 
            (Object)adaptor.create(Identifier548)
            ;
            adaptor.addChild(root_0, Identifier548_tree);
            }

            // org\\aries\\tmp2.g:540:15: ( ',' throwsListDeclsRest )?
            int alt92=2;
            switch ( input.LA(1) ) {
                case 57:
                    {
                    alt92=1;
                    }
                    break;
            }

            switch (alt92) {
                case 1 :
                    // org\\aries\\tmp2.g:540:16: ',' throwsListDeclsRest
                    {
                    char_literal549=(Token)match(input,57,FOLLOW_57_in_throwsListDeclsRest3009); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal549_tree = 
                    (Object)adaptor.create(char_literal549)
                    ;
                    adaptor.addChild(root_0, char_literal549_tree);
                    }

                    pushFollow(FOLLOW_throwsListDeclsRest_in_throwsListDeclsRest3011);
                    throwsListDeclsRest550=throwsListDeclsRest();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDeclsRest550.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 91, throwsListDeclsRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsListDeclsRest"


    public static class receiveMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveMember"
    // org\\aries\\tmp2.g:543:1: receiveMember : ( assignmentDecl | optionDecl | executeDecl | listenDecl | invokeDecl | statementDecl );
    public final tmp2Parser.receiveMember_return receiveMember() throws RecognitionException {
        tmp2Parser.receiveMember_return retval = new tmp2Parser.receiveMember_return();
        retval.start = input.LT(1);

        int receiveMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl551 =null;

        tmp2Parser.optionDecl_return optionDecl552 =null;

        tmp2Parser.executeDecl_return executeDecl553 =null;

        tmp2Parser.listenDecl_return listenDecl554 =null;

        tmp2Parser.invokeDecl_return invokeDecl555 =null;

        tmp2Parser.statementDecl_return statementDecl556 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 92) ) { return retval; }

            // org\\aries\\tmp2.g:544:2: ( assignmentDecl | optionDecl | executeDecl | listenDecl | invokeDecl | statementDecl )
            int alt93=6;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt93=1;
                }
                break;
            case 112:
                {
                alt93=2;
                }
                break;
            case 93:
                {
                alt93=3;
                }
                break;
            case 106:
                {
                alt93=4;
                }
                break;
            case 102:
                {
                alt93=5;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt93=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;

            }

            switch (alt93) {
                case 1 :
                    // org\\aries\\tmp2.g:544:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_receiveMember3025);
                    assignmentDecl551=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl551.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:545:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_receiveMember3030);
                    optionDecl552=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl552.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:546:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_receiveMember3035);
                    executeDecl553=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl553.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:547:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_receiveMember3040);
                    listenDecl554=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl554.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:548:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_receiveMember3045);
                    invokeDecl555=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl555.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:549:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_receiveMember3050);
                    statementDecl556=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl556.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 92, receiveMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "receiveMember"


    public static class roleDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "roleDecl"
    // org\\aries\\tmp2.g:553:1: roleDecl : 'role' ^ Identifier roleBody ;
    public final tmp2Parser.roleDecl_return roleDecl() throws RecognitionException {
        tmp2Parser.roleDecl_return retval = new tmp2Parser.roleDecl_return();
        retval.start = input.LT(1);

        int roleDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal557=null;
        Token Identifier558=null;
        tmp2Parser.roleBody_return roleBody559 =null;


        Object string_literal557_tree=null;
        Object Identifier558_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 93) ) { return retval; }

            // org\\aries\\tmp2.g:554:2: ( 'role' ^ Identifier roleBody )
            // org\\aries\\tmp2.g:554:4: 'role' ^ Identifier roleBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal557=(Token)match(input,121,FOLLOW_121_in_roleDecl3062); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal557_tree = 
            (Object)adaptor.create(string_literal557)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal557_tree, root_0);
            }

            Identifier558=(Token)match(input,Identifier,FOLLOW_Identifier_in_roleDecl3065); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier558_tree = 
            (Object)adaptor.create(Identifier558)
            ;
            adaptor.addChild(root_0, Identifier558_tree);
            }

            pushFollow(FOLLOW_roleBody_in_roleDecl3067);
            roleBody559=roleBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, roleBody559.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 93, roleDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "roleDecl"


    public static class roleBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "roleBody"
    // org\\aries\\tmp2.g:557:1: roleBody : '{' ( roleMember )* '}' ;
    public final tmp2Parser.roleBody_return roleBody() throws RecognitionException {
        tmp2Parser.roleBody_return retval = new tmp2Parser.roleBody_return();
        retval.start = input.LT(1);

        int roleBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal560=null;
        Token char_literal562=null;
        tmp2Parser.roleMember_return roleMember561 =null;


        Object char_literal560_tree=null;
        Object char_literal562_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 94) ) { return retval; }

            // org\\aries\\tmp2.g:558:2: ( '{' ( roleMember )* '}' )
            // org\\aries\\tmp2.g:558:4: '{' ( roleMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal560=(Token)match(input,136,FOLLOW_136_in_roleBody3078); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal560_tree = 
            (Object)adaptor.create(char_literal560)
            ;
            adaptor.addChild(root_0, char_literal560_tree);
            }

            // org\\aries\\tmp2.g:558:8: ( roleMember )*
            loop94:
            do {
                int alt94=2;
                switch ( input.LA(1) ) {
                case 65:
                    {
                    alt94=1;
                    }
                    break;

                }

                switch (alt94) {
            	case 1 :
            	    // org\\aries\\tmp2.g:558:9: roleMember
            	    {
            	    pushFollow(FOLLOW_roleMember_in_roleBody3081);
            	    roleMember561=roleMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, roleMember561.getTree());

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            char_literal562=(Token)match(input,140,FOLLOW_140_in_roleBody3085); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal562_tree = 
            (Object)adaptor.create(char_literal562)
            ;
            adaptor.addChild(root_0, char_literal562_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 94, roleBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "roleBody"


    public static class roleMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "roleMember"
    // org\\aries\\tmp2.g:561:1: roleMember : ';' ;
    public final tmp2Parser.roleMember_return roleMember() throws RecognitionException {
        tmp2Parser.roleMember_return retval = new tmp2Parser.roleMember_return();
        retval.start = input.LT(1);

        int roleMember_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal563=null;

        Object char_literal563_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 95) ) { return retval; }

            // org\\aries\\tmp2.g:562:2: ( ';' )
            // org\\aries\\tmp2.g:562:4: ';'
            {
            root_0 = (Object)adaptor.nil();


            char_literal563=(Token)match(input,65,FOLLOW_65_in_roleMember3096); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal563_tree = 
            (Object)adaptor.create(char_literal563)
            ;
            adaptor.addChild(root_0, char_literal563_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 95, roleMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "roleMember"


    public static class scheduleDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "scheduleDecl"
    // org\\aries\\tmp2.g:565:1: scheduleDecl : 'schedule' ^ Identifier scheduleBody ;
    public final tmp2Parser.scheduleDecl_return scheduleDecl() throws RecognitionException {
        tmp2Parser.scheduleDecl_return retval = new tmp2Parser.scheduleDecl_return();
        retval.start = input.LT(1);

        int scheduleDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal564=null;
        Token Identifier565=null;
        tmp2Parser.scheduleBody_return scheduleBody566 =null;


        Object string_literal564_tree=null;
        Object Identifier565_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 96) ) { return retval; }

            // org\\aries\\tmp2.g:566:2: ( 'schedule' ^ Identifier scheduleBody )
            // org\\aries\\tmp2.g:566:4: 'schedule' ^ Identifier scheduleBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal564=(Token)match(input,122,FOLLOW_122_in_scheduleDecl3107); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal564_tree = 
            (Object)adaptor.create(string_literal564)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal564_tree, root_0);
            }

            Identifier565=(Token)match(input,Identifier,FOLLOW_Identifier_in_scheduleDecl3110); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier565_tree = 
            (Object)adaptor.create(Identifier565)
            ;
            adaptor.addChild(root_0, Identifier565_tree);
            }

            pushFollow(FOLLOW_scheduleBody_in_scheduleDecl3112);
            scheduleBody566=scheduleBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleBody566.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 96, scheduleDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "scheduleDecl"


    public static class scheduleBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "scheduleBody"
    // org\\aries\\tmp2.g:569:1: scheduleBody : '{' ( scheduleMember )* '}' ;
    public final tmp2Parser.scheduleBody_return scheduleBody() throws RecognitionException {
        tmp2Parser.scheduleBody_return retval = new tmp2Parser.scheduleBody_return();
        retval.start = input.LT(1);

        int scheduleBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal567=null;
        Token char_literal569=null;
        tmp2Parser.scheduleMember_return scheduleMember568 =null;


        Object char_literal567_tree=null;
        Object char_literal569_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 97) ) { return retval; }

            // org\\aries\\tmp2.g:570:2: ( '{' ( scheduleMember )* '}' )
            // org\\aries\\tmp2.g:570:4: '{' ( scheduleMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal567=(Token)match(input,136,FOLLOW_136_in_scheduleBody3123); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal567_tree = 
            (Object)adaptor.create(char_literal567)
            ;
            adaptor.addChild(root_0, char_literal567_tree);
            }

            // org\\aries\\tmp2.g:570:8: ( scheduleMember )*
            loop95:
            do {
                int alt95=2;
                switch ( input.LA(1) ) {
                case 65:
                case 76:
                case 92:
                case 98:
                case 99:
                case 102:
                case 104:
                case 115:
                case 117:
                case 118:
                case 124:
                case 126:
                case 131:
                    {
                    alt95=1;
                    }
                    break;

                }

                switch (alt95) {
            	case 1 :
            	    // org\\aries\\tmp2.g:570:9: scheduleMember
            	    {
            	    pushFollow(FOLLOW_scheduleMember_in_scheduleBody3126);
            	    scheduleMember568=scheduleMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleMember568.getTree());

            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);


            char_literal569=(Token)match(input,140,FOLLOW_140_in_scheduleBody3130); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal569_tree = 
            (Object)adaptor.create(char_literal569)
            ;
            adaptor.addChild(root_0, char_literal569_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 97, scheduleBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "scheduleBody"


    public static class scheduleMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "scheduleMember"
    // org\\aries\\tmp2.g:573:1: scheduleMember : ( assignmentDecl | receiveDecl | invokeDecl | schedulableCommandDecl );
    public final tmp2Parser.scheduleMember_return scheduleMember() throws RecognitionException {
        tmp2Parser.scheduleMember_return retval = new tmp2Parser.scheduleMember_return();
        retval.start = input.LT(1);

        int scheduleMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.assignmentDecl_return assignmentDecl570 =null;

        tmp2Parser.receiveDecl_return receiveDecl571 =null;

        tmp2Parser.invokeDecl_return invokeDecl572 =null;

        tmp2Parser.schedulableCommandDecl_return schedulableCommandDecl573 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 98) ) { return retval; }

            // org\\aries\\tmp2.g:574:2: ( assignmentDecl | receiveDecl | invokeDecl | schedulableCommandDecl )
            int alt96=4;
            switch ( input.LA(1) ) {
            case 76:
            case 98:
            case 99:
            case 104:
            case 126:
                {
                alt96=1;
                }
                break;
            case 117:
                {
                alt96=2;
                }
                break;
            case 102:
                {
                alt96=3;
                }
                break;
            case 65:
            case 92:
            case 115:
            case 118:
            case 124:
            case 131:
                {
                alt96=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;

            }

            switch (alt96) {
                case 1 :
                    // org\\aries\\tmp2.g:574:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_scheduleMember3142);
                    assignmentDecl570=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl570.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:575:4: receiveDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_receiveDecl_in_scheduleMember3147);
                    receiveDecl571=receiveDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveDecl571.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:576:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_scheduleMember3152);
                    invokeDecl572=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl572.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:577:4: schedulableCommandDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_schedulableCommandDecl_in_scheduleMember3157);
                    schedulableCommandDecl573=schedulableCommandDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, schedulableCommandDecl573.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 98, scheduleMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "scheduleMember"


    public static class schedulableCommandDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "schedulableCommandDecl"
    // org\\aries\\tmp2.g:581:1: schedulableCommandDecl : ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | 'throw' ^ Identifier ( formalParameters )? ';' | ';' );
    public final tmp2Parser.schedulableCommandDecl_return schedulableCommandDecl() throws RecognitionException {
        tmp2Parser.schedulableCommandDecl_return retval = new tmp2Parser.schedulableCommandDecl_return();
        retval.start = input.LT(1);

        int schedulableCommandDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal574=null;
        Token char_literal575=null;
        Token string_literal576=null;
        Token Identifier577=null;
        Token char_literal579=null;
        Token string_literal580=null;
        Token Identifier581=null;
        Token char_literal583=null;
        Token string_literal584=null;
        Token char_literal587=null;
        Token string_literal588=null;
        Token Identifier589=null;
        Token char_literal591=null;
        Token char_literal592=null;
        tmp2Parser.formalParameters_return formalParameters578 =null;

        tmp2Parser.formalParameters_return formalParameters582 =null;

        tmp2Parser.qualifiedName_return qualifiedName585 =null;

        tmp2Parser.formalParameters_return formalParameters586 =null;

        tmp2Parser.formalParameters_return formalParameters590 =null;


        Object string_literal574_tree=null;
        Object char_literal575_tree=null;
        Object string_literal576_tree=null;
        Object Identifier577_tree=null;
        Object char_literal579_tree=null;
        Object string_literal580_tree=null;
        Object Identifier581_tree=null;
        Object char_literal583_tree=null;
        Object string_literal584_tree=null;
        Object char_literal587_tree=null;
        Object string_literal588_tree=null;
        Object Identifier589_tree=null;
        Object char_literal591_tree=null;
        Object char_literal592_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 99) ) { return retval; }

            // org\\aries\\tmp2.g:582:2: ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | 'throw' ^ Identifier ( formalParameters )? ';' | ';' )
            int alt101=6;
            switch ( input.LA(1) ) {
            case 92:
                {
                alt101=1;
                }
                break;
            case 115:
                {
                alt101=2;
                }
                break;
            case 118:
                {
                alt101=3;
                }
                break;
            case 124:
                {
                alt101=4;
                }
                break;
            case 131:
                {
                alt101=5;
                }
                break;
            case 65:
                {
                alt101=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;

            }

            switch (alt101) {
                case 1 :
                    // org\\aries\\tmp2.g:582:4: 'end' ^ ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal574=(Token)match(input,92,FOLLOW_92_in_schedulableCommandDecl3170); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal574_tree = 
                    (Object)adaptor.create(string_literal574)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal574_tree, root_0);
                    }

                    char_literal575=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3173); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal575_tree = 
                    (Object)adaptor.create(char_literal575)
                    ;
                    adaptor.addChild(root_0, char_literal575_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:583:4: 'post' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal576=(Token)match(input,115,FOLLOW_115_in_schedulableCommandDecl3178); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal576_tree = 
                    (Object)adaptor.create(string_literal576)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal576_tree, root_0);
                    }

                    Identifier577=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3181); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier577_tree = 
                    (Object)adaptor.create(Identifier577)
                    ;
                    adaptor.addChild(root_0, Identifier577_tree);
                    }

                    // org\\aries\\tmp2.g:583:23: ( formalParameters )?
                    int alt97=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt97=1;
                            }
                            break;
                    }

                    switch (alt97) {
                        case 1 :
                            // org\\aries\\tmp2.g:583:23: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3183);
                            formalParameters578=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters578.getTree());

                            }
                            break;

                    }


                    char_literal579=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3186); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal579_tree = 
                    (Object)adaptor.create(char_literal579)
                    ;
                    adaptor.addChild(root_0, char_literal579_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp2.g:584:4: 'reply' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal580=(Token)match(input,118,FOLLOW_118_in_schedulableCommandDecl3191); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal580_tree = 
                    (Object)adaptor.create(string_literal580)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal580_tree, root_0);
                    }

                    Identifier581=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3194); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier581_tree = 
                    (Object)adaptor.create(Identifier581)
                    ;
                    adaptor.addChild(root_0, Identifier581_tree);
                    }

                    // org\\aries\\tmp2.g:584:24: ( formalParameters )?
                    int alt98=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt98=1;
                            }
                            break;
                    }

                    switch (alt98) {
                        case 1 :
                            // org\\aries\\tmp2.g:584:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3196);
                            formalParameters582=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters582.getTree());

                            }
                            break;

                    }


                    char_literal583=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3199); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal583_tree = 
                    (Object)adaptor.create(char_literal583)
                    ;
                    adaptor.addChild(root_0, char_literal583_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp2.g:585:4: 'send' ^ qualifiedName ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal584=(Token)match(input,124,FOLLOW_124_in_schedulableCommandDecl3204); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal584_tree = 
                    (Object)adaptor.create(string_literal584)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal584_tree, root_0);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_schedulableCommandDecl3207);
                    qualifiedName585=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName585.getTree());

                    // org\\aries\\tmp2.g:585:26: ( formalParameters )?
                    int alt99=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt99=1;
                            }
                            break;
                    }

                    switch (alt99) {
                        case 1 :
                            // org\\aries\\tmp2.g:585:26: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3209);
                            formalParameters586=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters586.getTree());

                            }
                            break;

                    }


                    char_literal587=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3212); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal587_tree = 
                    (Object)adaptor.create(char_literal587)
                    ;
                    adaptor.addChild(root_0, char_literal587_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp2.g:586:4: 'throw' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal588=(Token)match(input,131,FOLLOW_131_in_schedulableCommandDecl3217); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal588_tree = 
                    (Object)adaptor.create(string_literal588)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal588_tree, root_0);
                    }

                    Identifier589=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3220); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier589_tree = 
                    (Object)adaptor.create(Identifier589)
                    ;
                    adaptor.addChild(root_0, Identifier589_tree);
                    }

                    // org\\aries\\tmp2.g:586:24: ( formalParameters )?
                    int alt100=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt100=1;
                            }
                            break;
                    }

                    switch (alt100) {
                        case 1 :
                            // org\\aries\\tmp2.g:586:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3222);
                            formalParameters590=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters590.getTree());

                            }
                            break;

                    }


                    char_literal591=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3225); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal591_tree = 
                    (Object)adaptor.create(char_literal591)
                    ;
                    adaptor.addChild(root_0, char_literal591_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp2.g:587:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal592=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3230); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal592_tree = 
                    (Object)adaptor.create(char_literal592)
                    ;
                    adaptor.addChild(root_0, char_literal592_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 99, schedulableCommandDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "schedulableCommandDecl"


    public static class timeoutDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeoutDecl"
    // org\\aries\\tmp2.g:590:1: timeoutDecl : 'timeout' ^ ':' timeoutBody ;
    public final tmp2Parser.timeoutDecl_return timeoutDecl() throws RecognitionException {
        tmp2Parser.timeoutDecl_return retval = new tmp2Parser.timeoutDecl_return();
        retval.start = input.LT(1);

        int timeoutDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal593=null;
        Token char_literal594=null;
        tmp2Parser.timeoutBody_return timeoutBody595 =null;


        Object string_literal593_tree=null;
        Object char_literal594_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 100) ) { return retval; }

            // org\\aries\\tmp2.g:591:2: ( 'timeout' ^ ':' timeoutBody )
            // org\\aries\\tmp2.g:591:4: 'timeout' ^ ':' timeoutBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal593=(Token)match(input,133,FOLLOW_133_in_timeoutDecl3242); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal593_tree = 
            (Object)adaptor.create(string_literal593)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal593_tree, root_0);
            }

            char_literal594=(Token)match(input,64,FOLLOW_64_in_timeoutDecl3245); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal594_tree = 
            (Object)adaptor.create(char_literal594)
            ;
            adaptor.addChild(root_0, char_literal594_tree);
            }

            pushFollow(FOLLOW_timeoutBody_in_timeoutDecl3247);
            timeoutBody595=timeoutBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutBody595.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 100, timeoutDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeoutDecl"


    public static class timeoutBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeoutBody"
    // org\\aries\\tmp2.g:594:1: timeoutBody : '{' ( timeoutMember )* '}' ;
    public final tmp2Parser.timeoutBody_return timeoutBody() throws RecognitionException {
        tmp2Parser.timeoutBody_return retval = new tmp2Parser.timeoutBody_return();
        retval.start = input.LT(1);

        int timeoutBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal596=null;
        Token char_literal598=null;
        tmp2Parser.timeoutMember_return timeoutMember597 =null;


        Object char_literal596_tree=null;
        Object char_literal598_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 101) ) { return retval; }

            // org\\aries\\tmp2.g:595:2: ( '{' ( timeoutMember )* '}' )
            // org\\aries\\tmp2.g:595:4: '{' ( timeoutMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal596=(Token)match(input,136,FOLLOW_136_in_timeoutBody3259); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal596_tree = 
            (Object)adaptor.create(char_literal596)
            ;
            adaptor.addChild(root_0, char_literal596_tree);
            }

            // org\\aries\\tmp2.g:595:8: ( timeoutMember )*
            loop102:
            do {
                int alt102=2;
                switch ( input.LA(1) ) {
                case CHARLITERAL:
                case DoubleLiteral:
                case EXCEPTION:
                case FALSE:
                case FloatLiteral:
                case Identifier:
                case IntegerLiteral:
                case MESSAGE:
                case NULL:
                case STRINGLITERAL:
                case TRUE:
                case 43:
                case 50:
                case 54:
                case 55:
                case 58:
                case 59:
                case 78:
                case 80:
                case 81:
                case 84:
                case 87:
                case 88:
                case 90:
                case 92:
                case 94:
                case 95:
                case 97:
                case 101:
                case 107:
                case 112:
                case 115:
                case 118:
                case 120:
                case 124:
                case 127:
                case 131:
                case 135:
                case 136:
                case 141:
                    {
                    alt102=1;
                    }
                    break;

                }

                switch (alt102) {
            	case 1 :
            	    // org\\aries\\tmp2.g:595:9: timeoutMember
            	    {
            	    pushFollow(FOLLOW_timeoutMember_in_timeoutBody3262);
            	    timeoutMember597=timeoutMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutMember597.getTree());

            	    }
            	    break;

            	default :
            	    break loop102;
                }
            } while (true);


            char_literal598=(Token)match(input,140,FOLLOW_140_in_timeoutBody3266); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal598_tree = 
            (Object)adaptor.create(char_literal598)
            ;
            adaptor.addChild(root_0, char_literal598_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 101, timeoutBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeoutBody"


    public static class timeoutMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeoutMember"
    // org\\aries\\tmp2.g:598:1: timeoutMember : ( optionDecl | statementDecl );
    public final tmp2Parser.timeoutMember_return timeoutMember() throws RecognitionException {
        tmp2Parser.timeoutMember_return retval = new tmp2Parser.timeoutMember_return();
        retval.start = input.LT(1);

        int timeoutMember_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.optionDecl_return optionDecl599 =null;

        tmp2Parser.statementDecl_return statementDecl600 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 102) ) { return retval; }

            // org\\aries\\tmp2.g:599:2: ( optionDecl | statementDecl )
            int alt103=2;
            switch ( input.LA(1) ) {
            case 112:
                {
                alt103=1;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
            case EXCEPTION:
            case FALSE:
            case FloatLiteral:
            case Identifier:
            case IntegerLiteral:
            case MESSAGE:
            case NULL:
            case STRINGLITERAL:
            case TRUE:
            case 43:
            case 50:
            case 54:
            case 55:
            case 58:
            case 59:
            case 78:
            case 80:
            case 81:
            case 84:
            case 87:
            case 88:
            case 90:
            case 92:
            case 94:
            case 95:
            case 97:
            case 101:
            case 107:
            case 115:
            case 118:
            case 120:
            case 124:
            case 127:
            case 131:
            case 135:
            case 136:
            case 141:
                {
                alt103=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;

            }

            switch (alt103) {
                case 1 :
                    // org\\aries\\tmp2.g:599:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_timeoutMember3277);
                    optionDecl599=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl599.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:600:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_timeoutMember3282);
                    statementDecl600=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl600.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 102, timeoutMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeoutMember"


    public static class typeRef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typeRef"
    // org\\aries\\tmp2.g:606:1: typeRef : Identifier ':' Identifier ;
    public final tmp2Parser.typeRef_return typeRef() throws RecognitionException {
        tmp2Parser.typeRef_return retval = new tmp2Parser.typeRef_return();
        retval.start = input.LT(1);

        int typeRef_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier601=null;
        Token char_literal602=null;
        Token Identifier603=null;

        Object Identifier601_tree=null;
        Object char_literal602_tree=null;
        Object Identifier603_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 103) ) { return retval; }

            // org\\aries\\tmp2.g:607:2: ( Identifier ':' Identifier )
            // org\\aries\\tmp2.g:607:4: Identifier ':' Identifier
            {
            root_0 = (Object)adaptor.nil();


            Identifier601=(Token)match(input,Identifier,FOLLOW_Identifier_in_typeRef3296); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier601_tree = 
            (Object)adaptor.create(Identifier601)
            ;
            adaptor.addChild(root_0, Identifier601_tree);
            }

            char_literal602=(Token)match(input,64,FOLLOW_64_in_typeRef3298); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal602_tree = 
            (Object)adaptor.create(char_literal602)
            ;
            adaptor.addChild(root_0, char_literal602_tree);
            }

            Identifier603=(Token)match(input,Identifier,FOLLOW_Identifier_in_typeRef3300); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier603_tree = 
            (Object)adaptor.create(Identifier603)
            ;
            adaptor.addChild(root_0, Identifier603_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 103, typeRef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typeRef"


    public static class formalParameters_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameters"
    // org\\aries\\tmp2.g:622:1: formalParameters : '(' ( formalParameterDecls )? ')' ;
    public final tmp2Parser.formalParameters_return formalParameters() throws RecognitionException {
        tmp2Parser.formalParameters_return retval = new tmp2Parser.formalParameters_return();
        retval.start = input.LT(1);

        int formalParameters_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal604=null;
        Token char_literal606=null;
        tmp2Parser.formalParameterDecls_return formalParameterDecls605 =null;


        Object char_literal604_tree=null;
        Object char_literal606_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 104) ) { return retval; }

            // org\\aries\\tmp2.g:623:2: ( '(' ( formalParameterDecls )? ')' )
            // org\\aries\\tmp2.g:623:4: '(' ( formalParameterDecls )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal604=(Token)match(input,50,FOLLOW_50_in_formalParameters3324); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal604_tree = 
            (Object)adaptor.create(char_literal604)
            ;
            adaptor.addChild(root_0, char_literal604_tree);
            }

            // org\\aries\\tmp2.g:623:8: ( formalParameterDecls )?
            int alt104=2;
            switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                    {
                    alt104=1;
                    }
                    break;
            }

            switch (alt104) {
                case 1 :
                    // org\\aries\\tmp2.g:623:8: formalParameterDecls
                    {
                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameters3326);
                    formalParameterDecls605=formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDecls605.getTree());

                    }
                    break;

            }


            char_literal606=(Token)match(input,51,FOLLOW_51_in_formalParameters3329); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal606_tree = 
            (Object)adaptor.create(char_literal606)
            ;
            adaptor.addChild(root_0, char_literal606_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 104, formalParameters_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameters"


    public static class formalParameterDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterDecls"
    // org\\aries\\tmp2.g:626:1: formalParameterDecls : formalParameterDeclsRest ;
    public final tmp2Parser.formalParameterDecls_return formalParameterDecls() throws RecognitionException {
        tmp2Parser.formalParameterDecls_return retval = new tmp2Parser.formalParameterDecls_return();
        retval.start = input.LT(1);

        int formalParameterDecls_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.formalParameterDeclsRest_return formalParameterDeclsRest607 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 105) ) { return retval; }

            // org\\aries\\tmp2.g:627:2: ( formalParameterDeclsRest )
            // org\\aries\\tmp2.g:627:4: formalParameterDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameterDeclsRest_in_formalParameterDecls3341);
            formalParameterDeclsRest607=formalParameterDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDeclsRest607.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 105, formalParameterDecls_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterDecls"


    public static class formalParameterDeclsRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterDeclsRest"
    // org\\aries\\tmp2.g:630:1: formalParameterDeclsRest : qualifiedName ( ',' formalParameterDecls )? ;
    public final tmp2Parser.formalParameterDeclsRest_return formalParameterDeclsRest() throws RecognitionException {
        tmp2Parser.formalParameterDeclsRest_return retval = new tmp2Parser.formalParameterDeclsRest_return();
        retval.start = input.LT(1);

        int formalParameterDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal609=null;
        tmp2Parser.qualifiedName_return qualifiedName608 =null;

        tmp2Parser.formalParameterDecls_return formalParameterDecls610 =null;


        Object char_literal609_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 106) ) { return retval; }

            // org\\aries\\tmp2.g:631:2: ( qualifiedName ( ',' formalParameterDecls )? )
            // org\\aries\\tmp2.g:631:4: qualifiedName ( ',' formalParameterDecls )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_formalParameterDeclsRest3353);
            qualifiedName608=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName608.getTree());

            // org\\aries\\tmp2.g:631:18: ( ',' formalParameterDecls )?
            int alt105=2;
            switch ( input.LA(1) ) {
                case 57:
                    {
                    alt105=1;
                    }
                    break;
            }

            switch (alt105) {
                case 1 :
                    // org\\aries\\tmp2.g:631:19: ',' formalParameterDecls
                    {
                    char_literal609=(Token)match(input,57,FOLLOW_57_in_formalParameterDeclsRest3356); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal609_tree = 
                    (Object)adaptor.create(char_literal609)
                    ;
                    adaptor.addChild(root_0, char_literal609_tree);
                    }

                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameterDeclsRest3358);
                    formalParameterDecls610=formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDecls610.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 106, formalParameterDeclsRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterDeclsRest"


    public static class formalParametersSignature_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParametersSignature"
    // org\\aries\\tmp2.g:634:1: formalParametersSignature : '(' ( formalParameterSignatureDecls )? ')' ;
    public final tmp2Parser.formalParametersSignature_return formalParametersSignature() throws RecognitionException {
        tmp2Parser.formalParametersSignature_return retval = new tmp2Parser.formalParametersSignature_return();
        retval.start = input.LT(1);

        int formalParametersSignature_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal611=null;
        Token char_literal613=null;
        tmp2Parser.formalParameterSignatureDecls_return formalParameterSignatureDecls612 =null;


        Object char_literal611_tree=null;
        Object char_literal613_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 107) ) { return retval; }

            // org\\aries\\tmp2.g:635:2: ( '(' ( formalParameterSignatureDecls )? ')' )
            // org\\aries\\tmp2.g:635:4: '(' ( formalParameterSignatureDecls )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal611=(Token)match(input,50,FOLLOW_50_in_formalParametersSignature3371); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal611_tree = 
            (Object)adaptor.create(char_literal611)
            ;
            adaptor.addChild(root_0, char_literal611_tree);
            }

            // org\\aries\\tmp2.g:635:8: ( formalParameterSignatureDecls )?
            int alt106=2;
            switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                case 78:
                case 81:
                case 84:
                case 90:
                case 94:
                case 101:
                case 107:
                case 127:
                    {
                    alt106=1;
                    }
                    break;
            }

            switch (alt106) {
                case 1 :
                    // org\\aries\\tmp2.g:635:8: formalParameterSignatureDecls
                    {
                    pushFollow(FOLLOW_formalParameterSignatureDecls_in_formalParametersSignature3373);
                    formalParameterSignatureDecls612=formalParameterSignatureDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDecls612.getTree());

                    }
                    break;

            }


            char_literal613=(Token)match(input,51,FOLLOW_51_in_formalParametersSignature3376); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal613_tree = 
            (Object)adaptor.create(char_literal613)
            ;
            adaptor.addChild(root_0, char_literal613_tree);
            }

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 107, formalParametersSignature_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParametersSignature"


    public static class formalParameterSignatureDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterSignatureDecls"
    // org\\aries\\tmp2.g:638:1: formalParameterSignatureDecls : formalParameterSignatureDeclsRest ;
    public final tmp2Parser.formalParameterSignatureDecls_return formalParameterSignatureDecls() throws RecognitionException {
        tmp2Parser.formalParameterSignatureDecls_return retval = new tmp2Parser.formalParameterSignatureDecls_return();
        retval.start = input.LT(1);

        int formalParameterSignatureDecls_StartIndex = input.index();

        Object root_0 = null;

        tmp2Parser.formalParameterSignatureDeclsRest_return formalParameterSignatureDeclsRest614 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 108) ) { return retval; }

            // org\\aries\\tmp2.g:639:2: ( formalParameterSignatureDeclsRest )
            // org\\aries\\tmp2.g:639:4: formalParameterSignatureDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameterSignatureDeclsRest_in_formalParameterSignatureDecls3388);
            formalParameterSignatureDeclsRest614=formalParameterSignatureDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDeclsRest614.getTree());

            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 108, formalParameterSignatureDecls_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterSignatureDecls"


    public static class formalParameterSignatureDeclsRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterSignatureDeclsRest"
    // org\\aries\\tmp2.g:642:1: formalParameterSignatureDeclsRest : type Identifier ( ',' formalParameterSignatureDecls )? ;
    public final tmp2Parser.formalParameterSignatureDeclsRest_return formalParameterSignatureDeclsRest() throws RecognitionException {
        tmp2Parser.formalParameterSignatureDeclsRest_return retval = new tmp2Parser.formalParameterSignatureDeclsRest_return();
        retval.start = input.LT(1);

        int formalParameterSignatureDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier616=null;
        Token char_literal617=null;
        tmp2Parser.type_return type615 =null;

        tmp2Parser.formalParameterSignatureDecls_return formalParameterSignatureDecls618 =null;


        Object Identifier616_tree=null;
        Object char_literal617_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 109) ) { return retval; }

            // org\\aries\\tmp2.g:643:2: ( type Identifier ( ',' formalParameterSignatureDecls )? )
            // org\\aries\\tmp2.g:643:4: type Identifier ( ',' formalParameterSignatureDecls )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_formalParameterSignatureDeclsRest3400);
            type615=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type615.getTree());

            Identifier616=(Token)match(input,Identifier,FOLLOW_Identifier_in_formalParameterSignatureDeclsRest3402); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier616_tree = 
            (Object)adaptor.create(Identifier616)
            ;
            adaptor.addChild(root_0, Identifier616_tree);
            }

            // org\\aries\\tmp2.g:643:20: ( ',' formalParameterSignatureDecls )?
            int alt107=2;
            switch ( input.LA(1) ) {
                case 57:
                    {
                    alt107=1;
                    }
                    break;
            }

            switch (alt107) {
                case 1 :
                    // org\\aries\\tmp2.g:643:21: ',' formalParameterSignatureDecls
                    {
                    char_literal617=(Token)match(input,57,FOLLOW_57_in_formalParameterSignatureDeclsRest3405); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal617_tree = 
                    (Object)adaptor.create(char_literal617)
                    ;
                    adaptor.addChild(root_0, char_literal617_tree);
                    }

                    pushFollow(FOLLOW_formalParameterSignatureDecls_in_formalParameterSignatureDeclsRest3407);
                    formalParameterSignatureDecls618=formalParameterSignatureDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDecls618.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 109, formalParameterSignatureDeclsRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterSignatureDeclsRest"


    public static class qualifiedNameList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "qualifiedNameList"
    // org\\aries\\tmp2.g:646:1: qualifiedNameList : qualifiedName ( ',' qualifiedName )* ;
    public final tmp2Parser.qualifiedNameList_return qualifiedNameList() throws RecognitionException {
        tmp2Parser.qualifiedNameList_return retval = new tmp2Parser.qualifiedNameList_return();
        retval.start = input.LT(1);

        int qualifiedNameList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal620=null;
        tmp2Parser.qualifiedName_return qualifiedName619 =null;

        tmp2Parser.qualifiedName_return qualifiedName621 =null;


        Object char_literal620_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 110) ) { return retval; }

            // org\\aries\\tmp2.g:647:2: ( qualifiedName ( ',' qualifiedName )* )
            // org\\aries\\tmp2.g:647:4: qualifiedName ( ',' qualifiedName )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList3420);
            qualifiedName619=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName619.getTree());

            // org\\aries\\tmp2.g:647:18: ( ',' qualifiedName )*
            loop108:
            do {
                int alt108=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt108=1;
                    }
                    break;

                }

                switch (alt108) {
            	case 1 :
            	    // org\\aries\\tmp2.g:647:19: ',' qualifiedName
            	    {
            	    char_literal620=(Token)match(input,57,FOLLOW_57_in_qualifiedNameList3423); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal620_tree = 
            	    (Object)adaptor.create(char_literal620)
            	    ;
            	    adaptor.addChild(root_0, char_literal620_tree);
            	    }

            	    pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList3425);
            	    qualifiedName621=qualifiedName();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName621.getTree());

            	    }
            	    break;

            	default :
            	    break loop108;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 110, qualifiedNameList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "qualifiedNameList"


    public static class qualifiedName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "qualifiedName"
    // org\\aries\\tmp2.g:650:1: qualifiedName : ( ( identifier '.' )=> identifier '.' qualifiedName | identifier );
    public final tmp2Parser.qualifiedName_return qualifiedName() throws RecognitionException {
        tmp2Parser.qualifiedName_return retval = new tmp2Parser.qualifiedName_return();
        retval.start = input.LT(1);

        int qualifiedName_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal623=null;
        tmp2Parser.identifier_return identifier622 =null;

        tmp2Parser.qualifiedName_return qualifiedName624 =null;

        tmp2Parser.identifier_return identifier625 =null;


        Object char_literal623_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 111) ) { return retval; }

            // org\\aries\\tmp2.g:651:2: ( ( identifier '.' )=> identifier '.' qualifiedName | identifier )
            int alt109=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA109_1 = input.LA(2);

                if ( (synpred10_tmp2()) ) {
                    alt109=1;
                }
                else if ( (true) ) {
                    alt109=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 109, 1, input);

                    throw nvae;

                }
                }
                break;
            case EXCEPTION:
            case MESSAGE:
                {
                int LA109_2 = input.LA(2);

                if ( (synpred10_tmp2()) ) {
                    alt109=1;
                }
                else if ( (true) ) {
                    alt109=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 109, 2, input);

                    throw nvae;

                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;

            }

            switch (alt109) {
                case 1 :
                    // org\\aries\\tmp2.g:651:4: ( identifier '.' )=> identifier '.' qualifiedName
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_identifier_in_qualifiedName3446);
                    identifier622=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier622.getTree());

                    char_literal623=(Token)match(input,61,FOLLOW_61_in_qualifiedName3448); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal623_tree = 
                    (Object)adaptor.create(char_literal623)
                    ;
                    adaptor.addChild(root_0, char_literal623_tree);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_qualifiedName3450);
                    qualifiedName624=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName624.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:652:4: identifier
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_identifier_in_qualifiedName3455);
                    identifier625=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier625.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 111, qualifiedName_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "qualifiedName"


    public static class identifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "identifier"
    // org\\aries\\tmp2.g:657:1: identifier : ( Identifier | keyword );
    public final tmp2Parser.identifier_return identifier() throws RecognitionException {
        tmp2Parser.identifier_return retval = new tmp2Parser.identifier_return();
        retval.start = input.LT(1);

        int identifier_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier626=null;
        tmp2Parser.keyword_return keyword627 =null;


        Object Identifier626_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 112) ) { return retval; }

            // org\\aries\\tmp2.g:658:2: ( Identifier | keyword )
            int alt110=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                alt110=1;
                }
                break;
            case EXCEPTION:
            case MESSAGE:
                {
                alt110=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;

            }

            switch (alt110) {
                case 1 :
                    // org\\aries\\tmp2.g:658:4: Identifier
                    {
                    root_0 = (Object)adaptor.nil();


                    Identifier626=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifier3469); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier626_tree = 
                    (Object)adaptor.create(Identifier626)
                    ;
                    adaptor.addChild(root_0, Identifier626_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp2.g:659:4: keyword
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_keyword_in_identifier3474);
                    keyword627=keyword();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, keyword627.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 112, identifier_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "identifier"


    public static class keyword_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "keyword"
    // org\\aries\\tmp2.g:662:1: keyword : ( 'exception' | 'message' );
    public final tmp2Parser.keyword_return keyword() throws RecognitionException {
        tmp2Parser.keyword_return retval = new tmp2Parser.keyword_return();
        retval.start = input.LT(1);

        int keyword_StartIndex = input.index();

        Object root_0 = null;

        Token set628=null;

        Object set628_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 113) ) { return retval; }

            // org\\aries\\tmp2.g:663:2: ( 'exception' | 'message' )
            // org\\aries\\tmp2.g:
            {
            root_0 = (Object)adaptor.nil();


            set628=(Token)input.LT(1);

            if ( input.LA(1)==EXCEPTION||input.LA(1)==MESSAGE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set628)
                );
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
            if ( state.backtracking>0 ) { memoize(input, 113, keyword_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "keyword"

    // $ANTLR start synpred1_tmp2
    public final void synpred1_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:123:48: ( 'else' )
        // org\\aries\\tmp2.g:123:49: 'else'
        {
        match(input,91,FOLLOW_91_in_synpred1_tmp2793); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_tmp2

    // $ANTLR start synpred2_tmp2
    public final void synpred2_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:256:4: ( qualifiedName '(' ')' )
        // org\\aries\\tmp2.g:256:5: qualifiedName '(' ')'
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred2_tmp21601);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,50,FOLLOW_50_in_synpred2_tmp21603); if (state.failed) return ;

        match(input,51,FOLLOW_51_in_synpred2_tmp21605); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_tmp2

    // $ANTLR start synpred3_tmp2
    public final void synpred3_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:257:4: ( qualifiedName '(' )
        // org\\aries\\tmp2.g:257:5: qualifiedName '('
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred3_tmp21634);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,50,FOLLOW_50_in_synpred3_tmp21636); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_tmp2

    // $ANTLR start synpred4_tmp2
    public final void synpred4_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:258:4: ( qualifiedName '[' )
        // org\\aries\\tmp2.g:258:5: qualifiedName '['
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred4_tmp21669);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,71,FOLLOW_71_in_synpred4_tmp21671); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_tmp2

    // $ANTLR start synpred5_tmp2
    public final void synpred5_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:259:4: ( type Identifier '=' )
        // org\\aries\\tmp2.g:259:5: type Identifier '='
        {
        pushFollow(FOLLOW_type_in_synpred5_tmp21704);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input,Identifier,FOLLOW_Identifier_in_synpred5_tmp21706); if (state.failed) return ;

        match(input,67,FOLLOW_67_in_synpred5_tmp21708); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_tmp2

    // $ANTLR start synpred6_tmp2
    public final void synpred6_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:260:4: ( qualifiedName '.' )
        // org\\aries\\tmp2.g:260:5: qualifiedName '.'
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred6_tmp21731);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,61,FOLLOW_61_in_synpred6_tmp21733); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_tmp2

    // $ANTLR start synpred7_tmp2
    public final void synpred7_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:266:4: ( '(' )
        // org\\aries\\tmp2.g:266:5: '('
        {
        match(input,50,FOLLOW_50_in_synpred7_tmp21786); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_tmp2

    // $ANTLR start synpred8_tmp2
    public final void synpred8_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:267:4: ( '.' )
        // org\\aries\\tmp2.g:267:5: '.'
        {
        match(input,61,FOLLOW_61_in_synpred8_tmp21797); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred8_tmp2

    // $ANTLR start synpred9_tmp2
    public final void synpred9_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:268:4: ( '[' )
        // org\\aries\\tmp2.g:268:5: '['
        {
        match(input,71,FOLLOW_71_in_synpred9_tmp21812); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_tmp2

    // $ANTLR start synpred10_tmp2
    public final void synpred10_tmp2_fragment() throws RecognitionException {
        // org\\aries\\tmp2.g:651:4: ( identifier '.' )
        // org\\aries\\tmp2.g:651:5: identifier '.'
        {
        pushFollow(FOLLOW_identifier_in_synpred10_tmp23439);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        match(input,61,FOLLOW_61_in_synpred10_tmp23441); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred10_tmp2

    // Delegated rules

    public final boolean synpred3_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_tmp2() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_tmp2_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_importDecl_in_compilationUnit114 = new BitSet(new long[]{0x0000000000000000L,0x0010000400000000L});
    public static final BitSet FOLLOW_protocolDecl_in_compilationUnit117 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_EOF_in_compilationUnit120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_assignmentDecl136 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl138 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl140 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_assignmentDecl153 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl155 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_assignmentDecl157 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl159 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_assignmentDecl167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_assignmentDecl170 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl172 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl174 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl176 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl184 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_assignmentDecl187 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl189 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_assignmentDecl191 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl193 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl201 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_assignmentDecl204 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl206 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl208 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl218 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_105_in_assignmentDecl221 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl223 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_LevelType_in_assignmentDecl225 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl235 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_assignmentDecl238 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl240 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IntegerLiteral_in_assignmentDecl242 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl252 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_assignmentDecl255 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl257 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IntegerLiteral_in_assignmentDecl259 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl261 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl269 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_111_in_assignmentDecl272 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl274 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl276 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl286 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_assignmentDecl289 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl291 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl293 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl303 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_119_in_assignmentDecl306 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl308 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_assignmentDecl310 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl320 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_assignmentDecl323 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl325 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl327 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl329 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_assignmentDecl337 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_assignmentDecl340 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl342 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl344 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl346 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl354 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_123_in_assignmentDecl357 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl359 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_ScopeType_in_assignmentDecl361 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl363 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl371 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_assignmentDecl374 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl376 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl378 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl380 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl388 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_assignmentDecl391 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl393 = new BitSet(new long[]{0x0000002000008000L});
    public static final BitSet FOLLOW_set_in_assignmentDecl395 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl403 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl411 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_133_in_assignmentDecl414 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl416 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_TimeoutLiteral_in_assignmentDecl418 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_assignmentDecl428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_134_in_assignmentDecl431 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl433 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_TransactionType_in_assignmentDecl435 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl437 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_assignmentDecl445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_134_in_assignmentDecl448 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl450 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl452 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_assignmentDecl462 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_111_in_assignmentDecl465 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl467 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl469 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_assignmentDecl479 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_111_in_assignmentDecl482 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl484 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl486 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl488 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_branchDecl502 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000001L});
    public static final BitSet FOLLOW_Identifier_in_branchDecl505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_branchDecl508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_branchBody_in_branchDecl510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_branchBody523 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91490C22D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_branchMember_in_branchBody526 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91490C22D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_branchBody530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_branchMember541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_branchMember547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_branchMember552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_cacheDecl565 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_cacheDecl568 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_cacheBody_in_cacheDecl570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_cacheBody582 = new BitSet(new long[]{0x0000000000000000L,0x4000018C00001000L,0x0000000000001000L});
    public static final BitSet FOLLOW_cacheMember_in_cacheBody585 = new BitSet(new long[]{0x0000000000000000L,0x4000018C00001000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_cacheBody589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_cacheMember600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemsDecl_in_cacheMember605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_channelDecl616 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelDecl619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_136_in_channelDecl621 = new BitSet(new long[]{0x0000000000000000L,0x4000000000001002L,0x0000000000001000L});
    public static final BitSet FOLLOW_channelBody_in_channelDecl624 = new BitSet(new long[]{0x0000000000000000L,0x4000000000001002L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_channelDecl628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody639 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_channelBody641 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody643 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody645 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody647 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody654 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_125_in_channelBody656 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody658 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody660 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody662 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody669 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_channelBody671 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody673 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody675 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody677 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_channelBody684 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_channelBody686 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody688 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody690 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody692 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_commandDecl710 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_commandDecl718 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_commandDecl721 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl723 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_commandDecl731 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_commandDecl734 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl736 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_commandDecl744 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_commandDecl747 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl749 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_statementDecl765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_statementDecl780 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl783 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl785 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl787 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000002188L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl789 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statementDecl798 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000002188L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_statementDecl807 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl810 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124002L});
    public static final BitSet FOLLOW_variableDeclaration_in_statementDecl813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl817 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124002L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl820 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl824 = new BitSet(new long[]{0x0CCC0824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionList_in_statementDecl827 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl831 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000002188L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_statementDecl838 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl841 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl843 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl845 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000002188L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_statementDecl852 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000002188L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl855 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_135_in_statementDecl857 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl859 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl861 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl863 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_statementDecl870 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124002L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_131_in_statementDecl883 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl886 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_statementDecl893 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_statementDecl897 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_statementDecl906 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_statementDecl910 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl919 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_commandDecl_in_statementDecl926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_statementBlock939 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_statementMember_in_statementBlock942 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_statementBlock946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_statementMember958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variableDeclaration970 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclaration972 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_71_in_variableDeclaration975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_variableDeclaration977 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_67_in_variableDeclaration981 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002100L});
    public static final BitSet FOLLOW_variableInitializer_in_variableDeclaration983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayInitializer_in_variableInitializer1016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_variableInitializer1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_arrayInitializer1033 = new BitSet(new long[]{0x0EC40824C0C18920L,0x8000082044124000L,0x0000000000003100L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1036 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_57_in_arrayInitializer1039 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002100L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1041 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_57_in_arrayInitializer1049 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_arrayInitializer1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_expressionDecl1065 = new BitSet(new long[]{0x9122400000000002L,0x000000000000042CL,0x0000000000000400L});
    public static final BitSet FOLLOW_assignmentOperator_in_expressionDecl1068 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionDecl1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionList1099 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_expressionList1102 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionList1104 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_assignmentOperator1123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_assignmentOperator1128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_assignmentOperator1133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_assignmentOperator1138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_assignmentOperator1143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_assignmentOperator1148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_assignmentOperator1153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_assignmentOperator1158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_assignmentOperator1163 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_assignmentOperator1165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1172 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1174 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1176 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1183 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1185 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalExpression1198 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_conditionalExpression1201 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_conditionalExpression1203 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_conditionalExpression1205 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_conditionalExpression_in_conditionalExpression1207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1221 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_139_in_conditionalOrExpression1224 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1226 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1240 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_conditionalAndExpression1243 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1245 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1259 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_137_in_inclusiveOrExpression1262 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1264 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression1278 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_exclusiveOrExpression1281 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression1283 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression1297 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_andExpression1300 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression1302 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1316 = new BitSet(new long[]{0x0000100000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_equalityExpression1320 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1328 = new BitSet(new long[]{0x0000100000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression1342 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpression1345 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression1347 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_relationalOp1361 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_relationalOp1363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_relationalOp1368 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_relationalOp1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_relationalOp1375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_relationalOp1380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression1392 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_shiftOp_in_shiftExpression1395 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression1397 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_shiftOp1411 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_shiftOp1413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_shiftOp1418 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_shiftOp1427 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1441 = new BitSet(new long[]{0x0440000000000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1445 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1453 = new BitSet(new long[]{0x0440000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1467 = new BitSet(new long[]{0x4010200000000002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1471 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1483 = new BitSet(new long[]{0x4010200000000002L});
    public static final BitSet FOLLOW_54_in_unaryExpression1499 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_unaryExpression1506 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_unaryExpression1513 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_unaryExpression1520 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression1527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_unaryExpressionNotPlusMinus1539 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_unaryExpressionNotPlusMinus1546 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_unaryExpressionNotPlusMinus1553 = new BitSet(new long[]{0x2884000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_selector_in_unaryExpressionNotPlusMinus1556 = new BitSet(new long[]{0x2884000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_50_in_primary1579 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_primary1581 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1611 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_primary1613 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1642 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_primary1644 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionList_in_primary1646 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1677 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_primary1679 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_primary1681 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_primary1683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_primary1713 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_primary1715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primary1764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_selector1791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_selector1802 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_selector1804 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_arguments_in_selector1806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_selector1817 = new BitSet(new long[]{0x0CC40824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionDecl_in_selector1819 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_selector1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type1834 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_type1837 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_type1839 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_qualifiedName_in_type1861 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000084L});
    public static final BitSet FOLLOW_typeArguments_in_type1863 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_type1867 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_type1869 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_typeList1901 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_typeList1904 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124000L});
    public static final BitSet FOLLOW_type_in_typeList1906 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_66_in_typeArguments1920 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124000L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1922 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_57_in_typeArguments1925 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124000L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1927 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_typeArguments1931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeArgument1943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_arguments2003 = new BitSet(new long[]{0x0CCC0824C0C18920L,0x8000082044124000L,0x0000000000002000L});
    public static final BitSet FOLLOW_expressionList_in_arguments2006 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_arguments2010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXCEPTION_in_exceptionDecl2072 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_exceptionDecl2075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_exceptionBody_in_exceptionDecl2077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_exceptionBody2088 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91490822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_exceptionMember_in_exceptionBody2091 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91490822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_exceptionBody2095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_exceptionMember2106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_exceptionMember2111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_executeDecl2123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_executeBody_in_executeDecl2126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_executeDecl2131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_130_in_executeDecl2134 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_executeDecl2136 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParameters_in_executeDecl2138 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_executeBody_in_executeDecl2140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_executeDeclRest2152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_executeBody_in_executeDeclRest2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_executeDeclRest2159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_executeBody2171 = new BitSet(new long[]{0x0000000000000800L,0x4000010C00009000L,0x0000000000001020L});
    public static final BitSet FOLLOW_executeMember_in_executeBody2174 = new BitSet(new long[]{0x0000000000000800L,0x4000010C00009000L,0x0000000000001020L});
    public static final BitSet FOLLOW_140_in_executeBody2178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_executeMember2190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_branchDecl_in_executeMember2195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_executeMember2200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_executeMember2205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_groupDecl2216 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_groupDecl2219 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_groupBody_in_groupDecl2221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_groupBody2232 = new BitSet(new long[]{0x0000000000000000L,0x4000010C00001000L,0x0000000000001000L});
    public static final BitSet FOLLOW_groupMember_in_groupBody2235 = new BitSet(new long[]{0x0000000000000000L,0x4000010C00001000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_groupBody2239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_groupMember2250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_importDecl2262 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_importDecl2265 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_importDecl2268 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_importDecl2270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_importDecl2274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_invokeDecl2285 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_invokeDecl2288 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_invokeDecl2290 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_invokeDecl2292 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParameters_in_invokeDecl2294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_invokeBody_in_invokeDecl2296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_invokeBody2307 = new BitSet(new long[]{0x0000000040000800L,0x4000010C00001000L,0x0000000000001020L});
    public static final BitSet FOLLOW_invokeMember_in_invokeBody2310 = new BitSet(new long[]{0x0000000040000800L,0x4000010C00001000L,0x0000000000001020L});
    public static final BitSet FOLLOW_140_in_invokeBody2314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_invokeMember2325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_messageDecl_in_invokeMember2330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_invokeMember2335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_invokeMember2340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_itemsDecl2351 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_itemsBody_in_itemsDecl2354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_itemsBody2365 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124000L,0x0000000000001000L});
    public static final BitSet FOLLOW_itemsMember_in_itemsBody2368 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_itemsBody2372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemDecl_in_itemsMember2383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_itemDecl2394 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_itemDecl2396 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_itemDeclRest_in_itemDecl2398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_itemDeclRest2424 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_itemMember_in_itemDeclRest2427 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_itemDeclRest2431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_itemDeclRest2436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_itemMember2447 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_itemMember2450 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_itemMember2452 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_itemMember2454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_itemMember2456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_listenDecl2467 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_listenDecl2470 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_formalParameters_in_listenDecl2472 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_listenBody_in_listenDecl2475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_listenBody2487 = new BitSet(new long[]{0x0CC40824C0C18920L,0xD149092ED5935000L,0x0000000000003188L});
    public static final BitSet FOLLOW_listenMember_in_listenBody2490 = new BitSet(new long[]{0x0CC40824C0C18920L,0xD149092ED5935000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_listenBody2494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_listenMember2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_listenMember2510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_listenMember2515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MESSAGE_in_messageDecl2527 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_messageDecl2530 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_formalParameters_in_messageDecl2532 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_messageDecl2535 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_messageBody_in_messageDecl2537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_messageBody2549 = new BitSet(new long[]{0x0CC40824C0C18920L,0xD1490D6EF5935000L,0x0000000000003188L});
    public static final BitSet FOLLOW_messageMember_in_messageBody2552 = new BitSet(new long[]{0x0CC40824C0C18920L,0xD1490D6EF5935000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_messageBody2556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_messageMember2567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_messageMember2572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_messageMember2577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_messageMember2582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_messageMember2587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_messageMember2592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_methodDecl2605 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParametersSignature_in_methodDecl2608 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_methodBody_in_methodDecl2610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_methodBody2621 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480C62F5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_methodMember_in_methodBody2624 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480C62F5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_methodBody2628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_methodMember2639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_methodMember2644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_methodMember2649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_methodMember2654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_optionDecl2666 = new BitSet(new long[]{0x0004000000400000L,0x0000000000000001L});
    public static final BitSet FOLLOW_Identifier_in_optionDecl2669 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_formalParameters_in_optionDecl2672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_optionDecl2675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_optionBody_in_optionDecl2677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_optionBody2688 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_optionMember_in_optionBody2691 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91480822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_optionBody2695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_optionMember2706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_participantDecl2721 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_participantDecl2724 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_participantBody_in_participantDecl2726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_participantBody2737 = new BitSet(new long[]{0x0000000040400800L,0x4424010C00041000L,0x0000000000001000L});
    public static final BitSet FOLLOW_participantMember_in_participantBody2740 = new BitSet(new long[]{0x0000000040400800L,0x4424010C00041000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_participantBody2744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_participantMember2756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_receiveDecl_in_participantMember2761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cacheDecl_in_participantMember2766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_persistDecl_in_participantMember2771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scheduleDecl_in_participantMember2776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodDecl_in_participantMember2781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_persistDecl2792 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_persistDecl2795 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_persistBody_in_persistDecl2797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_persistBody2808 = new BitSet(new long[]{0x0000000000000000L,0x4000018C00001000L,0x0000000000001000L});
    public static final BitSet FOLLOW_persistMember_in_persistBody2811 = new BitSet(new long[]{0x0000000000000000L,0x4000018C00001000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_persistBody2815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_persistMember2826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemsDecl_in_persistMember2831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_protocolDecl2842 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_protocolDecl2845 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_protocolBody_in_protocolDecl2847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_protocolBody2858 = new BitSet(new long[]{0x0000000000000000L,0x4606010D000C1000L,0x0000000000001000L});
    public static final BitSet FOLLOW_protocolMember_in_protocolBody2860 = new BitSet(new long[]{0x0000000000000000L,0x4606010D000C1000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_protocolBody2863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_protocolMember2875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_roleDecl_in_protocolMember2880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groupDecl_in_protocolMember2885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_channelDecl_in_protocolMember2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_participantDecl_in_protocolMember2895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cacheDecl_in_protocolMember2900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_persistDecl_in_protocolMember2905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scheduleDecl_in_protocolMember2910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_receiveDecl2921 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_receiveDecl2924 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParametersSignature_in_receiveDecl2926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000110L});
    public static final BitSet FOLLOW_throwsBody_in_receiveDecl2928 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_receiveBody_in_receiveDecl2932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_receiveDecl2936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_receiveBody2948 = new BitSet(new long[]{0x0CC40824C0C18920L,0xD1490D6EF5935000L,0x0000000000003188L});
    public static final BitSet FOLLOW_receiveMember_in_receiveBody2951 = new BitSet(new long[]{0x0CC40824C0C18920L,0xD1490D6EF5935000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_receiveBody2955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_132_in_throwsBody2966 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_throwsList_in_throwsBody2969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwsListDecls_in_throwsList2981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwsListDeclsRest_in_throwsListDecls2994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_throwsListDeclsRest3006 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_throwsListDeclsRest3009 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_throwsListDeclsRest_in_throwsListDeclsRest3011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_receiveMember3025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_receiveMember3030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_receiveMember3035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_receiveMember3040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_receiveMember3045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_receiveMember3050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_roleDecl3062 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_roleDecl3065 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_roleBody_in_roleDecl3067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_roleBody3078 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_roleMember_in_roleBody3081 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_roleBody3085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_roleMember3096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_scheduleDecl3107 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_scheduleDecl3110 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_scheduleBody_in_scheduleDecl3112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_scheduleBody3123 = new BitSet(new long[]{0x0000000000000000L,0x5068014C10001002L,0x0000000000001008L});
    public static final BitSet FOLLOW_scheduleMember_in_scheduleBody3126 = new BitSet(new long[]{0x0000000000000000L,0x5068014C10001002L,0x0000000000001008L});
    public static final BitSet FOLLOW_140_in_scheduleBody3130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_scheduleMember3142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_receiveDecl_in_scheduleMember3147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_scheduleMember3152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schedulableCommandDecl_in_scheduleMember3157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_schedulableCommandDecl3170 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_schedulableCommandDecl3178 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3181 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3183 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_schedulableCommandDecl3191 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3194 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_schedulableCommandDecl3204 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_schedulableCommandDecl3207 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3209 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_131_in_schedulableCommandDecl3217 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3220 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3222 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_133_in_timeoutDecl3242 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_timeoutDecl3245 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_timeoutBody_in_timeoutDecl3247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_timeoutBody3259 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91490822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_timeoutMember_in_timeoutBody3262 = new BitSet(new long[]{0x0CC40824C0C18920L,0x91490822D5934000L,0x0000000000003188L});
    public static final BitSet FOLLOW_140_in_timeoutBody3266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_timeoutMember3277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_timeoutMember3282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_typeRef3296 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_typeRef3298 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_typeRef3300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_formalParameters3324 = new BitSet(new long[]{0x0008000040400800L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameters3326 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_formalParameters3329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterDeclsRest_in_formalParameterDecls3341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_formalParameterDeclsRest3353 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_formalParameterDeclsRest3356 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameterDeclsRest3358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_formalParametersSignature3371 = new BitSet(new long[]{0x0008000040400800L,0x8000082044124000L});
    public static final BitSet FOLLOW_formalParameterSignatureDecls_in_formalParametersSignature3373 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_formalParametersSignature3376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterSignatureDeclsRest_in_formalParameterSignatureDecls3388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_formalParameterSignatureDeclsRest3400 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_formalParameterSignatureDeclsRest3402 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_formalParameterSignatureDeclsRest3405 = new BitSet(new long[]{0x0000000040400800L,0x8000082044124000L});
    public static final BitSet FOLLOW_formalParameterSignatureDecls_in_formalParameterSignatureDeclsRest3407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList3420 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_qualifiedNameList3423 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList3425 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_identifier_in_qualifiedName3446 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_qualifiedName3448 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedName3450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_qualifiedName3455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_identifier3469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_identifier3474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_synpred1_tmp2793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred2_tmp21601 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_synpred2_tmp21603 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_synpred2_tmp21605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred3_tmp21634 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_synpred3_tmp21636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred4_tmp21669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_synpred4_tmp21671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred5_tmp21704 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_synpred5_tmp21706 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_synpred5_tmp21708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred6_tmp21731 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_synpred6_tmp21733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_synpred7_tmp21786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_synpred8_tmp21797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_synpred9_tmp21812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_synpred10_tmp23439 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_synpred10_tmp23441 = new BitSet(new long[]{0x0000000000000002L});

}