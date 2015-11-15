package org.aries.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.bean.Proxy;
import org.aries.bean.ProxyLocator;
import org.aries.common.AbstractMessage;
import org.aries.notification.NotificationDispatcher;
import org.aries.runtime.BeanContext;
import org.aries.util.concurrent.FutureResult;


public abstract class AbstractProcess implements Process {

	public abstract String getDomainId();

	public Log log = LogFactory.getLog(getClass());

	//@Resource
	//protected WebServiceContext webServiceContext;

	//protected AbstractStateManager<? extends ServiceState> stateManager;

	private Map<String, Object> values = new HashMap<String, Object>();

	private FutureResult<Boolean> futureResult = new FutureResult<Boolean>();
	
	
//	@Resource
//	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
//
//	private Map<String, Object> conversationState = new HashMap<String, Object>();


//	public AbstractProcess() {
//		this(UUIDGenerator.generateRandomUUIDString());
//	}
//
//	public AbstractProcess(String correlationId) {
//		setCorrelationId(correlationId);
//	}
	
	public String getName() {
		return getValue("name");
	}

	public void setName(String name) {
		setValue("name", name);
	}

	public String getVersion() {
		return getValue("name");
	}

	public void setVersion(String version) {
		setValue("version", version);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		return (T) values.get(key);
	}

	public void setValue(String key, Object value) {
		values.put(key, value);
	}
	
//	public Object getCorrelationId() {
//		return getCorrelationId("serviceId");
//	}
//
//	public void setCorrelationId(Object correlationId) {
//		setCorrelationId("serviceId", correlationId);
//	}
//	
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
//
//	protected Map<String, String> getReplyToDestinations(String serviceId, String correlationId) {
//		return getConversationStateValue(serviceId, correlationId, "replyToDestinations");
//	}
//
//	protected void setReplyToDestinations(String serviceId, String correlationId, Map<String, String> replyToDestinations) {
//		setConversationStateValue(serviceId, correlationId, "replyToDestinations", replyToDestinations);
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> T getConversationStateValue(String serviceId, String correlationId, String fieldName) {
//		String key = getConversationKey(serviceId, correlationId) + "." + fieldName;
//		return (T) getConversationStateValue(key);
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> T getConversationStateValue(String key) {
//		return (T) conversationState.get(key);
//	}
//
//	public void setConversationStateValue(String serviceId, String correlationId, String fieldName, Object value) {
//		String key = getConversationKey(serviceId, correlationId) + "." + fieldName;
//		setConversationStateValue(key, value);
//	}
//	
//	public void setConversationStateValue(String key, Object value) {
//		conversationState.put(key, value);
//	}
//
//	
//	protected String getConversationKey(String serviceId, String correlationId) {
//		String key = serviceId + "[" + correlationId + "]";
//		return key;
//	}
//
//	protected String getCorrelationIdFromRegistry() {
//		return (String) transactionSynchronizationRegistry.getResource("correlationId");
//	}
//
//	protected String getTransactionIdFromRegistry() {
//		return (String) transactionSynchronizationRegistry.getResource("transactionId");
//	}
//
//	protected String getReplyToDestinationFromRegistry(String serviceId, String correlationId) {
//		String key = getConversationKey(serviceId, correlationId) + ".replyToDestination";
//		return (String) transactionSynchronizationRegistry.getResource(key);
//	}
//
//	protected void setReplyToDestinationInRegistry(String serviceId, String correlationId, String replyToDestination) {
//		String key = getConversationKey(serviceId, correlationId) + ".replyToDestination";
//		transactionSynchronizationRegistry.putResource(key, replyToDestination);
//	}
//
//	
//	protected void initializeContext(String serviceId, AbstractMessage message) {
//		String correlationId = message.getCorrelationId();
//		String transactionId = message.getTransactionId();
//		Map<String, String> replyToDestinations = message.getReplyToDestinations();
//		setCorrelationId(serviceId, correlationId);
//		setTransactionId(serviceId, transactionId);
//		setReplyToDestinations(serviceId, correlationId, replyToDestinations);
//	}
//
////	protected void initializeMessage(AbstractMessage message) {
////		initializeMessage(null, message);
////	}
//	
//	protected void initializeMessage(String serviceId, AbstractMessage message) {
//		String correlationId = getCorrelationId(serviceId);
//		String transactionId = getTransactionId(serviceId);
//		message.setCorrelationId(correlationId);
//		message.setTransactionId(transactionId);
//		Map<String, String> replyToDestinations = getReplyToDestinations(serviceId, correlationId);
////		if (replyToDestinations == null)
////			System.out.println();
//		if (replyToDestinations != null)
//			message.getReplyToDestinations().putAll(replyToDestinations);
//	}
//	
//	protected void initializeMessageXX(Map<String, String> replyToDestinations, AbstractMessage message) {
//		message.getReplyToDestinations().putAll(replyToDestinations);
//		message.setCorrelationId(getCorrelationIdFromRegistry());
//		message.setTransactionId(getTransactionIdFromRegistry());
//	}
	
	
	protected void fireServiceCompleted(String serviceName, String correlationId) {
		fireServiceCompleted(serviceName, correlationId, serviceName);
	}
	
	protected void fireServiceCompleted(String serviceName, String correlationId, Object userData) {
		String sourceName = getClass().getName();
		String dispatcherName = getDomainId() + ".notificationDispatcher";
		NotificationDispatcher dispatcher = BeanContext.get(dispatcherName);
		dispatcher.fireEventNotification(serviceName, correlationId, sourceName, serviceName);
	}
	
	
	protected ActionState createActionState(String actionId, AbstractMessage message) {
		ActionState actionState = new ActionState();
		actionState.setActionId(actionId);
		actionState.setCorrelationId(message.getCorrelationId());
		actionState.setTransactionId(message.getTransactionId());
		return actionState;
	}
	

	protected <T> T getClient(String clientId) {
		T client = BeanContext.get(clientId);
		return client;
	}

	protected <T> T getReplyTo(String replyToId) {
		T replyTo = BeanContext.get(replyToId);
		return replyTo;
	}

	protected <T> Proxy<T> getClientOLD(String clientId) {
		String proxyKey = clientId;
		ProxyLocator proxyLocator = BeanContext.get(getDomainId() + ".proxyLocator");
		Proxy<T> client = proxyLocator.get(proxyKey);
		//client.initialize();
		//if (client == null)
		//	System.out.println();
		//((Client) client).setCorrelationId(getCorrelationId());
		return client;
	}

//	protected Responder getResponder(String proxyKey) {
//		Map<String, Serializable> conversationState = ConversationRegistry.getInstance().getConversationState(getCorrelationId());
//		Assert.notNull(conversationState, "ConversationState not found: "+getCorrelationId());
//		String originatingTransport = (String) conversationState.get("originatingTransport");
//		Responder responder = getResponder(proxyKey, originatingTransport);
//		
//		if (originatingTransport.equals("JMS")) {
//			Destination jmsReplyTo = (Destination) conversationState.get("replyToDestination");
//			JmsClient jmsClient = (JmsClient) responder;
//			jmsClient.setDestination(jmsReplyTo);
//		}
//		
//		return responder;
//	}
//
//	//TODO use transport to return correct proxy
//	protected Responder getResponder(String proxyKey, String transport) {
//		Responder responder = getClient(proxyKey);
//		return responder;
//	}

	
	public TimeoutHandler startTimeoutHandler(Runnable runnable, long timeout) {
		TimeoutHandler timeoutHandler = new TimeoutHandler(getClass().getName(), runnable);
		timeoutHandler.setTimeout(timeout);
		timeoutHandler.startTimer();
		return timeoutHandler;
	}
	

	public boolean waitForTermination() throws Exception {
		return futureResult.get();
	}

	public boolean waitForTermination(long timeout) throws Exception {
		return futureResult.get(timeout);
	}

	public void exit() {
		futureResult.set(true);
	}
	
	public void abort() {
		futureResult.set(false);
	}
	
}
