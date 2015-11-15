package nam.model.persistenceProvider;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;


public class PersistenceProviderListObject extends AbstractListObject<PersistenceProvider> implements Comparable<PersistenceProviderListObject>, Serializable {
	
	private PersistenceProvider persistenceProvider;
	
	
	public PersistenceProviderListObject(PersistenceProvider persistenceProvider) {
		this.persistenceProvider = persistenceProvider;
	}
	
	
	public PersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}
	
	@Override
	public Object getKey() {
		return getKey(persistenceProvider);
	}
	
	public Object getKey(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.getKey(persistenceProvider);
	}
	
	@Override
	public String getLabel() {
		return getLabel(persistenceProvider);
	}
	
	public String getLabel(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.getLabel(persistenceProvider);
	}
	
	@Override
	public String toString() {
		return toString(persistenceProvider);
	}
	
	@Override
	public String toString(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.toString(persistenceProvider);
	}
	
	@Override
	public int compareTo(PersistenceProviderListObject other) {
		Object thisKey = getKey(this.persistenceProvider);
		Object otherKey = getKey(other.persistenceProvider);
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
		PersistenceProviderListObject other = (PersistenceProviderListObject) object;
		Object thisKey = PersistenceProviderUtil.getKey(this.persistenceProvider);
		Object otherKey = PersistenceProviderUtil.getKey(other.persistenceProvider);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
