package nam.model.fault;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Fault;
import nam.model.util.FaultUtil;


public class FaultListObject extends AbstractListObject<Fault> implements Comparable<FaultListObject>, Serializable {
	
	private Fault fault;
	
	
	public FaultListObject(Fault fault) {
		this.fault = fault;
	}
	
	
	public Fault getFault() {
		return fault;
	}
	
	@Override
	public Object getKey() {
		return getKey(fault);
	}
	
	public Object getKey(Fault fault) {
		return FaultUtil.getKey(fault);
	}
	
	@Override
	public String getLabel() {
		return getLabel(fault);
	}
	
	public String getLabel(Fault fault) {
		return FaultUtil.getLabel(fault);
	}
	
	@Override
	public String toString() {
		return toString(fault);
	}
	
	@Override
	public String toString(Fault fault) {
		return FaultUtil.toString(fault);
	}
	
	@Override
	public int compareTo(FaultListObject other) {
		Object thisKey = getKey(this.fault);
		Object otherKey = getKey(other.fault);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		FaultListObject other = (FaultListObject) object;
		Object thisKey = FaultUtil.getKey(this.fault);
		Object otherKey = FaultUtil.getKey(other.fault);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
