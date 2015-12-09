package common;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class CommonDataGeneratorMain extends AbstractGenerator {

	public static String FILE_NAME = "/common/aries-common-data-1.0.aries";

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING";
	

	public static void main(String[] args) throws Exception {
		CommonDataGeneratorMain main = new CommonDataGeneratorMain();
		main.generate();
	}

	protected void generate() throws Exception {
		String domain = "org.aries";
		String name = "common";
		String namespace = "http://www.aries.org/common";
		String version = "0.0.1-SNAPSHOT";

		//String inputPath = RUNTIME_LOCATION + "/" + FILE_NAME;
       	ProjectBuilder builder = createProjectBuilder(FILE_NAME);
       	Project project = builder.buildProject(domain, name, namespace, version);
		builder.buildPersistenceModel(project);
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
		context.addSubset("data");
		return context;
	}
	
}
