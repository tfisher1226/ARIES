package org.aries.nam.model.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceDefinition implements ServiceDescripter {

	private static final long serialVersionUID = 1L;
	
	private String serviceId;

	private String version;
	
	private String description;
	
	private String groupName;
	
	private String serviceName;
	
	private String serviceQName;
	
	private String portQName;
	
	private String targetNamespace;
	
	private String className;
	
	private String methodName;
	
	private ApplicationProfile applicationProfile;
	
	private Map<String, ProcessDescripter> processes = new HashMap<String, ProcessDescripter>();
	
	private Map<String, ListenerDescripter> listenersByName = new HashMap<String, ListenerDescripter>();
	
	private Map<String, ListenerDescripter> listenersByType = new HashMap<String, ListenerDescripter>();
	
	private Map<String, PropertyDescripter> properties = new HashMap<String, PropertyDescripter>();

	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceQName() {
		return serviceQName;
	}

	public void setServiceQName(String serviceQName) {
		this.serviceQName = serviceQName;
	}

	public String getPortQName() {
		return portQName;
	}

	public void setPortQName(String portQName) {
		this.portQName = portQName;
	}

	public String getTargetNamespace() {
		return targetNamespace;
	}

	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void initialize(String groupName) {
		if (groupName != null && getGroupName() == null)
			setGroupName(groupName);
		serviceId = getGroupName()+"."+getServiceName();
	}

	public ApplicationProfile getApplicationProfile() {
		return applicationProfile;
	}

	public void setApplicationProfile(ApplicationProfile applicationProfile) {
		this.applicationProfile = applicationProfile;
	}

	public List<ProcessDescripter> getProcessDescripters() {
		List<ProcessDescripter> list = new ArrayList<ProcessDescripter>(processes.values());
		return Collections.unmodifiableList(list);
	}

	public ProcessDescripter getProcessDescripter(String processName) {
		ProcessDescripter processDescripter = (ProcessDescripter)processes.get(processName);
		return processDescripter;
	}

	public void addProcessDescripter(ProcessDescripter processDescripter) {
		if (processDescripter.getProcessName() != null)
			processes.put(processDescripter.getProcessName(), processDescripter);
		else processes.put("process", processDescripter);
	}

	public ListenerDescripter getListenerDescripterForName(String name) {
		ListenerDescripter Descripter = (ListenerDescripter)listenersByName.get(name);
		return Descripter;
	}

	public ListenerDescripter getListenerDescripterForType(ChannelType channelType) {
		return getListenerDescripterForType(channelType.name());
	}

	public ListenerDescripter getListenerDescripterForType(String type) {
		return (ListenerDescripter)listenersByType.get(type);
	}

	public List<ListenerDescripter> getListenerDescripters() {
		List<ListenerDescripter> list = new ArrayList<ListenerDescripter>(listenersByName.values());
		return Collections.unmodifiableList(list);
	}

	public void addListenerDescripter(ListenerDescripter descriptor) {
		listenersByName.put(descriptor.getListenerName(), descriptor);
		listenersByType.put(descriptor.getListenerType(), descriptor);
	}

	public void removeListenerDescripters() {
		listenersByName.clear();
		listenersByType.clear();
	}

	public List<PropertyDescripter> getPropertyDescripters() {
		List<PropertyDescripter> list = new ArrayList<PropertyDescripter>(properties.values());
		return Collections.unmodifiableList(list);
	}

	public void addPropertyDescripter(PropertyDescripter propertyDescripter) {
		properties.put(propertyDescripter.getName(), propertyDescripter);
	}

	public int hashCode() {
		return serviceId.hashCode();
	}

	public boolean equals(Object object) {
		if (getClass().isAssignableFrom(object.getClass())) {
			ServiceDefinition other = (ServiceDefinition)object;
			if (!other.getServiceId().equals(getServiceId()))
				return false;
		}
		return true;
	}

	public String toString() {
		return getServiceId();
	}
}
