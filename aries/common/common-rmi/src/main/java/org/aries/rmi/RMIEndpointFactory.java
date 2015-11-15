package org.aries.rmi;

import org.aries.Handler;
import org.aries.message.MessageReceiver;
import org.aries.message.MessageSender;
import org.aries.nam.model.old.ChannelDescripter;


public class RMIEndpointFactory {
	
	public MessageSender createSender(String serviceId, ChannelDescripter toChannel, ChannelDescripter fromChannel) {
		RMIEndpointContext context = createServiceContext(serviceId, toChannel);
		//TODO what should be done with fromChannel at this point?
		MessageSender sender = createSender(context);
		return sender;
	}
	
	public MessageSender createSender(RMIEndpointContext context) {
		RMIMessageSender sender = new RMIMessageSender(context);
		sender.initialize();
		return sender;
	}
	
	//specified Handler not need right now for RMI
	public <T> MessageReceiver<T> createReceiver(String serviceId, ChannelDescripter fromChannel, Handler<T> handler) {
		RMIEndpointContext context = createServiceContext(serviceId, fromChannel);
		MessageReceiver<T> receiver = createReceiver(context, handler);
		return receiver;
	}

	//specified Handler not need right now for RMI
	public <T> MessageReceiver<T> createReceiver(RMIEndpointContext context, Handler<T> handler) {
		RMIMessageReceiver<T> receiver = new RMIMessageReceiver<T>(context);
		receiver.initialize();
		return receiver;
	}

	public RMIEndpointContext createServiceContext(String serviceId, ChannelDescripter channel) {
		String host = channel.getHost();
		int port = Integer.parseInt(channel.getPort());
		RMIEndpointContext endpointContext = new RMIEndpointContext(host, port, serviceId);
		return endpointContext;
	}

}
