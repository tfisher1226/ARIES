package nam.model.cacheProvider;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


public class CacheProviderListObject extends AbstractListObject<CacheProvider> implements Comparable<CacheProviderListObject>, Serializable {
	
	private CacheProvider cacheProvider;
	
	
	public CacheProviderListObject(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}
	
	
	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}
	
	@Override
	public Object getKey() {
		return getKey(cacheProvider);
	}
	
	public Object getKey(CacheProvider cacheProvider) {
		return CacheProviderUtil.getKey(cacheProvider);
	}
	
	@Override
	public String getLabel() {
		return getLabel(cacheProvider);
	}
	
	public String getLabel(CacheProvider cacheProvider) {
		return CacheProviderUtil.getLabel(cacheProvider);
	}
	
	@Override
	public String toString() {
		return toString(cacheProvider);
	}
	
	@Override
	public String toString(CacheProvider cacheProvider) {
		return CacheProviderUtil.toString(cacheProvider);
	}
	
	@Override
	public int compareTo(CacheProviderListObject other) {
		Object thisKey = getKey(this.cacheProvider);
		Object otherKey = getKey(other.cacheProvider);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		CacheProviderListObject other = (CacheProviderListObject) object;
		Object thisKey = CacheProviderUtil.getKey(this.cacheProvider);
		Object otherKey = CacheProviderUtil.getKey(other.cacheProvider);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
