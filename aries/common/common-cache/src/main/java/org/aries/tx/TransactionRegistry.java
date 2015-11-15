package org.aries.tx;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transaction;

import org.aries.cache.AbstractCachePeer;


public class TransactionRegistry extends AbstractCachePeer implements TransactionProvider {

	//private static Log log = LogFactory.getLog(TransactionRegistry.class);

	private static final String CACHE_PEER_NAME = "TransactionCoordinationRegistry";

	private static final String CACHE_NAME = "TransactionRegistry";

    private static final TransactionRegistry INSTANCE = new TransactionRegistry();

    public static TransactionRegistry getInstance() {
        return INSTANCE;
    }

    
    private boolean useDistributedCache = false;
    
    private Map<Object, Object> internalCache;
    
	private Object mutex = new Object();
	

	public TransactionRegistry() {
		if (useDistributedCache)
			initialize("transaction-registry-ehcache-jgroups.xml");
		else internalCache = new HashMap<Object, Object>();
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
//				Transaction transaction = getElement(serviceId);
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
//	public List<TransactionContext> getAllTransactionContexts() {
//		List<TransactionContext> transactions = new ArrayList<TransactionContext>();
//		Ehcache cache = getCache(CACHE_NAME);
//		if (cache != null) {
//			List<?> keys = cache.getKeys();
//			Iterator<?> iterator = keys.iterator();
//			while (iterator.hasNext()) {
//				Object key = iterator.next();
//				Element element = cache.get(key);
//				if (element != null) {
//					Serializable value = element.getValue();
//					if (value instanceof Transaction) {
//						transactions.add((Transaction) value);
//					}
//				}
//			}
//		}
//
//		//transientCache.get(serviceStateKey);
//		return transactions;
//	}
	
	
	public void registerTransaction(Object transactionId, Transaction transaction) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				logRegisterTransactionStarted(transaction);
				putElement(transactionId, transaction);
				
				//TODO take this out - this only for testing
				//element = new Element("current", transaction);
				//cache.put(element);
				
				logRegisterTransactionComplete(transaction);
			} catch (Exception e) {
				logRegisterTransactionAborted(transaction, e);
			} finally {
				//releaseWriteLock();
			}
		}
	}

	protected void putElement(Object transactionId, Transaction transaction) {
		if (useDistributedCache)
			super.putElement(CACHE_NAME, transactionId, transaction);
		else internalCache.put(transactionId, transaction);
	}

	//TODO take this out - this only for testing
	public Transaction getTransaction() {
		return getTransaction("current");		
	}
	
	public Transaction getTransaction(Object transactionId) {
		synchronized (mutex) {
			try {
				return getElement(transactionId);
				//return getElement(transactionId);
			} catch (Exception e) {
				log.error("Error", e);
				return null;
			} finally {
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> T getElement(Object transactionId) {
		if (useDistributedCache)
			return super.getElement(CACHE_NAME, transactionId);
		return (T) internalCache.get(transactionId);
	}
	
	public void removeTransaction(Object transactionId) {
		synchronized (mutex) {
			try {
				Transaction transaction = removeElementByKey(transactionId);
				if (transaction != null) {
					archiveTransaction(transactionId, transaction);
				}

			} catch (Exception e) {
				log.error("Error", e);
			} finally {
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> T removeElementByKey(Object transactionId) {
		if (useDistributedCache)
			return super.removeElementByKey(transactionId);
		return (T) internalCache.remove(transactionId);
		
	}

	public void archiveTransaction(Object transactionId, Transaction transaction) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				putElement("Archived_"+transactionId, transaction);

			} catch (Exception e) {
				log.error("Error", e);
			} finally {
			}
		}
	}

	public Transaction getArchivedTransaction(Object transactionId) {
		synchronized (mutex) {
			try {
				return getElement("Archived_"+transactionId);
			} catch (Exception e) {
				log.error("Error", e);
				return null;
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

	protected void logRegisterTransactionStarted(Object transactionId) {
		//EventLog.getInstance().trace("RefreshLinkState Started: "+state.getProcessId());
		log.info("RegisterTransaction started: "+transactionId);
	}

	protected void logRegisterTransactionComplete(Object transactionId) {
		//EventLog.getInstance().trace("RefreshLinkState Complete: "+state.getProcessId());
		log.info("RegisterTransaction complete: "+transactionId);
	}

	protected void logRegisterTransactionAborted(Object transactionId, Exception e) {
		//EventLog.getInstance().trace("RefreshLinkState Aborted: "+state.getProcessId()+", "+e.getMessage());
		log.error("RegisterTransaction aborted: "+transactionId+", "+e.getMessage());
	}
	

}
