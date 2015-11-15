package org.aries;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class AdapterMulticaster implements AdapterListener {

	private static final int INITIALIZED_EVENT = 1;
	
	private static final int CLOSED_EVENT = 2;
	
	private static final int RESET_EVENT = 3;
	
	private Set<AdapterListener> _listeners;

	
	public AdapterMulticaster() {
		_listeners = Collections.synchronizedSet(new HashSet<AdapterListener>());
	}
	
	public final Set<AdapterListener> getListeners() {
		return Collections.unmodifiableSet(_listeners);
	}

	public final void addListener(AdapterListener listener) {
		Assert.notNull(listener, "Listener must not be null");
		_listeners.add(listener);
	}

	public final void removeListener(AdapterListener listener) {
		Assert.notNull(listener, "Listener must not be null");
		_listeners.remove(listener);
	}
	
	public void initialized() {
		multicast(INITIALIZED_EVENT);
	}
	
	public void closed() {
		multicast(CLOSED_EVENT);
	}
	
	public void reset() {
		multicast(RESET_EVENT);
	}

	protected void multicast(int event) {
		synchronized(_listeners) {
			Iterator<AdapterListener> iterator = _listeners.iterator();
			while (iterator.hasNext()) {
				AdapterListener listener = (AdapterListener) iterator.next();
				switch (event) {
				case INITIALIZED_EVENT: listener.initialized(); break;
				case CLOSED_EVENT: listener.closed(); break;
				case RESET_EVENT: listener.reset(); break;
				}
				
			}
		}
	}
	
}