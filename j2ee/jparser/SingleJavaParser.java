/*
 * Created on Jun 18, 2015
 *
 * $HeadURL$
 * $Date: Jun 18, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jun 18, 2015 liang.guisheng Exp
 *          $
 */
public class SingleJavaParser {

	public static void main(String[] args) throws IOException {
		String filepath = "/home/local/RISKOFFICE/liang.guisheng/ROWorkspace/rosys-core/src/core/br/com/riskoffice/client/ui/web/portfolio/ClientPortfolioManagerBean.java";

		BufferedReader reader = new BufferedReader(new FileReader(filepath));

		String line = reader.readLine();
		while (line != null) {

			if (line.trim().startsWith("/*") && !line.trim().startsWith("/**")) {
				System.out.println("Multple Comments: " +
						multipleCommentsProcess(reader, line));
			}

			if (line.trim().startsWith("//")) {
				System.out.println("Single Comment: " + singleCommentProcess(reader, line));
			}

			if (line.trim().startsWith("/**")) {
				System.out.println("Document: " + documentProcess(reader, line));
			}

			if (line.trim().startsWith("package")) {
				System.out.println("Package: " + packageStatementProcess(reader, line));
			}

			// import statement
			if (line.trim().startsWith("import")) {

				if (importStatementProcess(reader, line) != null) {
					System.out.println("Import: " + importStatementProcess(reader, line));
				}
			}
			// annotation statement process
			if (line.trim().startsWith("@")) {

			}
			line = reader.readLine();
		}

	}

// static String

	/**
	 * process annotations can have 3 kind of annotations
	 * 
	 * 1. simplest annotation without any arguments
	 * 
	 * 2. with an argument but no assignment expression
	 * 
	 * 3. with arguments including assignment expressions
	 */
	static String annotationStatementProcess(BufferedReader reader, String line) {
		String annotationStatement = "";

		if (!line.trim().contains("(")) {
			annotationStatement = line.trim().substring(1);
			return annotationStatement;
		}

		if (line.trim().contains("(")) {

			annotationStatement = line.trim().substring(1);
			return annotationStatement;
		}

		if (line.trim().contains("")) {
			return annotationStatement;
		}

		return annotationStatement;
	}

	static String importStatementProcess(BufferedReader reader, String line)
			throws IOException {

		String importStatement;
		if (line.trim().substring("import".length() + 1).startsWith("br.com.riskoffice")) {
			importStatement = line.trim().substring("import".length() + 1);

			if (!line.trim().endsWith(";")) {
				while ((line = reader.readLine()) != null) {
					if (line.trim().endsWith(";")) {
						importStatement += line.trim();
						break;
					}
				}
			}

			return importStatement;

		} else {
			return null;
		}

	}

	static String packageStatementProcess(BufferedReader reader, String line)
			throws IOException {
		String packageStatement;
		if (line.trim().endsWith(";")) {
			packageStatement = line.trim().substring("package".length() + 1);
			return packageStatement;
		} else {

			packageStatement = line.trim().substring("package".length() + 1);

			while ((line = reader.readLine()) != null) {
				if (line.trim().endsWith(";")) {
					packageStatement += line.trim();
					break;
				}
			}
			return packageStatement;
		}
	}

	static String singleCommentProcess(BufferedReader reader, String line) {
		return line.trim();

	}

	static String documentProcess(BufferedReader reader, String line) throws IOException {
		String document = line.trim();
		while ((line = reader.readLine()) != null) {
			if (line.trim().endsWith("*/")) {
				document += line.trim();
				break;
			}
			document += line.trim().substring(1).trim();
		}

		return document;

	}

	static String multipleCommentsProcess(BufferedReader is, String line) throws IOException {

		String completeStatement = "";

		if (line.trim().startsWith("/*")) {
			completeStatement = line.trim();
			while ((line = is.readLine()) != null) {
				if (line.trim().endsWith("*/")) {

					completeStatement += line;
					break;
				}

				completeStatement += line.trim().substring(1);
			}
		}
		return completeStatement;
	}

	static StatementType checkStatementType(String statement) {

		return StatementType.VARIABLE_DEFINE_STATEMENT;
	}

	static void importProcess(String importStatement) {

	}
}
