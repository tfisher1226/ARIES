package org.aries.nam;

import nam.model.Application;
import nam.model.Project;
import nam.model.User;
import nam.model.util.ExtensionsUtil;
import nam.model.util.ProjectUtil;


public class ModelTestFixture {

	public static String getProjectNameForTest() {
		return "project1";
	}
	
	public static String getProjectNamespaceForTest() {
		return "http://project1";
	}
	
	public static String getProjectDomainForTest() {
		return "domain1";
	}
	
	public static Project createProjectForTest(String userId) {
		Project project = new Project();
		project.setOwner(userId);
		project.setName(getProjectNameForTest());
		project.setNamespace(getProjectNamespaceForTest());
		project.setDomain(getProjectDomainForTest());
		User user = UserTestFixture.createUserForTest(userId);
		//Configuration configuration = ConfigurationTestFixture.createConfigurationForTest(user);
		Application application = ApplicationTestFixture.createApplicationForTest(user);
		ProjectUtil.addConfiguration(project, application.getConfiguration());
		ProjectUtil.addApplication(project, application);
		project.setExtensions(ExtensionsUtil.newExtensions());
		return project;
	}

}
