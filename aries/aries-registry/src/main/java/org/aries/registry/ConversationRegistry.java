package org.aries.registry;

import java.io.Serializable;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aries.cache.AbstractCachePeer;


public class ConversationRegistry extends AbstractCachePeer {

	//private static Log log = LogFactory.getLog(ConversationRegistry.class);

	private static final String CACHE_PEER_NAME = "SharedStateCacheManager";

	private static final String CACHE_NAME = "ConversationStateRegistry";

    private static ConversationRegistry INSTANCE = new ConversationRegistry();

    
    public static void initialize() {
    	//INSTANCE = new ConversationRegistry();
    }
    
    public static ConversationRegistry getInstance() {
        return INSTANCE;
    }
    
	private Object mutex = new Object();
	

	public ConversationRegistry() {
		initialize("registry-ehcache-jgroups-client.xml");
	}

	@Override
	protected String getCacheName() {
		return CACHE_NAME;
	}

	@Override
	protected String getCachePeerName() {
		return CACHE_PEER_NAME;
	}

	
//	public String getServiceURI(String serviceId) {
//		synchronized (mutex) {
//			try {
//				//acquireReadLock();
//				logGetServiceUriStarted(serviceId);
//				Conversation transaction = getElement(serviceId);
//				String serviceUri = transaction.getServiceUri();
//				logGetServiceUriComplete(serviceId);
//				return serviceUri;
//			} catch (Exception e) {
//				logGetServiceUriAborted(serviceId, e);
//				return null;
//			} finally {
//				//releaseReadLock();
//			}
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<ConversationContext> getAllConversationContexts() {
//		List<ConversationContext> transactions = new ArrayList<ConversationContext>();
//		Ehcache cache = getCache(CACHE_NAME);
//		if (cache != null) {
//			List<?> keys = cache.getKeys();
//			Iterator<?> iterator = keys.iterator();
//			while (iterator.hasNext()) {
//				Object key = iterator.next();
//				Element element = cache.get(key);
//				if (element != null) {
//					Serializable value = element.getValue();
//					if (value instanceof Conversation) {
//						transactions.add((Conversation) value);
//					}
//				}
//			}
//		}
//
//		//transientCache.get(serviceStateKey);
//		return transactions;
//	}
	
	
	//TODO take this out - this only for testing
	public Map<String, Serializable> getConversationState() {
		return getConversationState("current");		
	}
	
	public Map<String, Serializable> getConversationState(Object conversationId) {
		synchronized (mutex) {
			try {
				return getElement(conversationId);
			} catch (Exception e) {
				log.error("Error", e);
				return null;
			} finally {
			}
		}
	}
	
	public void registerConversationState(Object conversationId, Map<String, Serializable> conversationState) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				logRegisterConversationStarted(conversationId);
				Ehcache cache = getCache(CACHE_NAME);
				Element element = new Element(conversationId, conversationState);
				cache.put(element);
				
				//TODO take this out - this only for testing
				element = new Element("current", conversationState);
				cache.put(element);
				
				logRegisterConversationComplete(conversationState);
			} catch (Exception e) {
				logRegisterConversationAborted(conversationState, e);
			} finally {
				//releaseWriteLock();
			}
		}
	}

	public void removeConversationState(Object conversationId) {
		synchronized (mutex) {
			try {
				removeElementByKey(conversationId);
			} catch (Exception e) {
				log.error("Error", e);
			} finally {
			}
		}
	}
	
	protected void logGetServiceUriStarted(String serviceId) {
		//EventLog.getInstance().trace("GetLinkStates Started: "+serviceId);
		log.info("GetServiceUri started: "+serviceId);
	}

	protected void logGetServiceUriComplete(String serviceId) {
		//EventLog.getInstance().trace("GetLinkStates Complete: "+serviceId);
		log.info("GetServiceUri complete: "+serviceId);
	}

	protected void logGetServiceUriAborted(String serviceId, Exception e) {
		//EventLog.getInstance().trace("GetLinkStates Aborted: "+serviceId+", "+e.getMessage());
		log.error("GetServiceUri aborted: "+serviceId+", "+e.getMessage());
	}

	protected void logRegisterConversationStarted(Object conversationId) {
		//EventLog.getInstance().trace("RefreshLinkState Started: "+state.getProcessId());
		log.info("RegisterConversation started: "+conversationId);
	}

	protected void logRegisterConversationComplete(Object conversationId) {
		//EventLog.getInstance().trace("RefreshLinkState Complete: "+state.getProcessId());
		log.info("RegisterConversation complete: "+conversationId);
	}

	protected void logRegisterConversationAborted(Object conversationId, Exception e) {
		//EventLog.getInstance().trace("RefreshLinkState Aborted: "+state.getProcessId()+", "+e.getMessage());
		log.error("RegisterConversation aborted: "+conversationId+", "+e.getMessage());
	}
	
}
