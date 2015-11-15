package org.aries.launcher.jms;

import org.aries.Assert;
import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;
import org.aries.nam.model.old.ChannelDescripter;
import org.aries.nam.model.old.ProviderDescripter;
import org.aries.nam.model.old.ServiceDescripter;
import org.aries.service.jms.JMSService;
import org.aries.service.jms.JMSServiceImpl;


public class JMSLauncherOriginal {
	
	private JMSService service;
	
	private ServiceDescripter serviceDescripter;
	
	private ProviderDescripter providerDescripter;
	
	private ChannelDescripter channelDescripter;
	

	public ServiceDescripter getServiceDescripter() {
		return serviceDescripter;
	}

	public void setServiceDescripter(ServiceDescripter serviceDescripter) {
		this.serviceDescripter = serviceDescripter;
	}

	public ProviderDescripter getProviderDescripter() {
		return providerDescripter;
	}

	public void setProviderDescripter(ProviderDescripter providerDescripter) {
		this.providerDescripter = providerDescripter;
	}

	public ChannelDescripter getChannelDescripter() {
		return channelDescripter;
	}

	public void setChannelDescripter(ChannelDescripter channelDescripter) {
		this.channelDescripter = channelDescripter;
	}

	
	public void launch() {
		validate();

    	//create JNDI context
		JndiContext jndiContext = createJndiContext();

		//create JMS descripters
    	JmsConnectionDescripter connectionDescripter = createConnectionDescripter(jndiContext);
    	JmsSessionDescripter sessionDescripter = createSessionDescripter();

    	//create JMS adapters
    	JmsConnectionAdapter connectionAdapter = createConnectionAdapter(connectionDescripter);
    	JmsSessionAdapter sessionAdapter = createSessionAdapter(sessionDescripter, connectionAdapter);

    	try {
        	sessionAdapter.initialize();
			connectionAdapter.start();

			String destinationName = channelDescripter.getDestinationName();
			String transferMode = channelDescripter.getTransferMode();
			
			String serviceId = serviceDescripter.getServiceId();
			JMSServiceImpl service = new JMSServiceImpl(sessionAdapter, destinationName, serviceId, transferMode);

//			ProcessDescripter processDescripter = serviceDescripter.getProcessDescripters().get(0);
//			TypeMap typeMap = TypeMap.INSTANCE;
//			String returnType = processDescripter.getResultDescripter().getType();
//			List<ParameterDescripter> parameterDescripters = processDescripter.getParameterDescripters();
//			Iterator<ParameterDescripter> iterator = parameterDescripters.iterator();
//			Class<?>[] parameterTypes = new Class<?>[parameterDescripters.size()];
//			for (int i=0; iterator.hasNext(); i++) {
//				ParameterDescripter parameterDescripter = iterator.next();
//				String typeName = parameterDescripter.getType();
//				parameterTypes[i] = typeMap.getClassObject(typeName);
//			}
//			service.setReturnType(typeMap.getClassObject(returnType));
//			service.setParameterTypes(parameterTypes);
			
			service.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//validate inputs
	public void validate() {
		Assert.notNull(serviceDescripter, "Service Descripter not specified");
		Assert.notNull(serviceDescripter.getServiceId(), "Service ID not specified");

		Assert.notNull(providerDescripter, "Provider Descripter not specified");
		Assert.notNull(providerDescripter.getName(), "Provider Name not specified");
		Assert.notNull(providerDescripter.getType(), "Provider Type not specified");
		Assert.isTrue(providerDescripter.getType().toLowerCase().equals("jms"), "Provider Type must be JMS");
		Assert.notNull(providerDescripter.getConnectionUrl(), "Provider ConnectionUrl not specified");
		Assert.notNull(providerDescripter.getContextFactory(), "Provider ContextFactory not specified");
		Assert.notNull(providerDescripter.getSecurityPrinciple(), "Security Principle (username) not specified");
		Assert.notNull(providerDescripter.getSecurityCredentials(), "Security Credentials (password) not specified");

		Assert.notNull(channelDescripter, "Channel Descripter not specified");
		Assert.notNull(channelDescripter.getName(), "Channel Name not specified");
		Assert.isTrue(channelDescripter.getType().toLowerCase().equals("jms"), "Channel Type must be JMS");
		Assert.notNull(channelDescripter.getProviderName(), "Channel ProviderName not specified");
		Assert.notNull(channelDescripter.getConnectionFactory(), "Channel ConnectionFactory not specified");
	}
	

	protected JndiContext createJndiContext() {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(providerDescripter.getConnectionUrl());
    	jndiContext.setContextFactory(providerDescripter.getContextFactory());
    	jndiContext.setUserName(providerDescripter.getSecurityPrinciple());
    	jndiContext.setPassword(providerDescripter.getSecurityCredentials());
    	return jndiContext;
    }

	protected JmsConnectionDescripter createConnectionDescripter(JndiContext jndiContext) {
    	JmsConnectionDescripter connectionDescripter = new JmsConnectionDescripter();
    	connectionDescripter.setConnectionFactory(channelDescripter.getConnectionFactory());
    	connectionDescripter.setClientId(channelDescripter.getName()+"ClientId");
    	connectionDescripter.setUserName(jndiContext.getUserName());//TODO this may need to come from somewhere else...
    	connectionDescripter.setPassword(jndiContext.getPassword());//TODO this may need to come from somewhere else...
    	connectionDescripter.setJndiContext(jndiContext);
		return connectionDescripter;
	}

	protected JmsSessionDescripter createSessionDescripter() {
    	JmsSessionDescripter sessionDescripter = new JmsSessionDescripter();
		return sessionDescripter;
	}

	
	protected JmsConnectionAdapter createConnectionAdapter(JmsConnectionDescripter connectionDescripter) {
    	JmsConnectionAdapter connectionAdapter = new JmsConnectionAdapter(connectionDescripter);
		return connectionAdapter;
    }
    
	protected JmsSessionAdapter createSessionAdapter(JmsSessionDescripter sessionDescripter, JmsConnectionAdapter connectionAdapter) {
    	JmsSessionAdapter sessionAdapter = new JmsSessionAdapter(sessionDescripter);
    	sessionAdapter.setConnectionAdapter(connectionAdapter);
		return sessionAdapter;
	}
    
}
