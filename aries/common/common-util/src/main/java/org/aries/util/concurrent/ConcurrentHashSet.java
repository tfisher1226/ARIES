package org.aries.util.concurrent;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentHashSet<E> extends AbstractSet<E> {

	private Map<E, Object> _map;

	private static Object _tmp = new Object();


	public ConcurrentHashSet() {
		_map = new ConcurrentHashMap<E, Object>();
	}

	public ConcurrentHashSet(Set<E> other) {
		_map = new ConcurrentHashMap<E, Object>();
		addAll(other);
	}

	public ConcurrentHashSet(int size) {
		_map = new ConcurrentHashMap<E, Object>(size);
	}

	public int size() {
		return _map.size();
	}

	public boolean isEmpty() {
		return _map.isEmpty();
	}

	public boolean add(E object) {
		return _map.put(object, _tmp) == _tmp;
	}

	public boolean contains(Object o) {
		return _map.containsKey(o);
	}

	public boolean remove(Object object) {
		return _map.remove(object) == _tmp;
	}

	public Iterator<E> iterator() {
		return _map.keySet().iterator();
	}

	public void clear() {
		_map.clear();
	}

}
