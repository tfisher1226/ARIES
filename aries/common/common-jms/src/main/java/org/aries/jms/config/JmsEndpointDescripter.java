package org.aries.jms.config;

import javax.jms.Destination;

import org.aries.jndi.JndiContext;


public abstract class JmsEndpointDescripter {

	private JmsSessionDescripter sessionDescripter;
	
	private String destinationName;

	private Destination destination;

	private Destination replyto;

	
    public JmsSessionDescripter getSessionDescripter() {
        return sessionDescripter;
    }

    public void setSessionDescripter(JmsSessionDescripter value) {
    	sessionDescripter = value;
    }
    
    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String value) {
        destinationName = value;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination value) {
        destination = value;
    }

    public Destination getReplyto() {
        return replyto;
    }

    public void setReplyto(Destination value) {
        replyto = value;
    }

    public JndiContext getJndiContext() {
        return sessionDescripter != null ? sessionDescripter.getJndiContext() : null;
    }

}
