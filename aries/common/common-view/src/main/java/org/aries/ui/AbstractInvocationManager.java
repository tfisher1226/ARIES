package org.aries.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aries.runtime.BeanContext;
import org.aries.transport.TransportType;


public class AbstractInvocationManager extends AbstractPanelManager {

	private Map<String, TransportType> transportTypes = new HashMap<String, TransportType>();

	
	//TODO make mode flag come from somewhere else
	public String getMode(String serviceId) {
		if (serviceId.startsWith("save") || serviceId.startsWith("delete"))
			return "Select";
		return "";
	}
	
//	public TransportType getTransportType() {
//		return transportType;
//	}
//
//	public void setTransportType(String transportType) {
//		this.transportType = TransportType.valueOf(transportType);
//	}
//	
//	public void setTransportType(TransportType transportType) {
//		this.transportType = transportType;
//	}
	
//	public TransportType getTransportType(String serviceId) {
//		return getInvocationValues(serviceId).getTransportType();
//	}
//
//	public void setTransportType(String serviceId, TransportType transportType) {
//		getInvocationValues(serviceId).setTransportType(transportType);
//	}

	public Map<String, TransportType> getTransportTypes() {
		return transportTypes;
	}
	
	public TransportType getTransportType() {
		return transportTypes.get(targetService);
	}
	
	public boolean hasResult(String serviceId) {
		return getInvocationValues(serviceId).hasResult();
	}

	public InvocationValue getResult(String serviceId) {
		return getInvocationValues(serviceId).getResult();
	}
	
//	public String getResultType(String serviceId) {
//		return getInvocationValues(serviceId).getResultType();
//	}
//
//	public String getResultClass(String serviceId) {
//		return getInvocationValues(serviceId).getResultClass();
//	}
//
//	public String getResultName(String serviceId) {
//		return getInvocationValues(serviceId).getResultName();
//	}

	public InvocationValue getParameter(String serviceId, String name) {
		return getInvocationValues(serviceId).getParameter(name);
	}

	public String getParameterString(String serviceId) {
		return getInvocationValues(serviceId).getParameterString();
	}

	public List<String> getParameterNames(String serviceId) {
		return getInvocationValues(serviceId).getParameterNames();
	}

//	public String getParameterType(String serviceId, String name) {
//		return getInvocationValues(serviceId).getParameterType(name);
//	}
//
//	public String getParameterClass(String serviceId, String name) {
//		return getInvocationValues(serviceId).getParameterClass(name);
//	}

	public Object getParameterValue(String serviceId, String name) {
		InvocationValue parameter = getInvocationValues(serviceId).getParameter(name);
		return parameter.getValue();
	}
	
	protected void setParameterValue(String serviceId, String name, Object value) {
		InvocationValues invocationValues = getInvocationValues(serviceId);
		invocationValues.setParameterValue(name, value);
	}
	
	public InvocationValues getInvocationValues(String serviceId) {
		TransportType transportType = transportTypes.get(serviceId);
		if (transportType == null)
			transportTypes.put(serviceId, TransportType.EJB);
		return getInputManager().getInvocationValues(serviceId);
	}
	
	protected InvocationValues getInvocationValues() {
		return getInputManager().getInvocationValues(targetService);
	}

	protected InputManager getInputManager() {
		return BeanContext.getFromSession("inputManager");
	}

	public void setTargetService(String targetService) {
		this.targetService = targetService;
		setModule(targetService);
		setRenderList("");
	}
	
}
