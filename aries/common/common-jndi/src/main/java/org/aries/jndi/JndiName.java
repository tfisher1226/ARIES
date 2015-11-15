package org.aries.jndi;

import java.io.Serializable;


public class JndiName {

	private final static String DEFAULT_PREFIX = "java:global";

	private StringBuilder builder;

	private String prefix = DEFAULT_PREFIX;

	private String applicationName = "bookshop2-shipper";

	private String moduleName = "bookshop2-shipper-service-0.0.1-SNAPSHOT";

	private String beanName = "ShipperProcess";
	

	public JndiName() {
		//loading the configuration
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public JndiName withAppLicationName(String applicationName) {
		this.applicationName = applicationName;
		return this;
	}

	public JndiName withModuleName(String moduleName) {
		this.moduleName = moduleName;
		return this;
	}

	public JndiName withBeanName(String beanName) {
		this.beanName = beanName;
		return this;
	}

	public JndiName withInterface(Class<Serializable> classObject) {
		this.beanName = classObject.getName();
		return this;
	}

	private boolean isNotEmpty(String name){
		return (name != null && !name.isEmpty());
	}

	public String toString() {
		return prefix + "/" + applicationName + "/" + moduleName + "/" + beanName;
		//return "java:global/bookshop2-shipper/bookshop2-shipper-service-0.0.1-SNAPSHOT/ShipperProcess";
	}
	
}
