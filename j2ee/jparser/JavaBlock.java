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

/**
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 23, 2015 liang.guisheng Exp
 *          $
 */
public class JavaBlock {

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

	/*
	 * the statements inside this block
	 */
	private List<JavaStatement> statements;

	/*
	 * the variable collection defined inside the block
	 */
	private List<JavaVariable> variables;

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
	}

	public JavaBlock(JavaBlockType type, String[] blockContent) {

		this();

		if (type == JavaBlockType.MAIN_TYPE_BLOCK) {
			isMainType = true;
		}
		this.blockType = type;
		this.blockContent = blockContent;

		parseBlock();
	}

	// --------------utility methods------------------------------

	public void parseBlock() {
		switch (this.blockType) {
		case MAIN_TYPE_BLOCK:
			mainTypeBlockParse();
			break;
		case NESTED_TYPE_BLOCK:
			nestedTypeBlockParse();
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

	public void mainTypeBlockParse() {

		System.err.println("----MainType Block Content:" + blockContent[0] + " " +
				blockContent[1] + " " + blockContent[2] + " content Length: " +
				blockContent.length);
	}

	public void typeBlockParse() {

	}

	public void nestedTypeBlockParse() {

		System.err.println("----NestedType Block Content: " + blockContent[0] + " " +
				blockContent[1] + " " + blockContent[2] + " content Length: " +
				blockContent.length);
	}

	public void staticBlockParse() {

	}

	public void methodBlockParse() {

	}

	public void ifBlockParse() {

	}

	public void forBlockParse() {

	}

	public void doWhileBlockParse() {

	}

	public void switchBlockParse() {

	}

	public void tryCatchBlockParse() {

	}

	public void anonymousBlockParse() {

	}

	/**
	 * parse blockPrefix String content to return its type
	 * 
	 * @return
	 */
	public JavaBlockType getJavaBlockType() {

		return JavaBlockType.MAIN_TYPE_BLOCK;
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

	/**
	 * @return Returns the isInBlock.
	 */
	public boolean isInBlock() {
		return isInBlock;
	}

	/**
	 * @param isInBlock
	 *            The isInBlock to set.
	 */
	public void setInBlock(boolean isInBlock) {
		this.isInBlock = isInBlock;
	}

	/**
	 * @return Returns the blockContent.
	 */
	public String[] getBlockContent() {
		return blockContent;
	}

	/**
	 * @param blockContent
	 *            The blockContent to set.
	 */
	public void setBlockContent(String[] blockContent) {
		this.blockContent = blockContent;
	}

	/**
	 * @return Returns the isMainType.
	 */
	public boolean isMainType() {
		return isMainType;
	}

	/**
	 * @param isMainType
	 *            The isMainType to set.
	 */
	public void setMainType(boolean isMainType) {
		this.isMainType = isMainType;
	}

}
