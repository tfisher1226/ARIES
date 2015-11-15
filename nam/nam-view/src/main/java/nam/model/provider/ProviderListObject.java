package nam.model.provider;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Provider;
import nam.model.util.ProviderUtil;


public class ProviderListObject extends AbstractListObject<Provider> implements Comparable<ProviderListObject>, Serializable {
	
	private Provider provider;
	
	
	public ProviderListObject(Provider provider) {
		this.provider = provider;
	}
	
	
	public Provider getProvider() {
		return provider;
	}
	
	@Override
	public Object getKey() {
		return getKey(provider);
	}
	
	public Object getKey(Provider provider) {
		return ProviderUtil.getKey(provider);
	}
	
	@Override
	public String getLabel() {
		return getLabel(provider);
	}
	
	public String getLabel(Provider provider) {
		return ProviderUtil.getLabel(provider);
	}
	
	@Override
	public String toString() {
		return toString(provider);
	}
	
	@Override
	public String toString(Provider provider) {
		return ProviderUtil.toString(provider);
	}
	
	@Override
	public int compareTo(ProviderListObject other) {
		Object thisKey = getKey(this.provider);
		Object otherKey = getKey(other.provider);
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
		ProviderListObject other = (ProviderListObject) object;
		Object thisKey = ProviderUtil.getKey(this.provider);
		Object otherKey = ProviderUtil.getKey(other.provider);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
