package aries.codegen;

import java.io.File;
import java.io.FileReader;

import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.util.FileUtils;

import aries.codegen.util.FilterUtil;
import aries.codegen.util.MyFilterSet;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public abstract class AbstractFileBuilder extends AbstractBuilder {

	public AbstractFileBuilder() {
		//do nothing
	}
	
	public AbstractFileBuilder(GenerationContext context) {
		super(context);
	}
	
	protected ModelFile createMainResourcesFile(String fileName) throws Exception {
		return createFile(context.getMainResourcesFolder(), fileName);
	}

	protected ModelFile createResourcesFile(boolean isTest, String fileName) throws Exception {
		return createFile(context.getResourcesFolder(isTest), fileName);
	}

	protected ModelFile createMainResourcesFile(String folderName, String fileName) throws Exception {
		return createFile(context.getMainResourcesFolder() + "/" + folderName, fileName);
	}

	protected ModelFile createResourcesFile(boolean isTest, String folderName, String fileName) throws Exception {
		return createFile(context.getResourcesFolder(isTest) + "/" + folderName, fileName);
	}

	protected ModelFile createTestResourcesFile(String fileName) throws Exception {
		return createFile(context.getTestResourcesFolder(), fileName);
	}
	
	protected ModelFile createTestResourcesFile(String folderName, String fileName) throws Exception {
		return createFile(context.getTestResourcesFolder() + "/" + folderName, fileName);
	}
	
	protected ModelFile createMainWebappFile(String fileName) throws Exception {
		return createFile(context.getMainWebappFolder(), fileName);
	}
	
	protected ModelFile createMainWebappFile(String folderName, String fileName) throws Exception {
		return createFile(context.getMainWebappFolder() + "/" + folderName, fileName);
	}

	protected ModelFile createFile(String folderName, String fileName) throws Exception {
		String projectName = context.getProjectName();
		String targetPath = context.getTargetPath(projectName);
		
		ModelFile modelFile = new ModelFile();
		modelFile.setTargetFile(fileName);
		modelFile.setTargetFolder(folderName);
		modelFile.setTargetPath(targetPath + "/" + folderName);
		return modelFile;
	}
	
	public String buildFile(String sourceFolder, String sourceFile) throws Exception {
		String output = buildFile(sourceFolder, sourceFile, (FilterSet) null);
		return output;
	}
	
	public String buildFile(String sourceFolder, String sourceFile, FilterSet filterSet) throws Exception {
		String projectName = context.getProjectName();
		String projectNameCapped = context.getProjectNameCapped();
		String projectNameUpper = context.getProjectNameUpper();
		String projectDomain = context.getProjectDomain();

		FilterSet myFilterSet = new MyFilterSet(filterSet);
		myFilterSet.addFilter("template1", projectName);
		myFilterSet.addFilter("Template1", projectNameCapped);
		myFilterSet.addFilter("TEMPLATE1", projectNameUpper);
		myFilterSet.addFilter("template1Domain", projectDomain);
		
		sourceFolder = context.getTemplateWorkspace()+"/"+context.getTemplateHome()+"/"+sourceFolder;
		FilterSetCollection filterCollection = FilterUtil.createFilterSetCollection(myFilterSet);
		String output = buildFile(sourceFolder, sourceFile, filterCollection);
		return output;
	}
	
	public String buildFile(String sourceFolder, String sourceFile, FilterSetCollection filters) throws Exception {
		String tempFolder = System.getProperty("java.io.tmpdir");
		if (!tempFolder.endsWith(File.separator))
			tempFolder += File.separator;
		tempFolder += sourceFile;
		//System.out.println("Reading file: "+sourceFolder+"/"+sourceFile);
		FileUtils.getFileUtils().copyFile(sourceFolder+"/"+sourceFile, tempFolder, filters, true);
		FileReader reader = new FileReader(tempFolder);
		String output = FileUtils.readFully(reader);
		return output;
	}
	
}
