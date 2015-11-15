package org.aries.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Link;
import nam.model.Role2;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aries.cache.AbstractCachePeer;


/**
 * Maps that exist here:
 * Map<ServiceName, Set<Transport>> availableServiceTransports
 * Map<ServiceId, List<ServiceState>> availableServiceInstances
 *  
 * @author tfisher
 */
//@BypassInterceptors
public class LinkStateRegistry extends AbstractCachePeer {

	//private static Log log = LogFactory.getLog(ProcessRegistry.class);

	private static String CACHE_PEER_NAME = "SharedStateCacheManager";

	private static String CACHE_NAME = "LinkStateRegistry";

	private Object mutex = new Object();
	

	public LinkStateRegistry() {
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


	protected LinkStateKey getLinkStateKey(LinkState LinkState) {
		return LinkStateUtil.createLinkStateKey(LinkState);
	}

	public static LinkStateKey getLinkStateKey(String name, String type) {
		return LinkStateUtil.createLinkStateKey(name, type);
	}

	
	public LinkState getLinkState(String name, String type) {
		synchronized (mutex) {
			try {
				//acquireReadLock();
				logGetLinkStatesStarted(name);
				LinkStateKey linkStateKey = getLinkStateKey(name, type);
				LinkState linkState = getLinkState(linkStateKey);
				logGetLinkStatesComplete(name);
				return linkState;
			} catch (Exception e) {
				logGetLinkStatesAborted(name, e);
				return null;
			} finally {
				//releaseReadLock();
			}
		}
	}
	
	protected LinkState createLinkState(Link link) {
		LinkState linkState = new LinkState();
		linkState.setLink(link);
		return linkState;
	}

//	protected LinkState createLinkState(LinkStateKey key) {
//		LinkState linkState = new LinkState();
//		linkState.setLink(key.getLink());
//		return linkState;
//	}
	
	public LinkState getLinkState(LinkStateKey key) {
		LinkState state = null;
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			Element element = cache.get(key);
			if (element != null) {
				state = (LinkState) element.getValue();
			}
		}

		//transientCache.get(serviceStateKey);
		return state;
	}

	
	@SuppressWarnings("unchecked")
	public List<LinkState> getAllLinkStates() {
		List<LinkState> LinkStates = new ArrayList<LinkState>();
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			List<?> keys = cache.getKeys();
			Iterator<?> iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				if (key instanceof LinkStateKey) {
					LinkStateKey LinkStateKey = (LinkStateKey) key;
					Element element = cache.get(LinkStateKey);
					if (element != null) {
						List<LinkState> list = (List<LinkState>) element.getValue();
						LinkStates.addAll(list);
					}
				}
			}
		}

		//transientCache.get(serviceStateKey);
		return LinkStates;
	}
	

	public void refreshLinkState(LinkState state) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				logRefreshLinkStateStarted(state);
				Ehcache cache = getCache(CACHE_NAME);
				LinkStateKey LinkStateKey = getLinkStateKey(state);
				Element element = new Element(LinkStateKey, state);
				cache.put(element);
				logRefreshLinkStateComplete(state);
			} catch (Exception e) {
				logRefreshLinkStateAborted(state, e);
			} finally {
				//releaseWriteLock();
			}
		}
	}

	
	public void printLinkStates() {
		List<LinkState> states = getAllLinkStates();
		if (states.size() == 0) {
			//log.info("Available Processes: NONE");
			System.out.println("Available Processes: NONE");
		} else {
			//log.info("Available Processes: ");
			System.out.println("Available Processes: "+states.size());
			Iterator<LinkState> iterator = states.iterator();
			while (iterator.hasNext()) {
				LinkState state = iterator.next();
				log.info("LinkState: "+state.toString());
			}
		}
	}

	
	protected void logGetLinkStatesStarted(String serviceId) {
		//EventLog.getInstance().trace("GetLinkStates Started: "+serviceId);
		log.info("GetLinkStates started: "+serviceId);
	}

	protected void logGetLinkStatesComplete(String serviceId) {
		//EventLog.getInstance().trace("GetLinkStates Complete: "+serviceId);
		log.info("GetLinkStates complete: "+serviceId);
	}

	protected void logGetLinkStatesAborted(String serviceId, Exception e) {
		//EventLog.getInstance().trace("GetLinkStates Aborted: "+serviceId+", "+e.getMessage());
		log.error("GetLinkStates aborted: "+serviceId+", "+e.getMessage());
	}

	protected void logRefreshLinkStateStarted(LinkState state) {
		//EventLog.getInstance().trace("RefreshLinkState Started: "+state.getProcessId());
		log.info("RefreshLinkState started: "+state.getLinkId());
	}

	protected void logRefreshLinkStateComplete(LinkState state) {
		//EventLog.getInstance().trace("RefreshLinkState Complete: "+state.getProcessId());
		log.info("RefreshLinkState complete: "+state.getLinkId());
	}

	protected void logRefreshLinkStateAborted(LinkState state, Exception e) {
		//EventLog.getInstance().trace("RefreshLinkState Aborted: "+state.getProcessId()+", "+e.getMessage());
		log.error("RefreshLinkState aborted: "+state.getLinkId()+", "+e.getMessage());
	}

	

	protected String getLinkKey(String linkName) {
		String linkKey = "Link[name="+linkName+"]";
		return linkKey;
	}
	
	public Link getLink(String linkName) {
		Link link = null;
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			String linkKey = getLinkKey(linkName);
			Element element = cache.get(linkKey);
			if (element != null) {
				link = (Link) element.getValue();
			}
		}
		return link;
	}

	public void putLink(Link link) {
		//UserTransaction transaction = com.arjuna.ats.jta.UserTransaction.userTransaction();
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				//transaction.begin();
				Ehcache cache = getCache(CACHE_NAME);
				String linkKey = getLinkKey(link.getName());
				Element element = new Element(linkKey, link);
				cache.put(element);
				//transaction.commit();
			} catch (Exception e) {
				log.error("Error: link="+link.getName()+", "+e.getMessage());
				try {
					//transaction.rollback();
				} catch (Exception e1) {
					log.error("Error", e1);
				}
			} finally {
				//releaseWriteLock();
			}
		}
	}

	protected String getRoleKey(Link link, String roleName) {
		String roleKey = "Link[type="+link.getType()+",name="+link.getName()+"], Role[name="+roleName+"]";
		return roleKey;
	}
	
	public Role2 getRole(String linkName, String roleName) {
		Link link = getLink(linkName);
		Role2 role = getRole(link, roleName);
		return role;
	}
	
	public Role2 getRole(Link link, String roleName) {
		Role2 role = null;
		Ehcache cache = getCache(CACHE_NAME);
		if (cache != null) {
			String roleKey = getRoleKey(link, roleName);
			Element element = cache.get(roleKey);
			if (element != null) {
				role = (Role2) element.getValue();
			}
		}
		return role;
	}

	public void putRole(Link link, Role2 role) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				Ehcache cache = getCache(CACHE_NAME);
				String roleKey = getRoleKey(link, role.getName());
				Element element = new Element(roleKey, role);
				cache.put(element);
			} catch (Exception e) {
				log.error("Error: role="+role.getName()+", "+e.getMessage());
			} finally {
				//releaseWriteLock();
			}
		}
	}
	
}
