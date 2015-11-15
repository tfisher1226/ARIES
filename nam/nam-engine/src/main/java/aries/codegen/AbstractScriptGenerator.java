package aries.codegen;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.aries.util.FileUtil;

import aries.generation.model.ModelScript;


public class AbstractScriptGenerator extends AbstractFileGenerator {
	
	public void generateScripts(List<ModelScript> scripts) throws Exception {
		Iterator<ModelScript> iterator = scripts.iterator();
		while (iterator.hasNext()) {
			ModelScript modelScript = iterator.next();
			generateScript(modelScript);
		}
	}

	public void generateScript(ModelScript modelScript) throws Exception {
		String projectName = context.getProjectName();
		String targetPath = context.getTargetPath(projectName);
		String targetFolder = modelScript.getTargetFolder();
		String targetFile = modelScript.getTargetFile();
		
		FileUtil.assureDirectoryExists(targetPath + "/" + targetFolder);
		FileUtil.assureFileExists(targetPath + "/" + targetFolder + "/" + targetFile);
		
		targetPath = targetPath + "/" + targetFolder + "/" + targetFile;
		String fileContent = modelScript.getContent();
		writeFile(targetPath, fileContent);
	}

	protected void writeFile(String filePath, String fileContent) throws Exception {
		FileWriter writer = null;
		try {
			System.out.println("Generating "+filePath);
			//TODO check an overwrite flag, and avoid writing if set AND file already exists
			//TODO verify if we have permission to write to targetPath
			writer = new FileWriter(filePath);
			writer.write(fileContent);
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	
}
