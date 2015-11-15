package org.aries.tx;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.aries.jms.client.JmsClient;


public abstract class AbstractJMSServiceSideArquillionTest extends AbstractServiceSideArquillionTest {

	protected String getJNDINameForQueue(String destinationName) {
		return getJNDINameForDestination(destinationName, "queue");
	}

	protected String getJNDINameForTopic(String destinationName) {
		return getJNDINameForDestination(destinationName, "topic");
	}

	protected String getJNDINameForDestination(String destinationName, String typeName) {
		if (!destinationName.startsWith(typeName))
			return typeName + "/" + destinationName;
		return destinationName;
	}
	
	protected JmsClient createClient(String destination) throws Exception {
		JmsClient client = new JmsClient();
		configureClient(client, destination);
		//client.initialize();
		return client;
	}
	
	protected JmsClient createMockServiceListener(String destination, MessageListener messageListener) throws Exception {
		JmsClient client = createClient(destination, messageListener);
		return client;
	}

	protected JmsClient createClient(Destination destination) throws Exception {
		JmsClient client = new JmsClient();
		configureClient(client, destination);
		//client.initialize();
		return client;
	}

	protected JmsClient createClient(Destination destination, MessageListener messageListener) throws Exception {
		JmsClient client = new JmsClient();
		configureClient(client, destination, messageListener);
		//client.initialize();
		return client;
	}
	
	protected JmsClient createClient(String destination, MessageListener messageListener) throws Exception {
		JmsClient client = new JmsClient();
		configureClient(client, destination, messageListener);
		//client.initialize();
		return client;
	}
	
	
	protected void configureClient(JmsClient client, Destination destination) throws Exception {
		configureClient(client);
		client.setDestination(destination);
	}

	protected void configureClient(JmsClient client, Destination destination, MessageListener messageListener) throws Exception {
		configureClient(client, messageListener);
		client.setDestination(destination);
	}
	
	protected void configureClient(JmsClient client, String destination) throws Exception {
		configureClient(client);
		client.setDestinationName(destination);
	}

	protected void configureClient(JmsClient client, String destination, MessageListener messageListener) throws Exception {
		configureClient(client, messageListener);
		client.setDestinationName(destination);
	}
	
	protected void configureClient(JmsClient client, MessageListener messageListener) throws Exception {
		configureClient(client);
		client.setMessageListener(messageListener);
	}
	
	protected void configureClient(JmsClient client) throws Exception {
		client.setUserName(configuration.getUsername());
		client.setPassword(configuration.getPassword());
		client.setInitialContextProperties(configuration.getInitialContextProperties());
		//if (isLocal)
			client.setConnectionFactoryName(configuration.getLocalConnectionFactoryName());
		//else client.setConnectionFactoryName(configuration.getRemoteConnectionFactoryName());
	}

	@SuppressWarnings("unchecked")
	protected <T> T getObjectFromMessage(Message message) throws JMSException {
		if (message instanceof ObjectMessage) {
			ObjectMessage objectMessage = (ObjectMessage) message;
			T object = (T) objectMessage.getObject();
			return object;
		} else {
			T object = (T) message.getObjectProperty("MessageContent");
			return object;
		}
	}
	
}
