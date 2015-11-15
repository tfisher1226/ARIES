package aries.codegen;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.util.FileUtils;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.FilterUtil;
import aries.codegen.util.GenerateUtil;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public abstract class AbstractFileGenerator extends AbstractGenerator {
	
	protected String sourceFile;

	protected String sourcePath;
	
	protected String sourceFolder;
	
	protected String targetFile;
	
	protected String targetPath;
	
	protected String targetFolder;
	

	public AbstractFileGenerator() {
	}
	
	public AbstractFileGenerator(GenerationContext context) {
		this.context = context;
	}
	
	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = FileUtil.normalizePath(sourcePath);
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = FileUtil.normalizePath(targetPath);
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}


	/*
	 * Source code formatter
	 * ---------------------
	 */

	public String format(String content) {
		//return SourceCodeFormatter.formatCode(content);
		//TODO get another one...
		return content;
	}


	/*
	 * Key configurable helper methods
	 * -------------------------------
	 */
	
//	protected String createSourcePath() {
//		String sourcePath =	context.getTemplateWorkspace()+"/"+context.getTemplateHome();
//		return sourcePath;
//	}

//	protected String createTargetPath() {
//		String targetPath =	context.getTargetWorkspace()+"/"+context.getModule().getArtifactId();
//		return targetPath;
//	}

	protected FilterSet createFilterSet() {
		return createFilterSet(null);
	}
	
	protected FilterSet createFilterSet(String moduleNameSuffix) {
		String projectName = null;
		String projectNameCapped = null;
		if (context.isOptionLimitToSingleModule())
			projectName = context.getApplication().getArtifactId();
		else projectName = context.getModule().getArtifactId();
		projectNameCapped = NameUtil.capName(projectName);
		
		FilterSet filterSet = new MyFilterSet();
		if (moduleNameSuffix != null) {
			filterSet.addFilter("template1"+moduleNameSuffix, projectName);
			filterSet.addFilter("Template1"+moduleNameSuffix, projectNameCapped);
		}
		filterSet.addFilter("template1", projectName);
		filterSet.addFilter("Template1", projectNameCapped);
		return filterSet;
	}
	

	/*
	 * File generation from content
	 * ----------------------------
	 */

	public void generateFiles(Collection<ModelFile> modelFiles) throws Exception {
		Iterator<ModelFile> iterator = modelFiles.iterator();
		while (iterator.hasNext()) {
			ModelFile modelFile = iterator.next();
			generateFile(modelFile);
		}
	}
	
	public void generateFile(ModelFile modelFile) throws Exception {
		if (modelFile != null) {
			String content = modelFile.getTextContent();
			setTargetFolder(modelFile.getTargetFolder());
			setTargetFile(modelFile.getTargetFile());
			setTargetPath(modelFile.getTargetPath());
			generateFile(content);
		}
	}

	public void generateFile(String content) throws Exception {
		FileUtil.assureDirectoryExists(targetPath);
		String filePath = FileUtil.normalizePath(targetPath + "/" + targetFile);
		
		File file = new File(filePath);
		System.out.println("Generating " + filePath);
		FileUtils.getFileUtils().createNewFile(file);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(format(content));
		fileWriter.close();
	}

	public void generate(String fileName, String folderName, String content) throws Exception {
		generate(fileName, fileName, folderName, folderName, content);
	}
	
	public void generate(String sourceFile, String sourceFolder, String targetFile, String targetFolder, String content) throws Exception {
		String sourcePath = FileUtil.normalizePath(sourceFolder + "/" + sourceFile);
		String targetPath = FileUtil.normalizePath(targetFolder + "/" + targetFile);
		FileUtil.assureDirectoryExists(targetFolder);
		File file = new File(targetPath);
		System.out.println("Generating "+targetPath);
		FileUtils.getFileUtils().createNewFile(file);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(format(content));
		fileWriter.close();
	}
	
	/*
	 * File generation from source
	 * ---------------------------
	 */

//	public void generate(String fileName, String folderName, FilterSet filterSet) throws Exception {
//		generate(fileName, fileName, folderName, folderName, filterSet);
//	}
//	
//	public void generate(String sourceFile, String sourceFolder, String targetFile, String targetFolder, FilterSet filterSet) throws Exception {
//		String projectName = context.getProjectName();
//		String projectNameCapped = context.getProjectNameCapped();
//		String projectNameUpper = context.getProjectNameUpper();
//		String projectDomain = context.getProjectDomain();
//
//		setSourceFile(sourceFile);
//		setTargetFile(targetFile);
//		setSourceFolder(sourceFolder);
//		setTargetFolder(targetFolder);
//
//		FilterSet myFilterSet = new MyFilterSet();
//		myFilterSet.addFilter("template1", projectName);
//		myFilterSet.addFilter("Template1", projectNameCapped);
//		myFilterSet.addFilter("TEMPLATE1", projectNameUpper);
//		myFilterSet.addFilter("template1Domain", projectDomain);
//		if (filterSet != null)
//			myFilterSet.addConfiguredFilterSet(filterSet);
//		generateFile(myFilterSet);
//	}
	
	public void generateFile(String fileName, FilterSet filterSet) throws Exception {
		generateFile(".", fileName, filterSet);
	}

	public void generateFile(String fileFolder, String fileName) throws Exception {
		generateFile(fileFolder, fileName, null);
	}
	
	public void generateFile(String fileFolder, String fileName, FilterSet filterSet) throws Exception {
		setSourceFile(fileName);
		setTargetFile(fileName);
		setSourceFolder(fileFolder);
		setTargetFolder(fileFolder);
//		boolean fileFolderExists = !StringUtils.isEmpty(fileFolder) && !fileFolder.equals(".");
//		if (fileFolderExists) {
//			setSourceFolder(sourceFolder+"/"+fileFolder);
//			setTargetFolder(targetFolder+"/"+fileFolder);
//		}
		generateFile(filterSet);
	}

	public static void generateFile(String sourcePath, String sourceFile, String targetPath, String targetFile) throws Exception {
		FilterSetCollection filters = FilterUtil.createFilterSetCollection();
		generateFile(sourcePath, sourceFile, targetPath, targetFile, filters);
	}

	public static void generateFile(String sourcePath, String sourceFile, String targetPath, String targetFile, FilterSet filters) throws Exception {
		FilterSetCollection filterSetCollection = FilterUtil.createFilterSetCollection(filters);
		generateFile(sourcePath, sourceFile, targetPath, targetFile, filterSetCollection);
	}
	
	public static void generateFile(String sourcePath, String sourceFile, String targetPath, String targetFile, FilterSetCollection filters) throws Exception {
		String tempFile = System.getProperty("java.io.tmpdir") + targetFile;
		FileUtil.assureDirectoryExists(targetPath);
		//System.out.println(">>>"+sourceFile+", "+targetFile);
		FileUtils.getFileUtils().copyFile(sourcePath+"/"+sourceFile, tempFile, filters, true);
		FileUtils.getFileUtils().copyFile(tempFile, targetPath+"/"+targetFile);
	}

	public void generateFile(FilterSet filterSet) throws Exception {
		String tokenHome = context.getTemplateWorkspace() + "/" + context.getTemplateName() + "/" + TOKEN_HOME;
		String sourcePath = context.getTemplateWorkspace() + "/" + context.getTemplateHome() + "/"; 
		//String targetPath = context.getTargetWorkspace() + "/" + context.getTargetHome() + "/";
		String projectName = context.getProjectName();
		String targetPath = context.getTargetPath(projectName) + "/";
		if (!sourceFolder.equals("."))
			sourcePath += sourceFolder + "/";
		if (!targetFolder.equals("."))
			targetPath += targetFolder + "/";
		sourcePath += sourceFile;
		targetPath += targetFile;
		setSourcePath(sourcePath);
		setTargetPath(targetPath);
		
		FilterSetCollection filters = FilterUtil.createFilterSetCollection(tokenHome);
		if (filterSet != null)
			filters.addFilterSet(filterSet);
		generateFile(filters);
	}
	
	public void generateFile(FilterSetCollection filters) throws Exception {
		String tempFile = System.getProperty("java.io.tmpdir") + targetFile;
		FileUtil.assureFileExists(targetPath);
		System.out.println("Generating "+targetPath);
		FileUtils.getFileUtils().copyFile(sourcePath, tempFile, filters, true, false);
		FileUtils.getFileUtils().copyFile(tempFile, targetPath, null, true, true);
	}

	
	public void copyFiles(String fileFolder, FilterSet filterSet) throws Exception {
		setSourceFile(null);
		setTargetFile(null);
		setSourceFolder(fileFolder);
		setTargetFolder(fileFolder);
		copyFiles(filterSet);
	}
	
	public void copyFiles(FilterSet filterSet) throws Exception {
		String sourcePath = context.getTemplateWorkspace() + "/" + context.getTemplateHome() + "/"; 
		//String targetPath = context.getTargetWorkspace() + "/" + context.getTargetHome() + "/";
		String projectName = context.getProjectName();
		String targetPath = context.getTargetPath(projectName) + "/";
		if (!sourceFolder.equals("."))
			sourcePath += sourceFolder + "/";
		if (!targetFolder.equals("."))
			targetPath += targetFolder + "/";
		setSourcePath(sourcePath);
		setTargetPath(targetPath);
		
		FilterSetCollection filters = FilterUtil.createFilterSetCollection(TOKEN_HOME);
		if (filterSet != null)
			filters.addFilterSet(filterSet);
		copyFiles(filters);
	}
	
	public void copyFiles(FilterSetCollection filters) throws Exception {
		copyFiles(sourcePath, targetPath, filters);

	}
	
	public void copyFiles(String sourceDirectory, String targetDirectory, FilterSetCollection filters) throws Exception {
		FileUtil.assureDirectoryExists(targetDirectory);
		System.out.println("Copying files: "+targetDirectory);
		File folder = new File(sourceDirectory);
		Assert.isTrue(folder.isDirectory(), "Source path not a directory: "+sourceDirectory);
		String fileSeparator = System.getProperty("file.separator");
		if (!targetDirectory.endsWith(fileSeparator))
			targetDirectory += "/";
		if (!sourceDirectory.endsWith(fileSeparator))
			sourceDirectory += "/";
		File[] files = folder.listFiles();
		for (File file : files) {
			String targetPath = FileUtil.normalizePath(targetDirectory + file.getName());
			String sourcePath = FileUtil.normalizePath(sourceDirectory + file.getName());
			if (file.isDirectory()) {
				copyFiles(sourcePath, targetPath, filters);
			} else {
				copyFile(sourcePath, targetPath, filters);
			}
		}
	}

	public void copyFile(String sourceFile, String targetFile, FilterSetCollection filters) throws Exception {
		if (!sourceFile.endsWith("~") && !sourceFile.endsWith("-") && !sourceFile.endsWith(".bak")) {
			System.out.println("Copying file: "+targetFile);
			FileUtils.getFileUtils().copyFile(sourceFile, targetFile, filters, true, true);
		}
	}

	/*
	 * Folder generation from source
	 * -----------------------------
	 */
	
	public void generateFolder(String folderPath, FilterSet filterSet) throws Exception {
		generateFolder(folderPath, folderPath, filterSet, null);
	}

	public static void generateFolder(String sourcePath, String targetPath, FilterSet filters, List<Pattern> excludes) throws Exception {
		File directory = new File(sourcePath);
		Assert.isTrue(directory.isDirectory(), "SourcePath must be a folder");
		File[] list = directory.listFiles();
		for (int i = 0; i < list.length; i++) {
			File file = list[i];
			generateFolder(sourcePath, targetPath, file, filters, excludes);
		}
	}
	
	protected static void generateFolder(String sourcePath, String targetPath, File file, FilterSet filters, List<Pattern> excludes) throws Exception {
		String fileName = file.getName();
		if (!isMatch(excludes, fileName)) {
			if (file.isDirectory()) {
				sourcePath += "/" + fileName;
				targetPath += "/" + fileName;
				generateFolder(sourcePath, targetPath, filters, excludes); 
			} else {
				GenerateUtil.generateFile(sourcePath, fileName, targetPath, fileName, filters); 
			}
		}
	}


	public static boolean isMatch(List<Pattern> patterns, String text) {
		Iterator<Pattern> iterator = patterns.iterator();
		while (iterator.hasNext()) {
			Pattern pattern = iterator.next();
	        if (pattern != null) {
	            Matcher matcher = pattern.matcher(text);
	            if (matcher.matches())
	            	return true;
	        }
		}
    	return false;
	}

}
