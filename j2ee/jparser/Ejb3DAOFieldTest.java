/*
 * Created on Jan 14, 2015
 *
 * $HeadURL$
 * $Date: Jan 14, 2015 $
 * $Author: liang.guisheng $
 * $Revision: 1.1 $
 */
package br.com.riskoffice.ejb3dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.riskoffice.util.Log;

/**
 * test if Ejb3DAO field has been abused in a already transaction method
 * 
 * @author liang.guisheng (Last Modified By: $Author: liang.guisheng $)
 *
 * @version $Id: org.eclipse.jdt.ui.prefs,v 1.1 Jan 14, 2015 liang.guisheng Exp
 *          $
 */
@Test(enabled = false)
public class Ejb3DAOFieldTest {

	private static final Log log = new Log(Ejb3DAOFieldTest.class);

	private static List<String> classExcludeNames = new ArrayList<String>();

	private static List<String> srcPaths;

	private static List<String> errorClassList = new ArrayList<String>();

	private final String javaExt = ".java";

	private final String initialClass = "br.com.riskoffice.ejb3.Ejb3DAO";

	private final Ejb3DAOInvocationContainer container = new Ejb3DAOInvocationContainer();

	private final int classBracketCount = 0;

	@BeforeClass
	public void setUp() throws IOException {

		setSrcPaths();
		setExcludeClassNames();
		setClassAndFileData();
	}

	public void setClassAndFileData() {

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Ejb3DAOFieldTest test = new Ejb3DAOFieldTest();
		test.setSrcPaths();
		test.travelWorkspace();

	}

	/**
	 * travel all the java files in workspace in loops to get the invocation
	 * tree
	 * 
	 * @throws IOException
	 */
	public void travelWorkspace() throws IOException {

		container.putInvoker(initialClass);
		container.setLevel(container.getLevel() + 1);

		while (true) {

			/*
			 * after traveled all the classes and does not found any invoker
			 */
			if (container.getInvocations().size() < container.getLevel()) {
				break;
			}

			for (String srcPath : srcPaths) {

				File path = new File(srcPath);

				travelSrcPaths(path);

			}

			container.setLevel(container.getLevel() + 1);

		}

		System.out.println("----Container's Level is: " + container.getLevel());
// container.displayInvocations();

	}

	/**
	 * used to travel file path recursively
	 * 
	 */
	public void travelSrcPaths(File path) throws IOException {

		Pattern classPathP = Pattern.compile(".*/src/.*?/(.*)\\.java");
		Pattern svnPathP = Pattern.compile("\\.svn");

		for (File file : path.listFiles()) {

			if (file.isFile()) {

				Matcher classPathM = classPathP.matcher(file.getAbsolutePath());
				Matcher svnPathM = svnPathP.matcher(file.getAbsolutePath());

				if (classPathM.find() && !svnPathM.find()) {
					String className = classPathM.group(1).replace("/", ".");

					// if current class invoked the Ejb3DAO put it into the
					// container
					if (!container.isInvokerExists(className) &&
							checkIfFileInvokedClasses(file,
									container.getInvoker(container.getLevel() - 1))) {

						container.putInvoker(className);
					}
				}
			} else {
				travelSrcPaths(file);
			}
		}
	}

	/**
	 * read current class file's all import statements, and check if the current
	 * level class name is in it
	 * 
	 * @param file
	 * @param currentLevelClasses
	 * @return when can not find any class file that contains a class that
	 *         invoked ejb3dao related class return false and exit
	 * @throws IOException
	 */
	public boolean checkIfFileInvokedClasses(File file,
			List<ClassContainer> currentLevelClasses) throws IOException {

		boolean isInvoked = false;

		Pattern importP = Pattern.compile("import\\s+(.*);");
		List<String> imports = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine().trim();

		while (!line.startsWith("@") && !line.startsWith("/**") && !line.contains("class") &&
				!line.contains("interface") && !line.contains("enum") &&
				!line.contains("abstract")) {

			if (line.startsWith("import")) {
				Matcher importM = importP.matcher(line);

				if (importM.find()) {
					imports.add(importM.group(1));
				}
			}

			line = reader.readLine().trim();
		}

		reader.close();

		for (ClassContainer currentLevelClass : currentLevelClasses) {
			for (String importC : imports) {
				if (currentLevelClass.getClassName().equals(importC)) {
// System.out.println(file.getName());
					parseClassInvocation(file, importC);

					isInvoked = true;
					return isInvoked;
				}
			}
		}

		return isInvoked;
	}

	/**
	 * parse the current java class file about the invocation of importedClass
	 * field
	 * 
	 * @param currentClassFile
	 * @param importedClass
	 * @throws IOException
	 */
	public void parseClassInvocation(File currentClassFile, String importedClass)
			throws IOException {

		System.out.println("--java file: " + currentClassFile.getName() +
				"  -- invoked the class --  " + importedClass);

		boolean isInClass = false;

		boolean isInMethod = false;

		boolean isClassNotSupport = false;

		boolean isMethodNotSupport = false;

		String notSupporttedAttribute = "TransactionAttributeType.NOT_SUPPORTED";

		Pattern importedClassSimpleNameP = Pattern.compile(".*\\.(.*)");

		Matcher importedClassSimpleNameM = importedClassSimpleNameP.matcher(importedClass);

		Pattern methodDeclarationP = Pattern
				.compile("(public|private|protected)\\s+.*?\\s+(\\w+)\\s?\\(");

		Pattern fieldDeclarationP = Pattern
				.compile("(private|protected|private)?\\s+(static|final)?\\s+(final|static)?\\s+([\\w\\d]+)\\s([\\w\\d]+)\\s+=?.*?;");

		Pattern classDeclarationP = Pattern.compile(".*?\\s+class\\s+([\\w\\d]+).*?\\{?");

		String simpleImportedName = null;

		String annotationStatement = null;

		// current parsing class file corresponded class
		ClassContainer clazz = new ClassContainer();

		String targetFieldName = null;

		if (importedClassSimpleNameM.find()) {

			simpleImportedName = importedClassSimpleNameM.group(1);
// System.out.println("--Imported Class Name: " + simpleImportedName);

		}

		BufferedReader reader = new BufferedReader(new FileReader(currentClassFile));

		String line;

		StringBuilder sb = new StringBuilder();

		while ((line = reader.readLine()) != null) {
			line = line.trim();

			// multiple comments or document string
			if (line.startsWith("/*") && !line.endsWith("*/")) {

				while ((line = reader.readLine()) != null) {

					if (line.trim().endsWith("*/")) {
						break;
					}
				}
				continue;
			}

			// multiple line comment mark or document in a single line
			if (line.startsWith("/*") && line.endsWith("*/")) {
				continue;
			}

			// single line comment with single comment mark
			if (line.startsWith("//")) {
				continue;
			}

			if (line.contains("//") && !line.startsWith("//") && !line.contains("\"")) {
				sb.append(line.substring(0, line.indexOf("/")));
				continue;
			}

			sb.append(line + " ");

		}

		String[] classTokens = ClassContentParser.getClassTokens(sb.toString());
		ClassContentParser.targetImportedClass = importedClass;

		ClassContentParser.targetImportedClassSimpleName = getClassSimpleName(importedClass);
		ClassContentParser.parseClassTokens(classTokens);

	}


	public String getClassSimpleName(String classFullName) {
		Pattern classSimpleNameP = Pattern.compile(".*\\.([\\w\\d]+)");
		Matcher classSimpleNameM = classSimpleNameP.matcher(classFullName);

		if (classSimpleNameM.find()) {
			return classSimpleNameM.group(1);
		}

		return classFullName;
	}




	/*
	 * set the project's source path for travel all the files under it
	 */
	public void setSrcPaths() throws IOException {
		if (srcPaths == null) {
			srcPaths = new ArrayList<String>();
		}

		String currentPath = ".";

		File wsPath = new File(new File(currentPath).getCanonicalPath()).getParentFile();

		srcPaths.add(wsPath.getAbsolutePath() + "/ro-commons/src");
		srcPaths.add(wsPath.getAbsolutePath() + "/rosys-core/src");
	}

	// set exclude class name to ignore these classes
	public void setExcludeClassNames() {
		classExcludeNames.add("AnbimaTheoreticalWalletHelper");
		classExcludeNames.add("FutureHelper");
		classExcludeNames.add("RofsRarCalculator");
		// Require_new
		classExcludeNames.add("IFMFundsIncorporationBean");
		// some invokers have NOT_SUPPORTED, some are not
		classExcludeNames.add("RepeatSeriesBean");
		classExcludeNames.add("OptionSurfaceCalculatorBean");
		// not have annotations
		// classExcludeNames.add("ReportBacktestRequestManager");
		classExcludeNames.add("ReportGenPortfolioLoaderBean");
		// Required but not invoked Ejb3Dao object, invoked em directly
		classExcludeNames.add("ROClusterUploaderBean");
		// persist method has been invoked by method without NOT_SUPPORTED
		// classExcludeNames.add("PortfolioDAOBean");
		classExcludeNames.add("CompositionXLSLoaderBean");

		// classExcludeNames.add("PUContainerHandler");
		// classExcludeNames.add("AssetFileParser");
		// some invokers have NOT_SUPPORTED, but some are not
		// classExcludeNames.add("PortfolioSimpleBuilderBean");
		// classExcludeNames.add("ClientReportRequesterBean");
		// classExcludeNames.add("ClientPortfolioCreateByQuotaBean");

		classExcludeNames.add("BookMovementsComputationHelper");
	}

	@Test(enabled = false)
	public void startTest() throws IOException, ClassNotFoundException {
		for (String srcPath : srcPaths) {
			testEjb3DaoFieldUsingInFile(new File(srcPath));
		}
	}

	/*
	 * travel all the files under the project's source path
	 */
	private void testEjb3DaoFieldUsingInFile(File path) throws IOException,
			ClassNotFoundException {
		for (File file : path.listFiles()) {
			if (file.isDirectory()) {
				testEjb3DaoFieldUsingInFile(file);
			} else {

				if (hasEjb3DaoField(file)) {

					Pattern classPathP = Pattern.compile(".*/src/.*?/(.*)\\.java");
					Matcher classPathM = classPathP.matcher(file.getAbsolutePath());

					if (classPathM.find()) {
						String className = classPathM.group(1).replace("/", ".");

						Class<?> c = Class.forName(className);
						checkEjb3DAOFieldInvok(c, file.getAbsolutePath());
					}

				}

			}

		}
	}

	/*
	 * check if the current traveled file is a java file & used Ejb3DAO & not
	 * abstract class & not interface
	 */
	private boolean hasEjb3DaoField(File path) throws IOException {
		boolean hasEjb3Field = false;
		boolean isAbstract = false;
		boolean isInterface = false;

		if (path.getAbsolutePath().endsWith(javaExt) &&
				!isFileExcluded(path.getAbsolutePath())) {

			BufferedReader br = new BufferedReader(new FileReader(path));

			String line = br.readLine();
			while (line != null) {

				if (line.contains("Ejb3DAO")) {
					hasEjb3Field = true;
				}
				// test if a class is an abstract class
				if (line.contains("class") && line.contains("abstract")) {
					isAbstract = true;
				}

				// test if a class is an interface
				if (line.contains("interface")) {
					isInterface = true;
				}

				line = br.readLine();
			}
			br.close();

		}

		return hasEjb3Field && !isAbstract && !isInterface;
	}

	private boolean isFileExcluded(String path) {
		boolean f = false;
		for (String file : classExcludeNames) {
			if (path.contains(file + javaExt)) {
				f = true;
				break;
			}
		}
		return f;
	}

	private void checkEjb3DAOFieldInvok(Class<?> clazz, String fileName) throws IOException {
		ParsedClass pc = new ParsedClass(clazz);
		pc.setClassFileName(fileName);

		if (!pc.checkEjb3DaoAbuse()) {

			log.warn("Ejb3DAO field has been abused in class :" + clazz.getCanonicalName());
			if (!errorClassList.contains(clazz.getCanonicalName())) {
				errorClassList.add(clazz.getCanonicalName());
			}
		}

	}

	@AfterClass
	public void afterClass() {
		if (!errorClassList.isEmpty()) {
			log.warn("Ejb3DAO Field has been abused in the following classes:");
			for (String error : errorClassList) {
				log.warn("Ejb3DAO field has been abused in class: " + error);
			}
			log.warn("\nTest Found " + errorClassList.size() + " Abused classes");
			Assert.fail();
		}

	}
}
