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
public class JavaExpression {

	/*
	 * indicate which type the expression is
	 */
	private JavaExpressionType expressionType;

	/*
	 * content String of the current Expression
	 */
	private String[] expressionContent;



	// --------------constructors----------------------

	/**
	 * 
	 */
	public JavaExpression() {

	}


	// ---------------utility methods--------------------

	/*
	 * parse the Expression string content to return which type it is
	 */
	public JavaExpressionType getJavaExpressionType() {

		return JavaExpressionType.ARITHMETIC_EXPRESSION;
	}

	/**
	 * used to parse the expression String content
	 */
	public void parseExpression() {

	}



	// -----------------getters&setters-----------------------


	public void setExpressionType(JavaExpressionType expressionType) {
		this.expressionType = expressionType;
	}

	public JavaExpressionType getExpressionType() {
		return expressionType;
	}

}
