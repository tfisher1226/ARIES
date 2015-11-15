package org.aries.tx;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.ejb.SessionContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.rpc.handler.MessageContext;

import org.aries.common.AbstractMessage;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.notification.NotificationDispatcher;
import org.aries.process.AbstractProcess;
import org.aries.registry.ProcessLocator;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.util.FieldUtil;
import org.aries.util.NameUtil;

import common.jmx.MBeanUtil;


public abstract class AbstractHandlerUnitTest extends AbstractUnitTest {

	public abstract String getName();

	public abstract String getDomain();
	
	public abstract Transactional getFixture();
	
	//public abstract ConversationContext getMockServiceContext();
	
	//public abstract AbstractProcess getMockServiceProcess();
	
	
	protected String correlationId;
	
	protected String transactionId;
	
	protected String expectedCorrelationId;

	protected String expectedTransactionId;

	protected AbstractMessage inputMessage;

	protected SessionContext mockSessionContext;
	
	protected MessageContext mockMessageContext;
	
	protected ProcessLocator mockProcessLocator;

	protected NotificationDispatcher mockNotificationDispatcher;

	protected TransactionRegistryManager mockTransactionRegistryManager;

	protected TransactionParticipantManager mockTransactionParticipantManager;

	protected TransactionSynchronizationRegistry mockTransactionSynchronizationRegistry;

	
	protected String getServiceName() {
		return getDomain() + "." + NameUtil.uncapName(getName());
	}

	protected AbstractMessage getMessage() {
		return inputMessage;
	}
	
	protected boolean isGlobalTransactionActive() {
		return mockTransactionRegistryManager.isGlobalTransactionActive();
	}

	protected void setGlobalTransactionActive(boolean value) {
		when(mockTransactionRegistryManager.isGlobalTransactionActive()).thenReturn(value);
	}

	public ConversationContext getMockServiceContext() {
		return null;
	}
	
	public AbstractProcess getMockServiceProcess() {
		return null;
	}
	
	public ProcessLocator getMockProcessLocator() {
		return mockProcessLocator;
	}
	
	public NotificationDispatcher getMockNotificationDispatcher() {
		return mockNotificationDispatcher;
	}
	
	protected JAXBSessionCache getJAXBSessionCache() {
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(getDomain());
		return jaxbSessionCache;
	}


	public void setUp() throws Exception {
		expectedCorrelationId = "dummyCorrelationId";
		expectedTransactionId = "dummyTransactionId";
		mockSessionContext = mock(SessionContext.class);
		mockMessageContext = mock(MessageContext.class);
		mockProcessLocator = mock(ProcessLocator.class);
		mockNotificationDispatcher = mock(NotificationDispatcher.class);
		mockTransactionRegistryManager = mock(TransactionRegistryManager.class);
		mockTransactionParticipantManager = mock(TransactionParticipantManager.class);
		mockTransactionSynchronizationRegistry = mock(TransactionSynchronizationRegistry.class);
		if (getMockServiceContext() != null)
			FieldUtil.setFieldValue(getMockServiceContext(), "transactionRegistryManager", mockTransactionRegistryManager);
		BeanContext.set(getDomain()+".notificationDispatcher", mockNotificationDispatcher);
		BeanContext.set(getDomain()+".jaxbSessionCache", new JAXBSessionCache(getDomain()));
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
		mockSessionContext = null;
		mockMessageContext = null;
		mockProcessLocator = null;
		mockNotificationDispatcher = null;
		BeanContext.clear();
		super.tearDown();
	}
	
//	protected void setUp(String correlationId, String transactionId) {
//		when(mockSessionContext.getMessageContext()).thenReturn(mockMessageContext);
//		when(mockMessageContext.getProperty(MessageConstants.PROPERTY_CORRELATION_ID)).thenReturn(correlationId);
//		when(mockMessageContext.getProperty(MessageConstants.PROPERTY_TRANSACTION_ID)).thenReturn(transactionId);
//	}

	protected void initialize(AbstractServiceHandler fixture) {
		//FieldUtil.setFieldValue(fixture, "transactionRegistryManager", mockTransactionRegistryManager);
		FieldUtil.setFieldValue(fixture, "transactionParticipantManager", mockTransactionParticipantManager);
		FieldUtil.setFieldValue(fixture, "transactionSynchronizationRegistry", mockTransactionSynchronizationRegistry);
		when(mockTransactionSynchronizationRegistry.getTransactionKey()).thenReturn("dummyTransactionId");
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
	
	protected void validateEnrollTransaction() {
		validateEnrollTransaction(inputMessage);
	}

	protected void validateEnrollTransaction(AbstractMessage message) {
		verify(mockTransactionParticipantManager).enrollTransaction(getName(), message.getTransactionId(), getFixture());
	}

	protected void validateAfterException(Exception e) {
		validateExceptionType(e);
		verify(mockNotificationDispatcher).fireEventNotification(getServiceName()+".ServiceAborted", expectedCorrelationId, getFixture().getClass().getName(), getServiceName());
	}

	protected void validateAfterExecution() throws Exception {
//		if (isAbortExpected)
//			verify(mockNotificationDispatcher).fireEventNotification(getServiceName()+".ServiceAborted", getFixture().getClass().getName(), new AssertionFailure(expectedExceptionMessage));
//		verify(mockNotificationDispatcher).fireEventNotification(getServiceName()+".ServiceInvoked", getFixture().getClass().getName(), getServiceName());
		if (getMockServiceProcess() != null)
			verifyNoMoreInteractions(getMockServiceProcess());
	}

	protected void validate(Transactional fixture, AbstractMessage message, String transactionName) {
		String transactionId = message.getTransactionId();
		if (isGlobalTransactionActive())
			verify(mockTransactionParticipantManager).enrollTransaction(transactionName, transactionId, fixture);
		//verify(mockNotificationDispatcher).fireServiceInvokedNotification("bookshop.supplier.reserveBooks", fixture, reservationRequestMessage);
		verifyNoMoreInteractions(mockTransactionParticipantManager);
	}

//	protected void validate(String transactionName, String transactionId, Transactional fixture) {
//		if (globalTransaction)
//			verify(mockTransactionParticipantManager).enrollTransaction(transactionName, transactionId, fixture);
//		//verify(mockNotificationDispatcher).fireServiceInvokedNotification("bookshop.supplier.reserveBooks", fixture, reservationRequestMessage);
//		verifyNoMoreInteractions(mockTransactionParticipantManager);
//	}

}
