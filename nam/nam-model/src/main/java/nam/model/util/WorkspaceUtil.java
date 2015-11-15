package nam.model.util;

import java.util.List;

import nam.model.Project;
import nam.model.Projects;
import nam.model.Workspace;



public class WorkspaceUtil {

	public static Project getProject(Workspace workspace) {
		List<Project> projects = getProjects(workspace);
		if (projects.size() > 0)
			return projects.get(0);
		return null;
	}
	
	public static List<Project> getProjects(Workspace workspace) {
		List<String> projectNames = workspace.getProjects();
		//TODO convert names to projects
		Projects projects = new Projects();
		return projects.getProjects();
	}


//	public static List<Configuration> getConfigurations(Workspace workspace) {
//		Configurations configurations = workspace.getConfigurations();
//		if (configurations == null) {
//			configurations = new Configurations();
//			workspace.setConfigurations(configurations);
//		}
//		return configurations.getConfigurations();
//	}
//	
//	public static List<Application> getApplications(Workspace workspace) {
//		Applications applications = workspace.getApplications();
//		if (applications == null) {
//			applications = new Applications();
//			workspace.setApplications(applications);
//		}
//		return applications.getApplications();
//	}
//
//	public static List<Property> getProperties(Workspace workspace) {
//		Properties properties = workspace.getProperties();
//		if (properties == null) {
//			properties = new Properties();
//			workspace.setProperties(properties);
//		}
//		return properties.getProperties();
//	}
	
}
