package org.aries.generator;

import java.io.File;

import org.apache.commons.io.FileUtils;

import aries.generation.engine.GenerationContext;
import aries.generation.engine.GeneratorEngine;


public class AriesCommonModelGeneratorOLD {

	private static String ARIES_COMMON_MODEL = "aries-common-1.0.aries";

	private static String USER_DIR = System.getProperty("user.dir");

	private static String RUNTIME_LOCATION = USER_DIR + "/src/main/resources/schema";

	private static String WORKSPACE_LOCATION = "C:/workspace/STAGING";

	private static String MODEL_LOCATION = WORKSPACE_LOCATION + "/common/projects/common-model/model";
	
	
	public static void main(String[] args) throws Exception {
		File ariesCommonSchemaFile = new File(WORKSPACE_LOCATION + "/common/common-model/src/main/resources/schema/common" + ARIES_COMMON_MODEL);
		File runtimeLocation = new File(RUNTIME_LOCATION);
		
		FileUtils.copyFileToDirectory(ariesCommonSchemaFile, runtimeLocation);
       	generateFromInformationModel(ARIES_COMMON_MODEL);
	}

    protected static void generateFromInformationModel(String inputFile) throws Exception {
       	String inputPath = RUNTIME_LOCATION + "/" + inputFile;
   		GenerationContext context = createGenerationContext();
       	GeneratorEngine engine = new GeneratorEngine(context);
       	engine.initialize();
       	engine.generateModelLayer(inputPath);
    }
    
    protected static GenerationContext createGenerationContext() {
		GenerationContext context = new GenerationContext();
		context.setProperty("generateJavadoc");

    	//context.setProjectName(projectName);
		context.setRuntimeLocation(RUNTIME_LOCATION);
		context.setWorkspaceLocation(WORKSPACE_LOCATION);
		context.setModelLocation(MODEL_LOCATION);
    	context.setTargetWorkspace("..");
    	context.setTemplateWorkspace("..");
    	context.setTemplateName("template1");
    	context.setProjectName("common-model");
    	context.setProjectPrefix("common");
    	context.setProjectGroupId("org.aries");
    	context.setProjectDomain("org.aries");
    	context.setProjectVersion("1.0");
		//context.addSubset("project");
		context.addSubset("model");
		return context;
	}

}
