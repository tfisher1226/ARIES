package org.aries.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aries.cache.AbstractCachePeer;
import org.aries.process.ProcessKey;
import org.aries.process.ProcessState;
import org.aries.process.ProcessStateUtil;


/**
 * Maps that exist here:
 * Map<ServiceName, Set<Transport>> availableServiceTransports
 * Map<ServiceId, List<ServiceState>> availableServiceInstances
 *  
 * @author tfisher
 */
//@BypassInterceptors
public class ProcessRegistry extends AbstractCachePeer {

	//private static Log log = LogFactory.getLog(ProcessRegistry.class);

	private static String CACHE_PEER_NAME = "SharedStateCacheManager";

	private static String CACHE_NAME = "ProcessStateRegistry";

	private Object mutex = new Object();
	

	public ProcessRegistry() {
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


	protected ProcessKey getProcessKey(ProcessState processState) {
		return ProcessStateUtil.createProcessKey(processState);
	}

	public static ProcessKey getProcessKey(String name, String version, Object correlationId) {
		return ProcessStateUtil.createProcessKey(name, version, correlationId);
	}

	
	public ProcessState getProcessState(String name, String version, Object correlationId) {
		synchronized (mutex) {
			try {
				//acquireReadLock();
				logGetProcessStatesStarted(name);
				ProcessKey processKey = getProcessKey(name, version, correlationId);
				ProcessState processState = getProcessState(processKey);
				logGetProcessStatesComplete(name);
				return processState;
			} catch (Exception e) {
				logGetProcessStatesAborted(name, e);
				return null;
			} finally {
				//releaseReadLock();
			}
		}
	}
	
	protected ProcessState createProcessState(String name, String version) {
		ProcessState processState = new ProcessState();
		processState.setName(name);
		processState.setVersion(version);
		return processState;
	}

	protected ProcessState createProcessState(ProcessKey key) {
		ProcessState processState = new ProcessState();
		processState.setName(key.getName());
		processState.setVersion(key.getVersion());
		processState.setCorrelationId(key.getCorrelationId());
		return processState;
	}
	
	public ProcessState getProcessState(ProcessKey key) {
		ProcessState state = null;
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			Element element = cache.get(key);
			if (element != null) {
				state = (ProcessState) element.getValue();
			} else {
				state = createProcessState(key);
				refreshProcessState(state);
			}
		}

		//transientCache.get(serviceStateKey);
		return state;
	}

	
	@SuppressWarnings("unchecked")
	public List<ProcessState> getAllProcessStates() {
		List<ProcessState> processStates = new ArrayList<ProcessState>();
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			List<?> keys = cache.getKeys();
			Iterator<?> iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				if (key instanceof ProcessKey) {
					ProcessKey processKey = (ProcessKey) key;
					Element element = cache.get(processKey);
					if (element != null) {
						List<ProcessState> list = (List<ProcessState>) element.getValue();
						processStates.addAll(list);
					}
				}
			}
		}

		//transientCache.get(serviceStateKey);
		return processStates;
	}
	

	public void refreshProcessState(ProcessState state) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				logRefreshProcessStateStarted(state);
				Ehcache cache = getCache(CACHE_NAME);
				ProcessKey processKey = getProcessKey(state);
				Element element = new Element(processKey, state);
				cache.put(element);
				logRefreshProcessStateComplete(state);
			} catch (Exception e) {
				logRefreshProcessStateAborted(state, e);
			} finally {
				//releaseWriteLock();
			}
		}
	}

	
	public void printProcessStates() {
		List<ProcessState> states = getAllProcessStates();
		if (states.size() == 0) {
			//log.info("Available Processes: NONE");
			System.out.println("Available Processes: NONE");
		} else {
			//log.info("Available Processes: ");
			System.out.println("Available Processes: "+states.size());
			Iterator<ProcessState> iterator = states.iterator();
			while (iterator.hasNext()) {
				ProcessState state = iterator.next();
				//log.info("Service: "+serviceState.toString());
				System.out.println("Process: "+state.toString());
			}
		}
	}

	
	protected void logGetProcessStatesStarted(String serviceId) {
		//EventLog.getInstance().trace("GetProcessStates Started: "+serviceId);
		log.info("GetProcessStates Started: "+serviceId);
	}

	protected void logGetProcessStatesComplete(String serviceId) {
		//EventLog.getInstance().trace("GetProcessStates Complete: "+serviceId);
		log.info("GetProcessStates Complete: "+serviceId);
	}

	protected void logGetProcessStatesAborted(String serviceId, Exception e) {
		//EventLog.getInstance().trace("GetProcessStates Aborted: "+serviceId+", "+e.getMessage());
		log.info("GetProcessStates Aborted: "+serviceId+", "+e.getMessage());
	}

	protected void logRefreshProcessStateStarted(ProcessState state) {
		//EventLog.getInstance().trace("RefreshProcessState Started: "+state.getProcessId());
		log.info("RefreshProcessState Started: "+state.getCorrelationId());
	}

	protected void logRefreshProcessStateComplete(ProcessState state) {
		//EventLog.getInstance().trace("RefreshProcessState Complete: "+state.getProcessId());
		log.info("RefreshProcessState Complete: "+state.getCorrelationId());
	}

	protected void logRefreshProcessStateAborted(ProcessState state, Exception e) {
		//EventLog.getInstance().trace("RefreshProcessState Aborted: "+state.getProcessId()+", "+e.getMessage());
		log.info("RefreshProcessState Aborted: "+state.getCorrelationId()+", "+e.getMessage());
	}

}
