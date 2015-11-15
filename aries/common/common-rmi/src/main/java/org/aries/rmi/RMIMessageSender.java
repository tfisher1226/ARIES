package org.aries.rmi;

import org.aries.message.Message;
import org.aries.message.MessageSender;


public class RMIMessageSender implements MessageSender {

	private RMIEndpointContext context;
	
	
    public RMIMessageSender(RMIEndpointContext context) {
    	this.context = context;
    }
    
    
	@Override
	public void initialize() {
		context.initialize();
	}

    @Override
	public void send(Message message) {
    	String xml = context.toXML(message);
    	context.getClient().send(xml);
	}

	@Override
	public void send(Message message, String correlationId) {
    	String xml = context.toXML(message);
    	context.getClient().send(xml, correlationId);
	}

}
