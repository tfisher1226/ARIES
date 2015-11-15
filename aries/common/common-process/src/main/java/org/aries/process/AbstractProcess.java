package org.aries.process;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.bean.ProxyLocator;
import org.aries.client.Client;
import org.aries.runtime.BeanContext;
import org.aries.util.UUIDGenerator;
import org.aries.util.concurrent.FutureResult;


public abstract class AbstractProcess implements Process {

	Log log = LogFactory.getLog(getClass());

	//@Resource
	protected WebServiceContext webServiceContext;

	//protected AbstractStateManager<? extends ServiceState> stateManager;

	private Map<String, Object> values = new HashMap<String, Object>();

	private FutureResult<Boolean> futureResult = new FutureResult<Boolean>();
	

	public AbstractProcess() {
		this(UUIDGenerator.generateRandomUUIDString());
	}

	public AbstractProcess(String correlationId) {
		setCorrelationId(correlationId);
	}

	public String getCorrelationId() {
		return getValue("correlationId");
	}

	public void setCorrelationId(Object correlationId) {
		setValue("correlationId", correlationId);
	}

//	public String getTransactionId() {
//		return getValue("transactionId");
//	}
//
//	public void setTransactionId(String transactionId) {
//		setValue("transactionId", transactionId);
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

	protected <T> T getClient(String clientId) {
		String proxyKey = clientId;
		ProxyLocator proxyLocator = BeanContext.get("org.aries.proxyLocator");
		T client = proxyLocator.get(proxyKey);
		//if (client == null)
		//	System.out.println();
		((Client) client).setCorrelationId(getCorrelationId());
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
