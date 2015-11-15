package org.aries.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class InvocationValues {
	
//	private TransportType transportType = TransportType.EJB;

	private List<String> parameterNames = new ArrayList<String>();

	private Map<String, InvocationValue> parameters = new HashMap<String, InvocationValue>();

//	private Map<String, String> parameterTypes = new HashMap<String, String>();
//
//	private Map<String, String> parameterClasses = new HashMap<String, String>();
//	
//	private Map<String, Object> parameterValues = new HashMap<String, Object>();

	private String parameterString;
	
//	private String resultName;
//
//	private String resultType;
//
//	private String resultClass;
//
//	private Object resultValue;

	private InvocationValue result;

	
//	public TransportType getTransportType() {
//		return transportType;
//	}
//
//	public void setTransportType(TransportType transportType) {
//		this.transportType = transportType;
//	}
	
	public InvocationValue getInvocationValue(String name) {
		if (result != null && result.getName().equals(name))
			return result;
		return parameters.get(name);
	}

	public List<String> getParameterNames() {
		return parameterNames;
	}
	
//	public Map<String, String> getParameterTypes() {
//		return parameterTypes;
//	}
//
//	public String getParameterType(String name) {
//		return parameterTypes.get(name);
//	}
//	
//	public String getParameterClass(String name) {
//		return parameterClasses.get(name);
//	}
	
	public InvocationValue getParameter(String name) {
		InvocationValue invocationValue = parameters.get(name);
		return invocationValue;
	}
	
	public String getParameterString() {
		if (parameterString == null)
			parameterString = createParameterString();
		return parameterString;
	}
	
	public String createParameterString() {
		StringBuffer buf = new StringBuffer();
		Iterator<String> iterator = parameters.keySet().iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String name = iterator.next();
			if (i > 0)
				buf.append(", ");
			buf.append(name);
		}
		return buf.toString();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParameterValue(String name) {
		InvocationValue invocationValue = parameters.get(name);
		return (T) invocationValue.getValue();
	}
	
	public void setParameterValue(String name, Object value) {
		InvocationValue parameter = parameters.get(name);
		parameter.setValue(value);
	}


//	public Map<String, Object> getParameterValues() {
//		return parameterValues;
//	}

	public void addParameterPlaceholder(String clazz, String name) {
		addParameterPlaceholder("item", clazz, null, name, null);
	}

	public void addParameterPlaceholder(String clazz, String name, Object value) {
		addParameterPlaceholder("item", clazz, null, name, value);
	}

	public void addParameterListPlaceholder(String clazz, String name) {
		addParameterPlaceholder("list", clazz, null, name, null);
	}

	public void addParameterListPlaceholder(String clazz, String name, Object value) {
		addParameterPlaceholder("list", clazz, null, name, value);
	}

	public void addParameterSetPlaceholder(String clazz, String name) {
		addParameterPlaceholder("set", clazz, null, name, null);
	}

	public void addParameterSetPlaceholder(String clazz, String name, Object value) {
		addParameterPlaceholder("set", clazz, null, name, value);
	}

	public void addParameterMapPlaceholder(String clazz, String key, String name) {
		addParameterPlaceholder("map", clazz, key, name, null);
	}
	
	public void addParameterMapPlaceholder(String clazz, String key, String name, Object value) {
		addParameterPlaceholder("map", clazz, key, name, value);
	}
	
//	public void addParameterPlaceholder(String type, String name) {
//		addParameterPlaceholder(type, name, null);
//	}
	
	public void addParameterPlaceholder(String structure, String clazz, String key, String name, Object value) {
		InvocationValue parameter = new InvocationValue();
		parameter.setName(name);
		parameter.setType(getStructuredType(structure, clazz, key));
		parameter.setKey(key);
		parameter.setStructure(structure);
		parameter.setModule(getModule(structure, clazz));
		parameter.setClazz(clazz);
		parameters.put(name, parameter);
		if (!parameterNames.contains(name))
			parameterNames.add(name);
	}

	
	public boolean hasResult() {
		return result != null;
	}

	public InvocationValue getResult() {
		return result;
	}
	
//	public String getResultName() {
//		return resultName;
//	}

//	public String getResultType() {
//		return resultType;
//	}
//
//	public String getResultClass() {
//		return resultClass;
//	}
//
//	public Object getResultValue() {
//		return resultValue;
//	}

	public void addResultPlaceholder(String clazz, String name) {
		addResultPlaceholder("item", clazz, null, name);
	}

	public void addResultListPlaceholder(String clazz, String name) {
		addResultPlaceholder("list", clazz, null, name);
	}

	public void addResultSetPlaceholder(String clazz, String name) {
		addResultPlaceholder("set", clazz, null, name);
	}

	public void addResultMapPlaceholder(String clazz, String key, String name) {
		addResultPlaceholder("map", clazz, key, name);
	}

	public void addResultPlaceholder(String structure, String clazz, String key, String name) {
		result = new InvocationValue();
		result.setName(name);
		result.setType(getStructuredType(structure, clazz, key));
		result.setKey(key);
		result.setStructure(structure);
		result.setModule(getModule(structure, clazz));
		result.setClazz(clazz);
	}
	
	public void setResultValue(Object resultValue) {
		this.result.setValue(resultValue);
	}


	protected String getStructuredType(String structure, String clazz, String key) {
		if (structure.equalsIgnoreCase("item")) {
			return clazz;
		} else if (structure.equalsIgnoreCase("list")) {
			return  "List<"+clazz+">";
		} else if (structure.equalsIgnoreCase("set")) {
			return "Set<"+clazz+">";
		} else if (structure.equalsIgnoreCase("map")) {
			return "Map<"+key+", "+clazz+">";
		}
		return null;
	}

	protected String getModule(String structure, String clazz) {
		if (clazz.equals("String") || clazz.equals("Boolean") || clazz.equals("Integer") || clazz.equals("Long") || clazz.equals("Short") || clazz.equals("Date") || clazz.equals("Time"))
			clazz = "Input";
		if (structure.equalsIgnoreCase("item")) {
			return clazz;
		} else {
			if (structure.equalsIgnoreCase("list")) {
				return clazz+"List";
			} else if (structure.equalsIgnoreCase("set")) {
				return clazz+"Select";
			}
		}
		return null;
	}

}
