package org.aries.audit_log;

import java.util.List;

import nam.model.Project;

import org.aries.AbstractGenerator;
import org.aries.ProjectBuilder;

import aries.generation.engine.GenerationContext;


public class AuditLogGeneratorMain extends AbstractGenerator {

	public static final String FILE_NAME = "/audit_log/audit_log.aries";	

	protected static String WORKSPACE_LOCATION = "c:/workspace/ARIES";
	//protected static String WORKSPACE_LOCATION = "c:/workspace/STAGING";
	

	public static void main(String[] args) throws Exception {
		AuditLogGeneratorMain main = new AuditLogGeneratorMain();
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
		//context.setProperty("generateServicePerOperation");
		//context.setProperty("generateMessageLevelTransport");
		context.addSubset("project");
		context.addSubset("service");
		context.addSubset("model");
		context.addSubset("client");
		context.addSubset("data");
		return context;
	}
	
}
