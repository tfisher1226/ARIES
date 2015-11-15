package org.aries.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.AssertionFailedError;


public abstract class AbstractTestCase {

	protected final Log log = LogFactory.getLog(getClass());
	
	
	/**
	 * Fails a test with the given message.
	 */
	public void fail(String message) {
		throw new AssertionFailedError(message);
	}
	
	/**
	 * Fails a test with no message.
	 */
	public void fail() {
		fail(null);
	}
	
}
