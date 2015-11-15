package org.aries;

import aries.generation.engine.GenerationContext;


public abstract class AbstractGenerator {

	protected static String USER_DIR = System.getProperty("user.dir");

	protected static String MODEL_LOCATION = USER_DIR + "/src/main/resources";
	
	protected static String START_LOCATION = USER_DIR + "/..";
	
	protected static String PROPERTY_LOCATION = START_LOCATION + "/aries/properties";

	protected static String TEMPLATE_LOCATION = START_LOCATION + "/aries/templates";
	
	protected static String RUNTIME_LOCATION = USER_DIR + "/target/runtime";

	protected static String CACHE_LOCATION = USER_DIR + "/target/runtime/cache";


	protected ProjectBuilder createProjectBuilder(String fileName) throws Exception {
		ProjectBuilder builder = new ProjectBuilder(createContext());
		builder.initialize(fileName);
		return builder;
	}
	
	protected GenerationContext createContext() {
		GenerationContext context = new GenerationContext();
		//GenerationContext.INSTANCE = context;
		
		//locations
		context.setModelLocation(MODEL_LOCATION);
		context.setPropertyLocation(PROPERTY_LOCATION);
		//context.setWorkspaceLocation(WORKSPACE_LOCATION);
		context.setRuntimeLocation(RUNTIME_LOCATION);
		context.setCacheLocation(CACHE_LOCATION);
    	context.setTemplateWorkspace(TEMPLATE_LOCATION);
    	context.setTemplateName("template1");
    	
    	//properties
    	context.setProperty("overwriteAll");
		context.setProperty("generateServiceTransport", "JAXWS");
		context.setProperty("useQualifiedContextNames");
		context.setProperty("generateTableAnnotation");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
		
		//contents
		//context.addSubset("all");
		//context.addSubset("pom");
		context.addSubset("project");
		context.addSubset("sources");
		context.addSubset("tests");
		//context.addSubset("client");
		//context.addSubset("data");
		//context.addSubset("view");
		//context.addSubset("ear");
		return context;
	}
	
}
