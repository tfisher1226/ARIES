package org.nam.generator;

import java.net.URL;

import aries.generation.engine.GenerationContext;


public class AbstractBuilderTest {

	protected GenerationContext context;
	

	protected GenerationContext getGenerationContextForTest() throws Exception {
		context = new GenerationContext();
		context.setTargetWorkspace("../nam-generated");
		context.setTemplateWorkspace("..");
		context.setTemplateName("template1");
		context.setProjectName("model1");
		context.setProjectPrefix("model1");
		context.setProjectDomain("model1.org");
		context.setProjectGroupId("org.model1");
		context.setProjectVersion("0.0.1-SNAPSHOT");
		return context;
	}

	protected URL getResourceForTest(String fileName) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(fileName);
		return resource; 
	}

}
