package org.aries.service.jaxws;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.ws.handler.MessageContext;


public class MessageContextImpl implements MessageContext {

	private Map<String, Object> map = new ConcurrentHashMap<String, Object>();
	
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return map.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		this.map.putAll(map);
	}

	@Override
	public void clear() {
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Object> values() {
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public void setScope(String name, Scope scope) {
	}

	@Override
	public Scope getScope(String name) {
		return null;
	}

}
