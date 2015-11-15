package org.aries.process;

import java.util.HashMap;
import java.util.Map;


public class ActionState {

	private String actionId;

	private String correlationId;
	
	private String transactionId;
	
	private Map<String, Object> elements = new HashMap<String, Object>();

	
	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, Object> getElements() {
		return elements;
	}

	@SuppressWarnings("unchecked")
	public <T> T getElement(String name) {
		return (T) elements.get(name);
	}
	
	public void setElements(Map<String, Object> elements) {
		this.elements = elements;
	}
	
	public void addElement(String name, Object element) {
		this.elements.put(name,  element);
	}
	
	public void addElements(Map<String, Object> elements) {
		this.elements.putAll(elements);
	}
	
}
