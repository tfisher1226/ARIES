package nam.model.persistence;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;


public class PersistenceListObject extends AbstractListObject<Persistence> implements Comparable<PersistenceListObject>, Serializable {
	
	private Persistence persistence;
	
	
	public PersistenceListObject(Persistence persistence) {
		this.persistence = persistence;
	}
	
	
	public Persistence getPersistence() {
		return persistence;
	}
	
	@Override
	public Object getKey() {
		return getKey(persistence);
	}
	
	public Object getKey(Persistence persistence) {
		return PersistenceUtil.getKey(persistence);
	}
	
	@Override
	public String getLabel() {
		return getLabel(persistence);
	}
	
	public String getLabel(Persistence persistence) {
		return PersistenceUtil.getLabel(persistence);
	}
	
	@Override
	public String toString() {
		return toString(persistence);
	}
	
	@Override
	public String toString(Persistence persistence) {
		return PersistenceUtil.toString(persistence);
	}
	
	@Override
	public int compareTo(PersistenceListObject other) {
		Object thisKey = getKey(this.persistence);
		Object otherKey = getKey(other.persistence);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PersistenceListObject other = (PersistenceListObject) object;
		Object thisKey = PersistenceUtil.getKey(this.persistence);
		Object otherKey = PersistenceUtil.getKey(other.persistence);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
