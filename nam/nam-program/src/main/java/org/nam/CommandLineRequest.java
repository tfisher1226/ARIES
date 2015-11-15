package org.nam;

import java.io.PrintStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;


public class CommandLineRequest {

	private String[] args;
	private CommandLine commandLine;
	private String workingDirectory;
	private boolean debug;
	private boolean quiet;
	private boolean showErrors = true;
	private PrintStream logStream;
	private Properties userProperties = new Properties();
	private Properties systemProperties = new Properties();
	private ExecutionRequest request;

	
	public CommandLineRequest(String[] args) {
		this.args = args;
		this.request = new ExecutionRequest();
	}

	
	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public CommandLine getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(CommandLine commandLine) {
		this.commandLine = commandLine;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public boolean getDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isQuiet() {
		return quiet;
	}

	public void setQuiet(boolean quiet) {
		this.quiet = quiet;
	}

	public boolean isShowErrors() {
		return showErrors;
	}

	public void setShowErrors(boolean showErrors) {
		this.showErrors = showErrors;
	}

	public PrintStream getLogStream() {
		return logStream;
	}

	public void setLogStream(PrintStream logStream) {
		this.logStream = logStream;
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

	public ExecutionRequest getRequest() {
		return request;
	}

	public void setRequest(ExecutionRequest request) {
		this.request = request;
	}

	
}
