package org.aries.service.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aries.jndi.JndiDescripter;
import org.aries.nam.model.old.NamespaceDescripter;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.nam.model.old.ProcessDescripter;
import org.aries.transport.TransferMode;


@SuppressWarnings("serial")
public class ServiceState implements Serializable {

	private String serviceId;

	private String serviceURL;

	private String context;

	private String domain;

	private String name;

	private String version;

	private String interfaceName;

	private String serviceQName;

	private String portQName;

	private String targetNamespace;

	private String defaultTransport;

	private String desiredTransport;

	private String host;
	
	private int port;


	/*
	 * Listener/channel information
	 */

//	private String inboundQueue;
//
//	private String outboundQueue;
//
//	private String exceptionQueue;

	private String providerName;

	private String jndiName;

	private String destinationName;
	
	private String connectionFactory;

	private JndiDescripter jndiDescripter;

	private TransferMode transferMode;

	/*
	 * Referenced model information
	 */

	private Map<String, NamespaceDescripter> namespaces;

	/*
	 * Hosted operation information
	 */

	private Map<String, OperationDescripter> operations;

	/*
	 * Hosted process information
	 */

	private Map<String, ProcessDescripter> processes;

	/*
	 * State information
	 */
	
	private int requestsInProcess;
	
	private int requestsInQueue;

	
	public ServiceState() {
		namespaces = new HashMap<String, NamespaceDescripter>();
		operations = new HashMap<String, OperationDescripter>();
		processes = new HashMap<String, ProcessDescripter>();
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getDefaultTransport() {
		return defaultTransport;
	}

	public void setDefaultTransport(String defaultTransport) {
		this.defaultTransport = defaultTransport;
	}

	public String getDesiredTransport() {
		return desiredTransport;
	}

	public void setDesiredTransport(String desiredTransport) {
		this.desiredTransport = desiredTransport;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	public List<NamespaceDescripter> getNamespaceDescripters() {
		return new ArrayList<NamespaceDescripter>(namespaces.values());
	}

	public NamespaceDescripter getNamespaceDescripter(String namespaceURI) {
		return namespaces.get(namespaceURI);
	}

	public void addNamespaceDescripter(NamespaceDescripter namespaceDescripter) {
		this.namespaces.put(namespaceDescripter.getUri(), namespaceDescripter);
	}

	public void removeNamespaceDescripter(NamespaceDescripter namespaceDescripter) {
		this.namespaces.remove(namespaceDescripter.getUri());
	}

	public List<OperationDescripter> getOperationDescripters() {
		return new ArrayList<OperationDescripter>(operations.values());
	}

	public OperationDescripter getOperationDescripter(String operationName) {
		return operations.get(operationName);
	}

	public void addOperationDescripter(OperationDescripter operationDescripter) {
		this.operations.put(operationDescripter.getName(), operationDescripter);
	}

	public void removeOperationDescripter(OperationDescripter operationDescripter) {
		this.operations.remove(operationDescripter.getName());
	}

	public List<ProcessDescripter> getProcessDescripters() {
		return new ArrayList<ProcessDescripter>(processes.values());
	}

	public ProcessDescripter getProcessDescripter(String processName) {
		return processes.get(processName);
	}

	public void addProcessDescripter(ProcessDescripter processDescripter) {
		this.processes.put(processDescripter.getProcessName(), processDescripter);
	}

	public void removeProcessDescripter(ProcessDescripter processDescripter) {
		this.processes.remove(processDescripter.getProcessName());
	}

//	public String getInboundQueue() {
//		return inboundQueue;
//	}
//
//	public void setInboundQueue(String inboundQueue) {
//		this.inboundQueue = inboundQueue;
//	}
//
//	public String getOutboundQueue() {
//		return outboundQueue;
//	}
//
//	public void setOutboundQueue(String outboundQueue) {
//		this.outboundQueue = outboundQueue;
//	}
//
//	public String getExceptionQueue() {
//		return exceptionQueue;
//	}
//
//	public void setExceptionQueue(String exceptionQueue) {
//		this.exceptionQueue = exceptionQueue;
//	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(String connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public JndiDescripter getJndiDescripter() {
		return jndiDescripter;
	}

	public void setJndiDescripter(JndiDescripter jndiDescripter) {
		this.jndiDescripter = jndiDescripter;
	}

	public TransferMode getTransferMode() {
		return transferMode;
	}
	
	public void setTransferMode(TransferMode transferMode) {
		this.transferMode = transferMode;
	}
	
	
	
	/*
	 * State information
	 */
	
	public int getRequestsInProcess() {
		return requestsInProcess;
	}

	public void setRequestsInProcess(int requestsInProcess) {
		this.requestsInProcess = requestsInProcess;
	}

	public int getRequestsInQueue() {
		return requestsInQueue;
	}

	public void setRequestsInQueue(int requestsInQueue) {
		this.requestsInQueue = requestsInQueue;
	}

	
    @Override
	public int hashCode() {
		return name.hashCode();
	}

    @Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ServiceState other = (ServiceState) object;
		//if (!context.equals(other.context))
		//	return false;
		if (!version.equals(other.version))
			return false;
		if (!name.equals(other.name))
			return false;
		return true;
	}
    
    @Override
	public String toString() {
    	StringBuffer buf = new StringBuffer();
    	//buf.append("/"+context);
    	buf.append("/"+name);
    	buf.append("/"+version);
    	if (host != null)
        	buf.append("/"+host+":"+port);
		return buf.toString();
	}

}
