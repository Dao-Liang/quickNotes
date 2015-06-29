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
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 23, 2015 liang.guisheng Exp
 *          $
 */
public class JavaBlock {

	// match method declaration before {
	public static final Pattern shortMethodDeclarationP = Pattern
			.compile("(^(public|private|protected)?\\s*(static)?\\s*([\\w\\d<>\\s,\\[\\]]+)\\s*([\\w\\d_]+)\\s*\\()");

	private static final String targetAnnotation = "TransactionAttributeType.NOT_SUPPORTED";

	/*
	 * the string before a "{"
	 * 
	 * used to determine the Block's Type
	 */
	private String blockPrefix;

	private String[] blockContent;

	/*
	 * indicate which type the block is
	 */
	private JavaBlockType blockType;

	/*
	 * the nested blocks inside the current blockks
	 */
	private List<JavaBlock> subBlocks;

	/**
	 * the siblings blocks which defined in the same java class file
	 */
	private final List<JavaBlock> siblingBlocks;

	private final List<JavaBlock> staticBlocks;

	private final List<JavaBlock> methodBlocks;

	private final List<JavaBlock> ifBlocks;

	private final List<JavaBlock> forBlocks;

	private final List<JavaBlock> doWhileBlocks;

	private final List<JavaBlock> switchBlocks;

	private final List<JavaBlock> tryBlocks;

// private final List<JavaBlock> anonymousBlocks;

	/*
	 * the statements inside this block
	 */
	private List<JavaStatement> statements;

	private final List<JavaStatement> annotationStatements;

	/*
	 * the variable collection defined inside the block
	 */
	private List<JavaVariable> variables;

	private String blockStatement;

	private boolean isNonTransactionalBlock = false;

	/**
	 * used to help parse block nesting in class content
	 */
	private boolean isInBlock = false;

	private boolean isMainType = false;

	// ---------------constructor-----------------------------------
	public JavaBlock() {
		// initiate all these element containers
		this.subBlocks = new LinkedList<JavaBlock>();
		this.statements = new LinkedList<JavaStatement>();
		this.variables = new LinkedList<JavaVariable>();
		this.siblingBlocks = new LinkedList<JavaBlock>();
		this.staticBlocks = new LinkedList<JavaBlock>();
		this.methodBlocks = new LinkedList<JavaBlock>();
		this.ifBlocks = new LinkedList<JavaBlock>();
		this.forBlocks = new LinkedList<JavaBlock>();
		this.doWhileBlocks = new LinkedList<JavaBlock>();
		this.switchBlocks = new LinkedList<JavaBlock>();
		this.tryBlocks = new LinkedList<JavaBlock>();
// this.anonymousBlocks = new LinkedList<JavaBlock>();
		this.annotationStatements = new LinkedList<JavaStatement>();
	}

	public JavaBlock(JavaBlockType type, String[] blockContent, boolean isNonTransactional) {

		this();

		this.blockType = type;
		this.blockContent = blockContent;
		this.setNonTransactionalBlock(isNonTransactional);

		parseBlock();
	}

	// --------------utility methods------------------------------

	public void parseBlock() {
		switch (this.blockType) {
		case TYPE_BLOCK:
			typeBlockParse();
			break;
		case STATIC_BLOCK:
			staticBlockParse();
			break;
		case METHOD_DEFINE_BLOCK:
			methodBlockParse();
			break;
		case IF_ELSE_BLOCK:
			ifBlockParse();
			break;
		case FOR_BLOCK:
			forBlockParse();
			break;
		case ANONYMOUS_CLASS_BLOCK:
			anonymousBlockParse();
			break;
		case DO_WHILE_BLOCK:
			doWhileBlockParse();
			break;
		case SWITCH_BLOCK:
			switchBlockParse();
			break;
		case TRY_CATCH_BLOCK:
			tryCatchBlockParse();
			break;
		}
	}

	/**
	 * parse a java class' all code block
	 */
	public void typeBlockParse() {

		if (this.isNonTransactionalBlock) {
			System.err.println("------Type is NonTransactional Type !!!!!");
		}
// System.err.println("----MainType Block Content:" + blockContent[0] + " " +
// blockContent[1] + " " + blockContent[2] + " content Length: " +
// blockContent.length);

		/*
		 * inside type parsing
		 * 
		 * 1. statements(annotation/field declaration)
		 * 
		 * 2. blocks(static/method)
		 */
		String currentToken;
		StringBuilder sb = new StringBuilder();

		boolean isCommonStatement = false;
		boolean isStaticBlock = false;
		boolean isAnnotationStatement = false;
		boolean isMethodBlock = false;
		boolean isInString = false;
		boolean isInMethodStatement = false;

		boolean isInType = false;

		int staticBracketCount = 0;
		int annotationBracketCount = 0;
		int methodParentheseCount = 0;
		int methodBracketCount = 0;

		JavaBlock methodBlock;

		boolean foundTargetAnnotationForMethod = false;

		for (int i = 0; i < blockContent.length; i++) {

			currentToken = blockContent[i].trim();

			/*
			 * not in type block yet
			 */
			if (!currentToken.equals("{") && !isInType) {

				continue;

			} else if (currentToken.equals("{") && !isInType) {
				isInType = true;
				continue;
			}

			sb.append(currentToken + " ");

			if (!isCommonStatement && !isStaticBlock && !isAnnotationStatement &&
					!isMethodBlock) {
				isCommonStatement = true;
			}

			/*
			 * commonStatement
			 */
			if (isCommonStatement && !isStaticBlock && !isAnnotationStatement &&
					!isMethodBlock) {

				// test if in method block
				if (currentToken.equals("(")) {

					Matcher methodM = shortMethodDeclarationP.matcher(sb.toString());

					if (methodM.find()) {

						methodParentheseCount++;

						isMethodBlock = true;
						isInMethodStatement = true;

						isCommonStatement = false;

						continue;
					}

				}

				// static block
				else if (currentToken.equals("static") && blockContent[i + 1].equals("{")) {
					isStaticBlock = true;
					isCommonStatement = false;
					continue;
				}

				// annotation
				else if (currentToken.startsWith("@")) {
					isAnnotationStatement = true;
					isCommonStatement = false;

					// a simple annotation skip
					if (!blockContent[i + 1].equals("(")) {

						isAnnotationStatement = false;
						isCommonStatement = true;

						sb = new StringBuilder();
						continue;
					}

					continue;

				}

				// field declaration
				if (currentToken.endsWith(";")) {
					isCommonStatement = false;

					JavaStatement fieldStatement = new JavaStatement(
							JavaStatementType.SIMPLE_STATEMENT, sb.toString().split("\\s+"));

					this.statements.add(fieldStatement);
					sb = new StringBuilder();
					continue;
				}

				continue;

			}

			/*
			 * static block
			 */
			if (!isCommonStatement && isStaticBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (currentToken.equals("{") && !isInString) {
					staticBracketCount++;
				} else if (currentToken.equals("}") && !isInString) {
					staticBracketCount--;
				}

				if (staticBracketCount == 0) {

					isStaticBlock = false;
					isCommonStatement = true;

					JavaBlock staticBlock = new JavaBlock(JavaBlockType.STATIC_BLOCK, sb
							.toString().split("\\s+"), false);

					this.staticBlocks.add(staticBlock);
					sb = new StringBuilder();
					continue;

				}
				continue;

			}

			/*
			 * annotation statement in type block declaration
			 */
			if (!isCommonStatement && isAnnotationStatement) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (currentToken.equals("(") && !isInString) {
					annotationBracketCount++;
				} else if (currentToken.equals(")") && !isInString) {
					annotationBracketCount--;
				}

				if (annotationBracketCount == 0) {
					isAnnotationStatement = false;
					isCommonStatement = true;

					if (sb.toString().contains(targetAnnotation)) {
						foundTargetAnnotationForMethod = true;
					}

					sb = new StringBuilder();
					continue;
				}

				continue;

			}

			/*
			 * 
			 * method block inside type block declaration
			 */
			if (!isCommonStatement && isMethodBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				/*
				 * in method declaration declaration
				 */
				if (!isInString && isInMethodStatement && currentToken.equals("(")) {
					methodParentheseCount++;
				} else if (!isInString && isInMethodStatement && currentToken.equals(")")) {
					methodParentheseCount--;
				}

				/*
				 * process until find method block start sign {
				 */
				if (!isInString && (methodParentheseCount == 0) && currentToken.equals("{")) {

					methodBracketCount++;

					/*
					 * find method block start char
					 */
					if (isInMethodStatement) {
						isInMethodStatement = !isInMethodStatement;
					}

				} else if (!isInString && (methodParentheseCount == 0) &&
						currentToken.equals("}")) {
					methodBracketCount--;
				}

				if (!isInMethodStatement && methodBracketCount == 0) {
					isMethodBlock = false;
					isCommonStatement = true;

					// System.err.println("----Method Block: " + sb.toString());

					if (this.isNonTransactionalBlock) {
						methodBlock = new JavaBlock(JavaBlockType.METHOD_DEFINE_BLOCK, sb
								.toString().split("\\s+"), true);
					} else {
						methodBlock = new JavaBlock(JavaBlockType.METHOD_DEFINE_BLOCK, sb
								.toString().split("\\s+"), foundTargetAnnotationForMethod);
						if (foundTargetAnnotationForMethod) {
							foundTargetAnnotationForMethod = false;
						}
					}

					this.methodBlocks.add(methodBlock);
					sb = new StringBuilder();
					continue;
				}
				continue;

			}

		}

	}

	/**
	 * parse the static block
	 */
	public void staticBlockParse() {

// System.err.println("----Static Block Declaration: " + String.join(" ",
// blockContent));

		/*
		 * if-else block
		 * 
		 * for block
		 * 
		 * do-while block
		 * 
		 * switch block
		 * 
		 * try-catch block
		 * 
		 * anonymous block
		 */

		boolean isInString = false;
		boolean isCommonStatement = false;
		boolean isInIfBlock = false;
		boolean hasElseIfBlock = false;
		boolean hasElseBlock = false;
		boolean isInForBlock = false;
		boolean isInDoWhileBlock = false;
		boolean isInSwitchBlock = false;
		boolean isInTryBlock = false;
		boolean hasFinallyBlock = false;
		boolean isInAnonymousBlock = false;

		String currentToken = "";

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < blockContent.length; i++) {

			currentToken = blockContent[i].trim();
			sb.append(currentToken + " ");

			if (currentToken.equals("if")) {

			}

			if (currentToken.equals("for")) {

			}

			if (currentToken.equals("do")) {

			}

			if (currentToken.equals("while")) {

			}

			if (currentToken.equals("try")) {

			}

		}

	}

	/**
	 * process method block statements
	 */
	public void methodBlockParse() {

		if (this.isNonTransactionalBlock) {
			System.err.println("------Method is NonTransactional Type !!!!!");
		}

// System.err
// .println("------Method Block Declaration: " + String.join(" ",
// blockContent));

		/*
		 * if-else block
		 * 
		 * for block
		 * 
		 * do-while block
		 * 
		 * switch block
		 * 
		 * try-catch block
		 * 
		 * anonymous block
		 */

		boolean isInString = false;
		boolean isCommonStatement = false;

		boolean isInIfBlock = false;
		boolean isInIfStatement = false;
		boolean isInForBlock = false;
		boolean isInForStatement = false;
		boolean isInDoWhileBlock = false;
		boolean isInDoWhileStatement = false;
		boolean isInWhileBlock = false;
		boolean isInWhileStatement = false;
		boolean isInSwitchBlock = false;
		boolean isInSwitchStatement = false;
		boolean isInTryBlock = false;
		boolean isInTryStatement = false;
		boolean isInAnonymousBlock = false;
		boolean isInMethod = false;

		int ifParentheses = 0;
		int ifBracketCount = 0;
		int forParentheses = 0;
		int forBracketCount = 0;
		int doWhileBracketCount = 0;
		int doWhileParentheses = 0;
		int whileParentheses = 0;
		int whileBracketCount = 0;
		int switchParentheses = 0;
		int switchBrachetCount = 0;
		int tryParentheses = 0;
		int tryBracketCount = 0;

		String currentToken = "";

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < blockContent.length; i++) {

			currentToken = blockContent[i].trim();
			sb.append(currentToken + " ");

			// skip method declaration statement
			if (!currentToken.equals("{") && !isInMethod) {
				continue;

			} else if (currentToken.equals("{") && !isInMethod) {

				this.blockStatement = sb.toString();
				sb = new StringBuilder();
				isInMethod = true;
				continue;
			}

			if (!isCommonStatement && !isInIfBlock && !isInForBlock && !isInDoWhileBlock &&
					!isInWhileBlock && !isInSwitchBlock && !isInTryBlock &&
					!isInAnonymousBlock) {

				isCommonStatement = true;

			}

			/*
			 * default all statement is considered as common statement
			 * 
			 * when meet some keywords to determine which block the statement is
			 */
			if (isCommonStatement && !isInIfBlock && !isInForBlock && !isInDoWhileBlock &&
					!isInWhileBlock && !isInSwitchBlock && !isInTryBlock &&
					!isInAnonymousBlock) {

				/*
				 * skip if the token is in string context
				 */
				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (currentToken.equals("if") && !isInString) {

					isInIfBlock = true;
					isInIfStatement = true;
					isCommonStatement = false;

					continue;
				}

				if (currentToken.equals("for") && !isInString) {

					isInForBlock = true;
					isInForStatement = true;
					isCommonStatement = false;
					continue;
				}

				if (currentToken.equals("do") && !isInString) {

					isInDoWhileBlock = true;
					isCommonStatement = false;
					continue;
				}

				if (currentToken.equals("while") && !isInString) {
					isInWhileBlock = true;
					isInWhileStatement = true;
					isCommonStatement = false;

					continue;

				}

				if (currentToken.equals("switch") && !isInString) {

					isInSwitchBlock = true;
					isInSwitchStatement = true;
					isCommonStatement = false;
					continue;

				}

				if (currentToken.equals("try") && !isInString) {

					isInTryBlock = true;
					isCommonStatement = false;
					if (blockContent[i + 1].trim().equals("(")) {
						isInTryStatement = true;
					}
					continue;

				}

				/*
				 * simple java statement
				 */
				if (currentToken.equals(";") && !isInString) {

					isCommonStatement = false;

					JavaStatement simpleStatement = new JavaStatement(
							JavaStatementType.SIMPLE_STATEMENT, sb.toString().split("\\s+"));

// System.err.println("------Simple Statement in Method: " + sb.toString());
					this.statements.add(simpleStatement);

					sb = new StringBuilder();
					continue;
				}
				continue;

			}

			/*
			 * "if" block in method define block
			 * 
			 * "if" block can be nested
			 * 
			 * also "if" block can have "else if"/"else" block
			 */
			if (!isCommonStatement && isInIfBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				/*
				 * if condition statement
				 */
				if (isInIfStatement && !isInString && currentToken.equals("(")) {
					ifParentheses++;
					continue;

				} else if (isInIfStatement && !isInString && currentToken.equals(")")) {

					ifParentheses--;
					if (ifParentheses != 0) {
						continue;
					}
				}

				/*
				 * starting if block declaration and parsed all if block code
				 */
				if (!isInString && (ifParentheses == 0) && currentToken.equals("{")) {

					ifBracketCount++;

					if (isInIfStatement) {
						isInIfStatement = !isInIfStatement;
					}
					continue;

				} else if (!isInString && (ifParentheses == 0) && currentToken.equals("}")) {
					ifBracketCount--;
					if (ifBracketCount != 0) {
						continue;
					}
				}

				if (ifBracketCount == 0 && !isInIfStatement) {

					if (blockContent[i + 1].trim().equals("else") &&
							blockContent[i + 2].trim().equals("if")) {
						sb.append(blockContent[++i] + " ");
						sb.append(blockContent[++i] + " ");
						sb.append(blockContent[++i] + " ");// (
						ifParentheses++;
						isInIfStatement = true;
						continue;
					} else if (blockContent[i + 1].trim().equals("else") &&
							blockContent[i + 2].trim().equals("{")) {

						sb.append(blockContent[++i] + " ");
						sb.append(blockContent[++i] + " ");
						ifBracketCount++;
						continue;
					} else {
						isInIfBlock = false;
						isCommonStatement = true;
					}

					// the condition to exit if block parsing process
					if (!isInIfBlock) {

// System.err.println("------If Block: " + sb.toString());

						JavaBlock ifBlock = new JavaBlock(JavaBlockType.IF_ELSE_BLOCK, sb
								.toString().split("\\s+"), false);
						this.ifBlocks.add(ifBlock);
						sb = new StringBuilder();
						continue;
					}

					continue;
				}

				continue;

			}

			// for block parsing
			if (!isCommonStatement && isInForBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				// for statement
				if (isInForStatement && !isInString && currentToken.equals("(")) {
					forParentheses++;
					continue;
				} else if (!isInString && isInForStatement && currentToken.equals(")")) {
					forParentheses--;
					if (forParentheses != 0) {
						continue;
					}
				}

				if (!isInString && (forParentheses == 0) && currentToken.equals("{")) {
					forBracketCount++;
					if (isInForStatement) {
						isInForStatement = !isInForStatement;
					}
					continue;
				} else if (!isInString && (forParentheses == 0) && currentToken.equals("}")) {
					forBracketCount--;
					if (forBracketCount != 0) {
						continue;
					}
				}

				// finish for block parsing
				if (forBracketCount == 0 && !isInForStatement) {
					isInForBlock = false;
					isCommonStatement = true;

					JavaBlock forBlock = new JavaBlock(JavaBlockType.FOR_BLOCK, sb.toString()
							.split("\\s+"), false);
					// System.err.println("------For Block: " + sb.toString());
					this.forBlocks.add(forBlock);
					sb = new StringBuilder();
					continue;
				}

				continue;
			}

			// do-while parsing
			if (!isCommonStatement && isInDoWhileBlock) {
				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				// do block
				if (!isInString && !isInDoWhileStatement && currentToken.equals("{")) {
					doWhileBracketCount++;
					continue;
				} else if (!isInString && !isInDoWhileStatement && currentToken.equals("}")) {
					doWhileBracketCount--;
					if (doWhileBracketCount != 0) {
						continue;
					}
				}

				if (doWhileBracketCount == 0 && currentToken.equals("while")) {

					isInDoWhileStatement = true;
					continue;
				}

				if (!isInString && isInDoWhileStatement && currentToken.equals("(")) {
					doWhileParentheses++;
					continue;
				} else if (!isInString && isInDoWhileStatement && currentToken.equals(")")) {
					doWhileParentheses--;
					if (doWhileParentheses != 0) {
						continue;
					}
				}
				// finish do-while block parsing process
				if (doWhileParentheses == 0 && doWhileBracketCount == 0 &&
						isInDoWhileStatement) {
					sb.append(blockContent[++i] + " ");

					JavaBlock doWhileBlock = new JavaBlock(JavaBlockType.DO_WHILE_BLOCK, sb
							.toString().split("\\s+"), false);
					this.doWhileBlocks.add(doWhileBlock);
// System.err.println("------Do While Block: " + sb.toString());
					sb = new StringBuilder();
					continue;
				}
				continue;

			}

			if (!isCommonStatement && isInWhileBlock) {
				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (!isInString && currentToken.equals("(")) {
					whileParentheses++;
					continue;
				} else if (!isInString && currentToken.equals(")")) {
					whileParentheses--;
					if (whileParentheses != 0) {
						continue;
					}
				}

				if ((whileParentheses == 0) && !isInString && currentToken.equals("{")) {
					whileBracketCount++;
					if (isInWhileStatement) {
						isInWhileStatement = !isInWhileStatement;
					}
					continue;
				} else if ((whileParentheses == 0) && !isInString && currentToken.equals("}")) {
					whileBracketCount--;
					if (whileBracketCount != 0) {
						continue;
					}
				}

				// finish while block
				if (!isInWhileStatement && (whileBracketCount == 0)) {
					isInWhileBlock = false;
					isCommonStatement = true;
					JavaBlock whileBlock = new JavaBlock(JavaBlockType.WHILE_BLOCK, sb
							.toString().split("\\s+"), false);
					this.doWhileBlocks.add(whileBlock);

// System.err.println("------While Block: " + sb.toString());

					sb = new StringBuilder();
					continue;
				}
				continue;

			}

			// switch-case block
			if (!isCommonStatement && isInSwitchBlock) {
				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (!isInString && isInSwitchStatement && currentToken.equals("(")) {
					switchParentheses++;
					continue;
				} else if (!isInString && isInSwitchStatement && currentToken.equals(")")) {
					switchParentheses--;
					if (switchParentheses != 0) {
						continue;
					}
				}

				if ((switchParentheses == 0) && !isInString && currentToken.equals("{")) {
					switchBrachetCount++;
					if (isInSwitchStatement) {
						isInSwitchStatement = !isInSwitchStatement;
					}
				} else if ((switchParentheses == 0) && !isInString && currentToken.equals("}")) {
					switchBrachetCount--;
					if (switchBrachetCount != 0) {
						continue;
					}
				}

				// finish switch block
				if (!isInSwitchStatement && (switchBrachetCount == 0)) {
					isInSwitchBlock = false;
					isCommonStatement = true;
					JavaBlock switchBlock = new JavaBlock(JavaBlockType.SWITCH_BLOCK, sb
							.toString().split("\\s+"), false);
					this.switchBlocks.add(switchBlock);
// System.err.println("------Switch Block: " + sb.toString());
					sb = new StringBuilder();
					continue;
				}

				continue;

			}

			// try-catch block parsing process
			if (!isCommonStatement && isInTryBlock) {

				if (currentToken.equals("\"") && !isInString) {
					isInString = !isInString;
				} else if (currentToken.equals("\"") && isInString) {
					isInString = !isInString;
				}

				if (!isInString && isInTryStatement && currentToken.equals("(")) {
					tryParentheses++;
					continue;
				} else if (!isInString && isInTryStatement && currentToken.equals(")")) {
					tryParentheses--;
					if (tryParentheses != 0) {
						continue;
					}
				}

				if ((tryParentheses == 0) && !isInString && currentToken.equals("{")) {
					tryBracketCount++;
					if (isInTryStatement) {
						isInTryStatement = !isInTryStatement;
					}
					continue;
				} else if ((tryParentheses == 0) && !isInString && currentToken.equals("}")) {
					tryBracketCount--;
					if (tryBracketCount != 0) {
						continue;
					}
				}

				if (tryBracketCount == 0 && !isInTryStatement) {
					if (blockContent[i + 1].equals("catch")) {
						sb.append(blockContent[++i] + " ");// catch
						sb.append(blockContent[++i] + " ");// (
						isInTryStatement = true;
						tryParentheses++;
						continue;
					} else if (blockContent[i + 1].equals("finally")) {
						sb.append(blockContent[++i] + " "); // finally
						sb.append(blockContent[++i] + " "); // {
						tryBracketCount++;
						continue;
					}

					// exit try block
					else {
						isInTryBlock = false;
						isCommonStatement = true;
					}

					// meet condition to exit try block
					if (!isInTryBlock) {
						JavaBlock tryBlock = new JavaBlock(JavaBlockType.TRY_CATCH_BLOCK, sb
								.toString().split("\\s+"), false);

// System.err.println("------Try Catch Block: " + sb.toString());
						this.tryBlocks.add(tryBlock);
						sb = new StringBuilder();
						continue;
					}
					continue;
				}
				continue;
			}
		}
	}

	public void ifBlockParse() {
/*
 * if-else block
 * 
 * for block
 * 
 * do-while block
 * 
 * switch block
 * 
 * try-catch block
 * 
 * anonymous block
 */

		// System.err.println("------If Block: " + String.join(" ",
// blockContent));

	}

	public void forBlockParse() {
/*
 * if-else block
 * 
 * for block
 * 
 * do-while block
 * 
 * switch block
 * 
 * try-catch block
 * 
 * anonymous block
 */

	}

	public void doWhileBlockParse() {
/*
 * if-else block
 * 
 * for block
 * 
 * do-while block
 * 
 * switch block
 * 
 * try-catch block
 * 
 * anonymous block
 */

	}

	public void switchBlockParse() {
/*
 * if-else block
 * 
 * for block
 * 
 * do-while block
 * 
 * switch block
 * 
 * try-catch block
 * 
 * anonymous block
 */

	}

	public void tryCatchBlockParse() {
/*
 * if-else block
 * 
 * for block
 * 
 * do-while block
 * 
 * switch block
 * 
 * try-catch block
 * 
 * anonymous block
 */

	}

	public void anonymousBlockParse() {
/*
 * if-else block
 * 
 * for block
 * 
 * do-while block
 * 
 * switch block
 * 
 * try-catch block
 * 
 * anonymous block
 */

	}

	/**
	 * parse blockPrefix String content to return its type
	 * 
	 * @return
	 */
	public JavaBlockType getJavaBlockType() {

		return JavaBlockType.TYPE_BLOCK;
	}

	public void addSubBlock(JavaBlock subBlock) {

		this.getSubBlocks().add(subBlock);
	}

	public void addStatement(JavaStatement statement) {

		this.getStatements().add(statement);
	}

	public void addVariable(JavaVariable variable) {
		this.getVariables().add(variable);
	}

	public void addSiblingBlock(JavaBlock siblingBlock) {
		System.err.println("====Sibling Type name: " + siblingBlock.getBlockContent()[1]);
		this.siblingBlocks.add(siblingBlock);
	}

	// -----------------------getters&setters----------------------

	public String getBlockPrefix() {
		return blockPrefix;
	}

	public void setBlockPrefix(String blockPrefix) {
		this.blockPrefix = blockPrefix;
	}

	public JavaBlockType getBlockType() {
		return blockType;
	}

	public void setBlockType(JavaBlockType blockType) {
		this.blockType = blockType;
	}

	public List<JavaBlock> getSubBlocks() {
		return subBlocks;
	}

	public void setSubBlocks(List<JavaBlock> subBlocks) {
		this.subBlocks = subBlocks;
	}

	public List<JavaStatement> getStatements() {
		return statements;
	}

	public void setStatements(List<JavaStatement> statements) {
		this.statements = statements;
	}

	public List<JavaVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<JavaVariable> variables) {
		this.variables = variables;
	}

	public boolean isInBlock() {
		return isInBlock;
	}

	public void setInBlock(boolean isInBlock) {
		this.isInBlock = isInBlock;
	}

	public String[] getBlockContent() {
		return blockContent;
	}

	public void setBlockContent(String[] blockContent) {
		this.blockContent = blockContent;
	}

	public boolean isMainType() {
		return isMainType;
	}

	public void setMainType(boolean isMainType) {
		this.isMainType = isMainType;
	}

	public String getBlockStatement() {
		return blockStatement;
	}

	public void setBlockStatement(String blockStatement) {
		this.blockStatement = blockStatement;
	}

	public static Pattern getShortmethoddeclarationp() {
		return shortMethodDeclarationP;
	}

	public List<JavaBlock> getSiblingBlocks() {
		return siblingBlocks;
	}

	public List<JavaBlock> getStaticBlocks() {
		return staticBlocks;
	}

	public List<JavaBlock> getMethodBlocks() {
		return methodBlocks;
	}

	public List<JavaBlock> getIfBlocks() {
		return ifBlocks;
	}

	public List<JavaBlock> getForBlocks() {
		return forBlocks;
	}

	public List<JavaBlock> getDoWhileBlocks() {
		return doWhileBlocks;
	}

	public List<JavaBlock> getSwitchBlocks() {
		return switchBlocks;
	}

	public List<JavaBlock> getTryBlocks() {
		return tryBlocks;
	}

	public List<JavaStatement> getAnnotationStatements() {
		return annotationStatements;
	}

	public boolean isNonTransactionalBlock() {
		return isNonTransactionalBlock;
	}

	public void setNonTransactionalBlock(boolean isNonTransactionalBlock) {
		this.isNonTransactionalBlock = isNonTransactionalBlock;
	}

}
