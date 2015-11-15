package org.aries.tx;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.SessionContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.AbstractMessage;
import org.aries.message.util.MessageConstants;
import org.aries.notification.NotificationDispatcher;
import org.aries.process.AbstractContext;
import org.aries.process.ActionState;
import org.aries.runtime.BeanContext;
import org.aries.validate.util.Check;

import common.jmx.MBeanUtil;


public abstract class ConversationContext extends AbstractContext /*implements TransactionContext*/ {

	private TransactionIsolationLevel transactionIsolationLevel;

	protected TransactionRegistryManager transactionRegistryManager;
	
	private Map<String, Map<String, ActionState>> actionStates = new HashMap<String, Map<String, ActionState>>();
	

	public ConversationContext() {
		transactionRegistryManager = TransactionRegistryManager.getInstance();
	}
	
//	@Override
//	public int getStatus() {
//		return 0;
//	}
//
//	@Override
//	public String getState() {
//		return null;
//	}

	protected void registerWithJMX() {
		MBeanUtil.registerMBean(transactionRegistryManager, TransactionRegistryManager.MBEAN_NAME);
	}
	
	protected void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(TransactionRegistryManager.MBEAN_NAME);
	}
	
	public boolean isGlobalTransactionActive() {
		return transactionRegistryManager.isGlobalTransactionActive();
	}
	
//	public void setGlobalTransactionActive(boolean globalTransactionActive) {
//		return transactionRegistryManager.setGlobalTransactionActive(globalTransactionActive);
//	}
	
	

	//TODO
	//@Override
    public boolean isSecure() {
        if (isValid()) {
            //CoordinationContextType coordinationContextType = context;
            //W3CEndpointReference epref = coordinationContextType.getRegistrationService();
            //NativeEndpointReference nativeRef = EndpointHelper.transform(NativeEndpointReference.class, epref);
            //String address = nativeRef.getAddress();
            //return address.startsWith("https");
        }
        return false;
    }

	//@Override
	public TransactionIsolationLevel getTransactionIsolationLevel() {
	    return transactionIsolationLevel;
	}

	public void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
	    this.transactionIsolationLevel = transactionIsolationLevel;
	}
	

	public void validate() {
		//validateCorrelationId(correlationId);
		//validateTransactionId(transactionId);
	}

	public void validateMessage(AbstractMessage message) {
		validateCorrelationId(message.getCorrelationId());
		if (isGlobalTransactionActive())
			validateTransactionId(message.getTransactionId());
	}

	public void validate(String key, Object value) {
		Check.isValid(key, value);
		validate();
	}

	public void validateCorrelationId(String correlationId) {
		Assert.notNull(correlationId, "CorrelationId null");
		Assert.notEmpty(correlationId, "CorrelationId empty");
	}
	
	public void validateTransactionId(String transactionId) {
		Assert.notNull(transactionId, "TransactionId null");
		Assert.notEmpty(transactionId, "TransactionId empty");
	}
	
//	public void validate(SessionContext sessionContext) {
//		validate(sessionContext.getMessageContext());
//	}
//
//	public void validate(WebServiceContext webServiceContext) {
//		validate(webServiceContext.getMessageContext());
//	}
//	
//	public void validate(javax.xml.ws.handler.MessageContext messageContext) {
//		setCorrelationId((String) messageContext.get(MessageConstants.PROPERTY_CORRELATION_ID));
//		setTransactionId((String) messageContext.get(MessageConstants.PROPERTY_TRANSACTION_ID));
//		populate(messageContext);
//	}
//
//	public void validate(javax.xml.rpc.handler.MessageContext messageContext) {
//		setCorrelationId((String) messageContext.getProperty(MessageConstants.PROPERTY_CORRELATION_ID));
//		setTransactionId((String) messageContext.getProperty(MessageConstants.PROPERTY_TRANSACTION_ID));
//		populate(messageContext);
//	}
//	
//	public void validate(TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
//		Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
//		setCorrelationId((String) transactionSynchronizationRegistry.getResource(MessageConstants.PROPERTY_CORRELATION_ID));
//		setTransactionId((String) transactionSynchronizationRegistry.getResource(MessageConstants.PROPERTY_TRANSACTION_ID));
//	}


	public void populate(SessionContext sessionContext) {
		populate(sessionContext.getMessageContext());
	}

	public void populate(javax.xml.ws.handler.MessageContext messageContext) {
		messageContext.put(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
		messageContext.put(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
	}

	public void populate(javax.xml.rpc.handler.MessageContext messageContext) {
		messageContext.setProperty(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
		messageContext.setProperty(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
	}
	
	
	protected void fireServiceEvent(String typeId) {
		fireServiceEvent(typeId, typeId);
	}
	
	protected void fireServiceEvent(String eventId, Object userData) {
		//String eventId = getDomainId() + "." + typeId;
		String sourceName = getClass().getName();
		String dispatcherName = getDomainId() + ".notificationDispatcher";
		NotificationDispatcher dispatcher = BeanContext.get(dispatcherName);
		dispatcher.fireEventNotification(eventId, correlationId, sourceName, eventId);
	}
	
	
	protected void fireServiceCompleted(String eventId) {
		fireServiceCompleted(eventId, eventId);
	}
	
	protected void fireServiceCompleted(String eventId, Object userData) {
		//if (!eventId.startsWith(getDomainId()))
		//	eventId = getDomainId() + "." + eventId;
		String sourceName = getClass().getName();
		String dispatcherName = getDomainId() + ".notificationDispatcher";
		//log.info("["+sourceName+"]: Firing notification: "+eventId);
		NotificationDispatcher dispatcher = BeanContext.get(dispatcherName);
		dispatcher.fireEventNotification(eventId, correlationId, sourceName, eventId);
		//log.info("["+sourceName+"]: Fired notification: "+eventId);
	}
	
//	protected void fireServiceAborted() {
//		fireServiceAborted(null);
//	}

	protected void fireServiceAborted(String eventId, Throwable exception) {
		Throwable rootCause = ExceptionUtils.getRootCause(exception);
		if (rootCause == null)
			rootCause = exception;
		fireServiceAborted(eventId, (Serializable) rootCause);
	}

	protected void fireServiceAborted(String eventId, Serializable userData) {
		//if (!eventId.startsWith(getDomainId()))
		//	eventId = getDomainId() + "." + eventId;
		String sourceName = this.getClass().getName();
		String dispatcherName = getDomainId() + ".notificationDispatcher";
		NotificationDispatcher dispatcher = BeanContext.get(dispatcherName);
		dispatcher.fireEventNotification(eventId, correlationId, sourceName, userData);
	}

	
	public void logAction(ActionState action) {
		//String serviceId = getConversationId();
		action.setCorrelationId(correlationId);
		action.setTransactionId(transactionId);
		Map<String, ActionState> map = actionStates.get(correlationId);
		if (map == null) {
			map = new HashMap<String, ActionState>();
			actionStates.put(correlationId, map);
		}
		map.put(action.getActionId(), action);
	}

	public Map<String, ActionState> getActionStates(String correlationId) {
		Map<String, ActionState> actions = new HashMap<String, ActionState>();
		Map<String, ActionState> map = actionStates.get(correlationId);
		if (map != null)
			actions.putAll(map);
		return actions;
	}

	public ActionState getActionState(String correlationId, String actionName) {
		Map<String, ActionState> actionStates = getActionStates(correlationId);
		ActionState actionState = actionStates.get(actionName);
		return actionState;
	}

	
	@Override
	public boolean equals(Object object) {
		if (object instanceof ConversationContext) {
			ConversationContext other = (ConversationContext) object;
			if (!correlationId.equals(other.correlationId))
				return false;
			if (!transactionId.equals(other.transactionId))
				return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "correlationId="+correlationId+", transactionId="+transactionId;
	}

}