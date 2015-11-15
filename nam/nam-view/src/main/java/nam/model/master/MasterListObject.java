package nam.model.master;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Master;
import nam.model.util.MasterUtil;


public class MasterListObject extends AbstractListObject<Master> implements Comparable<MasterListObject>, Serializable {
	
	private Master master;
	
	
	public MasterListObject(Master master) {
		this.master = master;
	}
	
	
	public Master getMaster() {
		return master;
	}
	
	@Override
	public Object getKey() {
		return getKey(master);
	}
	
	public Object getKey(Master master) {
		return MasterUtil.getKey(master);
	}
	
	@Override
	public String getLabel() {
		return getLabel(master);
	}
	
	public String getLabel(Master master) {
		return MasterUtil.getLabel(master);
	}
	
	@Override
	public String toString() {
		return toString(master);
	}
	
	@Override
	public String toString(Master master) {
		return MasterUtil.toString(master);
	}
	
	@Override
	public int compareTo(MasterListObject other) {
		Object thisKey = getKey(this.master);
		Object otherKey = getKey(other.master);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		MasterListObject other = (MasterListObject) object;
		Object thisKey = MasterUtil.getKey(this.master);
		Object otherKey = MasterUtil.getKey(other.master);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
