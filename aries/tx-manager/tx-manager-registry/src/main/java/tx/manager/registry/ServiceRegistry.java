package tx.manager.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aries.cache.AbstractCachePeer;


public class ServiceRegistry extends AbstractCachePeer {

	//private static Log log = LogFactory.getLog(ProcessRegistry.class);

	private static final String CACHE_PEER_NAME = "TransactionCoordinationRegistry";

	private static final String CACHE_NAME = "ServiceRegistry";

    private static final ServiceRegistry INSTANCE = new ServiceRegistry();

    public static ServiceRegistry getInstance() {
        return INSTANCE;
    }
    
	private Object mutex = new Object();
	

	public ServiceRegistry() {
		initialize("transaction-registry-ehcache-jgroups.xml");
	}

	@Override
	protected String getCacheName() {
		return CACHE_NAME;
	}

	@Override
	protected String getCachePeerName() {
		return CACHE_PEER_NAME;
	}

	
	public String getServiceURI(String serviceId) {
		synchronized (mutex) {
			try {
				//acquireReadLock();
				logGetServiceUriStarted(serviceId);
				Map<String, Serializable> serviceRecord = getElement(serviceId);
				String serviceUri = (String) serviceRecord.get("serviceUri");
				logGetServiceUriComplete(serviceId);
				return serviceUri;
			} catch (Exception e) {
				logGetServiceUriAborted(serviceId, e);
				return null;
			} finally {
				//releaseReadLock();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Serializable>> getServiceRecords() {
		List<Map<String, Serializable>> serviceRecords = new ArrayList<Map<String, Serializable>>();
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			List<?> keys = cache.getKeys();
			Iterator<?> iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Element element = cache.get(key);
				if (element != null) {
					Serializable value = element.getValue();
					if (value instanceof Map) {
						serviceRecords.add((Map<String, Serializable>) value);
					}
				}
			}
		}

		//transientCache.get(serviceStateKey);
		return serviceRecords;
	}
	
	//use serviceId for now
	protected String getKey(Serializable serviceId) {
		String key = serviceId.toString();
		return key;
	}
	
	protected String getKey(Serializable bindAddress, Serializable portNumber, Serializable serviceId) {
		//Serializable key = bindAddress+":"+portNumber+"/"+serviceId;
		String key = getKey(serviceId);
		return key;
	}
	
	protected String getKey(Map<String, Serializable> serviceRecord) {
		Serializable bindAddress = serviceRecord.get("bindAddress");
		Serializable portNumber = serviceRecord.get("portNumber");
		Serializable serviceId = serviceRecord.get("serviceId");
		String key = getKey(bindAddress, portNumber, serviceId);
		return key;
	}
	
	public void registerServiceProvider(String bindAddress, Integer portNumber, String serviceId, String serviceUri) {
		Map<String, Serializable> serviceRecord = new HashMap<String, Serializable>(); 
		serviceRecord.put("bindAddress", bindAddress);
		serviceRecord.put("portNumber", portNumber);
		serviceRecord.put("serviceId", serviceId);
		serviceRecord.put("serviceUri", serviceUri);
		registerServiceProvider(serviceRecord);
	}
	
	public void registerServiceProvider(Map<String, Serializable> serviceRecord) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				logRegisterServiceProviderStarted(serviceRecord);
				Ehcache cache = getCache(CACHE_NAME);
				String key = getKey(serviceRecord);
				String serviceURI = getServiceURI(key);
				if (serviceURI != null) {
					logRegisterServiceProviderAborted(serviceRecord, "Already exists");
					return;
				}
				Element element = new Element(key, serviceRecord);
				cache.put(element);
				logRegisterServiceProviderComplete(serviceRecord);
			} catch (Exception e) {
				logRegisterServiceProviderAborted(serviceRecord, e);
			} finally {
				//releaseWriteLock();
			}
		}
	}

	public void removeServiceProvider(String bindAddress, Integer portNumber, String serviceId) {
		synchronized (mutex) {
			try {
				Serializable key = getKey(bindAddress, portNumber, serviceId);
				removeElementByKey(key);
			} catch (Exception e) {
				log.error("Error", e);
			} finally {
			}
		}
	}
	
	protected void logGetServiceUriStarted(String serviceId) {
		//EventLog.getInstance().trace("GetLinkStates Started: "+serviceId);
		log.info("GetServiceUri started: "+this+", "+serviceId);
	}

	protected void logGetServiceUriComplete(String serviceId) {
		//EventLog.getInstance().trace("GetLinkStates Complete: "+serviceId);
		log.info("GetServiceUri complete: "+this+", "+serviceId);
	}

	protected void logGetServiceUriAborted(String serviceId, Exception e) {
		//EventLog.getInstance().trace("GetLinkStates Aborted: "+serviceId+", "+e.getMessage());
		log.error("GetServiceUri aborted: "+this+", "+serviceId+", "+e.getMessage());
	}

	protected void logRegisterServiceProviderStarted(Map<String, Serializable> serviceRecord) {
		//EventLog.getInstance().trace("RefreshLinkState Started: "+state.getProcessId());
		log.info("RegisterServiceProvider started: "+this+", "+serviceRecord.get("serviceUri"));
	}

	protected void logRegisterServiceProviderComplete(Map<String, Serializable> serviceRecord) {
		//EventLog.getInstance().trace("RefreshLinkState Complete: "+state.getProcessId());
		log.info("RegisterServiceProvider complete: "+this+", "+serviceRecord.get("serviceUri"));
	}

	protected void logRegisterServiceProviderAborted(Map<String, Serializable> serviceRecord, Exception e) {
		logRegisterServiceProviderAborted(serviceRecord, e.getMessage());
	}
	
	protected void logRegisterServiceProviderAborted(Map<String, Serializable> serviceRecord, String message) {
		//EventLog.getInstance().trace("RefreshLinkState Aborted: "+state.getProcessId()+", "+e.getMessage());
		log.error("RegisterServiceProvider aborted: "+this+", "+serviceRecord.get("serviceUri")+", "+message);
	}
	
}
