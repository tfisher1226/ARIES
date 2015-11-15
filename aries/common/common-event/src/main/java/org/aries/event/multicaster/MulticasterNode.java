/*
 * EventMulticaster.java -
 * Created on March 3, 2002
 * 
 * 
 */
package org.aries.event.multicaster;

import org.aries.common.AbstractEvent;
import org.aries.event.EventListener;

/**
 * A general purpose class which implements efficient and thread-safe multi-cast 
 * event dispatching for the events which extend the <code>AbstractEvent</code>
 * base class.
 * <p>
 * This class is based in part on the <code>java.awt.AWTEventMulticaster</code>
 * but has been modified to avoid the requirement of having to know about any
 * specific event type.  This modification does not compromise any feature or
 * otherwise violate the elegance of this data structure but instead enhances
 * its beauty (its reusability) by becoming general purpose in nature. -tfisher
 * <p>
 * This class will manage an immutable structure consisting of a chain of event 
 * listeners and will dispatch events to those listeners.  Because the structure 
 * is immutable, it is safe to use this API to add/remove listeners during the 
 * process of an event dispatch operation.  In addition, event listeners added 
 * during the processing of an event dispatch operation will not be notified of 
 * the event currently being dispatched.  And, event listeners that have been
 * removed will not be notified of impending events after their removal.  And, 
 * perhaps more importantly, the event listener list (comprised of instances
 * of this class) will NOT be copied in any portion at any time during event
 * notification.  Note: these problems plague most all other implementations of
 * event dispatching and structures of event listener lists.
 * 
 * @author tfisher
 *
 */
public class MulticasterNode<T extends AbstractEvent> implements EventListener<T> {

    /** Referred event listener. */
    protected final EventListener<T> a;

    /** Referred event listener. */
    protected final EventListener<T> b;

//    /** The events this listener is interested in. */
//    protected final String[] _eventMask;

    
    /**
     * Creates an event multicaster instance which chains listener-a
     * with listener-b.
     *
     * @param a listener-a
     * @param b listener-b
     *
     * @pre a != null
     * @pre b != null
     */
    protected MulticasterNode(EventListener<T> a, EventListener<T> b) {
        this.a = a;
        this.b = b;

//        // Here we try not to go down the whole structure.
//        if (a instanceof MulticasterNode) {
//            _eventMask = b.mask();
//        } else {
//            _eventMask = a.mask();
//        }
    }

//    /**
//     * Returns the set of events this processor is interested in.
//     * @return an array of interested event types.
//     * @see EventListener#mask()
//     */
//    public String[] mask() {
//        return _eventMask;
//    }

    /** 
     * Performs event processing by delegating (dispatching) to
     * underlying (or chained) event processors.
     * @param event the event to be processed.
     */  
    public void process(T event) {
        a.process(event);
        b.process(event);
    }

//    /**
//     * Removes a listener from this multicaster and returns the
//     * resulting multicast listener.
//     *
//     * @param oldListener the listener to be removed
//     * @return the resulting listener.
//     * @post $return != null
//     */
//    protected EventListener removeInternal(EventListener oldListener) {
//        if (oldListener == _a)
//            return _b;
//        if (oldListener == _b)
//            return _a;
//
//        EventListener<T> a2 = MulticasterTree.remove(_a, oldListener);
//        EventListener<T> b2 = MulticasterTree.remove(_b, oldListener);
//
//        if ((a2 == _a) && (b2 == _b))
//            return this;
//        return MulticasterTree.add(a2, b2);
//    }
}
