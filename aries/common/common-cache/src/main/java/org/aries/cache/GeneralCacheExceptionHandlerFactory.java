package org.aries.cache;

import java.util.Properties;

import net.sf.ehcache.exceptionhandler.CacheExceptionHandler;
import net.sf.ehcache.exceptionhandler.CacheExceptionHandlerFactory;


/**
 * Provides a factory for creating <code>CacheExceptionHandler</code>s 
 * at configuration time, from declaration specified in ehcache.xml.
 */
public class GeneralCacheExceptionHandlerFactory extends CacheExceptionHandlerFactory {

	/**
	 * Creates a new <code>CacheExceptionHandler</code>.
	 *
	 * @param properties Implementation specific properties. 
	 * These are configured as comma separated name value pairs in ehcache.xml
	 * 
	 * @return a constructed CacheExceptionHandler
	 */
	public CacheExceptionHandler createExceptionHandler(Properties properties) {
		return new GeneralCacheExceptionHandler();
	}

}
