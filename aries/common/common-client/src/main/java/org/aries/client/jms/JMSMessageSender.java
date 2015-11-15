package org.aries.client.jms;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.message.MessageSender;
import org.aries.service.ServiceProxy;
import org.aries.util.IdGenerator;


public class JMSMessageSender implements MessageSender {

	private static Log log = LogFactory.getLog(ServiceProxy.class);

	private JMSEndpointContext context;
	
	
	public JMSMessageSender(JMSEndpointContext context) {
		this.context = context;
	}
	
	@Override
	public void initialize() {
		try {
			context.initialize();
			context.getProducer().initialize();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void send(Message message) {
		String correlationId = IdGenerator.createId();
		send(message, correlationId);
	}
	
	@Override
	public void send(Message message, String correlationId) {
		try {
			javax.jms.Message jmsMessage = context.toJMSMessage(message);
    		jmsMessage.setStringProperty("CorrelationId", correlationId);
			jmsMessage.setJMSReplyTo(context.getReplyTo());
			context.getProducer().send(jmsMessage);
			
		} catch (JMSException e) {
			log.error("Error", e);
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw new RuntimeException(e);

		} catch (RuntimeException e) {
			log.error("Error", e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw e;

		} catch (Throwable e) {
			log.error(e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			//TODO drill down and get the actual cause
			//TODO throw instead a UserDefined fault 
			throw new RuntimeException(e);
		}
	}
	
}
