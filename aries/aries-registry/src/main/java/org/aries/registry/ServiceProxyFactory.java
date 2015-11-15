package org.aries.registry;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.client.jaxws.JAXWSServiceProxy;
import org.aries.client.jms.JMSEndpointContext;
import org.aries.client.jms.JMSServiceProxy;
import org.aries.ejb.EJBEndpointContext;
import org.aries.ejb.EJBServiceProxy;
import org.aries.event.EventLog;
import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiDescripter;
import org.aries.jndi.JndiProxy;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.nam.model.old.ResultDescripter;
import org.aries.node.NamespaceContext;
import org.aries.rmi.RMIServiceProxy;
import org.aries.runtime.BeanContext;
import org.aries.service.AbstractServiceProxy;
import org.aries.service.ServiceProxy;
import org.aries.service.registry.ServiceState;
import org.aries.transport.TransportType;
import org.aries.util.ClassUtil;


//@BypassInterceptors
public class ServiceProxyFactory {

	protected Log log = LogFactory.getLog(ServiceProxyFactory.class);

	public EventLog eventLog;


	public ServiceProxyFactory() {
		System.out.println();
	}

	public ServiceProxy createServiceProxy(ServiceState serviceState, OperationDescripter operation, TransportType transport) throws Exception {
		Assert.notNull(transport, "Transport must be specified");
		AbstractServiceProxy serviceProxy = null;

		//TODO need domain name here to access correct namespaceContext
		NamespaceContext namespaceContext = BeanContext.get("org.aries.namespaceContext");
		namespaceContext.initialize(serviceState);

		logCreatingServiceProxy(serviceState, operation, transport);

		switch (transport) {
		case EJB: serviceProxy = createServiceProxyForEJB(serviceState, operation); break;
		case JMS: serviceProxy = createServiceProxyForJMS(serviceState, operation); break;
		case HTTP: serviceProxy = createServiceProxyForJAXWS(serviceState, operation); break;
		case RMI: serviceProxy = createServiceProxyForRMI(serviceState, operation); break;
		default: throw new IllegalArgumentException("Unrecognized transport: "+transport);
		}

		logCreatedServiceProxy(serviceState, operation, transport);

		String userName = null;
		String password = null;
		String systemId = null;
		String correlationId = null;

		//read security credentials from external properties file

		serviceProxy.setUserName(userName);
		serviceProxy.setPassword(password);
		serviceProxy.setSystemId(systemId);
		serviceProxy.setCorrelationId(correlationId);
		return serviceProxy;
	}

	protected AbstractServiceProxy createServiceProxyForEJB(ServiceState serviceState, OperationDescripter operation) {
		try {
			JndiDescripter jndiDescripter = serviceState.getJndiDescripter();
			JndiContext jndiContext = createJndiContext(jndiDescripter);

			String jndiName = serviceState.getJndiName();
			Assert.notNull(jndiName, "JNDI name must be specified");

			EJBEndpointContext endpointContext = new EJBEndpointContext();
			endpointContext.setServiceState(serviceState);
			endpointContext.setOperationDescripter(operation);
			endpointContext.setJndiContext(jndiContext);
			endpointContext.setJndiName(jndiName);

			EJBServiceProxy serviceProxy = new EJBServiceProxy(endpointContext);
			serviceProxy.initialize();
			return serviceProxy;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AbstractServiceProxy createServiceProxyForJMS(ServiceState serviceState, OperationDescripter operation) {
		try {
			JndiDescripter jndiDescripter = serviceState.getJndiDescripter();
			JndiContext jndiContext = createJndiContext(jndiDescripter);
			String connectionFactory = serviceState.getConnectionFactory();
			JmsConnectionAdapter connectionAdapter = createConnectionAdapter(jndiContext, connectionFactory, "clientId");
			JmsSessionAdapter sessionAdapter = createSessionAdapter(connectionAdapter);
			sessionAdapter.setTransferMode(serviceState.getTransferMode());
			sessionAdapter.initialize();
			connectionAdapter.start();

			//String serviceName = serviceState.getName();
			//ServiceDescripter serviceDescripter = applicationProfile.getServiceDescripter(serviceName);
			//Assert.notNull(serviceDescripter, "Service Descripter not found: "+serviceName);
			//serviceDescripter.get

			String destinationName = serviceState.getDestinationName();
			//ChannelDescripter channelDescripter = applicationProfile.getChannelDescripterByName(destinationName);
			String outgoingLinkName = destinationName;

			//String incomingDestination = serviceState.getOutboundQueue();
			Assert.notNull(destinationName, "JMS outgoing-destination must be specified");
			//Assert.notNull(incomingDestination, "JMS outgoing-destination must be specified");
			//ProcessDescripter processDescripter = serviceState.getProcessDescripters().get(0);

			Assert.notNull(sessionAdapter, "JMS Session must be specified");
			//Assert.notNull(incomingChannel, "JMS incoming channel must be specified");
			//Assert.notNull(channelDescripter, "Outgoing channel must be specified");
			//Assert.notNull(processDescripter, "ProcessDescripter must be specified");
			//Assert.notNull(transferMode, "TransferMode must be specified");

			JMSEndpointContext endpointContext = new JMSEndpointContext();
			endpointContext.setServiceState(serviceState);
			endpointContext.setOperationDescripter(operation);
			endpointContext.setOutgoingSessionAdapter(sessionAdapter);
			//serviceContext.setIncomingChannel(incomingChannel.getName());
			//serviceContext.setOutgoingChannel(channelDescripter.getName());
			//serviceContext.setIncomingChannel(outgoingLinkName);
			endpointContext.setOutgoingChannel(outgoingLinkName);

			JMSServiceProxy serviceProxy = new JMSServiceProxy(endpointContext);
			serviceProxy.initialize();
			return serviceProxy;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AbstractServiceProxy createServiceProxyForJAXWS(ServiceState serviceState, OperationDescripter operation) {
		//TODO add assertions in here
		//String context = serviceState.getContext();
		//String host = serviceState.getHost();
		//int port = serviceState.getPort();
		//String name = serviceState.getName();

		String serviceQNameText = serviceState.getServiceQName();
		String portQNameText = serviceState.getPortQName();
		//String serviceQNameCapitalized = NameUtil.capName(serviceState.getServiceQName());
		//String portQNameCapitalized = NameUtil.capName(serviceState.getPortQName());
		QName serviceQName = new QName(serviceState.getTargetNamespace(), serviceQNameText, "unused");
		QName portQName = new QName(serviceState.getTargetNamespace(), portQNameText, "unused");

		try {
			String interfaceName = serviceState.getInterfaceName();
			Class<?> interfaceClass = ClassUtil.loadClass(interfaceName);
			//String serviceLocation = "http://"+host+":"+port+context+"/"+name;
			String serviceURL = serviceState.getServiceURL();

			String resultName = null;
			List<ResultDescripter> resultDescripters = operation.getResultDescripters();
			if (resultDescripters.size() > 0) {
				ResultDescripter resultDescripter = resultDescripters.get(0);
				resultName = resultDescripter.getName();
			}

			//			ProcessDescripter processDescripter = serviceState.getProcessDescripter(operationName);
			//			//Assert.notNull(processDescripter, "Execution definition not found: "+operationName);
			//			if (processDescripter != null) {
			//				methodName = processDescripter.getProcessName();
			//				resultName = processDescripter.getResultName();
			//				if (resultName == null)
			//					resultName = "response";
			//			}

			String methodName = operation.getName();
			AbstractServiceProxy serviceProxy = new JAXWSServiceProxy(serviceURL, serviceQName, portQName, interfaceClass, methodName, resultName);
			return serviceProxy;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AbstractServiceProxy createServiceProxyForRMI(ServiceState serviceState, OperationDescripter operation) {
		String host = serviceState.getHost();
		int port = serviceState.getPort();
		String context = serviceState.getContext();
		String serviceId = serviceState.getServiceId();
		Assert.notNull(host, "Host must be specified for RMI service");
		Assert.isTrue(port > 0, "Port must be specified for RMI service");
		RMIServiceProxy serviceProxy = new RMIServiceProxy(host, port, context, serviceId, operation.getName());
		return serviceProxy;
	}


	public JndiContext createJndiContext(JndiDescripter jndiDescripter) {
		String contextFactory = jndiDescripter.getContextFactory();
		String connectionUrl = jndiDescripter.getConnectionUrl();
		JndiContext jndiContext = createJndiContext(connectionUrl, contextFactory);
		return jndiContext;
	}

	public JndiContext createJndiContext(String connectionUrl, String contextFactory) {
		JndiProxy jndiContext = new JndiProxy();
		jndiContext.setConnectionUrl(connectionUrl);
		jndiContext.setContextFactory(contextFactory);
		return jndiContext;
	}

	public JmsConnectionAdapter createConnectionAdapter(JndiContext jndiContext, String connectionFactory, String clientId) throws Exception {
		JmsConnectionDescripter Descripter = new JmsConnectionDescripter();
		Descripter.setClientId(clientId);
		Descripter.setJndiContext(jndiContext);
		Descripter.setConnectionFactory(connectionFactory);
		JmsConnectionAdapter connectionAdapter = new JmsConnectionAdapter(Descripter);
		return connectionAdapter;
	}

	public JmsSessionAdapter createSessionAdapter(JmsConnectionAdapter connectionAdapter) throws Exception {
		JmsSessionAdapter sessionAdapter = new JmsSessionAdapter();
		sessionAdapter.setConnectionAdapter(connectionAdapter);
		return sessionAdapter;
	}


	protected void logCreatedServiceProxy(ServiceState serviceState, OperationDescripter operation, TransportType transport) {
		//EventLog.getInstance().trace("CreatedServiceProxy: "+serviceState.getServiceId()+", "+operation.getName()+", "+transport);
		log.info("CreatedServiceProxy started: "+serviceState.getServiceId()+", "+operation.getName()+", "+transport);
	}

	protected void logCreatingServiceProxy(ServiceState serviceState, OperationDescripter operation, TransportType transport) {
		//EventLog.getInstance().trace("CreatedServiceProxy: "+serviceState.getServiceId()+", "+operation.getName()+", "+transport);
		log.info("CreatedServiceProxy complete: "+serviceState.getServiceId()+", "+operation.getName()+", "+transport);
	}


}
