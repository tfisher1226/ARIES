package org.aries.cache;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.Criteria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.ThreadUtil;


public abstract class AbstractCachePeer {

	protected abstract String getCacheName();

	protected abstract String getCachePeerName();

	
	protected Log log = LogFactory.getLog(getClass());
	
	private Object mutex = new Object();

	private CacheManager cacheManager;

	private URL fileUrl;

	
//	public void initialize() {
//    	initialize("ehcache.xml");
//	}

	public void initialize(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
    	initialize(url);
	}

	public void initialize(URL fileUrl) {
		this.fileUrl = fileUrl;
		synchronized (mutex) {
			cacheManager = getCacheManager();
			if (cacheManager == null) {
				reset(fileUrl);
			}
		}
	}

	/**
	 * Carries out recreation of the {@link CacheManager} based on specified URL. 
	 * @param fileUrl The URL location for the cache configuration file. 
	 * @throws Exception 
	 */
	protected void reset(URL fileUrl) {
		log.info(getCacheName()+" Initializing...");
		cacheManager = CacheManager.newInstance(fileUrl);
		if (cacheManager == null)
			cacheManager = new CacheManager(fileUrl);
		//cacheManager = new CacheManager(fileUrl);
		//We sleep below because it is needed.
		//We are not yet sure why at this time. 
		//-tfisher (4/11/11)
		ThreadUtil.sleep(2000); 
		//System.out.println();
		cacheManager.getName();
		cacheManager.getCacheNames();
		log.info(getCacheName()+" Initialized");
	}

	
	//TODO do we cache the cache instance?
	//TODO is this call expensive at all?
	protected Ehcache getCache(String cacheName) {
		//if (cache == null) {
		//	cache = getCacheManager().getEhcache(cacheName);
		Ehcache cache = getCacheManager().getEhcache(cacheName);
		String[] cacheNames = getCacheManager().getCacheNames();
		initializeCache(cache);
		return cache;
	}
	
	protected void initializeCache(Ehcache cache) {
		//do nothing
	}

	protected CacheManager getCacheManager() {
		synchronized (mutex) {
			if (cacheManager == null) {
				List<CacheManager> cacheManagers = CacheManager.ALL_CACHE_MANAGERS;
				Iterator<CacheManager> iterator = cacheManagers.iterator();
				while (iterator.hasNext()) {
					CacheManager cacheManager = iterator.next();
					if (cacheManager.getName().equals(getCachePeerName())) {
						this.cacheManager = cacheManager;
						break;
					}
				}
			}
			if (cacheManager == null) {
				reset(fileUrl);
			}
			Assert.notNull(cacheManager, "Cache not found: "+getCachePeerName());
			return cacheManager;
		}
	}
	

	protected void acquireReadLock() {
		getCacheLock().readLock().lock();
	}

	protected void releaseReadLock() {
		getCacheLock().readLock().unlock();
	}
	
	protected void acquireWriteLock() {
		getCacheLock().writeLock().lock();
	}

	protected void releaseWriteLock() {
		getCacheLock().writeLock().unlock();
	}
	
	protected ReentrantReadWriteLock getCacheLock() {
		CacheManager cacheManager = getCacheManager();
		Ehcache cache = cacheManager.getEhcache(getCacheName());
		Assert.notNull(cache, "Cache not found: "+getCacheName());
		Element element = cache.get("cacheLock");
		if (element != null) {
			ReentrantReadWriteLock cacheLock = (ReentrantReadWriteLock) element.getValue();
			return cacheLock;
		} else {
			ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
			element = new Element("cacheLock", cacheLock);
			cache.put(element);
			return cacheLock;
		}
	}

	
	protected <T> List<T> getAllElements(String name) {
		String cacheName = getCacheName();
		return getAllElements(cacheName, name);
	}

	protected <T> List<T> getAllElements(String cacheName, String name) {
		Ehcache cache = getCache(cacheName);
		return getAllElements(cache, name);
	}
	
	protected <T> List<T> getAllElements(Ehcache cache, String name) {
		return getCacheValues(cache, name);
	}

	
	protected <T> Set<T> getAllElementsAsSet(String name) {
		String cacheName = getCacheName();
		return getAllElementsAsSet(cacheName, name);
	}

	protected <T> Set<T> getAllElementsAsSet(String cacheName, String name) {
		Ehcache cache = getCache(cacheName);
		return getAllElementsAsSet(cache, name);
	}
	
	protected <T> Set<T> getAllElementsAsSet(Ehcache cache, String name) {
		return getCacheValuesAsSet(cache, name);
	}
	
	
	

	protected <T> List<T> getElements(String name) {
		String cacheName = getCacheName();
		return getElements(cacheName, name);
	}

	protected <T> List<T> getElements(String cacheName, String name) {
		Ehcache cache = getCache(cacheName);
		return getElements(cache, name);
	}
	
	protected <T> List<T> getElements(Ehcache cache, String name) {
		List<?> keys = cache.getKeys();
		List<T> values = getElements(name, keys);
		return values;
	}

	protected <K, T> List<T> getElements(String name, Collection<K> keys) {
		String cacheName = getCacheName();
		return getElements(cacheName, name, keys);
	}
	
	protected <K, T> List<T> getElements(String cacheName, String name, Collection<K> keys) {
		Ehcache cache = getCache(cacheName);
		return getElements(cache, name, keys);
	}
	
	protected <K, T> List<T> getElements(Ehcache cache, String name, Collection<K> keys) {
		List<T> list = new ArrayList<T>();
		if (keys == null)
			return list;
		Iterator<K> iterator = keys.iterator();
		while (iterator.hasNext()) {
			K key = iterator.next();
			T value = getCacheValue(cache, name, key);
			if (value != null)
				list.add(value);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	protected <K, T> List<T> getElementsOLD(Ehcache cache, String name, Collection<K> keys) {
		synchronized (mutex) {
			List<K> keySet = cache.getKeys();
			List<T> list = new ArrayList<T>();
			Iterator<K> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				K key = iterator.next();
				if (keySet.contains(key)) {
					T element = getElement(name, key);
					list.add(element);
				}
			}
			return list;
		}
	}
	
	
	
	protected <T> Set<T> getElementsAsSet(String name) {
		String cacheName = getCacheName();
		return getElementsAsSet(cacheName, name);
	}

	protected <T> Set<T> getElementsAsSet(String cacheName, String name) {
		Ehcache cache = getCache(cacheName);
		return getElementsAsSet(cache, name);
	}
	
	protected <T> Set<T> getElementsAsSet(Ehcache cache, String name) {
		List<?> keys = cache.getKeys();
		return getElementsAsSet(name, keys);
	}

	protected <K, T> Set<T> getElementsAsSet(String name, Collection<K> keys) {
		String cacheName = getCacheName();
		return getElementsAsSet(cacheName, name, keys);
	}
	
	protected <K, T> Set<T> getElementsAsSet(String cacheName, String name, Collection<K> keys) {
		Ehcache cache = getCache(cacheName);
		return getElementsAsSet(cache, name, keys);
	}
	
	protected <K, T> Set<T> getElementsAsSet(Ehcache cache, String name, Collection<K> keys) {
		Set<T> set = new HashSet<T>();
		Iterator<K> iterator = keys.iterator();
		while (iterator.hasNext()) {
			K key = iterator.next();
			T value = getCacheValue(cache, name, key);
			if (value != null)
				set.add(value);
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	protected <K, T> Set<T> getElementsAsSetOLD(Ehcache cache, String name, Collection<K> keys) {
		synchronized (mutex) {
			List<K> keySet = cache.getKeys();
			Set<T> set = new HashSet<T>();
			Iterator<K> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				K key = iterator.next();
				if (keySet.contains(key)) {
					T element = getElement(name, key);
					set.add(element);
				}
			}
			return set;
		}
	}
	
	
	
//	protected <K, T> Map<K, T> getElementsAsMap(String name) {
//		String cacheName = getCacheName();
//		return getElementsAsMap(cacheName, name);
//	}
//
//	protected <K, T> Map<K, T> getElementsAsMap(String cacheName, String name) {
//		Ehcache cache = getCache(cacheName);
//		return getElementsAsMap(cache, name);
//	}
//	
//	protected <K, T> Map<K, T> getElementsAsMap(Ehcache cache, String name) {
//		return getCacheValuesAsMap(cache, name);
//	}
//	
//	@SuppressWarnings("unchecked")
//	protected <K, T> Map<K, T> getElementsAsMapOLD(Ehcache cache, String name) {
//		synchronized (mutex) {
//			List<K> keys = cache.getKeys();
//			List<T> values = getElements(name, keys);
//			Map<K, T> map = new HashMap<K, T>(keys.size());
//			for (int i=0; i < keys.size(); i++) {
//				map.put(keys.get(i), values.get(i));
//			}
//			return map;
//		}
//	}
//	
//	protected <K, T> Map<K, T> getElementsAsMap(String name, Collection<K> keys) {
//		String cacheName = getCacheName();
//		return getElementsAsMap(cacheName, keys);
//	}
//
//	protected <K, T> Map<K, T> getElementsAsMap(String cacheName, String name, Collection<K> keys) {
//		Ehcache cache = getCache(cacheName);
//		return getElementsAsMap(cache, name, keys);
//	}
//	
//	protected <K, T> Map<K, T> getElementsAsMap(Ehcache cache, String name, Collection<K> keys) {
//		Map<K, T> map = new HashMap<K, T>();
//		Iterator<K> iterator = keys.iterator();
//		while (iterator.hasNext()) {
//			K key = iterator.next();
//			T value = getCacheValue(cache, name, key);
//			if (value != null)
//				map.put(key, value);
//		}
//		return map;
//	}
//	
//	@SuppressWarnings("unchecked")
//	protected <K, T> Map<K, T> getElementsAsMapOLD(Ehcache cache, String name, Collection<K> keys) {
//		synchronized (mutex) {
//			List<K> keySet = cache.getKeys();
//			Map<K, T> map = new HashMap<K, T>(keys.size());
//			Iterator<K> iterator = keys.iterator();
//			for (int i=0; iterator.hasNext(); i++) {
//				K key = (K) iterator.next();
//				if (keySet.contains(key)) {
//					T element = getElement(name, key);
//					map.put(key, element);
//				}
//			}
//			return map;
//		}
//	}
	
	
	protected <T> T getElement(Object key) {
		String cacheName = getCacheName();
		return getElement(cacheName, cacheName, key);
	}
	
	protected <T> T getElement(String name, Object key) {
		String cacheName = getCacheName();
		return getElement(cacheName, name, key);
	}

	protected <T> T getElement(String cacheName, String name, Object key) {
		Ehcache cache = getCache(cacheName);
		return getElement(cache, name, key);
	}
	
	public <T> T getElement(Ehcache cache, String name, Object key) {
		return getCacheValue(cache, name, key);
	}

	
	public <T> T getElementOLD(String name, Object key) {
		return getElementOLD(name, name, key);
	}
	
	public <T> T getElementOLD(String cacheName, String name, Object key) {
		Ehcache cache = getCache(cacheName);
		return getElementOLD(cache, name, key);
	}
	
	@SuppressWarnings("unchecked")
	public <K, T> T getElementOLD(Ehcache cache, String name, K key) {
		synchronized (mutex) {
			T object = null;
			//List<?> keys = cache.getKeys();
			//Statistics statistics = cache.getStatistics();
			//int size = cache.getSize();
			if (cache != null) {
				String cacheKey = getCacheKey(name, key);
				Element element = cache.get(cacheKey);
				if (element != null) {
					try {
						object = (T) element.getValue();
					} catch (CacheException e) {
						object = (T) element.getObjectValue();
					}
				}
			}
			return object;
		}
	}
	

	protected <T> T getElement(int index) {
		String cacheName = getCacheName();
		return getElement(cacheName, cacheName, index);
	}
	
	protected <T> T getElement(String name, int index) {
		String cacheName = getCacheName();
		return getElement(cacheName, name, index);
	}

	protected <T> T getElement(String cacheName, String name, int index) {
		Ehcache cache = getCache(cacheName);
		return getElement(cache, name, index);
	}
	
	public <T> T getElement(Ehcache cache, String name, int index) {
		synchronized (mutex) {
			T object = null;
			//List<?> keys = cache.getKeys();
			//Statistics statistics = cache.getStatistics();
			//int size = cache.getSize();
			if (cache != null) {
//				Query query = cache.createQuery();
//				query.includeKeys();
//				query.includeValues();
//				query.
//				Results results = query.execute();
//				for (Result result : results.all()) {
//					result.
					
				String cacheKey = getCacheKey(name, null);
				Element element = cache.get(cacheKey);
				if (element != null) {
					try {
						object = (T) element.getValue();
					} catch (CacheException e) {
						object = (T) element.getObjectValue();
					}
				}
			}
			return object;
		}
	}
	
	
	protected void putElement(Object key, Object value) {
		String cacheName = getCacheName();
		putElement(cacheName, cacheName, key, value);
	}
	
	protected void putElement(String name, Object key, Object value) {
		String cacheName = getCacheName();
		putElement(cacheName, name, key, value);
	}
	
	protected void putElement(String cacheName, String name, Object key, Object value) {
		Ehcache cache = getCache(cacheName);
		putElement(cache, name, key, value);
	}

	//overwrite any existing value
	protected void putElement(Ehcache cache, String name, Object key, Object value) {
		//Object existingValue = getCacheValue(cache, name, key);
		CachedItem item = new CachedItem();
		item.setElementName(name);
		item.setElementData(value);
		String cacheKey = getCacheKey(name, key);
		Element newElement = new Element(cacheKey, item);
		cache.put(newElement);
	}

	protected void putElementOLD(String name, Object key, Object value) {
		putElementOLD(name, name, key, value);
	}
	
	protected void putElementOLD(String cacheName, String name, Object key, Object value) {
		Ehcache cache = getCache(cacheName);
		putElementOLD(cache, name, key, value);
	}
	
	protected void putElementOLD(Ehcache cache, String name, Object key, Object value) {
		synchronized (mutex) {
			try {
				//acquireWriteLock();
				//logRegisterConversationStarted("");
				String cacheKey = getCacheKey(name, key);
				Element existingElement = cache.get(cacheKey);
				if (existingElement != null) {
					//TODO merge existing element with new element (using a callback)
				}
				//now just overwrite existing element
				Element newElement = new Element(cacheKey, value);
				cache.put(newElement);
				
				//logRegisterConversationComplete("");
			} catch (Exception e) {
				log.error("Error", e);
				//logRegisterConversationAborted("", e);
				
			} finally {
				//releaseWriteLock();
			}
		}
	}
	

	
	protected void putElements(Collection<?> keys, Collection<?> values) {
		String cacheName = getCacheName();
		putElements(cacheName, cacheName, keys, values);
	}
	
	protected void putElements(String name, Collection<?> keys, Collection<?> values) {
		String cacheName = getCacheName();
		putElements(cacheName, name, keys, values);
	}
	
	protected void putElements(String cacheName, String name, Collection<?> keys, Collection<?> values) {
		Ehcache cache = getCache(cacheName);
		putElements(cache, name, keys, values);
	}
	
	protected void putElements(Ehcache cache, String name, Collection<?> keys, Collection<?> values) {
		synchronized (mutex) {
			Iterator<?> keyIterator = keys.iterator();
			Iterator<?> valueIterator = values.iterator();
			while (keyIterator.hasNext() && valueIterator.hasNext()) {
				Object key = keyIterator.next();
				Object value = valueIterator.next();
				putElement(cache, name, key, value);
			}
		}
	}
	
	
	
	protected void putElements(Map<?, ?> values) {
		String cacheName = getCacheName();
		putElements(cacheName, cacheName, values);
	}
	
	protected void putElements(String name, Map<?, ?> values) {
		String cacheName = getCacheName();
		putElements(cacheName, name, values);
	}
	
	protected void putElements(String cacheName, String name, Map<?, ?> values) {
		Ehcache cache = getCache(cacheName);
		putElements(cache, name, values);
	}
	
	protected void putElements(Ehcache cache, String name, Map<?, ?> values) {
		synchronized (mutex) {
			Iterator<?> iterator = values.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = values.get(key);
				putElement(cache, name, key, value);
			}
		}
	}
	
	
	
	public void replaceElements(Collection<?> keys, Collection<?> values) {
		String cacheName = getCacheName();
		replaceElements(cacheName, cacheName, keys, values);
	}
	
	public void replaceElements(String name, Collection<?> keys, Collection<?> values) {
		String cacheName = getCacheName();
		replaceElements(cacheName, name, keys, values);
	}
	
	protected void replaceElements(String cacheName, String name, Collection<?> keys, Collection<?> values) {
		Ehcache cache = getCache(cacheName);
		replaceElements(cache, name, keys, values);
	}
	
	protected void replaceElements(Ehcache cache, String name, Collection<?> keys, Collection<?> values) {
		synchronized (mutex) {
			removeAllElements(name);
			putElements(cache, name, keys, values);
		}
	}
	
	
	public void replaceElements(Map<?, ?> values) {
		String cacheName = getCacheName();
		replaceElements(cacheName, cacheName, values);
	}
	
	public void replaceElements(String name, Map<?, ?> values) {
		String cacheName = getCacheName();
		replaceElements(cacheName, name, values);
	}
	
	protected void replaceElements(String cacheName, String name, Map<?, ?> values) {
		Ehcache cache = getCache(cacheName);
		replaceElements(cache, name, values);
	}
	
	protected void replaceElements(Ehcache cache, String name, Map<?, ?> values) {
		synchronized (mutex) {
			removeAllElements(name);
			putElements(cache, name, values);
		}
	}
	
	
	protected <T> List<T> removeElementsByKey(Collection<?> keys) {
		String cacheName = getCacheName();
		return removeElementsByKey(cacheName, cacheName, keys);
	}
	
	protected <T> List<T> removeElementsByKey(String name, Collection<?> keys) {
		String cacheName = getCacheName();
		return removeElementsByKey(cacheName, name, keys);
	}
	
	protected <T> List<T> removeElementsByKey(String cacheName, String name, Collection<?> keys) {
		Ehcache cache = getCache(cacheName);
		return removeElementsByKey(cache, name, keys);
	}
	
	protected <T> List<T> removeElementsByKey(Ehcache cache, String name, Collection<?> keys) {
		synchronized (mutex) {
			List<T> values = new ArrayList<T>(keys.size());
			Iterator<?> iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				T value = removeElementByKey(cache, name, key);
				if (value != null)
					values.add(value);
			}
			return values;
		}
	}
	
	
	protected <T> T removeElementByKey(Object key) {
		String cacheName = getCacheName();
		return removeElementByKey(cacheName, cacheName, key);
	}
	
	protected <T> T removeElementByKey(String name, Object key) {
		String cacheName = getCacheName();
		return removeElementByKey(cacheName, name, key);
	}
	
	protected <K, T> T removeElementByKey(String cacheName, String name, K key) {
		Ehcache cache = getCache(cacheName);
		return removeElementByKey(cache, name, key);
	}
	
	protected <K, T> T removeElementByKey(Ehcache cache, String name, K key) {
		String cacheKey = getCacheKey(name, key);
		Element element = cache.get(cacheKey);
		if (element != null) {
			Object value = element.getObjectValue();
			Assert.isInstanceOf(CachedItem.class, value);
			CachedItem item = (CachedItem) value;
			@SuppressWarnings("unchecked")
			T data = (T) item.getElementData();
			if (cache.remove(cacheKey)) {
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ "+cacheKey+", "+data);
				return data;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T removeElementByKeyOLD(Ehcache cache, String name, Object key) {
		synchronized (mutex) {
			T object = null;
			if (cache != null) {
				String cacheKey = getCacheKey(name, key);
				Element element = cache.get(cacheKey);
				if (element != null) {
					object = (T) element.getObjectValue();
					if (!cache.remove(key))
						return null;
				}
			}
			return object;
		}
	}
	
	
//	protected <T> List<T> removeElements(Map<?, T> map) {
//		String cacheName = getCacheName();
//		return removeElements(cacheName, map);
//	}
//
//	protected <T> List<T> removeElements(String name, Map<?, T> map) {
//		String cacheName = getCacheName();
//		List<?> keys = new ArrayList<Object>(map.keySet());
//		return removeElements(cacheName, cacheName, keys, map.values());
//	}
//
//	protected <T> List<T> removeElements(List<?> keys, Collection<T> values) {
//		String cacheName = getCacheName();
//		return removeElements(cacheName, cacheName, keys, values);
//	}
//	
//	protected <T> List<T> removeElements(String name, List<?> keys, Collection<T> values) {
//		String cacheName = getCacheName();
//		return removeElements(cacheName, name, keys, values);
//	}
//	
//	protected <T> List<T> removeElements(String cacheName, String name, List<?> keys, Collection<T> values) {
//		Ehcache cache = getCache(cacheName);
//		return removeElements(cache, name, keys, values);
//	}
//	
//	protected <T> List<T> removeElements(Ehcache cache, String name, List<?> keys, Collection<T> values) {
//		synchronized (mutex) {
//			List<T> list = new ArrayList<T>(values.size());
//			Iterator<T> iterator = values.iterator();
//			for (int i=0; iterator.hasNext(); i++) {
//				T element = iterator.next();
//				Object key = keys.get(i);
//				T value = removeElement(cache, name, key, element);
//				if (value != null)
//					list.add(value);
//			}
//			return list;
//		}
//	}
	
	
//	protected <T> T removeElement(String name, Object key, T value) {
//		String cacheName = getCacheName();
//		return removeElement(cacheName, name, key, value);
//	}
//	
//	protected <T> T removeElement(String cacheName, String name, Object key, T value) {
//		Ehcache cache = getCache(cacheName);
//		return removeElement(cache, name, key, value);
//	}
//	
//	protected <T> T removeElement(Ehcache cache, String name, Object key, T value) {
//		CachedItem item = new CachedItem();
//		item.setElementName(name);
//		item.setElementData("empty");
//		Element element = new Element(key, item);
//		cache.put(element);
//		//TODO - This call below "removeElement" causes this exception:
//		//You have configured the cache with a replication scheme that cannot properly support CAS operation guarantees
//		//if (cache.removeElement(element))
//		//	return value;
//		return value;
//	}
	
	
	
//	protected <T> Set<T> removeElementsFromSet(List<?> keys) {
//		String cacheName = getCacheName();
//		return removeElementsFromSet(cacheName, cacheName, keys);
//	}
//	
//	protected <T> Set<T> removeElementsFromSet(String name, List<?> keys) {
//		String cacheName = getCacheName();
//		return removeElementsFromSet(cacheName, name, keys);
//	}
//	
//	protected <T> Set<T> removeElementsFromSet(String cacheName, String name, List<?> keys) {
//		Ehcache cache = getCache(cacheName);
//		return removeElementsFromSet(cache, name, keys);
//	}
//	
//	protected <T> Set<T> removeElementsFromSet(Ehcache cache, String name, List<?> keys) {
//		synchronized (mutex) {
//			Set<T> set = new HashSet<T>(keys.size());
//			Iterator<?> iterator = keys.iterator();
//			while (iterator.hasNext()) {
//				Object key = iterator.next();
//				T value = removeElementByKey(cache, name, key);
//				if (value != null)
//					set.add(value);
//			}
//			return set;
//		}
//	}
	
	

	public void removeAllElements() {
		Ehcache cache = getCache(getCacheName());
		cache.removeAll();
	}
	
	public <T> Collection<T> removeAllElements(String name) {
		Ehcache cache = getCache(getCacheName());
		Set<Object> keys = new HashSet<Object>();
		List<T> values = new ArrayList<T>();
		Query query = cache.createQuery();
		query.includeKeys();
		query.includeValues();
		Results results = query.execute();
		List<Result> all = results.all();
		Iterator<Result> iterator = all.iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			Object key = result.getKey();
			@SuppressWarnings("unchecked")
			T value = (T) result.getValue();
			if (value != null) {
				keys.add(key);
				values.add(value);
			}
		}
		cache.removeAll(keys);
		return values;
	}
	
	
	protected <T> List<T> getAllElementsFromCache(String cacheName) {
		List<T> list = new ArrayList<T>();
		CacheManager cacheManager = getCacheManager();
		Cache cache = cacheManager.getCache(cacheName);
		@SuppressWarnings("unchecked")
		List<String> keys = cache.getKeys();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Element element = cache.get(key);
			@SuppressWarnings("unchecked")
			T value = (T) element.getObjectValue();
			if (value != null)
				list.add(value);
		}
		return list;
	}
	
	/**
	 * Carries out re-population of specified cache by calling specified Runnable.
	 * Assumes the existance of a cache specific lock called "cacheLock".
	 * Appropriately acquires/releases cache lock.
	 * @param cacheName
	 * @param runner
	 */
	protected void resetNamedCache(String cacheName, Runnable runner) {
		Cache cache = getCacheManager().getCache(cacheName);
		ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
		Element element = new Element("cacheLock", cacheLock);
		cache.put(element);
		// Lock read/write access
		cacheLock.writeLock();
   		log.info("Starting cache reset for: "+cacheName);
   		// Carry out the reset
   		runner.run();
   		log.info("Done with cache reset for: "+cacheName);
		// unlock read/write access
   		cacheLock.writeLock();
	}
	
	public void shutdown() {
		if (getCacheManager() != null) {
			getCacheManager().shutdown();
		}
	}

	
	
	
	protected <K, T> T getCacheValue(Ehcache cache, String name, K key) {
		Attribute<String> elementName = cache.getSearchAttribute("elementName");
		//List<Object> keys = cache.getKeys();
		Query query = cache.createQuery();
		query.includeKeys();
		query.includeValues();
		String cacheKey = getCacheKey(name, key);
		@SuppressWarnings("unchecked")
		Criteria keyCriteria = Query.KEY.eq(cacheKey);
		Criteria nameCriteria = elementName.eq(name);
		query.addCriteria(keyCriteria.and(nameCriteria));
		Results results = query.execute();
		int size = results.all().size();
		Assert.isTrue(size == 0 || size == 1);
		Iterator<Result> iterator = results.all().iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			Object value = result.getValue();
			Assert.isInstanceOf(CachedItem.class, value);
			CachedItem item = (CachedItem) value;
			@SuppressWarnings("unchecked")
			T data = (T) item.getElementData();
			return data;
		}
		
//		Object actualKey = getCacheKey(name, key);
//		Element element = cache.get(actualKey);
//		if (element != null) {
//			try {
//				object = (T) element.getValue();
//			} catch (CacheException e) {
//				object = (T) element.getObjectValue();
//			}
//		}
		return null;
	}
	
	
	
	protected <T> List<T> getCacheValues(Ehcache cache, String name) {
		Attribute<String> elementName = cache.getSearchAttribute("elementName");
		Query query = cache.createQuery();
		query.includeKeys();
		query.includeValues();
		query.addCriteria(Query.KEY.ilike(name+"*").and(elementName.eq(name)));
		Results results = query.execute();
		List<T> list = new ArrayList<T>();
		Iterator<Result> iterator = results.all().iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			Object value = result.getValue();
			Assert.isInstanceOf(CachedItem.class, value);
			CachedItem item = (CachedItem) value;
			@SuppressWarnings("unchecked")
			T data = (T) item.getElementData();
			list.add(data);
		}
		return list;
	}
	
	
	protected <T> Set<T> getCacheValuesAsSet(Ehcache cache, String name) {
		Attribute<String> elementName = cache.getSearchAttribute("elementName");
		Query query = cache.createQuery();
		query.includeKeys();
		query.includeValues();
		query.addCriteria(Query.KEY.ilike(name+"*").and(elementName.eq(name)));
		Results results = query.execute();
		Set<T> set = new HashSet<T>();
		Iterator<Result> iterator = results.all().iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			Object value = result.getValue();
			Assert.isInstanceOf(CachedItem.class, value);
			CachedItem item = (CachedItem) value;
			@SuppressWarnings("unchecked")
			T data = (T) item.getElementData();
			set.add(data);
		}
		return set;
	}
	
	
//	protected <K, T> Map<K, T> getCacheValuesAsMap(Ehcache cache, String name) {
//		Attribute<String> elementName = cache.getSearchAttribute("elementName");
//		Query query = cache.createQuery();
//		query.includeKeys();
//		query.includeValues();
//		query.addCriteria(Query.KEY.ilike(name+"*").and(elementName.eq(name)));
//		Results results = query.execute();
//		Map<K, T> map = new HashMap<K, T>();
//		Iterator<Result> iterator = results.all().iterator();
//		while (iterator.hasNext()) {
//			Result result = iterator.next();
//			@SuppressWarnings("unchecked")
//			String cacheKey = (String) result.getKey();
//			Object value = result.getValue();
//			Assert.isInstanceOf(CachedItem.class, value);
//			CachedItem item = (CachedItem) value;
//			@SuppressWarnings("unchecked")
//			T data = (T) item.getElementData();
//			@SuppressWarnings("unchecked")
//			K rawKey = (K) getRawKey(cacheKey);
//			map.put(rawKey, data);
//		}
//		return map;
//	}
	
	
	protected String getCacheKey(String name, Object key) {
		if (key instanceof String)
			return (String) key;
		CacheKey cacheKey = new CacheKey();
		cacheKey.setName(name);
		cacheKey.setKey(key);
		return cacheKey.toString();
	}
	
	protected <K> K getRawKey(CacheKey cacheKey) {
		@SuppressWarnings("unchecked") 
		K key = (K) cacheKey.getKey();
		return key;
	}
	
	protected Object getRawKeyOLD(String name, Object key) {
		String textValue = key.toString();
		if (!textValue.startsWith(name))
			return textValue;
		int beginIndex = name.length() + 1;
		int endIndex = key.toString().length() - 1;
		return textValue.substring(beginIndex, endIndex);
	}
	
}
