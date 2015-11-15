package org.aries.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.util.Validator;


@SessionScoped
@Named("inputManager")
public class InputManager extends AbstractPanelManager implements Serializable {

	private Map<String, InvocationValues> valuesMap = new HashMap<String, InvocationValues>();

	private String instanceName;
	
	private Object instanceValue;
	
	
	public InputManager() {
	}

//	public List<String> getValueNames() {
//		return getInvocationValues().getParameterNames();
//	}

	public InvocationValue getInstance() {
		return getInvocationValues().getInvocationValue(instanceName);
	}
	
//	public Map<String, String> getParameterTypes() {
//		return getInvocationValues().getParameterTypes();
//	}
//
//	public Map<String, Object> getParameterValues() {
//		return getInvocationValues().getParameterValues();
//	}

	public InvocationValues getInvocationValues() {
		return getInvocationValues(targetService);
	}
	
	public InvocationValues getInvocationValues(String targetService) {
		InvocationValues invocationValues = valuesMap.get(targetService);
		if (invocationValues != null)
			return invocationValues;
		invocationValues = new InvocationValues();
		valuesMap.put(targetService, invocationValues);
		return invocationValues;
	}
	
	public void setTargetInstance(String instanceName) {
		this.instanceName = instanceName;
	}
	
	public void setTargetValue(Object instanceValue) {
		InvocationValue inputValue = getInstance();
		inputValue.setValue(instanceValue);
	}

	public void setTargetContext(String eventPrefix) {
		setActionEvent(eventPrefix+"Entered");
		setCancelEvent(eventPrefix+"Cancelled");
	}
	
	public void setTargetService(String targetService) {
		this.targetService = targetService;
		String instanceId = targetService + "." + instanceName;
		this.instanceValue = BeanContext.getFromSession(instanceId);
		setTargetValue(instanceValue);
		//setTargetContext(instanceId);
		setModule(targetService);
		setActionEvent(null);
		setCancelEvent(null);
		setRenderList("");
	}
	
	
	public String refresh() {
		return super.refresh();
	}

	public String submit() {
		setModule("Input");
		return super.submit();
	}
	
	public boolean validate() {
		Validator validator = Validator.getValidator();
		InvocationValues invocationValues = getInvocationValues(targetService);
		InvocationValue parameter = invocationValues.getParameter(instanceName);
		String type = parameter.getType();
		Object value = parameter.getValue();
		//validator.notNull(value, instanceId+" value not found.");
		if (value != null) {
			if (type.equals("String"))
				validator.validateString(value);
			else if (type.equals("Integer"))
				validator.validateInteger(value);
			else if (type.equals("Long"))
				validator.validateLong(value);
			else if (type.equals("Date"))
				validator.validateDate(value);
		}
		
		display.addErrors(validator.getMessages());
		boolean isValid = validator.isValid();
		setValidated(isValid);
		return isValid;
	}

	public String cancel() {
		return super.cancel();
	}
	
}
