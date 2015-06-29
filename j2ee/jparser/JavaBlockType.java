/*
 * Created on Jun 23, 2015
 *
 * $HeadURL$
 * $Date: Jun 23, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

/**
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 23, 2015 liang.guisheng Exp
 *          $
 */
public enum JavaBlockType {

	// interface, class, enum [extends/implements [super classes]]{j
	TYPE_BLOCK,

	// static {...}
	STATIC_BLOCK,

	// public|private|protected|null type|void methodName([parameters]){
	METHOD_DEFINE_BLOCK,

	// new ClassName(){}
	ANONYMOUS_CLASS_BLOCK,

	// if(){}[ else if(){} [else{}]]
	IF_ELSE_BLOCK,

	// for(){}
	FOR_BLOCK,

	// while(){} / do{} while();
	DO_WHILE_BLOCK,

	WHILE_BLOCK,

	// switch(){case}
	SWITCH_BLOCK,

	// try{}catch(){}[finally{}]
	TRY_CATCH_BLOCK;
}

enum JavaStatementType {

	// package packageName;
	PACKAGE_STATEMENT,

	// import className;
	IMPORT_STATEMENT,

	// annotation blocks: @Annotation[(value,[args=value])]
	TYPE_ANNOTATION_STATEMENT,

	FIELD_ANNOTATION_STATEMENT,

	PARAMETER_ANNOTATION_STATEMENT,

	METHOD_ANNOTATION_STATEMENT,

	ANNOTATION_STATEMENT,

	SIMPLE_STATEMENT,

	// type variable=value;
	// variable=value;
	ASSIGNMENT_STATEMENT,

	// variable++/--
	UNARY_OPERATOR_STATEMNET,

	// instance.method([parameters])
	METHOD_INVOCATION_STATEMENT,

	// new ClassName();
	OBJECT_CREATE_STATEMENT,

	// type variable;
	// modifier type variableName;
	DECLARATION_STATEMENT,

	// if(){}[ else if(){} [else{}]]
	IF_ELSE_STATEMENT,

	// switch(){case}
	SWITCH_STATEMENT,

	// for(){}
	FOR_STATEMENT,

	// while(){} / do{} while();
	DO_WHILE_STATEMENT;

}

enum JavaExpressionType {
	// type variable=value;
	// variable=value;
	ASSIGNMENT_DECLARATION_EXPRESSION,

	// a[+-*/%..()]b
	ARITHMETIC_EXPRESSION,

	// variable++/--
	UNARY_OPERATOR_EXPRESSION,

	// "string value", numbers
	LITERAL_VALUE_EXPRESSION,

	// condition test: a[==,!=,<,>,<=,>=]b
	BOOLEAN_TEST_EXPRESSION,

	// new ClassName()
	OBJECT_CREATE_EXPRESSION,

	// instance.method()
	METHOD_INVOCATION_EXPRESSION;
}
