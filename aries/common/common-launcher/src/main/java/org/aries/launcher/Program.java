package org.aries.launcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Program implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String path;

	private String location;

	private ProgramType programType;

	private List<String> options = new ArrayList<String>();

	private List<String> arguments = new ArrayList<String>();;

	private Map<String, String> environment = new HashMap<String, String>();

	private boolean outputEnabled = true;

	private boolean monitored;
	
	
	public Program() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}
	
    public void setEnvironment(Map<String, String> environment) {
    	this.environment = environment;
    }

    public boolean isOutputEnabled() {
		return outputEnabled;
	}

	public void setOutputEnabled(boolean outputEnabled) {
		this.outputEnabled = outputEnabled;
	}

	public boolean isMonitored() {
		return monitored;
	}

	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}

}
