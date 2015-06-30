/*
 * Created on Jun 18, 2015
 *
 * $HeadURL$
 * $Date: Jun 18, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

/**
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 18, 2015 liang.guisheng Exp
 *          $
 */
public enum StatementType {

	PACKAGE_DEFINE_STATEMENT,
	IMPORT_STATEMENT,
	STATIC_STATEMENT,
	TYPE_DEFINE_STATEMENT,
	VARIABLE_DEFINE_STATEMENT,
	METHOD_DEFINE_STATEMENT,
	METHOD_INVOCATION_STATEMENT,
	ANNOTATION_STATEMENT,
	RETURN_STATEMENT,
	CONTINUE_STATEMENT,
	BREAK_STATEMENT,
	ASSIGNMENT_STATEMENT,
	EXPRESSION_STATEMENT,
	DOCUMENTATIONS,
	COMMENTS,
	CONDITION_STATEMENT;

	/**
	 * Java
	 */

	boolean is;

	byte by;

	char c;

}
