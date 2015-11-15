package org.aries.launcher;

import java.io.Serializable;


public class JavaProgram extends Program implements Serializable {

	private static final long serialVersionUID = 1L;

	private String javaHome;
	
	private String classPath;

	private String mainClass;

	private String archiveFile;

	private String archiveLocation;

	private String hostName;
	
	private int httpPort;

	private int jndiPort;

	private int jmxPort;

	private int rmiPort;


	public String getJavaHome() {
		return javaHome;
	}

	public void setJavaHome(String javaHome) {
		this.javaHome = javaHome;
	}
	
	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public String getArchiveFile() {
		return archiveFile;
	}

	public void setArchiveFile(String archiveFile) {
		this.archiveFile = archiveFile;
	}

	public String getArchiveLocation() {
		return archiveLocation;
	}

	public void setArchiveLocation(String archiveLocation) {
		this.archiveLocation = archiveLocation;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public int getJndiPort() {
		return jndiPort;
	}

	public void setJndiPort(int jndiPort) {
		this.jndiPort = jndiPort;
	}

	public int getJmxPort() {
		return jmxPort;
	}

	public void setJmxPort(int jmxPort) {
		this.jmxPort = jmxPort;
	}

	public int getRmiPort() {
		return rmiPort;
	}

	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}
	
}
