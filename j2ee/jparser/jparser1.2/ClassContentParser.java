/*
 * Created on Jun 22, 2015
 *
 * $HeadURL$
 * $Date: Jun 22, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * class parse the top class file
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 22, 2015 liang.guisheng Exp
 *          $
 */
public class ClassContentParser {

	private final static String[] keywords = {
			"abstract",
			"continue",
			"for",
			"new",
			"switch",
			"goto",
			"package",
			"synchronized",
			"boolean",
			"do",
			"if",
			"private",
			"this",
			"break",
			"double",
			"implements",
			"protected",
			"throw",
			"byte",
			"else",
			"import",
			"public",
			"throws",
			"case",
			"enum",
			"instanceof",
			"return",
			"transient",
			"catch",
			"extends",
			"int",
			"short",
			"try",
			"char",
			"final",
			"interface",
			"static",
			"void",
			"class",
			"finally",
			"long",
			"strictfp",
			"volatile",
			"const",
			"float",
			"native",
			"super",
			"while" };

	private static String[] REPLACE_TOKENS = { "\"", "(", ")", "{", "}", ";", "," };

	private static String[] JAVA_TYPE_TOKENS = { "class", "enum", "interface" };

	private static String[] JAVA_ARITHMETIC_OPERATORS = {
			"+",
			"-",
			"*",
			"/",
			"%",
			"&",
			"^",
			"<<",
			">>",
			">>>",
			"|" };

	private static String[] JAVA_RELATIONAL_OPERATORS = {
			"!",
			"==",
			"!=",
			">",
			"<",
			">=",
			"<=",
			"||",
			"&&",
			"instanceof", };

	private static String[] JAVA_ASSIGNMENT_OPERATORS = {
			"=",
			"+=",
			"-=",
			"*=",
			"/=",
			"%=",
			"&=",
			"^=",
			"|=",
			"<<=",
			">>==",
			">>>=" };

	private static String[] JAVA_UNARY_OPERATORS = { "+", "-", "++", "--", "~" };

	private static String[] JAVA_TERNARY_OPERATOR = { "?:", };

	// the java constant words used to parse java statement
	public final static List<String> keywordList = Arrays.asList(keywords);

	public final static List<String> javaTypeList = Arrays.asList(JAVA_TYPE_TOKENS);

	public final static List<String> javaArithmeticOperators = Arrays
			.asList(JAVA_ARITHMETIC_OPERATORS);

	public final static List<String> javaRelationalOperators = Arrays
			.asList(JAVA_RELATIONAL_OPERATORS);

	public final static List<String> javaAssignmentOperators = Arrays
			.asList(JAVA_ASSIGNMENT_OPERATORS);

	public final static List<String> javaUnaryOperators = Arrays.asList(JAVA_UNARY_OPERATORS);

	public final static List<String> javaTernaryOperators = Arrays
			.asList(JAVA_TERNARY_OPERATOR);

	// block delimiter char
	private static String BLOCK_BEGIN = "{";

	private static String BLOCK_END = "}";

	// statement determine char
	private static String STATEMENT_END = ";";

	private static JavaBlock mainTypeBlock;

	public static boolean isInMainTypeBlock = false;

	// used to check if entered specific statements
	private static boolean isInPackageStatement = false;

	private static boolean isInImportStatement = false;

	private static boolean isInAnnotatioinStatement = false;

	private static boolean isInTypeStatement = false;

	private static boolean isInIfStatement = false;

	private static boolean isInWhileStatement = false;

	private static boolean isInDoWhileStatement = false;

	private static boolean isInForStatement = false;

	private static boolean isInSwitchStatement = false;

	// used to control annotation statement

	private static int annotationParenthesesCount = 0;

	// used to check bracket balance
	private static int typeBracketCount = 0;

	// used to check if entered specific blocks
	private static boolean isInString = false;

	private static boolean isInTypeBlock = false;

	// an String expression

	private static final String targetAnnotation = "TransactionAttributeType.NOT_SUPPORTED";

	public static String targetImportedClass = "";

	public static String targetImportedClassSimpleName = "";

	public static ClassContainer clazz = null;

	/**
	 * 
	 * 
	 * 
	 * to get the variable declaration statement which type is the target
	 * imported class
	 */
	public static Pattern getTargetTypeVariableDeclarationPattern() {

		String pattern = "((" + targetImportedClassSimpleName + ")\\s+([\\w\\d_]+))";

		return Pattern.compile(pattern);
	}

	/**
	 * replace some special tokens to split the content into tokens
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceSpecialTokens(String content) {

		for (String token : REPLACE_TOKENS) {
			content = content.replace(token, " " + token + " ");
		}
		content = content.replace("@", " " + "@");
		return content;
	}

	/**
	 * split all the words in class content to get a token list
	 * 
	 * @param classContent
	 * @return
	 */
	public static String[] getClassTokens(String classContent) {

		classContent = replaceSpecialTokens(classContent);

		return classContent.split("\\s+");
	}

	/**
	 * parse all the tokens in class file content
	 * 
	 * @param tokens
	 */
	public static void parseClassTokens(String[] tokens) {

		StringBuilder sb = new StringBuilder();

		String currentToken;
		String nextToken;
		JavaStatement targetImportStatement = null;

		boolean foundTargetAnnotationForType = false;

		// use index to iterate elements in tokens
		for (int i = 0; i < tokens.length; i++) {

			currentToken = tokens[i].trim();

			sb.append(currentToken + " ");

			/*
			 * package statement
			 */

			if (currentToken.equals("package") && !isInPackageStatement &&
					isOutsideTypeBlock()) {
				isInPackageStatement = true;
				continue;
			} else if (isInPackageStatement) {

				if (currentToken.equals(STATEMENT_END)) {
					isInPackageStatement = false;

					sb = new StringBuilder();
					continue;
				}
				continue;
			}

			/*
			 * import statement
			 */

			else if (currentToken.equals("import") && !isInImportStatement &&
					isOutsideTypeBlock())

			{
				isInImportStatement = true;
				continue;
			}

			else if (isInImportStatement && isOutsideTypeBlock())

			{

				if (currentToken.equals(STATEMENT_END)) {
					isInImportStatement = false;

					if (sb.toString().contains(targetImportedClass))

					{
						targetImportStatement = new JavaStatement();
// System.err.println("----Invoked Imported Class: " + sb.toString());

					}

					sb = new StringBuilder();
					continue;
				}

				continue;
			}
			/*
			 * annotation statement
			 */
			else if (currentToken.startsWith("@") && !currentToken.equals("@interface") &&
					!isInAnnotatioinStatement && isOutsideTypeBlock())

			{
				isInAnnotatioinStatement = true;

				nextToken = tokens[i + 1];

				// simple annotation
				if (!nextToken.equals("(")) {
					isInAnnotatioinStatement = false;
					sb = new StringBuilder();
					continue;
				}

				continue;

			}

			else if (isInAnnotatioinStatement && isOutsideTypeBlock()) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (currentToken.equals("(") && !isInString) {
					annotationParenthesesCount++;
				} else if (currentToken.equals(")") && !isInString) {
					annotationParenthesesCount--;
				}

				if (annotationParenthesesCount == 0) {

					if (sb.toString().contains(targetAnnotation)) {

						// set target annotation for type definition
						foundTargetAnnotationForType = true;

					}

					isInAnnotatioinStatement = false;
					sb = new StringBuilder();
					continue;

				}
				continue;

			}

			/*
			 * 
			 * found type define statement keywords
			 */
			else if (javaTypeList.contains(currentToken) && !isInTypeStatement) {

				isInTypeStatement = true;

				continue;
			}

			/*
			 * continue type define statement process
			 */
			else if (isInTypeStatement) {

				if (currentToken.equals("{")) {
					typeBracketCount++;

					isInTypeStatement = false;

					isInTypeBlock = true;

					if (mainTypeBlock == null) {
						isInMainTypeBlock = true;
					}

					continue;

				}

				continue;
			}

			/*
			 * main type define block
			 */
			else if (isInMainTypeBlock && isInTypeBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (currentToken.equals(BLOCK_BEGIN) && !isInString) {
					typeBracketCount++;
				} else if (currentToken.equals(BLOCK_END) && !isInString) {
					typeBracketCount--;
				}

				if (typeBracketCount == 0) {

					isInMainTypeBlock = false;
					isInTypeBlock = false;

					// ------found block and start---------------
					mainTypeBlock = new JavaBlock(JavaBlockType.TYPE_BLOCK, sb.toString()
							.split("\\s+"), foundTargetAnnotationForType);

					mainTypeBlock.setTargetImportTypeName(targetImportedClassSimpleName);
					if (targetImportStatement != null) {
						mainTypeBlock.addStatement(targetImportStatement);
					}
					mainTypeBlock.setClazz(clazz);

					mainTypeBlock.parseBlock();
					// ------end block parse----------------------
					foundTargetAnnotationForType = false;

					sb = new StringBuilder();
					continue;
				}

				continue;

			}

			/*
			 * sibling type define block
			 */
			else if (isInTypeBlock && !isInMainTypeBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {

					isInString = !isInString;
				}

				if (currentToken.equals(BLOCK_BEGIN) && !isInString) {
					typeBracketCount++;
				} else if (currentToken.equals(BLOCK_END) && !isInString) {
					typeBracketCount--;
				}

				if (typeBracketCount == 0) {
					isInTypeBlock = false;

					// -------found sibling type block and start-----
					JavaBlock sibType = new JavaBlock(JavaBlockType.TYPE_BLOCK, sb.toString()
							.split("\\s+"), foundTargetAnnotationForType);

					sibType.setTargetImportTypeName(targetImportedClassSimpleName);
					sibType.setClazz(clazz);
					sibType.parseBlock();
					// -------end block parse

					mainTypeBlock.addSiblingBlock(sibType);
					foundTargetAnnotationForType = false;

					sb = new StringBuilder();
					continue;
				}

				continue;

			}

		}

		resetAllFlags();
		mainTypeBlock = null;

	}

	/**
	 * is currently outside type block define statement and block
	 */
	public static boolean isOutsideTypeBlock() {

		return !isInTypeStatement && !isInTypeBlock && !isInMainTypeBlock;
	}

	public static boolean isTokenInNonTypeBlock(String token) {

		return isInIfStatement || isInSwitchStatement || isInDoWhileStatement ||
				isInWhileStatement || isInForStatement || isInTypeStatement;

	}

	public static void stringContextToggle(String token) {
		if (token.equals("\"") && !isInString) {
			isInString = !isInString;
		} else if (token.equals("\"") && isInString) {
			isInString = !isInString;
		}

	}

	public static void bracketCountAdjust(String type) {

	}

	public static void resetAllFlags() {

		isInPackageStatement = false;
		isInImportStatement = false;
		isInAnnotatioinStatement = false;
		isInTypeStatement = false;
		isInIfStatement = false;
		isInWhileStatement = false;
		isInDoWhileStatement = false;
		isInForStatement = false;
		isInSwitchStatement = false;
		annotationParenthesesCount = 0;
		typeBracketCount = 0;
		isInString = false;
		isInTypeBlock = false;
	}

}
