// $ANTLR 3.4 org\\aries\\Ariel.g 2015-06-28 03:15:50

	package org.aries;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class ArielParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BLOCK", "CHARLITERAL", "COMMENT", "Digit", "DoubleLiteral", "DoubleSuffix", "DoubleValueSuffix", "EXCEPTION", "EXPR", "EscapeSequence", "Exponent", "FALSE", "FloatLiteral", "FloatSuffix", "FloatValueSuffix", "HexDigit", "HexPrefix", "ITEM", "Identifier", "IntegerLiteral", "IntegerNumber", "IntegerValueSuffix", "LINE_COMMENT", "Letter", "LevelType", "LongSuffix", "MESSAGE", "NULL", "NonIntegerNumber", "PRIMARY", "STRINGLITERAL", "ScopeType", "THROWS", "TRUE", "TYPE", "TimeValueSuffix", "TimeoutLiteral", "TransactionType", "WS", "'!'", "'!='", "'%'", "'%='", "'&&'", "'&'", "'&='", "'('", "')'", "'*'", "'*='", "'+'", "'++'", "'+='", "','", "'-'", "'--'", "'-='", "'.'", "'/'", "'/='", "':'", "';'", "'<'", "'='", "'=='", "'>'", "'?'", "'['", "']'", "'^'", "'^='", "'adapter'", "'add'", "'backingStore'", "'boolean'", "'branch'", "'break'", "'byte'", "'cache'", "'channel'", "'char'", "'client'", "'condition'", "'continue'", "'do'", "'domain'", "'done'", "'double'", "'else'", "'end'", "'execute'", "'float'", "'for'", "'group'", "'if'", "'import'", "'include'", "'index'", "'int'", "'invoke'", "'items'", "'join'", "'level'", "'listen'", "'long'", "'manager'", "'maxResponse'", "'minResponse'", "'name'", "'namespace'", "'network'", "'new'", "'option'", "'participant'", "'persist'", "'post'", "'project'", "'receive'", "'reply'", "'restriction'", "'return'", "'role'", "'schedule'", "'scope'", "'send'", "'service'", "'set'", "'short'", "'source'", "'synchronous'", "'then'", "'throw'", "'throws'", "'timeout'", "'transaction'", "'while'", "'{'", "'|'", "'|='", "'||'", "'}'", "'~'"
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
    public static final int T__144=144;
    public static final int T__145=145;
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


    public ArielParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public ArielParser(TokenStream input, RecognizerSharedState state) {
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
    public String[] getTokenNames() { return ArielParser.tokenNames; }
    public String getGrammarFileName() { return "org\\aries\\Ariel.g"; }


    public static class compilationUnit_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "compilationUnit"
    // org\\aries\\Ariel.g:43:1: compilationUnit : ( importDecl )* ( networkDecl )* EOF ;
    public final ArielParser.compilationUnit_return compilationUnit() throws RecognitionException {
        ArielParser.compilationUnit_return retval = new ArielParser.compilationUnit_return();
        retval.start = input.LT(1);

        int compilationUnit_StartIndex = input.index();

        Object root_0 = null;

        Token EOF3=null;
        ArielParser.importDecl_return importDecl1 =null;

        ArielParser.networkDecl_return networkDecl2 =null;


        Object EOF3_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }

            // org\\aries\\Ariel.g:44:2: ( ( importDecl )* ( networkDecl )* EOF )
            // org\\aries\\Ariel.g:44:5: ( importDecl )* ( networkDecl )* EOF
            {
            root_0 = (Object)adaptor.nil();


            // org\\aries\\Ariel.g:44:5: ( importDecl )*
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
            	    // org\\aries\\Ariel.g:44:5: importDecl
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


            // org\\aries\\Ariel.g:44:17: ( networkDecl )*
            loop2:
            do {
                int alt2=2;
                switch ( input.LA(1) ) {
                case 114:
                    {
                    alt2=1;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // org\\aries\\Ariel.g:44:17: networkDecl
            	    {
            	    pushFollow(FOLLOW_networkDecl_in_compilationUnit117);
            	    networkDecl2=networkDecl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, networkDecl2.getTree());

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
    // org\\aries\\Ariel.g:48:1: assignmentDecl : ( 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';' | 'set' ^ 'backingStore' '(' Identifier ')' ';' | 'add' ^ 'channel' '(' STRINGLITERAL ')' ';' | 'set' ^ 'condition' '(' Identifier ')' ';' | 'set' ^ 'domain' '(' STRINGLITERAL ')' ';' | 'set' ^ 'level' '(' LevelType ')' ';' | 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'name' '(' STRINGLITERAL ')' ';' | 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'set' ^ 'participant' '(' STRINGLITERAL ')' ';' | 'add' ^ 'project' '(' STRINGLITERAL ')' ';' | 'set' ^ 'restriction' '(' qualifiedName ')' ';' | 'set' ^ 'role' '(' STRINGLITERAL ')' ';' | 'add' ^ 'role' '(' STRINGLITERAL ')' ';' | 'set' ^ 'scope' '(' ScopeType ')' ';' | 'set' ^ 'source' '(' STRINGLITERAL ')' ';' | 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';' | 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';' | 'set' ^ 'transaction' '(' TransactionType ')' ';' | 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';' | 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';' );
    public final ArielParser.assignmentDecl_return assignmentDecl() throws RecognitionException {
        ArielParser.assignmentDecl_return retval = new ArielParser.assignmentDecl_return();
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
        Token STRINGLITERAL67=null;
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
        Token char_literal80=null;
        Token char_literal81=null;
        Token string_literal82=null;
        Token string_literal83=null;
        Token char_literal84=null;
        Token STRINGLITERAL85=null;
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
        Token ScopeType97=null;
        Token char_literal98=null;
        Token char_literal99=null;
        Token string_literal100=null;
        Token string_literal101=null;
        Token char_literal102=null;
        Token STRINGLITERAL103=null;
        Token char_literal104=null;
        Token char_literal105=null;
        Token string_literal106=null;
        Token string_literal107=null;
        Token char_literal108=null;
        Token set109=null;
        Token char_literal110=null;
        Token char_literal111=null;
        Token string_literal112=null;
        Token string_literal113=null;
        Token char_literal114=null;
        Token TimeoutLiteral115=null;
        Token char_literal116=null;
        Token char_literal117=null;
        Token string_literal118=null;
        Token string_literal119=null;
        Token char_literal120=null;
        Token TransactionType121=null;
        Token char_literal122=null;
        Token char_literal123=null;
        Token string_literal124=null;
        Token string_literal125=null;
        Token char_literal126=null;
        Token STRINGLITERAL127=null;
        Token char_literal128=null;
        Token char_literal129=null;
        Token string_literal130=null;
        Token string_literal131=null;
        Token char_literal132=null;
        Token STRINGLITERAL133=null;
        Token char_literal134=null;
        Token char_literal135=null;
        Token string_literal136=null;
        Token string_literal137=null;
        Token char_literal138=null;
        Token STRINGLITERAL139=null;
        Token char_literal140=null;
        Token char_literal141=null;
        ArielParser.qualifiedName_return qualifiedName79 =null;


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
        Object STRINGLITERAL67_tree=null;
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
        Object char_literal80_tree=null;
        Object char_literal81_tree=null;
        Object string_literal82_tree=null;
        Object string_literal83_tree=null;
        Object char_literal84_tree=null;
        Object STRINGLITERAL85_tree=null;
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
        Object ScopeType97_tree=null;
        Object char_literal98_tree=null;
        Object char_literal99_tree=null;
        Object string_literal100_tree=null;
        Object string_literal101_tree=null;
        Object char_literal102_tree=null;
        Object STRINGLITERAL103_tree=null;
        Object char_literal104_tree=null;
        Object char_literal105_tree=null;
        Object string_literal106_tree=null;
        Object string_literal107_tree=null;
        Object char_literal108_tree=null;
        Object set109_tree=null;
        Object char_literal110_tree=null;
        Object char_literal111_tree=null;
        Object string_literal112_tree=null;
        Object string_literal113_tree=null;
        Object char_literal114_tree=null;
        Object TimeoutLiteral115_tree=null;
        Object char_literal116_tree=null;
        Object char_literal117_tree=null;
        Object string_literal118_tree=null;
        Object string_literal119_tree=null;
        Object char_literal120_tree=null;
        Object TransactionType121_tree=null;
        Object char_literal122_tree=null;
        Object char_literal123_tree=null;
        Object string_literal124_tree=null;
        Object string_literal125_tree=null;
        Object char_literal126_tree=null;
        Object STRINGLITERAL127_tree=null;
        Object char_literal128_tree=null;
        Object char_literal129_tree=null;
        Object string_literal130_tree=null;
        Object string_literal131_tree=null;
        Object char_literal132_tree=null;
        Object STRINGLITERAL133_tree=null;
        Object char_literal134_tree=null;
        Object char_literal135_tree=null;
        Object string_literal136_tree=null;
        Object string_literal137_tree=null;
        Object char_literal138_tree=null;
        Object STRINGLITERAL139_tree=null;
        Object char_literal140_tree=null;
        Object char_literal141_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }

            // org\\aries\\Ariel.g:49:2: ( 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';' | 'set' ^ 'backingStore' '(' Identifier ')' ';' | 'add' ^ 'channel' '(' STRINGLITERAL ')' ';' | 'set' ^ 'condition' '(' Identifier ')' ';' | 'set' ^ 'domain' '(' STRINGLITERAL ')' ';' | 'set' ^ 'level' '(' LevelType ')' ';' | 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';' | 'set' ^ 'name' '(' STRINGLITERAL ')' ';' | 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'set' ^ 'participant' '(' STRINGLITERAL ')' ';' | 'add' ^ 'project' '(' STRINGLITERAL ')' ';' | 'set' ^ 'restriction' '(' qualifiedName ')' ';' | 'set' ^ 'role' '(' STRINGLITERAL ')' ';' | 'add' ^ 'role' '(' STRINGLITERAL ')' ';' | 'set' ^ 'scope' '(' ScopeType ')' ';' | 'set' ^ 'source' '(' STRINGLITERAL ')' ';' | 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';' | 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';' | 'set' ^ 'transaction' '(' TransactionType ')' ';' | 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';' | 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';' | 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';' )
            int alt3=23;
            switch ( input.LA(1) ) {
            case 130:
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
                case 113:
                    {
                    alt3=10;
                    }
                    break;
                case 117:
                    {
                    alt3=11;
                    }
                    break;
                case 123:
                    {
                    alt3=13;
                    }
                    break;
                case 125:
                    {
                    alt3=14;
                    }
                    break;
                case 127:
                    {
                    alt3=16;
                    }
                    break;
                case 132:
                    {
                    alt3=17;
                    }
                    break;
                case 133:
                    {
                    alt3=18;
                    }
                    break;
                case 137:
                    {
                    alt3=19;
                    }
                    break;
                case 138:
                    {
                    alt3=20;
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
                case 120:
                    {
                    alt3=12;
                    }
                    break;
                case 125:
                    {
                    alt3=15;
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
                alt3=21;
                }
                break;
            case 100:
                {
                alt3=22;
                }
                break;
            case 99:
                {
                alt3=23;
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
                    // org\\aries\\Ariel.g:49:5: 'set' ^ 'adapter' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal4=(Token)match(input,130,FOLLOW_130_in_assignmentDecl133); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:50:5: 'set' ^ 'backingStore' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal10=(Token)match(input,130,FOLLOW_130_in_assignmentDecl150); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:51:5: 'add' ^ 'channel' '(' STRINGLITERAL ')' ';'
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
                    // org\\aries\\Ariel.g:52:5: 'set' ^ 'condition' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal22=(Token)match(input,130,FOLLOW_130_in_assignmentDecl184); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:53:5: 'set' ^ 'domain' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal28=(Token)match(input,130,FOLLOW_130_in_assignmentDecl201); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:54:5: 'set' ^ 'level' '(' LevelType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal34=(Token)match(input,130,FOLLOW_130_in_assignmentDecl218); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:55:5: 'set' ^ 'maxResponse' '(' IntegerLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal40=(Token)match(input,130,FOLLOW_130_in_assignmentDecl235); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:56:5: 'set' ^ 'minResponse' '(' IntegerLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal46=(Token)match(input,130,FOLLOW_130_in_assignmentDecl252); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:57:5: 'set' ^ 'name' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal52=(Token)match(input,130,FOLLOW_130_in_assignmentDecl269); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:58:5: 'set' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal58=(Token)match(input,130,FOLLOW_130_in_assignmentDecl286); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:59:5: 'set' ^ 'participant' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal64=(Token)match(input,130,FOLLOW_130_in_assignmentDecl303); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal64_tree = 
                    (Object)adaptor.create(string_literal64)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal64_tree, root_0);
                    }

                    string_literal65=(Token)match(input,117,FOLLOW_117_in_assignmentDecl306); if (state.failed) return retval;
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

                    STRINGLITERAL67=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl310); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL67_tree = 
                    (Object)adaptor.create(STRINGLITERAL67)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL67_tree);
                    }

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
                    // org\\aries\\Ariel.g:60:5: 'add' ^ 'project' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal70=(Token)match(input,76,FOLLOW_76_in_assignmentDecl320); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal70_tree = 
                    (Object)adaptor.create(string_literal70)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal70_tree, root_0);
                    }

                    string_literal71=(Token)match(input,120,FOLLOW_120_in_assignmentDecl323); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:61:5: 'set' ^ 'restriction' '(' qualifiedName ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal76=(Token)match(input,130,FOLLOW_130_in_assignmentDecl337); if (state.failed) return retval;
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

                    pushFollow(FOLLOW_qualifiedName_in_assignmentDecl344);
                    qualifiedName79=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName79.getTree());

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
                    // org\\aries\\Ariel.g:62:5: 'set' ^ 'role' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal82=(Token)match(input,130,FOLLOW_130_in_assignmentDecl354); if (state.failed) return retval;
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

                    STRINGLITERAL85=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl361); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL85_tree = 
                    (Object)adaptor.create(STRINGLITERAL85)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL85_tree);
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
                    // org\\aries\\Ariel.g:63:5: 'add' ^ 'role' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal88=(Token)match(input,76,FOLLOW_76_in_assignmentDecl371); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal88_tree = 
                    (Object)adaptor.create(string_literal88)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal88_tree, root_0);
                    }

                    string_literal89=(Token)match(input,125,FOLLOW_125_in_assignmentDecl374); if (state.failed) return retval;
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
                    // org\\aries\\Ariel.g:64:5: 'set' ^ 'scope' '(' ScopeType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal94=(Token)match(input,130,FOLLOW_130_in_assignmentDecl388); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal94_tree = 
                    (Object)adaptor.create(string_literal94)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal94_tree, root_0);
                    }

                    string_literal95=(Token)match(input,127,FOLLOW_127_in_assignmentDecl391); if (state.failed) return retval;
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

                    ScopeType97=(Token)match(input,ScopeType,FOLLOW_ScopeType_in_assignmentDecl395); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ScopeType97_tree = 
                    (Object)adaptor.create(ScopeType97)
                    ;
                    adaptor.addChild(root_0, ScopeType97_tree);
                    }

                    char_literal98=(Token)match(input,51,FOLLOW_51_in_assignmentDecl397); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal98_tree = 
                    (Object)adaptor.create(char_literal98)
                    ;
                    adaptor.addChild(root_0, char_literal98_tree);
                    }

                    char_literal99=(Token)match(input,65,FOLLOW_65_in_assignmentDecl399); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal99_tree = 
                    (Object)adaptor.create(char_literal99)
                    ;
                    adaptor.addChild(root_0, char_literal99_tree);
                    }

                    }
                    break;
                case 17 :
                    // org\\aries\\Ariel.g:65:5: 'set' ^ 'source' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal100=(Token)match(input,130,FOLLOW_130_in_assignmentDecl405); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal100_tree = 
                    (Object)adaptor.create(string_literal100)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal100_tree, root_0);
                    }

                    string_literal101=(Token)match(input,132,FOLLOW_132_in_assignmentDecl408); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal101_tree = 
                    (Object)adaptor.create(string_literal101)
                    ;
                    adaptor.addChild(root_0, string_literal101_tree);
                    }

                    char_literal102=(Token)match(input,50,FOLLOW_50_in_assignmentDecl410); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal102_tree = 
                    (Object)adaptor.create(char_literal102)
                    ;
                    adaptor.addChild(root_0, char_literal102_tree);
                    }

                    STRINGLITERAL103=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl412); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL103_tree = 
                    (Object)adaptor.create(STRINGLITERAL103)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL103_tree);
                    }

                    char_literal104=(Token)match(input,51,FOLLOW_51_in_assignmentDecl414); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal104_tree = 
                    (Object)adaptor.create(char_literal104)
                    ;
                    adaptor.addChild(root_0, char_literal104_tree);
                    }

                    char_literal105=(Token)match(input,65,FOLLOW_65_in_assignmentDecl416); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal105_tree = 
                    (Object)adaptor.create(char_literal105)
                    ;
                    adaptor.addChild(root_0, char_literal105_tree);
                    }

                    }
                    break;
                case 18 :
                    // org\\aries\\Ariel.g:66:5: 'set' ^ 'synchronous' '(' ( TRUE | FALSE ) ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal106=(Token)match(input,130,FOLLOW_130_in_assignmentDecl422); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal106_tree = 
                    (Object)adaptor.create(string_literal106)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal106_tree, root_0);
                    }

                    string_literal107=(Token)match(input,133,FOLLOW_133_in_assignmentDecl425); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal107_tree = 
                    (Object)adaptor.create(string_literal107)
                    ;
                    adaptor.addChild(root_0, string_literal107_tree);
                    }

                    char_literal108=(Token)match(input,50,FOLLOW_50_in_assignmentDecl427); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal108_tree = 
                    (Object)adaptor.create(char_literal108)
                    ;
                    adaptor.addChild(root_0, char_literal108_tree);
                    }

                    set109=(Token)input.LT(1);

                    if ( input.LA(1)==FALSE||input.LA(1)==TRUE ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (Object)adaptor.create(set109)
                        );
                        state.errorRecovery=false;
                        state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
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
                    // org\\aries\\Ariel.g:67:5: 'set' ^ 'timeout' '(' TimeoutLiteral ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal112=(Token)match(input,130,FOLLOW_130_in_assignmentDecl445); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal112_tree = 
                    (Object)adaptor.create(string_literal112)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal112_tree, root_0);
                    }

                    string_literal113=(Token)match(input,137,FOLLOW_137_in_assignmentDecl448); if (state.failed) return retval;
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

                    TimeoutLiteral115=(Token)match(input,TimeoutLiteral,FOLLOW_TimeoutLiteral_in_assignmentDecl452); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TimeoutLiteral115_tree = 
                    (Object)adaptor.create(TimeoutLiteral115)
                    ;
                    adaptor.addChild(root_0, TimeoutLiteral115_tree);
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
                    // org\\aries\\Ariel.g:68:5: 'set' ^ 'transaction' '(' TransactionType ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal118=(Token)match(input,130,FOLLOW_130_in_assignmentDecl462); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal118_tree = 
                    (Object)adaptor.create(string_literal118)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal118_tree, root_0);
                    }

                    string_literal119=(Token)match(input,138,FOLLOW_138_in_assignmentDecl465); if (state.failed) return retval;
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

                    TransactionType121=(Token)match(input,TransactionType,FOLLOW_TransactionType_in_assignmentDecl469); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TransactionType121_tree = 
                    (Object)adaptor.create(TransactionType121)
                    ;
                    adaptor.addChild(root_0, TransactionType121_tree);
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
                    // org\\aries\\Ariel.g:69:5: 'join' ^ 'transaction' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal124=(Token)match(input,105,FOLLOW_105_in_assignmentDecl479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal124_tree = 
                    (Object)adaptor.create(string_literal124)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal124_tree, root_0);
                    }

                    string_literal125=(Token)match(input,138,FOLLOW_138_in_assignmentDecl482); if (state.failed) return retval;
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
                case 22 :
                    // org\\aries\\Ariel.g:70:5: 'include' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal130=(Token)match(input,100,FOLLOW_100_in_assignmentDecl496); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal130_tree = 
                    (Object)adaptor.create(string_literal130)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal130_tree, root_0);
                    }

                    string_literal131=(Token)match(input,113,FOLLOW_113_in_assignmentDecl499); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal131_tree = 
                    (Object)adaptor.create(string_literal131)
                    ;
                    adaptor.addChild(root_0, string_literal131_tree);
                    }

                    char_literal132=(Token)match(input,50,FOLLOW_50_in_assignmentDecl501); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal132_tree = 
                    (Object)adaptor.create(char_literal132)
                    ;
                    adaptor.addChild(root_0, char_literal132_tree);
                    }

                    STRINGLITERAL133=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl503); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL133_tree = 
                    (Object)adaptor.create(STRINGLITERAL133)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL133_tree);
                    }

                    char_literal134=(Token)match(input,51,FOLLOW_51_in_assignmentDecl505); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal134_tree = 
                    (Object)adaptor.create(char_literal134)
                    ;
                    adaptor.addChild(root_0, char_literal134_tree);
                    }

                    char_literal135=(Token)match(input,65,FOLLOW_65_in_assignmentDecl507); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal135_tree = 
                    (Object)adaptor.create(char_literal135)
                    ;
                    adaptor.addChild(root_0, char_literal135_tree);
                    }

                    }
                    break;
                case 23 :
                    // org\\aries\\Ariel.g:71:5: 'import' ^ 'namespace' '(' STRINGLITERAL ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal136=(Token)match(input,99,FOLLOW_99_in_assignmentDecl513); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal136_tree = 
                    (Object)adaptor.create(string_literal136)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal136_tree, root_0);
                    }

                    string_literal137=(Token)match(input,113,FOLLOW_113_in_assignmentDecl516); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal137_tree = 
                    (Object)adaptor.create(string_literal137)
                    ;
                    adaptor.addChild(root_0, string_literal137_tree);
                    }

                    char_literal138=(Token)match(input,50,FOLLOW_50_in_assignmentDecl518); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal138_tree = 
                    (Object)adaptor.create(char_literal138)
                    ;
                    adaptor.addChild(root_0, char_literal138_tree);
                    }

                    STRINGLITERAL139=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_assignmentDecl520); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRINGLITERAL139_tree = 
                    (Object)adaptor.create(STRINGLITERAL139)
                    ;
                    adaptor.addChild(root_0, STRINGLITERAL139_tree);
                    }

                    char_literal140=(Token)match(input,51,FOLLOW_51_in_assignmentDecl522); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal140_tree = 
                    (Object)adaptor.create(char_literal140)
                    ;
                    adaptor.addChild(root_0, char_literal140_tree);
                    }

                    char_literal141=(Token)match(input,65,FOLLOW_65_in_assignmentDecl524); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal141_tree = 
                    (Object)adaptor.create(char_literal141)
                    ;
                    adaptor.addChild(root_0, char_literal141_tree);
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
    // org\\aries\\Ariel.g:74:1: branchDecl : 'branch' ^ ( Identifier )? ':' branchBody ;
    public final ArielParser.branchDecl_return branchDecl() throws RecognitionException {
        ArielParser.branchDecl_return retval = new ArielParser.branchDecl_return();
        retval.start = input.LT(1);

        int branchDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal142=null;
        Token Identifier143=null;
        Token char_literal144=null;
        ArielParser.branchBody_return branchBody145 =null;


        Object string_literal142_tree=null;
        Object Identifier143_tree=null;
        Object char_literal144_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }

            // org\\aries\\Ariel.g:75:2: ( 'branch' ^ ( Identifier )? ':' branchBody )
            // org\\aries\\Ariel.g:75:5: 'branch' ^ ( Identifier )? ':' branchBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal142=(Token)match(input,79,FOLLOW_79_in_branchDecl536); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal142_tree = 
            (Object)adaptor.create(string_literal142)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal142_tree, root_0);
            }

            // org\\aries\\Ariel.g:75:15: ( Identifier )?
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
                    // org\\aries\\Ariel.g:75:15: Identifier
                    {
                    Identifier143=(Token)match(input,Identifier,FOLLOW_Identifier_in_branchDecl539); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier143_tree = 
                    (Object)adaptor.create(Identifier143)
                    ;
                    adaptor.addChild(root_0, Identifier143_tree);
                    }

                    }
                    break;

            }


            char_literal144=(Token)match(input,64,FOLLOW_64_in_branchDecl542); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal144_tree = 
            (Object)adaptor.create(char_literal144)
            ;
            adaptor.addChild(root_0, char_literal144_tree);
            }

            pushFollow(FOLLOW_branchBody_in_branchDecl544);
            branchBody145=branchBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, branchBody145.getTree());

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
    // org\\aries\\Ariel.g:78:1: branchBody : '{' ( branchMember )* '}' ;
    public final ArielParser.branchBody_return branchBody() throws RecognitionException {
        ArielParser.branchBody_return retval = new ArielParser.branchBody_return();
        retval.start = input.LT(1);

        int branchBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal146=null;
        Token char_literal148=null;
        ArielParser.branchMember_return branchMember147 =null;


        Object char_literal146_tree=null;
        Object char_literal148_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }

            // org\\aries\\Ariel.g:79:2: ( '{' ( branchMember )* '}' )
            // org\\aries\\Ariel.g:79:5: '{' ( branchMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal146=(Token)match(input,140,FOLLOW_140_in_branchBody557); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal146_tree = 
            (Object)adaptor.create(char_literal146)
            ;
            adaptor.addChild(root_0, char_literal146_tree);
            }

            // org\\aries\\Ariel.g:79:9: ( branchMember )*
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 103:
                case 107:
                case 108:
                case 115:
                case 116:
                case 119:
                case 122:
                case 124:
                case 128:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt5=1;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // org\\aries\\Ariel.g:79:10: branchMember
            	    {
            	    pushFollow(FOLLOW_branchMember_in_branchBody560);
            	    branchMember147=branchMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, branchMember147.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            char_literal148=(Token)match(input,144,FOLLOW_144_in_branchBody564); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal148_tree = 
            (Object)adaptor.create(char_literal148)
            ;
            adaptor.addChild(root_0, char_literal148_tree);
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
    // org\\aries\\Ariel.g:82:1: branchMember : ( optionDecl | listenDecl | statementDecl );
    public final ArielParser.branchMember_return branchMember() throws RecognitionException {
        ArielParser.branchMember_return retval = new ArielParser.branchMember_return();
        retval.start = input.LT(1);

        int branchMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.optionDecl_return optionDecl149 =null;

        ArielParser.listenDecl_return listenDecl150 =null;

        ArielParser.statementDecl_return statementDecl151 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }

            // org\\aries\\Ariel.g:83:2: ( optionDecl | listenDecl | statementDecl )
            int alt6=3;
            switch ( input.LA(1) ) {
            case 116:
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
            case 90:
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
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
                    // org\\aries\\Ariel.g:83:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_branchMember575);
                    optionDecl149=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl149.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:84:5: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_branchMember581);
                    listenDecl150=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl150.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:85:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_branchMember586);
                    statementDecl151=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl151.getTree());

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
    // org\\aries\\Ariel.g:89:1: cacheDecl : 'cache' ^ Identifier cacheBody ;
    public final ArielParser.cacheDecl_return cacheDecl() throws RecognitionException {
        ArielParser.cacheDecl_return retval = new ArielParser.cacheDecl_return();
        retval.start = input.LT(1);

        int cacheDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal152=null;
        Token Identifier153=null;
        ArielParser.cacheBody_return cacheBody154 =null;


        Object string_literal152_tree=null;
        Object Identifier153_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }

            // org\\aries\\Ariel.g:90:2: ( 'cache' ^ Identifier cacheBody )
            // org\\aries\\Ariel.g:90:5: 'cache' ^ Identifier cacheBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal152=(Token)match(input,82,FOLLOW_82_in_cacheDecl599); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal152_tree = 
            (Object)adaptor.create(string_literal152)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal152_tree, root_0);
            }

            Identifier153=(Token)match(input,Identifier,FOLLOW_Identifier_in_cacheDecl602); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier153_tree = 
            (Object)adaptor.create(Identifier153)
            ;
            adaptor.addChild(root_0, Identifier153_tree);
            }

            pushFollow(FOLLOW_cacheBody_in_cacheDecl604);
            cacheBody154=cacheBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheBody154.getTree());

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
    // org\\aries\\Ariel.g:93:1: cacheBody : '{' ( cacheMember )* '}' ;
    public final ArielParser.cacheBody_return cacheBody() throws RecognitionException {
        ArielParser.cacheBody_return retval = new ArielParser.cacheBody_return();
        retval.start = input.LT(1);

        int cacheBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal155=null;
        Token char_literal157=null;
        ArielParser.cacheMember_return cacheMember156 =null;


        Object char_literal155_tree=null;
        Object char_literal157_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }

            // org\\aries\\Ariel.g:94:2: ( '{' ( cacheMember )* '}' )
            // org\\aries\\Ariel.g:94:5: '{' ( cacheMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal155=(Token)match(input,140,FOLLOW_140_in_cacheBody616); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal155_tree = 
            (Object)adaptor.create(char_literal155)
            ;
            adaptor.addChild(root_0, char_literal155_tree);
            }

            // org\\aries\\Ariel.g:94:9: ( cacheMember )*
            loop7:
            do {
                int alt7=2;
                switch ( input.LA(1) ) {
                case 76:
                case 99:
                case 100:
                case 104:
                case 105:
                case 130:
                    {
                    alt7=1;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // org\\aries\\Ariel.g:94:10: cacheMember
            	    {
            	    pushFollow(FOLLOW_cacheMember_in_cacheBody619);
            	    cacheMember156=cacheMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheMember156.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            char_literal157=(Token)match(input,144,FOLLOW_144_in_cacheBody623); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal157_tree = 
            (Object)adaptor.create(char_literal157)
            ;
            adaptor.addChild(root_0, char_literal157_tree);
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
    // org\\aries\\Ariel.g:97:1: cacheMember : ( assignmentDecl | itemsDecl );
    public final ArielParser.cacheMember_return cacheMember() throws RecognitionException {
        ArielParser.cacheMember_return retval = new ArielParser.cacheMember_return();
        retval.start = input.LT(1);

        int cacheMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl158 =null;

        ArielParser.itemsDecl_return itemsDecl159 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }

            // org\\aries\\Ariel.g:98:2: ( assignmentDecl | itemsDecl )
            int alt8=2;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
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
                    // org\\aries\\Ariel.g:98:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_cacheMember634);
                    assignmentDecl158=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl158.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:99:4: itemsDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_itemsDecl_in_cacheMember639);
                    itemsDecl159=itemsDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsDecl159.getTree());

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
    // org\\aries\\Ariel.g:102:1: channelDecl : 'channel' ^ Identifier '{' ( channelBody )* '}' ;
    public final ArielParser.channelDecl_return channelDecl() throws RecognitionException {
        ArielParser.channelDecl_return retval = new ArielParser.channelDecl_return();
        retval.start = input.LT(1);

        int channelDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal160=null;
        Token Identifier161=null;
        Token char_literal162=null;
        Token char_literal164=null;
        ArielParser.channelBody_return channelBody163 =null;


        Object string_literal160_tree=null;
        Object Identifier161_tree=null;
        Object char_literal162_tree=null;
        Object char_literal164_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }

            // org\\aries\\Ariel.g:103:2: ( 'channel' ^ Identifier '{' ( channelBody )* '}' )
            // org\\aries\\Ariel.g:103:4: 'channel' ^ Identifier '{' ( channelBody )* '}'
            {
            root_0 = (Object)adaptor.nil();


            string_literal160=(Token)match(input,83,FOLLOW_83_in_channelDecl650); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal160_tree = 
            (Object)adaptor.create(string_literal160)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal160_tree, root_0);
            }

            Identifier161=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelDecl653); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier161_tree = 
            (Object)adaptor.create(Identifier161)
            ;
            adaptor.addChild(root_0, Identifier161_tree);
            }

            char_literal162=(Token)match(input,140,FOLLOW_140_in_channelDecl655); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal162_tree = 
            (Object)adaptor.create(char_literal162)
            ;
            adaptor.addChild(root_0, char_literal162_tree);
            }

            // org\\aries\\Ariel.g:103:30: ( channelBody )*
            loop9:
            do {
                int alt9=2;
                switch ( input.LA(1) ) {
                case 65:
                case 76:
                case 130:
                    {
                    alt9=1;
                    }
                    break;

                }

                switch (alt9) {
            	case 1 :
            	    // org\\aries\\Ariel.g:103:31: channelBody
            	    {
            	    pushFollow(FOLLOW_channelBody_in_channelDecl658);
            	    channelBody163=channelBody();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, channelBody163.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            char_literal164=(Token)match(input,144,FOLLOW_144_in_channelDecl662); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal164_tree = 
            (Object)adaptor.create(char_literal164)
            ;
            adaptor.addChild(root_0, char_literal164_tree);
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
    // org\\aries\\Ariel.g:106:1: channelBody : ( 'add' 'client' '(' Identifier ')' ';' | 'add' 'service' '(' Identifier ')' ';' | 'add' 'manager' '(' Identifier ')' ';' | 'set' 'domain' '(' Identifier ')' ';' | ';' );
    public final ArielParser.channelBody_return channelBody() throws RecognitionException {
        ArielParser.channelBody_return retval = new ArielParser.channelBody_return();
        retval.start = input.LT(1);

        int channelBody_StartIndex = input.index();

        Object root_0 = null;

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
        Token string_literal177=null;
        Token string_literal178=null;
        Token char_literal179=null;
        Token Identifier180=null;
        Token char_literal181=null;
        Token char_literal182=null;
        Token string_literal183=null;
        Token string_literal184=null;
        Token char_literal185=null;
        Token Identifier186=null;
        Token char_literal187=null;
        Token char_literal188=null;
        Token char_literal189=null;

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
        Object string_literal177_tree=null;
        Object string_literal178_tree=null;
        Object char_literal179_tree=null;
        Object Identifier180_tree=null;
        Object char_literal181_tree=null;
        Object char_literal182_tree=null;
        Object string_literal183_tree=null;
        Object string_literal184_tree=null;
        Object char_literal185_tree=null;
        Object Identifier186_tree=null;
        Object char_literal187_tree=null;
        Object char_literal188_tree=null;
        Object char_literal189_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }

            // org\\aries\\Ariel.g:107:2: ( 'add' 'client' '(' Identifier ')' ';' | 'add' 'service' '(' Identifier ')' ';' | 'add' 'manager' '(' Identifier ')' ';' | 'set' 'domain' '(' Identifier ')' ';' | ';' )
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
                case 129:
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
            case 130:
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
                    // org\\aries\\Ariel.g:107:4: 'add' 'client' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal165=(Token)match(input,76,FOLLOW_76_in_channelBody673); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal165_tree = 
                    (Object)adaptor.create(string_literal165)
                    ;
                    adaptor.addChild(root_0, string_literal165_tree);
                    }

                    string_literal166=(Token)match(input,85,FOLLOW_85_in_channelBody675); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal166_tree = 
                    (Object)adaptor.create(string_literal166)
                    ;
                    adaptor.addChild(root_0, string_literal166_tree);
                    }

                    char_literal167=(Token)match(input,50,FOLLOW_50_in_channelBody677); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal167_tree = 
                    (Object)adaptor.create(char_literal167)
                    ;
                    adaptor.addChild(root_0, char_literal167_tree);
                    }

                    Identifier168=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody679); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier168_tree = 
                    (Object)adaptor.create(Identifier168)
                    ;
                    adaptor.addChild(root_0, Identifier168_tree);
                    }

                    char_literal169=(Token)match(input,51,FOLLOW_51_in_channelBody681); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal169_tree = 
                    (Object)adaptor.create(char_literal169)
                    ;
                    adaptor.addChild(root_0, char_literal169_tree);
                    }

                    char_literal170=(Token)match(input,65,FOLLOW_65_in_channelBody683); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal170_tree = 
                    (Object)adaptor.create(char_literal170)
                    ;
                    adaptor.addChild(root_0, char_literal170_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:108:4: 'add' 'service' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal171=(Token)match(input,76,FOLLOW_76_in_channelBody688); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal171_tree = 
                    (Object)adaptor.create(string_literal171)
                    ;
                    adaptor.addChild(root_0, string_literal171_tree);
                    }

                    string_literal172=(Token)match(input,129,FOLLOW_129_in_channelBody690); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal172_tree = 
                    (Object)adaptor.create(string_literal172)
                    ;
                    adaptor.addChild(root_0, string_literal172_tree);
                    }

                    char_literal173=(Token)match(input,50,FOLLOW_50_in_channelBody692); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal173_tree = 
                    (Object)adaptor.create(char_literal173)
                    ;
                    adaptor.addChild(root_0, char_literal173_tree);
                    }

                    Identifier174=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody694); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier174_tree = 
                    (Object)adaptor.create(Identifier174)
                    ;
                    adaptor.addChild(root_0, Identifier174_tree);
                    }

                    char_literal175=(Token)match(input,51,FOLLOW_51_in_channelBody696); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal175_tree = 
                    (Object)adaptor.create(char_literal175)
                    ;
                    adaptor.addChild(root_0, char_literal175_tree);
                    }

                    char_literal176=(Token)match(input,65,FOLLOW_65_in_channelBody698); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal176_tree = 
                    (Object)adaptor.create(char_literal176)
                    ;
                    adaptor.addChild(root_0, char_literal176_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:109:4: 'add' 'manager' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal177=(Token)match(input,76,FOLLOW_76_in_channelBody703); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal177_tree = 
                    (Object)adaptor.create(string_literal177)
                    ;
                    adaptor.addChild(root_0, string_literal177_tree);
                    }

                    string_literal178=(Token)match(input,109,FOLLOW_109_in_channelBody705); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal178_tree = 
                    (Object)adaptor.create(string_literal178)
                    ;
                    adaptor.addChild(root_0, string_literal178_tree);
                    }

                    char_literal179=(Token)match(input,50,FOLLOW_50_in_channelBody707); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal179_tree = 
                    (Object)adaptor.create(char_literal179)
                    ;
                    adaptor.addChild(root_0, char_literal179_tree);
                    }

                    Identifier180=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody709); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier180_tree = 
                    (Object)adaptor.create(Identifier180)
                    ;
                    adaptor.addChild(root_0, Identifier180_tree);
                    }

                    char_literal181=(Token)match(input,51,FOLLOW_51_in_channelBody711); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal181_tree = 
                    (Object)adaptor.create(char_literal181)
                    ;
                    adaptor.addChild(root_0, char_literal181_tree);
                    }

                    char_literal182=(Token)match(input,65,FOLLOW_65_in_channelBody713); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal182_tree = 
                    (Object)adaptor.create(char_literal182)
                    ;
                    adaptor.addChild(root_0, char_literal182_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:110:4: 'set' 'domain' '(' Identifier ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal183=(Token)match(input,130,FOLLOW_130_in_channelBody718); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal183_tree = 
                    (Object)adaptor.create(string_literal183)
                    ;
                    adaptor.addChild(root_0, string_literal183_tree);
                    }

                    string_literal184=(Token)match(input,89,FOLLOW_89_in_channelBody720); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal184_tree = 
                    (Object)adaptor.create(string_literal184)
                    ;
                    adaptor.addChild(root_0, string_literal184_tree);
                    }

                    char_literal185=(Token)match(input,50,FOLLOW_50_in_channelBody722); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal185_tree = 
                    (Object)adaptor.create(char_literal185)
                    ;
                    adaptor.addChild(root_0, char_literal185_tree);
                    }

                    Identifier186=(Token)match(input,Identifier,FOLLOW_Identifier_in_channelBody724); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier186_tree = 
                    (Object)adaptor.create(Identifier186)
                    ;
                    adaptor.addChild(root_0, Identifier186_tree);
                    }

                    char_literal187=(Token)match(input,51,FOLLOW_51_in_channelBody726); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal187_tree = 
                    (Object)adaptor.create(char_literal187)
                    ;
                    adaptor.addChild(root_0, char_literal187_tree);
                    }

                    char_literal188=(Token)match(input,65,FOLLOW_65_in_channelBody728); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal188_tree = 
                    (Object)adaptor.create(char_literal188)
                    ;
                    adaptor.addChild(root_0, char_literal188_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:111:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal189=(Token)match(input,65,FOLLOW_65_in_channelBody733); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal189_tree = 
                    (Object)adaptor.create(char_literal189)
                    ;
                    adaptor.addChild(root_0, char_literal189_tree);
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
    // org\\aries\\Ariel.g:114:1: commandDecl : ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | invokeDecl );
    public final ArielParser.commandDecl_return commandDecl() throws RecognitionException {
        ArielParser.commandDecl_return retval = new ArielParser.commandDecl_return();
        retval.start = input.LT(1);

        int commandDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal190=null;
        Token char_literal191=null;
        Token string_literal192=null;
        Token Identifier193=null;
        Token char_literal195=null;
        Token string_literal196=null;
        Token Identifier197=null;
        Token char_literal199=null;
        Token string_literal200=null;
        Token char_literal203=null;
        ArielParser.formalParameters_return formalParameters194 =null;

        ArielParser.formalParameters_return formalParameters198 =null;

        ArielParser.qualifiedName_return qualifiedName201 =null;

        ArielParser.formalParameters_return formalParameters202 =null;

        ArielParser.invokeDecl_return invokeDecl204 =null;


        Object string_literal190_tree=null;
        Object char_literal191_tree=null;
        Object string_literal192_tree=null;
        Object Identifier193_tree=null;
        Object char_literal195_tree=null;
        Object string_literal196_tree=null;
        Object Identifier197_tree=null;
        Object char_literal199_tree=null;
        Object string_literal200_tree=null;
        Object char_literal203_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }

            // org\\aries\\Ariel.g:115:2: ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | invokeDecl )
            int alt14=5;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt14=1;
                }
                break;
            case 119:
                {
                alt14=2;
                }
                break;
            case 122:
                {
                alt14=3;
                }
                break;
            case 128:
                {
                alt14=4;
                }
                break;
            case 103:
                {
                alt14=5;
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
                    // org\\aries\\Ariel.g:115:4: 'end' ^ ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal190=(Token)match(input,93,FOLLOW_93_in_commandDecl744); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal190_tree = 
                    (Object)adaptor.create(string_literal190)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal190_tree, root_0);
                    }

                    char_literal191=(Token)match(input,65,FOLLOW_65_in_commandDecl747); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal191_tree = 
                    (Object)adaptor.create(char_literal191)
                    ;
                    adaptor.addChild(root_0, char_literal191_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:116:4: 'post' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal192=(Token)match(input,119,FOLLOW_119_in_commandDecl752); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal192_tree = 
                    (Object)adaptor.create(string_literal192)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal192_tree, root_0);
                    }

                    Identifier193=(Token)match(input,Identifier,FOLLOW_Identifier_in_commandDecl755); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier193_tree = 
                    (Object)adaptor.create(Identifier193)
                    ;
                    adaptor.addChild(root_0, Identifier193_tree);
                    }

                    // org\\aries\\Ariel.g:116:23: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:116:23: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_commandDecl757);
                            formalParameters194=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters194.getTree());

                            }
                            break;

                    }


                    char_literal195=(Token)match(input,65,FOLLOW_65_in_commandDecl760); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal195_tree = 
                    (Object)adaptor.create(char_literal195)
                    ;
                    adaptor.addChild(root_0, char_literal195_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:117:4: 'reply' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal196=(Token)match(input,122,FOLLOW_122_in_commandDecl765); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal196_tree = 
                    (Object)adaptor.create(string_literal196)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal196_tree, root_0);
                    }

                    Identifier197=(Token)match(input,Identifier,FOLLOW_Identifier_in_commandDecl768); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier197_tree = 
                    (Object)adaptor.create(Identifier197)
                    ;
                    adaptor.addChild(root_0, Identifier197_tree);
                    }

                    // org\\aries\\Ariel.g:117:24: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:117:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_commandDecl770);
                            formalParameters198=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters198.getTree());

                            }
                            break;

                    }


                    char_literal199=(Token)match(input,65,FOLLOW_65_in_commandDecl773); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal199_tree = 
                    (Object)adaptor.create(char_literal199)
                    ;
                    adaptor.addChild(root_0, char_literal199_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:118:4: 'send' ^ qualifiedName ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal200=(Token)match(input,128,FOLLOW_128_in_commandDecl778); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal200_tree = 
                    (Object)adaptor.create(string_literal200)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal200_tree, root_0);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_commandDecl781);
                    qualifiedName201=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName201.getTree());

                    // org\\aries\\Ariel.g:118:26: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:118:26: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_commandDecl783);
                            formalParameters202=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters202.getTree());

                            }
                            break;

                    }


                    char_literal203=(Token)match(input,65,FOLLOW_65_in_commandDecl786); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal203_tree = 
                    (Object)adaptor.create(char_literal203)
                    ;
                    adaptor.addChild(root_0, char_literal203_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:120:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_commandDecl792);
                    invokeDecl204=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl204.getTree());

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
    // org\\aries\\Ariel.g:123:1: statementDecl : ( statementBlock -> ^( BLOCK statementBlock ) | 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )? | 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl | 'while' ^ '(' expressionDecl ')' statementDecl | 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';' | 'return' ^ ( expressionDecl )? ';' | 'throw' ^ expressionDecl ';' | 'break' ^ ( Identifier )? ';' | 'continue' ^ ( Identifier )? ';' | expressionDecl ';' | commandDecl | doneDecl );
    public final ArielParser.statementDecl_return statementDecl() throws RecognitionException {
        ArielParser.statementDecl_return retval = new ArielParser.statementDecl_return();
        retval.start = input.LT(1);

        int statementDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal206=null;
        Token char_literal207=null;
        Token char_literal209=null;
        Token string_literal211=null;
        Token string_literal213=null;
        Token char_literal214=null;
        Token char_literal216=null;
        Token char_literal218=null;
        Token char_literal220=null;
        Token string_literal222=null;
        Token char_literal223=null;
        Token char_literal225=null;
        Token string_literal227=null;
        Token string_literal229=null;
        Token char_literal230=null;
        Token char_literal232=null;
        Token char_literal233=null;
        Token string_literal234=null;
        Token char_literal236=null;
        Token string_literal237=null;
        Token char_literal239=null;
        Token string_literal240=null;
        Token Identifier241=null;
        Token char_literal242=null;
        Token string_literal243=null;
        Token Identifier244=null;
        Token char_literal245=null;
        Token char_literal247=null;
        ArielParser.statementBlock_return statementBlock205 =null;

        ArielParser.expressionDecl_return expressionDecl208 =null;

        ArielParser.statementDecl_return statementDecl210 =null;

        ArielParser.statementDecl_return statementDecl212 =null;

        ArielParser.variableDeclaration_return variableDeclaration215 =null;

        ArielParser.expressionDecl_return expressionDecl217 =null;

        ArielParser.expressionList_return expressionList219 =null;

        ArielParser.statementDecl_return statementDecl221 =null;

        ArielParser.expressionDecl_return expressionDecl224 =null;

        ArielParser.statementDecl_return statementDecl226 =null;

        ArielParser.statementDecl_return statementDecl228 =null;

        ArielParser.expressionDecl_return expressionDecl231 =null;

        ArielParser.expressionDecl_return expressionDecl235 =null;

        ArielParser.expressionDecl_return expressionDecl238 =null;

        ArielParser.expressionDecl_return expressionDecl246 =null;

        ArielParser.commandDecl_return commandDecl248 =null;

        ArielParser.doneDecl_return doneDecl249 =null;


        Object string_literal206_tree=null;
        Object char_literal207_tree=null;
        Object char_literal209_tree=null;
        Object string_literal211_tree=null;
        Object string_literal213_tree=null;
        Object char_literal214_tree=null;
        Object char_literal216_tree=null;
        Object char_literal218_tree=null;
        Object char_literal220_tree=null;
        Object string_literal222_tree=null;
        Object char_literal223_tree=null;
        Object char_literal225_tree=null;
        Object string_literal227_tree=null;
        Object string_literal229_tree=null;
        Object char_literal230_tree=null;
        Object char_literal232_tree=null;
        Object char_literal233_tree=null;
        Object string_literal234_tree=null;
        Object char_literal236_tree=null;
        Object string_literal237_tree=null;
        Object char_literal239_tree=null;
        Object string_literal240_tree=null;
        Object Identifier241_tree=null;
        Object char_literal242_tree=null;
        Object string_literal243_tree=null;
        Object Identifier244_tree=null;
        Object char_literal245_tree=null;
        Object char_literal247_tree=null;
        RewriteRuleSubtreeStream stream_statementBlock=new RewriteRuleSubtreeStream(adaptor,"rule statementBlock");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }

            // org\\aries\\Ariel.g:124:2: ( statementBlock -> ^( BLOCK statementBlock ) | 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )? | 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl | 'while' ^ '(' expressionDecl ')' statementDecl | 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';' | 'return' ^ ( expressionDecl )? ';' | 'throw' ^ expressionDecl ';' | 'break' ^ ( Identifier )? ';' | 'continue' ^ ( Identifier )? ';' | expressionDecl ';' | commandDecl | doneDecl )
            int alt22=12;
            switch ( input.LA(1) ) {
            case 140:
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
            case 139:
                {
                alt22=4;
                }
                break;
            case 88:
                {
                alt22=5;
                }
                break;
            case 124:
                {
                alt22=6;
                }
                break;
            case 135:
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
            case 115:
            case 131:
            case 145:
                {
                alt22=10;
                }
                break;
            case 93:
            case 103:
            case 119:
            case 122:
            case 128:
                {
                alt22=11;
                }
                break;
            case 90:
                {
                alt22=12;
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
                    // org\\aries\\Ariel.g:124:4: statementBlock
                    {
                    pushFollow(FOLLOW_statementBlock_in_statementDecl803);
                    statementBlock205=statementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statementBlock.add(statementBlock205.getTree());

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
                    // 124:19: -> ^( BLOCK statementBlock )
                    {
                        // org\\aries\\Ariel.g:124:22: ^( BLOCK statementBlock )
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
                    // org\\aries\\Ariel.g:126:4: 'if' ^ '(' expressionDecl ')' statementDecl ( ( 'else' )=> 'else' statementDecl )?
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal206=(Token)match(input,98,FOLLOW_98_in_statementDecl818); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal206_tree = 
                    (Object)adaptor.create(string_literal206)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal206_tree, root_0);
                    }

                    char_literal207=(Token)match(input,50,FOLLOW_50_in_statementDecl821); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal207_tree = 
                    (Object)adaptor.create(char_literal207)
                    ;
                    adaptor.addChild(root_0, char_literal207_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl823);
                    expressionDecl208=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl208.getTree());

                    char_literal209=(Token)match(input,51,FOLLOW_51_in_statementDecl825); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal209_tree = 
                    (Object)adaptor.create(char_literal209)
                    ;
                    adaptor.addChild(root_0, char_literal209_tree);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl827);
                    statementDecl210=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl210.getTree());

                    // org\\aries\\Ariel.g:126:47: ( ( 'else' )=> 'else' statementDecl )?
                    int alt15=2;
                    switch ( input.LA(1) ) {
                        case 92:
                            {
                            int LA15_1 = input.LA(2);

                            if ( (synpred1_Ariel()) ) {
                                alt15=1;
                            }
                            }
                            break;
                    }

                    switch (alt15) {
                        case 1 :
                            // org\\aries\\Ariel.g:126:48: ( 'else' )=> 'else' statementDecl
                            {
                            string_literal211=(Token)match(input,92,FOLLOW_92_in_statementDecl836); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            string_literal211_tree = 
                            (Object)adaptor.create(string_literal211)
                            ;
                            adaptor.addChild(root_0, string_literal211_tree);
                            }

                            pushFollow(FOLLOW_statementDecl_in_statementDecl838);
                            statementDecl212=statementDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl212.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:127:4: 'for' ^ '(' ( variableDeclaration )? ';' ( expressionDecl )? ';' ( expressionList )? ')' statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal213=(Token)match(input,96,FOLLOW_96_in_statementDecl845); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal213_tree = 
                    (Object)adaptor.create(string_literal213)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal213_tree, root_0);
                    }

                    char_literal214=(Token)match(input,50,FOLLOW_50_in_statementDecl848); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal214_tree = 
                    (Object)adaptor.create(char_literal214)
                    ;
                    adaptor.addChild(root_0, char_literal214_tree);
                    }

                    // org\\aries\\Ariel.g:127:15: ( variableDeclaration )?
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
                        case 131:
                            {
                            alt16=1;
                            }
                            break;
                    }

                    switch (alt16) {
                        case 1 :
                            // org\\aries\\Ariel.g:127:16: variableDeclaration
                            {
                            pushFollow(FOLLOW_variableDeclaration_in_statementDecl851);
                            variableDeclaration215=variableDeclaration();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, variableDeclaration215.getTree());

                            }
                            break;

                    }


                    char_literal216=(Token)match(input,65,FOLLOW_65_in_statementDecl855); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal216_tree = 
                    (Object)adaptor.create(char_literal216)
                    ;
                    adaptor.addChild(root_0, char_literal216_tree);
                    }

                    // org\\aries\\Ariel.g:127:42: ( expressionDecl )?
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
                        case 115:
                        case 131:
                        case 145:
                            {
                            alt17=1;
                            }
                            break;
                    }

                    switch (alt17) {
                        case 1 :
                            // org\\aries\\Ariel.g:127:43: expressionDecl
                            {
                            pushFollow(FOLLOW_expressionDecl_in_statementDecl858);
                            expressionDecl217=expressionDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl217.getTree());

                            }
                            break;

                    }


                    char_literal218=(Token)match(input,65,FOLLOW_65_in_statementDecl862); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal218_tree = 
                    (Object)adaptor.create(char_literal218)
                    ;
                    adaptor.addChild(root_0, char_literal218_tree);
                    }

                    // org\\aries\\Ariel.g:127:64: ( expressionList )?
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
                        case 115:
                        case 131:
                        case 145:
                            {
                            alt18=1;
                            }
                            break;
                    }

                    switch (alt18) {
                        case 1 :
                            // org\\aries\\Ariel.g:127:65: expressionList
                            {
                            pushFollow(FOLLOW_expressionList_in_statementDecl865);
                            expressionList219=expressionList();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList219.getTree());

                            }
                            break;

                    }


                    char_literal220=(Token)match(input,51,FOLLOW_51_in_statementDecl869); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal220_tree = 
                    (Object)adaptor.create(char_literal220)
                    ;
                    adaptor.addChild(root_0, char_literal220_tree);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl871);
                    statementDecl221=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl221.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:128:4: 'while' ^ '(' expressionDecl ')' statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal222=(Token)match(input,139,FOLLOW_139_in_statementDecl876); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal222_tree = 
                    (Object)adaptor.create(string_literal222)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal222_tree, root_0);
                    }

                    char_literal223=(Token)match(input,50,FOLLOW_50_in_statementDecl879); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal223_tree = 
                    (Object)adaptor.create(char_literal223)
                    ;
                    adaptor.addChild(root_0, char_literal223_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl881);
                    expressionDecl224=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl224.getTree());

                    char_literal225=(Token)match(input,51,FOLLOW_51_in_statementDecl883); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal225_tree = 
                    (Object)adaptor.create(char_literal225)
                    ;
                    adaptor.addChild(root_0, char_literal225_tree);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl885);
                    statementDecl226=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl226.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:129:4: 'do' ^ statementDecl 'while' '(' expressionDecl ')' ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal227=(Token)match(input,88,FOLLOW_88_in_statementDecl890); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal227_tree = 
                    (Object)adaptor.create(string_literal227)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal227_tree, root_0);
                    }

                    pushFollow(FOLLOW_statementDecl_in_statementDecl893);
                    statementDecl228=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl228.getTree());

                    string_literal229=(Token)match(input,139,FOLLOW_139_in_statementDecl895); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal229_tree = 
                    (Object)adaptor.create(string_literal229)
                    ;
                    adaptor.addChild(root_0, string_literal229_tree);
                    }

                    char_literal230=(Token)match(input,50,FOLLOW_50_in_statementDecl897); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal230_tree = 
                    (Object)adaptor.create(char_literal230)
                    ;
                    adaptor.addChild(root_0, char_literal230_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl899);
                    expressionDecl231=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl231.getTree());

                    char_literal232=(Token)match(input,51,FOLLOW_51_in_statementDecl901); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal232_tree = 
                    (Object)adaptor.create(char_literal232)
                    ;
                    adaptor.addChild(root_0, char_literal232_tree);
                    }

                    char_literal233=(Token)match(input,65,FOLLOW_65_in_statementDecl903); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal233_tree = 
                    (Object)adaptor.create(char_literal233)
                    ;
                    adaptor.addChild(root_0, char_literal233_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\Ariel.g:130:4: 'return' ^ ( expressionDecl )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal234=(Token)match(input,124,FOLLOW_124_in_statementDecl908); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal234_tree = 
                    (Object)adaptor.create(string_literal234)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal234_tree, root_0);
                    }

                    // org\\aries\\Ariel.g:130:14: ( expressionDecl )?
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
                        case 115:
                        case 131:
                        case 145:
                            {
                            alt19=1;
                            }
                            break;
                    }

                    switch (alt19) {
                        case 1 :
                            // org\\aries\\Ariel.g:130:15: expressionDecl
                            {
                            pushFollow(FOLLOW_expressionDecl_in_statementDecl912);
                            expressionDecl235=expressionDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl235.getTree());

                            }
                            break;

                    }


                    char_literal236=(Token)match(input,65,FOLLOW_65_in_statementDecl916); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal236_tree = 
                    (Object)adaptor.create(char_literal236)
                    ;
                    adaptor.addChild(root_0, char_literal236_tree);
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\Ariel.g:131:4: 'throw' ^ expressionDecl ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal237=(Token)match(input,135,FOLLOW_135_in_statementDecl921); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal237_tree = 
                    (Object)adaptor.create(string_literal237)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal237_tree, root_0);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_statementDecl924);
                    expressionDecl238=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl238.getTree());

                    char_literal239=(Token)match(input,65,FOLLOW_65_in_statementDecl926); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal239_tree = 
                    (Object)adaptor.create(char_literal239)
                    ;
                    adaptor.addChild(root_0, char_literal239_tree);
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\Ariel.g:132:4: 'break' ^ ( Identifier )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal240=(Token)match(input,80,FOLLOW_80_in_statementDecl931); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal240_tree = 
                    (Object)adaptor.create(string_literal240)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal240_tree, root_0);
                    }

                    // org\\aries\\Ariel.g:132:13: ( Identifier )?
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
                            // org\\aries\\Ariel.g:132:14: Identifier
                            {
                            Identifier241=(Token)match(input,Identifier,FOLLOW_Identifier_in_statementDecl935); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            Identifier241_tree = 
                            (Object)adaptor.create(Identifier241)
                            ;
                            adaptor.addChild(root_0, Identifier241_tree);
                            }

                            }
                            break;

                    }


                    char_literal242=(Token)match(input,65,FOLLOW_65_in_statementDecl939); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal242_tree = 
                    (Object)adaptor.create(char_literal242)
                    ;
                    adaptor.addChild(root_0, char_literal242_tree);
                    }

                    }
                    break;
                case 9 :
                    // org\\aries\\Ariel.g:133:4: 'continue' ^ ( Identifier )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal243=(Token)match(input,87,FOLLOW_87_in_statementDecl944); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal243_tree = 
                    (Object)adaptor.create(string_literal243)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal243_tree, root_0);
                    }

                    // org\\aries\\Ariel.g:133:16: ( Identifier )?
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
                            // org\\aries\\Ariel.g:133:17: Identifier
                            {
                            Identifier244=(Token)match(input,Identifier,FOLLOW_Identifier_in_statementDecl948); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            Identifier244_tree = 
                            (Object)adaptor.create(Identifier244)
                            ;
                            adaptor.addChild(root_0, Identifier244_tree);
                            }

                            }
                            break;

                    }


                    char_literal245=(Token)match(input,65,FOLLOW_65_in_statementDecl952); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal245_tree = 
                    (Object)adaptor.create(char_literal245)
                    ;
                    adaptor.addChild(root_0, char_literal245_tree);
                    }

                    }
                    break;
                case 10 :
                    // org\\aries\\Ariel.g:134:4: expressionDecl ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_expressionDecl_in_statementDecl957);
                    expressionDecl246=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl246.getTree());

                    char_literal247=(Token)match(input,65,FOLLOW_65_in_statementDecl959); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal247_tree = 
                    (Object)adaptor.create(char_literal247)
                    ;
                    adaptor.addChild(root_0, char_literal247_tree);
                    }

                    }
                    break;
                case 11 :
                    // org\\aries\\Ariel.g:135:4: commandDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_commandDecl_in_statementDecl964);
                    commandDecl248=commandDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, commandDecl248.getTree());

                    }
                    break;
                case 12 :
                    // org\\aries\\Ariel.g:136:4: doneDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_doneDecl_in_statementDecl969);
                    doneDecl249=doneDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doneDecl249.getTree());

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
    // org\\aries\\Ariel.g:139:1: statementBlock : '{' ( statementMember )* '}' ;
    public final ArielParser.statementBlock_return statementBlock() throws RecognitionException {
        ArielParser.statementBlock_return retval = new ArielParser.statementBlock_return();
        retval.start = input.LT(1);

        int statementBlock_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal250=null;
        Token char_literal252=null;
        ArielParser.statementMember_return statementMember251 =null;


        Object char_literal250_tree=null;
        Object char_literal252_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }

            // org\\aries\\Ariel.g:140:2: ( '{' ( statementMember )* '}' )
            // org\\aries\\Ariel.g:140:4: '{' ( statementMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal250=(Token)match(input,140,FOLLOW_140_in_statementBlock982); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal250_tree = 
            (Object)adaptor.create(char_literal250)
            ;
            adaptor.addChild(root_0, char_literal250_tree);
            }

            // org\\aries\\Ariel.g:140:8: ( statementMember )*
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
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 103:
                case 108:
                case 115:
                case 119:
                case 122:
                case 124:
                case 128:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt23=1;
                    }
                    break;

                }

                switch (alt23) {
            	case 1 :
            	    // org\\aries\\Ariel.g:140:9: statementMember
            	    {
            	    pushFollow(FOLLOW_statementMember_in_statementBlock985);
            	    statementMember251=statementMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementMember251.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            char_literal252=(Token)match(input,144,FOLLOW_144_in_statementBlock989); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal252_tree = 
            (Object)adaptor.create(char_literal252)
            ;
            adaptor.addChild(root_0, char_literal252_tree);
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
    // org\\aries\\Ariel.g:143:1: statementMember : statementDecl ;
    public final ArielParser.statementMember_return statementMember() throws RecognitionException {
        ArielParser.statementMember_return retval = new ArielParser.statementMember_return();
        retval.start = input.LT(1);

        int statementMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.statementDecl_return statementDecl253 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }

            // org\\aries\\Ariel.g:144:2: ( statementDecl )
            // org\\aries\\Ariel.g:144:4: statementDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_statementDecl_in_statementMember1001);
            statementDecl253=statementDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl253.getTree());

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
    // org\\aries\\Ariel.g:147:1: variableDeclaration : type Identifier ( '[' ']' )* '=' variableInitializer -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer ) ;
    public final ArielParser.variableDeclaration_return variableDeclaration() throws RecognitionException {
        ArielParser.variableDeclaration_return retval = new ArielParser.variableDeclaration_return();
        retval.start = input.LT(1);

        int variableDeclaration_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier255=null;
        Token char_literal256=null;
        Token char_literal257=null;
        Token char_literal258=null;
        ArielParser.type_return type254 =null;

        ArielParser.variableInitializer_return variableInitializer259 =null;


        Object Identifier255_tree=null;
        Object char_literal256_tree=null;
        Object char_literal257_tree=null;
        Object char_literal258_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_variableInitializer=new RewriteRuleSubtreeStream(adaptor,"rule variableInitializer");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }

            // org\\aries\\Ariel.g:148:2: ( type Identifier ( '[' ']' )* '=' variableInitializer -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer ) )
            // org\\aries\\Ariel.g:148:4: type Identifier ( '[' ']' )* '=' variableInitializer
            {
            pushFollow(FOLLOW_type_in_variableDeclaration1013);
            type254=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type254.getTree());

            Identifier255=(Token)match(input,Identifier,FOLLOW_Identifier_in_variableDeclaration1015); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Identifier.add(Identifier255);


            // org\\aries\\Ariel.g:148:20: ( '[' ']' )*
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
            	    // org\\aries\\Ariel.g:148:21: '[' ']'
            	    {
            	    char_literal256=(Token)match(input,71,FOLLOW_71_in_variableDeclaration1018); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_71.add(char_literal256);


            	    char_literal257=(Token)match(input,72,FOLLOW_72_in_variableDeclaration1020); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_72.add(char_literal257);


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            char_literal258=(Token)match(input,67,FOLLOW_67_in_variableDeclaration1024); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(char_literal258);


            pushFollow(FOLLOW_variableInitializer_in_variableDeclaration1026);
            variableInitializer259=variableInitializer();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_variableInitializer.add(variableInitializer259.getTree());

            // AST REWRITE
            // elements: variableInitializer, 67, Identifier, 71, 72, type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 148:55: -> ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer )
            {
                // org\\aries\\Ariel.g:148:58: ^( EXPR type Identifier ( '[' ']' )* '=' variableInitializer )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(EXPR, "EXPR")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_Identifier.nextNode()
                );

                // org\\aries\\Ariel.g:148:81: ( '[' ']' )*
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
    // org\\aries\\Ariel.g:151:1: variableInitializer : ( arrayInitializer | expressionDecl );
    public final ArielParser.variableInitializer_return variableInitializer() throws RecognitionException {
        ArielParser.variableInitializer_return retval = new ArielParser.variableInitializer_return();
        retval.start = input.LT(1);

        int variableInitializer_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.arrayInitializer_return arrayInitializer260 =null;

        ArielParser.expressionDecl_return expressionDecl261 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }

            // org\\aries\\Ariel.g:152:2: ( arrayInitializer | expressionDecl )
            int alt25=2;
            switch ( input.LA(1) ) {
            case 140:
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
            case 115:
            case 131:
            case 145:
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
                    // org\\aries\\Ariel.g:152:4: arrayInitializer
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_arrayInitializer_in_variableInitializer1059);
                    arrayInitializer260=arrayInitializer();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayInitializer260.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:153:4: expressionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_expressionDecl_in_variableInitializer1064);
                    expressionDecl261=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl261.getTree());

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
    // org\\aries\\Ariel.g:156:1: arrayInitializer : '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}' ;
    public final ArielParser.arrayInitializer_return arrayInitializer() throws RecognitionException {
        ArielParser.arrayInitializer_return retval = new ArielParser.arrayInitializer_return();
        retval.start = input.LT(1);

        int arrayInitializer_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal262=null;
        Token char_literal264=null;
        Token char_literal266=null;
        Token char_literal267=null;
        ArielParser.variableInitializer_return variableInitializer263 =null;

        ArielParser.variableInitializer_return variableInitializer265 =null;


        Object char_literal262_tree=null;
        Object char_literal264_tree=null;
        Object char_literal266_tree=null;
        Object char_literal267_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }

            // org\\aries\\Ariel.g:157:2: ( '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}' )
            // org\\aries\\Ariel.g:157:4: '{' ( variableInitializer ( ',' variableInitializer )* )? ( ',' )? '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal262=(Token)match(input,140,FOLLOW_140_in_arrayInitializer1076); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal262_tree = 
            (Object)adaptor.create(char_literal262)
            ;
            adaptor.addChild(root_0, char_literal262_tree);
            }

            // org\\aries\\Ariel.g:157:8: ( variableInitializer ( ',' variableInitializer )* )?
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
                case 115:
                case 131:
                case 140:
                case 145:
                    {
                    alt27=1;
                    }
                    break;
            }

            switch (alt27) {
                case 1 :
                    // org\\aries\\Ariel.g:157:9: variableInitializer ( ',' variableInitializer )*
                    {
                    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1079);
                    variableInitializer263=variableInitializer();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableInitializer263.getTree());

                    // org\\aries\\Ariel.g:157:29: ( ',' variableInitializer )*
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
                            case 115:
                            case 131:
                            case 140:
                            case 145:
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
                    	    // org\\aries\\Ariel.g:157:30: ',' variableInitializer
                    	    {
                    	    char_literal264=(Token)match(input,57,FOLLOW_57_in_arrayInitializer1082); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal264_tree = 
                    	    (Object)adaptor.create(char_literal264)
                    	    ;
                    	    adaptor.addChild(root_0, char_literal264_tree);
                    	    }

                    	    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1084);
                    	    variableInitializer265=variableInitializer();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableInitializer265.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }


            // org\\aries\\Ariel.g:157:59: ( ',' )?
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
                    // org\\aries\\Ariel.g:157:60: ','
                    {
                    char_literal266=(Token)match(input,57,FOLLOW_57_in_arrayInitializer1092); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal266_tree = 
                    (Object)adaptor.create(char_literal266)
                    ;
                    adaptor.addChild(root_0, char_literal266_tree);
                    }

                    }
                    break;

            }


            char_literal267=(Token)match(input,144,FOLLOW_144_in_arrayInitializer1096); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal267_tree = 
            (Object)adaptor.create(char_literal267)
            ;
            adaptor.addChild(root_0, char_literal267_tree);
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
    // org\\aries\\Ariel.g:160:1: expressionDecl : ( ( 'new' Identifier '(' ')' ) -> ^( EXPR 'new' Identifier '(' ')' ) | conditionalExpression ( assignmentOperator expressionDecl )? -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? ) );
    public final ArielParser.expressionDecl_return expressionDecl() throws RecognitionException {
        ArielParser.expressionDecl_return retval = new ArielParser.expressionDecl_return();
        retval.start = input.LT(1);

        int expressionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal268=null;
        Token Identifier269=null;
        Token char_literal270=null;
        Token char_literal271=null;
        ArielParser.conditionalExpression_return conditionalExpression272 =null;

        ArielParser.assignmentOperator_return assignmentOperator273 =null;

        ArielParser.expressionDecl_return expressionDecl274 =null;


        Object string_literal268_tree=null;
        Object Identifier269_tree=null;
        Object char_literal270_tree=null;
        Object char_literal271_tree=null;
        RewriteRuleTokenStream stream_115=new RewriteRuleTokenStream(adaptor,"token 115");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_assignmentOperator=new RewriteRuleSubtreeStream(adaptor,"rule assignmentOperator");
        RewriteRuleSubtreeStream stream_expressionDecl=new RewriteRuleSubtreeStream(adaptor,"rule expressionDecl");
        RewriteRuleSubtreeStream stream_conditionalExpression=new RewriteRuleSubtreeStream(adaptor,"rule conditionalExpression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }

            // org\\aries\\Ariel.g:161:2: ( ( 'new' Identifier '(' ')' ) -> ^( EXPR 'new' Identifier '(' ')' ) | conditionalExpression ( assignmentOperator expressionDecl )? -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? ) )
            int alt30=2;
            switch ( input.LA(1) ) {
            case 115:
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
            case 131:
            case 145:
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
                    // org\\aries\\Ariel.g:161:4: ( 'new' Identifier '(' ')' )
                    {
                    // org\\aries\\Ariel.g:161:4: ( 'new' Identifier '(' ')' )
                    // org\\aries\\Ariel.g:161:5: 'new' Identifier '(' ')'
                    {
                    string_literal268=(Token)match(input,115,FOLLOW_115_in_expressionDecl1109); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_115.add(string_literal268);


                    Identifier269=(Token)match(input,Identifier,FOLLOW_Identifier_in_expressionDecl1111); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier269);


                    char_literal270=(Token)match(input,50,FOLLOW_50_in_expressionDecl1113); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal270);


                    char_literal271=(Token)match(input,51,FOLLOW_51_in_expressionDecl1115); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal271);


                    }


                    // AST REWRITE
                    // elements: 115, 51, Identifier, 50
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 161:31: -> ^( EXPR 'new' Identifier '(' ')' )
                    {
                        // org\\aries\\Ariel.g:161:34: ^( EXPR 'new' Identifier '(' ')' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(EXPR, "EXPR")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_115.nextNode()
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
                    // org\\aries\\Ariel.g:162:4: conditionalExpression ( assignmentOperator expressionDecl )?
                    {
                    pushFollow(FOLLOW_conditionalExpression_in_expressionDecl1135);
                    conditionalExpression272=conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_conditionalExpression.add(conditionalExpression272.getTree());

                    // org\\aries\\Ariel.g:162:26: ( assignmentOperator expressionDecl )?
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
                        case 142:
                            {
                            alt29=1;
                            }
                            break;
                    }

                    switch (alt29) {
                        case 1 :
                            // org\\aries\\Ariel.g:162:27: assignmentOperator expressionDecl
                            {
                            pushFollow(FOLLOW_assignmentOperator_in_expressionDecl1138);
                            assignmentOperator273=assignmentOperator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_assignmentOperator.add(assignmentOperator273.getTree());

                            pushFollow(FOLLOW_expressionDecl_in_expressionDecl1140);
                            expressionDecl274=expressionDecl();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl274.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: expressionDecl, assignmentOperator, conditionalExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 162:63: -> ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? )
                    {
                        // org\\aries\\Ariel.g:162:66: ^( EXPR conditionalExpression ( assignmentOperator expressionDecl )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(EXPR, "EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_conditionalExpression.nextTree());

                        // org\\aries\\Ariel.g:162:95: ( assignmentOperator expressionDecl )?
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
    // org\\aries\\Ariel.g:165:1: expressionList : expressionDecl ( ',' expressionDecl )* ;
    public final ArielParser.expressionList_return expressionList() throws RecognitionException {
        ArielParser.expressionList_return retval = new ArielParser.expressionList_return();
        retval.start = input.LT(1);

        int expressionList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal276=null;
        ArielParser.expressionDecl_return expressionDecl275 =null;

        ArielParser.expressionDecl_return expressionDecl277 =null;


        Object char_literal276_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }

            // org\\aries\\Ariel.g:166:2: ( expressionDecl ( ',' expressionDecl )* )
            // org\\aries\\Ariel.g:166:4: expressionDecl ( ',' expressionDecl )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_expressionDecl_in_expressionList1169);
            expressionDecl275=expressionDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl275.getTree());

            // org\\aries\\Ariel.g:166:19: ( ',' expressionDecl )*
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
            	    // org\\aries\\Ariel.g:166:20: ',' expressionDecl
            	    {
            	    char_literal276=(Token)match(input,57,FOLLOW_57_in_expressionList1172); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal276_tree = 
            	    (Object)adaptor.create(char_literal276)
            	    ;
            	    adaptor.addChild(root_0, char_literal276_tree);
            	    }

            	    pushFollow(FOLLOW_expressionDecl_in_expressionList1174);
            	    expressionDecl277=expressionDecl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl277.getTree());

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
    // org\\aries\\Ariel.g:169:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '>' '=' | '>' '>' '=' );
    public final ArielParser.assignmentOperator_return assignmentOperator() throws RecognitionException {
        ArielParser.assignmentOperator_return retval = new ArielParser.assignmentOperator_return();
        retval.start = input.LT(1);

        int assignmentOperator_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal278=null;
        Token string_literal279=null;
        Token string_literal280=null;
        Token string_literal281=null;
        Token string_literal282=null;
        Token string_literal283=null;
        Token string_literal284=null;
        Token string_literal285=null;
        Token string_literal286=null;
        Token char_literal287=null;
        Token char_literal288=null;
        Token char_literal289=null;
        Token char_literal290=null;
        Token char_literal291=null;
        Token char_literal292=null;
        Token char_literal293=null;
        Token char_literal294=null;
        Token char_literal295=null;
        Token char_literal296=null;

        Object char_literal278_tree=null;
        Object string_literal279_tree=null;
        Object string_literal280_tree=null;
        Object string_literal281_tree=null;
        Object string_literal282_tree=null;
        Object string_literal283_tree=null;
        Object string_literal284_tree=null;
        Object string_literal285_tree=null;
        Object string_literal286_tree=null;
        Object char_literal287_tree=null;
        Object char_literal288_tree=null;
        Object char_literal289_tree=null;
        Object char_literal290_tree=null;
        Object char_literal291_tree=null;
        Object char_literal292_tree=null;
        Object char_literal293_tree=null;
        Object char_literal294_tree=null;
        Object char_literal295_tree=null;
        Object char_literal296_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }

            // org\\aries\\Ariel.g:170:2: ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '>' '=' | '>' '>' '=' )
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
            case 142:
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
                    // org\\aries\\Ariel.g:170:4: '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal278=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1188); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal278_tree = 
                    (Object)adaptor.create(char_literal278)
                    ;
                    adaptor.addChild(root_0, char_literal278_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:171:4: '+='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal279=(Token)match(input,56,FOLLOW_56_in_assignmentOperator1193); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal279_tree = 
                    (Object)adaptor.create(string_literal279)
                    ;
                    adaptor.addChild(root_0, string_literal279_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:172:4: '-='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal280=(Token)match(input,60,FOLLOW_60_in_assignmentOperator1198); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal280_tree = 
                    (Object)adaptor.create(string_literal280)
                    ;
                    adaptor.addChild(root_0, string_literal280_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:173:4: '*='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal281=(Token)match(input,53,FOLLOW_53_in_assignmentOperator1203); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal281_tree = 
                    (Object)adaptor.create(string_literal281)
                    ;
                    adaptor.addChild(root_0, string_literal281_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:174:4: '/='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal282=(Token)match(input,63,FOLLOW_63_in_assignmentOperator1208); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal282_tree = 
                    (Object)adaptor.create(string_literal282)
                    ;
                    adaptor.addChild(root_0, string_literal282_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\Ariel.g:175:4: '&='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal283=(Token)match(input,49,FOLLOW_49_in_assignmentOperator1213); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal283_tree = 
                    (Object)adaptor.create(string_literal283)
                    ;
                    adaptor.addChild(root_0, string_literal283_tree);
                    }

                    }
                    break;
                case 7 :
                    // org\\aries\\Ariel.g:176:4: '|='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal284=(Token)match(input,142,FOLLOW_142_in_assignmentOperator1218); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal284_tree = 
                    (Object)adaptor.create(string_literal284)
                    ;
                    adaptor.addChild(root_0, string_literal284_tree);
                    }

                    }
                    break;
                case 8 :
                    // org\\aries\\Ariel.g:177:4: '^='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal285=(Token)match(input,74,FOLLOW_74_in_assignmentOperator1223); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal285_tree = 
                    (Object)adaptor.create(string_literal285)
                    ;
                    adaptor.addChild(root_0, string_literal285_tree);
                    }

                    }
                    break;
                case 9 :
                    // org\\aries\\Ariel.g:178:4: '%='
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal286=(Token)match(input,46,FOLLOW_46_in_assignmentOperator1228); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal286_tree = 
                    (Object)adaptor.create(string_literal286)
                    ;
                    adaptor.addChild(root_0, string_literal286_tree);
                    }

                    }
                    break;
                case 10 :
                    // org\\aries\\Ariel.g:179:4: '<' '<' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal287=(Token)match(input,66,FOLLOW_66_in_assignmentOperator1233); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal287_tree = 
                    (Object)adaptor.create(char_literal287)
                    ;
                    adaptor.addChild(root_0, char_literal287_tree);
                    }

                    char_literal288=(Token)match(input,66,FOLLOW_66_in_assignmentOperator1235); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal288_tree = 
                    (Object)adaptor.create(char_literal288)
                    ;
                    adaptor.addChild(root_0, char_literal288_tree);
                    }

                    char_literal289=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1237); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal289_tree = 
                    (Object)adaptor.create(char_literal289)
                    ;
                    adaptor.addChild(root_0, char_literal289_tree);
                    }

                    }
                    break;
                case 11 :
                    // org\\aries\\Ariel.g:180:4: '>' '>' '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal290=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1242); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal290_tree = 
                    (Object)adaptor.create(char_literal290)
                    ;
                    adaptor.addChild(root_0, char_literal290_tree);
                    }

                    char_literal291=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1244); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal291_tree = 
                    (Object)adaptor.create(char_literal291)
                    ;
                    adaptor.addChild(root_0, char_literal291_tree);
                    }

                    char_literal292=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1246); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal292_tree = 
                    (Object)adaptor.create(char_literal292)
                    ;
                    adaptor.addChild(root_0, char_literal292_tree);
                    }

                    char_literal293=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1248); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal293_tree = 
                    (Object)adaptor.create(char_literal293)
                    ;
                    adaptor.addChild(root_0, char_literal293_tree);
                    }

                    }
                    break;
                case 12 :
                    // org\\aries\\Ariel.g:181:4: '>' '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal294=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1253); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal294_tree = 
                    (Object)adaptor.create(char_literal294)
                    ;
                    adaptor.addChild(root_0, char_literal294_tree);
                    }

                    char_literal295=(Token)match(input,69,FOLLOW_69_in_assignmentOperator1255); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal295_tree = 
                    (Object)adaptor.create(char_literal295)
                    ;
                    adaptor.addChild(root_0, char_literal295_tree);
                    }

                    char_literal296=(Token)match(input,67,FOLLOW_67_in_assignmentOperator1257); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal296_tree = 
                    (Object)adaptor.create(char_literal296)
                    ;
                    adaptor.addChild(root_0, char_literal296_tree);
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
    // org\\aries\\Ariel.g:184:1: conditionalExpression : conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )? ;
    public final ArielParser.conditionalExpression_return conditionalExpression() throws RecognitionException {
        ArielParser.conditionalExpression_return retval = new ArielParser.conditionalExpression_return();
        retval.start = input.LT(1);

        int conditionalExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal298=null;
        Token char_literal300=null;
        ArielParser.conditionalOrExpression_return conditionalOrExpression297 =null;

        ArielParser.expressionDecl_return expressionDecl299 =null;

        ArielParser.conditionalExpression_return conditionalExpression301 =null;


        Object char_literal298_tree=null;
        Object char_literal300_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }

            // org\\aries\\Ariel.g:185:2: ( conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )? )
            // org\\aries\\Ariel.g:185:4: conditionalOrExpression ( '?' expressionDecl ':' conditionalExpression )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalExpression1268);
            conditionalOrExpression297=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalOrExpression297.getTree());

            // org\\aries\\Ariel.g:185:28: ( '?' expressionDecl ':' conditionalExpression )?
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
                    // org\\aries\\Ariel.g:185:29: '?' expressionDecl ':' conditionalExpression
                    {
                    char_literal298=(Token)match(input,70,FOLLOW_70_in_conditionalExpression1271); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal298_tree = 
                    (Object)adaptor.create(char_literal298)
                    ;
                    adaptor.addChild(root_0, char_literal298_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_conditionalExpression1273);
                    expressionDecl299=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl299.getTree());

                    char_literal300=(Token)match(input,64,FOLLOW_64_in_conditionalExpression1275); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal300_tree = 
                    (Object)adaptor.create(char_literal300)
                    ;
                    adaptor.addChild(root_0, char_literal300_tree);
                    }

                    pushFollow(FOLLOW_conditionalExpression_in_conditionalExpression1277);
                    conditionalExpression301=conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalExpression301.getTree());

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
    // org\\aries\\Ariel.g:188:1: conditionalOrExpression : conditionalAndExpression ( '||' conditionalAndExpression )* ;
    public final ArielParser.conditionalOrExpression_return conditionalOrExpression() throws RecognitionException {
        ArielParser.conditionalOrExpression_return retval = new ArielParser.conditionalOrExpression_return();
        retval.start = input.LT(1);

        int conditionalOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal303=null;
        ArielParser.conditionalAndExpression_return conditionalAndExpression302 =null;

        ArielParser.conditionalAndExpression_return conditionalAndExpression304 =null;


        Object string_literal303_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }

            // org\\aries\\Ariel.g:189:2: ( conditionalAndExpression ( '||' conditionalAndExpression )* )
            // org\\aries\\Ariel.g:189:4: conditionalAndExpression ( '||' conditionalAndExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1291);
            conditionalAndExpression302=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression302.getTree());

            // org\\aries\\Ariel.g:189:29: ( '||' conditionalAndExpression )*
            loop34:
            do {
                int alt34=2;
                switch ( input.LA(1) ) {
                case 143:
                    {
                    alt34=1;
                    }
                    break;

                }

                switch (alt34) {
            	case 1 :
            	    // org\\aries\\Ariel.g:189:30: '||' conditionalAndExpression
            	    {
            	    string_literal303=(Token)match(input,143,FOLLOW_143_in_conditionalOrExpression1294); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal303_tree = 
            	    (Object)adaptor.create(string_literal303)
            	    ;
            	    adaptor.addChild(root_0, string_literal303_tree);
            	    }

            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1296);
            	    conditionalAndExpression304=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression304.getTree());

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
    // org\\aries\\Ariel.g:192:1: conditionalAndExpression : inclusiveOrExpression ( '&&' inclusiveOrExpression )* ;
    public final ArielParser.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        ArielParser.conditionalAndExpression_return retval = new ArielParser.conditionalAndExpression_return();
        retval.start = input.LT(1);

        int conditionalAndExpression_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal306=null;
        ArielParser.inclusiveOrExpression_return inclusiveOrExpression305 =null;

        ArielParser.inclusiveOrExpression_return inclusiveOrExpression307 =null;


        Object string_literal306_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }

            // org\\aries\\Ariel.g:193:2: ( inclusiveOrExpression ( '&&' inclusiveOrExpression )* )
            // org\\aries\\Ariel.g:193:4: inclusiveOrExpression ( '&&' inclusiveOrExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1310);
            inclusiveOrExpression305=inclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, inclusiveOrExpression305.getTree());

            // org\\aries\\Ariel.g:193:26: ( '&&' inclusiveOrExpression )*
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
            	    // org\\aries\\Ariel.g:193:27: '&&' inclusiveOrExpression
            	    {
            	    string_literal306=(Token)match(input,47,FOLLOW_47_in_conditionalAndExpression1313); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal306_tree = 
            	    (Object)adaptor.create(string_literal306)
            	    ;
            	    adaptor.addChild(root_0, string_literal306_tree);
            	    }

            	    pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1315);
            	    inclusiveOrExpression307=inclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, inclusiveOrExpression307.getTree());

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
    // org\\aries\\Ariel.g:196:1: inclusiveOrExpression : exclusiveOrExpression ( '|' exclusiveOrExpression )* ;
    public final ArielParser.inclusiveOrExpression_return inclusiveOrExpression() throws RecognitionException {
        ArielParser.inclusiveOrExpression_return retval = new ArielParser.inclusiveOrExpression_return();
        retval.start = input.LT(1);

        int inclusiveOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal309=null;
        ArielParser.exclusiveOrExpression_return exclusiveOrExpression308 =null;

        ArielParser.exclusiveOrExpression_return exclusiveOrExpression310 =null;


        Object char_literal309_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }

            // org\\aries\\Ariel.g:197:2: ( exclusiveOrExpression ( '|' exclusiveOrExpression )* )
            // org\\aries\\Ariel.g:197:4: exclusiveOrExpression ( '|' exclusiveOrExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1329);
            exclusiveOrExpression308=exclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exclusiveOrExpression308.getTree());

            // org\\aries\\Ariel.g:197:26: ( '|' exclusiveOrExpression )*
            loop36:
            do {
                int alt36=2;
                switch ( input.LA(1) ) {
                case 141:
                    {
                    alt36=1;
                    }
                    break;

                }

                switch (alt36) {
            	case 1 :
            	    // org\\aries\\Ariel.g:197:27: '|' exclusiveOrExpression
            	    {
            	    char_literal309=(Token)match(input,141,FOLLOW_141_in_inclusiveOrExpression1332); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal309_tree = 
            	    (Object)adaptor.create(char_literal309)
            	    ;
            	    adaptor.addChild(root_0, char_literal309_tree);
            	    }

            	    pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1334);
            	    exclusiveOrExpression310=exclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exclusiveOrExpression310.getTree());

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
    // org\\aries\\Ariel.g:200:1: exclusiveOrExpression : andExpression ( '^' andExpression )* ;
    public final ArielParser.exclusiveOrExpression_return exclusiveOrExpression() throws RecognitionException {
        ArielParser.exclusiveOrExpression_return retval = new ArielParser.exclusiveOrExpression_return();
        retval.start = input.LT(1);

        int exclusiveOrExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal312=null;
        ArielParser.andExpression_return andExpression311 =null;

        ArielParser.andExpression_return andExpression313 =null;


        Object char_literal312_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }

            // org\\aries\\Ariel.g:201:2: ( andExpression ( '^' andExpression )* )
            // org\\aries\\Ariel.g:201:4: andExpression ( '^' andExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression1348);
            andExpression311=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpression311.getTree());

            // org\\aries\\Ariel.g:201:18: ( '^' andExpression )*
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
            	    // org\\aries\\Ariel.g:201:19: '^' andExpression
            	    {
            	    char_literal312=(Token)match(input,73,FOLLOW_73_in_exclusiveOrExpression1351); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal312_tree = 
            	    (Object)adaptor.create(char_literal312)
            	    ;
            	    adaptor.addChild(root_0, char_literal312_tree);
            	    }

            	    pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression1353);
            	    andExpression313=andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, andExpression313.getTree());

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
    // org\\aries\\Ariel.g:204:1: andExpression : equalityExpression ( '&' equalityExpression )* ;
    public final ArielParser.andExpression_return andExpression() throws RecognitionException {
        ArielParser.andExpression_return retval = new ArielParser.andExpression_return();
        retval.start = input.LT(1);

        int andExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal315=null;
        ArielParser.equalityExpression_return equalityExpression314 =null;

        ArielParser.equalityExpression_return equalityExpression316 =null;


        Object char_literal315_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }

            // org\\aries\\Ariel.g:205:2: ( equalityExpression ( '&' equalityExpression )* )
            // org\\aries\\Ariel.g:205:4: equalityExpression ( '&' equalityExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_equalityExpression_in_andExpression1367);
            equalityExpression314=equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression314.getTree());

            // org\\aries\\Ariel.g:205:23: ( '&' equalityExpression )*
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
            	    // org\\aries\\Ariel.g:205:24: '&' equalityExpression
            	    {
            	    char_literal315=(Token)match(input,48,FOLLOW_48_in_andExpression1370); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal315_tree = 
            	    (Object)adaptor.create(char_literal315)
            	    ;
            	    adaptor.addChild(root_0, char_literal315_tree);
            	    }

            	    pushFollow(FOLLOW_equalityExpression_in_andExpression1372);
            	    equalityExpression316=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression316.getTree());

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
    // org\\aries\\Ariel.g:208:1: equalityExpression : relationalExpression ( ( '==' | '!=' ) relationalExpression )* ;
    public final ArielParser.equalityExpression_return equalityExpression() throws RecognitionException {
        ArielParser.equalityExpression_return retval = new ArielParser.equalityExpression_return();
        retval.start = input.LT(1);

        int equalityExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set318=null;
        ArielParser.relationalExpression_return relationalExpression317 =null;

        ArielParser.relationalExpression_return relationalExpression319 =null;


        Object set318_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }

            // org\\aries\\Ariel.g:209:2: ( relationalExpression ( ( '==' | '!=' ) relationalExpression )* )
            // org\\aries\\Ariel.g:209:4: relationalExpression ( ( '==' | '!=' ) relationalExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relationalExpression_in_equalityExpression1386);
            relationalExpression317=relationalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression317.getTree());

            // org\\aries\\Ariel.g:209:25: ( ( '==' | '!=' ) relationalExpression )*
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
            	    // org\\aries\\Ariel.g:209:27: ( '==' | '!=' ) relationalExpression
            	    {
            	    set318=(Token)input.LT(1);

            	    if ( input.LA(1)==44||input.LA(1)==68 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set318)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression1398);
            	    relationalExpression319=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression319.getTree());

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
    // org\\aries\\Ariel.g:212:1: relationalExpression : shiftExpression ( relationalOp shiftExpression )* ;
    public final ArielParser.relationalExpression_return relationalExpression() throws RecognitionException {
        ArielParser.relationalExpression_return retval = new ArielParser.relationalExpression_return();
        retval.start = input.LT(1);

        int relationalExpression_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.shiftExpression_return shiftExpression320 =null;

        ArielParser.relationalOp_return relationalOp321 =null;

        ArielParser.shiftExpression_return shiftExpression322 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }

            // org\\aries\\Ariel.g:213:2: ( shiftExpression ( relationalOp shiftExpression )* )
            // org\\aries\\Ariel.g:213:4: shiftExpression ( relationalOp shiftExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_shiftExpression_in_relationalExpression1412);
            shiftExpression320=shiftExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftExpression320.getTree());

            // org\\aries\\Ariel.g:213:20: ( relationalOp shiftExpression )*
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
                    case 131:
                    case 145:
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
                    case 131:
                    case 145:
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
            	    // org\\aries\\Ariel.g:213:21: relationalOp shiftExpression
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpression1415);
            	    relationalOp321=relationalOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalOp321.getTree());

            	    pushFollow(FOLLOW_shiftExpression_in_relationalExpression1417);
            	    shiftExpression322=shiftExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftExpression322.getTree());

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
    // org\\aries\\Ariel.g:216:1: relationalOp : ( '<' '=' | '>' '=' | '<' | '>' );
    public final ArielParser.relationalOp_return relationalOp() throws RecognitionException {
        ArielParser.relationalOp_return retval = new ArielParser.relationalOp_return();
        retval.start = input.LT(1);

        int relationalOp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal323=null;
        Token char_literal324=null;
        Token char_literal325=null;
        Token char_literal326=null;
        Token char_literal327=null;
        Token char_literal328=null;

        Object char_literal323_tree=null;
        Object char_literal324_tree=null;
        Object char_literal325_tree=null;
        Object char_literal326_tree=null;
        Object char_literal327_tree=null;
        Object char_literal328_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }

            // org\\aries\\Ariel.g:217:2: ( '<' '=' | '>' '=' | '<' | '>' )
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
                case 131:
                case 145:
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
                case 131:
                case 145:
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
                    // org\\aries\\Ariel.g:217:4: '<' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal323=(Token)match(input,66,FOLLOW_66_in_relationalOp1431); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal323_tree = 
                    (Object)adaptor.create(char_literal323)
                    ;
                    adaptor.addChild(root_0, char_literal323_tree);
                    }

                    char_literal324=(Token)match(input,67,FOLLOW_67_in_relationalOp1433); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal324_tree = 
                    (Object)adaptor.create(char_literal324)
                    ;
                    adaptor.addChild(root_0, char_literal324_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:218:4: '>' '='
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal325=(Token)match(input,69,FOLLOW_69_in_relationalOp1438); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal325_tree = 
                    (Object)adaptor.create(char_literal325)
                    ;
                    adaptor.addChild(root_0, char_literal325_tree);
                    }

                    char_literal326=(Token)match(input,67,FOLLOW_67_in_relationalOp1440); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal326_tree = 
                    (Object)adaptor.create(char_literal326)
                    ;
                    adaptor.addChild(root_0, char_literal326_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:219:4: '<'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal327=(Token)match(input,66,FOLLOW_66_in_relationalOp1445); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal327_tree = 
                    (Object)adaptor.create(char_literal327)
                    ;
                    adaptor.addChild(root_0, char_literal327_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:220:4: '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal328=(Token)match(input,69,FOLLOW_69_in_relationalOp1450); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal328_tree = 
                    (Object)adaptor.create(char_literal328)
                    ;
                    adaptor.addChild(root_0, char_literal328_tree);
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
    // org\\aries\\Ariel.g:223:1: shiftExpression : additiveExpression ( shiftOp additiveExpression )* ;
    public final ArielParser.shiftExpression_return shiftExpression() throws RecognitionException {
        ArielParser.shiftExpression_return retval = new ArielParser.shiftExpression_return();
        retval.start = input.LT(1);

        int shiftExpression_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.additiveExpression_return additiveExpression329 =null;

        ArielParser.shiftOp_return shiftOp330 =null;

        ArielParser.additiveExpression_return additiveExpression331 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }

            // org\\aries\\Ariel.g:224:2: ( additiveExpression ( shiftOp additiveExpression )* )
            // org\\aries\\Ariel.g:224:4: additiveExpression ( shiftOp additiveExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_additiveExpression_in_shiftExpression1462);
            additiveExpression329=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression329.getTree());

            // org\\aries\\Ariel.g:224:23: ( shiftOp additiveExpression )*
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
                        case 131:
                        case 145:
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
                            case 131:
                            case 145:
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
                        case 131:
                        case 145:
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
            	    // org\\aries\\Ariel.g:224:24: shiftOp additiveExpression
            	    {
            	    pushFollow(FOLLOW_shiftOp_in_shiftExpression1465);
            	    shiftOp330=shiftOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, shiftOp330.getTree());

            	    pushFollow(FOLLOW_additiveExpression_in_shiftExpression1467);
            	    additiveExpression331=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression331.getTree());

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
    // org\\aries\\Ariel.g:227:1: shiftOp : ( '<' '<' | '>' '>' '>' | '>' '>' );
    public final ArielParser.shiftOp_return shiftOp() throws RecognitionException {
        ArielParser.shiftOp_return retval = new ArielParser.shiftOp_return();
        retval.start = input.LT(1);

        int shiftOp_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal332=null;
        Token char_literal333=null;
        Token char_literal334=null;
        Token char_literal335=null;
        Token char_literal336=null;
        Token char_literal337=null;
        Token char_literal338=null;

        Object char_literal332_tree=null;
        Object char_literal333_tree=null;
        Object char_literal334_tree=null;
        Object char_literal335_tree=null;
        Object char_literal336_tree=null;
        Object char_literal337_tree=null;
        Object char_literal338_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }

            // org\\aries\\Ariel.g:228:2: ( '<' '<' | '>' '>' '>' | '>' '>' )
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
                    case 131:
                    case 145:
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
                    // org\\aries\\Ariel.g:228:4: '<' '<'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal332=(Token)match(input,66,FOLLOW_66_in_shiftOp1481); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal332_tree = 
                    (Object)adaptor.create(char_literal332)
                    ;
                    adaptor.addChild(root_0, char_literal332_tree);
                    }

                    char_literal333=(Token)match(input,66,FOLLOW_66_in_shiftOp1483); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal333_tree = 
                    (Object)adaptor.create(char_literal333)
                    ;
                    adaptor.addChild(root_0, char_literal333_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:229:4: '>' '>' '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal334=(Token)match(input,69,FOLLOW_69_in_shiftOp1488); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal334_tree = 
                    (Object)adaptor.create(char_literal334)
                    ;
                    adaptor.addChild(root_0, char_literal334_tree);
                    }

                    char_literal335=(Token)match(input,69,FOLLOW_69_in_shiftOp1490); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal335_tree = 
                    (Object)adaptor.create(char_literal335)
                    ;
                    adaptor.addChild(root_0, char_literal335_tree);
                    }

                    char_literal336=(Token)match(input,69,FOLLOW_69_in_shiftOp1492); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal336_tree = 
                    (Object)adaptor.create(char_literal336)
                    ;
                    adaptor.addChild(root_0, char_literal336_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:230:4: '>' '>'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal337=(Token)match(input,69,FOLLOW_69_in_shiftOp1497); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal337_tree = 
                    (Object)adaptor.create(char_literal337)
                    ;
                    adaptor.addChild(root_0, char_literal337_tree);
                    }

                    char_literal338=(Token)match(input,69,FOLLOW_69_in_shiftOp1499); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal338_tree = 
                    (Object)adaptor.create(char_literal338)
                    ;
                    adaptor.addChild(root_0, char_literal338_tree);
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
    // org\\aries\\Ariel.g:233:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final ArielParser.additiveExpression_return additiveExpression() throws RecognitionException {
        ArielParser.additiveExpression_return retval = new ArielParser.additiveExpression_return();
        retval.start = input.LT(1);

        int additiveExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set340=null;
        ArielParser.multiplicativeExpression_return multiplicativeExpression339 =null;

        ArielParser.multiplicativeExpression_return multiplicativeExpression341 =null;


        Object set340_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }

            // org\\aries\\Ariel.g:234:2: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // org\\aries\\Ariel.g:234:4: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1511);
            multiplicativeExpression339=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression339.getTree());

            // org\\aries\\Ariel.g:234:29: ( ( '+' | '-' ) multiplicativeExpression )*
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
            	    // org\\aries\\Ariel.g:234:31: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    set340=(Token)input.LT(1);

            	    if ( input.LA(1)==54||input.LA(1)==58 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set340)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1523);
            	    multiplicativeExpression341=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression341.getTree());

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
    // org\\aries\\Ariel.g:237:1: multiplicativeExpression : unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* ;
    public final ArielParser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        ArielParser.multiplicativeExpression_return retval = new ArielParser.multiplicativeExpression_return();
        retval.start = input.LT(1);

        int multiplicativeExpression_StartIndex = input.index();

        Object root_0 = null;

        Token set343=null;
        ArielParser.unaryExpression_return unaryExpression342 =null;

        ArielParser.unaryExpression_return unaryExpression344 =null;


        Object set343_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }

            // org\\aries\\Ariel.g:238:2: ( unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* )
            // org\\aries\\Ariel.g:238:4: unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1537);
            unaryExpression342=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression342.getTree());

            // org\\aries\\Ariel.g:238:20: ( ( '*' | '/' | '%' ) unaryExpression )*
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
            	    // org\\aries\\Ariel.g:238:22: ( '*' | '/' | '%' ) unaryExpression
            	    {
            	    set343=(Token)input.LT(1);

            	    if ( input.LA(1)==45||input.LA(1)==52||input.LA(1)==62 ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
            	        (Object)adaptor.create(set343)
            	        );
            	        state.errorRecovery=false;
            	        state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1553);
            	    unaryExpression344=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression344.getTree());

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
    // org\\aries\\Ariel.g:245:1: unaryExpression : ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus );
    public final ArielParser.unaryExpression_return unaryExpression() throws RecognitionException {
        ArielParser.unaryExpression_return retval = new ArielParser.unaryExpression_return();
        retval.start = input.LT(1);

        int unaryExpression_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal345=null;
        Token char_literal347=null;
        Token string_literal349=null;
        Token string_literal351=null;
        ArielParser.unaryExpression_return unaryExpression346 =null;

        ArielParser.unaryExpression_return unaryExpression348 =null;

        ArielParser.unaryExpression_return unaryExpression350 =null;

        ArielParser.unaryExpression_return unaryExpression352 =null;

        ArielParser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus353 =null;


        Object char_literal345_tree=null;
        Object char_literal347_tree=null;
        Object string_literal349_tree=null;
        Object string_literal351_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }

            // org\\aries\\Ariel.g:246:2: ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus )
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
            case 131:
            case 145:
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
                    // org\\aries\\Ariel.g:246:4: '+' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal345=(Token)match(input,54,FOLLOW_54_in_unaryExpression1569); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal345_tree = 
                    (Object)adaptor.create(char_literal345)
                    ;
                    adaptor.addChild(root_0, char_literal345_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1571);
                    unaryExpression346=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression346.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:247:4: '-' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal347=(Token)match(input,58,FOLLOW_58_in_unaryExpression1576); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal347_tree = 
                    (Object)adaptor.create(char_literal347)
                    ;
                    adaptor.addChild(root_0, char_literal347_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1578);
                    unaryExpression348=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression348.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:248:4: '++' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal349=(Token)match(input,55,FOLLOW_55_in_unaryExpression1583); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal349_tree = 
                    (Object)adaptor.create(string_literal349)
                    ;
                    adaptor.addChild(root_0, string_literal349_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1585);
                    unaryExpression350=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression350.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:249:4: '--' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal351=(Token)match(input,59,FOLLOW_59_in_unaryExpression1590); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal351_tree = 
                    (Object)adaptor.create(string_literal351)
                    ;
                    adaptor.addChild(root_0, string_literal351_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1592);
                    unaryExpression352=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression352.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:250:4: unaryExpressionNotPlusMinus
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression1597);
                    unaryExpressionNotPlusMinus353=unaryExpressionNotPlusMinus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpressionNotPlusMinus353.getTree());

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
    // org\\aries\\Ariel.g:253:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | primary ( selector )* ( '++' | '--' )? );
    public final ArielParser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus() throws RecognitionException {
        ArielParser.unaryExpressionNotPlusMinus_return retval = new ArielParser.unaryExpressionNotPlusMinus_return();
        retval.start = input.LT(1);

        int unaryExpressionNotPlusMinus_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal354=null;
        Token char_literal356=null;
        Token set360=null;
        ArielParser.unaryExpression_return unaryExpression355 =null;

        ArielParser.unaryExpression_return unaryExpression357 =null;

        ArielParser.primary_return primary358 =null;

        ArielParser.selector_return selector359 =null;


        Object char_literal354_tree=null;
        Object char_literal356_tree=null;
        Object set360_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }

            // org\\aries\\Ariel.g:254:2: ( '~' unaryExpression | '!' unaryExpression | primary ( selector )* ( '++' | '--' )? )
            int alt49=3;
            switch ( input.LA(1) ) {
            case 145:
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
            case 131:
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
                    // org\\aries\\Ariel.g:254:4: '~' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal354=(Token)match(input,145,FOLLOW_145_in_unaryExpressionNotPlusMinus1609); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal354_tree = 
                    (Object)adaptor.create(char_literal354)
                    ;
                    adaptor.addChild(root_0, char_literal354_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1611);
                    unaryExpression355=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression355.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:255:4: '!' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal356=(Token)match(input,43,FOLLOW_43_in_unaryExpressionNotPlusMinus1616); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal356_tree = 
                    (Object)adaptor.create(char_literal356)
                    ;
                    adaptor.addChild(root_0, char_literal356_tree);
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1618);
                    unaryExpression357=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression357.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:256:4: primary ( selector )* ( '++' | '--' )?
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_primary_in_unaryExpressionNotPlusMinus1623);
                    primary358=primary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary358.getTree());

                    // org\\aries\\Ariel.g:256:12: ( selector )*
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
                    	    // org\\aries\\Ariel.g:256:13: selector
                    	    {
                    	    pushFollow(FOLLOW_selector_in_unaryExpressionNotPlusMinus1626);
                    	    selector359=selector();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, selector359.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);


                    // org\\aries\\Ariel.g:256:24: ( '++' | '--' )?
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
                            // org\\aries\\Ariel.g:
                            {
                            set360=(Token)input.LT(1);

                            if ( input.LA(1)==55||input.LA(1)==59 ) {
                                input.consume();
                                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                                (Object)adaptor.create(set360)
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
    // org\\aries\\Ariel.g:259:1: primary : ( '(' expressionDecl ')' -> ^( PRIMARY '(' expressionDecl ')' ) | ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' ) -> ^( PRIMARY qualifiedName '(' ')' ) | ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' ) -> ^( PRIMARY qualifiedName '(' expressionList ')' ) | ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' ) -> ^( PRIMARY qualifiedName '[' expressionDecl ']' ) | ( type Identifier '=' )=> type Identifier -> ^( PRIMARY type Identifier ) | ( qualifiedName '.' )=> qualifiedName -> ^( PRIMARY qualifiedName ) | qualifiedName -> ^( PRIMARY qualifiedName ) | literal -> ^( PRIMARY literal ) );
    public final ArielParser.primary_return primary() throws RecognitionException {
        ArielParser.primary_return retval = new ArielParser.primary_return();
        retval.start = input.LT(1);

        int primary_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal361=null;
        Token char_literal363=null;
        Token char_literal365=null;
        Token char_literal366=null;
        Token char_literal368=null;
        Token char_literal370=null;
        Token char_literal372=null;
        Token char_literal374=null;
        Token Identifier376=null;
        ArielParser.expressionDecl_return expressionDecl362 =null;

        ArielParser.qualifiedName_return qualifiedName364 =null;

        ArielParser.qualifiedName_return qualifiedName367 =null;

        ArielParser.expressionList_return expressionList369 =null;

        ArielParser.qualifiedName_return qualifiedName371 =null;

        ArielParser.expressionDecl_return expressionDecl373 =null;

        ArielParser.type_return type375 =null;

        ArielParser.qualifiedName_return qualifiedName377 =null;

        ArielParser.qualifiedName_return qualifiedName378 =null;

        ArielParser.literal_return literal379 =null;


        Object char_literal361_tree=null;
        Object char_literal363_tree=null;
        Object char_literal365_tree=null;
        Object char_literal366_tree=null;
        Object char_literal368_tree=null;
        Object char_literal370_tree=null;
        Object char_literal372_tree=null;
        Object char_literal374_tree=null;
        Object Identifier376_tree=null;
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

            // org\\aries\\Ariel.g:260:2: ( '(' expressionDecl ')' -> ^( PRIMARY '(' expressionDecl ')' ) | ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' ) -> ^( PRIMARY qualifiedName '(' ')' ) | ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' ) -> ^( PRIMARY qualifiedName '(' expressionList ')' ) | ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' ) -> ^( PRIMARY qualifiedName '[' expressionDecl ']' ) | ( type Identifier '=' )=> type Identifier -> ^( PRIMARY type Identifier ) | ( qualifiedName '.' )=> qualifiedName -> ^( PRIMARY qualifiedName ) | qualifiedName -> ^( PRIMARY qualifiedName ) | literal -> ^( PRIMARY literal ) )
            int alt50=8;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==50) ) {
                alt50=1;
            }
            else if ( (LA50_0==Identifier) ) {
                int LA50_2 = input.LA(2);

                if ( (synpred2_Ariel()) ) {
                    alt50=2;
                }
                else if ( (synpred3_Ariel()) ) {
                    alt50=3;
                }
                else if ( (synpred4_Ariel()) ) {
                    alt50=4;
                }
                else if ( (synpred5_Ariel()) ) {
                    alt50=5;
                }
                else if ( (synpred6_Ariel()) ) {
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

                if ( (synpred2_Ariel()) ) {
                    alt50=2;
                }
                else if ( (synpred3_Ariel()) ) {
                    alt50=3;
                }
                else if ( (synpred4_Ariel()) ) {
                    alt50=4;
                }
                else if ( (synpred5_Ariel()) ) {
                    alt50=5;
                }
                else if ( (synpred6_Ariel()) ) {
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
            else if ( (LA50_0==78||LA50_0==81||LA50_0==84||LA50_0==91||LA50_0==95||LA50_0==102||LA50_0==108||LA50_0==131) && (synpred5_Ariel())) {
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
                    // org\\aries\\Ariel.g:260:4: '(' expressionDecl ')'
                    {
                    char_literal361=(Token)match(input,50,FOLLOW_50_in_primary1649); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal361);


                    pushFollow(FOLLOW_expressionDecl_in_primary1651);
                    expressionDecl362=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl362.getTree());

                    char_literal363=(Token)match(input,51,FOLLOW_51_in_primary1653); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal363);


                    // AST REWRITE
                    // elements: 50, expressionDecl, 51
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 260:27: -> ^( PRIMARY '(' expressionDecl ')' )
                    {
                        // org\\aries\\Ariel.g:260:30: ^( PRIMARY '(' expressionDecl ')' )
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
                    // org\\aries\\Ariel.g:261:4: ( qualifiedName '(' ')' )=> ( qualifiedName '(' ')' )
                    {
                    // org\\aries\\Ariel.g:261:31: ( qualifiedName '(' ')' )
                    // org\\aries\\Ariel.g:261:32: qualifiedName '(' ')'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1681);
                    qualifiedName364=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName364.getTree());

                    char_literal365=(Token)match(input,50,FOLLOW_50_in_primary1683); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal365);


                    char_literal366=(Token)match(input,51,FOLLOW_51_in_primary1685); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal366);


                    }


                    // AST REWRITE
                    // elements: 51, qualifiedName, 50
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 261:55: -> ^( PRIMARY qualifiedName '(' ')' )
                    {
                        // org\\aries\\Ariel.g:261:58: ^( PRIMARY qualifiedName '(' ')' )
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
                    // org\\aries\\Ariel.g:262:4: ( qualifiedName '(' )=> ( qualifiedName '(' expressionList ')' )
                    {
                    // org\\aries\\Ariel.g:262:27: ( qualifiedName '(' expressionList ')' )
                    // org\\aries\\Ariel.g:262:28: qualifiedName '(' expressionList ')'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1712);
                    qualifiedName367=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName367.getTree());

                    char_literal368=(Token)match(input,50,FOLLOW_50_in_primary1714); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_50.add(char_literal368);


                    pushFollow(FOLLOW_expressionList_in_primary1716);
                    expressionList369=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionList.add(expressionList369.getTree());

                    char_literal370=(Token)match(input,51,FOLLOW_51_in_primary1718); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal370);


                    }


                    // AST REWRITE
                    // elements: 50, expressionList, qualifiedName, 51
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 262:66: -> ^( PRIMARY qualifiedName '(' expressionList ')' )
                    {
                        // org\\aries\\Ariel.g:262:69: ^( PRIMARY qualifiedName '(' expressionList ')' )
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
                    // org\\aries\\Ariel.g:263:4: ( qualifiedName '[' )=> ( qualifiedName '[' expressionDecl ']' )
                    {
                    // org\\aries\\Ariel.g:263:27: ( qualifiedName '[' expressionDecl ']' )
                    // org\\aries\\Ariel.g:263:28: qualifiedName '[' expressionDecl ']'
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1747);
                    qualifiedName371=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName371.getTree());

                    char_literal372=(Token)match(input,71,FOLLOW_71_in_primary1749); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_71.add(char_literal372);


                    pushFollow(FOLLOW_expressionDecl_in_primary1751);
                    expressionDecl373=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expressionDecl.add(expressionDecl373.getTree());

                    char_literal374=(Token)match(input,72,FOLLOW_72_in_primary1753); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_72.add(char_literal374);


                    }


                    // AST REWRITE
                    // elements: 71, 72, expressionDecl, qualifiedName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 263:66: -> ^( PRIMARY qualifiedName '[' expressionDecl ']' )
                    {
                        // org\\aries\\Ariel.g:263:69: ^( PRIMARY qualifiedName '[' expressionDecl ']' )
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
                    // org\\aries\\Ariel.g:264:4: ( type Identifier '=' )=> type Identifier
                    {
                    pushFollow(FOLLOW_type_in_primary1783);
                    type375=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_type.add(type375.getTree());

                    Identifier376=(Token)match(input,Identifier,FOLLOW_Identifier_in_primary1785); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier376);


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
                    // 264:45: -> ^( PRIMARY type Identifier )
                    {
                        // org\\aries\\Ariel.g:264:48: ^( PRIMARY type Identifier )
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
                    // org\\aries\\Ariel.g:265:4: ( qualifiedName '.' )=> qualifiedName
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1808);
                    qualifiedName377=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName377.getTree());

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
                    // 265:41: -> ^( PRIMARY qualifiedName )
                    {
                        // org\\aries\\Ariel.g:265:44: ^( PRIMARY qualifiedName )
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
                    // org\\aries\\Ariel.g:266:4: qualifiedName
                    {
                    pushFollow(FOLLOW_qualifiedName_in_primary1821);
                    qualifiedName378=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName378.getTree());

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
                    // 266:18: -> ^( PRIMARY qualifiedName )
                    {
                        // org\\aries\\Ariel.g:266:21: ^( PRIMARY qualifiedName )
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
                    // org\\aries\\Ariel.g:267:4: literal
                    {
                    pushFollow(FOLLOW_literal_in_primary1834);
                    literal379=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_literal.add(literal379.getTree());

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
                    // 267:12: -> ^( PRIMARY literal )
                    {
                        // org\\aries\\Ariel.g:267:15: ^( PRIMARY literal )
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
    // org\\aries\\Ariel.g:270:1: selector : ( ( '(' )=> arguments | ( '.' )=> '.' Identifier arguments | ( '[' )=> '[' expressionDecl ']' );
    public final ArielParser.selector_return selector() throws RecognitionException {
        ArielParser.selector_return retval = new ArielParser.selector_return();
        retval.start = input.LT(1);

        int selector_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal381=null;
        Token Identifier382=null;
        Token char_literal384=null;
        Token char_literal386=null;
        ArielParser.arguments_return arguments380 =null;

        ArielParser.arguments_return arguments383 =null;

        ArielParser.expressionDecl_return expressionDecl385 =null;


        Object char_literal381_tree=null;
        Object Identifier382_tree=null;
        Object char_literal384_tree=null;
        Object char_literal386_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }

            // org\\aries\\Ariel.g:271:2: ( ( '(' )=> arguments | ( '.' )=> '.' Identifier arguments | ( '[' )=> '[' expressionDecl ']' )
            int alt51=3;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==50) && (synpred7_Ariel())) {
                alt51=1;
            }
            else if ( (LA51_0==61) && (synpred8_Ariel())) {
                alt51=2;
            }
            else if ( (LA51_0==71) && (synpred9_Ariel())) {
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
                    // org\\aries\\Ariel.g:271:4: ( '(' )=> arguments
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_arguments_in_selector1861);
                    arguments380=arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arguments380.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:272:4: ( '.' )=> '.' Identifier arguments
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal381=(Token)match(input,61,FOLLOW_61_in_selector1872); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal381_tree = 
                    (Object)adaptor.create(char_literal381)
                    ;
                    adaptor.addChild(root_0, char_literal381_tree);
                    }

                    Identifier382=(Token)match(input,Identifier,FOLLOW_Identifier_in_selector1874); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier382_tree = 
                    (Object)adaptor.create(Identifier382)
                    ;
                    adaptor.addChild(root_0, Identifier382_tree);
                    }

                    pushFollow(FOLLOW_arguments_in_selector1876);
                    arguments383=arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arguments383.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:273:4: ( '[' )=> '[' expressionDecl ']'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal384=(Token)match(input,71,FOLLOW_71_in_selector1887); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal384_tree = 
                    (Object)adaptor.create(char_literal384)
                    ;
                    adaptor.addChild(root_0, char_literal384_tree);
                    }

                    pushFollow(FOLLOW_expressionDecl_in_selector1889);
                    expressionDecl385=expressionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionDecl385.getTree());

                    char_literal386=(Token)match(input,72,FOLLOW_72_in_selector1891); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal386_tree = 
                    (Object)adaptor.create(char_literal386)
                    ;
                    adaptor.addChild(root_0, char_literal386_tree);
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
    // org\\aries\\Ariel.g:276:1: type : ( primitiveType ( '[' ']' )* -> ^( TYPE primitiveType ( '[' ']' )* ) | qualifiedName ( typeArguments )? ( '[' ']' )* -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* ) );
    public final ArielParser.type_return type() throws RecognitionException {
        ArielParser.type_return retval = new ArielParser.type_return();
        retval.start = input.LT(1);

        int type_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal388=null;
        Token char_literal389=null;
        Token char_literal392=null;
        Token char_literal393=null;
        ArielParser.primitiveType_return primitiveType387 =null;

        ArielParser.qualifiedName_return qualifiedName390 =null;

        ArielParser.typeArguments_return typeArguments391 =null;


        Object char_literal388_tree=null;
        Object char_literal389_tree=null;
        Object char_literal392_tree=null;
        Object char_literal393_tree=null;
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleSubtreeStream stream_primitiveType=new RewriteRuleSubtreeStream(adaptor,"rule primitiveType");
        RewriteRuleSubtreeStream stream_qualifiedName=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedName");
        RewriteRuleSubtreeStream stream_typeArguments=new RewriteRuleSubtreeStream(adaptor,"rule typeArguments");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }

            // org\\aries\\Ariel.g:277:2: ( primitiveType ( '[' ']' )* -> ^( TYPE primitiveType ( '[' ']' )* ) | qualifiedName ( typeArguments )? ( '[' ']' )* -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* ) )
            int alt55=2;
            switch ( input.LA(1) ) {
            case 78:
            case 81:
            case 84:
            case 91:
            case 95:
            case 102:
            case 108:
            case 131:
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
                    // org\\aries\\Ariel.g:277:4: primitiveType ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_primitiveType_in_type1904);
                    primitiveType387=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primitiveType.add(primitiveType387.getTree());

                    // org\\aries\\Ariel.g:277:18: ( '[' ']' )*
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
                    	    // org\\aries\\Ariel.g:277:19: '[' ']'
                    	    {
                    	    char_literal388=(Token)match(input,71,FOLLOW_71_in_type1907); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_71.add(char_literal388);


                    	    char_literal389=(Token)match(input,72,FOLLOW_72_in_type1909); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_72.add(char_literal389);


                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);


                    // AST REWRITE
                    // elements: 72, 71, primitiveType
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 277:29: -> ^( TYPE primitiveType ( '[' ']' )* )
                    {
                        // org\\aries\\Ariel.g:277:32: ^( TYPE primitiveType ( '[' ']' )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(TYPE, "TYPE")
                        , root_1);

                        adaptor.addChild(root_1, stream_primitiveType.nextTree());

                        // org\\aries\\Ariel.g:277:53: ( '[' ']' )*
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
                    // org\\aries\\Ariel.g:278:4: qualifiedName ( typeArguments )? ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_qualifiedName_in_type1931);
                    qualifiedName390=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_qualifiedName.add(qualifiedName390.getTree());

                    // org\\aries\\Ariel.g:278:18: ( typeArguments )?
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
                            // org\\aries\\Ariel.g:278:18: typeArguments
                            {
                            pushFollow(FOLLOW_typeArguments_in_type1933);
                            typeArguments391=typeArguments();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_typeArguments.add(typeArguments391.getTree());

                            }
                            break;

                    }


                    // org\\aries\\Ariel.g:278:33: ( '[' ']' )*
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
                    	    // org\\aries\\Ariel.g:278:34: '[' ']'
                    	    {
                    	    char_literal392=(Token)match(input,71,FOLLOW_71_in_type1937); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_71.add(char_literal392);


                    	    char_literal393=(Token)match(input,72,FOLLOW_72_in_type1939); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_72.add(char_literal393);


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);


                    // AST REWRITE
                    // elements: 72, typeArguments, 71, qualifiedName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 278:44: -> ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* )
                    {
                        // org\\aries\\Ariel.g:278:47: ^( TYPE qualifiedName ( typeArguments )? ( '[' ']' )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(TYPE, "TYPE")
                        , root_1);

                        adaptor.addChild(root_1, stream_qualifiedName.nextTree());

                        // org\\aries\\Ariel.g:278:68: ( typeArguments )?
                        if ( stream_typeArguments.hasNext() ) {
                            adaptor.addChild(root_1, stream_typeArguments.nextTree());

                        }
                        stream_typeArguments.reset();

                        // org\\aries\\Ariel.g:278:83: ( '[' ']' )*
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
    // org\\aries\\Ariel.g:281:1: typeList : type ( ',' type )* ;
    public final ArielParser.typeList_return typeList() throws RecognitionException {
        ArielParser.typeList_return retval = new ArielParser.typeList_return();
        retval.start = input.LT(1);

        int typeList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal395=null;
        ArielParser.type_return type394 =null;

        ArielParser.type_return type396 =null;


        Object char_literal395_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }

            // org\\aries\\Ariel.g:282:2: ( type ( ',' type )* )
            // org\\aries\\Ariel.g:282:4: type ( ',' type )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_typeList1971);
            type394=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type394.getTree());

            // org\\aries\\Ariel.g:282:9: ( ',' type )*
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
            	    // org\\aries\\Ariel.g:282:10: ',' type
            	    {
            	    char_literal395=(Token)match(input,57,FOLLOW_57_in_typeList1974); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal395_tree = 
            	    (Object)adaptor.create(char_literal395)
            	    ;
            	    adaptor.addChild(root_0, char_literal395_tree);
            	    }

            	    pushFollow(FOLLOW_type_in_typeList1976);
            	    type396=type();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, type396.getTree());

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
    // org\\aries\\Ariel.g:285:1: typeArguments : '<' typeArgument ( ',' typeArgument )* '>' ;
    public final ArielParser.typeArguments_return typeArguments() throws RecognitionException {
        ArielParser.typeArguments_return retval = new ArielParser.typeArguments_return();
        retval.start = input.LT(1);

        int typeArguments_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal397=null;
        Token char_literal399=null;
        Token char_literal401=null;
        ArielParser.typeArgument_return typeArgument398 =null;

        ArielParser.typeArgument_return typeArgument400 =null;


        Object char_literal397_tree=null;
        Object char_literal399_tree=null;
        Object char_literal401_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }

            // org\\aries\\Ariel.g:286:2: ( '<' typeArgument ( ',' typeArgument )* '>' )
            // org\\aries\\Ariel.g:286:4: '<' typeArgument ( ',' typeArgument )* '>'
            {
            root_0 = (Object)adaptor.nil();


            char_literal397=(Token)match(input,66,FOLLOW_66_in_typeArguments1990); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal397_tree = 
            (Object)adaptor.create(char_literal397)
            ;
            adaptor.addChild(root_0, char_literal397_tree);
            }

            pushFollow(FOLLOW_typeArgument_in_typeArguments1992);
            typeArgument398=typeArgument();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeArgument398.getTree());

            // org\\aries\\Ariel.g:286:21: ( ',' typeArgument )*
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
            	    // org\\aries\\Ariel.g:286:22: ',' typeArgument
            	    {
            	    char_literal399=(Token)match(input,57,FOLLOW_57_in_typeArguments1995); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal399_tree = 
            	    (Object)adaptor.create(char_literal399)
            	    ;
            	    adaptor.addChild(root_0, char_literal399_tree);
            	    }

            	    pushFollow(FOLLOW_typeArgument_in_typeArguments1997);
            	    typeArgument400=typeArgument();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, typeArgument400.getTree());

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


            char_literal401=(Token)match(input,69,FOLLOW_69_in_typeArguments2001); if (state.failed) return retval;
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
    // org\\aries\\Ariel.g:289:1: typeArgument : type ;
    public final ArielParser.typeArgument_return typeArgument() throws RecognitionException {
        ArielParser.typeArgument_return retval = new ArielParser.typeArgument_return();
        retval.start = input.LT(1);

        int typeArgument_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.type_return type402 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }

            // org\\aries\\Ariel.g:290:2: ( type )
            // org\\aries\\Ariel.g:290:4: type
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_typeArgument2013);
            type402=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type402.getTree());

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
    // org\\aries\\Ariel.g:293:1: primitiveType : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final ArielParser.primitiveType_return primitiveType() throws RecognitionException {
        ArielParser.primitiveType_return retval = new ArielParser.primitiveType_return();
        retval.start = input.LT(1);

        int primitiveType_StartIndex = input.index();

        Object root_0 = null;

        Token set403=null;

        Object set403_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }

            // org\\aries\\Ariel.g:294:2: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            // org\\aries\\Ariel.g:
            {
            root_0 = (Object)adaptor.nil();


            set403=(Token)input.LT(1);

            if ( input.LA(1)==78||input.LA(1)==81||input.LA(1)==84||input.LA(1)==91||input.LA(1)==95||input.LA(1)==102||input.LA(1)==108||input.LA(1)==131 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set403)
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
    // org\\aries\\Ariel.g:304:1: arguments : '(' ( expressionList )? ')' ;
    public final ArielParser.arguments_return arguments() throws RecognitionException {
        ArielParser.arguments_return retval = new ArielParser.arguments_return();
        retval.start = input.LT(1);

        int arguments_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal404=null;
        Token char_literal406=null;
        ArielParser.expressionList_return expressionList405 =null;


        Object char_literal404_tree=null;
        Object char_literal406_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }

            // org\\aries\\Ariel.g:305:2: ( '(' ( expressionList )? ')' )
            // org\\aries\\Ariel.g:305:4: '(' ( expressionList )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal404=(Token)match(input,50,FOLLOW_50_in_arguments2073); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal404_tree = 
            (Object)adaptor.create(char_literal404)
            ;
            adaptor.addChild(root_0, char_literal404_tree);
            }

            // org\\aries\\Ariel.g:305:8: ( expressionList )?
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
                case 115:
                case 131:
                case 145:
                    {
                    alt58=1;
                    }
                    break;
            }

            switch (alt58) {
                case 1 :
                    // org\\aries\\Ariel.g:305:9: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_arguments2076);
                    expressionList405=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList405.getTree());

                    }
                    break;

            }


            char_literal406=(Token)match(input,51,FOLLOW_51_in_arguments2080); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal406_tree = 
            (Object)adaptor.create(char_literal406)
            ;
            adaptor.addChild(root_0, char_literal406_tree);
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
    // org\\aries\\Ariel.g:308:1: literal : ( IntegerLiteral | FloatLiteral | DoubleLiteral | CHARLITERAL | STRINGLITERAL | TRUE | FALSE | NULL );
    public final ArielParser.literal_return literal() throws RecognitionException {
        ArielParser.literal_return retval = new ArielParser.literal_return();
        retval.start = input.LT(1);

        int literal_StartIndex = input.index();

        Object root_0 = null;

        Token set407=null;

        Object set407_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }

            // org\\aries\\Ariel.g:309:2: ( IntegerLiteral | FloatLiteral | DoubleLiteral | CHARLITERAL | STRINGLITERAL | TRUE | FALSE | NULL )
            // org\\aries\\Ariel.g:
            {
            root_0 = (Object)adaptor.nil();


            set407=(Token)input.LT(1);

            if ( input.LA(1)==CHARLITERAL||input.LA(1)==DoubleLiteral||(input.LA(1) >= FALSE && input.LA(1) <= FloatLiteral)||input.LA(1)==IntegerLiteral||input.LA(1)==NULL||input.LA(1)==STRINGLITERAL||input.LA(1)==TRUE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set407)
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
    // org\\aries\\Ariel.g:320:1: doneDecl : 'done' ';' ;
    public final ArielParser.doneDecl_return doneDecl() throws RecognitionException {
        ArielParser.doneDecl_return retval = new ArielParser.doneDecl_return();
        retval.start = input.LT(1);

        int doneDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal408=null;
        Token char_literal409=null;

        Object string_literal408_tree=null;
        Object char_literal409_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }

            // org\\aries\\Ariel.g:321:2: ( 'done' ';' )
            // org\\aries\\Ariel.g:321:4: 'done' ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal408=(Token)match(input,90,FOLLOW_90_in_doneDecl2141); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal408_tree = 
            (Object)adaptor.create(string_literal408)
            ;
            adaptor.addChild(root_0, string_literal408_tree);
            }

            char_literal409=(Token)match(input,65,FOLLOW_65_in_doneDecl2143); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal409_tree = 
            (Object)adaptor.create(char_literal409)
            ;
            adaptor.addChild(root_0, char_literal409_tree);
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
    // org\\aries\\Ariel.g:324:1: exceptionDecl : EXCEPTION ^ ':' exceptionBody ;
    public final ArielParser.exceptionDecl_return exceptionDecl() throws RecognitionException {
        ArielParser.exceptionDecl_return retval = new ArielParser.exceptionDecl_return();
        retval.start = input.LT(1);

        int exceptionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token EXCEPTION410=null;
        Token char_literal411=null;
        ArielParser.exceptionBody_return exceptionBody412 =null;


        Object EXCEPTION410_tree=null;
        Object char_literal411_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }

            // org\\aries\\Ariel.g:325:2: ( EXCEPTION ^ ':' exceptionBody )
            // org\\aries\\Ariel.g:325:4: EXCEPTION ^ ':' exceptionBody
            {
            root_0 = (Object)adaptor.nil();


            EXCEPTION410=(Token)match(input,EXCEPTION,FOLLOW_EXCEPTION_in_exceptionDecl2154); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EXCEPTION410_tree = 
            (Object)adaptor.create(EXCEPTION410)
            ;
            root_0 = (Object)adaptor.becomeRoot(EXCEPTION410_tree, root_0);
            }

            char_literal411=(Token)match(input,64,FOLLOW_64_in_exceptionDecl2157); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal411_tree = 
            (Object)adaptor.create(char_literal411)
            ;
            adaptor.addChild(root_0, char_literal411_tree);
            }

            pushFollow(FOLLOW_exceptionBody_in_exceptionDecl2159);
            exceptionBody412=exceptionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionBody412.getTree());

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
    // org\\aries\\Ariel.g:328:1: exceptionBody : '{' ( exceptionMember )* '}' ;
    public final ArielParser.exceptionBody_return exceptionBody() throws RecognitionException {
        ArielParser.exceptionBody_return retval = new ArielParser.exceptionBody_return();
        retval.start = input.LT(1);

        int exceptionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal413=null;
        Token char_literal415=null;
        ArielParser.exceptionMember_return exceptionMember414 =null;


        Object char_literal413_tree=null;
        Object char_literal415_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }

            // org\\aries\\Ariel.g:329:2: ( '{' ( exceptionMember )* '}' )
            // org\\aries\\Ariel.g:329:4: '{' ( exceptionMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal413=(Token)match(input,140,FOLLOW_140_in_exceptionBody2170); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal413_tree = 
            (Object)adaptor.create(char_literal413)
            ;
            adaptor.addChild(root_0, char_literal413_tree);
            }

            // org\\aries\\Ariel.g:329:8: ( exceptionMember )*
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
                case 103:
                case 108:
                case 115:
                case 116:
                case 119:
                case 122:
                case 124:
                case 128:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt59=1;
                    }
                    break;

                }

                switch (alt59) {
            	case 1 :
            	    // org\\aries\\Ariel.g:329:9: exceptionMember
            	    {
            	    pushFollow(FOLLOW_exceptionMember_in_exceptionBody2173);
            	    exceptionMember414=exceptionMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionMember414.getTree());

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);


            char_literal415=(Token)match(input,144,FOLLOW_144_in_exceptionBody2177); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal415_tree = 
            (Object)adaptor.create(char_literal415)
            ;
            adaptor.addChild(root_0, char_literal415_tree);
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
    // org\\aries\\Ariel.g:332:1: exceptionMember : ( optionDecl | statementDecl );
    public final ArielParser.exceptionMember_return exceptionMember() throws RecognitionException {
        ArielParser.exceptionMember_return retval = new ArielParser.exceptionMember_return();
        retval.start = input.LT(1);

        int exceptionMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.optionDecl_return optionDecl416 =null;

        ArielParser.statementDecl_return statementDecl417 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }

            // org\\aries\\Ariel.g:333:2: ( optionDecl | statementDecl )
            int alt60=2;
            switch ( input.LA(1) ) {
            case 116:
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
            case 90:
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
                {
                alt60=2;
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
                    // org\\aries\\Ariel.g:333:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_exceptionMember2188);
                    optionDecl416=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl416.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:334:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_exceptionMember2193);
                    statementDecl417=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl417.getTree());

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
    // org\\aries\\Ariel.g:339:1: executeDecl : ( 'execute' ^ executeBody | 'execute' ^ 'then' Identifier formalParameters executeBody );
    public final ArielParser.executeDecl_return executeDecl() throws RecognitionException {
        ArielParser.executeDecl_return retval = new ArielParser.executeDecl_return();
        retval.start = input.LT(1);

        int executeDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal418=null;
        Token string_literal420=null;
        Token string_literal421=null;
        Token Identifier422=null;
        ArielParser.executeBody_return executeBody419 =null;

        ArielParser.formalParameters_return formalParameters423 =null;

        ArielParser.executeBody_return executeBody424 =null;


        Object string_literal418_tree=null;
        Object string_literal420_tree=null;
        Object string_literal421_tree=null;
        Object Identifier422_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }

            // org\\aries\\Ariel.g:340:2: ( 'execute' ^ executeBody | 'execute' ^ 'then' Identifier formalParameters executeBody )
            int alt61=2;
            switch ( input.LA(1) ) {
            case 94:
                {
                switch ( input.LA(2) ) {
                case 134:
                    {
                    alt61=2;
                    }
                    break;
                case 140:
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
                    // org\\aries\\Ariel.g:340:4: 'execute' ^ executeBody
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal418=(Token)match(input,94,FOLLOW_94_in_executeDecl2206); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal418_tree = 
                    (Object)adaptor.create(string_literal418)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal418_tree, root_0);
                    }

                    pushFollow(FOLLOW_executeBody_in_executeDecl2209);
                    executeBody419=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody419.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:341:4: 'execute' ^ 'then' Identifier formalParameters executeBody
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal420=(Token)match(input,94,FOLLOW_94_in_executeDecl2214); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal420_tree = 
                    (Object)adaptor.create(string_literal420)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal420_tree, root_0);
                    }

                    string_literal421=(Token)match(input,134,FOLLOW_134_in_executeDecl2217); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal421_tree = 
                    (Object)adaptor.create(string_literal421)
                    ;
                    adaptor.addChild(root_0, string_literal421_tree);
                    }

                    Identifier422=(Token)match(input,Identifier,FOLLOW_Identifier_in_executeDecl2219); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier422_tree = 
                    (Object)adaptor.create(Identifier422)
                    ;
                    adaptor.addChild(root_0, Identifier422_tree);
                    }

                    pushFollow(FOLLOW_formalParameters_in_executeDecl2221);
                    formalParameters423=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters423.getTree());

                    pushFollow(FOLLOW_executeBody_in_executeDecl2223);
                    executeBody424=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody424.getTree());

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
    // org\\aries\\Ariel.g:344:1: executeDeclRest : formalParameters ( executeBody | ';' ) ;
    public final ArielParser.executeDeclRest_return executeDeclRest() throws RecognitionException {
        ArielParser.executeDeclRest_return retval = new ArielParser.executeDeclRest_return();
        retval.start = input.LT(1);

        int executeDeclRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal427=null;
        ArielParser.formalParameters_return formalParameters425 =null;

        ArielParser.executeBody_return executeBody426 =null;


        Object char_literal427_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }

            // org\\aries\\Ariel.g:345:2: ( formalParameters ( executeBody | ';' ) )
            // org\\aries\\Ariel.g:345:4: formalParameters ( executeBody | ';' )
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameters_in_executeDeclRest2235);
            formalParameters425=formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters425.getTree());

            // org\\aries\\Ariel.g:345:21: ( executeBody | ';' )
            int alt62=2;
            switch ( input.LA(1) ) {
            case 140:
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
                    // org\\aries\\Ariel.g:345:22: executeBody
                    {
                    pushFollow(FOLLOW_executeBody_in_executeDeclRest2238);
                    executeBody426=executeBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeBody426.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:345:36: ';'
                    {
                    char_literal427=(Token)match(input,65,FOLLOW_65_in_executeDeclRest2242); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal427_tree = 
                    (Object)adaptor.create(char_literal427)
                    ;
                    adaptor.addChild(root_0, char_literal427_tree);
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
    // org\\aries\\Ariel.g:348:1: executeBody : '{' ( executeMember )* '}' ;
    public final ArielParser.executeBody_return executeBody() throws RecognitionException {
        ArielParser.executeBody_return retval = new ArielParser.executeBody_return();
        retval.start = input.LT(1);

        int executeBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal428=null;
        Token char_literal430=null;
        ArielParser.executeMember_return executeMember429 =null;


        Object char_literal428_tree=null;
        Object char_literal430_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }

            // org\\aries\\Ariel.g:349:2: ( '{' ( executeMember )* '}' )
            // org\\aries\\Ariel.g:349:4: '{' ( executeMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal428=(Token)match(input,140,FOLLOW_140_in_executeBody2254); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal428_tree = 
            (Object)adaptor.create(char_literal428)
            ;
            adaptor.addChild(root_0, char_literal428_tree);
            }

            // org\\aries\\Ariel.g:349:8: ( executeMember )*
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
                case 130:
                case 137:
                    {
                    alt63=1;
                    }
                    break;

                }

                switch (alt63) {
            	case 1 :
            	    // org\\aries\\Ariel.g:349:9: executeMember
            	    {
            	    pushFollow(FOLLOW_executeMember_in_executeBody2257);
            	    executeMember429=executeMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeMember429.getTree());

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            char_literal430=(Token)match(input,144,FOLLOW_144_in_executeBody2261); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal430_tree = 
            (Object)adaptor.create(char_literal430)
            ;
            adaptor.addChild(root_0, char_literal430_tree);
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
    // org\\aries\\Ariel.g:352:1: executeMember : ( assignmentDecl | branchDecl | exceptionDecl | timeoutDecl );
    public final ArielParser.executeMember_return executeMember() throws RecognitionException {
        ArielParser.executeMember_return retval = new ArielParser.executeMember_return();
        retval.start = input.LT(1);

        int executeMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl431 =null;

        ArielParser.branchDecl_return branchDecl432 =null;

        ArielParser.exceptionDecl_return exceptionDecl433 =null;

        ArielParser.timeoutDecl_return timeoutDecl434 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }

            // org\\aries\\Ariel.g:353:2: ( assignmentDecl | branchDecl | exceptionDecl | timeoutDecl )
            int alt64=4;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
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
            case 137:
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
                    // org\\aries\\Ariel.g:353:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_executeMember2273);
                    assignmentDecl431=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl431.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:354:4: branchDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_branchDecl_in_executeMember2278);
                    branchDecl432=branchDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, branchDecl432.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:355:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_executeMember2283);
                    exceptionDecl433=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl433.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:356:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_executeMember2288);
                    timeoutDecl434=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl434.getTree());

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
    // org\\aries\\Ariel.g:359:1: groupDecl : 'group' ^ Identifier groupBody ;
    public final ArielParser.groupDecl_return groupDecl() throws RecognitionException {
        ArielParser.groupDecl_return retval = new ArielParser.groupDecl_return();
        retval.start = input.LT(1);

        int groupDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal435=null;
        Token Identifier436=null;
        ArielParser.groupBody_return groupBody437 =null;


        Object string_literal435_tree=null;
        Object Identifier436_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }

            // org\\aries\\Ariel.g:360:2: ( 'group' ^ Identifier groupBody )
            // org\\aries\\Ariel.g:360:4: 'group' ^ Identifier groupBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal435=(Token)match(input,97,FOLLOW_97_in_groupDecl2299); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal435_tree = 
            (Object)adaptor.create(string_literal435)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal435_tree, root_0);
            }

            Identifier436=(Token)match(input,Identifier,FOLLOW_Identifier_in_groupDecl2302); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier436_tree = 
            (Object)adaptor.create(Identifier436)
            ;
            adaptor.addChild(root_0, Identifier436_tree);
            }

            pushFollow(FOLLOW_groupBody_in_groupDecl2304);
            groupBody437=groupBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, groupBody437.getTree());

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
    // org\\aries\\Ariel.g:363:1: groupBody : '{' ( groupMember )* '}' ;
    public final ArielParser.groupBody_return groupBody() throws RecognitionException {
        ArielParser.groupBody_return retval = new ArielParser.groupBody_return();
        retval.start = input.LT(1);

        int groupBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal438=null;
        Token char_literal440=null;
        ArielParser.groupMember_return groupMember439 =null;


        Object char_literal438_tree=null;
        Object char_literal440_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }

            // org\\aries\\Ariel.g:364:2: ( '{' ( groupMember )* '}' )
            // org\\aries\\Ariel.g:364:4: '{' ( groupMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal438=(Token)match(input,140,FOLLOW_140_in_groupBody2315); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal438_tree = 
            (Object)adaptor.create(char_literal438)
            ;
            adaptor.addChild(root_0, char_literal438_tree);
            }

            // org\\aries\\Ariel.g:364:8: ( groupMember )*
            loop65:
            do {
                int alt65=2;
                switch ( input.LA(1) ) {
                case 76:
                case 99:
                case 100:
                case 105:
                case 130:
                    {
                    alt65=1;
                    }
                    break;

                }

                switch (alt65) {
            	case 1 :
            	    // org\\aries\\Ariel.g:364:9: groupMember
            	    {
            	    pushFollow(FOLLOW_groupMember_in_groupBody2318);
            	    groupMember439=groupMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, groupMember439.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);


            char_literal440=(Token)match(input,144,FOLLOW_144_in_groupBody2322); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal440_tree = 
            (Object)adaptor.create(char_literal440)
            ;
            adaptor.addChild(root_0, char_literal440_tree);
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
    // org\\aries\\Ariel.g:367:1: groupMember : assignmentDecl ;
    public final ArielParser.groupMember_return groupMember() throws RecognitionException {
        ArielParser.groupMember_return retval = new ArielParser.groupMember_return();
        retval.start = input.LT(1);

        int groupMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl441 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }

            // org\\aries\\Ariel.g:368:2: ( assignmentDecl )
            // org\\aries\\Ariel.g:368:4: assignmentDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_assignmentDecl_in_groupMember2333);
            assignmentDecl441=assignmentDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl441.getTree());

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
    // org\\aries\\Ariel.g:371:1: importDecl : 'import' ^ qualifiedName ( '.' '*' )? ';' ;
    public final ArielParser.importDecl_return importDecl() throws RecognitionException {
        ArielParser.importDecl_return retval = new ArielParser.importDecl_return();
        retval.start = input.LT(1);

        int importDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal442=null;
        Token char_literal444=null;
        Token char_literal445=null;
        Token char_literal446=null;
        ArielParser.qualifiedName_return qualifiedName443 =null;


        Object string_literal442_tree=null;
        Object char_literal444_tree=null;
        Object char_literal445_tree=null;
        Object char_literal446_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }

            // org\\aries\\Ariel.g:372:2: ( 'import' ^ qualifiedName ( '.' '*' )? ';' )
            // org\\aries\\Ariel.g:372:4: 'import' ^ qualifiedName ( '.' '*' )? ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal442=(Token)match(input,99,FOLLOW_99_in_importDecl2345); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal442_tree = 
            (Object)adaptor.create(string_literal442)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal442_tree, root_0);
            }

            pushFollow(FOLLOW_qualifiedName_in_importDecl2348);
            qualifiedName443=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName443.getTree());

            // org\\aries\\Ariel.g:372:28: ( '.' '*' )?
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
                    // org\\aries\\Ariel.g:372:29: '.' '*'
                    {
                    char_literal444=(Token)match(input,61,FOLLOW_61_in_importDecl2351); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal444_tree = 
                    (Object)adaptor.create(char_literal444)
                    ;
                    adaptor.addChild(root_0, char_literal444_tree);
                    }

                    char_literal445=(Token)match(input,52,FOLLOW_52_in_importDecl2353); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal445_tree = 
                    (Object)adaptor.create(char_literal445)
                    ;
                    adaptor.addChild(root_0, char_literal445_tree);
                    }

                    }
                    break;

            }


            char_literal446=(Token)match(input,65,FOLLOW_65_in_importDecl2357); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal446_tree = 
            (Object)adaptor.create(char_literal446)
            ;
            adaptor.addChild(root_0, char_literal446_tree);
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
    // org\\aries\\Ariel.g:375:1: invokeDecl : 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody ;
    public final ArielParser.invokeDecl_return invokeDecl() throws RecognitionException {
        ArielParser.invokeDecl_return retval = new ArielParser.invokeDecl_return();
        retval.start = input.LT(1);

        int invokeDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal447=null;
        Token Identifier448=null;
        Token char_literal449=null;
        Token Identifier450=null;
        ArielParser.formalParameters_return formalParameters451 =null;

        ArielParser.invokeBody_return invokeBody452 =null;


        Object string_literal447_tree=null;
        Object Identifier448_tree=null;
        Object char_literal449_tree=null;
        Object Identifier450_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }

            // org\\aries\\Ariel.g:376:2: ( 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody )
            // org\\aries\\Ariel.g:376:4: 'invoke' ^ Identifier '.' Identifier formalParameters invokeBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal447=(Token)match(input,103,FOLLOW_103_in_invokeDecl2368); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal447_tree = 
            (Object)adaptor.create(string_literal447)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal447_tree, root_0);
            }

            Identifier448=(Token)match(input,Identifier,FOLLOW_Identifier_in_invokeDecl2371); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier448_tree = 
            (Object)adaptor.create(Identifier448)
            ;
            adaptor.addChild(root_0, Identifier448_tree);
            }

            char_literal449=(Token)match(input,61,FOLLOW_61_in_invokeDecl2373); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal449_tree = 
            (Object)adaptor.create(char_literal449)
            ;
            adaptor.addChild(root_0, char_literal449_tree);
            }

            Identifier450=(Token)match(input,Identifier,FOLLOW_Identifier_in_invokeDecl2375); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier450_tree = 
            (Object)adaptor.create(Identifier450)
            ;
            adaptor.addChild(root_0, Identifier450_tree);
            }

            pushFollow(FOLLOW_formalParameters_in_invokeDecl2377);
            formalParameters451=formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters451.getTree());

            pushFollow(FOLLOW_invokeBody_in_invokeDecl2379);
            invokeBody452=invokeBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeBody452.getTree());

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
    // org\\aries\\Ariel.g:379:1: invokeBody : '{' ( invokeMember )* '}' ;
    public final ArielParser.invokeBody_return invokeBody() throws RecognitionException {
        ArielParser.invokeBody_return retval = new ArielParser.invokeBody_return();
        retval.start = input.LT(1);

        int invokeBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal453=null;
        Token char_literal455=null;
        ArielParser.invokeMember_return invokeMember454 =null;


        Object char_literal453_tree=null;
        Object char_literal455_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }

            // org\\aries\\Ariel.g:380:2: ( '{' ( invokeMember )* '}' )
            // org\\aries\\Ariel.g:380:4: '{' ( invokeMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal453=(Token)match(input,140,FOLLOW_140_in_invokeBody2390); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal453_tree = 
            (Object)adaptor.create(char_literal453)
            ;
            adaptor.addChild(root_0, char_literal453_tree);
            }

            // org\\aries\\Ariel.g:380:8: ( invokeMember )*
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
                case 130:
                case 137:
                    {
                    alt67=1;
                    }
                    break;

                }

                switch (alt67) {
            	case 1 :
            	    // org\\aries\\Ariel.g:380:9: invokeMember
            	    {
            	    pushFollow(FOLLOW_invokeMember_in_invokeBody2393);
            	    invokeMember454=invokeMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeMember454.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            char_literal455=(Token)match(input,144,FOLLOW_144_in_invokeBody2397); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal455_tree = 
            (Object)adaptor.create(char_literal455)
            ;
            adaptor.addChild(root_0, char_literal455_tree);
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
    // org\\aries\\Ariel.g:383:1: invokeMember : ( assignmentDecl | messageDecl | exceptionDecl | timeoutDecl );
    public final ArielParser.invokeMember_return invokeMember() throws RecognitionException {
        ArielParser.invokeMember_return retval = new ArielParser.invokeMember_return();
        retval.start = input.LT(1);

        int invokeMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl456 =null;

        ArielParser.messageDecl_return messageDecl457 =null;

        ArielParser.exceptionDecl_return exceptionDecl458 =null;

        ArielParser.timeoutDecl_return timeoutDecl459 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }

            // org\\aries\\Ariel.g:384:2: ( assignmentDecl | messageDecl | exceptionDecl | timeoutDecl )
            int alt68=4;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
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
            case 137:
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
                    // org\\aries\\Ariel.g:384:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_invokeMember2408);
                    assignmentDecl456=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl456.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:385:4: messageDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_messageDecl_in_invokeMember2413);
                    messageDecl457=messageDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, messageDecl457.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:386:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_invokeMember2418);
                    exceptionDecl458=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl458.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:387:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_invokeMember2423);
                    timeoutDecl459=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl459.getTree());

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
    // org\\aries\\Ariel.g:390:1: itemsDecl : 'items' ^ itemsBody ;
    public final ArielParser.itemsDecl_return itemsDecl() throws RecognitionException {
        ArielParser.itemsDecl_return retval = new ArielParser.itemsDecl_return();
        retval.start = input.LT(1);

        int itemsDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal460=null;
        ArielParser.itemsBody_return itemsBody461 =null;


        Object string_literal460_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }

            // org\\aries\\Ariel.g:391:2: ( 'items' ^ itemsBody )
            // org\\aries\\Ariel.g:391:4: 'items' ^ itemsBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal460=(Token)match(input,104,FOLLOW_104_in_itemsDecl2434); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal460_tree = 
            (Object)adaptor.create(string_literal460)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal460_tree, root_0);
            }

            pushFollow(FOLLOW_itemsBody_in_itemsDecl2437);
            itemsBody461=itemsBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsBody461.getTree());

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
    // org\\aries\\Ariel.g:394:1: itemsBody : '{' ( itemsMember )* '}' ;
    public final ArielParser.itemsBody_return itemsBody() throws RecognitionException {
        ArielParser.itemsBody_return retval = new ArielParser.itemsBody_return();
        retval.start = input.LT(1);

        int itemsBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal462=null;
        Token char_literal464=null;
        ArielParser.itemsMember_return itemsMember463 =null;


        Object char_literal462_tree=null;
        Object char_literal464_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }

            // org\\aries\\Ariel.g:395:2: ( '{' ( itemsMember )* '}' )
            // org\\aries\\Ariel.g:395:4: '{' ( itemsMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal462=(Token)match(input,140,FOLLOW_140_in_itemsBody2448); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal462_tree = 
            (Object)adaptor.create(char_literal462)
            ;
            adaptor.addChild(root_0, char_literal462_tree);
            }

            // org\\aries\\Ariel.g:395:8: ( itemsMember )*
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
                case 131:
                    {
                    alt69=1;
                    }
                    break;

                }

                switch (alt69) {
            	case 1 :
            	    // org\\aries\\Ariel.g:395:9: itemsMember
            	    {
            	    pushFollow(FOLLOW_itemsMember_in_itemsBody2451);
            	    itemsMember463=itemsMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsMember463.getTree());

            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);


            char_literal464=(Token)match(input,144,FOLLOW_144_in_itemsBody2455); if (state.failed) return retval;
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
    // org\\aries\\Ariel.g:398:1: itemsMember : itemDecl ;
    public final ArielParser.itemsMember_return itemsMember() throws RecognitionException {
        ArielParser.itemsMember_return retval = new ArielParser.itemsMember_return();
        retval.start = input.LT(1);

        int itemsMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.itemDecl_return itemDecl465 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }

            // org\\aries\\Ariel.g:399:2: ( itemDecl )
            // org\\aries\\Ariel.g:399:4: itemDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_itemDecl_in_itemsMember2466);
            itemDecl465=itemDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemDecl465.getTree());

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
    // org\\aries\\Ariel.g:402:1: itemDecl : type Identifier itemDeclRest -> ^( ITEM type Identifier itemDeclRest ) ;
    public final ArielParser.itemDecl_return itemDecl() throws RecognitionException {
        ArielParser.itemDecl_return retval = new ArielParser.itemDecl_return();
        retval.start = input.LT(1);

        int itemDecl_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier467=null;
        ArielParser.type_return type466 =null;

        ArielParser.itemDeclRest_return itemDeclRest468 =null;


        Object Identifier467_tree=null;
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_itemDeclRest=new RewriteRuleSubtreeStream(adaptor,"rule itemDeclRest");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }

            // org\\aries\\Ariel.g:403:2: ( type Identifier itemDeclRest -> ^( ITEM type Identifier itemDeclRest ) )
            // org\\aries\\Ariel.g:403:4: type Identifier itemDeclRest
            {
            pushFollow(FOLLOW_type_in_itemDecl2477);
            type466=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type466.getTree());

            Identifier467=(Token)match(input,Identifier,FOLLOW_Identifier_in_itemDecl2479); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Identifier.add(Identifier467);


            pushFollow(FOLLOW_itemDeclRest_in_itemDecl2481);
            itemDeclRest468=itemDeclRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_itemDeclRest.add(itemDeclRest468.getTree());

            // AST REWRITE
            // elements: Identifier, type, itemDeclRest
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 403:33: -> ^( ITEM type Identifier itemDeclRest )
            {
                // org\\aries\\Ariel.g:403:36: ^( ITEM type Identifier itemDeclRest )
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
    // org\\aries\\Ariel.g:407:1: itemDeclRest : ( '{' ( itemMember )* '}' | ';' );
    public final ArielParser.itemDeclRest_return itemDeclRest() throws RecognitionException {
        ArielParser.itemDeclRest_return retval = new ArielParser.itemDeclRest_return();
        retval.start = input.LT(1);

        int itemDeclRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal469=null;
        Token char_literal471=null;
        Token char_literal472=null;
        ArielParser.itemMember_return itemMember470 =null;


        Object char_literal469_tree=null;
        Object char_literal471_tree=null;
        Object char_literal472_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }

            // org\\aries\\Ariel.g:408:2: ( '{' ( itemMember )* '}' | ';' )
            int alt71=2;
            switch ( input.LA(1) ) {
            case 140:
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
                    // org\\aries\\Ariel.g:408:4: '{' ( itemMember )* '}'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal469=(Token)match(input,140,FOLLOW_140_in_itemDeclRest2507); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal469_tree = 
                    (Object)adaptor.create(char_literal469)
                    ;
                    adaptor.addChild(root_0, char_literal469_tree);
                    }

                    // org\\aries\\Ariel.g:408:8: ( itemMember )*
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
                    	    // org\\aries\\Ariel.g:408:9: itemMember
                    	    {
                    	    pushFollow(FOLLOW_itemMember_in_itemDeclRest2510);
                    	    itemMember470=itemMember();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemMember470.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);


                    char_literal471=(Token)match(input,144,FOLLOW_144_in_itemDeclRest2514); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal471_tree = 
                    (Object)adaptor.create(char_literal471)
                    ;
                    adaptor.addChild(root_0, char_literal471_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:409:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal472=(Token)match(input,65,FOLLOW_65_in_itemDeclRest2519); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal472_tree = 
                    (Object)adaptor.create(char_literal472)
                    ;
                    adaptor.addChild(root_0, char_literal472_tree);
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
    // org\\aries\\Ariel.g:412:1: itemMember : 'index' ^ '(' Identifier ')' ';' ;
    public final ArielParser.itemMember_return itemMember() throws RecognitionException {
        ArielParser.itemMember_return retval = new ArielParser.itemMember_return();
        retval.start = input.LT(1);

        int itemMember_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal473=null;
        Token char_literal474=null;
        Token Identifier475=null;
        Token char_literal476=null;
        Token char_literal477=null;

        Object string_literal473_tree=null;
        Object char_literal474_tree=null;
        Object Identifier475_tree=null;
        Object char_literal476_tree=null;
        Object char_literal477_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }

            // org\\aries\\Ariel.g:413:2: ( 'index' ^ '(' Identifier ')' ';' )
            // org\\aries\\Ariel.g:413:4: 'index' ^ '(' Identifier ')' ';'
            {
            root_0 = (Object)adaptor.nil();


            string_literal473=(Token)match(input,101,FOLLOW_101_in_itemMember2530); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal473_tree = 
            (Object)adaptor.create(string_literal473)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal473_tree, root_0);
            }

            char_literal474=(Token)match(input,50,FOLLOW_50_in_itemMember2533); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal474_tree = 
            (Object)adaptor.create(char_literal474)
            ;
            adaptor.addChild(root_0, char_literal474_tree);
            }

            Identifier475=(Token)match(input,Identifier,FOLLOW_Identifier_in_itemMember2535); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier475_tree = 
            (Object)adaptor.create(Identifier475)
            ;
            adaptor.addChild(root_0, Identifier475_tree);
            }

            char_literal476=(Token)match(input,51,FOLLOW_51_in_itemMember2537); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal476_tree = 
            (Object)adaptor.create(char_literal476)
            ;
            adaptor.addChild(root_0, char_literal476_tree);
            }

            char_literal477=(Token)match(input,65,FOLLOW_65_in_itemMember2539); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal477_tree = 
            (Object)adaptor.create(char_literal477)
            ;
            adaptor.addChild(root_0, char_literal477_tree);
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
    // org\\aries\\Ariel.g:416:1: listenDecl : 'listen' ^ Identifier ( formalParameters )? listenBody ;
    public final ArielParser.listenDecl_return listenDecl() throws RecognitionException {
        ArielParser.listenDecl_return retval = new ArielParser.listenDecl_return();
        retval.start = input.LT(1);

        int listenDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal478=null;
        Token Identifier479=null;
        ArielParser.formalParameters_return formalParameters480 =null;

        ArielParser.listenBody_return listenBody481 =null;


        Object string_literal478_tree=null;
        Object Identifier479_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }

            // org\\aries\\Ariel.g:417:2: ( 'listen' ^ Identifier ( formalParameters )? listenBody )
            // org\\aries\\Ariel.g:417:4: 'listen' ^ Identifier ( formalParameters )? listenBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal478=(Token)match(input,107,FOLLOW_107_in_listenDecl2550); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal478_tree = 
            (Object)adaptor.create(string_literal478)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal478_tree, root_0);
            }

            Identifier479=(Token)match(input,Identifier,FOLLOW_Identifier_in_listenDecl2553); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier479_tree = 
            (Object)adaptor.create(Identifier479)
            ;
            adaptor.addChild(root_0, Identifier479_tree);
            }

            // org\\aries\\Ariel.g:417:25: ( formalParameters )?
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
                    // org\\aries\\Ariel.g:417:25: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_listenDecl2555);
                    formalParameters480=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters480.getTree());

                    }
                    break;

            }


            pushFollow(FOLLOW_listenBody_in_listenDecl2558);
            listenBody481=listenBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, listenBody481.getTree());

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
    // org\\aries\\Ariel.g:420:1: listenBody : '{' ( listenMember )* '}' ;
    public final ArielParser.listenBody_return listenBody() throws RecognitionException {
        ArielParser.listenBody_return retval = new ArielParser.listenBody_return();
        retval.start = input.LT(1);

        int listenBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal482=null;
        Token char_literal484=null;
        ArielParser.listenMember_return listenMember483 =null;


        Object char_literal482_tree=null;
        Object char_literal484_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }

            // org\\aries\\Ariel.g:421:2: ( '{' ( listenMember )* '}' )
            // org\\aries\\Ariel.g:421:4: '{' ( listenMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal482=(Token)match(input,140,FOLLOW_140_in_listenBody2570); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal482_tree = 
            (Object)adaptor.create(char_literal482)
            ;
            adaptor.addChild(root_0, char_literal482_tree);
            }

            // org\\aries\\Ariel.g:421:8: ( listenMember )*
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
                case 103:
                case 105:
                case 108:
                case 115:
                case 116:
                case 119:
                case 122:
                case 124:
                case 128:
                case 130:
                case 131:
                case 135:
                case 137:
                case 139:
                case 140:
                case 145:
                    {
                    alt73=1;
                    }
                    break;

                }

                switch (alt73) {
            	case 1 :
            	    // org\\aries\\Ariel.g:421:9: listenMember
            	    {
            	    pushFollow(FOLLOW_listenMember_in_listenBody2573);
            	    listenMember483=listenMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenMember483.getTree());

            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);


            char_literal484=(Token)match(input,144,FOLLOW_144_in_listenBody2577); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal484_tree = 
            (Object)adaptor.create(char_literal484)
            ;
            adaptor.addChild(root_0, char_literal484_tree);
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
    // org\\aries\\Ariel.g:424:1: listenMember : ( assignmentDecl | optionDecl | statementDecl | timeoutDecl | exceptionDecl );
    public final ArielParser.listenMember_return listenMember() throws RecognitionException {
        ArielParser.listenMember_return retval = new ArielParser.listenMember_return();
        retval.start = input.LT(1);

        int listenMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl485 =null;

        ArielParser.optionDecl_return optionDecl486 =null;

        ArielParser.statementDecl_return statementDecl487 =null;

        ArielParser.timeoutDecl_return timeoutDecl488 =null;

        ArielParser.exceptionDecl_return exceptionDecl489 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }

            // org\\aries\\Ariel.g:425:2: ( assignmentDecl | optionDecl | statementDecl | timeoutDecl | exceptionDecl )
            int alt74=5;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
                {
                alt74=1;
                }
                break;
            case 116:
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
            case 90:
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
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
                case 141:
                case 142:
                case 143:
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
            case 137:
                {
                alt74=4;
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
                    // org\\aries\\Ariel.g:425:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_listenMember2588);
                    assignmentDecl485=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl485.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:426:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_listenMember2593);
                    optionDecl486=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl486.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:427:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_listenMember2598);
                    statementDecl487=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl487.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:429:4: timeoutDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_timeoutDecl_in_listenMember2604);
                    timeoutDecl488=timeoutDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutDecl488.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:430:4: exceptionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_exceptionDecl_in_listenMember2609);
                    exceptionDecl489=exceptionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exceptionDecl489.getTree());

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
    // org\\aries\\Ariel.g:434:1: messageDecl : MESSAGE ^ Identifier ( formalParameters )? ':' messageBody ;
    public final ArielParser.messageDecl_return messageDecl() throws RecognitionException {
        ArielParser.messageDecl_return retval = new ArielParser.messageDecl_return();
        retval.start = input.LT(1);

        int messageDecl_StartIndex = input.index();

        Object root_0 = null;

        Token MESSAGE490=null;
        Token Identifier491=null;
        Token char_literal493=null;
        ArielParser.formalParameters_return formalParameters492 =null;

        ArielParser.messageBody_return messageBody494 =null;


        Object MESSAGE490_tree=null;
        Object Identifier491_tree=null;
        Object char_literal493_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }

            // org\\aries\\Ariel.g:435:2: ( MESSAGE ^ Identifier ( formalParameters )? ':' messageBody )
            // org\\aries\\Ariel.g:435:4: MESSAGE ^ Identifier ( formalParameters )? ':' messageBody
            {
            root_0 = (Object)adaptor.nil();


            MESSAGE490=(Token)match(input,MESSAGE,FOLLOW_MESSAGE_in_messageDecl2621); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MESSAGE490_tree = 
            (Object)adaptor.create(MESSAGE490)
            ;
            root_0 = (Object)adaptor.becomeRoot(MESSAGE490_tree, root_0);
            }

            Identifier491=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageDecl2624); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier491_tree = 
            (Object)adaptor.create(Identifier491)
            ;
            adaptor.addChild(root_0, Identifier491_tree);
            }

            // org\\aries\\Ariel.g:435:24: ( formalParameters )?
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
                    // org\\aries\\Ariel.g:435:24: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_messageDecl2626);
                    formalParameters492=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters492.getTree());

                    }
                    break;

            }


            char_literal493=(Token)match(input,64,FOLLOW_64_in_messageDecl2629); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal493_tree = 
            (Object)adaptor.create(char_literal493)
            ;
            adaptor.addChild(root_0, char_literal493_tree);
            }

            pushFollow(FOLLOW_messageBody_in_messageDecl2631);
            messageBody494=messageBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, messageBody494.getTree());

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
    // org\\aries\\Ariel.g:438:1: messageBody : '{' ( messageMember )* '}' ;
    public final ArielParser.messageBody_return messageBody() throws RecognitionException {
        ArielParser.messageBody_return retval = new ArielParser.messageBody_return();
        retval.start = input.LT(1);

        int messageBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal495=null;
        Token char_literal497=null;
        ArielParser.messageMember_return messageMember496 =null;


        Object char_literal495_tree=null;
        Object char_literal497_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }

            // org\\aries\\Ariel.g:439:2: ( '{' ( messageMember )* '}' )
            // org\\aries\\Ariel.g:439:4: '{' ( messageMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal495=(Token)match(input,140,FOLLOW_140_in_messageBody2643); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal495_tree = 
            (Object)adaptor.create(char_literal495)
            ;
            adaptor.addChild(root_0, char_literal495_tree);
            }

            // org\\aries\\Ariel.g:439:8: ( messageMember )*
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
                case 115:
                case 116:
                case 119:
                case 122:
                case 124:
                case 128:
                case 130:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt76=1;
                    }
                    break;

                }

                switch (alt76) {
            	case 1 :
            	    // org\\aries\\Ariel.g:439:9: messageMember
            	    {
            	    pushFollow(FOLLOW_messageMember_in_messageBody2646);
            	    messageMember496=messageMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, messageMember496.getTree());

            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);


            char_literal497=(Token)match(input,144,FOLLOW_144_in_messageBody2650); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal497_tree = 
            (Object)adaptor.create(char_literal497)
            ;
            adaptor.addChild(root_0, char_literal497_tree);
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
    // org\\aries\\Ariel.g:442:1: messageMember : ( assignmentDecl | optionDecl | executeDecl | listenDecl | statementDecl );
    public final ArielParser.messageMember_return messageMember() throws RecognitionException {
        ArielParser.messageMember_return retval = new ArielParser.messageMember_return();
        retval.start = input.LT(1);

        int messageMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl498 =null;

        ArielParser.optionDecl_return optionDecl499 =null;

        ArielParser.executeDecl_return executeDecl500 =null;

        ArielParser.listenDecl_return listenDecl501 =null;

        ArielParser.statementDecl_return statementDecl502 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }

            // org\\aries\\Ariel.g:443:2: ( assignmentDecl | optionDecl | executeDecl | listenDecl | statementDecl )
            int alt77=5;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
                {
                alt77=1;
                }
                break;
            case 116:
                {
                alt77=2;
                }
                break;
            case 94:
                {
                alt77=3;
                }
                break;
            case 107:
                {
                alt77=4;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
                {
                alt77=5;
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
                    // org\\aries\\Ariel.g:443:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_messageMember2661);
                    assignmentDecl498=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl498.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:444:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_messageMember2666);
                    optionDecl499=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl499.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:445:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_messageMember2671);
                    executeDecl500=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl500.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:446:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_messageMember2676);
                    listenDecl501=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl501.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:447:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_messageMember2681);
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
    // org\\aries\\Ariel.g:452:1: methodDecl : qualifiedName ^ formalParametersSignature methodBody ;
    public final ArielParser.methodDecl_return methodDecl() throws RecognitionException {
        ArielParser.methodDecl_return retval = new ArielParser.methodDecl_return();
        retval.start = input.LT(1);

        int methodDecl_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.qualifiedName_return qualifiedName503 =null;

        ArielParser.formalParametersSignature_return formalParametersSignature504 =null;

        ArielParser.methodBody_return methodBody505 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }

            // org\\aries\\Ariel.g:453:2: ( qualifiedName ^ formalParametersSignature methodBody )
            // org\\aries\\Ariel.g:453:4: qualifiedName ^ formalParametersSignature methodBody
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_methodDecl2695);
            qualifiedName503=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(qualifiedName503.getTree(), root_0);

            pushFollow(FOLLOW_formalParametersSignature_in_methodDecl2698);
            formalParametersSignature504=formalParametersSignature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParametersSignature504.getTree());

            pushFollow(FOLLOW_methodBody_in_methodDecl2700);
            methodBody505=methodBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, methodBody505.getTree());

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
    // org\\aries\\Ariel.g:456:1: methodBody : '{' ( methodMember )* '}' ;
    public final ArielParser.methodBody_return methodBody() throws RecognitionException {
        ArielParser.methodBody_return retval = new ArielParser.methodBody_return();
        retval.start = input.LT(1);

        int methodBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal506=null;
        Token char_literal508=null;
        ArielParser.methodMember_return methodMember507 =null;


        Object char_literal506_tree=null;
        Object char_literal508_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }

            // org\\aries\\Ariel.g:457:2: ( '{' ( methodMember )* '}' )
            // org\\aries\\Ariel.g:457:4: '{' ( methodMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal506=(Token)match(input,140,FOLLOW_140_in_methodBody2711); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal506_tree = 
            (Object)adaptor.create(char_literal506)
            ;
            adaptor.addChild(root_0, char_literal506_tree);
            }

            // org\\aries\\Ariel.g:457:8: ( methodMember )*
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
                case 90:
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
                case 115:
                case 119:
                case 122:
                case 124:
                case 128:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt78=1;
                    }
                    break;

                }

                switch (alt78) {
            	case 1 :
            	    // org\\aries\\Ariel.g:457:9: methodMember
            	    {
            	    pushFollow(FOLLOW_methodMember_in_methodBody2714);
            	    methodMember507=methodMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodMember507.getTree());

            	    }
            	    break;

            	default :
            	    break loop78;
                }
            } while (true);


            char_literal508=(Token)match(input,144,FOLLOW_144_in_methodBody2718); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal508_tree = 
            (Object)adaptor.create(char_literal508)
            ;
            adaptor.addChild(root_0, char_literal508_tree);
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
    // org\\aries\\Ariel.g:460:1: methodMember : ( executeDecl | listenDecl | statementDecl );
    public final ArielParser.methodMember_return methodMember() throws RecognitionException {
        ArielParser.methodMember_return retval = new ArielParser.methodMember_return();
        retval.start = input.LT(1);

        int methodMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.executeDecl_return executeDecl509 =null;

        ArielParser.listenDecl_return listenDecl510 =null;

        ArielParser.statementDecl_return statementDecl511 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }

            // org\\aries\\Ariel.g:461:2: ( executeDecl | listenDecl | statementDecl )
            int alt79=3;
            switch ( input.LA(1) ) {
            case 94:
                {
                alt79=1;
                }
                break;
            case 107:
                {
                alt79=2;
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
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
                {
                alt79=3;
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
                    // org\\aries\\Ariel.g:461:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_methodMember2729);
                    executeDecl509=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl509.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:462:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_methodMember2734);
                    listenDecl510=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl510.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:463:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_methodMember2739);
                    statementDecl511=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl511.getTree());

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
    // org\\aries\\Ariel.g:467:1: optionDecl : 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody ;
    public final ArielParser.optionDecl_return optionDecl() throws RecognitionException {
        ArielParser.optionDecl_return retval = new ArielParser.optionDecl_return();
        retval.start = input.LT(1);

        int optionDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal512=null;
        Token Identifier513=null;
        Token char_literal515=null;
        ArielParser.formalParameters_return formalParameters514 =null;

        ArielParser.optionBody_return optionBody516 =null;


        Object string_literal512_tree=null;
        Object Identifier513_tree=null;
        Object char_literal515_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }

            // org\\aries\\Ariel.g:468:2: ( 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody )
            // org\\aries\\Ariel.g:468:4: 'option' ^ ( Identifier )? ( formalParameters )? ':' optionBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal512=(Token)match(input,116,FOLLOW_116_in_optionDecl2751); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal512_tree = 
            (Object)adaptor.create(string_literal512)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal512_tree, root_0);
            }

            // org\\aries\\Ariel.g:468:14: ( Identifier )?
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
                    // org\\aries\\Ariel.g:468:14: Identifier
                    {
                    Identifier513=(Token)match(input,Identifier,FOLLOW_Identifier_in_optionDecl2754); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier513_tree = 
                    (Object)adaptor.create(Identifier513)
                    ;
                    adaptor.addChild(root_0, Identifier513_tree);
                    }

                    }
                    break;

            }


            // org\\aries\\Ariel.g:468:26: ( formalParameters )?
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
                    // org\\aries\\Ariel.g:468:26: formalParameters
                    {
                    pushFollow(FOLLOW_formalParameters_in_optionDecl2757);
                    formalParameters514=formalParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters514.getTree());

                    }
                    break;

            }


            char_literal515=(Token)match(input,64,FOLLOW_64_in_optionDecl2760); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal515_tree = 
            (Object)adaptor.create(char_literal515)
            ;
            adaptor.addChild(root_0, char_literal515_tree);
            }

            pushFollow(FOLLOW_optionBody_in_optionDecl2762);
            optionBody516=optionBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, optionBody516.getTree());

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
    // org\\aries\\Ariel.g:471:1: optionBody : '{' ( optionMember )* '}' ;
    public final ArielParser.optionBody_return optionBody() throws RecognitionException {
        ArielParser.optionBody_return retval = new ArielParser.optionBody_return();
        retval.start = input.LT(1);

        int optionBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal517=null;
        Token char_literal519=null;
        ArielParser.optionMember_return optionMember518 =null;


        Object char_literal517_tree=null;
        Object char_literal519_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 76) ) { return retval; }

            // org\\aries\\Ariel.g:472:2: ( '{' ( optionMember )* '}' )
            // org\\aries\\Ariel.g:472:4: '{' ( optionMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal517=(Token)match(input,140,FOLLOW_140_in_optionBody2773); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal517_tree = 
            (Object)adaptor.create(char_literal517)
            ;
            adaptor.addChild(root_0, char_literal517_tree);
            }

            // org\\aries\\Ariel.g:472:8: ( optionMember )*
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
                case 90:
                case 91:
                case 93:
                case 95:
                case 96:
                case 98:
                case 102:
                case 103:
                case 108:
                case 115:
                case 119:
                case 122:
                case 124:
                case 128:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt82=1;
                    }
                    break;

                }

                switch (alt82) {
            	case 1 :
            	    // org\\aries\\Ariel.g:472:9: optionMember
            	    {
            	    pushFollow(FOLLOW_optionMember_in_optionBody2776);
            	    optionMember518=optionMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionMember518.getTree());

            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);


            char_literal519=(Token)match(input,144,FOLLOW_144_in_optionBody2780); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal519_tree = 
            (Object)adaptor.create(char_literal519)
            ;
            adaptor.addChild(root_0, char_literal519_tree);
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
    // org\\aries\\Ariel.g:475:1: optionMember : statementDecl ;
    public final ArielParser.optionMember_return optionMember() throws RecognitionException {
        ArielParser.optionMember_return retval = new ArielParser.optionMember_return();
        retval.start = input.LT(1);

        int optionMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.statementDecl_return statementDecl520 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 77) ) { return retval; }

            // org\\aries\\Ariel.g:476:2: ( statementDecl )
            // org\\aries\\Ariel.g:476:4: statementDecl
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_statementDecl_in_optionMember2791);
            statementDecl520=statementDecl();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl520.getTree());

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
    // org\\aries\\Ariel.g:481:1: participantDecl : 'participant' ^ Identifier participantBody ;
    public final ArielParser.participantDecl_return participantDecl() throws RecognitionException {
        ArielParser.participantDecl_return retval = new ArielParser.participantDecl_return();
        retval.start = input.LT(1);

        int participantDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal521=null;
        Token Identifier522=null;
        ArielParser.participantBody_return participantBody523 =null;


        Object string_literal521_tree=null;
        Object Identifier522_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return retval; }

            // org\\aries\\Ariel.g:482:2: ( 'participant' ^ Identifier participantBody )
            // org\\aries\\Ariel.g:482:4: 'participant' ^ Identifier participantBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal521=(Token)match(input,117,FOLLOW_117_in_participantDecl2806); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal521_tree = 
            (Object)adaptor.create(string_literal521)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal521_tree, root_0);
            }

            Identifier522=(Token)match(input,Identifier,FOLLOW_Identifier_in_participantDecl2809); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier522_tree = 
            (Object)adaptor.create(Identifier522)
            ;
            adaptor.addChild(root_0, Identifier522_tree);
            }

            pushFollow(FOLLOW_participantBody_in_participantDecl2811);
            participantBody523=participantBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, participantBody523.getTree());

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
    // org\\aries\\Ariel.g:485:1: participantBody : '{' ( participantMember )* '}' ;
    public final ArielParser.participantBody_return participantBody() throws RecognitionException {
        ArielParser.participantBody_return retval = new ArielParser.participantBody_return();
        retval.start = input.LT(1);

        int participantBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal524=null;
        Token char_literal526=null;
        ArielParser.participantMember_return participantMember525 =null;


        Object char_literal524_tree=null;
        Object char_literal526_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 79) ) { return retval; }

            // org\\aries\\Ariel.g:486:2: ( '{' ( participantMember )* '}' )
            // org\\aries\\Ariel.g:486:4: '{' ( participantMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal524=(Token)match(input,140,FOLLOW_140_in_participantBody2822); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal524_tree = 
            (Object)adaptor.create(char_literal524)
            ;
            adaptor.addChild(root_0, char_literal524_tree);
            }

            // org\\aries\\Ariel.g:486:8: ( participantMember )*
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
                case 118:
                case 121:
                case 126:
                case 130:
                    {
                    alt83=1;
                    }
                    break;

                }

                switch (alt83) {
            	case 1 :
            	    // org\\aries\\Ariel.g:486:9: participantMember
            	    {
            	    pushFollow(FOLLOW_participantMember_in_participantBody2825);
            	    participantMember525=participantMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, participantMember525.getTree());

            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);


            char_literal526=(Token)match(input,144,FOLLOW_144_in_participantBody2829); if (state.failed) return retval;
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
    // org\\aries\\Ariel.g:489:1: participantMember : ( assignmentDecl | receiveDecl | cacheDecl | persistDecl | scheduleDecl | methodDecl );
    public final ArielParser.participantMember_return participantMember() throws RecognitionException {
        ArielParser.participantMember_return retval = new ArielParser.participantMember_return();
        retval.start = input.LT(1);

        int participantMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl527 =null;

        ArielParser.receiveDecl_return receiveDecl528 =null;

        ArielParser.cacheDecl_return cacheDecl529 =null;

        ArielParser.persistDecl_return persistDecl530 =null;

        ArielParser.scheduleDecl_return scheduleDecl531 =null;

        ArielParser.methodDecl_return methodDecl532 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }

            // org\\aries\\Ariel.g:490:2: ( assignmentDecl | receiveDecl | cacheDecl | persistDecl | scheduleDecl | methodDecl )
            int alt84=6;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
                {
                alt84=1;
                }
                break;
            case 121:
                {
                alt84=2;
                }
                break;
            case 82:
                {
                alt84=3;
                }
                break;
            case 118:
                {
                alt84=4;
                }
                break;
            case 126:
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
                    // org\\aries\\Ariel.g:490:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_participantMember2841);
                    assignmentDecl527=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl527.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:491:4: receiveDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_receiveDecl_in_participantMember2846);
                    receiveDecl528=receiveDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveDecl528.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:492:4: cacheDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cacheDecl_in_participantMember2851);
                    cacheDecl529=cacheDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheDecl529.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:493:4: persistDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_persistDecl_in_participantMember2856);
                    persistDecl530=persistDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistDecl530.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:494:4: scheduleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_scheduleDecl_in_participantMember2861);
                    scheduleDecl531=scheduleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleDecl531.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\Ariel.g:495:4: methodDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_methodDecl_in_participantMember2866);
                    methodDecl532=methodDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodDecl532.getTree());

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
    // org\\aries\\Ariel.g:498:1: persistDecl : 'persist' ^ Identifier persistBody ;
    public final ArielParser.persistDecl_return persistDecl() throws RecognitionException {
        ArielParser.persistDecl_return retval = new ArielParser.persistDecl_return();
        retval.start = input.LT(1);

        int persistDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal533=null;
        Token Identifier534=null;
        ArielParser.persistBody_return persistBody535 =null;


        Object string_literal533_tree=null;
        Object Identifier534_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 81) ) { return retval; }

            // org\\aries\\Ariel.g:499:2: ( 'persist' ^ Identifier persistBody )
            // org\\aries\\Ariel.g:499:4: 'persist' ^ Identifier persistBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal533=(Token)match(input,118,FOLLOW_118_in_persistDecl2877); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal533_tree = 
            (Object)adaptor.create(string_literal533)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal533_tree, root_0);
            }

            Identifier534=(Token)match(input,Identifier,FOLLOW_Identifier_in_persistDecl2880); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier534_tree = 
            (Object)adaptor.create(Identifier534)
            ;
            adaptor.addChild(root_0, Identifier534_tree);
            }

            pushFollow(FOLLOW_persistBody_in_persistDecl2882);
            persistBody535=persistBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, persistBody535.getTree());

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
    // org\\aries\\Ariel.g:502:1: persistBody : '{' ( persistMember )* '}' ;
    public final ArielParser.persistBody_return persistBody() throws RecognitionException {
        ArielParser.persistBody_return retval = new ArielParser.persistBody_return();
        retval.start = input.LT(1);

        int persistBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal536=null;
        Token char_literal538=null;
        ArielParser.persistMember_return persistMember537 =null;


        Object char_literal536_tree=null;
        Object char_literal538_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 82) ) { return retval; }

            // org\\aries\\Ariel.g:503:2: ( '{' ( persistMember )* '}' )
            // org\\aries\\Ariel.g:503:4: '{' ( persistMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal536=(Token)match(input,140,FOLLOW_140_in_persistBody2893); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal536_tree = 
            (Object)adaptor.create(char_literal536)
            ;
            adaptor.addChild(root_0, char_literal536_tree);
            }

            // org\\aries\\Ariel.g:503:8: ( persistMember )*
            loop85:
            do {
                int alt85=2;
                switch ( input.LA(1) ) {
                case 76:
                case 99:
                case 100:
                case 104:
                case 105:
                case 130:
                    {
                    alt85=1;
                    }
                    break;

                }

                switch (alt85) {
            	case 1 :
            	    // org\\aries\\Ariel.g:503:9: persistMember
            	    {
            	    pushFollow(FOLLOW_persistMember_in_persistBody2896);
            	    persistMember537=persistMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistMember537.getTree());

            	    }
            	    break;

            	default :
            	    break loop85;
                }
            } while (true);


            char_literal538=(Token)match(input,144,FOLLOW_144_in_persistBody2900); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal538_tree = 
            (Object)adaptor.create(char_literal538)
            ;
            adaptor.addChild(root_0, char_literal538_tree);
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
    // org\\aries\\Ariel.g:506:1: persistMember : ( assignmentDecl | itemsDecl );
    public final ArielParser.persistMember_return persistMember() throws RecognitionException {
        ArielParser.persistMember_return retval = new ArielParser.persistMember_return();
        retval.start = input.LT(1);

        int persistMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl539 =null;

        ArielParser.itemsDecl_return itemsDecl540 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 83) ) { return retval; }

            // org\\aries\\Ariel.g:507:2: ( assignmentDecl | itemsDecl )
            int alt86=2;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
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
                    // org\\aries\\Ariel.g:507:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_persistMember2911);
                    assignmentDecl539=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl539.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:508:4: itemsDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_itemsDecl_in_persistMember2916);
                    itemsDecl540=itemsDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, itemsDecl540.getTree());

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


    public static class networkDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "networkDecl"
    // org\\aries\\Ariel.g:511:1: networkDecl : 'network' ^ Identifier networkBody ;
    public final ArielParser.networkDecl_return networkDecl() throws RecognitionException {
        ArielParser.networkDecl_return retval = new ArielParser.networkDecl_return();
        retval.start = input.LT(1);

        int networkDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal541=null;
        Token Identifier542=null;
        ArielParser.networkBody_return networkBody543 =null;


        Object string_literal541_tree=null;
        Object Identifier542_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 84) ) { return retval; }

            // org\\aries\\Ariel.g:512:2: ( 'network' ^ Identifier networkBody )
            // org\\aries\\Ariel.g:512:4: 'network' ^ Identifier networkBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal541=(Token)match(input,114,FOLLOW_114_in_networkDecl2927); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal541_tree = 
            (Object)adaptor.create(string_literal541)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal541_tree, root_0);
            }

            Identifier542=(Token)match(input,Identifier,FOLLOW_Identifier_in_networkDecl2930); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier542_tree = 
            (Object)adaptor.create(Identifier542)
            ;
            adaptor.addChild(root_0, Identifier542_tree);
            }

            pushFollow(FOLLOW_networkBody_in_networkDecl2932);
            networkBody543=networkBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, networkBody543.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 84, networkDecl_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "networkDecl"


    public static class networkBody_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "networkBody"
    // org\\aries\\Ariel.g:515:1: networkBody : '{' ( networkMember )* '}' ;
    public final ArielParser.networkBody_return networkBody() throws RecognitionException {
        ArielParser.networkBody_return retval = new ArielParser.networkBody_return();
        retval.start = input.LT(1);

        int networkBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal544=null;
        Token char_literal546=null;
        ArielParser.networkMember_return networkMember545 =null;


        Object char_literal544_tree=null;
        Object char_literal546_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 85) ) { return retval; }

            // org\\aries\\Ariel.g:516:2: ( '{' ( networkMember )* '}' )
            // org\\aries\\Ariel.g:516:4: '{' ( networkMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal544=(Token)match(input,140,FOLLOW_140_in_networkBody2943); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal544_tree = 
            (Object)adaptor.create(char_literal544)
            ;
            adaptor.addChild(root_0, char_literal544_tree);
            }

            // org\\aries\\Ariel.g:516:8: ( networkMember )*
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
                case 117:
                case 118:
                case 125:
                case 126:
                case 130:
                    {
                    alt87=1;
                    }
                    break;

                }

                switch (alt87) {
            	case 1 :
            	    // org\\aries\\Ariel.g:516:8: networkMember
            	    {
            	    pushFollow(FOLLOW_networkMember_in_networkBody2945);
            	    networkMember545=networkMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, networkMember545.getTree());

            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);


            char_literal546=(Token)match(input,144,FOLLOW_144_in_networkBody2948); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal546_tree = 
            (Object)adaptor.create(char_literal546)
            ;
            adaptor.addChild(root_0, char_literal546_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 85, networkBody_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "networkBody"


    public static class networkMember_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "networkMember"
    // org\\aries\\Ariel.g:519:1: networkMember : ( assignmentDecl | roleDecl | groupDecl | channelDecl | participantDecl | cacheDecl | persistDecl | scheduleDecl );
    public final ArielParser.networkMember_return networkMember() throws RecognitionException {
        ArielParser.networkMember_return retval = new ArielParser.networkMember_return();
        retval.start = input.LT(1);

        int networkMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl547 =null;

        ArielParser.roleDecl_return roleDecl548 =null;

        ArielParser.groupDecl_return groupDecl549 =null;

        ArielParser.channelDecl_return channelDecl550 =null;

        ArielParser.participantDecl_return participantDecl551 =null;

        ArielParser.cacheDecl_return cacheDecl552 =null;

        ArielParser.persistDecl_return persistDecl553 =null;

        ArielParser.scheduleDecl_return scheduleDecl554 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 86) ) { return retval; }

            // org\\aries\\Ariel.g:520:2: ( assignmentDecl | roleDecl | groupDecl | channelDecl | participantDecl | cacheDecl | persistDecl | scheduleDecl )
            int alt88=8;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
                {
                alt88=1;
                }
                break;
            case 125:
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
            case 117:
                {
                alt88=5;
                }
                break;
            case 82:
                {
                alt88=6;
                }
                break;
            case 118:
                {
                alt88=7;
                }
                break;
            case 126:
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
                    // org\\aries\\Ariel.g:520:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_networkMember2960);
                    assignmentDecl547=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl547.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:521:4: roleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_roleDecl_in_networkMember2965);
                    roleDecl548=roleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, roleDecl548.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:522:4: groupDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_groupDecl_in_networkMember2970);
                    groupDecl549=groupDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, groupDecl549.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:523:4: channelDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_channelDecl_in_networkMember2975);
                    channelDecl550=channelDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, channelDecl550.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:524:4: participantDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_participantDecl_in_networkMember2980);
                    participantDecl551=participantDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, participantDecl551.getTree());

                    }
                    break;
                case 6 :
                    // org\\aries\\Ariel.g:525:4: cacheDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_cacheDecl_in_networkMember2985);
                    cacheDecl552=cacheDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cacheDecl552.getTree());

                    }
                    break;
                case 7 :
                    // org\\aries\\Ariel.g:526:4: persistDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_persistDecl_in_networkMember2990);
                    persistDecl553=persistDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, persistDecl553.getTree());

                    }
                    break;
                case 8 :
                    // org\\aries\\Ariel.g:527:4: scheduleDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_scheduleDecl_in_networkMember2995);
                    scheduleDecl554=scheduleDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleDecl554.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 86, networkMember_StartIndex); }

        }
        return retval;
    }
    // $ANTLR end "networkMember"


    public static class receiveDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "receiveDecl"
    // org\\aries\\Ariel.g:530:1: receiveDecl : 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' ) ;
    public final ArielParser.receiveDecl_return receiveDecl() throws RecognitionException {
        ArielParser.receiveDecl_return retval = new ArielParser.receiveDecl_return();
        retval.start = input.LT(1);

        int receiveDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal555=null;
        Token Identifier556=null;
        Token char_literal560=null;
        ArielParser.formalParametersSignature_return formalParametersSignature557 =null;

        ArielParser.throwsBody_return throwsBody558 =null;

        ArielParser.receiveBody_return receiveBody559 =null;


        Object string_literal555_tree=null;
        Object Identifier556_tree=null;
        Object char_literal560_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 87) ) { return retval; }

            // org\\aries\\Ariel.g:531:2: ( 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' ) )
            // org\\aries\\Ariel.g:531:4: 'receive' ^ Identifier formalParametersSignature ( throwsBody )? ( receiveBody | ';' )
            {
            root_0 = (Object)adaptor.nil();


            string_literal555=(Token)match(input,121,FOLLOW_121_in_receiveDecl3006); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal555_tree = 
            (Object)adaptor.create(string_literal555)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal555_tree, root_0);
            }

            Identifier556=(Token)match(input,Identifier,FOLLOW_Identifier_in_receiveDecl3009); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier556_tree = 
            (Object)adaptor.create(Identifier556)
            ;
            adaptor.addChild(root_0, Identifier556_tree);
            }

            pushFollow(FOLLOW_formalParametersSignature_in_receiveDecl3011);
            formalParametersSignature557=formalParametersSignature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParametersSignature557.getTree());

            // org\\aries\\Ariel.g:531:52: ( throwsBody )?
            int alt89=2;
            switch ( input.LA(1) ) {
                case 136:
                    {
                    alt89=1;
                    }
                    break;
            }

            switch (alt89) {
                case 1 :
                    // org\\aries\\Ariel.g:531:52: throwsBody
                    {
                    pushFollow(FOLLOW_throwsBody_in_receiveDecl3013);
                    throwsBody558=throwsBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsBody558.getTree());

                    }
                    break;

            }


            // org\\aries\\Ariel.g:531:64: ( receiveBody | ';' )
            int alt90=2;
            switch ( input.LA(1) ) {
            case 140:
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
                    // org\\aries\\Ariel.g:531:65: receiveBody
                    {
                    pushFollow(FOLLOW_receiveBody_in_receiveDecl3017);
                    receiveBody559=receiveBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveBody559.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:531:79: ';'
                    {
                    char_literal560=(Token)match(input,65,FOLLOW_65_in_receiveDecl3021); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal560_tree = 
                    (Object)adaptor.create(char_literal560)
                    ;
                    adaptor.addChild(root_0, char_literal560_tree);
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
    // org\\aries\\Ariel.g:534:1: receiveBody : '{' ( receiveMember )* '}' ;
    public final ArielParser.receiveBody_return receiveBody() throws RecognitionException {
        ArielParser.receiveBody_return retval = new ArielParser.receiveBody_return();
        retval.start = input.LT(1);

        int receiveBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal561=null;
        Token char_literal563=null;
        ArielParser.receiveMember_return receiveMember562 =null;


        Object char_literal561_tree=null;
        Object char_literal563_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 88) ) { return retval; }

            // org\\aries\\Ariel.g:535:2: ( '{' ( receiveMember )* '}' )
            // org\\aries\\Ariel.g:535:4: '{' ( receiveMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal561=(Token)match(input,140,FOLLOW_140_in_receiveBody3033); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal561_tree = 
            (Object)adaptor.create(char_literal561)
            ;
            adaptor.addChild(root_0, char_literal561_tree);
            }

            // org\\aries\\Ariel.g:535:8: ( receiveMember )*
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
                case 115:
                case 116:
                case 119:
                case 122:
                case 124:
                case 128:
                case 130:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt91=1;
                    }
                    break;

                }

                switch (alt91) {
            	case 1 :
            	    // org\\aries\\Ariel.g:535:9: receiveMember
            	    {
            	    pushFollow(FOLLOW_receiveMember_in_receiveBody3036);
            	    receiveMember562=receiveMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveMember562.getTree());

            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);


            char_literal563=(Token)match(input,144,FOLLOW_144_in_receiveBody3040); if (state.failed) return retval;
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
    // org\\aries\\Ariel.g:538:1: receiveMember : ( assignmentDecl | optionDecl | executeDecl | listenDecl | statementDecl );
    public final ArielParser.receiveMember_return receiveMember() throws RecognitionException {
        ArielParser.receiveMember_return retval = new ArielParser.receiveMember_return();
        retval.start = input.LT(1);

        int receiveMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl564 =null;

        ArielParser.optionDecl_return optionDecl565 =null;

        ArielParser.executeDecl_return executeDecl566 =null;

        ArielParser.listenDecl_return listenDecl567 =null;

        ArielParser.statementDecl_return statementDecl568 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 89) ) { return retval; }

            // org\\aries\\Ariel.g:539:2: ( assignmentDecl | optionDecl | executeDecl | listenDecl | statementDecl )
            int alt92=5;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
                {
                alt92=1;
                }
                break;
            case 116:
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
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
                {
                alt92=5;
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
                    // org\\aries\\Ariel.g:539:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_receiveMember3051);
                    assignmentDecl564=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl564.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:540:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_receiveMember3056);
                    optionDecl565=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl565.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:541:4: executeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_executeDecl_in_receiveMember3061);
                    executeDecl566=executeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executeDecl566.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:542:4: listenDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_listenDecl_in_receiveMember3066);
                    listenDecl567=listenDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, listenDecl567.getTree());

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:543:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_receiveMember3071);
                    statementDecl568=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl568.getTree());

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
    // org\\aries\\Ariel.g:548:1: roleDecl : 'role' ^ Identifier roleBody ;
    public final ArielParser.roleDecl_return roleDecl() throws RecognitionException {
        ArielParser.roleDecl_return retval = new ArielParser.roleDecl_return();
        retval.start = input.LT(1);

        int roleDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal569=null;
        Token Identifier570=null;
        ArielParser.roleBody_return roleBody571 =null;


        Object string_literal569_tree=null;
        Object Identifier570_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 90) ) { return retval; }

            // org\\aries\\Ariel.g:549:2: ( 'role' ^ Identifier roleBody )
            // org\\aries\\Ariel.g:549:4: 'role' ^ Identifier roleBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal569=(Token)match(input,125,FOLLOW_125_in_roleDecl3084); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal569_tree = 
            (Object)adaptor.create(string_literal569)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal569_tree, root_0);
            }

            Identifier570=(Token)match(input,Identifier,FOLLOW_Identifier_in_roleDecl3087); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier570_tree = 
            (Object)adaptor.create(Identifier570)
            ;
            adaptor.addChild(root_0, Identifier570_tree);
            }

            pushFollow(FOLLOW_roleBody_in_roleDecl3089);
            roleBody571=roleBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, roleBody571.getTree());

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
    // org\\aries\\Ariel.g:552:1: roleBody : '{' ( roleMember )* '}' ;
    public final ArielParser.roleBody_return roleBody() throws RecognitionException {
        ArielParser.roleBody_return retval = new ArielParser.roleBody_return();
        retval.start = input.LT(1);

        int roleBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal572=null;
        Token char_literal574=null;
        ArielParser.roleMember_return roleMember573 =null;


        Object char_literal572_tree=null;
        Object char_literal574_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 91) ) { return retval; }

            // org\\aries\\Ariel.g:553:2: ( '{' ( roleMember )* '}' )
            // org\\aries\\Ariel.g:553:4: '{' ( roleMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal572=(Token)match(input,140,FOLLOW_140_in_roleBody3100); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal572_tree = 
            (Object)adaptor.create(char_literal572)
            ;
            adaptor.addChild(root_0, char_literal572_tree);
            }

            // org\\aries\\Ariel.g:553:8: ( roleMember )*
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
            	    // org\\aries\\Ariel.g:553:9: roleMember
            	    {
            	    pushFollow(FOLLOW_roleMember_in_roleBody3103);
            	    roleMember573=roleMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, roleMember573.getTree());

            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);


            char_literal574=(Token)match(input,144,FOLLOW_144_in_roleBody3107); if (state.failed) return retval;
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
    // org\\aries\\Ariel.g:556:1: roleMember : ';' ;
    public final ArielParser.roleMember_return roleMember() throws RecognitionException {
        ArielParser.roleMember_return retval = new ArielParser.roleMember_return();
        retval.start = input.LT(1);

        int roleMember_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal575=null;

        Object char_literal575_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 92) ) { return retval; }

            // org\\aries\\Ariel.g:557:2: ( ';' )
            // org\\aries\\Ariel.g:557:4: ';'
            {
            root_0 = (Object)adaptor.nil();


            char_literal575=(Token)match(input,65,FOLLOW_65_in_roleMember3118); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal575_tree = 
            (Object)adaptor.create(char_literal575)
            ;
            adaptor.addChild(root_0, char_literal575_tree);
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
    // org\\aries\\Ariel.g:560:1: scheduleDecl : 'schedule' ^ Identifier scheduleBody ;
    public final ArielParser.scheduleDecl_return scheduleDecl() throws RecognitionException {
        ArielParser.scheduleDecl_return retval = new ArielParser.scheduleDecl_return();
        retval.start = input.LT(1);

        int scheduleDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal576=null;
        Token Identifier577=null;
        ArielParser.scheduleBody_return scheduleBody578 =null;


        Object string_literal576_tree=null;
        Object Identifier577_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 93) ) { return retval; }

            // org\\aries\\Ariel.g:561:2: ( 'schedule' ^ Identifier scheduleBody )
            // org\\aries\\Ariel.g:561:4: 'schedule' ^ Identifier scheduleBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal576=(Token)match(input,126,FOLLOW_126_in_scheduleDecl3129); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal576_tree = 
            (Object)adaptor.create(string_literal576)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal576_tree, root_0);
            }

            Identifier577=(Token)match(input,Identifier,FOLLOW_Identifier_in_scheduleDecl3132); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier577_tree = 
            (Object)adaptor.create(Identifier577)
            ;
            adaptor.addChild(root_0, Identifier577_tree);
            }

            pushFollow(FOLLOW_scheduleBody_in_scheduleDecl3134);
            scheduleBody578=scheduleBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleBody578.getTree());

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
    // org\\aries\\Ariel.g:564:1: scheduleBody : '{' ( scheduleMember )* '}' ;
    public final ArielParser.scheduleBody_return scheduleBody() throws RecognitionException {
        ArielParser.scheduleBody_return retval = new ArielParser.scheduleBody_return();
        retval.start = input.LT(1);

        int scheduleBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal579=null;
        Token char_literal581=null;
        ArielParser.scheduleMember_return scheduleMember580 =null;


        Object char_literal579_tree=null;
        Object char_literal581_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 94) ) { return retval; }

            // org\\aries\\Ariel.g:565:2: ( '{' ( scheduleMember )* '}' )
            // org\\aries\\Ariel.g:565:4: '{' ( scheduleMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal579=(Token)match(input,140,FOLLOW_140_in_scheduleBody3145); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal579_tree = 
            (Object)adaptor.create(char_literal579)
            ;
            adaptor.addChild(root_0, char_literal579_tree);
            }

            // org\\aries\\Ariel.g:565:8: ( scheduleMember )*
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
                case 119:
                case 121:
                case 122:
                case 128:
                case 130:
                case 135:
                    {
                    alt94=1;
                    }
                    break;

                }

                switch (alt94) {
            	case 1 :
            	    // org\\aries\\Ariel.g:565:9: scheduleMember
            	    {
            	    pushFollow(FOLLOW_scheduleMember_in_scheduleBody3148);
            	    scheduleMember580=scheduleMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, scheduleMember580.getTree());

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            char_literal581=(Token)match(input,144,FOLLOW_144_in_scheduleBody3152); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal581_tree = 
            (Object)adaptor.create(char_literal581)
            ;
            adaptor.addChild(root_0, char_literal581_tree);
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
    // org\\aries\\Ariel.g:568:1: scheduleMember : ( assignmentDecl | receiveDecl | invokeDecl | schedulableCommandDecl );
    public final ArielParser.scheduleMember_return scheduleMember() throws RecognitionException {
        ArielParser.scheduleMember_return retval = new ArielParser.scheduleMember_return();
        retval.start = input.LT(1);

        int scheduleMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.assignmentDecl_return assignmentDecl582 =null;

        ArielParser.receiveDecl_return receiveDecl583 =null;

        ArielParser.invokeDecl_return invokeDecl584 =null;

        ArielParser.schedulableCommandDecl_return schedulableCommandDecl585 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 95) ) { return retval; }

            // org\\aries\\Ariel.g:569:2: ( assignmentDecl | receiveDecl | invokeDecl | schedulableCommandDecl )
            int alt95=4;
            switch ( input.LA(1) ) {
            case 76:
            case 99:
            case 100:
            case 105:
            case 130:
                {
                alt95=1;
                }
                break;
            case 121:
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
            case 119:
            case 122:
            case 128:
            case 135:
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
                    // org\\aries\\Ariel.g:569:4: assignmentDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assignmentDecl_in_scheduleMember3164);
                    assignmentDecl582=assignmentDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentDecl582.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:570:4: receiveDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_receiveDecl_in_scheduleMember3169);
                    receiveDecl583=receiveDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, receiveDecl583.getTree());

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:571:4: invokeDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_invokeDecl_in_scheduleMember3174);
                    invokeDecl584=invokeDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, invokeDecl584.getTree());

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:572:4: schedulableCommandDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_schedulableCommandDecl_in_scheduleMember3179);
                    schedulableCommandDecl585=schedulableCommandDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, schedulableCommandDecl585.getTree());

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
    // org\\aries\\Ariel.g:576:1: schedulableCommandDecl : ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | 'throw' ^ Identifier ( formalParameters )? ';' | ';' );
    public final ArielParser.schedulableCommandDecl_return schedulableCommandDecl() throws RecognitionException {
        ArielParser.schedulableCommandDecl_return retval = new ArielParser.schedulableCommandDecl_return();
        retval.start = input.LT(1);

        int schedulableCommandDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal586=null;
        Token char_literal587=null;
        Token string_literal588=null;
        Token Identifier589=null;
        Token char_literal591=null;
        Token string_literal592=null;
        Token Identifier593=null;
        Token char_literal595=null;
        Token string_literal596=null;
        Token char_literal599=null;
        Token string_literal600=null;
        Token Identifier601=null;
        Token char_literal603=null;
        Token char_literal604=null;
        ArielParser.formalParameters_return formalParameters590 =null;

        ArielParser.formalParameters_return formalParameters594 =null;

        ArielParser.qualifiedName_return qualifiedName597 =null;

        ArielParser.formalParameters_return formalParameters598 =null;

        ArielParser.formalParameters_return formalParameters602 =null;


        Object string_literal586_tree=null;
        Object char_literal587_tree=null;
        Object string_literal588_tree=null;
        Object Identifier589_tree=null;
        Object char_literal591_tree=null;
        Object string_literal592_tree=null;
        Object Identifier593_tree=null;
        Object char_literal595_tree=null;
        Object string_literal596_tree=null;
        Object char_literal599_tree=null;
        Object string_literal600_tree=null;
        Object Identifier601_tree=null;
        Object char_literal603_tree=null;
        Object char_literal604_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 96) ) { return retval; }

            // org\\aries\\Ariel.g:577:2: ( 'end' ^ ';' | 'post' ^ Identifier ( formalParameters )? ';' | 'reply' ^ Identifier ( formalParameters )? ';' | 'send' ^ qualifiedName ( formalParameters )? ';' | 'throw' ^ Identifier ( formalParameters )? ';' | ';' )
            int alt100=6;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt100=1;
                }
                break;
            case 119:
                {
                alt100=2;
                }
                break;
            case 122:
                {
                alt100=3;
                }
                break;
            case 128:
                {
                alt100=4;
                }
                break;
            case 135:
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
                    // org\\aries\\Ariel.g:577:4: 'end' ^ ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal586=(Token)match(input,93,FOLLOW_93_in_schedulableCommandDecl3192); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal586_tree = 
                    (Object)adaptor.create(string_literal586)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal586_tree, root_0);
                    }

                    char_literal587=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3195); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal587_tree = 
                    (Object)adaptor.create(char_literal587)
                    ;
                    adaptor.addChild(root_0, char_literal587_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:578:4: 'post' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal588=(Token)match(input,119,FOLLOW_119_in_schedulableCommandDecl3200); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal588_tree = 
                    (Object)adaptor.create(string_literal588)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal588_tree, root_0);
                    }

                    Identifier589=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3203); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier589_tree = 
                    (Object)adaptor.create(Identifier589)
                    ;
                    adaptor.addChild(root_0, Identifier589_tree);
                    }

                    // org\\aries\\Ariel.g:578:23: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:578:23: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3205);
                            formalParameters590=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters590.getTree());

                            }
                            break;

                    }


                    char_literal591=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3208); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal591_tree = 
                    (Object)adaptor.create(char_literal591)
                    ;
                    adaptor.addChild(root_0, char_literal591_tree);
                    }

                    }
                    break;
                case 3 :
                    // org\\aries\\Ariel.g:579:4: 'reply' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal592=(Token)match(input,122,FOLLOW_122_in_schedulableCommandDecl3213); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal592_tree = 
                    (Object)adaptor.create(string_literal592)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal592_tree, root_0);
                    }

                    Identifier593=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3216); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier593_tree = 
                    (Object)adaptor.create(Identifier593)
                    ;
                    adaptor.addChild(root_0, Identifier593_tree);
                    }

                    // org\\aries\\Ariel.g:579:24: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:579:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3218);
                            formalParameters594=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters594.getTree());

                            }
                            break;

                    }


                    char_literal595=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3221); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal595_tree = 
                    (Object)adaptor.create(char_literal595)
                    ;
                    adaptor.addChild(root_0, char_literal595_tree);
                    }

                    }
                    break;
                case 4 :
                    // org\\aries\\Ariel.g:580:4: 'send' ^ qualifiedName ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal596=(Token)match(input,128,FOLLOW_128_in_schedulableCommandDecl3226); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal596_tree = 
                    (Object)adaptor.create(string_literal596)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal596_tree, root_0);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_schedulableCommandDecl3229);
                    qualifiedName597=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName597.getTree());

                    // org\\aries\\Ariel.g:580:26: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:580:26: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3231);
                            formalParameters598=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters598.getTree());

                            }
                            break;

                    }


                    char_literal599=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3234); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal599_tree = 
                    (Object)adaptor.create(char_literal599)
                    ;
                    adaptor.addChild(root_0, char_literal599_tree);
                    }

                    }
                    break;
                case 5 :
                    // org\\aries\\Ariel.g:581:4: 'throw' ^ Identifier ( formalParameters )? ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal600=(Token)match(input,135,FOLLOW_135_in_schedulableCommandDecl3239); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal600_tree = 
                    (Object)adaptor.create(string_literal600)
                    ;
                    root_0 = (Object)adaptor.becomeRoot(string_literal600_tree, root_0);
                    }

                    Identifier601=(Token)match(input,Identifier,FOLLOW_Identifier_in_schedulableCommandDecl3242); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier601_tree = 
                    (Object)adaptor.create(Identifier601)
                    ;
                    adaptor.addChild(root_0, Identifier601_tree);
                    }

                    // org\\aries\\Ariel.g:581:24: ( formalParameters )?
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
                            // org\\aries\\Ariel.g:581:24: formalParameters
                            {
                            pushFollow(FOLLOW_formalParameters_in_schedulableCommandDecl3244);
                            formalParameters602=formalParameters();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameters602.getTree());

                            }
                            break;

                    }


                    char_literal603=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3247); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal603_tree = 
                    (Object)adaptor.create(char_literal603)
                    ;
                    adaptor.addChild(root_0, char_literal603_tree);
                    }

                    }
                    break;
                case 6 :
                    // org\\aries\\Ariel.g:582:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();


                    char_literal604=(Token)match(input,65,FOLLOW_65_in_schedulableCommandDecl3252); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal604_tree = 
                    (Object)adaptor.create(char_literal604)
                    ;
                    adaptor.addChild(root_0, char_literal604_tree);
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
    // org\\aries\\Ariel.g:585:1: throwsBody : 'throws' ^ throwsList ;
    public final ArielParser.throwsBody_return throwsBody() throws RecognitionException {
        ArielParser.throwsBody_return retval = new ArielParser.throwsBody_return();
        retval.start = input.LT(1);

        int throwsBody_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal605=null;
        ArielParser.throwsList_return throwsList606 =null;


        Object string_literal605_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 97) ) { return retval; }

            // org\\aries\\Ariel.g:586:2: ( 'throws' ^ throwsList )
            // org\\aries\\Ariel.g:586:4: 'throws' ^ throwsList
            {
            root_0 = (Object)adaptor.nil();


            string_literal605=(Token)match(input,136,FOLLOW_136_in_throwsBody3264); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal605_tree = 
            (Object)adaptor.create(string_literal605)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal605_tree, root_0);
            }

            pushFollow(FOLLOW_throwsList_in_throwsBody3267);
            throwsList606=throwsList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsList606.getTree());

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
    // org\\aries\\Ariel.g:589:1: throwsList : ( throwsListDecls )? ;
    public final ArielParser.throwsList_return throwsList() throws RecognitionException {
        ArielParser.throwsList_return retval = new ArielParser.throwsList_return();
        retval.start = input.LT(1);

        int throwsList_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.throwsListDecls_return throwsListDecls607 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 98) ) { return retval; }

            // org\\aries\\Ariel.g:590:2: ( ( throwsListDecls )? )
            // org\\aries\\Ariel.g:590:4: ( throwsListDecls )?
            {
            root_0 = (Object)adaptor.nil();


            // org\\aries\\Ariel.g:590:4: ( throwsListDecls )?
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
                    // org\\aries\\Ariel.g:590:4: throwsListDecls
                    {
                    pushFollow(FOLLOW_throwsListDecls_in_throwsList3279);
                    throwsListDecls607=throwsListDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDecls607.getTree());

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
    // org\\aries\\Ariel.g:593:1: throwsListDecls : throwsListDeclsRest ;
    public final ArielParser.throwsListDecls_return throwsListDecls() throws RecognitionException {
        ArielParser.throwsListDecls_return retval = new ArielParser.throwsListDecls_return();
        retval.start = input.LT(1);

        int throwsListDecls_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.throwsListDeclsRest_return throwsListDeclsRest608 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 99) ) { return retval; }

            // org\\aries\\Ariel.g:594:2: ( throwsListDeclsRest )
            // org\\aries\\Ariel.g:594:4: throwsListDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_throwsListDeclsRest_in_throwsListDecls3292);
            throwsListDeclsRest608=throwsListDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDeclsRest608.getTree());

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
    // org\\aries\\Ariel.g:597:1: throwsListDeclsRest : Identifier ( ',' throwsListDeclsRest )? ;
    public final ArielParser.throwsListDeclsRest_return throwsListDeclsRest() throws RecognitionException {
        ArielParser.throwsListDeclsRest_return retval = new ArielParser.throwsListDeclsRest_return();
        retval.start = input.LT(1);

        int throwsListDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier609=null;
        Token char_literal610=null;
        ArielParser.throwsListDeclsRest_return throwsListDeclsRest611 =null;


        Object Identifier609_tree=null;
        Object char_literal610_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 100) ) { return retval; }

            // org\\aries\\Ariel.g:598:2: ( Identifier ( ',' throwsListDeclsRest )? )
            // org\\aries\\Ariel.g:598:4: Identifier ( ',' throwsListDeclsRest )?
            {
            root_0 = (Object)adaptor.nil();


            Identifier609=(Token)match(input,Identifier,FOLLOW_Identifier_in_throwsListDeclsRest3304); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier609_tree = 
            (Object)adaptor.create(Identifier609)
            ;
            adaptor.addChild(root_0, Identifier609_tree);
            }

            // org\\aries\\Ariel.g:598:15: ( ',' throwsListDeclsRest )?
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
                    // org\\aries\\Ariel.g:598:16: ',' throwsListDeclsRest
                    {
                    char_literal610=(Token)match(input,57,FOLLOW_57_in_throwsListDeclsRest3307); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal610_tree = 
                    (Object)adaptor.create(char_literal610)
                    ;
                    adaptor.addChild(root_0, char_literal610_tree);
                    }

                    pushFollow(FOLLOW_throwsListDeclsRest_in_throwsListDeclsRest3309);
                    throwsListDeclsRest611=throwsListDeclsRest();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwsListDeclsRest611.getTree());

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
    // org\\aries\\Ariel.g:601:1: timeoutDecl : 'timeout' ^ ':' timeoutBody ;
    public final ArielParser.timeoutDecl_return timeoutDecl() throws RecognitionException {
        ArielParser.timeoutDecl_return retval = new ArielParser.timeoutDecl_return();
        retval.start = input.LT(1);

        int timeoutDecl_StartIndex = input.index();

        Object root_0 = null;

        Token string_literal612=null;
        Token char_literal613=null;
        ArielParser.timeoutBody_return timeoutBody614 =null;


        Object string_literal612_tree=null;
        Object char_literal613_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 101) ) { return retval; }

            // org\\aries\\Ariel.g:602:2: ( 'timeout' ^ ':' timeoutBody )
            // org\\aries\\Ariel.g:602:4: 'timeout' ^ ':' timeoutBody
            {
            root_0 = (Object)adaptor.nil();


            string_literal612=(Token)match(input,137,FOLLOW_137_in_timeoutDecl3323); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal612_tree = 
            (Object)adaptor.create(string_literal612)
            ;
            root_0 = (Object)adaptor.becomeRoot(string_literal612_tree, root_0);
            }

            char_literal613=(Token)match(input,64,FOLLOW_64_in_timeoutDecl3326); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal613_tree = 
            (Object)adaptor.create(char_literal613)
            ;
            adaptor.addChild(root_0, char_literal613_tree);
            }

            pushFollow(FOLLOW_timeoutBody_in_timeoutDecl3328);
            timeoutBody614=timeoutBody();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutBody614.getTree());

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
    // org\\aries\\Ariel.g:605:1: timeoutBody : '{' ( timeoutMember )* '}' ;
    public final ArielParser.timeoutBody_return timeoutBody() throws RecognitionException {
        ArielParser.timeoutBody_return retval = new ArielParser.timeoutBody_return();
        retval.start = input.LT(1);

        int timeoutBody_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal615=null;
        Token char_literal617=null;
        ArielParser.timeoutMember_return timeoutMember616 =null;


        Object char_literal615_tree=null;
        Object char_literal617_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 102) ) { return retval; }

            // org\\aries\\Ariel.g:606:2: ( '{' ( timeoutMember )* '}' )
            // org\\aries\\Ariel.g:606:4: '{' ( timeoutMember )* '}'
            {
            root_0 = (Object)adaptor.nil();


            char_literal615=(Token)match(input,140,FOLLOW_140_in_timeoutBody3340); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal615_tree = 
            (Object)adaptor.create(char_literal615)
            ;
            adaptor.addChild(root_0, char_literal615_tree);
            }

            // org\\aries\\Ariel.g:606:8: ( timeoutMember )*
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
                case 103:
                case 108:
                case 115:
                case 116:
                case 119:
                case 122:
                case 124:
                case 128:
                case 131:
                case 135:
                case 139:
                case 140:
                case 145:
                    {
                    alt103=1;
                    }
                    break;

                }

                switch (alt103) {
            	case 1 :
            	    // org\\aries\\Ariel.g:606:9: timeoutMember
            	    {
            	    pushFollow(FOLLOW_timeoutMember_in_timeoutBody3343);
            	    timeoutMember616=timeoutMember();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeoutMember616.getTree());

            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);


            char_literal617=(Token)match(input,144,FOLLOW_144_in_timeoutBody3347); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal617_tree = 
            (Object)adaptor.create(char_literal617)
            ;
            adaptor.addChild(root_0, char_literal617_tree);
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
    // org\\aries\\Ariel.g:609:1: timeoutMember : ( optionDecl | statementDecl );
    public final ArielParser.timeoutMember_return timeoutMember() throws RecognitionException {
        ArielParser.timeoutMember_return retval = new ArielParser.timeoutMember_return();
        retval.start = input.LT(1);

        int timeoutMember_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.optionDecl_return optionDecl618 =null;

        ArielParser.statementDecl_return statementDecl619 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 103) ) { return retval; }

            // org\\aries\\Ariel.g:610:2: ( optionDecl | statementDecl )
            int alt104=2;
            switch ( input.LA(1) ) {
            case 116:
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
            case 90:
            case 91:
            case 93:
            case 95:
            case 96:
            case 98:
            case 102:
            case 103:
            case 108:
            case 115:
            case 119:
            case 122:
            case 124:
            case 128:
            case 131:
            case 135:
            case 139:
            case 140:
            case 145:
                {
                alt104=2;
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
                    // org\\aries\\Ariel.g:610:4: optionDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_optionDecl_in_timeoutMember3358);
                    optionDecl618=optionDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, optionDecl618.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:611:4: statementDecl
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_statementDecl_in_timeoutMember3363);
                    statementDecl619=statementDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementDecl619.getTree());

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
    // org\\aries\\Ariel.g:618:1: typeRef : Identifier ':' Identifier ;
    public final ArielParser.typeRef_return typeRef() throws RecognitionException {
        ArielParser.typeRef_return retval = new ArielParser.typeRef_return();
        retval.start = input.LT(1);

        int typeRef_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier620=null;
        Token char_literal621=null;
        Token Identifier622=null;

        Object Identifier620_tree=null;
        Object char_literal621_tree=null;
        Object Identifier622_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 104) ) { return retval; }

            // org\\aries\\Ariel.g:619:2: ( Identifier ':' Identifier )
            // org\\aries\\Ariel.g:619:4: Identifier ':' Identifier
            {
            root_0 = (Object)adaptor.nil();


            Identifier620=(Token)match(input,Identifier,FOLLOW_Identifier_in_typeRef3378); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier620_tree = 
            (Object)adaptor.create(Identifier620)
            ;
            adaptor.addChild(root_0, Identifier620_tree);
            }

            char_literal621=(Token)match(input,64,FOLLOW_64_in_typeRef3380); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal621_tree = 
            (Object)adaptor.create(char_literal621)
            ;
            adaptor.addChild(root_0, char_literal621_tree);
            }

            Identifier622=(Token)match(input,Identifier,FOLLOW_Identifier_in_typeRef3382); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier622_tree = 
            (Object)adaptor.create(Identifier622)
            ;
            adaptor.addChild(root_0, Identifier622_tree);
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
    // org\\aries\\Ariel.g:634:1: formalParameters : '(' ( formalParameterDecls )? ')' ;
    public final ArielParser.formalParameters_return formalParameters() throws RecognitionException {
        ArielParser.formalParameters_return retval = new ArielParser.formalParameters_return();
        retval.start = input.LT(1);

        int formalParameters_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal623=null;
        Token char_literal625=null;
        ArielParser.formalParameterDecls_return formalParameterDecls624 =null;


        Object char_literal623_tree=null;
        Object char_literal625_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 105) ) { return retval; }

            // org\\aries\\Ariel.g:635:2: ( '(' ( formalParameterDecls )? ')' )
            // org\\aries\\Ariel.g:635:4: '(' ( formalParameterDecls )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal623=(Token)match(input,50,FOLLOW_50_in_formalParameters3406); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal623_tree = 
            (Object)adaptor.create(char_literal623)
            ;
            adaptor.addChild(root_0, char_literal623_tree);
            }

            // org\\aries\\Ariel.g:635:8: ( formalParameterDecls )?
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
                    // org\\aries\\Ariel.g:635:8: formalParameterDecls
                    {
                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameters3408);
                    formalParameterDecls624=formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDecls624.getTree());

                    }
                    break;

            }


            char_literal625=(Token)match(input,51,FOLLOW_51_in_formalParameters3411); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal625_tree = 
            (Object)adaptor.create(char_literal625)
            ;
            adaptor.addChild(root_0, char_literal625_tree);
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
    // org\\aries\\Ariel.g:638:1: formalParameterDecls : formalParameterDeclsRest ;
    public final ArielParser.formalParameterDecls_return formalParameterDecls() throws RecognitionException {
        ArielParser.formalParameterDecls_return retval = new ArielParser.formalParameterDecls_return();
        retval.start = input.LT(1);

        int formalParameterDecls_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.formalParameterDeclsRest_return formalParameterDeclsRest626 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 106) ) { return retval; }

            // org\\aries\\Ariel.g:639:2: ( formalParameterDeclsRest )
            // org\\aries\\Ariel.g:639:4: formalParameterDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameterDeclsRest_in_formalParameterDecls3423);
            formalParameterDeclsRest626=formalParameterDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDeclsRest626.getTree());

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
    // org\\aries\\Ariel.g:642:1: formalParameterDeclsRest : qualifiedName ( ',' formalParameterDecls )? ;
    public final ArielParser.formalParameterDeclsRest_return formalParameterDeclsRest() throws RecognitionException {
        ArielParser.formalParameterDeclsRest_return retval = new ArielParser.formalParameterDeclsRest_return();
        retval.start = input.LT(1);

        int formalParameterDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal628=null;
        ArielParser.qualifiedName_return qualifiedName627 =null;

        ArielParser.formalParameterDecls_return formalParameterDecls629 =null;


        Object char_literal628_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 107) ) { return retval; }

            // org\\aries\\Ariel.g:643:2: ( qualifiedName ( ',' formalParameterDecls )? )
            // org\\aries\\Ariel.g:643:4: qualifiedName ( ',' formalParameterDecls )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_formalParameterDeclsRest3435);
            qualifiedName627=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName627.getTree());

            // org\\aries\\Ariel.g:643:18: ( ',' formalParameterDecls )?
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
                    // org\\aries\\Ariel.g:643:19: ',' formalParameterDecls
                    {
                    char_literal628=(Token)match(input,57,FOLLOW_57_in_formalParameterDeclsRest3438); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal628_tree = 
                    (Object)adaptor.create(char_literal628)
                    ;
                    adaptor.addChild(root_0, char_literal628_tree);
                    }

                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameterDeclsRest3440);
                    formalParameterDecls629=formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterDecls629.getTree());

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
    // org\\aries\\Ariel.g:646:1: formalParametersSignature : '(' ( formalParameterSignatureDecls )? ')' ;
    public final ArielParser.formalParametersSignature_return formalParametersSignature() throws RecognitionException {
        ArielParser.formalParametersSignature_return retval = new ArielParser.formalParametersSignature_return();
        retval.start = input.LT(1);

        int formalParametersSignature_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal630=null;
        Token char_literal632=null;
        ArielParser.formalParameterSignatureDecls_return formalParameterSignatureDecls631 =null;


        Object char_literal630_tree=null;
        Object char_literal632_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 108) ) { return retval; }

            // org\\aries\\Ariel.g:647:2: ( '(' ( formalParameterSignatureDecls )? ')' )
            // org\\aries\\Ariel.g:647:4: '(' ( formalParameterSignatureDecls )? ')'
            {
            root_0 = (Object)adaptor.nil();


            char_literal630=(Token)match(input,50,FOLLOW_50_in_formalParametersSignature3453); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal630_tree = 
            (Object)adaptor.create(char_literal630)
            ;
            adaptor.addChild(root_0, char_literal630_tree);
            }

            // org\\aries\\Ariel.g:647:8: ( formalParameterSignatureDecls )?
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
                case 131:
                    {
                    alt107=1;
                    }
                    break;
            }

            switch (alt107) {
                case 1 :
                    // org\\aries\\Ariel.g:647:8: formalParameterSignatureDecls
                    {
                    pushFollow(FOLLOW_formalParameterSignatureDecls_in_formalParametersSignature3455);
                    formalParameterSignatureDecls631=formalParameterSignatureDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDecls631.getTree());

                    }
                    break;

            }


            char_literal632=(Token)match(input,51,FOLLOW_51_in_formalParametersSignature3458); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal632_tree = 
            (Object)adaptor.create(char_literal632)
            ;
            adaptor.addChild(root_0, char_literal632_tree);
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
    // org\\aries\\Ariel.g:650:1: formalParameterSignatureDecls : formalParameterSignatureDeclsRest ;
    public final ArielParser.formalParameterSignatureDecls_return formalParameterSignatureDecls() throws RecognitionException {
        ArielParser.formalParameterSignatureDecls_return retval = new ArielParser.formalParameterSignatureDecls_return();
        retval.start = input.LT(1);

        int formalParameterSignatureDecls_StartIndex = input.index();

        Object root_0 = null;

        ArielParser.formalParameterSignatureDeclsRest_return formalParameterSignatureDeclsRest633 =null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 109) ) { return retval; }

            // org\\aries\\Ariel.g:651:2: ( formalParameterSignatureDeclsRest )
            // org\\aries\\Ariel.g:651:4: formalParameterSignatureDeclsRest
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_formalParameterSignatureDeclsRest_in_formalParameterSignatureDecls3470);
            formalParameterSignatureDeclsRest633=formalParameterSignatureDeclsRest();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDeclsRest633.getTree());

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
    // org\\aries\\Ariel.g:654:1: formalParameterSignatureDeclsRest : type Identifier ( ',' formalParameterSignatureDecls )? ;
    public final ArielParser.formalParameterSignatureDeclsRest_return formalParameterSignatureDeclsRest() throws RecognitionException {
        ArielParser.formalParameterSignatureDeclsRest_return retval = new ArielParser.formalParameterSignatureDeclsRest_return();
        retval.start = input.LT(1);

        int formalParameterSignatureDeclsRest_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier635=null;
        Token char_literal636=null;
        ArielParser.type_return type634 =null;

        ArielParser.formalParameterSignatureDecls_return formalParameterSignatureDecls637 =null;


        Object Identifier635_tree=null;
        Object char_literal636_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 110) ) { return retval; }

            // org\\aries\\Ariel.g:655:2: ( type Identifier ( ',' formalParameterSignatureDecls )? )
            // org\\aries\\Ariel.g:655:4: type Identifier ( ',' formalParameterSignatureDecls )?
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_type_in_formalParameterSignatureDeclsRest3482);
            type634=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type634.getTree());

            Identifier635=(Token)match(input,Identifier,FOLLOW_Identifier_in_formalParameterSignatureDeclsRest3484); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier635_tree = 
            (Object)adaptor.create(Identifier635)
            ;
            adaptor.addChild(root_0, Identifier635_tree);
            }

            // org\\aries\\Ariel.g:655:20: ( ',' formalParameterSignatureDecls )?
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
                    // org\\aries\\Ariel.g:655:21: ',' formalParameterSignatureDecls
                    {
                    char_literal636=(Token)match(input,57,FOLLOW_57_in_formalParameterSignatureDeclsRest3487); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal636_tree = 
                    (Object)adaptor.create(char_literal636)
                    ;
                    adaptor.addChild(root_0, char_literal636_tree);
                    }

                    pushFollow(FOLLOW_formalParameterSignatureDecls_in_formalParameterSignatureDeclsRest3489);
                    formalParameterSignatureDecls637=formalParameterSignatureDecls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterSignatureDecls637.getTree());

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
    // org\\aries\\Ariel.g:658:1: qualifiedNameList : qualifiedName ( ',' qualifiedName )* ;
    public final ArielParser.qualifiedNameList_return qualifiedNameList() throws RecognitionException {
        ArielParser.qualifiedNameList_return retval = new ArielParser.qualifiedNameList_return();
        retval.start = input.LT(1);

        int qualifiedNameList_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal639=null;
        ArielParser.qualifiedName_return qualifiedName638 =null;

        ArielParser.qualifiedName_return qualifiedName640 =null;


        Object char_literal639_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 111) ) { return retval; }

            // org\\aries\\Ariel.g:659:2: ( qualifiedName ( ',' qualifiedName )* )
            // org\\aries\\Ariel.g:659:4: qualifiedName ( ',' qualifiedName )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList3502);
            qualifiedName638=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName638.getTree());

            // org\\aries\\Ariel.g:659:18: ( ',' qualifiedName )*
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
            	    // org\\aries\\Ariel.g:659:19: ',' qualifiedName
            	    {
            	    char_literal639=(Token)match(input,57,FOLLOW_57_in_qualifiedNameList3505); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal639_tree = 
            	    (Object)adaptor.create(char_literal639)
            	    ;
            	    adaptor.addChild(root_0, char_literal639_tree);
            	    }

            	    pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList3507);
            	    qualifiedName640=qualifiedName();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName640.getTree());

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
    // org\\aries\\Ariel.g:662:1: qualifiedName : ( ( identifier '.' )=> identifier '.' qualifiedName | identifier );
    public final ArielParser.qualifiedName_return qualifiedName() throws RecognitionException {
        ArielParser.qualifiedName_return retval = new ArielParser.qualifiedName_return();
        retval.start = input.LT(1);

        int qualifiedName_StartIndex = input.index();

        Object root_0 = null;

        Token char_literal642=null;
        ArielParser.identifier_return identifier641 =null;

        ArielParser.qualifiedName_return qualifiedName643 =null;

        ArielParser.identifier_return identifier644 =null;


        Object char_literal642_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 112) ) { return retval; }

            // org\\aries\\Ariel.g:663:2: ( ( identifier '.' )=> identifier '.' qualifiedName | identifier )
            int alt110=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA110_1 = input.LA(2);

                if ( (synpred10_Ariel()) ) {
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

                if ( (synpred10_Ariel()) ) {
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
                    // org\\aries\\Ariel.g:663:4: ( identifier '.' )=> identifier '.' qualifiedName
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_identifier_in_qualifiedName3528);
                    identifier641=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier641.getTree());

                    char_literal642=(Token)match(input,61,FOLLOW_61_in_qualifiedName3530); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal642_tree = 
                    (Object)adaptor.create(char_literal642)
                    ;
                    adaptor.addChild(root_0, char_literal642_tree);
                    }

                    pushFollow(FOLLOW_qualifiedName_in_qualifiedName3532);
                    qualifiedName643=qualifiedName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, qualifiedName643.getTree());

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:664:4: identifier
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_identifier_in_qualifiedName3537);
                    identifier644=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier644.getTree());

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
    // org\\aries\\Ariel.g:669:1: identifier : ( Identifier | keyword );
    public final ArielParser.identifier_return identifier() throws RecognitionException {
        ArielParser.identifier_return retval = new ArielParser.identifier_return();
        retval.start = input.LT(1);

        int identifier_StartIndex = input.index();

        Object root_0 = null;

        Token Identifier645=null;
        ArielParser.keyword_return keyword646 =null;


        Object Identifier645_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 113) ) { return retval; }

            // org\\aries\\Ariel.g:670:2: ( Identifier | keyword )
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
                    // org\\aries\\Ariel.g:670:4: Identifier
                    {
                    root_0 = (Object)adaptor.nil();


                    Identifier645=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifier3551); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Identifier645_tree = 
                    (Object)adaptor.create(Identifier645)
                    ;
                    adaptor.addChild(root_0, Identifier645_tree);
                    }

                    }
                    break;
                case 2 :
                    // org\\aries\\Ariel.g:671:4: keyword
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_keyword_in_identifier3556);
                    keyword646=keyword();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, keyword646.getTree());

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
    // org\\aries\\Ariel.g:674:1: keyword : ( 'exception' | 'message' );
    public final ArielParser.keyword_return keyword() throws RecognitionException {
        ArielParser.keyword_return retval = new ArielParser.keyword_return();
        retval.start = input.LT(1);

        int keyword_StartIndex = input.index();

        Object root_0 = null;

        Token set647=null;

        Object set647_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 114) ) { return retval; }

            // org\\aries\\Ariel.g:675:2: ( 'exception' | 'message' )
            // org\\aries\\Ariel.g:
            {
            root_0 = (Object)adaptor.nil();


            set647=(Token)input.LT(1);

            if ( input.LA(1)==EXCEPTION||input.LA(1)==MESSAGE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                (Object)adaptor.create(set647)
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

    // $ANTLR start synpred1_Ariel
    public final void synpred1_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:126:48: ( 'else' )
        // org\\aries\\Ariel.g:126:49: 'else'
        {
        match(input,92,FOLLOW_92_in_synpred1_Ariel831); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Ariel

    // $ANTLR start synpred2_Ariel
    public final void synpred2_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:261:4: ( qualifiedName '(' ')' )
        // org\\aries\\Ariel.g:261:5: qualifiedName '(' ')'
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred2_Ariel1671);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,50,FOLLOW_50_in_synpred2_Ariel1673); if (state.failed) return ;

        match(input,51,FOLLOW_51_in_synpred2_Ariel1675); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Ariel

    // $ANTLR start synpred3_Ariel
    public final void synpred3_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:262:4: ( qualifiedName '(' )
        // org\\aries\\Ariel.g:262:5: qualifiedName '('
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred3_Ariel1704);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,50,FOLLOW_50_in_synpred3_Ariel1706); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Ariel

    // $ANTLR start synpred4_Ariel
    public final void synpred4_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:263:4: ( qualifiedName '[' )
        // org\\aries\\Ariel.g:263:5: qualifiedName '['
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred4_Ariel1739);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,71,FOLLOW_71_in_synpred4_Ariel1741); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Ariel

    // $ANTLR start synpred5_Ariel
    public final void synpred5_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:264:4: ( type Identifier '=' )
        // org\\aries\\Ariel.g:264:5: type Identifier '='
        {
        pushFollow(FOLLOW_type_in_synpred5_Ariel1774);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input,Identifier,FOLLOW_Identifier_in_synpred5_Ariel1776); if (state.failed) return ;

        match(input,67,FOLLOW_67_in_synpred5_Ariel1778); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Ariel

    // $ANTLR start synpred6_Ariel
    public final void synpred6_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:265:4: ( qualifiedName '.' )
        // org\\aries\\Ariel.g:265:5: qualifiedName '.'
        {
        pushFollow(FOLLOW_qualifiedName_in_synpred6_Ariel1801);
        qualifiedName();

        state._fsp--;
        if (state.failed) return ;

        match(input,61,FOLLOW_61_in_synpred6_Ariel1803); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Ariel

    // $ANTLR start synpred7_Ariel
    public final void synpred7_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:271:4: ( '(' )
        // org\\aries\\Ariel.g:271:5: '('
        {
        match(input,50,FOLLOW_50_in_synpred7_Ariel1856); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_Ariel

    // $ANTLR start synpred8_Ariel
    public final void synpred8_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:272:4: ( '.' )
        // org\\aries\\Ariel.g:272:5: '.'
        {
        match(input,61,FOLLOW_61_in_synpred8_Ariel1867); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred8_Ariel

    // $ANTLR start synpred9_Ariel
    public final void synpred9_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:273:4: ( '[' )
        // org\\aries\\Ariel.g:273:5: '['
        {
        match(input,71,FOLLOW_71_in_synpred9_Ariel1882); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_Ariel

    // $ANTLR start synpred10_Ariel
    public final void synpred10_Ariel_fragment() throws RecognitionException {
        // org\\aries\\Ariel.g:663:4: ( identifier '.' )
        // org\\aries\\Ariel.g:663:5: identifier '.'
        {
        pushFollow(FOLLOW_identifier_in_synpred10_Ariel3521);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        match(input,61,FOLLOW_61_in_synpred10_Ariel3523); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred10_Ariel

    // Delegated rules

    public final boolean synpred3_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Ariel() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Ariel_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_importDecl_in_compilationUnit114 = new BitSet(new long[]{0x0000000000000000L,0x0004000800000000L});
    public static final BitSet FOLLOW_networkDecl_in_compilationUnit117 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_EOF_in_compilationUnit120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_assignmentDecl136 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl138 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl140 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
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
    public static final BitSet FOLLOW_130_in_assignmentDecl184 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_assignmentDecl187 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl189 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_assignmentDecl191 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl193 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl201 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_assignmentDecl204 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl206 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl208 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl218 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_assignmentDecl221 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl223 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_LevelType_in_assignmentDecl225 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl235 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_assignmentDecl238 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl240 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IntegerLiteral_in_assignmentDecl242 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl252 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_111_in_assignmentDecl255 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl257 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IntegerLiteral_in_assignmentDecl259 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl261 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl269 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_assignmentDecl272 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl274 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl276 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl286 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_assignmentDecl289 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl291 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl293 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl303 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_117_in_assignmentDecl306 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl308 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl310 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_assignmentDecl320 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_120_in_assignmentDecl323 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl325 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl327 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl329 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl337 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_123_in_assignmentDecl340 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl342 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_assignmentDecl344 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl346 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl354 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_125_in_assignmentDecl357 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl359 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl361 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl363 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_assignmentDecl371 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_125_in_assignmentDecl374 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl376 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl378 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl380 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl388 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_127_in_assignmentDecl391 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl393 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_ScopeType_in_assignmentDecl395 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl397 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl405 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_132_in_assignmentDecl408 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl410 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl412 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl414 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl422 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_133_in_assignmentDecl425 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl427 = new BitSet(new long[]{0x0000002000008000L});
    public static final BitSet FOLLOW_set_in_assignmentDecl429 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl437 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_137_in_assignmentDecl448 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl450 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_TimeoutLiteral_in_assignmentDecl452 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_assignmentDecl462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_138_in_assignmentDecl465 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl467 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_TransactionType_in_assignmentDecl469 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_assignmentDecl479 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_138_in_assignmentDecl482 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl484 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl486 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl488 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_assignmentDecl496 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_assignmentDecl499 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl501 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl503 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_assignmentDecl513 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_assignmentDecl516 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_assignmentDecl518 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_assignmentDecl520 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_assignmentDecl522 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_assignmentDecl524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_branchDecl536 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000001L});
    public static final BitSet FOLLOW_Identifier_in_branchDecl539 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_branchDecl542 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_branchBody_in_branchDecl544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_branchBody557 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149818C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_branchMember_in_branchBody560 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149818C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_144_in_branchBody564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_branchMember575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_branchMember581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_branchMember586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_cacheDecl599 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_cacheDecl602 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_cacheBody_in_cacheDecl604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_cacheBody616 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000010004L});
    public static final BitSet FOLLOW_cacheMember_in_cacheBody619 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000010004L});
    public static final BitSet FOLLOW_144_in_cacheBody623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_cacheMember634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemsDecl_in_cacheMember639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_channelDecl650 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelDecl653 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_140_in_channelDecl655 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001002L,0x0000000000010004L});
    public static final BitSet FOLLOW_channelBody_in_channelDecl658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001002L,0x0000000000010004L});
    public static final BitSet FOLLOW_144_in_channelDecl662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody673 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_channelBody675 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody677 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody679 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody681 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody688 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_channelBody690 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody692 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody694 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody696 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_channelBody703 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_channelBody705 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody707 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody709 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody711 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_channelBody718 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_channelBody720 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_channelBody722 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_channelBody724 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_channelBody726 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_channelBody733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_commandDecl744 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_commandDecl752 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_commandDecl755 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl757 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_commandDecl765 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_commandDecl768 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl770 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_commandDecl778 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_commandDecl781 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_commandDecl783 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_commandDecl786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_commandDecl792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_statementDecl803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_statementDecl818 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl821 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl823 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl825 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000021889L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl827 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_statementDecl836 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000021889L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_statementDecl845 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl848 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124002L,0x0000000000000008L});
    public static final BitSet FOLLOW_variableDeclaration_in_statementDecl851 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl855 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124002L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl858 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl862 = new BitSet(new long[]{0x0CCC0824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionList_in_statementDecl865 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl869 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000021889L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_139_in_statementDecl876 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl879 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl881 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl883 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000021889L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_statementDecl890 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000021889L});
    public static final BitSet FOLLOW_statementDecl_in_statementDecl893 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_139_in_statementDecl895 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_statementDecl897 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl899 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_statementDecl901 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_statementDecl908 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124002L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl912 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_statementDecl921 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_statementDecl931 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_statementDecl935 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_statementDecl944 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_statementDecl948 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_statementDecl957 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statementDecl959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_commandDecl_in_statementDecl964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doneDecl_in_statementDecl969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_statementBlock982 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_statementMember_in_statementBlock985 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_144_in_statementBlock989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_statementMember1001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variableDeclaration1013 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclaration1015 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_71_in_variableDeclaration1018 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_variableDeclaration1020 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_67_in_variableDeclaration1024 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000021008L});
    public static final BitSet FOLLOW_variableInitializer_in_variableDeclaration1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayInitializer_in_variableInitializer1059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_variableInitializer1064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_arrayInitializer1076 = new BitSet(new long[]{0x0EC40824C0C18920L,0x0008104088124000L,0x0000000000031008L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1079 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_57_in_arrayInitializer1082 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000021008L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1084 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_57_in_arrayInitializer1092 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_144_in_arrayInitializer1096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_expressionDecl1109 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_expressionDecl1111 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_expressionDecl1113 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_expressionDecl1115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_expressionDecl1135 = new BitSet(new long[]{0x9122400000000002L,0x000000000000042CL,0x0000000000004000L});
    public static final BitSet FOLLOW_assignmentOperator_in_expressionDecl1138 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionDecl1140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionList1169 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_expressionList1172 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_expressionList1174 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_assignmentOperator1193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_assignmentOperator1198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_assignmentOperator1203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_assignmentOperator1208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_assignmentOperator1213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_assignmentOperator1218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_assignmentOperator1223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_assignmentOperator1228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_assignmentOperator1233 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_assignmentOperator1235 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1242 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1253 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_assignmentOperator1255 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_assignmentOperator1257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalExpression1268 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_conditionalExpression1271 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_conditionalExpression1273 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_conditionalExpression1275 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_conditionalExpression_in_conditionalExpression1277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1291 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_143_in_conditionalOrExpression1294 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1296 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1310 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_conditionalAndExpression1313 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression1315 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1329 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_141_in_inclusiveOrExpression1332 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression1334 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression1348 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_exclusiveOrExpression1351 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression1353 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression1367 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_andExpression1370 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression1372 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1386 = new BitSet(new long[]{0x0000100000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_equalityExpression1390 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1398 = new BitSet(new long[]{0x0000100000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression1412 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpression1415 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression1417 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_relationalOp1431 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_relationalOp1433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_relationalOp1438 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_relationalOp1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_relationalOp1445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_relationalOp1450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression1462 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_shiftOp_in_shiftExpression1465 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression1467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_shiftOp1481 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_shiftOp1483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_shiftOp1488 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1490 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_shiftOp1497 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_shiftOp1499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1511 = new BitSet(new long[]{0x0440000000000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1515 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1523 = new BitSet(new long[]{0x0440000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1537 = new BitSet(new long[]{0x4010200000000002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1541 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1553 = new BitSet(new long[]{0x4010200000000002L});
    public static final BitSet FOLLOW_54_in_unaryExpression1569 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_unaryExpression1576 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_unaryExpression1583 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_unaryExpression1590 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression1597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_145_in_unaryExpressionNotPlusMinus1609 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_unaryExpressionNotPlusMinus1616 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0000104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus1618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_unaryExpressionNotPlusMinus1623 = new BitSet(new long[]{0x2884000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_selector_in_unaryExpressionNotPlusMinus1626 = new BitSet(new long[]{0x2884000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_50_in_primary1649 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_primary1651 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1681 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_primary1683 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1712 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_primary1714 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionList_in_primary1716 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primary1718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1747 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_primary1749 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_primary1751 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_primary1753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_primary1783 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_primary1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_primary1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primary1834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_selector1861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_selector1872 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_selector1874 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_arguments_in_selector1876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_selector1887 = new BitSet(new long[]{0x0CC40824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionDecl_in_selector1889 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_selector1891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type1904 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_type1907 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_type1909 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_qualifiedName_in_type1931 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000084L});
    public static final BitSet FOLLOW_typeArguments_in_type1933 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_type1937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_type1939 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_typeList1971 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_typeList1974 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000008L});
    public static final BitSet FOLLOW_type_in_typeList1976 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_66_in_typeArguments1990 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000008L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1992 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_57_in_typeArguments1995 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000008L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1997 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_typeArguments2001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeArgument2013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_arguments2073 = new BitSet(new long[]{0x0CCC0824C0C18920L,0x0008104088124000L,0x0000000000020008L});
    public static final BitSet FOLLOW_expressionList_in_arguments2076 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_arguments2080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_doneDecl2141 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_doneDecl2143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXCEPTION_in_exceptionDecl2154 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_exceptionDecl2157 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_exceptionBody_in_exceptionDecl2159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_exceptionBody2170 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_exceptionMember_in_exceptionBody2173 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_144_in_exceptionBody2177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_exceptionMember2188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_exceptionMember2193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_executeDecl2206 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_executeBody_in_executeDecl2209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_executeDecl2214 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_134_in_executeDecl2217 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_executeDecl2219 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParameters_in_executeDecl2221 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_executeBody_in_executeDecl2223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_executeDeclRest2235 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_executeBody_in_executeDeclRest2238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_executeDeclRest2242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_executeBody2254 = new BitSet(new long[]{0x0000000000000800L,0x0000021800009000L,0x0000000000010204L});
    public static final BitSet FOLLOW_executeMember_in_executeBody2257 = new BitSet(new long[]{0x0000000000000800L,0x0000021800009000L,0x0000000000010204L});
    public static final BitSet FOLLOW_144_in_executeBody2261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_executeMember2273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_branchDecl_in_executeMember2278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_executeMember2283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_executeMember2288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_groupDecl2299 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_groupDecl2302 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_groupBody_in_groupDecl2304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_groupBody2315 = new BitSet(new long[]{0x0000000000000000L,0x0000021800001000L,0x0000000000010004L});
    public static final BitSet FOLLOW_groupMember_in_groupBody2318 = new BitSet(new long[]{0x0000000000000000L,0x0000021800001000L,0x0000000000010004L});
    public static final BitSet FOLLOW_144_in_groupBody2322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_groupMember2333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_importDecl2345 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_importDecl2348 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_importDecl2351 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_importDecl2353 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_importDecl2357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_invokeDecl2368 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_invokeDecl2371 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_invokeDecl2373 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_invokeDecl2375 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParameters_in_invokeDecl2377 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_invokeBody_in_invokeDecl2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_invokeBody2390 = new BitSet(new long[]{0x0000000040000800L,0x0000021800001000L,0x0000000000010204L});
    public static final BitSet FOLLOW_invokeMember_in_invokeBody2393 = new BitSet(new long[]{0x0000000040000800L,0x0000021800001000L,0x0000000000010204L});
    public static final BitSet FOLLOW_144_in_invokeBody2397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_invokeMember2408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_messageDecl_in_invokeMember2413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_invokeMember2418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_invokeMember2423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_itemsDecl2434 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_itemsBody_in_itemsDecl2437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_itemsBody2448 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000010008L});
    public static final BitSet FOLLOW_itemsMember_in_itemsBody2451 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000010008L});
    public static final BitSet FOLLOW_144_in_itemsBody2455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemDecl_in_itemsMember2466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_itemDecl2477 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_itemDecl2479 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_itemDeclRest_in_itemDecl2481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_itemDeclRest2507 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_itemMember_in_itemDeclRest2510 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_144_in_itemDeclRest2514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_itemDeclRest2519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_itemMember2530 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_itemMember2533 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_itemMember2535 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_itemMember2537 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_itemMember2539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_listenDecl2550 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_listenDecl2553 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_formalParameters_in_listenDecl2555 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_listenBody_in_listenDecl2558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_listenBody2570 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149812DDAD935000L,0x0000000000031A8DL});
    public static final BitSet FOLLOW_listenMember_in_listenBody2573 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149812DDAD935000L,0x0000000000031A8DL});
    public static final BitSet FOLLOW_144_in_listenBody2577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_listenMember2588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_listenMember2593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_listenMember2598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeoutDecl_in_listenMember2604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exceptionDecl_in_listenMember2609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MESSAGE_in_messageDecl2621 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_messageDecl2624 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_formalParameters_in_messageDecl2626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_messageDecl2629 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_messageBody_in_messageDecl2631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_messageBody2643 = new BitSet(new long[]{0x0CC40824C0C18920L,0x14981ADDED935000L,0x000000000003188DL});
    public static final BitSet FOLLOW_messageMember_in_messageBody2646 = new BitSet(new long[]{0x0CC40824C0C18920L,0x14981ADDED935000L,0x000000000003188DL});
    public static final BitSet FOLLOW_144_in_messageBody2650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_messageMember2661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_messageMember2666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_messageMember2671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_messageMember2676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_messageMember2681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_methodDecl2695 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParametersSignature_in_methodDecl2698 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_methodBody_in_methodDecl2700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_methodBody2711 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148818C5ED934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_methodMember_in_methodBody2714 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148818C5ED934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_144_in_methodBody2718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_methodMember2729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_methodMember2734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_methodMember2739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_optionDecl2751 = new BitSet(new long[]{0x0004000000400000L,0x0000000000000001L});
    public static final BitSet FOLLOW_Identifier_in_optionDecl2754 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_formalParameters_in_optionDecl2757 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_optionDecl2760 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_optionBody_in_optionDecl2762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_optionBody2773 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_optionMember_in_optionBody2776 = new BitSet(new long[]{0x0CC40824C0C18920L,0x148810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_144_in_optionBody2780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_optionMember2791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_participantDecl2806 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_participantDecl2809 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_participantBody_in_participantDecl2811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_participantBody2822 = new BitSet(new long[]{0x0000000040400800L,0x4240021800041000L,0x0000000000010004L});
    public static final BitSet FOLLOW_participantMember_in_participantBody2825 = new BitSet(new long[]{0x0000000040400800L,0x4240021800041000L,0x0000000000010004L});
    public static final BitSet FOLLOW_144_in_participantBody2829 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_participantMember2841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_receiveDecl_in_participantMember2846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cacheDecl_in_participantMember2851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_persistDecl_in_participantMember2856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scheduleDecl_in_participantMember2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodDecl_in_participantMember2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_persistDecl2877 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_persistDecl2880 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_persistBody_in_persistDecl2882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_persistBody2893 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000010004L});
    public static final BitSet FOLLOW_persistMember_in_persistBody2896 = new BitSet(new long[]{0x0000000000000000L,0x0000031800001000L,0x0000000000010004L});
    public static final BitSet FOLLOW_144_in_persistBody2900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_persistMember2911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemsDecl_in_persistMember2916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_networkDecl2927 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_networkDecl2930 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_networkBody_in_networkDecl2932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_networkBody2943 = new BitSet(new long[]{0x0000000000000000L,0x6060021A000C1000L,0x0000000000010004L});
    public static final BitSet FOLLOW_networkMember_in_networkBody2945 = new BitSet(new long[]{0x0000000000000000L,0x6060021A000C1000L,0x0000000000010004L});
    public static final BitSet FOLLOW_144_in_networkBody2948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_networkMember2960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_roleDecl_in_networkMember2965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groupDecl_in_networkMember2970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_channelDecl_in_networkMember2975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_participantDecl_in_networkMember2980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cacheDecl_in_networkMember2985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_persistDecl_in_networkMember2990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scheduleDecl_in_networkMember2995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_receiveDecl3006 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_receiveDecl3009 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_formalParametersSignature_in_receiveDecl3011 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000001100L});
    public static final BitSet FOLLOW_throwsBody_in_receiveDecl3013 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_receiveBody_in_receiveDecl3017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_receiveDecl3021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_receiveBody3033 = new BitSet(new long[]{0x0CC40824C0C18920L,0x14981ADDED935000L,0x000000000003188DL});
    public static final BitSet FOLLOW_receiveMember_in_receiveBody3036 = new BitSet(new long[]{0x0CC40824C0C18920L,0x14981ADDED935000L,0x000000000003188DL});
    public static final BitSet FOLLOW_144_in_receiveBody3040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_receiveMember3051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_receiveMember3056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeDecl_in_receiveMember3061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listenDecl_in_receiveMember3066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_receiveMember3071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_125_in_roleDecl3084 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_roleDecl3087 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_roleBody_in_roleDecl3089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_roleBody3100 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_roleMember_in_roleBody3103 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_144_in_roleBody3107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_roleMember3118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_scheduleDecl3129 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_scheduleDecl3132 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_scheduleBody_in_scheduleDecl3134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_scheduleBody3145 = new BitSet(new long[]{0x0000000000000000L,0x0680029820001002L,0x0000000000010085L});
    public static final BitSet FOLLOW_scheduleMember_in_scheduleBody3148 = new BitSet(new long[]{0x0000000000000000L,0x0680029820001002L,0x0000000000010085L});
    public static final BitSet FOLLOW_144_in_scheduleBody3152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentDecl_in_scheduleMember3164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_receiveDecl_in_scheduleMember3169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeDecl_in_scheduleMember3174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schedulableCommandDecl_in_scheduleMember3179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_schedulableCommandDecl3192 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_schedulableCommandDecl3200 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3203 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3205 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_schedulableCommandDecl3213 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3216 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3218 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_schedulableCommandDecl3226 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_schedulableCommandDecl3229 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3231 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_schedulableCommandDecl3239 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_schedulableCommandDecl3242 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_schedulableCommandDecl3244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_schedulableCommandDecl3252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_throwsBody3264 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_throwsList_in_throwsBody3267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwsListDecls_in_throwsList3279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwsListDeclsRest_in_throwsListDecls3292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_throwsListDeclsRest3304 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_throwsListDeclsRest3307 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_throwsListDeclsRest_in_throwsListDeclsRest3309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_137_in_timeoutDecl3323 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_timeoutDecl3326 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_timeoutBody_in_timeoutDecl3328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_timeoutBody3340 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_timeoutMember_in_timeoutBody3343 = new BitSet(new long[]{0x0CC40824C0C18920L,0x149810C5AD934000L,0x0000000000031889L});
    public static final BitSet FOLLOW_144_in_timeoutBody3347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_optionDecl_in_timeoutMember3358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementDecl_in_timeoutMember3363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_typeRef3378 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_typeRef3380 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_typeRef3382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_formalParameters3406 = new BitSet(new long[]{0x0008000040400800L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameters3408 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_formalParameters3411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterDeclsRest_in_formalParameterDecls3423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_formalParameterDeclsRest3435 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_formalParameterDeclsRest3438 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameterDeclsRest3440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_formalParametersSignature3453 = new BitSet(new long[]{0x0008000040400800L,0x0000104088124000L,0x0000000000000008L});
    public static final BitSet FOLLOW_formalParameterSignatureDecls_in_formalParametersSignature3455 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_formalParametersSignature3458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterSignatureDeclsRest_in_formalParameterSignatureDecls3470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_formalParameterSignatureDeclsRest3482 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_formalParameterSignatureDeclsRest3484 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_formalParameterSignatureDeclsRest3487 = new BitSet(new long[]{0x0000000040400800L,0x0000104088124000L,0x0000000000000008L});
    public static final BitSet FOLLOW_formalParameterSignatureDecls_in_formalParameterSignatureDeclsRest3489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList3502 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_qualifiedNameList3505 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList3507 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_identifier_in_qualifiedName3528 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_qualifiedName3530 = new BitSet(new long[]{0x0000000040400800L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedName3532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_qualifiedName3537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_identifier3551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_identifier3556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_synpred1_Ariel831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred2_Ariel1671 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_synpred2_Ariel1673 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_synpred2_Ariel1675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred3_Ariel1704 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_synpred3_Ariel1706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred4_Ariel1739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_synpred4_Ariel1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred5_Ariel1774 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Identifier_in_synpred5_Ariel1776 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_synpred5_Ariel1778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_synpred6_Ariel1801 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_synpred6_Ariel1803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_synpred7_Ariel1856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_synpred8_Ariel1867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_synpred9_Ariel1882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_synpred10_Ariel3521 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_synpred10_Ariel3523 = new BitSet(new long[]{0x0000000000000002L});

}