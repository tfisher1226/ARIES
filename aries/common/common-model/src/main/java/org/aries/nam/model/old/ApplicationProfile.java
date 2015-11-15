package org.aries.nam.model.old;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ApplicationProfile {

	private String name;

	private String context;
	
	private String version;
	
	private String state;
	
	private String defaultPackage;
	
	private Map<String, ImportDescripter> imports = new HashMap<String, ImportDescripter>();
	
	private Map<String, ServiceGroupDescripter> serviceGroups = new HashMap<String, ServiceGroupDescripter>();
	
	private Map<String, ChannelGroupDescripter> channelGroups = new HashMap<String, ChannelGroupDescripter>();
	
	private Map<String, ChannelDescripter> listenersByName = new HashMap<String, ChannelDescripter>();
	
	private Map<String, ProviderDescripter> providersByName = new HashMap<String, ProviderDescripter>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDefaultPackage() {
		return defaultPackage;
	}

	public void setDefaultPackage(String defaultPackage) {
		this.defaultPackage = defaultPackage;
	}

	public Map<String, ImportDescripter> getImportDescripters() {
		return imports;
	}

	public ImportDescripter getImportDescripter(String domain, String name) {
		return (ImportDescripter) imports.get(getImportKey(domain, name));
	}

	public void addImportDescripter(ImportDescripter descripter) {
		imports.put(getImportKey(descripter), descripter);
	}

	public String getImportKey(ImportDescripter descripter) {
		return getImportKey(descripter.getDomain(), descripter.getName());
	}

	public String getImportKey(String domain, String name) {
		return (new StringBuilder()).append(domain).append(".").append(name).toString();
	}

	public Collection<ServiceDescripter> getServiceDescriptersAsList() {
		Map<String, ServiceDescripter> map = getServiceDescriptersAsMap();
		return map.values();
	}

	public Map<String, ServiceDescripter> getServiceDescriptersAsMap() {
		Map<String, ServiceDescripter> map = new HashMap<String, ServiceDescripter>();
		Collection<String> groups = serviceGroups.keySet();
		Iterator<String> iterator = groups.iterator(); 
		while (iterator.hasNext()) {
			String groupName = (String) iterator.next();
			ServiceGroupDescripter servicesDescripter = (ServiceGroupDescripter) serviceGroups.get(groupName);
			map.putAll(servicesDescripter.getServiceDescripters());
		}
		return map;
	}

	public Map<String, ServiceGroupDescripter> getServiceGroupDescripters() {
		return serviceGroups;
	}

	public ServiceDescripter getServiceDescripter(String serviceId) {
		return (ServiceDescripter)getServiceDescriptersAsMap().get(serviceId);
	}

	public ServiceGroupDescripter getServiceGroupDescripter(String groupName) {
		return (ServiceGroupDescripter)serviceGroups.get(groupName);
	}

	public void addServiceGroupDescripter(ServiceGroupDescripter descripter) {
		serviceGroups.put(descripter.getGroupName(), descripter);
	}

	public Map<String, ChannelGroupDescripter> getChannelGroupDescripters() {
		return channelGroups;
	}

	public ChannelGroupDescripter getChannelGroupDescripter(String domain) {
		return (ChannelGroupDescripter) channelGroups.get(domain);
	}

	public void addChannelGroupDescripter(ChannelGroupDescripter descripter) {
		channelGroups.put(descripter.getDomain(), descripter);
		Collection<ChannelDescripter> values = descripter.getChannelDescripters().values();
		Iterator<ChannelDescripter> iterator = values.iterator();
		while (iterator.hasNext()) {
			ChannelDescripter channelDescripter = iterator.next();
			addChannelDescripter(channelDescripter);
		}
	}

	public Map<String, ChannelDescripter> getChannelDescriptersByName() {
		return listenersByName;
	}

	public ChannelDescripter getChannelDescripterByName(String name) {
		return (ChannelDescripter) listenersByName.get(name);
	}

	public void addChannelDescripter(ChannelDescripter descripter) {
		listenersByName.put(descripter.getName(), descripter);
	}

	public Map<String, ProviderDescripter> getProviderDescriptersByName() {
		return providersByName;
	}

	public ProviderDescripter getProviderDescripterByName(String name) {
		return (ProviderDescripter)providersByName.get(name);
	}

	public void addProviderDescripter(ProviderDescripter providerDescripter) {
		providersByName.put(providerDescripter.getName(), providerDescripter);
	}

	public void integrate(ApplicationProfile application) {
		serviceGroups.putAll(application.getServiceGroupDescripters());
		channelGroups.putAll(application.getChannelGroupDescripters());
		listenersByName.putAll(application.getChannelDescriptersByName());
		providersByName.putAll(application.getProviderDescriptersByName());
	}

	public String toString() {
		return (new StringBuilder()).append(name).append("/").append(version).toString();
	}
}
