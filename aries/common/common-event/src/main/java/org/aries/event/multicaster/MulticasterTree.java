package org.aries.event.multicaster;

import org.aries.common.AbstractEvent;
import org.aries.event.EventListener;


public class MulticasterTree {

    /**
     * Returns the resulting multicast listener from adding listener-a and
     * listener-b together. Of course, different types of event listeners are
     * not to be mixed.
     *
     * @param a event listener-a
     * @param b event listener-b
     *
     * @return the resulting listener if at least one of the two parameters is
     * not null; <code>null</code> otherwise.
     */
    public <T extends AbstractEvent> EventListener<T> add(EventListener<T> a, EventListener<T> b) {
        if (a == null)
            return b;
        if (b == null)
            return a;
        return new MulticasterNode<T>(a, b);
    }

    /**
     * Returns the resulting multicast listener after removing listener-removed
     * from listener-from. Of course, different types of event listeners are
     * not to be mixed.
     *
     * @param from the listener being removed from
     * @param removed the listener being removed
     *
     * @return the resulting listener. If, before the removal or as a consequence
     * of it, no listener remains then returns <code>null</code>.
     */
	public <T extends AbstractEvent> EventListener<T> remove(EventListener<T> from, EventListener<T> removed) {
        if ((from == removed) || (from == null))
            return null;
        else if (removed == null)
            return from;
        else if (from instanceof MulticasterNode)
            return removeInternal((MulticasterNode<T>) from, removed);
        return from;
    }
    
    /**
     * Removes a listener from this multicaster and returns the
     * resulting multicast listener.
     *
     * @param oldListener the listener to be removed
     * @return the resulting listener.
     * @post $return != null
     */
    protected <T extends AbstractEvent> EventListener<T> removeInternal(MulticasterNode<T> node, EventListener<T> oldListener) {
        if (oldListener == node.a)
            return node.b;
        if (oldListener == node.b)
            return node.a;

        EventListener<T> a2 = remove(node.a, oldListener);
        EventListener<T> b2 = remove(node.b, oldListener);

        if ((a2 == node.a) && (b2 == node.b))
            return node;
        return add(a2, b2);
    }
    
}
