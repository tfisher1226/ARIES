package nam.model.minion;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Minion;
import nam.model.util.MinionUtil;


public class MinionListObject extends AbstractListObject<Minion> implements Comparable<MinionListObject>, Serializable {
	
	private Minion minion;
	
	
	public MinionListObject(Minion minion) {
		this.minion = minion;
	}
	
	
	public Minion getMinion() {
		return minion;
	}
	
	@Override
	public Object getKey() {
		return getKey(minion);
	}
	
	public Object getKey(Minion minion) {
		return MinionUtil.getKey(minion);
	}
	
	@Override
	public String getLabel() {
		return getLabel(minion);
	}
	
	public String getLabel(Minion minion) {
		return MinionUtil.getLabel(minion);
	}
	
	@Override
	public String toString() {
		return toString(minion);
	}
	
	@Override
	public String toString(Minion minion) {
		return MinionUtil.toString(minion);
	}
	
	@Override
	public int compareTo(MinionListObject other) {
		Object thisKey = getKey(this.minion);
		Object otherKey = getKey(other.minion);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		MinionListObject other = (MinionListObject) object;
		Object thisKey = MinionUtil.getKey(this.minion);
		Object otherKey = MinionUtil.getKey(other.minion);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
