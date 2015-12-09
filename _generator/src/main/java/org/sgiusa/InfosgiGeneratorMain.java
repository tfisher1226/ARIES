package org.sgiusa;

import java.util.List;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class InfosgiGeneratorMain extends AbstractGenerator {

	public static String FILE_NAME = "/sgiusa/infosgi.aries";

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING";

	public static String RUNTIME_LOCATION = USER_DIR + "/src/main/resources";


	public static void main(String[] args) throws Exception {
		InfosgiGeneratorMain main = new InfosgiGeneratorMain();
		main.generate();
	}

	protected void generate() throws Exception {
		ProjectBuilder builder = createProjectBuilder(FILE_NAME);
		List<Project> projects = builder.buildProjects();
		builder.generateProjects(projects);
	}

	protected ProjectBuilder createProjectBuilder(String fileName) throws Exception {
		ProjectBuilder builder = new ProjectBuilder(createContext());
		builder.initialize(fileName);
		return builder;
	}
	
	protected GenerationContext createContext() {
		GenerationContext context = super.createContext();
		context.setWorkspaceLocation(WORKSPACE_LOCATION);
		context.setRuntimeLocation(RUNTIME_LOCATION);
		
		context.setProperty("generateServiceTransport", "JAXWS");
		context.setProperty("useQualifiedContextNames");
		context.setProperty("generateTableAnnotation");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
		
		context.addSubset("pom");
		//context.addSubset("project");
		context.addSubset("client");
		context.addSubset("model");
		context.addSubset("service");
		context.addSubset("data");
		//context.addSubset("view");
		//context.addSubset("ear");
		
		context.addSubset("project", "infosgi");
		context.addSubset("application", "infosgi");

		return context;
	}
	
}
