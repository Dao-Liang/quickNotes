package br.com.riskoffice.ejb3dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * object to hold the fields and methods data of some class
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jan 16, 2015 liang.guisheng Exp
 *          $
 */
public class ParsedClass {

	private static int id = 0;

	private List<ParsedMethod> methods = new ArrayList<ParsedMethod>();

	private List<ParsedField> fields = new ArrayList<ParsedField>();

	private final List<String> excludeMethodPattern = new ArrayList<String>();

	private final List<String> includeFieldPattern = new ArrayList<String>();

	private final List<String> excludeFieldPattern = new ArrayList<String>();

	private final List<String> includeMethodPattern = new ArrayList<String>();

	private final List<String> targetFieldPattern = new ArrayList<String>();

	private final List<String> targetMethodPattern = new ArrayList<String>();

	private final static String targetTestString = "TransactionAttribute(value=NOT_SUPPORTED)";

	private boolean isClassNotSupported = false;

	private boolean isAbstract = false;

	private boolean isInterface = false;

	private final static String TransactionAttributeString = "TransactionAttribute";

	private final static String TransactionNotSupportedValueString = "NOT_SUPPORTED";

	private int basketCount = 0;

	private Map<String, List<String>> fieldsInvokerMap = new HashMap<String, List<String>>();

	private Map<String, List<String>> methodsInvocationMap = new HashMap<String, List<String>>();

	private Class<?> clazz;

	private String classFileName;

	private boolean debug = true;

	public ParsedClass() {
		excludeMethodPattern.add("$");
		excludeFieldPattern.add("$");
	}

	public ParsedClass(Class<?> clazz) {
		this();
		setClazz(clazz);

		// test and set if the class has NOT_SUPPORTED annotation
		if (clazz.getAnnotations().length > 0) {
			for (Annotation a : clazz.getAnnotations()) {
				if (a.toString().contains(targetTestString)) {
					setClassNotSupported(true);
				}
			}
		}

	}

	/**
	 * get all declared fields of the class
	 */
	public void createClassDeclaredFieldList() {

		for (Field field : getClazz().getDeclaredFields()) {
			if (!isFieldExclude(field.getName()) &&
					isFieldInclude(field.getType().getCanonicalName())) {
				fields.add(new ParsedField(field));
			}
		}

	}

	/**
	 * get all declared methods of the class
	 */
	public void createClassDeclaredMethodList() {
		for (Method method : getClazz().getDeclaredMethods()) {
			if (!isMethodExclude(method.getName()) && isMethodInclude(method.getName())) {
				ParsedClass.id = ParsedClass.id + 1;
				methods.add(new ParsedMethod(method, ParsedClass.id));
			}
		}
	}

	/**
	 * parse each line the class file to get the content of class's declared
	 * methods
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void parseClassFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		boolean inMethod = false;
		boolean outterClass = false;
		boolean innerClass = false;

		String methodTitleEndChar = "{";
		ParsedMethod currentMethod = null;
		StringBuilder currentMethodContent = new StringBuilder();
		while (line != null) {

			if (isContainsModifers(line) && line.contains("class") && !outterClass) {
				outterClass = true;

				if (line.contains("abstract")) {
					setAbstract(true);
				} else if (line.contains("interface")) {
					setInterface(true);
				}

			} else if (isContainsModifers(line) && line.contains(" class ") && outterClass &&
					!innerClass) {

				innerClass = true;
				checkMethodFinish(line);

				while (line != null) {
					line = br.readLine();
					if (line != null && checkMethodFinish(line)) {
						break;
					}
				}
				innerClass = false;
			}

			if (isContainsMethodName(line) && isContainsMethodName(line) &&
					!line.contains(methodTitleEndChar) && !inMethod) {
				while (!line.contains(methodTitleEndChar)) {
					line = line + br.readLine();
				}
			}

			if ((currentMethod = isStartOfMethodLine(line)) != null) {

				while (line != null) {
					currentMethodContent.append(line + "\n");

					if (checkMethodFinish(line)) {
						currentMethod.setContent(currentMethodContent.toString());
						currentMethodContent = new StringBuilder();
						inMethod = false;
						break;
					}
					line = br.readLine();
				}
			}

			line = br.readLine();
		}

		br.close();
	}

	public boolean isPackageDeclaration(String line) {
		return line.trim().startsWith("package");
	}

	public boolean isImportDeclaration(String line) {
		return line.trim().startsWith("import");
	}

	public boolean isInterfaceDeclaration(String line) {
		return line.trim().contains("interface");
	}

	public boolean isAbstractClassDeclaration(String line) {
		return line.trim().contains("abstract class");
	}

	public boolean isPublicClassDeclaration(String line) {
		return line.trim().contains("public class");
	}

	public boolean isFieldDeclaration(String line) {
		return true;
	}

	public boolean isStaticBlock(String line) {
		return true;
	}

	public boolean isMethodDeclaration(String line) {
/*
 * 1 modifiers: public, private, ... 2 return type: java_types or void 3
 * method_name: java_nameing_rules 4 parameter list in parenthesis 5 exception
 * list 6 method_body
 */
		return true;
	}

	/**
	 * check if the current line of class file contains a java modifiers
	 * 
	 * @param line
	 * @return
	 */
	public boolean isContainsModifers(String line) {
		boolean flag = false;

		String[] modifers = { "public", "private", "void", "protected", };

		for (String m : modifers) {
			if (line.contains(m + " ")) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * check if the current line of class file contains any class's methods name
	 * 
	 * @param line
	 * @return
	 */
	private boolean isContainsMethodName(String line) {
		boolean flag = false;
		for (ParsedMethod pm : methods) {
			if (line.contains(pm.getSimpleName() + "(")) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * check if the current line of the class file is the start line of a
	 * method's body
	 * 
	 * @param line
	 * @return
	 */
	private ParsedMethod isStartOfMethodLine(String line) {

		ParsedMethod found = null;

		for (ParsedMethod method : methods) {
			Pattern startP = Pattern.compile(method.getTitlePatternString());
			Matcher startM = startP.matcher(line);
			if (startM.find()) {
				found = method;
			}
		}
		return found;
	}

	/**
	 * check if method's body is finished when parse the class file line
	 * 
	 * @param line
	 * @return
	 */
	private boolean checkMethodFinish(String line) {
		parseLeftBrace(line);
		parseRightBrace(line);
		if (getBasketCount() == 0) {
			return true;
		} else {
			return false;
		}
	}

	// count "{"
	private void parseLeftBrace(String line) {

		line = line.replaceAll("\".*\"", "");
		for (int i = 0; i < line.length(); i++) {
			if (line.substring(i, i + 1).equals("{")) {
				setBasketCount(getBasketCount() + 1);
			}
		}

	}

	// count "}"
	private void parseRightBrace(String line) {
		line = line.replaceAll("\".*\"", "");
		for (int i = 0; i < line.length(); i++) {
			if (line.substring(i, i + 1).equals("}")) {
				setBasketCount(getBasketCount() - 1);
			}
		}

	}

	// parse all the delcared methods' content to find out which method invoked
	// the field
	public void createFieldsInvokerMap() {
		for (ParsedField field : getFields()) {

			if (!fieldsInvokerMap.containsKey(field.getSimpleName())) {
				fieldsInvokerMap.put(field.getSimpleName(), new ArrayList<String>());
			}

			for (ParsedMethod method : getMethods()) {

				Pattern fieldCallP = Pattern.compile("\\b" + field.getSimpleName() + "\\b");

				if (method.getContent() != null) {
					Matcher fieldCallM = fieldCallP.matcher(method.getContent().replace("\n",
							" "));

					if (fieldCallM.find()) {
						fieldsInvokerMap.get(field.getSimpleName()).add(method.getLongName());
					}

				}
			}

		}
	}

	/**
	 * parse the method invocation and create the invoke graph
	 */
	public void createMethodsInvocationMap() {

		// create method's initial call map
		for (ParsedMethod invoker : getMethods()) {

			if (!methodsInvocationMap.containsKey(invoker.getLongName())) {
				methodsInvocationMap.put(invoker.getLongName(), new ArrayList<String>());
			}

			for (ParsedMethod invoked : getMethods()) {

				if (invoker.getId() != invoked.getId()) {

					if (invoker.getContent() != null) {

						Pattern methodNameP = Pattern.compile("(\\s*(\\w*\\.)?" +
								invoked.getInvokeName() + ")");

						Matcher methodNameM = methodNameP.matcher(invoker.getContent()
								.replace("\n", "  "));

						if (methodNameM.find()) {

							if (methodNameM.group(2) != null &&
									methodNameM.group(2).contains("this")) {
								methodsInvocationMap.get(invoker.getLongName()).add(
										invoked.getLongName());

							} else if (methodNameM.group(2) == null) {
								methodsInvocationMap.get(invoker.getLongName()).add(
										invoked.getLongName());
							}

						}
					}
				}
			}

			// add the method self-invoke
			methodsInvocationMap.get(invoker.getLongName()).add(invoker.getLongName());
		}

		// create the full call map put all the list of invoked method of a
		// invoker into the invoker's invoked list
		for (ParsedMethod invoker : getMethods()) {

			for (ParsedMethod invoked : getMethods()) {
				if (invoker.getId() != invoked.getId()) {

					if (methodsInvocationMap.get(invoker.getLongName()).contains(
							invoked.getLongName())) {

						for (String invokedM : methodsInvocationMap.get(invoked.getLongName())) {
							if (!methodsInvocationMap.get(invoker.getLongName()).contains(
									invokedM)) {
								methodsInvocationMap.get(invoker.getLongName()).add(invokedM);
							}
						}
					}

				}

			}

		}
		for (ParsedMethod invoker : getMethods()) {

			for (ParsedMethod invoked : getMethods()) {
				if (invoker.getId() != invoked.getId()) {
					if (methodsInvocationMap.containsKey(invoker.getLongName())) {
						if (methodsInvocationMap.get(invoker.getLongName()).contains(
								invoked.getLongName())) {
							methodsInvocationMap.remove(invoked.getLongName());
						}
					}

				}

			}

		}

	}

	/**
	 * check if the class abused the Ejb3DAO
	 * 
	 */
	public boolean checkEjb3DaoAbuse() throws IOException {

		addIncludeFieldPattern("Ejb3DAO");
		createClassDeclaredFieldList();
		if (fields.isEmpty()) {
			return true;
		}

		createClassDeclaredMethodList();
		parseClassFile(getClassFileName());

		if (isAbstract() || isInterface()) {
			return true;
		}

		createFieldsInvokerMap();
		createMethodsInvocationMap();
		return checkFieldTopInvokerType();

	}

	/**
	 * check the Ejb3DAO fields' topmost invoker's type
	 * 
	 * @return
	 */
	public boolean checkFieldTopInvokerType() {
		boolean result = true;
		for (Entry<String, List<String>> field : getFieldsInvokerMap().entrySet()) {
			for (String invoker : field.getValue()) {

				for (Entry<String, List<String>> method : getMethodsInvocationMap().entrySet()) {
					if (method.getValue().contains(invoker)) {

						if (!isFieldTopInvokderNonTransaction(getMethodByLongName(method
								.getKey()))) {
							result = false;
						}
					}
				}

			}
		}

		return result;
	}

	/**
	 * check if the field's top invoker satisfied as a non-transaction methods
	 * or non-transaction class
	 * 
	 * @param method
	 * @return
	 */
	private boolean isFieldTopInvokderNonTransaction(ParsedMethod method) {

		if (method == null) {
			return true;
		}

		// class annotated with NOT_SUPPORTED
		if (isClassNotSupported()) {
			return true;

		} else if (method.getMethod().getAnnotations().length > 0) {

			// check methods' annotations have NOT_SUPPORTED value
			for (Annotation a : method.getMethod().getAnnotations()) {

				if (a.toString().contains(TransactionAttributeString) &&
						a.toString().contains(TransactionNotSupportedValueString)) {
					return true;
				} else if (a.toString().contains(TransactionAttributeString) &&
						!a.toString().contains(TransactionNotSupportedValueString)) {
					return false;
				}
			}
		} else if (method.getSimpleName().startsWith("set") ||
				method.getSimpleName().startsWith("get")) {
			return true;

		} else {
			return false;
		}

		return true;
	}

	private String getMethodSimpleName(String longName) {
		String simpleName = null;
		for (ParsedMethod method : methods) {
			if (method.getLongName().equals(longName)) {
				simpleName = method.getSimpleName();
			}
		}
		return simpleName;
	}

	private int getMethodIdByLongName(String longName) {
		int id = -1;
		for (ParsedMethod method : methods) {
			if (method.getLongName().equals(longName)) {
				id = method.getId();
			}
		}
		return id;
	}

	private ParsedMethod getMethodByLongName(String longName) {

		ParsedMethod result = null;
		for (ParsedMethod method : getMethods()) {
			if (method.getLongName().equals(longName)) {
				result = method;
			}
		}
		return result;
	}

	// exclude field pattern
	private boolean isFieldExclude(String fieldName) {
		boolean isExclude = false;

		for (String s : excludeFieldPattern) {
			if (fieldName.contains(s)) {
				isExclude = true;
			}
		}

		return isExclude;
	}

	// include field pattern
	private boolean isFieldInclude(String fieldTypeName) {
		boolean isInclude = false;

		if (getIncludeFieldPattern().size() == 0) {
			isInclude = true;
		} else {
			for (String s : getIncludeFieldPattern()) {
				if (fieldTypeName.toLowerCase().contains(s.toLowerCase())) {
					isInclude = true;
				}
			}
		}

		return isInclude;
	}

	// exclude some method pattern
	private boolean isMethodExclude(String methodName) {
		boolean isExclude = false;
		for (String s : excludeMethodPattern) {

			if (methodName.contains(s)) {
				isExclude = true;
			}
		}

		return isExclude;
	}

	// include method pattern
	private boolean isMethodInclude(String methodName) {
		boolean isInclude = false;
		if (getIncludeMethodPattern().size() == 0) {
			isInclude = true;
		} else {
			for (String s : getIncludeMethodPattern()) {
				if (methodName.toLowerCase().contains(s.toLowerCase())) {
					isInclude = true;
				}
			}
		}

		return isInclude;
	}

	private void print(Object msg, Object... objects) {
		if (isDebug()) {
			if (objects.length == 0) {
				System.out.println("Parsed Class : " + msg);
			} else {
				System.out.printf("Parsed Class : " + msg, objects);
			}
		}
	}

	public void displayField() {
		for (ParsedField field : fields) {
			print("----Display Field  " + field.getSimpleName());
		}
	}

	public void displayMethod() {

		int count = 0;
		for (ParsedMethod method : methods) {
			count++;
			print("---- Method Name: " + method.getSimpleName());
			print("---- Method long name: " + method.getLongName());
			print("---- Method title Pattern: " + method.getTitlePatternString());
		}
		print("----method count:" + count);
	}

	public void displayMethodContent() {
		int methodCount = 0;
		for (ParsedMethod method : methods) {
			if (method.getContent() != null) {
				methodCount++;
				print("----DisplayMethodContent  \n" + method.getSimpleName());
				print("----Method title Pattern " + method.getTitlePatternString());
				print("----Method Long Name: " + method.getLongName());
				System.out.println(method.getContent());
			}
		}
		print("Non-null method count: " + methodCount);
	}

	public void displayFieldsInvokerMap() {
		print("---- Display Field Invoker Map \n");
		for (Entry<String, List<String>> field : fieldsInvokerMap.entrySet()) {
			System.out.print(field.getKey() + " : ");

			for (String invoker : field.getValue()) {
				System.out.print(getMethodSimpleName(invoker) + " - " +
						getMethodIdByLongName(invoker) + " ,");
			}
			System.out.println();
		}

	}

	public void displayMethodsInvocationMap() {
		print("---- Display Method Invocation Map \n");
		for (Entry<String, List<String>> method : methodsInvocationMap.entrySet()) {
			System.out.print(getMethodSimpleName(method.getKey()) + " " +
					getMethodIdByLongName(method.getKey()) + " : ");
			for (String invoked : method.getValue()) {
				System.out.print(" <- " + getMethodIdByLongName(invoked) + "[" +
						getMethodSimpleName(invoked) + "]");
			}
			System.out.println();
		}
	}

	public List<ParsedMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<ParsedMethod> methods) {
		this.methods = methods;
	}

	public List<ParsedField> getFields() {
		return fields;
	}

	public void setFields(List<ParsedField> fields) {
		this.fields = fields;
	}

	public Map<String, List<String>> getFieldsInvokerMap() {
		return fieldsInvokerMap;
	}

	public void setFieldsInvokerMap(Map<String, List<String>> fieldsInvokerMap) {
		this.fieldsInvokerMap = fieldsInvokerMap;
	}

	public Map<String, List<String>> getMethodsInvocationMap() {
		return methodsInvocationMap;
	}

	public void setMethodsInvocationMap(Map<String, List<String>> methodsInvocationMap) {
		this.methodsInvocationMap = methodsInvocationMap;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getClassFileName() {
		return classFileName;
	}

	public void setClassFileName(String classFileName) {
		this.classFileName = classFileName;
	}

	public int getBasketCount() {
		return basketCount;
	}

	public void setBasketCount(int basketCount) {
		this.basketCount = basketCount;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public List<String> getExcludeMethodPattern() {
		return excludeMethodPattern;
	}

	public List<String> getExcludeFieldPattern() {
		return excludeFieldPattern;
	}

	public List<String> getTargetFieldPattern() {
		return targetFieldPattern;
	}

	public List<String> getIncludeFieldPattern() {
		return includeFieldPattern;
	}

	public List<String> getIncludeMethodPattern() {
		return includeMethodPattern;
	}

	public List<String> getTargetMethodPattern() {
		return targetMethodPattern;
	}

	public void addExcludeFieldPattern(String excludeFieldPattern) {
		getExcludeFieldPattern().add(excludeFieldPattern);
	}

	public void addExcludeMethodPattern(String excludeMethodPattern) {
		getExcludeMethodPattern().add(excludeMethodPattern);
	}

	public void addIncludeFieldPattern(String includeFieldPattern) {
		getIncludeFieldPattern().add(includeFieldPattern);
	}

	public void addIncludeMethodPattern(String includeMethodPattern) {
		getIncludeMethodPattern().add(includeMethodPattern);
	}

	public void addTargetFieldPattern(String targetFieldPattern) {
		getTargetFieldPattern().add(targetFieldPattern);
	}

	public void addTargetMethodPattern(String targetMethodPattern) {
		getTargetMethodPattern().add(targetMethodPattern);
	}

	public void removeExcludeFieldPattern(String excludeFieldPattern) {
		getExcludeFieldPattern().remove(excludeFieldPattern);
	}

	public void removeExcludeMethodPattern(String excludeMethodPattern) {
		getExcludeMethodPattern().remove(excludeMethodPattern);
	}

	public void removeIncludeFieldPattern(String includeFieldPattern) {
		getIncludeFieldPattern().remove(includeFieldPattern);
	}

	public void removeIncludeMethodPattern(String includeMethodPattern) {
		getIncludeMethodPattern().remove(includeMethodPattern);
	}

	public void removeTargetFieldPattern(String targetFieldPattern) {
		getTargetFieldPattern().remove(targetFieldPattern);
	}

	public void removeTargetMethodPattern(String targetMethodPattern) {
		getTargetMethodPattern().remove(targetMethodPattern);
	}

	public boolean isClassNotSupported() {
		return isClassNotSupported;
	}

	public void setClassNotSupported(boolean isClassNotSupported) {
		this.isClassNotSupported = isClassNotSupported;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

}
