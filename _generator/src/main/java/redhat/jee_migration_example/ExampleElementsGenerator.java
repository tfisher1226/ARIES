package redhat.jee_migration_example;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class ExampleElementsGenerator extends AbstractGenerator {

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING/redhat";
	
	public static String FILE_NAME = "/redhat/jee-migration-example/elements.aries";
	

	public static void main(String[] args) throws Exception {
		ExampleElementsGenerator main = new ExampleElementsGenerator();
		main.generate();
	}

	protected void generate() throws Exception {
		String domain = "redhat";
		String name = "jee-migration-example";
		String namespace = "http://redhat/jee-migration-example";
		String version = "0.0.1-SNAPSHOT";

		//String inputPath = RUNTIME_LOCATION + "/" + FILE_NAME;
       	ProjectBuilder builder = createProjectBuilder(FILE_NAME);
       	Project project = builder.buildProject(domain, name, namespace, version);
       	
		builder.buildInformationModel(project);
       	//engine.generateModelLayer(inputPath);
		builder.generateProject(project);
	}

	protected ProjectBuilder createProjectBuilder(String fileName) throws Exception {
		ProjectBuilder builder = new ProjectBuilder(createContext());
		builder.initialize(fileName);
		return builder;
	}
	
	protected GenerationContext createContext() {
		GenerationContext context = super.createContext();
		context.setWorkspaceLocation(WORKSPACE_LOCATION);
		//context.setProperty("generateJavadoc");
		context.addSubset("pom");
		context.addSubset("model");
		return context;
	}

}
