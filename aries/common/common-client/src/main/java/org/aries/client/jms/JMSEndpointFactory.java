package org.aries.client.jms;

import org.aries.Handler;
import org.aries.jms.JmsConnectionAdapter;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiDescripter;
import org.aries.jndi.JndiProxy;
import org.aries.message.MessageReceiver;
import org.aries.message.MessageSender;
import org.aries.nam.model.old.ApplicationModel;
import org.aries.nam.model.old.ApplicationProfile;
import org.aries.nam.model.old.ChannelDescripter;
import org.aries.nam.model.old.ProviderDescripter;


public class JMSEndpointFactory {

	public MessageSender createSender(String serviceId, String toChannel, String fromChannel) {
		JMSEndpointContext serviceContext = createServiceContext(serviceId, toChannel, fromChannel);
		MessageSender sender = createSender(serviceContext);
		return sender;
	}

	public MessageSender createSender(String serviceId, ChannelDescripter toChannel, ChannelDescripter fromChannel) {
		JMSEndpointContext serviceContext = createServiceContext(serviceId, toChannel.getName(), fromChannel.getName());
		MessageSender sender = createSender(serviceContext);
		return sender;
	}

	public MessageSender createSender(JMSEndpointContext serviceContext) {
		JMSMessageSender sender = new JMSMessageSender(serviceContext);
		sender.initialize();
		return sender;
	}
	
	
	public <T> MessageReceiver<T> createReceiver(String serviceId, String incomingChannel, Handler<T> handler) {
		JMSEndpointContext serviceContext = createServiceContext(serviceId, incomingChannel);
		MessageReceiver<T> receiver = createReceiver(serviceContext, handler);
		return receiver;
	}

	public <T> MessageReceiver<T> createReceiver(String serviceId, ChannelDescripter incomingChannel, Handler<T> handler) {
		JMSEndpointContext serviceContext = createServiceContext(serviceId, incomingChannel.getName());
		MessageReceiver<T> receiver = createReceiver(serviceContext, handler);
		return receiver;
	}

	public <T> MessageReceiver<T> createReceiver(JMSEndpointContext serviceContext, Handler<T> handler) {
		JMSMessageReceiver<T> receiver = new JMSMessageReceiver<T>(serviceContext, handler);
		receiver.initialize();
		return receiver;
	}

	
	public JMSEndpointContext createServiceContext(String serviceId, String incomingChannel) {
		JMSEndpointContext serviceContext = new JMSEndpointContext(serviceId);
		serviceContext.setIncomingSessionAdapter(createSessionAdapter(incomingChannel));
//		serviceContext.setIncomingChannel(getDestinationName(incomingChannel));
		serviceContext.setIncomingChannel(incomingChannel);
		return serviceContext;
	}

	public JMSEndpointContext createServiceContext(String serviceId, String outgoingChannel, String incomingChannel) {
		JMSEndpointContext serviceContext = new JMSEndpointContext(serviceId);
		serviceContext.setOutgoingSessionAdapter(createSessionAdapter(outgoingChannel));
		serviceContext.setIncomingSessionAdapter(createSessionAdapter(incomingChannel));
//		serviceContext.setOutgoingChannel(getDestinationName(outgoingChannel));
//		serviceContext.setIncomingChannel(getDestinationName(incomingChannel));
		serviceContext.setOutgoingChannel(outgoingChannel);
		serviceContext.setIncomingChannel(incomingChannel);
		return serviceContext;
	}
	
//	protected static String getDestinationName(String channelName) {
//		ApplicationProfile applicationProfile = ApplicationModel.getApplicationProfile();
//		ChannelDescripter channelDescripter = applicationProfile.getChannelDescripterByName(channelName);
//		return channelDescripter.getDestinationName();
//	}

	public JmsSessionAdapter createSessionAdapter(String channelName) {
		ApplicationProfile applicationProfile = ApplicationModel.getApplicationProfile();
		ChannelDescripter channelDescripter = applicationProfile.getChannelDescripterByName(channelName);
		ProviderDescripter providerDescripter = applicationProfile.getProviderDescripterByName(channelDescripter.getProviderName());
		JndiDescripter jndiDescripter = createJndiDescripter(providerDescripter);
		JndiContext jndiContext = createJndiContext(jndiDescripter);

		String clientId = createClientId(channelName);
		String connectionFactory = channelDescripter.getConnectionFactory();
		JmsConnectionAdapter connectionAdapter = createConnectionAdapter(jndiContext, connectionFactory, clientId);
		JmsSessionAdapter sessionAdapter = createSessionAdapter(connectionAdapter);
		sessionAdapter.setTransferMode(channelDescripter.getTransferMode());
		return sessionAdapter;
	}

//	private static Map<String, Integer> nameInstanceMap = new HashMap<String, Integer>();
	
	public String createClientId(String channelName) {
		String applicationName = ApplicationModel.getApplicationProfile().toString();
		String nameInstance = applicationName+"/"+channelName;
//		Integer count = nameInstanceMap.get(nameInstance);
//		if (count == null) {
//			count = new Integer(0);
//		} else { 
//			count = new Integer(count+1);
//		}
//		nameInstanceMap.put(nameInstance, count);
		
		long ms = System.currentTimeMillis();
		String clientId = "/"+nameInstance+"/"+ms;
		System.out.println("*** Client ID: "+clientId);
		return clientId;
	}

	public JndiDescripter createJndiDescripter(ProviderDescripter providerDescripter) {
		JndiDescripter jndiDescripter = new JndiDescripter();
		jndiDescripter.setConnectionUrl(providerDescripter.getConnectionUrl());
		jndiDescripter.setContextFactory(providerDescripter.getContextFactory());
		jndiDescripter.setSecurityPrinciple(providerDescripter.getSecurityPrinciple());
		jndiDescripter.setSecurityCredentials(providerDescripter.getSecurityCredentials());
		return jndiDescripter;
	}

	public JndiContext createJndiContext(JndiDescripter jndiDescripter) {
		JndiProxy jndiContext = new JndiProxy();
		jndiContext.setConnectionUrl(jndiDescripter.getConnectionUrl());
		jndiContext.setContextFactory(jndiDescripter.getContextFactory());
		jndiContext.setUserName(jndiDescripter.getSecurityPrinciple());
		jndiContext.setPassword(jndiDescripter.getSecurityCredentials());
		return jndiContext;
	}

	public JmsConnectionAdapter createConnectionAdapter(JndiContext jndiContext, String connectionFactory, String clientId) {
		JmsConnectionDescripter Descripter = new JmsConnectionDescripter();
		Descripter.setJndiContext(jndiContext);
		Descripter.setConnectionFactory(connectionFactory);
		Descripter.setClientId(clientId);
		JmsConnectionAdapter connectionAdapter = new JmsConnectionAdapter(Descripter);
		return connectionAdapter;
	}

	public JmsSessionAdapter createSessionAdapter(JmsConnectionAdapter connectionAdapter) {
		JmsSessionAdapter sessionAdapter = new JmsSessionAdapter();
		sessionAdapter.setConnectionAdapter(connectionAdapter);
		return sessionAdapter;
	}

}
