package nam.model.unit;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Unit;
import nam.model.util.UnitUtil;


public class UnitListObject extends AbstractListObject<Unit> implements Comparable<UnitListObject>, Serializable {
	
	private Unit unit;
	
	
	public UnitListObject(Unit unit) {
		this.unit = unit;
	}
	
	
	public Unit getUnit() {
		return unit;
	}
	
	@Override
	public Object getKey() {
		return getKey(unit);
	}
	
	public Object getKey(Unit unit) {
		return UnitUtil.getKey(unit);
	}
	
	@Override
	public String getLabel() {
		return getLabel(unit);
	}
	
	public String getLabel(Unit unit) {
		return UnitUtil.getLabel(unit);
	}
	
	@Override
	public String toString() {
		return toString(unit);
	}
	
	@Override
	public String toString(Unit unit) {
		return UnitUtil.toString(unit);
	}
	
	@Override
	public int compareTo(UnitListObject other) {
		Object thisKey = getKey(this.unit);
		Object otherKey = getKey(other.unit);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		UnitListObject other = (UnitListObject) object;
		Object thisKey = UnitUtil.getKey(this.unit);
		Object otherKey = UnitUtil.getKey(other.unit);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
