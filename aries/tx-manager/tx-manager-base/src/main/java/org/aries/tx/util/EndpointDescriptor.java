package org.aries.tx.util;


public class EndpointDescriptor {

	private String bindAddress = "127.0.0.1";
	
	private int bindPort = 8080;

	private int bindPortSSL = 8443;

    private String namespace;

    private String context;

    private String serviceName;
    
    private String portTypeName;
    
    private String actionName;

    private String description;

    private String packageName;

    private String interfaceClass;

    private String implementationClass;

    private Object serviceInstance;


	public EndpointDescriptor() {
		//nothing
	}

	public EndpointDescriptor(Object serviceInstance) {
		setServiceInstance(serviceInstance);
	}

	public String getBindAddress() {
		return bindAddress;
	}

	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}

	public int getBindPort() {
		return bindPort;
	}

	public void setBindPort(int bindPort) {
		this.bindPort = bindPort;
	}

	public int getBindPortSSL() {
		return bindPortSSL;
	}

	public void setBindPortSSL(int bindPortSSL) {
		this.bindPortSSL = bindPortSSL;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getPortTypeName() {
		return portTypeName;
	}

	public void setPortTypeName(String portTypeName) {
		this.portTypeName = portTypeName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
    
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public String getImplementationClass() {
		return implementationClass;
	}

	public void setImplementationClass(String implementationClass) {
		this.implementationClass = implementationClass;
	}

	public Object getServiceInstance() {
		return serviceInstance;
	}

	public void setServiceInstance(Object serviceInstance) {
		this.serviceInstance = serviceInstance;
	}

}
