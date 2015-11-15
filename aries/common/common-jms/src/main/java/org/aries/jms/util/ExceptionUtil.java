package org.aries.jms.util;

import javax.jms.JMSException;


public class ExceptionUtil {

	/**
	 * Converts a throwable to a JMSException if it is not already.
	 * @param message any message to add to a constructed JMSException
	 * @throws JMSException always
	 */
	public static void rethrowAs(Throwable t) throws JMSException {
		throw getAs(t);
	}

	/**
	 * Converts a throwable to a JMSException if it is not already.
	 * @param message any message to add to a constructed JMSException
	 * @param t the throwable
	 * @throws JMSException always
	 */
	public static void rethrowAs(String message, Throwable t) throws JMSException {
		throw getAs(message, t);
	}

	/**
	 * Converts a throwable to a JMSException if it is not already.
	 * @param t the throwable
	 * @return a JMSException
	 */
	public static JMSException getAs(Throwable t) {
		if (t instanceof JMSException)
			return (JMSException) t;
		JMSException e = new JMSException(t.getMessage());
		e.setStackTrace(t.getStackTrace());
		return e;
	}

	/**
	 * Converts a throwable to a JMSException if it is not already.
	 * @param message any message to add to a constructed JMSException
	 * @param t the throwable
	 * @return a JMSException
	 */
	public static JMSException getAs(String message, Throwable t) {
		if (t instanceof JMSException)
			return (JMSException) t;
		message = t.getMessage()+": "+message;
		JMSException e = new JMSException(message);
		e.setStackTrace(t.getStackTrace());
		return e;
	}

}
