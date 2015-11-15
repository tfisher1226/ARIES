/*
 * EventDispatcher.java -
 * Created on March 3, 2002
 * 
 * 
 */
package org.aries.event.multicaster;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aries.common.AbstractEvent;
import org.aries.event.EventListener;


/**
 * Handles the dispatching of events to local listeners i.e. listeners within the current JVM. 
 * @author tfisher
 */
public abstract class AbstractEventMulticaster /*implements EventMulticaster*/ {

	/** The event listener repository. */
	private Map<String, EventListener<?>> listeners;
	//private Map _eventProcessors = new ConcurrentHashMap();

	private MulticasterTree multicasterTree;
	
	
	/**
	 * Creates new instance of <code>AbstractEventMulticaster</code>. 
	 */
	public AbstractEventMulticaster() {
		multicasterTree = new MulticasterTree();
		listeners = new HashMap<String, EventListener<?>>();
	}
	
	public String getMBeanName() {
		String className = getClass().getName();
		String packageName = getClass().getPackage().getName();
		return packageName+":name="+className;
	}
	
//	@PostConstruct
//	public void registerWithJMX() {
//		MBeanUtil.registerMBean(this, getMBeanName());
//	}
//	
//	@PreDestroy
//	public void unregisterWithJMX() {
//		MBeanUtil.unregisterMBean(getMBeanName());
//	}

	/**
	 * Sends an event to all listeners registered for that event.
	 * @param event the event to be processed.
	 */
	//@Override
	public <T extends AbstractEvent> void fireEvent(T event) {
		Class<?> className = event.getClass();
		System.out.println(">>> Dispatching event: "+className);
		EventListener<T> processor = getListener(event);
		if (processor != null) {
			processor.process(event);
		}
		System.out.println(">>> Dispatched event: "+className);
	}
	
	/**
	 * Sends a list of events to all listeners registered for each event.
	 * @param eventList the event list to be processed.
	 */
	//@Override
	public <T extends AbstractEvent> void fireEvents(List<T> eventList) {
		Iterator<T> iterator = eventList.iterator();
		while (iterator.hasNext()) {
			T event = iterator.next();
			fireEvent(event);
		}
	}

	/**
	 * Adds given event processor (event listener) to specific listener lists 
	 * that are specified within given processor.  The listener will then be 
	 * notified of all events it is interested in, i.e. those listed in its 
	 * <code>mask()</code> method, until unregistered.
	 *
	 * @param newListener the listener to be registered.
	 */
	//@Override
	public synchronized <T extends AbstractEvent> void addEventListener(String eventType, EventListener<T> newListener) {
		if (newListener != null) {
			EventListener<T> listener = getListener(eventType);
			listeners.put(eventType, multicasterTree.add(listener, newListener));
			//System.out.println("******* ADDED "+listeners.get(eventType));
		}
	}
	
	//@Override
	public synchronized <T extends AbstractEvent> void addEventListener(Class<T> eventType, EventListener<T> newListener) {
		addEventListener(eventType.getName(), newListener);
	}

	/**
	 * Removes given event processor (event listener) from specific listener lists
	 * that are specified with the given processor. The listener will thereafter not 
	 * be notified of any of the events listed in its <code>mask()</code>
	 * method.
	 *
	 * @param removedListener the listener to be unregistered.
	 */
	//@Override
	public synchronized <T extends AbstractEvent> void removeEventListener(String eventType, EventListener<T> oldListener) {
		if (oldListener != null) {
			EventListener<T> listener = getListener(eventType);
			EventListener<T> removed = multicasterTree.remove(listener, oldListener);
			if (removed != null)
				listeners.put(eventType, removed);
			else listeners.remove(eventType);
			//System.out.println("******* REMOVED "+listeners.get(eventType));
		}
	}

	//@Override
	public synchronized <T extends AbstractEvent> void removeEventListener(Class<T> eventType, EventListener<T> oldListener) {
		removeEventListener(eventType.getName(), oldListener);
	}

	/**
	 * Finds the head of the appropriate processor chain for specified event. 
	 * First look for a matching processor based on the event <code>name</code>.
	 * If <code>name</code> is not set, then look for a matching processor
	 * based on the the event <code>type</code>.  If <code>name</code> is not 
	 * set, then look for a matching processor based on the event's class.
	 * @param event the event to be processed.
	 * @return The appropriate processor for specified event.
	 */
	//@Override
	public <T extends AbstractEvent> EventListener<T> getListener(T event) {
		@SuppressWarnings("unchecked") Class<T> eventType = (Class<T>) event.getClass();
		return getListener(eventType);
	}
	
	//@Override
	public <T extends AbstractEvent> EventListener<T> getListener(Class<T> eventType) {
		return getListener(eventType.getName());
	}

	//@Override
	public <T extends AbstractEvent> EventListener<T> getListener(String eventType) {
		@SuppressWarnings("unchecked") EventListener<T> listener = (EventListener<T>) listeners.get(eventType);
		return listener;
	}

}
