package org.aries.nam.model.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ProcessDefinition implements ProcessDescripter {

	private static final long serialVersionUID = 1L;
	
	private String processId;

	private String processName;
	
	private String resultName;
	
	private String description;
	
	private boolean transacted;
	
	private List<ParameterDescripter> parameters = new ArrayList<ParameterDescripter>();
	
	private Map<String, ActionDescripter> actions = new LinkedHashMap<String, ActionDescripter>();
	
	private Map<String, PropertyDescripter> properties = new HashMap<String, PropertyDescripter>();
	
	private ResultDescripter result;


	public String getProcessId() {
		if (processId == null)
			return processName;
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isTransacted() {
		return transacted;
	}

	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}

	public List<ParameterDescripter> getParameterDescripters() {
		return parameters;
	}

	public void setParameterDescripters(List<ParameterDescripter> parameters) {
		this.parameters = parameters;
	}

	public void addParameterDescripter(ParameterDescripter parameter) {
		parameters.add(parameter);
	}

	public List<ActionDescripter> getActionDescripters() {
		List<ActionDescripter> list = new ArrayList<ActionDescripter>(actions.values());
		return Collections.unmodifiableList(list);
	}

	public void addActionDescripter(ActionDescripter descripter) {
		actions.put(descripter.getActionName(), descripter);
	}

	public List<PropertyDescripter> getPropertyDescripters() {
		List<PropertyDescripter> list = new ArrayList<PropertyDescripter>(properties.values());
		return Collections.unmodifiableList(list);
	}

	public void addPropertyDescripter(PropertyDescripter descripter) {
		properties.put(descripter.getName(), descripter);
	}

	public ResultDescripter getResultDescripter() {
		return result;
	}

	public void setResultDescripter(ResultDescripter result) {
		this.result = result;
	}

	public int hashCode() {
		return processId.hashCode();
	}

	public boolean equals(Object object) {
		if (getClass().isAssignableFrom(object.getClass())) {
			ProcessDefinition other = (ProcessDefinition)object;
			if (!other.getProcessId().equals(getProcessId()))
				return false;
		}
		return true;
	}

	public String toString() {
		return getProcessId();
	}
}
