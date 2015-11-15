grammar Ariel;

options {
	// antlr will generate java lexer and parser
	language = Java;
	
	// generated parser should create abstract syntax tree
	output = AST;
	
	//backtrack = true; 

	//filter = true;

	//memoize = false;
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
    :   importDecl* protocolDecl*
    ;


assignmentDecl
    :   'set'^ 'role' '(' Identifier ')' ';'
    |   'set'^ 'domain' '(' Identifier ')' ';'
    |   'add'^ 'channel' '(' Identifier ')' ';'
    |   'set'^ 'level' '(' LevelType ')' ';'
    |   'set'^ 'scope' '(' ScopeType ')' ';'
    |   'set'^ 'source' '(' Identifier ')' ';'
    |   'set'^ 'timeout' '(' TimeoutLiteral ')' ';'
    |   'set'^ 'condition' '(' Identifier ')' ';'
    |   'set'^ 'transaction' '(' TransactionType ')' ';'
    |   'set'^ 'participant' '(' Identifier ')' ';'
    |   'set'^ 'restriction' '(' qualifiedName ')' ';'
    |   'set'^ 'maxResponse' '(' IntegerLiteral ')' ';'
    |   'set'^ 'minResponse' '(' IntegerLiteral ')' ';'
    |   'set'^ 'backingStore' '(' Identifier ')' ';'
    |   'set'^ 'namespace' '(' Identifier ')' ';'
    ;

branchDecl
    :   'branch'^ Identifier? ':' branchBody 
    ;

branchBody
    :   branchMember
    |   '{' branchMember* '}'
    ;

branchMember
	:	optionDecl
    |   listenDecl
    |   commandDecl
	|	methodCall
    ;

cacheDecl
    :   'cache'^ Identifier cacheBody
    ;

cacheBody
    :   '{' cacheMember* '}'
    ;

cacheMember
    :   assignmentDecl
    |	itemsDecl
    ;

channelDecl
    :   'channel'^ Identifier '{' channelBody* '}'
    ;

channelBody
    :   'add' 'client' '(' Identifier ')' ';'
    |   'add' 'service' '(' Identifier ')' ';'
    |   'add' 'manager' '(' Identifier ')' ';'
    |   'set' 'domain' '(' Identifier ')' ';'
	|	';'
    ;

commandDecl
	:	'end'^ ';'
    |   'post'^ Identifier formalParameters? ';'
    |   'reply'^ Identifier formalParameters? ';'
    |   'send'^ qualifiedName formalParameters? ';'
    |   'throw'^ Identifier formalParameters? ';'
	|	';'
    ;

exceptionDecl
    :   'exception'^ ':' exceptionBody 
    ;

exceptionBody
    :   exceptionMember
    |   '{' exceptionMember* '}'
    ;

exceptionMember
	:	optionDecl
	|	commandDecl
	|	methodCall
    ;

executeDecl
    :   'execute'^ executeBody
    |   'execute'^ 'then' Identifier formalParameters executeBody 
    ;

executeDeclRest
    :   formalParameters
        (   executeBody
        |   ';'
        )
    ;

executeBody
    :   '{' executeMember* '}'
    ;
    
executeMember
    :   assignmentDecl
    |	branchDecl
    |	exceptionDecl
    |	timeoutDecl
    ;

groupDecl
    :   'group'^ Identifier groupBody
    ;

groupBody
    :   '{' groupMember* '}'
    ;

groupMember
    :   assignmentDecl
    ;

importDecl
    :   'import'^ qualifiedName ('.' '*')? ';'
    ;

invokeDecl
    :   'invoke'^ Identifier '.' Identifier formalParameters invokeBody
    ;

invokeBody
    :   '{' invokeMember* '}'
    ;

invokeMember
    :   assignmentDecl
    |   messageDecl
	|	exceptionDecl
	|	timeoutDecl
    ;

itemsDecl
    :   'items'^ itemsBody
    ;

itemsBody
    :   '{' itemsMember* '}'
    ;

itemsMember
    :   itemDecl
    ;

itemDecl
    :   'List'^ '<' typeRef '>' Identifier itemDeclRest
    |   'Map'^ '<' typeRef ',' typeRef '>' Identifier itemDeclRest
    ;

itemDeclRest
	:	'{' itemMember* '}'
	|	';'
	;

itemMember
	:	'index'^ '(' Identifier ')' ';'
	;

listenDecl
    :   'listen'^ Identifier formalParameters? ':' listenBody 
    ;

listenBody
    :   '{' listenMember* '}'
    ;

listenMember
	:	optionDecl
    |   commandDecl
	|	methodCall
    ;

messageDecl
    :   'message'^ Identifier formalParameters? ':' messageBody 
    ;

messageBody
    :   messageMember
    |   '{' messageMember* '}'
    ;

messageMember
	:	optionDecl
    |   commandDecl
	|	newObjectDecl
    |	methodCall
    ;

methodCall
    :   qualifiedName formalParameters ';'
    ;

methodDecl
    :   qualifiedName^ formalParametersSignature methodBody
    ;

methodBody
    :   '{' methodMember* '}'
    ;

methodMember
    :	invokeDecl
    |   executeDecl
    |   listenDecl
    |   commandDecl
    |	methodCall
    ;

newObjectDecl
    :   typeDeclaratorId Identifier '=' 'new' typeDeclaratorId '(' ')' ';'
    ;

optionDecl
    :   'option'^ Identifier? formalParameters? ':' optionBody
    ;

optionBody
    :   optionMember
    |   '{' optionMember* '}'
    ;

optionMember
    :   commandDecl
    |	methodCall
    ;

participantDecl
    :   'participant'^ Identifier participantBody
    ;

participantBody
    :   '{' participantMember* '}'
    ;
    
participantMember
    :   assignmentDecl
    |   receiveDecl
	|	cacheDecl
	|	persistDecl
    |	methodDecl
    ;

persistDecl
    :   'persist'^ Identifier persistBody
    ;

persistBody
    :   '{' persistMember* '}'
    ;

persistMember
    :   assignmentDecl
    |	itemsDecl
    ;

protocolDecl
    :   'protocol'^ Identifier protocolBody
    ;

protocolBody
    :   '{' protocolMember* '}'
    ;
    
protocolMember
    :   roleDecl
    |   groupDecl
    |   channelDecl
    |   participantDecl
	|	cacheDecl
	|	persistDecl
    ;

receiveDecl
    :   'receive'^ Identifier formalParametersSignature (receiveBody | ';')
    ;

receiveBody
    :   '{' receiveMember* '}'
    ;
    
receiveMember
    :   assignmentDecl
    |	optionDecl
    |	executeDecl
    |	invokeDecl
    |	commandDecl
    |	methodCall
    ;

roleDecl
    :   'role'^ Identifier roleBody
    ;

roleBody
    :   '{' roleMember* '}'
    ;

roleMember
    :   ';'
    ;

timeoutDecl
    :   'timeout'^ ':' timeoutBody 
    ;

timeoutBody
    :   timeoutMember
    |   '{' timeoutMember* '}'
    ;

timeoutMember
	:	optionDecl
	|	commandDecl
	|	methodCall
    ;




	
typeRef
    :   Identifier
    |   Identifier '.' Identifier
	;

formalParameters
    :   '(' formalParameterDecls? ')'
    ;
    
formalParameterDecls
    :   formalParameterDeclsRest
    ;
    
formalParameterDeclsRest
    :   qualifiedName (',' formalParameterDecls)?
    ;

formalParametersSignature
    :   '(' formalParameterSignatureDecls? ')'
    ;
    
formalParameterSignatureDecls
    :   formalParameterSignatureDeclsRest
    ;
    
formalParameterSignatureDeclsRest
    :   typeDeclaratorId Identifier (',' formalParameterSignatureDecls)?
    ;
    
typeDeclaratorId
    :   qualifiedName ('[' ']')*
    |   'List' '<' qualifiedName ('[' ']')* '>'
    |   'Set' '<' qualifiedName ('[' ']')* '>'
    |   'Map' '<' qualifiedName ',' qualifiedName ('[' ']')* '>'
    ;


// STATEMENTS / BLOCKS

block
    :   '{' blockStatement* '}'
    ;
    
blockStatement
    :   statement
    ;

statement
    : block
    |   statementExpression ';'
    |   'end'^ ';'
    ;


// EXPRESSIONS

parExpression
    :   '(' expression ')'
    ;
    
expressionList
    :   expression (',' expression)*
    ;

statementExpression
    :   expression
    ;
    
constantExpression
    :   expression
    ;
    
expression
    :   ';'		//conditionalExpression (assignmentOperator expression)?
    ;




qualifiedNameList
    :   qualifiedName (',' qualifiedName)*
    ;

qualifiedName
    :   Identifier ('.' Identifier)*
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

IntegerLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerValueSuffix? ;

TimeoutLiteral : ('1'..'9' '0'..'9'*) TimeValueSuffix? ;

fragment
IntegerValueSuffix : ('l'|'L') ;

fragment
TimeValueSuffix : ('s'|'m'|'h') ;

Identifier 
    :   Letter (Letter | IdentifierDigit)*
    ;

/**
 * I found this char range in JavaCC's grammar, but Letter and Digit overlap.
 * Still works, but...
 */
fragment
Letter
    :	'\u0024'
	|	'\u0041'..'\u005a'
	|	'\u005f'
	|	'\u0061'..'\u007a'
	|	'\u00c0'..'\u00d6'
	|	'\u00d8'..'\u00f6'
	|	'\u00f8'..'\u00ff'
	|	'\u0100'..'\u1fff'
	|	'\u3040'..'\u318f'
	|	'\u3300'..'\u337f'
	|	'\u3400'..'\u3d2d'
	|	'\u4e00'..'\u9fff'
	|	'\uf900'..'\ufaff'
    ;

fragment
IdentifierDigit
    :	'\u0030'..'\u0039'
	|	'\u0660'..'\u0669'
	|	'\u06f0'..'\u06f9'
	|	'\u0966'..'\u096f'
	|	'\u09e6'..'\u09ef'
	|	'\u0a66'..'\u0a6f'
	|	'\u0ae6'..'\u0aef'
	|	'\u0b66'..'\u0b6f'
	|	'\u0be7'..'\u0bef'
	|	'\u0c66'..'\u0c6f'
	|	'\u0ce6'..'\u0cef'
	|	'\u0d66'..'\u0d6f'
	|	'\u0e50'..'\u0e59'
	|	'\u0ed0'..'\u0ed9'
	|	'\u1040'..'\u1049'
   ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;
