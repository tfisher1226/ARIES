package org.aries.launcher.jms;

import nam.model.JmsTransport;
import nam.model.Provider;
import nam.model.Role2;
import nam.model.TransferMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;
import org.aries.launcher.ServiceLauncher;
import org.aries.service.jms.JMSService;
import org.aries.service.jms.JMSServiceImpl;


public class JMSLauncher implements ServiceLauncher {
	
	private static Log log = LogFactory.getLog(JMSLauncher.class);
	
	private String serviceId;

	private String address;

	private Role2 channel;

	private Provider provider;
	
	private JmsConnectionAdapter jmsConnectionAdapter;

	private JmsSessionAdapter jmsSessionAdapter;
	
	private JMSService jmsService;
	
	private JmsTransport jmsTransport;
	

	public JMSLauncher(JmsTransport jmsTransport) {
		this.jmsTransport = jmsTransport;
	}

	@Override
	public String getAddress() {
		return address;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Role2 getChannel() {
		return channel;
	}

	public void setChannel(Role2 channel) {
		this.channel = channel;
	}


	public void launch() {
		validate();
		if (jmsTransport != null)
			launchTransport(jmsTransport);

//		List<JmsTransport> jmsTransports = channel.getJmsTransports();
//		Iterator<JmsTransport> iterator = jmsTransports.iterator();
//		while (iterator.hasNext()) {
//			JmsTransport jmsTransport = iterator.next();
//			launchTransport(jmsTransport);
//		}
	}
	
	public void launchTransport(JmsTransport transport) {
		//this.jmsTransport = transport;
		validate();

    	//create JNDI context
		JndiContext jndiContext = createJndiContext();

		//create JMS descripters
    	JmsConnectionDescripter connectionDescripter = createConnectionDescripter(jndiContext);
    	JmsSessionDescripter sessionDescripter = createSessionDescripter();

    	//create JMS adapters
    	jmsConnectionAdapter = createConnectionAdapter(connectionDescripter);
    	jmsSessionAdapter = createSessionAdapter(sessionDescripter, jmsConnectionAdapter);

    	try {
        	jmsSessionAdapter.initialize();
			jmsConnectionAdapter.start();

			String destinationName = transport.getDestination();
			TransferMode transferMode = transport.getTransferMode();
			
			JMSServiceImpl jmsService = new JMSServiceImpl(jmsSessionAdapter, destinationName, serviceId, transferMode.toString());
			jmsService.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//validate inputs
	public void validate() {
		Assert.notNull(serviceId, "Service ID not specified");
		Assert.notNull(provider, "Provider not specified");
		Assert.notNull(provider.getName(), "Provider Name not specified");
		Assert.notNull(provider.getType(), "Provider Type not specified");
		Assert.isTrue(provider.getType().toLowerCase().equals("jndi"), "Provider Type must be JNDI");
		Assert.notNull(provider.getJndiContext().getConnectionUrl(), "Provider ConnectionUrl not specified");
		Assert.notNull(provider.getJndiContext().getContextFactory(), "Provider ContextFactory not specified");
		Assert.notNull(provider.getJndiContext().getUserName(), "Security principal (username) not specified");
		Assert.notNull(provider.getJndiContext().getPassword(), "Security credentials (password) not specified");
		Assert.notNull(channel, "Channel Descripter not specified");
		Assert.notNull(channel.getName(), "Channel Name not specified");
		//Assert.notNull(channel.getProvider(), "Channel ProviderName not specified");
		//Assert.isTrue(channel.getTransport().toLowerCase().equals("jms"), "Channel Type must be JMS");
		//Assert.notNull(channel.getConnectionFactory(), "Channel ConnectionFactory not specified");
	}
	
	protected JndiContext createJndiContext() {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(provider.getJndiContext().getConnectionUrl());
    	jndiContext.setContextFactory(provider.getJndiContext().getContextFactory());
    	jndiContext.setUserName(provider.getJndiContext().getUserName());
    	jndiContext.setPassword(provider.getJndiContext().getPassword());
    	return jndiContext;
    }

	protected JmsConnectionDescripter createConnectionDescripter(JndiContext jndiContext) {
    	JmsConnectionDescripter connectionDescripter = new JmsConnectionDescripter();
    	connectionDescripter.setConnectionFactory(jmsTransport.getConnectionFactory());
    	connectionDescripter.setClientId(channel.getName()+"ClientId");
    	connectionDescripter.setUserName(jndiContext.getUserName());//TODO this may need to come from somewhere else...
    	connectionDescripter.setPassword(jndiContext.getPassword());//TODO this may need to come from somewhere else...
    	connectionDescripter.setJndiContext(jndiContext);
		return connectionDescripter;
	}

	protected JmsSessionDescripter createSessionDescripter() {
    	JmsSessionDescripter sessionDescripter = new JmsSessionDescripter();
		return sessionDescripter;
	}

	
	/*
	 * TODO manage connections/sessions outside of Launcher
	 * ----------------------------------------------------
	 */
	
	protected JmsConnectionAdapter createConnectionAdapter(JmsConnectionDescripter connectionDescripter) {
    	JmsConnectionAdapter connectionAdapter = new JmsConnectionAdapter(connectionDescripter);
		return connectionAdapter;
    }
    
	protected JmsSessionAdapter createSessionAdapter(JmsSessionDescripter sessionDescripter, JmsConnectionAdapter connectionAdapter) {
    	JmsSessionAdapter sessionAdapter = new JmsSessionAdapter(sessionDescripter);
    	sessionAdapter.setConnectionAdapter(connectionAdapter);
		return sessionAdapter;
	}
    
	
	public void shutdown() {
		log.info("Shutting down listener: "+getServiceId());
		shutdownSessions();
		shutdownConnections();
		log.info("Shutdown listener: "+getServiceId());
	}

	void shutdownSessions() {
		try {
			if (jmsSessionAdapter != null)
				jmsSessionAdapter.close();
		} catch (Throwable e) {
			log.error(e);
		}
	}

	void shutdownConnections() {
		try {
			if (jmsConnectionAdapter != null)
				jmsConnectionAdapter.close();
		} catch (Throwable e) {
			log.error(e);
		}
	}
}
