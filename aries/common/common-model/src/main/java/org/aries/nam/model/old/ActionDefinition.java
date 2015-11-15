package org.aries.nam.model.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActionDefinition implements ActionDescripter {

	private static final long serialVersionUID = 1L;
	
	private String actionName;

    private String className;
    
    private String description;

	private List<ParameterDescripter> parameters = new ArrayList<ParameterDescripter>();

    private Map<String, PropertyDescripter> properties = new HashMap<String, PropertyDescripter>();

	private ResultDescripter result;


    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) { 
    	this.actionName = actionName; 
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
    	this.className = className;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    	this.description = description;
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

	public List<PropertyDescripter> getPropertyDescripters() {
		return Collections.unmodifiableList(new ArrayList<PropertyDescripter>(properties.values()));
    }

    public void addPropertyDescripter(PropertyDescripter Descripter) {
    	properties.put(Descripter.getName(), Descripter);
    }

	public ResultDescripter getResultDescripter() {
		return result;
	}

	public void setResultDescripter(ResultDescripter result) {
		this.result = result;
	}
	
	
    public boolean equals(Object object) {
    	if (getClass().isAssignableFrom(object.getClass())) {
    		ActionDefinition other = (ActionDefinition) object;
    		if (!other.getActionName().equals(getActionName()))
    			return false;
    	}
    	return true;
    }

    public int hashCode() {
    	return getActionName().hashCode();
    }

    public String toString() {
    	return getActionName();
    }
    
}
