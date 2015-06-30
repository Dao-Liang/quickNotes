/*
 * Created on Jun 22, 2015
 *
 * $HeadURL$
 * $Date: Jun 22, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.util.LinkedList;
import java.util.List;

/**
 * class used to cache the classes of each level which invoked Ejb3Dao or its
 * related class
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 22, 2015 liang.guisheng Exp
 *          $
 */
public class Ejb3DAOInvocationContainer {

	private List<List<ClassContainer>> invocations = new LinkedList<List<ClassContainer>>();

	private int level = 0;

	public Ejb3DAOInvocationContainer() {

	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * put the current found Invoker into this container
	 * 
	 * @param invokerName
	 */
	public void putInvoker(String invokerName) {

		if (invocations.size() <= level) {
			invocations.add(level, new LinkedList<ClassContainer>());
		}

		ClassContainer clazz = new ClassContainer(invokerName);
		invocations.get(level).add(clazz);
	}

	/**
	 * get all the invokers of level
	 * 
	 * @param level
	 * @return
	 */
	public List<ClassContainer> getInvoker(int level) {
		return invocations.get(level);
	}

	/**
	 * check if invoker name already processed before
	 * 
	 * @param invokerName
	 * @return
	 */
	public boolean isInvokerExists(String invokerName) {
		for (int i = 0; i < level; i++) {
			for (ClassContainer invoker : invocations.get(i)) {
				if (invoker.getClassName().equals(invokerName)) {
					return true;
				}
			}
		}

		return false;
	}

	public void displayInvocations() {

		int l = 0;
		for (List<ClassContainer> level : invocations) {

			System.out.println("--Level :" + l);
			for (ClassContainer clazz : level) {
				System.out.println(clazz);
			}

			l++;
		}
	}

	/**
	 * using class name to get ClassContainer object
	 * 
	 * @param className
	 * @return
	 */
	public ClassContainer getClassContainerByClassName(String className) {

		ClassContainer target = null;

		for (List<ClassContainer> level : invocations) {

			for (ClassContainer clazz : level) {
				if (clazz.getClassName().equals(className)) {

					target = clazz;
					break;

				}
			}
		}

		return target;
	}

	/**
	 * @return Returns the invocations.
	 */
	public List<List<ClassContainer>> getInvocations() {
		return invocations;
	}

	/**
	 * @param invocations
	 *            The invocations to set.
	 */
	public void setInvocations(List<List<ClassContainer>> invocations) {
		this.invocations = invocations;
	}

}
