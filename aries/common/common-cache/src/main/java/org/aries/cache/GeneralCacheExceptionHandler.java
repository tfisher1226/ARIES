package org.aries.cache;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.exceptionhandler.CacheExceptionHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Provides a handler which may be registered with an Ehcache, 
 * to handle exceptions thrown on Cache operations.
 * <p/>
 * Handlers may be registered at configuration time in ehcache.xml, using a
 * CacheExceptionHandlerFactory, or set at runtime (a strategy).
 * <p/>
 * If an exception handler is registered, the default behaviour of throwing the exception
 * will not occur. The handler method <code>onException</code> will be called. Of course, if
 * the handler decides to throw the exception, it will propagate up through the call stack.
 * If the handler does not, it won't.
 * <p/>
 * Some common Exceptions thrown, and which therefore should be considered when 
 * implementing this class are listed below:
 * <ul>
 * <li>{@link IllegalStateException} if the cache is not
 * {@link net.sf.ehcache.Status#STATUS_ALIVE}
 * </li>
 * <li>{@link IllegalArgumentException} if an attempt is made to put a null
 * element into a cache
 * </li>
 * <li>{@link net.sf.ehcache.distribution.RemoteCacheException} if an issue occurs
 *  in remote synchronous replication
 * </li>
 * </ul>
 */
public class GeneralCacheExceptionHandler implements CacheExceptionHandler {

	private static Log log = LogFactory.getLog(GeneralCacheExceptionHandler.class);
	
	/**
	 * Called if an Exception occurs in a Cache method. 
	 * This method is not called if an <code>Error</code> occurs.
	 *
	 * @param Ehcache The cache in which the Exception occurred
	 * 
	 * @param key The key used in the operation, or null if the operation 
	 * does not use a key or the key was null.
	 * 
	 * @param exception The exception that was caught.
	 */
	public void onException(Ehcache ehcache, Object key, Exception exception) {
		//List keys = ehcache.getKeys();
		log.error(key, exception);
	}

}
