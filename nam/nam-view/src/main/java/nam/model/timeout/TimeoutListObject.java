package nam.model.timeout;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Timeout;
import nam.model.util.TimeoutUtil;


public class TimeoutListObject extends AbstractListObject<Timeout> implements Comparable<TimeoutListObject>, Serializable {
	
	private Timeout timeout;
	
	
	public TimeoutListObject(Timeout timeout) {
		this.timeout = timeout;
	}
	
	
	public Timeout getTimeout() {
		return timeout;
	}
	
	@Override
	public Object getKey() {
		return getKey(timeout);
	}
	
	public Object getKey(Timeout timeout) {
		return TimeoutUtil.getKey(timeout);
	}
	
	@Override
	public String getLabel() {
		return getLabel(timeout);
	}
	
	public String getLabel(Timeout timeout) {
		return TimeoutUtil.getLabel(timeout);
	}
	
	@Override
	public String toString() {
		return toString(timeout);
	}
	
	@Override
	public String toString(Timeout timeout) {
		return TimeoutUtil.toString(timeout);
	}
	
	@Override
	public int compareTo(TimeoutListObject other) {
		Object thisKey = getKey(this.timeout);
		Object otherKey = getKey(other.timeout);
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
		TimeoutListObject other = (TimeoutListObject) object;
		Object thisKey = TimeoutUtil.getKey(this.timeout);
		Object otherKey = TimeoutUtil.getKey(other.timeout);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
