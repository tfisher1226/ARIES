package aries.codegen;

import org.apache.tools.ant.types.FilterSet;

import aries.generation.model.ModelScript;


public abstract class AbstractScriptBuilder extends AbstractFileBuilder {

	public ModelScript buildModelScript(String folderName, String fileName) throws Exception {
		ModelScript modelScript = buildModelScript(folderName, fileName, folderName, fileName, null);
		return modelScript;
	}

	public ModelScript buildModelScript(String folderName, String fileName, FilterSet filterSet) throws Exception {
		ModelScript modelScript = buildModelScript(folderName, fileName, folderName, fileName, filterSet);
		return modelScript;
	}

	public ModelScript buildModelScript(String sourceFolder, String sourceFile, String targetFolder, String targetFile) throws Exception {
		ModelScript modelScript = buildModelScript(sourceFolder, sourceFile, targetFolder, targetFile, null);
		return modelScript;
	}
	
	public ModelScript buildModelScript(String sourceFolder, String sourceFile, String targetFolder, String targetFile, FilterSet filterSet) throws Exception {
		String content = buildFile(sourceFolder, sourceFile, filterSet);
		ModelScript modelScript = new ModelScript();
		modelScript.setSourceFolder(sourceFolder);
		modelScript.setSourceFile(sourceFile);
		modelScript.setTargetFolder(targetFolder);
		modelScript.setTargetFile(targetFile);
		modelScript.setContent(content);
		//modelScript.setScriptType("");
		return modelScript;
	}

//	public FilterSet buildFilterSet() {
//		String projectName = context.getProjectName();
//		String projectNameCapped = context.getProjectNameCapped();
//		FilterSet filterSet = new MyFilterSet();
//		filterSet.addFilter("template1", projectName);
//		filterSet.addFilter("Template1", projectNameCapped);
//		return filterSet;
//	}

}
