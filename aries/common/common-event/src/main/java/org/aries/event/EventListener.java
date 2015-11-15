/*
 * EventListener.java -
 * Created on March 3, 2002
 * 
 * 
 */
package org.aries.event;

import org.aries.common.AbstractEvent;


/**
 * The base event interface that all event processor interfaces must extend.
 * @author tfisher
 */
public interface EventListener<T extends AbstractEvent> {

	//public String getName();
	
    /**
     * Returns the set of events this processor is interested in.
     * @return an array of interested event types.
     */
    //public String[] mask();

    /**
     * Performs event processing or delegates event dispatch 
     * to some underlying (or chained) event processor.
     * @param event the event to be processed.
     */
    public void process(T event);
    
}
