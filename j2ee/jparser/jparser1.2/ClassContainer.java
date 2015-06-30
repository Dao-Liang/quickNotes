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
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 22, 2015 liang.guisheng Exp
 *          $
 */
public class ClassContainer {

	private String className;

	private String fileName;

	private boolean isNotSupport = false;

	private List<MethodContainer> methods = new LinkedList<MethodContainer>();

	public ClassContainer() {

	}

	public ClassContainer(String className) {
		this.className = className;
	}

	/**
	 * used to add a method object into class's methods' list
	 * 
	 * @param method
	 */
	public void addMethod(MethodContainer method) {
		if (!methods.contains(method)) {

			this.methods.add(method);
		} else {
			System.err.println("******----Duplicated method name: " + method.getMethodName());
		}
	}

	/**
	 * get class' method by its name
	 * 
	 * @param methodName
	 * @return
	 */
	public MethodContainer getMethodByName(String methodName) {
		MethodContainer target = null;
		for (MethodContainer method : methods) {
			if (method.getMethodName().equals(methodName)) {
				target = method;
				break;
			}
		}

		return target;
	}

	/**
	 * check if method is already exists in class' methods list
	 * 
	 * @param method
	 * @return
	 */
	public boolean isMethodExists(MethodContainer method) {
		return methods.contains(method);

	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isNotSupport() {
		return isNotSupport;
	}

	public void setNotSupport(boolean isNotSupport) {
		this.isNotSupport = isNotSupport;
		if (isNotSupport) {
			for (MethodContainer method : methods) {
				method.setNotSupport(isNotSupport);
			}
		}
	}

	public List<MethodContainer> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodContainer> methods) {
		this.methods = methods;
	}

	@Override
	public String toString() {

		String result = this.getClassName();
		result += "\n--";
		for (MethodContainer method : methods) {
			result += method.getMethodName() + " : ";
		}

		return result;
	}

}

class MethodContainer {
	private String Signature;

	private String methodName;

	private int methodId;

	private List<Integer> invokerList = new LinkedList<Integer>();

	private boolean isNotSupport = false;

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public int getMethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public List<Integer> getInvokerList() {
		return invokerList;
	}

	public boolean isNotSupport() {
		return isNotSupport;
	}

	public void setInvokerList(List<Integer> invokerList) {
		this.invokerList = invokerList;
	}

	public void setNotSupport(boolean isNotSupport) {
		this.isNotSupport = isNotSupport;
	}

	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
