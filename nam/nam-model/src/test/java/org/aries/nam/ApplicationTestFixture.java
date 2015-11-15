package org.aries.nam;

import nam.model.Application;
import nam.model.Configuration;
import nam.model.Information;
import nam.model.Namespace;
import nam.model.User;
import nam.model.util.InformationUtil;


public class ApplicationTestFixture {

	public static String getNameForTest() {
		return "application1";
	}

	public static String getGroupIdForTest() {
		return "org.aries";
	}
	
	public static String getArtifactIdForTest() {
		return getNameForTest();
	}

	public static String getNamespaceForTest() {
		return "http://"+getNameForTest();
	}

	public static String getVersionForTest() {
		return "0.0.1-SNAPSHOT";
	}

	public static Application createApplicationForTest() {
		User user = UserTestFixture.createUserForTest();
		return createApplicationForTest(user);
	}

	public static Application createApplicationForTest(User user) {
		Configuration configuration = ConfigurationTestFixture.createConfigurationForTest();
		Application application = new Application();
		application.setName(getNameForTest());
		application.setGroupId(getGroupIdForTest());
		application.setArtifactId(getArtifactIdForTest());
		application.setVersion(getVersionForTest());
		application.setNamespace(getNamespaceForTest());
		application.setInformation(createInformationForTest(getNamespaceForTest()));
		application.setConfiguration(configuration);
		application.setCreator(user.getUserName());
		return application;
	}
	
	public static Information createInformationForTest(String namespaceUrl) {
		Information information = InformationUtil.newInformation();
		Namespace namespace = new Namespace();
		namespace.setUri(namespaceUrl);
		InformationUtil.addNamespace(information, namespace);
		return information;
	}
}
