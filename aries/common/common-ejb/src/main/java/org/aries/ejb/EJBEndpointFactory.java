package org.aries.ejb;

import org.aries.Handler;
import org.aries.message.MessageReceiver;
import org.aries.message.MessageSender;
import org.aries.nam.model.old.ChannelDescripter;


public class EJBEndpointFactory {
	
//	public MessageSender createSender(String serviceId, ChannelDescripter toChannel, ChannelDescripter fromChannel) {
//		EJBEndpointContext context = createServiceContext(serviceId, toChannel);
//		//TODO what should be done with fromChannel at this point?
//		MessageSender sender = createSender(context);
//		return sender;
//	}
	
//	public MessageSender createSender(EJBEndpointContext context) {
//		EJBMessageSender sender = new EJBMessageSender(context);
//		sender.initialize();
//		return sender;
//	}
	
//	//specified Handler not need right now for RMI
//	public <T> MessageReceiver<T> createReceiver(String serviceId, ChannelDescripter fromChannel, Handler<T> handler) {
//		EJBEndpointContext context = createServiceContext(serviceId, fromChannel);
//		MessageReceiver<T> receiver = createReceiver(context, handler);
//		return receiver;
//	}

//	//specified Handler not need right now for RMI
//	public <T> MessageReceiver<T> createReceiver(EJBEndpointContext context, Handler<T> handler) {
//		EJBMessageReceiver<T> receiver = new EJBMessageReceiver<T>(context);
//		receiver.initialize();
//		return receiver;
//	}

	public EJBEndpointContext createServiceContext(String serviceId, ChannelDescripter channel) {
		EJBEndpointContext endpointContext = new EJBEndpointContext();
//		endpointContext.setServiceState(serviceState);
//		endpointContext.setOperationDescripter(operation);
//		endpointContext.setJndiContext(jndiContext);
//		endpointContext.setJndiName(jndiName);
		return endpointContext;
	}

}
