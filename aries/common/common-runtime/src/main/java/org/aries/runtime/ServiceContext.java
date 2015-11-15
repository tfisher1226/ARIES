package org.aries.runtime;

import java.util.HashMap;
import java.util.Map;

import javax.management.ObjectName;


public class ServiceContext {

	/** The name of the service **/
	private ObjectName objectName;

	private Map<String, Object> values = new HashMap<String, Object>();


	public ObjectName getObjectName() {
		return objectName;
	}

	public void setObjectName(ObjectName objectName) {
		this.objectName = objectName;
	}

	public String getCorrelationId() {
		return getValue("correlationId");
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		T value = (T) values.get(key);
		return value;
	}

	public boolean containsValue(String key) {
		Object value = getValue(key);
		return value != null;
	}

	@SuppressWarnings("unchecked")
	public <T> T removeValue(String key) {
		T value = (T) values.remove(key);
		return value;
	}
	
	public void addValue(String key, Object value) {
		values.put(key, value);
	}
	

//	public String toString() {
//		StringBuffer buf = new StringBuffer();
//		buf.append("ObjectName: ").append(objectName);
//		return buf.toString();
//	}

}
