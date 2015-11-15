// $ANTLR 3.4 org\\aries\\tmp.g 2014-06-06 14:39:50

	package org.aries;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class tmpParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BLOCK", "CHARLITERAL", "COMMENT", "Digit", "DoubleLiteral", "DoubleSuffix", "DoubleValueSuffix", "EXCEPTION", "EXPR", "EscapeSequence", "Exponent", "FALSE", "FloatLiteral", "FloatSuffix", "FloatValueSuffix", "HexDigit", "HexPrefix", "ITEM", "Identifier", "IntegerLiteral", "IntegerNumber", "IntegerValueSuffix", "LINE_COMMENT", "Letter", "LevelType", "LongSuffix", "MESSAGE", "NULL", "NonIntegerNumber", "PRIMARY", "STRINGLITERAL", "ScopeType", "THROWS", "TRUE", "TYPE", "TimeValueSuffix", "TimeoutLiteral", "TransactionType", "WS", "'!'", "'!='", "'%'", "'%='", "'&&'", "'&'", "'&='", "'('", "')'", "'*'", "'*='", "'+'", "'++'", "'+='", "','", "'-'", "'--'", "'-='", "'.'", "'/'", "'/='", "':'", "';'", "'<'", "'='", "'=='", "'>'", "'?'", "'['", "']'", "'^'", "'^='", "'adapter'", "'add'", "'backingStore'", "'boolean'", "'branch'", "'break'", "'byte'", "'cache'", "'channel'", "'char'", "'client'", "'condition'", "'continue'", "'do'", "'domain'", "'done'", "'double'", "'else'", "'end'", "'execute'", "'float'", "'for'", "'group'", "'if'", "'import'", "'include'", "'index'", "'int'", "'invoke'", "'items'", "'join'", "'level'", "'listen'", "'long'", "'manager'", "'maxResponse'", "'minResponse'", "'namespace'", "'new'", "'option'", "'participant'", "'persist'", "'post'", "'protocol'", "'receive'", "'reply'", "'restriction'", "'return'", "'role'", "'schedule'", "'scope'", "'send'", "'service'", "'set'", "'short'", "'source'", "'synchronous'", "'then'", "'throw'", "'throws'", "'timeout'", "'transaction'", "'while'", "'{'", "'|'", "'|='", "'||'", "'}'", "'~'"
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
    public static final int T__142=142;
    public static final int T__143=143;
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


    public tmpParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public tmpParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
        this.state.ruleMemo = new HashMap[124+1];
         

    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return tmpParser.tokenNames; }
    public String getGrammarFileName() { return "org\\aries\\tmp.g"; }


    public static class compilationUnit_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "compilationUnit"
    // org\\aries\\tmp.g:43:1: compilationUnit : ( importDecl )* ( protocolDecl )* EOF ;
    public final tmpParser.compilationUnit_return compilationUnit() throws RecognitionException {
        tmpParser.compilationUnit_return retval = new tmpParser.compilationUnit_return();
        retval.start = input.LT(1);

        int compilationUnit_StartIndex = input.index();

        Object root_0 = null;

        Token EOF3=null;
        tmpParser.importDecl_return importDecl1 =null;

        tmpParser.protocolDecl_return protocolDecl2 =null;


        Object EOF3_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }

            // org\\aries\\tmp.g:44:2: ( ( importDecl )* ( protocolDecl )* EOF )
            // org\\aries\\tmp.g:44:5: ( importDecl )* ( protocolDecl )* EOF
            {
            root_0 = (Object)adaptor.nil();


            // org\\aries\\tmp.g:44:5: ( importDecl )*
            loop1:
            do {
                int alt1=2;
                switch ( input.LA(1) ) {
                case 99:
                    {
                    alt1=1;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // org\\aries\\tmp.g:44:5: importDecl
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


            // org\\aries\\tmp.g:44:17: ( protocolDecl )*
            loop2:
            do {
                int alt2=2;
                switch ( input.LA(1) ) {
                case 118:
                    {
                    alt2=1;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // org\\aries\\tmp.g:44:17: protocolDecl
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
    // org\\aries\\tmp.g:48:1: assignmentDecl : ( 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';' | 'set' ^ 'backingStore' '(' Identifier ')' ';' | 'add' ^ 'channel' '(' STRINGLITERAL ')' ';' | 'set' ^ 'condition' '(' Identifier ')' ';' | 'set' ^ 'domain' '(' STRINGLITERAL ')' ';' | 'set' ^ 'level' '(' LevelType ')' ';' | 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'set' ^ 'participant' '(' STRINGLITERAL ')' ';' | 'set' ^ 'restriction' '(' qualifiedName ')' ';' | 'set' ^ 'role' '(' STRINGLITERAL ')' ';' | 'add' ^ 'role' '(' STRINGLITERAL ')' ';' | 'set' ^ 'scope' '(' ScopeType ')' ';' | 'set' ^ 'source' '(' STRINGLITERAL ')' ';' | 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';' | 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';' | 'set' ^ 'transaction' '(' TransactionType ')' ';' | 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';' | 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';' );
    public final tmpParser.assignmentDecl_return assignmentDecl() throws RecognitionException {
        tmpParser.assignmentDecl_return retval = new tmpParser.assignmentDecl_return();
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
        tmpParser.qualifiedName_return qualifiedName67 =null;


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

            // org\\aries\\tmp.g:49:2: ( 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';' | 'set' ^ 'backingStore' '(' Identifier ')' ';' | 'add' ^ 'channel' '(' STRINGLITERAL ')' ';' | 'set' ^ 'condition' '(' Identifier ')' ';' | 'set' ^ 'domain' '(' STRINGLITERAL ')' ';' | 'set' ^ 'level' '(' LevelType ')' ';' | 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'set' ^ 'participant' '(' STRINGLITERAL ')' ';' | 'set' ^ 'restriction' '(' qualifiedName ')' ';' | 'set' ^ 'role' '(' STRINGLITERAL ')' ';' | 'add' ^ 'role' '(' STRINGLITERAL ')' ';' | 'set' ^ 'scope' '(' ScopeType ')' ';' | 'set' ^ 'source' '(' STRINGLITERAL ')' ';' | 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';' | 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';' | 'set' ^ 'transaction' '(' TransactionType ')' ';' | 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';' | 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';' )
            int alt3=21;
            switch ( input.LA(1) ) {
            case 128:
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
                case 106:
                    {
                    alt3=6;
                    }
                    break;
                case 110:
                    {
                    alt3=7;
                    }
                    break;
                case 111:
                    {
                    alt3=8;
                    }
                    break;
                case 112:
                    {
                    alt3=9;
                    }
                    break;
                case 115:
                    {
                    alt3=10;
                    }
                    break;
                case 121:
                    {
                    alt3=11;
                    }
                    break;
                case 123:
                    {
                    alt3=12;
                    }
                    break;
                case 125:
                    {
                    alt3=14;
                    }
                    break;
                case 130:
                    {
                    alt3=15;
                    }
                    break;
                case 131:
                    {
                    alt3=16;
                    }
                    break;
                case 135:
                    {
                    alt3=17;
                    }
                    break;
                case 136:
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
                case 123:
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
            case 105:
                {
                alt3=19;
                }
                break;
            case 100:
                {
                alt3=20;
                }
                break;
            case 99:
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
                    // org\\aries\\tmp.g:49:5: 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal4=(Token)match(input,128,FOLLOW_128_in_assignmentDecl133); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:50:5: 'set' ^ 'backingStore' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal10=(Token)match(input,128,FOLLOW_128_in_assignmentDecl150); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:51:5: 'add' ^ 'channel' '(' STRINGLITERAL ')' ';'
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
                    // org\\aries\\tmp.g:52:5: 'set' ^ 'condition' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal22=(Token)match(input,128,FOLLOW_128_in_assignmentDecl184); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:53:5: 'set' ^ 'domain' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal28=(Token)match(input,128,FOLLOW_128_in_assignmentDecl201); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:54:5: 'set' ^ 'level' '(' LevelType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal34=(Token)match(input,128,FOLLOW_128_in_assignmentDecl218); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal34_tree = 
                    (Object)adaptor.create(string_literal34)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal34_tree, root_0);
                    }

                    string_literal35=(Token)match(input,106,FOLLOW_106_in_assignmentDecl221); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:55:5: 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal40=(Token)match(input,128,FOLLOW_128_in_assignmentDecl235); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal40_tree = 
                    (Object)adaptor.create(string_literal40)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal40_tree, root_0);
                    }

                    string_literal41=(Token)match(input,110,FOLLOW_110_in_assignmentDecl238); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:56:5: 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal46=(Token)match(input,128,FOLLOW_128_in_assignmentDecl252); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal46_tree = 
                    (Object)adaptor.create(string_literal46)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal46_tree, root_0);
                    }

                    string_literal47=(Token)match(input,111,FOLLOW_111_in_assignmentDecl255); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:57:5: 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal52=(Token)match(input,128,FOLLOW_128_in_assignmentDecl269); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal52_tree = 
                    (Object)adaptor.create(string_literal52)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal52_tree, root_0);
                    }

                    string_literal53=(Token)match(input,112,FOLLOW_112_in_assignmentDecl272); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:58:5: 'set' ^ 'participant' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal58=(Token)match(input,128,FOLLOW_128_in_assignmentDecl286); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal58_tree = 
                    (Object)adaptor.create(string_literal58)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal58_tree, root_0);
                    }

                    string_literal59=(Token)match(input,115,FOLLOW_115_in_assignmentDecl289); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:59:5: 'set' ^ 'restriction' '(' qualifiedName ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal64=(Token)match(input,128,FOLLOW_128_in_assignmentDecl303); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal64_tree = 
                    (Object)adaptor.create(string_literal64)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal64_tree, root_0);
                    }

                    string_literal65=(Token)match(input,121,FOLLOW_121_in_assignmentDecl306); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:60:5: 'set' ^ 'role' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal70=(Token)match(input,128,FOLLOW_128_in_assignmentDecl320); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal70_tree = 
                    (Object)adaptor.create(string_literal70)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal70_tree, root_0);
                    }

                    string_literal71=(Token)match(input,123,FOLLOW_123_in_assignmentDecl323); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:61:5: 'add' ^ 'role' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal76=(Token)match(input,76,FOLLOW_76_in_assignmentDecl337); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal76_tree = 
                    (Object)adaptor.create(string_literal76)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal76_tree, root_0);
                    }

                    string_literal77=(Token)match(input,123,FOLLOW_123_in_assignmentDecl340); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:62:5: 'set' ^ 'scope' '(' ScopeType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal82=(Token)match(input,128,FOLLOW_128_in_assignmentDecl354); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal82_tree = 
                    (Object)adaptor.create(string_literal82)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal82_tree, root_0);
                    }

                    string_literal83=(Token)match(input,125,FOLLOW_125_in_assignmentDecl357); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:63:5: 'set' ^ 'source' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal88=(Token)match(input,128,FOLLOW_128_in_assignmentDecl371); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal88_tree = 
                    (Object)adaptor.create(string_literal88)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal88_tree, root_0);
                    }

                    string_literal89=(Token)match(input,130,FOLLOW_130_in_assignmentDecl374); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:64:5: 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal94=(Token)match(input,128,FOLLOW_128_in_assignmentDecl388); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal94_tree = 
                    (Object)adaptor.create(string_literal94)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal94_tree, root_0);
                    }

                    string_literal95=(Token)match(input,131,FOLLOW_131_in_assignmentDecl391); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:65:5: 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal100=(Token)match(input,128,FOLLOW_128_in_assignmentDecl411); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal100_tree = 
                    (Object)adaptor.create(string_literal100)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal100_tree, root_0);
                    }

                    string_literal101=(Token)match(input,135,FOLLOW_135_in_assignmentDecl414); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:66:5: 'set' ^ 'transaction' '(' TransactionType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal106=(Token)match(input,128,FOLLOW_128_in_assignmentDecl428); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal106_tree = 
                    (Object)adaptor.create(string_literal106)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal106_tree, root_0);
                    }

                    string_literal107=(Token)match(input,136,FOLLOW_136_in_assignmentDecl431); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:67:5: 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal112=(Token)match(input,105,FOLLOW_105_in_assignmentDecl445); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal112_tree = 
                    (Object)adaptor.create(string_literal112)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal112_tree, root_0);
                    }

                    string_literal113=(Token)match(input,136,FOLLOW_136_in_assignmentDecl448); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:68:5: 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal118=(Token)match(input,100,FOLLOW_100_in_assignmentDecl462); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal118_tree = 
                    (Object)adaptor.create(string_literal118)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal118_tree, root_0);
                    }

                    string_literal119=(Token)match(input,112,FOLLOW_112_in_assignmentDecl465); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:69:5: 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal124=(Token)match(input,99,FOLLOW_99_in_assignmentDecl479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal124_tree = 
                    (Object)adaptor.create(string_literal124)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal124_tree, root_0);
                    }

                    string_literal125=(Token)match(input,112,FOLLOW_112_in_assignmentDecl482); if (state.failed) return retval;
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
    // org\\aries\\tmp.g:72:1: branchDecl : 'branch' ^ ( Identifier )? ':' branchBody ;
    public final tmpParser.branchDecl_return branchDecl() throws RecognitionException {
        tmpParser.branchDecl_return retval = new tmpParser.branchDecl_return();
        retval.start = input.LT(1);

        int branchDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal130=null;
        Token Identifier131=null;
        Token char_literal132=null;
        tmpParser.branchBody_return branchBody133 =null;


        Object string_literal130_tree=null;
        Object Identifier131_tree=null;
        Object char_literal132_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }

            // org\\aries\\tmp.g:73:2: ( 'branch' ^ ( Identifier )? ':' branchBody )
            // org\\aries\\tmp.g:73:5: 'branch' ^ ( Identifier )? ':' branchBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal130=(Token)match(input,79,FOLLOW_79_in_branchDecl502); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal130_tree = 
            (Object)adaptor.create(string_literal130)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal130_tree, root_0);
            }

            // org\\aries\\tmp.g:73:15: ( Identifier )?
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
                    // org\\aries\\tmp.g:73:15: Identifier
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
    // org\\aries\\tmp.g:76:1: branchBody : '{' ( branchMember )* '}' ;
    public final tmpParser.branchBody_return branchBody() throws RecognitionException {
        tmpParser.branchBody_return retval = new tmpParser.branchBody_return();
        retval.start = input.LT(1);

        int branchBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal134=null;
        Token char_literal136=null;
        tmpParser.branchMember_return branchMember135 =null;


        Object char_literal134_tree=null;
        Object char_literal136_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }

            // org\\aries\\tmp.g:77:2: ( '{' ( branchMember )* '}' )
            // org\\aries\\tmp.g:77:5: '{' ( branchMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal134=(Token)match(input,138,FOLLOW_138_in_branchBody523); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal134_tree = 
            (Object)adaptor.create(char_literal134)
            ;
            adaptor.addChild(root_0, char_literal134_tree);
            }

            // org\\aries\\tmp.g:77:9: ( branchMember )*
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 107:
                case 108:
                case 113:
                case 114:
                case 117:
                case 120:
                case 122:
                case 126:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt5=1;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // org\\aries\\tmp.g:77:10: branchMember
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


            char_literal136=(Token)match(input,142,FOLLOW_142_in_branchBody530); if (state.failed) return retval;
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
    // org\\aries\\tmp.g:80:1: branchMember : ( optionDecl | listenDecl | statementDecl );
    public final tmpParser.branchMember_return branchMember() throws RecognitionException {
        tmpParser.branchMember_return retval = new tmpParser.branchMember_return();
        retval.start = input.LT(1);

        int branchMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.optionDecl_return optionDecl137 =null;

        tmpParser.listenDecl_return listenDecl138 =null;

        tmpParser.statementDecl_return statementDecl139 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }

            // org\\aries\\tmp.g:81:2: ( optionDecl | listenDecl | statementDecl )
            int alt6=3;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt6=1;
                }
                break;
            case 107:
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
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
                    // org\\aries\\tmp.g:81:4: optionDecl
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
                    // org\\aries\\tmp.g:82:5: listenDecl
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
                    // org\\aries\\tmp.g:83:4: statementDecl
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
    // org\\aries\\tmp.g:87:1: cacheDecl : 'cache' ^ Identifier cacheBody ;
    public final tmpParser.cacheDecl_return cacheDecl() throws RecognitionException {
        tmpParser.cacheDecl_return retval = new tmpParser.cacheDecl_return();
        retval.start = input.LT(1);

        int cacheDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal140=null;
        Token Identifier141=null;
        tmpParser.cacheBody_return cacheBody142 =null;


        Object string_literal140_tree=null;
        Object Identifier141_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }

            // org\\aries\\tmp.g:88:2: ( 'cache' ^ Identifier cacheBody )
            // org\\aries\\tmp.g:88:5: 'cache' ^ Identifier cacheBody
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
    // org\\aries\\tmp.g:91:1: cacheBody : '{' ( cacheMember )* '}' ;
    public final tmpParser.cacheBody_return cacheBody() throws RecognitionException {
        tmpParser.cacheBody_return retval = new tmpParser.cacheBody_return();
        retval.start = input.LT(1);

        int cacheBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal143=null;
        Token char_literal145=null;
        tmpParser.cacheMember_return cacheMember144 =null;


        Object char_literal143_tree=null;
        Object char_literal145_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }

            // org\\aries\\tmp.g:92:2: ( '{' ( cacheMember )* '}' )
            // org\\aries\\tmp.g:92:5: '{' ( cacheMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal143=(Token)match(input,138,FOLLOW_138_in_cacheBody582); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal143_tree = 
            (Object)adaptor.create(char_literal143)
            ;
            adaptor.addChild(root_0, char_literal143_tree);
            }

            // org\\aries\\tmp.g:92:9: ( cacheMember )*
            loop7:
            do {
                int alt7=2;
                switch ( input.LA(1) ) {
                case 76:
                case 99:
                case 100:
                case 104:
                case 105:
                case 128:
                    {
                    alt7=1;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // org\\aries\\tmp.g:92:10: cacheMember
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


            char_literal145=(Token)match(input,142,FOLLOW_142_in_cacheBody589); if (state.failed) return retval;
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
    // org\\aries\\tmp.g:95:1: cacheMember : ( assignmentDecl | itemsDecl );
    public final tmpParser.cacheMember_return cacheMember() throws RecognitionException {
        tmpParser.cacheMember_return retval = new tmpParser.cacheMember_return();
        retval.start = input.LT(1);

        int cacheMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl146 =null;

        tmpParser.itemsDecl_return itemsDecl147 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }

            // org\\aries\\tmp.g:96:2: ( assignmentDecl | itemsDecl )
            int alt8=2;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt8=1;
                }
                break;
            case 104:
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
                    // org\\aries\\tmp.g:96:4: assignmentDecl
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
                    // org\\aries\\tmp.g:97:4: itemsDecl
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
    // org\\aries\\tmp.g:100:1: channelDecl : 'channel' ^ Identifier '{' ( channelBody )* '}' ;
    public final tmpParser.channelDecl_return channelDecl() throws RecognitionException {
        tmpParser.channelDecl_return retval = new tmpParser.channelDecl_return();
        retval.start = input.LT(1);

        int channelDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal148=null;
        Token Identifier149=null;
        Token char_literal150=null;
        Token char_literal152=null;
        tmpParser.channelBody_return channelBody151 =null;


        Object string_literal148_tree=null;
        Object Identifier149_tree=null;
        Object char_literal150_tree=null;
        Object char_literal152_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }

            // org\\aries\\tmp.g:101:2: ( 'channel' ^ Identifier '{' ( channelBody )* '}' )
            // org\\aries\\tmp.g:101:4: 'channel' ^ Identifier '{' ( channelBody )* '}'
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

            char_literal150=(Token)match(input,138,FOLLOW_138_in_channelDecl621); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal150_tree = 
            (Object)adaptor.create(char_literal150)
            ;
            adaptor.addChild(root_0, char_literal150_tree);
            }

            // org\\aries\\tmp.g:101:30: ( channelBody )*
            loop9:
            do {
                int alt9=2;
                switch ( input.LA(1) ) {
                case 65:
                case 76:
                case 128:
                    {
                    alt9=1;
                    }
                    break;

                }

                switch (alt9) {
            	case 1 :
            	    // org\\aries\\tmp.g:101:31: channelBody
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


            char_literal152=(Token)match(input,142,FOLLOW_142_in_channelDecl628); if (state.failed) return retval;
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
    // org\\aries\\tmp.g:104:1: channelBody : ( 'add' 'client' '(' Identifier ')' ';' | 'add' 'service' '(' Identifier ')' ';' | 'add' 'manager' '(' Identifier ')' ';' | 'set' 'domain' '(' Identifier ')' ';' | ';' );
    public final tmpParser.channelBody_return channelBody() throws RecognitionException {
        tmpParser.channelBody_return retval = new tmpParser.channelBody_return();
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

            // org\\aries\\tmp.g:105:2: ( 'add' 'client' '(' Identifier ')' ';' | 'add' 'service' '(' Identifier ')' ';' | 'add' 'manager' '(' Identifier ')' ';' | 'set' 'domain' '(' Identifier ')' ';' | ';' )
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
                case 127:
                    {
                    alt10=2;
                    }
                    break;
                case 109:
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
            case 128:
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
                    // org\\aries\\tmp.g:105:4: 'add' 'client' '(' Identifier ')' ';'
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
                    // org\\aries\\tmp.g:106:4: 'add' 'service' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal159=(Token)match(input,76,FOLLOW_76_in_channelBody654); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal159_tree = 
                    (Object)adaptor.create(string_literal159)
                    ;
                    adaptor.addChild(root_0, string_literal159_tree);
                    }

                    string_literal160=(Token)match(input,127,FOLLOW_127_in_channelBody656); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:107:4: 'add' 'manager' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal165=(Token)match(input,76,FOLLOW_76_in_channelBody669); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal165_tree = 
                    (Object)adaptor.create(string_literal165)
                    ;
                    adaptor.addChild(root_0, string_literal165_tree);
                    }

                    string_literal166=(Token)match(input,109,FOLLOW_109_in_channelBody671); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:108:4: 'set' 'domain' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal171=(Token)match(input,128,FOLLOW_128_in_channelBody684); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:109:4: ';'
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
    // org\\aries\\tmp.g:112:1: commandDecl : ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' );
    public final tmpParser.commandDecl_return commandDecl() throws RecognitionException {
        tmpParser.commandDecl_return retval = new tmpParser.commandDecl_return();
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
        tmpParser.formalParameters_return formalParameters182 =null;

        tmpParser.formalParameters_return formalParameters186 =null;

        tmpParser.qualifiedName_return qualifiedName189 =null;

        tmpParser.formalParameters_return formalParameters190 =null;


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

            // org\\aries\\tmp.g:113:2: ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' )
            int alt14=4;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt14=1;
                }
                break;
            case 117:
                {
                alt14=2;
                }
                break;
            case 120:
                {
                alt14=3;
                }
                break;
            case 126:
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
                    // org\\aries\\tmp.g:113:4: 'end' ^ ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal178=(Token)match(input,93,FOLLOW_93_in_commandDecl710); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:114:4: 'post' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal180=(Token)match(input,117,FOLLOW_117_in_commandDecl718); if (state.failed) return retval;
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

                    // org\\aries\\tmp.g:114:23: ( formalParameters )?
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
                            // org\\aries\\tmp.g:114:23: formalParameters
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
                    // org\\aries\\tmp.g:115:4: 'reply' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal184=(Token)match(input,120,FOLLOW_120_in_commandDecl731); if (state.failed) return retval;
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

                    // org\\aries\\tmp.g:115:24: ( formalParameters )?
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
                            // org\\aries\\tmp.g:115:24: formalParameters
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
                    // org\\aries\\tmp.g:116:4: 'send' ^ qualifiedName ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal188=(Token)match(input,126,FOLLOW_126_in_commandDecl744); if (state.failed) return retval;
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

                    // org\\aries\\tmp.g:116:26: ( formalParameters )?
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
                            // org\\aries\\tmp.g:116:26: formalParameters
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
    // org\\aries\\tmp.g:120:1: statementDecl : ( statementBlock -> ^( BLOCK statementBlock ) | 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )? | 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl | 'while' ^ '(' expressionDecl ')' statementDecl | 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';' | 'return' ^ ( expressionDecl )? ';' | 'throw' ^ expressionDecl ';' | 'break' ^ ( Identifier )? ';' | 'continue' ^ ( Identifier )? ';' | expressionDecl ';' | commandDecl );
    public final tmpParser.statementDecl_return statementDecl() throws RecognitionException {
        tmpParser.statementDecl_return retval = new tmpParser.statementDecl_return();
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
        tmpParser.statementBlock_return statementBlock192 =null;

        tmpParser.expressionDecl_return expressionDecl195 =null;

        tmpParser.statementDecl_return statementDecl197 =null;

        tmpParser.statementDecl_return statementDecl199 =null;

        tmpParser.variableDeclaration_return variableDeclaration202 =null;

        tmpParser.expressionDecl_return expressionDecl204 =null;

        tmpParser.expressionList_return expressionList206 =null;

        tmpParser.statementDecl_return statementDecl208 =null;

        tmpParser.expressionDecl_return expressionDecl211 =null;

        tmpParser.statementDecl_return statementDecl213 =null;

        tmpParser.statementDecl_return statementDecl215 =null;

        tmpParser.expressionDecl_return expressionDecl218 =null;

        tmpParser.expressionDecl_return expressionDecl222 =null;

        tmpParser.expressionDecl_return expressionDecl225 =null;

        tmpParser.expressionDecl_return expressionDecl233 =null;

        tmpParser.commandDecl_return commandDecl235 =null;


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

            // org\\aries\\tmp.g:121:2: ( statementBlock -> ^( BLOCK statementBlock ) | 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )? | 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl | 'while' ^ '(' expressionDecl ')' statementDecl | 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';' | 'return' ^ ( expressionDecl )? ';' | 'throw' ^ expressionDecl ';' | 'break' ^ ( Identifier )? ';' | 'continue' ^ ( Identifier )? ';' | expressionDecl ';' | commandDecl )
            int alt22=11;
            switch ( input.LA(1) ) {
            case 138:
                {
                alt22=1;
                }
                break;
            case 98:
                {
                alt22=2;
                }
                break;
            case 96:
                {
                alt22=3;
                }
                break;
            case 137:
                {
                alt22=4;
                }
                break;
            case 88:
                {
                alt22=5;
                }
                break;
            case 122:
                {
                alt22=6;
                }
                break;
            case 133:
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
            case 91:
            case 95:
            case 102:
            case 108:
            case 113:
            case 129:
            case 143:
                {
                alt22=10;
                }
                break;
            case 93:
            case 117:
            case 120:
            case 126:
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
                    // org\\aries\\tmp.g:121:4: statementBlock
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
                        // org\\aries\\tmp.g:121:22: ^( BLOCK statementBlock )
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
                    // org\\aries\\tmp.g:123:4: 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )?
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal193=(Token)match(input,98,FOLLOW_98_in_statementDecl780); if (state.failed) return retval;
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

                    // org\\aries\\tmp.g:123:47: ( ( 'else' )=> 'else' statementDecl )?
                    int alt15=2;
                    switch ( input.LA(1) ) {
                        case 92:
                            {
                            int LA15_1 = input.LA(2);

                            if ( (synpred1_tmp()) ) {
                                alt15=1;
                            }
                            }
                            break;
                    }

                    switch (alt15) {
                        case 1 :
                            // org\\aries\\tmp.g:123:48: ( 'else' )=> 'else' statementDecl
                            {
                            string_literal198=(Token)match(input,92,FOLLOW_92_in_statementDecl798); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:124:4: 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal200=(Token)match(input,96,FOLLOW_96_in_statementDecl807); if (state.failed) return retval;
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

                    // org\\aries\\tmp.g:124:15: ( variableDeclaration )?
                    int alt16=2;
                    switch ( input.LA(1) ) {
                        case EXCEPTION:
                        case Identifier:
                        case MESSAGE:
                        case 78:
                        case 81:
                        case 84:
                        case 91:
                        case 95:
                        case 102:
                        case 108:
                        case 129:
                            {
                            alt16=1;
                            }
                            break;
                    }

                    switch (alt16) {
                        case 1 :
                            // org\\aries\\tmp.g:124:16: variableDeclaration
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

                    // org\\aries\\tmp.g:124:42: ( expressionDecl )?
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
                        case 91:
                        case 95:
                        case 102:
                        case 108:
                        case 113:
                        case 129:
                        case 143:
                            {
                            alt17=1;
                            }
                            break;
                    }

                    switch (alt17) {
                        case 1 :
                            // org\\aries\\tmp.g:124:43: expressionDecl
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

                    // org\\aries\\tmp.g:124:64: ( expressionList )?
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
                        case 91:
                        case 95:
                        case 102:
                        case 108:
                        case 113:
                        case 129:
                        case 143:
                            {
                            alt18=1;
                            }
                            break;
                    }

                    switch (alt18) {
                        case 1 :
                            // org\\aries\\tmp.g:124:65: expressionList
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
                    // org\\aries\\tmp.g:125:4: 'while' ^ '(' expressionDecl ')' statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal209=(Token)match(input,137,FOLLOW_137_in_statementDecl838); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:126:4: 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';'
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

                    string_literal216=(Token)match(input,137,FOLLOW_137_in_statementDecl857); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:127:4: 'return' ^ ( expressionDecl )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal221=(Token)match(input,122,FOLLOW_122_in_statementDecl870); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal221_tree = 
                    (Object)adaptor.create(string_literal221)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal221_tree, root_0);
                    }

                    // org\\aries\\tmp.g:127:14: ( expressionDecl )?
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
                        case 91:
                        case 95:
                        case 102:
                        case 108:
                        case 113:
                        case 129:
                        case 143:
                            {
                            alt19=1;
                            }
                            break;
                    }

                    switch (alt19) {
                        case 1 :
                            // org\\aries\\tmp.g:127:15: expressionDecl
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
                    // org\\aries\\tmp.g:128:4: 'throw' ^ expressionDecl ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal224=(Token)match(input,133,FOLLOW_133_in_statementDecl883); if (state.failed) return retval;
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
                    // org\\aries\\tmp.g:129:4: 'break' ^ ( Identifier )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal227=(Token)match(input,80,FOLLOW_80_in_statementDecl893); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal227_tree = 
                    (Object)adaptor.create(string_literal227)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal227_tree, root_0);
                    }

                    // org\\aries\\tmp.g:129:13: ( Identifier )?
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
                            // org\\aries\\tmp.g:129:14: Identifier
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
                    // org\\aries\\tmp.g:130:4: 'continue' ^ ( Identifier )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal230=(Token)match(input,87,FOLLOW_87_in_statementDecl906); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal230_tree = 
                    (Object)adaptor.create(string_literal230)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal230_tree, root_0);
                    }

                    // org\\aries\\tmp.g:130:16: ( Identifier )?
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
                            // org\\aries\\tmp.g:130:17: Identifier
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
                    // org\\aries\\tmp.g:131:4: expressionDecl ';'
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
                    // org\\aries\\tmp.g:132:4: commandDecl
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
    // org\\aries\\tmp.g:135:1: statementBlock : '{' ( statementMember )* '}' ;
    public final tmpParser.statementBlock_return statementBlock() throws RecognitionException {
        tmpParser.statementBlock_return retval = new tmpParser.statementBlock_return();
        retval.start = input.LT(1);

        int statementBlock_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal236=null;
        Token char_literal238=null;
        tmpParser.statementMember_return statementMember237 =null;


        Object char_literal236_tree=null;
        Object char_literal238_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }

            // org\\aries\\tmp.g:136:2: ( '{' ( statementMember )* '}' )
            // org\\aries\\tmp.g:136:4: '{' ( statementMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal236=(Token)match(input,138,FOLLOW_138_in_statementBlock939); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal236_tree = 
            (Object)adaptor.create(char_literal236)
            ;
            adaptor.addChild(root_0, char_literal236_tree);
            }

            // org\\aries\\tmp.g:136:8: ( statementMember )*
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 108:
                case 113:
                case 117:
                case 120:
                case 122:
                case 126:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt23=1;
                    }
                    break;

                }

                switch (alt23) {
            	case 1 :
            	    // org\\aries\\tmp.g:136:9: statementMember
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


            char_literal238=(Token)match(input,142,FOLLOW_142_in_statementBlock946); if (state.failed) return retval;
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
    // org\\aries\\tmp.g:139:1: statementMember : statementDecl ;
    public final tmpParser.statementMember_return statementMember() throws RecognitionException {
        tmpParser.statementMember_return retval = new tmpParser.statementMember_return();
        retval.start = input.LT(1);

        int statementMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.statementDecl_return statementDecl239 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }

            // org\\aries\\tmp.g:140:2: ( statementDecl )
            // org\\aries\\tmp.g:140:4: statementDecl
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
    // org\\aries\\tmp.g:143:1: variableDeclaration : type Identifier ( '[' ']' )* '=' variableInitializer -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer ) ;
    public final tmpParser.variableDeclaration_return variableDeclaration() throws RecognitionException {
        tmpParser.variableDeclaration_return retval = new tmpParser.variableDeclaration_return();
        retval.start = input.LT(1);

        int variableDeclaration_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier241=null;
        Token char_literal242=null;
        Token char_literal243=null;
        Token char_literal244=null;
        tmpParser.type_return type240 =null;

        tmpParser.variableInitializer_return variableInitializer245 =null;


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

            // org\\aries\\tmp.g:144:2: ( type Identifier ( '[' ']' )* '=' variableInitializer -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer ) )
            // org\\aries\\tmp.g:144:4: type Identifier ( '[' ']' )* '=' variableInitializer
            {
            pushFollow(FOLLOW_type_in_variableDeclaration970);
            type240=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type240.getTree());

            Identifier241=(Token)match(input,Identifier,FOLLOW_Identifier_in_variableDeclaration972); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Identifier.add(Identifier241);


            // org\\aries\\tmp.g:144:20: ( '[' ']' )*
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
            	    // org\\aries\\tmp.g:144:21: '[' ']'
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
            // elements: Identifier, variableInitializer, 71, 72, 67, type
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
                // org\\aries\\tmp.g:144:58: ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EXPR, "EXPR")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_Identifier.nextNode()
                );

                // org\\aries\\tmp.g:144:81: ( '[' ']' )*
                while ( stream_71.hasNext()||stream_72.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_71.nextNode()
                    );

                    adaptor.addChild(root_1, 
                    stream_72.nextNode()
                    );

                }
                stream_71.reset();
                stream_72.reset();

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
    // org\\aries\\tmp.g:147:1: variableInitializer : ( arrayInitializer | expressionDecl );
    public final tmpParser.variableInitializer_return variableInitializer() throws RecognitionException {
        tmpParser.variableInitializer_return retval = new tmpParser.variableInitializer_return();
        retval.start = input.LT(1);

        int variableInitializer_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.arrayInitializer_return arrayInitializer246 =null;

        tmpParser.expressionDecl_return expressionDecl247 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }

            // org\\aries\\tmp.g:148:2: ( arrayInitializer | expressionDecl )
            int alt25=2;
            switch ( input.LA(1) ) {
            case 138:
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
            case 91:
            case 95:
            case 102:
            case 108:
            case 113:
            case 129:
            case 143:
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
                    // org\\aries\\tmp.g:148:4: arrayInitializer
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
                    // org\\aries\\tmp.g:149:4: expressionDecl
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
    // org\\aries\\tmp.g:152:1: arrayInitializer : '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}' ;
    public final tmpParser.arrayInitializer_return arrayInitializer() throws RecognitionException {
        tmpParser.arrayInitializer_return retval = new tmpParser.arrayInitializer_return();
        retval.start = input.LT(1);

        int arrayInitializer_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal248=null;
        Token char_literal250=null;
        Token char_literal252=null;
        Token char_literal253=null;
        tmpParser.variableInitializer_return variableInitializer249 =null;

        tmpParser.variableInitializer_return variableInitializer251 =null;


        Object char_literal248_tree=null;
        Object char_literal250_tree=null;
        Object char_literal252_tree=null;
        Object char_literal253_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }

            // org\\aries\\tmp.g:153:2: ( '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}' )
            // org\\aries\\tmp.g:153:4: '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal248=(Token)match(input,138,FOLLOW_138_in_arrayInitializer1033); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal248_tree = 
            (Object)adaptor.create(char_literal248)
            ;
            adaptor.addChild(root_0, char_literal248_tree);
            }

            // org\\aries\\tmp.g:153:8: ( variableInitializer ( ',' variableInitializer )* )?
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
                case 91:
                case 95:
                case 102:
                case 108:
                case 113:
                case 129:
                case 138:
                case 143:
                    {
                    alt27=1;
                    }
                    break;
            }

            switch (alt27) {
                case 1 :
                    // org\\aries\\tmp.g:153:9: variableInitializer ( ',' variableInitializer )*
                    {
                    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1036);
                    variableInitializer249=variableInitializer();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableInitializer249.getTree());

                    // org\\aries\\tmp.g:153:29: ( ',' variableInitializer )*
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
                            case 91:
                            case 95:
                            case 102:
                            case 108:
                            case 113:
                            case 129:
                            case 138:
                            case 143:
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
                    	    // org\\aries\\tmp.g:153:30: ',' variableInitializer
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


            // org\\aries\\tmp.g:153:59: ( ',' )?
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
                    // org\\aries\\tmp.g:153:60: ','
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


            char_literal253=(Token)match(input,142,FOLLOW_142_in_arrayInitializer1053); if (state.failed) return retval;
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
    // org\\aries\\tmp.g:156:1: expressionDecl : ( ( 'new' Identifier '(' ')' ) -> ^( EXPR 'new' Identifier '(' ')' ) | conditionalExpression ( assignmentOperator expressionDecl )? -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? ) );
    public final tmpParser.expressionDecl_return expressionDecl() throws RecognitionException {
        tmpParser.expressionDecl_return retval = new tmpParser.expressionDecl_return();
        retval.start = input.LT(1);

        int expressionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal254=null;
        Token Identifier255=null;
        Token char_literal256=null;
        Token char_literal257=null;
        tmpParser.conditionalExpression_return conditionalExpression258 =null;

        tmpParser.assignmentOperator_return assignmentOperator259 =null;

        tmpParser.expressionDecl_return expressionDecl260 =null;


        Object string_literal254_tree=null;
        Object Identifier255_tree=null;
        Object char_literal256_tree=null;
        Object char_literal257_tree=null;
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_assignmentOperator=new RewriteRuleSubtreeStream(adaptor,"rule assignmentOperator");
        RewriteRuleSubtreeStream stream_expressionDecl=new RewriteRuleSubtreeStream(adaptor,"rule expressionDecl");
        RewriteRuleSubtreeStream stream_conditionalExpression=new RewriteRuleSubtreeStream(adaptor,"rule conditionalExpression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }

            // org\\aries\\tmp.g:157:2: ( ( 'new' Identifier '(' ')' ) -> ^( EXPR 'new' Identifier '(' ')' ) | conditionalExpression ( assignmentOperator expressionDecl )? -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? ) )
            int alt30=2;
            switch ( input.LA(1) ) {
            case 113:
                {
                alt30=1;
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
            case 91:
            case 95:
            case 102:
            case 108:
            case 129:
            case 143:
                {
                alt30=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }

            switch (alt30) {
                case 1 :
                    // org\\aries\\tmp.g:157:4: ( 'new' Identifier '(' ')' )
                    {
                    // org\\aries\\tmp.g:157:4: ( 'new' Identifier '(' ')' )
                    // org\\aries\\tmp.g:157:5: 'new' Identifier '(' ')'
                    {
                    string_literal254=(Token)match(input,113,FOLLOW_113_in_expressionDecl1066); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_113.add(string_literal254);


                    Identifier255=(Token)match(input,Identifier,FOLLOW_Identifier_in_expressionDecl1068); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier255);


                    char_literal256=(Token)match(input,50,FOLLOW_50_in_expressionDecl1070); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal256);


                    char_literal257=(Token)match(input,51,FOLLOW_51_in_expressionDecl1072); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal257);


                    }


                    // AST REWRITE
                    // elements: 51, Identifier, 113, 50
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 157:31: -> ^( EXPR 'new' Identifier '(' ')' )
                    {
                        // org\\aries\\tmp.g:157:34: ^( EXPR 'new' Identifier '(' ')' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(EXPR, "EXPR")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_113.nextNode()
                        );

                        adaptor.addChild(root_1, 
                        stream_Identifier.nextNode()
                        );

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
                case 2 :
                    // org\\aries\\tmp.g:158:4: conditionalExpression ( assignmentOperator expressionDecl )?
                    {
                    pushFollow(FOLLOW_conditionalExpression_in_expressionDecl1092);
                    conditionalExpression258=conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_conditionalExpression.add(conditionalExpression258.getTree());

                    // org\\aries\\tmp.g:158:26: ( assignmentOperator expressionDecl )?
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
                        case 140:
                            {
                            alt29=1;
                            }
                            break;
                    }

                    switch (alt29) {
                        case 1 :
                            // org\\aries\\tmp.g:158:27: assignmentOperator expressionDecl
                            {
                            pushFollow(FOLLOW_assignmentOperator_in_expressionDecl1095);
                            assignmentOperator259=assignmentOperator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_assignmentOperator.add(assignmentOperator259.getTree());

                            pushFollow(FOLLOW_expressionDecl_in_expressionDecl1097);
                            expressionDecl260=expressionDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl260.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: conditionalExpression, assignmentOperator, expressionDecl
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 158:63: -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? )
                    {
                        // org\\aries\\tmp.g:158:66: ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(EXPR, "EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_conditionalExpression.nextTree());

                        // org\\aries\\tmp.g:158:95: ( assignmentOperator expressionDecl )?
                        if ( stream_assignmentOperator.hasNext()||stream_expressionDecl.hasNext() ) {
                            adaptor.addChild(root_1, stream_assignmentOperator.nextTree());

                            adaptor.addChild(root_1, stream_expressionDecl.nextTree());

                        }
                        stream_assignmentOperator.reset();
                        stream_expressionDecl.reset();

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
    // org\\aries\\tmp.g:161:1: expressionList : expressionDecl ( ',' expressionDecl )* ;
    public final tmpParser.expressionList_return expressionList() throws RecognitionException {
        tmpParser.expressionList_return retval = new tmpParser.expressionList_return();
        retval.start = input.LT(1);

        int expressionList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal262=null;
        tmpParser.expressionDecl_return expressionDecl261 =null;

        tmpParser.expressionDecl_return expressionDecl263 =null;


        Object char_literal262_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }

            // org\\aries\\tmp.g:162:2: ( expressionDecl ( ',' expressionDecl )* )
            // org\\aries\\tmp.g:162:4: expressionDecl ( ',' expressionDecl )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_expressionDecl_in_expressionList1126);
            expressionDecl261=expressionDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl261.getTree());

            // org\\aries\\tmp.g:162:19: ( ',' expressionDecl )*
            loop31:
            do {
                int alt31=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt31=1;
                    }
                    break;

                }

                switch (alt31) {
            	case 1 :
            	    // org\\aries\\tmp.g:162:20: ',' expressionDecl
            	    {
            	    char_literal262=(Token)match(input,57,FOLLOW_57_in_expressionList1129); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal262_tree = 
            	    (Object)adaptor.create(char_literal262)
            	    ;
            	    adaptor.addChild(root_0, char_literal262_tree);
            	    }

            	    pushFollow(FOLLOW_expressionDecl_in_expressionList1131);
            	    expressionDecl263=expressionDecl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl263.getTree());

            	    }
            	    break;

            	default :
            	    break loop31;
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
    // org\\aries\\tmp.g:165:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '>' '=' | '>' '>' '=' );
    public final tmpParser.assignmentOperator_return assignmentOperator() throws RecognitionException {
        tmpParser.assignmentOperator_return retval = new tmpParser.assignmentOperator_return();
        retval.start = input.LT(1);

        int assignmentOperator_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal264=null;
        Token string_literal265=null;
        Token string_literal266=null;
        Token string_literal267=null;
        Token string_literal268=null;
        Token string_literal269=null;
        Token string_literal270=null;
        Token string_literal271=null;
        Token string_literal272=null;
        Token char_literal273=null;
        Token char_literal274=null;
        Token char_literal275=null;
        Token char_literal276=null;
        Token char_literal277=null;
        Token char_literal278=null;
        Token char_literal279=null;
        Token char_literal280=null;
        Token char_literal281=null;
        Token char_literal282=null;

        Object char_literal264_tree=null;
        Object string_literal265_tree=null;
        Object string_literal266_tree=null;
        Object string_literal267_tree=null;
        Object string_literal268_tree=null;
        Object string_literal269_tree=null;
        Object string_literal270_tree=null;
        Object string_literal271_tree=null;
        Object string_literal272_tree=null;
        Object char_literal273_tree=null;
        Object char_literal274_tree=null;
        Object char_literal275_tree=null;
        Object char_literal276_tree=null;
        Object char_literal277_tree=null;
        Object char_literal278_tree=null;
        Object char_literal279_tree=null;
        Object char_literal280_tree=null;
        Object char_literal281_tree=null;
        Object char_literal282_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }

            // org\\aries\\tmp.g:166:2: ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '>' '=' | '>' '>' '=' )
            int alt32=12;
            switch ( input.LA(1) ) {
            case 67:
                {
                alt32=1;
                }
                break;
            case 56:
                {
                alt32=2;
                }
                break;
            case 60:
                {
                alt32=3;
                }
                break;
            case 53:
                {
                alt32=4;
                }
                break;
            case 63:
                {
                alt32=5;
                }
                break;
            case 49:
                {
                alt32=6;
                }
                break;
            case 140:
                {
                alt32=7;
                }
                break;
            case 74:
                {
                alt32=8;
                }
                break;
            case 46:
                {
                alt32=9;
                }
                break;
            case 66:
                {
                alt32=10;
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
                        alt32=11;
                        }
                        break;
                    case 67:
                        {
                        alt32=12;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 12, input);

                        throw nvae;

                    }

                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 11, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;

            }

            switch (alt32) {
                case 1 :
                    // org\\aries\\tmp.g:166:4: '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal264=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1145); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal264_tree = 
                    (Object)adaptor.create(char_literal264)
                    ;
                    adaptor.addChild(root_0, char_literal264_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:167:4: '+='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal265=(Token)match(input,56,FOLLOW_56_in_assignmentOperator1150); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal265_tree = 
                    (Object)adaptor.create(string_literal265)
                    ;
                    adaptor.addChild(root_0, string_literal265_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:168:4: '-='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal266=(Token)match(input,60,FOLLOW_60_in_assignmentOperator1155); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal266_tree = 
                    (Object)adaptor.create(string_literal266)
                    ;
                    adaptor.addChild(root_0, string_literal266_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:169:4: '*='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal267=(Token)match(input,53,FOLLOW_53_in_assignmentOperator1160); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal267_tree = 
                    (Object)adaptor.create(string_literal267)
                    ;
                    adaptor.addChild(root_0, string_literal267_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:170:4: '/='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal268=(Token)match(input,63,FOLLOW_63_in_assignmentOperator1165); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal268_tree = 
                    (Object)adaptor.create(string_literal268)
                    ;
                    adaptor.addChild(root_0, string_literal268_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:171:4: '&='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal269=(Token)match(input,49,FOLLOW_49_in_assignmentOperator1170); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal269_tree = 
                    (Object)adaptor.create(string_literal269)
                    ;
                    adaptor.addChild(root_0, string_literal269_tree);
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp.g:172:4: '|='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal270=(Token)match(input,140,FOLLOW_140_in_assignmentOperator1175); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal270_tree = 
                    (Object)adaptor.create(string_literal270)
                    ;
                    adaptor.addChild(root_0, string_literal270_tree);
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp.g:173:4: '^='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal271=(Token)match(input,74,FOLLOW_74_in_assignmentOperator1180); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal271_tree = 
                    (Object)adaptor.create(string_literal271)
                    ;
                    adaptor.addChild(root_0, string_literal271_tree);
                    }

                    }
                    break;
                case 9 :
                    // org\\aries\\tmp.g:174:4: '%='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal272=(Token)match(input,46,FOLLOW_46_in_assignmentOperator1185); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal272_tree = 
                    (Object)adaptor.create(string_literal272)
                    ;
                    adaptor.addChild(root_0, string_literal272_tree);
                    }

                    }
                    break;
                case 10 :
                    // org\\aries\\tmp.g:175:4: '<' '<' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal273=(Token)match(input,66,FOLLOW_66_in_assignmentOperator1190); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal273_tree = 
                    (Object)adaptor.create(char_literal273)
                    ;
                    adaptor.addChild(root_0, char_literal273_tree);
                    }

                    char_literal274=(Token)match(input,66,FOLLOW_66_in_assignmentOperator1192); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal274_tree = 
                    (Object)adaptor.create(char_literal274)
                    ;
                    adaptor.addChild(root_0, char_literal274_tree);
                    }

                    char_literal275=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1194); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal275_tree = 
                    (Object)adaptor.create(char_literal275)
                    ;
                    adaptor.addChild(root_0, char_literal275_tree);
                    }

                    }
                    break;
                case 11 :
                    // org\\aries\\tmp.g:176:4: '>' '>' '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal276=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1199); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal276_tree = 
                    (Object)adaptor.create(char_literal276)
                    ;
                    adaptor.addChild(root_0, char_literal276_tree);
                    }

                    char_literal277=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1201); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal277_tree = 
                    (Object)adaptor.create(char_literal277)
                    ;
                    adaptor.addChild(root_0, char_literal277_tree);
                    }

                    char_literal278=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1203); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal278_tree = 
                    (Object)adaptor.create(char_literal278)
                    ;
                    adaptor.addChild(root_0, char_literal278_tree);
                    }

                    char_literal279=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1205); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal279_tree = 
                    (Object)adaptor.create(char_literal279)
                    ;
                    adaptor.addChild(root_0, char_literal279_tree);
                    }

                    }
                    break;
                case 12 :
                    // org\\aries\\tmp.g:177:4: '>' '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal280=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1210); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal280_tree = 
                    (Object)adaptor.create(char_literal280)
                    ;
                    adaptor.addChild(root_0, char_literal280_tree);
                    }

                    char_literal281=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1212); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal281_tree = 
                    (Object)adaptor.create(char_literal281)
                    ;
                    adaptor.addChild(root_0, char_literal281_tree);
                    }

                    char_literal282=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1214); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal282_tree = 
                    (Object)adaptor.create(char_literal282)
                    ;
                    adaptor.addChild(root_0, char_literal282_tree);
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
    // org\\aries\\tmp.g:180:1: conditionalExpression : conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )? ;
    public final tmpParser.conditionalExpression_return conditionalExpression() throws RecognitionException {
        tmpParser.conditionalExpression_return retval = new tmpParser.conditionalExpression_return();
        retval.start = input.LT(1);

        int conditionalExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal284=null;
        Token char_literal286=null;
        tmpParser.conditionalOrExpression_return conditionalOrExpression283 =null;

        tmpParser.expressionDecl_return expressionDecl285 =null;

        tmpParser.conditionalExpression_return conditionalExpression287 =null;


        Object char_literal284_tree=null;
        Object char_literal286_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }

            // org\\aries\\tmp.g:181:2: ( conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )? )
            // org\\aries\\tmp.g:181:4: conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalExpression1225);
            conditionalOrExpression283=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalOrExpression283.getTree());

            // org\\aries\\tmp.g:181:28: ( '?' expressionDecl ':' conditionalExpression )?
            int alt33=2;
            switch ( input.LA(1) ) {
                case 70:
                    {
                    alt33=1;
                    }
                    break;
            }

            switch (alt33) {
                case 1 :
                    // org\\aries\\tmp.g:181:29: '?' expressionDecl ':' conditionalExpression
                    {
                    char_literal284=(Token)match(input,70,FOLLOW_70_in_conditionalExpression1228); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal284_tree = 
                    (Object)adaptor.create(char_literal284)
                    ;
                    adaptor.addChild(root_0, char_literal284_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_conditionalExpression1230);
                    expressionDecl285=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl285.getTree());

                    char_literal286=(Token)match(input,64,FOLLOW_64_in_conditionalExpression1232); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal286_tree = 
                    (Object)adaptor.create(char_literal286)
                    ;
                    adaptor.addChild(root_0, char_literal286_tree);
                    }

                    pushFollow(FOLLOW_conditionalExpression_in_conditionalExpression1234);
                    conditionalExpression287=conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalExpression287.getTree());

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
    // org\\aries\\tmp.g:184:1: conditionalOrExpression : conditionalAndExpression ( '||' conditionalAndExpression )* ;
    public final tmpParser.conditionalOrExpression_return conditionalOrExpression() throws RecognitionException {
        tmpParser.conditionalOrExpression_return retval = new tmpParser.conditionalOrExpression_return();
        retval.start = input.LT(1);

        int conditionalOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal289=null;
        tmpParser.conditionalAndExpression_return conditionalAndExpression288 =null;

        tmpParser.conditionalAndExpression_return conditionalAndExpression290 =null;


        Object string_literal289_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }

            // org\\aries\\tmp.g:185:2: ( conditionalAndExpression ( '||' conditionalAndExpression )* )
            // org\\aries\\tmp.g:185:4: conditionalAndExpression ( '||' conditionalAndExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1248);
            conditionalAndExpression288=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression288.getTree());

            // org\\aries\\tmp.g:185:29: ( '||' conditionalAndExpression )*
            loop34:
            do {
                int alt34=2;
                switch ( input.LA(1) ) {
                case 141:
                    {
                    alt34=1;
                    }
                    break;

                }

                switch (alt34) {
            	case 1 :
            	    // org\\aries\\tmp.g:185:30: '||' conditionalAndExpression
            	    {
            	    string_literal289=(Token)match(input,141,FOLLOW_141_in_conditionalOrExpression1251); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal289_tree = 
            	    (Object)adaptor.create(string_literal289)
            	    ;
            	    adaptor.addChild(root_0, string_literal289_tree);
            	    }

            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1253);
            	    conditionalAndExpression290=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression290.getTree());

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
    // org\\aries\\tmp.g:188:1: conditionalAndExpression : inclusiveOrExpression ( '&&' inclusiveOrExpression )* ;
    public final tmpParser.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        tmpParser.conditionalAndExpression_return retval = new tmpParser.conditionalAndExpression_return();
        retval.start = input.LT(1);

        int conditionalAndExpression_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal292=null;
        tmpParser.inclusiveOrExpression_return inclusiveOrExpression291 =null;

        tmpParser.inclusiveOrExpression_return inclusiveOrExpression293 =null;


        Object string_literal292_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }

            // org\\aries\\tmp.g:189:2: ( inclusiveOrExpression ( '&&' inclusiveOrExpression )* )
            // org\\aries\\tmp.g:189:4: inclusiveOrExpression ( '&&' inclusiveOrExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1267);
            inclusiveOrExpression291=inclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, inclusiveOrExpression291.getTree());

            // org\\aries\\tmp.g:189:26: ( '&&' inclusiveOrExpression )*
            loop35:
            do {
                int alt35=2;
                switch ( input.LA(1) ) {
                case 47:
                    {
                    alt35=1;
                    }
                    break;

                }

                switch (alt35) {
            	case 1 :
            	    // org\\aries\\tmp.g:189:27: '&&' inclusiveOrExpression
            	    {
            	    string_literal292=(Token)match(input,47,FOLLOW_47_in_conditionalAndExpression1270); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal292_tree = 
            	    (Object)adaptor.create(string_literal292)
            	    ;
            	    adaptor.addChild(root_0, string_literal292_tree);
            	    }

            	    pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1272);
            	    inclusiveOrExpression293=inclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, inclusiveOrExpression293.getTree());

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
    // org\\aries\\tmp.g:192:1: inclusiveOrExpression : exclusiveOrExpression ( '|' exclusiveOrExpression )* ;
    public final tmpParser.inclusiveOrExpression_return inclusiveOrExpression() throws RecognitionException {
        tmpParser.inclusiveOrExpression_return retval = new tmpParser.inclusiveOrExpression_return();
        retval.start = input.LT(1);

        int inclusiveOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal295=null;
        tmpParser.exclusiveOrExpression_return exclusiveOrExpression294 =null;

        tmpParser.exclusiveOrExpression_return exclusiveOrExpression296 =null;


        Object char_literal295_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }

            // org\\aries\\tmp.g:193:2: ( exclusiveOrExpression ( '|' exclusiveOrExpression )* )
            // org\\aries\\tmp.g:193:4: exclusiveOrExpression ( '|' exclusiveOrExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1286);
            exclusiveOrExpression294=exclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exclusiveOrExpression294.getTree());

            // org\\aries\\tmp.g:193:26: ( '|' exclusiveOrExpression )*
            loop36:
            do {
                int alt36=2;
                switch ( input.LA(1) ) {
                case 139:
                    {
                    alt36=1;
                    }
                    break;

                }

                switch (alt36) {
            	case 1 :
            	    // org\\aries\\tmp.g:193:27: '|' exclusiveOrExpression
            	    {
            	    char_literal295=(Token)match(input,139,FOLLOW_139_in_inclusiveOrExpression1289); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal295_tree = 
            	    (Object)adaptor.create(char_literal295)
            	    ;
            	    adaptor.addChild(root_0, char_literal295_tree);
            	    }

            	    pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1291);
            	    exclusiveOrExpression296=exclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exclusiveOrExpression296.getTree());

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
    // org\\aries\\tmp.g:196:1: exclusiveOrExpression : andExpression ( '^' andExpression )* ;
    public final tmpParser.exclusiveOrExpression_return exclusiveOrExpression() throws RecognitionException {
        tmpParser.exclusiveOrExpression_return retval = new tmpParser.exclusiveOrExpression_return();
        retval.start = input.LT(1);

        int exclusiveOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal298=null;
        tmpParser.andExpression_return andExpression297 =null;

        tmpParser.andExpression_return andExpression299 =null;


        Object char_literal298_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }

            // org\\aries\\tmp.g:197:2: ( andExpression ( '^' andExpression )* )
            // org\\aries\\tmp.g:197:4: andExpression ( '^' andExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression1305);
            andExpression297=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpression297.getTree());

            // org\\aries\\tmp.g:197:18: ( '^' andExpression )*
            loop37:
            do {
                int alt37=2;
                switch ( input.LA(1) ) {
                case 73:
                    {
                    alt37=1;
                    }
                    break;

                }

                switch (alt37) {
            	case 1 :
            	    // org\\aries\\tmp.g:197:19: '^' andExpression
            	    {
            	    char_literal298=(Token)match(input,73,FOLLOW_73_in_exclusiveOrExpression1308); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal298_tree = 
            	    (Object)adaptor.create(char_literal298)
            	    ;
            	    adaptor.addChild(root_0, char_literal298_tree);
            	    }

            	    pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression1310);
            	    andExpression299=andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpression299.getTree());

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
    // org\\aries\\tmp.g:200:1: andExpression : equalityExpression ( '&' equalityExpression )* ;
    public final tmpParser.andExpression_return andExpression() throws RecognitionException {
        tmpParser.andExpression_return retval = new tmpParser.andExpression_return();
        retval.start = input.LT(1);

        int andExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal301=null;
        tmpParser.equalityExpression_return equalityExpression300 =null;

        tmpParser.equalityExpression_return equalityExpression302 =null;


        Object char_literal301_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }

            // org\\aries\\tmp.g:201:2: ( equalityExpression ( '&' equalityExpression )* )
            // org\\aries\\tmp.g:201:4: equalityExpression ( '&' equalityExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_equalityExpression_in_andExpression1324);
            equalityExpression300=equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression300.getTree());

            // org\\aries\\tmp.g:201:23: ( '&' equalityExpression )*
            loop38:
            do {
                int alt38=2;
                switch ( input.LA(1) ) {
                case 48:
                    {
                    alt38=1;
                    }
                    break;

                }

                switch (alt38) {
            	case 1 :
            	    // org\\aries\\tmp.g:201:24: '&' equalityExpression
            	    {
            	    char_literal301=(Token)match(input,48,FOLLOW_48_in_andExpression1327); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal301_tree = 
            	    (Object)adaptor.create(char_literal301)
            	    ;
            	    adaptor.addChild(root_0, char_literal301_tree);
            	    }

            	    pushFollow(FOLLOW_equalityExpression_in_andExpression1329);
            	    equalityExpression302=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression302.getTree());

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
    // org\\aries\\tmp.g:204:1: equalityExpression : relationalExpression ( ( '==' | '!=' ) relationalExpression )* ;
    public final tmpParser.equalityExpression_return equalityExpression() throws RecognitionException {
        tmpParser.equalityExpression_return retval = new tmpParser.equalityExpression_return();
        retval.start = input.LT(1);

        int equalityExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set304=null;
        tmpParser.relationalExpression_return relationalExpression303 =null;

        tmpParser.relationalExpression_return relationalExpression305 =null;


        Object set304_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }

            // org\\aries\\tmp.g:205:2: ( relationalExpression ( ( '==' | '!=' ) relationalExpression )* )
            // org\\aries\\tmp.g:205:4: relationalExpression ( ( '==' | '!=' ) relationalExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpression_in_equalityExpression1343);
            relationalExpression303=relationalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression303.getTree());

            // org\\aries\\tmp.g:205:25: ( ( '==' | '!=' ) relationalExpression )*
            loop39:
            do {
                int alt39=2;
                switch ( input.LA(1) ) {
                case 44:
                case 68:
                    {
                    alt39=1;
                    }
                    break;

                }

                switch (alt39) {
            	case 1 :
            	    // org\\aries\\tmp.g:205:27: ( '==' | '!=' ) relationalExpression
            	    {
            	    set304=(Token)input.LT(1);

            	    if ( input.LA(1)==44||input.LA(1)==68 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set304)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression1355);
            	    relationalExpression305=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression305.getTree());

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
    // org\\aries\\tmp.g:208:1: relationalExpression : shiftExpression ( relationalOp shiftExpression )* ;
    public final tmpParser.relationalExpression_return relationalExpression() throws RecognitionException {
        tmpParser.relationalExpression_return retval = new tmpParser.relationalExpression_return();
        retval.start = input.LT(1);

        int relationalExpression_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.shiftExpression_return shiftExpression306 =null;

        tmpParser.relationalOp_return relationalOp307 =null;

        tmpParser.shiftExpression_return shiftExpression308 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }

            // org\\aries\\tmp.g:209:2: ( shiftExpression ( relationalOp shiftExpression )* )
            // org\\aries\\tmp.g:209:4: shiftExpression ( relationalOp shiftExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_shiftExpression_in_relationalExpression1369);
            shiftExpression306=shiftExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftExpression306.getTree());

            // org\\aries\\tmp.g:209:20: ( relationalOp shiftExpression )*
            loop40:
            do {
                int alt40=2;
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
                    case 91:
                    case 95:
                    case 102:
                    case 108:
                    case 129:
                    case 143:
                        {
                        alt40=1;
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
                    case 91:
                    case 95:
                    case 102:
                    case 108:
                    case 129:
                    case 143:
                        {
                        alt40=1;
                        }
                        break;

                    }

                    }
                    break;

                }

                switch (alt40) {
            	case 1 :
            	    // org\\aries\\tmp.g:209:21: relationalOp shiftExpression
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpression1372);
            	    relationalOp307=relationalOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalOp307.getTree());

            	    pushFollow(FOLLOW_shiftExpression_in_relationalExpression1374);
            	    shiftExpression308=shiftExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftExpression308.getTree());

            	    }
            	    break;

            	default :
            	    break loop40;
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
    // org\\aries\\tmp.g:212:1: relationalOp : ( '<' '=' | '>' '=' | '<' | '>' );
    public final tmpParser.relationalOp_return relationalOp() throws RecognitionException {
        tmpParser.relationalOp_return retval = new tmpParser.relationalOp_return();
        retval.start = input.LT(1);

        int relationalOp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal309=null;
        Token char_literal310=null;
        Token char_literal311=null;
        Token char_literal312=null;
        Token char_literal313=null;
        Token char_literal314=null;

        Object char_literal309_tree=null;
        Object char_literal310_tree=null;
        Object char_literal311_tree=null;
        Object char_literal312_tree=null;
        Object char_literal313_tree=null;
        Object char_literal314_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }

            // org\\aries\\tmp.g:213:2: ( '<' '=' | '>' '=' | '<' | '>' )
            int alt41=4;
            switch ( input.LA(1) ) {
            case 66:
                {
                switch ( input.LA(2) ) {
                case 67:
                    {
                    alt41=1;
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
                case 91:
                case 95:
                case 102:
                case 108:
                case 129:
                case 143:
                    {
                    alt41=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 41, 1, input);

                    throw nvae;

                }

                }
                break;
            case 69:
                {
                switch ( input.LA(2) ) {
                case 67:
                    {
                    alt41=2;
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
                case 91:
                case 95:
                case 102:
                case 108:
                case 129:
                case 143:
                    {
                    alt41=4;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 41, 2, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;

            }

            switch (alt41) {
                case 1 :
                    // org\\aries\\tmp.g:213:4: '<' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal309=(Token)match(input,66,FOLLOW_66_in_relationalOp1388); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal309_tree = 
                    (Object)adaptor.create(char_literal309)
                    ;
                    adaptor.addChild(root_0, char_literal309_tree);
                    }

                    char_literal310=(Token)match(input,67,FOLLOW_67_in_relationalOp1390); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal310_tree = 
                    (Object)adaptor.create(char_literal310)
                    ;
                    adaptor.addChild(root_0, char_literal310_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:214:4: '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal311=(Token)match(input,69,FOLLOW_69_in_relationalOp1395); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal311_tree = 
                    (Object)adaptor.create(char_literal311)
                    ;
                    adaptor.addChild(root_0, char_literal311_tree);
                    }

                    char_literal312=(Token)match(input,67,FOLLOW_67_in_relationalOp1397); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal312_tree = 
                    (Object)adaptor.create(char_literal312)
                    ;
                    adaptor.addChild(root_0, char_literal312_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:215:4: '<'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal313=(Token)match(input,66,FOLLOW_66_in_relationalOp1402); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal313_tree = 
                    (Object)adaptor.create(char_literal313)
                    ;
                    adaptor.addChild(root_0, char_literal313_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:216:4: '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal314=(Token)match(input,69,FOLLOW_69_in_relationalOp1407); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal314_tree = 
                    (Object)adaptor.create(char_literal314)
                    ;
                    adaptor.addChild(root_0, char_literal314_tree);
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
    // org\\aries\\tmp.g:219:1: shiftExpression : additiveExpression ( shiftOp additiveExpression )* ;
    public final tmpParser.shiftExpression_return shiftExpression() throws RecognitionException {
        tmpParser.shiftExpression_return retval = new tmpParser.shiftExpression_return();
        retval.start = input.LT(1);

        int shiftExpression_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.additiveExpression_return additiveExpression315 =null;

        tmpParser.shiftOp_return shiftOp316 =null;

        tmpParser.additiveExpression_return additiveExpression317 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }

            // org\\aries\\tmp.g:220:2: ( additiveExpression ( shiftOp additiveExpression )* )
            // org\\aries\\tmp.g:220:4: additiveExpression ( shiftOp additiveExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_additiveExpression_in_shiftExpression1419);
            additiveExpression315=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression315.getTree());

            // org\\aries\\tmp.g:220:23: ( shiftOp additiveExpression )*
            loop42:
            do {
                int alt42=2;
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
                        case 91:
                        case 95:
                        case 102:
                        case 108:
                        case 129:
                        case 143:
                            {
                            alt42=1;
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
                            case 91:
                            case 95:
                            case 102:
                            case 108:
                            case 129:
                            case 143:
                                {
                                alt42=1;
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
                        case 91:
                        case 95:
                        case 102:
                        case 108:
                        case 129:
                        case 143:
                            {
                            alt42=1;
                            }
                            break;

                        }

                        }
                        break;

                    }

                    }
                    break;

                }

                switch (alt42) {
            	case 1 :
            	    // org\\aries\\tmp.g:220:24: shiftOp additiveExpression
            	    {
            	    pushFollow(FOLLOW_shiftOp_in_shiftExpression1422);
            	    shiftOp316=shiftOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftOp316.getTree());

            	    pushFollow(FOLLOW_additiveExpression_in_shiftExpression1424);
            	    additiveExpression317=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression317.getTree());

            	    }
            	    break;

            	default :
            	    break loop42;
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
    // org\\aries\\tmp.g:223:1: shiftOp : ( '<' '<' | '>' '>' '>' | '>' '>' );
    public final tmpParser.shiftOp_return shiftOp() throws RecognitionException {
        tmpParser.shiftOp_return retval = new tmpParser.shiftOp_return();
        retval.start = input.LT(1);

        int shiftOp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal318=null;
        Token char_literal319=null;
        Token char_literal320=null;
        Token char_literal321=null;
        Token char_literal322=null;
        Token char_literal323=null;
        Token char_literal324=null;

        Object char_literal318_tree=null;
        Object char_literal319_tree=null;
        Object char_literal320_tree=null;
        Object char_literal321_tree=null;
        Object char_literal322_tree=null;
        Object char_literal323_tree=null;
        Object char_literal324_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }

            // org\\aries\\tmp.g:224:2: ( '<' '<' | '>' '>' '>' | '>' '>' )
            int alt43=3;
            switch ( input.LA(1) ) {
            case 66:
                {
                alt43=1;
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
                        alt43=2;
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
                    case 91:
                    case 95:
                    case 102:
                    case 108:
                    case 129:
                    case 143:
                        {
                        alt43=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 43, 3, input);

                        throw nvae;

                    }

                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 43, 2, input);

                    throw nvae;

                }

                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;

            }

            switch (alt43) {
                case 1 :
                    // org\\aries\\tmp.g:224:4: '<' '<'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal318=(Token)match(input,66,FOLLOW_66_in_shiftOp1438); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal318_tree = 
                    (Object)adaptor.create(char_literal318)
                    ;
                    adaptor.addChild(root_0, char_literal318_tree);
                    }

                    char_literal319=(Token)match(input,66,FOLLOW_66_in_shiftOp1440); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal319_tree = 
                    (Object)adaptor.create(char_literal319)
                    ;
                    adaptor.addChild(root_0, char_literal319_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:225:4: '>' '>' '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal320=(Token)match(input,69,FOLLOW_69_in_shiftOp1445); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal320_tree = 
                    (Object)adaptor.create(char_literal320)
                    ;
                    adaptor.addChild(root_0, char_literal320_tree);
                    }

                    char_literal321=(Token)match(input,69,FOLLOW_69_in_shiftOp1447); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal321_tree = 
                    (Object)adaptor.create(char_literal321)
                    ;
                    adaptor.addChild(root_0, char_literal321_tree);
                    }

                    char_literal322=(Token)match(input,69,FOLLOW_69_in_shiftOp1449); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal322_tree = 
                    (Object)adaptor.create(char_literal322)
                    ;
                    adaptor.addChild(root_0, char_literal322_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:226:4: '>' '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal323=(Token)match(input,69,FOLLOW_69_in_shiftOp1454); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal323_tree = 
                    (Object)adaptor.create(char_literal323)
                    ;
                    adaptor.addChild(root_0, char_literal323_tree);
                    }

                    char_literal324=(Token)match(input,69,FOLLOW_69_in_shiftOp1456); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal324_tree = 
                    (Object)adaptor.create(char_literal324)
                    ;
                    adaptor.addChild(root_0, char_literal324_tree);
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
    // org\\aries\\tmp.g:229:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final tmpParser.additiveExpression_return additiveExpression() throws RecognitionException {
        tmpParser.additiveExpression_return retval = new tmpParser.additiveExpression_return();
        retval.start = input.LT(1);

        int additiveExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set326=null;
        tmpParser.multiplicativeExpression_return multiplicativeExpression325 =null;

        tmpParser.multiplicativeExpression_return multiplicativeExpression327 =null;


        Object set326_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }

            // org\\aries\\tmp.g:230:2: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // org\\aries\\tmp.g:230:4: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1468);
            multiplicativeExpression325=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression325.getTree());

            // org\\aries\\tmp.g:230:29: ( ( '+' | '-' ) multiplicativeExpression )*
            loop44:
            do {
                int alt44=2;
                switch ( input.LA(1) ) {
                case 54:
                case 58:
                    {
                    alt44=1;
                    }
                    break;

                }

                switch (alt44) {
            	case 1 :
            	    // org\\aries\\tmp.g:230:31: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    set326=(Token)input.LT(1);

            	    if ( input.LA(1)==54||input.LA(1)==58 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set326)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1480);
            	    multiplicativeExpression327=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression327.getTree());

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
    // org\\aries\\tmp.g:233:1: multiplicativeExpression : unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* ;
    public final tmpParser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        tmpParser.multiplicativeExpression_return retval = new tmpParser.multiplicativeExpression_return();
        retval.start = input.LT(1);

        int multiplicativeExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set329=null;
        tmpParser.unaryExpression_return unaryExpression328 =null;

        tmpParser.unaryExpression_return unaryExpression330 =null;


        Object set329_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }

            // org\\aries\\tmp.g:234:2: ( unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* )
            // org\\aries\\tmp.g:234:4: unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1494);
            unaryExpression328=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression328.getTree());

            // org\\aries\\tmp.g:234:20: ( ( '*' | '/' | '%' ) unaryExpression )*
            loop45:
            do {
                int alt45=2;
                switch ( input.LA(1) ) {
                case 45:
                case 52:
                case 62:
                    {
                    alt45=1;
                    }
                    break;

                }

                switch (alt45) {
            	case 1 :
            	    // org\\aries\\tmp.g:234:22: ( '*' | '/' | '%' ) unaryExpression
            	    {
            	    set329=(Token)input.LT(1);

            	    if ( input.LA(1)==45||input.LA(1)==52||input.LA(1)==62 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set329)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1510);
            	    unaryExpression330=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression330.getTree());

            	    }
            	    break;

            	default :
            	    break loop45;
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
    // org\\aries\\tmp.g:241:1: unaryExpression : ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus );
    public final tmpParser.unaryExpression_return unaryExpression() throws RecognitionException {
        tmpParser.unaryExpression_return retval = new tmpParser.unaryExpression_return();
        retval.start = input.LT(1);

        int unaryExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal331=null;
        Token char_literal333=null;
        Token string_literal335=null;
        Token string_literal337=null;
        tmpParser.unaryExpression_return unaryExpression332 =null;

        tmpParser.unaryExpression_return unaryExpression334 =null;

        tmpParser.unaryExpression_return unaryExpression336 =null;

        tmpParser.unaryExpression_return unaryExpression338 =null;

        tmpParser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus339 =null;


        Object char_literal331_tree=null;
        Object char_literal333_tree=null;
        Object string_literal335_tree=null;
        Object string_literal337_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }

            // org\\aries\\tmp.g:242:2: ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus )
            int alt46=5;
            switch ( input.LA(1) ) {
            case 54:
                {
                alt46=1;
                }
                break;
            case 58:
                {
                alt46=2;
                }
                break;
            case 55:
                {
                alt46=3;
                }
                break;
            case 59:
                {
                alt46=4;
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
            case 91:
            case 95:
            case 102:
            case 108:
            case 129:
            case 143:
                {
                alt46=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;

            }

            switch (alt46) {
                case 1 :
                    // org\\aries\\tmp.g:242:4: '+' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal331=(Token)match(input,54,FOLLOW_54_in_unaryExpression1526); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal331_tree = 
                    (Object)adaptor.create(char_literal331)
                    ;
                    adaptor.addChild(root_0, char_literal331_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1528);
                    unaryExpression332=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression332.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:243:4: '-' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal333=(Token)match(input,58,FOLLOW_58_in_unaryExpression1533); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal333_tree = 
                    (Object)adaptor.create(char_literal333)
                    ;
                    adaptor.addChild(root_0, char_literal333_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1535);
                    unaryExpression334=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression334.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:244:4: '++' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal335=(Token)match(input,55,FOLLOW_55_in_unaryExpression1540); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal335_tree = 
                    (Object)adaptor.create(string_literal335)
                    ;
                    adaptor.addChild(root_0, string_literal335_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1542);
                    unaryExpression336=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression336.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:245:4: '--' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal337=(Token)match(input,59,FOLLOW_59_in_unaryExpression1547); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal337_tree = 
                    (Object)adaptor.create(string_literal337)
                    ;
                    adaptor.addChild(root_0, string_literal337_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1549);
                    unaryExpression338=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression338.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:246:4: unaryExpressionNotPlusMinus
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression1554);
                    unaryExpressionNotPlusMinus339=unaryExpressionNotPlusMinus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpressionNotPlusMinus339.getTree());

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
    // org\\aries\\tmp.g:249:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | primary ( selector )* ( '++' | '--' )? );
    public final tmpParser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus() throws RecognitionException {
        tmpParser.unaryExpressionNotPlusMinus_return retval = new tmpParser.unaryExpressionNotPlusMinus_return();
        retval.start = input.LT(1);

        int unaryExpressionNotPlusMinus_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal340=null;
        Token char_literal342=null;
        Token set346=null;
        tmpParser.unaryExpression_return unaryExpression341 =null;

        tmpParser.unaryExpression_return unaryExpression343 =null;

        tmpParser.primary_return primary344 =null;

        tmpParser.selector_return selector345 =null;


        Object char_literal340_tree=null;
        Object char_literal342_tree=null;
        Object set346_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }

            // org\\aries\\tmp.g:250:2: ( '~' unaryExpression | '!' unaryExpression | primary ( selector )* ( '++' | '--' )? )
            int alt49=3;
            switch ( input.LA(1) ) {
            case 143:
                {
                alt49=1;
                }
                break;
            case 43:
                {
                alt49=2;
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
            case 91:
            case 95:
            case 102:
            case 108:
            case 129:
                {
                alt49=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;

            }

            switch (alt49) {
                case 1 :
                    // org\\aries\\tmp.g:250:4: '~' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal340=(Token)match(input,143,FOLLOW_143_in_unaryExpressionNotPlusMinus1566); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal340_tree = 
                    (Object)adaptor.create(char_literal340)
                    ;
                    adaptor.addChild(root_0, char_literal340_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1568);
                    unaryExpression341=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression341.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:251:4: '!' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal342=(Token)match(input,43,FOLLOW_43_in_unaryExpressionNotPlusMinus1573); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal342_tree = 
                    (Object)adaptor.create(char_literal342)
                    ;
                    adaptor.addChild(root_0, char_literal342_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1575);
                    unaryExpression343=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression343.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:252:4: primary ( selector )* ( '++' | '--' )?
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_primary_in_unaryExpressionNotPlusMinus1580);
                    primary344=primary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary344.getTree());

                    // org\\aries\\tmp.g:252:12: ( selector )*
                    loop47:
                    do {
                        int alt47=2;
                        switch ( input.LA(1) ) {
                        case 50:
                        case 61:
                        case 71:
                            {
                            alt47=1;
                            }
                            break;

                        }

                        switch (alt47) {
                    	case 1 :
                    	    // org\\aries\\tmp.g:252:13: selector
                    	    {
                    	    pushFollow(FOLLOW_selector_in_unaryExpressionNotPlusMinus1583);
                    	    selector345=selector();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, selector345.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);


                    // org\\aries\\tmp.g:252:24: ( '++' | '--' )?
                    int alt48=2;
                    switch ( input.LA(1) ) {
                        case 55:
                        case 59:
                            {
                            alt48=1;
                            }
                            break;
                    }

                    switch (alt48) {
                        case 1 :
                            // org\\aries\\tmp.g:
                            {
                            set346=(Token)input.LT(1);

                            if ( input.LA(1)==55||input.LA(1)==59 ) {
                                input.consume();
                                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                                (Object)adaptor.create(set346)
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
    // org\\aries\\tmp.g:255:1: primary : ( '(' expressionDecl ')' -> ^( PRIMARY '(' expressionDecl ')' ) | ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' ) -> ^( PRIMARY qualifiedName '(' ')' ) | ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' ) -> ^( PRIMARY qualifiedName '(' expressionList ')' ) | ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' ) -> ^( PRIMARY qualifiedName '[' expressionDecl ']' ) | ( type Identifier '=' )=> type Identifier -> ^( PRIMARY type Identifier ) | ( qualifiedName '.' )=> qualifiedName -> ^( PRIMARY qualifiedName ) | qualifiedName -> ^( PRIMARY qualifiedName ) | literal -> ^( PRIMARY literal ) );
    public final tmpParser.primary_return primary() throws RecognitionException {
        tmpParser.primary_return retval = new tmpParser.primary_return();
        retval.start = input.LT(1);

        int primary_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal347=null;
        Token char_literal349=null;
        Token char_literal351=null;
        Token char_literal352=null;
        Token char_literal354=null;
        Token char_literal356=null;
        Token char_literal358=null;
        Token char_literal360=null;
        Token Identifier362=null;
        tmpParser.expressionDecl_return expressionDecl348 =null;

        tmpParser.qualifiedName_return qualifiedName350 =null;

        tmpParser.qualifiedName_return qualifiedName353 =null;

        tmpParser.expressionList_return expressionList355 =null;

        tmpParser.qualifiedName_return qualifiedName357 =null;

        tmpParser.expressionDecl_return expressionDecl359 =null;

        tmpParser.type_return type361 =null;

        tmpParser.qualifiedName_return qualifiedName363 =null;

        tmpParser.qualifiedName_return qualifiedName364 =null;

        tmpParser.literal_return literal365 =null;


        Object char_literal347_tree=null;
        Object char_literal349_tree=null;
        Object char_literal351_tree=null;
        Object char_literal352_tree=null;
        Object char_literal354_tree=null;
        Object char_literal356_tree=null;
        Object char_literal358_tree=null;
        Object char_literal360_tree=null;
        Object Identifier362_tree=null;
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

            // org\\aries\\tmp.g:256:2: ( '(' expressionDecl ')' -> ^( PRIMARY '(' expressionDecl ')' ) | ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' ) -> ^( PRIMARY qualifiedName '(' ')' ) | ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' ) -> ^( PRIMARY qualifiedName '(' expressionList ')' ) | ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' ) -> ^( PRIMARY qualifiedName '[' expressionDecl ']' ) | ( type Identifier '=' )=> type Identifier -> ^( PRIMARY type Identifier ) | ( qualifiedName '.' )=> qualifiedName -> ^( PRIMARY qualifiedName ) | qualifiedName -> ^( PRIMARY qualifiedName ) | literal -> ^( PRIMARY literal ) )
            int alt50=8;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==50) ) {
                alt50=1;
            }
            else if ( (LA50_0==Identifier) ) {
                int LA50_2 = input.LA(2);

                if ( (synpred2_tmp()) ) {
                    alt50=2;
                }
                else if ( (synpred3_tmp()) ) {
                    alt50=3;
                }
                else if ( (synpred4_tmp()) ) {
                    alt50=4;
                }
                else if ( (synpred5_tmp()) ) {
                    alt50=5;
                }
                else if ( (synpred6_tmp()) ) {
                    alt50=6;
                }
                else if ( (true) ) {
                    alt50=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 50, 2, input);

                    throw nvae;

                }
            }
            else if ( (LA50_0==EXCEPTION||LA50_0==MESSAGE) ) {
                int LA50_3 = input.LA(2);

                if ( (synpred2_tmp()) ) {
                    alt50=2;
                }
                else if ( (synpred3_tmp()) ) {
                    alt50=3;
                }
                else if ( (synpred4_tmp()) ) {
                    alt50=4;
                }
                else if ( (synpred5_tmp()) ) {
                    alt50=5;
                }
                else if ( (synpred6_tmp()) ) {
                    alt50=6;
                }
                else if ( (true) ) {
                    alt50=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 50, 3, input);

                    throw nvae;

                }
            }
            else if ( (LA50_0==78||LA50_0==81||LA50_0==84||LA50_0==91||LA50_0==95||LA50_0==102||LA50_0==108||LA50_0==129) && (synpred5_tmp())) {
                alt50=5;
            }
            else if ( (LA50_0==CHARLITERAL||LA50_0==DoubleLiteral||(LA50_0 >= FALSE && LA50_0 <= FloatLiteral)||LA50_0==IntegerLiteral||LA50_0==NULL||LA50_0==STRINGLITERAL||LA50_0==TRUE) ) {
                alt50=8;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;

            }
            switch (alt50) {
                case 1 :
                    // org\\aries\\tmp.g:256:4: '(' expressionDecl ')'
                    {
                    char_literal347=(Token)match(input,50,FOLLOW_50_in_primary1606); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal347);


                    pushFollow(FOLLOW_expressionDecl_in_primary1608);
                    expressionDecl348=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl348.getTree());

                    char_literal349=(Token)match(input,51,FOLLOW_51_in_primary1610); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal349);


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
                    // 256:27: -> ^( PRIMARY '(' expressionDecl ')' )
                    {
                        // org\\aries\\tmp.g:256:30: ^( PRIMARY '(' expressionDecl ')' )
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
                    // org\\aries\\tmp.g:257:4: ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' )
                    {
                    // org\\aries\\tmp.g:257:31: ( qualifiedName '(' ')' )
                    // org\\aries\\tmp.g:257:32: qualifiedName '(' ')'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1638);
                    qualifiedName350=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName350.getTree());

                    char_literal351=(Token)match(input,50,FOLLOW_50_in_primary1640); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal351);


                    char_literal352=(Token)match(input,51,FOLLOW_51_in_primary1642); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal352);


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
                    // 257:55: -> ^( PRIMARY qualifiedName '(' ')' )
                    {
                        // org\\aries\\tmp.g:257:58: ^( PRIMARY qualifiedName '(' ')' )
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
                    // org\\aries\\tmp.g:258:4: ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' )
                    {
                    // org\\aries\\tmp.g:258:27: ( qualifiedName '(' expressionList ')' )
                    // org\\aries\\tmp.g:258:28: qualifiedName '(' expressionList ')'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1669);
                    qualifiedName353=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName353.getTree());

                    char_literal354=(Token)match(input,50,FOLLOW_50_in_primary1671); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal354);


                    pushFollow(FOLLOW_expressionList_in_primary1673);
                    expressionList355=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionList.add(expressionList355.getTree());

                    char_literal356=(Token)match(input,51,FOLLOW_51_in_primary1675); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal356);


                    }


                    // AST REWRITE
                    // elements: expressionList, 51, 50, qualifiedName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 258:66: -> ^( PRIMARY qualifiedName '(' expressionList ')' )
                    {
                        // org\\aries\\tmp.g:258:69: ^( PRIMARY qualifiedName '(' expressionList ')' )
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
                    // org\\aries\\tmp.g:259:4: ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' )
                    {
                    // org\\aries\\tmp.g:259:27: ( qualifiedName '[' expressionDecl ']' )
                    // org\\aries\\tmp.g:259:28: qualifiedName '[' expressionDecl ']'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1704);
                    qualifiedName357=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName357.getTree());

                    char_literal358=(Token)match(input,71,FOLLOW_71_in_primary1706); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_71.add(char_literal358);


                    pushFollow(FOLLOW_expressionDecl_in_primary1708);
                    expressionDecl359=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl359.getTree());

                    char_literal360=(Token)match(input,72,FOLLOW_72_in_primary1710); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_72.add(char_literal360);


                    }


                    // AST REWRITE
                    // elements: qualifiedName, expressionDecl, 72, 71
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 259:66: -> ^( PRIMARY qualifiedName '[' expressionDecl ']' )
                    {
                        // org\\aries\\tmp.g:259:69: ^( PRIMARY qualifiedName '[' expressionDecl ']' )
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
                    // org\\aries\\tmp.g:260:4: ( type Identifier '=' )=> type Identifier
                    {
                    pushFollow(FOLLOW_type_in_primary1740);
                    type361=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_type.add(type361.getTree());

                    Identifier362=(Token)match(input,Identifier,FOLLOW_Identifier_in_primary1742); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier362);


                    // AST REWRITE
                    // elements: Identifier, type
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 260:45: -> ^( PRIMARY type Identifier )
                    {
                        // org\\aries\\tmp.g:260:48: ^( PRIMARY type Identifier )
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
                    // org\\aries\\tmp.g:261:4: ( qualifiedName '.' )=> qualifiedName
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1765);
                    qualifiedName363=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName363.getTree());

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
                    // 261:41: -> ^( PRIMARY qualifiedName )
                    {
                        // org\\aries\\tmp.g:261:44: ^( PRIMARY qualifiedName )
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
                    // org\\aries\\tmp.g:262:4: qualifiedName
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1778);
                    qualifiedName364=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName364.getTree());

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
                    // 262:18: -> ^( PRIMARY qualifiedName )
                    {
                        // org\\aries\\tmp.g:262:21: ^( PRIMARY qualifiedName )
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
                    // org\\aries\\tmp.g:263:4: literal
                    {
                    pushFollow(FOLLOW_literal_in_primary1791);
                    literal365=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_literal.add(literal365.getTree());

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
                    // 263:12: -> ^( PRIMARY literal )
                    {
                        // org\\aries\\tmp.g:263:15: ^( PRIMARY literal )
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
    // org\\aries\\tmp.g:266:1: selector : ( ( '(' )=> arguments | ( '.' )=> '.' Identifier arguments | ( '[' )=> '[' expressionDecl ']' );
    public final tmpParser.selector_return selector() throws RecognitionException {
        tmpParser.selector_return retval = new tmpParser.selector_return();
        retval.start = input.LT(1);

        int selector_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal367=null;
        Token Identifier368=null;
        Token char_literal370=null;
        Token char_literal372=null;
        tmpParser.arguments_return arguments366 =null;

        tmpParser.arguments_return arguments369 =null;

        tmpParser.expressionDecl_return expressionDecl371 =null;


        Object char_literal367_tree=null;
        Object Identifier368_tree=null;
        Object char_literal370_tree=null;
        Object char_literal372_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }

            // org\\aries\\tmp.g:267:2: ( ( '(' )=> arguments | ( '.' )=> '.' Identifier arguments | ( '[' )=> '[' expressionDecl ']' )
            int alt51=3;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==50) && (synpred7_tmp())) {
                alt51=1;
            }
            else if ( (LA51_0==61) && (synpred8_tmp())) {
                alt51=2;
            }
            else if ( (LA51_0==71) && (synpred9_tmp())) {
                alt51=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;

            }
            switch (alt51) {
                case 1 :
                    // org\\aries\\tmp.g:267:4: ( '(' )=> arguments
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_arguments_in_selector1818);
                    arguments366=arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arguments366.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:268:4: ( '.' )=> '.' Identifier arguments
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal367=(Token)match(input,61,FOLLOW_61_in_selector1829); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal367_tree = 
                    (Object)adaptor.create(char_literal367)
                    ;
                    adaptor.addChild(root_0, char_literal367_tree);
                    }

                    Identifier368=(Token)match(input,Identifier,FOLLOW_Identifier_in_selector1831); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier368_tree = 
                    (Object)adaptor.create(Identifier368)
                    ;
                    adaptor.addChild(root_0, Identifier368_tree);
                    }

                    pushFollow(FOLLOW_arguments_in_selector1833);
                    arguments369=arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arguments369.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:269:4: ( '[' )=> '[' expressionDecl ']'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal370=(Token)match(input,71,FOLLOW_71_in_selector1844); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal370_tree = 
                    (Object)adaptor.create(char_literal370)
                    ;
                    adaptor.addChild(root_0, char_literal370_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_selector1846);
                    expressionDecl371=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl371.getTree());

                    char_literal372=(Token)match(input,72,FOLLOW_72_in_selector1848); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal372_tree = 
                    (Object)adaptor.create(char_literal372)
                    ;
                    adaptor.addChild(root_0, char_literal372_tree);
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
    // org\\aries\\tmp.g:272:1: type : ( primitiveType ( '[' ']' )* -> ^( TYPE primitiveType ( '[' ']' )* ) | qualifiedName ( typeArguments )? ( '[' ']' )* -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* ) );
    public final tmpParser.type_return type() throws RecognitionException {
        tmpParser.type_return retval = new tmpParser.type_return();
        retval.start = input.LT(1);

        int type_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal374=null;
        Token char_literal375=null;
        Token char_literal378=null;
        Token char_literal379=null;
        tmpParser.primitiveType_return primitiveType373 =null;

        tmpParser.qualifiedName_return qualifiedName376 =null;

        tmpParser.typeArguments_return typeArguments377 =null;


        Object char_literal374_tree=null;
        Object char_literal375_tree=null;
        Object char_literal378_tree=null;
        Object char_literal379_tree=null;
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleSubtreeStream stream_primitiveType=new RewriteRuleSubtreeStream(adaptor,"rule primitiveType");
        RewriteRuleSubtreeStream stream_qualifiedName=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedName");
        RewriteRuleSubtreeStream stream_typeArguments=new RewriteRuleSubtreeStream(adaptor,"rule typeArguments");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }

            // org\\aries\\tmp.g:273:2: ( primitiveType ( '[' ']' )* -> ^( TYPE primitiveType ( '[' ']' )* ) | qualifiedName ( typeArguments )? ( '[' ']' )* -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* ) )
            int alt55=2;
            switch ( input.LA(1) ) {
            case 78:
            case 81:
            case 84:
            case 91:
            case 95:
            case 102:
            case 108:
            case 129:
                {
                alt55=1;
                }
                break;
            case EXCEPTION:
            case Identifier:
            case MESSAGE:
                {
                alt55=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;

            }

            switch (alt55) {
                case 1 :
                    // org\\aries\\tmp.g:273:4: primitiveType ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_primitiveType_in_type1861);
                    primitiveType373=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primitiveType.add(primitiveType373.getTree());

                    // org\\aries\\tmp.g:273:18: ( '[' ']' )*
                    loop52:
                    do {
                        int alt52=2;
                        switch ( input.LA(1) ) {
                        case 71:
                            {
                            alt52=1;
                            }
                            break;

                        }

                        switch (alt52) {
                    	case 1 :
                    	    // org\\aries\\tmp.g:273:19: '[' ']'
                    	    {
                    	    char_literal374=(Token)match(input,71,FOLLOW_71_in_type1864); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_71.add(char_literal374);


                    	    char_literal375=(Token)match(input,72,FOLLOW_72_in_type1866); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_72.add(char_literal375);


                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);


                    // AST REWRITE
                    // elements: 71, primitiveType, 72
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 273:29: -> ^( TYPE primitiveType ( '[' ']' )* )
                    {
                        // org\\aries\\tmp.g:273:32: ^( TYPE primitiveType ( '[' ']' )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(TYPE, "TYPE")
                        , root_1);

                        adaptor.addChild(root_1, stream_primitiveType.nextTree());

                        // org\\aries\\tmp.g:273:53: ( '[' ']' )*
                        while ( stream_71.hasNext()||stream_72.hasNext() ) {
                            adaptor.addChild(root_1, 
                            stream_71.nextNode()
                            );

                            adaptor.addChild(root_1, 
                            stream_72.nextNode()
                            );

                        }
                        stream_71.reset();
                        stream_72.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:274:4: qualifiedName ( typeArguments )? ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_qualifiedName_in_type1888);
                    qualifiedName376=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName376.getTree());

                    // org\\aries\\tmp.g:274:18: ( typeArguments )?
                    int alt53=2;
                    switch ( input.LA(1) ) {
                        case 66:
                            {
                            alt53=1;
                            }
                            break;
                    }

                    switch (alt53) {
                        case 1 :
                            // org\\aries\\tmp.g:274:18: typeArguments
                            {
                            pushFollow(FOLLOW_typeArguments_in_type1890);
                            typeArguments377=typeArguments();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_typeArguments.add(typeArguments377.getTree());

                            }
                            break;

                    }


                    // org\\aries\\tmp.g:274:33: ( '[' ']' )*
                    loop54:
                    do {
                        int alt54=2;
                        switch ( input.LA(1) ) {
                        case 71:
                            {
                            alt54=1;
                            }
                            break;

                        }

                        switch (alt54) {
                    	case 1 :
                    	    // org\\aries\\tmp.g:274:34: '[' ']'
                    	    {
                    	    char_literal378=(Token)match(input,71,FOLLOW_71_in_type1894); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_71.add(char_literal378);


                    	    char_literal379=(Token)match(input,72,FOLLOW_72_in_type1896); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_72.add(char_literal379);


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);


                    // AST REWRITE
                    // elements: 71, qualifiedName, 72, typeArguments
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 274:44: -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* )
                    {
                        // org\\aries\\tmp.g:274:47: ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(TYPE, "TYPE")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        // org\\aries\\tmp.g:274:68: ( typeArguments )?
                        if ( stream_typeArguments.hasNext() ) {
                            adaptor.addChild(root_1, stream_typeArguments.nextTree());

                        }
                        stream_typeArguments.reset();

                        // org\\aries\\tmp.g:274:83: ( '[' ']' )*
                        while ( stream_71.hasNext()||stream_72.hasNext() ) {
                            adaptor.addChild(root_1, 
                            stream_71.nextNode()
                            );

                            adaptor.addChild(root_1, 
                            stream_72.nextNode()
                            );

                        }
                        stream_71.reset();
                        stream_72.reset();

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
    // org\\aries\\tmp.g:277:1: typeList : type ( ',' type )* ;
    public final tmpParser.typeList_return typeList() throws RecognitionException {
        tmpParser.typeList_return retval = new tmpParser.typeList_return();
        retval.start = input.LT(1);

        int typeList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal381=null;
        tmpParser.type_return type380 =null;

        tmpParser.type_return type382 =null;


        Object char_literal381_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }

            // org\\aries\\tmp.g:278:2: ( type ( ',' type )* )
            // org\\aries\\tmp.g:278:4: type ( ',' type )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_typeList1928);
            type380=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type380.getTree());

            // org\\aries\\tmp.g:278:9: ( ',' type )*
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
            	    // org\\aries\\tmp.g:278:10: ',' type
            	    {
            	    char_literal381=(Token)match(input,57,FOLLOW_57_in_typeList1931); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal381_tree = 
            	    (Object)adaptor.create(char_literal381)
            	    ;
            	    adaptor.addChild(root_0, char_literal381_tree);
            	    }

            	    pushFollow(FOLLOW_type_in_typeList1933);
            	    type382=type();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, type382.getTree());

            	    }
            	    break;

            	default :
            	    break loop56;
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
    // org\\aries\\tmp.g:281:1: typeArguments : '<' typeArgument ( ',' typeArgument )* '>' ;
    public final tmpParser.typeArguments_return typeArguments() throws RecognitionException {
        tmpParser.typeArguments_return retval = new tmpParser.typeArguments_return();
        retval.start = input.LT(1);

        int typeArguments_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal383=null;
        Token char_literal385=null;
        Token char_literal387=null;
        tmpParser.typeArgument_return typeArgument384 =null;

        tmpParser.typeArgument_return typeArgument386 =null;


        Object char_literal383_tree=null;
        Object char_literal385_tree=null;
        Object char_literal387_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }

            // org\\aries\\tmp.g:282:2: ( '<' typeArgument ( ',' typeArgument )* '>' )
            // org\\aries\\tmp.g:282:4: '<' typeArgument ( ',' typeArgument )* '>'
            {
            root_0 = (Object)adaptor.nil();


            char_literal383=(Token)match(input,66,FOLLOW_66_in_typeArguments1947); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal383_tree = 
            (Object)adaptor.create(char_literal383)
            ;
            adaptor.addChild(root_0, char_literal383_tree);
            }

            pushFollow(FOLLOW_typeArgument_in_typeArguments1949);
            typeArgument384=typeArgument();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeArgument384.getTree());

            // org\\aries\\tmp.g:282:21: ( ',' typeArgument )*
            loop57:
            do {
                int alt57=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt57=1;
                    }
                    break;

                }

                switch (alt57) {
            	case 1 :
            	    // org\\aries\\tmp.g:282:22: ',' typeArgument
            	    {
            	    char_literal385=(Token)match(input,57,FOLLOW_57_in_typeArguments1952); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal385_tree = 
            	    (Object)adaptor.create(char_literal385)
            	    ;
            	    adaptor.addChild(root_0, char_literal385_tree);
            	    }

            	    pushFollow(FOLLOW_typeArgument_in_typeArguments1954);
            	    typeArgument386=typeArgument();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, typeArgument386.getTree());

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


            char_literal387=(Token)match(input,69,FOLLOW_69_in_typeArguments1958); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal387_tree = 
            (Object)adaptor.create(char_literal387)
            ;
            adaptor.addChild(root_0, char_literal387_tree);
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
    // org\\aries\\tmp.g:285:1: typeArgument : type ;
    public final tmpParser.typeArgument_return typeArgument() throws RecognitionException {
        tmpParser.typeArgument_return retval = new tmpParser.typeArgument_return();
        retval.start = input.LT(1);

        int typeArgument_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.type_return type388 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }

            // org\\aries\\tmp.g:286:2: ( type )
            // org\\aries\\tmp.g:286:4: type
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_typeArgument1970);
            type388=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type388.getTree());

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
    // org\\aries\\tmp.g:289:1: primitiveType : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final tmpParser.primitiveType_return primitiveType() throws RecognitionException {
        tmpParser.primitiveType_return retval = new tmpParser.primitiveType_return();
        retval.start = input.LT(1);

        int primitiveType_StartIndex = input.index();

        Object root_0 = null;

        Token set389=null;

        Object set389_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }

            // org\\aries\\tmp.g:290:2: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            // org\\aries\\tmp.g:
            {
            root_0 = (Object)adaptor.nil();


            set389=(Token)input.LT(1);

            if ( input.LA(1)==78||input.LA(1)==81||input.LA(1)==84||input.LA(1)==91||input.LA(1)==95||input.LA(1)==102||input.LA(1)==108||input.LA(1)==129 ) {
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
    // org\\aries\\tmp.g:300:1: arguments : '(' ( expressionList )? ')' ;
    public final tmpParser.arguments_return arguments() throws RecognitionException {
        tmpParser.arguments_return retval = new tmpParser.arguments_return();
        retval.start = input.LT(1);

        int arguments_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal390=null;
        Token char_literal392=null;
        tmpParser.expressionList_return expressionList391 =null;


        Object char_literal390_tree=null;
        Object char_literal392_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }

            // org\\aries\\tmp.g:301:2: ( '(' ( expressionList )? ')' )
            // org\\aries\\tmp.g:301:4: '(' ( expressionList )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal390=(Token)match(input,50,FOLLOW_50_in_arguments2030); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal390_tree = 
            (Object)adaptor.create(char_literal390)
            ;
            adaptor.addChild(root_0, char_literal390_tree);
            }

            // org\\aries\\tmp.g:301:8: ( expressionList )?
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
                case 81:
                case 84:
                case 91:
                case 95:
                case 102:
                case 108:
                case 113:
                case 129:
                case 143:
                    {
                    alt58=1;
                    }
                    break;
            }

            switch (alt58) {
                case 1 :
                    // org\\aries\\tmp.g:301:9: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_arguments2033);
                    expressionList391=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList391.getTree());

                    }
                    break;

            }


            char_literal392=(Token)match(input,51,FOLLOW_51_in_arguments2037); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal392_tree = 
            (Object)adaptor.create(char_literal392)
            ;
            adaptor.addChild(root_0, char_literal392_tree);
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
    // org\\aries\\tmp.g:304:1: literal : ( IntegerLiteral | FloatLiteral | DoubleLiteral | CHARLITERAL | STRINGLITERAL | TRUE | FALSE | NULL );
    public final tmpParser.literal_return literal() throws RecognitionException {
        tmpParser.literal_return retval = new tmpParser.literal_return();
        retval.start = input.LT(1);

        int literal_StartIndex = input.index();

        Object root_0 = null;

        Token set393=null;

        Object set393_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }

            // org\\aries\\tmp.g:305:2: ( IntegerLiteral | FloatLiteral | DoubleLiteral | CHARLITERAL | STRINGLITERAL | TRUE | FALSE | NULL )
            // org\\aries\\tmp.g:
            {
            root_0 = (Object)adaptor.nil();


            set393=(Token)input.LT(1);

            if ( input.LA(1)==CHARLITERAL||input.LA(1)==DoubleLiteral||(input.LA(1) >= FALSE && input.LA(1) <= FloatLiteral)||input.LA(1)==IntegerLiteral||input.LA(1)==NULL||input.LA(1)==STRINGLITERAL||input.LA(1)==TRUE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set393)
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


    public static class doneDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "doneDecl"
    // org\\aries\\tmp.g:316:1: doneDecl : 'done' ';' ;
    public final tmpParser.doneDecl_return doneDecl() throws RecognitionException {
        tmpParser.doneDecl_return retval = new tmpParser.doneDecl_return();
        retval.start = input.LT(1);

        int doneDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal394=null;
        Token char_literal395=null;

        Object string_literal394_tree=null;
        Object char_literal395_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }

            // org\\aries\\tmp.g:317:2: ( 'done' ';' )
            // org\\aries\\tmp.g:317:4: 'done' ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal394=(Token)match(input,90,FOLLOW_90_in_doneDecl2098); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal394_tree = 
            (Object)adaptor.create(string_literal394)
            ;
            adaptor.addChild(root_0, string_literal394_tree);
            }

            char_literal395=(Token)match(input,65,FOLLOW_65_in_doneDecl2100); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 45, doneDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "doneDecl"


    public static class exceptionDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exceptionDecl"
    // org\\aries\\tmp.g:320:1: exceptionDecl : EXCEPTION ^ ':' exceptionBody ;
    public final tmpParser.exceptionDecl_return exceptionDecl() throws RecognitionException {
        tmpParser.exceptionDecl_return retval = new tmpParser.exceptionDecl_return();
        retval.start = input.LT(1);

        int exceptionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token EXCEPTION396=null;
        Token char_literal397=null;
        tmpParser.exceptionBody_return exceptionBody398 =null;


        Object EXCEPTION396_tree=null;
        Object char_literal397_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }

            // org\\aries\\tmp.g:321:2: ( EXCEPTION ^ ':' exceptionBody )
            // org\\aries\\tmp.g:321:4: EXCEPTION ^ ':' exceptionBody
            {
            root_0 = (Object)adaptor.nil();


            EXCEPTION396=(Token)match(input,EXCEPTION,FOLLOW_EXCEPTION_in_exceptionDecl2111); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EXCEPTION396_tree = 
            (Object)adaptor.create(EXCEPTION396)
            ;
            root_0 = (Object)adaptor.becomeRoot(EXCEPTION396_tree, root_0);
            }

            char_literal397=(Token)match(input,64,FOLLOW_64_in_exceptionDecl2114); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal397_tree = 
            (Object)adaptor.create(char_literal397)
            ;
            adaptor.addChild(root_0, char_literal397_tree);
            }

            pushFollow(FOLLOW_exceptionBody_in_exceptionDecl2116);
            exceptionBody398=exceptionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionBody398.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 46, exceptionDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exceptionDecl"


    public static class exceptionBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exceptionBody"
    // org\\aries\\tmp.g:324:1: exceptionBody : '{' ( exceptionMember )* '}' ;
    public final tmpParser.exceptionBody_return exceptionBody() throws RecognitionException {
        tmpParser.exceptionBody_return retval = new tmpParser.exceptionBody_return();
        retval.start = input.LT(1);

        int exceptionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal399=null;
        Token char_literal401=null;
        tmpParser.exceptionMember_return exceptionMember400 =null;


        Object char_literal399_tree=null;
        Object char_literal401_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }

            // org\\aries\\tmp.g:325:2: ( '{' ( exceptionMember )* '}' )
            // org\\aries\\tmp.g:325:4: '{' ( exceptionMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal399=(Token)match(input,138,FOLLOW_138_in_exceptionBody2127); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal399_tree = 
            (Object)adaptor.create(char_literal399)
            ;
            adaptor.addChild(root_0, char_literal399_tree);
            }

            // org\\aries\\tmp.g:325:8: ( exceptionMember )*
            loop59:
            do {
                int alt59=2;
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 108:
                case 113:
                case 114:
                case 117:
                case 120:
                case 122:
                case 126:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt59=1;
                    }
                    break;

                }

                switch (alt59) {
            	case 1 :
            	    // org\\aries\\tmp.g:325:9: exceptionMember
            	    {
            	    pushFollow(FOLLOW_exceptionMember_in_exceptionBody2130);
            	    exceptionMember400=exceptionMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionMember400.getTree());

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);


            char_literal401=(Token)match(input,142,FOLLOW_142_in_exceptionBody2134); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal401_tree = 
            (Object)adaptor.create(char_literal401)
            ;
            adaptor.addChild(root_0, char_literal401_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 47, exceptionBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exceptionBody"


    public static class exceptionMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exceptionMember"
    // org\\aries\\tmp.g:328:1: exceptionMember : ( optionDecl | statementDecl | doneDecl );
    public final tmpParser.exceptionMember_return exceptionMember() throws RecognitionException {
        tmpParser.exceptionMember_return retval = new tmpParser.exceptionMember_return();
        retval.start = input.LT(1);

        int exceptionMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.optionDecl_return optionDecl402 =null;

        tmpParser.statementDecl_return statementDecl403 =null;

        tmpParser.doneDecl_return doneDecl404 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }

            // org\\aries\\tmp.g:329:2: ( optionDecl | statementDecl | doneDecl )
            int alt60=3;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt60=1;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
                {
                alt60=2;
                }
                break;
            case 90:
                {
                alt60=3;
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
                    // org\\aries\\tmp.g:329:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_exceptionMember2145);
                    optionDecl402=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl402.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:330:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_exceptionMember2150);
                    statementDecl403=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl403.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:332:4: doneDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_doneDecl_in_exceptionMember2156);
                    doneDecl404=doneDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doneDecl404.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 48, exceptionMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "exceptionMember"


    public static class executeDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeDecl"
    // org\\aries\\tmp.g:335:1: executeDecl : ( 'execute' ^ executeBody | 'execute' ^ 'then' Identifier formalParameters executeBody );
    public final tmpParser.executeDecl_return executeDecl() throws RecognitionException {
        tmpParser.executeDecl_return retval = new tmpParser.executeDecl_return();
        retval.start = input.LT(1);

        int executeDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal405=null;
        Token string_literal407=null;
        Token string_literal408=null;
        Token Identifier409=null;
        tmpParser.executeBody_return executeBody406 =null;

        tmpParser.formalParameters_return formalParameters410 =null;

        tmpParser.executeBody_return executeBody411 =null;


        Object string_literal405_tree=null;
        Object string_literal407_tree=null;
        Object string_literal408_tree=null;
        Object Identifier409_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }

            // org\\aries\\tmp.g:336:2: ( 'execute' ^ executeBody | 'execute' ^ 'then' Identifier formalParameters executeBody )
            int alt61=2;
            switch ( input.LA(1) ) {
            case 94:
                {
                switch ( input.LA(2) ) {
                case 132:
                    {
                    alt61=2;
                    }
                    break;
                case 138:
                    {
                    alt61=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 61, 1, input);

                    throw nvae;

                }

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
                    // org\\aries\\tmp.g:336:4: 'execute' ^ executeBody
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal405=(Token)match(input,94,FOLLOW_94_in_executeDecl2167); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal405_tree = 
                    (Object)adaptor.create(string_literal405)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal405_tree, root_0);
                    }

                    pushFollow(FOLLOW_executeBody_in_executeDecl2170);
                    executeBody406=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody406.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:337:4: 'execute' ^ 'then' Identifier formalParameters executeBody
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal407=(Token)match(input,94,FOLLOW_94_in_executeDecl2175); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal407_tree = 
                    (Object)adaptor.create(string_literal407)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal407_tree, root_0);
                    }

                    string_literal408=(Token)match(input,132,FOLLOW_132_in_executeDecl2178); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal408_tree = 
                    (Object)adaptor.create(string_literal408)
                    ;
                    adaptor.addChild(root_0, string_literal408_tree);
                    }

                    Identifier409=(Token)match(input,Identifier,FOLLOW_Identifier_in_executeDecl2180); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier409_tree = 
                    (Object)adaptor.create(Identifier409)
                    ;
                    adaptor.addChild(root_0, Identifier409_tree);
                    }

                    pushFollow(FOLLOW_formalParameters_in_executeDecl2182);
                    formalParameters410=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters410.getTree());

                    pushFollow(FOLLOW_executeBody_in_executeDecl2184);
                    executeBody411=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody411.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 49, executeDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeDecl"


    public static class executeDeclRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeDeclRest"
    // org\\aries\\tmp.g:340:1: executeDeclRest : formalParameters ( executeBody | ';' ) ;
    public final tmpParser.executeDeclRest_return executeDeclRest() throws RecognitionException {
        tmpParser.executeDeclRest_return retval = new tmpParser.executeDeclRest_return();
        retval.start = input.LT(1);

        int executeDeclRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal414=null;
        tmpParser.formalParameters_return formalParameters412 =null;

        tmpParser.executeBody_return executeBody413 =null;


        Object char_literal414_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }

            // org\\aries\\tmp.g:341:2: ( formalParameters ( executeBody | ';' ) )
            // org\\aries\\tmp.g:341:4: formalParameters ( executeBody | ';' )
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameters_in_executeDeclRest2196);
            formalParameters412=formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters412.getTree());

            // org\\aries\\tmp.g:341:21: ( executeBody | ';' )
            int alt62=2;
            switch ( input.LA(1) ) {
            case 138:
                {
                alt62=1;
                }
                break;
            case 65:
                {
                alt62=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;

            }

            switch (alt62) {
                case 1 :
                    // org\\aries\\tmp.g:341:22: executeBody
                    {
                    pushFollow(FOLLOW_executeBody_in_executeDeclRest2199);
                    executeBody413=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody413.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:341:36: ';'
                    {
                    char_literal414=(Token)match(input,65,FOLLOW_65_in_executeDeclRest2203); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal414_tree = 
                    (Object)adaptor.create(char_literal414)
                    ;
                    adaptor.addChild(root_0, char_literal414_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 50, executeDeclRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeDeclRest"


    public static class executeBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeBody"
    // org\\aries\\tmp.g:344:1: executeBody : '{' ( executeMember )* '}' ;
    public final tmpParser.executeBody_return executeBody() throws RecognitionException {
        tmpParser.executeBody_return retval = new tmpParser.executeBody_return();
        retval.start = input.LT(1);

        int executeBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal415=null;
        Token char_literal417=null;
        tmpParser.executeMember_return executeMember416 =null;


        Object char_literal415_tree=null;
        Object char_literal417_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }

            // org\\aries\\tmp.g:345:2: ( '{' ( executeMember )* '}' )
            // org\\aries\\tmp.g:345:4: '{' ( executeMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal415=(Token)match(input,138,FOLLOW_138_in_executeBody2215); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal415_tree = 
            (Object)adaptor.create(char_literal415)
            ;
            adaptor.addChild(root_0, char_literal415_tree);
            }

            // org\\aries\\tmp.g:345:8: ( executeMember )*
            loop63:
            do {
                int alt63=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case 76:
                case 79:
                case 99:
                case 100:
                case 105:
                case 128:
                case 135:
                    {
                    alt63=1;
                    }
                    break;

                }

                switch (alt63) {
            	case 1 :
            	    // org\\aries\\tmp.g:345:9: executeMember
            	    {
            	    pushFollow(FOLLOW_executeMember_in_executeBody2218);
            	    executeMember416=executeMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeMember416.getTree());

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            char_literal417=(Token)match(input,142,FOLLOW_142_in_executeBody2222); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal417_tree = 
            (Object)adaptor.create(char_literal417)
            ;
            adaptor.addChild(root_0, char_literal417_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 51, executeBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeBody"


    public static class executeMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "executeMember"
    // org\\aries\\tmp.g:348:1: executeMember : ( assignmentDecl | branchDecl | exceptionDecl | timeoutDecl );
    public final tmpParser.executeMember_return executeMember() throws RecognitionException {
        tmpParser.executeMember_return retval = new tmpParser.executeMember_return();
        retval.start = input.LT(1);

        int executeMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl418 =null;

        tmpParser.branchDecl_return branchDecl419 =null;

        tmpParser.exceptionDecl_return exceptionDecl420 =null;

        tmpParser.timeoutDecl_return timeoutDecl421 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }

            // org\\aries\\tmp.g:349:2: ( assignmentDecl | branchDecl | exceptionDecl | timeoutDecl )
            int alt64=4;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt64=1;
                }
                break;
            case 79:
                {
                alt64=2;
                }
                break;
            case EXCEPTION:
                {
                alt64=3;
                }
                break;
            case 135:
                {
                alt64=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;

            }

            switch (alt64) {
                case 1 :
                    // org\\aries\\tmp.g:349:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_executeMember2234);
                    assignmentDecl418=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl418.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:350:4: branchDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_branchDecl_in_executeMember2239);
                    branchDecl419=branchDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, branchDecl419.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:351:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_executeMember2244);
                    exceptionDecl420=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl420.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:352:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_executeMember2249);
                    timeoutDecl421=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl421.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 52, executeMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "executeMember"


    public static class groupDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "groupDecl"
    // org\\aries\\tmp.g:355:1: groupDecl : 'group' ^ Identifier groupBody ;
    public final tmpParser.groupDecl_return groupDecl() throws RecognitionException {
        tmpParser.groupDecl_return retval = new tmpParser.groupDecl_return();
        retval.start = input.LT(1);

        int groupDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal422=null;
        Token Identifier423=null;
        tmpParser.groupBody_return groupBody424 =null;


        Object string_literal422_tree=null;
        Object Identifier423_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }

            // org\\aries\\tmp.g:356:2: ( 'group' ^ Identifier groupBody )
            // org\\aries\\tmp.g:356:4: 'group' ^ Identifier groupBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal422=(Token)match(input,97,FOLLOW_97_in_groupDecl2260); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal422_tree = 
            (Object)adaptor.create(string_literal422)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal422_tree, root_0);
            }

            Identifier423=(Token)match(input,Identifier,FOLLOW_Identifier_in_groupDecl2263); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier423_tree = 
            (Object)adaptor.create(Identifier423)
            ;
            adaptor.addChild(root_0, Identifier423_tree);
            }

            pushFollow(FOLLOW_groupBody_in_groupDecl2265);
            groupBody424=groupBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, groupBody424.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 53, groupDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "groupDecl"


    public static class groupBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "groupBody"
    // org\\aries\\tmp.g:359:1: groupBody : '{' ( groupMember )* '}' ;
    public final tmpParser.groupBody_return groupBody() throws RecognitionException {
        tmpParser.groupBody_return retval = new tmpParser.groupBody_return();
        retval.start = input.LT(1);

        int groupBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal425=null;
        Token char_literal427=null;
        tmpParser.groupMember_return groupMember426 =null;


        Object char_literal425_tree=null;
        Object char_literal427_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }

            // org\\aries\\tmp.g:360:2: ( '{' ( groupMember )* '}' )
            // org\\aries\\tmp.g:360:4: '{' ( groupMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal425=(Token)match(input,138,FOLLOW_138_in_groupBody2276); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal425_tree = 
            (Object)adaptor.create(char_literal425)
            ;
            adaptor.addChild(root_0, char_literal425_tree);
            }

            // org\\aries\\tmp.g:360:8: ( groupMember )*
            loop65:
            do {
                int alt65=2;
                switch ( input.LA(1) ) {
                case 76:
                case 99:
                case 100:
                case 105:
                case 128:
                    {
                    alt65=1;
                    }
                    break;

                }

                switch (alt65) {
            	case 1 :
            	    // org\\aries\\tmp.g:360:9: groupMember
            	    {
            	    pushFollow(FOLLOW_groupMember_in_groupBody2279);
            	    groupMember426=groupMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, groupMember426.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);


            char_literal427=(Token)match(input,142,FOLLOW_142_in_groupBody2283); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal427_tree = 
            (Object)adaptor.create(char_literal427)
            ;
            adaptor.addChild(root_0, char_literal427_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 54, groupBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "groupBody"


    public static class groupMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "groupMember"
    // org\\aries\\tmp.g:363:1: groupMember : assignmentDecl ;
    public final tmpParser.groupMember_return groupMember() throws RecognitionException {
        tmpParser.groupMember_return retval = new tmpParser.groupMember_return();
        retval.start = input.LT(1);

        int groupMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl428 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }

            // org\\aries\\tmp.g:364:2: ( assignmentDecl )
            // org\\aries\\tmp.g:364:4: assignmentDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_assignmentDecl_in_groupMember2294);
            assignmentDecl428=assignmentDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl428.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 55, groupMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "groupMember"


    public static class importDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "importDecl"
    // org\\aries\\tmp.g:367:1: importDecl : 'import' ^ qualifiedName ( '.' '*' )? ';' ;
    public final tmpParser.importDecl_return importDecl() throws RecognitionException {
        tmpParser.importDecl_return retval = new tmpParser.importDecl_return();
        retval.start = input.LT(1);

        int importDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal429=null;
        Token char_literal431=null;
        Token char_literal432=null;
        Token char_literal433=null;
        tmpParser.qualifiedName_return qualifiedName430 =null;


        Object string_literal429_tree=null;
        Object char_literal431_tree=null;
        Object char_literal432_tree=null;
        Object char_literal433_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }

            // org\\aries\\tmp.g:368:2: ( 'import' ^ qualifiedName ( '.' '*' )? ';' )
            // org\\aries\\tmp.g:368:4: 'import' ^ qualifiedName ( '.' '*' )? ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal429=(Token)match(input,99,FOLLOW_99_in_importDecl2306); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal429_tree = 
            (Object)adaptor.create(string_literal429)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal429_tree, root_0);
            }

            pushFollow(FOLLOW_qualifiedName_in_importDecl2309);
            qualifiedName430=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName430.getTree());

            // org\\aries\\tmp.g:368:28: ( '.' '*' )?
            int alt66=2;
            switch ( input.LA(1) ) {
                case 61:
                    {
                    alt66=1;
                    }
                    break;
            }

            switch (alt66) {
                case 1 :
                    // org\\aries\\tmp.g:368:29: '.' '*'
                    {
                    char_literal431=(Token)match(input,61,FOLLOW_61_in_importDecl2312); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal431_tree = 
                    (Object)adaptor.create(char_literal431)
                    ;
                    adaptor.addChild(root_0, char_literal431_tree);
                    }

                    char_literal432=(Token)match(input,52,FOLLOW_52_in_importDecl2314); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal432_tree = 
                    (Object)adaptor.create(char_literal432)
                    ;
                    adaptor.addChild(root_0, char_literal432_tree);
                    }

                    }
                    break;

            }


            char_literal433=(Token)match(input,65,FOLLOW_65_in_importDecl2318); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal433_tree = 
            (Object)adaptor.create(char_literal433)
            ;
            adaptor.addChild(root_0, char_literal433_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 56, importDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "importDecl"


    public static class invokeDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "invokeDecl"
    // org\\aries\\tmp.g:371:1: invokeDecl : 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody ;
    public final tmpParser.invokeDecl_return invokeDecl() throws RecognitionException {
        tmpParser.invokeDecl_return retval = new tmpParser.invokeDecl_return();
        retval.start = input.LT(1);

        int invokeDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal434=null;
        Token Identifier435=null;
        Token char_literal436=null;
        Token Identifier437=null;
        tmpParser.formalParameters_return formalParameters438 =null;

        tmpParser.invokeBody_return invokeBody439 =null;


        Object string_literal434_tree=null;
        Object Identifier435_tree=null;
        Object char_literal436_tree=null;
        Object Identifier437_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }

            // org\\aries\\tmp.g:372:2: ( 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody )
            // org\\aries\\tmp.g:372:4: 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal434=(Token)match(input,103,FOLLOW_103_in_invokeDecl2329); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal434_tree = 
            (Object)adaptor.create(string_literal434)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal434_tree, root_0);
            }

            Identifier435=(Token)match(input,Identifier,FOLLOW_Identifier_in_invokeDecl2332); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier435_tree = 
            (Object)adaptor.create(Identifier435)
            ;
            adaptor.addChild(root_0, Identifier435_tree);
            }

            char_literal436=(Token)match(input,61,FOLLOW_61_in_invokeDecl2334); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal436_tree = 
            (Object)adaptor.create(char_literal436)
            ;
            adaptor.addChild(root_0, char_literal436_tree);
            }

            Identifier437=(Token)match(input,Identifier,FOLLOW_Identifier_in_invokeDecl2336); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier437_tree = 
            (Object)adaptor.create(Identifier437)
            ;
            adaptor.addChild(root_0, Identifier437_tree);
            }

            pushFollow(FOLLOW_formalParameters_in_invokeDecl2338);
            formalParameters438=formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters438.getTree());

            pushFollow(FOLLOW_invokeBody_in_invokeDecl2340);
            invokeBody439=invokeBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeBody439.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 57, invokeDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "invokeDecl"


    public static class invokeBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "invokeBody"
    // org\\aries\\tmp.g:375:1: invokeBody : '{' ( invokeMember )* '}' ;
    public final tmpParser.invokeBody_return invokeBody() throws RecognitionException {
        tmpParser.invokeBody_return retval = new tmpParser.invokeBody_return();
        retval.start = input.LT(1);

        int invokeBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal440=null;
        Token char_literal442=null;
        tmpParser.invokeMember_return invokeMember441 =null;


        Object char_literal440_tree=null;
        Object char_literal442_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }

            // org\\aries\\tmp.g:376:2: ( '{' ( invokeMember )* '}' )
            // org\\aries\\tmp.g:376:4: '{' ( invokeMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal440=(Token)match(input,138,FOLLOW_138_in_invokeBody2351); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal440_tree = 
            (Object)adaptor.create(char_literal440)
            ;
            adaptor.addChild(root_0, char_literal440_tree);
            }

            // org\\aries\\tmp.g:376:8: ( invokeMember )*
            loop67:
            do {
                int alt67=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case MESSAGE:
                case 76:
                case 99:
                case 100:
                case 105:
                case 128:
                case 135:
                    {
                    alt67=1;
                    }
                    break;

                }

                switch (alt67) {
            	case 1 :
            	    // org\\aries\\tmp.g:376:9: invokeMember
            	    {
            	    pushFollow(FOLLOW_invokeMember_in_invokeBody2354);
            	    invokeMember441=invokeMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeMember441.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            char_literal442=(Token)match(input,142,FOLLOW_142_in_invokeBody2358); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal442_tree = 
            (Object)adaptor.create(char_literal442)
            ;
            adaptor.addChild(root_0, char_literal442_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 58, invokeBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "invokeBody"


    public static class invokeMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "invokeMember"
    // org\\aries\\tmp.g:379:1: invokeMember : ( assignmentDecl | messageDecl | exceptionDecl | timeoutDecl );
    public final tmpParser.invokeMember_return invokeMember() throws RecognitionException {
        tmpParser.invokeMember_return retval = new tmpParser.invokeMember_return();
        retval.start = input.LT(1);

        int invokeMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl443 =null;

        tmpParser.messageDecl_return messageDecl444 =null;

        tmpParser.exceptionDecl_return exceptionDecl445 =null;

        tmpParser.timeoutDecl_return timeoutDecl446 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }

            // org\\aries\\tmp.g:380:2: ( assignmentDecl | messageDecl | exceptionDecl | timeoutDecl )
            int alt68=4;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt68=1;
                }
                break;
            case MESSAGE:
                {
                alt68=2;
                }
                break;
            case EXCEPTION:
                {
                alt68=3;
                }
                break;
            case 135:
                {
                alt68=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;

            }

            switch (alt68) {
                case 1 :
                    // org\\aries\\tmp.g:380:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_invokeMember2369);
                    assignmentDecl443=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl443.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:381:4: messageDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_messageDecl_in_invokeMember2374);
                    messageDecl444=messageDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, messageDecl444.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:382:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_invokeMember2379);
                    exceptionDecl445=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl445.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:383:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_invokeMember2384);
                    timeoutDecl446=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl446.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 59, invokeMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "invokeMember"


    public static class itemsDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemsDecl"
    // org\\aries\\tmp.g:386:1: itemsDecl : 'items' ^ itemsBody ;
    public final tmpParser.itemsDecl_return itemsDecl() throws RecognitionException {
        tmpParser.itemsDecl_return retval = new tmpParser.itemsDecl_return();
        retval.start = input.LT(1);

        int itemsDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal447=null;
        tmpParser.itemsBody_return itemsBody448 =null;


        Object string_literal447_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }

            // org\\aries\\tmp.g:387:2: ( 'items' ^ itemsBody )
            // org\\aries\\tmp.g:387:4: 'items' ^ itemsBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal447=(Token)match(input,104,FOLLOW_104_in_itemsDecl2395); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal447_tree = 
            (Object)adaptor.create(string_literal447)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal447_tree, root_0);
            }

            pushFollow(FOLLOW_itemsBody_in_itemsDecl2398);
            itemsBody448=itemsBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsBody448.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 60, itemsDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemsDecl"


    public static class itemsBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemsBody"
    // org\\aries\\tmp.g:390:1: itemsBody : '{' ( itemsMember )* '}' ;
    public final tmpParser.itemsBody_return itemsBody() throws RecognitionException {
        tmpParser.itemsBody_return retval = new tmpParser.itemsBody_return();
        retval.start = input.LT(1);

        int itemsBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal449=null;
        Token char_literal451=null;
        tmpParser.itemsMember_return itemsMember450 =null;


        Object char_literal449_tree=null;
        Object char_literal451_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }

            // org\\aries\\tmp.g:391:2: ( '{' ( itemsMember )* '}' )
            // org\\aries\\tmp.g:391:4: '{' ( itemsMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal449=(Token)match(input,138,FOLLOW_138_in_itemsBody2409); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal449_tree = 
            (Object)adaptor.create(char_literal449)
            ;
            adaptor.addChild(root_0, char_literal449_tree);
            }

            // org\\aries\\tmp.g:391:8: ( itemsMember )*
            loop69:
            do {
                int alt69=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                case 78:
                case 81:
                case 84:
                case 91:
                case 95:
                case 102:
                case 108:
                case 129:
                    {
                    alt69=1;
                    }
                    break;

                }

                switch (alt69) {
            	case 1 :
            	    // org\\aries\\tmp.g:391:9: itemsMember
            	    {
            	    pushFollow(FOLLOW_itemsMember_in_itemsBody2412);
            	    itemsMember450=itemsMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsMember450.getTree());

            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);


            char_literal451=(Token)match(input,142,FOLLOW_142_in_itemsBody2416); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal451_tree = 
            (Object)adaptor.create(char_literal451)
            ;
            adaptor.addChild(root_0, char_literal451_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 61, itemsBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemsBody"


    public static class itemsMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemsMember"
    // org\\aries\\tmp.g:394:1: itemsMember : itemDecl ;
    public final tmpParser.itemsMember_return itemsMember() throws RecognitionException {
        tmpParser.itemsMember_return retval = new tmpParser.itemsMember_return();
        retval.start = input.LT(1);

        int itemsMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.itemDecl_return itemDecl452 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }

            // org\\aries\\tmp.g:395:2: ( itemDecl )
            // org\\aries\\tmp.g:395:4: itemDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_itemDecl_in_itemsMember2427);
            itemDecl452=itemDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemDecl452.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 62, itemsMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemsMember"


    public static class itemDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemDecl"
    // org\\aries\\tmp.g:398:1: itemDecl : type Identifier itemDeclRest -> ^( ITEM type Identifier itemDeclRest ) ;
    public final tmpParser.itemDecl_return itemDecl() throws RecognitionException {
        tmpParser.itemDecl_return retval = new tmpParser.itemDecl_return();
        retval.start = input.LT(1);

        int itemDecl_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier454=null;
        tmpParser.type_return type453 =null;

        tmpParser.itemDeclRest_return itemDeclRest455 =null;


        Object Identifier454_tree=null;
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_itemDeclRest=new RewriteRuleSubtreeStream(adaptor,"rule itemDeclRest");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }

            // org\\aries\\tmp.g:399:2: ( type Identifier itemDeclRest -> ^( ITEM type Identifier itemDeclRest ) )
            // org\\aries\\tmp.g:399:4: type Identifier itemDeclRest
            {
            pushFollow(FOLLOW_type_in_itemDecl2438);
            type453=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type453.getTree());

            Identifier454=(Token)match(input,Identifier,FOLLOW_Identifier_in_itemDecl2440); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Identifier.add(Identifier454);


            pushFollow(FOLLOW_itemDeclRest_in_itemDecl2442);
            itemDeclRest455=itemDeclRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_itemDeclRest.add(itemDeclRest455.getTree());

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
            // 399:33: -> ^( ITEM type Identifier itemDeclRest )
            {
                // org\\aries\\tmp.g:399:36: ^( ITEM type Identifier itemDeclRest )
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
            if ( state.backtracking>0 ) { memoize(input, 63, itemDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemDecl"


    public static class itemDeclRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemDeclRest"
    // org\\aries\\tmp.g:403:1: itemDeclRest : ( '{' ( itemMember )* '}' | ';' );
    public final tmpParser.itemDeclRest_return itemDeclRest() throws RecognitionException {
        tmpParser.itemDeclRest_return retval = new tmpParser.itemDeclRest_return();
        retval.start = input.LT(1);

        int itemDeclRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal456=null;
        Token char_literal458=null;
        Token char_literal459=null;
        tmpParser.itemMember_return itemMember457 =null;


        Object char_literal456_tree=null;
        Object char_literal458_tree=null;
        Object char_literal459_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }

            // org\\aries\\tmp.g:404:2: ( '{' ( itemMember )* '}' | ';' )
            int alt71=2;
            switch ( input.LA(1) ) {
            case 138:
                {
                alt71=1;
                }
                break;
            case 65:
                {
                alt71=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;

            }

            switch (alt71) {
                case 1 :
                    // org\\aries\\tmp.g:404:4: '{' ( itemMember )* '}'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal456=(Token)match(input,138,FOLLOW_138_in_itemDeclRest2468); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal456_tree = 
                    (Object)adaptor.create(char_literal456)
                    ;
                    adaptor.addChild(root_0, char_literal456_tree);
                    }

                    // org\\aries\\tmp.g:404:8: ( itemMember )*
                    loop70:
                    do {
                        int alt70=2;
                        switch ( input.LA(1) ) {
                        case 101:
                            {
                            alt70=1;
                            }
                            break;

                        }

                        switch (alt70) {
                    	case 1 :
                    	    // org\\aries\\tmp.g:404:9: itemMember
                    	    {
                    	    pushFollow(FOLLOW_itemMember_in_itemDeclRest2471);
                    	    itemMember457=itemMember();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemMember457.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);


                    char_literal458=(Token)match(input,142,FOLLOW_142_in_itemDeclRest2475); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal458_tree = 
                    (Object)adaptor.create(char_literal458)
                    ;
                    adaptor.addChild(root_0, char_literal458_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:405:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal459=(Token)match(input,65,FOLLOW_65_in_itemDeclRest2480); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal459_tree = 
                    (Object)adaptor.create(char_literal459)
                    ;
                    adaptor.addChild(root_0, char_literal459_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 64, itemDeclRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemDeclRest"


    public static class itemMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "itemMember"
    // org\\aries\\tmp.g:408:1: itemMember : 'index' ^ '(' Identifier ')' ';' ;
    public final tmpParser.itemMember_return itemMember() throws RecognitionException {
        tmpParser.itemMember_return retval = new tmpParser.itemMember_return();
        retval.start = input.LT(1);

        int itemMember_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal460=null;
        Token char_literal461=null;
        Token Identifier462=null;
        Token char_literal463=null;
        Token char_literal464=null;

        Object string_literal460_tree=null;
        Object char_literal461_tree=null;
        Object Identifier462_tree=null;
        Object char_literal463_tree=null;
        Object char_literal464_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }

            // org\\aries\\tmp.g:409:2: ( 'index' ^ '(' Identifier ')' ';' )
            // org\\aries\\tmp.g:409:4: 'index' ^ '(' Identifier ')' ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal460=(Token)match(input,101,FOLLOW_101_in_itemMember2491); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal460_tree = 
            (Object)adaptor.create(string_literal460)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal460_tree, root_0);
            }

            char_literal461=(Token)match(input,50,FOLLOW_50_in_itemMember2494); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal461_tree = 
            (Object)adaptor.create(char_literal461)
            ;
            adaptor.addChild(root_0, char_literal461_tree);
            }

            Identifier462=(Token)match(input,Identifier,FOLLOW_Identifier_in_itemMember2496); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier462_tree = 
            (Object)adaptor.create(Identifier462)
            ;
            adaptor.addChild(root_0, Identifier462_tree);
            }

            char_literal463=(Token)match(input,51,FOLLOW_51_in_itemMember2498); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal463_tree = 
            (Object)adaptor.create(char_literal463)
            ;
            adaptor.addChild(root_0, char_literal463_tree);
            }

            char_literal464=(Token)match(input,65,FOLLOW_65_in_itemMember2500); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 65, itemMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "itemMember"


    public static class listenDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "listenDecl"
    // org\\aries\\tmp.g:412:1: listenDecl : 'listen' ^ Identifier ( formalParameters )? listenBody ;
    public final tmpParser.listenDecl_return listenDecl() throws RecognitionException {
        tmpParser.listenDecl_return retval = new tmpParser.listenDecl_return();
        retval.start = input.LT(1);

        int listenDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal465=null;
        Token Identifier466=null;
        tmpParser.formalParameters_return formalParameters467 =null;

        tmpParser.listenBody_return listenBody468 =null;


        Object string_literal465_tree=null;
        Object Identifier466_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }

            // org\\aries\\tmp.g:413:2: ( 'listen' ^ Identifier ( formalParameters )? listenBody )
            // org\\aries\\tmp.g:413:4: 'listen' ^ Identifier ( formalParameters )? listenBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal465=(Token)match(input,107,FOLLOW_107_in_listenDecl2511); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal465_tree = 
            (Object)adaptor.create(string_literal465)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal465_tree, root_0);
            }

            Identifier466=(Token)match(input,Identifier,FOLLOW_Identifier_in_listenDecl2514); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier466_tree = 
            (Object)adaptor.create(Identifier466)
            ;
            adaptor.addChild(root_0, Identifier466_tree);
            }

            // org\\aries\\tmp.g:413:25: ( formalParameters )?
            int alt72=2;
            switch ( input.LA(1) ) {
                case 50:
                    {
                    alt72=1;
                    }
                    break;
            }

            switch (alt72) {
                case 1 :
                    // org\\aries\\tmp.g:413:25: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_listenDecl2516);
                    formalParameters467=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters467.getTree());

                    }
                    break;

            }


            pushFollow(FOLLOW_listenBody_in_listenDecl2519);
            listenBody468=listenBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, listenBody468.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 66, listenDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "listenDecl"


    public static class listenBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "listenBody"
    // org\\aries\\tmp.g:416:1: listenBody : '{' ( listenMember )* '}' ;
    public final tmpParser.listenBody_return listenBody() throws RecognitionException {
        tmpParser.listenBody_return retval = new tmpParser.listenBody_return();
        retval.start = input.LT(1);

        int listenBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal469=null;
        Token char_literal471=null;
        tmpParser.listenMember_return listenMember470 =null;


        Object char_literal469_tree=null;
        Object char_literal471_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }

            // org\\aries\\tmp.g:417:2: ( '{' ( listenMember )* '}' )
            // org\\aries\\tmp.g:417:4: '{' ( listenMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal469=(Token)match(input,138,FOLLOW_138_in_listenBody2531); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal469_tree = 
            (Object)adaptor.create(char_literal469)
            ;
            adaptor.addChild(root_0, char_literal469_tree);
            }

            // org\\aries\\tmp.g:417:8: ( listenMember )*
            loop73:
            do {
                int alt73=2;
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 99:
                case 100:
                case 102:
                case 105:
                case 108:
                case 113:
                case 114:
                case 117:
                case 120:
                case 122:
                case 126:
                case 128:
                case 129:
                case 133:
                case 135:
                case 137:
                case 138:
                case 143:
                    {
                    alt73=1;
                    }
                    break;

                }

                switch (alt73) {
            	case 1 :
            	    // org\\aries\\tmp.g:417:9: listenMember
            	    {
            	    pushFollow(FOLLOW_listenMember_in_listenBody2534);
            	    listenMember470=listenMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenMember470.getTree());

            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);


            char_literal471=(Token)match(input,142,FOLLOW_142_in_listenBody2538); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal471_tree = 
            (Object)adaptor.create(char_literal471)
            ;
            adaptor.addChild(root_0, char_literal471_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 67, listenBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "listenBody"


    public static class listenMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "listenMember"
    // org\\aries\\tmp.g:420:1: listenMember : ( assignmentDecl | optionDecl | statementDecl | timeoutDecl | exceptionDecl | doneDecl );
    public final tmpParser.listenMember_return listenMember() throws RecognitionException {
        tmpParser.listenMember_return retval = new tmpParser.listenMember_return();
        retval.start = input.LT(1);

        int listenMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl472 =null;

        tmpParser.optionDecl_return optionDecl473 =null;

        tmpParser.statementDecl_return statementDecl474 =null;

        tmpParser.timeoutDecl_return timeoutDecl475 =null;

        tmpParser.exceptionDecl_return exceptionDecl476 =null;

        tmpParser.doneDecl_return doneDecl477 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }

            // org\\aries\\tmp.g:421:2: ( assignmentDecl | optionDecl | statementDecl | timeoutDecl | exceptionDecl | doneDecl )
            int alt74=6;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt74=1;
                }
                break;
            case 114:
                {
                alt74=2;
                }
                break;
            case CHARLITERAL:
            case DoubleLiteral:
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
                {
                alt74=3;
                }
                break;
            case EXCEPTION:
                {
                switch ( input.LA(2) ) {
                case 64:
                    {
                    alt74=5;
                    }
                    break;
                case Identifier:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 73:
                case 74:
                case 139:
                case 140:
                case 141:
                    {
                    alt74=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 4, input);

                    throw nvae;

                }

                }
                break;
            case 135:
                {
                alt74=4;
                }
                break;
            case 90:
                {
                alt74=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;

            }

            switch (alt74) {
                case 1 :
                    // org\\aries\\tmp.g:421:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_listenMember2549);
                    assignmentDecl472=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl472.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:422:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_listenMember2554);
                    optionDecl473=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl473.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:423:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_listenMember2559);
                    statementDecl474=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl474.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:425:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_listenMember2565);
                    timeoutDecl475=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl475.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:426:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_listenMember2570);
                    exceptionDecl476=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl476.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:427:4: doneDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_doneDecl_in_listenMember2575);
                    doneDecl477=doneDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doneDecl477.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 68, listenMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "listenMember"


    public static class messageDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "messageDecl"
    // org\\aries\\tmp.g:430:1: messageDecl : MESSAGE ^ Identifier ( formalParameters )? ':' messageBody ;
    public final tmpParser.messageDecl_return messageDecl() throws RecognitionException {
        tmpParser.messageDecl_return retval = new tmpParser.messageDecl_return();
        retval.start = input.LT(1);

        int messageDecl_StartIndex = input.index();

        Object root_0 = null;

        Token MESSAGE478=null;
        Token Identifier479=null;
        Token char_literal481=null;
        tmpParser.formalParameters_return formalParameters480 =null;

        tmpParser.messageBody_return messageBody482 =null;


        Object MESSAGE478_tree=null;
        Object Identifier479_tree=null;
        Object char_literal481_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }

            // org\\aries\\tmp.g:431:2: ( MESSAGE ^ Identifier ( formalParameters )? ':' messageBody )
            // org\\aries\\tmp.g:431:4: MESSAGE ^ Identifier ( formalParameters )? ':' messageBody
            {
            root_0 = (Object)adaptor.nil();


            MESSAGE478=(Token)match(input,MESSAGE,FOLLOW_MESSAGE_in_messageDecl2586); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MESSAGE478_tree = 
            (Object)adaptor.create(MESSAGE478)
            ;
            root_0 = (Object)adaptor.becomeRoot(MESSAGE478_tree, root_0);
            }

            Identifier479=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageDecl2589); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier479_tree = 
            (Object)adaptor.create(Identifier479)
            ;
            adaptor.addChild(root_0, Identifier479_tree);
            }

            // org\\aries\\tmp.g:431:24: ( formalParameters )?
            int alt75=2;
            switch ( input.LA(1) ) {
                case 50:
                    {
                    alt75=1;
                    }
                    break;
            }

            switch (alt75) {
                case 1 :
                    // org\\aries\\tmp.g:431:24: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_messageDecl2591);
                    formalParameters480=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters480.getTree());

                    }
                    break;

            }


            char_literal481=(Token)match(input,64,FOLLOW_64_in_messageDecl2594); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal481_tree = 
            (Object)adaptor.create(char_literal481)
            ;
            adaptor.addChild(root_0, char_literal481_tree);
            }

            pushFollow(FOLLOW_messageBody_in_messageDecl2596);
            messageBody482=messageBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, messageBody482.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 69, messageDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "messageDecl"


    public static class messageBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "messageBody"
    // org\\aries\\tmp.g:434:1: messageBody : '{' ( messageMember )* '}' ;
    public final tmpParser.messageBody_return messageBody() throws RecognitionException {
        tmpParser.messageBody_return retval = new tmpParser.messageBody_return();
        retval.start = input.LT(1);

        int messageBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal483=null;
        Token char_literal485=null;
        tmpParser.messageMember_return messageMember484 =null;


        Object char_literal483_tree=null;
        Object char_literal485_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }

            // org\\aries\\tmp.g:435:2: ( '{' ( messageMember )* '}' )
            // org\\aries\\tmp.g:435:4: '{' ( messageMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal483=(Token)match(input,138,FOLLOW_138_in_messageBody2608); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal483_tree = 
            (Object)adaptor.create(char_literal483)
            ;
            adaptor.addChild(root_0, char_literal483_tree);
            }

            // org\\aries\\tmp.g:435:8: ( messageMember )*
            loop76:
            do {
                int alt76=2;
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
                case 91:
                case 93:
                case 94:
                case 95:
                case 96:
                case 98:
                case 99:
                case 100:
                case 102:
                case 103:
                case 105:
                case 107:
                case 108:
                case 113:
                case 114:
                case 117:
                case 120:
                case 122:
                case 126:
                case 128:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt76=1;
                    }
                    break;

                }

                switch (alt76) {
            	case 1 :
            	    // org\\aries\\tmp.g:435:9: messageMember
            	    {
            	    pushFollow(FOLLOW_messageMember_in_messageBody2611);
            	    messageMember484=messageMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, messageMember484.getTree());

            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);


            char_literal485=(Token)match(input,142,FOLLOW_142_in_messageBody2615); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal485_tree = 
            (Object)adaptor.create(char_literal485)
            ;
            adaptor.addChild(root_0, char_literal485_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 70, messageBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "messageBody"


    public static class messageMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "messageMember"
    // org\\aries\\tmp.g:438:1: messageMember : ( assignmentDecl | optionDecl | executeDecl | invokeDecl | listenDecl | statementDecl | doneDecl );
    public final tmpParser.messageMember_return messageMember() throws RecognitionException {
        tmpParser.messageMember_return retval = new tmpParser.messageMember_return();
        retval.start = input.LT(1);

        int messageMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl486 =null;

        tmpParser.optionDecl_return optionDecl487 =null;

        tmpParser.executeDecl_return executeDecl488 =null;

        tmpParser.invokeDecl_return invokeDecl489 =null;

        tmpParser.listenDecl_return listenDecl490 =null;

        tmpParser.statementDecl_return statementDecl491 =null;

        tmpParser.doneDecl_return doneDecl492 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }

            // org\\aries\\tmp.g:439:2: ( assignmentDecl | optionDecl | executeDecl | invokeDecl | listenDecl | statementDecl | doneDecl )
            int alt77=7;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt77=1;
                }
                break;
            case 114:
                {
                alt77=2;
                }
                break;
            case 94:
                {
                alt77=3;
                }
                break;
            case 103:
                {
                alt77=4;
                }
                break;
            case 107:
                {
                alt77=5;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
                {
                alt77=6;
                }
                break;
            case 90:
                {
                alt77=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;

            }

            switch (alt77) {
                case 1 :
                    // org\\aries\\tmp.g:439:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_messageMember2626);
                    assignmentDecl486=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl486.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:440:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_messageMember2631);
                    optionDecl487=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl487.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:441:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_messageMember2636);
                    executeDecl488=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl488.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:442:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_messageMember2641);
                    invokeDecl489=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl489.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:443:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_messageMember2646);
                    listenDecl490=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl490.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:444:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_messageMember2651);
                    statementDecl491=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl491.getTree());

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp.g:446:4: doneDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_doneDecl_in_messageMember2657);
                    doneDecl492=doneDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doneDecl492.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 71, messageMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "messageMember"


    public static class methodDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodDecl"
    // org\\aries\\tmp.g:449:1: methodDecl : qualifiedName ^ formalParametersSignature methodBody ;
    public final tmpParser.methodDecl_return methodDecl() throws RecognitionException {
        tmpParser.methodDecl_return retval = new tmpParser.methodDecl_return();
        retval.start = input.LT(1);

        int methodDecl_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.qualifiedName_return qualifiedName493 =null;

        tmpParser.formalParametersSignature_return formalParametersSignature494 =null;

        tmpParser.methodBody_return methodBody495 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }

            // org\\aries\\tmp.g:450:2: ( qualifiedName ^ formalParametersSignature methodBody )
            // org\\aries\\tmp.g:450:4: qualifiedName ^ formalParametersSignature methodBody
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_methodDecl2669);
            qualifiedName493=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(qualifiedName493.getTree(), root_0);

            pushFollow(FOLLOW_formalParametersSignature_in_methodDecl2672);
            formalParametersSignature494=formalParametersSignature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParametersSignature494.getTree());

            pushFollow(FOLLOW_methodBody_in_methodDecl2674);
            methodBody495=methodBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, methodBody495.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 72, methodDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "methodDecl"


    public static class methodBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodBody"
    // org\\aries\\tmp.g:453:1: methodBody : '{' ( methodMember )* '}' ;
    public final tmpParser.methodBody_return methodBody() throws RecognitionException {
        tmpParser.methodBody_return retval = new tmpParser.methodBody_return();
        retval.start = input.LT(1);

        int methodBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal496=null;
        Token char_literal498=null;
        tmpParser.methodMember_return methodMember497 =null;


        Object char_literal496_tree=null;
        Object char_literal498_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }

            // org\\aries\\tmp.g:454:2: ( '{' ( methodMember )* '}' )
            // org\\aries\\tmp.g:454:4: '{' ( methodMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal496=(Token)match(input,138,FOLLOW_138_in_methodBody2685); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal496_tree = 
            (Object)adaptor.create(char_literal496)
            ;
            adaptor.addChild(root_0, char_literal496_tree);
            }

            // org\\aries\\tmp.g:454:8: ( methodMember )*
            loop78:
            do {
                int alt78=2;
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
                case 91:
                case 93:
                case 94:
                case 95:
                case 96:
                case 98:
                case 102:
                case 103:
                case 107:
                case 108:
                case 113:
                case 117:
                case 120:
                case 122:
                case 126:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt78=1;
                    }
                    break;

                }

                switch (alt78) {
            	case 1 :
            	    // org\\aries\\tmp.g:454:9: methodMember
            	    {
            	    pushFollow(FOLLOW_methodMember_in_methodBody2688);
            	    methodMember497=methodMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodMember497.getTree());

            	    }
            	    break;

            	default :
            	    break loop78;
                }
            } while (true);


            char_literal498=(Token)match(input,142,FOLLOW_142_in_methodBody2692); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal498_tree = 
            (Object)adaptor.create(char_literal498)
            ;
            adaptor.addChild(root_0, char_literal498_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 73, methodBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "methodBody"


    public static class methodMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodMember"
    // org\\aries\\tmp.g:457:1: methodMember : ( invokeDecl | executeDecl | listenDecl | statementDecl );
    public final tmpParser.methodMember_return methodMember() throws RecognitionException {
        tmpParser.methodMember_return retval = new tmpParser.methodMember_return();
        retval.start = input.LT(1);

        int methodMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.invokeDecl_return invokeDecl499 =null;

        tmpParser.executeDecl_return executeDecl500 =null;

        tmpParser.listenDecl_return listenDecl501 =null;

        tmpParser.statementDecl_return statementDecl502 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }

            // org\\aries\\tmp.g:458:2: ( invokeDecl | executeDecl | listenDecl | statementDecl )
            int alt79=4;
            switch ( input.LA(1) ) {
            case 103:
                {
                alt79=1;
                }
                break;
            case 94:
                {
                alt79=2;
                }
                break;
            case 107:
                {
                alt79=3;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
                {
                alt79=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;

            }

            switch (alt79) {
                case 1 :
                    // org\\aries\\tmp.g:458:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_methodMember2703);
                    invokeDecl499=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl499.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:459:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_methodMember2708);
                    executeDecl500=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl500.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:460:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_methodMember2713);
                    listenDecl501=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl501.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:461:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_methodMember2718);
                    statementDecl502=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl502.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 74, methodMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "methodMember"


    public static class optionDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optionDecl"
    // org\\aries\\tmp.g:465:1: optionDecl : 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody ;
    public final tmpParser.optionDecl_return optionDecl() throws RecognitionException {
        tmpParser.optionDecl_return retval = new tmpParser.optionDecl_return();
        retval.start = input.LT(1);

        int optionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal503=null;
        Token Identifier504=null;
        Token char_literal506=null;
        tmpParser.formalParameters_return formalParameters505 =null;

        tmpParser.optionBody_return optionBody507 =null;


        Object string_literal503_tree=null;
        Object Identifier504_tree=null;
        Object char_literal506_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }

            // org\\aries\\tmp.g:466:2: ( 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody )
            // org\\aries\\tmp.g:466:4: 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal503=(Token)match(input,114,FOLLOW_114_in_optionDecl2730); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal503_tree = 
            (Object)adaptor.create(string_literal503)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal503_tree, root_0);
            }

            // org\\aries\\tmp.g:466:14: ( Identifier )?
            int alt80=2;
            switch ( input.LA(1) ) {
                case Identifier:
                    {
                    alt80=1;
                    }
                    break;
            }

            switch (alt80) {
                case 1 :
                    // org\\aries\\tmp.g:466:14: Identifier
                    {
                    Identifier504=(Token)match(input,Identifier,FOLLOW_Identifier_in_optionDecl2733); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier504_tree = 
                    (Object)adaptor.create(Identifier504)
                    ;
                    adaptor.addChild(root_0, Identifier504_tree);
                    }

                    }
                    break;

            }


            // org\\aries\\tmp.g:466:26: ( formalParameters )?
            int alt81=2;
            switch ( input.LA(1) ) {
                case 50:
                    {
                    alt81=1;
                    }
                    break;
            }

            switch (alt81) {
                case 1 :
                    // org\\aries\\tmp.g:466:26: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_optionDecl2736);
                    formalParameters505=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters505.getTree());

                    }
                    break;

            }


            char_literal506=(Token)match(input,64,FOLLOW_64_in_optionDecl2739); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal506_tree = 
            (Object)adaptor.create(char_literal506)
            ;
            adaptor.addChild(root_0, char_literal506_tree);
            }

            pushFollow(FOLLOW_optionBody_in_optionDecl2741);
            optionBody507=optionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, optionBody507.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 75, optionDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optionDecl"


    public static class optionBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optionBody"
    // org\\aries\\tmp.g:469:1: optionBody : '{' ( optionMember )* '}' ;
    public final tmpParser.optionBody_return optionBody() throws RecognitionException {
        tmpParser.optionBody_return retval = new tmpParser.optionBody_return();
        retval.start = input.LT(1);

        int optionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal508=null;
        Token char_literal510=null;
        tmpParser.optionMember_return optionMember509 =null;


        Object char_literal508_tree=null;
        Object char_literal510_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 76) ) { return retval; }

            // org\\aries\\tmp.g:470:2: ( '{' ( optionMember )* '}' )
            // org\\aries\\tmp.g:470:4: '{' ( optionMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal508=(Token)match(input,138,FOLLOW_138_in_optionBody2752); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal508_tree = 
            (Object)adaptor.create(char_literal508)
            ;
            adaptor.addChild(root_0, char_literal508_tree);
            }

            // org\\aries\\tmp.g:470:8: ( optionMember )*
            loop82:
            do {
                int alt82=2;
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 108:
                case 113:
                case 117:
                case 120:
                case 122:
                case 126:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt82=1;
                    }
                    break;

                }

                switch (alt82) {
            	case 1 :
            	    // org\\aries\\tmp.g:470:9: optionMember
            	    {
            	    pushFollow(FOLLOW_optionMember_in_optionBody2755);
            	    optionMember509=optionMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionMember509.getTree());

            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);


            char_literal510=(Token)match(input,142,FOLLOW_142_in_optionBody2759); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal510_tree = 
            (Object)adaptor.create(char_literal510)
            ;
            adaptor.addChild(root_0, char_literal510_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 76, optionBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optionBody"


    public static class optionMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "optionMember"
    // org\\aries\\tmp.g:473:1: optionMember : statementDecl ;
    public final tmpParser.optionMember_return optionMember() throws RecognitionException {
        tmpParser.optionMember_return retval = new tmpParser.optionMember_return();
        retval.start = input.LT(1);

        int optionMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.statementDecl_return statementDecl511 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 77) ) { return retval; }

            // org\\aries\\tmp.g:474:2: ( statementDecl )
            // org\\aries\\tmp.g:474:4: statementDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_statementDecl_in_optionMember2770);
            statementDecl511=statementDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl511.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 77, optionMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "optionMember"


    public static class participantDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "participantDecl"
    // org\\aries\\tmp.g:479:1: participantDecl : 'participant' ^ Identifier participantBody ;
    public final tmpParser.participantDecl_return participantDecl() throws RecognitionException {
        tmpParser.participantDecl_return retval = new tmpParser.participantDecl_return();
        retval.start = input.LT(1);

        int participantDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal512=null;
        Token Identifier513=null;
        tmpParser.participantBody_return participantBody514 =null;


        Object string_literal512_tree=null;
        Object Identifier513_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return retval; }

            // org\\aries\\tmp.g:480:2: ( 'participant' ^ Identifier participantBody )
            // org\\aries\\tmp.g:480:4: 'participant' ^ Identifier participantBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal512=(Token)match(input,115,FOLLOW_115_in_participantDecl2785); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal512_tree = 
            (Object)adaptor.create(string_literal512)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal512_tree, root_0);
            }

            Identifier513=(Token)match(input,Identifier,FOLLOW_Identifier_in_participantDecl2788); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier513_tree = 
            (Object)adaptor.create(Identifier513)
            ;
            adaptor.addChild(root_0, Identifier513_tree);
            }

            pushFollow(FOLLOW_participantBody_in_participantDecl2790);
            participantBody514=participantBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, participantBody514.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 78, participantDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "participantDecl"


    public static class participantBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "participantBody"
    // org\\aries\\tmp.g:483:1: participantBody : '{' ( participantMember )* '}' ;
    public final tmpParser.participantBody_return participantBody() throws RecognitionException {
        tmpParser.participantBody_return retval = new tmpParser.participantBody_return();
        retval.start = input.LT(1);

        int participantBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal515=null;
        Token char_literal517=null;
        tmpParser.participantMember_return participantMember516 =null;


        Object char_literal515_tree=null;
        Object char_literal517_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 79) ) { return retval; }

            // org\\aries\\tmp.g:484:2: ( '{' ( participantMember )* '}' )
            // org\\aries\\tmp.g:484:4: '{' ( participantMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal515=(Token)match(input,138,FOLLOW_138_in_participantBody2801); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal515_tree = 
            (Object)adaptor.create(char_literal515)
            ;
            adaptor.addChild(root_0, char_literal515_tree);
            }

            // org\\aries\\tmp.g:484:8: ( participantMember )*
            loop83:
            do {
                int alt83=2;
                switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                case 76:
                case 82:
                case 99:
                case 100:
                case 105:
                case 116:
                case 119:
                case 124:
                case 128:
                    {
                    alt83=1;
                    }
                    break;

                }

                switch (alt83) {
            	case 1 :
            	    // org\\aries\\tmp.g:484:9: participantMember
            	    {
            	    pushFollow(FOLLOW_participantMember_in_participantBody2804);
            	    participantMember516=participantMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, participantMember516.getTree());

            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);


            char_literal517=(Token)match(input,142,FOLLOW_142_in_participantBody2808); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal517_tree = 
            (Object)adaptor.create(char_literal517)
            ;
            adaptor.addChild(root_0, char_literal517_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 79, participantBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "participantBody"


    public static class participantMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "participantMember"
    // org\\aries\\tmp.g:487:1: participantMember : ( assignmentDecl | receiveDecl | cacheDecl | persistDecl | scheduleDecl | methodDecl );
    public final tmpParser.participantMember_return participantMember() throws RecognitionException {
        tmpParser.participantMember_return retval = new tmpParser.participantMember_return();
        retval.start = input.LT(1);

        int participantMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl518 =null;

        tmpParser.receiveDecl_return receiveDecl519 =null;

        tmpParser.cacheDecl_return cacheDecl520 =null;

        tmpParser.persistDecl_return persistDecl521 =null;

        tmpParser.scheduleDecl_return scheduleDecl522 =null;

        tmpParser.methodDecl_return methodDecl523 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }

            // org\\aries\\tmp.g:488:2: ( assignmentDecl | receiveDecl | cacheDecl | persistDecl | scheduleDecl | methodDecl )
            int alt84=6;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt84=1;
                }
                break;
            case 119:
                {
                alt84=2;
                }
                break;
            case 82:
                {
                alt84=3;
                }
                break;
            case 116:
                {
                alt84=4;
                }
                break;
            case 124:
                {
                alt84=5;
                }
                break;
            case EXCEPTION:
            case Identifier:
            case MESSAGE:
                {
                alt84=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;

            }

            switch (alt84) {
                case 1 :
                    // org\\aries\\tmp.g:488:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_participantMember2820);
                    assignmentDecl518=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl518.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:489:4: receiveDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_receiveDecl_in_participantMember2825);
                    receiveDecl519=receiveDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveDecl519.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:490:4: cacheDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cacheDecl_in_participantMember2830);
                    cacheDecl520=cacheDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheDecl520.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:491:4: persistDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_persistDecl_in_participantMember2835);
                    persistDecl521=persistDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistDecl521.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:492:4: scheduleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_scheduleDecl_in_participantMember2840);
                    scheduleDecl522=scheduleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleDecl522.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:493:4: methodDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_methodDecl_in_participantMember2845);
                    methodDecl523=methodDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodDecl523.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 80, participantMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "participantMember"


    public static class persistDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "persistDecl"
    // org\\aries\\tmp.g:496:1: persistDecl : 'persist' ^ Identifier persistBody ;
    public final tmpParser.persistDecl_return persistDecl() throws RecognitionException {
        tmpParser.persistDecl_return retval = new tmpParser.persistDecl_return();
        retval.start = input.LT(1);

        int persistDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal524=null;
        Token Identifier525=null;
        tmpParser.persistBody_return persistBody526 =null;


        Object string_literal524_tree=null;
        Object Identifier525_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 81) ) { return retval; }

            // org\\aries\\tmp.g:497:2: ( 'persist' ^ Identifier persistBody )
            // org\\aries\\tmp.g:497:4: 'persist' ^ Identifier persistBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal524=(Token)match(input,116,FOLLOW_116_in_persistDecl2856); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal524_tree = 
            (Object)adaptor.create(string_literal524)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal524_tree, root_0);
            }

            Identifier525=(Token)match(input,Identifier,FOLLOW_Identifier_in_persistDecl2859); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier525_tree = 
            (Object)adaptor.create(Identifier525)
            ;
            adaptor.addChild(root_0, Identifier525_tree);
            }

            pushFollow(FOLLOW_persistBody_in_persistDecl2861);
            persistBody526=persistBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, persistBody526.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 81, persistDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "persistDecl"


    public static class persistBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "persistBody"
    // org\\aries\\tmp.g:500:1: persistBody : '{' ( persistMember )* '}' ;
    public final tmpParser.persistBody_return persistBody() throws RecognitionException {
        tmpParser.persistBody_return retval = new tmpParser.persistBody_return();
        retval.start = input.LT(1);

        int persistBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal527=null;
        Token char_literal529=null;
        tmpParser.persistMember_return persistMember528 =null;


        Object char_literal527_tree=null;
        Object char_literal529_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 82) ) { return retval; }

            // org\\aries\\tmp.g:501:2: ( '{' ( persistMember )* '}' )
            // org\\aries\\tmp.g:501:4: '{' ( persistMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal527=(Token)match(input,138,FOLLOW_138_in_persistBody2872); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal527_tree = 
            (Object)adaptor.create(char_literal527)
            ;
            adaptor.addChild(root_0, char_literal527_tree);
            }

            // org\\aries\\tmp.g:501:8: ( persistMember )*
            loop85:
            do {
                int alt85=2;
                switch ( input.LA(1) ) {
                case 76:
                case 99:
                case 100:
                case 104:
                case 105:
                case 128:
                    {
                    alt85=1;
                    }
                    break;

                }

                switch (alt85) {
            	case 1 :
            	    // org\\aries\\tmp.g:501:9: persistMember
            	    {
            	    pushFollow(FOLLOW_persistMember_in_persistBody2875);
            	    persistMember528=persistMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistMember528.getTree());

            	    }
            	    break;

            	default :
            	    break loop85;
                }
            } while (true);


            char_literal529=(Token)match(input,142,FOLLOW_142_in_persistBody2879); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal529_tree = 
            (Object)adaptor.create(char_literal529)
            ;
            adaptor.addChild(root_0, char_literal529_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 82, persistBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "persistBody"


    public static class persistMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "persistMember"
    // org\\aries\\tmp.g:504:1: persistMember : ( assignmentDecl | itemsDecl );
    public final tmpParser.persistMember_return persistMember() throws RecognitionException {
        tmpParser.persistMember_return retval = new tmpParser.persistMember_return();
        retval.start = input.LT(1);

        int persistMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl530 =null;

        tmpParser.itemsDecl_return itemsDecl531 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 83) ) { return retval; }

            // org\\aries\\tmp.g:505:2: ( assignmentDecl | itemsDecl )
            int alt86=2;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt86=1;
                }
                break;
            case 104:
                {
                alt86=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;

            }

            switch (alt86) {
                case 1 :
                    // org\\aries\\tmp.g:505:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_persistMember2890);
                    assignmentDecl530=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl530.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:506:4: itemsDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_itemsDecl_in_persistMember2895);
                    itemsDecl531=itemsDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsDecl531.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 83, persistMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "persistMember"


    public static class protocolDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "protocolDecl"
    // org\\aries\\tmp.g:509:1: protocolDecl : 'protocol' ^ Identifier protocolBody ;
    public final tmpParser.protocolDecl_return protocolDecl() throws RecognitionException {
        tmpParser.protocolDecl_return retval = new tmpParser.protocolDecl_return();
        retval.start = input.LT(1);

        int protocolDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal532=null;
        Token Identifier533=null;
        tmpParser.protocolBody_return protocolBody534 =null;


        Object string_literal532_tree=null;
        Object Identifier533_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 84) ) { return retval; }

            // org\\aries\\tmp.g:510:2: ( 'protocol' ^ Identifier protocolBody )
            // org\\aries\\tmp.g:510:4: 'protocol' ^ Identifier protocolBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal532=(Token)match(input,118,FOLLOW_118_in_protocolDecl2906); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal532_tree = 
            (Object)adaptor.create(string_literal532)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal532_tree, root_0);
            }

            Identifier533=(Token)match(input,Identifier,FOLLOW_Identifier_in_protocolDecl2909); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier533_tree = 
            (Object)adaptor.create(Identifier533)
            ;
            adaptor.addChild(root_0, Identifier533_tree);
            }

            pushFollow(FOLLOW_protocolBody_in_protocolDecl2911);
            protocolBody534=protocolBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, protocolBody534.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 84, protocolDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "protocolDecl"


    public static class protocolBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "protocolBody"
    // org\\aries\\tmp.g:513:1: protocolBody : '{' ( protocolMember )* '}' ;
    public final tmpParser.protocolBody_return protocolBody() throws RecognitionException {
        tmpParser.protocolBody_return retval = new tmpParser.protocolBody_return();
        retval.start = input.LT(1);

        int protocolBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal535=null;
        Token char_literal537=null;
        tmpParser.protocolMember_return protocolMember536 =null;


        Object char_literal535_tree=null;
        Object char_literal537_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 85) ) { return retval; }

            // org\\aries\\tmp.g:514:2: ( '{' ( protocolMember )* '}' )
            // org\\aries\\tmp.g:514:4: '{' ( protocolMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal535=(Token)match(input,138,FOLLOW_138_in_protocolBody2922); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal535_tree = 
            (Object)adaptor.create(char_literal535)
            ;
            adaptor.addChild(root_0, char_literal535_tree);
            }

            // org\\aries\\tmp.g:514:8: ( protocolMember )*
            loop87:
            do {
                int alt87=2;
                switch ( input.LA(1) ) {
                case 76:
                case 82:
                case 83:
                case 97:
                case 99:
                case 100:
                case 105:
                case 115:
                case 116:
                case 123:
                case 124:
                case 128:
                    {
                    alt87=1;
                    }
                    break;

                }

                switch (alt87) {
            	case 1 :
            	    // org\\aries\\tmp.g:514:8: protocolMember
            	    {
            	    pushFollow(FOLLOW_protocolMember_in_protocolBody2924);
            	    protocolMember536=protocolMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, protocolMember536.getTree());

            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);


            char_literal537=(Token)match(input,142,FOLLOW_142_in_protocolBody2927); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal537_tree = 
            (Object)adaptor.create(char_literal537)
            ;
            adaptor.addChild(root_0, char_literal537_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 85, protocolBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "protocolBody"


    public static class protocolMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "protocolMember"
    // org\\aries\\tmp.g:517:1: protocolMember : ( assignmentDecl | roleDecl | groupDecl | channelDecl | participantDecl | cacheDecl | persistDecl | scheduleDecl );
    public final tmpParser.protocolMember_return protocolMember() throws RecognitionException {
        tmpParser.protocolMember_return retval = new tmpParser.protocolMember_return();
        retval.start = input.LT(1);

        int protocolMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl538 =null;

        tmpParser.roleDecl_return roleDecl539 =null;

        tmpParser.groupDecl_return groupDecl540 =null;

        tmpParser.channelDecl_return channelDecl541 =null;

        tmpParser.participantDecl_return participantDecl542 =null;

        tmpParser.cacheDecl_return cacheDecl543 =null;

        tmpParser.persistDecl_return persistDecl544 =null;

        tmpParser.scheduleDecl_return scheduleDecl545 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 86) ) { return retval; }

            // org\\aries\\tmp.g:518:2: ( assignmentDecl | roleDecl | groupDecl | channelDecl | participantDecl | cacheDecl | persistDecl | scheduleDecl )
            int alt88=8;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt88=1;
                }
                break;
            case 123:
                {
                alt88=2;
                }
                break;
            case 97:
                {
                alt88=3;
                }
                break;
            case 83:
                {
                alt88=4;
                }
                break;
            case 115:
                {
                alt88=5;
                }
                break;
            case 82:
                {
                alt88=6;
                }
                break;
            case 116:
                {
                alt88=7;
                }
                break;
            case 124:
                {
                alt88=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;

            }

            switch (alt88) {
                case 1 :
                    // org\\aries\\tmp.g:518:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_protocolMember2939);
                    assignmentDecl538=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl538.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:519:4: roleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_roleDecl_in_protocolMember2944);
                    roleDecl539=roleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, roleDecl539.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:520:4: groupDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_groupDecl_in_protocolMember2949);
                    groupDecl540=groupDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, groupDecl540.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:521:4: channelDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_channelDecl_in_protocolMember2954);
                    channelDecl541=channelDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, channelDecl541.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:522:4: participantDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_participantDecl_in_protocolMember2959);
                    participantDecl542=participantDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, participantDecl542.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:523:4: cacheDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cacheDecl_in_protocolMember2964);
                    cacheDecl543=cacheDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheDecl543.getTree());

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp.g:524:4: persistDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_persistDecl_in_protocolMember2969);
                    persistDecl544=persistDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistDecl544.getTree());

                    }
                    break;
                case 8 :
                    // org\\aries\\tmp.g:525:4: scheduleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_scheduleDecl_in_protocolMember2974);
                    scheduleDecl545=scheduleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleDecl545.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 86, protocolMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "protocolMember"


    public static class receiveDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveDecl"
    // org\\aries\\tmp.g:528:1: receiveDecl : 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' ) ;
    public final tmpParser.receiveDecl_return receiveDecl() throws RecognitionException {
        tmpParser.receiveDecl_return retval = new tmpParser.receiveDecl_return();
        retval.start = input.LT(1);

        int receiveDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal546=null;
        Token Identifier547=null;
        Token char_literal551=null;
        tmpParser.formalParametersSignature_return formalParametersSignature548 =null;

        tmpParser.throwsBody_return throwsBody549 =null;

        tmpParser.receiveBody_return receiveBody550 =null;


        Object string_literal546_tree=null;
        Object Identifier547_tree=null;
        Object char_literal551_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 87) ) { return retval; }

            // org\\aries\\tmp.g:529:2: ( 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' ) )
            // org\\aries\\tmp.g:529:4: 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' )
            {
            root_0 = (Object)adaptor.nil();


            string_literal546=(Token)match(input,119,FOLLOW_119_in_receiveDecl2985); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal546_tree = 
            (Object)adaptor.create(string_literal546)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal546_tree, root_0);
            }

            Identifier547=(Token)match(input,Identifier,FOLLOW_Identifier_in_receiveDecl2988); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier547_tree = 
            (Object)adaptor.create(Identifier547)
            ;
            adaptor.addChild(root_0, Identifier547_tree);
            }

            pushFollow(FOLLOW_formalParametersSignature_in_receiveDecl2990);
            formalParametersSignature548=formalParametersSignature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParametersSignature548.getTree());

            // org\\aries\\tmp.g:529:52: ( throwsBody )?
            int alt89=2;
            switch ( input.LA(1) ) {
                case 134:
                    {
                    alt89=1;
                    }
                    break;
            }

            switch (alt89) {
                case 1 :
                    // org\\aries\\tmp.g:529:52: throwsBody
                    {
                    pushFollow(FOLLOW_throwsBody_in_receiveDecl2992);
                    throwsBody549=throwsBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsBody549.getTree());

                    }
                    break;

            }


            // org\\aries\\tmp.g:529:64: ( receiveBody | ';' )
            int alt90=2;
            switch ( input.LA(1) ) {
            case 138:
                {
                alt90=1;
                }
                break;
            case 65:
                {
                alt90=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;

            }

            switch (alt90) {
                case 1 :
                    // org\\aries\\tmp.g:529:65: receiveBody
                    {
                    pushFollow(FOLLOW_receiveBody_in_receiveDecl2996);
                    receiveBody550=receiveBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveBody550.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:529:79: ';'
                    {
                    char_literal551=(Token)match(input,65,FOLLOW_65_in_receiveDecl3000); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal551_tree = 
                    (Object)adaptor.create(char_literal551)
                    ;
                    adaptor.addChild(root_0, char_literal551_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 87, receiveDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "receiveDecl"


    public static class receiveBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveBody"
    // org\\aries\\tmp.g:532:1: receiveBody : '{' ( receiveMember )* '}' ;
    public final tmpParser.receiveBody_return receiveBody() throws RecognitionException {
        tmpParser.receiveBody_return retval = new tmpParser.receiveBody_return();
        retval.start = input.LT(1);

        int receiveBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal552=null;
        Token char_literal554=null;
        tmpParser.receiveMember_return receiveMember553 =null;


        Object char_literal552_tree=null;
        Object char_literal554_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 88) ) { return retval; }

            // org\\aries\\tmp.g:533:2: ( '{' ( receiveMember )* '}' )
            // org\\aries\\tmp.g:533:4: '{' ( receiveMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal552=(Token)match(input,138,FOLLOW_138_in_receiveBody3012); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal552_tree = 
            (Object)adaptor.create(char_literal552)
            ;
            adaptor.addChild(root_0, char_literal552_tree);
            }

            // org\\aries\\tmp.g:533:8: ( receiveMember )*
            loop91:
            do {
                int alt91=2;
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
                case 91:
                case 93:
                case 94:
                case 95:
                case 96:
                case 98:
                case 99:
                case 100:
                case 102:
                case 103:
                case 105:
                case 107:
                case 108:
                case 113:
                case 114:
                case 117:
                case 120:
                case 122:
                case 126:
                case 128:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt91=1;
                    }
                    break;

                }

                switch (alt91) {
            	case 1 :
            	    // org\\aries\\tmp.g:533:9: receiveMember
            	    {
            	    pushFollow(FOLLOW_receiveMember_in_receiveBody3015);
            	    receiveMember553=receiveMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveMember553.getTree());

            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);


            char_literal554=(Token)match(input,142,FOLLOW_142_in_receiveBody3019); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal554_tree = 
            (Object)adaptor.create(char_literal554)
            ;
            adaptor.addChild(root_0, char_literal554_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 88, receiveBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "receiveBody"


    public static class receiveMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveMember"
    // org\\aries\\tmp.g:536:1: receiveMember : ( assignmentDecl | optionDecl | executeDecl | listenDecl | invokeDecl | statementDecl | doneDecl );
    public final tmpParser.receiveMember_return receiveMember() throws RecognitionException {
        tmpParser.receiveMember_return retval = new tmpParser.receiveMember_return();
        retval.start = input.LT(1);

        int receiveMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl555 =null;

        tmpParser.optionDecl_return optionDecl556 =null;

        tmpParser.executeDecl_return executeDecl557 =null;

        tmpParser.listenDecl_return listenDecl558 =null;

        tmpParser.invokeDecl_return invokeDecl559 =null;

        tmpParser.statementDecl_return statementDecl560 =null;

        tmpParser.doneDecl_return doneDecl561 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 89) ) { return retval; }

            // org\\aries\\tmp.g:537:2: ( assignmentDecl | optionDecl | executeDecl | listenDecl | invokeDecl | statementDecl | doneDecl )
            int alt92=7;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt92=1;
                }
                break;
            case 114:
                {
                alt92=2;
                }
                break;
            case 94:
                {
                alt92=3;
                }
                break;
            case 107:
                {
                alt92=4;
                }
                break;
            case 103:
                {
                alt92=5;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
                {
                alt92=6;
                }
                break;
            case 90:
                {
                alt92=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;

            }

            switch (alt92) {
                case 1 :
                    // org\\aries\\tmp.g:537:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_receiveMember3030);
                    assignmentDecl555=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl555.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:538:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_receiveMember3035);
                    optionDecl556=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl556.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:539:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_receiveMember3040);
                    executeDecl557=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl557.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:540:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_receiveMember3045);
                    listenDecl558=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl558.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:541:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_receiveMember3050);
                    invokeDecl559=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl559.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:542:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_receiveMember3055);
                    statementDecl560=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl560.getTree());

                    }
                    break;
                case 7 :
                    // org\\aries\\tmp.g:544:4: doneDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_doneDecl_in_receiveMember3061);
                    doneDecl561=doneDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doneDecl561.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 89, receiveMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "receiveMember"


    public static class roleDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "roleDecl"
    // org\\aries\\tmp.g:547:1: roleDecl : 'role' ^ Identifier roleBody ;
    public final tmpParser.roleDecl_return roleDecl() throws RecognitionException {
        tmpParser.roleDecl_return retval = new tmpParser.roleDecl_return();
        retval.start = input.LT(1);

        int roleDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal562=null;
        Token Identifier563=null;
        tmpParser.roleBody_return roleBody564 =null;


        Object string_literal562_tree=null;
        Object Identifier563_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 90) ) { return retval; }

            // org\\aries\\tmp.g:548:2: ( 'role' ^ Identifier roleBody )
            // org\\aries\\tmp.g:548:4: 'role' ^ Identifier roleBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal562=(Token)match(input,123,FOLLOW_123_in_roleDecl3072); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal562_tree = 
            (Object)adaptor.create(string_literal562)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal562_tree, root_0);
            }

            Identifier563=(Token)match(input,Identifier,FOLLOW_Identifier_in_roleDecl3075); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier563_tree = 
            (Object)adaptor.create(Identifier563)
            ;
            adaptor.addChild(root_0, Identifier563_tree);
            }

            pushFollow(FOLLOW_roleBody_in_roleDecl3077);
            roleBody564=roleBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, roleBody564.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 90, roleDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "roleDecl"


    public static class roleBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "roleBody"
    // org\\aries\\tmp.g:551:1: roleBody : '{' ( roleMember )* '}' ;
    public final tmpParser.roleBody_return roleBody() throws RecognitionException {
        tmpParser.roleBody_return retval = new tmpParser.roleBody_return();
        retval.start = input.LT(1);

        int roleBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal565=null;
        Token char_literal567=null;
        tmpParser.roleMember_return roleMember566 =null;


        Object char_literal565_tree=null;
        Object char_literal567_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 91) ) { return retval; }

            // org\\aries\\tmp.g:552:2: ( '{' ( roleMember )* '}' )
            // org\\aries\\tmp.g:552:4: '{' ( roleMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal565=(Token)match(input,138,FOLLOW_138_in_roleBody3088); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal565_tree = 
            (Object)adaptor.create(char_literal565)
            ;
            adaptor.addChild(root_0, char_literal565_tree);
            }

            // org\\aries\\tmp.g:552:8: ( roleMember )*
            loop93:
            do {
                int alt93=2;
                switch ( input.LA(1) ) {
                case 65:
                    {
                    alt93=1;
                    }
                    break;

                }

                switch (alt93) {
            	case 1 :
            	    // org\\aries\\tmp.g:552:9: roleMember
            	    {
            	    pushFollow(FOLLOW_roleMember_in_roleBody3091);
            	    roleMember566=roleMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, roleMember566.getTree());

            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);


            char_literal567=(Token)match(input,142,FOLLOW_142_in_roleBody3095); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal567_tree = 
            (Object)adaptor.create(char_literal567)
            ;
            adaptor.addChild(root_0, char_literal567_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 91, roleBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "roleBody"


    public static class roleMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "roleMember"
    // org\\aries\\tmp.g:555:1: roleMember : ';' ;
    public final tmpParser.roleMember_return roleMember() throws RecognitionException {
        tmpParser.roleMember_return retval = new tmpParser.roleMember_return();
        retval.start = input.LT(1);

        int roleMember_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal568=null;

        Object char_literal568_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 92) ) { return retval; }

            // org\\aries\\tmp.g:556:2: ( ';' )
            // org\\aries\\tmp.g:556:4: ';'
            {
            root_0 = (Object)adaptor.nil();


            char_literal568=(Token)match(input,65,FOLLOW_65_in_roleMember3106); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal568_tree = 
            (Object)adaptor.create(char_literal568)
            ;
            adaptor.addChild(root_0, char_literal568_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 92, roleMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "roleMember"


    public static class scheduleDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "scheduleDecl"
    // org\\aries\\tmp.g:559:1: scheduleDecl : 'schedule' ^ Identifier scheduleBody ;
    public final tmpParser.scheduleDecl_return scheduleDecl() throws RecognitionException {
        tmpParser.scheduleDecl_return retval = new tmpParser.scheduleDecl_return();
        retval.start = input.LT(1);

        int scheduleDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal569=null;
        Token Identifier570=null;
        tmpParser.scheduleBody_return scheduleBody571 =null;


        Object string_literal569_tree=null;
        Object Identifier570_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 93) ) { return retval; }

            // org\\aries\\tmp.g:560:2: ( 'schedule' ^ Identifier scheduleBody )
            // org\\aries\\tmp.g:560:4: 'schedule' ^ Identifier scheduleBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal569=(Token)match(input,124,FOLLOW_124_in_scheduleDecl3117); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal569_tree = 
            (Object)adaptor.create(string_literal569)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal569_tree, root_0);
            }

            Identifier570=(Token)match(input,Identifier,FOLLOW_Identifier_in_scheduleDecl3120); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier570_tree = 
            (Object)adaptor.create(Identifier570)
            ;
            adaptor.addChild(root_0, Identifier570_tree);
            }

            pushFollow(FOLLOW_scheduleBody_in_scheduleDecl3122);
            scheduleBody571=scheduleBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleBody571.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 93, scheduleDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "scheduleDecl"


    public static class scheduleBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "scheduleBody"
    // org\\aries\\tmp.g:563:1: scheduleBody : '{' ( scheduleMember )* '}' ;
    public final tmpParser.scheduleBody_return scheduleBody() throws RecognitionException {
        tmpParser.scheduleBody_return retval = new tmpParser.scheduleBody_return();
        retval.start = input.LT(1);

        int scheduleBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal572=null;
        Token char_literal574=null;
        tmpParser.scheduleMember_return scheduleMember573 =null;


        Object char_literal572_tree=null;
        Object char_literal574_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 94) ) { return retval; }

            // org\\aries\\tmp.g:564:2: ( '{' ( scheduleMember )* '}' )
            // org\\aries\\tmp.g:564:4: '{' ( scheduleMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal572=(Token)match(input,138,FOLLOW_138_in_scheduleBody3133); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal572_tree = 
            (Object)adaptor.create(char_literal572)
            ;
            adaptor.addChild(root_0, char_literal572_tree);
            }

            // org\\aries\\tmp.g:564:8: ( scheduleMember )*
            loop94:
            do {
                int alt94=2;
                switch ( input.LA(1) ) {
                case 65:
                case 76:
                case 93:
                case 99:
                case 100:
                case 103:
                case 105:
                case 117:
                case 119:
                case 120:
                case 126:
                case 128:
                case 133:
                    {
                    alt94=1;
                    }
                    break;

                }

                switch (alt94) {
            	case 1 :
            	    // org\\aries\\tmp.g:564:9: scheduleMember
            	    {
            	    pushFollow(FOLLOW_scheduleMember_in_scheduleBody3136);
            	    scheduleMember573=scheduleMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleMember573.getTree());

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            char_literal574=(Token)match(input,142,FOLLOW_142_in_scheduleBody3140); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal574_tree = 
            (Object)adaptor.create(char_literal574)
            ;
            adaptor.addChild(root_0, char_literal574_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 94, scheduleBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "scheduleBody"


    public static class scheduleMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "scheduleMember"
    // org\\aries\\tmp.g:567:1: scheduleMember : ( assignmentDecl | receiveDecl | invokeDecl | schedulableCommandDecl );
    public final tmpParser.scheduleMember_return scheduleMember() throws RecognitionException {
        tmpParser.scheduleMember_return retval = new tmpParser.scheduleMember_return();
        retval.start = input.LT(1);

        int scheduleMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.assignmentDecl_return assignmentDecl575 =null;

        tmpParser.receiveDecl_return receiveDecl576 =null;

        tmpParser.invokeDecl_return invokeDecl577 =null;

        tmpParser.schedulableCommandDecl_return schedulableCommandDecl578 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 95) ) { return retval; }

            // org\\aries\\tmp.g:568:2: ( assignmentDecl | receiveDecl | invokeDecl | schedulableCommandDecl )
            int alt95=4;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 128:
                {
                alt95=1;
                }
                break;
            case 119:
                {
                alt95=2;
                }
                break;
            case 103:
                {
                alt95=3;
                }
                break;
            case 65:
            case 93:
            case 117:
            case 120:
            case 126:
            case 133:
                {
                alt95=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;

            }

            switch (alt95) {
                case 1 :
                    // org\\aries\\tmp.g:568:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_scheduleMember3152);
                    assignmentDecl575=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl575.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:569:4: receiveDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_receiveDecl_in_scheduleMember3157);
                    receiveDecl576=receiveDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveDecl576.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:570:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_scheduleMember3162);
                    invokeDecl577=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl577.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:571:4: schedulableCommandDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_schedulableCommandDecl_in_scheduleMember3167);
                    schedulableCommandDecl578=schedulableCommandDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, schedulableCommandDecl578.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 95, scheduleMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "scheduleMember"


    public static class schedulableCommandDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "schedulableCommandDecl"
    // org\\aries\\tmp.g:575:1: schedulableCommandDecl : ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | 'throw' ^ Identifier ( formalParameters )? ';' | ';' );
    public final tmpParser.schedulableCommandDecl_return schedulableCommandDecl() throws RecognitionException {
        tmpParser.schedulableCommandDecl_return retval = new tmpParser.schedulableCommandDecl_return();
        retval.start = input.LT(1);

        int schedulableCommandDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal579=null;
        Token char_literal580=null;
        Token string_literal581=null;
        Token Identifier582=null;
        Token char_literal584=null;
        Token string_literal585=null;
        Token Identifier586=null;
        Token char_literal588=null;
        Token string_literal589=null;
        Token char_literal592=null;
        Token string_literal593=null;
        Token Identifier594=null;
        Token char_literal596=null;
        Token char_literal597=null;
        tmpParser.formalParameters_return formalParameters583 =null;

        tmpParser.formalParameters_return formalParameters587 =null;

        tmpParser.qualifiedName_return qualifiedName590 =null;

        tmpParser.formalParameters_return formalParameters591 =null;

        tmpParser.formalParameters_return formalParameters595 =null;


        Object string_literal579_tree=null;
        Object char_literal580_tree=null;
        Object string_literal581_tree=null;
        Object Identifier582_tree=null;
        Object char_literal584_tree=null;
        Object string_literal585_tree=null;
        Object Identifier586_tree=null;
        Object char_literal588_tree=null;
        Object string_literal589_tree=null;
        Object char_literal592_tree=null;
        Object string_literal593_tree=null;
        Object Identifier594_tree=null;
        Object char_literal596_tree=null;
        Object char_literal597_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 96) ) { return retval; }

            // org\\aries\\tmp.g:576:2: ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | 'throw' ^ Identifier ( formalParameters )? ';' | ';' )
            int alt100=6;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt100=1;
                }
                break;
            case 117:
                {
                alt100=2;
                }
                break;
            case 120:
                {
                alt100=3;
                }
                break;
            case 126:
                {
                alt100=4;
                }
                break;
            case 133:
                {
                alt100=5;
                }
                break;
            case 65:
                {
                alt100=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;

            }

            switch (alt100) {
                case 1 :
                    // org\\aries\\tmp.g:576:4: 'end' ^ ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal579=(Token)match(input,93,FOLLOW_93_in_schedulableCommandDecl3180); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal579_tree = 
                    (Object)adaptor.create(string_literal579)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal579_tree, root_0);
                    }

                    char_literal580=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3183); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal580_tree = 
                    (Object)adaptor.create(char_literal580)
                    ;
                    adaptor.addChild(root_0, char_literal580_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:577:4: 'post' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal581=(Token)match(input,117,FOLLOW_117_in_schedulableCommandDecl3188); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal581_tree = 
                    (Object)adaptor.create(string_literal581)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal581_tree, root_0);
                    }

                    Identifier582=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3191); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier582_tree = 
                    (Object)adaptor.create(Identifier582)
                    ;
                    adaptor.addChild(root_0, Identifier582_tree);
                    }

                    // org\\aries\\tmp.g:577:23: ( formalParameters )?
                    int alt96=2;
                    switch ( input.LA(1) ) {
                        case 50:
                            {
                            alt96=1;
                            }
                            break;
                    }

                    switch (alt96) {
                        case 1 :
                            // org\\aries\\tmp.g:577:23: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3193);
                            formalParameters583=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters583.getTree());

                            }
                            break;

                    }


                    char_literal584=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3196); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal584_tree = 
                    (Object)adaptor.create(char_literal584)
                    ;
                    adaptor.addChild(root_0, char_literal584_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:578:4: 'reply' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal585=(Token)match(input,120,FOLLOW_120_in_schedulableCommandDecl3201); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal585_tree = 
                    (Object)adaptor.create(string_literal585)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal585_tree, root_0);
                    }

                    Identifier586=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3204); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier586_tree = 
                    (Object)adaptor.create(Identifier586)
                    ;
                    adaptor.addChild(root_0, Identifier586_tree);
                    }

                    // org\\aries\\tmp.g:578:24: ( formalParameters )?
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
                            // org\\aries\\tmp.g:578:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3206);
                            formalParameters587=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters587.getTree());

                            }
                            break;

                    }


                    char_literal588=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3209); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal588_tree = 
                    (Object)adaptor.create(char_literal588)
                    ;
                    adaptor.addChild(root_0, char_literal588_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\tmp.g:579:4: 'send' ^ qualifiedName ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal589=(Token)match(input,126,FOLLOW_126_in_schedulableCommandDecl3214); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal589_tree = 
                    (Object)adaptor.create(string_literal589)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal589_tree, root_0);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_schedulableCommandDecl3217);
                    qualifiedName590=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName590.getTree());

                    // org\\aries\\tmp.g:579:26: ( formalParameters )?
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
                            // org\\aries\\tmp.g:579:26: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3219);
                            formalParameters591=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters591.getTree());

                            }
                            break;

                    }


                    char_literal592=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3222); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal592_tree = 
                    (Object)adaptor.create(char_literal592)
                    ;
                    adaptor.addChild(root_0, char_literal592_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\tmp.g:580:4: 'throw' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal593=(Token)match(input,133,FOLLOW_133_in_schedulableCommandDecl3227); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal593_tree = 
                    (Object)adaptor.create(string_literal593)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal593_tree, root_0);
                    }

                    Identifier594=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3230); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier594_tree = 
                    (Object)adaptor.create(Identifier594)
                    ;
                    adaptor.addChild(root_0, Identifier594_tree);
                    }

                    // org\\aries\\tmp.g:580:24: ( formalParameters )?
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
                            // org\\aries\\tmp.g:580:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3232);
                            formalParameters595=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters595.getTree());

                            }
                            break;

                    }


                    char_literal596=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3235); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal596_tree = 
                    (Object)adaptor.create(char_literal596)
                    ;
                    adaptor.addChild(root_0, char_literal596_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\tmp.g:581:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal597=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3240); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal597_tree = 
                    (Object)adaptor.create(char_literal597)
                    ;
                    adaptor.addChild(root_0, char_literal597_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 96, schedulableCommandDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "schedulableCommandDecl"


    public static class throwsBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsBody"
    // org\\aries\\tmp.g:584:1: throwsBody : 'throws' ^ throwsList ;
    public final tmpParser.throwsBody_return throwsBody() throws RecognitionException {
        tmpParser.throwsBody_return retval = new tmpParser.throwsBody_return();
        retval.start = input.LT(1);

        int throwsBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal598=null;
        tmpParser.throwsList_return throwsList599 =null;


        Object string_literal598_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 97) ) { return retval; }

            // org\\aries\\tmp.g:585:2: ( 'throws' ^ throwsList )
            // org\\aries\\tmp.g:585:4: 'throws' ^ throwsList
            {
            root_0 = (Object)adaptor.nil();


            string_literal598=(Token)match(input,134,FOLLOW_134_in_throwsBody3252); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal598_tree = 
            (Object)adaptor.create(string_literal598)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal598_tree, root_0);
            }

            pushFollow(FOLLOW_throwsList_in_throwsBody3255);
            throwsList599=throwsList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsList599.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 97, throwsBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsBody"


    public static class throwsList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsList"
    // org\\aries\\tmp.g:588:1: throwsList : ( throwsListDecls )? ;
    public final tmpParser.throwsList_return throwsList() throws RecognitionException {
        tmpParser.throwsList_return retval = new tmpParser.throwsList_return();
        retval.start = input.LT(1);

        int throwsList_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.throwsListDecls_return throwsListDecls600 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 98) ) { return retval; }

            // org\\aries\\tmp.g:589:2: ( ( throwsListDecls )? )
            // org\\aries\\tmp.g:589:4: ( throwsListDecls )?
            {
            root_0 = (Object)adaptor.nil();


            // org\\aries\\tmp.g:589:4: ( throwsListDecls )?
            int alt101=2;
            switch ( input.LA(1) ) {
                case Identifier:
                    {
                    alt101=1;
                    }
                    break;
            }

            switch (alt101) {
                case 1 :
                    // org\\aries\\tmp.g:589:4: throwsListDecls
                    {
                    pushFollow(FOLLOW_throwsListDecls_in_throwsList3267);
                    throwsListDecls600=throwsListDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDecls600.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 98, throwsList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsList"


    public static class throwsListDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsListDecls"
    // org\\aries\\tmp.g:592:1: throwsListDecls : throwsListDeclsRest ;
    public final tmpParser.throwsListDecls_return throwsListDecls() throws RecognitionException {
        tmpParser.throwsListDecls_return retval = new tmpParser.throwsListDecls_return();
        retval.start = input.LT(1);

        int throwsListDecls_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.throwsListDeclsRest_return throwsListDeclsRest601 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 99) ) { return retval; }

            // org\\aries\\tmp.g:593:2: ( throwsListDeclsRest )
            // org\\aries\\tmp.g:593:4: throwsListDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_throwsListDeclsRest_in_throwsListDecls3280);
            throwsListDeclsRest601=throwsListDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDeclsRest601.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 99, throwsListDecls_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsListDecls"


    public static class throwsListDeclsRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "throwsListDeclsRest"
    // org\\aries\\tmp.g:596:1: throwsListDeclsRest : Identifier ( ',' throwsListDeclsRest )? ;
    public final tmpParser.throwsListDeclsRest_return throwsListDeclsRest() throws RecognitionException {
        tmpParser.throwsListDeclsRest_return retval = new tmpParser.throwsListDeclsRest_return();
        retval.start = input.LT(1);

        int throwsListDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier602=null;
        Token char_literal603=null;
        tmpParser.throwsListDeclsRest_return throwsListDeclsRest604 =null;


        Object Identifier602_tree=null;
        Object char_literal603_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 100) ) { return retval; }

            // org\\aries\\tmp.g:597:2: ( Identifier ( ',' throwsListDeclsRest )? )
            // org\\aries\\tmp.g:597:4: Identifier ( ',' throwsListDeclsRest )?
            {
            root_0 = (Object)adaptor.nil();


            Identifier602=(Token)match(input,Identifier,FOLLOW_Identifier_in_throwsListDeclsRest3292); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier602_tree = 
            (Object)adaptor.create(Identifier602)
            ;
            adaptor.addChild(root_0, Identifier602_tree);
            }

            // org\\aries\\tmp.g:597:15: ( ',' throwsListDeclsRest )?
            int alt102=2;
            switch ( input.LA(1) ) {
                case 57:
                    {
                    alt102=1;
                    }
                    break;
            }

            switch (alt102) {
                case 1 :
                    // org\\aries\\tmp.g:597:16: ',' throwsListDeclsRest
                    {
                    char_literal603=(Token)match(input,57,FOLLOW_57_in_throwsListDeclsRest3295); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal603_tree = 
                    (Object)adaptor.create(char_literal603)
                    ;
                    adaptor.addChild(root_0, char_literal603_tree);
                    }

                    pushFollow(FOLLOW_throwsListDeclsRest_in_throwsListDeclsRest3297);
                    throwsListDeclsRest604=throwsListDeclsRest();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDeclsRest604.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 100, throwsListDeclsRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "throwsListDeclsRest"


    public static class timeoutDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeoutDecl"
    // org\\aries\\tmp.g:600:1: timeoutDecl : 'timeout' ^ ':' timeoutBody ;
    public final tmpParser.timeoutDecl_return timeoutDecl() throws RecognitionException {
        tmpParser.timeoutDecl_return retval = new tmpParser.timeoutDecl_return();
        retval.start = input.LT(1);

        int timeoutDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal605=null;
        Token char_literal606=null;
        tmpParser.timeoutBody_return timeoutBody607 =null;


        Object string_literal605_tree=null;
        Object char_literal606_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 101) ) { return retval; }

            // org\\aries\\tmp.g:601:2: ( 'timeout' ^ ':' timeoutBody )
            // org\\aries\\tmp.g:601:4: 'timeout' ^ ':' timeoutBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal605=(Token)match(input,135,FOLLOW_135_in_timeoutDecl3311); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal605_tree = 
            (Object)adaptor.create(string_literal605)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal605_tree, root_0);
            }

            char_literal606=(Token)match(input,64,FOLLOW_64_in_timeoutDecl3314); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal606_tree = 
            (Object)adaptor.create(char_literal606)
            ;
            adaptor.addChild(root_0, char_literal606_tree);
            }

            pushFollow(FOLLOW_timeoutBody_in_timeoutDecl3316);
            timeoutBody607=timeoutBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutBody607.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 101, timeoutDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeoutDecl"


    public static class timeoutBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeoutBody"
    // org\\aries\\tmp.g:604:1: timeoutBody : '{' ( timeoutMember )* '}' ;
    public final tmpParser.timeoutBody_return timeoutBody() throws RecognitionException {
        tmpParser.timeoutBody_return retval = new tmpParser.timeoutBody_return();
        retval.start = input.LT(1);

        int timeoutBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal608=null;
        Token char_literal610=null;
        tmpParser.timeoutMember_return timeoutMember609 =null;


        Object char_literal608_tree=null;
        Object char_literal610_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 102) ) { return retval; }

            // org\\aries\\tmp.g:605:2: ( '{' ( timeoutMember )* '}' )
            // org\\aries\\tmp.g:605:4: '{' ( timeoutMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal608=(Token)match(input,138,FOLLOW_138_in_timeoutBody3328); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal608_tree = 
            (Object)adaptor.create(char_literal608)
            ;
            adaptor.addChild(root_0, char_literal608_tree);
            }

            // org\\aries\\tmp.g:605:8: ( timeoutMember )*
            loop103:
            do {
                int alt103=2;
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 108:
                case 113:
                case 114:
                case 117:
                case 120:
                case 122:
                case 126:
                case 129:
                case 133:
                case 137:
                case 138:
                case 143:
                    {
                    alt103=1;
                    }
                    break;

                }

                switch (alt103) {
            	case 1 :
            	    // org\\aries\\tmp.g:605:9: timeoutMember
            	    {
            	    pushFollow(FOLLOW_timeoutMember_in_timeoutBody3331);
            	    timeoutMember609=timeoutMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutMember609.getTree());

            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);


            char_literal610=(Token)match(input,142,FOLLOW_142_in_timeoutBody3335); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal610_tree = 
            (Object)adaptor.create(char_literal610)
            ;
            adaptor.addChild(root_0, char_literal610_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 102, timeoutBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeoutBody"


    public static class timeoutMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "timeoutMember"
    // org\\aries\\tmp.g:608:1: timeoutMember : ( optionDecl | statementDecl | doneDecl );
    public final tmpParser.timeoutMember_return timeoutMember() throws RecognitionException {
        tmpParser.timeoutMember_return retval = new tmpParser.timeoutMember_return();
        retval.start = input.LT(1);

        int timeoutMember_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.optionDecl_return optionDecl611 =null;

        tmpParser.statementDecl_return statementDecl612 =null;

        tmpParser.doneDecl_return doneDecl613 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 103) ) { return retval; }

            // org\\aries\\tmp.g:609:2: ( optionDecl | statementDecl | doneDecl )
            int alt104=3;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt104=1;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 108:
            case 113:
            case 117:
            case 120:
            case 122:
            case 126:
            case 129:
            case 133:
            case 137:
            case 138:
            case 143:
                {
                alt104=2;
                }
                break;
            case 90:
                {
                alt104=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;

            }

            switch (alt104) {
                case 1 :
                    // org\\aries\\tmp.g:609:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_timeoutMember3346);
                    optionDecl611=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl611.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:610:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_timeoutMember3351);
                    statementDecl612=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl612.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\tmp.g:612:4: doneDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_doneDecl_in_timeoutMember3357);
                    doneDecl613=doneDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doneDecl613.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 103, timeoutMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "timeoutMember"


    public static class typeRef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "typeRef"
    // org\\aries\\tmp.g:617:1: typeRef : Identifier ':' Identifier ;
    public final tmpParser.typeRef_return typeRef() throws RecognitionException {
        tmpParser.typeRef_return retval = new tmpParser.typeRef_return();
        retval.start = input.LT(1);

        int typeRef_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier614=null;
        Token char_literal615=null;
        Token Identifier616=null;

        Object Identifier614_tree=null;
        Object char_literal615_tree=null;
        Object Identifier616_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 104) ) { return retval; }

            // org\\aries\\tmp.g:618:2: ( Identifier ':' Identifier )
            // org\\aries\\tmp.g:618:4: Identifier ':' Identifier
            {
            root_0 = (Object)adaptor.nil();


            Identifier614=(Token)match(input,Identifier,FOLLOW_Identifier_in_typeRef3370); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier614_tree = 
            (Object)adaptor.create(Identifier614)
            ;
            adaptor.addChild(root_0, Identifier614_tree);
            }

            char_literal615=(Token)match(input,64,FOLLOW_64_in_typeRef3372); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal615_tree = 
            (Object)adaptor.create(char_literal615)
            ;
            adaptor.addChild(root_0, char_literal615_tree);
            }

            Identifier616=(Token)match(input,Identifier,FOLLOW_Identifier_in_typeRef3374); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier616_tree = 
            (Object)adaptor.create(Identifier616)
            ;
            adaptor.addChild(root_0, Identifier616_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 104, typeRef_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "typeRef"


    public static class formalParameters_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameters"
    // org\\aries\\tmp.g:633:1: formalParameters : '(' ( formalParameterDecls )? ')' ;
    public final tmpParser.formalParameters_return formalParameters() throws RecognitionException {
        tmpParser.formalParameters_return retval = new tmpParser.formalParameters_return();
        retval.start = input.LT(1);

        int formalParameters_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal617=null;
        Token char_literal619=null;
        tmpParser.formalParameterDecls_return formalParameterDecls618 =null;


        Object char_literal617_tree=null;
        Object char_literal619_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 105) ) { return retval; }

            // org\\aries\\tmp.g:634:2: ( '(' ( formalParameterDecls )? ')' )
            // org\\aries\\tmp.g:634:4: '(' ( formalParameterDecls )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal617=(Token)match(input,50,FOLLOW_50_in_formalParameters3398); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal617_tree = 
            (Object)adaptor.create(char_literal617)
            ;
            adaptor.addChild(root_0, char_literal617_tree);
            }

            // org\\aries\\tmp.g:634:8: ( formalParameterDecls )?
            int alt105=2;
            switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                    {
                    alt105=1;
                    }
                    break;
            }

            switch (alt105) {
                case 1 :
                    // org\\aries\\tmp.g:634:8: formalParameterDecls
                    {
                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameters3400);
                    formalParameterDecls618=formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDecls618.getTree());

                    }
                    break;

            }


            char_literal619=(Token)match(input,51,FOLLOW_51_in_formalParameters3403); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal619_tree = 
            (Object)adaptor.create(char_literal619)
            ;
            adaptor.addChild(root_0, char_literal619_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 105, formalParameters_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameters"


    public static class formalParameterDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterDecls"
    // org\\aries\\tmp.g:637:1: formalParameterDecls : formalParameterDeclsRest ;
    public final tmpParser.formalParameterDecls_return formalParameterDecls() throws RecognitionException {
        tmpParser.formalParameterDecls_return retval = new tmpParser.formalParameterDecls_return();
        retval.start = input.LT(1);

        int formalParameterDecls_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.formalParameterDeclsRest_return formalParameterDeclsRest620 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 106) ) { return retval; }

            // org\\aries\\tmp.g:638:2: ( formalParameterDeclsRest )
            // org\\aries\\tmp.g:638:4: formalParameterDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameterDeclsRest_in_formalParameterDecls3415);
            formalParameterDeclsRest620=formalParameterDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDeclsRest620.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 106, formalParameterDecls_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterDecls"


    public static class formalParameterDeclsRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterDeclsRest"
    // org\\aries\\tmp.g:641:1: formalParameterDeclsRest : qualifiedName ( ',' formalParameterDecls )? ;
    public final tmpParser.formalParameterDeclsRest_return formalParameterDeclsRest() throws RecognitionException {
        tmpParser.formalParameterDeclsRest_return retval = new tmpParser.formalParameterDeclsRest_return();
        retval.start = input.LT(1);

        int formalParameterDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal622=null;
        tmpParser.qualifiedName_return qualifiedName621 =null;

        tmpParser.formalParameterDecls_return formalParameterDecls623 =null;


        Object char_literal622_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 107) ) { return retval; }

            // org\\aries\\tmp.g:642:2: ( qualifiedName ( ',' formalParameterDecls )? )
            // org\\aries\\tmp.g:642:4: qualifiedName ( ',' formalParameterDecls )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_formalParameterDeclsRest3427);
            qualifiedName621=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName621.getTree());

            // org\\aries\\tmp.g:642:18: ( ',' formalParameterDecls )?
            int alt106=2;
            switch ( input.LA(1) ) {
                case 57:
                    {
                    alt106=1;
                    }
                    break;
            }

            switch (alt106) {
                case 1 :
                    // org\\aries\\tmp.g:642:19: ',' formalParameterDecls
                    {
                    char_literal622=(Token)match(input,57,FOLLOW_57_in_formalParameterDeclsRest3430); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal622_tree = 
                    (Object)adaptor.create(char_literal622)
                    ;
                    adaptor.addChild(root_0, char_literal622_tree);
                    }

                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameterDeclsRest3432);
                    formalParameterDecls623=formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDecls623.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 107, formalParameterDeclsRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterDeclsRest"


    public static class formalParametersSignature_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParametersSignature"
    // org\\aries\\tmp.g:645:1: formalParametersSignature : '(' ( formalParameterSignatureDecls )? ')' ;
    public final tmpParser.formalParametersSignature_return formalParametersSignature() throws RecognitionException {
        tmpParser.formalParametersSignature_return retval = new tmpParser.formalParametersSignature_return();
        retval.start = input.LT(1);

        int formalParametersSignature_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal624=null;
        Token char_literal626=null;
        tmpParser.formalParameterSignatureDecls_return formalParameterSignatureDecls625 =null;


        Object char_literal624_tree=null;
        Object char_literal626_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 108) ) { return retval; }

            // org\\aries\\tmp.g:646:2: ( '(' ( formalParameterSignatureDecls )? ')' )
            // org\\aries\\tmp.g:646:4: '(' ( formalParameterSignatureDecls )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal624=(Token)match(input,50,FOLLOW_50_in_formalParametersSignature3445); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal624_tree = 
            (Object)adaptor.create(char_literal624)
            ;
            adaptor.addChild(root_0, char_literal624_tree);
            }

            // org\\aries\\tmp.g:646:8: ( formalParameterSignatureDecls )?
            int alt107=2;
            switch ( input.LA(1) ) {
                case EXCEPTION:
                case Identifier:
                case MESSAGE:
                case 78:
                case 81:
                case 84:
                case 91:
                case 95:
                case 102:
                case 108:
                case 129:
                    {
                    alt107=1;
                    }
                    break;
            }

            switch (alt107) {
                case 1 :
                    // org\\aries\\tmp.g:646:8: formalParameterSignatureDecls
                    {
                    pushFollow(FOLLOW_formalParameterSignatureDecls_in_formalParametersSignature3447);
                    formalParameterSignatureDecls625=formalParameterSignatureDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDecls625.getTree());

                    }
                    break;

            }


            char_literal626=(Token)match(input,51,FOLLOW_51_in_formalParametersSignature3450); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal626_tree = 
            (Object)adaptor.create(char_literal626)
            ;
            adaptor.addChild(root_0, char_literal626_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 108, formalParametersSignature_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParametersSignature"


    public static class formalParameterSignatureDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterSignatureDecls"
    // org\\aries\\tmp.g:649:1: formalParameterSignatureDecls : formalParameterSignatureDeclsRest ;
    public final tmpParser.formalParameterSignatureDecls_return formalParameterSignatureDecls() throws RecognitionException {
        tmpParser.formalParameterSignatureDecls_return retval = new tmpParser.formalParameterSignatureDecls_return();
        retval.start = input.LT(1);

        int formalParameterSignatureDecls_StartIndex = input.index();

        Object root_0 = null;

        tmpParser.formalParameterSignatureDeclsRest_return formalParameterSignatureDeclsRest627 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 109) ) { return retval; }

            // org\\aries\\tmp.g:650:2: ( formalParameterSignatureDeclsRest )
            // org\\aries\\tmp.g:650:4: formalParameterSignatureDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameterSignatureDeclsRest_in_formalParameterSignatureDecls3462);
            formalParameterSignatureDeclsRest627=formalParameterSignatureDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDeclsRest627.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 109, formalParameterSignatureDecls_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterSignatureDecls"


    public static class formalParameterSignatureDeclsRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formalParameterSignatureDeclsRest"
    // org\\aries\\tmp.g:653:1: formalParameterSignatureDeclsRest : type Identifier ( ',' formalParameterSignatureDecls )? ;
    public final tmpParser.formalParameterSignatureDeclsRest_return formalParameterSignatureDeclsRest() throws RecognitionException {
        tmpParser.formalParameterSignatureDeclsRest_return retval = new tmpParser.formalParameterSignatureDeclsRest_return();
        retval.start = input.LT(1);

        int formalParameterSignatureDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier629=null;
        Token char_literal630=null;
        tmpParser.type_return type628 =null;

        tmpParser.formalParameterSignatureDecls_return formalParameterSignatureDecls631 =null;


        Object Identifier629_tree=null;
        Object char_literal630_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 110) ) { return retval; }

            // org\\aries\\tmp.g:654:2: ( type Identifier ( ',' formalParameterSignatureDecls )? )
            // org\\aries\\tmp.g:654:4: type Identifier ( ',' formalParameterSignatureDecls )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_formalParameterSignatureDeclsRest3474);
            type628=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type628.getTree());

            Identifier629=(Token)match(input,Identifier,FOLLOW_Identifier_in_formalParameterSignatureDeclsRest3476); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier629_tree = 
            (Object)adaptor.create(Identifier629)
            ;
            adaptor.addChild(root_0, Identifier629_tree);
            }

            // org\\aries\\tmp.g:654:20: ( ',' formalParameterSignatureDecls )?
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
                    // org\\aries\\tmp.g:654:21: ',' formalParameterSignatureDecls
                    {
                    char_literal630=(Token)match(input,57,FOLLOW_57_in_formalParameterSignatureDeclsRest3479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal630_tree = 
                    (Object)adaptor.create(char_literal630)
                    ;
                    adaptor.addChild(root_0, char_literal630_tree);
                    }

                    pushFollow(FOLLOW_formalParameterSignatureDecls_in_formalParameterSignatureDeclsRest3481);
                    formalParameterSignatureDecls631=formalParameterSignatureDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDecls631.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 110, formalParameterSignatureDeclsRest_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "formalParameterSignatureDeclsRest"


    public static class qualifiedNameList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "qualifiedNameList"
    // org\\aries\\tmp.g:657:1: qualifiedNameList : qualifiedName ( ',' qualifiedName )* ;
    public final tmpParser.qualifiedNameList_return qualifiedNameList() throws RecognitionException {
        tmpParser.qualifiedNameList_return retval = new tmpParser.qualifiedNameList_return();
        retval.start = input.LT(1);

        int qualifiedNameList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal633=null;
        tmpParser.qualifiedName_return qualifiedName632 =null;

        tmpParser.qualifiedName_return qualifiedName634 =null;


        Object char_literal633_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 111) ) { return retval; }

            // org\\aries\\tmp.g:658:2: ( qualifiedName ( ',' qualifiedName )* )
            // org\\aries\\tmp.g:658:4: qualifiedName ( ',' qualifiedName )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList3494);
            qualifiedName632=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName632.getTree());

            // org\\aries\\tmp.g:658:18: ( ',' qualifiedName )*
            loop109:
            do {
                int alt109=2;
                switch ( input.LA(1) ) {
                case 57:
                    {
                    alt109=1;
                    }
                    break;

                }

                switch (alt109) {
            	case 1 :
            	    // org\\aries\\tmp.g:658:19: ',' qualifiedName
            	    {
            	    char_literal633=(Token)match(input,57,FOLLOW_57_in_qualifiedNameList3497); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal633_tree = 
            	    (Object)adaptor.create(char_literal633)
            	    ;
            	    adaptor.addChild(root_0, char_literal633_tree);
            	    }

            	    pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList3499);
            	    qualifiedName634=qualifiedName();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName634.getTree());

            	    }
            	    break;

            	default :
            	    break loop109;
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
            if ( state.backtracking>0 ) { memoize(input, 111, qualifiedNameList_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "qualifiedNameList"


    public static class qualifiedName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "qualifiedName"
    // org\\aries\\tmp.g:661:1: qualifiedName : ( ( identifier '.' )=> identifier '.' qualifiedName | identifier );
    public final tmpParser.qualifiedName_return qualifiedName() throws RecognitionException {
        tmpParser.qualifiedName_return retval = new tmpParser.qualifiedName_return();
        retval.start = input.LT(1);

        int qualifiedName_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal636=null;
        tmpParser.identifier_return identifier635 =null;

        tmpParser.qualifiedName_return qualifiedName637 =null;

        tmpParser.identifier_return identifier638 =null;


        Object char_literal636_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 112) ) { return retval; }

            // org\\aries\\tmp.g:662:2: ( ( identifier '.' )=> identifier '.' qualifiedName | identifier )
            int alt110=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA110_1 = input.LA(2);

                if ( (synpred10_tmp()) ) {
                    alt110=1;
                }
                else if ( (true) ) {
                    alt110=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 110, 1, input);

                    throw nvae;

                }
                }
                break;
            case EXCEPTION:
            case MESSAGE:
                {
                int LA110_2 = input.LA(2);

                if ( (synpred10_tmp()) ) {
                    alt110=1;
                }
                else if ( (true) ) {
                    alt110=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 110, 2, input);

                    throw nvae;

                }
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
                    // org\\aries\\tmp.g:662:4: ( identifier '.' )=> identifier '.' qualifiedName
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_identifier_in_qualifiedName3520);
                    identifier635=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier635.getTree());

                    char_literal636=(Token)match(input,61,FOLLOW_61_in_qualifiedName3522); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal636_tree = 
                    (Object)adaptor.create(char_literal636)
                    ;
                    adaptor.addChild(root_0, char_literal636_tree);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_qualifiedName3524);
                    qualifiedName637=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName637.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:663:4: identifier
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_identifier_in_qualifiedName3529);
                    identifier638=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier638.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 112, qualifiedName_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "qualifiedName"


    public static class identifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "identifier"
    // org\\aries\\tmp.g:668:1: identifier : ( Identifier | keyword );
    public final tmpParser.identifier_return identifier() throws RecognitionException {
        tmpParser.identifier_return retval = new tmpParser.identifier_return();
        retval.start = input.LT(1);

        int identifier_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier639=null;
        tmpParser.keyword_return keyword640 =null;


        Object Identifier639_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 113) ) { return retval; }

            // org\\aries\\tmp.g:669:2: ( Identifier | keyword )
            int alt111=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                alt111=1;
                }
                break;
            case EXCEPTION:
            case MESSAGE:
                {
                alt111=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;

            }

            switch (alt111) {
                case 1 :
                    // org\\aries\\tmp.g:669:4: Identifier
                    {
                    root_0 = (Object)adaptor.nil();


                    Identifier639=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifier3543); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier639_tree = 
                    (Object)adaptor.create(Identifier639)
                    ;
                    adaptor.addChild(root_0, Identifier639_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\tmp.g:670:4: keyword
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_keyword_in_identifier3548);
                    keyword640=keyword();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, keyword640.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 113, identifier_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "identifier"


    public static class keyword_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "keyword"
    // org\\aries\\tmp.g:673:1: keyword : ( 'exception' | 'message' );
    public final tmpParser.keyword_return keyword() throws RecognitionException {
        tmpParser.keyword_return retval = new tmpParser.keyword_return();
        retval.start = input.LT(1);

        int keyword_StartIndex = input.index();

        Object root_0 = null;

        Token set641=null;

        Object set641_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 114) ) { return retval; }

            // org\\aries\\tmp.g:674:2: ( 'exception' | 'message' )
            // org\\aries\\tmp.g:
            {
            root_0 = (Object)adaptor.nil();


            set641=(Token)input.LT(1);

            if ( input.LA(1)==EXCEPTION||input.LA(1)==MESSAGE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set641)
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
            if ( state.backtracking>0 ) { memoize(input, 114, keyword_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "keyword"

    // $ANTLR start synpred1_tmp
    public final void synpred1_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:123:48: ( 'else' )
        // org\\aries\\tmp.g:123:49: 'else'
        {
        match(input,92,FOLLOW_92_in_synpred1_tmp793); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_tmp

    // $ANTLR start synpred2_tmp
    public final void synpred2_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:257:4: ( qualifiedName '(' ')' )
        // org\\aries\\tmp.g:257:5: qualifiedName '(' ')'
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred2_tmp1628);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,50,FOLLOW_50_in_synpred2_tmp1630); if (state.failed) return ;

        match(input,51,FOLLOW_51_in_synpred2_tmp1632); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_tmp

    // $ANTLR start synpred3_tmp
    public final void synpred3_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:258:4: ( qualifiedName '(' )
        // org\\aries\\tmp.g:258:5: qualifiedName '('
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred3_tmp1661);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,50,FOLLOW_50_in_synpred3_tmp1663); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_tmp

    // $ANTLR start synpred4_tmp
    public final void synpred4_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:259:4: ( qualifiedName '[' )
        // org\\aries\\tmp.g:259:5: qualifiedName '['
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred4_tmp1696);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,71,FOLLOW_71_in_synpred4_tmp1698); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_tmp

    // $ANTLR start synpred5_tmp
    public final void synpred5_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:260:4: ( type Identifier '=' )
        // org\\aries\\tmp.g:260:5: type Identifier '='
        {
        pushFollow(FOLLOW_type_in_synpred5_tmp1731);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input,Identifier,FOLLOW_Identifier_in_synpred5_tmp1733); if (state.failed) return ;

        match(input,67,FOLLOW_67_in_synpred5_tmp1735); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_tmp

    // $ANTLR start synpred6_tmp
    public final void synpred6_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:261:4: ( qualifiedName '.' )
        // org\\aries\\tmp.g:261:5: qualifiedName '.'
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred6_tmp1758);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,61,FOLLOW_61_in_synpred6_tmp1760); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_tmp

    // $ANTLR start synpred7_tmp
    public final void synpred7_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:267:4: ( '(' )
        // org\\aries\\tmp.g:267:5: '('
        {
        match(input,50,FOLLOW_50_in_synpred7_tmp1813); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_tmp

    // $ANTLR start synpred8_tmp
    public final void synpred8_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:268:4: ( '.' )
        // org\\aries\\tmp.g:268:5: '.'
        {
        match(input,61,FOLLOW_61_in_synpred8_tmp1824); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred8_tmp

    // $ANTLR start synpred9_tmp
    public final void synpred9_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:269:4: ( '[' )
        // org\\aries\\tmp.g:269:5: '['
        {
        match(input,71,FOLLOW_71_in_synpred9_tmp1839); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_tmp

    // $ANTLR start synpred10_tmp
    public final void synpred10_tmp_fragment() throws RecognitionException {
        // org\\aries\\tmp.g:662:4: ( identifier '.' )
        // org\\aries\\tmp.g:662:5: identifier '.'
        {
        pushFollow(FOLLOW_identifier_in_synpred10_tmp3513);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        match(input,61,FOLLOW_61_in_synpred10_tmp3515); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred10_tmp

    // Delegated rules

    public final boolean synpred3_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_tmp() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_tmp_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_importDecl_in_compilationUnit114 = new BitSet(new long[]{0x0000000000000000L,0x0040000800000000L});
    public static final BitSet FOLLOW_protocolDecl_in_compilationUnit117 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_EOF_in_compilationUnit120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_assignmentDecl136 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl138 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl140 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
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
    public static final BitSet FOLLOW_128_in_assignmentDecl184 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_assignmentDecl187 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl189 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_assignmentDecl191 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl193 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl201 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_assignmentDecl204 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl206 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl208 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl218 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_assignmentDecl221 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl223 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_LevelType_in_assignmentDecl225 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl235 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_assignmentDecl238 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl240 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IntegerLiteral_in_assignmentDecl242 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl252 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_111_in_assignmentDecl255 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl257 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IntegerLiteral_in_assignmentDecl259 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl261 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl269 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_assignmentDecl272 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl274 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl276 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl286 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_115_in_assignmentDecl289 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl291 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl293 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl303 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_assignmentDecl306 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl308 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_assignmentDecl310 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl320 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_123_in_assignmentDecl323 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl325 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl327 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl329 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_assignmentDecl337 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_123_in_assignmentDecl340 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl342 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl344 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl346 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl354 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_125_in_assignmentDecl357 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl359 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_ScopeType_in_assignmentDecl361 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl363 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl371 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_130_in_assignmentDecl374 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl376 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl378 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl380 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl388 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_131_in_assignmentDecl391 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl393 = new BitSet(new long[]{0x0000002000008000L});
    public static final BitSet FOLLOW_set_in_assignmentDecl395 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl403 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl411 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_135_in_assignmentDecl414 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl416 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_TimeoutLiteral_in_assignmentDecl418 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_assignmentDecl428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_136_in_assignmentDecl431 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl433 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_TransactionType_in_assignmentDecl435 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl437 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_assignmentDecl445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_136_in_assignmentDecl448 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl450 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl452 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_assignmentDecl462 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_assignmentDecl465 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl467 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl469 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_assignmentDecl479 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_assignmentDecl482 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl484 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl486 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl488 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_branchDecl502 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000001L});
    public static final BitSet FOLLOW_Identifier_in_branchDecl505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_branchDecl508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_branchBody_in_branchDecl510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_branchBody523 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261845A9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_branchMember_in_branchBody526 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261845A9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_142_in_branchBody530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_branchMember541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_branchMember547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_branchMember552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_cacheDecl565 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_cacheDecl568 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_cacheBody_in_cacheDecl570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_cacheBody582 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000004001L});
    public static final BitSet FOLLOW_cacheMember_in_cacheBody585 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000004001L});
    public static final BitSet FOLLOW_142_in_cacheBody589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_cacheMember600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemsDecl_in_cacheMember605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_channelDecl616 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelDecl619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_138_in_channelDecl621 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001002L,0x0000000000004001L});
    public static final BitSet FOLLOW_channelBody_in_channelDecl624 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001002L,0x0000000000004001L});
    public static final BitSet FOLLOW_142_in_channelDecl628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody639 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_channelBody641 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody643 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody645 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody647 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody654 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_127_in_channelBody656 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody658 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody660 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody662 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody669 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_channelBody671 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody673 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody675 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody677 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_channelBody684 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_channelBody686 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody688 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody690 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody692 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_commandDecl710 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_commandDecl718 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_commandDecl721 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl723 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_commandDecl731 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_commandDecl734 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl736 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_commandDecl744 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_commandDecl747 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl749 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_statementDecl765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_statementDecl780 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl783 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl785 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl787 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x0000000000008622L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl789 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_statementDecl798 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x0000000000008622L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_statementDecl807 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl810 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124002L,0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaration_in_statementDecl813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl817 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124002L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl820 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl824 = new BitSet(new long[]{0x0CCC0824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionList_in_statementDecl827 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl831 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x0000000000008622L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_137_in_statementDecl838 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl841 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl843 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl845 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x0000000000008622L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_statementDecl852 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x0000000000008622L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl855 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_137_in_statementDecl857 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl859 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl861 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl863 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_statementDecl870 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124002L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_133_in_statementDecl883 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
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
    public static final BitSet FOLLOW_138_in_statementBlock939 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_statementMember_in_statementBlock942 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_142_in_statementBlock946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_statementMember958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variableDeclaration970 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclaration972 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_71_in_variableDeclaration975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_variableDeclaration977 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_67_in_variableDeclaration981 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008402L});
    public static final BitSet FOLLOW_variableInitializer_in_variableDeclaration983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayInitializer_in_variableInitializer1016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_variableInitializer1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_arrayInitializer1033 = new BitSet(new long[]{0x0EC40824C0C18920L,0x0002104088124000L,0x000000000000C402L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1036 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_57_in_arrayInitializer1039 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008402L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1041 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_57_in_arrayInitializer1049 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_142_in_arrayInitializer1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_expressionDecl1066 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_expressionDecl1068 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_expressionDecl1070 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_expressionDecl1072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_expressionDecl1092 = new BitSet(new long[]{0x9122400000000002L,0x000000000000042CL,0x0000000000001000L});
    public static final BitSet FOLLOW_assignmentOperator_in_expressionDecl1095 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionDecl1097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionList1126 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_expressionList1129 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionList1131 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_assignmentOperator1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_assignmentOperator1155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_assignmentOperator1160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_assignmentOperator1165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_assignmentOperator1170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_assignmentOperator1175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_assignmentOperator1180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_assignmentOperator1185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_assignmentOperator1190 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_assignmentOperator1192 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1199 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1201 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1203 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1212 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalExpression1225 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_conditionalExpression1228 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_conditionalExpression1230 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_conditionalExpression1232 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_conditionalExpression_in_conditionalExpression1234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1248 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_141_in_conditionalOrExpression1251 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1253 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1267 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_conditionalAndExpression1270 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1272 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1286 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_139_in_inclusiveOrExpression1289 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1291 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression1305 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_exclusiveOrExpression1308 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression1310 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression1324 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_andExpression1327 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression1329 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1343 = new BitSet(new long[]{0x0000100000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_equalityExpression1347 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1355 = new BitSet(new long[]{0x0000100000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression1369 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpression1372 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression1374 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_relationalOp1388 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_relationalOp1390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_relationalOp1395 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_relationalOp1397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_relationalOp1402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_relationalOp1407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression1419 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_shiftOp_in_shiftExpression1422 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression1424 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_shiftOp1438 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_shiftOp1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_shiftOp1445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_shiftOp1454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1468 = new BitSet(new long[]{0x0440000000000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1472 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1480 = new BitSet(new long[]{0x0440000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1494 = new BitSet(new long[]{0x4010200000000002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1498 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1510 = new BitSet(new long[]{0x4010200000000002L});
    public static final BitSet FOLLOW_54_in_unaryExpression1526 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_unaryExpression1533 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_unaryExpression1540 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_unaryExpression1547 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression1554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_143_in_unaryExpressionNotPlusMinus1566 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_unaryExpressionNotPlusMinus1573 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_unaryExpressionNotPlusMinus1580 = new BitSet(new long[]{0x2884000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_selector_in_unaryExpressionNotPlusMinus1583 = new BitSet(new long[]{0x2884000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_50_in_primary1606 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_primary1608 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1638 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_primary1640 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1669 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_primary1671 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionList_in_primary1673 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1704 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_primary1706 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_primary1708 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_primary1710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_primary1740 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_primary1742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primary1791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_selector1818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_selector1829 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_selector1831 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_arguments_in_selector1833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_selector1844 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionDecl_in_selector1846 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_selector1848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type1861 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_type1864 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_type1866 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_qualifiedName_in_type1888 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000084L});
    public static final BitSet FOLLOW_typeArguments_in_type1890 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_type1894 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_type1896 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_typeList1928 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_typeList1931 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeList1933 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_66_in_typeArguments1947 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000002L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1949 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_57_in_typeArguments1952 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000002L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1954 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_typeArguments1958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeArgument1970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_arguments2030 = new BitSet(new long[]{0x0CCC0824C0C18920L,0x0002104088124000L,0x0000000000008002L});
    public static final BitSet FOLLOW_expressionList_in_arguments2033 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_arguments2037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_doneDecl2098 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_doneDecl2100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXCEPTION_in_exceptionDecl2111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_exceptionDecl2114 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_exceptionBody_in_exceptionDecl2116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_exceptionBody2127 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261045AD934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_exceptionMember_in_exceptionBody2130 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261045AD934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_142_in_exceptionBody2134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_exceptionMember2145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_exceptionMember2150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doneDecl_in_exceptionMember2156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_executeDecl2167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_executeBody_in_executeDecl2170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_executeDecl2175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_132_in_executeDecl2178 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_executeDecl2180 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParameters_in_executeDecl2182 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_executeBody_in_executeDecl2184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_executeDeclRest2196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_executeBody_in_executeDeclRest2199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_executeDeclRest2203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_executeBody2215 = new BitSet(new long[]{0x0000000000000800L,0x0000021800009000L,0x0000000000004081L});
    public static final BitSet FOLLOW_executeMember_in_executeBody2218 = new BitSet(new long[]{0x0000000000000800L,0x0000021800009000L,0x0000000000004081L});
    public static final BitSet FOLLOW_142_in_executeBody2222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_executeMember2234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_branchDecl_in_executeMember2239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_executeMember2244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_executeMember2249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_groupDecl2260 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_groupDecl2263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_groupBody_in_groupDecl2265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_groupBody2276 = new BitSet(new long[]{0x0000000000000000L,0x0000021800001000L,0x0000000000004001L});
    public static final BitSet FOLLOW_groupMember_in_groupBody2279 = new BitSet(new long[]{0x0000000000000000L,0x0000021800001000L,0x0000000000004001L});
    public static final BitSet FOLLOW_142_in_groupBody2283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_groupMember2294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_importDecl2306 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_importDecl2309 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_importDecl2312 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_importDecl2314 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_importDecl2318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_invokeDecl2329 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_invokeDecl2332 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_invokeDecl2334 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_invokeDecl2336 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParameters_in_invokeDecl2338 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_invokeBody_in_invokeDecl2340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_invokeBody2351 = new BitSet(new long[]{0x0000000040000800L,0x0000021800001000L,0x0000000000004081L});
    public static final BitSet FOLLOW_invokeMember_in_invokeBody2354 = new BitSet(new long[]{0x0000000040000800L,0x0000021800001000L,0x0000000000004081L});
    public static final BitSet FOLLOW_142_in_invokeBody2358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_invokeMember2369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_messageDecl_in_invokeMember2374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_invokeMember2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_invokeMember2384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_itemsDecl2395 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_itemsBody_in_itemsDecl2398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_itemsBody2409 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000004002L});
    public static final BitSet FOLLOW_itemsMember_in_itemsBody2412 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000004002L});
    public static final BitSet FOLLOW_142_in_itemsBody2416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemDecl_in_itemsMember2427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_itemDecl2438 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_itemDecl2440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_itemDeclRest_in_itemDecl2442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_itemDeclRest2468 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_itemMember_in_itemDeclRest2471 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_142_in_itemDeclRest2475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_itemDeclRest2480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_itemMember2491 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_itemMember2494 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_itemMember2496 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_itemMember2498 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_itemMember2500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_listenDecl2511 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_listenDecl2514 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_formalParameters_in_listenDecl2516 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_listenBody_in_listenDecl2519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_listenBody2531 = new BitSet(new long[]{0x0CC40824C0C18920L,0x4526125DAD935000L,0x000000000000C6A3L});
    public static final BitSet FOLLOW_listenMember_in_listenBody2534 = new BitSet(new long[]{0x0CC40824C0C18920L,0x4526125DAD935000L,0x000000000000C6A3L});
    public static final BitSet FOLLOW_142_in_listenBody2538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_listenMember2549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_listenMember2554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_listenMember2559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_listenMember2565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_listenMember2570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doneDecl_in_listenMember2575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MESSAGE_in_messageDecl2586 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_messageDecl2589 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_formalParameters_in_messageDecl2591 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_messageDecl2594 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_messageBody_in_messageDecl2596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_messageBody2608 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261ADDED935000L,0x000000000000C623L});
    public static final BitSet FOLLOW_messageMember_in_messageBody2611 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261ADDED935000L,0x000000000000C623L});
    public static final BitSet FOLLOW_142_in_messageBody2615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_messageMember2626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_messageMember2631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_messageMember2636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_messageMember2641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_messageMember2646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_messageMember2651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doneDecl_in_messageMember2657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_methodDecl2669 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParametersSignature_in_methodDecl2672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_methodBody_in_methodDecl2674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_methodBody2685 = new BitSet(new long[]{0x0CC40824C0C18920L,0x452218C5E9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_methodMember_in_methodBody2688 = new BitSet(new long[]{0x0CC40824C0C18920L,0x452218C5E9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_142_in_methodBody2692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_methodMember2703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_methodMember2708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_methodMember2713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_methodMember2718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_optionDecl2730 = new BitSet(new long[]{0x0004000000400000L,0x0000000000000001L});
    public static final BitSet FOLLOW_Identifier_in_optionDecl2733 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_formalParameters_in_optionDecl2736 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_optionDecl2739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_optionBody_in_optionDecl2741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_optionBody2752 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_optionMember_in_optionBody2755 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45221045A9934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_142_in_optionBody2759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_optionMember2770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_participantDecl2785 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_participantDecl2788 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_participantBody_in_participantDecl2790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_participantBody2801 = new BitSet(new long[]{0x0000000040400800L,0x1090021800041000L,0x0000000000004001L});
    public static final BitSet FOLLOW_participantMember_in_participantBody2804 = new BitSet(new long[]{0x0000000040400800L,0x1090021800041000L,0x0000000000004001L});
    public static final BitSet FOLLOW_142_in_participantBody2808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_participantMember2820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_receiveDecl_in_participantMember2825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cacheDecl_in_participantMember2830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_persistDecl_in_participantMember2835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scheduleDecl_in_participantMember2840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodDecl_in_participantMember2845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_persistDecl2856 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_persistDecl2859 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_persistBody_in_persistDecl2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_persistBody2872 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000004001L});
    public static final BitSet FOLLOW_persistMember_in_persistBody2875 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000004001L});
    public static final BitSet FOLLOW_142_in_persistBody2879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_persistMember2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemsDecl_in_persistMember2895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_protocolDecl2906 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_protocolDecl2909 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_protocolBody_in_protocolDecl2911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_protocolBody2922 = new BitSet(new long[]{0x0000000000000000L,0x1818021A000C1000L,0x0000000000004001L});
    public static final BitSet FOLLOW_protocolMember_in_protocolBody2924 = new BitSet(new long[]{0x0000000000000000L,0x1818021A000C1000L,0x0000000000004001L});
    public static final BitSet FOLLOW_142_in_protocolBody2927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_protocolMember2939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_roleDecl_in_protocolMember2944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groupDecl_in_protocolMember2949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_channelDecl_in_protocolMember2954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_participantDecl_in_protocolMember2959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cacheDecl_in_protocolMember2964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_persistDecl_in_protocolMember2969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scheduleDecl_in_protocolMember2974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_receiveDecl2985 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_receiveDecl2988 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParametersSignature_in_receiveDecl2990 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000440L});
    public static final BitSet FOLLOW_throwsBody_in_receiveDecl2992 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_receiveBody_in_receiveDecl2996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_receiveDecl3000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_receiveBody3012 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261ADDED935000L,0x000000000000C623L});
    public static final BitSet FOLLOW_receiveMember_in_receiveBody3015 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261ADDED935000L,0x000000000000C623L});
    public static final BitSet FOLLOW_142_in_receiveBody3019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_receiveMember3030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_receiveMember3035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_receiveMember3040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_receiveMember3045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_receiveMember3050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_receiveMember3055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doneDecl_in_receiveMember3061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_roleDecl3072 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_roleDecl3075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_roleBody_in_roleDecl3077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_roleBody3088 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_roleMember_in_roleBody3091 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_142_in_roleBody3095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_roleMember3106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_scheduleDecl3117 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_scheduleDecl3120 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_scheduleBody_in_scheduleDecl3122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_scheduleBody3133 = new BitSet(new long[]{0x0000000000000000L,0x41A0029820001002L,0x0000000000004021L});
    public static final BitSet FOLLOW_scheduleMember_in_scheduleBody3136 = new BitSet(new long[]{0x0000000000000000L,0x41A0029820001002L,0x0000000000004021L});
    public static final BitSet FOLLOW_142_in_scheduleBody3140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_scheduleMember3152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_receiveDecl_in_scheduleMember3157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_scheduleMember3162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schedulableCommandDecl_in_scheduleMember3167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_schedulableCommandDecl3180 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_schedulableCommandDecl3188 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3191 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3193 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_schedulableCommandDecl3201 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3204 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3206 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_schedulableCommandDecl3214 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_schedulableCommandDecl3217 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3219 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_133_in_schedulableCommandDecl3227 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3230 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3232 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_134_in_throwsBody3252 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_throwsList_in_throwsBody3255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwsListDecls_in_throwsList3267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwsListDeclsRest_in_throwsListDecls3280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_throwsListDeclsRest3292 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_throwsListDeclsRest3295 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_throwsListDeclsRest_in_throwsListDeclsRest3297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_timeoutDecl3311 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_timeoutDecl3314 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_timeoutBody_in_timeoutDecl3316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_timeoutBody3328 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261045AD934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_timeoutMember_in_timeoutBody3331 = new BitSet(new long[]{0x0CC40824C0C18920L,0x45261045AD934000L,0x000000000000C622L});
    public static final BitSet FOLLOW_142_in_timeoutBody3335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_timeoutMember3346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_timeoutMember3351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doneDecl_in_timeoutMember3357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_typeRef3370 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_typeRef3372 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_typeRef3374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_formalParameters3398 = new BitSet(new long[]{0x0008000040400800L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameters3400 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_formalParameters3403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterDeclsRest_in_formalParameterDecls3415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_formalParameterDeclsRest3427 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_formalParameterDeclsRest3430 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameterDeclsRest3432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_formalParametersSignature3445 = new BitSet(new long[]{0x0008000040400800L,0x0000104088124000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterSignatureDecls_in_formalParametersSignature3447 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_formalParametersSignature3450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterSignatureDeclsRest_in_formalParameterSignatureDecls3462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_formalParameterSignatureDeclsRest3474 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_formalParameterSignatureDeclsRest3476 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_formalParameterSignatureDeclsRest3479 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterSignatureDecls_in_formalParameterSignatureDeclsRest3481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList3494 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_qualifiedNameList3497 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList3499 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_identifier_in_qualifiedName3520 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_qualifiedName3522 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedName3524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_qualifiedName3529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_identifier3543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_identifier3548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_synpred1_tmp793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred2_tmp1628 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_synpred2_tmp1630 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_synpred2_tmp1632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred3_tmp1661 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_synpred3_tmp1663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred4_tmp1696 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_synpred4_tmp1698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred5_tmp1731 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_synpred5_tmp1733 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_synpred5_tmp1735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred6_tmp1758 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_synpred6_tmp1760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_synpred7_tmp1813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_synpred8_tmp1824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_synpred9_tmp1839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_synpred10_tmp3513 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_synpred10_tmp3515 = new BitSet(new long[]{0x0000000000000002L});

}