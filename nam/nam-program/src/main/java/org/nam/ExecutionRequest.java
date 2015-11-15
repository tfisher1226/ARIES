package org.nam;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.aries.nam.model.Project;


public class ExecutionRequest {
    
	private Project project;
	
    private File baseDirectory;

    private List<String> projects;

    private Properties systemProperties;

    private Properties userProperties;

    private boolean showErrors = false;

    private int loggingLevel = Logging.LOG_LEVEL_INFO;

    //private ExecutionListener executionListener;

    //private GenerationListener generationListener;

    
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public File getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(File baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public Properties getSystemProperties() {
		return systemProperties;
	}

	public void setSystemProperties(Properties systemProperties) {
		this.systemProperties = systemProperties;
	}

	public Properties getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(Properties userProperties) {
		this.userProperties = userProperties;
	}

	public boolean isShowErrors() {
		return showErrors;
	}

	public void setShowErrors(boolean showErrors) {
		this.showErrors = showErrors;
	}

	public int getLoggingLevel() {
		return loggingLevel;
	}

	public void setLoggingLevel(int loggingLevel) {
		this.loggingLevel = loggingLevel;
	}

}
