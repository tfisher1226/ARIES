package org.aries.tx;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ejb.SessionContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.aries.common.AbstractMessage;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.notification.NotificationDispatcher;
import org.aries.runtime.BeanContext;
import org.aries.runtime.Initializer;
import org.aries.util.FieldUtil;

import common.jmx.MBeanUtil;


public abstract class AbstractListenerForJAXWSUnitTest extends AbstractUnitTest {

	public abstract String getDomain();
	
	public abstract String getModule();
	
	public abstract String getServiceId();

	//public abstract ConversationContext getMockServiceContext();
	
	
	protected String correlationId;
	
	protected String transactionId;
	
	protected AbstractMessage inputMessage;

	protected Initializer mockInitializer;

	protected SessionContext mockSessionContext;
	
	protected MessageContext mockMessageContext;
	
	protected WebServiceContext mockWebServiceContext;

	protected NotificationDispatcher mockNotificationDispatcher;

	protected TransactionRegistryManager mockTransactionRegistryManager;

	protected TransactionParticipantManager mockTransactionParticipantManager;

	protected TransactionSynchronizationRegistry mockTransactionSynchronizationRegistry;

	protected boolean isExpectedValidationError;
	
	
//	protected String getServiceName() {
//		return getDomain() + "." + NameUtil.uncapName(getName());
//	}

	public AbstractMessage getMessage() {
		return inputMessage;
	}

	protected boolean isGlobalTransactionActive() {
		return mockTransactionRegistryManager.isGlobalTransactionActive();
	}

	protected void setGlobalTransactionActive(boolean value) {
		when(mockTransactionRegistryManager.isGlobalTransactionActive()).thenReturn(value);
	}

	public NotificationDispatcher getMockNotificationDispatcher() {
		return mockNotificationDispatcher;
	}
	
	public ConversationContext getMockServiceContext() {
		return null;
	}
	
	protected JAXBSessionCache getJAXBSessionCache() {
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(getDomain());
		return jaxbSessionCache;
	}

	public void setUp() throws Exception {
		mockInitializer = mock(Initializer.class);
		mockSessionContext = mock(SessionContext.class);
		mockMessageContext = mock(MessageContext.class);
		mockWebServiceContext = mock(WebServiceContext.class);
		mockNotificationDispatcher = mock(NotificationDispatcher.class);
		mockTransactionRegistryManager = mock(TransactionRegistryManager.class);
		mockTransactionParticipantManager = mock(TransactionParticipantManager.class);
		mockTransactionSynchronizationRegistry = mock(TransactionSynchronizationRegistry.class);
		when(mockWebServiceContext.getMessageContext()).thenReturn(mockMessageContext);
		if (getMockServiceContext() != null)
			FieldUtil.setFieldValue(getMockServiceContext(), "transactionRegistryManager", mockTransactionRegistryManager);
		BeanContext.set(getDomain() + ".notificationDispatcher", mockNotificationDispatcher);
		BeanContext.set(getDomain() + ".jaxbSessionCache", new JAXBSessionCache(getDomain()));
		BeanContext.set(getModule() + ".initializer", mockInitializer);
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		MBeanUtil.setMBeanServer(mbeanServer);
		setGlobalTransactionActive(false);
		super.setUp();
	}
	
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		correlationId = null;
		transactionId = null;
		inputMessage = null;
		mockInitializer = null;
		mockSessionContext = null;
		mockMessageContext = null;
		mockWebServiceContext = null;
		mockNotificationDispatcher = null;
		super.tearDown();
	}

	protected void initialize(Object fixture) {
		FieldUtil.setFieldValue(fixture, "webServiceContext", mockWebServiceContext);
		FieldUtil.setFieldValue(fixture, "transactionSynchronizationRegistry", mockTransactionSynchronizationRegistry);
		//when(mockTransactionSynchronizationRegistry.getTransactionKey()).thenReturn("dummyTransactionId");
	}

	protected void setupContext(String correlationId, String transactionId) {
		this.correlationId = correlationId;
		this.transactionId = transactionId;
		if (getMessage() != null) {
			getMessage().setCorrelationId(correlationId);
			getMessage().setTransactionId(transactionId);
		}
		if (getMockServiceContext() != null) {
			getMockServiceContext().setCorrelationId(correlationId);
			getMockServiceContext().setTransactionId(transactionId);
		}
	}

	protected void setupMessage(AbstractMessage message) {
		message.setCorrelationId(correlationId);
		message.setTransactionId(transactionId);
	}
	
	protected void validateAfterExecution() {
	}

}
