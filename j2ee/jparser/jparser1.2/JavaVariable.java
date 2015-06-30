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
public class JavaVariable {

	/*
	 * used to indicate which type the variable is
	 */
	private String variableType;

	/*
	 * an identifier to the variable
	 */
	private String variableName;

	public JavaVariable() {

	}

	public JavaVariable(String variableType, String variableName) {

		this.variableType = variableType;
		this.variableName = variableName;
	}

	// -----------getters&&setters--------------

	public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

}
