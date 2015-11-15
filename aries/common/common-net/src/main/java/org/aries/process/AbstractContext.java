package org.aries.process;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.common.AbstractMessage;


public abstract class AbstractContext {

	private static Map<String, AbstractContext> contextStore = new HashMap<String, AbstractContext>();


	@SuppressWarnings("unchecked")
	public static <T extends AbstractContext> T getInstance(Class<T> contextClass, String correlationId) {
		synchronized (contextStore) {
			String key = contextClass.getName()+"["+correlationId+"]";
			T context = (T) contextStore.get(key);
			if (context == null) {
				try {
					context = (T) contextClass.newInstance();
					context.setCorrelationId(correlationId);
					contextStore.put(key, context);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return context;
		}
	}	

	public static <T extends AbstractContext> Object getReplyToDestination(Class<T> contextClass, AbstractMessage message, String replyToId) {
		String correlationId = message.getCorrelationId();
		T context = getInstance(contextClass, correlationId);
		Object replyToDestination = context.getReplyToDestination(replyToId, correlationId);
		if (replyToDestination == null)
			replyToDestination = getReplyToDestinationName(message, replyToId);
		return replyToDestination;
	}

	public static String getReplyToDestinationName(AbstractMessage message, String serviceName) {
		Map<String, String> replyToDestinations = message.getReplyToDestinations();
		if (replyToDestinations == null)
			System.out.println();
		Assert.notNull(replyToDestinations, "ReplyTo map not found");
		String replyToDestination = replyToDestinations.get(serviceName);
		if (replyToDestination == null)
			System.out.println();
		Assert.notNull(replyToDestination, "ReplyTo destination not found: "+serviceName);
		String jndiName = "queue/" + replyToDestination;
		return jndiName;
	}


	public abstract String getDomainId();

	//public abstract String getConversationId();

	protected String correlationId;

	protected String transactionId;
	
	protected String replyToDestinaton;
	
	private Map<String, Object> replyToDestinations;

	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;

	private Map<String, Object> conversationState = new HashMap<String, Object>();
	
	protected Log log = LogFactory.getLog(getClass());
	
	
	public AbstractContext() {
		replyToDestinations = new HashMap<String, Object>();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

//	public Object getCorrelationId() {
//		return getCorrelationId("serviceId");
//}
//
//	public void setCorrelationId(Object correlationId) {
//		setCorrelationId("serviceId", correlationId);
//}

//	public String getCorrelationId(String serviceId) {
//		return getConversationStateValue(serviceId + ".correlationId");
//	}
//
//	public void setCorrelationId(String serviceId, Object correlationId) {
//		setConversationStateValue(serviceId + ".correlationId", correlationId);
//	}
//
//	public String getTransactionId(String serviceId) {
//		return getConversationStateValue(serviceId + ".transactionId");
//	}
//
//	public void setTransactionId(String serviceId, Object correlationId) {
//		setConversationStateValue(serviceId + ".transactionId", correlationId);
//	}

	
	public Object getReplyToDestination(String serviceName, String correlationId) {
		String key = getReplyToDestinationKey(serviceName, correlationId);
		return replyToDestinations.get(key);
	}

	public void addReplyToDestination(String serviceName, String correlationId, Object replyToDestination) {
		String key = getReplyToDestinationKey(serviceName, correlationId);
		replyToDestinations.put(key, replyToDestination);
	}
	
	protected String getReplyToDestinationKey(String serviceName, String correlationId) {
		return serviceName + "[" + correlationId + "]";
	}

	
	protected Map<String, String> getReplyToDestinations(String serviceId, String correlationId) {
		return getConversationStateValue(serviceId, correlationId, "replyToDestinations");
	}

	protected void setReplyToDestinations(String serviceId, String correlationId, Map<String, String> replyToDestinations) {
		setConversationStateValue(serviceId, correlationId, "replyToDestinations", replyToDestinations);
	}

	@SuppressWarnings("unchecked")
	public <T> T getConversationStateValue(String serviceId, String correlationId, String fieldName) {
		String key = getConversationKey(serviceId, correlationId) + "." + fieldName;
		return (T) getConversationStateValue(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getConversationStateValue(String key) {
		return (T) conversationState.get(key);
	}

	public void setConversationStateValue(String serviceId, String correlationId, String fieldName, Object value) {
		String key = getConversationKey(serviceId, correlationId) + "." + fieldName;
		setConversationStateValue(key, value);
	}
	
	public void setConversationStateValue(String key, Object value) {
		conversationState.put(key, value);
	}

	
	protected String getConversationKey(String serviceId, String correlationId) {
		String key = serviceId + "[" + correlationId + "]";
		return key;
	}

	protected String getCorrelationIdFromRegistry() {
		return (String) transactionSynchronizationRegistry.getResource("correlationId");
	}

	protected String getTransactionIdFromRegistry() {
		return (String) transactionSynchronizationRegistry.getResource("transactionId");
	}

	protected String getReplyToDestinationFromRegistry(String serviceId, String correlationId) {
		String key = getConversationKey(serviceId, correlationId) + ".replyToDestination";
		return (String) transactionSynchronizationRegistry.getResource(key);
	}

	protected void setReplyToDestinationInRegistry(String serviceId, String correlationId, String replyToDestination) {
		String key = getConversationKey(serviceId, correlationId) + ".replyToDestination";
		transactionSynchronizationRegistry.putResource(key, replyToDestination);
	}

	
	public void initializeContext(AbstractMessage message) {
		correlationId = message.getCorrelationId();
		transactionId = message.getTransactionId();
		//XXX setCorrelationId(serviceId, correlationId);
		//XXX setTransactionId(serviceId, transactionId);
	}

	public void initializeContext(String serviceId, AbstractMessage message) {
		correlationId = message.getCorrelationId();
		transactionId = message.getTransactionId();
		Map<String, String> replyToDestinations = message.getReplyToDestinations();
		setReplyToDestinations(serviceId, correlationId, replyToDestinations);
	}

//	protected void initializeMessage(AbstractMessage message) {
//		initializeMessage(null, message);
//	}
	
	public void initializeMessage(String serviceId, AbstractMessage message) {
		//XXX String correlationId = getCorrelationId(serviceId);
		//XXX String transactionId = getTransactionId(serviceId);
		message.setCorrelationId(correlationId);
		message.setTransactionId(transactionId);
		initializeReplyToDestinations(serviceId, message);
	}
	
	public void initializeReplyToDestinations(String serviceId, AbstractMessage message) {
		Map<String, String> replyToDestinations = getReplyToDestinations(serviceId, correlationId);
//		if (replyToDestinations == null)
//			System.out.println();
		if (replyToDestinations != null)
			message.getReplyToDestinations().putAll(replyToDestinations);
	}
	
//	public String getCorrelationId() {
//		return correlationId;
//	}
//
//	public void setCorrelationId(String correlationId) {
//		//validateCorrelationId(correlationId);
//		this.correlationId = correlationId;
//	}
//
//	public String getTransactionId() {
//		return transactionId;
//	}
//
//	public void setTransactionId(String transactionId) {
//		//validateTransactionId(transactionId);
//		this.transactionId = transactionId;
//	}
//
//	public String getReplyToDestinaton() {
//		return replyToDestinaton;
//	}
//
//	public void setReplyToDestinaton(String replyToDestinaton) {
//		this.replyToDestinaton = replyToDestinaton;
//	}


	public boolean isValid() {
		return !StringUtils.isEmpty(correlationId) && !StringUtils.isEmpty(transactionId);
	}


}
