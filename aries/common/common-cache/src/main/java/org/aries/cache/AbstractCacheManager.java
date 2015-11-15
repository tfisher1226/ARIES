package org.aries.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractCacheManager {

	protected abstract CacheManager getCacheManager();

	protected abstract Ehcache getCache(String cacheName);

	
	private Log log = LogFactory.getLog(getClass());
	
	
	protected <T> T getElementFromCache(String cacheName, String key) {
		Ehcache cache = getCache(cacheName);
		Element element = cache.get(key);
		@SuppressWarnings("unchecked")
		T value = (T) element.getValue();
		return value;
	}
	
	protected <T> void addElementToCache(String cacheName, String key, T value) {
		Ehcache cache = getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	protected <K, V> void addElementToCache(String cacheName, String key, Map<K, V> value) {
		Ehcache cache = getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	protected <T> void addElementToCache(String cacheName, String key, List<T> value) {
		Ehcache cache = getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	protected <T> List<T> getElementsFromCache(String cacheName) {
		List<T> list = new ArrayList<T>();
		Cache cache = getCacheManager().getCache(cacheName);
		@SuppressWarnings("unchecked")
		List<String> keys = cache.getKeys();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Element element = cache.get(key);
			Serializable value = element.getValue();
			@SuppressWarnings("unchecked")
			T object = (T) value;
			list.add(object);
		}
		return list;
	}

	
	//NOT USED RIGHT NOW
	public <T> void updateElement(String cacheName, String key, T value, Callable<T> callable) {
		ReentrantReadWriteLock cacheLock = getCacheLock(cacheName);
		try {
			cacheLock.writeLock().lock();
			log.debug("started");
			Ehcache cache = getCache(cacheName);
			Element element = new Element(key, value);
			cache.put(element);
			log.debug("complete");
		} catch (Exception e) {
			log.debug("aborted", e);
		} finally {
			cacheLock.writeLock().unlock();
		}

	}
	
	//NOT USED RIGHT NOW
	@SuppressWarnings("unchecked")
	public <T> T getElement(String cacheName, String key, Callable<T> callable) {
		ReentrantReadWriteLock cacheLock = getCacheLock(cacheName);
		try {
			cacheLock.readLock().lock();
			log.debug("started");
			Ehcache cache = getCache(cacheName);
			Element element = cache.get(key);
			T value = null;
			
			if (element != null) {
				// Element exists
				value = (T) element.getValue();
				log.debug("complete");
				return value;

			} else {
				// Element does not exist, use callable to get it
				// Must release read lock before acquiring write lock
				cacheLock.readLock().unlock();
				cacheLock.writeLock().lock();
				// Re-check state because another thread may have acquired
				// the write lock and changed state before we did
				element = cache.get(key);
				if (element == null) {
					value = callable.call();
					if (value != null) {
						element = new Element(key, value);
						cache.put(element);
					}
				}
				// Downgrade by acquiring read lock before releasing write lock
				cacheLock.readLock().lock();
				// Unlock write access, still hold read access
				cacheLock.writeLock().unlock(); 
				log.debug("complete");
				return value;
			}
		} catch (Exception e) {
			log.debug("aborted", e);
			return null;
		} finally {
			cacheLock.readLock().unlock();
		}
	}

	
	/**
	 * Carries out re-population of specified cache by calling specified Runnable.
	 * Assumes the existance of a cache specific lock called "cacheLock".
	 * Appropriately acquires/releases cache lock.
	 * @param cacheName
	 * @param runner
	 */
	//NOT USED RIGHT NOW
	//TODO use a Callable
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

	// this is an example of how to use the above method
	protected void resetEmployeeNameCache() {
		resetNamedCache("employeeNameCache", new Runnable() {
			public void run() {
				//retrieveEmployeeNames();
			}
		});
	}

	//NOT USED RIGHT NOW
	protected ReentrantReadWriteLock getCacheLock(String cacheName) {
		Ehcache cache = getCache(cacheName);
		Element element = cache.get("cacheLock");
		if (element == null)
			throw new RuntimeException("Invalid cache state, lock not found");
		ReentrantReadWriteLock lock = (ReentrantReadWriteLock) element.getValue();
		return lock;
	}
	
	public void shutdownCache() {
		if (getCacheManager() != null) {
			getCacheManager().shutdown();
		}
	}

}
