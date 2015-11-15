package org.aries.client.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ExceptionMessageSender {

	private QueueConnection connection;
    
	private QueueSession session;
    
	private Queue queue;
    
   	private String jmsSecurityPrincipal = "gatewayuser";

   	private String jmsSecurityCredential = "gwpassword";	

	
    public static void main(String args[]) throws Exception {
    	ExceptionMessageSender g = new ExceptionMessageSender();
    	g.initialize();
    	g.sendException(); 
    	g.stop();
    }
    
    public void initialize() throws JMSException, NamingException {
		InitialContext iniCtx = new InitialContext();
    	QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");

    	connection = queueConnectionFactory.createQueueConnection();
    	//connection = queueConnectionFactory.createQueueConnection(jmsSecurityPrincipal, jmsSecurityCredential);

    	queue = (Queue) iniCtx.lookup("queue/om.esb.jms.exception.persist.gw");
    	session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	connection.start();
    	System.out.println("Connected");
    }
    
    public void stop() throws JMSException { 
        connection.stop();
        session.close();
        connection.close();
    }
    
    public void sendException() throws JMSException {
    	Exception exception = new Exception("Test exception message");
    	sendException(session, queue, exception);
    }

    public void sendException(Throwable exception) throws JMSException {
    	sendException(session, queue, exception);
    }

    public void sendException(QueueSession session, Queue queue, Throwable exception) throws JMSException {
        QueueSender sender = session.createSender(queue);        
        ObjectMessage message = session.createObjectMessage(exception);
    	try {
            sender.send(message);        
    	} finally {
    		sender.close();
    	}
    }

	public void close() {
		// TODO 
	}

}
