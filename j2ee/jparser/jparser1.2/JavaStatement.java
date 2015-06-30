/*
 * Created on Jun 23, 2015
 *
 * $HeadURL$
 * $Date: Jun 23, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a statement is a execution unit in java programming which will ended with ";"
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 23, 2015 liang.guisheng Exp
 *          $
 */
public class JavaStatement {

	/*
	 * used to indicate which type the statement is
	 */
	private JavaStatementType statementType;

	/*
	 * the String content of the current Statement
	 */
	private String[] statementContent;

	private List<JavaExpression> expressions;

	public static final Pattern packageP = Pattern.compile("(package\\s+([\\w\\.\\d]+)\\s*;)");

	public static final Pattern importP = Pattern.compile("(import\\s+([\\w\\.\\d]+)\\s*;)");

	public static final Pattern typeP = Pattern
			.compile("((public|private|protected)?\\s*(static)?\\s*(abstract)?\\s*(class|enum|interface)?\\s*([\\w\\d,<>]+).*\\{)");

	public static final Pattern declarationP = Pattern
			.compile("((public|private|protected)?\\s*(final|static)?\\s*(final|static)?\\s*([\\w\\d_<>,\\[\\]]+)\\s+([\\w\\d_]+)\\s*;)");

	public final static Pattern assignmentP = Pattern
			.compile("((public|private|protected)?\\s*(static|final)?\\s*(static|final)?\\s*([\\w\\d_<,>\\[\\]]+)\\s+([\\w\\d_]+)\\s*=(.*);)");

	public static final Pattern staticP = Pattern.compile("static\\s+\\{");

	public static final Pattern methodDeclarationP = Pattern
			.compile("((public|private|protected)?\\s*(static)?\\s*([\\w\\d_<>,\\[\\]]+)\\s+([\\w\\d_<>,]+)\\s*\\((.*)\\)\\s*(throws)?(.*?)\\{)");

	public static final Pattern unaryP = Pattern
			.compile("(([\\w\\d_]+)\\+\\+|([\\w\\d_]+)--|\\+\\+([\\w\\d_]+)|--([\\w\\d_]+))");

	public static final Pattern instanceCreateP = Pattern
			.compile("(new\\s*([\\w\\d,<>_]+)\\s*\\(.*\\))");

	public static final Pattern annotationP = Pattern
			.compile("(@([\\w\\d_]+)\\s*\\(?(.*)\\))");

	public static final Pattern ifP = Pattern
			.compile("(if\\s*\\(.*\\)\\s*\\{(.*)\\}\\s*(else\\s*if\\s*\\(.*\\)\\s*\\{.*\\})*\\s*(else\\s*\\{.*\\}))");

	public static final Pattern forP = Pattern
			.compile("((for\\s*\\([^:]+:[^:]+\\)\\s*\\{.*\\})|(for\\s*\\(.*;.*;.*\\)\\s*\\{.*\\}))");

	public static final Pattern whileP = Pattern
			.compile("((do\\s*\\{.*\\}\\s*while\\s*\\(.*\\);)|(while\\s*\\(.*\\)\\s*\\{.*\\}))");

	public static final Pattern switchP = Pattern.compile("(switch\\s*\\(.*\\)\\s*\\{.*\\})");

	public static final Pattern tryP = Pattern
			.compile("(try\\s*(\\(.*\\))?\\s*\\{.*\\}\\s*catch\\s*\\(.*\\)\\s*\\{.*\\}\\s*(finally\\s*\\{.*\\})?)");

	// ----------------constructor--------------------
	public JavaStatement() {
		this.expressions = new LinkedList<JavaExpression>();
	}

	public JavaStatement(JavaStatementType type, String[] statementContent) {
		this();

		this.statementType = type;
		this.statementContent = statementContent;

		parseStatement();
	}

	// ----------------utility methods----------------

	public void addExpression(JavaExpression expression) {
		this.getExpressions().add(expression);
	}

	/*
	 * used to parse the Statement string content based on its type
	 */
	public void parseStatement() {

		switch (this.statementType) {
		case PACKAGE_STATEMENT:
			packageStatementParse();
			break;
		case IMPORT_STATEMENT:
			importStatementParse();
			break;
		case TYPE_ANNOTATION_STATEMENT:
			typeAnnotationStatementParse();
			break;
		case FIELD_ANNOTATION_STATEMENT:
			fieldAnnotationStatementParse();
			break;
		case PARAMETER_ANNOTATION_STATEMENT:
			parameterAnnotationStatementParse();
			break;
		case METHOD_ANNOTATION_STATEMENT:
			methodAnnotationStatementParse();
			break;
		case METHOD_INVOCATION_STATEMENT:
			methodInvocationStatementParse();
			break;
		case ASSIGNMENT_STATEMENT:
			assignmentStatementParse();
			break;
		case UNARY_OPERATOR_STATEMNET:
			unaryOperationStatementParse();
			break;
		case OBJECT_CREATE_STATEMENT:
			objectCreationStatementParse();
			break;
		case DECLARATION_STATEMENT:
			declarationStatementParse();
			break;
		case IF_ELSE_STATEMENT:
			ifStatementParse();
			break;
		case SWITCH_STATEMENT:
			switchStatemenetParse();
			break;
		case FOR_STATEMENT:
			forStatementParse();
			break;
		case DO_WHILE_STATEMENT:
			doWhileStatementParse();
			break;
		case ANNOTATION_STATEMENT:
			annotationStatementParse();
			break;
		case SIMPLE_STATEMENT:
			simpleStatementParse();
			break;
		}
	}

	public void simpleStatementParse() {

// System.err.println("------Simple Statement: " + String.join(" ",
// statementContent));

	}

	public void annotationStatementParse() {
// System.err
// .println("------Annotation Statement: " + String.join(" ",
// statementContent));

	}

	public void packageStatementParse() {

	}

	public void importStatementParse() {

	}

	public void typeAnnotationStatementParse() {

	}

	public void fieldAnnotationStatementParse() {

	}

	public void parameterAnnotationStatementParse() {

	}

	public void methodAnnotationStatementParse() {

	}

	public void methodInvocationStatementParse() {

	}

	public void objectCreationStatementParse() {

	}

	public void declarationStatementParse() {

	}

	public void assignmentStatementParse() {

	}

	public void unaryOperationStatementParse() {

	}

	public void ifStatementParse() {

	}

	public void switchStatemenetParse() {

	}

	public void forStatementParse() {

	}

	public void doWhileStatementParse() {

	}

	/**
	 * parse the statement string content to return which type it is
	 * 
	 * @return
	 */
	public JavaStatementType getJavaStatementType() {

		return JavaStatementType.ASSIGNMENT_STATEMENT;
	}

	public static void main(String[] args) {
		String statement1 = "try{statement}catch(Exception e){statement1}";
		String statement2 = "try(resource){statement1}catch(Exception e){statement2}";
		String statement3 = "try{statement1}catch(Exception e){statemenet2} finally{statemtn3}";
		String statement4 = "try(resource){statemnet1}catch(Exception e){statement2}finally{statement3}";

		Matcher m1 = tryP.matcher(statement1);
		Matcher m2 = tryP.matcher(statement2);
		Matcher m3 = tryP.matcher(statement3);
		Matcher m4 = tryP.matcher(statement4);

		if (m1.find()) {
			System.out.println("statemenet1 :  " + m1.group(1));
		}
		if (m2.find()) {
			System.out.println("statemenet2 :  " + m2.group(1));
		}
		if (m3.find()) {
			System.out.println("statemenet3 :  " + m3.group(1));
		}
		if (m4.find()) {
			System.out.println("statemenet4 :  " + m4.group(1));
		}
	}

// ---------------getters&setters---------------------

	public JavaStatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(JavaStatementType statementType) {
		this.statementType = statementType;
	}

	public List<JavaExpression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<JavaExpression> expressions) {
		this.expressions = expressions;
	}

	/**
	 * @return Returns the statementContent.
	 */
	public String[] getStatementContent() {
		return statementContent;
	}

	/**
	 * @param statementContent
	 *            The statementContent to set.
	 */
	public void setStatementContent(String[] statementContent) {
		this.statementContent = statementContent;
	}

}
