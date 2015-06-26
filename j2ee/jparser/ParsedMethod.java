/*
 * Created on Jan 8, 2015
 *
 * $HeadURL$
 * $Date: Jan 8, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class hold content body of method of some classes
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jan 9, 2015 liang.guisheng Exp $
 */
public class ParsedMethod {

	// java built-in modifier number and value mapping
	private final static Map<Integer, String> modifierMap = new HashMap<Integer, String>();
	static {
		modifierMap.put(1, "public");
		modifierMap.put(2, "private");
		modifierMap.put(4, "protected");
		modifierMap.put(8, "static");
		modifierMap.put(16, "final");
		modifierMap.put(32, "sync");
		modifierMap.put(64, "volatile");
		modifierMap.put(128, "transient");
		modifierMap.put(256, "native");
		modifierMap.put(512, "interface");
		modifierMap.put(1024, "abstract");
		modifierMap.put(2048, "strict");

	}

	// java's collection Type
	private static String[] collectionMethodType = {
			"java.util.\\w*Map",
			"java.util.\\w*Set",
			"java.util.\\w*List" };

	private static Pattern javaTypePattern = Pattern.compile(".*\\.([\\w\\[\\]]*)");

	private int id;

	private Method method;

	private String modifierName;

	private String simpleName;

	private String longName;

	private String invokeName;

	private String titlePatternString;

	private String content;

	public ParsedMethod() {
	}

	public ParsedMethod(Method method, int id) {
		setId(id);
		setMethod(method);
		setLongName(method.toString());
		setSimpleName(method.getName());
		setModifierName(getModiferString(method.getModifiers()));

		// set the method's invoked string pattern
		if (getMethod().getParameterTypes().length == 0) {
			setInvokeName(method.getName() + "\\(\\s*\\)\\s*;");
		} else {
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < getMethod().getParameterTypes().length; i++) {
				s.append("\\s*[\\w\\.\\(\\)\\[\\]\\s\\d\"\\+\\-\\*&\\{\\}]+\\s*,");
			}
			s.deleteCharAt(s.length() - 1);
			setInvokeName(method.getName() + "\\(" + s.toString() + "\\)");
		}

		// set the method's title pattern to parse method in class file
		generateMethodSimpleTitlePattern();
	}

	private String escapeRegexKeys(String pattern) {
		return pattern.replace(".", "\\.").replace("[", "\\[").replace("]", "\\]")
				.replace("{", "\\{").replace("}", "\\}").replace("(", "\\(")
				.replace(")", "\\)").replace(" ", "\\s*");
	}

	/**
	 * generate the method's title pattern string to parse method beginning in
	 * class file
	 * 
	 * <pre>
	 *  modifiers  <return type> <method name> ( <method argument list> )
	 * </pre>
	 */
	private void generateMethodSimpleTitlePattern() {
		StringBuilder titlePatternString = new StringBuilder();
		titlePatternString.append(getModifierPatternString());
		titlePatternString.append(getReturnTypePatternString());
		titlePatternString.append(method.getName());
		titlePatternString.append("\\( ");
		titlePatternString.append(getArgumentsPatternString());
		titlePatternString.append(" \\)");
		setTitlePatternString(titlePatternString.toString().replace(" ", "\\s*"));
	}

	public String getModifierPatternString() {
		return escapeRegexKeys(getModifierName() + " ");
	}

	public String getReturnTypePatternString() {
		return getSimplifiedTypeName(method.getReturnType().getCanonicalName()) + " ";
	}

	public String getArgumentsPatternString() {
		if (method.getParameterTypes().length > 0) {

			StringBuilder argPatternString = new StringBuilder();
			for (Class<?> arg : method.getParameterTypes()) {

				argPatternString.append(getSimplifiedTypeName(arg.getCanonicalName()) +
						"\\s*\\w*\\s* , ");

			}
			argPatternString.deleteCharAt(argPatternString.length() - 2);

			return argPatternString.toString();
		} else {
			return " ";
		}
	}

	private String getModiferString(int modifier) {

		int[] modifierIndexs = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };
		int transientIndex = 7;
		int index = 0;
		StringBuilder modifierString = new StringBuilder();
		List<String> modifiers = new ArrayList<String>();

		for (; index < modifierIndexs.length; index++) {

			if (modifier < modifierIndexs[index] && modifier > 0) {
				modifier = modifier - modifierIndexs[index - 1];
				if ((index - 1) == transientIndex) {
					index = 0;
					continue;
				}
				modifiers.add(modifierMap.get(modifierIndexs[index - 1]));
				index = 0;
			}
		}

		for (int i = modifiers.size() - 1; i >= 0; i--) {
			modifierString.append(modifiers.get(i) + " ");
		}
		return modifierString.toString();
	}

	public String getSimplifiedTypeName(String typeName) {
		String simpleName = "";
		if (isPrimitiveType(typeName)) {
			simpleName = getPrimitiveName(typeName).replace("[", "\\[?").replace("]", "\\]");
		} else if (isCollectionType(typeName)) {
			simpleName = getCollectName(typeName);
		} else if (isJavaSimpleType(typeName)) {
			simpleName = getJavaSimpleName(typeName).replace("[", ".*\\[?").replace("]",
					"\\]?") +
					"[^,]* ";
		}
		return simpleName;
	}

	public String getCollectName(String s) {
		String typeString = "";
		Matcher collectM = javaTypePattern.matcher(s);
		if (collectM.find()) {
			typeString = collectM.group(1) + "\\s*<.*>";
		}
		return typeString;
	}

	public String getJavaSimpleName(String s) {
		String typeString = "";
		Matcher simpleM = javaTypePattern.matcher(s);
		if (simpleM.find()) {
			typeString = simpleM.group(1);
		}
		return typeString;
	}

	private boolean isCollectionType(String longName) {
		boolean flag = false;

		for (String type : collectionMethodType) {
			Pattern typeP = Pattern.compile(type);
			Matcher typeM = typeP.matcher(longName);

			if (typeM.find()) {
				flag = true;
			}
		}
		return flag;
	}

	private boolean isJavaSimpleType(String type) {
		if (!isCollectionType(type) && !isPrimitiveType(type)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isPrimitiveType(String type) {
		if (!type.contains(".")) {
			return true;
		} else {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvokeName() {
		return invokeName;
	}

	public void setInvokeName(String invokeName) {
		this.invokeName = invokeName;
	}

	public String getTitlePatternString() {
		return titlePatternString;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public void setTitlePatternString(String titlePatternString) {
		this.titlePatternString = titlePatternString;
	}

	public String getLongName() {
		return longName;
	}

	public Method getMethod() {
		return method;
	}

	public String getModifierName() {
		return modifierName;
	}

	public String getPrimitiveName(String s) {
		return s;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Method Long Name: " + getLongName() + "\n Method Simple Name: " +
				getSimpleName() + "\n " + "\n Method Title Pattern: " +
				getTitlePatternString() + "\n Method Content: " + getContent();
	}
}
