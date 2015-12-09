package admin;

import java.util.List;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class AdminViewGeneratorMain extends AbstractGenerator {

	public static String FILE_NAME = "/admin/admin_service.aries";

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING";
	

	public static void main(String[] args) throws Exception {
		AdminViewGeneratorMain main = new AdminViewGeneratorMain();
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
		context.setProperty("generateServiceTransport", "JAXWS");
		context.setProperty("useQualifiedContextNames");
		context.setProperty("generateTableAnnotation");
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
		
		context.addSubset("project", "admin");
		context.addSubset("application", "admin");
		
		context.addSubset("pom");
		//context.addSubset("client");
		context.addSubset("model");
		context.addSubset("service");
		//context.addSubset("data");
		context.addSubset("war");
		return context;
	}
	
}
