package nam.model.cache;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Cache;
import nam.model.util.CacheUtil;


public class CacheListObject extends AbstractListObject<Cache> implements Comparable<CacheListObject>, Serializable {
	
	private Cache cache;
	
	
	public CacheListObject(Cache cache) {
		this.cache = cache;
	}
	
	
	public Cache getCache() {
		return cache;
	}
	
	@Override
	public Object getKey() {
		return getKey(cache);
	}
	
	public Object getKey(Cache cache) {
		return CacheUtil.getKey(cache);
	}
	
	@Override
	public String getLabel() {
		return getLabel(cache);
	}
	
	public String getLabel(Cache cache) {
		return CacheUtil.getLabel(cache);
	}
	
	@Override
	public String toString() {
		return toString(cache);
	}
	
	@Override
	public String toString(Cache cache) {
		return CacheUtil.toString(cache);
	}
	
	@Override
	public int compareTo(CacheListObject other) {
		Object thisKey = getKey(this.cache);
		Object otherKey = getKey(other.cache);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		CacheListObject other = (CacheListObject) object;
		Object thisKey = CacheUtil.getKey(this.cache);
		Object otherKey = CacheUtil.getKey(other.cache);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
