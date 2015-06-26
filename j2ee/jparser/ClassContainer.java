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
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the isNotSupport.
	 */
	public boolean isNotSupport() {
		return isNotSupport;
	}

	/**
	 * @param isNotSupport
	 *            The isNotSupport to set.
	 */
	public void setNotSupport(boolean isNotSupport) {
		this.isNotSupport = isNotSupport;
	}

	/**
	 * @return Returns the methods.
	 */
	public List<MethodContainer> getMethods() {
		return methods;
	}

	/**
	 * @param methods
	 *            The methods to set.
	 */
	public void setMethods(List<MethodContainer> methods) {
		this.methods = methods;
	}

}

class MethodContainer {
	private String Signature;

	private int methodId;

	private List<Integer> invokerList = new LinkedList<Integer>();

	private boolean isNotSupport = false;

	/**
	 * @return Returns the signature.
	 */
	public String getSignature() {
		return Signature;
	}

	/**
	 * @param signature
	 *            The signature to set.
	 */
	public void setSignature(String signature) {
		Signature = signature;
	}

	/**
	 * @return Returns the methodId.
	 */
	public int getMethodId() {
		return methodId;
	}

	/**
	 * @param methodId
	 *            The methodId to set.
	 */
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	/**
	 * @return Returns the invokerList.
	 */
	public List<Integer> getInvokerList() {
		return invokerList;
	}

	/**
	 * @return Returns the isNotSupport.
	 */
	public boolean isNotSupport() {
		return isNotSupport;
	}

	/**
	 * @param invokerList
	 *            The invokerList to set.
	 */
	public void setInvokerList(List<Integer> invokerList) {
		this.invokerList = invokerList;
	}

	/**
	 * @param isNotSupport
	 *            The isNotSupport to set.
	 */
	public void setNotSupport(boolean isNotSupport) {
		this.isNotSupport = isNotSupport;
	}

}
