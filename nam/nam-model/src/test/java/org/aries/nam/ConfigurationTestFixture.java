package org.aries.nam;

import nam.model.Configuration;
import nam.model.User;


public class ConfigurationTestFixture {

	public static String getGroupIdForTest() {
		return "org.aries.sample1";
	}
	
	public static String getArtifactIdForTest() {
		return "parent";
	}

	public static String getVersionForTest() {
		return "0.0.1-SNAPSHOT";
	}

	public static Configuration createConfigurationForTest() {
		User user = UserTestFixture.createUserForTest();
		return createConfigurationForTest(user);
	}

	public static Configuration createConfigurationForTest(User user) {
		Configuration configuration = new Configuration();
		configuration.setGroupId(getGroupIdForTest());
		configuration.setArtifactId(getArtifactIdForTest());
		configuration.setVersion(getVersionForTest());
		configuration.setOwner(user.getUserName());
		return configuration;
	}
	
}
