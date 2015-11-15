package org.aries.jms.p2p;

import javax.jms.JMSException;
import javax.jms.Message;


public interface JmsMessageProcessor {

    //subclass to provide implementation 
    public Message process(Message message) throws JMSException;
    
}
