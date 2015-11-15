/*
 * EventAdapter.java -
 * Created on March 19, 2002
 * 
 * 
 */
package org.aries.event;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.aries.common.AbstractEvent;
import org.aries.util.ReflectionUtil;



/**
 * Provides implementation of a listener for <code>Event</code>s that 
 * does nothing.  Designed to be overridden by sub-classes that provide context
 * specific behaviour i.e. specific event handler methods for specific events.
 * @author tfisher
 */
public abstract class EventAdapter<T extends AbstractEvent> implements EventListener<T> {

    private String[] _interestedEvents = new String[0];
    
    
    public EventAdapter() {
        //registers interest in all events
        registerInterest(Event.class);
    }

    public EventAdapter(String eventType) {
        registerInterest(eventType);
    }

    public EventAdapter(Class<T> eventType) {
    	eventType.getCanonicalName();
    	eventType.getSimpleName();
    	String name = eventType.getName();
		int index = name.lastIndexOf(".");
    	if (index != -1)
    		name = name.substring(index+1);
        registerInterest(name);
    }

    //public EventAdapter(Class[] eventTypes) {
    //    //does nothing yet
    //}

    /**
     * Provides set of event types that this listener is interested in.
     * @return the set of event types that this listener is interested in.
     * @see EventProcessor#mask()
     */
    public String[] mask() {
        return _interestedEvents;
    }
    
    /**
     * Allows sub-classes to register interest for a specific event i.e. receiving 
     * notification when an event of the specified <code>eventType</code> is fired. 
     * @param The <code>eventType</code> to register interest for.
     */
    public void registerInterest(Class eventType) {
    	registerInterest(eventType.getName());
    }
    
    /**
     * Allows sub-classes to register interest for a specific event i.e. receiving 
     * notification when an event of the specified <code>eventType</code> is fired. 
     * @param The <code>eventType</code> to register interest for.
     */
    public void registerInterest(String eventType) {
        synchronized(this) {
        	String[] a = (String[]) Array.newInstance(String.class, _interestedEvents.length+1);
	        System.arraycopy(_interestedEvents, 0, a, 0, _interestedEvents.length);
	        a[_interestedEvents.length] = eventType;
	        _interestedEvents = a;
        }
    }
    
    /** 
     * Performs event processing by delegating (dispatching) to sub-class specific
     * handler methods.  Searches through sub-class specific "process" methods for 
     * an exact match on the <code>class</code> of specified <code>event</code>.
     * @param event the <code>event</code> to be processed.
     */ 
    public void process(Event event) {
    	Class[] argTypes = new Class[] {event.getClass()};
        Method method = ReflectionUtil.findMethod(getClass(), "process", argTypes);
        if (method != null) {
            Object[] args = new Object[] {event};
            ReflectionUtil.invokeMethod(this, method, args);
        }
    }

}
