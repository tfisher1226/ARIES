package org.aries.launcher;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.aries.Assert;


public class MavenProgram extends JavaProgram implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_POM = "pom.xml";

	private static final String DEFAULT_GLOBAL_SETTINGS = "conf/settings.xml";

	private static final String DEFAULT_USER_SETTINGS = "c:/workspace/.m2/settings.xml";

	private static final String DEFAULT_LOCAL_REPOSITORY = "c:/workspace/.m2/repository";

	
	private String mavenHome;

	private File pomFile;

	private File workingDirectory;

	private String localRepositoryPath;

	private List<String> goals = new ArrayList<String>();

	private List<String> profiles = new ArrayList<String>();

	private Properties userProperties = new Properties();

	private Properties systemProperties = new Properties();

	private File globalSettingsFile;

	private File userSettingsFile;


	public MavenProgram() {
		localRepositoryPath = DEFAULT_LOCAL_REPOSITORY;
		userProperties.put("maven.test.skip", "true");
		systemProperties.putAll(System.getProperties());
	}

	public void initialize() {
		if (mavenHome == null)
			mavenHome = systemProperties.getProperty("maven.home");
		Assert.notNull(mavenHome, "MavenHome must be specified");
		if (workingDirectory == null)
			workingDirectory = new File(System.getProperty("user.dir")).getAbsoluteFile();
		if (pomFile == null)
			pomFile = new File(workingDirectory, DEFAULT_POM);
		if (globalSettingsFile == null)
			globalSettingsFile = new File(mavenHome, DEFAULT_GLOBAL_SETTINGS);
		if (userSettingsFile == null)
			userSettingsFile = new File(DEFAULT_USER_SETTINGS);
		if (localRepositoryPath == null)
			localRepositoryPath = DEFAULT_LOCAL_REPOSITORY;
		Assert.isTrue(goals.size() > 0 || profiles.size() > 0, "At least one goal or profile must be specified");
	}
	

	public String getMavenHome() {
		return mavenHome;
	}

	public void setMavenHome(String mavenHome) {
		this.mavenHome = mavenHome;
	}

	public File getPomFile() {
		return pomFile;
	}

	public void setPomFile(File pomFile) {
		this.pomFile = pomFile;
	}

	public void setPomFile(String pomFile) {
		this.pomFile = new File(workingDirectory, pomFile);
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public String getLocalRepositoryPath() {
		return localRepositoryPath;
	}

	public void setLocalRepositoryPath(String localRepositoryPath) {
		this.localRepositoryPath = localRepositoryPath;
	}

	public List<String> getGoals() {
		return goals;
	}

	public void setGoals(List<String> goals) {
		this.goals = goals;
	}

	public void addGoal(String goal) {
		goals.add(goal);
	}

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

	public void addProfile(String profile) {
		profiles.add(profile);
	}

	public Properties getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(Properties userProperties) {
		this.userProperties = userProperties;
	}

	public Properties getSystemProperties() {
		return systemProperties;
	}

	public void setSystemProperties(Properties systemProperties) {
		this.systemProperties = systemProperties;
	}

	public File getGlobalSettingsFile() {
		return globalSettingsFile;
	}

	public void setGlobalSettingsFile(File globalSettingsFile) {
		this.globalSettingsFile = globalSettingsFile;
	}

	public void setGlobalSettingsFile(String globalSettingsFile) {
		this.globalSettingsFile = new File(globalSettingsFile);
	}

	public File getUserSettingsFile() {
		return userSettingsFile;
	}

	public void setUserSettingsFile(File userSettingsFile) {
		this.userSettingsFile = userSettingsFile;
	}

	public void setUserSettingsFile(String userSettingsFile) {
		this.userSettingsFile = new File(userSettingsFile);
	}

}
