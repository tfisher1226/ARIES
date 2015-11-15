package org.aries.tx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.XAConnectionFactory;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.aries.Assert;
import org.aries.bean.ProxyLocator;
import org.aries.common.AbstractMessage;
import org.aries.common.ActionType;
import org.aries.jms.client.JmsClient;
import org.aries.runtime.BeanContext;
import org.aries.transport.TransportType;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;
import org.aries.util.ResourceUtil;
import org.aries.util.concurrent.FutureResult;
import org.aries.util.properties.PropertyManager;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;
import org.jboss.shrinkwrap.descriptor.api.DescriptorExportException;


public abstract class AbstractJMSListenerArquillionTest extends AbstractArquillianTest {

	public abstract String getDomainId();
	
	//public abstract String getModuleId();
	
	public abstract String getServiceId();
	
	public abstract String getTargetArchive();

	public abstract String getTargetDestination();

	
	protected String username;

	protected String password;

	protected String providerUrl;

	protected Properties initialContextProperties;
	
	protected String initialContextFactoryClass;

	protected String connectionFactoryName;
	
	protected ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/JmsXA")
    private XAConnectionFactory xaConnectionFactory;
	
	protected String destinationName;

	protected Destination destination;
	
	/*
	 * Run-time support
	 */
	
	protected PropertyManager propertyManager;
	
	protected ProxyLocator proxyLocator;
	

	public void setUp() throws Exception {
		super.setUp();
		initializePropertyManager();
		initializeProxyLocator();
	}

	public Properties getInitialContextProperties() {
		return initialContextProperties;
	}

	public void setInitialContextProperties(Properties initialContextProperties) {
		this.initialContextProperties = initialContextProperties;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getInitialContextFactoryClass() {
		return initialContextFactoryClass;
	}

	public void setInitialContextFactoryClass(String initialContextFactoryClass) {
		this.initialContextFactoryClass = initialContextFactoryClass;
	}

	public String getConnectionFactoryName() {
		return connectionFactoryName;
	}

	public void setConnectionFactoryName(String connectionFactoryName) {
		this.connectionFactoryName = connectionFactoryName;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	
	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	protected String getReplyToDestination(AbstractMessage message, String serviceName) {
		Map<String, String> replyToDestinations = message.getReplyToDestinations();
		return getReplyToDestination(replyToDestinations, serviceName);
	}

	protected String getReplyToDestination(Map<String, String> replyToDestinations, String serviceName) {
		Assert.notNull(replyToDestinations, "ReplyTo map not found");
		String replyToDestination = replyToDestinations.get(serviceName);
		Assert.notNull(replyToDestination, "ReplyTo destination not found: "+serviceName);
		String jndiName = "jms/queue/" + replyToDestination;
		return jndiName;
	}


	public void sendMessage(Serializable message) throws JMSException {
		sendMessage(message, null);
	}

	
	/*
	 * Runtime support
	 */

	//TODO take out
	public String getModuleId() {
		return "";
	}

	protected void initializePropertyManager() {
		propertyManager = new PropertyManager();
		propertyManager.setPropertyLocation("/properties");
		propertyManager.initialize();
		String propertyManagerKey = getModuleId() + ".propertyManager";
		BeanContext.set(propertyManagerKey, propertyManager);
	}
	
	protected void initializeProxyLocator() {
		proxyLocator = new ProxyLocator();
		String proxyLocatorId = getDomainId() + ".proxyLocator";
		BeanContext.set(proxyLocatorId, proxyLocator);
	}

	protected void registerJMSProxy(JMSProxy jmsProxy, String proxyKey) {
		proxyLocator.add(proxyKey, jmsProxy, TransportType.JMS);
	}

	protected FutureResult<Object> registerForResult() throws Exception {
		if (expectedEvent == null)
			expectedEvent = getServiceId() + "_Request_Done";
		return registerForResult(expectedEvent);
	}
	
//	protected Object waitForEvent(String notificationId) throws Exception {
//		if (!notificationId.startsWith(getServiceId()))
//			notificationId = getServiceId() + "." + notificationId;
//		return super.waitForEvent(notificationId);
//	}
	
	
	/* 
	 * JMX related calls
	 */
	
	public void clearContext(String mbeanName) throws Exception {
		ObjectName objectName = new ObjectName(mbeanName);
		jmxManager.invoke(objectName, "clearContext");
	}

	protected boolean isFiredMessageComplete(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Message_Sent");
	}
	
	protected boolean isFiredMessageAborted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Message_Aborted");
	}
	
	protected boolean isFiredRequestSent(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Request_Sent");
	}
	
//	protected boolean isFiredRequestSent(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Request_Sent");
//	}
	
	protected boolean isFiredRequestReceived(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Request_Received");
	}
	
//	protected boolean isFiredRequestReceived(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Request_Received");
//	}
	
	protected boolean isFiredRequestHandled(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Request_Handled");
	}
	
//	protected boolean isFiredRequestHandled(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Request_Handled");
//	}
	
	protected boolean isFiredRequestDone(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Request_Done");
	}
	
//	protected boolean isFiredRequestDone(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Request_Done");
//	}
	
	protected boolean isFiredRequestCancelled(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Request_Cancel_Done");
		//return super.isNotificationExist(correlationId, typeId + "_Request_Cancelled");
	}
	
//	protected boolean isFiredRequestCancelled(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Request_Cancel_Done");
//		//return super.isNotificationExist(correlationId, typeId + "_Request_Cancelled");
//	}
	
	protected boolean isFiredRequestRolledBack(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Request_Undo_Done");
		//return super.isNotificationExist(correlationId, typeId + "_Request_RolledBack");
	}
	
//	protected boolean isFiredRequestRolledBack(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Request_Undo_Done");
//		//return super.isNotificationExist(correlationId, typeId + "_Request_RolledBack");
//	}
	
	protected boolean isFiredIncomingRequestAborted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Incoming_Request_Aborted");
	}
	
//	protected boolean isFiredIncomingRequestAborted(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Incoming_Request_Aborted");
//	}
	
	protected boolean isFiredOutgoingRequestAborted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Outgoing_Request_Aborted");
	}
	
//	protected boolean isFiredOutgoingRequestAborted(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Outgoing_Request_Aborted");
//	}
	
	protected boolean isFiredResponseSent(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Response_Sent");
	}

//	protected boolean isFiredResponseSent(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Response_Sent");
//	}

	protected boolean isFiredResponseReceived(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Response_Received");
	}

//	protected boolean isFiredResponseReceived(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Response_Received");
//	}

	protected boolean isFiredResponseHandled(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Response_Handled");
	}

//	protected boolean isFiredResponseHandled(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Response_Handled");
//	}

	protected boolean isFiredResponseDone(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Response_Done");
	}

//	protected boolean isFiredResponseDone(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Response_Done");
//	}

	protected boolean isFiredResponseCancelled(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Response_Cancel_Done");
		//return super.isNotificationExist(correlationId, typeId + "_Response_Cancelled");
	}

//	protected boolean isFiredResponseCancelled(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Response_Cancel_Done");
//		//return super.isNotificationExist(correlationId, typeId + "_Response_Cancelled");
//	}

	protected boolean isFiredResponseRolledBack(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Response_Undo_Done");
		//return super.isNotificationExist(correlationId, typeId + "_Response_RolledBack");
	}

//	protected boolean isFiredResponseRolledBack(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Response_Undo_Done");
//		//return super.isNotificationExist(correlationId, typeId + "_Response_RolledBack");
//	}

	protected boolean isFiredResponseReceiptAborted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Incoming_Response_Aborted");
	}
	
//	protected boolean isFiredResponseReceiptAborted(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Incoming_Response_Aborted");
//	}
	
	protected boolean isFiredResponseSendAborted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Outgoing_Response_Aborted");
	}
	
//	protected boolean isFiredResponseSendAborted(String targetId, String eventId) {
//		return super.isNotificationExist(correlationId, getDomainId() + "." + targetId + "_" + eventId + "_Outgoing_Response_Aborted");
//	}
	
	protected boolean isFiredEventPosted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Event_Posted");
	}

	protected boolean isFiredProcessComplete(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Process_Complete");
	}

	protected boolean isFiredProcessAborted(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Process_Aborted");
	}

	protected boolean isFiredErrorSent(String typeId) {
		return super.isNotificationExist(correlationId, typeId + "_Error_Sent");
	}

	
//	protected String getAdjustedEventId(String eventId) {
//	if (!eventId.startsWith(getDomainId()) && !eventId.contains("."))
//		return getDomainId() + "." + eventId;
//	return eventId;
//}


	/* 
	 * JMS related calls
	 */
	
	protected void assertEmptyTargetDestination() throws Exception {
		assertMessageCountForTargetDestination(0L);
	}

	protected void assertMessageCountForTargetDestination(long count) throws Exception {
		assertMessageCountForQueue(getTargetArchive(), getTargetDestination(), count);
	}

	protected void removeMessagesFromTargetDestination() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
	}

	public void sendMessage(Serializable message, String correlationId) throws JMSException {
		Connection connection = null;
		Session session = null;

		try {
			InitialContext initialContext = new InitialContext(initialContextProperties);
			//InitialContext initialContext = new InitialContext();

			if (connectionFactory == null) 
				connectionFactory = (ConnectionFactory) initialContext.lookup(connectionFactoryName);

			if (destination == null)
				destination = (Destination) initialContext.lookup(destinationName);

			if (username == null || password == null)
				connection = connectionFactory.createConnection();
			else connection = connectionFactory.createConnection(username, password);
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//session.
			
			MessageProducer producer = session.createProducer(destination);
			connection.start();

			ObjectMessage objectMessage = session.createObjectMessage(message);
			objectMessage.setStringProperty("CorrelationId", correlationId);
			producer.send(objectMessage);

		} catch (NamingException e) {
			connectionFactory = null;
			destination = null;
			throw ExceptionUtil.rewrap(e);
			
		} catch (JMSException e) {
			connectionFactory = null;
			destination = null;
			throw e;

		} finally {
			// it is important to close session
			if (session != null) 
				session.close();
			try { 
				// Closing a connection automatically returns the connection and
				// its session plus producer to the resource reference pool.
				if (connection != null) 
					connection.close();
			} catch (JMSException e) {
				//ignore
			};
		}
	}
	
	
	protected void removeMessagesFromQueue() throws Exception {
		removeMessagesFromQueue(configuration.getQueueDestinationName());
	}

	protected void removeMessagesFromQueue(String queueName) throws Exception {
		jmsManager.removeMessagesFromQueue(null, getJNDINameForQueue(queueName));
	}

	protected void removeMessagesFromQueue(String deploymentArchive, String queueName) throws Exception {
		jmsManager.removeMessagesFromQueue(deploymentArchive, getJNDINameForQueue(queueName));
	}
	
	protected void removeMessagesFromTopic() throws Exception {
		jmsManager.removeMessagesFromTopic(configuration.getTopicDestinationName());
	}

	protected void removeMessagesFromTopic(String topicName) throws Exception {
		jmsManager.removeMessagesFromTopic(null, getJNDINameForTopic(topicName));
	}

	protected void removeMessagesFromTopic(String deploymentArchive, String topicName) throws Exception {
		jmsManager.removeMessagesFromTopic(deploymentArchive, getJNDINameForTopic(topicName));
	}
	
	protected String getJNDINameForQueue(String destinationName) {
		return getJNDINameForDestination(destinationName, "queue");
	}

	protected String getJNDINameForTopic(String destinationName) {
		return getJNDINameForDestination(destinationName, "topic");
	}

	protected String getJNDINameForDestination(String destinationName, String typeName) {
		if (!destinationName.startsWith("jms/" + typeName))
			return "jms/" + typeName + "/" + destinationName;
		return destinationName;
	}



	protected void assertMessageCountForQueue(long expectedCount) throws Exception {
		assertMessageCountForQueue(configuration.getQueueDestinationName(), expectedCount);
	}

	protected void assertMessageCountForQueue(String queueName, long expectedCount) throws Exception {
		assertMessageCountForQueue(null, queueName, expectedCount);
	}
	
	protected void assertMessageCountForQueue(String deploymentArchive, String queueName, long expectedCount) throws Exception {
		Long messageCount = jmsManager.countMessagesForQueue(deploymentArchive, queueName);
		Assert.equals(messageCount, expectedCount, "Unexpected message count: "+messageCount);
	}

	protected void assertMessageCountForTopic(long expectedCount) throws Exception {
		assertMessageCountForTopic(configuration.getTopicDestinationName(), expectedCount);
	}

	protected void assertMessageCountForTopic(String topicName, long expectedCount) throws Exception {
		assertMessageCountForTopic(null, topicName, expectedCount);
	}

	protected void assertMessageCountForTopic(String deploymentArchive, String topicName, long expectedCount) throws Exception {
		Long messageCount = jmsManager.countMessagesForTopic(deploymentArchive, topicName);
		Assert.equals(messageCount, 0L, "Message count should be 0");
	}

	
	protected Long countMessagesForTargetDestination() throws Exception {
		return jmsManager.countMessagesForQueue(configuration.getQueueDestinationName());
	}

	protected Long countMessagesForTargetDestination(String queueName) throws Exception {
		return jmsManager.countMessagesForQueue(null, queueName);
	}

	protected Long countMessagesForTargetDestination(String deploymentArchive, String queueName) throws Exception {
		return jmsManager.countMessagesForQueue(deploymentArchive, queueName);
	}

	
	protected JmsClient createClient(String destination) throws Exception {
		JmsClient client = new JmsClient();
		configureClient(client, destination);
		//client.initialize();
		return client;
	}
	
//	protected JmsClient createMockServiceListener(String destination) throws Exception {
//		JmsClient client = createClient(destination);
//		return client;
//	}

	protected JmsClient createClient(Destination destination) throws Exception {
		JmsClient client = new JmsClient();
		configureClient(client, destination);
		//client.initialize();
		return client;
	}

//	protected JmsClient createClient(Destination destination, MessageListener messageListener) throws Exception {
//		JmsClient client = new JmsClient();
//		configureClient(client, destination, messageListener);
//		//client.initialize();
//		return client;
//	}
	
//	protected JmsClient createClient(String destination, MessageListener messageListener) throws Exception {
//		JmsClient client = new JmsClient();
//		configureClient(client, destination, messageListener);
//		//client.initialize();
//		return client;
//	}
	
	
	protected void configureClient(JmsClient client, Destination destination) throws Exception {
		configureClient(client);
		client.setDestination(destination);
	}

//	protected void configureClient(JmsClient client, Destination destination, MessageListener messageListener) throws Exception {
//		configureClient(client, messageListener);
//		client.setDestination(destination);
//	}
	
//	protected void configureClient(JmsClient client, String destination) throws Exception {
//		configureClient(client);
//		client.setDestinationName(destination);
//	}

	protected void configureClient(JmsClient client, String destination) throws Exception {
		configureClient(client);
		client.setDestinationName(destination);
	}
	
//	protected void configureClient(AbstractJMSClient client, MessageListener messageListener) throws Exception {
//		configureClient(client);
//	}
	
	protected void configureClient(JmsClient client) throws Exception {
		client.setUserName(configuration.getUsername());
		client.setPassword(configuration.getPassword());
		client.setInitialContextProperties(configuration.getInitialContextProperties());
		//if (isLocal)
		//	client.setConnectionFactoryName(configuration.getLocalConnectionFactoryName());
		client.setConnectionFactoryName(configuration.getRemoteConnectionFactoryName());
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
	
	
	public ActionType getActionFromMessage(AbstractMessage abstractMessage) {
		if (abstractMessage.isCancelRequest())
			return ActionType.CANCEL; 
		if (abstractMessage.isUndoRequest())
			return ActionType.UNDO; 
		return ActionType.PROCESS; 
	}
	
	
	

//	@Deployment(name = "bookshop-jms", order = 1, testable = true)
//	@TargetsContainer("hornetQ01_local")
//	public static Descriptor createDataSource() { 
//		//return Descriptors.create(JMSDescriptor.class, "bookshop-jms").
//		//		queue("public_buyer_order_books_queue", "/queues/public_buyer_order_books_queue");  
//		return new MyQueueDescriptor("bookshop-jms.xml");
//	}
	
	static class MyQueueDescriptor implements Descriptor {

		private String fileName;
		
		
		public MyQueueDescriptor(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public String getDescriptorName() {
			return fileName;
		}

		@Override
		public String exportAsString() throws DescriptorExportException {
			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			this.exportTo(stream);

			final String content;
			try {
				// Make a String out of the bytes
				content = stream.toString("UTF-8");
				return content;
			} catch (final UnsupportedEncodingException e) {
				throw new DescriptorExportException("Inconsistent encoding during export", e);
			}
		}

		@Override
		public void exportTo(OutputStream output) throws DescriptorExportException, IllegalArgumentException {
			try {
				URL resource = ResourceUtil.getResource(fileName);
				File file = new File(resource.getPath());
				String contents = FileUtils.readFileToString(file);
				output.write(contents.getBytes());
			} catch (Exception e) {
				throw new DescriptorExportException("Error", e);
			}
		}
		
	}
	

}
