package org.aries.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;


public class ExceptionUtil {

	/*
	 * Returns the first non-RuntimeException in the cause chain.
	 * We assume here that the main causing Exception that is at the head 
	 * of the cause chain is simply the first non wrapping-exception which 
	 * in our case it almost always the first non-RuntimeException (i.e. 
	 * we use try to always use only RuntimeException as wrapping-exceptions). 
	 * This method won't return a RuntimeException unless only RuntimeExceptions 
	 * exist on the chain, if true then the specified itself is returned.
	 * 
	 * Can we assume if the Exception is not an instance of RuntimeException
	 * then it is an instance of Exception?
	 */
	public static Exception getRootCause(Throwable e) {
		List<?> throwableList = ExceptionUtils.getThrowableList(e);
		Iterator<?> iterator = throwableList.iterator();
		while (iterator.hasNext()) {
			Throwable throwable = (Throwable) iterator.next();
			if (throwable instanceof RuntimeException == false && throwable instanceof Exception)
				return (Exception) throwable;
		}
		if (e instanceof Exception == false)
			return new Exception(e);
		if (e.getCause() != null)
			e = e.getCause();
		return (Exception) e;
	}

	public static void rethrow(Throwable e) throws RuntimeException {
		Throwable cause = ExceptionUtils.getCause(e);
		if (cause == null)
			cause = e;
		if (cause instanceof RuntimeException)
			throw (RuntimeException) cause;
		throw new RuntimeException(cause);
	}

	public static RuntimeException rewrap(Throwable e) {
		Throwable cause = ExceptionUtils.getCause(e);
		if (cause == null)
			cause = e;
		if (cause instanceof RuntimeException)
			return (RuntimeException) cause;
		return new RuntimeException(cause);
	}
	
	public static RuntimeException createRuntimeException(Throwable e) {
		Exception cause = ExceptionUtil.getRootCause(e);
		return new RuntimeException(cause.getLocalizedMessage());
	}
	
}
