/*
 * Created on Jun 19, 2015
 *
 * $HeadURL$
 * $Date: Jun 19, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 19, 2015 liang.guisheng Exp
 *          $
 */
public class ScopeVariableContainer {

	private final List<Map<String, String>> variables = new LinkedList<Map<String, String>>();

	private int level = 0;

	public int getLevel() {
		return level;
	}

	/**
	 * when the parser enter a code block
	 * 
	 * initiate a map object and add it into list level++
	 */
	public void enterBlock() {
		variables.add(new HashMap<String, String>());
		level++;

	}

	/**
	 * when parser exit a code block
	 * 
	 * remove the element of current level from the list level--
	 */
	public void exitBlock() {
		variables.remove(level - 1);
		level--;
	}

	/**
	 * when find a variable definition in block, put it into this container
	 * 
	 * @param varName
	 * @param varType
	 */
	public void addLocalVariable(String varName, String varType) {

		if (variables.get(level) == null) {
			variables.add(new java.util.HashMap<String, String>());
		}

		variables.get(level).put(varName, varType);
	}

	/**
	 * when find a variable in a block, obtain its type String
	 * 
	 * 
	 * the deepest block code can access its outer variables
	 * 
	 * @param varName
	 * @return
	 */
	public String fetchLocalVariableType(String varName) {

		String varType = null;

		if (variables.get(level - 1) == null) {
			return varType;
		}

		for (int i = level - 1; i >= 0; i--) {
			if (variables.get(i).containsKey(varName)) {
				varType = variables.get(i).get(varName);
				break;
			}
		}
		return varType;
	}
}
