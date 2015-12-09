package nam;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class NamModelGeneratorMain extends AbstractGenerator {

	public static String FILE_NAME = "/nam/nam_model.aries";

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING";
	

	public static void main(String[] args) throws Exception {
		NamModelGeneratorMain main = new NamModelGeneratorMain();
		main.generate();
	}

	protected void generate() throws Exception {
		String domain = "org.aries";
		String name = "nam";
		String namespace = "http://nam/model";
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
		context.addSubset("model");
		return context;
	}

}
