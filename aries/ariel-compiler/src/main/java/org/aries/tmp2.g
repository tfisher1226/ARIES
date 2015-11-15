grammar tmp2;

options {
	// antlr will generate java lexer and parser
	language = Java;
	
	// generated parser should create abstract syntax tree
	output = AST;
	
	//k = 1;
	
	//backtrack = true; 

	//filter = true;

	memoize = true;
	
	//analyzerDebug = true; 
}

tokens {
	EXPR;
	BLOCK;
	PRIMARY;
	TYPE;
	ITEM;
}

@header {
	package org.aries;
}

@lexer::header {
	package org.aries;
}
 
@lexer::members {
	protected boolean enumIsKeyword = true;
	protected boolean assertIsKeyword = true;
}

// starting point
compilationUnit
	:	 importDecl* protocolDecl* EOF
	;


assignmentDecl
	:	 'set'^ 'adapter' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'backingStore' '(' Identifier ')' ';'
	|	 'add'^ 'channel' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'condition' '(' Identifier ')' ';'
	|	 'set'^ 'domain' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'level' '(' LevelType ')' ';'
	|	 'set'^ 'maxResponse' '(' IntegerLiteral ')' ';'
	|	 'set'^ 'minResponse' '(' IntegerLiteral ')' ';'
	|	 'set'^ 'namespace' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'participant' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'restriction' '(' qualifiedName ')' ';'
	|	 'set'^ 'role' '(' STRINGLITERAL ')' ';'
	|	 'add'^ 'role' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'scope' '(' ScopeType ')' ';'
	|	 'set'^ 'source' '(' STRINGLITERAL ')' ';'
	|	 'set'^ 'synchronous' '(' (TRUE | FALSE) ')' ';'
	|	 'set'^ 'timeout' '(' TimeoutLiteral ')' ';'
	|	 'set'^ 'transaction' '(' TransactionType ')' ';'
	|	 'join'^ 'transaction' '(' STRINGLITERAL ')' ';'
	|	 'include'^ 'namespace' '(' STRINGLITERAL ')' ';'
	|	 'import'^ 'namespace' '(' STRINGLITERAL ')' ';'
	;

branchDecl
	:	 'branch'^ Identifier? ':' branchBody 
	;

branchBody
	:	 '{' (branchMember)* '}'
	;

branchMember
	:	optionDecl
	|	 listenDecl
	|	statementDecl
//	|	methodCallDecl
	;

cacheDecl
	:	 'cache'^ Identifier cacheBody
	;

cacheBody
	:	 '{' (cacheMember)* '}'
	;

cacheMember
	:	assignmentDecl
	|	itemsDecl
	;

channelDecl
	:	'channel'^ Identifier '{' (channelBody)* '}'
	;

channelBody
	:	'add' 'client' '(' Identifier ')' ';'
	|	'add' 'service' '(' Identifier ')' ';'
	|	'add' 'manager' '(' Identifier ')' ';'
	|	'set' 'domain' '(' Identifier ')' ';'
	|	';'
	;

commandDecl
	:	'end'^ ';'
	|	'post'^ Identifier formalParameters? ';'
	|	'reply'^ Identifier formalParameters? ';'
	|	'send'^ qualifiedName formalParameters? ';'
	//|	'throw'^ Identifier formalParameters? ';'
	;

statementDecl
	:	statementBlock -> ^(BLOCK statementBlock)
	//|	'if'^ '(' expressionDecl ')' statementDecl ('else' statementDecl)?
	|	'if'^ '(' expressionDecl ')' statementDecl (('else') => 'else' statementDecl)?
	|	'for'^ '(' (variableDeclaration)? ';' (expressionDecl)? ';' (expressionList)? ')' statementDecl
	|	'while'^ '(' expressionDecl ')' statementDecl
	|	'do'^ statementDecl 'while' '(' expressionDecl ')' ';'
	|	'return'^ (expressionDecl)? ';'
	|	'throw'^ expressionDecl ';'
	|	'break'^ (Identifier)? ';'
	|	'continue'^ (Identifier)? ';'
	|	expressionDecl ';'
	|	commandDecl
	;
	
statementBlock 
	:	'{' (statementMember)* '}'
	;

statementMember 
	:	statementDecl
	;

variableDeclaration 
	:	type Identifier ('[' ']')* '=' variableInitializer -> ^(EXPR type Identifier ('[' ']')* '=' variableInitializer)
	;

variableInitializer 
	:	arrayInitializer
	|	expressionDecl
	;

arrayInitializer 
	:	'{' (variableInitializer (',' variableInitializer)* )? (',')? '}'
	;
	
expressionDecl
	:	conditionalExpression (assignmentOperator expressionDecl)? -> ^(EXPR conditionalExpression (assignmentOperator expressionDecl)?)
	;

expressionList 
	:	expressionDecl (',' expressionDecl)*
	;

assignmentOperator 
	:	'='
	|	'+='
	|	'-='
	|	'*='
	|	'/='
	|	'&='
	|	'|='
	|	'^='
	|	'%='
	|	'<' '<' '='
	|	'>' '>' '>' '='
	|	'>' '>' '='
	;

conditionalExpression
	:	conditionalOrExpression ('?' expressionDecl ':' conditionalExpression)?
	;

conditionalOrExpression 
	:	conditionalAndExpression ('||' conditionalAndExpression)*
	;

conditionalAndExpression 
	:	inclusiveOrExpression ('&&' inclusiveOrExpression)*
	;

inclusiveOrExpression 
	:	exclusiveOrExpression ('|' exclusiveOrExpression)*
	;

exclusiveOrExpression 
	:	andExpression ('^' andExpression)*
	;

andExpression 
	:	equalityExpression ('&' equalityExpression)*
	;

equalityExpression 
	:	relationalExpression ( ('==' | '!=') relationalExpression)*
	;

relationalExpression 
	:	shiftExpression (relationalOp shiftExpression)*
	;

relationalOp 
	:	'<' '='
	|	'>' '='
	|	'<'
	|	'>'
	;

shiftExpression 
	:	additiveExpression (shiftOp additiveExpression)*
	;

shiftOp 
	:	'<' '<'
	|	'>' '>' '>'
	|	'>' '>'
	;

additiveExpression 
	:	multiplicativeExpression ( ('+' | '-') multiplicativeExpression)*
	;

multiplicativeExpression 
	:	unaryExpression ( ('*' | '/' | '%') unaryExpression)*
	;

/**
 * NOTE: for '+' and '-', if the next token is int or long interal, then it's not a unary expression.
 *		  it's a literal with signed value. INTLTERAL AND LONG LITERAL are added here for this.
 */
unaryExpression 
	:	'+' unaryExpression
	|	'-' unaryExpression
	|	'++' unaryExpression
	|	'--' unaryExpression
	|	unaryExpressionNotPlusMinus
	;

unaryExpressionNotPlusMinus 
	:	'~' unaryExpression
	|	'!' unaryExpression
	|	primary (selector)* ('++' | '--')?
	;

primary 
	:	'(' expressionDecl ')' -> ^(PRIMARY '(' expressionDecl ')')
	|	(qualifiedName '(' ')') => (qualifiedName '(' ')') -> ^(PRIMARY qualifiedName '(' ')')
	|	(qualifiedName '(') => (qualifiedName '(' expressionList ')') -> ^(PRIMARY qualifiedName '(' expressionList ')')
	|	(qualifiedName '[') => (qualifiedName '[' expressionDecl ']') -> ^(PRIMARY qualifiedName '[' expressionDecl ']')
	|	(type Identifier '=') => type Identifier -> ^(PRIMARY type Identifier)
	|	(qualifiedName '.') => qualifiedName -> ^(PRIMARY qualifiedName)
	|	qualifiedName -> ^(PRIMARY qualifiedName)
	|	literal -> ^(PRIMARY literal)
	;

selector  
	:	('(') => arguments
	|	('.') => '.' Identifier arguments
	|	('[') => '[' expressionDecl ']'
	;
	
type 
	:	primitiveType ('[' ']')* -> ^(TYPE primitiveType ('[' ']')*)
	|	qualifiedName typeArguments? ('[' ']')* -> ^(TYPE qualifiedName typeArguments? ('[' ']')*)
	;

typeList 
	:	type (',' type)*
	;

typeArguments 
	:	'<' typeArgument (',' typeArgument)* '>'
	;

typeArgument 
	:	type
	;

primitiveType  
	:	'boolean'
	|	'char'
	|	'byte'
	|	'short'
	|	'int'
	|	'long'
	|	'float'
	|	'double'
	;

arguments 
	:	'(' (expressionList)? ')'
	;

literal 
	:	IntegerLiteral
	|	FloatLiteral
	|	DoubleLiteral
	|	CHARLITERAL
	|	STRINGLITERAL
	|	TRUE
	|	FALSE
	|	NULL
	;
	
	

exceptionDecl
	:	EXCEPTION^ ':' exceptionBody
	;

exceptionBody
	:	'{' (exceptionMember)* '}'
	;

exceptionMember
	:	optionDecl
	|	statementDecl
//	|	methodCallDecl
	;

executeDecl
	:	'execute'^ executeBody
	|	'execute'^ 'then' Identifier formalParameters executeBody 
	;

executeDeclRest
	:	formalParameters (executeBody | ';')
	;

executeBody
	:	'{' (executeMember)* '}'
	;
	
executeMember
	:	assignmentDecl
	|	branchDecl
	|	exceptionDecl
	|	timeoutDecl
	;

groupDecl
	:	'group'^ Identifier groupBody
	;

groupBody
	:	'{' (groupMember)* '}'
	;

groupMember
	:	assignmentDecl
	;
	
importDecl
	:	'import'^ qualifiedName ('.' '*')? ';'
	;

invokeDecl
	:	'invoke'^ Identifier '.' Identifier formalParameters invokeBody
	;

invokeBody
	:	'{' (invokeMember)* '}'
	;

invokeMember
	:	assignmentDecl
	|	messageDecl
	|	exceptionDecl
	|	timeoutDecl
	;

itemsDecl
	:	'items'^ itemsBody
	;

itemsBody
	:	'{' (itemsMember)* '}'
	;

itemsMember
	:	itemDecl
	;

itemDecl
	:	type Identifier itemDeclRest -> ^(ITEM type Identifier itemDeclRest)
	//|	qualifiedName typeArguments Identifier itemDeclRest
	;
	
itemDeclRest
	:	'{' (itemMember)* '}'
	|	';'
	;

itemMember
	:	'index'^ '(' Identifier ')' ';'
	;

listenDecl
	:	'listen'^ Identifier formalParameters? listenBody 
	;

listenBody
	:	'{' (listenMember)* '}'
	;

listenMember
	:	assignmentDecl
	|	optionDecl
	|	statementDecl
//	|	methodCallDecl
	;

messageDecl
	:	MESSAGE^ Identifier formalParameters? ':' messageBody 
	;

messageBody
	:	'{' (messageMember)* '}'
	;

messageMember
	:	assignmentDecl
	|	optionDecl
	|	executeDecl
	|	invokeDecl
	|	listenDecl
	|	statementDecl
//	|	methodCallDecl
	;
	
methodDecl
	:	qualifiedName^ formalParametersSignature methodBody
	;

methodBody
	:	'{' (methodMember)* '}'
	;

methodMember
	:	invokeDecl
	|	executeDecl
	|	listenDecl
	|	statementDecl
//	|	methodCallDecl
	;

optionDecl
	:	'option'^ Identifier? formalParameters? ':' optionBody
	;

optionBody
	:	'{' (optionMember)* '}'
	;

optionMember
	:	statementDecl
	//|	newObjectDecl
	//|	methodCallDecl
	;

participantDecl
	:	'participant'^ Identifier participantBody
	;

participantBody
	:	'{' (participantMember)* '}'
	;
	
participantMember
	:	assignmentDecl
	|	receiveDecl
	|	cacheDecl
	|	persistDecl
	|	scheduleDecl
	|	methodDecl
	;

persistDecl
	:	'persist'^ Identifier persistBody
	;

persistBody
	:	'{' (persistMember)* '}'
	;

persistMember
	:	assignmentDecl
	|	itemsDecl
	;

protocolDecl
	:	'protocol'^ Identifier protocolBody
	;

protocolBody
	:	'{' protocolMember* '}'
	;
	
protocolMember
	:	assignmentDecl
	|	roleDecl
	|	groupDecl
	|	channelDecl
	|	participantDecl
	|	cacheDecl
	|	persistDecl
	|	scheduleDecl
	;

receiveDecl
	:	'receive'^ Identifier formalParametersSignature throwsBody? (receiveBody | ';')
	;

receiveBody
	:	'{' (receiveMember)* '}'
	;

throwsBody
	:	'throws'^ throwsList
	;
	
throwsList
	:	throwsListDecls?
	;
	
throwsListDecls
	:	throwsListDeclsRest
	;
	
throwsListDeclsRest
	:	Identifier (',' throwsListDeclsRest)?
	;
	
receiveMember
	:	assignmentDecl
	|	optionDecl
	|	executeDecl
	|	listenDecl
	|	invokeDecl
	|	statementDecl
//	|	methodCallDecl
	;

roleDecl
	:	'role'^ Identifier roleBody
	;

roleBody
	:	'{' (roleMember)* '}'
	;

roleMember
	:	';'
	;

scheduleDecl
	:	'schedule'^ Identifier scheduleBody
	;

scheduleBody
	:	'{' (scheduleMember)* '}'
	;
	
scheduleMember
	:	assignmentDecl
	|	receiveDecl
	|	invokeDecl
	|	schedulableCommandDecl
//	|	methodCallDecl
	;
	
schedulableCommandDecl
	:	'end'^ ';'
	|	'post'^ Identifier formalParameters? ';'
	|	'reply'^ Identifier formalParameters? ';'
	|	'send'^ qualifiedName formalParameters? ';'
	|	'throw'^ Identifier formalParameters? ';'
	|	';'
	;
	
timeoutDecl
	:	'timeout'^ ':' timeoutBody 
	;

timeoutBody
	:	'{' (timeoutMember)* '}'
	;

timeoutMember
	:	optionDecl
	|	statementDecl
//	|	methodCallDecl
	;



typeRef
	:	Identifier ':' Identifier
	;

//typeRef
//	:	Identifier
//	|	Identifier ':' Identifier
//	;

//qualifiedTypeRef
//	:	Identifier ':' Identifier -> ^(imaginaryDecl Identifier ':' Identifier)
//	;

//imaginaryDecl
//	;
	
formalParameters
	:	'(' formalParameterDecls? ')'
	;
	
formalParameterDecls
	:	formalParameterDeclsRest
	;
	
formalParameterDeclsRest
	:	qualifiedName (',' formalParameterDecls)?
	;

formalParametersSignature
	:	'(' formalParameterSignatureDecls? ')'
	;
	
formalParameterSignatureDecls
	:	formalParameterSignatureDeclsRest
	;
	
formalParameterSignatureDeclsRest
	:	type Identifier (',' formalParameterSignatureDecls)?
	;

qualifiedNameList
	:	qualifiedName (',' qualifiedName)*
	;

qualifiedName
	:	(identifier '.') => identifier '.' qualifiedName
	|	identifier
	;



identifier 
	:	Identifier
	|	keyword
	;

keyword
	:	'exception'
	|	'message'
	;
	


/* Types */

LevelType
	:	'global'
	|	'local'
	|	'none'
	;

ScopeType
	:	'application'
	|	'process'
	|	'service'
	|	'session'
	|	'invocation'
	|	'event'
	|	'none'
	;

TransactionType
	:	'mandatory'
	|	'required'
	|	'requires new'
	|	'supports'
	|	'none'
	;


/* Reserved word tokens */

MESSAGE : 'message' ;

THROWS : 'throws' ':' ;

EXCEPTION : 'exception' ;


/* Literals */

TRUE : 'true' ;
	
FALSE : 'false' ;

NULL : 'null' ;

IntegerLiteral : IntegerNumber IntegerValueSuffix? ;

DoubleLiteral : NonIntegerNumber DoubleValueSuffix? ;

FloatLiteral : NonIntegerNumber FloatValueSuffix ;

TimeoutLiteral : ('1'..'9' '0'..'9'*) TimeValueSuffix? ;

fragment
IntegerValueSuffix : ('l'|'L') ;

fragment
DoubleValueSuffix : ('d'|'D') ;

fragment
FloatValueSuffix : ('f'|'F') ;

fragment
TimeValueSuffix : ('s'|'m'|'h') ;

	
fragment
IntegerNumber
	:	'0' 
	|	'1'..'9' ('0'..'9')*
	;

fragment
HexPrefix
	:	'0x' | '0X'
	;
		
fragment
HexDigit
	:	('0'..'9'|'a'..'f'|'A'..'F')
	;

fragment
LongSuffix
	:	'l' | 'L'
	;


fragment
NonIntegerNumber
	:	('0' .. '9')+ '.' ('0' .. '9')* Exponent?	
	|	'.' ( '0' .. '9' )+ Exponent?	
	|	('0' .. '9')+ Exponent	 
	|	('0' .. '9')+ 
	|	HexPrefix (HexDigit )* (()
		| ('.' (HexDigit )*)) 
		( 'p' | 'P' ) 
		( '+' | '-' )? 
		( '0' .. '9' )+
	;
		
fragment 
Exponent	
	:	( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ 
	;
	
fragment 
FloatSuffix
	:	'f' | 'F' 
	;	  

fragment
DoubleSuffix
	:	'd' | 'D'
	;

CHARLITERAL
	:	'\'' (EscapeSequence | ~( '\'' | '\\' | '\r' | '\n' )) '\''
	; 

STRINGLITERAL
	:	'"' (EscapeSequence | ~( '\\' | '"' | '\r' | '\n' ))* '"' 
	;

fragment
EscapeSequence 
	:	'\\' ('b' 
		|	't' 
		|	'n' 
		|	'f' 
		|	'r' 
		|	'\"' 
		|	'\'' 
		|	'\\' 
		|	('0'..'3') ('0'..'7') ('0'..'7')
		|	('0'..'7') ('0'..'7') 
		|	('0'..'7')
		)			
	;


WS
	:	(' ' | '\r' | '\t' | '\u000C' | '\n') { $channel=HIDDEN; }
	;

COMMENT
	:	'/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
	;

LINE_COMMENT
	:	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
	;

	
	
Identifier 
	:	Letter (Letter | Digit)*
	;
	
fragment
Letter
	:	'\u0024'
	|	'\u0041'..'\u005a'
	|	'\u005f'
	|	'\u0061'..'\u007a'
	;

fragment
Digit
	:	'\u0030'..'\u0039'
	;
	
