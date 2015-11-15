package org.aries.jms;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.aries.Assert;



public class JmsConnectionListener implements ExceptionListener {

	private Set<ExceptionListener> exceptionListeners;

	
	public JmsConnectionListener() {
		Set<ExceptionListener> set = new HashSet<ExceptionListener>();
		exceptionListeners = Collections.synchronizedSet(set);
	}
	
	public final Set<ExceptionListener> getExceptionListeners() {
		return Collections.unmodifiableSet(exceptionListeners);
	}

	
	public final void addExceptionListener(ExceptionListener listener) {
		Assert.notNull(listener, "Listener must not be null");
		exceptionListeners.add(listener);
	}

	public void onException(JMSException e) {
		synchronized(exceptionListeners) {
			Iterator<ExceptionListener> iterator = exceptionListeners.iterator();
			while (iterator.hasNext()) {
				ExceptionListener listener = (ExceptionListener) iterator.next();
				listener.onException(e);
			}
		}
	}
	
}
