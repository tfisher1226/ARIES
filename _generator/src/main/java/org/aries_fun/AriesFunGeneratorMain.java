package org.aries_fun;

import java.util.List;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class AriesFunGeneratorMain extends AbstractGenerator {

	public static final String FILE_NAME = "/aries_fun/aries_fun.aries";	

	//protected static String WORKSPACE_LOCATION = "c:/workspace/ARIES";
	protected static String WORKSPACE_LOCATION = "c:/workspace/STAGING";
	

	public static void main(String[] args) throws Exception {
		AriesFunGeneratorMain main = new AriesFunGeneratorMain();
		main.generate();
	}

	protected void generate() throws Exception {
		ProjectBuilder builder = createProjectBuilder();
		List<Project> projects = builder.buildProjects();
		builder.generateProjects(projects);
	}

	protected ProjectBuilder createProjectBuilder() throws Exception {
		ProjectBuilder builder = new ProjectBuilder(createContext());
		builder.initialize(FILE_NAME);
		return builder;
	}
	
	protected GenerationContext createContext() {
		GenerationContext context = super.createContext();
		context.setWorkspaceLocation(WORKSPACE_LOCATION);
		context.setProperty("generateServiceTransport", "JAXWS");
		context.setProperty("useQualifiedContextNames");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
		context.addSubset("project");
		context.addSubset("service");
		context.addSubset("model");
		context.addSubset("client");
		context.addSubset("data");
		context.addSubset("view");
		context.addSubset("ear");
		return context;
	}
	
}
