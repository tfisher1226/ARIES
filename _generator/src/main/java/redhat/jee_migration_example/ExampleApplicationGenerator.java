package redhat.jee_migration_example;

import java.util.List;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class ExampleApplicationGenerator extends AbstractGenerator {

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING/redhat";
	
	public static String FILE_NAME = "/redhat/jee-migration-example/application.ariel";


	public static void main(String[] args) throws Exception {
		ExampleApplicationGenerator main = new ExampleApplicationGenerator();
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
		context.setProperty("generateServiceTransport", "JMS");
		context.setProperty("useQualifiedContextNames");
		//context.setProperty("useMessageOrientedInteraction");
		context.setProperty("generateTableAnnotation");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
		//context.setProperty("prependProjectNameToArtifactId");
		
		context.addSubset("pom");
		context.addSubset("client");
		context.addSubset("model");
		context.addSubset("service");
		context.addSubset("data");
		//context.addSubset("war");
		//context.addSubset("ear");
		 
		context.addSubset("project", "jee-migration-example");
		context.addSubset("application", "eventLogger");
		return context;
	}
	
}
