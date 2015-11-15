package org.aries.tx.service;

import javax.annotation.Resource;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.Assert;
import org.aries.common.AbstractMessage;
import org.aries.common.ActionType;
import org.aries.notification.NotificationDispatcher;
import org.aries.process.TimeoutHandler;
import org.aries.runtime.BeanContext;
import org.aries.tx.ConversationContext;
import org.aries.tx.TransactionParticipantManager;
import org.aries.tx.Transactional;
import org.aries.util.NameUtil;


public abstract class AbstractServiceHandler implements Transactional {

	public abstract String getName();

	public abstract String getDomain();
	

	protected static final long DEFAULT_TIMEOUT = 120000;
	
	protected long timeout = DEFAULT_TIMEOUT;

	//protected String correlationId;

	//protected String transactionId;

//	@Inject
//	protected TransactionRegistryManager transactionRegistryManager;

	protected TransactionParticipantManager transactionParticipantManager;
	
	@Resource
	protected TransactionSynchronizationRegistry transactionSynchronizationRegistry;

	
	public AbstractServiceHandler() {
		//transactionRegistryManager = TransactionRegistryManager.getInstance();
		transactionParticipantManager = TransactionParticipantManager.getInstance();
	}
	
//	public AbstractServiceHandler(String correlationId) {
//		this.correlationId = correlationId;
//	}
//	
//	public String getCorrelationId() {
//		return correlationId;
//	}
//
//	public void setCorrelationId(String correlationId) {
//		this.correlationId = correlationId;
//	}
//	
//	public String getTransactionId() {
//		return transactionId;
//	}
//
//	public void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//	}
	
	protected String getCorrelationId(AbstractMessage message) {
		Assert.notNull(message, "Incoming message is null");
		return message.getCorrelationId();
	}

	protected String getServiceName() {
		String name = getDomain() + "." + NameUtil.uncapName(getName());
		return name;
	}
	
//	protected boolean isGlobalTransactionActive() {
//		return transactionRegistryManager.isGlobalTransactionActive();
//	}
	
//	protected void enrollTransaction(String transactionName) {
//		transactionParticipantManager.enrollTransaction(transactionName, transactionId, this);
//	}

	protected void enrollTransaction(String transactionName, String transactionId) {
		transactionParticipantManager.enrollTransaction(transactionName, transactionId, this);
	}

	protected void enrollTransaction(String transactionName, ConversationContext conversationContext) {
		transactionParticipantManager.enrollTransaction(transactionName, conversationContext.getTransactionId(), this);
	}
	
	protected String getNotificationDispatcherName() {
		String name = getDomain() + ".notificationDispatcher";
		return name;
	}

	protected NotificationDispatcher getNotificationDispatcher() {
		NotificationDispatcher dispatcher = BeanContext.get(getNotificationDispatcherName());
		return dispatcher;
	}
	
//	protected void fireServiceInvokedNotification() {
//		fireServiceInvokedNotification(getServiceName());
//	}
//
//	protected void fireServiceInvokedNotification(Serializable userData) {
//		String serviceName = getServiceName();
//		String sourceName = this.getClass().getName();
//		getNotificationDispatcher().fireServiceInvokedNotification(serviceName, sourceName, userData);
//	}

//	protected void fireServiceAbortedNotification() {
//		fireServiceAbortedNotification(getServiceName());
//	}
//
//	protected void fireServiceAbortedNotification(Throwable exception) {
//		Throwable rootCause = ExceptionUtils.getRootCause(exception);
//		if (rootCause == null)
//			rootCause = exception;
//		fireServiceAbortedNotification((Serializable) rootCause);
//	}
//	
//	protected void fireServiceAbortedNotification(Serializable userData) {
//		String serviceName = getServiceName();
//		String sourceName = this.getClass().getName();
//		getNotificationDispatcher().fireServiceAbortedNotification(serviceName, sourceName, userData);
//	}

//	protected void validateRequiredContext() {
//		Assert.notNull(correlationId, "CorrelationId null");
//		Assert.notEmpty(correlationId, "CorrelationId empty");
//		Assert.notNull(transactionId, "TransactionId null");
//		Assert.notEmpty(transactionId, "TransactionId empty");
//	}
	

	@Override
	public boolean prepare(String transactionId) {
		return false;
	}

	@Override
	public void commit(String transactionId) {
	}

	@Override
	public void rollback(String transactionId) {
	}
	

	public ActionType getAction(AbstractMessage abstractMessage) {
		if (abstractMessage.isCancelRequest())
			return ActionType.CANCEL; 
		if (abstractMessage.isUndoRequest())
			return ActionType.UNDO; 
		return ActionType.PROCESS; 
	}

	protected TimeoutHandler startTimeoutHandler(Runnable runnable) {
		TimeoutHandler timeoutHandler = new TimeoutHandler(getClass().getName(), runnable);
		timeoutHandler.setTimeout(timeout);
		timeoutHandler.startTimer();
		return timeoutHandler;
	}

}
