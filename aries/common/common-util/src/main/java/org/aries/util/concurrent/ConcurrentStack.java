package org.aries.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ConcurrentStack
 * Nonblocking stack using Treiber's algorithm
 *
 * @author Brian Goetz and Tim Peierls
 */
//@ThreadSafe
public class ConcurrentStack<E> {
	
	private static final Log log = LogFactory.getLog(ConcurrentStack.class);

	private AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

	
	public void push(E item) {
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead;
		do {
			oldHead = top.get();
			newHead.next = oldHead;
			if (log.isTraceEnabled())
				log.trace("Pushing: "+item);
		} while (!top.compareAndSet(oldHead, newHead));
	}

	public E pop() {
		Node<E> oldHead;
		Node<E> newHead;
		do {
			oldHead = top.get();
			if (oldHead == null)
				return null;
			newHead = oldHead.next;
		} while (!top.compareAndSet(oldHead, newHead));
		if (log.isTraceEnabled())
			log.trace("Popping: "+oldHead.item);
		return oldHead.item;
	}

	public E peek() {
		Node<E> oldHead = top.get();
		if (oldHead == null)
			return null;
		if (log.isTraceEnabled())
			log.trace("Peeking: "+oldHead.item);
		return oldHead.item;
	}

	public E remove(E item) {
		boolean found = false;
		Node<E> oldHead;
		Node<E> newHead;
		do {
			oldHead = top.get();
			if (oldHead == null)
				return null;
			if (oldHead.item.equals(item))
				found = true;
			newHead = oldHead.next;
		} while (!top.compareAndSet(oldHead, newHead) && !found);
		if (log.isTraceEnabled())
			log.trace("Removing: "+oldHead.item);
		return oldHead.item;
	}

	private static class Node <E> {
		public final E item;
		public Node<E> next;

		public Node(E item) {
			this.item = item;
		}
	}
}
