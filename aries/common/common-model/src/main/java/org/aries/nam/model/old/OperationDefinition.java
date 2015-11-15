package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class OperationDefinition implements OperationDescripter, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String operationName;
	
	private String description;
	
	private boolean transacted;
	
	private List<ParameterDescripter> parameters = new ArrayList<ParameterDescripter>();
	
	private List<ResultDescripter> results = new ArrayList<ResultDescripter>();

	private Map<String, PropertyDescripter> properties = new HashMap<String, PropertyDescripter>();
	

	public String getOperationId() {
		return toString();
	}
	
	public String getName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
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

	public List<PropertyDescripter> getPropertyDescripters() {
		List<PropertyDescripter> list = new ArrayList<PropertyDescripter>(properties.values());
		return Collections.unmodifiableList(list);
	}

	public void addPropertyDescripter(PropertyDescripter descripter) {
		properties.put(descripter.getName(), descripter);
	}

	public List<ResultDescripter> getResultDescripters() {
		return results;
	}

	public void setResultDescripters(List<ResultDescripter> results) {
		this.results = results;
	}

	public int hashCode() {
		return operationName.hashCode();
	}

	public boolean equals(Object object) {
		if (getClass().isAssignableFrom(object.getClass())) {
			OperationDefinition other = (OperationDefinition) object;
			if (!other.operationName.equals(operationName))
				return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(operationName);
		if (parameters != null && parameters.size() > 0) {
			Iterator<ParameterDescripter> iterator = parameters.iterator();
			buf.append(", parameters=[");
			for (int i=0; iterator.hasNext(); i++) {
				ParameterDescripter parameter = iterator.next();
				if (i > 0)
					buf.append(", ");
				buf.append(parameter.getType());
			}
			buf.append("]");
		}
		return buf.toString();
	}
}
