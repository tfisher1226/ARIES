package bookshop;

import java.util.List;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class BookshopGeneratorMain extends AbstractGenerator {

	//public static String FILE_NAME = "/bookshop/tmp.ariel";
	public static String FILE_NAME = "/bookshop/bookshop.ariel";
	//public static final String FILE_NAME = "/source/main/resources/bookshop.ariel";
	//public static final String FILE_NAME = "c:/workspace/ARIES/ariel-compiler/source/main/resources/bookshop.ariel";

	public static String WORKSPACE_LOCATION = "c:/workspace/STAGING";
	

	public static void main(String[] args) throws Exception {
		BookshopGeneratorMain main = new BookshopGeneratorMain();
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
		
		context.addSubset("pom");
		context.addSubset("client");
		context.addSubset("model");
		context.addSubset("service");
		context.addSubset("data");
//		context.addSubset("war");
//		context.addSubset("ear");
		 
		context.addSubset("project", "bookshop2");
//		context.addSubset("application", "bookshop2");
//		context.addSubset("application", "buyer");
//		context.addSubset("application", "seller");
		context.addSubset("application", "supplier");
		context.addSubset("application", "shipper");
		
		/*
		context.addSubset("project");
		context.addSubset("service");
		context.addSubset("model");
		context.addSubset("client");
		context.addSubset("data");
		context.addSubset("view");
		context.addSubset("ear");
		 */
		return context;
	}
	
}
